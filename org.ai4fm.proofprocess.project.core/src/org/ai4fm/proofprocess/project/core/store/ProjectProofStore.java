package org.ai4fm.proofprocess.project.core.store;

import java.util.Collections;
import java.util.List;

import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.Proof;
import org.ai4fm.proofprocess.core.store.IProofStore;
import org.ai4fm.proofprocess.project.Project;
import org.ai4fm.proofprocess.project.core.util.ProofProcessUtil;

public class ProjectProofStore implements IProofStore {

	private final Project project;
	
	public ProjectProofStore(Project project) {
		this.project = project;
	}

	@Override
	public List<Proof> getProofs() {
		return Collections.unmodifiableList(project.getProofs());
	}

	@Override
	public void addProof(Proof proof) {
		// TODO use EMF command?
		project.getProofs().add(proof);
	}

	@Override
	public Intent getIntent(String intentName) {
		return ProofProcessUtil.findCreateIntent(project, intentName);
	}

}
