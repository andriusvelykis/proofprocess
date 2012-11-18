package org.ai4fm.proofprocess.core.graph

import scala.collection.JavaConverters._

import org.ai4fm.proofprocess.ProofDecor

import org.ai4fm.proofprocess.ProofElem
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ProofParallel
import org.ai4fm.proofprocess.ProofProcessFactory
import org.ai4fm.proofprocess.ProofSeq
import org.ai4fm.proofprocess.ProofStep
import org.ai4fm.proofprocess.core.graph.PProcessTree.CaseObject

/** Extractors and factories for EMF ProofProcess elements to the PProcessTree bridge.
  * 
  * @author Andrius Velykis
  */
object EmfPProcessTree 
  extends PProcessTree[ProofElem, ProofEntry, ProofSeq, ProofParallel, ProofDecor, ProofStep] {

  val factory = ProofProcessFactory.eINSTANCE
  
  override val entry = ProofEntryTree
  override val seq = ProofSeqTree
  override val parallel = ProofParallelTree
  override val decor = ProofDecorTree
  

  object ProofEntryTree extends CaseObject[ProofElem, ProofEntry, ProofStep] {

    override def apply(c: ProofStep): ProofEntry = {
      val entry = factory.createProofEntry
      entry.setProofStep(c)
      entry.setInfo(factory.createProofInfo)
      entry
    }

    override def unapply(e: ProofElem): Option[ProofStep] = e match {
      case entry: ProofEntry => Some(entry.getProofStep)
      case _ => None
    }
  }
  
  
  object ProofSeqTree extends CaseObject[ProofElem, ProofSeq, List[ProofElem]] {

    override def apply(elems: List[ProofElem]): ProofSeq = {
      val seq = factory.createProofSeq
      seq.getEntries.addAll(elems.asJava)
      seq.setInfo(factory.createProofInfo)
      seq
    }

    override def unapply(e: ProofElem): Option[List[ProofElem]] = e match {
      case seq: ProofSeq => Some(seq.getEntries.asScala.toList)
      case _ => None
    }
  }
  
  
  object ProofParallelTree extends CaseObject[ProofElem, ProofParallel, Set[ProofElem]] {

    override def apply(elems: Set[ProofElem]): ProofParallel = {
      val par = factory.createProofParallel
      par.getEntries.addAll(elems.asJava)
      par.setInfo(factory.createProofInfo)
      par
    }

    override def unapply(e: ProofElem): Option[Set[ProofElem]] = e match {
      case par: ProofParallel => Some(par.getEntries.asScala.toSet)
      case _ => None
    }
  }
  
  
  object ProofDecorTree extends CaseObject[ProofElem, ProofDecor, ProofElem] {

    override def apply(elem: ProofElem): ProofDecor = {
      val dec = factory.createProofDecor
      dec.setEntry(elem)
      dec.setInfo(factory.createProofInfo)
      dec
    }

    override def unapply(e: ProofElem): Option[ProofElem] = e match {
      case dec: ProofDecor => Some(dec.getEntry)
      case _ => None
    }
  }
  
}
