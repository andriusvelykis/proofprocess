package org.ai4fm.proofprocess.isabelle.core.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ai4fm.proofprocess.isabelle.core.IsabelleProofPlugin;
import org.eclipse.core.runtime.Assert;

import scala.collection.Iterator;

import isabelle.Command;
import isabelle.Linear_Set;
import isabelle.Session;
import isabelle.Text;
import isabelle.Command.State;
import isabelle.Document.Snapshot;
import isabelle.eclipse.core.IsabelleCorePlugin;
import isabelle.scala.DocumentRef;
import isabelle.scala.ScalaCollections;

/**
 * Reads the proof states from the current snapshot. Given a set of changed
 * commands, calculates the proof step chains that involve all changed commands.
 * These represent the "changed proofs".
 * 
 * @author Andrius Velykis
 */
public class SnapshotReader {
	
	private final Set<Command> changedCommands;
	
	private final Map<DocumentRef, Snapshot> snapshots = new HashMap<DocumentRef, Snapshot>();
	private final Map<Command, List<State>> commandProofs = new HashMap<Command, List<State>>();
	
	public SnapshotReader(Set<Command> changedCommands) {
		super();
		this.changedCommands = new LinkedHashSet<Command>(changedCommands);
	}

	/**
	 * List of commands which can start the proof, so everything after such a
	 * command would be a new proof.
	 */
	private static Set<String> PROOF_START_CMDS = Collections.unmodifiableSet(
			new HashSet<String>(Arrays.asList(
			"lemma", "function", "primrec", "definition")));
	
	/**
	 * Retrieves current snapshots for the given commands, and reads the proof
	 * states.
	 * <p>
	 * For the set of commands, a list of "proofs" is retrieved, i.e. from the
	 * beginning of the proof, to the last changed command. So if several
	 * commands have changed in the proof, it will retrieve the longest proof
	 * chain.
	 * </p>
	 * 
	 * @return list of proofs, represented by a list of proof command states.
	 */
	public List<List<State>> readProofStates() {
		
		// filter the proof process commands, and 
		Set<Command> proofCmds = getProofProcessCommands(changedCommands);
		if (proofCmds.isEmpty()) {
			return Collections.emptyList();
		}
		
		// load the snapshots into the #snapshots map, so that we do not load
		// multiple snapshots for the commands arising from the same document
		collectSnapshots(proofCmds);
		
		/*
		 * Collect proof entries for each command.
		 * 
		 * First collect the proof entries, and only then query, because
		 * multiple commands may be represented by the same proof.
		 * 
		 * Note that we reverse the command order, so to avoid too much
		 * re-calculations of the same proof, if multiple commands have changed
		 * in a proof.
		 */
		List<Command> revCmds = new ArrayList<Command>(proofCmds);
		Collections.sort(revCmds, new CommandIdComparator(false));
		
		for (Command command : proofCmds) {
			collectProofs(command);
		}

		// get all collected proofs
		// try to preserve original specification order, so reverse the revCmds
		List<Command> orderedCmds = new ArrayList<Command>(revCmds);
		Collections.reverse(orderedCmds);
		
		List<List<State>> proofStates = new ArrayList<List<State>>();
		for (Command command : orderedCmds) {
			List<State> proofState = commandProofs.get(command);
			Assert.isTrue(!proofState.isEmpty());
			if (!proofStates.contains(proofState)) {
				// only add unique ones
				proofStates.add(proofState);
			}
		}
		
		return proofStates;
	}

	private void collectSnapshots(Set<Command> proofCmds) {
		Set<DocumentRef> changedDocs = getDocs(proofCmds);
		Session session = IsabelleCorePlugin.getIsabelle().getSession();
		
		for (DocumentRef doc : changedDocs) {
			Snapshot snapshot = session.snapshot(doc.getRef(), ScalaCollections.<Text.Edit>emptyList());
			snapshots.put(doc, snapshot);
		}
	}
	
	private Set<DocumentRef> getDocs(Set<Command> commands) {
		
		Set<DocumentRef> changedDocs = new HashSet<DocumentRef>();
		
		for (Command cmd : commands) {
			changedDocs.add(new DocumentRef(cmd.node_name()));
		}
		
		return changedDocs;
	}
	
	private Set<Command> getProofProcessCommands(Set<Command> commands) {
		
		// create an ordered set, which will order commands according to their index
		// since the IDs are decreasing, we will get back-to-front ordering
		// which is good for collecting proofs
		Set<Command> filtered = new HashSet<Command>();
		
		for (Command cmd : commands) {
			if (isProofProcessCommand(cmd)) {
				filtered.add(cmd);
			}
		}
		
		return filtered;
	}
	
	private void collectProofs(Command lastCommand) {
		
		if (commandProofs.containsKey(lastCommand)) {
			// already collected
			return;
		}
		
		Snapshot snapshot = snapshots.get(new DocumentRef(lastCommand.node_name()));
		Linear_Set<Command> nodeCommands = snapshot.node().commands();
		
		if (!nodeCommands.contains(lastCommand)) {
			IsabelleProofPlugin.log("Command not available in the node: " + lastCommand.toString()
					+ ", " + String.valueOf(lastCommand.source()) + ":" + String.valueOf(lastCommand.range()), null);
			return;
		}

		List<State> proofState = new LinkedList<State>();
		
		for (Iterator<Command> cIt = nodeCommands.reverse_iterator(lastCommand); cIt.hasNext();) {
			Command cmd = cIt.next();

			if (isProofProcessCommand(cmd)) {
				
				// check the command
				State commandState = snapshot.command_state(cmd);
				// add the command state to the beginning of proof state (we are going backwards) 
				proofState.add(0, commandState);
				
				// mark the command for subsequent queries
				// note that the same proof state will be used for this command and all its
				// predecessors. This is ok, since we want to analyse the longest proof chain
				commandProofs.put(cmd, proofState);
				
				if (isStartOfProof(cmd, commandState)) {
					// start of proof reached - enough for this proof
					break;
				}
			}
		}
	}
	

	private boolean isProofProcessCommand(Command command) {
		// TODO exclude others, e.g. definitions, "thm ...", etc?
		return !command.is_ignored() && !command.is_malformed();
	}
	
	private boolean isStartOfProof(Command command, State commandState) {
		// TODO add checks for "Step 0" in results as well, 
		// e.g. for proofs of "fun" definitions, etc.
		return PROOF_START_CMDS.contains(command.name());
	}
	
	private static class CommandIdComparator implements Comparator<Command> {
		
		private boolean oldToNew;
		
		/**
		 * @param oldToNew
		 *            whether the commands are to be sorted from older to newer,
		 *            {@code false} will sort new to old
		 */
		public CommandIdComparator(boolean oldToNew) {
			super();
			this.oldToNew = oldToNew;
		}

		@Override
		public int compare(Command o1, Command o2) {
			// sort by IDs
			long diff = o1.id() - o2.id();
			int result = diff == 0 ? 0 : (diff > 0 ? 1 : -1);
			
			// IDs are decreasing for newer commands, e.g. -1, -2, ...
			// So for #oldToNew = true we want to inverse the difference,
			// and keep it for #oldToNew = false
			return oldToNew ? -1 * result : result;
		}
		
	}
	
}
