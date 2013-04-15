package org.ai4fm.proofprocess.isabelle.core.parse

import org.ai4fm.proofprocess.core.analysis.{Assumption, EqTerm, Judgement}

import ResultParser.StepProofType.StepProofType
import isabelle.Command


/**
 * Represents results parsed from an Isabelle proof command state.
 * 
 * @author Andrius Velykis
 */
case class CommandResults(state: Command.State,
                          stateType: StepProofType,
                          inAssms: List[EqTerm],
                          outAssms: List[EqTerm],
                          outGoals: Option[List[EqTerm]]) {


  lazy val inAssmProps = inAssms map (Assumption(_))
  lazy val outAssmProps = outAssms map (Assumption(_))

  private def addInAssms(goal: Judgement[EqTerm]): Judgement[EqTerm] = {
    val updAssms = (goal.assms ::: inAssms).distinct
    goal.copy(assms = updAssms)
  }

  // convert each goal to a Judgement (assms + goal)
  // also link explicit assumptions with out goals - add them to each out goal
  lazy val outGoalProps = outGoals map { goals =>
    goals map ResultParser.splitAssmsGoal map addInAssms
  }

}
