package org.ai4fm.proofprocess.zeves.core.analysis

import java.{util => ju}

import net.sourceforge.czt.base.{ast => z}
import net.sourceforge.czt.z.ast.{AndPred, Expr, MemPred, Pred, TupleExpr, ZName}
import net.sourceforge.czt.zeves.util.Factory


/**
 * Schema-term computation utilities for CZT terms.
 * 
 * Creates terms with placeholders (term schemas).
 * 
 * @author Andrius Velykis
 */
object CztSchemaTerms {

  var counter = 1

  def schemaTerms(obj: AnyRef): List[z.Term] = obj match {
    case memPred: MemPred if memPred.getMixfix() => {
      val copy = memPred.create(memPred.getChildren).asInstanceOf[MemPred]
      // only change the left expr (right expr is the operator - keep it)
      copy.setLeftExpr(createReplacement(copy.getLeftExpr))
      List(copy)
    }

    case zObj: z.Term => {
      val children = zObj.getChildren
      val tempChildren = children map createReplacement
      List(zObj.create(tempChildren))
    }

    case _ => Nil
  }

  lazy val factory = new Factory

  private def nextCounter: Int = {
    val c = counter
    counter = counter + 1
    c
  }

  private def createReplacement[T](obj: T): T = obj match {
    case list: z.Term with ju.List[_] => createChildReplacements(list).asInstanceOf[T]

    case tuple: TupleExpr => createChildReplacements(tuple).asInstanceOf[T]

    case and: AndPred => createChildReplacements(and).asInstanceOf[T]

    case expr: Expr => {
      val tempName = factory.createZName("?e" + nextCounter)
      val tempExpr = factory.createRefExpr(tempName)
      tempExpr.asInstanceOf[T]
    }

    case pred: Pred => createPredPlaceholder().asInstanceOf[T]

    case other => other
  }

  def createNamePlaceholder(): ZName =
    factory.createZName("?n" + nextCounter)

  def createPredPlaceholder(): Pred = {
    val tempName = factory.createZName("?p" + nextCounter)
    val tempExpr = factory.createRefExpr(tempName)
    val tempPred = factory.createExprPred(tempExpr)
    tempPred
  }

  private def createChildReplacements[T <: z.Term](zObj: T): T = {
    val children = zObj.getChildren
    val tempChildren = children map createReplacement
    zObj.create(tempChildren).asInstanceOf[T]
  }

}
