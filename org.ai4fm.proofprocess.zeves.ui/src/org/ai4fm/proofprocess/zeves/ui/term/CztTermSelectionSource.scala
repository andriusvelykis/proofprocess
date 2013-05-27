package org.ai4fm.proofprocess.zeves.ui.term

import net.sourceforge.czt.base.{ast => z}
import net.sourceforge.czt.eclipse.core.document.DocumentUtil
import net.sourceforge.czt.session.{Markup, SectionManager}
import net.sourceforge.czt.zeves.util.PrintVisitor

import org.ai4fm.proofprocess.{ProofStep, Term}
import org.ai4fm.proofprocess.ui.{TermSelectionSource, TermSelectionSourceProvider}
import org.ai4fm.proofprocess.zeves.CztTerm
import org.ai4fm.proofprocess.zeves.core.analysis.CztSubTerms

import org.eclipse.core.runtime.IAdapterFactory
import org.eclipse.jface.viewers.StyledString


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

  private lazy val sectInfo = CztUtil.currentSectionInfo
  private lazy val printVisitor = new PrintVisitor(true)

  private def printZ(t: z.Term): String = sectInfo match {

    case Some((sectMan: SectionManager, sectName)) =>
      DocumentUtil.print(t, sectMan, sectName, Markup.UNICODE, 100, true)

    case _ => t.accept(printVisitor)
  }
  

  override lazy val subTerms: List[Term] = {
    val ts = CztSubTerms.subTerms(term.getTerm, SUBTERM_DEPTH)

    ts map (t => CztPPTerm(t, printZ(t)))
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
