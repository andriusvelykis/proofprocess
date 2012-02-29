package org.ai4fm.proofprocess.zeves.ui.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.czt.eclipse.zeves.ZEvesImages;

import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.ProofElem;
import org.ai4fm.proofprocess.ProofEntry;
import org.ai4fm.proofprocess.ProofInfo;
import org.ai4fm.proofprocess.ProofProcessFactory;
import org.ai4fm.proofprocess.ProofSeq;
import org.ai4fm.proofprocess.provider.ProofProcessEditPlugin;
import org.ai4fm.proofprocess.zeves.Activity;
import org.ai4fm.proofprocess.zeves.Project;
import org.ai4fm.proofprocess.zeves.ProofActivity;
import org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage;
import org.ai4fm.proofprocess.zeves.ZEvesTrace;
import org.ai4fm.proofprocess.zeves.ui.ProofManager;
import org.ai4fm.proofprocess.zeves.ui.SnapshotTracker;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.part.Page;

public class ProofProcessPage extends Page {

	private Composite main;
	
	private IProject projectResource;
	
	private TreeViewer treeViewer;
	private final ComposedAdapterFactory adapterFactory;
	
	private Project proofProject;
	
	private Action groupAttemptsAction;
	
	private IObservableList activitiesObservableList;
	private IChangeListener activitiesListListener;
	
	private WritableValue lastAttemptObservable = new WritableValue();
	private IObservableValue lastAttemptDelayed = Observables.observeDelayedValue(500, lastAttemptObservable);
	private IValueChangeListener lastAttemptListener;
	
	public ProofProcessPage(IProject projectResource) {
		super();
		this.projectResource = projectResource;
		
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
//
//		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
//		adapterFactory.addAdapterFactory(new ZEvesProofItemProviderAdapterFactory());
//		adapterFactory.addAdapterFactory(new ProofProcessItemProviderAdapterFactory());
//		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
	}

	@Override
	public void createControl(Composite parent) {
		main = new Composite(parent, SWT.NONE);
		main.setLayout(GridLayoutFactory.fillDefaults().extendedMargins(0, 0, 2, 0).create());

		Label label = new Label(main, SWT.NONE);
		label.setText("Project: " + projectResource.getName());
		
		treeViewer = new TreeViewer(main, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.getControl().setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
		
		groupAttemptsAction = new GroupAttemptsAction();
        // add context menu
        final MenuManager mgr = new MenuManager();
        mgr.add(groupAttemptsAction);
        Control tree = treeViewer.getTree();
        tree.setMenu(mgr.createContextMenu(tree));
		
		treeViewer.setContentProvider(new CustomizedAdapterContentProvider(adapterFactory));
//		treeViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
		treeViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory) {

			@Override
			public Image getImage(Object object) {
				// FIXME quite a hack here
				if (object instanceof ProofEntry) {
					ProofEntry entry = (ProofEntry) object;
					if (entry.getProofStep().getTrace() instanceof ZEvesTrace) {
						ZEvesTrace ref = (ZEvesTrace) entry.getProofStep().getTrace();
						if ("true".equals(ref.getGoal())) {
							return ZEvesImages.getImage(ZEvesImages.IMG_THEOREM_PROVED);
						}
 					}
				}
				return super.getImage(object);
			}
			
		});
		
		lastAttemptDelayed.addValueChangeListener(lastAttemptListener = new IValueChangeListener() {
			
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				ProofEntry lastAttempt = (ProofEntry) lastAttemptDelayed.getValue();
				if (lastAttempt == null) {
					return;
				}
				
				highlightAttempt(lastAttempt);
			}
		});

		
		try {
			proofProject = ProofManager.getProofProject(projectResource, null);
			treeViewer.setInput(proofProject);
			
			IEMFListProperty projectActivitiesProp = EMFProperties.list(
					ZEvesProofProcessPackage.eINSTANCE.getProject_Activities());
			activitiesObservableList = projectActivitiesProp.observe(proofProject);
			activitiesObservableList.addChangeListener(activitiesListListener = new IChangeListener() {
				
				@Override
				public void handleChange(ChangeEvent event) {
					if (activitiesObservableList.isEmpty()) {
						return;
					}
					
					Object lastValue = activitiesObservableList.get(activitiesObservableList.size() - 1);
					
					if (lastValue instanceof ProofActivity) {
						ProofEntry lastAttempt = ((ProofActivity) lastValue).getProofRef();
						lastAttemptObservable.setValue(lastAttempt);
					}
				}
			});
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void highlightAttempt(ProofEntry attempt) {
		
		List<EObject> parentList = new ArrayList<EObject>();
		collectParents(attempt, parentList);
		Collections.reverse(parentList);
		
		TreePath path = new TreePath(parentList.toArray());
		
		treeViewer.expandToLevel(path, parentList.size());
		treeViewer.setSelection(new StructuredSelection(attempt), true);
		
		System.out.println("Expanding: " + path);
		System.out.println("Delayed attempt: " + attempt.getInfo().getNarrative());
	}
	
	private void collectParents(EObject obj, List<EObject> list) {
		
		// tail recursive call to parents
		while (obj != null) {
			list.add(obj);
			obj = obj.eContainer();
		}
	}
	
	@Override
	public Control getControl() {
		return main;
	}

	@Override
	public void setFocus() {
		main.setFocus();
	}

	@Override
	public void dispose() {
		lastAttemptDelayed.removeValueChangeListener(lastAttemptListener);
		activitiesObservableList.removeChangeListener(activitiesListListener);
		adapterFactory.dispose();
		super.dispose();
	}
	
	private static class CustomizedAdapterContentProvider extends AdapterFactoryContentProvider {

		public CustomizedAdapterContentProvider(AdapterFactory adapterFactory) {
			super(adapterFactory);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getChildren(java.lang.Object)
		 */
		@Override
		public Object[] getChildren(Object object) {
			List<Object> children = new ArrayList<Object>(Arrays.asList(super.getChildren(object)));
			
			List<?> moreChildren = getChildrenCustomized(object);
			if (moreChildren.size() > 0) {
				// remove already in the list
				moreChildren.removeAll(children);
				children.addAll(moreChildren);
			}
			
			return children.toArray();
		}
		
		@Override
		public Object[] getElements(Object object) {
			
			if (object instanceof Project) {
				// FIXME investigate, make better
				// remove all activities here for now
				List<Object> filtered = new ArrayList<Object>();
				for (Object child : super.getElements(object)) {
					if (!(child instanceof Activity)) {
						filtered.add(child);
					}
				}
				
				return filtered.toArray();
			}
			
			return super.getElements(object);
		}

		private List<?> getChildrenCustomized(Object parent) {
			if (parent instanceof ZEvesTrace) {
				ZEvesTrace ref = (ZEvesTrace) parent;
				List<String> lemmas = new ArrayList<String>(ref.getUsedLemmas());
				
				String proofCase = ref.getCase();
				if (proofCase != null && !proofCase.isEmpty()) {
					lemmas.add(0, "Case #" + proofCase);
				}
				
				return lemmas;
			}
			
			if (parent instanceof ProofElem) {
				ProofElem attempt = (ProofElem) parent;
				if (attempt.getInfo().getIntent() != null) {
					return Arrays.asList(attempt.getInfo().getIntent());
				}
			}
			
			return Collections.emptyList();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#hasChildren(java.lang.Object)
		 */
		@Override
		public boolean hasChildren(Object object) {
			return super.hasChildren(object) || !getChildrenCustomized(object).isEmpty();
		}
		
	}
	
	private class GroupAttemptsAction extends Action {

		public GroupAttemptsAction() {
			super("Group Attempts");
			setToolTipText("Group Selected Attempts Sequentially");
			
			Object image = ProofProcessEditPlugin.INSTANCE.getImage("full/obj16/AttemptSequential");
			setImageDescriptor(ExtendedImageRegistry.getInstance().getImageDescriptor(image));
		}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		@Override
		public void run() {
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			if (selection.isEmpty()) {
				return;
			}

			List<ProofElem> attempts = new ArrayList<ProofElem>();
			for (Iterator<?> it = selection.iterator(); it.hasNext(); ) {
				Object elem = it.next();
				if (elem instanceof ProofElem) {
					attempts.add((ProofElem) elem);
				}
			}
			
			if (attempts.isEmpty()) {
				error("No proof elements have been selected for grouping.");
				return;
			}
			
			ProofElem first = attempts.get(0);
			EObject parentContainer = first.eContainer();
			if (!(parentContainer instanceof ProofElem)) {
				error("Cannot group root attempts.");
				return;
			}
			
			ProofElem parentGroup = (ProofElem) parentContainer;
			List<ProofElem> siblings = SnapshotTracker.getProofChildren(parentGroup);
			
			int sublistIndex = Collections.indexOfSubList(siblings, attempts);
			if (sublistIndex < 0) {
				error("Attempts to be grouped must be in the same level, and must be uniformly selected.");
				return;
			}
			
			FilteredEntryDialog intentDialog = new FilteredEntryDialog(
					main.getShell(), new LabelProvider(){

				@Override
				public String getText(Object element) {
					Intent intent = (Intent) element;
					return intent.getName();
				}
			});
			
			intentDialog.setTitle("Select Intent");
			intentDialog.setMessage("Enter or select the intent for this attempt group.");
			intentDialog.setMultipleSelection(false);
			intentDialog.setElements(proofProject.getIntents().toArray());
			
			if (intentDialog.open() != Window.OK) {
				return;
			}
			
			Object[] result = intentDialog.getResult();
			String intentText;
			if (result.length == 0) {
				intentText = intentDialog.filterText;
			} else {
				intentText = ((Intent) result[0]).getName();
			}
			
			if (intentText.isEmpty()) {
				error("Invalid intent entered.");
				return;
			}
			
			InputDialog descDialog = new InputDialog(main.getShell(), "Enter Description",
					"Enter attempt description", intentText, null);
			if (descDialog.open() != Window.OK) {
				return;
			}
			
			String description = descDialog.getValue();
			
			// create new AttemptGroup
			ProofSeq group = ProofProcessFactory.eINSTANCE.createProofSeq();
			ProofInfo info = ProofProcessFactory.eINSTANCE.createProofInfo();
			group.setInfo(info);
			info.setNarrative(description);
			info.setIntent(SnapshotTracker.findCreateIntent(proofProject, intentText));
			
			// TODO grouping Decor?
			siblings.add(sublistIndex, group);
//			parentGroup.getContained().add(sublistIndex, group);
			
//			SnapshotTracker.addToGroup(parentGroup, group);
//			siblings.move(sublistIndex, group);
			
			for (ProofElem attempt : attempts) {
				SnapshotTracker.addToGroup(group, attempt);
				siblings.remove(attempt);
			}
			
			// FIXME
			try {
				proofProject.eResource().save(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			treeViewer.setSelection(new StructuredSelection(group), true);
		}
		
		private void error(String message) {
			MessageDialog.openError(main.getShell(), "Group Attempts", message);
		}
	}
	
	private static class FilteredEntryDialog extends ElementListSelectionDialog {
		
		private String filterText;
		
		public FilteredEntryDialog(Shell parent, ILabelProvider renderer) {
			super(parent, renderer);
		}

		@Override
		protected boolean validateCurrentSelection() {
			
			Object[] elements = getSelectedElements();
			if (elements.length == 0) {
				// no elements - a new one will be created
				updateStatus(new Status(IStatus.OK, PlatformUI.PLUGIN_ID,
                        IStatus.OK, "", //$NON-NLS-1$
                        null));
				return true;
			}
			
			return super.validateCurrentSelection();
		}

		@Override
		protected void computeResult() {
			filterText = fFilteredList.getFilter();
			super.computeResult();
		}
		
	};

}
