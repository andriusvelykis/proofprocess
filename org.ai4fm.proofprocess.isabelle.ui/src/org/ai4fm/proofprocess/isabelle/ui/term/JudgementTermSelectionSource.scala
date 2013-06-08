package org.ai4fm.proofprocess.isabelle.ui.term

import scala.collection.JavaConverters._

import org.ai4fm.proofprocess.{ProofStep, Term}
import org.ai4fm.proofprocess.isabelle.JudgementTerm
import org.ai4fm.proofprocess.isabelle.provider.JudgementTermItemProvider
import org.ai4fm.proofprocess.ui.{TermSelectionSource, TermSelectionSourceProvider}

import org.eclipse.core.runtime.IAdapterFactory
import org.eclipse.jface.viewers.StyledString


/**
 * The term selection source for `JudgementTerm`.
 * 
 * Only provides subterm decomposition into assumptions and goal.
 * 
 * @author Andrius Velykis
 */
class JudgementTermSelectionSource(term: JudgementTerm, context: ProofStep)
  extends TermSelectionSource {

  override val rendered: StyledString = {
    val text = JudgementTermItemProvider.renderTerm(term)
    new StyledString(text, IsabelleFontStyler)
  }

  // add the term itself to subterms
  override def subTerms: List[Term] = term :: (term.getAssms.asScala.toList ++ List(term.getGoal))

  override def schemaTerms: List[Term] = Nil

  // do nothing for now (no diff)
  override def diff(other: Term): (Term, Term) = (term, other)

}


class JudgementTermSelectionSourceAdapter extends IAdapterFactory {

  override def getAdapter(adaptableObject: Object, adapterType: Class[_]): Object =
    if (classOf[TermSelectionSourceProvider] == adapterType)
      adaptableObject match {
        case t: JudgementTerm => new JudgementTermSelectionSourceProvider(t)
        case _ => null
      }
    else null

  override def getAdapterList(): Array[Class[_]] =
    Array(classOf[TermSelectionSourceProvider])

}

class JudgementTermSelectionSourceProvider(term: JudgementTerm)
    extends TermSelectionSourceProvider {

  override def getTermSource(context: ProofStep): TermSelectionSource =
    new JudgementTermSelectionSource(term, context)

}
