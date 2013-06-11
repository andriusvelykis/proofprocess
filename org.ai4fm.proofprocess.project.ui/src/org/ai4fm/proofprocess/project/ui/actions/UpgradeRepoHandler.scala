package org.ai4fm.proofprocess.project.ui.actions

import org.ai4fm.proofprocess.cdo.PProcessCDO
import org.ai4fm.proofprocess.project.core.ProofManager

import org.eclipse.core.commands.{AbstractHandler, ExecutionEvent, ExecutionException}
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.{IProgressMonitor, IStatus, Status}
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.jface.viewers.{ISelection, IStructuredSelection}
import org.eclipse.ui.handlers.HandlerUtil


/**
 * Upgrades the Proof Process repository for the selected project. 
 *
 * @author Andrius Velykis
 */
class UpgradeRepoHandler extends AbstractHandler {

  @throws(classOf[ExecutionException])
  override def execute(event: ExecutionEvent): AnyRef = {

    // get the selected element if available
    val selection = selectionElement(HandlerUtil.getCurrentSelection(event))

    selection match {
      case Some(project: IProject) => upgradeProjectRepo(event, project)
      case _ => // ignore
    }

    // return value is reserved for future APIs
    null
  }

  private def selectionElement(sel: ISelection): Option[Any] =
    Option(sel) match {
      case Some(ss: IStructuredSelection) => Option(ss.getFirstElement)
      case _ => None
    }


  private def upgradeProjectRepo(event: ExecutionEvent, project: IProject) {

    val (databaseLoc, repoName) = ProofManager.projectPProcessRepositoryInfo(project)

    val shell = HandlerUtil.getActiveShell(event);
    val dialogTitle = "Upgrade/Compact Repository"

    val userConfirm = MessageDialog.openConfirm(shell, dialogTitle,
      "Do you want to upgrade and compact proof process repository " + repoName + "?")

    if (userConfirm) {

      val job = new Job("Upgrading repository " + repoName) {
        setPriority(Job.LONG)
        setUser(true)
        override def run(monitor: IProgressMonitor): IStatus = {

          monitor.beginTask("Upgrading repository " + repoName, IProgressMonitor.UNKNOWN)
          PProcessCDO.upgradeRepository(databaseLoc, repoName, monitor)

          shell.getDisplay.asyncExec(new Runnable {
            override def run() = MessageDialog.openInformation(shell, dialogTitle,
              "Repository upgraded succcessfully. " +
              "Restart the application to ensure it is loaded correctly.")
          })

          Status.OK_STATUS
        }
      }

      job.schedule()

    }

  }

}
