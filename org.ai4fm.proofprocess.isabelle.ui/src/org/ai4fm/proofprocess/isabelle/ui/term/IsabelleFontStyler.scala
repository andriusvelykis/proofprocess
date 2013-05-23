package org.ai4fm.proofprocess.isabelle.ui.term

import org.eclipse.jface.resource.{FontDescriptor, JFaceResources}
import org.eclipse.jface.viewers.StyledString.Styler
import org.eclipse.swt.graphics.TextStyle


/**
 * A text styler that sets Isabelle font.
 * 
 * @author Andrius Velykis
 */
object IsabelleFontStyler extends Styler {

  def ISABELLE_FONT_ID = "isabelle.eclipse.ui.theoryEditorFont"

  private def isabelleFont: FontDescriptor =
    JFaceResources.getFontRegistry.getDescriptor(ISABELLE_FONT_ID)
  
  override def applyStyles(textStyle: TextStyle) {
    textStyle.font = JFaceResources.getResources.createFont(isabelleFont)
  }

}
