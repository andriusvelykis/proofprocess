package org.ai4fm.proofprocess.isabelle.core.analyse;

import isabelle.Command;
import isabelle.Document;
import isabelle.Command.State;
import isabelle.Text.Range;
import isabelle.XML.Tree;
import isabelle.scala.DocumentRef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ai4fm.filehistory.FileVersion;
import org.ai4fm.proofprocess.Attempt;
import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.Loc;
import org.ai4fm.proofprocess.Proof;
import org.ai4fm.proofprocess.ProofElem;
import org.ai4fm.proofprocess.ProofEntry;
import org.ai4fm.proofprocess.ProofInfo;
import org.ai4fm.proofprocess.ProofProcessFactory;
import org.ai4fm.proofprocess.ProofSeq;
import org.ai4fm.proofprocess.ProofStep;
import org.ai4fm.proofprocess.Property;
import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.Trace;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory;
import org.ai4fm.proofprocess.isabelle.IsabelleTrace;
import org.ai4fm.proofprocess.isabelle.core.IsabelleProofPlugin;
import org.ai4fm.proofprocess.isabelle.core.parse.SnapshotReader;
import org.ai4fm.proofprocess.isabelle.core.parse.TermParser;
import org.ai4fm.proofprocess.log.ProofLog;
import org.ai4fm.proofprocess.project.Project;
import org.ai4fm.proofprocess.project.core.ProofHistoryManager;
import org.ai4fm.proofprocess.project.core.ProofManager;
import org.ai4fm.proofprocess.project.core.ProofMatcher;
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

import scala.collection.JavaConversions;

/**
 * 
 * @author Andrius Velykis
 */
public class ProofAnalyzer {

	public IStatus analyze(Set<Command> changedCommands, Document.State docState, 
			IProgressMonitor monitor) throws CoreException {
	
		SnapshotReader reader = new SnapshotReader(changedCommands, docState);
		List<List<State>> proofStates = reader.readProofStates();
		if (proofStates.isEmpty()) {
			// nothing found to analyse
			return Status.OK_STATUS;
		}
		
		for (List<State> proofState : proofStates) {
			Set<Command> proofCommands = filterProofCommands(proofState, changedCommands);
			
			// TODO what if analyzer returns CANCEL_STATUS?
			analyze(proofState, proofCommands, reader, monitor);
		}
		
		return Status.OK_STATUS;
	}
	
	private Set<Command> filterProofCommands(List<State> proofState, Set<Command> filter) {
		
		Set<Command> proofCmds = new HashSet<Command>();
		for (State cmdState : proofState) {
			proofCmds.add(cmdState.command());
		}
		
		// keep just the commands in the filter
		proofCmds.retainAll(filter);
		
		return proofCmds;
	}
	
	private IStatus analyze(List<State> proofState, Set<Command> changedCommands,
			SnapshotReader reader, IProgressMonitor monitor) throws CoreException {

		Assert.isLegal(!proofState.isEmpty());
		
		State lastProofStep = proofState.get(proofState.size() - 1);
		Command lastCommand = lastProofStep.command();
		
		Integer commandStart = reader.getCommandStart(lastCommand);
		String documentText = reader.getDocumentText(lastCommand);
		if (commandStart == null || documentText == null) {
			// invalid command, no longer in the snapshot, so just ignore the proof
			return Status.OK_STATUS;
		}
		
		DocumentRef docRef = new DocumentRef(lastCommand.node_name());
		
		String filePathStr = docRef.getNode();
		IPath filePath = Path.fromOSString(filePathStr);
		
		IProject project = ResourceUtil.findProject(filePath);
		if (project == null) {
			// cannot locate the project, therefore cannot access proof process model
			return IsabelleProofPlugin.error("Unable to locate project for resource " + filePath, null);
		}
		
		Project proofProject = ProofManager.getProofProject(project, monitor);
		
		int commandEnd = commandStart.intValue() + lastCommand.range().stop();
		FileVersion fileVersion = ProofHistoryManager.syncFileVersion(
				project, filePath, documentText, commandEnd, monitor);
		
		analyzeEntry(proofProject, proofState, fileVersion);
		
		// FIXME retrieve proof entries from the analysis
		Map<State, ProofEntry> proofEntries = new HashMap<State, ProofEntry>();
		logActivity(project, proofState, changedCommands, proofEntries, monitor);

		return Status.OK_STATUS;
	}
	
	private void analyzeEntry(Project proofProject, List<State> proofState, FileVersion fileVersion) {
		// TODO
		
		// Convert to Proof Process steps - they will be matched to the existing
		// proof process records afterwards
		List<ProofEntry> ppState = createProofSteps(proofProject, proofState, fileVersion);
		
		// Assume that the first step in any proof is the "declaration" command, e.g. "lemma ..."
		ProofEntry declStep = ppState.get(0);
		List<Term> declGoals = declStep.getProofStep().getOutGoals();
		
		if (declGoals.isEmpty()) {
			// no declaration goals - no proof
			return;
		}
		
		// find the proof for this
		ProofMatcher proofMatcher = new ProofMatcher();
		// TODO extract proof label
		Proof proof = proofMatcher.findCreateProof(proofProject, null, declGoals);

		// TODO export State-Entry matchings for Activities
		proofMatcher.findCreateProofTree(proofProject, proof, ppState);
		
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
		
		// TODO copy in goals from the previous
		List<Term> inGoals = step.getInGoals();
		
		List<Term> outGoals = step.getOutGoals();
		outGoals.addAll(parseGoals(commandState));
		
		// create tactic application attempt
		ProofEntry entry = ProofProcessFactory.eINSTANCE.createProofEntry();
		entry.setInfo(info);
		entry.setProofStep(step);
		
		return entry;
	}
	
	private List<Term> parseGoals(State commandState) {
		
		List<Term> parsedTerms = new ArrayList<Term>();
		
		for (Tree result : JavaConversions.asJavaIterable(commandState.results().values())) {
			List<Term> parsed = TermParser.parseGoals(result);
			parsedTerms.addAll(parsed);
		}
		
		return parsedTerms;
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
	
	private Attempt createAttempt(List<? extends ProofElem> proofState) {
		Attempt attempt = ProofProcessFactory.eINSTANCE.createAttempt();
		
		if (!proofState.isEmpty()) {
			
			ProofElem rootElem;
			if (proofState.size() > 1) {
				// wrap the proof state into a sequential group
				ProofSeq seq = ProofProcessFactory.eINSTANCE.createProofSeq();
				seq.getEntries().addAll(proofState);
				
				ProofInfo info = ProofProcessFactory.eINSTANCE.createProofInfo();
				seq.setInfo(info);
				
				rootElem = seq;
			} else {
				rootElem = proofState.get(0);
			}
			
			attempt.setProof(rootElem);
		}
		
		return attempt;
	}
	
	
	private void logActivity(IProject project, List<State> proofState,
			Set<Command> changedCommands, Map<State, ProofEntry> proofEntries,
			IProgressMonitor monitor) throws CoreException {

		ProofLog proofLog = ProofManager.getProofLog(project, monitor);
		ProofActivityLogger activityLogger = new ProofActivityLogger(proofLog);

		activityLogger.logProof(proofState, changedCommands, proofEntries);
	}
	
}
