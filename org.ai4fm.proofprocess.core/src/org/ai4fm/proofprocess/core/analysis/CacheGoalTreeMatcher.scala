package org.ai4fm.proofprocess.core.analysis

/** A special case of [[org.ai4fm.proofprocess.core.analysis.GoalTreeMatcher]], which caches the
  * goals and only uses their identifiers to calculate the goal tree structure. This way it avoids
  * possibly expensive checks for each match and only performs matching initially.
  * 
  * This is also necessary for goals that use custom matching (instead of ==), e.g. EMF Terms.
  * The matcher function can be provided to establish "same" goals. Note that some of the matching
  * original goals are not preserved in this manner - the single "matching" goal is used for all
  * similar cases.
  * 
  * @author Andrius Velykis
  */
object CacheGoalTreeMatcher {

  def goalTree[A, T](goalMatcher: (T, T) => Boolean)
    (initialGoals: List[T], steps: List[(A, List[T])]): Option[GoalTree[A, T]] = {
    
    val (cache, initialGoalIds, stepsIds) = toIds(goalMatcher)(initialGoals, steps)
    
    val resultTree = GoalTreeMatcher.goalTree(initialGoalIds, stepsIds)
    
    resultTree.map(toGoals(cache))
  }

  private def toIds[A, T](goalMatcher: (T, T) => Boolean)
    (initialGoals: List[T], steps: List[(A, List[T])]): (IndexedSeq[T], List[Int], List[(A, List[Int])]) = {

    val goalIds = foldAndMapLeft(goalId(goalMatcher) _) _

    val (cache, initialGoalIds) = goalIds(IndexedSeq[T]())(initialGoals)

    val (finalCache, stepsIds) = foldAndMapLeft[(A, List[T]), IndexedSeq[T], (A, List[Int])] {
      case (cache, (info, goals)) => {
        val (newCache, stepGoalIds) = goalIds(cache)(goals)
        (newCache, (info, stepGoalIds))
      }
    }(cache)(steps)

    (finalCache, initialGoalIds, stepsIds)
  }

  private def goalId[T](goalMatcher: (T, T) => Boolean)(cache: IndexedSeq[T], goal: T): (IndexedSeq[T], Int) = {

    val existingIndex = cache.indexWhere(goalMatcher(goal, _))
    if (existingIndex >= 0) {
      // found a match
      (cache, existingIndex)
    } else {
      // no match found - add to the end and return the index
      val newCache = cache :+ goal
      (newCache, newCache.size - 1)
    }
  }
  
  private def foldAndMapLeft[A, B, C](f: (B, A) => (B, C))(z: B)(list: List[A]): (B, List[C]) = {
    var acc = z
    var newList = List[C]()
    var these = list
    while (!these.isEmpty) {
      val res = f(acc, these.head)
      acc = res._1
      these = these.tail
      newList = res._2 :: newList
    }
    (acc, newList.reverse)
  }
  
  private def toGoals[A, T](cache: IndexedSeq[T])(tree: GoalTree[A, Int]): GoalTree[A, T] = {
    tree match {
      case GoalEntry(info, inGoals, outGoals) => GoalEntry(info, idGoals(cache, inGoals), idGoals(cache, outGoals))
      case GoalSeq(seq) => GoalSeq(seq.map(toGoals(cache)))
      case GoalParallel(par) => GoalParallel(par.map(toGoals(cache)))
    }
  }
  
  private def idGoals[T](cache: IndexedSeq[T], goalIds: List[Int]) = goalIds.map(cache(_))
  
}
