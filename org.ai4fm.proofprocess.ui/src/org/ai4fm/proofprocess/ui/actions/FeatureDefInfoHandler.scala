package org.ai4fm.proofprocess.ui.actions

import org.ai4fm.proofprocess.ProofFeatureDef
import org.ai4fm.proofprocess.ui.features.FeatureDefInfoDialog
import org.ai4fm.proofprocess.ui.util.SWTUtil.selectionElement

import org.eclipse.core.commands.{AbstractHandler, ExecutionEvent, ExecutionException}
import org.eclipse.ui.handlers.HandlerUtil


/**
 * Opens a dialog to edit feature definition properties for the given selection.
 *
 * @author Andrius Velykis
 */
class FeatureDefInfoHandler extends AbstractHandler {

  @throws(classOf[ExecutionException])
  override def execute(event: ExecutionEvent): AnyRef = {

    // get the selected element if available
    val selection = selectionElement(HandlerUtil.getCurrentSelection(event))

    selection match {
      case Some(feature: ProofFeatureDef) => handleFeatureSelected(event, feature)
      case _ => // ignore
    }

    // return value is reserved for future APIs
    null
  }


  private def handleFeatureSelected(event: ExecutionEvent, feature: ProofFeatureDef) {

    val shell = HandlerUtil.getActiveShell(event)
    
    val dialog = new FeatureDefInfoDialog(shell, feature)
    dialog.open()
    
  }

}
