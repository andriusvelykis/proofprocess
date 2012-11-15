package org.ai4fm.proofprocess.core.graph

import scala.collection.JavaConversions._

import scalax.collection.GraphEdge._
import scalax.collection.GraphPredef._
import scalax.collection.GraphTraversal.VisitorReturn._
import scalax.collection.immutable.Graph


/** Utility functions to convert between Scala Graph and ProofProcess tree structure.
  * 
  * @author Andrius Velykis
  */
object PProcessGraph {

  /** Converts ProofProcess graph represented as tree with the given root element to
    * a Scala DAG of just the ProofEntry elements, and the list of root entries.
    *
    * @return  `(graph, roots)` the pair of created graph and the list of roots to traverse it
    */
  def graph[Elem, Entry <: Elem, Seq <: Elem, Parallel <: Elem, Decor <: Elem]
      (ppTree: PProcessTree[Elem, Entry, Seq, Parallel, Decor, _],
        // FIXME a workaround for type issues
        empty: => scalax.collection.immutable.Graph[Entry, DiEdge])
      (rootElem: Elem): (Graph[Entry, DiEdge], List[Entry]) = {
    
    type PPGraph = Graph[Entry, DiEdge]
    type PPGraphRoots = List[Entry]
    
    val emptyGraph = (empty, List(): PPGraphRoots)

    // a method that collects the graph with an accumulator (necessary for Seq implementation)
    def graph0(rootElem: Elem, acc: (PPGraph, PPGraphRoots)): (PPGraph, PPGraphRoots) = rootElem match {

      // for proof entry, add it to the graph and connect to all outstanding roots
      // (this means that this entry step is followed by all outstanding entry steps)
      // 
      // normally there should be only a single outstanding root here (or none), but multiple can
      // happen, e.g., when we have 2 parallels in a row in a sequence: Seq(Entry, Parallel, Parallel, Entry).
      // This way there should be a merge between parallels, but it is not represented as entry

      // see the double-match here, since the extractors cannot set the subtype correctly,
      // we perform this pattern matching, then casting
      case entry @ ppTree.entry(_) => entry match {
        case entry: Entry => {
          val (accGraph, accRoots) = acc

          // add the entry to the graph
          val withEntryEdges = accRoots.foldLeft(accGraph + entry) {
            // for each root, add an edge from entry to root
            (accGraph, root) => accGraph + (entry ~> root)
          }

          // only the entry is a root now
          (withEntryEdges, List(entry))
        }
      }

      // for decorator, just extract the underlying entry
      case ppTree.decor(entry) => graph0(entry, acc)

      // for parallel, create subgraphs of each parallel entry,
      // and then merge these subgraphs and their roots
      case ppTree.parallel(elems) => {

        // create subgraph based on the accumulated graph
        val subGraphs = elems map (e => graph0(e, acc))

        val merged = subGraphs.foldRight(emptyGraph) {
          case ((subGraph, subRoots), (foldGraph, foldRoots)) => (subGraph ++ foldGraph, subRoots ++ foldRoots)
        }

        merged
      }

      // for sequences, just accumulate subgraph from the end, this way elements at the
      // start are parents/roots to the subsequent elements
      case ppTree.seq(elems) => elems.foldRight(acc) {
        (entry, acc) => graph0(entry, acc)
      }
    }
    
    // invoke with empty accumulator and the given root, it will create the graph recursively
    graph0(rootElem, emptyGraph)
  }
  
  def proofProcessTree[Elem, Entry <: Elem, Seq <: Elem, Parallel <: Elem]
      (ppTree: PProcessTree[Elem, Entry, Seq, Parallel, _, _], root: => Entry)
      (graph: Graph[Entry, DiEdge], roots: List[Entry]): Elem = {
    
    sealed trait Branch
    case class Root(entry: Entry) extends Branch
    case class Merge(roots: List[Branch]) extends Branch
    
    type MergeMap = Map[Entry, List[Entry]]

    assume(!roots.isEmpty)
    
    // add the roots to the graph, thus ensuring that the traversal is possible
    val graphWithRoots = roots.foldLeft(graph)(_ + _)
    
    val (singleRoot, rootGraph) = roots match {
      // already single root
      case List(root) => (root, graphWithRoots)
      // for multiple roots, create a special new root element that will be replaced
      // by a ProofParallel after constructions. This way we indicate that the proof
      // is parallel from the start
      case roots => {
        val newRoot = root
        val newGraph = roots.foldRight(graphWithRoots)((root, accGraph) => accGraph + (newRoot ~> root))
        (newRoot, newGraph)
      }
    }
    
    assert(rootGraph.isAcyclic)
    
    def pullMergeUp(mergeAt: MergeMap, successor: Entry, entry: Entry) = {
      val succMerges = mergeAt(successor)
      if (!succMerges.isEmpty) {
        mergeAt + (entry -> succMerges)
      } else {
        mergeAt
      }
    }

    def toSeq(entry: Elem, following: Elem): Elem = {
      // either prepend to the existing sequence, or create a new one with the entry and the subgraph
      val entrySeq = following match {
        case ppTree.seq(entries) => {
          ppTree.seq(entry :: entries)
        }
        case _ => ppTree.seq(List(entry, following))
      }
      entrySeq
    }
    
    /** Performs the actual merge of branch subgraphs: creates a parallel for branch
      * split and then merges it back into the mergePoint subgraph. So we get the following
      * graph represented:
      * 
      *  / \
      *  B C
      *  \ /
      *   D
      * 
      * Here we join branch roots B and C (with their subgraphs) into a parallel, and
      * then append the subgraph of D to the sequence, indicating a merge point. Note that
      * no element is prepended (i.e. there is no A at the start of the sequence) 
      */
    def merge(branchSubGraphs: Map[Branch, Elem], mergePoint: Branch, branchRoots: List[Branch]): Elem = {
      
      // resolve subgraphs for branches and merge point
      val rootSubGraphs = branchRoots.map(branchSubGraphs)
      val mergeSubGraph = branchSubGraphs(mergePoint)
      
      // create a new parallel of the branch subgraphs
      val par = ppTree.parallel(rootSubGraphs.reverse.toSet)
      
      // append the merge point to the parallel
      toSeq(par, mergeSubGraph)
    }
    
    /** Resolves the merge queue (all merge points downwards) for a merge root (where several
      * branches were merged into a parallel).
      */
    def mergeQueue(branchMerges: Map[Branch, List[Branch]], mergeRoot: Merge): List[Branch] = {
      // for now just take the queue of the first merge element
      // NOTE: this drops multi-merges!!
      // TODO elaborate comments
      val firstMerge = mergeRoot.roots.head
      branchMerges(firstMerge)
    }
    
    def mergeBranches(mergeAt: MergeMap, subGraphs: Map[Entry, Elem])(branchRoots0: List[Entry]): Elem = {

      // here we have roots of branches, which may need merging. Note that there can be multiple
      // merges needed, e.g. for the top split of the following graph:
      //         A
      //       / | \
      //      B  C  D
      //       \ /  |
      //        E   F
      //         \ /
      //          G
      // 
      // Say we are at A at the moment, so we have 3 branches with roots at B, C and D. They have
      // following merge point lists in `mergeAt` map:
      //   B -> [E, G]
      //   C -> [E, G]
      //   D -> [G]
      // 
      // We need to merge it so that B and C are merged at E, and then the result is merged with D
      // at G. This would be the structure to get:
      // 
      // seq - A
      //     - par - seq - par - B
      //     |     |     |     - C
      //     |     |     - E
      //     |     |
      //     |     - seq - D
      //     |           - F
      //     |
      //     - G
      //
      // To achieve this, we recursively group the branches by the top merge point, and do the merge
      // there, e.g. group B and C on E merge, then group this merged branch with D on G merge.

      def mergeMulti(branchSubGraphs: Map[Branch, Elem],
        branchMerges: Map[Branch, List[Branch]], branchRoots: List[Branch]): Elem = {

        // check there is something to merge
        assume(isMulti(branchRoots))

        // take the leaf branches to a side (which have no merge points) - will add them to the top parallel
        val (leafBranches, mergeBranches) = branchRoots.partition(root => branchMerges(root).isEmpty)

        if (mergeBranches.isEmpty) {

          // nothing to merge - just create and return the parallel with leaf branches
          ppTree.parallel(leafBranches.map(branchSubGraphs).reverse.toSet)

        } else {

          // group by the head merge point
          val groupedBranches = mergeBranches.groupBy(root => branchMerges(root).head)

          // partition to single roots vs multi roots (then merge multi-roots)
          //
          // we will postpone single roots resolution to the recursive call (see D -> [G] branch)
          // that will merge with the shortened merge of B and C
          val (multiRoots, singleRoots) = groupedBranches.partition {
            case (mergePoint, rootsToMerge) => isMulti(rootsToMerge)
          }

          if (multiRoots.isEmpty && isMulti(singleRoots)) {
            
            // no roots sharing a merge point, but multiple single roots
            // do not support this case at the moment
            
            // TODO !!! - remove merges somehow arbitrarily?
            // e.g. find subsequent shared merge points and drop the interfering ones?
            // so that we would at least handle [P, R], [Q, R] merge queues?
            
            throw new IllegalArgumentException("Invalid graph to convert to ProofProcess tree.")
          }

          // merge all multi-roots (e.g. B and C above with the E merge point)
          // this produces a map from a new root (B+C) pointing to the merged ProofElem
          val mergedRoots = multiRoots.map {
            case (mergePoint, branchRoots) => (Merge(branchRoots), merge(branchSubGraphs, mergePoint, branchRoots))
          }

          // now continue recursively with the remaining single roots joined with
          // the newly created merged roots
          val newRoots = singleRoots.values.flatten ++ mergedRoots.keys
          val newSubGraphs = branchSubGraphs ++ mergedRoots

          // add the new merge roots to the map, but consuming the head of the merge queue
          // (the merge point that has been applied)
          val newMerges = mergedRoots.keys.foldLeft(branchMerges) {
            (merges, rootMerge) => merges + (rootMerge -> mergeQueue(merges, rootMerge).tail)
          }

          val fullMerge = if (isMulti(newRoots)) {
            // there are multiple roots pending - continue recursively
            mergeMulti(newSubGraphs, newMerges, newRoots.toList)
          } else {
            // only a single root is remaining, return its subgraph
            newSubGraphs(newRoots.head)
          }

          if (leafBranches.isEmpty) {
            // no leafs, return the full merge
            fullMerge
          } else {
            // wrap into a parallel with the leaf branches
            ppTree.parallel(leafBranches.map(branchSubGraphs).reverse.toSet + fullMerge)
          }
        }
      }
      
      val branchRoots = branchRoots0.map(Root)
      
      // make a smaller map of branch merges
      val branchMerges: Map[Branch, List[Branch]] = 
        branchRoots.map(root => (root, mergeAt(root.entry).map(Root))).toMap
        
      // create a subgraphs map that looks up roots in the subgraph map as a fallback
      val branchSubGraphs = Map[Branch, Elem]().withDefault{ 
        case Root(r) => subGraphs(r)
        case Merge(_) => throw new IllegalStateException("Querying for merge without putting it first")
      }
      
      mergeMulti(branchSubGraphs, branchMerges, branchRoots)
    }

    def createSubGraph(mergeAt: MergeMap, subGraphs: Map[Entry, Elem])
        (entry: Entry, successors: List[Entry], isMerge: Boolean): (Elem, MergeMap) = {

      val mergePoints = mergeAt.values.flatten.toSet
      val (pendingMergeSuccs, succs) = successors.partition(mergePoints.contains)

      succs match {
        // TODO subGraphs(entry)?
        case Nil => (entry, mergeAt)
        case single :: Nil => {
          // sequence:
          // either prepend to the existing sequence, or create a new one with the entry and the subgraph
          val entrySeq = toSeq(entry, subGraphs(single))

          // update merge map so that entry points to the outstanding merge list
          // (since it is the parent element in the merge branch)
          val newMergeAt = pullMergeUp(mergeAt, single, entry)

          (entrySeq, newMergeAt)
        }

        case multiple => {
          // parallel split here
          // first try to close the open branches of the parallel that can be merged
          val splitMergeTree = mergeBranches(mergeAt, subGraphs)(multiple)

          if (isMerge) {
            // this is a merge as well as split
            // This special case is handled as a bit of a hack (though the case is not expected
            // to appear in proofs), by doing 2 Parallel branches in a row
            // TODO implement
            throw new IllegalArgumentException("Invalid graph to convert to ProofProcess tree (merge+split).")

          } else {
            // not a merge - add the entry before the parallel
            val newSeq = toSeq(entry, splitMergeTree)
            (newSeq, mergeAt)
          }
        }
      }
    }
    
    var subGraphs = Map[Entry, Elem]()
    var mergeAt: MergeMap = Map().withDefaultValue(List())
    
    (rootGraph get singleRoot).traverseDownUp()(
        nodeDown = n => Continue,
        nodeUp = n => {
          
          val entry = n.value
          
          val predecessors = n.diPredecessors.map(_.value)
          val isMerge = isMulti(predecessors) 
          if (isMerge) {
            // more than one predecessor - this is a merge point
            val entryMerges = mergeAt(entry)
            val mergeQueue = entry :: entryMerges
            mergeAt = predecessors.foldLeft(mergeAt){
              (merges, predecessor) => merges + (predecessor -> mergeQueue)
            }
          }
          
          val successors = n.diSuccessors.map(_.value).toList
          val (entrySubGraph, newMergeAt) =
            createSubGraph(mergeAt, subGraphs)(entry, successors, isMerge)
          mergeAt = newMergeAt
          
          subGraphs += (entry -> entrySubGraph)
        })
    
    // after the down->up traversal, find the subgraph for the root
    // TODO remove the new merge root at the top?
    subGraphs(singleRoot)
  }
  
  private def isMulti(col: Iterable[_]): Boolean = {
    // try taking 2 and check if the result is bigger than a single element
    // this avoids calculating full size of, say, List
    col.take(2).size > 1
  }

}
