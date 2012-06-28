package org.ai4fm.proofprocess.ui.graph.draw

import scala.collection.JavaConversions._

import org.ai4fm.proofprocess.ProofEntry
import org.eclipse.jface.resource.ResourceManager
import org.eclipse.jface.viewers.ILabelProvider

/** A top-level figure to display ProofEntry in ProofProcess graph.
  * 
  * @author Andrius Velykis
  */
class ProofEntryFigure(resourceMgr: ResourceManager, labelProvider: ILabelProvider, val proofEntry: ProofEntry)
  extends PProcessFigure() {

  Option(proofEntry.getInfo) foreach { info =>
    add(new ProofInfoFigure(resourceMgr, labelProvider, info))
  }

  add(new GoalsFigure(resourceMgr, labelProvider,
    proofEntry.getProofStep.getInGoals,
    proofEntry.getProofStep.getOutGoals))
  
}
