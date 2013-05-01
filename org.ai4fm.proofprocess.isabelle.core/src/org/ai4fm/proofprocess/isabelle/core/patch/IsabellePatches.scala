package org.ai4fm.proofprocess.isabelle.core.patch

import java.io.{File, PrintWriter}
import java.net.URL

import scala.collection.JavaConverters._
import scala.io.{BufferedSource, Source}

import org.ai4fm.proofprocess.isabelle.core.IsabellePProcessCorePlugin

import difflib.DiffUtils


/**
 * A manager for Isabelle patches required for ProofProcess.
 *
 * @author Andrius Velykis
 */
object IsabellePatches {

  private val PATCH_DIR = "isa-src/patches/"
  private lazy val PATCH_DIR_URL = IsabellePProcessCorePlugin.plugin.getBundle.getEntry(PATCH_DIR)

  private val PATCHED_COMMENT_REGEX = "Patched for AI4FM ProofProcess v([0-9]+)".r

  private val allPatches: List[PatchInfo] = List(
    PatchInfo("trace_goal_terms.patch", "201305011000", "src/Pure/goal_display.ML")
    )

  case class PatchInfo(patch: String, version: String, targetPath: String) {

    private[IsabellePatches] def targetFile(rootDir: File): File =
      new File(rootDir, targetPath)

  }


  def findUnpatched(isabelleHomePath: String): Either[String, List[PatchInfo]] = {
    val isabelleHome = new File(isabelleHomePath)

    val checks = allPatches.toStream map checkPatched(isabelleHome)

    val empty: Either[String, List[PatchInfo]] = Right(Nil)

    val errs = checks map (_.left.toOption)
    val err = errs.flatten.headOption

    err match {
      case Some(err) => Left(err)

      case None => {
        // no errors - unpack all patches, if available
        val checkResults = checks map (_.right.toOption)
        Right(checkResults.flatten.flatten.toList)
      }
    }
  }


  private def checkPatched(isabelleHome: File)(
                             patch: PatchInfo): Either[String, Option[PatchInfo]] = {

    val targetFile = patch.targetFile(isabelleHome)

    parsePatchVersion(targetFile) match {
      // unpatched
      case None => Right(Some(patch))

      case Some(version) => if (version == patch.version) {
        // matching version - patched correctly
        Right(None)
      } else {
        Left(badVersionMessage(version))
      }
    }
  }


  private def parsePatchVersion(file: File): Option[String] = {

    if (!file.exists) {
      None
    } else {
      val source = Source.fromFile(file)
      val linesIt = source.getLines

      // search for patch version in each line
      val patchVersionIt = (linesIt map findPatchVersion).flatten

      // get the first found, if available
      val result = if (patchVersionIt.hasNext) Some(patchVersionIt.next) else None
      source.close()

      result
    }
  }

  private def findPatchVersion(text: String): Option[String] =
    PATCHED_COMMENT_REGEX.findFirstMatchIn(text) map (_.group(1))

  private def badVersionMessage(badVersion: String): String =
    "Invalid ProofProcess patch version found (" + badVersion + ").\n\n" +
      "Use a clean Isabelle installation to apply correct patches."
  

  def applyPatches(isabelleHomePath: String, patches: List[PatchInfo]) {

    val isabelleHome = new File(isabelleHomePath)

    patches foreach doApplyPatch(isabelleHome)
  }

  private def doApplyPatch(targetDir: File)(patchInfo: PatchInfo) {
    val patchText = readURLTextLines(patchURL(patchInfo.patch))

    val patch = DiffUtils.parseUnifiedDiff(patchText.asJava)

    val targetFile = patchInfo.targetFile(targetDir)
    val targetText = readFileTextLines(targetFile)

    val targetResult = patch.applyTo(targetText.asJava)

    saveFile(targetFile, targetResult.asScala.mkString("\n"))
  }

  private def patchURL(patchPath: String) = new URL(PATCH_DIR_URL, patchPath)

  private def readURLTextLines(url: URL): List[String] = readTextLines(Source.fromURL(url))
  
  private def readFileTextLines(file: File): List[String] =
    if (file.exists) readTextLines(Source.fromFile(file)) else Nil

  private def readTextLines(source: BufferedSource): List[String] = {
    val lines = source.getLines.toList
    source.close()
    lines
  }

  private def saveFile(file: File, text: String) = printToFile(file)(p => p.print(text))

  private def printToFile(file: File)(op: PrintWriter => Unit) {
    // create parent directories if parent exists
    Option(file.getParentFile).foreach(_.mkdirs)
    
    val p = new PrintWriter(file)
    try { op(p) } finally { p.close() }
  }
    
}
