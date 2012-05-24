package org.ai4fm.proofprocess.project.core.store

import org.ai4fm.proofprocess.ProofStore
import org.ai4fm.proofprocess.core.store.IProofStoreProvider
import org.ai4fm.proofprocess.project.core.ProofManager

import org.eclipse.core.resources.IProject


class ProjectProofStoreProvider(private val project: IProject) extends IProofStoreProvider {

  lazy val store: ProofStore = ProofManager.getProofProject(project, null);
  
}
