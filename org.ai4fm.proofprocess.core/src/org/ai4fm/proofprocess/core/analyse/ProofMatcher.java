package org.ai4fm.proofprocess.core.analyse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.ai4fm.proofprocess.Attempt;
import org.ai4fm.proofprocess.Proof;
import org.ai4fm.proofprocess.ProofDecor;
import org.ai4fm.proofprocess.ProofElem;
import org.ai4fm.proofprocess.ProofEntry;
import org.ai4fm.proofprocess.ProofInfo;
import org.ai4fm.proofprocess.ProofParallel;
import org.ai4fm.proofprocess.ProofProcessFactory;
import org.ai4fm.proofprocess.ProofSeq;
import org.ai4fm.proofprocess.ProofStep;
import org.ai4fm.proofprocess.ProofStore;
import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.Trace;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;


/**
 * TODO map for matches to retrieve which pairs were matched (for activities)
 * 
 * @author Andrius Velykis
 */
public class ProofMatcher {

	/**
	 * Finds first proof matching the given initial goals (and label,
	 * optionally). The proofs are searched backwards, so the last matching
	 * proof is returned. If none is found, a new proof is created and added to
	 * the project. TODO multiple proofs?
	 * 
	 * @param proofStore
	 * @param proofLabel
	 *            proof label to match, or {@code null} if label should be ignored
	 * @param proofGoals
	 * @return The last matching proof
	 * 
	 */
	public Proof findCreateProof(ProofStore proofStore, String proofLabel, List<Term> proofGoals) {
		
		List<Proof> proofs = proofStore.getProofs();
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
		
		// copy the goals defensively, they may be used somewhere else
		targetProof.getGoals().addAll(copyTerms(proofGoals));
		
		// add to the project
		proofStore.getProofs().add(targetProof);
		
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
	
	protected boolean matchProofEntry(ProofEntry e1, ProofEntry e2) {
		
		ProofStep s1 = e1.getProofStep();
		ProofStep s2 = e2.getProofStep();

		return matchGoals(s1.getInGoals(), s2.getInGoals())
				&& matchGoals(s1.getOutGoals(), s2.getOutGoals())
				&& matchTrace(s1.getTrace(), s2.getTrace());
	}
	
	protected boolean matchTrace(Trace trace1, Trace trace2) {
		return EcoreUtil.equals(trace1, trace2);
	}
	
	protected Term copyTerm(Term term) {
		return EcoreUtil.copy(term);
	}
	
	public final List<Term> copyTerms(List<? extends Term> terms) {
		List<Term> copy = new ArrayList<Term>();
		for (Term term : terms) {
			copy.add(copyTerm(term));
		}
		return copy;
	}
	
	public ProofElemMatch findCreateProofTree(ProofStore proofStore, Proof proof, 
			List<ProofEntry> proofState) {

		ProofElemMatch matched = findMatchingProofTree(proof, proofState);
		if (matched != null) {
			// found a proof tree fully matching the given proof, use it
			return matched;
		}
		
		if (proofState.size() == 1) {
			return createProofTree(proofStore, proof, proofState.get(0));
		}
		
		/*
		 * Continue recursively on the shorter proof entries list (without the last
		 * element), then add the removed element to the end.
		 */
		List<ProofEntry> shorter = proofState.subList(0, proofState.size() - 1);
		ProofEntry proofStep = proofState.get(proofState.size() - 1);
		
		ProofElemMatch targetMatch;
		
		// match shorter
		ProofElemMatch sMatch = findCreateProofTree(proofStore, proof, shorter);
		
		/*
		 * Check whether the last element has been matched (e.g. shorter matches
		 * a previous proof fully). In that case, the new proof entry needs to be
		 * added to the end.
		 */
		int sMatchIndex = sMatch.allEntries.indexOf(sMatch.entry);
		Assert.isTrue(sMatchIndex >= 0);
		
		if (sMatchIndex == sMatch.allEntries.size() - 1) {
			// the last one - add to the end
			int sGroupIndex = proof.getAttempts().indexOf(sMatch.attempt);
			Assert.isTrue(sGroupIndex >= 0, "Last attempt entry must be in the last attempt set");

			// FIXME
//			Assert.isTrue(sMatch.group is in the attempt tree)
			
			if (sGroupIndex != proof.getAttempts().size() - 1) {
				// this is an older attempt tree that has matched - copy everything into a new one
				// and use the copied to add the new attempt
				targetMatch = copyAttemptTree(proof, sMatch.attempt, sMatch.allEntries);
			} else {
				// this is the latest attempt - append the entry to it
				targetMatch = sMatch;
			}
		} else {
			// partially matched other tree - copy the match and add to there
			targetMatch = copyAttemptTree(proof, sMatch.attempt, 
					sMatch.allEntries.subList(0, sMatchIndex + 1));
		}
		
		// use the same group as the last entry has
		// FIXME what about case splits?
		ProofElem groupToAdd = getGroupToAdd(proofStore, targetMatch.entry, proofStep);
		addToGroup(targetMatch.attempt, groupToAdd, proofStep);
		
		List<ProofEntry> entriesPlus = new ArrayList<ProofEntry>(targetMatch.allEntries);
		entriesPlus.add(proofStep);
		
		return new ProofElemMatch(targetMatch.attempt, entriesPlus, proofStep);
	}
	
	private ProofElemMatch findMatchingProofTree(Proof attemptSet, List<ProofEntry> entries) {
		
		List<Attempt> attempts = attemptSet.getAttempts();
		for (int rIndex = attempts.size() - 1; rIndex >= 0; rIndex--) {
			Attempt root = attempts.get(rIndex);
			List<ProofEntry> rootEntries = getProofEntriesDepthFirst(root.getProof());
			if (rootEntries.size() < entries.size()) {
				continue;
			}
			
			boolean matching = true;
			ProofEntry lastMatch = null;
			for (int index = 0; index < entries.size(); index++) {
				ProofEntry entry = rootEntries.get(index);
				if (!matchProofEntry(entry, entries.get(index))) {
					matching = false;
					break;
				}
				
				lastMatch = entry;
			}
			
			if (matching) {
				return new ProofElemMatch(root, rootEntries, lastMatch);
			}
		}
		
		return null;
	}
	
	private List<ProofEntry> getProofEntriesDepthFirst(ProofElem proofElem) {
		return getProofEntriesDepthFirst(proofElem, true);
	}
	
	private List<ProofEntry> getProofEntriesDepthFirst(ProofElem proofElem, boolean includeParallel) {
		
		List<ProofEntry> entries = new ArrayList<ProofEntry>();
		
		for (ProofElem child : getProofChildren(proofElem)) {
			
			if (!includeParallel && child instanceof ProofParallel) {
				continue;
			}
			
			entries.addAll(getProofEntriesDepthFirst(child));
		}
		
		if (proofElem instanceof ProofEntry) {
			entries.add((ProofEntry) proofElem);
		}
		
		return entries;
	}
	
	public static List<ProofElem> getProofChildren(ProofElem proofElem) {
		if (proofElem instanceof ProofSeq) {
			return ((ProofSeq) proofElem).getEntries();
		}
		
		if (proofElem instanceof ProofParallel) {
			return ((ProofParallel) proofElem).getEntries();
		}
		
		if (proofElem instanceof ProofDecor) {
			return Collections.singletonList(((ProofDecor) proofElem).getEntry());
		}
		
		return Collections.emptyList();
	}
	
	private ProofElemMatch copyAttemptTree(Proof proof, Attempt sourceAttempt, 
			List<ProofEntry> entriesToCopy) {
		
		Assert.isLegal(!entriesToCopy.isEmpty(), "Cannot copy an attempt tree with no entries");
		
		final Set<ProofElem> parentEntries = new HashSet<ProofElem>();
		for (ProofEntry entry : entriesToCopy) {
			collectParents(entry, parentEntries);
		}
		
		Copier copier = new Copier() {
			@Override
			public <T> Collection<T> copyAll(Collection<? extends T> eObjects) {
				
				List<T> filtered = new ArrayList<T>();
				for (T obj : eObjects) {
					if (obj instanceof ProofElem && !parentEntries.contains(obj)) {
						// do not include this into the copy
						continue;
					}
					
					filtered.add(obj);
				}
				
				return super.copyAll(filtered);
			}
		};
		
		Attempt result = (Attempt) copier.copy(sourceAttempt);
	    copier.copyReferences();
		
		// parent references still get copied within copyReferences(), but
		// the following removes them
	    Set<EObject> copiedParents = new HashSet<EObject>();
	    for (EObject parent : parentEntries) {
	    	EObject parentCopy = copier.get(parent);
	    	if (parentCopy != null) {
	    		copiedParents.add(parentCopy);
	    	}
	    }
	    
	    // TODO maybe below is unnecessary if we have containments only?
		for (Entry<EObject, EObject> copyEntry : copier.entrySet()) {
			if (copyEntry.getValue() instanceof ProofSeq) {
				ProofSeq group = (ProofSeq) copyEntry.getValue();
				group.getEntries().retainAll(copiedParents);
			} else if (copyEntry.getValue() instanceof ProofParallel) {
				ProofParallel group = (ProofParallel) copyEntry.getValue();
				group.getEntries().retainAll(copiedParents);
			} else if (copyEntry.getValue() instanceof ProofDecor) {
				// TODO what do we do about decor here?
//				ProofDecor group = (ProofDecor) copyEntry.getValue();
//				group.getEntries().retainAll(copiedParents);
			}
		}
		
		proof.getAttempts().add(result);
	    
		List<ProofEntry> copiedEntries = getProofEntriesDepthFirst(result.getProof());
		
		return new ProofElemMatch(result, copiedEntries, copiedEntries.get(copiedEntries.size() - 1));
	}
	
	private void collectParents(ProofElem entry, Set<ProofElem> entries) {
		// TODO how about when the attempt is referenced twice?
		// Then one is not-containment reference
		entries.add(entry);
		
		ProofElem parent = getParentProofElem(entry);
		if (parent != null) {
			collectParents(parent, entries);
		}
	}
	
	/**
	 * 
	 * @param proofStore
	 * @param previous
	 * @param entry
	 * @return ProofElem where to add the entry, {@code null} if add to the root
	 */
	protected ProofElem getGroupToAdd(ProofStore proofStore, ProofEntry previous, ProofEntry entry) {
		// add the attempt to the same group
		return getParentProofElem(previous);
	}
	
	protected ProofElem getParentProofElem(ProofElem entry) {
		
		EObject parent = entry.eContainer();
		if (parent instanceof ProofElem) {
			return (ProofElem) parent;
		}
		
		return null;
	}
	
	protected void addToGroup(Attempt attempt, ProofElem group, ProofElem proofElem) {
		if (group == null) {
			// root - add to attempt
			ProofSeq seq = ProofProcessFactory.eINSTANCE.createProofSeq();
			
			ProofInfo info = ProofProcessFactory.eINSTANCE.createProofInfo();
			seq.setInfo(info);
			
			List<ProofElem> rootEntries = seq.getEntries();
			rootEntries.add(attempt.getProof());
			rootEntries.add(proofElem);
			
			// TODO use Edit commands?
			attempt.setProof(seq);
		} else {
			addToGroup(group, proofElem);
		}
	}
	
	public static void addToGroup(ProofElem group, ProofElem proofElem) {
		if (group instanceof ProofSeq) {
			((ProofSeq) group).getEntries().add(proofElem);
//			group.getContained().add(proofElem);
			return;
		}
		
		if (group instanceof ProofParallel) {
			((ProofParallel) group).getEntries().add(proofElem);
			return;
		}
		
		if (group instanceof ProofDecor) {
			ProofDecor decor = (ProofDecor) group;
			if (decor.getEntry() != null) {
				throw new IllegalArgumentException("Cannot add to non-empty ProofDecor!");
			}
			
			decor.setEntry(proofElem);
			return;
		}
		
		throw new IllegalArgumentException("Cannot add to " + group.getClass().getSimpleName());
	}
	
	private ProofElemMatch createProofTree(ProofStore proofStore, Proof attemptSet, ProofEntry entry) {
		
		Attempt attempt = ProofProcessFactory.eINSTANCE.createAttempt();
		attempt.setProof(entry);
		
		attemptSet.getAttempts().add(attempt);
		
		return new ProofElemMatch(attempt, Arrays.asList(entry), entry);
	}
	
	public static class ProofElemMatch {
		
		private final Attempt attempt;
		private final List<ProofEntry> allEntries;
		private final ProofEntry entry;
		
		public ProofElemMatch(Attempt attempt, List<ProofEntry> allEntries,
				ProofEntry matchedEntry) {
			super();
			this.attempt = attempt;
			this.allEntries = allEntries;
			this.entry = matchedEntry;
		}

		public Attempt getAttempt() {
			return attempt;
		}

		public List<ProofEntry> getAllEntries() {
			return Collections.unmodifiableList(allEntries);
		}

		public ProofEntry getEntry() {
			return entry;
		}
		
	}
	
}
