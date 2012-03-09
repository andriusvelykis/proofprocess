package org.ai4fm.proofprocess.isabelle.core.analyse;

import isabelle.Command;
import isabelle.Command.State;
import isabelle.scala.DocumentRef;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ai4fm.filehistory.FileVersion;
import org.ai4fm.proofprocess.ProofEntry;
import org.ai4fm.proofprocess.isabelle.core.IsabelleProofPlugin;
import org.ai4fm.proofprocess.log.ProofLog;
import org.ai4fm.proofprocess.project.Project;
import org.ai4fm.proofprocess.project.core.ProofHistoryManager;
import org.ai4fm.proofprocess.project.core.ProofManager;
import org.ai4fm.proofprocess.project.core.util.ResourceUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

/**
 * 
 * @author Andrius Velykis
 */
public class ProofAnalyzer {

	public IStatus analyze(List<State> proofState, Set<Command> changedCommands,
			String documentText, IProgressMonitor monitor) throws CoreException {

		Assert.isLegal(!proofState.isEmpty());
		Assert.isNotNull(documentText);
		
		State lastProofStep = proofState.get(proofState.size() - 1);
		Command lastCommand = lastProofStep.command();
		DocumentRef docRef = new DocumentRef(lastCommand.node_name());
		
		String filePathStr = docRef.getNode();
		IPath filePath = Path.fromOSString(filePathStr);
		
		IProject project = ResourceUtil.findProject(filePath);
		if (project == null) {
			// cannot locate the project, therefore cannot access proof process model
			return IsabelleProofPlugin.error("Unable to locate project for resource " + filePath, null);
		}
		
		Project proofProject = ProofManager.getProofProject(project, monitor);
		
		int commandEnd = lastCommand.range().stop();
		FileVersion fileVersion = ProofHistoryManager.syncFileVersion(
				project, filePath, documentText, commandEnd, monitor);
		
		analyseEntry(proofProject, proofState, fileVersion);
		
		// FIXME retrieve proof entries from the analysis
		Map<State, ProofEntry> proofEntries = null;
		logActivity(project, proofState, changedCommands, proofEntries, monitor);

		return Status.OK_STATUS;
	}
	
	private void analyseEntry(Project proofProject, List<State> proofState, FileVersion fileVersion) {
		// TODO
		
		// TODO save the proofProject
	}
	
	private void logActivity(IProject project, List<State> proofState,
			Set<Command> changedCommands, Map<State, ProofEntry> proofEntries,
			IProgressMonitor monitor) throws CoreException {

		ProofLog proofLog = ProofManager.getProofLog(project, monitor);
		ProofActivityLogger activityLogger = new ProofActivityLogger(proofLog);

		activityLogger.logProof(proofState, changedCommands, proofEntries);
	}
	
}
