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

  val r1 = Set(e(1))

  @Test
  def rootInEmptyGraph() {
    val e1 = e(1)
    val convertedTree = toPProcessTree(Graph(), r1)
    assertEquals(e1, convertedTree)
    assertGraphEquals(ppRootGraph(Graph(e1), r1), toGraph(convertedTree))
  }
  
  @Test
  def sequences() {
    
    val g1 = Graph(e(1) ~> e(2))
    val testTree1 = Seq(List(1, 2))
    val convertedTree1 = toPProcessTree(g1, r1)
    assertEquals(testTree1, convertedTree1)
    assertGraphEquals(ppRootGraph(g1, r1), toGraph(convertedTree1))
    
    val g2 = g1 + (e(2) ~> e(3))
    val testTree2 = Seq(List(1, 2, 3))
    val convertedTree2 = toPProcessTree(g2, r1)
    assertEquals(testTree2, convertedTree2)
    assertGraphEquals(ppRootGraph(g2, r1), toGraph(convertedTree2))
  }

  /**
   *   1
   *  / \
   * 2   3
   */
  @Test
  def parallel() {
    
    val g1 = Graph(e(1) ~> e(2), e(1) ~> e(3))
    val testTree = Seq(List(1, 
                      Par(Set(2,
                              3))))
    val convertedTree = toPProcessTree(g1, r1)
    assertEquals(testTree, convertedTree)
    assertGraphEquals(ppRootGraph(g1, r1), toGraph(convertedTree))
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
    val testGraph =
      Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(3) ~> e(4))
    val testTree = 
      Seq(List(1, 
               Par(Set(Seq(List(2,
                                Id(4))), 
                       Seq(List(3,
                                Id(4))))), 
               4))
    val convertedTree = toPProcessTree(testGraph, r1)
    assertEquals(testTree, convertedTree)
    assertGraphEquals(ppRootGraph(testGraph, r1), toGraph(convertedTree))
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
    val testGraph = 
      Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(3) ~> e(4), 
            e(1) ~> e(5), e(5) ~> e(6), e(6) ~> e(7), e(4) ~> e(7))
    val testTree = 
      Seq(List(1,
               Par(Set(Seq(List(Par(Set(Seq(List(2,
                                                 Id(4))),
                                        Seq(List(3,
                                                 Id(4))))),
                                4,
                                Id(7))),
                       Seq(List(5,
                                6,
                                Id(7))))),
               7))
    val convertedTree = toPProcessTree(testGraph, r1)
    assertEquals(testTree, convertedTree)
    assertGraphEquals(ppRootGraph(testGraph, r1), toGraph(convertedTree))
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
    val testGraph =
      Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(3) ~> e(4),
            e(1) ~> e(5), e(5) ~> e(6), e(6) ~> e(7), e(4) ~> e(7),
            e(3) ~> e(6))
    val testTree =
      Seq(List(1,
               Par(Set(Seq(List(Par(Set(Seq(List(2,
                                                 Id(4))),
                                        Seq(List(3,
                                                 Par(Set(Id(4),
                                                         Id(6))))),
                                        Seq(List(5,
                                                 Id(6))))),
                                Par(Set(Seq(List(4,
                                                 Id(7))),
                                        Seq(List(6,
                                                 Id(7))))))))),
               7))
    val convertedTree = toPProcessTree(testGraph, r1)
    assertEquals(testTree, convertedTree)
    assertGraphEquals(ppRootGraph(testGraph, r1), toGraph(convertedTree))
  }
  
  
  @Test
  def lowRootMerge() {
    // note no root element for multiple roots: starts with a parallel
    val testGraph =
      Graph(e(1) ~> e(2), e(2) ~> e(3))
    val testTree =
      Seq(List(Par(Set(Seq(List(1,
                                2,
                                Id(3))),
                       Id(3))),
               3))
    val rDouble = Set(e(1), e(3))
    val convertedTree = toPProcessTree(testGraph, rDouble)
    assertEquals(testTree, convertedTree)
    assertGraphEquals(ppRootGraph(testGraph, rDouble), toGraph(convertedTree))
  }

  /**
   * One of the branches does not have any steps in it (1 -> 3).
   *   1
   *  / \
   *  2  |
   *  \ /
   *   3
   *
   * This is a similar example to `testGraph`, but with a single explicit root.
   */
  @Test
  def directMerge() {
    val testGraph =
      Graph(e(1) ~> e(2), e(2) ~> e(3), e(1) ~> e(3))
    val testTree =
      Seq(List(1,
               Par(Set(Seq(List(2,
                                Id(3))),
                       Id(3))),
               3))
    val convertedTree = toPProcessTree(testGraph, r1)
    assertEquals(testTree, convertedTree)
    assertGraphEquals(ppRootGraph(testGraph, r1), toGraph(convertedTree))
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
   * This is the same example as `testGraph`, but with a single explicit root
   */
  @Test
  def directMerge2() {
    val testGraph =
      Graph(e(1) ~> e(2), e(2) ~> e(3), e(1) ~> e(3),
            e(3) ~> e(4), e(1) ~> e(4))
    val testTree =
      Seq(List(1,
               Par(Set(Seq(List(Par(Set(Seq(List(2,
                                                 Id(3))),
                                        Id(3))),
                                3,
                                Id(4))),
                       Id(4))),
               4))
    val convertedTree = toPProcessTree(testGraph, r1)
    assertEquals(testTree, convertedTree)
    // note that both links 1->3 and 1->4 get dropped when converting to graph
    assertGraphEquals(ppRootGraph(testGraph, r1), toGraph(convertedTree))
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
    val testGraph =
      Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(2) ~> e(5), 
            e(3) ~> e(4), e(4) ~> e(5))
    val testTree =
      Seq(List(1,
               Par(Set(Seq(List(Par(Set(Seq(List(2,
                                                 Par(Set(Id(4),
                                                         Id(5))))),
                                        Seq(List(3,
                                                 Id(4))))),
                                4,
                                Id(5))))),
               5))

    val convertedTree = toPProcessTree(testGraph, r1)
    assertEquals(testTree, convertedTree)
    assertGraphEquals(ppRootGraph(testGraph, r1), toGraph(convertedTree))
  }

  
  /**
   * A further complex triple-merge extending testGraph:
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
    val testGraph =
      Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(2) ~> e(5), 
            e(3) ~> e(4), e(4) ~> e(5), e(3) ~> e(6), e(5) ~> e(6))
    val testTree =
      Seq(List(1,
               Par(Set(Seq(List(Par(Set(Seq(List(Par(Set(Seq(List(2,
                                                                  Par(Set(Id(4),
                                                                          Id(5))))),
                                                         Seq(List(3,
                                                                  Par(Set(Id(4),
                                                                          Id(6))))))),
                                                 4,
                                                 Id(5))))),
                                5,
                                Id(6))))),
               6))

    val convertedTree = toPProcessTree(testGraph, r1)
    assertEquals(testTree, convertedTree)
    assertGraphEquals(ppRootGraph(testGraph, r1), toGraph(convertedTree))
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
   */
  @Test
  def softLink() {
    val testGraph =
      Graph(e(1) ~> e(2), e(2) ~> e(3), e(3) ~> e(4), e(4) ~> e(5), 
            e(3) ~> e(6), e(6) ~> e(7), e(2) ~> e(7))
    val testTree =
      Seq(List(1,
               2,
               Par(Set(Seq(List(3,
                                Par(Set(Seq(List(4,
                                                 5)),
                                        Seq(List(6,
                                                 Id(7))))))),
                       Id(7))),
               7))

    val convertedTree = toPProcessTree(testGraph, r1)
    assertEquals(testTree, convertedTree)
    assertGraphEquals(ppRootGraph(testGraph, r1), toGraph(convertedTree))
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
    val testGraph =
      Graph(e(1) ~> e(2), e(2) ~> e(3), e(3) ~> e(4), e(4) ~> e(5), 
            e(2) ~> e(6), e(6) ~> e(5), e(6) ~> e(7))
    val testTree =
      Seq(List(1,
               2,
               Par(Set(Seq(List(3,
                                4,
                                Id(5))),
                       Seq(List(6,
                                Par(Set(Id(5),
                                        7)))))),
               5))

    val convertedTree = toPProcessTree(testGraph, r1)
    assertEquals(testTree, convertedTree)
    assertGraphEquals(ppRootGraph(testGraph, r1), toGraph(convertedTree))
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
    val testGraph =
      Graph(e(1) ~> e(2), e(1) ~> e(3), e(2) ~> e(4), e(2) ~> e(5),
            e(3) ~> e(4), e(3) ~> e(5), e(4) ~> e(6), e(5) ~> e(6))
    val testTree =
      Seq(List(1,
               Par(Set(Seq(List(Par(Set(Seq(List(2,
                                                 Par(Set(Id(4),
                                                         Id(5))))),
                                        Seq(List(3,
                                                 Par(Set(Id(4),
                                                         Id(5))))))),
                                Par(Set(Seq(List(4,
                                                 Id(6))),
                                        Seq(List(5,
                                                 Id(6))))))))),
               6))

    val convertedTree = toPProcessTree(testGraph, r1)
    assertEquals(testTree, convertedTree)
    assertGraphEquals(ppRootGraph(testGraph, r1), toGraph(convertedTree))
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
    val testGraph =
      Graph(e(1) ~> e(2), e(2) ~> e(3), e(3) ~> e(4), e(4) ~> e(5), 
            e(2) ~> e(6), e(6) ~> e(7), e(6) ~> e(8), e(7) ~> e(5))
    val testTree =
      Seq(List(1,
               2,
               Par(Set(Seq(List(3,
                                4,
                                Id(5))),
                       Seq(List(6,
                                Par(Set(Seq(List(7,
                                                 Id(5))),
                                        8)))))),
               5))

    val convertedTree = toPProcessTree(testGraph, r1)
    assertEquals(testTree, convertedTree)
    assertGraphEquals(ppRootGraph(testGraph, r1), toGraph(convertedTree))
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
    val testGraph =
      Graph(e(1) ~> e(2),
            e(2) ~> e(3), e(3) ~> e(4), e(3) ~> e(5), e(4) ~> e(6), e(5) ~> e(6), 
            e(2) ~> e(7), e(7) ~> e(8), e(7) ~> e(9), e(8) ~> e(10), e(9) ~> e(10))
    val testTree =
      Seq(List(1,
               2,
               Par(Set(Seq(List(3,
                                Par(Set(Seq(List(4,
                                                 Id(6))),
                                        Seq(List(5,
                                                 Id(6))))),
                                6)),
                       Seq(List(7,
                                Par(Set(Seq(List(8,
                                                 Id(10))),
                                        Seq(List(9,
                                                 Id(10))))),
                                10))))))


    val convertedTree = toPProcessTree(testGraph, r1)
    assertEquals(testTree, convertedTree)
    assertGraphEquals(ppRootGraph(testGraph, r1), toGraph(convertedTree))
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
//      (shapes zip pelems).forall(Function.tupled(isShape))
//      
  
}
