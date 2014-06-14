package org.ai4fm.proofprocess.project.core.store

import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ProofStore
import org.ai4fm.proofprocess.core.store.IPProcessStoreProvider
import org.ai4fm.proofprocess.project.core.PProcessDataManager
import org.ai4fm.proofprocess.project.core.PProcessDataManager.PProcessData
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.emf.cdo.transaction.CDOTransaction


/**
 * @author Andrius Velykis
 */
class ProjectPProcessStoreProvider(private val project: IProject)
    extends IPProcessStoreProvider {

  @throws(classOf[CoreException])
  lazy val (transaction: CDOTransaction, data: PProcessData) = {
    val monitor = new NullProgressMonitor
    val session = PProcessDataManager.session(project, monitor)
    val transaction = PProcessDataManager.openTransaction(session)
    val data = PProcessDataManager.loadData(transaction, monitor)
    (transaction, data)
  }

  @throws(classOf[CoreException])
  lazy val store: ProofStore = data.proofs

  lazy val proofEntryTracker = new ProjectProofEntryTracker(data.log)

  def initTrackLatestEntry(f: ProofEntry => Unit) =
    proofEntryTracker.initTrackLatestEntry(f)

  def dispose() {
    proofEntryTracker.dispose()
    transaction.close()
  }

}
