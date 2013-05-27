package org.ai4fm.proofprocess.zeves.ui.term

import scala.collection.JavaConverters._

import net.sourceforge.czt.eclipse.zeves.core.ZEvesCore
import net.sourceforge.czt.session.SectionInfo

import org.eclipse.core.runtime.Path
import org.eclipse.ui.{IEditorPart, PlatformUI}


/**
 * @author Andrius Velykis
 */
object CztUtil {

  def currentSectionInfo: Option[(SectionInfo, String)] = {

    val snapshotSectionInfo = {
      val zevesSnapshot = ZEvesCore.getZEves.getSnapshot
      val sectInfo = Option(zevesSnapshot.getSectionInfo)

      // use the last section if available
      val sectName = zevesSnapshot.getSections.asScala.lastOption map (_.getSectionName)

      // combine if both are available
      for(si <- sectInfo; s <- sectName) yield (si, s)
    }

    lazy val editorSectionInfo = activeEditor match {
      case Some(editor) => {
        val sectInfo = Option(editor.getAdapter(classOf[SectionInfo]).asInstanceOf[SectionInfo])
        val sectName = new Path(editor.getEditorInput.getName).removeFileExtension.toString

        sectInfo map ((_, sectName))
      }

      case _ => None
    }

    snapshotSectionInfo orElse editorSectionInfo
  }

  private def activeEditor: Option[IEditorPart] = {
    val page = Option(PlatformUI.getWorkbench.getActiveWorkbenchWindow.getActivePage)
    page flatMap (p => Option(p.getActiveEditor))
  }

}
