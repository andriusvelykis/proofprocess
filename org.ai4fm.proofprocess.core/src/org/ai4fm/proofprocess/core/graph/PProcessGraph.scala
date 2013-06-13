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

  type PPGraph[E] = Graph[E, DiEdge]
  type PPGraphRoots[E] = List[E]
  
  case class PPRootGraph[E](graph: PPGraph[E], roots: PPGraphRoots[E])
  
  object PPRootGraph {
    /** Empty graph */
    def apply[E]()(implicit entryManifest: Manifest[E]): PPRootGraph[E] = 
      PPRootGraph(Graph[E, DiEdge](), List())
  }
  
  

  /** Converts ProofProcess graph represented as tree with the given root element to
    * a Scala DAG of just the ProofEntry elements, and the list of root entries.
    *
    * @return  `(graph, roots)` the pair of created graph and the list of roots to traverse it
    */
  def toGraph[L, E <: L, S <: L, Parallel <: L]
      (ppTree: PProcessTree[L, E, S, Parallel, _])
      (rootElem: L)
      (implicit entryManifest: Manifest[E]): PPRootGraph[E] = {
    
    val emptyGraph = PPRootGraph()

    // a method that collects the graph with an accumulator (necessary for Seq implementation)
    def graph0(rootElem: L, acc: PPRootGraph[E]): PPRootGraph[E] = rootElem match {

      // for proof entry, add it to the graph and connect to all outstanding roots
      // (this means that this entry step is followed by all outstanding entry steps)
      // 
      // normally there should be only a single outstanding root here (or none), but multiple can
      // happen, e.g., when we have 2 parallels in a row in a sequence: Seq(Entry, Parallel, Parallel, Entry).
      // This way there should be a merge between parallels, but it is not represented as entry

      // see that we typecast manually here, since the extractors cannot set the subtype correctly.
      // We perform this pattern matching, then casting
      case e @ ppTree.entry(_) => {
        val entry = e.asInstanceOf[E]
        val PPRootGraph(accGraph, accRoots) = acc

        // add the entry to the graph
        val withEntryEdges = accRoots.foldLeft(accGraph + entry) {
          // for each root, add an edge from entry to root
          (accGraph, root) => accGraph + (entry ~> root)
        }

        // only the entry is a root now
        PPRootGraph(withEntryEdges, List(entry))
      }

      // for parallel, create subgraphs of each parallel entry,
      // and then merge these subgraphs and their roots
      case ppTree.parallel((entries, links)) => {

        // create subgraph based on the accumulated graph
        val subGraphs = entries map (e => graph0(e, acc))

        val merged = subGraphs.foldRight(emptyGraph) {
          case (PPRootGraph(subGraph, subRoots), PPRootGraph(foldGraph, foldRoots)) => 
            PPRootGraph(subGraph ++ foldGraph, subRoots ++ foldRoots)
        }

        val withLinks =
          if (!links.isEmpty) {
            merged.copy(roots = merged.roots ++ links)
          }
          else merged

        withLinks
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
  
  def toPProcessTree[L, E <: L, S <: L, Parallel <: L]
      (ppTree: PProcessTree[L, E, S, Parallel, _], topRoot: => E)
      (rootGraph: PPRootGraph[E]): L = {
   
    val PPRootGraph(graph, roots) = rootGraph
    
    require(!roots.isEmpty)

    roots match {

      case single :: Nil => // already single root
        toPProcessTree(ppTree)(graph, single)

      case multiple => {

        // for multiple roots, create an artificial root that maps to every other root
        // assume a parallel split for multiple roots
        val newRoot = topRoot
        val newGraph = roots.foldRight(graph)((root, accGraph) => accGraph + (newRoot ~> root))
        
        val tree = toPProcessTree(ppTree)(newGraph, newRoot)
        
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
  
  def toPProcessTree[L, E <: L, S <: L, Parallel <: L]
      (ppTree: PProcessTree[L, E, S, Parallel, _])
      (graph: PPGraph[E], root: E): L = {
    
    type MergeMap = Map[E, List[E]]
    
    // add the root to the graph to ensure that we can traverse it
    val rootGraph = graph + root

    assert(rootGraph.isAcyclic)
    
    def pullMergeUp(mergeAt: MergeMap, successor: E, entry: E): MergeMap = {
      val succMerges = mergeAt(successor)
      if (!succMerges.isEmpty) {
        mergeAt + (entry -> succMerges)
      } else {
        mergeAt
      }
    }

    def toSeq(entry: L, following: L): L = {
      // either prepend to the existing sequence, or create a new one with the entry and the subgraph
      val seq = ppTree.seq
      val entrySeq = (entry, following) match {
        
        // both sequences - merge them together
        case (seq(entries1), seq(entries2)) => seq(entries1 ::: entries2)
        
        // following sequence 
        case (e, seq(entries)) => seq(e :: entries)
        
        // preceding sequence 
        case (seq(entries), e) => seq(entries ::: List(e))
        
        // both non-sequences, just merge together
        case _ => seq(List(entry, following))
      }
      entrySeq
    }
    
    def ensureParallel(elem: L): L = {
      // check if the element is already parallel, otherwise wrap it into a parallel
      val par = elem match {
        case ppTree.parallel(_) => elem
        case _ => ppTree.parallel(Set(elem), Set())
      }
      par
    }
    
    def merge(mergeAt: MergeMap, subGraphsInit: Map[E, L])(
                entry: E, branchRoots: List[E]): (L, Map[E, L]) = {

      type BranchMerges = List[(E, List[E])]

      var _subGraphs = subGraphsInit

      def subGraphs(e: E): Option[L] = {
        // get and consume - remove from the map
        // this provides a nice depth-first parallel usage, otherwise higher parallel splits
        // re-attach the same branch from lower parallel splits..
        // 
        // These higher-level parallel splits are instead handled as "soft links"
        val result = _subGraphs.get(e)
        if (result.isDefined) {
          _subGraphs -= e
        }
        result
      }

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
      def mergeDeepest(branchMergesDeepestFirst: BranchMerges): L = {

        require(!branchMergesDeepestFirst.isEmpty)
        
        // get all merge points for this level
        val mergePoints = (branchMergesDeepestFirst flatMap { case (root, merges) => merges.headOption }).toSet
        
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
        
        def groupBranches(branches: BranchMerges): (List[E], Map[E, BranchMerges]) = {
          
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
        
        /**
         * Returns the merged element. Note that soft-links are not created for merges, they will
         * be created for outgoing elements in parallels (TODO verify).
         */
        def mergeGroup(group: BranchMerges, mergePoint: E): L = {

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
          mergeSubGraph match {
            // no unclaimed subgraph available, so no merge

            // Note: do not add soft links on merge point, since it is just a calculation.
            // Soft links will be added where the outgoing link is (in parallel branches)
            case None => directMergeGroupElem

            // can merge - add after parallel
            case Some(mergeTail) => toSeq(directMergeGroupElem, mergeTail)
          }
        }
        
        val groupRoots = mergeGroups map { case (mergePoint, group) => mergeGroup(group, mergePoint) }
        
        // resolve parallel subgraphs:
        // - subGraphs if there are unclaimed subgraphs
        // - soft links if the subGraphs have already been used (are not available)
        val leafSubGraphs0 = leafBranches map { b => (b, subGraphs(b)) }
        val leafSubGraphs = (leafSubGraphs0 map (_._2)).flatten
        val leafSoftLinks = leafSubGraphs0 filter (_._2.isEmpty) map (_._1)

        // do not create soft links for group merges
        val softLinks = /*groupSoftLinks.flatten.toSet ++ */leafSoftLinks.toSet

        // now that we have the merged groups, join them with the leaf branches into a single
        // parallel split
        val branches = groupRoots.toSet ++ leafSubGraphs.toSet
        if (isMulti(branches) || !softLinks.isEmpty) {
          // multiple branches, group into parallel
          // this also handles the case of direct merges
          // also create a parallel if there are soft links
          ppTree.parallel(branches, softLinks)
        } else {
          // single branch - return itself
          branches.head
        }
      }
      
      // collect the merge entries for each root
      // but get them reversed, so that the deepest merge is at the beginning
      val branchMergesDeepestFirst = branchRoots map (root => (root, mergeAt(root).reverse))
      
      (mergeDeepest(branchMergesDeepestFirst), _subGraphs)
    }

    def createSubGraph(mergeAt: MergeMap,
                       subGraphs: Map[E, L])(
                         entry: E,
                         successors: Iterable[E]): (L, MergeMap, Map[E, L]) = {

      val succs = successors.toList
      
      val mergePoints = mergeAt.values.flatten.toSet
      if (succs.filterNot(mergePoints.contains).isEmpty) {
        
        // there are no successors that are not merge points:
        // this is an end of a branch, or end of the proof, so the entry is leaf one
        // TODO subGraphs(entry)?
        (entry, mergeAt, subGraphs)
        
      } else {
        
        // there are normal branch successors, check how many:
        // either sequence (single successor) or parallel (multiple successors)
        succs match {

          case single :: Nil => {
            // sequence:
            // either prepend to the existing sequence, or create a new one with the entry and the subgraph
            val entrySeq = toSeq(entry, subGraphs(single))

            // consume the subgraph
            val subGraphs1 = subGraphs - single 
            
            // update merge map so that entry points to the outstanding merge list
            // (since it is the parent element in the merge branch)
            val newMergeAt = pullMergeUp(mergeAt, single, entry)

            (entrySeq, newMergeAt, subGraphs1)
          }

          case multiple => {
            // parallel split here
            // first try to close the open branches of the parallel that can be merged
            val (splitMergeTree, subGraphs1) = merge(mergeAt, subGraphs)(entry, multiple)

            // add the entry before the parallel
            // this entry+split will work as a subgraph for entry. The case when an entry is also
            // a merge will be handled via soft links: the first merge branch will add this as
            // a parallel entry, and the remaining ones will make it a soft link
            val newSeq = toSeq(entry, splitMergeTree)
            (newSeq, mergeAt, subGraphs1)
          }
        }
      }
    }

    def handleNode(subGraphs: Map[E, L],
                   mergeAt: MergeMap)(
                     node: E,
                     predecessors: Iterable[E],
                     successors: Iterable[E]): (Map[E, L], MergeMap) = {

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

      val (entrySubGraph, newMergeAt, newSubGraphs) =
        createSubGraph(predMergeAt, subGraphs)(entry, successors)

      val newSubGraphs1 = newSubGraphs + (entry -> entrySubGraph)

      (newSubGraphs1, newMergeAt)
    }
    
    val emptySubGraphs = Map[E, L]()
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
