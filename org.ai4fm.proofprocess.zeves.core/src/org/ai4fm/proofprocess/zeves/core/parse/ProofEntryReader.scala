package org.ai4fm.proofprocess.zeves.core.parse

import scala.collection.JavaConversions._

import org.ai4fm.proofprocess.Intent
import org.ai4fm.proofprocess.Loc
import org.ai4fm.proofprocess.ProofElem
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ProofProcessFactory
import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.Trace
import org.ai4fm.proofprocess.core.analysis.CacheGoalTreeMatcher
import org.ai4fm.proofprocess.core.analysis.GoalEntry
import org.ai4fm.proofprocess.core.analysis.GoalParallel
import org.ai4fm.proofprocess.core.analysis.GoalSeq
import org.ai4fm.proofprocess.core.analysis.GoalTree
import org.ai4fm.proofprocess.zeves.ZEvesProofProcessFactory
import org.ai4fm.proofprocess.zeves.core.internal.ZEvesPProcessCorePlugin._

import net.sourceforge.czt.eclipse.ui.CztUI
import net.sourceforge.czt.eclipse.zeves.ui.core.SnapshotData
import net.sourceforge.czt.eclipse.zeves.ui.core.ZEvesSnapshot.ISnapshotEntry
import net.sourceforge.czt.session.SectionInfo
import net.sourceforge.czt.zeves.ast.ProofCommand
import net.sourceforge.czt.zeves.response.ZEvesOutput
import net.sourceforge.czt.zeves.response.ZEvesProofTrace
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
    
    // try finding a structure of the flat proof steps based on how the goals change
    val goalTree = CacheGoalTreeMatcher.goalTree(matchTerms)(inGoals, proofSteps)
    // map the tree structure to corresponding proof process data elements
    goalTree.map(proofProcessTree)
  }

  private def proofProcessTree(tree: GoalTree[ISnapshotEntry, Term]): ProofElem = tree match {
    
    case GoalEntry(state, inGoals, outGoals) => proofEntry(state, inGoals, outGoals)
    
    case GoalParallel(par) => {
      val proofPar = factory.createProofParallel
      proofPar.getEntries.addAll(par.map(proofProcessTree))
      proofPar.setInfo(factory.createProofInfo)
      proofPar
    }
    
    case GoalSeq(seq) => {
      val proofSeq = factory.createProofSeq
      proofSeq.getEntries.addAll(seq.map(proofProcessTree))
      proofSeq.setInfo(factory.createProofInfo)
      proofSeq
    }
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
    step.getInGoals.addAll(inGoals.map(cloneTerm))
    step.getOutGoals.addAll(outGoals)

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
    zevesResult foreach (res => trace.setCase(ProofEntryReader.proofCaseStr(res.getProofCase)))

    // retrieve used lemmas from the proof trace
    val lemmas = snapshotData.getTrace.flatMap(traceResult => usedLemmas(traceResult.getProofTrace))
    trace.getUsedLemmas.addAll(lemmas)

    trace
  }
  
  import ZEvesProofTrace.TraceType._
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
    lemmaTypes.map(trace.getTraceElements(_).flatMap(traceName)).flatten.toSet
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

