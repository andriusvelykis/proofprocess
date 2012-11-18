package org.ai4fm.proofprocess.zeves.core.parse

import scala.collection.JavaConverters._

import org.ai4fm.proofprocess.{Intent, Loc, ProofElem, ProofEntry, ProofProcessFactory, Term, Trace}
import org.ai4fm.proofprocess.core.graph.{EmfPProcessTree, PProcessGraph}
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.zeves.ZEvesProofProcessFactory
import org.ai4fm.proofprocess.zeves.core.analysis.ZEvesGraph
import org.ai4fm.proofprocess.zeves.core.internal.ZEvesPProcessCorePlugin.{error, log}

import net.sourceforge.czt.eclipse.ui.CztUI
import net.sourceforge.czt.eclipse.zeves.ui.core.SnapshotData
import net.sourceforge.czt.eclipse.zeves.ui.core.ZEvesSnapshot.ISnapshotEntry
import net.sourceforge.czt.session.SectionInfo
import net.sourceforge.czt.zeves.ast.ProofCommand
import net.sourceforge.czt.zeves.response.{ZEvesOutput, ZEvesProofTrace}
import net.sourceforge.czt.zeves.response.ZEvesProofTrace.TraceType._
import net.sourceforge.czt.zeves.response.form.ZEvesName


/** @author Andrius Velykis
  */
trait ProofEntryReader {
  
  private val factory = ProofProcessFactory.eINSTANCE
  private val zevesFactory = ZEvesProofProcessFactory.eINSTANCE
  
  def stepIntent(): Intent
  
  def cloneTerm(term: Term): Term
  
  def matchTerms(term1: Term, term2: Term): Boolean
  
  def textLoc(entry: ISnapshotEntry): Loc

  def readEntries(sectInfo: SectionInfo, proofSnapshot: List[ISnapshotEntry]): Option[ProofEntryData] =
    proofSnapshot match {
      // Assume that a proof with just one command (e.g. "declaration") is too short to be included
      // in the ProofProcess. This way we only concern ourselves with proofs that have been tried proving
      // (instead of capturing every version of lemma declaration)
      case goalEntry :: restEntries if !restEntries.isEmpty => {
        // Assume that the first step in any proof is the "declaration" command, e.g. "lemma ..."
        // Also check that initial goals are not empty - don't allow proofs with empty goals
        // Also check that proof steps are available

        assert(SnapshotUtil.isGoal(goalEntry), "The first element in the proof must be the goal.")

        val initialGoals = parseGoals(sectInfo, goalEntry)

        val restGoals = restEntries.map(entry => (entry, parseGoals(sectInfo, entry)))

        val proofSteps = readProofSteps(restGoals, initialGoals)

        proofSteps.map(steps => ProofEntryData(initialGoals, Option(goalEntry.getData.getGoalName), steps))
      }

      // empty/short proof state - nothing to parse
      case _ => None
    }

  private def parseGoals(sectInfo: SectionInfo, snapshotEntry: ISnapshotEntry) =
    // get the option - should always the Some here, since we have filtered the errors before
    SnapshotUtil.parseGoals(sectInfo, snapshotEntry).get

  private def snapshotProofResult(snapshotEntry: ISnapshotEntry) =
    // get the option - should always the Some here, since we have filtered the errors before
    SnapshotUtil.zEvesProofResult(snapshotEntry).get

  private def readProofSteps(proofSteps: List[(ISnapshotEntry, List[Term])],
                             inGoals: List[Term]): Option[ProofElem] = {
    
    val proofStepEntries = PProcessUtil.toInOutGoalSteps(proofEntry)(inGoals, proofSteps)
    
    val (proofGraph, proofGraphRoots) = ZEvesGraph.proofStepsGraph(proofStepEntries)

    val proofTree = PProcessGraph.toPProcessTree(
      EmfPProcessTree, EmfPProcessTree.ProofEntryTree(factory.createProofStep))(proofGraph, proofGraphRoots)
    
    Some(proofTree)
  }

  private def proofEntry(snapshotEntry: ISnapshotEntry,
                         inGoals: List[Term], outGoals: List[Term]): ProofEntry = {

    val snapshotData = snapshotEntry.getData

    val zevesResult = snapshotData.getResult match {
      case zeves: ZEvesOutput => Some(zeves)
      case _ => None
    }

    val info = factory.createProofInfo

    val entryCmd = snapshotData.getTerm
    val commandText = entryCmd match {

      case proofCmd: ProofCommand => CztUI.getTermLabel(proofCmd)

      case _ => zevesResult.map(_.getCommand.toString) getOrElse ""
    }

    info.setNarrative(commandText)
    info.setIntent(stepIntent)

    // TODO set features
    val inFeatures = info.getInFeatures
    val outFeatures = info.getOutFeatures

    val step = factory.createProofStep
    step.setTrace(proofStepTrace(snapshotData, commandText, zevesResult))
    step.setSource(textLoc(snapshotEntry))

    // copy the goals defensively because inGoals is a containment ref
    step.getInGoals.addAll(inGoals.map(cloneTerm).asJava)
    step.getOutGoals.addAll(outGoals.asJava)

    // create tactic application attempt
    val entry = factory.createProofEntry
    entry.setInfo(info)
    entry.setProofStep(step)

    entry
  }

  private def proofStepTrace(snapshotData: SnapshotData,
                             commandText: String, zevesResult: Option[ZEvesOutput]): Trace = {

    val trace = zevesFactory.createZEvesTrace

    trace.setText(commandText)
    // set the proof case if Z/EVES result is available
    zevesResult foreach (res => trace.setCase(ProofEntryReader.proofCaseStr(res.getProofCase.asScala)))

    // retrieve used lemmas from the proof trace
    val lemmas = snapshotData.getTrace.asScala.flatMap(traceResult => usedLemmas(traceResult.getProofTrace))
    trace.getUsedLemmas.addAll(lemmas.asJava)

    trace
  }
  
  private val lemmaTypes = List(APPLY, REWRITE, FRULE, GRULE, USE)

  private def usedLemmas(trace: ZEvesProofTrace): Set[String] = {

    def traceName(elem: Any): Option[String] = elem match {
      case name: ZEvesName => Some(name.getIdent)
      case _ => {
        log(error(msg = Some("Unknown used lemma element found in trace: " + elem.toString)))
        None
      }
    }

    // get the trace elements of each lemma type and extract their names
    lemmaTypes.map(trace.getTraceElements(_).asScala.flatMap(traceName)).flatten.toSet
  }

}

object ProofEntryReader {

  def proofCaseStr(proofCase: Iterable[java.lang.Integer]) = proofCase.mkString(".")

  def proofCase(caseStr: String): List[Int] = {

    Option(caseStr)
    
    if (caseStr.isEmpty) {
      List()
    } else {

      val caseNos = caseStr.split("\\.")

      try {

        caseNos.map(_.toInt).toList

      } catch {
        // invalid case?
        case ne: NumberFormatException => List()
      }
    }
  }

}

case class ProofEntryData(val goals: List[Term], val label: Option[String], val rootEntry: ProofElem)

