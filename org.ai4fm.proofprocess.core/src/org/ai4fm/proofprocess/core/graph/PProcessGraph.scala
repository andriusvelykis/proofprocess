package org.ai4fm.proofprocess.core.graph

import FoldableGraph._
import scalax.collection.GraphEdge._
import scalax.collection.GraphPredef._
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
      (ppTree: PProcessTree[Elem, Entry, Seq, Parallel, _, _], topRoot: => Entry)
      (graph: Graph[Entry, DiEdge], roots: List[Entry]): Elem = {
   
    assume(!roots.isEmpty)

    roots match {

      case single :: Nil => // already single root
        proofProcessTree(ppTree)(graph, single)

      case multiple => {

        // for multiple roots, create an artificial root that maps to every other root
        // assume a parallel split for multiple roots
        val newRoot = topRoot
        val newGraph = roots.foldRight(graph)((root, accGraph) => accGraph + (newRoot ~> root))
        
        val tree = proofProcessTree(ppTree)(newGraph, newRoot)
        
        // the new root will always be the top element in the top sequence
        tree match {
          
          case ppTree.seq(rootElem :: elems) if (rootElem == newRoot) =>
            // if there is only a single element remaining, unpack it from the sequence
            elems match {
              case single :: Nil => single
              case multiple => ppTree.seq(multiple)
            }
          
          case _ => // invalid?
            throw new IllegalStateException(tree.toString)
        }
      }
    }
    
  }
  
  private def proofProcessTree[Elem, Entry <: Elem, Seq <: Elem, Parallel <: Elem]
      (ppTree: PProcessTree[Elem, Entry, Seq, Parallel, _, _])
      (graph: Graph[Entry, DiEdge], root: Entry): Elem = {
    
    type MergeMap = Map[Entry, List[Entry]]
    
    // add the root to the graph to ensure that we can traverse it
    val rootGraph = graph + root

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
    
    def ensureParallel(elem: Elem): Elem = {
      // check if the element is already parallel, otherwise wrap it into a parallel
      val par = elem match {
        case ppTree.parallel(_) => elem
        case _ => ppTree.parallel(Set(elem))
      }
      par
    }
    
    def merge(mergeAt: MergeMap, subGraphs: Map[Entry, Elem])(entry: Entry, branchRoots: List[Entry]): Elem = {

      type BranchMerges = List[(Entry, List[Entry])]
      
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
      // To achieve this, we recursively group the branches by the deepest merge point, and do the merge
      // there, e.g. group B and C on E merge, then group this merged branch with D on G merge.
      def mergeDeepest(branchMergesDeepestFirst: BranchMerges): Elem = {

        assume(!branchMergesDeepestFirst.isEmpty)
        
        // get all merge points for this level
        val mergePoints = branchMergesDeepestFirst flatMap { case (root, merges) => merges.headOption } toSet
        
        // we will merge on the above merge points later in the method.
        // However, there is additional consideration needed: what if one of the branches is a merge point?
        // This happens when we have a split without any entries in one branch, e.g. (A -> C):
        //   A
        //  / \
        //  B  |
        //  \ /
        //   C 
        //
        // So drop all such branches, since they must not participate in merge resolution, and will be
        // specially handled at the end of this method
        
        val (mergePointBranches, entryBranches) =
          branchMergesDeepestFirst partition { case (root, merges) => mergePoints.contains(root) }
        
        def groupBranches(branches: BranchMerges): (List[Entry], Map[Entry, BranchMerges]) = {
          
          // group by the deepest merge point (at the start of the merge list)
          // note that empty branches have been split off already
          val mergeGroups = branches groupBy { case (root, merges) => merges.headOption }
          
          // get the leaf branches (grouped at None head), or create an empty list otherwise
          val leafBranches = (mergeGroups get None) getOrElse List()
          // drop the merges list, since it is empty anyways
          val leafEntries = leafBranches map { case (root, merges) => root }

          // drop the head element from the non-empty merge groups, since it is used
          // as a merge point for grouping
          // (also drop the leaf branch mapping, and thus unpack the key option)
          val mergeGroupTails = mergeGroups flatMap {
            case (None, _) => None
            
            // unpack merge point, drop the head of the merges (used as the merge point)
            case (Some(mergePoint), group) => {
              val mergeTails = group map { case (root, merges) => (root, merges.tail) }
              Some((mergePoint, mergeTails))
            }
          }
          
          (leafEntries.toList, mergeGroupTails)
        }
        
        // split off the leaf branches (with no merge information), will add them to root parallel
        // also group the other branches on their merge points and adjust the remaining merge points
        val (leafBranches, mergeGroups) = groupBranches(entryBranches)
        
        def mergeGroup(group: BranchMerges, mergePoint: Entry): Elem = {

          // continue recursively for the group (remaining merges)
          val groupElem = mergeDeepest(group)

          val directMergeGroupElem =
            if (mergePointBranches.exists(_._1 == mergePoint)) {
              // ensure the group element is wrapped into a parallel,
              // so for direct merge with additional normal branch, it will wrap that branch
              // into a parallel by itself
              ensureParallel(groupElem)
            } else {
              groupElem
            }
          
          // retrieve subgraph for the merge point
          val mergeSubGraph = subGraphs(mergePoint)
          
          // append the merge point to the grouped (parallel)
          // this will create a merge point after the (possibly) parallel split
          toSeq(directMergeGroupElem, mergeSubGraph)
        }
        
        val groupRoots = mergeGroups map { case (mergePoint, group) => mergeGroup(group, mergePoint) }
        
        // resolve parallel subgraphs
        val leafSubGraphs = leafBranches map subGraphs
        
        // now that we have the merged groups, join them with the leaf branches into a single
        // parallel split
        val branches = leafSubGraphs.toSet ++ groupRoots.toSet
        if (isMulti(branches)) {
          // multiple branches, group into parallel
          // this also handles the case of direct merges 
          ppTree.parallel(branches)
        } else {
          // single branch - return itself
          branches.head
        }
      }
      
      // collect the merge entries for each root
      // but get them reversed, so that the deepest merge is at the beginning
      val branchMergesDeepestFirst = branchRoots map (root => (root, mergeAt(root).reverse))
      
      mergeDeepest(branchMergesDeepestFirst)
    }

    def createSubGraph(mergeAt: MergeMap, subGraphs: Map[Entry, Elem])
        (entry: Entry, successors: Iterable[Entry], isMerge: Boolean): (Elem, MergeMap) = {

      val succs = successors.toList
      
      val mergePoints = mergeAt.values.flatten.toSet
      if (succs.filterNot(mergePoints.contains).isEmpty) {
        
        // there are no successors that are not merge points:
        // this is an end of a branch, or end of the proof, so the entry is leaf one
        // TODO subGraphs(entry)?
        (entry, mergeAt)
        
      } else {
        
        // there are normal branch successors, check how many:
        // either sequence (single successor) or parallel (multiple successors)
        succs match {

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
            val splitMergeTree = merge(mergeAt, subGraphs)(entry, multiple)

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
    }
    
    def handleNode(subGraphs: Map[Entry, Elem], mergeAt: MergeMap)
                  (node: Entry, predecessors: Iterable[Entry], successors: Iterable[Entry]
                  ): (Map[Entry, Elem], MergeMap) = {

      val entry = node

      val isMerge = isMulti(predecessors)
      val predMergeAt = if (isMerge) {
        // more than one predecessor - this is a merge point
        val entryMerges = mergeAt(entry)
        val mergeQueue = entry :: entryMerges
        predecessors.foldLeft(mergeAt) {
          (merges, predecessor) => merges + (predecessor -> mergeQueue)
        }
      } else {
        mergeAt
      }

      val (entrySubGraph, newMergeAt) =
        createSubGraph(predMergeAt, subGraphs)(entry, successors, isMerge)

      val newSubGraphs = subGraphs + (entry -> entrySubGraph)

      (newSubGraphs, newMergeAt)
    }
    
    val emptySubGraphs = Map[Entry, Elem]()
    val emptyMergeAt: MergeMap = Map().withDefaultValue(List())

    val (subGraphs, mergeAt) = rootGraph.foldNodesRight((emptySubGraphs, emptyMergeAt))({
      case ((node, predecessors, successors), (subGraphs, mergeAt)) => 
        handleNode(subGraphs, mergeAt)(node, predecessors, successors)
    })(List(root))
    
    // after the down->up traversal, find the subgraph for the root
    subGraphs(root)
  }
  
  private def isMulti(col: Iterable[_]): Boolean = {
    // try taking 2 and check if the result is bigger than a single element
    // this avoids calculating full size of, say, List
    col.take(2).size > 1
  }

}
