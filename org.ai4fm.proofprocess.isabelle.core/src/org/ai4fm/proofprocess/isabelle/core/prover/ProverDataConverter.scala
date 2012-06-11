package org.ai4fm.proofprocess.isabelle.core.prover

import isabelle.Markup
import isabelle.Term_XML
import isabelle.XML
import org.ai4fm.proofprocess.Attempt
import org.ai4fm.proofprocess.ProofDecor
import org.ai4fm.proofprocess.ProofElem
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ProofParallel
import org.ai4fm.proofprocess.ProofSeq
import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.isabelle.DisplayTerm
import org.ai4fm.proofprocess.isabelle.IsabelleCommand
import org.ai4fm.proofprocess.isabelle.IsabelleTrace
import org.ai4fm.proofprocess.isabelle.{IsaTerm => PPIsaTerm}
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
  
  def entry(entry: ProofEntry): (Why, List[TermRef]) = {
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
    
  def terms(terms: List[Term]): List[TermRef] =
    // TODO something about markup terms?
    terms.map({
      case t: PPIsaTerm => IsaTerm(t.getTerm)
      case s: DisplayTerm => StrTerm(s.getDisplay)
    })

    
  object Encode {

    import XML._
    
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
