package org.ai4fm.graph.isomorphism

import scala.collection.Set
import scala.language.higherKinds

import scalax.collection.Graph
import scalax.collection.GraphPredef._


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
  
  private type Edge1 = g1.EdgeT
  private type Edge2 = g2.EdgeT
  
  
  val isFeasibleNode: (Node1, Node2) => Boolean
  
  val isFeasibleEdge: Option[(Edge1, Edge2) => Boolean]
  
  
  def fromState0(s: State): Stream[Map[Node2, Node1]] =
    if (g2.order > g1.order) Stream(Map()) else fromState(s)
  
  def fromState(s: State): Stream[Map[Node2, Node1]] = {
    
    val candidates = s.nextCandidates
    
    val candidateBranches = candidates.toStream flatMap { 
      case (n, m) => {
        val nextS = s.nextState(n, m)
        
        if (nextS.isFeasiblePair(n, m)) {
          Some(fromState(nextS))
        } else {
          None
        }
      }
    }
    
    // flatten into a single stream
    s.mapping #:: candidateBranches.flatten
  }
  
  /** default isomorphism matching (depth-first ordering from the first node) */
  def default: MatchResult = 
    if (g2.isEmpty) empty else fromNode(g2.nodes.head)
  
  def from(rootVal: N2): MatchResult = fromNode(g2 get rootVal)

  private def fromNode(root: Node2): MatchResult = fromInitial(Map(), root)

  def fromInitial(initialMappings: Map[N2, N1]): MatchResult =
    if (g2.isEmpty) empty else fromInitial(initialMappings, g2.nodes.head)
  
  def fromInitial(initialMappings: Map[N2, N1], root: Node2): MatchResult = {

    val dfsOrder = NodeOrderings.depthFirstOrdering(g2, root)
    val initState = new State(Map(), dfsOrder)

    val state = if (initialMappings.isEmpty) {
      Some(initState)
    } else {
      checkMappings(initialMappings, initState)
    }

    state match {
      // for invalid state, return an empty stream instead of `empty`, which is an empty mapping
      case None => new MatchResult(Stream.empty)
      
      case Some(s) => new MatchResult(fromState0(s))
    }
  }

  private def checkMappings(mappings: Map[N2, N1], initState: State): Option[State] = {

    val initStateOpt: Option[State] = Some(initState)
    
    // check each mapping whether it is feasible and incrementally build up the state
    // if an illegal mapping is found, the state becomes None
    (mappings foldLeft initStateOpt) {
      
      case (Some(s), (mVal, nVal)) => {
        
        val n = g1 get nVal
        val m = g2 get mVal
        
        val nextState = s.nextState(n, m)
        if (nextState.isFeasiblePair(n, m)) {
          Some(nextState)
        } else {
          None
        }
      }

      case (None, _) => None
    }

  }
  
  class State(val mapping: Map[Node2, Node1], val ord: Ordering[_ >: Node2]) {
    
    def nextState(n: Node1, m: Node2): State = new State(mapping + (m -> n), ord)

    lazy val mapped1 = mapping.values.toSet
    lazy val mapped2 = mapping.keySet
    
    def terminals[Node](mapped: Set[Node], direction: Node => Set[Node]): Set[Node] = {
      val linked = (mapped map direction).flatten
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
    def isFeasibleSemantic(n: Node1, m: Node2): Boolean =
      isFeasibleNode(n, m) && 
        // only check edges if the feasibility function is defined
        // (computation required to find edges)
        (isFeasibleEdge forall (isFeasibleEdges(_, n, m)))
    
    def isFeasibleEdges(feas: (Edge1, Edge2) => Boolean, n: Node1, m: Node2): Boolean = {
      
      def checkEdges(linked: Set[Node2], 
          findEdge1: Node1 => Option[Edge1], 
          findEdge2: Node2 => Option[Edge2]): Boolean = {
        
        val neighborPairs = linked flatMap (m2 => (mapping get m2) map ((m2, _)))
        neighborPairs forall { case (m2, n2) => feas(findEdge1(n2).get, findEdge2(m2).get) }
      }
      
      checkEdges(m.diSuccessors, n2 => n.findOutgoingTo(n2), m2 => m.findOutgoingTo(m2)) &&
        checkEdges(m.diPredecessors, n2 => n.findIncomingFrom(n2), m2 => m.findIncomingFrom(m2))
    }
    
  }

  class MatchResult(val mappings: Stream[Map[Node2, Node1]]) {
    
    lazy val isomorphisms: Stream[Map[Node2, Node1]] = {
      val allNodes = g2.order
      mappings filter (_.size == allNodes)
    }

    def isomorphism: Option[Map[Node2, Node1]] = isomorphisms.headOption

    def isIsomorphism: Boolean = isomorphism.isDefined

    lazy val unmappedNodes: Option[Set[Node1]] =
      isomorphism map (mapping => g1.nodes diff mapping.values.toSet)
  }
  
  // empty match - result with only the single empty map (e.g. indicating that nothing matches)
  lazy val empty = new MatchResult(Stream(Map()))
  
}

object VF2Isomorphism {

  def apply[N1, E1[X1] <: EdgeLikeIn[X1], N2, E2[X2] <: EdgeLikeIn[X2]](
            graph1: Graph[N1, E1],
            graph2: Graph[N2, E2],
            matchNode: Option[(N1, N2) => Boolean] = None,
            matchEdge: Option[(E1[_], E2[_]) => Boolean] = None) =
    new VF2Isomorphism[N1, E1, N2, E2] {
      override val g1 = graph1
      override val g2 = graph2
      
      override val isFeasibleNode: (Node1, Node2) => Boolean = matchNode match {
        case Some(matcher) => ( (n, m) => matcher(n.value, m.value) )
        case None => ( (_, _) => true )
      }
      
      override val isFeasibleEdge: Option[(Edge1, Edge2) => Boolean] = matchEdge match {
        case Some(matcher) => Some( (e1, e2) => matcher(e1.value, e2.value) )
        case None => None
      }
      
    }

}
