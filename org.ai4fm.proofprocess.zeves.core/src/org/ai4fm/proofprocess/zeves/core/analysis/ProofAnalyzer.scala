package org.ai4fm.proofprocess.zeves.core.analysis

import java.util.Scanner

import org.ai4fm.filehistory.FileHistoryProject
import org.ai4fm.filehistory.FileVersion
import org.ai4fm.proofprocess.Attempt
import org.ai4fm.proofprocess.Loc
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ProofStore
import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.core.analysis.ProofAttemptMatcher.findCreateAttempt
import org.ai4fm.proofprocess.core.analysis.ProofAttemptMatcher.findCreateProof
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.project.core.PProcessDataManager
import org.ai4fm.proofprocess.project.core.PProcessDataManager.PProcessData
import org.ai4fm.proofprocess.project.core.ProofHistoryManager
import org.ai4fm.proofprocess.project.core.util.ProofProcessUtil
import org.ai4fm.proofprocess.project.core.util.ResourceUtil
import org.ai4fm.proofprocess.zeves.core.internal.ZEvesPProcessCorePlugin.error
import org.ai4fm.proofprocess.zeves.core.internal.ZEvesPProcessCorePlugin.log
import org.ai4fm.proofprocess.zeves.core.parse.ProofEntryData
import org.ai4fm.proofprocess.zeves.core.parse.ProofEntryReader
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Path
import org.eclipse.core.runtime.Status
import org.eclipse.emf.ecore.util.EcoreUtil

import net.sourceforge.czt.session.Key
import net.sourceforge.czt.session.SectionInfo
import net.sourceforge.czt.session.Source
import net.sourceforge.czt.zeves.snapshot.ISnapshotEntry


/** @author Andrius Velykis
  */
object ProofAnalyzer {

  @throws(classOf[CoreException])
  def analyze(sectInfo: SectionInfo,
              proofSnapshot: List[ISnapshotEntry],
              ppData: IProject => PProcessData)
      (implicit monitor: IProgressMonitor) = if (!proofSnapshot.isEmpty) {

    // take the last step as "active entry"
    val activeEntry = proofSnapshot.last

    val filePath = Path.fromOSString(activeEntry.getFilePath)
    Option(ResourceUtil.findProject(filePath)) match {
      case None => {
        // cannot load PP data without a file project
        error(msg = Some("Unable to locate project for resource " + filePath))
        Status.OK_STATUS;
      }

      case Some(project) => {
        val PProcessData(proofStore, proofLog, fileHistory) = ppData(project)

        val textLocFn =
          syncFileVersion(sectInfo, project, fileHistory, activeEntry, filePath, monitor)

        proofEntries(sectInfo, proofStore, proofSnapshot, textLocFn) match {
          case None => // no valid proof entries read
          case Some(proofEntryData) => {
            val (_, matchMapping) = analyzeEntries(proofStore, proofEntryData)

            // map snapshot entries to actually matched proof entries for logging
            val entryMatchMap = PProcessUtil.chainMaps(proofEntryData.entryMap, matchMapping)
            ProofActivityLogger.logProof(proofLog, entryMatchMap, List(activeEntry))

            // FIXME commit here or after all analysis? or somewhere else altogether?
            PProcessDataManager.commitTransaction(proofStore, monitor)
          }
        }

        Status.OK_STATUS;
      }
    }
  }
  
  private def chainMaps[A, B, C](m1: Map[A, B], m2: Map[B, C]): A => Option[C] = {
    val chained = (m1 andThen m2.lift).lift
    
    // unpack the nested option
    chained andThen (_ getOrElse None)
  }

  
  private def proofEntries(sectInfo: SectionInfo, proofStore: ProofStore,
                           proofSnapshot: List[ISnapshotEntry], entryLoc: ISnapshotEntry => Loc) = {

    val entryReader = new ProofEntryReader {

      lazy val stepIntent = PProcessUtil.getIntent(proofStore, "Tactic Application")

      // TODO review
      override def cloneTerm(term: Term): Term = EcoreUtil.copy(term)
      
      // TODO ignore some properties, e.g. display?
      override def matchTerms(term1: Term, term2: Term): Boolean = EcoreUtil.equals(term1, term2);

      override def textLoc(cmdState: ISnapshotEntry) = entryLoc(cmdState)
    }

    entryReader.readEntries(sectInfo, proofSnapshot)
  }

  @throws(classOf[CoreException])
  private def syncFileVersion(sectInfo: SectionInfo,
                              project: IProject,
                              history: FileHistoryProject,
                              activeEntry: ISnapshotEntry,
                              filePath: IPath,
                              monitor: IProgressMonitor): ISnapshotEntry => Loc = {

    val text = try {

      val source = snapshotEntrySource(sectInfo, activeEntry)
      val text = read(source)
      Some(text)

    } catch {
      case ex: Exception => {
        log(error(Some(ex)))
        // error getting the text
        None
      }
    }

    val posEnd = activeEntry.getPosition.offset + activeEntry.getPosition.length

    val historyMan = ProofHistoryManager.historyManager(project, history)
    val fileVersion = ProofHistoryManager.syncFileVersion(
      historyMan, history, project,
      filePath, text, Some(posEnd), monitor)

    // location resolution function
    def textLoc(snapshotEntry: ISnapshotEntry): Loc = {
      val pos = snapshotEntry.getPosition
      ProofProcessUtil.createTextLoc(fileVersion, pos.getOffset, pos.getLength);
    }

    textLoc
  }

  private def snapshotEntrySource(sectInfo: SectionInfo, entry: ISnapshotEntry): Source = {

    // check the source for this section - if it is not cached, use the DEFAULT name,
    // which means that it is the source from the editor. Otherwise, use saved file one.

    val sectionName = entry.getSectionName
    val sectionSourceKey = new Key[Source](sectionName, classOf[Source])

    if (sectInfo.isCached(sectionSourceKey)) {
      sectInfo.get(sectionSourceKey)
    } else {
      // use editor source
      // FIXME avoid hardcoding ZCompiler.DEFAULT_SECTION_NAME
      val defaultSourceKey = new Key[Source]("NEWSECTION", classOf[Source])
      sectInfo.get(defaultSourceKey)
    }
  }

  private def read(source: Source): String = {
    val reader = source.getReader

    try {
      // read everything as a single token in the scanner
      // http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string#answer-5445161
      val scanner = new Scanner(reader).useDelimiter("\\A")
      if (scanner.hasNext) scanner.next else ""
    } finally {
      reader.close
    }
  }
  
  private def analyzeEntries(proofStore: ProofStore,
                             entryData: ProofEntryData): (Attempt, Map[ProofEntry, ProofEntry]) = {

    val entryMatcher = ZEvesProofEntryMatcher

    val targetProof =
      findCreateProof(entryMatcher)(proofStore, entryData.label, entryData.goals)

    val attemptMapping =
      findCreateAttempt(entryMatcher)(entryData.proofGraph, targetProof)

    attemptMapping
  }

}
