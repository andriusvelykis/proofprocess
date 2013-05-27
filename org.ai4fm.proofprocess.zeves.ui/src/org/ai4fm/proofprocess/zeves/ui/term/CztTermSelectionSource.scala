package org.ai4fm.proofprocess.zeves.ui.term

import scala.collection.JavaConverters._

import net.sourceforge.czt.base.{ast => z}
import net.sourceforge.czt.eclipse.core.document.DocumentUtil
import net.sourceforge.czt.eclipse.zeves.core.ZEvesCore
import net.sourceforge.czt.session.{Markup, SectionInfo, SectionManager}
import net.sourceforge.czt.zeves.util.PrintVisitor

import org.ai4fm.proofprocess.{ProofStep, Term}
import org.ai4fm.proofprocess.ui.{TermSelectionSource, TermSelectionSourceProvider}
import org.ai4fm.proofprocess.zeves.CztTerm
import org.ai4fm.proofprocess.zeves.core.analysis.CztSubTerms

import org.eclipse.core.runtime.{IAdapterFactory, Path}
import org.eclipse.jface.viewers.StyledString
import org.eclipse.ui.{IEditorPart, PlatformUI}


/**
 * The term selection source for `CztTerm`.
 * 
 * Decomposes the CZT term into parts.
 * 
 * @author Andrius Velykis
 */
class CztTermSelectionSource(term: CztTerm, context: ProofStep)
  extends TermSelectionSource {

  def SUBTERM_DEPTH = 2

  override val rendered: StyledString = {
    val text = term.getDisplay
    new StyledString(text, CztFontStyler)
  }

  override lazy val subTerms: List[Term] = {
    val ts = CztSubTerms.subTerms(term.getTerm, SUBTERM_DEPTH)

    val sectInfo = currentSectionInfo
    lazy val printVisitor = new PrintVisitor(true)

    def printZ(t: z.Term) = sectInfo match {
      case Some((sectMan: SectionManager, sectName)) =>
        DocumentUtil.print(t, sectMan, sectName, Markup.UNICODE, 100, true)

      case _ => t.accept(printVisitor)
    }
    
    ts map (t => CztPPTerm(t, printZ(t)))
  }


  private def currentSectionInfo: Option[(SectionInfo, String)] = {

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


  override def schemaTerms: List[Term] = Nil

}


class CztTermSelectionSourceAdapter extends IAdapterFactory {

  override def getAdapter(adaptableObject: Object, adapterType: Class[_]): Object =
    if (classOf[TermSelectionSourceProvider] == adapterType)
      adaptableObject match {
        case t: CztTerm => new CztTermSelectionSourceProvider(t)
        case _ => null
      }
    else null

  override def getAdapterList(): Array[Class[_]] =
    Array(classOf[TermSelectionSourceProvider])

}

class CztTermSelectionSourceProvider(term: CztTerm)
    extends TermSelectionSourceProvider {

  override def getTermSource(context: ProofStep): TermSelectionSource =
    new CztTermSelectionSource(term, context)

}
