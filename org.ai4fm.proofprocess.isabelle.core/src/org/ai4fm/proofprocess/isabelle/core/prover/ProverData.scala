package org.ai4fm.proofprocess.isabelle.core.prover

import isabelle.Term.Term

object ProverData {
  
  // Proof state
  // need to know about "used" assumptions etc.
  case class ProofState(fixes: List[String], assumptions: List[(String, Term)], goal: Term)

  sealed abstract class ThmName
  case class Thm(name: String) extends ThmName
  case class Hyp(name: String) extends ThmName
  
  sealed abstract class ATac
  case class Auto(simp: List[ThmName], intro: List[ThmName], dest: List[ThmName]) extends ATac
  case class Simp(add: List[ThmName], del: List[ThmName], only: Option[List[ThmName]]) extends ATac
  case class Conj(term: Term) extends ATac // subgoal tac (and have statement?)
  case class Blast(dest: List[ThmName], intro: List[ThmName]) extends ATac
  case class Force(simp: List[ThmName], intro: List[ThmName], dest: List[ThmName]) extends ATac
  case class Metis(thms: List[ThmName]) extends ATac
  case class Induction(rule: Option[ThmName], arg: Option[Term]) extends ATac // not sure about args here
  case class UnknownTac(source: String) extends ATac
  
  sealed abstract class Meth
  case class Rule(rule: ThmName) extends Meth
  case class Erule(assumption: Option[ThmName], rule: ThmName) extends Meth // which assumption + thm
  case class Frule(assumption: Option[ThmName], rule: ThmName) extends Meth // which assumption + thm
  case class SubstThm(rule: ThmName) extends Meth // rule used
  case class SubstAsmThm(assumption: ThmName, rule: ThmName) extends Meth // rule used
  case class SubstUsingAsm(assumption: ThmName) extends Meth // which assumption in list
  case class Case(term: Term) extends Meth // term which case is applied for
  case class Tactic(tactic: ATac) extends Meth
  case class Using(facts: List[ThmName], meth: Meth) extends Meth
  case class Unknown(source: String) extends Meth
  
  case class Why(why: String, meth: Meth)
  
  sealed abstract class ProofTree
  case class Gap() extends ProofTree
  case class Proof(why: Why, goals: List[ProofState], cont: ProofTree) extends ProofTree
  case class Failure(failures: List[ProofTree], valid: Option[ProofTree]) extends ProofTree
  
//  case class ProofGoal(state: ProofState, cont: ProofTree)
  
}
