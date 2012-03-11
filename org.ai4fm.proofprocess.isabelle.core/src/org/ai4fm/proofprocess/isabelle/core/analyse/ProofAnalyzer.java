package org.ai4fm.proofprocess.isabelle.core.analyse;

import isabelle.Command;
import isabelle.Command.State;
import isabelle.Text.Range;
import isabelle.scala.DocumentRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ai4fm.filehistory.FileVersion;
import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.Loc;
import org.ai4fm.proofprocess.ProofEntry;
import org.ai4fm.proofprocess.ProofInfo;
import org.ai4fm.proofprocess.ProofProcessFactory;
import org.ai4fm.proofprocess.ProofStep;
import org.ai4fm.proofprocess.Property;
import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.Trace;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory;
import org.ai4fm.proofprocess.isabelle.IsabelleTrace;
import org.ai4fm.proofprocess.isabelle.core.IsabelleProofPlugin;
import org.ai4fm.proofprocess.log.ProofLog;
import org.ai4fm.proofprocess.project.Project;
import org.ai4fm.proofprocess.project.core.ProofHistoryManager;
import org.ai4fm.proofprocess.project.core.ProofManager;
import org.ai4fm.proofprocess.project.core.util.ProofProcessUtil;
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
		
		// Convert to Proof Process steps - they will be matched to the existing
		// proof process records afterwards
		List<ProofEntry> ppState = createProofSteps(proofProject, proofState, fileVersion);
		
		// TODO save the proofProject
	}
	
	private List<ProofEntry> createProofSteps(Project proofProject, List<State> proofState,
			FileVersion fileVersion) {
		
		List<ProofEntry> entries = new ArrayList<ProofEntry>();
		
		ProofEntry previousEntry = null;
		for (State cmdState : proofState) {
			ProofEntry entry = createProofStep(proofProject, fileVersion, previousEntry, cmdState);
			entries.add(entry);
			
			previousEntry = entry;
		}
		
		return entries;
	}
	
	private ProofEntry createProofStep(Project project, FileVersion fileVersion, 
			ProofEntry previousEntry, State commandState) {
		
		ProofInfo info = ProofProcessFactory.eINSTANCE.createProofInfo();
		info.setNarrative("Tactic: " + commandState.command().name());
		
		Intent intent = ProofProcessUtil.findCreateIntent(project, "Tactic Application");
		info.setIntent(intent);
		
		// TODO set properties
		List<Property> inProps = info.getInProps();
		List<Property> outProps = info.getInProps();
		
		ProofStep step = ProofProcessFactory.eINSTANCE.createProofStep();
		step.setTrace(createProofStepTrace(commandState));
		step.setSource(createProofStepLoc(fileVersion, commandState));
		
		// TODO set in/out goals
		List<Term> inGoals = step.getInGoals();
		List<Term> outGoals = step.getOutGoals();
		
		// create tactic application attempt
		ProofEntry entry = ProofProcessFactory.eINSTANCE.createProofEntry();
		entry.setInfo(info);
		entry.setProofStep(step);
		
		return entry;
	}
	
	private Trace createProofStepTrace(State commandState) {
		
		IsabelleTrace trace = IsabelleProofProcessFactory.eINSTANCE.createIsabelleTrace();
		
		// TODO proper rendering of the command (maybe with Terms)
		// currently this is a quick hack
		String commandStr = commandState.command().source().trim();
		
		trace.setCommand(commandStr);
		
		// TODO add simplifier lemmas
		List<String> simpLemmas = trace.getSimpLemmas();

		return trace;
	}
	
	private Loc createProofStepLoc(FileVersion fileVersion, State commandState) {
		
		Range cmdRange = commandState.command().range();
		int length = cmdRange.stop() - cmdRange.start();
		return ProofProcessUtil.createTextLoc(fileVersion, cmdRange.start(), length);
	}
	
	
	private void logActivity(IProject project, List<State> proofState,
			Set<Command> changedCommands, Map<State, ProofEntry> proofEntries,
			IProgressMonitor monitor) throws CoreException {

		ProofLog proofLog = ProofManager.getProofLog(project, monitor);
		ProofActivityLogger activityLogger = new ProofActivityLogger(proofLog);

		activityLogger.logProof(proofState, changedCommands, proofEntries);
	}
	
}
