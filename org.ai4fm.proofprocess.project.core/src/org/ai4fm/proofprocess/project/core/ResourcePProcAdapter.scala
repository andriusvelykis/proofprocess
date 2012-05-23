package org.ai4fm.proofprocess.project.core

import org.ai4fm.proofprocess.core.store.IProofStoreProvider
import org.eclipse.core.runtime.IAdapterFactory
import org.eclipse.core.resources.IResource
import org.ai4fm.proofprocess.project.core.store.ProjectProofStoreProvider

class ResourcePProcAdapter extends IAdapterFactory {

  override def getAdapter(adaptableObject: Object, adapterType: Class[_]): Object =
    if (classOf[IProofStoreProvider] == adapterType)
      adaptableObject match {
        case res: IResource if (res.getProject() != null) => new ProjectProofStoreProvider(res.getProject())
        case _ => null
      }
    else null

  override def getAdapterList(): Array[Class[_]] = Array(classOf[IProofStoreProvider])

}