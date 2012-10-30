package org.ai4fm.filehistory.core

import java.io.File

/**
  * @author Andrius Velykis
  */
object FileHistoryUtil {

  def historyManager(historyRoot: File, historyFileDir: File): IFileHistoryManager =
    new org.ai4fm.filehistory.core.internal.FileHistoryManager(historyRoot, historyFileDir)
  
}
