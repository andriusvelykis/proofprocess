package org.ai4fm.proofprocess.core.store;

import java.util.List;

import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.Proof;

/**
 * An interface to abstract proof store implementation.
 * 
 * <p>
 * The ProofProcess model does not include top-level data structure to store proofs, intents, etc.
 * This allows utilising different ways of storing the data, e.g. as a database, or EMF structure,
 * etc.
 * </p>
 * 
 * @author Andrius Velykis
 */
public interface IProofStore {

	public List<Proof> getProofs();
	
	public void addProof(Proof proof);
	
	public Intent getIntent(String intentName);
	
}
