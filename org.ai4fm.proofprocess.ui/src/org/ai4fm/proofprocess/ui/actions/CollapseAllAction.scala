package org.ai4fm.proofprocess.ui.actions

import org.eclipse.jface.action.Action
import org.eclipse.jface.viewers.AbstractTreeViewer
import org.eclipse.ui.ISharedImages
import org.eclipse.ui.PlatformUI
import org.eclipse.ui.handlers.CollapseAllHandler

/**
 * @author Andrius Velykis
 */
class CollapseAllAction(viewer: AbstractTreeViewer)
    extends Action("Collapse All") {

  setToolTipText("Collapse All")
  setActionDefinitionId(CollapseAllHandler.COMMAND_ID)

  val sharedImgs = PlatformUI.getWorkbench.getSharedImages
  setImageDescriptor(sharedImgs.getImageDescriptor(ISharedImages.IMG_ELCL_COLLAPSEALL))
  setDisabledImageDescriptor(sharedImgs.getImageDescriptor(ISharedImages.IMG_ELCL_COLLAPSEALL_DISABLED))

  override def run() =
    try {
      viewer.getControl.setRedraw(false)
      viewer.collapseAll()
    } finally {
      viewer.getControl.setRedraw(true)
    }

}
