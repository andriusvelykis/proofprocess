package org.ai4fm.proofprocess.isabelle.core.prover

import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.isabelle.IsabelleCommand
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
  
  def command(cmd: IsabelleCommand): Meth = {
    val res = cmd match {
      case isa.Apply1(method) => method match {
        case isa.Auto(simp, intro, dest) => Some(Tactic(Auto(thms(simp), thms(intro), thms(dest))))
        case isa.Force(simp, intro, dest) => Some(Tactic(Force(thms(simp), thms(intro), thms(dest))))
        case isa.Blast(intro, dest) => Some(Tactic(Blast(thms(dest), thms(intro))))
        case isa.Simp(add, del, only) => Some(Tactic(Simp(thms(add), thms(del), only.map(thms _))))
        case isa.Metis(facts) => Some(Tactic(Metis(thms(facts))))
        case isa.SubgoalTac(facts) => Some(Tactic(Conj(terms(facts).head)))
//        case isa.Induct(rules, args, _, _) => Some(Tactic(Induction(thms(rules).head, terms(args).head)))
        case _ => None 
      }
      // TODO multiple branches (e.g. apply (auto, simp))
      case isa.Apply(methods) => None
      // TODO support others (e.g. "unfolding" etc)
      case _ => None
    }

    res.getOrElse(Unknown(cmd.getSource))
  }
  
  def thms(terms: List[Term]): List[ThmName] =
    // TODO support insts
    terms.map({ case t: NameTerm => Thm(t.getName) })
    
  def terms(terms: List[Term]): List[isabelle.Term.Term] =
    // TODO something about markup terms?
    terms.map({ case t: IsaTerm => t.getTerm })
    
}