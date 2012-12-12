package org.ai4fm.proofprocess.core.analysis

import scala.collection.JavaConverters._

import org.ai4fm.proofprocess.{ProofEntry, Term, Trace}
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * @author Andrius Velykis
 */
trait ProofEntryMatcher {

  // TODO ignore some properties, e.g. display?
  def matchTerm(term1: Term, term2: Term): Boolean = EcoreUtil.equals(term1, term2)

  /**
   * Matches each term in goals sequentially.
   *
   * TODO what about ignoring goal order? or repeated goals?
   */
  def matchGoals(goals1: Seq[Term], goals2: Seq[Term]): Boolean =

    (goals1.headOption, goals2.headOption) match {

      case (None, None) => true // both are empty

      case (Some(g1), Some(g2)) => matchTerm(g1, g2) && matchGoals(goals1.tail, goals2.tail)

      case _ => false // only one is empty
    }

  def matchTrace(trace1: Trace, trace2: Trace): Boolean = EcoreUtil.equals(trace1, trace2)

  def matchProofEntry(entry1: ProofEntry, entry2: ProofEntry): Boolean = {

    val s1 = entry1.getProofStep
    val s2 = entry2.getProofStep

    matchGoals(s1.getInGoals.asScala, s2.getInGoals.asScala) &&
      matchGoals(s1.getOutGoals.asScala, s2.getOutGoals.asScala) &&
      matchTrace(s1.getTrace, s2.getTrace)
  }

  def copyTerm(t: Term): Term = EcoreUtil.copy(t)

}

object ProofEntryMatcher {
  def apply(): ProofEntryMatcher = new ProofEntryMatcher {}
}
