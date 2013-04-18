package org.ai4fm.proofprocess.isabelle.core.logic

import isabelle.Term._


/**
 * Various methods to manipulate Isabelle terms.
 * 
 * (adapted from Pure/logic.ML)
 * 
 * @author Andrius Velykis
 */
object Logic {

  /**
   * A1==>...An==>B  goes to  [A1,...,An], where B is not an implication
   */
  def strip_imp_prems(term: Term): List[Term] = term match {
    case (App(App(Const("==>", _), a), b)) => a :: strip_imp_prems(b)
    case _ => Nil
  }

  /**
   * A1==>...An==>B  goes to B, where B is not an implication
   */
  def strip_imp_concl(term: Term): Term = term match {
    case (App(App(Const("==>", _), a), b)) => strip_imp_concl(b)
    case a => a
  }

}
