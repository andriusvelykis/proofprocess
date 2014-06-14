package org.ai4fm.proofprocess.project.core

import java.io.IOException

import scala.collection.JavaConverters.asScalaBufferConverter

import org.ai4fm.filehistory.FileHistoryFactory
import org.ai4fm.filehistory.FileHistoryProject
import org.ai4fm.proofprocess.cdo.PProcessCDO
import org.ai4fm.proofprocess.log.ProofLog
import org.ai4fm.proofprocess.log.ProofProcessLogFactory
import org.ai4fm.proofprocess.project.Project
import org.ai4fm.proofprocess.project.ProjectProofProcessFactory
import org.ai4fm.proofprocess.project.core.internal.ProjectPProcessCorePlugin.error
import org.ai4fm.proofprocess.project.core.internal.ProjectPProcessCorePlugin.log
import org.ai4fm.proofprocess.project.core.internal.ProjectPProcessCorePlugin.plugin
import org.eclipse.core.filesystem.URIUtil
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.core.runtime.QualifiedName
import org.eclipse.core.runtime.SubProgressMonitor
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.emf.cdo.CDOObject
import org.eclipse.emf.cdo.session.CDOSession
import org.eclipse.emf.cdo.transaction.CDOTransaction
import org.eclipse.emf.cdo.util.CommitException
import org.eclipse.emf.cdo.view.CDOView
import org.eclipse.emf.common.command.BasicCommandStack
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain
import org.eclipse.emf.edit.provider.ComposedAdapterFactory


/**
 * @author Andrius Velykis
 */
object PProcessDataManager {

  /** Key for the loaded CDO session reference on resource */
  lazy private val PROP_PPROCESS_SESSION =
    new QualifiedName(plugin.pluginId, "pprocessSession")

  @throws(classOf[CoreException])
  def session(projectResource: IProject,
              monitor: IProgressMonitor = new NullProgressMonitor): CDOSession = {

    // retrieve the stored session, or initialise one otherwise
    storedSession(projectResource) getOrElse {

      // create/load manager
      val submonitor = subMonitor(monitor)

      try {
        // start a rule on the resource to block when initialising the session
        Job.getJobManager.beginRule(projectResource, submonitor)

        // check maybe the session has already been loaded (double-checked locking)
        storedSession(projectResource) getOrElse {

          submonitor.beginTask("Initialising proof process data session", IProgressMonitor.UNKNOWN)

          // TODO what if the project gets renamed?
          val projectSession = initSession(projectResource, submonitor)

          projectResource.setSessionProperty(PROP_PPROCESS_SESSION, projectSession)

          // ensure data roots exist and perform data migration if necessary during initialisation
          initDataRoots(projectSession, projectResource, submonitor)

          projectSession
        }

      } finally {
        Job.getJobManager.endRule(projectResource)
        submonitor.done()
      }
    }
  }

  private def initSession(projectResource: IProject,
                          monitor: IProgressMonitor): CDOSession = {
    val (databaseLocUri, repositoryName) = repoInfo(projectResource)
    val session = PProcessCDO.session(databaseLocUri, repositoryName, monitor)
    session
  }

  private def initDataRoots(session: CDOSession,
                            project: IProject,
                            monitor: IProgressMonitor) {

    val transaction = openTransaction(session)
    val fileResourceSet = newResourceSet()

    val rootInfos = List(ProofsRoot, ProofLogRoot, FileHistoryRoot)
    rootInfos foreach { ensureRoot(transaction, fileResourceSet, project, monitor, _) }

    transaction.close()
  }

  def openTransaction(session: CDOSession): CDOTransaction =
    session.openTransaction(newResourceSet)

  private def newResourceSet(): ResourceSet = {
    // Create a ComposedAdapterFactory for all registered models
    val adapterFactory =
      new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)
    val editingDomain = new AdapterFactoryEditingDomain(adapterFactory, new BasicCommandStack)
    
    editingDomain.getResourceSet
  }

  private def ensureRoot[T <: EObject](transaction: CDOTransaction,
                                       fileResourceSet: ResourceSet,
                                       projectResource: IProject,
                                       monitor: IProgressMonitor,
                                       rootInfo: ResourceRootInfo[T]) {

    // resolve the resource in the CDO repository
    val proofResource = loadResource(transaction, rootInfo.resourceName)
    val proofRoot = proofResource.asScala.headOption flatMap rootInfo.matchRoot match {
      case Some(p) => // do nothing if root is already in the database
      case None => {
        // the root object is not available - check if need to load from XML
        val fileRootOpt = loadProofFile(fileResourceSet, rootInfo.repoXmlFile(projectResource))

        val fileRoot = fileRootOpt flatMap rootInfo.matchRoot

        // if file root does not exist (nothing to migrate), create a new root
        val newRoot = fileRoot getOrElse rootInfo.newRoot()

        // store the migrated/new root to the DB
        proofResource.clear
        proofResource.add(newRoot)
        commitTransaction(transaction, monitor)
      }
    }
  }

  private sealed trait ResourceRootInfo[T] {
    def resourceName: String
    def repoXmlFile(project: IProject): IFile
    def matchRoot(eObj: EObject): Option[T]
    def newRoot(): T
  }

  private object ProofsRoot extends ResourceRootInfo[Project] {

    val resourceName = "proof"

    def repoXmlFile(project: IProject) = project.getFile(proofFolder + "project.proof")

    def matchRoot(eObj: EObject): Option[Project] = eObj match {
      case p: Project => Some(p)
      case _ => None
    }

    def newRoot() = ProjectProofProcessFactory.eINSTANCE.createProject
  }

  private object ProofLogRoot extends ResourceRootInfo[ProofLog] {

    val resourceName = "prooflog"

    def repoXmlFile(project: IProject) = project.getFile(proofFolder + "project.prooflog")

    def matchRoot(eObj: EObject): Option[ProofLog] = eObj match {
      case p: ProofLog => Some(p)
      case _ => None
    }

    def newRoot() = ProofProcessLogFactory.eINSTANCE.createProofLog
  }

  private object FileHistoryRoot extends ResourceRootInfo[FileHistoryProject] {

    val resourceName = "filehistory"

    def repoXmlFile(project: IProject) = project.getFile(proofFolder + "project.filehistory")

    def matchRoot(eObj: EObject): Option[FileHistoryProject] = eObj match {
      case p: FileHistoryProject => Some(p)
      case _ => None
    }

    def newRoot() = FileHistoryFactory.eINSTANCE.createFileHistoryProject
  }

  /**
   * Retrieves proof process repository information for the given project resource.
   */
  def repoInfo(projectResource: IProject): (java.net.URI, String) = {
    // FIXME better repository names?
    val repositoryName = projectResource.getName

    // store database in workspace plug-in location
    // TODO investigate configuration/user locations
    // (via Platform.getConfigurationLocation or .getUserLocation)
    val databaseLocPath = plugin.getStateLocation.append("database")
    val databaseLocUri = URIUtil.toURI(databaseLocPath)

    (databaseLocUri, repositoryName)
  }

  private def storedSession(projectResource: IProject): Option[CDOSession] =
    projectResource.getSessionProperty(PROP_PPROCESS_SESSION) match {
      case session: CDOSession => Some(session)
      case _ => None
    }

  private def subMonitor(monitor: IProgressMonitor): IProgressMonitor = {
    Option(monitor) match {
      case Some(monitor) => new SubProgressMonitor(monitor, 10)
      case None => new NullProgressMonitor
    }
  }

  private def loadResource(view: CDOView, resourceName: String): EList[EObject] = {
    val emfResource = view match {
      case transaction: CDOTransaction => transaction.getOrCreateResource(resourceName)
      case _ => view.getResource(resourceName)
    }

    try {
      // load EMF resource
      emfResource.load(null)
    } catch {
      case e: IOException => log(error(Some(e)))
    }

    emfResource.getContents
  }

  private def loadResourceRoot[T](view: CDOView, rootInfo: ResourceRootInfo[T]): Option[T] = {
    val resourceElems = loadResource(view, rootInfo.resourceName)

    val rootEObj = resourceElems.asScala.headOption
    val root = rootEObj flatMap rootInfo.matchRoot
    root
  }

  def commitTransaction(cdoObj: CDOObject, monitor: IProgressMonitor) {
    cdoObj.cdoView match {
      case tr: CDOTransaction => commitTransaction(tr, monitor)
      case _ => log(error(
        msg = Some("Cannot commit transaction - object does not belong to one: " + cdoObj)))
    }
  }

  def commitTransaction(transaction: CDOTransaction, monitor: IProgressMonitor) {
    try {
      transaction.commit(monitor)
    } catch {
      case e: CommitException => log(error(Some(e)))
    }
  }

  private def loadProofFile[T](resourceSet: ResourceSet,
                               file: IFile): Option[EObject] = {

    val uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true)
    val emfResource = resourceSet.createResource(uri)

    if (file.exists) {

      // Register the XMI resource factory for the extension
      val reg = Resource.Factory.Registry.INSTANCE;
      reg.getExtensionToFactoryMap.put(file.getFileExtension, new XMIResourceFactoryImpl());

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

  case class PProcessData(proofs: Project, log: ProofLog, files: FileHistoryProject)

  def loadData(view: CDOView,
               monitor: IProgressMonitor = new NullProgressMonitor): PProcessData = {

    // assume all roots exist!
    val proofs = loadResourceRoot(view, ProofsRoot).get
    val log = loadResourceRoot(view, ProofLogRoot).get
    val files = loadResourceRoot(view, FileHistoryRoot).get
    
    PProcessData(proofs, log, files)
  }
  
}
