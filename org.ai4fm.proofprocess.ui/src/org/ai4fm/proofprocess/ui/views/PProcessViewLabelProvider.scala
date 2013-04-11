package org.ai4fm.proofprocess.ui.views

import org.ai4fm.proofprocess.{ProofStep, Term}
import org.ai4fm.proofprocess.ui.PProcessImages
import org.eclipse.jface.resource.ResourceManager
import org.eclipse.swt.graphics.Image


/**
 * An overriding label provider for ProofProcess view.
 * 
 * This allows providing images for cases when it is not too sensible to update ItemProviders
 * in proofprocess.edit. 
 * 
 * @author Andrius Velykis
 */
class PProcessViewLabelProvider(resourceMgr: ResourceManager) {

  import PProcessViewLabelProvider._

  def getImage(element: AnyRef): Option[Image] = element match {

    // special handling of label provider to indicate in/out terms via icon
    case StepTerm(step, term) =>
      if (step.getInGoals contains term)
        Some(resourceMgr.createImageWithDefault(PProcessImages.GOAL_IN))
      else if (step.getOutGoals contains term)
        Some(resourceMgr.createImageWithDefault(PProcessImages.GOAL_OUT))
      else
        None

    case _ => None
  }

}

object PProcessViewLabelProvider {

  /**
   * Extractor object for PP Terms that are within a ProofStep.
   */
  private object StepTerm {
    def unapply(element: AnyRef): Option[(ProofStep, Term)] = element match {
      case term: Term => term.eContainer match {
        case step: ProofStep => Some(step, term)
        case _ => None
      }
      case _ => None
    }
  }

}

