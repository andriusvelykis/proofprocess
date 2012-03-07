package org.ai4fm.proofprocess.isabelle.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import scala.collection.Iterator;
import scala.collection.JavaConversions;

import isabelle.Command;
import isabelle.Command.State;
import isabelle.Document.Snapshot;
import isabelle.Linear_Set;
import isabelle.Session;
import isabelle.Text;
import isabelle.Session.Commands_Changed;
import isabelle.eclipse.core.IsabelleCorePlugin;
import isabelle.eclipse.util.SessionEventSupport;
import isabelle.scala.ISessionCommandsListener;
import isabelle.scala.ScalaCollections;
import isabelle.scala.SessionActor;
import isabelle.scala.SessionEventType;

public class SessionTracker {

	private final SessionEventSupport sessionEvents;
	
	private final Job analyseJob;
	
	private static Set<String> proofStartCommands = Collections.unmodifiableSet(
			new HashSet<String>(Arrays.asList(
			"lemma", "function", "primrec", "definition")));
	
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
	
	private boolean addPendingAnalysis(Command changedCommand) {
		
		if (!isProofProcessCommand(changedCommand)) {
			return false;
		}
		
		Session session = IsabelleCorePlugin.getIsabelle().getSession();
		Snapshot snapshot = session.snapshot(changedCommand.node_name(), ScalaCollections.<Text.Edit>emptyList());

		Linear_Set<Command> nodeCommands = snapshot.node().commands();
		
		List<State> proofState = getProof(snapshot, changedCommand, nodeCommands);
		Assert.isTrue(!proofState.isEmpty());
		pendingEvents.add(new CommandAnalysisEvent(proofState));
		return true;
	}
	
	private List<State> getProof(Snapshot snapshot, Command lastCommand,
			Linear_Set<Command> nodeCommands) {

		List<State> proofState = new ArrayList<State>();
		
		System.out.println(">> " + lastCommand);
		
		for (Iterator<Command> cIt = nodeCommands.reverse_iterator(lastCommand); cIt.hasNext();) {
			Command cmd = cIt.next();

			if (isProofProcessCommand(cmd)) {
				// check the command
				State commandState = snapshot.command_state(cmd);
				proofState.add(commandState);
				
				System.out.println("^ " + cmd);
				
				if (isStartOfProof(cmd, commandState)) {
					// start of proof reached - enough for this proof
					break;
				}
			}
		}

		// reverse so that start of proof is at the beginning, and the lastCommand is at the end
		Collections.reverse(proofState);
		return proofState;
	}
	
	private boolean isProofProcessCommand(Command command) {
		// TODO exclude others, e.g. definitions, "thm ...", etc?
		return !command.is_ignored() && !command.is_malformed();
	}
	
	private boolean isStartOfProof(Command command, State commandState) {
		// TODO add checks for "Step 0" in results as well, 
		// e.g. for proofs of "fun" definitions, etc.
		return proofStartCommands.contains(command.name());
	}
	
	private static class CommandAnalysisEvent {
		
		private final List<State> proofState;
		
		public CommandAnalysisEvent(List<State> proofState) {
			super();
			this.proofState = proofState;
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
