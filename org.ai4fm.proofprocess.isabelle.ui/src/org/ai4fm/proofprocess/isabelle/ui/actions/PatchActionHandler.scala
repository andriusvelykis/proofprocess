package org.ai4fm.proofprocess.isabelle.ui.actions

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.promise

import org.ai4fm.proofprocess.isabelle.core.IPatchActionHandler
import org.ai4fm.proofprocess.isabelle.ui.IsabellePProcessUIPlugin.error
import org.eclipse.jface.dialogs.{ErrorDialog, MessageDialog}
import org.eclipse.ui.PlatformUI


/**
 * Handles patch actions by showing to/querying the user.
 * 
 * @author Andrius Velykis
 */
class PatchActionHandler extends IPatchActionHandler {

  override def performPatch(files: List[String]): Boolean = syncExecUIResult {

    val msg =
      "AI4FM ProofProcess currently requires patching Isabelle Pure source files to enable " +
      "capturing proof details. Without this, the captured data will be limited.\n\n" +
      "The following files in Isabelle distribution will be patched:\n" +
      files.mkString("\n")

    MessageDialog.openConfirm(shell, "Patch Isabelle for ProofProcess", msg)
  }

  override def reportPatchCompleted() = asyncExecUI {
    MessageDialog.openInformation(shell, "Patching Completed",
      "Isabelle patched successfully.\n\n" +
      "Restart Isabelle - the changes will be rebuilt upon startup.")
  }

  override def reportPatchProblem(message: String) = asyncExecUI {
    ErrorDialog.openError(shell, "Problems Patching Isabelle", null, error(msg = Some(message)))
  }


  private def asyncExecUI(f: => Unit) {
    val display = PlatformUI.getWorkbench.getDisplay

    display.asyncExec(new Runnable {
      override def run() { f }
    })
  }

  private def syncExecUIResult[A](op: => A): A = {
    val p = promise[A]

    asyncExecUI {
      val res = op
      p success res
    }

    Await.result(p.future, Duration.Inf)
  }
  

  private def shell = PlatformUI.getWorkbench.getActiveWorkbenchWindow.getShell

}
