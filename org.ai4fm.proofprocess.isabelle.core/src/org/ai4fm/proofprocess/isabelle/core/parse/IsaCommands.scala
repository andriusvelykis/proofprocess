package org.ai4fm.proofprocess.isabelle.core.parse

import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.isabelle.IsabelleCommand
import org.ai4fm.proofprocess.isabelle.NamedTermTree
import scala.collection.JavaConversions._

/** Extractor objects for common Isabelle commands.
  * 
  * @author Andrius Velykis
  */
object IsaCommands {

  private type TList = List[Term]

  object Auto {
    def unapply(branch: NamedTermTree): Option[(TList, TList, TList)] =
      autoNamedFacts(branch, "HOL.auto")
  }
  
  object Force {
    def unapply(branch: NamedTermTree): Option[(TList, TList, TList)] =
      autoNamedFacts(branch, "HOL.force")
  }
  
  object Blast {
    def unapply(branch: NamedTermTree): Option[(TList, TList)] =
      autoNamedFacts(branch, "HOL.blast") map { case (simp, intro, dest) => (intro, dest) }
  }
  
  object Simp {
    def unapply(branch: NamedTermTree): Option[(TList, TList, Option[TList])] =
      namedBranch(branch, "HOL.simp") map { branch =>
      val facts = namedFacts(branch)
      def get(name: String) = facts.getOrElse(name, Nil);

      (get("add"), get("del"), facts.get("only"))
    }
  }
  
  object Metis extends NamedBranchTerms("Metis.metis")
  object SubgoalTac extends NamedBranchTerms("Pure.subgoal_tac")
  object CaseTac extends NamedBranchTerms("HOL.subgoal_tac")
  object Rule extends NamedBranchTerms("HOL.rule")
  object ERule extends NamedBranchTerms("Pure.erule")
  object FRule extends NamedBranchTerms("Pure.frule")
  
  object Intro extends NamedBranchTerms("Pure.intro")
  object Elim extends NamedBranchTerms("Pure.elim")
  
  object Induct {
    // rules, args, arbitrary, taking
    def unapply(branch: NamedTermTree): Option[(TList, TList, TList, TList)] =
      namedBranch(branch, "HOL.induct") map { branch =>
      val facts = namedFacts(branch)
      def get(name: String) = facts.getOrElse(name, Nil);

      (get("rule"), branch.getTerms toList, get("arbitrary"), get("taking"))
    }
  }
  
  object Apply {
    def unapply(cmd: IsabelleCommand): Option[List[NamedTermTree]] = cmd.getName match {
      case "apply" => Some(cmd.getBranches toList)
      case _ => None
    }
  }
  
  object Apply1 {
    def unapply(cmd: IsabelleCommand): Option[NamedTermTree] = cmd match {
      case Apply(methods) if methods.size == 1 => Some(methods.head)
      case _ => None
    }
  }
  
  object ProofMethCommand {
    def unapply(cmd: IsabelleCommand): Option[List[NamedTermTree]] = cmd.getName match {
      case "apply" | "by" | "proof" => Some(cmd.getBranches toList)
      case _ => None
    }
  }
  
  def namedBranch[T <: NamedTermTree](branch: T, name: String): Option[T] =
    Option(branch) filter {_.getName() == name}
  
  def namedBranchTerms(branch: NamedTermTree, name: String): Option[TList] =
    namedBranch(branch, name) map { _.getTerms toList }

  def autoNamedFacts(branch: NamedTermTree, name: String): Option[(TList, TList, TList)] =
    namedBranch(branch, name) map { branch =>
      val facts = namedFacts(branch)
      def get(name: String) = facts.getOrElse(name, Nil);

      (get("simp"), get("intro"), get("dest"))
    }
  
  def namedFacts(parent: NamedTermTree): Map[String, List[Term]] =
    parent.getBranches map {br => (br.getName, br.getTerms toList )} toMap

  abstract class NamedBranchTerms(val name: String) {
    def unapply(branch: NamedTermTree): Option[TList] = namedBranchTerms(branch, name)
  }
  
}
