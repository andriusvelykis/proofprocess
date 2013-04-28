package org.ai4fm.proofprocess.isabelle.core.parse

import scala.collection.JavaConversions._

import scalax.collection.GraphPredef._
import scalax.collection.immutable.Graph

import org.ai4fm.proofprocess.{Intent, Loc, ProofEntry, ProofProcessFactory, Term, Trace}
import org.ai4fm.proofprocess.core.analysis.{
  Assumption,
  Eq,
  EqTerm,
  GoalGraphMatcher2,
  GoalStep,
  Judgement,
  Proposition
}
import org.ai4fm.proofprocess.core.graph.PProcessGraph._
import org.ai4fm.proofprocess.isabelle.{IsabelleProofProcessFactory, IsabelleTrace}
import org.ai4fm.proofprocess.isabelle.core.parse.ResultParser.StepProofType._

import isabelle.Command


/**
  * @author Andrius Velykis 
  */
trait ProofEntryReader {

  private type GoalStepT = GoalStep[Command.State, EqTerm]

  import ProofEntryReader.ParseEntries
  
  private val factory = ProofProcessFactory.eINSTANCE
  private val isaFactory = IsabelleProofProcessFactory.eINSTANCE
  
  def stepIntent: Intent
  
  def cloneTerm(term: Term): Term
  
  def matchTerms(term1: Term, term2: Term): Boolean
  
  def textLoc(cmd: Command.State): Loc

  
  /**
   * An extractor object for valid proof:
   * one that has a lemma definition command followed by at least one proof command.
   * 
   * Extracts the lemma command and the remaining proof commands, if available.
   */
  object NonEmptyProof {

    def unapply(proofState: List[CommandResults]): Option[(CommandResults, List[CommandResults])] =
      proofState match {
        // Assume that a proof with just one command (e.g. "declaration") is too short
        // to be included in the ProofProcess. This way we only concern ourselves with proofs
        // that have been attempted (instead of capturing every version of lemma declaration)
        case lemmaCmd :: restCmds
            if !lemmaCmd.outGoals.isEmpty && !restCmds.isEmpty => {
          // Assume that the first step in any proof is the "declaration" command, e.g. "lemma ..."
          // Also check that initial goals are not empty - don't allow proofs with empty goals
          // Also check that proof steps are available
          Some(lemmaCmd, restCmds)
        }

        // empty/short proof state - nothing to parse
        case _ => None
      }
  }


  def readEntries(proofState: List[CommandResults]): Option[(ParsedProof, ParseEntries)] =
    proofState match {

      // ensure we are analysing a non-empty proof here
      case NonEmptyProof(lemmaCmd, proofCmds) =>
        Some(parseProofStructure(lemmaCmd, proofCmds))

      case _ => None
    }


  private def parseProofStructure(lemmaStep: CommandResults,
                                  proofSteps: List[CommandResults]): (ParsedProof, ParseEntries) = {

    val (proofGraph, entryMapping) = parseProofGraph(lemmaStep, proofSteps)

    // get proof info from the lemma (first) step
    // TODO judgements instead of outGoals (or convert?)
    val proofGoals = lemmaStep.outGoals getOrElse Nil
    val proofLabel = CommandParser.commandId(lemmaStep.state.command)

    (ParsedProof(proofGoals map (_.term), proofLabel, proofGraph), entryMapping) 
  }


  private def parseProofGraph(initialStep: CommandResults, proofSteps: List[CommandResults])
      : (PPRootGraph[ProofEntry], ParseEntries) = {

    // create goal steps, which show how input goals are transformed to output goals
    val goalSteps = createGoalSteps(initialStep, proofSteps)

    // create ProofEntry elements for each command step - they will be used in further analysis
    // this bridges from Isabelle commands data to ProofProcess data structures
    // use `#proofEntry()` method for transformation
    val (ppSteps, commandEntries) = mapToPPEntries(proofEntry)(goalSteps)
    
    val proofGraph = stepsToGraph(ppSteps)

    (proofGraph, commandEntries)
  }


  private type StructAssmsStack = List[List[Assumption[EqTerm]]]

  private def createGoalSteps(initialStep: CommandResults,
                              proofSteps: List[CommandResults]): List[GoalStepT] = {

    // make a list with ingoals-info-outgoals steps
    val inSteps = initialStep :: proofSteps
    val inOutSteps = inSteps zip proofSteps

    // now try to to make sense of a GoalStep from the before-after results
    val goalSteps = inOutSteps map Function.tupled(analyseGoalStep0)

    // flatten - some of the goals may have been skipped (e.g. chain)
    goalSteps.flatten
  }

  private def analyseGoalStep0(prev: CommandResults,
                               current: CommandResults): Option[GoalStepT] = {

    val goalStepOpt = analyseGoalStep(prev, current)

    // if possible, adapt for have/show commands
    goalStepOpt flatMap (adaptForHaveShow(_, current))
  }

  // FIXME special support for fixing!
  private def analyseGoalStep(prev: CommandResults, current: CommandResults): Option[GoalStepT] =
    (prev.stateType, current.stateType) match {

      // Skip Chain steps, since they are just links between assumptions and their use.
      // The assumption introduction will be captured in the previous State steps, and their use
      // will be captured in the next State.
      case (_, Chain) => None

      case (Prove, _) => {
        // Two consecutive Prove steps, so check for goal diffs
        //
        // Note: it seems that only the previous needs to be `Prove`.
        // Switching from `Prove` to e.g. `State` would require a `proof` command, which may affect
        // the goal (`proof <command>` or keep it the same (e.g. `proof -`).
        // For this reason, only check that the previous is proof

        val (unchanged, changedIn, changedOut) =
          diffs(prev.outGoalProps getOrElse Nil, current.outGoalProps getOrElse Nil)

        // TODO narrow the changed goals further?
        Some(GoalStep(current.state,
          current.inAssmProps ::: changedIn,
          current.outAssmProps ::: changedOut))

      }

//      case (State, _) => // diff in assumptions - they may be repeating?

      case _ => {

        // out assumptions or out goals are exclusive
        val out = if (!current.outAssmProps.isEmpty) current.outAssmProps
        else current.outGoalProps getOrElse Nil

        Some(GoalStep(current.state,
          // TODO review: no "in" goals - previous was a state?
          current.inAssmProps,
          out))
      }
    }

  /**
   * Additional analysis checks to accommodate have/show commands in structured proof:
   * 
   * -  Final steps of such have/show proof are dropped if possible
   * -  "have ..." commands also introduce the assumption, not just an outstanding goal
   *    (note that this assumption technically is not available for the `have` proof itself)
   */
  private def adaptForHaveShow(goalStep: GoalStepT, current: CommandResults): Option[GoalStepT] =
    if (current.structFinish.isDefined) {
      // if this is a "structured proof finish step", i.e. the step that completes
      // a "have ..." or "show ...", drop it (or clear its out-goals)

      if (ResultParser.isByCmd(current.state)) {
        // keep the by cmd analysis.. but remove all out-results
        val updStep = goalStep.copy(out = Nil)
        Some(updStep)
      } else {
        // drop the struct finish command (normally either "done" or "qed")
        // but keep the updated stack
        None
      }

    } else if (ResultParser.isHaveCmd(current.state)) {
      // for "have ..." commands, the goal also becomes an out-assumption
      // (it introduces new assumptions)

      // wrap each out-goal into an Assumption and add to the goal step output
      val goalsAsAssms = current.outGoals map (goals => goals map (Assumption(_))) getOrElse Nil
      val updStep = goalStep.copy(out = goalsAsAssms ::: goalStep.out)
      Some(updStep)

    } else {
      // nothing else to adapt
      Some(goalStep)
    }
  
  
  private def diffs[A](l1: List[A], l2: List[A]): (List[A], List[A], List[A]) = {
    val same = l1 intersect l2
    val diffL1 = l1 diff same
    val diffL2 = l2 diff same

    (same, diffL1, diffL2)
  }


  /**
   * Creates ProofProcess ProofEntry data structures for each Isabelle Command state (each step).
   *
   * The ProofEntry is then used within each goal step, while the mapping Command.State -> ProofEntry
   * is also kept (for activity logging).
   */
  private def mapToPPEntries[T <: Eq](transform: GoalStep[Command.State, T] => ProofEntry)(
                                        steps: List[GoalStep[Command.State, T]])
      : (List[GoalStep[ProofEntry, T]], ParseEntries) = {

    // create ProofEntry elements for each step
    val ppMapping = steps.map { s => (s, transform(s)) }

    // replace the command State by ProofEntry in step contents
    val ppSteps = ppMapping.map { case (step, entry) => step.copy(info = entry) }
    
    // extract the mapping from command State -> ProofEntry
    val cmdToPPEntry = ppMapping.map { case (step, entry) => (step.info, entry) }
    
    (ppSteps, cmdToPPEntry.toMap)
  }


  /**
   * Tries mapping the goal step sequence to a Graph structure,
   * depending on how goals/assumptions change.
   */
  private def stepsToGraph[G <: Eq](steps: List[GoalStep[ProofEntry, G]])
      : PPRootGraph[ProofEntry] = {

    // map the possible root nodes to the nearest `proof` command
    // to avoid them hanging from the root, e.g. for assumptions, etc.
    val initialGraph = rootsToProof(steps, Nil)
    
    // try connecting the goal steps into a graph structure
    // depending on how goals/assumptions change
    GoalGraphMatcher2.goalGraph(steps, initialGraph)
  }


  private def rootsToProof(nodeSteps: List[GoalStep[ProofEntry, _]],
                           proofNodes: List[ProofEntry]): PPGraph[ProofEntry] = nodeSteps match {

    case Nil => Graph()

    case step :: ns => {

      val entry = step.info
      val cmdName = commandName(entry)

      if ("proof" == cmdName) {
        // add to stack, nothing to map
        rootsToProof(ns, entry :: proofNodes)

      } else if ("fix" == cmdName) {
        // replace as the node to attach roots instead of `proof` - also link to `proof`
        // (assume `proof` is already there..)
        val proofNode = proofNodes.head
        rootsToProof(ns, entry :: proofNodes.tail) + (proofNode ~> entry)

      } else if ("qed" == cmdName) {
        // pop the stack, consumed a nested proof (also nothing to map)
        rootsToProof(ns, proofNodes.tail)

      } else if (step.in.isEmpty) {
        // step will not be connected to anything, so link it to the nearest proof
        if (proofNodes.isEmpty) {
          // no proof nodes available, will be root - no mapping
          rootsToProof(ns, proofNodes)
        } else {
          val proofNode = proofNodes.head
          rootsToProof(ns, proofNodes) + (proofNode ~> entry)
        }
      } else {
        // step will possibly have other connections, so no mapping
        // TODO review with multiple assumptions, some of which should be linked to proof?
        rootsToProof(ns, proofNodes)
      }
    }
  }
  
  private def commandName(entry: ProofEntry): String =
    entry.getProofStep.getTrace.asInstanceOf[IsabelleTrace].getCommand.getName
  
  
  private def propsToTerms(props: List[Proposition[EqTerm]]): List[Term] =
    // note that terms are cloned when put into Assumption/Judgement terms
    // since they cannot be shared here
    props map (_ match {
      case Assumption(t) => {
        val assmTerm = isaFactory.createAssumptionTerm
        assmTerm.setTerm(cloneTerm(t.term))
        assmTerm
      }
      case Judgement(assms, goal) => {
        val jTerm = isaFactory.createJudgementTerm
        jTerm.getAssms.addAll(assms map (_.term) map cloneTerm)
        jTerm.setGoal(cloneTerm(goal.term))
        jTerm
      }
    })
  
  private def proofEntry(proofStep: GoalStepT): ProofEntry = {
    
    val cmdState = proofStep.info
    val inGoals = propsToTerms(proofStep.in)
    val outGoals = propsToTerms(proofStep.out)

    val info = factory.createProofInfo
    info.setNarrative(cmdState.command.source)

    info.setIntent(stepIntent)

    // TODO set features
    val inFeatures = info.getInFeatures
    val outFeatures = info.getOutFeatures

    val step = factory.createProofStep
    step.setTrace(proofStepTrace(cmdState))
    step.setSource(textLoc(cmdState))

    // copy the goals defensively because inGoals is a containment ref
    step.getInGoals.addAll(inGoals.map(cloneTerm))
    step.getOutGoals.addAll(outGoals)

    // create tactic application attempt
    val entry = factory.createProofEntry
    entry.setInfo(info)
    entry.setProofStep(step)

    entry
  }

  private def proofStepTrace(cmdState: Command.State): Trace = {

    val trace = isaFactory.createIsabelleTrace()

    // parse the command
    val ppCmd = CommandParser.parse(cmdState)
    trace.setCommand(ppCmd)

    // TODO add simplifier lemmas
    val simpLemmas = trace.getSimpLemmas()

    trace
  }

}

object ProofEntryReader {

  type ParseEntries = Map[Command.State, ProofEntry]

}

case class ParsedProof(proofGoals: List[Term],
                       label: Option[String],
                       proofGraph: PPRootGraph[ProofEntry])
