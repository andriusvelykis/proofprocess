package org.ai4fm.proofprocess.ui.graph.draw

import scala.collection.JavaConversions._

import org.ai4fm.proofprocess.Proof
import org.eclipse.draw2d.Label
import org.eclipse.jface.resource.ResourceManager
import org.eclipse.jface.viewers.ILabelProvider


/** A top-level figure to display Proof in ProofProcess graph.
  *
  * @author Andrius Velykis
  */
class ProofFigure(resourceMgr: ResourceManager, labelProvider: ILabelProvider, val proof: Proof)
  extends PProcessFigure() {

  private val nameLabel = new Label(proof.getLabel, labelProvider.getImage(proof))
  add(nameLabel)

  add(new GoalsFigure(resourceMgr, labelProvider, proof.getGoals))

}
