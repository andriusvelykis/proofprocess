package org.ai4fm.proofprocess.isabelle.core.analyse;

import isabelle.Command;
import isabelle.Command.State;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ai4fm.proofprocess.ProofEntry;
import org.ai4fm.proofprocess.isabelle.core.IsabelleProofPlugin;
import org.ai4fm.proofprocess.log.ProofActivity;
import org.ai4fm.proofprocess.log.ProofLog;
import org.ai4fm.proofprocess.log.ProofProcessLogFactory;
import org.ai4fm.proofprocess.log.ProofProcessLogPackage;
import org.ai4fm.proofprocess.project.core.util.EmfUtil;

/**
 * Logs the proof activities (e.g. proof events from Isabelle, user actions in
 * the proof assistant, interactions with the proof process).
 * <p>
 * The log is used to keep the time-based sequential account of the proof
 * process, since the proof process model does not track the sequence how the
 * model was created (apart from sequence of attempts - but even then old
 * attempts could be replayed as the new activity).
 * </p>
 * 
 * @author Andrius Velykis
 */
public class ProofActivityLogger {

	private final ProofLog proofLog;
	
	public ProofActivityLogger(ProofLog proofLog) {
		super();
		this.proofLog = proofLog;
	}
	
	public void logProof(List<State> proofState, Set<Command> changedCommands,
			Map<State, ProofEntry> proofEntries) {

		for (State cmdState : proofState) {
			if (changedCommands.contains(cmdState.command())) {
				ProofEntry proofEntry = proofEntries.get(cmdState);
				logProof(cmdState, proofEntry);
			}
		}
	}

	public void logProof(State cmdState, ProofEntry proofEntry) {
		
		// TODO add non-proof commands to activity logger?
		ProofActivity proofActivity = ProofProcessLogFactory.eINSTANCE.createProofActivity();
		proofActivity.setProofRef(proofEntry);
		
		proofActivity.setTimestamp(new Date(System.currentTimeMillis()));
		proofActivity.setDescription(getEntryDescription(cmdState, proofEntry));
		
		EmfUtil.addValue(proofLog, ProofProcessLogPackage.PROOF_LOG__ACTIVITIES, proofActivity);
		
		// TODO save selectively, or buffer?
		try {
			proofLog.eResource().save(null);
		} catch (IOException e) {
			IsabelleProofPlugin.log(e);
		}
	}
	
	private String getEntryDescription(State cmdState, ProofEntry proofEntry) {
		return "Proof: " + cmdState.command().name();
	}
	
}
