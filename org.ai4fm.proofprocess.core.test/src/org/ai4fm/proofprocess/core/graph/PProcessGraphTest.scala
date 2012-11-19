package org.ai4fm.proofprocess.core.graph

import scala.collection.JavaConversions._

import org.ai4fm.proofprocess.ProofProcessFactory
import org.junit.Assert._

import org.junit.Test

import scalax.collection.immutable.Graph
import scalax.collection.GraphPredef._
import scalax.collection.GraphEdge._

/**
  * @author Andrius Velykis 
  */
class PProcessGraphTest {

  private val factory = ProofProcessFactory.eINSTANCE
  
  import PProcessGraph._
  import PProcessTree._
  
  def toPProcessTree = PProcessGraph.toPProcessTree(intPPTree, Entry(0)) _
  def toGraph = PProcessGraph.toGraph(intPPTree) _
  
  @Test
  def rootInEmptyGraph() {
    val e1 = e(1)
    val p1 = toPProcessTree(Graph(), List(e1))
    assertEquals(e1, p1)
    assertEquals((Graph(e1), List(e1)), toGraph(p1))
  }
  
  def newEntry = factory.createProofEntry
  
  val r1 = List(e(1))
  
  @Test
  def sequences() {
    
    val g1 = Graph(e(1) ~> e(2))
    val s1 = Seq(List(1, 2))
    val p1 = toPProcessTree(g1, r1)
    assertEquals(s1, p1)
    assertEquals((g1, r1), toGraph(p1))
    
    val g2 = g1 + (e(2) ~> e(3))
    val s2 = Seq(List(1, 2, 3))
    val p2 = toPProcessTree(g2, r1)
    assertEquals(s2, p2)
    assertEquals((g2, r1), toGraph(p2))
  }
  
  @Test
  def parallel() {
    
    val g1 = Graph(e(1) ~> e(2), e(1) ~> e(3))
    // TODO parallel permutations?
    val s1 = Seq(List(1, 
                      Par(Set(2,
                              3))))
    val p1 = toPProcessTree(g1, r1)
    assertEquals(s1, p1)
    assertEquals((g1, r1), toGraph(p1))
  }
  
  /** Simple merge:
    *   1
    *  / \
    *  2 3
    *  \ /
    *   4
    */
  val m1 = Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(3) ~> e(4))
  
  @Test
  def simpleMerge() {
    val s1 = Seq(List(1, 
                      Par(Set(2, 
                              3)), 
                      4))
    val p1 = toPProcessTree(m1, r1)
    assertEquals(s1, p1)
    assertEquals((m1, r1), toGraph(p1))
  }
  
  
  /** Double merge, first merge two branches (2 and 3), and then merge the result with another one (5-6)
    * at merge point 7:
    *    1
    *  / | \
    *  2 3 5
    *  \ / |
    *   4  6
    *   \ /
    *    7
    */
  val m2 = m1 + (e(1) ~> e(5), e(5) ~> e(6), e(6) ~> e(7), e(4) ~> e(7))
  
  @Test
  def doubleMerge() {
    val s2 = Seq(List(e(1),
                      Par(Set(Seq(List(Par(Set(2, 
                                               3)), 
                                       4)),
                              Seq(List(5,
                                       6)))),
                      7))
    val p2 = toPProcessTree(m2, r1)
    assertEquals(s2, p2)
    assertEquals((m2, r1), toGraph(p2))
  }

  /** Branch merged twice with two other different branches in parallel (branch 3 is merged with both 2 and 5
    * at distinct merge points (4 and 6). This is currently not supported, as it cannot be easily mapped
    * into ProofProcess tree structure (should not occur in proofs actually):
    *     1
    *  /  | \
    *  2  3  5
    *  \ /\ /
    *   4  6
    *   \ /
    *    7
    */
  val m3 = m2 + (e(3) ~> e(6))
  
  @Test
  def branchMergedTwiceInParallelFailing() {
    
    try {
      // cannot convert because e3 has two merges below it (with e4 and e6)
      toPProcessTree(m3, r1)
    } catch {
      case e: IllegalArgumentException => {
        // expected exception - do nothing! 
      }
    }
  }
  
  val m4 = Graph(e(1) ~> e(2), e(2) ~> e(3))
  
  @Test
  def lowRootMerge() {
    // note no root element for multiple roots: starts with a parallel
    val s4 = Seq(List(Par(Set(Seq(List(1, 
                                       2)))), 
                      3))
    val rDouble = List(e(1), e(3))
    val p4 = toPProcessTree(m4, rDouble)
    assertEquals(s4, p4)
    // note that the root e3 gets dropped when converting to graph
    // since we cannot determine whether the parallel is exhaustive,
    // or whether a direct merge is necessary
    assertEquals((m4, List(e(1))), toGraph(p4))
  }
  
  /** One of the branches does not have any steps in it (1 -> 3). This is represented as a
    * parallel with a single branch and a merge point.
    *   1
    *  / \
    *  2  |
    *  \ /
    *   3
    * 
    * This is a similar example to `m4`, but with a single explicit root
    */
  val m5 = Graph(e(1) ~> e(2), e(2) ~> e(3), e(1) ~> e(3))
  
  @Test
  def directMerge() {
    val s5 = Seq(List(1,
                      Par(Set(2)), 
                      3))
    val p5 = toPProcessTree(m5, r1)
    assertEquals(s5, p5)
    // note that the link 1->3 gets dropped when converting to graph
    // since we cannot determine whether the parallel is exhaustive,
    // or whether a direct merge is necessary
    assertEquals((m5 - (e(1) ~> e(3)), r1), toGraph(p5))
  }
  
  /** Double merge of branches with no steps in them (1 -> 3 and 1 -> 4). This is represented as a
    * nested parallel with a single branch and merge points following.
    *     1
    *  /  | \
    *  2  | |
    *  \ /  |
    *   3   |
    *    \ /
    *     4
    * 
    * This is the same example as `m4`, but with a single explicit root
    */
  val m6 = m5 + (e(3) ~> e(4), e(1) ~> e(4))
  
  @Test
  def directMerge2() {
    val s6 = Seq(List(1, 
                      Par(Set(Seq(List(Par(Set(2)), 
                                       3)))),
                      4))
    val p6 = toPProcessTree(m6, r1)
    assertEquals(s6, p6)
    // note that both links 1->3 and 1->4 get dropped when converting to graph
    assertEquals((m6 - (e(1) ~> e(3), e(1) ~> e(4)), r1), toGraph(p6))
  }
  
  // Int + Case Class based testing data structures (to avoid creating EMF ones)
  
  implicit def e(entry: Int): Entry = Entry(entry)
  
  sealed trait PElem
  case class Entry(e: Int) extends PElem
  case class Seq(seq: List[PElem]) extends PElem
  case class Par(par: Set[PElem]) extends PElem
  case class Decor(entry: PElem) extends PElem
  
  object EntryCase extends CaseObject[PElem, Entry, Int]{
    def unapply(e: PElem): Option[Int] = e match {
        case Entry(c) => Some(c)
        case _ => None
      }
    def apply(c: Int) = Entry(c)
  }
  
  object SeqCase extends CaseObject[PElem, Seq, List[PElem]] {
    def unapply(e: PElem): Option[List[PElem]] = e match {
        case Seq(elems) => Some(elems)
        case _ => None
      }
    def apply(elems: List[PElem]) = Seq(elems)
  }
  
  object ParCase extends CaseObject[PElem, Par, Set[PElem]] {
    def unapply(e: PElem) = e match {
        case Par(elems) => Some(elems)
        case _ => None
      }
    def apply(elems: Set[PElem]) = Par(elems)
  }
  
  object DecorCase extends CaseObject[PElem, Decor, PElem] {
    def unapply(e: PElem): Option[PElem] = e match {
        case Decor(e) => Some(e)
        case _ => None
      }
    def apply(elem: PElem) = Decor(elem)
  }
  
  val intPPTree = new PProcessTree[PElem, Entry, Seq, Par, Decor, Int] {
    override def entry = EntryCase
    override def seq = SeqCase
    override def parallel = ParCase
    override def decor = DecorCase
  }
  
  
//  def isShape(shape: PElem, ptree: ProofElem): Boolean = (shape, ptree) match {
//    case (Entry(e), pe: ProofElem) => e == pe
//    case (Seq(seq), pseq: ProofSeq) => isShapeElems(seq, pseq.getEntries)
//    // for parallel, check for any permutation of branches
//    case (Par(par), ppar: ProofParallel) => par.permutations.exists(isShapeElems(_, ppar.getEntries))
//    case (Decor(e), d: ProofDecor) => isShape(e, d.getEntry)
//    case _ => false
//  }
//  
//  def isShapeElems(shapes: List[PElem], pelems: EList[ProofElem]): Boolean = 
//    shapes.size == pelems.size && 
//    	(shapes zip pelems).forall(Function.tupled(isShape))
//    	
  
}
