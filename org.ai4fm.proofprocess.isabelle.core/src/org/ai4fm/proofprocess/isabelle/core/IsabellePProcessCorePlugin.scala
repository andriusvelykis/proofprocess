package org.ai4fm.proofprocess.isabelle.core

import org.ai4fm.proofprocess.isabelle.core.parse.SessionTracker
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Plugin
import org.eclipse.core.runtime.Status
import org.osgi.framework.BundleContext


/**
  * @author Andrius Velykis 
  */
object IsabellePProcessCorePlugin {

  // The shared instance
  private var instance: IsabellePProcessCorePlugin = _
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

class IsabellePProcessCorePlugin extends Plugin {

  IsabellePProcessCorePlugin.instance = this
  
  private var sessionTracker: Option[SessionTracker] = None
  
  // The plug-in ID
  def pluginId = "org.ai4fm.proofprocess.isabelle.core" //$NON-NLS-1$

  override def start(context: BundleContext) {
    super.start(context)

    val tracker = new SessionTracker
    this.sessionTracker = Some(tracker)

    tracker.init
  }

  override def stop(context: BundleContext) {

    sessionTracker foreach { _.dispose }
    super.stop(context)
  }
  
}
