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
                             proof: ProofData) = (proof, monitor) match {

    // try to resolve proof process model and project
    case ProofResources(project, proofStore) => {

      // store file history and create a function to resolve command locations
      val textLocFun = syncFileVersion(project, commandStarts, proof, monitor)
      
      // parse the proof and try to extract proof entries and structure
      parsePPEntries(proofStore, proof.proofState, textLocFun) match {
        
        case Some((parsedProof, parseEntries)) => {
          
          // analyse the parsed proof and add to proof store if needed
          val (_, matchMapping) = analyzeProofAttempt(proofStore, parsedProof)
  
          // map snapshot entries to actually matched proof entries for logging
          val entryMatchMap = PProcessUtil.chainMaps(parseEntries, matchMapping)
          logActivity(project, entryMatchMap, changedCommands, proof.proofState.map(_.state), monitor)
  
          // FIXME commit here or after all analysis? or somewhere else altogether?
          commit(proofStore)
        }

        case None => // invalid proof - ignore PP analysis
      }
      
    }

    case _ => {
      // cannot locate proof project and proof process model
      error(msg = Some("Unable to locate project for resource " + proof.textData.name.node))
    }
  }


  /**
   * Extractor object to locate the project and proof store associated with the proof.
   */
  private object ProofResources {

    def unapply(proofData0: (ProofData, IProgressMonitor)): Option[(IProject, ProofStore)] = {
      val (proofData, monitor) = proofData0

      findProjectResource(proofData.textData.name) match {

        case Some(project) => Some(project, ProofManager.proofProject(project, monitor))

        case None => None
      }
    }
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


  /**
   * Synchronises the current proof state file with file history.
   * 
   * Returns a function to compute `Loc` locations for commands within this file version.
   */
  private def syncFileVersion(project: IProject,
                              commandStarts: Map[Command, Int],
                              proof: ProofData,
                              monitor: IProgressMonitor): State => Loc = {

    val proofTextData = proof.textData
    val pathStr = proofTextData.name.node

    // sync file history version that corresponds to the given proof
    // TODO make path relative for file history
    val fileVersion = ProofHistoryManager.syncFileVersion(
      project, pathStr, Some(proofTextData.documentText), Some(proofTextData.syncPoint), monitor)

    // location resolution function
    def textLoc(cmdState: State): Loc = {
      val cmd = cmdState.command
      // TODO ignore position at all instead of 0?
      val offset = commandStarts.getOrElse(cmd, 0)
      ProofProcessUtil.createTextLoc(fileVersion, offset, cmd.range.stop - cmd.range.start);
    }
    
    // return the location function
    textLoc
  }


  /**
   * Parses proof entries and structure from the captured raw proof state.
   */
  private def parsePPEntries(proofStore: ProofStore,
                             proofState: List[StepResults],
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
