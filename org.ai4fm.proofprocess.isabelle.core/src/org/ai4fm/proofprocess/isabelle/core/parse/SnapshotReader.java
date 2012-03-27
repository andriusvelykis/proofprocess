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

import org.eclipse.core.runtime.Assert;

import scala.Tuple2;
import scala.collection.Iterator;

import isabelle.Command;
import isabelle.Document;
import isabelle.Isar_Document$;
import isabelle.Isar_Document$Finished$;
import isabelle.Isar_Document.Status;
import isabelle.Linear_Set;
import isabelle.Text;
import isabelle.Command.State;
import isabelle.Document$Node$;
import isabelle.Document.Snapshot;
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
	
	private static final Document$Node$ DOCUMENT_NODE = Document$Node$.MODULE$;
	private static final Isar_Document$ ISAR_DOCUMENT = Isar_Document$.MODULE$;
	private static final Isar_Document$Finished$ FINISHED = Isar_Document$Finished$.MODULE$;
	
	private final Set<Command> changedCommands;
	private final Document.State docState;
	
	private final Map<DocumentRef, Snapshot> snapshots = new HashMap<DocumentRef, Snapshot>();
	private final Map<DocumentRef, String> docTexts = new HashMap<DocumentRef, String>();
	private final Map<Command, Integer> commandStarts = new HashMap<Command, Integer>();
	private final Map<Command, List<State>> commandProofs = new HashMap<Command, List<State>>();
	
	public SnapshotReader(Set<Command> changedCommands, Document.State docState) {
		super();
		this.changedCommands = new LinkedHashSet<Command>(changedCommands);
		this.docState = docState;
	}

	/**
	 * List of commands which can start the proof, so everything after such a
	 * command would be a new proof.
	 */
	private static Set<String> PROOF_START_CMDS = Collections.unmodifiableSet(
			new HashSet<String>(Arrays.asList(
			"lemma", "theorem", "function", "primrec", "definition")));
	
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
		
		// filter the proof process commands to valid one 
		Set<Command> proofCmds = filterValidProofCommands(changedCommands);
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
		
		List<Command> orderedCmds = new ArrayList<Command>();
		
		for (Command command : revCmds) {
			boolean collected = collectProofs(command);
			if (collected) {
				// if the command is no longer in the snapshot, just ignore it altogether
				orderedCmds.add(command);
			}
		}

		// get all collected proofs
		// try to preserve original specification order, so reverse the collected commands
		Collections.reverse(orderedCmds);
		
		List<List<State>> proofStates = new ArrayList<List<State>>();
		for (Command command : orderedCmds) {
			List<State> proofState = commandProofs.get(command);
			if (proofState == null || proofState.isEmpty()) {
				// invalid command - no proof state captured for it
				// this can be because the proof command is ignored, or it is outdated
				continue;
			}
			
			if (!proofStates.contains(proofState)) {
				// only add unique ones
				proofStates.add(proofState);
			}
		}
		
		return proofStates;
	}
	
	public String getDocumentText(List<State> proofState) {
		if (proofState.isEmpty()) {
			return null;
		}
		
		// take the document ref from the first command
		DocumentRef doc = new DocumentRef(proofState.get(0).command().node_name());
		return docTexts.get(doc);
	}
	
	public String getDocumentText(Command command) {
		DocumentRef doc = new DocumentRef(command.node_name());
		return docTexts.get(doc);
	}
	
	public Integer getCommandStart(Command command) {
		return commandStarts.get(command);
	}
	
	private void collectSnapshots(Set<Command> proofCmds) {
		Set<DocumentRef> changedDocs = getDocs(proofCmds);
		
		for (DocumentRef doc : changedDocs) {
			Snapshot snapshot = docState.snapshot(doc.getRef(), ScalaCollections.<Text.Edit>emptyList());
			snapshots.put(doc, snapshot);
			
			
			/*
			 * Print the commands into a text document. Each command carries the
			 * original source from the text document, so concatenating them back
			 * together produces the original document.
			 * 
			 * Also collect command starts during the same iteration
			 */
			StringBuilder docText = new StringBuilder();
			
			// wrap into command starts iterator
			Iterator<Tuple2<Command, Object>> cIt = DOCUMENT_NODE.command_starts(
					snapshot.node().commands().iterator(), 0);
			
			while (cIt.hasNext()) {
				Tuple2<Command, Object> cmdStart = cIt.next();
				Command cmd = cmdStart._1();
				Integer start = (Integer) cmdStart._2();
				
				// append to the document
				docText.append(cmd.source());
				
				// mark the command start
				commandStarts.put(cmd, start);
			}
			
			docTexts.put(doc, docText.toString());
		}
	}
	
	private Set<DocumentRef> getDocs(Set<Command> commands) {
		
		Set<DocumentRef> changedDocs = new HashSet<DocumentRef>();
		
		for (Command cmd : commands) {
			changedDocs.add(new DocumentRef(cmd.node_name()));
		}
		
		return changedDocs;
	}
	
	private Set<Command> filterValidProofCommands(Set<Command> commands) {
		
		Set<Command> filtered = new HashSet<Command>();
		
		for (Command cmd : commands) {
			if (isValidProofCommand(cmd)) {
				filtered.add(cmd);
			}
		}
		
		return filtered;
	}
	
	private boolean collectProofs(Command lastCommand) {
		
		if (commandProofs.containsKey(lastCommand)) {
			// already collected
			return false;
		}
		
		Snapshot snapshot = snapshots.get(new DocumentRef(lastCommand.node_name()));
		Linear_Set<Command> nodeCommands = snapshot.node().commands();
		
		if (!nodeCommands.contains(lastCommand)) {
			/*
			 * Happens fairly often if the text is being edited live.
			 * 
			 * This basically means that the snapshot has changed even more since the changed
			 * command - the command belongs to an old version. Return false to ignore it.
			 */
//			IsabelleProofPlugin.log("Command not available in the node: " + lastCommand.toString()
//					+ ", " + String.valueOf(lastCommand.source()) + ":" + String.valueOf(lastCommand.range()), null);
			return false;
		}

		List<State> proofState = getProofState(snapshot, nodeCommands, lastCommand);
		
		// filter out invalid proof state elements
		List<State> filteredProofState = filterProofState(proofState);
		
		// mark each command to point to this filtered proof state
		for (State cmdState : proofState) {
			// mark the command for subsequent queries
			// note that the same proof state will be used for this command and all its
			// predecessors. This is ok, since we want to analyse the longest proof chain
			commandProofs.put(cmdState.command(), filteredProofState);
		}
		
		return true;
	}

	/**
	 * Retrieves the proof state of the target command. The encompassing proof
	 * state can span both before and after the target command. We capture
	 * everything between two adjacent "proof starts", e.g. between start of the
	 * lemma, and start of the next lemma.
	 * 
	 * @param snapshot
	 * @param nodeCommands
	 * @param targetCommand
	 * @return
	 */
	private List<State> getProofState(Snapshot snapshot, Linear_Set<Command> nodeCommands, Command targetCommand) {
		
		// first of all go backwards and collect everything before the target command
		List<State> proofState = new LinkedList<State>();
		
		for (Iterator<Command> cIt = nodeCommands.reverse_iterator(targetCommand); cIt.hasNext();) {
			Command cmd = cIt.next();
			
			// check the command
			State commandState = snapshot.command_state(cmd);
			// add the command state to the beginning of proof state (we are going backwards) 
			proofState.add(0, commandState);
			
			if (isStartOfProof(cmd, commandState)) {
				// start of proof reached - enough for this proof
				break;
			}
		}
		
		// then go forwards from the target command until the start of the next proof is reached
		for (Iterator<Command> cIt = nodeCommands.iterator(targetCommand); cIt.hasNext();) {
			Command cmd = cIt.next();
			
			if (cmd == targetCommand) {
				// exclude
				continue;
			}
			
			// check the command
			State commandState = snapshot.command_state(cmd);
			
			if (isStartOfProof(cmd, commandState)) {
				// start of the next proof reached - enough for this proof
				break;
			}
			
			// add the command state to the end of proof state (we are going forwards) 
			proofState.add(commandState);
		}
		
		return proofState;
	}
	
	private List<State> filterProofState(List<State> proofState) {
		List<State> filtered = new ArrayList<State>();
		
		for (State cmdState : proofState) {
			
			if (!isValidProofCommandState(cmdState)) {
				// ignore invalid commands (e.g. unfinished)
				continue;
			}
			
			filtered.add(cmdState);
		}
		
		return filtered;
	}

	private boolean isValidProofCommandState(State commandState) {
		// TODO exclude others, e.g. definitions, "thm ...", etc?
		// take non-ignored and non-malformed commands, which computation has finished.
		Command command = commandState.command();
		
		return isValidProofCommand(command)
				&& (ISAR_DOCUMENT.command_status(commandState.status()) == FINISHED);
	}
	
	private boolean isValidProofCommand(Command command) {
		// TODO exclude others, e.g. definitions, "thm ...", etc?
		// take non-ignored and non-malformed commands
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
