package org.ai4fm.proofprocess.isabelle.core.prover

import java.util.UUID

import scala.collection.JavaConverters.asScalaBufferConverter

import org.ai4fm.proofprocess.Attempt
import org.ai4fm.proofprocess.DisplayTerm
import org.ai4fm.proofprocess.{Proof => PPProof}
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.core.graph.EmfPProcessTree
import org.ai4fm.proofprocess.core.graph.FoldableGraph.toFoldableGraph
import org.ai4fm.proofprocess.isabelle.{IsaTerm => PPIsaTerm}
import org.ai4fm.proofprocess.isabelle.IsabelleCommand
import org.ai4fm.proofprocess.isabelle.IsabelleTrace
import org.ai4fm.proofprocess.isabelle.JudgementTerm
import org.ai4fm.proofprocess.isabelle.NameTerm
import org.ai4fm.proofprocess.isabelle.NamedTermTree
import org.ai4fm.proofprocess.isabelle.core.parse.{IsaCommands => isa}
import org.ai4fm.proofprocess.isabelle.core.prover.ProverData._
import org.eclipse.emf.ecore.util.EcoreUtil

import isabelle.Symbol
import isabelle.eclipse.core.IsabelleCore


/** Converters from ProofProcess data to ProverData objects to send to Isabelle.
  *  
  * @author Andrius Velykis
  */
object ProverDataConverter {
  
  private def why(entry: ProofEntry): Why = {
    val intentName = Option(entry.getInfo.getIntent).map(_.getName).getOrElse("")
    
    val proofStep = entry.getProofStep
    val commandOpt = proofStep.getTrace match {
      case isaTrace: IsabelleTrace => Some(command(isaTrace.getCommand))
      case _ => None
    }
    
    val cmd = commandOpt getOrElse { Tac(String.valueOf(proofStep.getTrace)) }
    
    Why(intentName, cmd)
  }

  private def goalProofState(term: Term): ProofState = term match {
    case j: JudgementTerm => {
      val assms = j.getAssms.asScala.toList
      val namedAssms = assms.zipWithIndex map { case (assm, i) => ("assm" + i, encodeTerm(assm)) }
      val goal = encodeTerm(j.getGoal)
      // TODO support fixes!
      ProofState(Nil, namedAssms, goal)
    }

    case _ => {
      val encoded = encodeTerm(term)
      // for now just pack the term into the proof state without naming assumptions/fixes
      ProofState(Nil, Nil, encoded)
    }
  }

  def attempt(proof: PPProof, attempt: Attempt): (String, List[ProofGoal]) = {
    val attemptRoot = attempt.getProof
    val ppGraph = EmfPProcessTree.graphConverter.toGraph(attemptRoot)

    val emptyMap = Map[ProofEntry, List[ProofGoal]]()
    val subGraphs = (ppGraph.graph foldNodesRight emptyMap)({
      case ((node, preds, succs), subGraphs) => {
        val branches = succs.toList map subGraphs
        
        val w = why(node)

        // TODO relies on only the goal being set..
        val handledGoals = succs.toList map { _.getProofStep.getInGoals.asScala.toList }
        val outGoals = node.getProofStep.getOutGoals.asScala.toList
        val unhandledGoals = diffTerms(outGoals, handledGoals.flatten)

        val unhandledStates = unhandledGoals map goalProofState
        val unhandledBranches = unhandledStates map { state => ProofGoal(state, Gap()) }

        val allBranches = branches.flatten ::: unhandledBranches
        val proof = Proof(w, allBranches)

        val inGoals = node.getProofStep.getInGoals.asScala.toList
        
        // TODO the may be more inGoals than branches.. e.g. if "apply auto" handles
        // multiple branches.
        // Need to handle this somehow, e.g. by duplicating branches with multiple inGoals?
        // Note that this only works if there is no outGoals, otherwise we cannot match inGoals
        // with resulting outGoals

        // note that this produces duplicate trees for mid-proof "auto" command.
        // So for correct execution, such auto commands should be avoided or limited to
        // a single goal, i.e. "apply (auto)[1]"
        val inStates = inGoals map goalProofState
        val stepGoals = inStates map { state => ProofGoal(state, proof) }
        
        subGraphs + (node -> stepGoals)
      }
    })(ppGraph.roots)
    
    // after the down->up traversal, find the subgraph for the roots
    val rootGoals0 = ppGraph.roots.toList map subGraphs
    val rootGoals = rootGoals0.flatten

    val proofLabel = Option(proof.getLabel) filterNot (_.isEmpty) 
    val label = proofLabel getOrElse "Untitled proof " + UUID.randomUUID.toString

    (label, rootGoals)
  }

  // need a custom diff, because EMF objects do not implement .equals()
  private def diffTerms(l1: List[Term], l2: List[Term]): List[Term] = l1 match {
    case Nil => Nil
    case t1 :: ts1 => {
      val (found, ts2) = removeTerm(l2, t1)
      if (found) {
        diffTerms(ts1, ts2)
      } else {
        t1 :: diffTerms(ts1, l2)
      }
    }
  }

  private def removeTerm(terms: List[Term], target: Term): (Boolean, List[Term]) =
    terms match {
    case Nil => (false, Nil)
    case t :: ts =>
    if (eqTerms(t, target)) {
      (true, ts)
    } else {
      val (found, rest) = removeTerm(ts, target)
      (found, t :: rest)
    }
  }

  private def eqTerms(t1: Term, t2: Term): Boolean =
    EcoreUtil.equals(t1, t2)
  
  private def command(cmd: IsabelleCommand): Tac = {
    val res = cmd match {
      // TODO multiple branches (e.g. apply (auto, simp))
      case isa.ProofMethCommand(tacs) if tacs.size == 1 => {
        val tac = tacs.head
        val tacName = tac.getName
        val termArgs = tac.getTerms.asScala.toList map encodeTermStr

        val branches = tac.getBranches.asScala.toList map argBranch

        val allArgs = (termArgs :: branches) filterNot (_.isEmpty)

        Some(Tac(tacName, allArgs))
      }
      // TODO support others (e.g. "unfolding" etc)
      case _ => None
    }

    res getOrElse (Tac(cmd.getSource))
  }

  private def argBranch(branch: NamedTermTree): List[String] = {
    val branchName = branch.getName
    val branchArgs = branch.getTerms.asScala.toList map encodeTermStr
    
    // deeper branches not supported?

    // just join the name with args
    branchName :: branchArgs
  }

  private def encodeTermStr(t: Term): String = t match {
    // use StrTerm always for now
    case t: PPIsaTerm => encode(t.getDisplay) //IsaTerm(t.getTerm)
    case s: DisplayTerm => encode(s.getDisplay)
    case nt: NameTerm => encode(nt.getName)
    case _ => "<unsupported term " + t.getClass.getName + ">"
  }

  private def encodeTerm(t: Term): TermRef = StrTerm(encodeTermStr(t))
    
  // TODO move encoding to capture process?
  // E.g. so that we could access and convert the data without running Isabelle
  private def encode(termStr: String): String =
    if (IsabelleCore.isabelle.isInit) Symbol.encode(termStr) else termStr
    
}
