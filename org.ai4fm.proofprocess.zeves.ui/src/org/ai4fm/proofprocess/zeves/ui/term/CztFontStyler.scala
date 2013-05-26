package org.ai4fm.proofprocess.zeves.ui.term

import org.eclipse.jface.resource.{FontDescriptor, JFaceResources}
import org.eclipse.jface.viewers.StyledString.Styler
import org.eclipse.swt.graphics.TextStyle


/**
 * A text styler that sets CZT font.
 * 
 * @author Andrius Velykis
 */
object CztFontStyler extends Styler {

  def CZT_FONT_ID = "net.sourceforge.czt.eclipse.ui.editor.font.unicode"

  private def cztFont: FontDescriptor =
    JFaceResources.getFontRegistry.getDescriptor(CZT_FONT_ID)

  override def applyStyles(textStyle: TextStyle) {
    textStyle.font = JFaceResources.getResources.createFont(cztFont)
  }

}
