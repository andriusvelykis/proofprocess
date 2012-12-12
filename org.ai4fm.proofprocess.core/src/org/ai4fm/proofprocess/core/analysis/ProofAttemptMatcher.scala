package org.ai4fm.proofprocess.core.analysis

import scala.collection.JavaConverters._
import scala.collection.Set

import scalax.collection.GraphEdge.DiEdge
import scalax.collection.GraphPredef._
import scalax.collection.immutable.Graph

import org.ai4fm.graph.isomorphism.VF2Isomorphism
import org.ai4fm.proofprocess.{Attempt, Proof, ProofElem, ProofEntry, ProofProcessFactory, ProofStore, Term}
import org.ai4fm.proofprocess.core.graph.{EmfPProcessTree, PProcessGraph}
import org.ai4fm.proofprocess.core.graph.PProcessGraph._


/**
 * Matches a proof attempt to existing attempts in the proof store.
 * 
 * The new proof attempt is represented as a graph. Searches in reverse order for matching
 * proof and attempt for the new entry. The new attempt can be one of:
 * 
 * - subgraph of (contained within) existing attempt
 * - extension of existing attempt
 * - branch of existing attempt (new attempt) 
 * 
 * Uses subgraph isomorphism to match the attempt proof graphs.
 * 
 * @author Andrius Velykis
 */
object ProofAttemptMatcher {

  val factory = ProofProcessFactory.eINSTANCE
  
  /**
   * Finds first proof matching the given initial goals (and label, optionally).
   *
   * The proofs are searched backwards, so the last matching proof is returned. If none is found,
   * a new proof is created and added to the project.
   *
   * TODO multiple proofs?
   *
   * @param proofStore
   * @param proofLabel  proof label to match, or `None` if label should be ignored
   * @param proofGoals
   * @return The last matching proof
   */
  def findCreateProof(matcher: ProofEntryMatcher)(proofStore: ProofStore,
                                                  proofLabel: Option[String],
                                                  proofGoals: Seq[Term]): Proof = {

    val allProofs: Seq[Proof] = proofStore.getProofs.asScala

    val matchedProof = matchProof(matcher) _

    // go backwards and use the last one
    val matchIterator = allProofs.reverseIterator.filter(matchedProof(_, proofLabel, proofGoals))

    if (!matchIterator.isEmpty) {
      matchIterator.next
    } else {
      // create new and add to the store
      val proof = newProof(proofLabel, proofGoals)
      proofStore.getProofs.add(proof)

      proof
    }
  }

  private def matchProof(matcher: ProofEntryMatcher)(proof: Proof,
                                                     proofLabel: Option[String],
                                                     proofGoals: Seq[Term]): Boolean = {
    
    // TODO change the proof label if needed but do not make it part of the matching
    val labelMatched = Option(proof.getLabel) == proofLabel
    
    // do not match on proof label for now..
    /*labelMatched && */matcher.matchGoals(proof.getGoals.asScala, proofGoals)
  }
  
  private def newProof(proofLabel: Option[String], proofGoals: Seq[Term]): Proof = {
    
    val proof = factory.createProof
    
    // set label if available
    proofLabel map proof.setLabel
    
    // copy the goals defensively, they may be used somewhere else
    // TODO review copying goals
    proof.getGoals.addAll(proofGoals.asJava)
    
    proof
  }

  
  def findCreateAttempt(matcher: ProofEntryMatcher)
                       (ppGraph: PPRootGraph[ProofEntry], 
                        proof: Proof): (Attempt, Map[ProofEntry, ProofEntry]) = {
    
    // analyse attempts in backwards manner and use the last one
    val allAttempts: Seq[Attempt] = proof.getAttempts.asScala
    
    val matchedAttempt = matchAttempt(matcher) _
    
    // go backwards
    val matchIterator = allAttempts.reverseIterator flatMap (matchedAttempt(ppGraph, _))

    if (!matchIterator.isEmpty) {
      // found subgraph/extension
      matchIterator.next
    } else {
      // diff here!
      // that means that the new attempt is different from every existing one, e.g. a completely
      // new attempt or a branch from a previous one
      
      // TODO try MCS in the isomorphism mapping; just find the one with the most elements?
      
      // for now, just add as a new attempt
      val rootElem = toPProcessTree(ppGraph)
      
      val attempt = factory.createAttempt
      attempt.setProof(rootElem)
      proof.getAttempts.add(attempt)
      
      // create mapping of elements to themselves
      val idMap = (ppGraph.graph.nodes map ( node => (node.value, node.value) )).toMap

      (attempt, idMap)
    }
  }
  
  def toPProcessTree(ppGraph: PPRootGraph[ProofEntry]): ProofElem =
    PProcessGraph.toPProcessTree(
      EmfPProcessTree, EmfPProcessTree.ProofEntryTree(factory.createProofStep))(
        ppGraph)
  
  
  private def matchAttempt(matcher: ProofEntryMatcher)
                          (ppGraph: PPRootGraph[ProofEntry],
                           attempt: Attempt): Option[(Attempt, Map[ProofEntry, ProofEntry])] =
    Option(attempt.getProof) flatMap { proofRoot =>
      {
        val attemptGraph = PProcessGraph.toGraph(EmfPProcessTree)(proofRoot)

        val attemptMatcher = new AttemptMatcher(matcher)
        attemptMatcher.matchAttempt(ppGraph, attemptGraph, attempt)
      }
    }

  private class AttemptMatcher(matcher: ProofEntryMatcher) {

    def matchAttempt(ppGraph: PPRootGraph[ProofEntry],
                     attemptGraph: PPRootGraph[ProofEntry],
                     attempt: Attempt): Option[(Attempt, Map[ProofEntry, ProofEntry])] = {

      val PPRootGraph(graph, roots) = ppGraph
      val PPRootGraph(aGraph, aRoots) = attemptGraph

      // for multiple pairs, try matching them together for subgraph isomorphism testing
      val pairs = validRootPairs(aRoots.toSet, roots.toSet)

      // TODO think whether it would be better to go through all attempts
      // and check if the current one is a subgraph of them (so we don't need to add anything)
      // or whether to check for extension immediately, which is implied by the algorithm

      // subgraph - just reuse the attempt, since the current one is shorter!
      def subGraphOfAttempt = {
        val subGraphMatch = matchFirstSubGraph(pairs, aGraph, graph)
        subGraphMatch map { case (mapping, _) => (attempt, mapping) }
      }
      
      // extension - need to update the attempt with newly added things
      def extensionOfAttempt = {
        val extensionMatch = matchFirstSubGraph(pairs map (_.swap), graph, aGraph)
        
        extensionMatch map { case (mapping, unmapped) => {
          val extendedRoot = extendAttempt(aGraph, ppGraph, mapping, unmapped)
          attempt.setProof(extendedRoot)
          
          // add unmapped as identity mapping
          val fullMapping = mapping ++ (unmapped zip unmapped) 
          
          (attempt, fullMapping)
        }}
      }
      
      // check first if subgraph, otherwise if an extension
      // note that in both case the same attempt is preserved, just its contents are
      // updated in the case of extension
      subGraphOfAttempt orElse extensionOfAttempt
    }

    def validRootPairs(roots1: Set[ProofEntry],
                       roots2: Set[ProofEntry]): Stream[(ProofEntry, ProofEntry)] =
      for {
        r1 <- roots1.toStream
        r2 <- roots2
        if matcher.matchProofEntry(r1, r2)
      } yield (r1, r2)

    def matchFirstSubGraph(rootPairs: Stream[(ProofEntry, ProofEntry)],
                           graph: PPGraph[ProofEntry],
                           subGraph: PPGraph[ProofEntry]): Option[(Map[ProofEntry, ProofEntry], Set[ProofEntry])] = {

      val matches = rootPairs flatMap {
        case (gRoot, sRoot) =>
          matchSubGraph((graph, gRoot), (subGraph, sRoot))
      }

      // take the first one available
      matches.headOption
    }

    def matchSubGraph(graphRoot: (PPGraph[ProofEntry], ProofEntry),
                      subGraphRoot: (PPGraph[ProofEntry], ProofEntry)): Option[(Map[ProofEntry, ProofEntry], Set[ProofEntry])] = {

      val (graph, gRoot) = graphRoot
      val (subGraph, sRoot) = subGraphRoot

      val isomorphism = VF2Isomorphism(graph, subGraph, Some(matcher.matchProofEntry _))
      // require roots to be mapped
      val initialMapping = Map(sRoot -> gRoot) //, aGraph get root2) FIXME

      val isom = isomorphism.fromInitial(initialMapping)

      // if subgraph, there may be unmapped nodes in the big graph
      (isom.isomorphism, isom.unmappedNodes) match {

        case (Some(mapping), Some(unmappedGNodes)) => {

          // if found, extract the node values from the mappings
          val valueMapping = mapping map { case (m, n) => (m.value, n.value) }
          val unmappedValues = unmappedGNodes map (_.value)

          Some(valueMapping, unmappedValues)
        }

        case _ => None
      }
    }

  }

  private def extendAttempt(originalGraph: PPGraph[ProofEntry],
                            extensionGraph: PPRootGraph[ProofEntry],
                            originalToExt: Map[ProofEntry, ProofEntry],
                            unmapped: Set[ProofEntry]): ProofElem = {

    // note that we need to reverse the mapping here, since it is from original to extension
    val extToOriginal = originalToExt map (_.swap)
    
    // extend the original graph with unmapped values
    val extendedGraph = extendPPGraph(originalGraph, extensionGraph.graph, extToOriginal, unmapped)
    
    // use extension roots, but replace with originals where mappings are available
    val extendedRoots = extensionGraph.roots map ( root => (extToOriginal.get(root) getOrElse root) )

    toPProcessTree(PPRootGraph(extendedGraph, extendedRoots))
  }

  private def extendPPGraph(originalGraph: PPGraph[ProofEntry],
                            extensionGraph: PPGraph[ProofEntry],
                            extToOriginal: Map[ProofEntry, ProofEntry],
                            unmapped: Set[ProofEntry]): PPGraph[ProofEntry] = {

    def addNeighbors(graph: PPGraph[ProofEntry], unmappedEntry: ProofEntry): PPGraph[ProofEntry] = {

      val entryNode = (extensionGraph get unmappedEntry)
      
      // link successors and predecessors
      val graphWithSuccs = addLinked(graph, entryNode,
        entryNode.diSuccessors, (e1, e2) => (e1 ~> e2))
        
      val graphWithPreds = addLinked(graphWithSuccs, entryNode,
        entryNode.diPredecessors, (e1, e2) => (e2 ~> e1))

      graphWithPreds
    }
    
    def addLinked(updateGraph: PPGraph[ProofEntry],
                  baseNode: extensionGraph.NodeT,
                  linkedNodes: Set[extensionGraph.NodeT],
                  edge: (ProofEntry, ProofEntry) => DiEdge[ProofEntry]): PPGraph[ProofEntry] = {

      // add the node to the graph
      val nodeGraph = updateGraph + baseNode.value

      // go through each node and add its links to the graph
      val edgeGraph = (linkedNodes foldLeft nodeGraph)( (graph, linked) => {

        val mapped = extToOriginal get linked.value
        // target value - either the mapped one, or linked from original graph
        val targetValue = mapped getOrElse linked.value

        // add as an edge to the graph
        graph + edge(baseNode.value, targetValue)
      })

      edgeGraph
    }

    // extend the original graph with unmapped values
    (unmapped foldLeft originalGraph)( addNeighbors )
  }
  
}
