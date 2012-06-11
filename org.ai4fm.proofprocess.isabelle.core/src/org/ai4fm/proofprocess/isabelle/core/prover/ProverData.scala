package org.ai4fm.proofprocess.isabelle.core.prover

import isabelle.Term.Term

object ProverData {
  
  sealed abstract class TermRef
  case class IsaTerm(term: Term) extends TermRef
  case class StrTerm(str: String) extends TermRef
  
  // Proof state
  // need to know about "used" assumptions etc.
  case class ProofState(fixes: List[String], assumptions: List[(String, TermRef)], goal: TermRef)

  sealed abstract class ThmName
  case class Thm(name: String) extends ThmName
  case class Hyp(name: String) extends ThmName
  
  sealed abstract class ATac
  case class Auto(simp: List[ThmName], intro: List[ThmName], dest: List[ThmName]) extends ATac
  case class Simp(add: List[ThmName], del: List[ThmName], only: Option[List[ThmName]]) extends ATac
  case class Conj(term: TermRef) extends ATac // subgoal tac (and have statement?)
  case class Blast(dest: List[ThmName], intro: List[ThmName]) extends ATac
  case class Force(simp: List[ThmName], intro: List[ThmName], dest: List[ThmName]) extends ATac
  case class Metis(thms: List[ThmName]) extends ATac
  case class Induction(rule: Option[ThmName], arg: Option[TermRef]) extends ATac // not sure about args here
  case class UnknownTac(source: String) extends ATac
  
  sealed abstract class Meth
  case class Rule(rule: ThmName) extends Meth
  case class Erule(assumption: Option[ThmName], rule: ThmName) extends Meth // which assumption + thm
  case class Frule(assumption: Option[ThmName], rule: ThmName) extends Meth // which assumption + thm
  case class SubstThm(rule: ThmName) extends Meth // rule used
  case class SubstAsmThm(assumption: ThmName, rule: ThmName) extends Meth // rule used
  case class SubstUsingAsm(assumption: ThmName) extends Meth // which assumption in list
  case class Case(term: TermRef) extends Meth // term which case is applied for
  case class Tactic(tactic: ATac) extends Meth
  case class Using(facts: List[ThmName], meth: Meth) extends Meth
  case class Unknown(source: String) extends Meth
  
  case class Why(why: String, meth: Meth)
  
  sealed abstract class ProofTree
  case class Gap() extends ProofTree
  case class Proof(why: Why, goals: List[ProofState], cont: ProofTree) extends ProofTree
  case class Failure(failures: List[ProofTree], valid: Option[ProofTree]) extends ProofTree
  
//  case class ProofGoal(state: ProofState, cont: ProofTree)
  
    object Encode {

    import isabelle.Markup
    import isabelle.Term_XML
    import isabelle.XML._
    
    def encodeTerm(term: TermRef) = term match {
      case IsaTerm(t) => elem ("Term", Term_XML.Encode.term(t))
      case StrTerm(s) => Elem(Markup("TermStr", List(("val", s))), Nil)
    }

    def encodeStringPair(s: String, t: TermRef) = Elem(Markup("Pair", List(("name", s))), List(encodeTerm(t)))

    def encodeAssocL(als: List[(String, TermRef)]) = elem("AssocList", als.map(Function.tupled(encodeStringPair)))

    def encodePS(state: ProofState) = elem("PS", List(encodeAssocL(state.assumptions), encodeTerm(state.goal)))

    def encodeThmName(thmName: ThmName) = thmName match {
      case Thm(s) => Elem(Markup("Thm", List(("name", s))), Nil)
      case Hyp(s) => Elem(Markup("Hyp", List(("name", s))), Nil)
    }
    
    def encodeOpts(opt: Option[Body]) = opt match {
      case None => elem("NONE")
      case Some(body) => elem("SOME", body)
    }
    
    def encodeOpt(opt: Option[Tree]) = encodeOpts(opt.map(List(_)))
    
    def thmElem(name: String, thm: ThmName) = elem(name, List(encodeThmName(thm)))
    def thmsElem(name: String, thms: List[ThmName]) = elem(name, thms map encodeThmName)
    
    def optThmElem(name: String, thm: Option[ThmName]) = 
      elem(name, List(encodeOpt(thm map encodeThmName)))
    def optThmsElem(name: String, thms: Option[List[ThmName]]) =
      elem(name, List(encodeOpts(thms map { _ map encodeThmName })))
    
    def encodeTac(tac: ATac) = tac match {
      case Auto(simp, intro, dest) => elem("Auto", List(
          thmsElem("Simp", simp),
          thmsElem("Intro", intro),
          thmsElem("Dest", dest)))
      case Simp(add, del, only) => elem("Simp", List(
          thmsElem("Add", add),
          thmsElem("Del", del),
          optThmsElem("Only", only)))
      case Conj(term) => elem("Conj", List(encodeTerm(term)))
      case Blast(dest, intro) => elem("Blast", List(
          thmsElem("Intro", intro),
          thmsElem("Dest", dest)))
      case Force(simp, intro, dest) => elem("Force", List(
          thmsElem("Simp", simp),
          thmsElem("Intro", intro),
          thmsElem("Dest", dest)))
      case Metis(thms) => thmsElem("Metis", thms)
      case Induction(rule, arg) => elem("Induction", List(
          optThmElem("Rule", rule),
          elem("Arg", List(encodeOpt(arg map encodeTerm)))))
      case UnknownTac(s) => Elem(Markup("UnknownTac", List(("val", s))), Nil)
    }
    
    def encodeMeth(meth: Meth): Tree = meth match {
      case Rule(thm) => thmElem("Rule", thm)
      case Erule(asm, thm) => elem("Erule", List(
          optThmElem("Assumption", asm),
          thmElem("Theorem", thm)))
      case Frule(asm, thm) => elem("Frule", List(
          optThmElem("Assumption", asm),
          thmElem("Theorem", thm)))
      case SubstThm(thm) => thmElem("Subst_thm", thm)
      case SubstAsmThm(asm, thm) => elem("Subst_asm_thm", List(
          thmElem("Assumption", asm),
          thmElem("Theorem", thm)))
      case SubstUsingAsm(thm) => thmElem("Subst_using_asm", thm)
      case Case(term) => elem("Subst_using_asm", List(encodeTerm(term)))
      case Tactic(tac) => elem("Tactic", List(encodeTac(tac)))
      case Using(thms, meth) => elem("Using", List(
          thmsElem("Thms", thms),
          elem("Method", List(encodeMeth(meth)))))
      case Unknown(str) => Elem(Markup("Unknown", List(("name", str))), Nil)
    }
    
    def encodeWhy(w: Why) = Elem(Markup("Why", List(("why_info", w.why))), List(encodeMeth(w.meth)))
    
    def encodePT(pt: ProofTree): Tree = pt match {
      case Gap() => elem("Gap")
      case Proof(why, goals, cont) => elem("Proof", List(
          encodeWhy(why),
          elem("Goals", goals map encodePS),
          encodePT(cont)))
      case Failure(failures, valid) => elem("Failure", List(
          elem("Failures", failures map encodePT),
          elem("Valid", List(encodeOpt(valid map encodePT)))))
    }
    
  }
  
}
