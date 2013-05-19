package org.ai4fm.proofprocess.core.util

import scala.collection.JavaConverters._

import org.ai4fm.proofprocess.{Intent, ProofProcessFactory, ProofStore}
import org.ai4fm.proofprocess.core.PProcessCorePlugin.{error, log}

import org.eclipse.core.runtime.{IAdaptable, Platform}
import org.eclipse.emf.cdo.CDOObject
import org.eclipse.emf.cdo.transaction.CDOTransaction
import org.eclipse.emf.ecore.EObject


object PProcessUtil {
  
  /** Finds the intent in the proof store or creates a new one if not found. */
  def getIntent(store: ProofStore, intentName: String): Intent = {
    val found = store.getIntents.asScala.find(_.getName() == intentName)
    
    found getOrElse {
        // create new
		val intent = ProofProcessFactory.eINSTANCE.createIntent();
		intent.setName(intentName);
		store.getIntents().add(intent);
		intent
    }
  }

  /**
   * Converts the proof steps with outgoing goals only (as is usual with theorem provers) and
   * initial goals (e.g. from the conjecture) to a list that has both incoming and outgoing goals
   * for each proof step.
   */
  def toInOutGoalSteps[A, T, B](entry: (A, List[T], List[T]) => B)
                               (initialGoals: List[T], 
                                proofSteps: List[(A, List[T])]): List[B] = {

    // make a list with ingoals-info-outgoals steps
    val inGoals = initialGoals :: proofSteps.map(_._2)
    val inOutSteps = inGoals zip proofSteps

    inOutSteps map { case (inGoals, (info, outGoals)) => entry(info, inGoals, outGoals) }
  }
  
  def chainMaps[A, B, C](m1: Map[A, B], m2: Map[B, C]): A => Option[C] = {
    val chained = (m1 andThen m2.lift).lift
    
    // unpack the nested option
    chained andThen (_ getOrElse None)
  }


  /**
   * Tries to find a ProofStore among the parents of the given EMF object.
   */
  def findProofStore(elem: EObject): Option[ProofStore] = elem match {

    case store: ProofStore => Some(store)

    case e => Option(e.eContainer) match {
      case Some(parent) => findProofStore(parent)
      case None => None
    }
  }


  def cdoTransaction(elem: CDOObject): Option[CDOTransaction] = elem.cdoView match {
    case tr: CDOTransaction => Some(tr)
    case _ => {
      log(error(msg = Some("Cannot commit transaction - object does not belong to one: " + elem)))
      None
    }
  }


  /**
   * Returns the specified adapter for the given element.
   *
   *
   * @param element the model element
   * @param adapterType the type of adapter to look up
   * @param forceLoad `true` to force loading of the plug-in providing the adapter,
   *                  `false` otherwise
   * @return the adapter
   */
  // adapted from org.eclipse.ui.ide.ResourceUtil.getAdapter()
  def getAdapter[T](element: AnyRef, adapterType: Class[T], forceLoad: Boolean = true): Option[T] = {

    val directResult = element match {
      case adaptable: IAdaptable => Option(adaptable.getAdapter(adapterType).asInstanceOf[T])
      case _ => None
    }

    lazy val platformResult =
      if (forceLoad) {
        Option(Platform.getAdapterManager.loadAdapter(element, adapterType.getName).asInstanceOf[T])
      } else {
        Option(Platform.getAdapterManager.getAdapter(element, adapterType).asInstanceOf[T])
      }

    directResult orElse platformResult
  }

}
