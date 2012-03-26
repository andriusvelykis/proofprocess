package org.ai4fm.proofprocess.isabelle.core.parse;

import java.util.EnumSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.ai4fm.proofprocess.isabelle.core.IsabelleProofPlugin;
import org.ai4fm.proofprocess.isabelle.core.analyse.ProofAnalyzer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import scala.collection.JavaConversions;

import isabelle.Command;
import isabelle.Document;
import isabelle.Session;
import isabelle.Session.Commands_Changed;
import isabelle.eclipse.core.IsabelleCorePlugin;
import isabelle.eclipse.core.util.SafeSessionActor;
import isabelle.eclipse.util.SessionEventSupport;
import isabelle.scala.ISessionCommandsListener;
import isabelle.scala.SessionActor;
import isabelle.scala.SessionEventType;

public class SessionTracker {

	private final SessionEventSupport sessionEvents;
	
	private final Job analysisJob;
	
	/**
	 * A concurrent queue is used for pending events, because the queue is
	 * likely to be updated from multiple threads.
	 */
	private final Queue<CommandAnalysisEvent> pendingEvents = 
			new ConcurrentLinkedQueue<CommandAnalysisEvent>();
	
	public SessionTracker() {
		
		this.analysisJob = new AnalysisJob();
		// add a listener that reschedules the job after completion if there are pending events
		analysisJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (!pendingEvents.isEmpty()) {
					// there are pending events, reschedule the job to consume them
					analysisJob.schedule();
				}
			}
		});
		
		this.sessionEvents = new SessionEventSupport(EnumSet.of(SessionEventType.COMMAND)) {
			
			@Override
			protected SessionActor createSessionActor(Session session) {
				return new SafeSessionActor().commandsChanged(new ISessionCommandsListener() {
					@Override
					public void commandsChanged(Commands_Changed changed) {
						addPendingAnalysis(changed);
					}
				});
			}
		};
		
		// TODO add a pending analysis for commands possibly already in the snapshot
	}
	
	public void dispose() {
		this.sessionEvents.dispose();
	}
	
	private void addPendingAnalysis(Commands_Changed changed) {
		
		Session session = IsabelleCorePlugin.getIsabelle().getSession();
		// Isabelle State is immutable, so just take the whole state for processing 
		// we can analyse what is needed in a low-priority analysis thread
		pendingEvents.add(new CommandAnalysisEvent(
				JavaConversions.setAsJavaSet(changed.commands()),
				session.current_state()));
		
		// wake up the analysis job
		analysisJob.schedule();
	}
	
	private static class CommandAnalysisEvent {

		private final Set<Command> changedCommands;
		private final Document.State docState;

		public CommandAnalysisEvent(Set<Command> changedCommands, Document.State docState) {
			super();
			this.changedCommands = changedCommands;
			this.docState = docState;
		}
	}
	
	private class AnalysisJob extends Job {

		public AnalysisJob() {
			super("Analysing proof process");
			// lowest priority
			setPriority(DECORATE);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			
			CommandAnalysisEvent nextEvent = pendingEvents.poll();
			if (nextEvent == null) {
				// nothing to execute
				return Status.OK_STATUS;
			}
			
			if (IsabelleProofPlugin.getDefault() == null) {
				// shutting down - do not execute any more
				return Status.CANCEL_STATUS;
			}
			
			try {
				return analyze(nextEvent, monitor);
			} catch (CoreException e) {
				return IsabelleProofPlugin.error(
						"Proof process analysis failed: " + e.getMessage(), e);
			}
		}
	}
	
	private IStatus analyze(CommandAnalysisEvent event, IProgressMonitor monitor) throws CoreException {
		
		// delegate to the proof analyzer
		ProofAnalyzer proofAnalyzer = new ProofAnalyzer();
		return proofAnalyzer.analyze(event.changedCommands, event.docState, monitor);
	}
	
}
