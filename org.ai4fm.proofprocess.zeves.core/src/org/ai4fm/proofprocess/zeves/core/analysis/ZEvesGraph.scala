package org.ai4fm.proofprocess.zeves.core.analysis

import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.zeves.ZEvesTrace
import org.ai4fm.proofprocess.zeves.core.parse.ProofEntryReader

import net.sourceforge.czt.eclipse.zeves.ui.core.ZEvesSnapshot.ISnapshotEntry
import scalax.collection.GraphEdge._
import scalax.collection.GraphPredef._
import scalax.collection.immutable.Graph


/** Retrieves the proof structure from a list of proof steps: creates a graph based on
  * the proof case of each step.
  *
  * @author Andrius Velykis
  */
object ZEvesGraph {

  def proofStepEntries(entry: (ISnapshotEntry, List[Term], List[Term]) => ProofEntry)
                      (proofSteps: List[(ISnapshotEntry, List[Term])],
                       initialGoals: List[Term]): List[ProofEntry] = {

    // make a list with ingoals-info-outgoals steps
    val inGoals = initialGoals :: proofSteps.map(_._2)
    val inOutSteps = inGoals.zip(proofSteps)

    inOutSteps.map { case (inGoals, (info, outGoals)) => entry(info, inGoals, outGoals) }
  }
  
  type PPGraph = Graph[ProofEntry, DiEdge]
  type PPGraphRoots = List[ProofEntry]
  private type CaseRoots = List[(ProofEntry, List[Int])]
  
  def proofStepsGraph(proofSteps: List[ProofEntry]): (PPGraph, PPGraphRoots) = {  

    // go backwards through the steps and build up the graph
    val (graph, caseRoots) = proofSteps.foldRight(Graph(): PPGraph, List(): CaseRoots) {
      case (entry, (graph, roots)) => handleProofEntry(entry, graph, roots)
    }
    
    // drop the cases from the roots now
    (graph, caseRoots.map(_._1))
  }

  private def handleProofEntry(entry: ProofEntry,
                               graph: PPGraph,
                               branchRoots: CaseRoots): (PPGraph, CaseRoots) = {

    val entryCase = entryProofCase(entry)
    
    // partition branch roots according to their relationship with the entry case
    val rootTypes = partitionRootTypes(entryCase, branchRoots)
    
    // if there are split roots, drop the same roots (since they would represent the merge points)
    // otherwise link to same roots if they exist
    val splitGraph = if (!rootTypes.splitRoots.isEmpty) {
      addGraphLink(graph, entry, rootTypes.splitRoots)
    } else {
      addGraphLink(graph, entry, rootTypes.sameRoots)
    }
    
    // link to merge roots
    val mergeGraph = addGraphLink(graph, entry, rootTypes.mergeRoots)
    
    // for new roots, drop the splits and the same roots,
    // but keep the merge (for parallel merges) and other roots,
    // plus add the entry itself as a root
    // (use diff to preserve the order)
    val newRoots = (entry, entryCase) :: branchRoots.diff(rootTypes.splitRoots).diff(rootTypes.sameRoots)
    
    (mergeGraph, newRoots)
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
  
  private def addGraphLink(graph: PPGraph, from: ProofEntry, to: CaseRoots): PPGraph =
    to.foldRight(graph)( (t, g) => (g + (from ~> t._1)) )
  
  
}
