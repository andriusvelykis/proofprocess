package org.ai4fm.graph.isomorphism

import org.junit.Assert._
import org.junit.Test
import scalax.collection.GraphEdge.DiEdge
import scalax.collection.GraphPredef._
import scalax.collection.immutable.Graph
import scala.util.Random


/**
  * @author Andrius Velykis 
  */
class VF2IsomorphismTest {
  
  @Test
  def graphItself1() {
    
    val graph1 = Graph(1 ~> 2, 2 ~> 3, 2 ~> 4)
    val isom = VF2Isomorphism(graph1, graph1).matchDefault()
    assertTrue(isom.isIsomorphism)
    assertEquals(2, isom.isomorphisms.size)
  }
  
  @Test
  def graphItselfUndirected1() {
    
    val graph1 = Graph(1 ~ 2, 2 ~ 3, 2 ~ 4)
    val isom = VF2Isomorphism(graph1, graph1).matchDefault()
    assertTrue(isom.isIsomorphism)
    // 6 isomorphisms for undirected, since we get a star-like graph
    assertEquals(6, isom.isomorphisms.size)
  }
  
  @Test
  def disconnectedItself1() {
    
    val graph1 = Graph(1 ~> 2, 2 ~> 3, 2 ~> 4, 5 ~> 6)
    val isom = VF2Isomorphism(graph1, graph1).matchDefault()
    assertTrue(isom.isIsomorphism)
    assertEquals(2, isom.isomorphisms.size)
  }
  
  @Test
  def cycleItself1() {
    
    val graph1 = Graph(1 ~> 2, 2 ~> 3, 3 ~> 1)
    val isom = VF2Isomorphism(graph1, graph1).matchDefault()
    assertTrue(isom.isIsomorphism)
    assertEquals(3, isom.isomorphisms.size)
  }
  
  @Test
  def subgraph1() {
    
    val graph1 = Graph(1 ~> 2, 2 ~> 3, 2 ~> 4, 4 ~> 5, 3 ~> 5)
    val subgraph1 = Graph("A" ~> "B", "B" ~> "D", "A" ~> "C", "C" ~> "D")
    val isom = VF2Isomorphism(graph1, subgraph1).matchDefault()
    assertTrue(isom.isIsomorphism)
    assertEquals(2, isom.isomorphisms.size)
  }
  
  @Test
  def randomGraphItself() {
    
    val graph1 = ((1 to 100) foldLeft Graph(0 ~> 1)) {
      (g, _) => {
        g + (Random.nextInt(100) ~> Random.nextInt(100)) 
      }
    }
    
    val isom = VF2Isomorphism(graph1, graph1).matchDefault()
    assertTrue(isom.isIsomorphism)
  }
  
  @Test
  def dead() {
    
    // subgraph is larger than graph - should not even try calculating
    val graph1 = Graph(1 ~> 2, 2 ~> 3, 2 ~> 4)
    val subgraph1 = Graph("A" ~> "B", "B" ~> "D", "A" ~> "C", "C" ~> "D", "D" ~> "E")
    val isom = VF2Isomorphism(graph1, subgraph1).matchDefault()
    assertFalse(isom.isIsomorphism)
    assertEquals(0, isom.mappings.size)
  }
  
  @Test
  def fromRootItself1() {
    
    val graph1 = Graph(1 ~> 2, 2 ~> 3, 2 ~> 4)
    val isom = VF2Isomorphism(graph1, graph1).matchFrom(1)
    assertTrue(isom.isIsomorphism)
    assertEquals(2, isom.isomorphisms.size)
    // finds all mappings quicker
    assertEquals(8, isom.mappings.size)
  }
  
  
}
