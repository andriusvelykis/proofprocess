package org.ai4fm.proofprocess.project.core.util;

import org.ai4fm.filehistory.FileVersion;
import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.ProofProcessFactory;
import org.ai4fm.proofprocess.project.Position;
import org.ai4fm.proofprocess.project.Project;
import org.ai4fm.proofprocess.project.ProjectProofProcessFactory;
import org.ai4fm.proofprocess.project.TextLoc;

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
	
	public static TextLoc createTextLoc(FileVersion fileVersion, int offset, int length) {
		
		TextLoc loc = ProjectProofProcessFactory.eINSTANCE.createTextLoc();
		loc.setFilePath(fileVersion.getPath());
		
		Position position = ProjectProofProcessFactory.eINSTANCE.createPosition();
		position.setOffset(offset);
		position.setLength(length);
		loc.setPosition(position);

		return loc;
	}

}
