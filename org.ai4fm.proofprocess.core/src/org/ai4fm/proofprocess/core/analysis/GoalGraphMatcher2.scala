package org.ai4fm.proofprocess.core.analysis

import scalax.collection.GraphPredef._
import scalax.collection.immutable.Graph
import org.ai4fm.proofprocess.core.graph.PProcessGraph._
import scalax.collection.GraphEdge.DiEdge



/**
 * A matcher that creates a proof graph out of given proof steps. Aims to match
 * goals and assumptions consumed by the proof step with where they originated.
 * 
 * @author Andrius Velykis
 */
object GoalGraphMatcher2 {
  
  private case class LinkContext[N, T <: Eq](
      graph: PPGraph[N],
      props: Map[Proposition[T], N])


  def goalGraph[N, T <: Eq](proofSteps: List[GoalStep[N, T]])
                           (implicit nodeManifest: Manifest[N]): PPRootGraph[N] =
    goalGraph(proofSteps, Graph())


  // cannot set initial to default being empty graph, because the manifest is somehow not available
  def goalGraph[N, T <: Eq](proofSteps: List[GoalStep[N, T]], initial: PPGraph[N])
                           (implicit nodeManifest: Manifest[N]): PPRootGraph[N] = {
    
    // now go through the proof steps from the start
    // and link each proof step with its calculated parent
    // (based on their required assumptions or goals) into a graph
    val emptyContext = LinkContext[N, T](initial, Map())
    val LinkContext(graph, _) = (proofSteps foldLeft emptyContext)(linkStep)
    
    // as roots, just take everything without any `in` edges
    val roots = (graph.nodes filter (_.inDegree == 0) map (_.value)).toList
    
    PPRootGraph(graph, roots)
  }

  
  private def linkStep[N, T <: Eq](context: LinkContext[N, T],
                                   step: GoalStep[N, T]): LinkContext[N, T] = {
    
    val stepNode = step.info
    
    // add the entry itself to the graph
    val entryGraph = context.graph + stepNode
    
    // for each `in` proposition, locate in the context where it was introduced
    // and then link from that node
    val inLinks = step.in flatMap ( prop => context.props.get(prop) )
    val inEdges = inLinks map ( node => (node ~> stepNode) )
    val inGraph = entryGraph ++ inEdges
    
    // Note: do not consume the proposition, since assumptions can be reused and
    // we do not care that much if everything is consumed in this mapping
    
    // now add produced propositions to the map
    // they will point to the current step
    val outProps = context.props ++ (step.out map ( prop => (prop -> stepNode) ) )
    
    LinkContext(inGraph, outProps)
  }

}
