package org.ai4fm.proofprocess.core.store

import org.ai4fm.proofprocess.ProofStore
import org.eclipse.core.runtime.CoreException

trait IProofStoreProvider {

  @throws(classOf[CoreException])
  def store: ProofStore
  
}
