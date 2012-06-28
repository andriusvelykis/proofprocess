package org.ai4fm.proofprocess.ui.graph.draw

import org.eclipse.draw2d.Figure
import org.eclipse.draw2d.ToolbarLayout
import org.eclipse.draw2d.LineBorder
import org.eclipse.draw2d.ColorConstants

/** Common figure used in ProofProcess graph.
  * 
  * Defines common style and functionality elements.
  * 
  * @author Andrius Velykis
  */
class PProcessFigure(width: Int = 200, height: Int = -1) extends Figure {

  protected val mainLayout = new ToolbarLayout
  mainLayout.setSpacing(2)
  setLayoutManager(mainLayout)
  
  setBorder(new LineBorder(ColorConstants.darkBlue,1))
  setBackgroundColor(ColorConstants.lightGray)
  setOpaque(true)
  
  setSize(width, height)
  
}
