package org.ai4fm.graph.isomorphism

import scala.collection.Set
import scalax.collection.Graph
import scalax.collection.GraphPredef._
import scalax.collection.GraphTraversal.AnyConnected
import scalax.collection.GraphTraversal.VisitorReturn._


/**
 * Adaptation of VF2 graph isomorphism algorithm in Scala.
 * 
 * @author Andrius Velykis
 */
trait VF2Isomorphism[N1, E1[X1] <: EdgeLikeIn[X1], N2, E2[X2] <: EdgeLikeIn[X2]] {

  val g1: Graph[N1, E1]
  val g2: Graph[N2, E2]
  
  private type Node1 = g1.NodeT
  private type Node2 = g2.NodeT
  
  def matchNode(n: Node1, m: Node2): Boolean = true //n == m
  
  def matchEdge(nEdge: g1.EdgeT, mEdge: g2.EdgeT): Boolean = true

  def predsMatch(mapping: Map[Node2, Node1], n: Node1, m: Node2): Boolean = {
    
    // TODO check if the algorithm is correct
    // all n predecessors that have mappings
    val nPreds = n.diPredecessors intersect mapping.values.toSet
    
    // all mappings of m predecessors
    val mappedPreds = m.diPredecessors flatMap mapping.get
    
    // require the sets to be the same
    nPreds == mappedPreds
  }
  
  def succsMatch(mapping: Map[Node2, Node1], n: Node1, m: Node2): Boolean = {
    
    // all n successors that have mappings
    val nSuccs = n.diSuccessors intersect mapping.values.toSet
    
    // all mappings of m successors
    val mappedSuccs = m.diSuccessors flatMap mapping.get
    
    // require the sets to be the same
    nSuccs == mappedSuccs
  }
  
  def syntaxMatch(mapping: Map[Node2, Node1], n: Node1, m: Node2): Boolean = {
    // TODO 1- and 2-lookahead
    predsMatch(mapping, n, m) && succsMatch(mapping, n, m)
  }
  
  def semanticMatch(mapping: Map[Node2, Node1], n: Node1, m: Node2): Boolean = {
    // FIXME
    true
  }
  
  def isMatchFeasible(mapping0: Map[Node2, Node1], n: Node1, m: Node2): Boolean = {
    
    // TODO reuse mapping?
    val mapping = mapping0 + (m -> n)
    val result = syntaxMatch(mapping, n, m) && semanticMatch(mapping, n, m)
    result
  }

  def depthFirstTraversal2(): List[Node2] = {
    if (g2.isEmpty) {
      List()
    } else {
      depthFirstTraversalM(g2.nodes.head)
    }
  }
  
//  def depthFirstTraversal2[](rootVal: Node2): List[Node2] = depthFirstTraversalN(g2 get rootVal)
  
  def depthFirstTraversalM(root: Node2): List[Node2] = {

    def traverseConnected(root: Node2, all: Set[Node2], acc: List[Node2]): List[Node2] = {
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
    traverseConnected(root, g2.nodes, List()).reverse.distinct
  }
  
  def nodeCandidates(mapping: Map[Node2, Node1], m: Node2): Set[Node1] = {
    
    val mappedCandidates = mapping.values.toSet
    
    def candidatesVia(mNeighbors: Node2 => Set[Node2], 
                      nNeighbors: Node1 => Set[Node1]): Set[Node1] = {
      
      val mapped = mNeighbors(m) flatMap mapping.get
      val candidates = mapped map nNeighbors flatten
      
      candidates diff mappedCandidates
    }

    // check for candidates via successor ( node predecessors -> mapped -> node successors ) 
    lazy val candidatesPredSucc = candidatesVia( m => m.diPredecessors, n => n.diSuccessors )
    
    // try for candidates via predecessor ( node successors -> mapped -> node predecessors )
    lazy val candidatesSuccPred = candidatesVia( m => m.diSuccessors, n => n.diPredecessors )

    // finally, there are no hints to the candidates, so just take all unmapped nodes
    lazy val candidatesAllUnmapped = g1.nodes diff mappedCandidates
    
    if (!candidatesPredSucc.isEmpty) candidatesPredSucc
    else if (!candidatesSuccPred.isEmpty) candidatesSuccPred
    else candidatesAllUnmapped
  }
  
  def from(s: State): Stream[Map[Node2, Node1]] = {
    
    val mappedM = s.mapping.keySet
    // drop pending nodes that have already been mapped from the start
    val pendingM = s.pendingNodes dropWhile mappedM.contains
    
    if (pendingM.isEmpty) {
      Stream(s.mapping)
    } else {
      
      val m = pendingM.head
      
      // replace the node with its neighbors in the pending list
      // TODO review ordering, e.g. order the neighbors in the order they are in pendingM?
      lazy val newPendingM = m.neighbors.toList ++ pendingM.tail
      
      val candidateNs = nodeCandidates(s.mapping, m)
      
      val feasibleMatches = candidateNs.toStream filter (isMatchFeasible(s.mapping, _, m))
      
      val newMappings = feasibleMatches map ( n => from(State(s.mapping + (m -> n), newPendingM)) )
      
      s.mapping #:: newMappings.flatten
    }
  }
  
  lazy val mappings: Stream[Map[Node2, Node1]] = from(State(Map(), depthFirstTraversal2))
  
  lazy val isomorphisms: Stream[Map[Node2, Node1]] = {
    val allNodes = g2.nodes.size
    mappings filter (_.size == allNodes)
  }
  
  def isomorphism: Option[Map[Node2, Node1]] = isomorphisms.headOption
  
  def isIsomorphism: Boolean = isomorphism.isDefined
  
  case class State(mapping: Map[Node2, Node1], pendingNodes: List[Node2])
  
}

object VF2Isomorphism {

  def apply[N1, E1[X1] <: EdgeLikeIn[X1], N2, E2[X2] <: EdgeLikeIn[X2]](
            graph1: Graph[N1, E1], graph2: Graph[N2, E2]) =
    new VF2Isomorphism[N1, E1, N2, E2] {
      val g1 = graph1
      val g2 = graph2
    }

}
