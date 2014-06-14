package org.ai4fm.proofprocess.isabelle.core.analysis

import org.ai4fm.filehistory.FileHistoryProject
import org.ai4fm.proofprocess.Attempt
import org.ai4fm.proofprocess.Loc
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ProofStore
import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.core.analysis.ProofAttemptMatcher.findCreateAttempt
import org.ai4fm.proofprocess.core.analysis.ProofAttemptMatcher.findCreateProof
import org.ai4fm.proofprocess.core.analysis.ProofEntryMatcher
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.isabelle.core.IsabellePProcessCorePlugin.error
import org.ai4fm.proofprocess.isabelle.core.parse.CommandResults
import org.ai4fm.proofprocess.isabelle.core.parse.ParsedProof
import org.ai4fm.proofprocess.isabelle.core.parse.ProofEntryReader
import org.ai4fm.proofprocess.isabelle.core.parse.SnapshotReader
import org.ai4fm.proofprocess.isabelle.core.parse.SnapshotReader.ProofData
import org.ai4fm.proofprocess.project.core.PProcessDataManager
import org.ai4fm.proofprocess.project.core.PProcessDataManager.PProcessData
import org.ai4fm.proofprocess.project.core.ProofHistoryManager
import org.ai4fm.proofprocess.project.core.util.ProofProcessUtil
import org.ai4fm.proofprocess.project.core.util.ResourceUtil
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Path
import org.eclipse.core.runtime.Status
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
  def analyze(docState: Document.State,
      changedCommands: Set[Command],
      ppData: IProject => PProcessData,
      monitor: IProgressMonitor): IStatus = {

    // TODO sort proofs?
    val (proofs, commandStarts) = SnapshotReader.readProofs(docState, changedCommands)

    proofs foreach processProof(changedCommands, ppData, commandStarts, monitor)

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
                           ppData: IProject => PProcessData,
                           commandStarts: Map[Command, Int],
                           monitor: IProgressMonitor)(proof: ProofData) =
    findProjectResource(proof.textData.name) match {

    // try to resolve file project
    case Some(project) => {

      val PProcessData(proofStore, proofLog, fileHistory) = ppData(project)

      // store file history and create a function to resolve command locations
      val textLocFun = syncFileVersion(project, fileHistory, commandStarts, proof, monitor)
      
      // parse the proof and try to extract proof entries and structure
      parsePPEntries(proofStore, proof.proofState, textLocFun) match {
        
        case Some((parsedProof, parseEntries)) => {
          
          // analyse the parsed proof and add to proof store if needed
          val (_, matchMapping) = analyzeProofAttempt(proofStore, parsedProof)
  
          // map snapshot entries to actually matched proof entries for logging
          val entryMatchMap = PProcessUtil.chainMaps(parseEntries, matchMapping)
          
          val proofState = proof.proofState.map(_.state)
          ProofActivityLogger.logProof(proofLog, entryMatchMap, changedCommands, proofState)

          // FIXME commit here or after all analysis? or somewhere else altogether?
          PProcessDataManager.commitTransaction(proofStore, monitor)
        }

        case None => // invalid proof - ignore PP analysis
      }
      
    }

    case _ => {
      // cannot locate proof project
      error(msg = Some("Unable to locate project for resource " + proof.textData.name.node))
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
                              history: FileHistoryProject,
                              commandStarts: Map[Command, Int],
                              proof: ProofData,
                              monitor: IProgressMonitor): State => Loc = {

    val proofTextData = proof.textData
    val pathStr = proofTextData.name.node

    // sync file history version that corresponds to the given proof
    val historyMan = ProofHistoryManager.historyManager(project, history)
    val fileVersion = ProofHistoryManager.syncFileVersion(
      historyMan, history, project,
      pathStr, Some(proofTextData.documentText), Some(proofTextData.syncPoint), monitor)

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
                             proofState: List[CommandResults],
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
