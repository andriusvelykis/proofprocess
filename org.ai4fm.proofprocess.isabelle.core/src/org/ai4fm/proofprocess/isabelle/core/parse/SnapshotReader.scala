package org.ai4fm.proofprocess.isabelle.core.parse

import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.core.analysis.{Assumption, Judgement}
import org.ai4fm.proofprocess.isabelle.core.parse.ResultParser.StepProofType._

import isabelle.Command
import isabelle.Command.State
import isabelle.Document
import isabelle.Document.Node.Name
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
  case class ProofData(val proofState: List[StepResults], val textData: ProofTextData)

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
    
    def nodeCommandStarts(node: Document.Node) = Document.Node.command_starts(node.commands.iterator)
    
    // create a map of all command starts - needed to indicate command location
    val commandStartMaps = snapshots.values.map(s => nodeCommandStarts(s.node).toMap)
    // merge all maps (check for empty map case)
    val commandStarts = commandStartMaps reduceLeftOption (_ ++ _) getOrElse (Map.empty)
    
    val proofData = proofs map { proof =>
      
      val lastState = proof.last.state
      val lastCmd = lastState.command
      val doc = lastCmd.node_name
      val snapshot = snapshots.get(doc).get
      
      val command_starts = nodeCommandStarts(snapshot.node).toMap
      
      val lastCmdOffset = snapshot.node.command_start(lastCmd).get
      val documentText = docTexts.get(doc).get
      
      ProofData(proof, ProofTextData(doc, documentText, lastCmdOffset + lastCmd.length))
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
    import isabelle.Protocol._
    command_status(cmdState.status).is_finished
  }
  
  def isError(cmdState: State) =
    // no errors in the results
    cmdState.results.values.exists(ResultParser.isError)


  // "picking this" both in `in` and `out`?
  private val IN_ASSM_LABELS = Set("picking this:", "using this:")
  private val OUT_ASSM_LABELS = Set("picking this:", "this:")
  
  private val ALL_ASSM_LABELS = IN_ASSM_LABELS ++ OUT_ASSM_LABELS

  private def withGoals(proofState: List[State]): List[StepResults] = {

    proofState flatMap (state => {

      val assmTerms = ResultParser.labelledTerms(state,
        label => ALL_ASSM_LABELS.contains(label.trim))

      // trim the keys (remove newlines, etc)
      val trimmedAssms = assmTerms map { case (k, v) => (k.trim, v) }

      val inAssms = collectMapped(trimmedAssms, IN_ASSM_LABELS)
      val outAssms = collectMapped(trimmedAssms, OUT_ASSM_LABELS)
      
      val isByCmd = "by" == state.command.name

      val stepTypeOpt = ResultParser.stepProofType(state)
      // last step 'by' has no output, so assume 'prove' step type
      val stepType = stepTypeOpt orElse ( if (isByCmd) Some(Prove) else None ) 
      
      // workaround for "by" not outputting goals in "markup" term case
      // also "by" when used in forward proof can go into "state" proof and output outstanding
      // goals - so we replace it with "finished", i.e. empty list of goals
      val goals = if (isByCmd) Some(Nil) else ResultParser.goalTerms(state)
      
      // only produce results if step type is defined
      stepType map ( StepResults(state, _, inAssms, outAssms, goals) )
    })
  }
  
  private def collectMapped[A, B](mapped: Map[A, List[B]], collect: Set[A]): List[B] = {
    val results = collect.toList flatMap mapped.get
    results.flatten
  }

  case class StepResults(state: State,
                         stateType: StepProofType,
                         inAssms: List[Term],
                         outAssms: List[Term],
                         outGoals: Option[List[Term]]) {
    
    lazy val inAssmProps = inAssms map (Assumption(_))
    lazy val outAssmProps = outAssms map (Assumption(_))
    
    private def addInAssms(goal: Judgement[Term]): Judgement[Term] = {
      val updAssms = (goal.assms ::: inAssms).distinct
      goal.copy( assms = updAssms )
    }

    // convert each goal to a Judgement (assms + goal)
    // also link explicit assumptions with out goals - add them to each out goal
    lazy val outGoalProps = outGoals map { goals =>
      goals map ResultParser.splitAssmsGoal map addInAssms
    }
    
  }

}
