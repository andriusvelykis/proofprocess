package org.ai4fm.proofprocess.isabelle.core.parse

import scala.collection.JavaConversions._

import org.ai4fm.proofprocess.{Intent, Loc, ProofElem, ProofEntry, ProofProcessFactory, Term, Trace}
import org.ai4fm.proofprocess.core.analysis.GoalGraphMatcher
import org.ai4fm.proofprocess.core.analysis.TermIndex._
import org.ai4fm.proofprocess.core.graph.PProcessGraph._
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory

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

  def readEntries(proofState: List[(State, List[Term])]): Option[ProofEntryData] = proofState match {
    // Assume that a proof with just one command (e.g. "declaration") is too short to be included
    // in the ProofProcess. This way we only concern ourselves with proofs that have been tried proving
    // (instead of capturing every version of lemma declaration)
    case (firstCmd, initialGoals) :: restCmds if !initialGoals.isEmpty && !restCmds.isEmpty => {
      // Assume that the first step in any proof is the "declaration" command, e.g. "lemma ..."
      // Also check that initial goals are not empty - don't allow proofs with empty goals
      // Also check that proof steps are available
      val (proofGraph, entryMapping) = readProofSteps(restCmds, initialGoals)

      Some(ProofEntryData(initialGoals,
        CommandParser.commandId(firstCmd.command),
        proofGraph,
        entryMapping))
    }
    // empty/short proof state - nothing to parse
    case _ => None
  }

  private def readProofSteps(proofSteps: List[(State, List[Term])],
                             inGoals: List[Term]): (PPRootGraph[ProofEntry], Map[State, ProofEntry]) = {
    
    def stepTriple(cmdState: State, inGoals: List[Term], outGoals: List[Term]) =
      (cmdState, inGoals, outGoals)
      
    // create steps with in-out goals
    val proofStepTriples = PProcessUtil.toInOutGoalSteps(stepTriple)(inGoals, proofSteps)
    
    // link command states with respective proof step entries (for activity logging)
    var stateEntryMapping = Map[State, ProofEntry]()
    
    val (termIndex, indexedSteps) = indexedGoalSteps(matchTerms)(proofStepTriples)
    
    /** Create proof entry out of indexed goals */
    def proofEntryIndexed(entryData: (State, List[Int], List[Int])): ProofEntry = {
      val (cmdState, inGoalsIndexed, outGoalsIndexed) = entryData
      
      val inGoals = inGoalsIndexed map termIndex
      val outGoals = outGoalsIndexed map termIndex
      
      val entry = proofEntry(cmdState, inGoals, outGoals)
      
      // mark mapping
      stateEntryMapping += (cmdState -> entry)
      
      entry
    }
    
    // try finding a structure of the flat proof steps based on how the goals change
    val proofGraph = 
      GoalGraphMatcher.goalGraph[State, ProofEntry, Int](proofEntryIndexed)(indexedSteps)
    
    (proofGraph, stateEntryMapping)
  }
  
  private def proofEntry(cmdState: State, inGoals: List[Term], outGoals: List[Term]): ProofEntry = {

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

