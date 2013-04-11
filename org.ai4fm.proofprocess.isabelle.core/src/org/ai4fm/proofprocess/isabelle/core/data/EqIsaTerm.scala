package org.ai4fm.proofprocess.isabelle.core.data

import org.ai4fm.proofprocess.core.analysis.EqTerm
import org.ai4fm.proofprocess.isabelle.IsaTerm


/**
 * @author Andrius Velykis
 */
class EqIsaTerm(val term: IsaTerm) extends EqTerm {

  // cache for quick access to avoid EMF+CDO redirection
  lazy val display = term.getDisplay

  // assume immutable markup term
  override lazy val hashCode = 41 * display.hashCode

  override def equals(x: Any) = x match {
    case eqT: EqIsaTerm => display == eqT.display
  }
}
