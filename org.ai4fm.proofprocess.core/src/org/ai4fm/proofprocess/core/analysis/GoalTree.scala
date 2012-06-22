package org.ai4fm.proofprocess.core.analysis

sealed trait GoalTree[A, T]
case class GoalEntry[A, T](info: A, inGoals: List[T], outGoals: List[T]) extends GoalTree[A, T]
case class GoalSeq[A, T](seq: List[GoalTree[A, T]]) extends GoalTree[A, T]
case class GoalParallel[A, T](par: List[GoalTree[A, T]]) extends GoalTree[A, T]
