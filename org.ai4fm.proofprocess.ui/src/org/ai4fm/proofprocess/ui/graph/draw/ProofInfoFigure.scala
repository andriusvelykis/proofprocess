package org.ai4fm.proofprocess.ui.graph.draw

import org.ai4fm.proofprocess.ProofInfo
import org.eclipse.draw2d.Figure
import org.eclipse.draw2d.Label
import org.eclipse.draw2d.ToolbarLayout
import org.eclipse.draw2d.text.FlowPage
import org.eclipse.draw2d.text.TextFlow
import org.eclipse.jface.resource.ResourceManager
import org.eclipse.jface.viewers.ILabelProvider


/** Figure to display ProofInfo in ProofProcess graph.
  * 
  * This figure is intended to be nested in other composite figures.
  *
  * @author Andrius Velykis
  */
class ProofInfoFigure(resourceMgr: ResourceManager, labelProvider: ILabelProvider, val proofInfo: ProofInfo) extends Figure {

  val mainLayout = new ToolbarLayout
  mainLayout.setSpacing(2)
  setLayoutManager(mainLayout)

  setSize(-1, -1)
  
  Option(proofInfo.getIntent) foreach { intent =>
    val intentLabel = new Label(intent.getName, labelProvider.getImage(intent))
    add(intentLabel)
  }
  
  // if narrative is non-empty, display it
  Option(proofInfo.getNarrative) filterNot { _.isEmpty } foreach { text =>
    val flowPage = new FlowPage
    flowPage.add(new TextFlow(text))
    add(flowPage)
  }
  
  // TODO in/out features
  
}
