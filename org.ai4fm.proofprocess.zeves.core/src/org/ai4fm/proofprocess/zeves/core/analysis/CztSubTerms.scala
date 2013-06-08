package org.ai4fm.proofprocess.zeves.core.analysis

import scala.collection.JavaConverters._

import net.sourceforge.czt.base.{ast => z}
import net.sourceforge.czt.z.ast._
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

    case (AndTerm(_, al1), AndTerm(_, al2)) => {
      val (same, diff1, diff2) = diffsQnt(al1, al2)

      // add placeholder if there are same assumptions that will be hidden
      val sameAnds = if (same.isEmpty) Nil else List(CztSchemaTerms.createPredPlaceholder())

      (AndTerm(sameAnds ::: diff1).get, AndTerm(sameAnds ::: diff2).get)
    }

    case (p1: Pred, p2: Pred) if sameClass(p1, p2) && (qntEq(p1) == qntEq(p2)) => {
      // match - use placeholders
      val p = CztSchemaTerms.createPredPlaceholder
      (p, p)
    }

    case (t1: z.Term, t2: z.Term) if sameClass(t1, t2) => {

      // decompose
      val children1 = t1.getChildren
      val children2 = t2.getChildren

      if (children1.length == children2.length) {
        val newChildren = (children1.toList zip children2.toList) map diffAny
        val (new1, new2) = newChildren.unzip
        (t1.create(new1.toArray), t2.create(new2.toArray))
      } else {
        (t1, t2)
      }
    }

    case (unknown1, unknown2) => (unknown1, unknown2)
  }

  private def diffAny(objPair: (AnyRef, AnyRef)): (AnyRef, AnyRef) = objPair match {
    case (t1: z.Term, t2: z.Term) => diffSubTerms(t1, t2)
    case other => other
  }

  private def sameClass(o1: AnyRef, o2: AnyRef): Boolean = o1.getClass == o2.getClass


  private def diffPred(e1: Pred, e2: Pred): (Option[Pred], Option[Pred]) =
    if (qntEq(e1) == qntEq(e2)) {
      val p = CztSchemaTerms.createPredPlaceholder
      (Some(p), Some(p))
    } else {
      (Some(e1), Some(e2))
    }

  private def diffsQnt[T <: z.Term](l1: List[T], l2: List[T]): (List[T], List[T], List[T]) = {

    val qntL1 = l1 map qntEq
    val qntL2 = l2 map qntEq

    val (sameQnt, diffQnt1, diffQnt2) = diffs(qntL1, qntL2)

    val same = sameQnt map (_.term)
    val diff1 = diffQnt1 map (_.term)
    val diff2 = diffQnt2 map (_.term)

    (same, diff1, diff2)
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


  def findQuantifiedNames(t: AnyRef): List[ZName] = t match {
    case qp: QntPred => findQntQuantifiedNames(qp.getZSchText, qp.getPred)

    case qe: QntExpr => findQntQuantifiedNames(qe.getZSchText, qe.getExpr)

    case zObj: z.Term => (zObj.getChildren.toList map findQuantifiedNames).flatten

    case _ => Nil
  }

  private def findQntQuantifiedNames(schText: ZSchText, body: AnyRef): List[ZName] = {
    val declList = schText.getZDeclList

    val declNames = declList.asScala.toList map {
      case (v: VarDecl) => {
        val declNames = v.getZNameList.asScala.toList map { case zn: ZName => zn }
        val exprNames = findQuantifiedNames(v.getExpr)

        declNames ::: exprNames
      }
      case unknown => findQuantifiedNames(unknown)
    }

    val qSchPredNames = findQuantifiedNames(schText.getPred)
    val bodyNames = findQuantifiedNames(body)

    declNames.flatten ::: qSchPredNames ::: bodyNames
  }

  private def replaceNames(t: z.Term, names: Map[ZName, ZName]): z.Term = {
    val children = t.getChildren
    val replaced = children map { c =>
      c match {
        case name: ZName => names.getOrElse(name, name)
        case zObj: z.Term => replaceNames(zObj, names)
        case obj => obj
      }
    }
    t.create(replaced)
  }

  private def qntEq[T <: z.Term](term: T): QntEquals[T] = new QntEquals(term)

  /**
   * A wrapper to perform quantifier-equals operations on the term.
   * 
   * Quantifier-equals normalises quantified names to match semantically-same expressions.
   */
  sealed class QntEquals[T <: z.Term](val term: T) {

    lazy val qntNames = findQuantifiedNames(term)
    
    override def equals(other0: Any): Boolean = other0 match {
      case other: QntEquals[_] =>
        if (qntNames.isEmpty && other.qntNames.isEmpty) {
          // no quantifiers - just perform term equality
          term == other.term
        } else if (qntNames.size == other.qntNames.size) {
          // same quantifier count - map to the same ones and compare
          val newNames = List.fill(qntNames.size)(CztSchemaTerms.createNamePlaceholder())

          val map1 = qntNames zip newNames
          val map2 = other.qntNames zip newNames
          
          val new1 = replaceNames(term, map1.toMap)
          val new2 = replaceNames(other.term, map2.toMap)

          // try checking equality after normalising the names
          new1 == new2

        } else {
          // different quantifier count - different terms
          false
        }

      case _ => false
    }

    override lazy val hashCode: Int = term.hashCode
  }


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
