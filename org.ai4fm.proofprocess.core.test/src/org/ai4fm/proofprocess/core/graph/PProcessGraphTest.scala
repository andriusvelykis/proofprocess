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

  val intPPTree = new PProcessTree[PElem, Entry, Seq, Par, Int, Int] {
    override def entry = EntryCase
    override def seq = SeqCase
    override def parallel = ParCase
    // TODO better solution?
    override def info(elem: PElem): Int = 0
    // FIXME
    override def addInfo(elem: PElem, info: Int): PElem = elem
  }

  private val graphConverter = new PProcessGraph(intPPTree, Entry(0))
  
  def toPProcessTree(g: PPGraph[Entry], rs: PPGraphRoots[Entry]) = 
    graphConverter.toPProcessTree(PPRootGraph(g, rs, Map()))
  def toGraph = graphConverter.toGraph _


  private def assertGraphEquals[E](rg1: PPRootGraph[E, _], rg2: PPRootGraph[E, _]) = {
    val PPRootGraph(g1, r1, _) = rg1
    val PPRootGraph(g2, r2, _) = rg2

    assertEquals(g1, g2)
    assertEquals(r1, r2)
  }

  private def ppRootGraph[E](graph: PPGraph[E], roots: PPGraphRoots[E]): PPRootGraph[E, _] =
    PPRootGraph(graph, roots, Map())
  
  @Test
  def rootInEmptyGraph() {
    val e1 = e(1)
    val p1 = toPProcessTree(Graph(), List(e1))
    assertEquals(e1, p1)
    assertGraphEquals(ppRootGraph(Graph(e1), List(e1)), toGraph(p1))
  }
  
  val r1 = List(e(1))
  
  @Test
  def sequences() {
    
    val g1 = Graph(e(1) ~> e(2))
    val s1 = Seq(List(1, 2))
    val p1 = toPProcessTree(g1, r1)
    assertEquals(s1, p1)
    assertGraphEquals(ppRootGraph(g1, r1), toGraph(p1))
    
    val g2 = g1 + (e(2) ~> e(3))
    val s2 = Seq(List(1, 2, 3))
    val p2 = toPProcessTree(g2, r1)
    assertEquals(s2, p2)
    assertGraphEquals(ppRootGraph(g2, r1), toGraph(p2))
  }
  
  @Test
  def parallel() {
    
    val g1 = Graph(e(1) ~> e(2), e(1) ~> e(3))
    val s1 = Seq(List(1, 
                      Par(Set(2,
                              3))))
    val p1 = toPProcessTree(g1, r1)
    assertEquals(s1, p1)
    assertGraphEquals(ppRootGraph(g1, r1), toGraph(p1))
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
    assertGraphEquals(ppRootGraph(m1, r1), toGraph(p1))
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
    assertGraphEquals(ppRootGraph(m2, r1), toGraph(p2))
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
    val s3 = Seq(List(1, 
                      Par(Set(Seq(List(2,
                                       Par(Set(),
                                           Set(4)))),
                              Seq(List(5,
                                       Par(Set(),
                                           Set(6)))),
                              Seq(List(3,
                                       Par(Set(4,
                                               6)),
                                       7))))))
    val p3 = toPProcessTree(m3, r1)
    assertEquals(s3, p3)
    assertGraphEquals(ppRootGraph(m3, r1), toGraph(p3))
  }
  
  val m4 = Graph(e(1) ~> e(2), e(2) ~> e(3))
  
  @Test
  def lowRootMerge() {
    // note no root element for multiple roots: starts with a parallel
    // also note the soft link `-> 3` at root to indicate multi-root
    val s4 = Seq(List(Par(Set(Seq(List(1, 
                                       2))),
                          Set(3)), 
                      3))
    val rDouble = List(e(1), e(3))
    val p4 = toPProcessTree(m4, rDouble)
    assertEquals(s4, p4)
    // note that the root e3 gets dropped when converting to graph
    // since we cannot determine whether the parallel is exhaustive,
    // or whether a direct merge is necessary
    assertGraphEquals(ppRootGraph(m4, rDouble), toGraph(p4))
  }
  
  /** One of the branches does not have any steps in it (1 -> 3). This is represented as a
    * parallel with a single branch and a merge point.
    *   1
    *  / \
    *  2  |
    *  \ /
    *   3
    * 
    * This is a similar example to `m4`, but with a single explicit root.
    * The link 1 -> 3 is recorded as a soft link.
    */
  val m5 = Graph(e(1) ~> e(2), e(2) ~> e(3), e(1) ~> e(3))
  
  @Test
  def directMerge() {
    val s5 = Seq(List(1,
                      Par(Set(2), Set(3)), 
                      3))
    val p5 = toPProcessTree(m5, r1)
    assertEquals(s5, p5)
    assertGraphEquals(ppRootGraph(m5, r1), toGraph(p5))
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
                      Par(Set(Seq(List(Par(Set(2),
                                           Set(3)), 
                                       3))),
                          Set(4)),
                      4))
    val p6 = toPProcessTree(m6, r1)
    assertEquals(s6, p6)
    // note that both links 1->3 and 1->4 get dropped when converting to graph
    assertGraphEquals(ppRootGraph(m6, r1), toGraph(p6))
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
   */
  val m7 = Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(2) ~> e(5), 
                 e(3) ~> e(4), e(4) ~> e(5))
  
  @Test
  def complexMerge1() {
    val s7 = Seq(List(1, 
                      Par(Set(Seq(List(3,
                                       4)),
                              Seq(List(2,
                                       Par(Set(), Set(4)))))),
                      5))

    // The actual conversion to PPTree currently prefers smaller merges (need to add better layout)
    val actual7 = Seq(List(1,
                           Par(Set(Seq(List(3,
                                            Par(Set(),
                                                Set(4)))), 
                                   Seq(List(2,
                                            Par(Set(4),
                                                Set(5)), 
                                       5))))))
    
    val p7 = toPProcessTree(m7, r1)
    assertEquals(actual7, p7)
    assertGraphEquals(ppRootGraph(m7, r1), toGraph(p7))
    assertGraphEquals(ppRootGraph(m7, r1), toGraph(s7))
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
   */
  val m8 = m7 + (e(3) ~> e(6), e(5) ~> e(6))
  
  @Test
  def complexMerge2() {
//    val s8 = Seq(List(1, 
//                      Par(Set(3,
//                              2)),
//                      4,
//                      5,
//                      6))

    val actual8 = Seq(List(1,
                           Par(Set(Seq(List(2,
                                            Par(Set(),
                                                Set(4, 5)))),
                                   Seq(List(3,
                                            Par(Set(Seq(List(4,
                                                             5))),
                                                Set(6)),
                                            6))))))

    val p8 = toPProcessTree(m8, r1)
    assertEquals(actual8, p8)
    assertGraphEquals(ppRootGraph(m8, r1), toGraph(p8))
  }


  /**
   * A merge for the lower branches
   *    1
   *    |
   *    2
   *    |\
   *    3 \
   *   / \ \
   *  4   6 |
   *  |   |/
   *  5   7
   * 
   * The 2->7 will have a soft link.
   */
  val m9 = Graph(e(1) ~> e(2), e(2) ~> e(3), e(3) ~> e(4), e(4) ~> e(5), 
                 e(3) ~> e(6), e(6) ~> e(7), e(2) ~> e(7))
  
  @Test
  def softLink() {
    val s9 = Seq(List(1,
                      2,
                      Par(Set(Seq(List(3,
                                       Par(Set(Seq(List(4,
                                                        5)),
                                               Seq(List(6,
                                                        7))))
                                       ))),
                          Set(7)) // <-- soft link
                      ))

    val p9 = toPProcessTree(m9, r1)
    assertEquals(s9, p9)
    assertGraphEquals(ppRootGraph(m9, r1), toGraph(p9))
  }

  
  /**
   * A case of additional branching within merged branches
   *    1
   *    |
   *    2
   *   / \
   *  3   \
   *  |   |
   *  4   6
   *   \ / \
   *    5   7
   * 
   * 
   */
  val m10 = Graph(e(1) ~> e(2), e(2) ~> e(3), e(3) ~> e(4), e(4) ~> e(5), 
                  e(2) ~> e(6), e(6) ~> e(5), e(6) ~> e(7))
  
  @Test
  def softLink2() {
    val s10 =  Seq(List(1,
                        2,
                        Par(Set(Seq(List(6,
                                         Par(Set(7),
                                         Set(5)))),
                                Seq(List(3,
                                         4,
                                         5))))))

    val p10 = toPProcessTree(m10, r1)
    assertEquals(s10, p10)
    assertGraphEquals(ppRootGraph(m10, r1), toGraph(p10))
  }

  /**
   * A case where double parallel appears in the sequence.
   *    1
   *   / \
   *  2   3
   *  | X |
   *  4   5
   *   \ /
   *    6
   *
   */
  val m11 = Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(2) ~> e(5),
                  e(3) ~> e(4), e(3) ~> e(5), e(4) ~> e(6), e(5) ~> e(6))

  @Test
  def doubleParallel() {
    val s11 = Seq(List(1,
                      Par(Set(2, 3)),
                      Par(Set(4, 5)),
                      6))

    // The actual conversion to PPTree cannot recognise the double parallels yet
    // (it is quite a corner case)
    val actual11 = Seq(List(1,
                            Par(Set(Seq(List(3,
                                             Par(Set(),
                                                 Set(5, 4)))),
                                    Seq(List(2,
                                             Par(Set(5, 4),
                                                 Set()),
                                             6))))))

    val p11 = toPProcessTree(m11, r1)
    assertEquals(actual11, p11)
    assertGraphEquals(ppRootGraph(m11, r1), toGraph(p11))
    assertGraphEquals(ppRootGraph(m11, r1), toGraph(s11))
  }

  
  // Int + Case Class based testing data structures (to avoid creating EMF ones)
  
  implicit def e(entry: Int): Entry = Entry(entry)
  
  sealed trait PElem
  case class Entry(e: Int) extends PElem
  case class Seq(seq: List[PElem]) extends PElem
  case class Par(par: Set[PElem], links: Set[Entry]) extends PElem

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
  
  
//  def isShape(shape: PElem, ptree: ProofElem): Boolean = (shape, ptree) match {
//    case (Entry(e), pe: ProofElem) => e == pe
//    case (Seq(seq), pseq: ProofSeq) => isShapeElems(seq, pseq.getEntries)
//    // for parallel, check for any permutation of branches
//    case (Par(par), ppar: ProofParallel) => par.permutations.exists(isShapeElems(_, ppar.getEntries))
//    case _ => false
//  }
//  
//  def isShapeElems(shapes: List[PElem], pelems: EList[ProofElem]): Boolean = 
//    shapes.size == pelems.size && 
//    	(shapes zip pelems).forall(Function.tupled(isShape))
//    	
  
}
