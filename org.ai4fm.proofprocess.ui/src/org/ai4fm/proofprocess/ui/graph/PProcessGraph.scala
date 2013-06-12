package org.ai4fm.proofprocess.ui.graph

import scala.collection.JavaConversions._

import org.ai4fm.proofprocess.Attempt
import org.ai4fm.proofprocess.Proof
import org.ai4fm.proofprocess.ProofElem
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ProofParallel
import org.ai4fm.proofprocess.ProofSeq
import org.ai4fm.proofprocess.ProofStore
import org.eclipse.emf.ecore.EObject

/** Calculates ProofProcess graph structure for different elements.
  * 
  * The graph is represented as a map of elements to their children.
  * 
  * @author Andrius Velykis
  */
object PProcessGraph {

  def proofTreeGraph(elem: ProofElem): Map[ProofElem, List[ProofElem]] = {

    val emptyGraph = Map[ProofElem, List[ProofElem]]()
    
    def graphEntries(entries: List[ProofElem], cont: List[ProofElem]): (List[ProofElem], Map[ProofElem, List[ProofElem]]) = {
      entries.foldRight(cont, emptyGraph) {
        case (entry, (cont, graph)) => {
          val (entryCont, entryGraph) = proofGraph(entry, cont)
          (entryCont, graph ++ entryGraph)
        }
      }
    }

    def children(elem: ProofElem): List[ProofElem] = elem match {
      case seq: ProofSeq => seq.getEntries.toList
      case par: ProofParallel => par.getEntries.toList
    }

    def proofGraph(elem: ProofElem, cont: List[ProofElem]): (List[ProofElem], Map[ProofElem, List[ProofElem]]) = elem match {
      // TODO comments
      case entry: ProofEntry => (List(entry), Map(entry -> cont))
      case seq: ProofSeq => {
        val (entriesCont, entriesGraph) = seq.getEntries.toList.foldRight(cont, emptyGraph) {
          case (entry, (cont, graph)) => {
            val (entryCont, entryGraph) = proofGraph(entry, cont)
            (entryCont, graph ++ entryGraph)
          }
        }

        (seq :: entriesCont, entriesGraph + (seq -> (entriesCont ::: cont)))
      }
      case par: ProofParallel => {
        val branchInfos = par.getEntries.toList.map(e => proofGraph(e, cont))
        
        val branchCont = branchInfos.map(_._1).flatten
        val branchGraph = branchInfos.map(_._2).foldRight(emptyGraph)(_ ++ _)
        
        (elem :: branchCont, branchGraph + (elem -> (branchCont ::: cont)))
      }
    }
    
    proofGraph(elem, Nil)._2
  }

  def attemptGraph(attempt: Attempt): Map[EObject, List[EObject]] = {
    val proofOpt = Option(attempt.getProof)
    val proofMap = proofOpt.map(proofTreeGraph).getOrElse(Map())
    proofMap ++ Map(attempt -> proofOpt.toList)
  }

  def proofGraph(proof: Proof): Map[EObject, List[EObject]] = {

    val attempts = proof.getAttempts.toList
    val attemptsGraph = attempts.foldRight[Map[EObject, List[EObject]]](Map()) {
      case (attempt, graph) => graph ++ attemptGraph(attempt)
    }

    attemptsGraph + (proof -> attempts)
  }

  def storeGraph(proofStore: ProofStore): Map[EObject, List[EObject]] = {

    val proofs = proofStore.getProofs.toList
    val proofsGraph = proofs.foldRight[Map[EObject, List[EObject]]](Map()) {
      case (proof, graph) => graph ++ proofGraph(proof)
    }

    proofsGraph + (proofStore -> proofs)
  }
  
}
