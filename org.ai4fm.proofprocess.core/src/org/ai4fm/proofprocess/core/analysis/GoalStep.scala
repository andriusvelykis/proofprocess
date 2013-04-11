package org.ai4fm.proofprocess.core.analysis

/**
 * Encapsulates a goal step: the step contents A, two lists of propositions (either just
 * assumptions or full judgements (assms => goal), as terms T. The lists represent
 * incoming propositions (consumed by the step) and outgoing (produced by the step).
 *
 * So a step can consume some judgements (goals) and produce some, e.g. a rewrite step.
 * It can also produce assumptions for forward reasoning, or consume assumptions
 * (case not yet fully identified).
 * 
 * The propositions require terms to be equatable using `==` (see trait Eq)
 * 
 * @author Andrius Velykis
 */
case class GoalStep[+A, +T <: Eq](info: A,
                                  in: List[Proposition[T]],
                                  out: List[Proposition[T]])

sealed trait Proposition[+T <: Eq]
case class Assumption[+T <: Eq](assm: T) extends Proposition[T]
case class Judgement[+T <: Eq](assms: List[T], goal: T) extends Proposition[T]
