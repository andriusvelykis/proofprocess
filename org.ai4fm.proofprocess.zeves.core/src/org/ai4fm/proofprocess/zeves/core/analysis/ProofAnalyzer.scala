package org.ai4fm.proofprocess.zeves.core.analysis

import java.util.Scanner

import org.ai4fm.filehistory.FileVersion
import org.ai4fm.proofprocess.{Attempt, Loc, ProofEntry, ProofStore, Term}
import org.ai4fm.proofprocess.core.analysis.ProofAttemptMatcher._
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.project.core.{ProofHistoryManager, ProofManager}
import org.ai4fm.proofprocess.project.core.util.{ProofProcessUtil, ResourceUtil}
import org.ai4fm.proofprocess.zeves.core.internal.ZEvesPProcessCorePlugin._
import org.ai4fm.proofprocess.zeves.core.parse.{ProofEntryData, ProofEntryReader}
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.{CoreException, IPath, IProgressMonitor, IStatus, NullProgressMonitor, Path, Status}
import org.eclipse.emf.ecore.util.EcoreUtil

import net.sourceforge.czt.session.{Key, SectionInfo, Source}
import net.sourceforge.czt.zeves.snapshot.ISnapshotEntry


/** @author Andrius Velykis
  */
object ProofAnalyzer {

  @throws(classOf[CoreException])
  def analyze(sectInfo: SectionInfo, proofSnapshot: List[ISnapshotEntry], activeEntry: ISnapshotEntry)
             (implicit monitor: IProgressMonitor): IStatus = {

    val proofEntriesOpt = readProofEntries(sectInfo, proofSnapshot, activeEntry)

    proofEntriesOpt foreach {
      case (project, proofStore, proofEntryData) => {

        val (_, matchMapping) = analyzeEntries(proofStore, proofEntryData)

        // map snapshot entries to actually matched proof entries for logging
        val entryMatchMap = PProcessUtil.chainMaps(proofEntryData.entryMap, matchMapping)
        logActivity(project, entryMatchMap, activeEntry);

        // FIXME commit here or after all analysis? or somewhere else altogether?
        commit(proofStore)
      }
    }

    Status.OK_STATUS;
  }
  
  private def chainMaps[A, B, C](m1: Map[A, B], m2: Map[B, C]): A => Option[C] = {
    val chained = (m1 andThen m2.lift).lift
    
    // unpack the nested option
    chained andThen (_ getOrElse None)
  }
  
  @throws(classOf[CoreException])
  private def readProofEntries(sectInfo: SectionInfo, proofSnapshot: List[ISnapshotEntry],
                               activeEntry: ISnapshotEntry)
                               (implicit monitor: IProgressMonitor): Option[(IProject, ProofStore, ProofEntryData)] = {

    val filePath = Path.fromOSString(activeEntry.getFilePath)
    val project = Option(ResourceUtil.findProject(filePath))
    
    project match {
      
      case None => {
        // cannot locate the project, therefore cannot access proof process model
        error(msg = Some("Unable to locate project for resource " + filePath))
        None
      }
      
      case Some(project) => {
        
        // load/resolve the proof project
        val proofProject = ProofManager.proofProject(project, monitor)

        // TODO make path relative for file history
        val fileVersion = syncFileVersion(sectInfo, project, activeEntry, filePath)

        def textLoc(snapshotEntry: ISnapshotEntry): Loc = {
          val pos = snapshotEntry.getPosition
          ProofProcessUtil.createTextLoc(fileVersion, pos.getOffset, pos.getLength);
        }
        
        val proofInfoOpt = proofEntries(sectInfo, proofProject, proofSnapshot, textLoc)

        proofInfoOpt.map((project, proofProject, _))
      }
    }
    
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
  private def syncFileVersion(sectInfo: SectionInfo, project: IProject, activeEntry: ISnapshotEntry,
                              filePath: IPath)(implicit monitor: IProgressMonitor): FileVersion = {

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

    ProofHistoryManager.syncFileVersion(project, filePath, text, Some(posEnd), monitor)
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
  
  @throws(classOf[CoreException])
  private def logActivity(project: IProject,
                          proofEntries: ISnapshotEntry => Option[ProofEntry],
                          activeEntry: ISnapshotEntry)
                         (implicit monitor: IProgressMonitor) {

    val proofLog = ProofManager.proofLog(project, monitor)
    ProofActivityLogger.logProof(proofLog, proofEntries, List(activeEntry))
  }

  private def commit(proofStore: ProofStore) {
    ProofManager.commitTransaction(proofStore, new NullProgressMonitor)
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
