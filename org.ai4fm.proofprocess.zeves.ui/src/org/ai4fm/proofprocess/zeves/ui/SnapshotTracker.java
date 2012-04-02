package org.ai4fm.proofprocess.zeves.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.sourceforge.czt.eclipse.editors.parser.ZCompiler;
import net.sourceforge.czt.eclipse.outline.TermLabelVisitorFactory;
import net.sourceforge.czt.eclipse.zeves.core.ISnapshotChangedListener;
import net.sourceforge.czt.eclipse.zeves.core.SnapshotChangedEvent;
import net.sourceforge.czt.eclipse.zeves.core.SnapshotChangedEvent.SnapshotChangeType;
import net.sourceforge.czt.eclipse.zeves.core.ZEvesSnapshot;
import net.sourceforge.czt.eclipse.zeves.core.ZEvesSnapshot.ISnapshotEntry;
import net.sourceforge.czt.eclipse.zeves.core.ZEvesSnapshot.ResultType;
import net.sourceforge.czt.session.Key;
import net.sourceforge.czt.session.SectionInfo;
import net.sourceforge.czt.session.Source;
import net.sourceforge.czt.util.Visitor;
import net.sourceforge.czt.zeves.ast.ProofCommand;
import net.sourceforge.czt.zeves.response.ZEvesOutput;
import net.sourceforge.czt.zeves.response.ZEvesProofTrace;
import net.sourceforge.czt.zeves.response.ZEvesProofTrace.TraceType;
import net.sourceforge.czt.zeves.response.form.ZEvesName;

import org.ai4fm.filehistory.FileVersion;
import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.Proof;
import org.ai4fm.proofprocess.ProofElem;
import org.ai4fm.proofprocess.ProofEntry;
import org.ai4fm.proofprocess.ProofFeature;
import org.ai4fm.proofprocess.ProofInfo;
import org.ai4fm.proofprocess.ProofParallel;
import org.ai4fm.proofprocess.ProofProcessFactory;
import org.ai4fm.proofprocess.ProofSeq;
import org.ai4fm.proofprocess.ProofStep;
import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.Trace;
import org.ai4fm.proofprocess.log.Activity;
import org.ai4fm.proofprocess.log.ProofActivity;
import org.ai4fm.proofprocess.log.ProofLog;
import org.ai4fm.proofprocess.log.ProofProcessLogFactory;
import org.ai4fm.proofprocess.log.ProofProcessLogPackage;
import org.ai4fm.proofprocess.project.Project;
import org.ai4fm.proofprocess.project.core.ProofHistoryManager;
import org.ai4fm.proofprocess.project.core.ProofManager;
import org.ai4fm.proofprocess.project.core.ProofMatcher;
import org.ai4fm.proofprocess.project.core.ProofMatcher.ProofElemMatch;
import org.ai4fm.proofprocess.project.core.util.EmfUtil;
import org.ai4fm.proofprocess.project.core.util.ProofProcessUtil;
import org.ai4fm.proofprocess.project.core.util.ResourceUtil;
import org.ai4fm.proofprocess.zeves.ZEvesProofProcessFactory;
import org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage;
import org.ai4fm.proofprocess.zeves.ZEvesTrace;
import org.ai4fm.proofprocess.zeves.ui.parse.TermParser;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper;
import org.eclipse.jface.text.Position;

public class SnapshotTracker {

	private final ZEvesSnapshot snapshot;
	private final ISnapshotChangedListener snapshotListener;
	
	private final Job analysisJob;
	
	/**
	 * A concurrent queue is used for pending events, because the queue is
	 * likely to be updated from multiple threads.
	 */
	private final Queue<SnapshotAnalysisEvent> pendingEvents = 
			new ConcurrentLinkedQueue<SnapshotAnalysisEvent>();
	
	public SnapshotTracker(final ZEvesSnapshot snapshot) {
		super();
		this.snapshot = snapshot;
		
		this.snapshotListener = new ISnapshotChangedListener() {
			
			@Override
			public void snapshotChanged(SnapshotChangedEvent event) {
				addPendingAnalysis(snapshot.getSectionInfo(), event);
			}
		};
		
		analysisJob = new AnalysisJob();
		// add a listener that reschedules the job after completion if there are pending events
		analysisJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (!pendingEvents.isEmpty()) {
					// there are pending events, reschedule the job to consume them
					analysisJob.schedule();
				}
			}
		});
		
		snapshot.addSnapshotChangedListener(snapshotListener);
		
		// add a pending analysis for entries possibly already in the snapshot
		List<? extends ISnapshotEntry> allEntries = snapshot.getEntries();
		if (!allEntries.isEmpty()) {
			addPendingAnalysis(snapshot.getSectionInfo(),
					new SnapshotChangedEvent(snapshot, SnapshotChangeType.ADD, allEntries));
		}
	}
	
	public void dispose() {
		this.snapshot.removeSnapshotChangedListener(snapshotListener);
	}
	
	private void addPendingAnalysis(SectionInfo sectInfo, SnapshotChangedEvent snapshotChange) {
		
		Assert.isNotNull(snapshotChange);
		
		if (snapshotChange.getType() == SnapshotChangeType.ADD) {
			
			for (ISnapshotEntry entry : snapshotChange.getEntries()) {
				pendingEvents.add(new SnapshotAnalysisEvent(
						snapshotChange, sectInfo, entry, getProofEntries(entry)));
			}
			
		} else if (snapshotChange.getType() == SnapshotChangeType.REMOVE) {
			
			// for remove, use the last proof left, if any
			pendingEvents.add(new SnapshotAnalysisEvent(snapshotChange, null, null, getProofEntries(null)));
		}
		
		// wake up the analysis job
		analysisJob.schedule();
	}
	
	private IStatus analyse(SnapshotAnalysisEvent event, IProgressMonitor monitor) throws CoreException {

		long start = System.currentTimeMillis();
		
		if (event.event.getType() == SnapshotChangeType.ADD) {
			
			IPath filePath = Path.fromOSString(event.entry.getFilePath());
			
			IProject project = ResourceUtil.findProject(filePath);
			if (project == null) {
				// cannot locate the project, therefore cannot access proof process model
				return ZEvesProofUIPlugin.error("Unable to locate project for resource " + filePath, null);
			}
			
			Project proofProject = ProofManager.getProofProject(project, monitor);
			ProofLog proofLog = ProofManager.getProofLog(project, monitor);
			FileVersion fileVersion = syncFileVersion(project, event.entry, event.sectInfo, filePath, monitor);
			analyseEntry(proofProject, proofLog, event.entry, event.entryProof, fileVersion, event.sectInfo);
			
			if (pendingEvents.isEmpty() || pendingEvents.peek().event.getType() != SnapshotChangeType.ADD) {
				// no more pending events (or the next one is not ADD), save the file
				// TODO better saving algorithm, but not one which stops all 
				try {
					proofProject.eResource().save(ProofManager.SAVE_OPTIONS);
					proofLog.eResource().save(ProofManager.SAVE_OPTIONS);
				} catch (IOException e) {
					ZEvesProofUIPlugin.log(e);
				}
			}
		}
		
		System.out.println("Analysing event: " + event.event.getType() + " -- " + (System.currentTimeMillis() - start));
		
		return Status.OK_STATUS;
	}
	
	private FileVersion syncFileVersion(IProject project, ISnapshotEntry entry,
			SectionInfo sectInfo, IPath filePath, IProgressMonitor monitor) throws CoreException {
		
		String text = null;
		// check the source for this section - if it is not cached, use the
		// DEFAULT name,
		// which means that it is the source from the editor. Otherwise, use
		// saved file one.
		Key<Source> sectionSourceKey = new Key<Source>(entry.getSectionName(), Source.class);
		if (!sectInfo.isCached(sectionSourceKey)) {
			// use editor source
			sectionSourceKey = new Key<Source>(ZCompiler.DEFAULT_SECTION_NAME, Source.class);
			try {
				Source source = sectInfo.get(sectionSourceKey);
				text = IOUtils.toString(source.getReader());
			} catch (Exception e) {
				throw new CoreException(ZEvesProofUIPlugin.error(e));
			}
		}
		
		int posEnd = entry.getPosition().offset + entry.getPosition().length;

		return ProofHistoryManager.syncFileVersion(project, filePath, text, posEnd, monitor);
	}
	
	private void analyseEntry(Project proofProject, ProofLog proofLog, ISnapshotEntry entry,
			List<ISnapshotEntry> entryProof, FileVersion fileVersion, SectionInfo sectInfo) {
		
		Activity activity;
		// TODO is the proof check enough here?
		if (!entryProof.isEmpty()) {
			
			// TODO decide how to handle error elements in proofs
			List<ISnapshotEntry> nonErrorProof = new ArrayList<ZEvesSnapshot.ISnapshotEntry>();
			for (ISnapshotEntry pEntry : entryProof) {
				if (pEntry.getType() != ResultType.ERROR) {
					nonErrorProof.add(pEntry);
				}
			}
			
			if (nonErrorProof.isEmpty()) {
				// whole proof is just errors
				return;
			}
			
			ProofEntry attempt = analyseProofEntry(proofProject, nonErrorProof, fileVersion, sectInfo);
			
			ProofActivity proofActivity = ProofProcessLogFactory.eINSTANCE.createProofActivity();
			if (attempt != null) {
				// TODO no-attempt (e.g. goal step)
				proofActivity.setProofRef(attempt);
			}
			
			activity = proofActivity;
			
		} else if (entry.getData().getTerm() != null) {
			activity = ProofProcessLogFactory.eINSTANCE.createActivity();
		} else {
			// no term - ignore for now
			return;
		}
		
		activity.setTimestamp(new Date(System.currentTimeMillis()));
		net.sourceforge.czt.base.ast.Term source = entry.getData().getTerm();
		activity.setDescription("Added: " + (source != null ? source.getClass().getSimpleName() : "<goal?>"));
		
		EmfUtil.addValue(proofLog, ProofProcessLogPackage.PROOF_LOG__ACTIVITIES, activity);
	}
	
	private ProofEntry analyseProofEntry(Project proofProject, List<ISnapshotEntry> proofState, 
			FileVersion fileVersion, SectionInfo sectInfo) {
		
		Assert.isLegal(!proofState.isEmpty());
		
		ISnapshotEntry goalEntry = proofState.get(0);
		Assert.isLegal(goalEntry.getData().getProofStepIndex() == ZEvesSnapshot.GOAL_STEP_INDEX, 
				"The first element in the proof must be the goal.");
		
		String goalName = goalEntry.getData().getGoalName();
		List<Term> initialGoals = parseGoals(sectInfo, goalEntry);
		
		ProofMatcher proofMatcher = new ZEvesProofMatcher();
		Proof proof = proofMatcher.findCreateProof(proofProject, goalName, initialGoals);

		// Continue analysing the proof commands.
		// Note that the first entry is excluded, as it is the "proof declaration command"
		List<ISnapshotEntry> remainingState = proofState.subList(1, proofState.size());
		if (remainingState.isEmpty()) {
			// no proof steps afterwards
			return null;
		}
		
		List<ProofEntry> proofSteps = createProofSteps(
				proofMatcher, proofProject, sectInfo, proof, remainingState, fileVersion);
		
		ProofElemMatch match = proofMatcher.findCreateProofTree(proofProject, proof, proofSteps);
		return match.getEntry();
	}
	
	private List<ProofEntry> createProofSteps(ProofMatcher proofMatcher, Project proofProject,
			SectionInfo sectInfo, Proof proof, List<ISnapshotEntry> proofState, FileVersion fileVersion) {
		
		List<ProofEntry> entries = new ArrayList<ProofEntry>();
		
		List<Term> stepInGoals = proof.getGoals();
		for (ISnapshotEntry cmdState : proofState) {
			ProofEntry entry = createProofStep(
					proofMatcher, proofProject, fileVersion, sectInfo, stepInGoals, cmdState);
			entries.add(entry);
			
			stepInGoals = entry.getProofStep().getOutGoals();
		}
		
		return entries;
	}
	
	private static class ZEvesProofMatcher extends ProofMatcher {
		
		@Override
		protected boolean matchTrace(Trace trace1, Trace trace2) {
			
			if (trace1 instanceof ZEvesTrace && trace2 instanceof ZEvesTrace) {

				EqualityHelper equalityHelper = new FeatureEqualityHelper(Arrays.asList(
						ZEvesProofProcessPackage.eINSTANCE.getZEvesTrace_Text(),
						ZEvesProofProcessPackage.eINSTANCE.getZEvesTrace_UsedLemmas(),
						ZEvesProofProcessPackage.eINSTANCE.getZEvesTrace_Case()));
				
				return equalityHelper.equals(trace1, trace2);
			}
			
			return super.matchTrace(trace1, trace2);
		}

		@Override
		protected ProofElem getGroupToAdd(Project proofProject, ProofEntry previous,
				ProofEntry entry) {
			
			// TODO check cast
			ZEvesTrace refP = (ZEvesTrace) previous.getProofStep().getTrace();
			ZEvesTrace ref = (ZEvesTrace) entry.getProofStep().getTrace();
			
			List<Integer> proofCaseP = getProofCase(refP.getCase());
			String caseStr = ref.getCase();
			List<Integer> proofCase = getProofCase(caseStr);
			
			if (proofCaseP.equals(proofCase)) {
				// the same - add the entry to the same group
				return getParentProofElem(previous);
			} else {
				int sizeP = proofCaseP.size();
				int size = proofCase.size();
				
				if (sizeP == size) {
					// same size - a sibling of the previous case (add a split?) 
					return addParallelBranch(proofProject, getParentParallel(previous), caseStr);
				} else if (sizeP > size) {
					// shorter size, the previous case has ended - exit split
					// this means take the group of parent parallel - will be added
					// to the same level as parallel
					// FIXME this does not work if the jump is via multiple steps
					return getGroupToAdd(proofProject, getPreviousEntry(getParentParallel(previous)), entry);
				} else {
					// longer size, going deeper into the case
					return addParallelBranch(proofProject, createParallel(proofProject, getParentProofElem(previous)), caseStr);
				}
			}
		}
		
		private ProofParallel getParentParallel(ProofElem entry) {
			EObject parent = entry.eContainer();
			
			if (parent instanceof ProofParallel) {
				return (ProofParallel) parent;
			}
			
			if (parent instanceof ProofElem) {
				return getParentParallel((ProofElem) parent);
			}
			
			return null;
		}
		
		private ProofEntry getPreviousEntry(ProofElem attempt) {
			List<ProofElem> siblings = ProofMatcher.getProofChildren(getParentProofElem(attempt));
			for (int i = siblings.indexOf(attempt) - 1; i >= 0; i--) {
				ProofElem previous = siblings.get(i);
				if (previous instanceof ProofEntry) {
					return (ProofEntry) previous;
				}
			}
			
			return null;
			//193
		}
		
		private ProofSeq addParallelBranch(Project proofProject, ProofParallel parentParallel, String caseStr) {
			
			ProofSeq branch = ProofProcessFactory.eINSTANCE.createProofSeq();
			ProofInfo info = ProofProcessFactory.eINSTANCE.createProofInfo();
			branch.setInfo(info);
			info.setNarrative("Case #" + caseStr);
			info.setIntent(ProofProcessUtil.findCreateIntent(proofProject, "Parallel Branch"));
			
			addToGroup(parentParallel, branch);
			return branch;
		}
		
		private ProofParallel createParallel(Project proofProject, ProofElem parent) {
			
			ProofParallel parallel = ProofProcessFactory.eINSTANCE.createProofParallel();
			ProofInfo info = ProofProcessFactory.eINSTANCE.createProofInfo();
			parallel.setInfo(info);
			
			info.setNarrative("Parallel attempts");
			info.setIntent(ProofProcessUtil.findCreateIntent(proofProject, "Parallel"));
			
			// FIXME add to root?
			addToGroup(parent, parallel);
			return parallel;
		}
	}
	
	private ProofEntry createProofStep(ProofMatcher proofMatcher, Project project, 
			FileVersion fileVersion, SectionInfo sectInfo,
			List<Term> stepInGoals, ISnapshotEntry proofEntry) {
		
		ZEvesOutput entryResult = (ZEvesOutput) proofEntry.getData().getResult();
		
		net.sourceforge.czt.base.ast.Term entryTerm = proofEntry.getData().getTerm();
		String commandText;
		if (entryTerm instanceof ProofCommand) {
			Visitor<String> textVisitor = TermLabelVisitorFactory.getTermLabelVisitor(true);
			commandText = entryTerm.accept(textVisitor);			
		} else {
			commandText = entryResult.getCommand().toString();
		}
		
		ProofInfo info = ProofProcessFactory.eINSTANCE.createProofInfo();
		info.setNarrative("Tactic: " + commandText);
		
		Intent intent = ProofProcessUtil.findCreateIntent(project, "Tactic Application");
		info.setIntent(intent);
		
		// TODO set features
		List<ProofFeature> inFeatures = info.getInFeatures();
		List<ProofFeature> outFeatures = info.getOutFeatures();
		
		ZEvesTrace trace = ZEvesProofProcessFactory.eINSTANCE.createZEvesTrace();
		trace.setText(commandText);
		trace.setCase(proofCaseStr(entryResult.getProofCase()));
		
		Set<String> usedLemmas = new LinkedHashSet<String>();
		
		for (ZEvesOutput result : proofEntry.getData().getTrace()) {
			ZEvesProofTrace proofTrace = result.getProofTrace();
			usedLemmas.addAll(getUsedLemmas(proofTrace));
		}
		
		trace.getUsedLemmas().addAll(usedLemmas);
		
		ProofStep step = ProofProcessFactory.eINSTANCE.createProofStep();
		step.setTrace(trace);
		
		Position pos = proofEntry.getPosition();
		step.setSource(ProofProcessUtil.createTextLoc(fileVersion, pos.getOffset(), pos.getLength()));
		
		List<Term> inGoals = step.getInGoals();
		// copy the goals defensively because inGoals is a containment ref
		inGoals.addAll(proofMatcher.copyTerms(stepInGoals));
		
		List<Term> outGoals = step.getOutGoals();
		outGoals.addAll(parseGoals(sectInfo, proofEntry));
		
		// create tactic application attempt
		ProofEntry entry = ProofProcessFactory.eINSTANCE.createProofEntry();
		entry.setInfo(info);
		entry.setProofStep(step);
		
		return entry;
	}
	
	private List<Term> parseGoals(SectionInfo sectInfo, ISnapshotEntry proofEntry) {
		ZEvesOutput result = getZEvesProofResult(proofEntry);
		return TermParser.parseGoals(sectInfo, proofEntry.getSectionName(), result);
	}
	
	private ZEvesOutput getZEvesProofResult(ISnapshotEntry proofEntry) {
		return (ZEvesOutput) proofEntry.getData().getResult();
	}
	
	private static String proofCaseStr(List<Integer> proofCase) {
		return StringUtils.join(proofCase, ".");
	}
	
	public static List<Integer> getProofCase(String proofCaseStr) {
		
		if (proofCaseStr == null || proofCaseStr.isEmpty()) {
			return Collections.emptyList();
		}
		
		String[] split = proofCaseStr.split("\\.");
		
		try {
			
			List<Integer> proofCase = new ArrayList<Integer>(split.length);
			for (String str : split) {
				proofCase.add(Integer.parseInt(str));
			}
			
			return proofCase;
			
		} catch (NumberFormatException ex) {
			// invalid?
			return Collections.emptyList();
		}
		
	}
	
	private Collection<String> getUsedLemmas(ZEvesProofTrace trace) {

		EnumSet<TraceType> lemmaTypes = EnumSet.of(TraceType.APPLY, TraceType.REWRITE,
				TraceType.FRULE, TraceType.GRULE, TraceType.USE);
		
		Set<String> usedLemmas = new LinkedHashSet<String>();
		
		for (TraceType type : lemmaTypes) {
			for (Object elem : trace.getTraceElements(type)) {
				if (elem instanceof ZEvesName) {
					usedLemmas.add(((ZEvesName) elem).getIdent());
				} else {
					ZEvesProofUIPlugin.log("Unknown used lemma element found in trace: " + elem.toString(), null);
				}
			}
		}
		
		return usedLemmas;
	}
	
	private List<ISnapshotEntry> getProofEntries(ISnapshotEntry endingWithEntry) {
		
		if (endingWithEntry != null && endingWithEntry.getData().getGoalName() == null) {
			// no goal - not part of a proof, thus no proof for it
			// this is a shortcut check to avoid constructing iterator below
			return Collections.emptyList();
		}
		
		List<? extends ISnapshotEntry> entries = snapshot.getEntries();
		
		int startFrom = entries.size();
		if (endingWithEntry != null) {
			int lastFound = entries.lastIndexOf(endingWithEntry);
			if (lastFound >= 0) {
				startFrom = lastFound + 1;
			}
		}
		
		
		// backwards iterator
		ListIterator<? extends ISnapshotEntry> it = entries.listIterator(startFrom);
		
		List<ISnapshotEntry> proofEntries = new LinkedList<ISnapshotEntry>();
		String goalName = null;
		
		while (it.hasPrevious()) {
			ISnapshotEntry entry = it.previous();
			
			if (goalName == null) {
				// TODO is goal name enough?
				goalName = entry.getData().getGoalName();
				if (goalName == null) {
					// no goal name in the given entry - it is not part of a proof
					return Collections.emptyList();
				}
			}
			
			// goalName cannot be null here, because of the check above
			if (!goalName.equals(entry.getData().getGoalName())) {
				// different/no goal - no longer in the same proof
				break;
			}
			
			proofEntries.add(0, entry);
		}
		
		return proofEntries;
	}
	
	private class AnalysisJob extends Job {

		public AnalysisJob() {
			super("Analysing proof process");
			// lowest priority
			setPriority(DECORATE);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			
			SnapshotAnalysisEvent nextEvent = pendingEvents.poll();
			if (nextEvent == null) {
				// nothing to execute
				return Status.OK_STATUS;
			}
			
			if (ZEvesProofUIPlugin.getDefault() == null) {
				// shutting down - do not execute any more
				return Status.CANCEL_STATUS;
			}
			
			try {
				return analyse(nextEvent, monitor);
			} catch (CoreException e) {
				return ZEvesProofUIPlugin.error(
						"Proof process analysis failed: " + e.getMessage(), e);
			}
		}
	}
	
	private static class SnapshotAnalysisEvent {
		
		private final SnapshotChangedEvent event;
		private final SectionInfo sectInfo;
		private final ISnapshotEntry entry;
		private final List<ISnapshotEntry> entryProof;
		
		public SnapshotAnalysisEvent(SnapshotChangedEvent event, SectionInfo sectInfo,
				ISnapshotEntry entry, List<ISnapshotEntry> entryProof) {
			super();
			this.event = event;
			this.sectInfo = sectInfo;
			this.entry = entry;
			this.entryProof = entryProof;
		}
		
	}
	
	private static class FeatureEqualityHelper extends EqualityHelper {
		
		private final Set<EStructuralFeature> equalityFeatures = 
				new HashSet<EStructuralFeature>();
		
		public FeatureEqualityHelper(Collection<? extends EStructuralFeature> equalityFeatures) {
			this.equalityFeatures.addAll(equalityFeatures);
		}

		@Override
		protected boolean haveEqualFeature(EObject eObject1, EObject eObject2,
				EStructuralFeature feature) {
			
			if (!equalityFeatures.contains(feature)) {
				// not considered an equality feature - consider it to be equal there
				return true;
			}
			
			// TODO Auto-generated method stub
			return super.haveEqualFeature(eObject1, eObject2, feature);
		}
		
	}
	
}
