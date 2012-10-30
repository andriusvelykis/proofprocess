package org.ai4fm.proofprocess.project.core.store

import org.ai4fm.proofprocess.ProofStore
import org.ai4fm.proofprocess.core.store.IProofStoreProvider
import org.ai4fm.proofprocess.project.core.ProofManager
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.NullProgressMonitor


class ProjectProofStoreProvider(private val project: IProject) extends IProofStoreProvider {

  @throws(classOf[CoreException])
  lazy val store: ProofStore = ProofManager.proofProject(project, new NullProgressMonitor)
  
}
