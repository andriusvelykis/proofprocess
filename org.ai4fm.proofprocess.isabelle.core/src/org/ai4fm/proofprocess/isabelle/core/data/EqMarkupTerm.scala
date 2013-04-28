package org.ai4fm.proofprocess.isabelle.core.data

import org.ai4fm.proofprocess.core.analysis.EqTerm
import org.ai4fm.proofprocess.isabelle.MarkupTerm


/**
 * @author Andrius Velykis
 */
class EqMarkupTerm(val term: MarkupTerm) extends EqTerm {

  // cache for quick access to avoid EMF+CDO redirection
  lazy val display = term.getDisplay

  // assume immutable markup term
  override lazy val hashCode = 41 * display.hashCode

  override def equals(x: Any) = x match {
    case eqT: EqMarkupTerm => display == eqT.display
  }
}
