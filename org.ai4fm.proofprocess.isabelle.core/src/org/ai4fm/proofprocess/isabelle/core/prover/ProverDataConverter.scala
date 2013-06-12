package org.ai4fm.proofprocess.isabelle.core.prover

import isabelle.Symbol
import isabelle.eclipse.core.IsabelleCore
import org.ai4fm.proofprocess.Attempt
import org.ai4fm.proofprocess.{Proof => PPProof}
import org.ai4fm.proofprocess.ProofElem
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ProofParallel
import org.ai4fm.proofprocess.ProofSeq
import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.isabelle.DisplayTerm
import org.ai4fm.proofprocess.isabelle.IsabelleCommand
import org.ai4fm.proofprocess.isabelle.IsabelleTrace
import org.ai4fm.proofprocess.isabelle.{IsaTerm => PPIsaTerm}
import org.ai4fm.proofprocess.isabelle.JudgementTerm
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
  
  def attempt(proof: PPProof, attempt: Attempt): ProofGoal = {
    val rootElem = attempt.getProof
    val (rootTree, inGoals) = proofTree(rootElem)
    
    val initialGoals = terms(proof.getGoals.toList)
    
    def goalState(cont: ProofTree)(goal: TermRef) = ProofGoal(nameGoalState(goal), cont)
    
    // check for goals not handled by the proof
    val additionalGaps = initialGoals.diff(inGoals).map(goalState(Gap()))
    
    val root = if (additionalGaps.isEmpty) {
      rootTree
    } else {
      // some of the initial goals may have been unhandled in the proof (e.g. unfinished proof on a single branch)
      Proof(Why("Unhandled initial goals", Unknown("")), inGoals.map(goalState(rootTree)) ::: additionalGaps)
    }
    
    // for the root goal, assume that proof has one initial goal
    goalState(root)(initialGoals.head)
  }
  
  // only works for nice single-goal trees at the moment
  private def proofTree(elem: ProofElem): (ProofTree, List[TermRef]) = elem match {
    
    case e: ProofEntry => {
      val (why, inGoals, outGoals) = entry(e)
      val goalStates = outGoals.map(nameGoalState)
      
      val proofGoals = goalStates.map(state => ProofGoal(state, Gap()))
      (Proof(why, proofGoals), inGoals)
    }
    case s: ProofSeq => s.getEntries.foldRight[(ProofTree, List[TermRef])]((Gap(), Nil)) {
      case (entry, (result, resultInGoals)) => {
        val (entryTree, inGoals) = proofTree(entry)
        (replaceEnds(result, resultInGoals)(entryTree, None), inGoals)
      }
    }
    // special handling for parallel
    case p: ProofParallel => {
      val branchInfos = p.getEntries.toList.map(proofTree)
      // do not support parallels with no inGoals - actually use just the first of inGoals
      // multiple inGoals not supported
      val branches = branchInfos.filterNot(_._2.isEmpty).map{case (entry, inGoals) => (entry, inGoals.head)}
      
      // package the branches into a Proof
      val inGoals = branches.map(_._2)
      val branchPGs = branches.map{ case (entry, inGoal) => ProofGoal(nameGoalState(inGoal), entry)}
      
      (Proof(Why("prover-data-converter-parallel", Unknown("")), branchPGs), inGoals)
    }
  }

  private def matchTerm(t1: TermRef, t2: TermRef): Boolean =
    t1 == t2
  
  private def replaceEnds(replacement: ProofTree, replInGoals: List[TermRef])(tree: ProofTree, outGoal: Option[TermRef]): ProofTree =
    (tree, replacement) match {
      // for goals, go deeper and replace each cont Gap() with the replacement
      case (Proof(why, proofGoals), _) => Proof(why, proofGoals.map(
        goal => ProofGoal(goal.state, replaceEnds(replacement, replInGoals)(goal.cont, Some(goal.state.goal)))))
      case (Gap(), Proof(Why("prover-data-converter-parallel", _), parGoals)) => {
        val replGoal = replInGoals.indexWhere(g => outGoal.filter(matchTerm(_, g)).isDefined)
        if (replGoal >= 0) {
          // use the parallel goal
          parGoals(replGoal).cont
        } else {
          // use the Gap
          Gap()
        }
      }
      case (Gap(), _) => replacement
      // skip all non-proofs here
      case _ => replacement
    }
  
  def entry(entry: ProofEntry): (Why, List[TermRef], List[TermRef]) = {
    val intentName = Option(entry.getInfo.getIntent).map(_.getName).getOrElse("")
    
    val proofStep = entry.getProofStep
    val inGoals = terms(proofStep.getInGoals.toList)
    val outGoals = terms(proofStep.getOutGoals.toList)
    
    val commandOpt = proofStep.getTrace match {
      case isaTrace: IsabelleTrace => Some(command(isaTrace.getCommand))
      case _ => None
    }
    
    val cmd = commandOpt getOrElse {Unknown(String.valueOf(proofStep.getTrace))}
    
    (Why(intentName, cmd), inGoals, outGoals)
  }
  
  private def nameGoalState(term: TermRef): ProofState = {
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
        case isa.CaseTac(facts) => Some(Case(terms(facts).head))
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
    
  def terms(ts: List[Term]): List[TermRef] =
    // TODO something about markup terms?
    ts.map({
      // use StrTerm always for now
      case t: PPIsaTerm => StrTerm(encode(t.getDisplay)) //IsaTerm(t.getTerm)
      case s: DisplayTerm => StrTerm(encode(s.getDisplay))
      // FIXME a bit of a hack (also handle assumptions!)
      case j: JudgementTerm => terms(List(j.getGoal)).head
    })
    
  // TODO move encoding to capture process?
  // E.g. so that we could access and convert the data without running Isabelle
  def encode(termStr: String): String =
    if (IsabelleCore.isabelle.isInit) Symbol.encode(termStr) else termStr
    
}
