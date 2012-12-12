package org.ai4fm.proofprocess.isabelle.core.analysis

import java.util.Date

import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.log.{ProofLog, ProofProcessLogFactory, ProofProcessLogPackage}
import org.ai4fm.proofprocess.project.core.ProofManager
import org.ai4fm.proofprocess.project.core.util.EmfUtil
import org.eclipse.core.runtime.NullProgressMonitor

import isabelle.Command
import isabelle.Command.State

/** Logs the proof activities (e.g. proof events from Isabelle, user actions in the proof
  * assistant, interactions with the proof process). The log is used to keep the time-based
  * sequential account of the proof process, since the proof process model does not track the
  * sequence how the model was created (apart from sequence of attempts - but even then old
  * attempts could be replayed as the new activity).
  *
  * @author Andrius Velykis
  */
object ProofActivityLogger {

  def logProof(proofLog: ProofLog, proofEntry: PartialFunction[State, ProofEntry],
      changedCommands: Set[Command], proofState: List[State]) {

    // get changed states within the proof state
    val changedStates = proofState.filter(state => changedCommands.contains(state.command))

    // the entry logging function
    val logEntry = logProofEntry(proofLog, proofEntry) _

    changedStates foreach logEntry

    if (!changedStates.isEmpty) {
      // save the logged info
      ProofManager.commitTransaction(proofLog, new NullProgressMonitor)
    }
  }

  private def logProofEntry(proofLog: ProofLog,
                            proofEntry: PartialFunction[State, ProofEntry])
                           (cmdState: State) {

    // TODO add non-proof commands to activity logger?
    val proofActivity = ProofProcessLogFactory.eINSTANCE.createProofActivity

    // if proof entry is available, set it as activity reference
    if (proofEntry.isDefinedAt(cmdState)) {
      proofActivity.setProofRef(proofEntry(cmdState))
    }
    
    proofActivity.setTimestamp(new Date(System.currentTimeMillis()))
    proofActivity.setDescription(entryDescription(cmdState))

    EmfUtil.addValue(proofLog, ProofProcessLogPackage.PROOF_LOG__ACTIVITIES, proofActivity);
  }

  private def entryDescription(cmdState: State) =
    "Proof: " + cmdState.command.name

}
