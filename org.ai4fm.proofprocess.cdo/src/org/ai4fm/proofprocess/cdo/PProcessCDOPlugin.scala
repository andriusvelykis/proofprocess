package org.ai4fm.proofprocess.cdo

import scala.actors.Future
import scala.actors.Futures.future
import scala.collection.mutable.HashMap
import scala.collection.mutable.SynchronizedMap

import org.ai4fm.proofprocess.cdo.internal.db.PProcessRepository
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Plugin
import org.eclipse.core.runtime.Status
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
    
    new Status(IStatus.ERROR, plugin.pluginId, 0, message.orNull, ex.orNull);
  }
}

class PProcessCDOPlugin extends Plugin {

  PProcessCDOPlugin.instance = this
  
  // The plug-in ID
  def pluginId = "org.ai4fm.proofprocess.cdo" //$NON-NLS-1$

  /** A synchronized lazy map for connected CDO repositories */
  private val repositories =
    new HashMap[String, Future[PProcessRepository]] with SynchronizedMap[String, Future[PProcessRepository]]
  
  /** Retrieves a Proof Process repository with a given name.
    * Initializes a new one if no such repository exists currently.
    */
  private def repository(repositoryName: String) = {
    // get the repository future from the map, or create new one if not exists
    val repoFuture = repositories.getOrElseUpdate(repositoryName, future {
      new PProcessRepository(repositoryName)
    })
    
    // block until the future is resolved (repository is connected initially)
    val r = repoFuture();
    r
  }
  
  /** Retrieves an open session for the given repository.
    * Initialises a new repository if one does not exist.
    */
  def session(repositoryName: String) = repository(repositoryName).session
  
  
  @throws(classOf[Exception])
  override def stop(context: BundleContext) {

    // deactivate each repository
    repositories.values.foreach(_().deactivate)
    
    super.stop(context)
  }
  
}
