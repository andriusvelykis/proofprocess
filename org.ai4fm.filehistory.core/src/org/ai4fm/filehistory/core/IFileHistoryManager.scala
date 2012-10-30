package org.ai4fm.filehistory.core

import java.io.File

import org.ai4fm.filehistory.FileHistoryProject
import org.ai4fm.filehistory.FileVersion
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor

/**
  * @author Andrius Velykis
  */
trait IFileHistoryManager {

  @throws(classOf[CoreException])
  def syncFileVersion(historyProject: FileHistoryProject, sourceRootPath: String, sourcePath: String,
    textOpt: Option[String], syncPointOpt: Option[Int], monitor: IProgressMonitor): FileVersion
  
  def historyFile(version: FileVersion): File
  
}
