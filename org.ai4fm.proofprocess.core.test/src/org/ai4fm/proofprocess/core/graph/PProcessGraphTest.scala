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

  val intPPTree = new PProcessTree[PElem, Entry, Seq, Par, Id, Int, Int] {
    override def entry = EntryCase
    override def seq = SeqCase
    override def parallel = ParCase
    override def id = IdCase
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

  /**
   *   1
   *  / \
   * 2   3
   */
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

  /**
   * Simple merge:
   *   1
   *  / \
   * 2   3
   *  \ /
   *   4
   */
  @Test
  def simpleMerge() {
    val m1 = Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(3) ~> e(4))
    val s1 = Seq(List(1, 
                      Par(Set(2, 
                              3)), 
                      4))
    val p1 = toPProcessTree(m1, r1)
    assertEquals(s1, p1)
    assertGraphEquals(ppRootGraph(m1, r1), toGraph(p1))
  }
  
  
  /**
   * Double merge, first merge two branches (2 and 3), and then merge the result with
   * another one (5-6) at merge point 7:
   *    1
   *  / | \
   *  2 3 5
   *  \ / |
   *   4  6
   *   \ /
   *    7
   */
  @Test
  def doubleMerge() {
    val m2 = Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(3) ~> e(4), 
                   e(1) ~> e(5), e(5) ~> e(6), e(6) ~> e(7), e(4) ~> e(7))
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

  /**
   * Branch merged twice with two other different branches in parallel
   * (branch 3 is merged with both 2 and 5 at distinct merge points (4 and 6).
   *     1
   *  /  | \
   *  2  3  5
   *  \ /\ /
   *   4  6
   *   \ /
   *    7
   */
  @Test
  def branchMergedTwiceInParallel() {
    val m3 = Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(3) ~> e(4), 
                   e(1) ~> e(5), e(5) ~> e(6), e(6) ~> e(7), e(4) ~> e(7), e(3) ~> e(6))
    val s3 = Seq(List(1, 
                      Par(Set(Seq(List(2,
                                       Id(4))),
                              Seq(List(5,
                                       Id(6))),
                              Seq(List(3,
                                       Par(Set(4,
                                               6)),
                                       7))))))
    val p3 = toPProcessTree(m3, r1)
    assertEquals(s3, p3)
    assertGraphEquals(ppRootGraph(m3, r1), toGraph(p3))
  }
  
  
  @Test
  def lowRootMerge() {
    // note no root element for multiple roots: starts with a parallel
    // also note the soft link `-> 3` at root to indicate multi-root
    val m4 = Graph(e(1) ~> e(2), e(2) ~> e(3))
    val s4 = Seq(List(Par(Set(Seq(List(1, 
                                       2)),
                              Id(3))), 
                      3))
    val rDouble = List(e(1), e(3))
    val p4 = toPProcessTree(m4, rDouble)
    assertEquals(s4, p4)
    // note that the root e3 gets dropped when converting to graph
    // since we cannot determine whether the parallel is exhaustive,
    // or whether a direct merge is necessary
    assertGraphEquals(ppRootGraph(m4, rDouble), toGraph(p4))
  }

  /**
   * One of the branches does not have any steps in it (1 -> 3). This is represented as a
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
  @Test
  def directMerge() {
    val m5 = Graph(e(1) ~> e(2), e(2) ~> e(3), e(1) ~> e(3))
    val s5 = Seq(List(1,
                      Par(Set(2,
                              Id(3))), 
                      3))
    val p5 = toPProcessTree(m5, r1)
    assertEquals(s5, p5)
    assertGraphEquals(ppRootGraph(m5, r1), toGraph(p5))
  }

  
  /**
   * Double merge of branches with no steps in them (1 -> 3 and 1 -> 4). This is represented as a
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
  @Test
  def directMerge2() {
    val m6 = Graph(e(1) ~> e(2), e(2) ~> e(3), e(1) ~> e(3),
                   e(3) ~> e(4), e(1) ~> e(4))
    val s6 = Seq(List(1, 
                      Par(Set(Seq(List(Par(Set(2,
                                               Id(3))), 
                                       3)),
                              Id(4))),
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
  @Test
  def complexMerge1() {
    val m7 = Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(2) ~> e(5), 
                   e(3) ~> e(4), e(4) ~> e(5))
    val s7 = Seq(List(1, 
                      Par(Set(Seq(List(3,
                                       4,
                                       Id(5))),
                              Seq(List(2,
                                       Par(Set(Id(4),
                                               Id(5))))))),
                      5))

    // The actual conversion to PPTree currently prefers smaller merges (need to add better layout)
    val actual7 = Seq(List(1,
                           Par(Set(Seq(List(3,
                                            Id(4))), 
                                   Seq(List(2,
                                            Par(Set(4,
                                                    Id(5))), 
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
  @Test
  def complexMerge2() {
    val m8 = Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(2) ~> e(5), 
                   e(3) ~> e(4), e(4) ~> e(5), e(3) ~> e(6), e(5) ~> e(6))
//    val s8 = Seq(List(1, 
//                      Par(Set(3,
//                              2)),
//                      4,
//                      5,
//                      6))

    val actual8 = Seq(List(1,
                           Par(Set(Seq(List(2,
                                            Par(Set(Id(4),
                                                    Id(5))))),
                                   Seq(List(3,
                                            Par(Set(Seq(List(4,
                                                             5)),
                                                    Id(6))),
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
  @Test
  def softLink() {
    val m9 = Graph(e(1) ~> e(2), e(2) ~> e(3), e(3) ~> e(4), e(4) ~> e(5), 
                   e(3) ~> e(6), e(6) ~> e(7), e(2) ~> e(7))
    val s9 = Seq(List(1,
                      2,
                      Par(Set(Seq(List(3,
                                       Par(Set(Seq(List(4,
                                                        5)),
                                               Seq(List(6,
                                                        7))))
                                       )),
                              Id(7))) // <-- soft link
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
   */
  @Test
  def softLink2() {
    val m10 = Graph(e(1) ~> e(2), e(2) ~> e(3), e(3) ~> e(4), e(4) ~> e(5), 
                    e(2) ~> e(6), e(6) ~> e(5), e(6) ~> e(7))
    val s10 =  Seq(List(1,
                        2,
                        Par(Set(Seq(List(6,
                                         Par(Set(7,
                                                 Id(5))))),
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
  @Test
  def doubleParallel() {
    val m11 = Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(2) ~> e(5),
                    e(3) ~> e(4), e(3) ~> e(5), e(4) ~> e(6), e(5) ~> e(6))
    val s11 = Seq(List(1,
                      Par(Set(2, 3)),
                      Par(Set(4, 5)),
                      6))

    // The actual conversion to PPTree cannot recognise the double parallels yet
    // (it is quite a corner case)
    val actual11 = Seq(List(1,
                            Par(Set(Seq(List(3,
                                             Par(Set(Id(5),
                                                     Id(4))))),
                                    Seq(List(2,
                                             Par(Set(5, 4)),
                                             6))))))

    val p11 = toPProcessTree(m11, r1)
    assertEquals(actual11, p11)
    assertGraphEquals(ppRootGraph(m11, r1), toGraph(p11))
    assertGraphEquals(ppRootGraph(m11, r1), toGraph(s11))
  }
  
  /**
   * A case of additional branching within merged branches
   *    1
   *    |
   *    2
   *   / \
   *  3   6
   *  |   | \
   *  4   7  8
   *   \ / 
   *    5   
   */
  @Test
  def softLink3() {
    val m12 = Graph(e(1) ~> e(2), e(2) ~> e(3), e(3) ~> e(4), e(4) ~> e(5), 
                    e(2) ~> e(6), e(6) ~> e(7), e(6) ~> e(8), e(7) ~> e(5))
    val s12 = Seq(List(1,
                       2, Par(Set(Seq(List(6,
                                           Par(Set(8,
                                                   Seq(List(7,
                                                            Id(5))))))),
                                  Seq(List(3,
                                           4,
                                           5))))))

    val p12 = toPProcessTree(m12, r1)
    assertEquals(s12, p12)
    assertGraphEquals(ppRootGraph(m12, r1), toGraph(p12))
  }
  
  /**
   * A case of merges happening in parallel
   *      1
   *      |
   *      2
   *    /   \
   *   3     7
   *  / \   / \
   * 4   5 8   9
   *  \ /   \ /
   *   6     10
   */
  @Test
  def parallelMerged() {
    val m13 = Graph(e(1) ~> e(2),
                    e(2) ~> e(3), e(3) ~> e(4), e(3) ~> e(5), e(4) ~> e(6), e(5) ~> e(6), 
                    e(2) ~> e(7), e(7) ~> e(8), e(7) ~> e(9), e(8) ~> e(10), e(9) ~> e(10))
    val s13 = Seq(List(1,
                       2,
                       Par(Set(Seq(List(7,
                                        Par(Set(9,
                                                8)),
                                        10)),
                               Seq(List(3,
                                        Par(Set(5,
                                                4)),
                                        6))))))

    val p13 = toPProcessTree(m13, r1)
    assertEquals(s13, p13)
    assertGraphEquals(ppRootGraph(m13, r1), toGraph(p13))
  }

  
  // Int + Case Class based testing data structures (to avoid creating EMF ones)
  
  implicit def e(entry: Int): Entry = Entry(entry)
  
  sealed trait PElem
  case class Entry(e: Int) extends PElem
  case class Seq(seq: List[PElem]) extends PElem
  case class Par(par: Set[PElem]) extends PElem
  case class Id(e: Entry) extends PElem

  
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

  object IdCase extends CaseObject[PElem, Id, Entry] {
    def unapply(e: PElem) = e match {
        case Id(entry) => Some(entry)
        case _ => None
      }
    def apply(e: Entry) = Id(e)
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
