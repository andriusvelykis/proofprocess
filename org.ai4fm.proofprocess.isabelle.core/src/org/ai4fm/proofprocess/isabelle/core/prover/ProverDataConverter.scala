package org.ai4fm.proofprocess.isabelle.core.prover

import org.ai4fm.proofprocess.Attempt
import org.ai4fm.proofprocess.ProofDecor
import org.ai4fm.proofprocess.ProofElem
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ProofParallel
import org.ai4fm.proofprocess.ProofSeq
import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.isabelle.IsabelleCommand
import org.ai4fm.proofprocess.isabelle.IsabelleTrace
import org.ai4fm.proofprocess.isabelle.IsaTerm
import org.ai4fm.proofprocess.isabelle.NameTerm
import org.ai4fm.proofprocess.isabelle.core.parse.{IsaCommands => isa}
import org.ai4fm.proofprocess.isabelle.core.prover.ProverData._
import scala.collection.JavaConversions._


/** Converters from ProofProcess data to ProverData objects to send to Isabelle.
  *  
  * @author Andrius Velykis
  */
object ProverDataConverter {
  
  private type ITerm = isabelle.Term.Term
  
  def attempt(attempt: Attempt): ProofTree = {
    val rootElem = attempt.getProof
    proofTree(rootElem, Gap())
  }
  
  private def proofTree(elem: ProofElem, cont: ProofTree) : ProofTree = elem match {
    
    case e: ProofEntry => {
      val (why, goals) = entry(e)
      val goalStates = goals.map(nameGoalState)
      Proof(why, goalStates, cont)
    }
    case d: ProofDecor => proofTree(d.getEntry, cont)
    case s: ProofSeq => s.getEntries.foldRight(cont)(proofTree)
    // TODO special handling for parallel?
    case p: ProofParallel => p.getEntries.foldRight(cont)(proofTree)
  }
  
  def entry(entry: ProofEntry): (Why, List[ITerm]) = {
    val intentName = entry.getInfo.getIntent.getName
    
    val proofStep = entry.getProofStep
    // how about out goals?
    val inGoals = terms(proofStep.getInGoals.toList)
    
    val commandOpt = proofStep.getTrace match {
      case isaTrace: IsabelleTrace => Some(command(isaTrace.getCommand))
      case _ => None
    }
    
    val cmd = commandOpt getOrElse {Unknown(String.valueOf(proofStep.getTrace))}
    
    (Why(intentName, cmd), inGoals)
  }
  
  private def nameGoalState(term: ITerm): ProofState = {
    // for now just pack the term into the proof state without naming assumptions/fixes
    // TODO name assumptions
    ProofState(Nil, Nil, term)
  }
  
  def command(cmd: IsabelleCommand): Meth = {
    val res = cmd match {
      // TODO multiple branches (e.g. apply (auto, simp))
      case isa.ProofMethCommand(methods) if methods.size == 1 => methods.head match {
        case isa.Auto(simp, intro, dest) => Some(Tactic(Auto(thms(simp), thms(intro), thms(dest))))
        case isa.Force(simp, intro, dest) => Some(Tactic(Force(thms(simp), thms(intro), thms(dest))))
        case isa.Blast(intro, dest) => Some(Tactic(Blast(thms(dest), thms(intro))))
        case isa.Simp(add, del, only) => Some(Tactic(Simp(thms(add), thms(del), only.map(thms _))))
        case isa.Metis(facts) => Some(Tactic(Metis(thms(facts))))
        case isa.SubgoalTac(facts) => Some(Tactic(Conj(terms(facts).head)))
        case isa.Induct(rules, args, _, _) => Some(Tactic(Induction(thms(rules).headOption, terms(args).headOption)))
        case isa.Rule(facts) => Some(Rule(thms(facts).head))
        case isa.Intro(facts) => Some(Rule(thms(facts).head))
        case isa.ERule(facts) => Some(Erule(None, thms(facts).head))
        case isa.Elim(facts) => Some(Erule(None, thms(facts).head))
        case isa.FRule(facts) => Some(Frule(None, thms(facts).head))
        case _ => None 
      }
      // TODO support others (e.g. "unfolding" etc)
      case _ => None
    }

    res.getOrElse(Unknown(cmd.getSource))
  }
  
  def thms(terms: List[Term]): List[ThmName] =
    // TODO support insts
    terms.map({ case t: NameTerm => Thm(t.getName) })
    
  def terms(terms: List[Term]): List[ITerm] =
    // TODO something about markup terms?
    terms.map({ case t: IsaTerm => t.getTerm })
    
}
