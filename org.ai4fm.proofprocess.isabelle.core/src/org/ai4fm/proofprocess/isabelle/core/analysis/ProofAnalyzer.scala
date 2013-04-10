package org.ai4fm.proofprocess.isabelle.core.analysis

import org.ai4fm.proofprocess.{Attempt, Loc, ProofEntry, ProofStore, Term}
import org.ai4fm.proofprocess.core.analysis.ProofAttemptMatcher.{findCreateAttempt, findCreateProof}
import org.ai4fm.proofprocess.core.analysis.ProofEntryMatcher
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.isabelle.core.IsabellePProcessCorePlugin.error
import org.ai4fm.proofprocess.isabelle.core.parse.{ParsedProof, ProofEntryReader}
import org.ai4fm.proofprocess.isabelle.core.parse.ProofEntryReader.ParseEntries
import org.ai4fm.proofprocess.isabelle.core.parse.SnapshotReader
import org.ai4fm.proofprocess.isabelle.core.parse.SnapshotReader.{ProofData, StepResults}
import org.ai4fm.proofprocess.project.core.{ProofHistoryManager, ProofManager}
import org.ai4fm.proofprocess.project.core.util.{ProofProcessUtil, ResourceUtil}
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.{CoreException, IProgressMonitor, IStatus, NullProgressMonitor, Path, Status}
import org.eclipse.emf.ecore.util.EcoreUtil

import isabelle.Command
import isabelle.Command.State
import isabelle.Document
import isabelle.eclipse.core.resource.URIThyLoad.toURINodeName


/**
  * @author Andrius Velykis 
  */
object ProofAnalyzer {

  @throws[CoreException]
  def analyze(docState: Document.State, changedCommands: Set[Command], monitor: IProgressMonitor): IStatus = {

    // TODO sort proofs?
    val (proofs, commandStarts) = SnapshotReader.readProofs(docState, changedCommands)

    proofs foreach processProof(changedCommands, commandStarts, monitor)

    Status.OK_STATUS;
  }

  /**
   * Processes the proof attempt by parsing proof entries and performing some analysis to
   * infer the ProofProcess Attempt and its structure.
   * 
   * If successful, the attempt and the activities are added to the ProofProcess data.
   */
  @throws[CoreException]
  private def processProof(changedCommands: Set[Command],
                           commandStarts: Map[Command, Int],
                           monitor: IProgressMonitor)(
                             proof: ProofData) =
    readProofEntries(proof, commandStarts, monitor) match {

      case Some((project, proofStore, parsedProof, proofEntries)) => {

        val (_, matchMapping) = analyzeProofAttempt(proofStore, parsedProof)

        // map snapshot entries to actually matched proof entries for logging
        val entryMatchMap = PProcessUtil.chainMaps(proofEntries, matchMapping)
        logActivity(project, entryMatchMap, changedCommands, proof.proofState.map(_.state), monitor);

        // FIXME commit here or after all analysis? or somewhere else altogether?
        commit(proofStore)
      }

      case None => // invalid proof - ignore PP analysis
    }
  
  
  @throws[CoreException]
  private def readProofEntries(proofData: ProofData, commandStarts: Map[Command, Int],
      monitor: IProgressMonitor): Option[(IProject, ProofStore, ParsedProof, ParseEntries)] = {

    import isabelle.eclipse.core.resource.URIThyLoad._
    
    val proofTextData = proofData.textData
    
    withProjectResource(proofTextData.name) { project =>

      val proofProject = ProofManager.proofProject(project, monitor)
      val pathStr = proofTextData.name.node

      // TODO make path relative for file history
      val fileVersion = ProofHistoryManager.syncFileVersion(
        project, pathStr, Some(proofTextData.documentText), Some(proofTextData.syncPoint), monitor)

      def textLoc(cmdState: State): Loc = {
        val cmd = cmdState.command
        // TODO ignore position at all instead of 0?
        val offset = commandStarts.getOrElse(cmd, 0)
        ProofProcessUtil.createTextLoc(fileVersion, offset, cmd.range.stop - cmd.range.start);
      }
      
      proofEntries(proofProject, proofData.proofState, textLoc) match {
        
        case Some((parsedProof, parseEntries)) =>
          Some((project, proofProject, parsedProof, parseEntries))

        case None => None
      }
    }
  }

  private def withProjectResource[A](document: Document.Node.Name)(
                                       f: IProject => Option[A]): Option[A] =
    findProjectResource(document) match {

      case Some(project) => f(project)

      case None =>
        // cannot locate the project, therefore cannot access proof process model
        error(msg = Some("Unable to locate project for resource " + document.node))
        None

    }

  private def findProjectResource(document: Document.Node.Name): Option[IProject] = {

    val pathUri = document.uri
    
    if (pathUri.isAbsolute) {
      // URI encoding
      Option(ResourceUtil.findProject(pathUri))
    } else {
      // Path encoding
      val pathStr = document.node
      val filePath = Path.fromOSString(pathStr)
      Option(ResourceUtil.findProject(filePath))
    }
  }


  private def proofEntries(proofStore: ProofStore, proofState: List[StepResults],
      cmdLoc: State => Loc) = {

    val entryReader = new ProofEntryReader {

      lazy val stepIntent = PProcessUtil.getIntent(proofStore, "Tactic Application")

      // TODO review
      override def cloneTerm(term: Term): Term = EcoreUtil.copy(term)
      
      // TODO ignore some properties, e.g. display?
      override def matchTerms(term1: Term, term2: Term): Boolean = EcoreUtil.equals(term1, term2);

      override def textLoc(cmdState: State) = cmdLoc(cmdState)
    }

    entryReader.readEntries(proofState)
  }
  
  @throws[CoreException]
  private def logActivity(project: IProject, proofEntries: State => Option[ProofEntry],
    changedCommands: Set[Command], proofState: List[State], monitor: IProgressMonitor) {

    val proofLog = ProofManager.proofLog(project, monitor)
    ProofActivityLogger.logProof(proofLog, proofEntries, changedCommands, proofState)
  }

  private def commit(proofStore: ProofStore) {
    ProofManager.commitTransaction(proofStore, new NullProgressMonitor)
  }

  private def analyzeProofAttempt(proofStore: ProofStore, parsedProof: ParsedProof)
      : (Attempt, Map[ProofEntry, ProofEntry]) = {
    
    val entryMatcher = ProofEntryMatcher()

    val targetProof =
      findCreateProof(entryMatcher)(proofStore, parsedProof.label, parsedProof.proofGoals)

    val attemptMapping =
      findCreateAttempt(entryMatcher)(parsedProof.proofGraph, targetProof)

    attemptMapping
  }

}
