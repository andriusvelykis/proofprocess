package org.ai4fm.proofprocess.cdo

import org.eclipse.emf.ecore.{EObject, EReference}
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * A trait to participate in EMF model copying during repository upgrade.
 * 
 * @author Andrius Velykis
 */
trait CopyParticipant {

  /**
   * Checks whether the participant can take over copying of the reference.
   *
   * @see org.eclipse.emf.ecore.util.EcoreUtil.Copier#copyReference(EReference, EObject, EObject)
   */
  def canCopyReference(copier: EcoreUtil.Copier,
                                  eReference: EReference,
                                  eObject: EObject,
                                  copyEObject: EObject): Boolean

  /**
   * Performs copying of the reference
   *
   * @see org.eclipse.emf.ecore.util.EcoreUtil.Copier#copyReference(EReference, EObject, EObject)
   */
  def copyReference(copier: EcoreUtil.Copier,
                               eReference: EReference,
                               eObject: EObject,
                               copyEObject: EObject)


  /**
   * Checks whether the participant has post-copy (cleanup/finish) activities
   */
  def canFinish(copier: EcoreUtil.Copier): Boolean
  
  /**
   * Performs post-copy (cleanup/finish) activities
   */
  def finish(copier: EcoreUtil.Copier)

}
