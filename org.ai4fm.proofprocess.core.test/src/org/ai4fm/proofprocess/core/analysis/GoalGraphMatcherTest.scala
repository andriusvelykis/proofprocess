package org.ai4fm.proofprocess.core.analysis

import org.junit.Assert._
import org.junit.Test
import scalax.collection.immutable.Graph
import scalax.collection.GraphPredef._
import scalax.collection.GraphEdge._
import org.ai4fm.proofprocess.core.graph.PProcessGraph._
import org.ai4fm.proofprocess.core.util.PProcessUtil


/**
  * @author Andrius Velykis 
  */
class GoalGraphMatcherTest {

  def goalGraph = GoalGraphMatcher.goalGraph[String, GoalStep, Int](Function.tupled(GoalStep)) _
  
  def inOutGoals = PProcessUtil.toInOutGoalSteps((a: String, i: List[Int], o: List[Int]) => (a, i, o)) _


  private def assertGraphEquals[E](rg1: PPRootGraph[E, _], rg2: PPRootGraph[E, _]) = {
    val PPRootGraph(g1, r1, _) = rg1
    val PPRootGraph(g2, r2, _) = rg2

    assertEquals(g1, g2)
    assertEquals(r1, r2)
  }

  private def ppRootGraph[E](graph: PPGraph[E], roots: PPGraphRoots[E]): PPRootGraph[E, _] =
    PPRootGraph(graph, roots, Map())


  @Test
  def singleStep() {

    val s1 = inOutGoals(List(1), List(
      ("A", List())
    ))
    
    val g1: PPGraph[GoalStep] = Graph(GoalStep("A", List(1), List()))
    val r1 = Set(GoalStep("A", List(1), List()))
    
    assertGraphEquals(ppRootGraph(g1, r1), goalGraph(s1))
  }
  
  /**
   * Goal is transformed in sequence (two steps): G1 to G2 to "true"
   */
  @Test
  def seqStep() {

    val s1 = inOutGoals(List(1), List(
      ("A", List(2)),
      ("B", List())
    ))
    
    val g1 = Graph(GoalStep("A", List(1), List(2)) ~> GoalStep("B", List(2), List()))
    val r1 = Set(GoalStep("A", List(1), List(2)))
    
    assertGraphEquals(ppRootGraph(g1, r1), goalGraph(s1))
  }
  
  /**
   * Goal is transformed in two root parallel steps: G1 to "true" and then G2 to "true"
   */
  @Test
  def rootParallel() {

    val s1 = inOutGoals(List(1, 2), List(
      ("A", List(2)),
      ("B", List())
    ))
    
    // no links in the graph, both steps are root ones
    val g1: PPGraph[GoalStep] = Graph(GoalStep("A", List(1), List()), GoalStep("B", List(2), List()))
    val r1 = Set(GoalStep("A", List(1), List()), GoalStep("B", List(2), List()))
    
    assertGraphEquals(ppRootGraph(g1, r1), goalGraph(s1))
  }
  
  /**
   * Goal is split into two, which are then transformed in parallel
   */
  @Test
  def entryParallel() {
    
    val s1 = inOutGoals(List(1), List(
      ("A", List(2, 3)),
      ("B", List(3)),
      ("C", List())
    ))
    
    val tA = GoalStep("A", List(1), List(2, 3))
    val g1 = Graph(tA ~> GoalStep("B", List(2), List()), tA ~> GoalStep("C", List(3), List()))
    val r1 = Set(tA)
    
    assertGraphEquals(ppRootGraph(g1, r1), goalGraph(s1))
  }
  
  /**
   * Goal is split into two, which are then transformed in parallel in 2 steps each
   */
  @Test
  def entryParallel2() {
    
    val s1 = inOutGoals(List(1), List(
      ("A", List(2, 3)),
      ("B", List(4, 3)),
      ("C", List(3)),
      ("D", List(5)),
      ("E", List())
    ))
    
    val tA = GoalStep("A", List(1), List(2, 3))
    val tB = GoalStep("B", List(2), List(4))
    val tD = GoalStep("D", List(3), List(5))
    
    val g1 = Graph(tA ~> tB, tB ~> GoalStep("C", List(4), List()), tA ~> tD, tD ~> GoalStep("E", List(5), List()))
    val r1 = Set(tA)
    
    assertGraphEquals(ppRootGraph(g1, r1), goalGraph(s1))
    
    // also check that the goal order does not matter (e.g. in "B")
    val s2 = inOutGoals(List(1), List(
      ("A", List(2, 3)),
      ("B", List(3, 4)),
      ("C", List(3)),
      ("D", List(5)),
      ("E", List())
    ))
    
    assertGraphEquals(ppRootGraph(g1, r1), goalGraph(s2))
  }
  
 /**
   * Goal is split into two, which are then transformed in parallel and then merged
   */
  @Test
  def entryParallelMerge() {
    
    val s1 = inOutGoals(List(1), List(
      ("A", List(2, 3)),
      ("B", List(4, 3)),
      ("C", List(4, 5)),
      ("D", List())
    ))
    
    val tA = GoalStep("A", List(1), List(2, 3))
    val tB = GoalStep("B", List(2), List(4))
    val tC = GoalStep("C", List(3), List(5))
    val tD = GoalStep("D", List(4, 5), List())
    
    val g1 = Graph(tA ~> tB, tB ~> tD, tA ~> tC, tC ~> tD)
    val r1 = Set(tA)
    
    assertGraphEquals(ppRootGraph(g1, r1), goalGraph(s1))
  }
  
  /**
   * A new goal is produced in the middle of the proof: this is a merge from the root
   */
  @Test
  def newGoalInTheMiddle() {
    
    val s1 = inOutGoals(List(1), List(
      ("A", List(2)),
      ("B", List(2, 3)),
      ("C", List())
    ))
    
    val tA = GoalStep("A", List(1), List(2))
    // note that step B does not have an "in" goals: it just produces a new goal
    val tB = GoalStep("B", List(), List(3))
    val tC = GoalStep("C", List(2, 3), List())
    
    // this is a merge from the root
    val g1 = Graph(tA ~> tC, tB ~> tC)
    val r1 = Set(tA, tB)
    
    assertGraphEquals(ppRootGraph(g1, r1), goalGraph(s1))
  }
  
  /**
   * A step that does not change any goals is taken as the first step in proof
   */
  @Test
  def unchangedFirstParallel() {
    
    // parallel
    val s1 = inOutGoals(List(1, 2), List(
      ("A", List(2, 1)),
      ("B", List(1)),
      ("C", List())
    ))
    
    // step A is an empty step
    val tA = GoalStep("A", List(), List())
    val tB = GoalStep("B", List(2), List())
    val tC = GoalStep("C", List(1), List())
    
    // this is a merge from the root with B being accessible in 2 ways: 
    // directly from top and from A
    val g1 = Graph(tA ~> tB, tC)
    val r1 = Set(tA, tB, tC)
    
    assertGraphEquals(ppRootGraph(g1, r1), goalGraph(s1))
  }
  
  /**
   * A step that does not change any goals is taken as the first step in proof
   */
  @Test
  def unchangedFirstSeq() {
    
    // sequential
    val s1 = inOutGoals(List(1), List(
      ("A", List(1)),
      ("B", List())
    ))
    
    // step A is an empty step
    val tA = GoalStep("A", List(), List())
    val tB = GoalStep("B", List(1), List())
    
    // this is a merge from the root (B is accessible from the root and from A)
    val g1 = Graph(tA ~> tB)
    val r1 = Set(tA, tB)
    
    assertGraphEquals(ppRootGraph(g1, r1), goalGraph(s1))
  }
  
  /**
   * A step that does not change any goals is taken as the first step in proof
   */
  @Test
  def unchangedFirstMerge() {
    
    // merge
    val s1 = inOutGoals(List(1, 2), List(
      ("A", List(2, 1)),
      ("B", List(1, 3)),
      ("C", List(4, 3)),
      ("D", List())
    ))
    
    // step A is an empty step
    val tA = GoalStep("A", List(), List())
    val tB = GoalStep("B", List(2), List(3))
    val tC = GoalStep("C", List(1), List(4))
    val tD = GoalStep("D", List(4, 3), List())
    
    // this is a merge from the root (B is accessible from the root and from A)
    val g1 = Graph(tA ~> tB, tB ~> tD, tC ~> tD)
    val r1 = Set(tA, tB, tC)
    
    assertGraphEquals(ppRootGraph(g1, r1), goalGraph(s1))
  }
  
  /**
   * A step that does not change any goals is in the middle of the proof
   */
  @Test
  def unchangedMiddleSeq() {
    
    val s1 = inOutGoals(List(1), List(
      ("A", List(2)),
      ("B", List(2)),
      ("C", List())
    ))
    
    val tA = GoalStep("A", List(1), List(2))
    // step B is an empty step
    val tB = GoalStep("B", List(), List())
    val tC = GoalStep("C", List(2), List())
    
    // note that B is "on a side", since A links to C
    val g1 = Graph(tA ~> tB, tB ~> tC, tA ~> tC)
    val r1 = Set(tA)
    
    assertGraphEquals(ppRootGraph(g1, r1), goalGraph(s1))
  }
  
  /**
   * A step that affects goals in different branches (but not all goals)
   */
  @Test
  def partialMerge() {
    
    val s1 = inOutGoals(List(1), List(
      ("A", List(2, 3, 4, 5)),
      ("B", List(6, 4, 5, 7)),
      ("C", List(8, 9, 7, 6)),
      ("D", List(6, 8, 10)),
      ("E", List(8, 11)),
      ("F", List())
    ))
    
    val tA = GoalStep("A", List(1), List(2, 3, 4, 5))
    val tB = GoalStep("B", List(2, 3), List(6, 7))
    val tC = GoalStep("C", List(4, 5), List(8, 9))
    val tD = GoalStep("D", List(9, 7), List(10))
    val tE = GoalStep("E", List(6, 10), List(11))
    val tF = GoalStep("F", List(8, 11), List())
    
    // note the complex merge patterns: this cannot actually be represented as PProcessTree
    val g1 = Graph(tA ~> tB, tA ~> tC, tB ~> tD, tB ~> tE, tC ~> tD, tC ~> tF, tD ~> tE, tE ~> tF)
    val r1 = Set(tA)
    
    assertGraphEquals(ppRootGraph(g1, r1), goalGraph(s1))
  }
  
  // Int + Case Class based testing data structures (to avoid creating EMF ones)
  
  case class GoalStep(step: String, in: List[Int], out: List[Int]) {
    override def toString = step
  }
  
}
