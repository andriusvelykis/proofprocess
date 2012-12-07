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

  def depthFirstTraversal2(): List[Node2] =
    if (g2.isEmpty) {
      List()
    } else {
      depthFirstTraversalM(g2.nodes.head)
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
    
    val candidates = s.nextCandidates
    
    val candidateBranches = candidates.toStream flatMap { 
      case (n, m) => {
        val nextS = State(s.mapping + (m -> n), s.ord)
        
        if (nextS.isFeasiblePair(n, m)) {
          Some(from(nextS))
        } else {
          None
        }
      }
    }
    
    // flatten into a single stream
    s.mapping #:: candidateBranches.flatten
  }
  
  def predefOrdering(ordered: List[Node2]): g2.NodeOrdering = {
    
    val orderIndexes = ordered.zipWithIndex.toMap

    def compare(n1: Node2, n2: Node2): Int =
      (orderIndexes get n1, orderIndexes get n2) match {
        case (Some(i1), Some(i2)) => i1 - i2
        case (Some(_), _) => -1
        case (_, Some(_)) => 1
        case _ => 0
      }
    
    g2.NodeOrdering( compare )
  }
  
  lazy val depthFirstOrdering: g2.NodeOrdering = predefOrdering(depthFirstTraversal2)
  
  lazy val mappings: Stream[Map[Node2, Node1]] = from(State(Map(), depthFirstOrdering))
  
  lazy val isomorphisms: Stream[Map[Node2, Node1]] = {
    val allNodes = g2.nodes.size
    mappings filter (_.size == allNodes)
  }
  
  def isomorphism: Option[Map[Node2, Node1]] = isomorphisms.headOption
  
  def isIsomorphism: Boolean = isomorphism.isDefined
  
  case class State(mapping: Map[Node2, Node1], ord: g2.NodeOrdering) {

    lazy val mapped1 = mapping.values.toSet
    lazy val mapped2 = mapping.keySet
    
    def terminals[Node](mapped: Set[Node], direction: Node => Set[Node]): Set[Node] = {
      val linked = (mapped map direction) flatten
      val unmapped = linked diff mapped
      
      unmapped
    }

    lazy val terminals1Out = terminals[Node1](mapped1, n => n.diSuccessors)
    lazy val terminals1In = terminals[Node1](mapped1, n => n.diPredecessors)
    lazy val lookaheadNew1 = g1.nodes diff ( mapped1 ++ terminals1In ++ terminals1Out )

    lazy val terminals2Out = terminals[Node2](mapped2, n => n.diSuccessors)
    lazy val terminals2In = terminals[Node2](mapped2, m => m.diPredecessors)
    lazy val lookaheadNew2 = g2.nodes diff ( mapped2 ++ terminals2In ++ terminals2Out )
    
    def min(nodes: Set[Node2]): Node2 = nodes min ord
    
    def nextCandidates: List[(Node1, Node2)] = {
      
      def pairs(t1: Set[Node1], t2: Set[Node2]): Option[List[(Node1, Node2)]] =
        if (t1.isEmpty && t2.isEmpty) {
          // both are empty, so check the next terminals
          None
        } else if (t1.isEmpty || t2.isEmpty) {
          // one is empty (paper says that this does not lead anywhere)
          // return empty list
          Some(List())
        } else {
          // get the next node to visit according to ordering
          val m = min(t2)
          // create the pairs
          val ps = t1.toList map ( n => (n, m) )
          Some(ps)
        }

      pairs(terminals1Out, terminals2Out) getOrElse
        (pairs(terminals1In, terminals2In) getOrElse
          (pairs(lookaheadNew1, lookaheadNew2) getOrElse
            List()))
    }

    def isFeasiblePair(n: Node1, m: Node2): Boolean = 
      isFeasibleSyntax(n, m) && isFeasibleSemantic(n, m)
    
    def isFeasibleMapped(nLinked: Set[Node1], mLinked: Set[Node2]): Boolean = {
      // TODO check if the algorithm is correct
      // all n linked nodes that have mappings
      val nMapped = nLinked intersect mapped1

      // all mappings of m linked nodes
      val mMappedTo = mLinked flatMap mapping.get

      // require the sets to be the same
      nMapped == mMappedTo
    }

    def isFeasiblePreds(n: Node1, m: Node2): Boolean = 
      isFeasibleMapped(n.diPredecessors, m.diPredecessors)

    def isFeasibleSuccs(n: Node1, m: Node2): Boolean = 
      isFeasibleMapped(n.diSuccessors, m.diSuccessors)

    def isFeasibleLookahead(n: Node1, m: Node2, terminals1: Set[Node1], terminals2: Set[Node2]): Boolean = {

      def checkTerminalSizes(ns1: Set[Node1], ns2: Set[Node2]): Boolean = {
        val size1 = (ns1 intersect terminals1).size
        val size2 = (ns2 intersect terminals2).size

        size1 >= size2
      }

      checkTerminalSizes(n.diSuccessors, m.diSuccessors) &&
        checkTerminalSizes(n.diPredecessors, m.diPredecessors)
    }
    
    def isFeasibleIn(n: Node1, m: Node2): Boolean =
      isFeasibleLookahead(n, m, terminals1In, terminals2In)
      
    def isFeasibleOut(n: Node1, m: Node2): Boolean =
      isFeasibleLookahead(n, m, terminals1Out, terminals2Out)
      
    def isFeasibleNew(n: Node1, m: Node2): Boolean = {
      
      def checkLookaheadNewSizes(ns1: Set[Node1], ns2: Set[Node2]): Boolean = {
        val size1 = (ns1 intersect lookaheadNew1).size
        val size2 = (ns2 intersect lookaheadNew2).size

        size1 >= size2
      }

      checkLookaheadNewSizes(n.diPredecessors, m.diPredecessors) &&
        checkLookaheadNewSizes(n.diSuccessors, m.diSuccessors)
    }
    
    def isFeasibleSyntax(n: Node1, m: Node2): Boolean =
      isFeasiblePreds(n, m) && isFeasibleSuccs(n, m) && 
        isFeasibleIn(n, m) && isFeasibleOut(n, m) && isFeasibleNew(n, m)
        
    // FIXME (nodes + edges)
    def isFeasibleSemantic(n: Node1, m: Node2): Boolean = true
  }
  
}

object VF2Isomorphism {

  def apply[N1, E1[X1] <: EdgeLikeIn[X1], N2, E2[X2] <: EdgeLikeIn[X2]](
            graph1: Graph[N1, E1], graph2: Graph[N2, E2]) =
    new VF2Isomorphism[N1, E1, N2, E2] {
      val g1 = graph1
      val g2 = graph2
    }

}
