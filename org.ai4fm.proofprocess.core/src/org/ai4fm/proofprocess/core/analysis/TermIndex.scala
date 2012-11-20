package org.ai4fm.proofprocess.core.analysis

/**
 * Provides methods to index some terms based on some given match function.
 *
 * This is allows creating a cache for terms that maps terms to some index. It is useful to avoid
 * possibly expensive checks for each match, where a lot of matching is expected, and only perform matching initially.
 *
 * This is also necessary for goals that use custom matching (instead of ==), e.g. EMF Terms.
 * The matcher function can be provided to establish "same" goals. Note that some of the matching
 * original goals are not preserved in this manner - the single "matching" goal is used for all
 * similar cases.
 *
 * @author Andrius Velykis
 */
object TermIndex {

  type GoalStep[A, T] = (A, List[T], List[T])

  def indexedGoalSteps[A, T](matches: (T, T) => Boolean)
                            (goals: List[GoalStep[A, T]],
                             cache: IndexedSeq[T] = IndexedSeq()): (IndexedSeq[T], List[GoalStep[A, Int]]) = {

    /** indexes the goal step (its in/out goals) */
    def indexedStep(cache: IndexedSeq[T], goal: GoalStep[A, T]): (IndexedSeq[T], GoalStep[A, Int]) = {

      val (g, inGoals, outGoals) = goal
      val (c1, inGoalIds) = indexed(matches)(inGoals, cache)
      val (c2, outGoalIds) = indexed(matches)(outGoals, c1)
      (c2, (g, inGoalIds, outGoalIds))
    }

    foldLeftAndMap(indexedStep)(cache)(goals)
  }
  
  def indexed[T](matches: (T, T) => Boolean)
                (terms: List[T], cache: IndexedSeq[T] = IndexedSeq()): (IndexedSeq[T], List[Int]) =
    foldLeftAndMap((c: IndexedSeq[T], t: T) => indexedTerm(matches)(t, c) )(cache)(terms)
  
  def indexedTerm[T](matches: (T, T) => Boolean)
                    (term: T, cache: IndexedSeq[T] = IndexedSeq()): (IndexedSeq[T], Int) = {

    val existingIndex = cache.indexWhere(matches(term, _))
    if (existingIndex >= 0) {
      // found a match
      (cache, existingIndex)
    } else {
      // no match found - add to the end and return the index
      // (add to the end so that the indexes of the cached terms do not change!)
      val newCache = cache :+ term
      (newCache, newCache.size - 1)
    }
  }
  
  /**
   * Performs fold and map at the same time.
   * 
   * Similar to scanLeft, but also preserves the cumulative value.
   */
  private def foldLeftAndMap[A, B, C](f: (B, A) => (B, C))(z: B)(list: List[A]): (B, List[C]) = {
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
  
}
