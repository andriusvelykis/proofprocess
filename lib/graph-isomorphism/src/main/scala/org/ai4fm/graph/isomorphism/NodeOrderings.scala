package org.ai4fm.graph.isomorphism

import scala.language.higherKinds

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
  
  def depthFirstOrdering[N, E[X] <: EdgeLikeIn[X]](g: Graph[N, E], 
                                                   root: Node[N, E]): Ordering[Node[N, E]] = {

    // typecast because cannot use dependent types in parameters
    val r = root.asInstanceOf[g.NodeT]
    
    def traverseConnected(root: g.NodeT, all: scala.collection.Set[g.NodeT], 
                          acc: List[g.NodeT]): List[g.NodeT] = {
      
      // collected in reverse
      var dfsNodes = acc
      var size = 0;
      root.traverseNodes(direction = AnyConnected, breadthFirst = false) {
        node =>
          {
            dfsNodes = node :: dfsNodes
            size = size + 1
            Continue
          }
      }

      if (all.size > size) {
        // disconnected graph - recurse with remaining nodes
        val unvisitedNodes = all diff dfsNodes.toSet
        val nextRoot = unvisitedNodes.head

        traverseConnected(nextRoot, unvisitedNodes, dfsNodes)

      } else {
        dfsNodes
      }
    }

    // reverse after traversal to start with root
    val ordered = traverseConnected(r, g.nodes, List()).reverse.distinct
    
    predefOrdering(ordered)
  }

}
