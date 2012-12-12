package org.ai4fm.proofprocess.zeves.core.analysis

import java.util.Date

import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.log.{ProofLog, ProofProcessLogFactory, ProofProcessLogPackage}
import org.ai4fm.proofprocess.project.core.ProofManager
import org.ai4fm.proofprocess.project.core.util.EmfUtil
import org.ai4fm.proofprocess.zeves.core.parse.SnapshotUtil.ProofSnapshotEntry
import org.eclipse.core.runtime.IProgressMonitor

import net.sourceforge.czt.zeves.snapshot.ISnapshotEntry


/** Logs the proof activities (e.g. proof events from Z/EVES, user actions in the proof
  * assistant, interactions with the proof process). The log is used to keep the time-based
  * sequential account of the proof process, since the proof process model does not track the
  * sequence how the model was created (apart from sequence of attempts - but even then old
  * attempts could be replayed as the new activity).
  *
  * @author Andrius Velykis
  */
object ProofActivityLogger {

  def logProof(proofLog: ProofLog, proofEntry: ISnapshotEntry => Option[ProofEntry],
               snapshotEntries: Iterable[ISnapshotEntry])
               (implicit monitor: IProgressMonitor) {

    // the entry logging function
    val logEntryF = logEntry(proofLog, proofEntry) _

    snapshotEntries foreach logEntryF

    if (!snapshotEntries.isEmpty) {
      // save the logged info
      ProofManager.commitTransaction(proofLog, monitor)
    }
  }

  private val factory = ProofProcessLogFactory.eINSTANCE
  
  private def logEntry(proofLog: ProofLog, proofEntry: ISnapshotEntry => Option[ProofEntry])
                      (snapshotEntry: ISnapshotEntry) {

    val activity = snapshotEntry match {
      
      case ProofSnapshotEntry(_) => {
        val proofActivity = factory.createProofActivity
        
        // if proof entry is available, set it as activity reference
        val entry = proofEntry(snapshotEntry)
        entry foreach proofActivity.setProofRef
        
        proofActivity
      }
      
      case _ => factory.createActivity
    }

    activity.setTimestamp(new Date(System.currentTimeMillis))
    activity.setDescription(entryDescription(snapshotEntry))

    EmfUtil.addValue(proofLog, ProofProcessLogPackage.PROOF_LOG__ACTIVITIES, activity)
  }

  private def entryDescription(snapshotEntry: ISnapshotEntry) = {
    val term = Option(snapshotEntry.getData.getTerm)
    "Added: " + (term.map(_.getClass.getSimpleName) getOrElse "<goal?>")
  }

}
