package org.ai4fm.filehistory.core.internal

import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.UUID

import scala.annotation.tailrec
import scala.collection.JavaConversions.asScalaBuffer
import scala.io.Source

import org.ai4fm.filehistory.FileEntry
import org.ai4fm.filehistory.FileHistoryFactory
import org.ai4fm.filehistory.FileHistoryProject
import org.ai4fm.filehistory.FileVersion
import org.ai4fm.filehistory.core.FileHistoryCorePlugin.error
import org.ai4fm.filehistory.core.FileHistoryCorePlugin.log
import org.ai4fm.filehistory.core.IFileHistoryManager
import org.eclipse.core.runtime.Assert
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor

/**
  * @author Andrius Velykis 
  */
class FileHistoryManager(val historyRoot: File, val historyFileDir: File) extends IFileHistoryManager {

  private def checksumPart(text: String, textPoint: Int, fullChecksum: /*=> */ String): String =
    if (textPoint == text.length) {
      fullChecksum
    } else {
      checksum(text, textPoint)
    }

  private def checksum(text: String, textPoint: Int): String = {
    Assert.isLegal(textPoint <= text.length())
    checksum(text.substring(0, textPoint))
  }

  private def checksum(text: String): String = {
    try {
      hash(text, "SHA-256")
    } catch {
      case e: NoSuchAlgorithmException => {
        log(error(Some(e)))
        // return empty checksum (should never happen, since SHA-256 is part of Java)
        ""
      }
    }
  }

  @throws(classOf[NoSuchAlgorithmException])
  private def hash(text: String, algorithm: String) = {
    val hash = MessageDigest.getInstance(algorithm).digest(text.getBytes)
    // encode in hex
    val bi = new BigInteger(1, hash)
    val result = bi.toString(16)

    if (result.length % 2 != 0) "0" + result else result
  }

  /** Checks if the text portion up to the last sync point is unchanged (by checksum) */
  private def lastSyncUnchanged(text: String, newChecksum: /*=> */ String,
    lastSyncPoint: Int, lastSyncChecksum: String): Boolean =
    if (lastSyncPoint <= text.length) {
      // if text is longer than the last sync, take the part up the the sync point
      // and check if it matches the last sync checksum
      val newChecksumAtLastSync = checksumPart(text, lastSyncPoint, newChecksum)

      // check if old and new checksums up to the sync point match
      newChecksumAtLastSync == lastSyncChecksum
    } else {
      false
    }

  @throws(classOf[CoreException])
  override def syncFileVersion(historyProject: FileHistoryProject, sourceRootPath: String, sourcePath: String,
    textOpt: Option[String], syncPointOpt: Option[Int], monitor: IProgressMonitor): FileVersion = {

    // get the file record for the given path
    val fileEntry = historyEntry(historyProject, sourcePath)

    // load text from file if not available
    val text = textOpt.getOrElse(loadFile(new File(sourceRootPath, sourcePath)))

    // if no sync point indicated, use full text length
    val syncPoint = syncPointOpt.getOrElse(text.length)
    Assert.isLegal(syncPoint >= 0)
    Assert.isLegal(syncPoint <= text.length)

    // calculate checksum for new content
    val fileChecksum = checksum(text)

    // get the latest version
    val versions = fileEntry.getVersions

    // if last version is available, try to reuse that
    // compare with the last version - need to check if the version contents have
    // changed, and store the new version in the history if so
    val syncVersion = versions.lastOption flatMap { reuseLastVersion(text, syncPoint, fileChecksum, _) }

    // if version has been resolved, use it, otherwise create a new version
    syncVersion.getOrElse {

      // add a new version
      val version = FileHistoryFactory.eINSTANCE.createFileVersion
      versions.add(version)

      val targetFile = allocateNewFile()
      saveFileVersion(version, targetFile, text, fileChecksum, syncPoint)

      version
    }
  }

  private def historyEntry(historyProject: FileHistoryProject, path: String): FileEntry = {

    val historyFiles = historyProject.getFiles

    val foundOpt = historyFiles.find(_.getPath == path)

    // return found one or create a new one
    foundOpt getOrElse {
      // no file - create new
      // TODO synchronize for multiple access?
      val fileEntry = FileHistoryFactory.eINSTANCE.createFileEntry
      fileEntry.setPath(path)
      historyFiles.add(fileEntry)

      fileEntry
    }
  }

  private def reuseLastVersion(text: String, syncPoint: Int, fileChecksum: String,
    lastVersion: FileVersion): Option[FileVersion] = {

    if (fileChecksum == lastVersion.getChecksum) {
      // assume that the file contents are the same, since we are using SHA-256 hashing,
      // which has very low collision probability

      // use the last version as currently synced version
      Some(lastVersion)
    } else {

      // different full file contents:
      // check if partial contents are the same (e.g. everything up to the sync point
      // is unchanged), so that we would not care if the actual file changed outside
      // the proof process history "zone"

      val lastSyncPoint = lastVersion.getSyncPoint
      val lastSyncChecksum = lastVersion.getSyncChecksum

      if (lastSyncUnchanged(text, fileChecksum, lastSyncPoint, lastSyncChecksum)) {

        if (lastSyncPoint >= syncPoint) {
          // The current sync point is before the last one, but everything up to the
          // last sync point is unchanged, so keep the last version (the change happened
          // after the last sync)
          Some(lastVersion)
        } else {

          // Everything up to the last sync matches, so replace the last version with the
          // contents of the updated file (since it will encompass the old sync and add the new one)

          val versionFile = historyFile(lastVersion)
          saveFileVersion(lastVersion, versionFile, text, fileChecksum, syncPoint)
          Some(lastVersion)
        }
      } else {
        // changed before the last sync - cannot reuse the last version,
        // so will need a new version
        None
      }
    }
  }

  @throws(classOf[CoreException])
  private def saveFileVersion(version: FileVersion, targetFile: File, text: String,
    checksum: String, syncPoint: Int): FileVersion = {

    saveFile(targetFile, text)

    val relativePath = historyRoot.toURI().relativize(targetFile.toURI()).getPath

    version.setPath(relativePath)
    version.setTimestamp(System.currentTimeMillis())

    version.setChecksum(checksum)
    version.setSyncPoint(syncPoint)
    version.setSyncChecksum(checksumPart(text, syncPoint, checksum))

    version
  }

  override def historyFile(version: FileVersion): File = {
    val relativePath = version.getPath
    new File(historyRoot.toURI().resolve(relativePath))
  }

  @tailrec
  private def allocateNewFile(): File = {
      // generate new file ID that does not exist
      val newFileId = UUID.randomUUID.toString
      val file = new File(historyFileDir, newFileId)
      if (!file.exists) file else allocateNewFile()
  }

  private def fileEncoding = "UTF-8"

  @throws(classOf[CoreException])
  private def loadFile(file: File): String = {

    if (!file.exists) {
      throw new CoreException(error(msg = Some("Cannot locate file at path: " + file)))
    } else {
      try {
        val source = Source.fromFile(file, fileEncoding)
        try {
          source.mkString
        } finally {
          source.close
        }
      } catch {
        case ex: IOException =>
          throw new CoreException(error(Some(ex), Some("Problems reading from file at path: " + file)))
      }
    }
  }

  @throws(classOf[CoreException])
  private def saveFile(file: File, text: String) {
    try {
      printToFile(file)(p => p.print(text))
    } catch {
      case ex: IOException =>
        throw new CoreException(error(Some(ex), Some("Problems writing to file at path: " + file)))
    }
  }

  @throws(classOf[IOException])
  private def printToFile(file: File)(op: PrintWriter => Unit) {
    // create parent directories if parent exists
    Option(file.getParentFile).foreach(_.mkdirs)
    
    val p = new PrintWriter(file)
    try { op(p) } finally { p.close() }
  }

}