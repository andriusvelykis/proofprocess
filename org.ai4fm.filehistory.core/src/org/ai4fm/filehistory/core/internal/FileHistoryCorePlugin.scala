package org.ai4fm.filehistory.core.internal

import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Plugin
import org.eclipse.core.runtime.Status

/**
  * @author Andrius Velykis 
  */
object FileHistoryCorePlugin {

  // The shared instance
  private var instance: FileHistoryCorePlugin = _
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

class FileHistoryCorePlugin extends Plugin {

  FileHistoryCorePlugin.instance = this
  
  // The plug-in ID
  def pluginId = "org.ai4fm.filehistory.core" //$NON-NLS-1$
  
}
