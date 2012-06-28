package org.ai4fm.proofprocess.ui.graph.draw

import scala.collection.Iterable

import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.ui.PProcessImages
import org.eclipse.draw2d.Figure
import org.eclipse.draw2d.Label
import org.eclipse.draw2d.PositionConstants
import org.eclipse.draw2d.ToolbarLayout
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.jface.resource.ResourceManager
import org.eclipse.jface.viewers.ILabelProvider


/** Figure to display goals in ProofProcess graph.
  *
  * This figure is intended to be nested in other composite figures.
  * 
  * @author Andrius Velykis
  */
class GoalsFigure(resourceMgr: ResourceManager, labelProvider: ILabelProvider,
  inGoals: Iterable[Term], outGoals: Iterable[Term] = Nil) extends Figure {

  val goalsLayout = new ToolbarLayout
  goalsLayout.setStretchMinorAxis(false)
  goalsLayout.setSpacing(2)
  setLayoutManager(goalsLayout)
  
  setSize(-1, -1)

  def goalEntry(image: ImageDescriptor)(term: Term) = {
    val goalLabel = new Label(labelProvider.getText(term), resourceMgr.createImageWithDefault(image))
    goalLabel.setLabelAlignment(PositionConstants.LEFT)
    add(goalLabel)
  }

  inGoals.foreach(goalEntry(PProcessImages.GOAL_IN))
  outGoals.foreach(goalEntry(PProcessImages.GOAL_OUT))
  
}
