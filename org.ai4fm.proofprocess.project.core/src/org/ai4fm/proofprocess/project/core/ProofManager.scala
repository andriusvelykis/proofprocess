package org.ai4fm.proofprocess.project.core

import java.io.IOException

import scala.collection.JavaConverters._

import org.ai4fm.proofprocess.cdo.PProcessCDO
import org.ai4fm.proofprocess.log.{ProofLog, ProofProcessLogFactory}
import org.ai4fm.proofprocess.project.{Project, ProjectProofProcessFactory}
import org.ai4fm.proofprocess.project.core.internal.ProjectPProcessCorePlugin.{error, log, plugin}

import org.eclipse.core.filesystem.URIUtil
import org.eclipse.core.resources.{IFile, IProject}
import org.eclipse.core.runtime.{
  CoreException,
  IProgressMonitor,
  NullProgressMonitor,
  QualifiedName,
  SubProgressMonitor
}
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.emf.cdo.CDOObject
import org.eclipse.emf.cdo.transaction.CDOTransaction
import org.eclipse.emf.cdo.util.CommitException
import org.eclipse.emf.common.command.BasicCommandStack
import org.eclipse.emf.common.util.{EList, URI}
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain
import org.eclipse.emf.edit.provider.ComposedAdapterFactory


/**
 * @author Andrius Velykis
 */
object ProofManager {

  /** Key for the loaded project reference on resource */
  lazy private val PROP_PROOF_PROCESS =
    new QualifiedName(plugin.pluginId, "proofProcess") //$NON-NLS-1$

  @throws(classOf[CoreException])
  def proofProject(projectResource: IProject,
                   monitor: IProgressMonitor = new NullProgressMonitor): Project =
    proofManager(projectResource, monitor).proofs

  @throws(classOf[CoreException])
  def proofLog(projectResource: IProject,
               monitor: IProgressMonitor = new NullProgressMonitor): ProofLog =
    proofManager(projectResource, monitor).proofLog
  
  @throws(classOf[CoreException])
  private def proofManager(projectResource: IProject, monitor: IProgressMonitor): ProjectProofManager = {

    // retrieve the stored manager, or load one otherwise
    storedManager(projectResource) getOrElse {

      // create/load manager
      val submonitor = subMonitor(monitor)

      try {
        // start a rule on the resource to block when loading the manager
        Job.getJobManager.beginRule(projectResource, monitor)

        // check maybe the manager has already been loaded (double-checked locking)
        storedManager(projectResource) getOrElse {

          monitor.beginTask("Initialising proof manager", IProgressMonitor.UNKNOWN)

          // TODO what if the project gets renamed?
          val projectProofMan = loadProofProcess(projectResource, monitor)

          projectResource.setSessionProperty(PROP_PROOF_PROCESS, projectProofMan)

          projectProofMan
        }

      } finally {
        Job.getJobManager.endRule(projectResource)
        submonitor.done()
      }
    }
  }

  private def loadProofProcess(projectResource: IProject, monitor: IProgressMonitor): ProjectProofManager = {

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

    val session = PProcessCDO.session(databaseLocUri, repositoryName, monitor)
    val transaction = session.openTransaction(editingDomain.getResourceSet)

    // resolve the "proof" and "prooflog" resources in the CDO repository
    val proofRoot = loadCreateMigrateRoot(transaction, projectResource,
      "proof",
      proofProjectFile(projectResource),
      ProjectProofProcessFactory.eINSTANCE.createProject,
      {
        case p: Project => Some(p)
        case _ => None
      },
      monitor)

    val proofLogRoot = loadCreateMigrateRoot(transaction, projectResource,
      "prooflog",
      proofLogFile(projectResource),
      ProofProcessLogFactory.eINSTANCE.createProofLog,
      {
        case l: ProofLog => Some(l)
        case _ => None
      },
      monitor)

    ProjectProofManager(proofRoot, proofLogRoot)
  }

  private def storedManager(projectResource: IProject): Option[ProjectProofManager] =
    projectResource.getSessionProperty(PROP_PROOF_PROCESS) match {
      case manager: ProjectProofManager => Some(manager)
      case _ => None
    }

  private def subMonitor(monitor: IProgressMonitor): IProgressMonitor = {
    Option(monitor) match {
      case Some(monitor) => new SubProgressMonitor(monitor, 10)
      case None => new NullProgressMonitor
    }
  }

  private def loadCreateMigrateRoot[T <: EObject](transaction: CDOTransaction, projectResource: IProject,
    repositoryName: String, migrateFile: IFile, create: => T, convert: EObject => Option[T],
    monitor: IProgressMonitor): T = {

    // resolve the resource in the CDO repository
    val proofResource = loadResource(transaction, repositoryName)
    val proofRoot = proofResource.asScala.headOption.flatMap(convert) match {
      case Some(p) => p
      case None => {
        // the root project is not available - allocate a new one
        // create a new project, add to the resource and commit

        // before creating a new one, try migrating existing XML-based model
        val migrateProject = migrateXmlFileHistory(projectResource, migrateFile, convert, monitor)

        val newProject = migrateProject getOrElse create

        proofResource.clear
        proofResource.add(newProject)
        commitTransaction(transaction, monitor)

        newProject
      }
    }

    proofRoot
  }

  private def loadResource(transaction: CDOTransaction, resourceName: String): EList[EObject] = {
    val emfResource = transaction.getOrCreateResource(resourceName)

    try {
      // load EMF resource
      emfResource.load(null)
    } catch {
      case e: IOException => log(error(Some(e)))
    }

    emfResource.getContents
  }

  def commitTransaction(cdoObj: CDOObject, monitor: IProgressMonitor) {
    cdoObj.cdoView match {
      case tr: CDOTransaction => commitTransaction(tr, monitor)
      case _ => log(error(msg = Some("Cannot commit transaction - object does not belong to one: " + cdoObj)))
    }
  }

  def commitTransaction(transaction: CDOTransaction, monitor: IProgressMonitor) {
    try {
      transaction.commit(monitor)
    } catch {
      case e: CommitException => log(error(Some(e)))
    }
  }

  private case class ProjectProofManager(val proofs: Project, val proofLog: ProofLog)

  /** Load file history from XML to migrate */
  private def migrateXmlFileHistory[T](projectResource: IProject, file: IFile, convert: EObject => Option[T],
    monitor: IProgressMonitor): Option[T] = {

    // Create a ComposedAdapterFactory for all registered models
    val adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)
    val editingDomain = new AdapterFactoryEditingDomain(adapterFactory, new BasicCommandStack)

    val proofRoot = loadProofFile(editingDomain, file)

    proofRoot.flatMap(convert)
  }

  private def loadProofFile[T](editingDomain: AdapterFactoryEditingDomain, file: IFile): Option[EObject] = {

    val uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true)
    val emfResource = editingDomain.getResourceSet().createResource(uri)

    if (file.exists) {
      try {
        // load EMF resource
        emfResource.load(null)
      } catch {
        case e: IOException => log(error(Some(e)))
      }

      emfResource.getContents.asScala.headOption
    } else {
      None
    }
  }

  def proofFolder = ".proofprocess/"

  private def proofProjectFile(projectResource: IProject) =
    projectResource.getFile(proofFolder + "project.proof")

  private def proofLogFile(projectResource: IProject) =
    projectResource.getFile(proofFolder + "project.prooflog")

  private def initResourceFactories() {

    // Register the XMI resource factory for the .proof and .prooflog extensions
    val reg = Resource.Factory.Registry.INSTANCE;
    reg.getExtensionToFactoryMap().put("proof", new XMIResourceFactoryImpl());
    reg.getExtensionToFactoryMap().put("prooflog", new XMIResourceFactoryImpl());
  }

}
