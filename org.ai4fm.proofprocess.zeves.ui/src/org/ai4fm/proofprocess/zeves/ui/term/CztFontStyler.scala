package org.ai4fm.proofprocess.zeves.ui.term

import org.eclipse.jface.resource.{FontDescriptor, JFaceResources}
import org.eclipse.jface.viewers.StyledString.Styler
import org.eclipse.swt.graphics.{RGB, TextStyle}


/**
 * A text styler that sets CZT font.
 * 
 * @author Andrius Velykis
 */
class CztFontStyler(colour: Option[RGB]) extends Styler {

  def CZT_FONT_ID = "net.sourceforge.czt.eclipse.ui.editor.font.unicode"

  private def cztFont: FontDescriptor =
    JFaceResources.getFontRegistry.getDescriptor(CZT_FONT_ID)

  override def applyStyles(textStyle: TextStyle) {
    val resources = JFaceResources.getResources
    textStyle.font = resources.createFont(cztFont)

    colour match {
      case Some(c) => textStyle.foreground = resources.createColor(c)
      case _ => // ignore
    }
  }

}

object CztFontStyler extends CztFontStyler(None) {

  lazy val BLUE = new CztFontStyler(Some(new RGB(0, 0, 255)))

}

