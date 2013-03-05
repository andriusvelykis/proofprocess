package org.ai4fm.proofprocess.core.graph

import scala.language.implicitConversions

import scalax.collection.Graph
import scalax.collection.GraphEdge._
import scalax.collection.GraphPredef._


/** A graph wrapper that provides a custom fold method on the graph.
  *
  * This differs from the default fold in that it allows indicating the roots from which to fold,
  * and allows taking node predecessors/successors into consideration.
  * 
  * Can be used as implicit conversion.
  *
  * @author Andrius Velykis
  */
class FoldableGraph[A](graph: Graph[A, DiEdge]) {
  
  private type GNode = graph.InnerNodeLike

  /** Applies the given operator to (node, predecessors, successors) of each node, while traversing
    * the graph from bottom right to the given roots (right->left depth-first search, down->up traversal).
    */
  def foldNodesRight[B](z: B)
                       (f: ((A, collection.Set[A], collection.Set[A]), B) => B)
                       (roots: Iterable[A]): B = {

    def foldRight0(z: B, visited: Set[GNode])(node: GNode): (B, Set[GNode]) = {

      if (visited.contains(node)) {
        // already visited - do not continue
        (z, visited)
      } else {

        // mark visited
        val nodeVisited = visited + node

        // depth-first from the right, so first go recursively
        val (childrenResult, childrenVisited) =
          node.diSuccessors.foldRight((z, nodeVisited)) {
            case (child, (acc, accVisited)) => foldRight0(acc, accVisited)(child)
          }

        // after depth-first, apply the function
        val result = f((node.value,
          node.diPredecessors.map(_.value),
          node.diSuccessors.map(_.value)), childrenResult)

        (result, childrenVisited)
      }
    }

    val (result, _) = roots.foldRight((z, Set[graph.InnerNodeLike]())) {
      case (root, (acc, visited)) => foldRight0(acc, visited)(graph get root)
    }

    result
  }

}

object FoldableGraph {
  
  implicit def toFoldableGraph[A](graph: Graph[A, DiEdge]) = new FoldableGraph(graph)
  
}
