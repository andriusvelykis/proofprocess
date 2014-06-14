package org.ai4fm.proofprocess.project.core

import org.ai4fm.filehistory.FileHistoryProject
import org.ai4fm.filehistory.FileVersion
import org.ai4fm.filehistory.core.FileHistoryUtil
import org.ai4fm.filehistory.core.IFileHistoryManager
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Path

/**
 * @author Andrius Velykis
 */
object ProofHistoryManager {

  private def historyFileDir = "files"

  def historyManager(projectResource: IProject,
                     historyProject: FileHistoryProject): IFileHistoryManager = {

    // TODO what if the project gets moved?
    val proofProcessRoot = projectResource.getLocation.append(PProcessDataManager.proofFolder)
    val historyDir = proofProcessRoot.append(historyFileDir)

    FileHistoryUtil.historyManager(proofProcessRoot.toFile, historyDir.toFile)
  }

  @throws(classOf[CoreException])
  def syncFileVersion(manager: IFileHistoryManager,
                      history: FileHistoryProject,
                      project: IProject,
                      filePath: IPath,
                      text: Option[String],
                      syncPoint: Option[Int],
                      monitor: IProgressMonitor): FileVersion = {

    // if the file path is absolute to workspace, make it relative to project
    val relativePath = if (filePath.isAbsolute()) {
      filePath.makeRelativeTo(project.getLocation)
    } else {
      filePath
    }

    syncFileVersion(manager, history, project,
      relativePath.toPortableString, text, syncPoint, monitor)
  }

  @throws(classOf[CoreException])
  def syncFileVersion(manager: IFileHistoryManager,
                      history: FileHistoryProject,
                      projectResource: IProject,
                      path: String,
                      text: Option[String],
                      syncPoint: Option[Int],
                      monitor: IProgressMonitor): FileVersion = {

    val (syncedVersion, changed) = manager.syncFileVersion(
        history, projectResource.getLocation().toOSString, path, text, syncPoint, monitor)

    if (changed) {
      // refresh the created file
      val file = versionFile(manager, projectResource, syncedVersion)
      file.refreshLocal(IResource.DEPTH_ZERO, null)
    }

    // commit transaction after each sync
    PProcessDataManager.commitTransaction(syncedVersion, monitor)

    syncedVersion
  }

  private def versionFile(historyManager: IFileHistoryManager,
                          projectResource: IProject,
                          version: FileVersion): IFile = {

    val versionPath = Path.fromOSString(historyManager.historyFile(version).getPath)
    val relativePath = versionPath.makeRelativeTo(projectResource.getLocation)

    projectResource.getFile(relativePath)
  }

  private case class ProjectHistoryManager(
    val manager: IFileHistoryManager,
    val history: FileHistoryProject)

}
