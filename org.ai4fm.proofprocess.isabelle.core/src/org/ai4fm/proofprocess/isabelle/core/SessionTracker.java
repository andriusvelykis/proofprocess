package org.ai4fm.proofprocess.isabelle.core;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import scala.collection.JavaConversions;

import isabelle.Command;
import isabelle.Session;
import isabelle.Session.Commands_Changed;
import isabelle.eclipse.util.SessionEventSupport;
import isabelle.scala.ISessionCommandsListener;
import isabelle.scala.SessionActor;
import isabelle.scala.SessionEventType;

public class SessionTracker {

	private final SessionEventSupport sessionEvents;
	
	private final Job analyseJob;
	
	/**
	 * A concurrent queue is used for pending events, because the queue is
	 * likely to be updated from multiple threads.
	 */
	private final Queue<CommandAnalysisEvent> pendingEvents = 
			new ConcurrentLinkedQueue<CommandAnalysisEvent>();
	
	public SessionTracker() {
		
		this.analyseJob = new AnalyseJob();
		// add a listener that reschedules the job after completion if there are pending events
		analyseJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (!pendingEvents.isEmpty()) {
					// there are pending events, reschedule the job to consume them
					analyseJob.schedule();
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
		
		// get proof entries for each command
		for (Command command : changedCommands) {
			addPendingAnalysis(command);
		}
		
		// wake up the analysis job
		analyseJob.schedule();
	}
	
	private void addPendingAnalysis(Command changedCommand) {
		pendingEvents.add(new CommandAnalysisEvent(changedCommand, getProof(changedCommand)));
	}
	
	private List<Command> getProof(Command lastCommand) {
		// TODO implement proof retrieval
		return Collections.emptyList();
	}
	
	private static class CommandAnalysisEvent {
		
		private final Command changedCommand;
		private final List<Command> commandProof;
		
		public CommandAnalysisEvent(Command changedCommand, List<Command> commandProof) {
			super();
			this.changedCommand = changedCommand;
			this.commandProof = commandProof;
		}
	}
	
	private class AnalyseJob extends Job {

		public AnalyseJob() {
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
