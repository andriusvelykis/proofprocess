package org.ai4fm.proofprocess.cdo.internal

import java.net.URI

import scala.actors.Future
import scala.actors.Futures.future
import scala.collection.mutable.{HashMap, SynchronizedMap}

import org.ai4fm.proofprocess.cdo.internal.db.{PProcessRepository, RepositoryUtil}

import org.eclipse.core.runtime.{IProgressMonitor, IStatus, NullProgressMonitor, Plugin, Status}
import org.eclipse.emf.cdo.session.CDOSession

import org.osgi.framework.BundleContext


/**
  * @author Andrius Velykis 
  */
object PProcessCDOPlugin {

  // The shared instance
  private var instance: PProcessCDOPlugin = _
  def plugin = instance

  def log(status: IStatus) = plugin.getLog.log(status)

  /** Returns a new error `IStatus` for this plug-in.
    *
    * @param message    text to have as status message
    * @param exception  exception to wrap in the error `IStatus`
    * @return  the error `IStatus` wrapping the exception
    */
  def error(ex: Option[Throwable] = None, msg: Option[String] = None): IStatus = {
    
    // if message is not given, try to use exception's
    val message = msg orElse { ex.flatMap(ex => Option(ex.getMessage)) }
    
    new Status(IStatus.ERROR, plugin.pluginId, 0, message.orNull, ex.orNull)
  }

  /** 
   * Creates a new information `IStatus` for this plug-in with the given message.
   */
  def info(msg: String): IStatus = new Status(IStatus.INFO, plugin.pluginId, 0, msg, null)

}

class PProcessCDOPlugin extends Plugin {

  PProcessCDOPlugin.instance = this
  
  // The plug-in ID
  def pluginId = "org.ai4fm.proofprocess.cdo" //$NON-NLS-1$

  /** A synchronized lazy map for connected CDO repositories */
  private val repositories =
    new HashMap[RepositoryId, Future[PProcessRepository]]
      with SynchronizedMap[RepositoryId, Future[PProcessRepository]]
  
  /** Retrieves a Proof Process repository with a given name.
    * Initializes a new one if no such repository exists currently.
    */
  private def repository(repoId: RepositoryId, monitor: IProgressMonitor) = {
    // get the repository future from the map, or create new one if not exists
    val repoFuture = repositories.getOrElseUpdate(repoId, future {
      initRepository(repoId, monitor)
    })
    
    // block until the future is resolved (repository is connected initially)
    val r = repoFuture();
    r
  }

  private def initRepository(repoId: RepositoryId,
                             monitor: IProgressMonitor): PProcessRepository = {
    val newRepo = initNewRepositoryConnection(repoId)

    if (RepositoryUtil.needsUpgrade(newRepo.repository)) {
      // upgrade current repository and then initialise a new connection
      RepositoryUtil.upgradeRepository(newRepo,
        subTask(monitor, "Upgrading ProofProcess repository..."))

      initNewRepositoryConnection(repoId)

    } else {
      newRepo
    }
  }

  private def initNewRepositoryConnection(repoId: RepositoryId): PProcessRepository =
    new PProcessRepository(repoId.databaseLoc, repoId.name)


  private def subTask(monitor: IProgressMonitor, name: String): IProgressMonitor = {
    monitor.subTask(name)
    monitor
  }

  /**
   * Retrieves an open session for the given repository.
   *
   * Initialises a new repository if one does not exist, migrates existing file-based data or
   * upgrades the repository if it uses old EMF packages.
   */
  def session(databaseLoc: URI,
              repositoryName: String,
              monitor: IProgressMonitor = new NullProgressMonitor): CDOSession =
    repository(RepositoryId(databaseLoc, repositoryName), monitor).session


  private case class RepositoryId(val databaseLoc: URI, val name: String)
  
  @throws(classOf[Exception])
  override def stop(context: BundleContext) {

    // deactivate each repository
    repositories.values.foreach(_().deactivate)
    
    super.stop(context)
  }
  
}
