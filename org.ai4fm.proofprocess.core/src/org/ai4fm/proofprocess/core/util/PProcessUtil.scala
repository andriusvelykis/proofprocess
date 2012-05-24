package org.ai4fm.proofprocess.core.util

import org.ai4fm.proofprocess.Intent
import org.ai4fm.proofprocess.ProofStore
import org.ai4fm.proofprocess.ProofProcessFactory

import scala.collection.JavaConversions._


object PProcessUtil {
  
  /** Finds the intent in the proof store or creates a new one if not found. */
  def getIntent(store: ProofStore, intentName: String): Intent = {
    val found = store.getIntents.find(_.getName() == intentName)
    
    found getOrElse {
        // create new
		val intent = ProofProcessFactory.eINSTANCE.createIntent();
		intent.setName(intentName);
		store.getIntents().add(intent);
		intent
    }
  }
  
}
