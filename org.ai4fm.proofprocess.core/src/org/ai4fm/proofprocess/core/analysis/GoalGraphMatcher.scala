package org.ai4fm.proofprocess.core.analysis

import scalax.collection.GraphEdge.DiEdge
import scalax.collection.GraphPredef._
import scalax.collection.immutable.Graph


/**
 * A matcher to resolve proof graph structure from linear proof steps based on how their goals
 * change.
 * 
 * @author Andrius Velykis
 */
object GoalGraphMatcher {

  type PPGraph[N] = Graph[N, DiEdge]
  type PPGraphRoots[N] = List[N]
  
  /**
   * Encapsulates a goal step: the node contents N, two lists of goals T: 
   * incoming and outgoing (before step, and step results)
   */
  type GoalStep[A, T] = (A, List[T], List[T])
  
  private case class LinkContext[N, T](
      graph: PPGraph[N],
      roots: PPGraphRoots[N],
      branches: List[Branch[N, T]])
  
  private case class Branch[N, T](end: N, outGoals: List[T])
  
  /**
   * Creates a graph of proof nodes (provided by function `node`) from a linear proof step
   * structure (step + in/out goals).
   * 
   * Every proof step can consume some subset of its parent's (or even futher parent's) goals. This
   * is then recorded as a link between the parent step and the proof step. If the parent has more
   * outgoing goals than the child has incoming, we get a parallel split (multiple children for a
   * node). If the child has more incoming goals than the parent has outgoing, we have a merge
   * point: multiple parents to one child.
   * 
   * Note: Need to ensure that goal type T can be matched with ==
   */
  def goalGraph[A, N, T](node: GoalStep[A, T] => N)
                        (proofSteps: List[GoalStep[A, T]])
                        (implicit nodeManifest: Manifest[N]): (PPGraph[N], PPGraphRoots[N]) = {
    
    // now go through the proof steps from the start
    // and link each proof step with its calculated parent (based on their goals) into a graph
    val emptyContext = LinkContext[N, T](Graph(), List(), List())
    val LinkContext(graph, roots, _) = (proofSteps foldLeft emptyContext)(linkStep(node) _)
    
    // reverse the roots, since branches are constructed with prepend
    (graph, roots.reverse)
  }

  private def linkStep[A, N, T](node: GoalStep[A, T] => N)
                               (context: LinkContext[N, T],
                                step: GoalStep[A, T]): LinkContext[N, T] = {
    
    val (nodeInfo, inGoals, outGoals) = step
    
    val (unchanged, changedIn, changedOut) = diffs(inGoals, outGoals)
    
    // create new node with changed in/out goals only, so indicating that proof step affected part
    // of the goal, and the rest may be changed in parallel
    val nodeEntry = node(nodeInfo, changedIn, changedOut)
    
    // add the entry itself to the graph
    val entryGraph = context.graph + nodeEntry
    
    // diff the goals with the branches: find which branches satisfy which goals
    // note: only `changedIn` goals are checked, since only they were actually affected by the step
    val (matchedNodes, remainingGoals, remainingBranches) = 
      branchesWithGoals(context.branches, changedIn)

    // if there are unmatched "in" goals, it means they came from the root goal
    // so mark the node as one of the roots
    val newRoots = prependIfNotEmpty(remainingGoals, context.roots)(_ => nodeEntry)

    // create a new branch for the node with its "out" goals (if there are outstanding goals)
    val newBranches = prependIfNotEmpty(changedOut, remainingBranches)(Branch(nodeEntry, _))
    
    // link matched nodes to the current node in the graph
    val newGraph = (matchedNodes foldLeft entryGraph)( (g, m) => g + (m ~> nodeEntry) )
    
    LinkContext(newGraph, newRoots, newBranches)
  }

  /**
   * Finds matching goals within all open branches.
   * 
   * Continues until all goals have been matched first, and trims the "used" goals from the
   * matched branches.
   * 
   * @return (matchedNodes, remainingGoals, remainingBranches) 
   */
  private def branchesWithGoals[N, T](branches: List[Branch[N, T]],
                                      goals: List[T]): (List[N], List[T], List[Branch[N, T]]) =
    (branches, goals) match {

      // if there are no more ounstanding goals, return the remaining branches
      case (_, Nil) => (Nil, Nil, branches)

      // if there are no more branches, return the outstanding goals
      case (Nil, goals) => (Nil, goals, Nil)

      case (b :: bs, goals) => {
        // check if the branch has any of the goals
        val (matching, remainingBranch, remainingGoals) = diffs(b.outGoals, goals)

        // continue recursively with remaining branches and remaining goals
        val (finalNodes, finalGoals, finalBranches) = branchesWithGoals(bs, remainingGoals)
        
        // if there are matching goals, mark the branch node as matching
        val newNodes = prependIfNotEmpty(matching, finalNodes)(_ => b.end)
        
        // if there are remaining branch goals, keep the branch with them
        val newBranches = prependIfNotEmpty(remainingBranch, finalBranches)(Branch(b.end, _))
        
        (newNodes, finalGoals, newBranches)
      }
    }

  private def diffs[A](l1: List[A], l2: List[A]): (List[A], List[A], List[A]) = {
    val same = l1 intersect l2
    val diffL1 = l1 diff same
    val diffL2 = l2 diff same

    (same, diffL1, diffL2)
  }
  
  private def prependIfNotEmpty[A, B](consider: List[A], main: List[B])(f: List[A] => B): List[B] =
    if (consider.isEmpty) main else f(consider) :: main
  
}
