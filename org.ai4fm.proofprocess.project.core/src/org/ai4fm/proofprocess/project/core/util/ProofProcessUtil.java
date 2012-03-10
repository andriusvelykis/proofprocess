package org.ai4fm.proofprocess.project.core.util;

import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.ProofProcessFactory;
import org.ai4fm.proofprocess.project.Project;

/**
 * @author Andrius Velykis
 */
public class ProofProcessUtil {

	public static Intent findCreateIntent(Project project, String intentName) {
	
		for (Intent intent : project.getIntents()) {
			if (intentName.equals(intent.getName())) {
				return intent;
			}
		}
		
		// create new
		Intent intent = ProofProcessFactory.eINSTANCE.createIntent();
		intent.setName(intentName);
		project.getIntents().add(intent);
		
		return intent;
	}

}
