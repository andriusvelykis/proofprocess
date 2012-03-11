package org.ai4fm.proofprocess.project.core;

import java.util.List;

import org.ai4fm.proofprocess.Proof;
import org.ai4fm.proofprocess.ProofProcessFactory;
import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.project.Project;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * @author Andrius Velykis
 */
public class ProofMatcher {

	/**
	 * Finds first proof matching the given initial goals (and label,
	 * optionally). The proofs are searched backwards, so the last matching
	 * proof is returned. If none is found, a new proof is created and added to
	 * the project. TODO multiple proofs?
	 * 
	 * @param project
	 * @param proofLabel
	 *            proof label to match, or {@code null} if label should be ignored
	 * @param proofGoals
	 * @return The last matching proof
	 * 
	 */
	public Proof findCreateProof(Project project, String proofLabel, List<Term> proofGoals) {
		
		List<Proof> proofs = project.getProofs();
		// go backwards and use the last one
		for (int index = proofs.size() - 1; index >= 0; index--) {
			Proof proof = proofs.get(index);
			
			if (proofLabel != null) {
				boolean sameLabel = proofLabel.equals(proof.getLabel());
				if (!sameLabel) {
					// different label
					continue;
				}
			}
			
			if (matchGoals(proofGoals, proof.getGoals())) {
				// matching goals and label
				return proof;
			}
		}
		
		// create new
		Proof targetProof = ProofProcessFactory.eINSTANCE.createProof();
		targetProof.setLabel(proofLabel);
		for (Term goal : proofGoals) {
			// copy the goals defensively, they may be used somewhere else
			targetProof.getGoals().add(copyTerm(goal));
		}
		
		// add to the project
		proofs.add(targetProof);
		
		return targetProof;
	}
	
	protected boolean matchGoals(List<Term> goals1, List<Term> goals2) {
		
		if (goals1.size() != goals2.size()) {
			// different goals
			return false;
		}
		
		boolean match = true;
		for (int gi = 0; gi < goals1.size(); gi++) {
			Term g1 = goals1.get(gi);
			Term g2 = goals2.get(gi);
			if (!matchTerms(g1, g2)) {
				match = false;
				break;
			}
		}
		
		// TODO what about ignoring goal order? or repeated goals?
		return match;
	}
	
	protected boolean matchTerms(Term term1, Term term2) {
		// TODO ignore some properties, e.g. display?
		return EcoreUtil.equals(term1, term2);
	}
	
	protected Term copyTerm(Term term) {
		return EcoreUtil.copy(term);
	}
	
}
