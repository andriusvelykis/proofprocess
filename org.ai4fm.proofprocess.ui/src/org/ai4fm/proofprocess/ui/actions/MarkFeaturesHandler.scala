package org.ai4fm.proofprocess.ui.actions

import org.ai4fm.proofprocess.ProofElem
import org.ai4fm.proofprocess.ui.features.MarkFeaturesDialog
import org.ai4fm.proofprocess.ui.util.SWTUtil.selectionElement

import org.eclipse.core.commands.{AbstractHandler, ExecutionEvent, ExecutionException}
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
    val selection = selectionElement(HandlerUtil.getCurrentSelection(event))

    selection match {
      case Some(e: ProofElem) => handleProofEntrySelected(event, e)
      case _ => // ignore
    }

    // return value is reserved for future APIs
    null
  }


  private def handleProofEntrySelected(event: ExecutionEvent, e: ProofElem) {

    val shell = HandlerUtil.getActiveShell(event)
    
    val dialog = new MarkFeaturesDialog(shell, e)
    dialog.open()
    
  }

}
