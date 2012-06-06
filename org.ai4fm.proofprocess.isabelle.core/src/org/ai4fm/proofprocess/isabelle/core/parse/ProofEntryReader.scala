package org.ai4fm.proofprocess.isabelle.core.parse

import isabelle.Command.State
import isabelle.Text
import org.ai4fm.filehistory.FileVersion
import org.ai4fm.proofprocess.Intent
import org.ai4fm.proofprocess.Loc
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ProofProcessFactory
import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.Trace
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory
import org.ai4fm.proofprocess.project.core.util.ProofProcessUtil
import scala.annotation.tailrec
import scala.collection.JavaConversions._

/**
  * @author Andrius Velykis 
  */
trait ProofEntryReader {
  
  private val factory = ProofProcessFactory.eINSTANCE
  private val isaFactory = IsabelleProofProcessFactory.eINSTANCE
  
  def stepIntent(): Intent
  
  def cloneTerm(term: Term): Term
  
  def textLoc(cmd: State): Loc

  def readEntries(proofState: List[(State, List[Term])]): Option[ProofEntryData] = proofState match {
    // Assume that a proof with just one command (e.g. "declaration") is too short to be included
    // in the ProofProcess. This way we only concern ourselves with proofs that have been tried proving
    // (instead of capturing every version of lemma declaration)
    case (firstCmd, initialGoals) :: restCmds if !initialGoals.isEmpty && !restCmds.isEmpty => {
      // Assume that the first step in any proof is the "declaration" command, e.g. "lemma ..."
      // Also check that initial goals are not empty - don't allow proofs with empty goals
      
      Some(ProofEntryData(initialGoals, CommandParser.commandId(firstCmd.command), readProofSteps(restCmds, initialGoals)))
    }
    // empty/short proof state - nothing to parse
    case _ => None
  }

  @tailrec
  private def readProofSteps(proofSteps: List[(State, List[Term])], inGoals: List[Term]): List[ProofEntry] =
    proofSteps match {
    case (step, goals) :: rest => proofEntry(step, inGoals, goals) :: readProofSteps(rest, goals)
    case Nil => Nil
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

case class ProofEntryData(val goals: List[Term], val label: Option[String], val entries: List[ProofEntry])

