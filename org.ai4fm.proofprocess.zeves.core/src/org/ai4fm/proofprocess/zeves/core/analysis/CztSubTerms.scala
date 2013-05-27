package org.ai4fm.proofprocess.zeves.core.analysis

import net.sourceforge.czt.base.{ast => z}
import net.sourceforge.czt.z.ast.{AndPred, Expr, ExprPred, Pred}

/**
 * Sub-term computation utilities for CZT terms.
 * 
 * @author Andrius Velykis
 */
object CztSubTerms {

  def subTerms(obj: AnyRef, maxDepth: Int): List[z.Term] =
    // collect the subterms and reverse the result (it is accumulated backwards)
    collectSubTerms(maxDepth, 0)(Nil, obj).distinct.reverse


  private def collectSubTerms(maxDepth: Int, depth: Int)(
    acc: List[z.Term],
    obj: AnyRef): List[z.Term] =
    if (depth > maxDepth) {
      acc
    } else obj match {

      case AndTerm(andPred, andTerms) =>
        collectSubTermList(andPred :: acc, andTerms, maxDepth, depth)

      case SubTerm(zTerm) =>
        collectSubTermList(zTerm :: acc, zTerm.getChildren, maxDepth, depth)

      case zObj: z.Term =>
        collectSubTermList(acc, zObj.getChildren, maxDepth, depth)

      case _ => acc
    }

  private def collectSubTermList(acc: List[z.Term],
                                 objs: Seq[AnyRef],
                                 maxDepth: Int,
                                 depth: Int): List[z.Term] =
    (objs foldLeft acc)(collectSubTerms(maxDepth, depth + 1))


  object AndTerm {

    def unapply(zObj: z.Term): Option[(z.Term, List[z.Term])] = zObj match {
      case andPred: AndPred => Some((andPred, unfoldAndPred(andPred)))
      case _ => None
    }

    private def unfoldAndPred(zObj: z.Term): List[z.Term] = zObj match {
      case andPred: AndPred =>
        unfoldAndPred(andPred.getLeftPred) ::: unfoldAndPred(andPred.getRightPred)

      case _ => List(zObj)
    }

  }

  object SubTerm {

    def unapply(zObj: z.Term): Option[z.Term] = zObj match {
      case exprPred: ExprPred => Option(exprPred.getExpr)
      case expr: Expr => Some(expr)
      case pred: Pred => Some(pred)
      case _ => None
    }
  }

}
