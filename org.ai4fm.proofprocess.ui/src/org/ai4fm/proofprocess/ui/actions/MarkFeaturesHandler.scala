package org.ai4fm.proofprocess.ui.actions

import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ui.features.MarkFeaturesDialog
import org.eclipse.core.commands.{AbstractHandler, ExecutionEvent, ExecutionException}
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.ui.handlers.HandlerUtil


/**
 * Opens a dialog to mark features for the given selection.
 *
 * @author Andrius Velykis
 */
class MarkFeaturesHandler extends AbstractHandler {

  @throws(classOf[ExecutionException])
  override def execute(event: ExecutionEvent): AnyRef = {

    // get the selected element if available
    val selection = selectedElem(event)

    selection match {
      case Some(e: ProofEntry) => handleProofEntrySelected(event, e)
      case _ => // ignore
    }

    // return value is reserved for future APIs
    null
  }

  private def selectedElem(event: ExecutionEvent): Option[Any] =
    Option(HandlerUtil.getCurrentSelection(event)) match {
      case Some(ss: IStructuredSelection) => Option(ss.getFirstElement)
      case _ => None
    }

  private def handleProofEntrySelected(event: ExecutionEvent, e: ProofEntry) {

    val shell = HandlerUtil.getActiveShell(event)
    
    val dialog = new MarkFeaturesDialog(shell, e)
    dialog.open()
    
  }

}
