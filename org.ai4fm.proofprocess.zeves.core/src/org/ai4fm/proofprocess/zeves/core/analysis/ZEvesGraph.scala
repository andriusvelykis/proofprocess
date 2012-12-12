package org.ai4fm.proofprocess.zeves.core.analysis

import scalax.collection.GraphPredef._
import scalax.collection.immutable.Graph

import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.core.graph.PProcessGraph._
import org.ai4fm.proofprocess.zeves.ZEvesTrace
import org.ai4fm.proofprocess.zeves.core.parse.ProofEntryReader


/** Retrieves the proof structure from a list of proof steps: creates a graph based on
  * the proof case of each step.
  *
  * @author Andrius Velykis
  */
object ZEvesGraph {

  private type CaseRoots = List[(ProofEntry, List[Int])]
  
  def proofStepsGraph(proofSteps: List[ProofEntry]): PPRootGraph[ProofEntry] = {  

    // go backwards through the steps and build up the graph
    val (graph, caseRoots) = proofSteps.foldRight(Graph(): PPGraph[ProofEntry], List(): CaseRoots) {
      case (entry, (graph, roots)) => handleProofEntry(entry, graph, roots)
    }
    
    // drop the cases from the roots now
    PPRootGraph(graph, caseRoots.map(_._1))
  }

  private def handleProofEntry(entry: ProofEntry,
                               graph: PPGraph[ProofEntry],
                               branchRoots: CaseRoots): (PPGraph[ProofEntry], CaseRoots) = {

    val entryCase = entryProofCase(entry)
    
    // add the entry to the graph (e.g. if this is a rogue entry, or a single entry, etc.)
    val graphWithEntry = graph + entry
    
    // partition branch roots according to their relationship with the entry case
    val rootTypes = partitionRootTypes(entryCase, branchRoots)
    
    // if there are split roots, drop the same roots (since they would represent the merge points)
    // otherwise link to same roots if they exist
    val splitGraph = if (!rootTypes.splitRoots.isEmpty) {
      addGraphLink(graphWithEntry, entry, rootTypes.splitRoots)
    } else if (!rootTypes.sameRoots.isEmpty) {
      addGraphLink(graphWithEntry, entry, rootTypes.sameRoots)
    } else {
      // link to merge roots
      // we cannot link to merge roots when we have `sameRoots`, since the merge roots would be
      // already linked by the children of `sameRoots`
      addGraphLink(graphWithEntry, entry, rootTypes.mergeRoots)
    }
    
    // for new roots, drop the splits and the same roots,
    // but keep the merge (for parallel merges) and other roots,
    // plus add the entry itself as a root
    // (use diff to preserve the order)
    val newRoots = (entry, entryCase) :: branchRoots.diff(rootTypes.splitRoots).diff(rootTypes.sameRoots)
    
    (splitGraph, newRoots)
  }
  
  private def entryProofCase(entry: ProofEntry): List[Int] = {
    
    entry.getProofStep.getTrace match {
      
      case zevesTrace: ZEvesTrace => Option(zevesTrace.getCase) match {
        
        case Some(caseStr) => ProofEntryReader.proofCase(caseStr)
        
        // proof case not set, assume no proof case
        case None => List()
      }
        
      // unknown trace - cannot get the proof case
      case _ => List()
    }
  }

  
  /** Partitions branch roots according to their relationship with the entry case
    * - a split (e.g. entry "2.1", branch "2.1.1")
    * - same (e.g. entry "2.1", branch "2.1")
    * - merge (e.g. entry "2.1.1", branch "2.1")
    * - other (e.g. entry "2.1.1", branch "2.2.4")
    */
  private def partitionRootTypes(entryCase: List[Int], branchRoots: CaseRoots): RootTypes = {

    branchRoots.foldRight(RootTypes(Nil, Nil, Nil, Nil)) {
      case (branch @ (root, rootCase), rootTypes) => {

        // check if the root case starts with the entry case
        // e.g. if the branch case is "2.1.3", then it would match the entry case "2.1"
        val startsTail = startsWithTail(rootCase, entryCase)

        startsTail match {

          case Some(Nil) => rootTypes.copy(sameRoots = branch :: rootTypes.sameRoots)

          case Some(list) => rootTypes.copy(splitRoots = branch :: rootTypes.splitRoots)

          case None => {
            // the entry case is not a parent/same as the branch root case
            // try the other way around - check if the entry case starts with the root case
            // (merging), e.g. "2.1.3" is closed/merged back into "2.1"
            val mergeTail = startsWithTail(entryCase, rootCase)
            mergeTail match {

              case Some(list) => rootTypes.copy(mergeRoots = branch :: rootTypes.mergeRoots)

              // does not match at all, keep it as "other"
              case None => rootTypes.copy(otherRoots = branch :: rootTypes.otherRoots)
            }
          }
        }
      }
    }
  }

  private case class RootTypes(splitRoots: CaseRoots,
                               sameRoots: CaseRoots,
                               mergeRoots: CaseRoots,
                               otherRoots: CaseRoots)
    

  private def startsWithTail[E](source: List[E], target: List[E]): Option[List[E]] =
    (source, target) match {

      // target empty, so the source starts with the target, return the source
      case (list, Nil) => Some(list)

      // matching first elements, continue recursively
      case (s :: ss, t :: ts) if s == t => startsWithTail(ss, ts)

      // if the first elements don't match, we return None to indicate that
      // source list does not start with target list
      case _ => None
    }

  private def addGraphLink(graph: PPGraph[ProofEntry],
                           from: ProofEntry,
                           to: CaseRoots): PPGraph[ProofEntry] =
    to.foldRight(graph)((t, g) => (g + (from ~> t._1)))
  
  
}
