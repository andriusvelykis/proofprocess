package org.ai4fm.proofprocess.core.graph

import scala.language.implicitConversions

import scalax.collection.GraphEdge.DiEdge
import scalax.collection.GraphPredef._
import scalax.collection.immutable.Graph

import org.ai4fm.proofprocess.core.graph.PProcessGraph._
import org.ai4fm.proofprocess.core.graph.PProcessTree._
import org.junit.Assert._
import org.junit.Test


/**
  * @author Andrius Velykis 
  */
class PProcessGraphTest {
  
  def toPProcessTree(g: PPGraph[Entry], rs: PPGraphRoots[Entry]) = 
    PProcessGraph.toPProcessTree(intPPTree, Entry(0))(PPRootGraph(g, rs))
  def toGraph = PProcessGraph.toGraph(intPPTree) _
  
  @Test
  def rootInEmptyGraph() {
    val e1 = e(1)
    val p1 = toPProcessTree(Graph(), List(e1))
    assertEquals(e1, p1)
    assertEquals(PPRootGraph(Graph(e1), List(e1)), toGraph(p1))
  }
  
  val r1 = List(e(1))
  
  @Test
  def sequences() {
    
    val g1 = Graph(e(1) ~> e(2))
    val s1 = Seq(List(1, 2))
    val p1 = toPProcessTree(g1, r1)
    assertEquals(s1, p1)
    assertEquals(PPRootGraph(g1, r1), toGraph(p1))
    
    val g2 = g1 + (e(2) ~> e(3))
    val s2 = Seq(List(1, 2, 3))
    val p2 = toPProcessTree(g2, r1)
    assertEquals(s2, p2)
    assertEquals(PPRootGraph(g2, r1), toGraph(p2))
  }
  
  @Test
  def parallel() {
    
    val g1 = Graph(e(1) ~> e(2), e(1) ~> e(3))
    val s1 = Seq(List(1, 
                      Par(Set(2,
                              3))))
    val p1 = toPProcessTree(g1, r1)
    assertEquals(s1, p1)
    assertEquals(PPRootGraph(g1, r1), toGraph(p1))
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
    assertEquals(PPRootGraph(m1, r1), toGraph(p1))
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
    assertEquals(PPRootGraph(m2, r1), toGraph(p2))
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
    assertEquals(PPRootGraph(m4, List(e(1))), toGraph(p4))
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
    assertEquals(PPRootGraph(m5 - (e(1) ~> e(3)), r1), toGraph(p5))
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
    assertEquals(PPRootGraph(m6 - (e(1) ~> e(3), e(1) ~> e(4)), r1), toGraph(p6))
  }
  
  /**
   * A complex double-merge at points 4 and 5:
   *    1
   *   / \
   *  2   3
   *  |\ /
   *  | 4
   *  |/
   *  5
   * 
   * When converting to ProofProcess tree, we get a nice split+merge with 1, 2, 3, 4. Then,
   * however, this branch is merged at 2 points into 5, which is lost - we assume there is
   * only one link, thus adding 5 sequentially. Link 2->5 is lost when converting back to a graph.
   */
  val m7 = Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(2) ~> e(5), 
                 e(3) ~> e(4), e(4) ~> e(5))
  
  @Test
  def complexMerge1() {
    val s7 = Seq(List(1, 
                      Par(Set(3,
                                       2)),
                      4,
                      5))
    
    val p7 = toPProcessTree(m7, r1)
    assertEquals(s7, p7)
    assertEquals(PPRootGraph(m7 - (e(2) ~> e(5)), r1), toGraph(p7))
  }
  
  /**
   * A further complex triple-merge extending m7:
   *    1
   *   / \
   *  2   3
   *  |\ /|
   *  | 4 |
   *  |/  |
   *  5   |
   *   \ /
   *    6 
   * 
   * Just like with m7, we lose the double merges (now also at point 6) when converting to
   * ProofProcess tree.
   */
  val m8 = m7 + (e(3) ~> e(6), e(5) ~> e(6))
  
  @Test
  def complexMerge2() {
    val s8 = Seq(List(1, 
                      Par(Set(3,
                              2)),
                      4,
                      5,
                      6))

    val p8 = toPProcessTree(m8, r1)
    assertEquals(s8, p8)
    assertEquals(PPRootGraph(m8 - (e(2) ~> e(5), e(3) ~> e(6)), r1), toGraph(p8))
  }
  
  // Int + Case Class based testing data structures (to avoid creating EMF ones)
  
  implicit def e(entry: Int): Entry = Entry(entry)
  
  sealed trait PElem
  case class Entry(e: Int) extends PElem
  case class Seq(seq: List[PElem]) extends PElem
  case class Par(par: Set[PElem], links: Set[Entry]) extends PElem
  case class Decor(entry: PElem) extends PElem

  object Par {
    def apply(elems: Set[PElem]): Par = Par(elems, Set())
  }
  
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
  
  object ParCase extends CaseObject[PElem, Par, (Set[PElem], Set[Entry])] {
    def unapply(e: PElem) = e match {
        case Par(entries, links) => Some(entries, links)
        case _ => None
      }
    def apply(elems: (Set[PElem], Set[Entry])) = {
      val (entries, links) = elems
      Par(entries, links)
    }
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
