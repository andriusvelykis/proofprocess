package org.ai4fm.proofprocess.ui.graph

import org.ai4fm.proofprocess.Proof
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ui.graph.draw.ProofEntryFigure
import org.ai4fm.proofprocess.ui.graph.draw.ProofFigure
import org.eclipse.draw2d.IFigure
import org.eclipse.jface.resource.ResourceManager
import org.eclipse.jface.viewers.ILabelProvider
import org.eclipse.zest.core.viewers.IFigureProvider


/** Custom figure provider for ProofProcess data to display in a graph.
  * 
  * @author Andrius Velykis
  */
trait PProcessFigureProvider extends IFigureProvider with ILabelProvider {

  /** Resource manager to manage images instantiated by the figures. */
  def resourceManager: ResourceManager
  
  override def getFigure(element: Object): IFigure = element match {
    case p: Proof => new ProofFigure(resourceManager, this, p)
    case e: ProofEntry => new ProofEntryFigure(resourceManager, this, e)
    case _ => null
  }

}
