package org.ai4fm.proofprocess.core.util

import scala.collection.JavaConverters._

import org.ai4fm.proofprocess.{Intent, ProofProcessFactory, ProofStore, Term}


object PProcessUtil {
  
  /** Finds the intent in the proof store or creates a new one if not found. */
  def getIntent(store: ProofStore, intentName: String): Intent = {
    val found = store.getIntents.asScala.find(_.getName() == intentName)
    
    found getOrElse {
        // create new
		val intent = ProofProcessFactory.eINSTANCE.createIntent();
		intent.setName(intentName);
		store.getIntents().add(intent);
		intent
    }
  }

  /**
   * Converts the proof steps with outgoing goals only (as is usual with theorem provers) and
   * initial goals (e.g. from the conjecture) to a list that has both incoming and outgoing goals
   * for each proof step.
   */
  def toInOutGoalSteps[A, B](entry: (A, List[Term], List[Term]) => B)
                        (initialGoals: List[Term], 
                         proofSteps: List[(A, List[Term])]): List[B] = {

    // make a list with ingoals-info-outgoals steps
    val inGoals = initialGoals :: proofSteps.map(_._2)
    val inOutSteps = inGoals zip proofSteps

    inOutSteps map { case (inGoals, (info, outGoals)) => entry(info, inGoals, outGoals) }
  }
  
}
