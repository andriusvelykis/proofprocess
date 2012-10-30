package org.ai4fm.proofprocess.isabelle.core.analysis

import isabelle.Command
import isabelle.Command.State
import isabelle.Document
import isabelle.Text
import java.io.IOException
import org.ai4fm.filehistory.FileVersion
import org.ai4fm.proofprocess.Loc
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ProofStore
import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.core.analysis.ProofMatcher
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.isabelle.core.IsabellePProcessCorePlugin._
import org.ai4fm.proofprocess.isabelle.core.parse.ProofEntryData
import org.ai4fm.proofprocess.isabelle.core.parse.ProofEntryReader
import org.ai4fm.proofprocess.isabelle.core.parse.SnapshotReader
import org.ai4fm.proofprocess.isabelle.core.parse.SnapshotReader.ProofData
import org.ai4fm.proofprocess.project.core.ProofHistoryManager
import org.ai4fm.proofprocess.project.core.ProofManager
import org.ai4fm.proofprocess.project.core.util.ProofProcessUtil
import org.ai4fm.proofprocess.project.core.util.ResourceUtil
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Path
import org.eclipse.core.runtime.Status
import org.eclipse.emf.ecore.util.EcoreUtil
import scala.collection.JavaConversions._


/**
  * @author Andrius Velykis 
  */
object ProofAnalyzer {

  @throws(classOf[CoreException])
  def analyze(docState: Document.State, changedCommands: Set[Command], monitor: IProgressMonitor): IStatus = {

    // TODO sort proofs?
    val (proofs, commandStarts) = SnapshotReader.readProofs(docState, changedCommands)

    proofs foreach { proofData =>
      
      val proofEntriesOpt = readProofEntries(proofData, commandStarts, monitor)

      proofEntriesOpt foreach {
        case (project, proofStore, proofEntryData) => {

          analyzeEntries(proofStore, proofEntryData)
          
          // FIXME retrieve proof entries from the analysis
          logActivity(project, Map(), changedCommands, proofData.proofState.map(_._1), monitor);

          // FIXME commit here or after all analysis? or somewhere else altogether?
          commit(proofStore)
        }
      }
    }
    Status.OK_STATUS;
  }
  
  @throws(classOf[CoreException])
  private def readProofEntries(proofData: ProofData, commandStarts: Map[Command, Int],
      monitor: IProgressMonitor): Option[(IProject, ProofStore, ProofEntryData)] = {

    import isabelle.eclipse.core.resource.URIThyLoad._
    
    val proofTextData = proofData.textData
    
    val pathStr = proofTextData.name.node
    val pathUri = proofTextData.name.uri
    
    val project = if (pathUri.isAbsolute) {
      // URI encoding
      Option(ResourceUtil.findProject(pathUri))
    } else {
      // Path encoding
      val filePath = Path.fromOSString(pathStr)
      Option(ResourceUtil.findProject(filePath))
    }

    if (project.isEmpty) {
      // cannot locate the project, therefore cannot access proof process model
      error(msg = Some("Unable to locate project for resource " + pathStr))
    }

    project flatMap { project =>
      {

        val proofProject = ProofManager.getProofProject(project, monitor)

        // TODO make path relative for file history
        val fileVersion = ProofHistoryManager.syncFileVersion(
          project, pathStr, Some(proofTextData.documentText), Some(proofTextData.syncPoint), monitor)

        def textLoc(cmdState: State): Loc = {
          val cmd = cmdState.command
          // TODO ignore position at all instead of 0?
          val offset = commandStarts.getOrElse(cmd, 0)
          ProofProcessUtil.createTextLoc(fileVersion, offset, cmd.range.stop - cmd.range.start);
        }
        
        val proofInfoOpt = proofEntries(proofProject, proofData.proofState, textLoc)

        proofInfoOpt.map((project, proofProject, _))
      }
    }
  }

  private def proofEntries(proofStore: ProofStore, proofState: List[(State, List[Term])],
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
  
  @throws(classOf[CoreException])
  private def logActivity(project: IProject, proofEntries: Map[State, ProofEntry],
    changedCommands: Set[Command], proofState: List[State], monitor: IProgressMonitor) {

    val proofLog = ProofManager.getProofLog(project, monitor)
    ProofActivityLogger.logProof(proofLog, proofEntries, changedCommands, proofState)
  }

  private def commit(proofStore: ProofStore) {
    try {
      proofStore.eResource().save(ProofManager.SAVE_OPTIONS);
    } catch {
      case e: IOException => log(error(Some(e)))
    }
  }

  private def analyzeEntries(proofStore: ProofStore, entryData: ProofEntryData) {
    // FIXME

    // find the proof for this
    val proofMatcher = new ProofMatcher

    val proof = proofMatcher.findCreateProof(proofStore, entryData.label.orNull, entryData.goals);

    // TODO export State-Entry matchings for Activities
    proofMatcher.findCreateProofTree(proofStore, proof, entryData.rootEntry);
  }

}
