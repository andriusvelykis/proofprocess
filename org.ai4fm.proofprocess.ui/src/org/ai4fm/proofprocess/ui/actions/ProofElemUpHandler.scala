package org.ai4fm.proofprocess.ui.actions

import org.ai4fm.proofprocess.ProofElem
import org.ai4fm.proofprocess.ProofParallel
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.error
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.log
import org.ai4fm.proofprocess.ui.util.SWTUtil.selectionElement
import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.emf.cdo.transaction.CDOTransaction
import org.eclipse.emf.cdo.util.CommitException
import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.ui.handlers.HandlerUtil


/**
 * Moves the selected proof element up (reorders).
 * 
 * Only works for ProofParallel branches.
 *
 * @author Andrius Velykis
 */
class ProofElemUpHandler extends AbstractHandler {

  @throws(classOf[ExecutionException])
  override def execute(event: ExecutionEvent): AnyRef = {

    // get the selected element if available
    val selection = selectionElement(HandlerUtil.getCurrentSelection(event))

    selection match {
      case Some(e: ProofElem) => moveUp(event, e)
      case _ => // ignore
    }

    // return value is reserved for future APIs
    null
  }

  private def moveUp(event: ExecutionEvent, elem: ProofElem) = {
    
    val dialogTitle = "Move element up"

    val transaction = PProcessUtil.cdoTransaction(elem)
    val savePoint = transaction map (_.setSavepoint())

    def error(message: String): Boolean = {
      MessageDialog.openError(HandlerUtil.getActiveShell(event), dialogTitle, message)
      false
    }

    val moveSuccess = Option(elem.eContainer) match {

      case Some(parallel: ProofParallel) => {
        val elemIndex = parallel.getEntries.indexOf(elem)
        if (elemIndex > 0) {
          parallel.getEntries.move(elemIndex - 1, elemIndex)
          true
        } else {
          error("The element is already the top branch.")
        }
      }

      case bad => error("Can only reorder branches of a parallel split.")
    }
    

    if (moveSuccess) {
      commit(transaction)
    } else {
      savePoint foreach (_.rollback())
    }
  }

  private def commit(transaction: Option[CDOTransaction]) =
    try {
      transaction foreach { _.commit() }
    } catch {
      case ex: CommitException => log(error(Some(ex)))
    }

}
