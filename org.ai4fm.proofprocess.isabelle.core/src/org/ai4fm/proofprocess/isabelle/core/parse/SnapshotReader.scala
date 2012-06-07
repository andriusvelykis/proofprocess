package org.ai4fm.proofprocess.isabelle.core.parse

import isabelle.Command
import isabelle.Command.State
import isabelle.Document
import isabelle.Document.Node.Name
import isabelle.Document.Snapshot
import isabelle.Linear_Set
import isabelle.Isar_Document
import org.ai4fm.proofprocess.Term


/** Reads the proof states from the current snapshot. Given a set of changed commands, calculates
  * the proof step chains that involve all changed commands. These represent the "changed proofs".
  *
  * @author Andrius Velykis
  */
object SnapshotReader {

  /** List of commands which can start the proof, so everything after such a command would be a new proof. */
  private val PROOF_START_CMDS = Set("lemma", "theorem", "function", "primrec", "definition")
  
  case class ProofTextData(val path: String, val documentText: String, syncPoint: Int)
  case class ProofData(val proofState: List[(State, List[Term])], val textData: ProofTextData)

  def readProofs(docState: Document.State, changedCommands: Set[Command]): (List[ProofData], Map[Command, Int]) = {
    // filter the proof process commands to valid one 
    val validCmds = changedCommands.filter(isValidProofCommand)

    // load the snapshots into the snapshots map, so that we do not load
    // multiple snapshots for the commands arising from the same document
    val snapshots = collectSnapshots(docState, validCmds)

    val proofSpans = collectProofSpans(snapshots, validCmds)
    val proofStates = proofSpans.map(filterProofSpan)

    val proofsWithGoals = proofStates.map(withGoals)

    // filter out empty proofs and return
    val proofs = proofsWithGoals.filterNot(_.isEmpty)
    
    // Print the commands into a text document. Each command carries the original source from
    // the text document, so concatenating them back together produces the original document.
    val docTexts = snapshots.map({ case (doc, snapshot) => (doc, snapshot.node.commands.toList.map(_.source).mkString)})
    // create a map of all command starts - needed to indicate command location
    val commandStarts = snapshots.values.map(_.node.command_starts.toMap) reduce (_ ++ _)
    
    val proofData = proofs map { proof =>
      
      val (lastState, _) = proof.last
      val lastCmd = lastState.command
      val doc = lastCmd.node_name
      val snapshot = snapshots.get(doc).get
      
      val command_starts = snapshot.node.command_starts.toMap
      
      val lastCmdOffset = snapshot.node.command_start(lastCmd).get
      val documentText = docTexts.get(doc).get
      
      ProofData(proof, ProofTextData(doc.node, documentText, lastCmdOffset + lastCmd.length))
    }
    
    (proofData, commandStarts)
  }
  

  private def collectSnapshots(docState: Document.State, cmds: Set[Command]): Map[Name, Snapshot] = {

    val cmdDocs = cmds.map(_.node_name)
    val snapshots = cmdDocs map { docState.snapshot(_, Nil) }

    cmdDocs.zip(snapshots).toMap
  }
  

  private def collectProofSpans(snapshots: Map[Name, Snapshot], commands: Set[Command]): List[List[State]] = {

    if (commands.isEmpty) {
      Nil
    } else {
      val cmd = commands.head
      // take the remaining commands
      val remainingCmds = commands.filter(_ != cmd)

      // snapshot must always be present
      val snapshot = snapshots.get(cmd.node_name).get
      val nodeCommands = snapshot.node.commands

      if (nodeCommands.contains(cmd)) {

        val proofState = collectProofSpan(snapshot, nodeCommands, cmd)
        // Remove the collected commands from the remaining list to avoid including them twice.
        // It is possible that commands contains commands from the same proof
        val proofCmds = proofState.map(_.command)
        val filteredRemainingCmds = remainingCmds.filterNot(proofCmds.contains)

        // continue recursively with the subset of remaining commands
        proofState :: collectProofSpans(snapshots, filteredRemainingCmds)
      } else {
        // Happens fairly often if the text is being edited live.
        // This basically means that the snapshot has changed even more since the changed
        // command - the command belongs to an old version. Ignore it.
        collectProofSpans(snapshots, remainingCmds)
      }
    }
  }
  

  /** Retrieves the proof state of the target command. The encompassing proof state can span
    * both before and after the target command. We capture everything between two adjacent
    * "proof starts", e.g. between start of the lemma, and start of the next lemma.
    */
  private def collectProofSpan(snapshot: Snapshot, commands: Linear_Set[Command], targetCommand: Command): List[State] = {

    // first of all go backwards and collect everything before the target command
    // stop when the proof start command is reached
    var lastProofStart = false
    val beforeCmdsRev = commands.reverse_iterator(targetCommand).map(snapshot.command_state).takeWhile(state => {
      val prevStart = lastProofStart
      lastProofStart = isProofStart(state)
      // take while previous is not proof start
      !prevStart
    })

    // then go forwards from the target command until the start of the next proof is reached
    val afterIt = commands.iterator(targetCommand).filterNot(_ == targetCommand)
    val afterCmds = afterIt.map(snapshot.command_state).takeWhile(!isProofStart(_))

    beforeCmdsRev.toList.reverse ::: afterCmds.toList
  }
  

  private def isProofStart(cmdState: State): Boolean = {
    // TODO add checks for "Step 0" in results as well, 
    // e.g. for proofs of "fun" definitions, etc.
    PROOF_START_CMDS.contains(cmdState.command.name);
  }

  private def filterProofSpan(proofState: List[State]): List[State] = {

    // take valid proof commands only
    // do not continue after unfinished commands
    // ignore errors TODO count errors and do not include after certain threshold?
    proofState.filter(state => isValidProofCommand(state.command)).takeWhile(isFinished).takeWhile(!isError(_))
  }

  def isValidProofCommand(command: Command): Boolean = {
    // TODO exclude others, e.g. definitions, "thm ...", etc?
    // take non-ignored and non-malformed commands
    !command.is_ignored && !command.is_malformed
  }

  def isFinished(cmdState: State) = {
    import isabelle.Isar_Document._
    command_status(cmdState.status) == Finished
  }
  
  def isError(cmdState: State) =
    // no errors in the results
    cmdState.results.values.exists(ResultParser.isError)
    
    
  private def withGoals(proofState: List[State]): List[(State, List[Term])] = {
    
    proofState.flatMap(state => {
      val goals = ResultParser.goalTerms(state) orElse {
        // workaround for "by" not outputting goals in "markup" term case
        if ("by".equals(state.command.name)) Some(Nil) else None
      }
      
      // if goals can be resolved, map it with the state into tuple 
      goals map {(state, _)}
    })
  }

}
