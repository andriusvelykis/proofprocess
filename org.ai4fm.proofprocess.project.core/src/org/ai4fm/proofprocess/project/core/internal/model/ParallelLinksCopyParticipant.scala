package org.ai4fm.proofprocess.project.core.internal.model

import scala.collection.JavaConverters._
import scala.collection.mutable

import org.ai4fm.proofprocess.{ProofEntry, ProofId, ProofParallel}
import org.ai4fm.proofprocess.ProofProcessFactory.{eINSTANCE => factory}
import org.ai4fm.proofprocess.cdo.CopyParticipant

import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.{EObject, EReference}
import org.eclipse.emf.ecore.util.EcoreUtil


/**
 * A copy participant for ProofParallel.links to replace them with ProofId elements.
 * 
 * @author Andrius Velykis
 */
class ParallelLinksCopyParticipant extends CopyParticipant {

  private val linksToResolve: mutable.Map[ProofParallel, List[EObject]] = mutable.Map()

  def canCopyReference(copier: EcoreUtil.Copier,
                       eReference: EReference,
                       eObject: EObject,
                       copyEObject: EObject): Boolean = {
    val sourceClass = eObject.eClass
    if (eReference.getName == "links" && sourceClass.getName == "ProofParallel") {
      val uriVersion = v1Revision(sourceClass.getEPackage.getNsURI)
      
      uriVersion match {
        case Some(version) if version < 13 => true
        case _ => false
      }
    } else {
      false
    }
  }

  private def v1Revision(nsURI: String): Option[Int] =
    if (nsURI.startsWith("http://org/ai4fm/proofprocess/v1.0.0")) {
      val end = nsURI.substring(36)
      if (end.isEmpty) {
        Some(0)
      } else {
        // remove the dot
        val endNo = end.substring(1)
        Some(endNo.toInt)
      }
    } else {
      None
    }


  def copyReference(copier: EcoreUtil.Copier,
                    eReference: EReference,
                    eObject: EObject,
                    copyEObject: EObject) {

    val source: EList[EObject] = eObject.eGet(eReference).asInstanceOf[EList[EObject]]
    val sourceEntries = source.asScala.toList

    val target = copyEObject.asInstanceOf[ProofParallel]
    linksToResolve.put(target, sourceEntries)
  }

  def canFinish(copier: EcoreUtil.Copier): Boolean = !linksToResolve.isEmpty

  def finish(copier: EcoreUtil.Copier) = linksToResolve foreach {
    case (target, sourceEntries) => {
      val targetEntries = sourceEntries map copier.get
      targetEntries map createProofId foreach target.getEntries.add
    }
  }

  private def createProofId(link: EObject): ProofId = {
    val entry = link.asInstanceOf[ProofEntry]
    
    val proofId = factory.createProofId
    proofId.setInfo(factory.createProofInfo)
    proofId.setEntryRef(entry)
    proofId
  }

}