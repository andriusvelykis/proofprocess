package org.ai4fm.proofprocess.core.store

import org.ai4fm.proofprocess.ProofStore

trait IProofStoreProvider {

  def store: ProofStore
  
}
