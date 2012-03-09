package org.ai4fm.proofprocess.isabelle.core.parse;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.ai4fm.proofprocess.isabelle.core.IsabelleProofPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import scala.collection.JavaConversions;

import isabelle.Command;
import isabelle.Command.State;
import isabelle.Session;
import isabelle.Session.Commands_Changed;
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
				return new SessionActor().commandsChanged(new ISessionCommandsListener() {
					@Override
					public void commandsChanged(Commands_Changed changed) {
						addPendingAnalysis(JavaConversions.setAsJavaSet(changed.commands()));
					}
				});
			}
		};
		
		// TODO add a pending analysis for commands possibly already in the snapshot
	}
	
	public void dispose() {
		this.sessionEvents.dispose();
	}
	
	private void addPendingAnalysis(Set<Command> changedCommands) {
		
		SnapshotReader reader = new SnapshotReader(changedCommands);
		List<List<State>> proofStates = reader.readProofStates();
		if (proofStates.isEmpty()) {
			// nothing found to analyse
			return;
		}
		
		for (List<State> proofState : proofStates) {
			String documentText = reader.getDocumentText(proofState);
			Set<Command> proofCommands = filterProofCommands(proofState, changedCommands);
			pendingEvents.add(new CommandAnalysisEvent(proofState, proofCommands, documentText));
		}
		
		// wake up the analysis job
		analysisJob.schedule();
	}
	
	private Set<Command> filterProofCommands(List<State> proofState, Set<Command> filter) {
		
		Set<Command> proofCmds = new HashSet<Command>();
		for (State cmdState : proofState) {
			proofCmds.add(cmdState.command());
		}
		
		// keep just the commands in the filter
		proofCmds.retainAll(filter);
		
		return proofCmds;
	}
	
	private static class CommandAnalysisEvent {

		private final List<State> proofState;
		private final Set<Command> changedCommands;
		private final String documentText;

		public CommandAnalysisEvent(List<State> proofState, Set<Command> changedCommands,
				String documentText) {
			super();
			this.proofState = proofState;
			this.changedCommands = changedCommands;
			this.documentText = documentText;
		}
	}
	
	private class AnalysisJob extends Job {

		public AnalysisJob() {
			super("Analysing proof process");
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
				return analyse(nextEvent, monitor);
			} catch (CoreException e) {
				return IsabelleProofPlugin.error(
						"Proof process analysis failed: " + e.getMessage(), e);
			}
		}
	}
	
	private IStatus analyse(CommandAnalysisEvent event, IProgressMonitor monitor) throws CoreException {
		// TODO implement analysis
		return Status.OK_STATUS;
	}
	
	
}
