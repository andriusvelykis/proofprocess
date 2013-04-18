package org.ai4fm.proofprocess.isabelle.core.parse

import scala.collection.TraversableOnce

import org.ai4fm.proofprocess.isabelle.core.parse.ResultParser.CommandValueState

import isabelle.{Command, Document}
import isabelle.Document.Snapshot
import isabelle.Linear_Set
import isabelle.Protocol.command_status


/** Reads the proof states from the current snapshot. Given a set of changed commands, calculates
  * the proof step chains that involve all changed commands. These represent the "changed proofs".
  *
  * @author Andrius Velykis
  */
object SnapshotReader {

  /** List of commands which can start the proof, so everything after such a command would be a new proof. */
  private val PROOF_START_CMDS = Set("lemma", "theorem", "function", "primrec", "definition")
  
  case class ProofTextData(val name: Document.Node.Name, val documentText: String, syncPoint: Int)
  case class ProofData(val proofState: List[CommandResults], val textData: ProofTextData)

  /**
   * Reads proofs of that include the given commands from the given snapshots state.
   */
  def readProofs(docState: Document.State,
                 changedCommands: Set[Command]): (List[ProofData], Map[Command, Int]) = {

    // filter the proof process commands to valid one 
    val validCmds = changedCommands.filter(isValidProofCommand)

    // load the snapshots into the snapshots map, so that we do not load
    // multiple snapshots for the commands arising from the same document
    val snapshots = collectSnapshots(docState, validCmds)

    // also collect document text and command starts:
    // needed for file history sync and command locations
    val documents = snapshots mapValues collectDocumentInfo

    val proofSpans = collectProofSpans(snapshots, validCmds)

    val proofSpanResults = proofSpans map readProof(documents)
    val validResultSpans = proofSpanResults.flatten

    // merge all command starts into a single map
    val allCommandStarts = mergeMaps(documents.values.iterator map (_.commandStarts))

    (validResultSpans, allCommandStarts)
  }


  private def collectSnapshots(docState: Document.State,
                               cmds: Set[Command]): Map[Document.Node.Name, Snapshot] = {

    val cmdDocs = cmds.map(_.node_name)
    val snapshots = cmdDocs map { docState.snapshot(_, Nil) }

    cmdDocs.zip(snapshots).toMap
  }


  private def collectProofSpans(snapshots: Map[Document.Node.Name, Snapshot],
                                commands: Set[Command]): List[List[Command.State]] = {

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


  /**
   * Retrieves the proof state of the target command. The encompassing proof state can span
   * both before and after the target command. We capture everything between two adjacent
   * "proof starts", e.g. between start of the lemma, and start of the next lemma.
   */
  private def collectProofSpan(snapshot: Snapshot,
                               commands: Linear_Set[Command],
                               targetCommand: Command): List[Command.State] = {

    def commandState(cmd: Command) = snapshot.state.command_state(snapshot.version, cmd)
    
    // first of all go backwards and collect everything before the target command
    // stop when the proof start command is reached
    var lastProofStart = false
    val beforeCmdsRev = commands.reverse.iterator(targetCommand).map(commandState).takeWhile(state => {
      val prevStart = lastProofStart
      lastProofStart = isProofStart(state)
      // take while previous is not proof start
      !prevStart
    })

    // then go forwards from the target command until the start of the next proof is reached
    val afterIt = commands.iterator(targetCommand).filterNot(_ == targetCommand)
    val afterCmds = afterIt.map(commandState).takeWhile(!isProofStart(_))

    beforeCmdsRev.toList.reverse ::: afterCmds.toList
  }
  

  private def isProofStart(cmdState: Command.State): Boolean = {
    // TODO add checks for "Step 0" in results as well, 
    // e.g. for proofs of "fun" definitions, etc.
    PROOF_START_CMDS.contains(cmdState.command.name);
  }


  /**
   * Tries parsing valid proof results from the given proof span.
   *
   * Returns a minimal valid/parsed proof as a list of command results,
   * or None if the proof span is invalid (unfinished/erroneous/unparsable).
   */
  private def readProof(documents: Map[Document.Node.Name, DocumentInfo])(
                          proofSpan: List[Command.State]): Option[ProofData] = {
    // take minimum valid proof span (e.g. no errors, must be finished)
    val minValidSpan = filterProofSpan(proofSpan)

    val commandResults = minValidSpan.toStream map ResultParser.parseCommandResults

    // only take the first successfully parsed results (stop at parse problems)
    val validResults = commandResults takeWhile (_.isDefined)

    if (validResults.isEmpty) {
      None
    } else {
      // unpack
      val results = validResults.flatten.toList 

      // use the last command as a sync point
      val lastCmd = results.last.state.command
      val docName = lastCmd.node_name

      // resolve document text
      val docInfo = documents(docName)

      // use the last command end as the sync point
      val lastCmdOffset = docInfo.commandStarts(lastCmd)
      val proofEnd = lastCmdOffset + lastCmd.length

      Some(ProofData(results, ProofTextData(docName, docInfo.text, proofEnd)))
    }
  }


  private def filterProofSpan(proofState: List[Command.State]): List[Command.State] = {

    // take valid proof commands only
    // do not continue after unfinished/erroneous commands
    val valid = proofState.filter(state => isValidProofCommand(state.command))
    val finished = valid.takeWhile(isFinished)
    val nonErr = finished.takeWhile(!isError(_))
    nonErr
  }

  def isValidProofCommand(command: Command): Boolean = {
    // TODO exclude others, e.g. definitions, "thm ...", etc?
    // take non-ignored and non-malformed commands
    command.is_command
  }

  def isFinished(cmdState: Command.State) =
    command_status(cmdState.status).is_finished
  
  def isError(cmdState: Command.State) =
    // no errors in the results
    cmdState.resultValues.exists(ResultParser.isError)


  private def collectDocumentInfo(snapshot: Snapshot): DocumentInfo = {
    // Print the commands into a text document. Each command carries the original source from
    // the text document, so concatenating them back together produces the original document.
    val snapshotCmds = snapshot.node.commands.iterator
    val snapshotText = (snapshotCmds map (_.source)).mkString

    // create a map of command starts (to indicate command location)
    val cmdStartsIt = Document.Node.command_starts(snapshot.node.commands.iterator)
    val cmdStarts = cmdStartsIt.toMap

    DocumentInfo(snapshotText, cmdStarts)
  }


  /** Merges the given maps. Returns empty map if no maps are given. */
  private def mergeMaps[K, V](maps: TraversableOnce[Map[K, V]]): Map[K, V] =
    maps reduceLeftOption (_ ++ _) getOrElse Map.empty


  private case class DocumentInfo(text: String, commandStarts: Map[Command, Int])

}
