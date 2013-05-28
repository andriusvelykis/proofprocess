package org.ai4fm.proofprocess.zeves.core.analysis

import net.sourceforge.czt.base.{ast => z}
import net.sourceforge.czt.z.ast.{And, AndPred, Expr, ExprPred, ImpliesPred, Pred}
import net.sourceforge.czt.zeves.util.Factory


/**
 * Sub-term computation utilities for CZT terms.
 * 
 * @author Andrius Velykis
 */
object CztSubTerms {

  lazy val factory = new Factory

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


  def diffSubTerms(t1: z.Term, t2: z.Term): (z.Term, z.Term) = (t1, t2) match {

    case (imp1: ImpliesPred, imp2: ImpliesPred) => {
      val assms1 = imp1.getLeftPred
      val assms2 = imp2.getLeftPred

      val goal1 = imp1.getRightPred
      val goal2 = imp2.getRightPred

      val (assmDiff1, assmDiff2) = (assms1, assms2) match {
        case (AndTerm(_, al1), AndTerm(_, al2)) => {
          val (same, diff1, diff2) = diffs(al1, al2)

          // add placeholder if there are same assumptions that will be hidden
          val sameAnds = if (same.isEmpty) Nil else List(CztSchemaTerms.createPredPlaceholder())
          
          (AndTerm(sameAnds ::: diff1), AndTerm(sameAnds ::: diff2))
        }

        case (a1, a2) => diffPred(a1, a2)
      }

      val (goalDiff1, goalDiff2) = diffPred(goal1, goal2)

      val newImp1 = impPred(assmDiff1, goalDiff1.get)
      val newImp2 = impPred(assmDiff2, goalDiff2.get)

      (newImp1, newImp2)
    }

    case (unknown1, unknown2) => (unknown1, unknown2)
  }

  private def diffPred(e1: Pred, e2: Pred): (Option[Pred], Option[Pred]) =
    diffSingle(e1, e2, CztSchemaTerms.createPredPlaceholder)
  
  private def diffSingle[A](e1: A, e2: A, placeholder: => A): (Option[A], Option[A]) =
    if (e1 == e2) {
      val p = placeholder
      (Some(p), Some(p))
    } else {
      (Some(e1), Some(e2))
    }

  private def diffs[A](l1: List[A], l2: List[A]): (List[A], List[A], List[A]) = {
    val same = l1 intersect l2
    val diffL1 = l1 diff same
    val diffL2 = l2 diff same

    (same, diffL1, diffL2)
  }

  private def impPred(assms: Option[Pred], goal: Pred): Pred =
    if (assms.isEmpty) goal
    else factory.createImpliesPred(assms.get, goal)


  object AndTerm {

    def unapply(zObj: z.Term): Option[(AndPred, List[Pred])] = zObj match {
      case andPred: AndPred => Some((andPred, unfoldAndPred(andPred)))
      case _ => None
    }

    private def unfoldAndPred(zObj: Pred): List[Pred] = zObj match {
      case andPred: AndPred =>
        unfoldAndPred(andPred.getLeftPred) ::: unfoldAndPred(andPred.getRightPred)

      case _ => List(zObj)
    }

    def apply(andTerms: List[Pred]): Option[Pred] = andTerms match {
      case Nil => None
      case single :: Nil => Some(single)
      case first :: rest => {
        val joined = (rest foldLeft first)(andPred)
        Some(joined)
      }
    }

    // TODO review And type
    private def andPred(left: Pred, right: Pred): AndPred =
      factory.createAndPred(left, right, And.Wedge)

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
