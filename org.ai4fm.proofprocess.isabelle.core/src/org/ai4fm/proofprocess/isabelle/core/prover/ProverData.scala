package org.ai4fm.proofprocess.isabelle.core.prover

import isabelle.Term.Term
import isabelle.XML

object ProverData {
  
  sealed abstract class TermRef
  case class IsaTerm(term: Term) extends TermRef
  case class StrTerm(str: String) extends TermRef
  
  // Proof state
  // need to know about "used" assumptions etc.
  case class ProofState(fixes: List[String], assumptions: List[(String, TermRef)], goal: TermRef)

  case class Tac(name: String, args: List[List[String]] = Nil)
  
  case class Why(why: String, tac: Tac)
  
  sealed abstract class ProofTree
  case class Gap() extends ProofTree
  // in Proof, the goals represent proof state after the step
  case class Proof(why: Why, goals: List[ProofGoal]) extends ProofTree
  case class Failure(failures: List[List[ProofGoal]],
                     valid: Option[List[ProofGoal]]) extends ProofTree
  
  // ProofGoal is the start of a proof tree/subtree (e.g. initial goals)
  case class ProofGoal(state: ProofState, cont: ProofTree)
  
  object Encode {

    import isabelle.Markup
    import isabelle.Term_XML
    import isabelle.XML._
    
    def encodeTerm(term: TermRef) = term match {
      case IsaTerm(t) => elem ("IsaTerm", Term_XML.Encode.term(t))
      case StrTerm(s) => Elem(Markup("StrTerm", List(("val", s))), Nil)
    }

    def encodeStringPair(s: String, t: TermRef) = Elem(Markup("Pair", List(("name", s))), List(encodeTerm(t)))

    def encodeAssocL(als: List[(String, TermRef)]) = elem("AssocList", als.map(Function.tupled(encodeStringPair)))

    def encodePS(state: ProofState) = elem("PS", List(encodeAssocL(state.assumptions), encodeTerm(state.goal)))
    
    def encodeOpts(opt: Option[Body]) = opt match {
      case None => elem("NONE")
      case Some(body) => elem("SOME", body)
    }
    
    def encodeOpt(opt: Option[Tree]) = encodeOpts(opt.map(List(_)))

    def encodeTacArgs(args: List[String]) = elem ("Arg", args map XML.Text)
    
    def encodeTac(tac: Tac) =
      Elem(Markup("Tac", List(("val", tac.name))), tac.args map encodeTacArgs)
    
    def encodeWhy(w: Why) = Elem(Markup("Why", List(("why_info", w.why))), List(encodeTac(w.tac)))

    def encodePGs(goals: List[ProofGoal]) = elem("Goals", goals map encodePG)

    def encodePT(pt: ProofTree): Tree = pt match {
      case Gap() => elem("Gap")
      case Proof(why, goals) => elem("Proof", List(
          encodeWhy(why),
          encodePGs(goals)))
      case Failure(failures, valid) => elem("Failure", List(
          elem("Failures", failures map encodePGs),
          elem("Valid", List(encodeOpt(valid map encodePGs)))))
    }
    
    def encodePG(g: ProofGoal) = elem("Goal", List(encodePS(g.state), encodePT(g.cont)))

    def encodeLemma(name: String, goals: List[ProofGoal]) =
      Elem(Markup("Lemma", List(("name", name))), goals map encodePG)

    def encodeTheory(theory: Map[String, List[ProofGoal]]) =
      elem("Theory", theory.toList map Function.tupled(encodeLemma))
    
  }
  
}
