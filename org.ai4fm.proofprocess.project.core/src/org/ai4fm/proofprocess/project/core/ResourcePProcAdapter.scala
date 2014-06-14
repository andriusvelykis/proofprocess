package org.ai4fm.proofprocess.project.core

import org.ai4fm.proofprocess.core.store.IPProcessStoreProvider
import org.ai4fm.proofprocess.core.store.IProofEntryTracker
import org.ai4fm.proofprocess.core.store.IProofStoreProvider
import org.ai4fm.proofprocess.project.core.store.ProjectPProcessStoreProvider
import org.eclipse.core.resources.IResource
import org.eclipse.core.runtime.IAdapterFactory

object ResourcePProcAdapter {
  private val PPSTORE_CLS = List(
    classOf[IPProcessStoreProvider],
    classOf[IProofStoreProvider],
    classOf[IProofEntryTracker])
}

class ResourcePProcAdapter extends IAdapterFactory {

  import ResourcePProcAdapter._

  override def getAdapter(adaptableObject: Object, adapterType: Class[_]): Object =
    if (PPSTORE_CLS.contains(adapterType)) {
      adaptableObject match {
        case res: IResource if (res.getProject() != null) =>
          new ProjectPProcessStoreProvider(res.getProject())
        case _ => null
      }
    } else null

  override def getAdapterList(): Array[Class[_]] =
    Array(classOf[IProofStoreProvider], classOf[IProofEntryTracker])

}
