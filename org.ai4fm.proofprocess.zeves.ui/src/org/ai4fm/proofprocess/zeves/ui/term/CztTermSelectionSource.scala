package org.ai4fm.proofprocess.zeves.ui.term

import net.sourceforge.czt.base.{ast => z}
import net.sourceforge.czt.eclipse.core.document.DocumentUtil
import net.sourceforge.czt.session.{Markup, SectionManager}
import net.sourceforge.czt.zeves.util.PrintVisitor

import org.ai4fm.proofprocess.{ProofStep, Term}
import org.ai4fm.proofprocess.ui.{TermSelectionSource, TermSelectionSourceProvider}
import org.ai4fm.proofprocess.zeves.CztTerm
import org.ai4fm.proofprocess.zeves.core.analysis.{CztSchemaTerms, CztSubTerms}
import org.ai4fm.proofprocess.zeves.ui.internal.ZEvesPProcessUIPlugin.{error, log}

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

  // matcher for schema variables, e.g. `?a`
  private val schemaVarsMatch = """(\?\w+)""".r

  override val rendered: StyledString = {
    val text0 = term.getDisplay

    val text =
      if (!text0.contains("\n")) {
        // no newlines, thus possibly having the direct-from-Z/EVES rendering
        // re-render with CZT using nice formatting
        printZ(term.getTerm)
      } else {
        text0
      }

    // match schema variables
    val matches = schemaVarsMatch.findAllMatchIn(text)
    val matchRanges = matches map (m => (m.start, m.end))

    val result = new StyledString(text, CztFontStyler)

    matchRanges foreach {
      case (start, end) => result.setStyle(start, end - start, CztFontStyler.BLUE)
    }
    result
  }

  private lazy val sectInfo = CztUtil.currentSectionInfo
  private lazy val printVisitor = new PrintVisitor(true)

  private def printZ(t: z.Term): String = sectInfo match {

    case Some((sectMan: SectionManager, sectName)) =>
      DocumentUtil.print(t, sectMan, sectName, Markup.UNICODE, 100, true)

    case _ => t.accept(printVisitor)
  }


  private def printZSafe(t: z.Term): Option[String] =
    try {
      Some(printZ(t))
    } catch {
      case ex: Exception => {
        log(error(Some(ex), Some("Invalid schema term: " + ex.getMessage)))
        None
      }
    }


  override lazy val subTerms: List[Term] = {
    val ts = CztSubTerms.subTerms(term.getTerm, SUBTERM_DEPTH)
    ts map (t => CztPPTerm(t, printZ(t)))
  }


  override def schemaTerms: List[Term] = {
    val ts = CztSchemaTerms.schemaTerms(term.getTerm)
    val printed = ts map { t => printZSafe(t) map (CztPPTerm(t, _)) }
    printed.flatten
  }

  override def diff(other: Term): (Term, Term) = other match {
    case otherZ: CztTerm => {
      val (diff1, diff2) = CztSubTerms.diffSubTerms(term.getTerm, otherZ.getTerm)

      def newIfDifferent(t: CztTerm, diff: z.Term): Term =
        if (diff == t.getTerm) t else CztPPTerm(diff, printZ(diff))

      (newIfDifferent(term, diff1), newIfDifferent(otherZ, diff2))
    }

    case _ => (term, other)

  }

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
