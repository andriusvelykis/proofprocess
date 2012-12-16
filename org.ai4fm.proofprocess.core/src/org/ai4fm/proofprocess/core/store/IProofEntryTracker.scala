package org.ai4fm.proofprocess.core.store

import org.ai4fm.proofprocess.ProofEntry
import org.eclipse.core.runtime.CoreException

/**
 * @author Andrius Velykis
 */
trait IProofEntryTracker {

  @throws(classOf[CoreException])
  def initTrackLatestEntry(f: ProofEntry => Unit)
  
  def dispose()
  
}
