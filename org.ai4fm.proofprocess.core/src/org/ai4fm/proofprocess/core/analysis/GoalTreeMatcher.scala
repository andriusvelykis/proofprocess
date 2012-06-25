package org.ai4fm.proofprocess.core.analysis

/** Note: Need to ensure that goal type T can be matched with ==
  *
  * @author Andrius Velykis
  */
object GoalTreeMatcher {

  private case class Branch[A, T](root: GoalTree[A, T], inGoals: List[T])
  

  def goalTree[A, T](initialGoals: List[T], steps: List[(A, List[T])]): Option[GoalTree[A, T]] = {

    // make a list with ingoals-info-outgoals steps
    val inGoals = initialGoals :: steps.map(_._2)
    val inOutSteps = inGoals.zip(steps)

    val nil = List[Branch[A, T]]()

    // go backwards through the steps and build up the structure
    val (rootBranches, mergeBranches) = inOutSteps.foldRight((nil, nil)) {
      case ((inGoals, (info, outGoals)), (branches, mergeBranches)) =>
        handleTreeNodes(branches, mergeBranches, info, inGoals, outGoals)
    }

    // TODO sort? preserve original order somehow?
    
    val root = mergedRoot(branchRoot(rootBranches), mergeBranches)
    
    // also compact tree to eliminate nested parallel/seq , e.g. Seq(e1, Seq(e2, ...)) => Seq(e1, e2, ...)
    root.map(compactTree)
  }

  private def handleTreeNodes[A, T](branches: List[Branch[A, T]], mergeBranches: List[Branch[A, T]],
    info: A, inGoals: List[T], outGoals: List[T]): (List[Branch[A, T]], List[Branch[A, T]]) = {

    val unchangedGoals = inGoals.intersect(outGoals)
    val changedInGoals = inGoals.diff(unchangedGoals)
    val changedOutGoals = outGoals.diff(unchangedGoals)

    val matchFull = matchBranches[A, T](containsAll) _
    val matchPartial = matchBranches[A, T](containsAny) _

    val matchContext = {
      // first construct empty match context out of the branches
      br: List[Branch[A, T]] => MatchContext(br, (Nil, Nil), (Nil, Nil))
    } andThen {
      // first match fully unchanged goals
      c: MatchContext[A, T] => c.copy(unchanged = matchFull(c.br, unchangedGoals))
    } andThen {
      // remove the matches from the outstanding branch list
      c: MatchContext[A, T] => c.copy(br = c.br.diff(c.unchanged._1))
    } andThen {
      // match fully changed goals
      c: MatchContext[A, T] => c.copy(changed = matchFull(c.br, changedOutGoals))
    } andThen {
      // remove the matches from the outstanding branch list
      c: MatchContext[A, T] => c.copy(br = c.br.diff(c.changed._1))
    } andThen {
      // partially match goals
      c: MatchContext[A, T] =>
        {
          val (partChBranches, unmatchedChGoals) = matchPartial(c.br, changedOutGoals)
          c.copy(changed = (c.changed._1 ::: partChBranches, c.changed._2.intersect(unmatchedChGoals)))
        }
    } andThen {
      // remove the matches from the outstanding branch list
      c: MatchContext[A, T] => c.copy(br = c.br.diff(c.changed._1))
    } andThen {
      // add remaining outstanding branches to unchanged
      c: MatchContext[A, T] =>
        c.copy(
          unchanged = (c.unchanged._1 ::: c.br, c.unchanged._2.diff(c.br.map(_.inGoals))),
          br = Nil)
    }

    val matched = matchContext(branches)

    // create the current entry with changed goals
    val entry = GoalEntry(info, changedInGoals, changedOutGoals)

    val (changedBranches, additionalGoals) = matched.changed

    // the additional goals are frequently non-empty, especially for branch ends
    // the additional goals are yet unsolved end-goals
    
    // also get all unchanged branches with empty inGoals
    // these branches represent either reorder (no changes) or newly inserted goals (no incoming goals)
    // since they do not change anything, we just attach them to the next parent,
    // otherwise they get uncaught right to the top of the proof
    val unchangedEmptyBranches = branches.diff(changedBranches).filter(_.inGoals.isEmpty)
    
    // now affected branches consist of changed branches plus the unchanged empty ones
    val affectedBranches = changedBranches ::: unchangedEmptyBranches

    // get the root element for the affected branches (e.g. join in parallel if needed)
    val jointBranchRoot = branchRoot(affectedBranches)

    val affectedInGoals = affectedBranches.map(_.inGoals).flatten
    val unaffectedBranches = branches.diff(affectedBranches)
    val updatedMergeBranches = mergeBranches.map(updateMergeBranch(entry, _))

    val (newBranch, newMergeBranches) = if (containsAll(changedOutGoals, affectedInGoals)) {

      // Check if the merge branches can be fulfilled
      // a merge branch is fulfilled when after update its ingoals are covered by new entry ingoals
      val (matchedMergeBranches, openMergeBranches) =
        updatedMergeBranches.partition(b => b.inGoals.diff(entry.inGoals).isEmpty)

      val mergedJointRoot = mergedRoot(jointBranchRoot, matchedMergeBranches)

      // the outgoals contribute all ingoals of affected branches so no additional merge branch
      // the new branch is a normal branch
      val newBranch = mergedJointRoot match {
        // no joint - just put the entry into its own branch
        case None => Branch(entry, entry.inGoals)
        case Some(root) => {
          // put it into a sequence with the new entry
          val seq = GoalSeq(entry :: root :: Nil)
          Branch(seq, entry.inGoals)
        }
      }

      (newBranch, openMergeBranches)
    } else {
      // the branches have inGoals that were unchanged in this step - this is a merge point
      // mark the new branch as a merge branch, and add just the entry itself to the new branches
      // jointBranchRoot can never be None here
      val mergeRoot = jointBranchRoot.get
      val mergeBranch = updateMergeBranch(entry, (Branch(mergeRoot, affectedInGoals)))
      val newBranch = Branch(entry, entry.inGoals)

      (newBranch, mergeBranch :: updatedMergeBranches)
    }

    (newBranch :: unaffectedBranches, newMergeBranches)
  }

  private def branchRoot[A, T](branches: List[Branch[A, T]]): Option[GoalTree[A, T]] = branches match {
    // no branches, so nothing to join into a root
    case Nil => None
    // if single branch, it is the root
    case single :: Nil => Some(single.root)
    // for multiple branches, it is a parallel branching
    case multiple => Some(GoalParallel(multiple.map(_.root)))
  }

  private def mergedRoot[A, T](branchRoot: Option[GoalTree[A, T]], mergeBranches: List[Branch[A, T]]) =
    if (mergeBranches.isEmpty) {
      branchRoot
    } else {
      // merge the branches - pack them into Seq
      Some(GoalSeq(branchRoot.toList ::: mergeBranches.map(_.root)))
    }

  private def updateMergeBranch[A, T](entry: GoalEntry[A, T], branch: Branch[A, T]): Branch[A, T] = {
    val (updatedMergeGoals, remainingMergeGoals) = branch.inGoals.partition(entry.outGoals.contains)
    if (branch.inGoals.exists(entry.outGoals.contains)) {
      // if any of the merge inGoals are in the entry outGoals, replace them
      // the merge inGoals specify a requirement for goals introduced - when they are fulfilled,
      // the merge is added to the tree
      branch.copy(inGoals = entry.inGoals ::: remainingMergeGoals)
    } else {
      branch
    }
  }

  private def listsEqual(l1: List[_], l2: List[_]): Boolean =
    containsAll(l1, l2) && containsAll(l2, l1)

  private def containsAll(l1: List[_], l2: List[_]): Boolean =
    l2.intersect(l1).equals(l2)

  private def containsAny(l1: List[_], l2: List[_]): Boolean =
    l2.exists(l1.contains)

  private def matchBranches[A, T](contains: (List[T], List[T]) => Boolean)
    (branches: List[Branch[A, T]], goals: List[T]): (List[Branch[A, T]], List[T]) =

    branches.foldLeft((List[Branch[A, T]](), goals)) {
      case ((fullMatch, goals), branch) => if (contains(goals, branch.inGoals)) {
        (branch :: fullMatch, goals.diff(branch.inGoals))
      } else {
        // not a full match
        (fullMatch, goals)
      }
    }

  /** Drops the 'i'th element of a list. */
  private def dropIndex[T](xs: List[T], n: Int) = {
    val (l1, l2) = xs splitAt n
    l1 ::: (l2 drop 1)
  }

  private case class MatchContext[A, T](
    val br: List[Branch[A, T]],
    val unchanged: (List[Branch[A, T]], List[T]),
    val changed: (List[Branch[A, T]], List[T]))

  private def compactTree[A, T](tree: GoalTree[A, T]): GoalTree[A, T] = tree match {
    // leaf entries are not compacted
    case e @ GoalEntry(_, _, _) => e
    // for sequences, 'pull up' nested sequences into the parent
    case GoalSeq(seq) => GoalSeq(compactElems(seq){case GoalSeq(childSeq) => childSeq})
    // for parallels, 'pull up' nested branches into the parent
    case GoalParallel(par) => GoalParallel(compactElems(par){case GoalParallel(childPar) => childPar})
  }

  private def compactElems[A, T](elems: List[GoalTree[A, T]])
    (pf: PartialFunction[GoalTree[A, T], List[GoalTree[A, T]]]) : List[GoalTree[A, T]] =
      elems.map(compactTree).map(e => if (pf.isDefinedAt(e)) pf(e) else List(e)).flatten
}
