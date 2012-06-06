package org.ai4fm.proofprocess.isabelle.core.analysis

import isabelle.Command
import isabelle.Command.State
import java.io.IOException
import java.util.Date
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.isabelle.core.IsabellePProcessCorePlugin._
import org.ai4fm.proofprocess.log.ProofLog
import org.ai4fm.proofprocess.log.ProofProcessLogFactory
import org.ai4fm.proofprocess.log.ProofProcessLogPackage
import org.ai4fm.proofprocess.project.core.ProofManager
import org.ai4fm.proofprocess.project.core.util.EmfUtil

/** Logs the proof activities (e.g. proof events from Isabelle, user actions in the proof
  * assistant, interactions with the proof process). The log is used to keep the time-based
  * sequential account of the proof process, since the proof process model does not track the
  * sequence how the model was created (apart from sequence of attempts - but even then old
  * attempts could be replayed as the new activity).
  *
  * @author Andrius Velykis
  */
object ProofActivityLogger {

  def logProof(proofLog: ProofLog, proofEntries: Map[State, ProofEntry],
      changedCommands: Set[Command], proofState: List[State]) {

    // get changed states within the proof state
    val changedStates = proofState.filter(state => changedCommands.contains(state.command))

    // get proof entries for each state if available
    val cmdEntries = changedStates.flatMap(state => proofEntries.get(state).map((state, _)))

    // the entry logging function
    val logEntry = Function.tupled(logProofEntry(proofLog) _)

    cmdEntries foreach logEntry

    if (!cmdEntries.isEmpty) {
      // save the logged info
      try {
        proofLog.eResource().save(ProofManager.SAVE_OPTIONS);
      } catch {
        case e: IOException => log(error(Some(e)));
      }
    }
  }

  private def logProofEntry(proofLog: ProofLog)(cmdState: State, proofEntry: ProofEntry) {

    // TODO add non-proof commands to activity logger?
    val proofActivity = ProofProcessLogFactory.eINSTANCE.createProofActivity()
    proofActivity.setProofRef(proofEntry)

    proofActivity.setTimestamp(new Date(System.currentTimeMillis()))
    proofActivity.setDescription(entryDescription(cmdState, proofEntry))

    EmfUtil.addValue(proofLog, ProofProcessLogPackage.PROOF_LOG__ACTIVITIES, proofActivity);
  }

  private def entryDescription(cmdState: State, proofEntry: ProofEntry) =
    "Proof: " + cmdState.command.name

}
