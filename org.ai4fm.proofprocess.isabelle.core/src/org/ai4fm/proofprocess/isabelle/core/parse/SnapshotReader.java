package org.ai4fm.proofprocess.isabelle.core.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Assert;

import scala.collection.Iterator;

import isabelle.Command;
import isabelle.Linear_Set;
import isabelle.Session;
import isabelle.Text;
import isabelle.Command.State;
import isabelle.Document.Snapshot;
import isabelle.eclipse.core.IsabelleCorePlugin;
import isabelle.scala.ScalaCollections;

public class SnapshotReader {
	
	/**
	 * List of commands which can start the proof, so everything after such a
	 * command would be a new proof.
	 */
	private static Set<String> PROOF_START_CMDS = Collections.unmodifiableSet(
			new HashSet<String>(Arrays.asList(
			"lemma", "function", "primrec", "definition")));
	
	
	public List<List<State>> getProofStates(Set<Command> changedCommands) {
		
		List<List<State>> proofStates = new ArrayList<List<State>>();
		
		// get proof entries for each command
		for (Command command : changedCommands) {
			List<State> proofState = getProofState(command);
			if (!proofState.isEmpty()) {
				proofStates.add(proofState);
			}
		}
		
		return proofStates;
	}
	
	private List<State> getProofState(Command changedCommand) {
		
		if (!isProofProcessCommand(changedCommand)) {
			return Collections.emptyList();
		}
		
		Session session = IsabelleCorePlugin.getIsabelle().getSession();
		Snapshot snapshot = session.snapshot(changedCommand.node_name(), ScalaCollections.<Text.Edit>emptyList());

		Linear_Set<Command> nodeCommands = snapshot.node().commands();
		
		List<State> proofState = getProof(snapshot, changedCommand, nodeCommands);
		Assert.isTrue(!proofState.isEmpty());
		return proofState;
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
		return PROOF_START_CMDS.contains(command.name());
	}
	
}
