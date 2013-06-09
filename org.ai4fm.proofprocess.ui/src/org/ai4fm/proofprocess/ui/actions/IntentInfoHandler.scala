package org.ai4fm.proofprocess.ui.actions

import org.ai4fm.proofprocess.Intent
import org.ai4fm.proofprocess.ui.features.IntentInfoDialog
import org.ai4fm.proofprocess.ui.util.SWTUtil.selectionElement

import org.eclipse.core.commands.{AbstractHandler, ExecutionEvent, ExecutionException}
import org.eclipse.ui.handlers.HandlerUtil


/**
 * Opens a dialog to edit intent properties for the given selection.
 *
 * @author Andrius Velykis
 */
class IntentInfoHandler extends AbstractHandler {

  @throws(classOf[ExecutionException])
  override def execute(event: ExecutionEvent): AnyRef = {

    // get the selected element if available
    val selection = selectionElement(HandlerUtil.getCurrentSelection(event))

    selection match {
      case Some(intent: Intent) => handleIntentSelected(event, intent)
      case _ => // ignore
    }

    // return value is reserved for future APIs
    null
  }


  private def handleIntentSelected(event: ExecutionEvent, intent: Intent) {

    val shell = HandlerUtil.getActiveShell(event)
    
    val dialog = new IntentInfoDialog(shell, intent)
    dialog.open()
    
  }

}
