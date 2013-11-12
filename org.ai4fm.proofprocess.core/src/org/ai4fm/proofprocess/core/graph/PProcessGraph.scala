package org.ai4fm.proofprocess.core.graph

import FoldableGraph._
import scalax.collection.GraphEdge._
import scalax.collection.GraphPredef._
import scalax.collection.immutable.Graph


/** 
 * Converter between Scala Graph and ProofProcess tree structure.
 * 
 * @author Andrius Velykis
 */
object PProcessGraph {

  type PPGraph[E] = Graph[E, DiEdge]
  type PPGraphRoots[E] = List[E]
 
  /**
   * Encapsulates a ProofProcess graph that can have multiple roots and attached meta-information
   * for entries.
   * 
   * @tparam E  graph entry (node) type
   * @tparam I  meta-information type
   */
  case class PPRootGraph[E, I](graph: PPGraph[E], roots: PPGraphRoots[E], meta: Map[E, List[I]])
  
  object PPRootGraph {
    /** Empty graph */
    def apply[E, I]()(implicit entryManifest: Manifest[E]): PPRootGraph[E, I] = 
      PPRootGraph(Graph[E, DiEdge](), List(), Map())
  }

}


/**
 * Provides conversion methods between an proof entry-based Scala graph and a ProofProcess tree
 * structure.
 *
 * @tparam L  the supertype of all proof elements
 * @tparam E  proof entry type
 * @tparam S  proof entry sequence type
 * @tparam P  parallel proof entries type
 * @tparam I  proof meta-information type
 *
 * @param ppTree   the ProofProcess tree element extractors/converters
 * @param topRoot  a helper method to construct an intermediate artificial root element
 *                 in case of multiple graph roots
 *
 * @author Andrius Velykis
 */
class PProcessGraph[L, E <: L, S <: L, P <: L, I](ppTree: PProcessTree[L, E, S, P, _, I],
                                                  topRoot: => E) {

  import PProcessGraph._

  /**
   * Converts ProofProcess graph represented as tree with the given root element to
   * a Scala DAG of just the ProofEntry elements, and the list of root entries.
   *
   * @param rootElem  the ProofProcess tree represented by its root element
   *
   * @return  Graph representation of the given ProofProcess tree, as graph + root nodes.
   */
  def toGraph(rootElem: L)(implicit entryManifest: Manifest[E]): PPRootGraph[E, I] = {
    
    val emptyGraph = PPRootGraph[E, I]()

    // a method that collects the graph with an accumulator (necessary for Seq implementation)
    def graph0(rootElem: L,
               acc: PPRootGraph[E, I],
               parentMeta: List[I]): PPRootGraph[E, I] = {

      // add this level's meta to the list
      val elemMeta = ppTree.info(rootElem) :: parentMeta

      rootElem match {

        // for proof entry, add it to the graph and connect to all outstanding roots
        // (this means that this entry step is followed by all outstanding entry steps)
        // 
        // normally there should be only a single outstanding root here (or none), but multiple can
        // happen, e.g., when we have 2 parallels in a row in a sequence: Seq(Entry, Parallel, Parallel, Entry).
        // This way there should be a merge between parallels, but it is not represented as entry

        // see that we typecast manually here, since the extractors cannot set the subtype correctly.
        // We perform this pattern matching, then casting
        case ppTree.entry(_) => {
          val entry = rootElem.asInstanceOf[E]
          val PPRootGraph(accGraph, accRoots, accMeta) = acc

          // add the entry to the graph
          val withEntryEdges = accRoots.foldLeft(accGraph + entry) {
            // for each root, add an edge from entry to root
            (accGraph, root) => accGraph + (entry ~> root)
          }

          // only the entry is a root now
          PPRootGraph(withEntryEdges, List(entry), accMeta + (entry -> elemMeta))
        }

        // for parallel, create subgraphs of each parallel entry,
        // and then merge these subgraphs and their roots
        case ppTree.parallel((entries, links)) => {

          // create subgraph based on the accumulated graph
          val subGraphs = entries map (e => graph0(e, acc, elemMeta))

          val merged = subGraphs.foldRight(emptyGraph) {
            case (PPRootGraph(subGraph, subRoots, subMeta),
                  PPRootGraph(foldGraph, foldRoots, foldMeta)) =>
              PPRootGraph(subGraph ++ foldGraph, subRoots ++ foldRoots, subMeta ++ foldMeta)
          }

          val withLinks =
            if (!links.isEmpty) {
              merged.copy(roots = merged.roots ++ links)
            } else merged

          withLinks
        }

        // for sequences, just accumulate subgraph from the end, this way elements at the
        // start are parents/roots to the subsequent elements
        case ppTree.seq(elems) => elems.foldRight(acc) {
          (entry, acc) => graph0(entry, acc, elemMeta)
        }
      }
    }
    
    // invoke with empty accumulator and the given root, it will create the graph recursively
    graph0(rootElem, emptyGraph, Nil)
  }


  /**
   * Converts ProofProcess graph represented as a Scala graph + roots to a corresponding
   * ProofProcess tree structure.
   *
   * @param rootGraph   the Scala graph representation of ProofProcess data (graph + roots)
   *
   * @return  ProofProcess tree representation of the data as the root element of the tree.
   */
  def toPProcessTree(rootGraph: PPRootGraph[E, I]): L = {

    val PPRootGraph(graph, roots, meta) = rootGraph
    
    require(!roots.isEmpty)

    roots match {

      case single :: Nil => // already single root
        toPProcessTree(graph, single, meta)

      case multiple => {

        // for multiple roots, create an artificial root that maps to every other root
        // assume a parallel split for multiple roots
        val newRoot = topRoot
        val newGraph = roots.foldRight(graph)((root, accGraph) => accGraph + (newRoot ~> root))
        
        val tree = toPProcessTree(newGraph, newRoot, meta)
        
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


  /** 
   * Converts ProofProcess graph represented as a Scala graph + single root to a corresponding
   * ProofProcess tree structure.
   *
   * @param graph   the Scala graph representation of ProofProcess data
   * @param root    the single root element of the Scala ProofProcess graph
   * @param meta    meta proof-information for graph entries (entry to its parents)
   *
   * @return  ProofProcess tree representation of the data as the root element of the tree.
   */
  def toPProcessTree(graph: PPGraph[E], root: E, meta: Map[E, List[I]]): L = {
    val ppTreeStructure = toPProcessTreeStructure(graph, root)
    // need to reverse the meta, so that the lowest meta is the last
    val reverseInfoMeta = meta mapValues (_.reverse)
    addPProcessTreeMetaInfo(ppTreeStructure, reverseInfoMeta)
  }


  private def toPProcessTreeStructure(graph: PPGraph[E], root: E): L = {
    // FIXME support meta-information
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

    /**
     * Flatten parallel upon creation - lifts all given parallel entries 
     */
    def createParallel(entries: Set[L], softLinks: Set[E]): L = {
      val (flatEntries, flatLinks) = (entries map flattenParallel).unzip
      ppTree.parallel(flatEntries.flatten, flatLinks.flatten ++ softLinks)
    }

    def flattenParallel(elem: L): (Set[L], Set[E]) = elem match {
      case ppTree.parallel(entries, links) => (entries, links)
      case _ => (Set(elem), Set())
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
          createParallel(branches, softLinks)
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


  private def addPProcessTreeMetaInfo(rootElem: L,
                                      meta: Map[E, List[I]]): L = {
    val (infoRoot, parentInfos) = addInfo(meta)(rootElem)

    parentInfos match {
      case Some(infos) => addInfos(infoRoot, infos)
      case None => infoRoot
    }
  }

  private def addInfo(meta: Map[E, List[I]])(rootElem: L): (L, Option[List[I]]) = rootElem match {

    case ppTree.entry(_) => {
      val entry = rootElem.asInstanceOf[E]

      val entryInfo = meta.get(entry)
      entryInfo match {

        // assume that the last element in each meta list is the entry's info
        case Some(infos) if !infos.isEmpty => {
          val newEntry = ppTree.addInfo(entry, infos.last)
          (newEntry, Some(infos.init))
        }

        // no proof meta-information (also empty list?) is available:
        // do not add anything and return the entry
        case _ => (entry, None)
      }
    }

    case ppTree.parallel((entries, links)) => {
      // only add infos to entries (link infos will be added somewhere within)

      val infoEntries = entries map addInfo(meta)
      val (infoChildren, commonInfos) = addChildrenInfo(infoEntries.toList, true)

      val newParallel = ppTree.parallel((infoChildren.toSet, links))
      // add the last info to the parallel itself

      // TODO what if the group is actually much higher,
      // e.g. encompassing the parallel and its siblings?
      if (commonInfos.isEmpty) {
        (newParallel, None)
      } else {
        (ppTree.addInfo(newParallel, commonInfos.last), Some(commonInfos.init))
      }
    }

    case ppTree.seq(elems) => {
      val infoElems = elems map addInfo(meta)
      val (infoChildren, commonInfos) = addChildrenInfo(infoElems, false)

      val newSeq = ppTree.seq(infoChildren)
      if (commonInfos.isEmpty) {
        (newSeq, None)
      } else {
        (ppTree.addInfo(newSeq, commonInfos.last), Some(commonInfos.init))
      }
    }
  }


  private def addChildrenInfo(elemInfos0: List[(L, Option[List[I]])],
                              branchChildren: Boolean): (List[L], List[I]) = {
    val elemInfos = flattenInfos(elemInfos0)

    val parentInfos = longestCommonPrefixAll(elemInfos.view map (_._2))

    val childInfos =
      if (parentInfos.isEmpty) elemInfos
      else {
        // remove common parents from children infos
        val parentLength = parentInfos.size
        elemInfos map { case (e, infos) => (e, infos.drop(parentLength)) }
      }

    val childrenGroups =
      if (!branchChildren) {
        groupChildrenByInfoHeads(childInfos)
      } else {
        // do not group - just wrap the element into infos
        // (this is needed for parallels)
        childInfos map { case (elem, infos) => addInfos(elem, infos) }
      }

    (childrenGroups, parentInfos)
  }

  private def flattenInfos[E, I](elemInfos: List[(E, Option[List[I]])])
      : List[(E, List[I])] = {
    val (elems, infos) = elemInfos.unzip
    val flatInfos = flattenPrev(infos, Nil)
    elems zip flatInfos
  }

  /**
   * Flattens the sequence by using previous non-empty value to replace empty values.
   * Uses the given initial value if the first element is empty.
   */
  private def flattenPrev[A, B](seq: Seq[Option[B]], initial: => B): Seq[B] =
    if (seq.isEmpty) {
      Nil
    } else {
      // resolve the new head with the given initial value if not available and
      // accumulate the results based on previous element calculation
      val newHead = seq.head getOrElse initial
      (seq.tail scanLeft newHead) { (prev, value) => value getOrElse prev }
    }


  private def longestCommonPrefixAll[A](lists: Seq[List[A]]): List[A] =
    if (lists.isEmpty) Nil
    else lists reduceLeft longestCommonPrefix

  private def longestCommonPrefix[A](l1: List[A], l2: List[A]): List[A] = {
    l1 match {
      case Nil => Nil
      case x :: xs => if (l2 != Nil && l2.head == x) x :: longestCommonPrefix(xs, l2.tail) else Nil
    }
  }


  private def groupChildrenByInfoHeads(elemInfos: List[(L, List[I])]): List[L] = {
    // group by head element
    val groups = groupByHead(elemInfos)

    val groupSubtrees =
      groups map {
        case (groupInfo, group) => groupInfo match {

          case None => group map (_._1)

          // group with a shared info - wrap into a proof seq and add the info 
          case Some(info) => {
            val subTrees = groupChildrenByInfoHeads(group)
            val seqElem = groupWithInfo(subTrees, info)
            List(seqElem)
          }
        }
      }

    groupSubtrees.flatten
  }

  private def groupWithInfo(elems: List[L], info: I): L = ppTree.addInfo(ppTree.seq(elems), info)


  private def groupByHead[E, I](elemInfos: Seq[(E, List[I])])
      : List[(Option[I], List[(E, List[I])])] =
    // group and also remove empty groups
    groupByHead0(elemInfos.toList, None, Nil) filterNot { case (_, group) => group.isEmpty }

  private def groupByHead0[E, I](elemInfos: List[(E, List[I])],
                                 groupHead: Option[I],
                                 currentGroup: List[(E, List[I])])
      : List[(Option[I], List[(E, List[I])])] = {

    // need to reverse the group, because it is constructed backwards
    lazy val groupEntry = (groupHead, currentGroup.reverse)

    elemInfos match {

      case Nil => List(groupEntry)

      case (elem, infos) :: tail => {

        // take the infos tail to use in the group
        val currentEntry = (elem, if (infos.isEmpty) Nil else infos.tail)
        val currentHead = infos.headOption

        if (groupHead == currentHead) {
          // same group - add to the group and continue 
          groupByHead0(tail, groupHead, currentEntry :: currentGroup)
        } else {
          // new group - mark old group and continue recursively with a new group
          groupEntry :: groupByHead0(tail, currentHead, List(currentEntry))
        }
      }
    }
  }


  private def addInfos(elem: L, infos: List[I]): L = 
    (infos foldRight elem){ (info, e) => groupWithInfo(List(e), info) }


}
