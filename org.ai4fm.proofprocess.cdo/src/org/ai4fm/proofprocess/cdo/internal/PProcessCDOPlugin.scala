package org.ai4fm.proofprocess.cdo.internal

import java.net.URI

import org.ai4fm.proofprocess.cdo.internal.db.PProcessRepositoryManager

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

  private val repoManager = new PProcessRepositoryManager

  def session(databaseLoc: URI,
              repositoryName: String,
              monitor: IProgressMonitor = new NullProgressMonitor): CDOSession =
    repoManager.session(databaseLoc, repositoryName, monitor)

  def upgradeRepository(databaseLoc: URI,
                        repositoryName: String,
                        monitor: IProgressMonitor = new NullProgressMonitor) =
    repoManager.upgradeRepository(databaseLoc, repositoryName, monitor)


  @throws(classOf[Exception])
  override def stop(context: BundleContext) {
    repoManager.dispose()
    super.stop(context)
  }
  
}
