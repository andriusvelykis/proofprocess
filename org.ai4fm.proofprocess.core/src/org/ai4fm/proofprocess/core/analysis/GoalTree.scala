package org.ai4fm.proofprocess.core.analysis

sealed trait GoalTree[A]
case class GoalEntry[A, T](info: A, inGoals: List[T], outGoals: List[T]) extends GoalTree[A]
case class GoalSeq[A](seq: List[GoalTree[A]]) extends GoalTree[A]
case class GoalParallel[A](par: List[GoalTree[A]]) extends GoalTree[A]
