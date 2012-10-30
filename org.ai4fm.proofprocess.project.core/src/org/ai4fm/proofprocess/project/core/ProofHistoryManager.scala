package org.ai4fm.proofprocess.project.core

import java.io.IOException
import scala.collection.JavaConversions.asScalaBuffer
import org.ai4fm.filehistory.FileHistoryFactory
import org.ai4fm.filehistory.FileHistoryProject
import org.ai4fm.filehistory.FileVersion
import org.ai4fm.filehistory.core.FileHistoryUtil
import org.ai4fm.filehistory.core.IFileHistoryManager
import org.ai4fm.proofprocess.cdo.PProcessCDOPlugin
import org.eclipse.core.filesystem.URIUtil
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.core.runtime.Path
import org.eclipse.core.runtime.QualifiedName
import org.eclipse.core.runtime.SubProgressMonitor
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.emf.cdo.util.CommitException
import org.eclipse.emf.common.command.BasicCommandStack
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import ProjectPProcessCorePlugin._
import org.ai4fm.filehistory.core.XmlFileHistoryManager

/**
  * @author Andrius Velykis
  */
object ProofHistoryManager {

  private def historyFileDir = "files"

  /** Key for the loaded project reference on resource */
  lazy private val PROP_FILE_HISTORY =
    new QualifiedName(plugin.pluginId, "fileHistory") //$NON-NLS-1$

  @throws(classOf[CoreException])
  private def historyManager(projectResource: IProject, monitor: IProgressMonitor): ProjectHistoryManager = {

    // retrieve the stored manager, or load one otherwise
    storedManager(projectResource) getOrElse {

      // create/load manager
      val submonitor = subMonitor(monitor)

      try {
        // start a rule on the resource to block when loading the manager
        Job.getJobManager.beginRule(projectResource, monitor)

        // check maybe the manager has already been loaded (double-checked locking)
        storedManager(projectResource) getOrElse {

          monitor.beginTask("Initialising manager", IProgressMonitor.UNKNOWN)

          // TODO what if the project gets moved?
          val proofProcessRoot = projectResource.getLocation.append(ProofManager.PROOF_FOLDER)
          val historyDir = proofProcessRoot.append(historyFileDir)

          val historyManager = FileHistoryUtil.historyManager(proofProcessRoot.toFile, historyDir.toFile)
          val historyProject = loadHistory(projectResource, monitor)

          val projectHistoryMan = ProjectHistoryManager(historyManager, historyProject)
          projectResource.setSessionProperty(PROP_FILE_HISTORY, projectHistoryMan)

          projectHistoryMan
        }

      } finally {
        Job.getJobManager.endRule(projectResource)
        submonitor.done()
      }
    }
  }

  private def storedManager(projectResource: IProject): Option[ProjectHistoryManager] =
    projectResource.getSessionProperty(PROP_FILE_HISTORY) match {
      case manager: ProjectHistoryManager => Some(manager)
      case _ => None
    }

  private def subMonitor(monitor: IProgressMonitor): IProgressMonitor = {
    Option(monitor) match {
      case Some(monitor) => new SubProgressMonitor(monitor, 10)
      case None => new NullProgressMonitor
    }
  }

  private def loadHistory(projectResource: IProject, monitor: IProgressMonitor) = {

    // Create a ComposedAdapterFactory for all registered models
    val adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)
    val editingDomain = new AdapterFactoryEditingDomain(adapterFactory, new BasicCommandStack)

    // FIXME better repository names?
    val repositoryName = projectResource.getName

    // store database in workspace plug-in location
    // TODO investigate configuration/user locations
    // (via Platform.getConfigurationLocation or .getUserLocation)
    val databaseLocPath = plugin.getStateLocation.append("database")
    val databaseLocUri = URIUtil.toURI(databaseLocPath)

    val session = PProcessCDOPlugin.plugin.session(databaseLocUri, repositoryName)
    val transaction = session.openTransaction(editingDomain.getResourceSet)

    // resolve the "filehistory" resource in the CDO repository
    val emfResource = transaction.getOrCreateResource("filehistory")

    try {
      // load EMF resource
      emfResource.load(null)
    } catch {
      case e: IOException => log(error(Some(e)))
    }

    val resourceContents = emfResource.getContents()

    // look for the history project as the root element
    resourceContents.headOption match {
      case Some(e: FileHistoryProject) => e
      case _ => {

        // the history project is not available - allocate a new one
        // create a new project, add to the resource and commit
        
        // before creating a new one, try migrating existing XML-based history
        val migrateProject = migrateXmlFileHistory(projectResource, monitor)
        
        val newProject = migrateProject getOrElse FileHistoryFactory.eINSTANCE.createFileHistoryProject
        
        resourceContents.clear
        resourceContents.add(newProject)
        try {
          transaction.commit(monitor)
        } catch {
          case e: CommitException => log(error(Some(e)))
        }

        newProject
      }
    }
  }

  @throws(classOf[CoreException])
  def syncFileVersion(project: IProject, filePath: IPath, text: Option[String], syncPoint: Option[Int],
    monitor: IProgressMonitor): FileVersion = {

    // if the file path is absolute to workspace, make it relative to project
    val relativePath = if (filePath.isAbsolute()) {
      filePath.makeRelativeTo(project.getLocation)
    } else {
      filePath
    }

    syncFileVersion(project, relativePath.toPortableString, text, syncPoint, monitor)
  }

  @throws(classOf[CoreException])
  def syncFileVersion(projectResource: IProject, path: String, text: Option[String], syncPoint: Option[Int],
    monitor: IProgressMonitor): FileVersion = {

    val historyMan = historyManager(projectResource, monitor)

    val (syncedVersion, changed) = historyMan.manager.syncFileVersion(historyMan.history,
      projectResource.getLocation().toOSString, path, text, syncPoint, monitor)

    if (changed) {
      // refresh the created file
      val file = versionFile(historyMan.manager, projectResource, syncedVersion)
      file.refreshLocal(IResource.DEPTH_ZERO, null)
    }
    // TODO transaction

    syncedVersion
  }

  private def versionFile(historyManager: IFileHistoryManager, projectResource: IProject,
    version: FileVersion): IFile = {

    val versionPath = Path.fromOSString(historyManager.historyFile(version).getPath)
    val relativePath = versionPath.makeRelativeTo(projectResource.getLocation)

    projectResource.getFile(relativePath)
  }

  private case class ProjectHistoryManager(val manager: IFileHistoryManager, val history: FileHistoryProject)

  /** Load file history from XML to migrate */
  private def migrateXmlFileHistory(projectResource: IProject,
      monitor: IProgressMonitor): Option[FileHistoryProject] = {
    
    val historyPath = projectResource.getLocation.append(ProofManager.PROOF_FOLDER).toOSString
    Option(XmlFileHistoryManager.getHistoryProject(historyPath, monitor))
  }
  
}
