package org.ai4fm.proofprocess.isabelle.core.parse

import scala.collection.JavaConversions._

import scalax.collection.GraphPredef._
import scalax.collection.immutable.Graph

import org.ai4fm.proofprocess.{Intent, Loc, ProofEntry, ProofProcessFactory, Term, Trace}
import org.ai4fm.proofprocess.core.analysis.{Assumption, GoalGraphMatcher2, GoalStep, Judgement, Proposition}
import org.ai4fm.proofprocess.core.analysis.TermIndex.indexedGoalSteps
import org.ai4fm.proofprocess.core.graph.PProcessGraph._
import org.ai4fm.proofprocess.isabelle.{IsabelleProofProcessFactory, IsabelleTrace}
import org.ai4fm.proofprocess.isabelle.core.parse.ResultParser.StepProofType._
import org.ai4fm.proofprocess.isabelle.core.parse.SnapshotReader.StepResults

import isabelle.Command.State


/**
  * @author Andrius Velykis 
  */
trait ProofEntryReader {
  
  private val factory = ProofProcessFactory.eINSTANCE
  private val isaFactory = IsabelleProofProcessFactory.eINSTANCE
  
  def stepIntent(): Intent
  
  def cloneTerm(term: Term): Term
  
  def matchTerms(term1: Term, term2: Term): Boolean
  
  def textLoc(cmd: State): Loc

  def readEntries(proofState: List[StepResults]): Option[ProofEntryData] =
    proofState match {
      // Assume that a proof with just one command (e.g. "declaration") is too short to be included
      // in the ProofProcess. This way we only concern ourselves with proofs that have been tried proving
      // (instead of capturing every version of lemma declaration)
      case lemmaCmd :: restCmds if !lemmaCmd.outGoals.isEmpty && !restCmds.isEmpty => {
        // Assume that the first step in any proof is the "declaration" command, e.g. "lemma ..."
        // Also check that initial goals are not empty - don't allow proofs with empty goals
        // Also check that proof steps are available
        val (proofGraph, entryMapping) = readProofSteps(restCmds, lemmaCmd)

        // TODO judgements instead of outGoals (or convert?)
        Some(ProofEntryData(lemmaCmd.outGoals getOrElse Nil,
          CommandParser.commandId(lemmaCmd.state.command),
          proofGraph,
          entryMapping))
      }
      // empty/short proof state - nothing to parse
      case _ => None
    }

  private def readProofSteps(proofSteps: List[StepResults],
                             initialStep: StepResults): (PPRootGraph[ProofEntry], Map[State, ProofEntry]) = {
    
    // make a list with ingoals-info-outgoals steps
    val inSteps = initialStep :: proofSteps
    val inOutSteps = inSteps zip proofSteps
    
    val steps = inOutSteps map Function.tupled(analyseStep)
    
    // link command states with respective proof step entries (for activity logging)
    var stateEntryMapping = Map[State, ProofEntry]()

    // convert to nodes - keep the in/out propositions, but use ProofEntry as node
    val nodeSteps = steps map (step => {
      
      val entry = proofEntry(step)
      // mark mapping
      stateEntryMapping += (step.info -> entry)
      
      GoalStep(entry, step.in, step.out)
    })
    
    val (_, indexedSteps) = indexedGoalSteps(matchTerms)(nodeSteps)
    
    // map the possible root nodes to the nearest `proof` command
    // to avoid them hanging from the root, e.g. for assumptions, etc.
    val initialGraph = rootsToProof(indexedSteps, Nil)
    
    // try connecting the goal steps into a graph structure
    // depending on how goals/assumptions change
    val proofGraph = GoalGraphMatcher2.goalGraph(indexedSteps, initialGraph)
    
    (proofGraph, stateEntryMapping)
  }

  
  private def analyseStep(prev: StepResults, current: StepResults): GoalStep[State, Term] =
    // TODO only the previous needs to be 'prove'?
    if (prev.stateType == Prove/* && current.stateType == Prove*/) {
      
      // two consecutive Prove steps, so check for goal diffs
      val (unchanged, changedIn, changedOut) = 
        diffs(prev.outGoalProps getOrElse Nil, current.outGoalProps getOrElse Nil)

      // TODO narrow the changed goals further?
      GoalStep(current.state,
               current.inAssmProps ::: changedIn,
               current.outAssmProps ::: changedOut)
      
    }/* else if (prev.stateType == State) {
      // diff in assumptions - they may be repeating?
    }*/ else {

      // out assumptions or out goals are exclusive
      val out = if (!current.outAssmProps.isEmpty) current.outAssmProps
                else current.outGoalProps getOrElse Nil
      
      GoalStep(current.state,
               // TODO review: no "in" goals - previous was a state?
               current.inAssmProps,
               out)
    }
  
  
  private def diffs[A](l1: List[A], l2: List[A]): (List[A], List[A], List[A]) = {
    val same = l1 intersect l2
    val diffL1 = l1 diff same
    val diffL2 = l2 diff same

    (same, diffL1, diffL2)
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
  
  
  private def propsToTerms(props: List[Proposition[Term]]): List[Term] =
    // note that terms are cloned when put into Assumption/Judgement terms
    // since they cannot be shared here
    props map (_ match {
      case Assumption(t) => {
        val assmTerm = isaFactory.createAssumptionTerm
        assmTerm.setTerm(cloneTerm(t))
        assmTerm
      }
      case Judgement(assms, goal) => {
        val jTerm = isaFactory.createJudgementTerm
        jTerm.getAssms.addAll(assms map cloneTerm)
        jTerm.setGoal(cloneTerm(goal))
        jTerm
      }
    })
  
  private def proofEntry(proofStep: GoalStep[State, Term]): ProofEntry = {
    
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

  private def proofStepTrace(cmdState: State): Trace = {

    val trace = isaFactory.createIsabelleTrace()

    // parse the command
    val ppCmd = CommandParser.parse(cmdState)
    trace.setCommand(ppCmd)

    // TODO add simplifier lemmas
    val simpLemmas = trace.getSimpLemmas()

    trace
  }

}

case class ProofEntryData(goals: List[Term],
                          label: Option[String],
                          proofGraph: PPRootGraph[ProofEntry],
                          entryMap: Map[State, ProofEntry])

