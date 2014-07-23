package org.ai4fm.proofprocess.core.store

import scala.collection.JavaConverters.asScalaBufferConverter

import org.ai4fm.proofprocess.ProofElem
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ProofId
import org.ai4fm.proofprocess.ProofParallel
import org.ai4fm.proofprocess.ProofSeq
import org.ai4fm.proofprocess.Term


/**
 * Provides access to proof data in a compositional way,
 * e.g. the "before" and "after" goals of Seq or Parallel elements.
 * 
 * @author Andrius Velykis
 */
object ProofElemComposition {

  def composeEntries[A](elem: ProofElem, before: Boolean)(
      f: ProofEntry => Seq[A]): Seq[(A, ProofEntry)] = elem match {

    case entry: ProofEntry => f(entry) map ((_, entry))

    case parallel: ProofParallel => {
      // TODO review what to do with links? E.g. how about assumptions..?
      val parEntries = noLinks(parallel.getEntries.asScala) //++ parallel.getLinks.asScala

      if (parEntries.isEmpty) {
        Nil
      } else {
        val results = parEntries map { e => composeEntries(e, before)(f) }
        results.flatten
      }
    }

    case seq: ProofSeq => {
      val seqEntries = noLinks(seq.getEntries.asScala)

      if (seqEntries.isEmpty) {
        Nil
      } else if (before) {
        composeEntries(seqEntries.head, before)(f)
      } else {
        composeEntries(seqEntries.last, before)(f)
      }
    }
    
    case link: ProofId => Nil
  }

  /**
   * Filter ProofId elements, which do not contribute to composition.
   */
  private def noLinks[A](seq: Seq[A]): Seq[A] =
    seq filter (e => !e.isInstanceOf[ProofId])

  def composeInGoals(elem: ProofElem): Seq[(Term, ProofEntry)] =
    composeEntries(elem, true) { entry => entry.getProofStep.getInGoals.asScala }

  def composeOutGoals(elem: ProofElem): Seq[(Term, ProofEntry)] =
    composeEntries(elem, false) { entry => entry.getProofStep.getOutGoals.asScala }

}
