package org.ai4fm.graph.isomorphism

import scalax.collection.Graph
import scalax.collection.GraphPredef._
import scalax.collection.GraphTraversal.AnyConnected
import scalax.collection.GraphTraversal.VisitorReturn._

/**
 * @author Andrius Velykis
 */
object NodeOrderings {

  private type Node[N, E[X] <: EdgeLikeIn[X]] = Graph[N, E]#InnerNodeLike

  def predefOrdering[N](ordered: List[N]): Ordering[N] = {

    val orderIndexes = ordered.zipWithIndex.toMap

    new Ordering[N] {
      override def compare(n1: N, n2: N): Int =
        (orderIndexes get n1, orderIndexes get n2) match {
          case (Some(i1), Some(i2)) => i1 - i2
          case (Some(_), _) => -1
          case (_, Some(_)) => 1
          case _ => 0
        }
    }
  }

}
