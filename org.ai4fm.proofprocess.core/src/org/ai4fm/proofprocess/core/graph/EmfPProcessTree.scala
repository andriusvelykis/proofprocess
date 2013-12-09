package org.ai4fm.proofprocess.core.graph

import scala.collection.JavaConverters._

import org.ai4fm.proofprocess.{ProofElem, ProofEntry, ProofId, ProofInfo, ProofParallel, ProofProcessFactory, ProofSeq, ProofStep}
import org.ai4fm.proofprocess.core.graph.PProcessTree.CaseObject


/** Extractors and factories for EMF ProofProcess elements to the PProcessTree bridge.
  * 
  * @author Andrius Velykis
  */
object EmfPProcessTree 
  extends PProcessTree[ProofElem, ProofEntry, ProofSeq, ProofParallel, ProofId,
                       ProofStep, ProofInfo] {

  /** A singleton instance of PProcessGraph graph-tree converter for EMF ProofProcess data. */
  lazy val graphConverter =
    new PProcessGraph(EmfPProcessTree, ProofEntryTree(factory.createProofStep))

  private val factory = ProofProcessFactory.eINSTANCE
  
  override val entry = ProofEntryTree
  override val seq = ProofSeqTree
  override val parallel = ProofParallelTree
  override val id = ProofIdTree

  override def info(elem: ProofElem): ProofInfo = elem.getInfo

  override def addInfo(elem: ProofElem, info: ProofInfo): ProofElem = {
    elem.setInfo(addProofInfo(elem.getInfo, info))
    elem
  }


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


  object ProofParallelTree
      extends CaseObject[ProofElem, ProofParallel, Set[ProofElem]] {

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


  object ProofIdTree
      extends CaseObject[ProofElem, ProofId, ProofEntry] {

    override def apply(e: ProofEntry): ProofId = {
      val id = factory.createProofId
      id.setEntryRef(e)
      id.setInfo(factory.createProofInfo)
      id
    }

    override def unapply(e: ProofElem): Option[ProofEntry] = e match {
      case id: ProofId => Some(id.getEntryRef)
      case _ => None
    }
  }


  private def addProofInfo(base: ProofInfo, newInfo: ProofInfo): ProofInfo = {

    // TODO merge with base somehow? Or assume that base is not important - 
    // but where are "inferred features" coming from then?
    val info = factory.createProofInfo

    info.setIntent(newInfo.getIntent)
    info.setNarrative(newInfo.getNarrative)
    info.getInFeatures.addAll(newInfo.getInFeatures)
    info.getOutFeatures.addAll(newInfo.getOutFeatures)

    info
  }

}
