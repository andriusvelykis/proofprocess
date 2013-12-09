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
  type PPGraphRoots[E] = Set[E]
 
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
      PPRootGraph(Graph[E, DiEdge](), Set(), Map())
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
 * @tparam Id Id reference proof entry type
 * @tparam I  proof meta-information type
 *
 * @param ppTree   the ProofProcess tree element extractors/converters
 * @param topRoot  a helper method to construct an intermediate artificial root element
 *                 in case of multiple graph roots
 *
 * @author Andrius Velykis
 */
class PProcessGraph[L, E <: L, S <: L, P <: L, Id <: L, I](
    ppTree: PProcessTree[L, E, S, P, Id, _, I],
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
          PPRootGraph(withEntryEdges, Set(entry), accMeta + (entry -> elemMeta))
        }

        // for parallel, create subgraphs of each parallel entry,
        // and then merge these subgraphs and their roots
        case ppTree.parallel(elems) => {

          // if no entries are available, preserve the accumulated roots,
          // since they are not consumed
          if (elems.isEmpty) acc
          else {

            // drop any outstanding roots before resolving parallel branches
            // each root needs to end with Id(x) indicating the root used
            val noRootsAcc = acc.copy(roots = Set())
            
            // create subgraph based on the accumulated graph and merge them
            val subGraphs = elems map (e => graph0(e, noRootsAcc, elemMeta))

            val merged = subGraphs.foldRight(emptyGraph) {
              case (PPRootGraph(subGraph, subRoots, subMeta),
                PPRootGraph(foldGraph, foldRoots, foldMeta)) =>
                PPRootGraph(subGraph ++ foldGraph, subRoots ++ foldRoots, subMeta ++ foldMeta)
            }

            merged
          }
        }

        // for sequences, just accumulate subgraph from the end, this way elements at the
        // start are parents/roots to the subsequent elements
        case ppTree.seq(elems) => elems.foldRight(acc) {
          (entry, acc) => graph0(entry, acc, elemMeta)
        }

        // for Id refs, just make the entry the root
        case ppTree.id(entry) => {
          acc.copy(roots = Set(entry))
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
    
    if (isMulti(roots)) {
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

    } else {
      // already a single root
      toPProcessTree(graph, roots.head, meta)
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
    
    // add the root to the graph to ensure that we can traverse it
    val rootGraph = graph + root

    type GNode = rootGraph.InnerNodeLike

    assert(rootGraph.isAcyclic)
    
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
        case _ => ppTree.parallel(Set(elem))
      }
      par
    }

    /**
     * Flatten parallel upon creation - lifts all given parallel entries 
     */
    def createParallel(entries: Set[L]): L = {
      val flatElems = entries map flattenParallel
      ppTree.parallel(flatElems.flatten)
    }

    def flattenParallel(elem: L): Set[L] = elem match {
      case ppTree.parallel(elems) => elems
      case _ => Set(elem)
    }


    def handleNode(subGraphs: Map[E, L],
                   mergeInfo0: MergeInfo)(
                     node: E,
                     predecessors: Iterable[E],
                     successors: Iterable[E]): (Map[E, L], MergeInfo) = {

      val entry = node

      val mergeInfo = mergeInfo0 + (entry, predecessors)

      val (entrySubGraph, newMergeInfo) =
        createSubGraph(mergeInfo, subGraphs)(entry, successors)

      val newSubGraphs = subGraphs + (entry -> entrySubGraph)

      (newSubGraphs, newMergeInfo)
    }

    def createSubGraph(mergeInfo: MergeInfo,
                       subGraphs: Map[E, L])(
                         entry: E,
                         successors: Iterable[E]): (L, MergeInfo) = {

      def resolveSubGraph(e: E): L =
        if (mergeInfo.isMergePoint(e)) {
          // for every merge point, create an Id ref
          ppTree.id(e)
        } else {
          // subgraph should be available
          subGraphs(e)
        }

      def merge(resolveBranch: E => L,
                mergeOrder: List[Set[E]],
                roots: Set[E]): L = {

        // a recursive method to perform the deeper merges
        def merge0(mergeOrder: List[Set[E]],
                   pendingRoots: Set[E],
                   pendingJoin: Option[L]): L = {
          val (mergePoints :: remainingMerges) = mergeOrder

          // only some of the branches may participate in the merge
          val consumedRoots0 = (mergePoints map mergeInfo.getPreMergeRoots).flatten
          // add the merge points to consumption (in case of direct merges)
          // (direct merges are links from the split directly to the merge)
          val consumedRoots = consumedRoots0 ++ mergePoints
          
          val (consumed, remainingRoots) = pendingRoots partition consumedRoots.contains

          val mergeElem =
            // if multiple merge points at this level, join them in parallel
            // (this will become parallel followed by parallel)
            if (isMulti(mergePoints)) ppTree.parallel(mergePoints map subGraphs)
            else subGraphs(mergePoints.head)

          // join the consumed branches with pending join element (from higher merge)
          val consumedBranches = consumed map resolveBranch
          val branchElem = ppTree.parallel(pendingJoin.toSet ++ consumedBranches)
          val fullSplitMerge = toSeq(branchElem, mergeElem)

          if (remainingMerges.isEmpty) {
            if (remainingRoots.isEmpty) fullSplitMerge
            else {
              // add the remaining ones as parallel branches
              val leafBranches = remainingRoots map resolveBranch
              ppTree.parallel(Set(fullSplitMerge) ++ leafBranches)
            }
          }
          else merge0(remainingMerges, remainingRoots, Some(fullSplitMerge))
        }

        merge0(mergeOrder, roots, None)
      }

      val succs = successors.toList

      // check how many successors: leaf (no succs), sequence (one succ) or parallel (multiple)
      succs match {

        // this is an end of a branch, or end of the proof, so the entry is leaf one
        case Nil => (entry, mergeInfo)

        // sequence:
        // either prepend to the existing sequence,
        // or create a new one with the entry and the subgraph
        case single :: Nil => {

          val follow = resolveSubGraph(single)
          val entrySeq = toSeq(entry, follow)

          (entrySeq, mergeInfo)
        }

        // parallel split:
        case multiple => {
          
          val merges = mergeInfo findMerges entry
          val splitMergeElem =
            if (merges.isEmpty) {
              // no merges - a straightforward parallel split
              val branches = multiple.toSet map resolveSubGraph
              ppTree.parallel(branches)
              
            } else {
              
              val orderedMerges = mergeInfo orderMerges merges
              merge(resolveSubGraph, orderedMerges, multiple.toSet)
            }

          // add the initial split element to the beginning of the split+merge
          val entryParallel = toSeq(entry, splitMergeElem)

          // remove merge info
          val consumedMergeInfo =
            if (merges.isEmpty) mergeInfo
            else (merges foldLeft mergeInfo){ (info, m) => info - m }

          (entryParallel, consumedMergeInfo)
        }
      }

    }

    val (subGraphs, _) = (rootGraph foldNodesRight (Map[E, L](), MergeInfo()))({
      case ((node, predecessors, successors), (subGraphs, mergeInfo)) => 
        handleNode(subGraphs, mergeInfo)(node, predecessors, successors)
    })(List(root))
    
    // after the down->up traversal, find the subgraph for the root
    subGraphs(root)
  }
  
  private def isMulti(col: Iterable[_]): Boolean = {
    // try taking 2 and check if the result is bigger than a single element
    // this avoids calculating full size of, say, List
    col.take(2).size > 1
  }

  
  import MergeInfo.MergeRoots

  private class MergeInfo private(
      private val mergeRoots: Map[E, MergeRoots] = Map(),
      private val mergeSuccs: Map[E, Set[E]] = Map()) {

    def +(e: E, preds: Iterable[E]): MergeInfo = {

      val isMerge = isMulti(preds)
      val parents = preds.toSet

      // pull up roots of any pending merges, if applicable
      // (if `succs` are part of any merge roots, replace them with `e`)
      def pullRootsUp(roots: Map[E, MergeRoots]): Map[E, MergeRoots] =
        if (roots.isEmpty) roots
        else pullRootUp(roots, e, parents)

      // mark roots for new merge, if applicable
      def markNewMerge(roots: Map[E, MergeRoots]): Map[E, MergeRoots] =
        if (isMerge) roots + (e -> new MergeRoots(parents))
        else roots

      def markMergeSuccs(mSuccs: Map[E, Set[E]], roots: Map[E, MergeRoots]): Map[E, Set[E]] =
        if (isMerge) mSuccs + (e -> findMerges(roots, e))
        else mSuccs

      val newSuccs = markMergeSuccs(mergeSuccs, mergeRoots)
      val newRoots = markNewMerge(pullRootsUp(mergeRoots))

      new MergeInfo(newRoots, newSuccs)
    }

    private def pullRootUp(roots: Map[E, MergeRoots],
                           entry: E,
                           parents: Set[E]): Map[E, MergeRoots] =
      for ((merge, rs) <- roots) yield (merge, rs.pullMergeRoot(entry, parents))

    private def findMerges(roots: Map[E, MergeRoots], entry: E): Set[E] = {
      val filteredRoots = roots filter { case (merge, rs) => rs.current contains entry }
      filteredRoots.keys.toSet
    }

    // during removal, there is no need to update succs mapping
    def -(e: E): MergeInfo = new MergeInfo(mergeRoots - e, mergeSuccs)

    /**
     * Finds possible merges at the given element.
     * Merges are done if the element is the only pending root.
     */
    def findMerges(e: E): Set[E] =
      if (mergeRoots.isEmpty) Set()
      else {
        val eRoot = Set(e)
        val merges = mergeRoots filter { case (merge, pendingRoots) => pendingRoots.current == eRoot }

        merges.keys.toSet
      }

    def orderMerges(merges: Set[E]): List[Set[E]] = {
      val flatMergeSuccs =
        for { (m, succs) <- mergeSuccs if merges contains m } yield (m, allSuccs(succs))

      orderMerges0(merges, flatMergeSuccs)
    }

    private def orderMerges0(merges: Set[E], allSuccs: Map[E, Set[E]]): List[Set[E]] =
      if (merges.isEmpty) Nil
      else {
        // find merges which are not succeeded by any other merges - these will be at the end
        val lastMerges = merges filterNot { m => intersects(merges, allSuccs(m)) }
        if (lastMerges.isEmpty) {
          // cannot make clear merge order - just return all of them
          List(merges)
        } else {
          // continue recursively after dropping the identified last merges
          orderMerges0(merges -- lastMerges, allSuccs) ::: List(lastMerges)
        }
      }

    private def allSuccs(succs: Set[E]): Set[E] = {
      if (succs.isEmpty) succs
      else {
        val childSuccs = succs map mergeSuccs
        val allChildSuccs = childSuccs map allSuccs
        succs ++ allChildSuccs.flatten
      }
    }

    private def intersects[A](s1: Set[A], s2: Set[A]): Boolean =
      s1 exists { e1 => s2 contains e1 }

    def isMergePoint(e: E): Boolean = mergeSuccs contains e

    def getPreMergeRoots(m: E): Set[E] = mergeRoots(m).preMerge
  }

  private object MergeInfo {

    def apply() = new MergeInfo()

    private class MergeRoots private (val current: Set[E],
                                      private val preRoots: Map[E, Set[E]]) {

      def this(initial: Set[E]) = this(initial, Map().withDefaultValue(Set()))

      def pullMergeRoot(child: E, parents: Set[E]): MergeRoots =
        // pull roots if there are more than one (merge not complete yet)
        // and they can be pulled (the child exists among them)
        if (isMulti(current) && (current contains child)) {
          // mark pre-elements for each parent (add the child to the set)
          val newPreRoots = (parents foldLeft preRoots) { (roots, parent) =>
            {
              val newRoots = roots(parent) + child
              roots + { parent -> newRoots }
            }
          }
          val newRoots = (current - child) ++ parents
          new MergeRoots(newRoots, newPreRoots)

        } else {
          this
        }

      def preMerge: Set[E] = (current map preRoots).flatten

      override def toString = current.toString
    }
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

    case ppTree.parallel(elems) => {
      val infoElems = elems map addInfo(meta)
      val (infoChildren, commonInfos) = addChildrenInfo(infoElems.toList, true)

      val newParallel = ppTree.parallel(infoChildren.toSet)
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

    // do nothing for IDs
    case ppTree.id(_) => (rootElem.asInstanceOf[Id], None)
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
