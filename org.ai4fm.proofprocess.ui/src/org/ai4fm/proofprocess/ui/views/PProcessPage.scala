package org.ai4fm.proofprocess.ui.views

import scala.annotation.tailrec
import scala.language.implicitConversions

import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.core.prefs.PreferenceTracker
import org.ai4fm.proofprocess.core.store.{IProofEntryTracker, IProofStoreProvider}
import org.ai4fm.proofprocess.ui.prefs.PProcessUIPreferences._
import org.eclipse.core.runtime.{IProgressMonitor, IStatus, Status}
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.ui.provider.{AdapterFactoryContentProvider, AdapterFactoryLabelProvider}
import org.eclipse.jface.action.{GroupMarker, MenuManager}
import org.eclipse.jface.layout.{GridDataFactory, GridLayoutFactory}
import org.eclipse.jface.resource.{JFaceResources, LocalResourceManager}
import org.eclipse.jface.viewers.{StructuredSelection, TreePath, TreeViewer}
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.{IViewPart, IWorkbenchActionConstants}
import org.eclipse.ui.part.Page


class PProcessPage(viewPart: IViewPart,
                   proofStoreProvider: IProofStoreProvider,
                   proofEntryTracker: Option[IProofEntryTracker])
    extends Page {

  private val adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
  
  private var main: Composite = _
  private var treeViewer: TreeViewer = _
  
  private var latestProofEntry: Option[ProofEntry] = None

  override def createControl(parent: Composite) {

    main = new Composite(parent, SWT.NONE);
    main.setLayout(GridLayoutFactory.fillDefaults.extendedMargins(0, 0, 2, 0).create);

    treeViewer = new TreeViewer(main, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
    treeViewer.getControl().setLayoutData(GridDataFactory.fillDefaults.grab(true, true).create);

//    val groupAttemptsAction = new GroupAttemptsAction();
    // add context menu
    val mgr = new MenuManager();
//    mgr.add(groupAttemptsAction);
    // add a placeholder for contributed actions
    mgr.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS))
    val menu = mgr.createContextMenu(treeViewer.getTree)
    treeViewer.getTree.setMenu(menu)
    // register the menu with site to allow contributions
    getSite.registerContextMenu(viewPart.getViewSite.getId, mgr, treeViewer)

    val resourceMgr = new LocalResourceManager(JFaceResources.getResources, treeViewer.getTree)
    val overridingLabelProvider = new PProcessViewLabelProvider(resourceMgr)
    
    treeViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory))

    // allow overriding image via overriding label provider
    treeViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory) {
      override def getImage(element: AnyRef): Image = {
        val overrideImg = overridingLabelProvider.getImage(element)
        
        overrideImg getOrElse super.getImage(element)
      }
    })

    // register as global selection provider
    getSite().setSelectionProvider(treeViewer);
    
    // load the proof process in a separate job, otherwise it delays the startup
    val loadJob = new Job("Loading proof process") {

      override protected def run(monitor: IProgressMonitor): IStatus = {

        // load the proof store if needed
        val proofStore = proofStoreProvider.store

        // set tree input back in the UI thread
        main.getDisplay asyncExec { () =>
          {
            treeViewer.setInput(proofStore)

            // init latest entry tracker if available
            // note that it needs to be set up in UI thread, otherwise 
            // databinding does not work correctly
            proofEntryTracker foreach (_.initTrackLatestEntry(latestEntryChanged))
          }
        }

        Status.OK_STATUS
      }
    }
    loadJob.setPriority(Job.DECORATE);
    loadJob.schedule();
  }

  private def latestEntryChanged(entry: ProofEntry) {

    if (isTrackLatestEntry) {
      // ensure running in UI thread
      main.getDisplay asyncExec { () => highlightEntry(entry) }
      latestProofEntry = None
    } else {
      // mark for later - not tracking at the moment
      latestProofEntry = Some(entry)
    }
  }

  private def highlightEntry(entry: ProofEntry) {
    val parents = collectParents(entry)

    val path = new TreePath(parents.toArray)

    treeViewer.expandToLevel(path, path.getSegmentCount)
    treeViewer.setSelection(new StructuredSelection(entry), true)
  }

  private def collectParents(eobj: EObject): List[EObject] = {
    @tailrec
    def collect0(eobj: EObject, acc: List[EObject]): List[EObject] =
      Option(eobj) match {
        case None => acc
        case Some(eobj) => collect0(eobj.eContainer, eobj :: eobj :: acc)
      }

    collect0(eobj, List())
  }

  private val trackEntryPref = new PreferenceTracker(prefs, TRACK_LATEST_PROOF_ENTRY)(() => {
    
    if (isTrackLatestEntry) {
      // tracking! select the last entry if available
      latestProofEntry foreach highlightEntry
    }
  })
  
  private def isTrackLatestEntry: Boolean = prefs.getBoolean(TRACK_LATEST_PROOF_ENTRY, true)

  implicit private def runnable(f: () => Unit): Runnable =
    new Runnable() { def run() = f() }

  override def getControl = main

  override def setFocus = treeViewer.getControl.setFocus
  
  override def dispose {
    trackEntryPref.dispose()
    proofEntryTracker foreach (_.dispose())
    adapterFactory.dispose
    super.dispose
  }

}
