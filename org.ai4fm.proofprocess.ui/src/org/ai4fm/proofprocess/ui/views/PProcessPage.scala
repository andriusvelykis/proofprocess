package org.ai4fm.proofprocess.ui.views

import scala.annotation.tailrec
import scala.language.implicitConversions

import org.ai4fm.proofprocess.Proof
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.core.prefs.PreferenceTracker
import org.ai4fm.proofprocess.core.store.IProofEntryTracker
import org.ai4fm.proofprocess.core.store.IProofStoreProvider
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.error
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.log
import org.ai4fm.proofprocess.ui.prefs.PProcessUIPreferences.TRACK_LATEST_PROOF_ENTRY
import org.ai4fm.proofprocess.ui.prefs.PProcessUIPreferences.prefs
import org.ai4fm.proofprocess.ui.util.SWTUtil.noArgFnToDoubleClickListener
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.jface.action.GroupMarker
import org.eclipse.jface.action.MenuManager
import org.eclipse.jface.layout.GridDataFactory
import org.eclipse.jface.layout.GridLayoutFactory
import org.eclipse.jface.resource.JFaceResources
import org.eclipse.jface.resource.LocalResourceManager
import org.eclipse.jface.viewers.StructuredSelection
import org.eclipse.jface.viewers.TreePath
import org.eclipse.jface.viewers.Viewer
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.IViewPart
import org.eclipse.ui.IWorkbenchActionConstants
import org.eclipse.ui.commands.ICommandService
import org.eclipse.ui.dialogs.FilteredTree
import org.eclipse.ui.dialogs.PatternFilter
import org.eclipse.ui.handlers.IHandlerService
import org.eclipse.ui.part.Page


class PProcessPage(viewPart: IViewPart,
                   proofStoreProvider: IProofStoreProvider,
                   proofEntryTracker: Option[IProofEntryTracker])
    extends Page {

  private val adapterFactory =
    new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)
  
  private var main: Composite = _
  private var treeViewer: FilteredTree = _
  
  private var latestProofEntry: Option[ProofEntry] = None

  override def createControl(parent: Composite) {

    main = new Composite(parent, SWT.NONE);
    main.setLayout(GridLayoutFactory.fillDefaults.extendedMargins(0, 0, 2, 0).create);

    treeViewer = new FilteredTree(main, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL,
                                  new PProcessTreeFilter, true)
    treeViewer.getFilterControl.setMessage("type proof filter text")
    treeViewer.setLayoutData(GridDataFactory.fillDefaults.grab(true, true).create)

    // add context menu
    val mgr = new MenuManager();

    // add a placeholder for contributed actions
    mgr.add(new GroupMarker("edit"))
    mgr.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS))
    val menu = mgr.createContextMenu(treeViewer.getViewer.getTree)
    treeViewer.getViewer.getTree.setMenu(menu)
    // register the menu with site to allow contributions
    getSite.registerContextMenu(viewPart.getViewSite.getId, mgr, treeViewer.getViewer)

    // on double-click, open the dialog to mark features or to edit selected element
    // (availability handled by handler definition in the plugin.xml)
    treeViewer.getViewer.addDoubleClickListener { () =>
      openMarkFeaturesDialog() || openEditDialog()
    }

    val resourceMgr = new LocalResourceManager(
      JFaceResources.getResources, treeViewer.getViewer.getTree)
    val overridingLabelProvider = new PProcessViewLabelProvider(resourceMgr)
    
    treeViewer.getViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory))

    // allow overriding image via overriding label provider
    treeViewer.getViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory) {
      override def getImage(element: AnyRef): Image = {
        val overrideImg = overridingLabelProvider.getImage(element)
        
        overrideImg getOrElse super.getImage(element)
      }
    })

    // register as global selection provider
    getSite().setSelectionProvider(treeViewer.getViewer);
    
    // load the proof process in a separate job, otherwise it delays the startup
    val loadJob = new Job("Loading proof process") {

      override protected def run(monitor: IProgressMonitor): IStatus = {

        // load the proof store if needed
        val proofStore = proofStoreProvider.store

        // set tree input back in the UI thread
        main.getDisplay asyncExec { () =>
          {
            treeViewer.getViewer.setInput(proofStore)

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

    treeViewer.getViewer.expandToLevel(path, path.getSegmentCount)
    treeViewer.getViewer.setSelection(new StructuredSelection(entry), true)
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

  /**
   * A filter for ProofProcess view tree: only filters on proofs
   */
  private class PProcessTreeFilter extends PatternFilter {
    override def isElementVisible(viewer: Viewer, element: AnyRef): Boolean = element match {
      // for proofs, do the matching - otherwise show all other elements
      case proof: Proof => isLeafMatch(viewer, proof)
      case _ => true
    }
  }

  private def MARK_FEATURES_COMMAND_ID = "org.ai4fm.proofprocess.ui.markFeatures"
  private def EDIT_ELEM_COMMAND_ID = "org.ai4fm.proofprocess.ui.editElement"

  private def openMarkFeaturesDialog(): Boolean = executeCommand(MARK_FEATURES_COMMAND_ID)

  private def openEditDialog(): Boolean = executeCommand(EDIT_ELEM_COMMAND_ID)

  /**
   * Executes the given command ID if it is enabled
   */
  private def executeCommand(cmdId: String): Boolean =
    (getSiteService(classOf[ICommandService]), getSiteService(classOf[IHandlerService])) match {

      case (Some(commandService), Some(handlerService)) =>
        try {
          val command = commandService.getCommand(cmdId)
          if (command.isEnabled) {
            handlerService.executeCommand(cmdId, null)
            true
          } else {
            false
          }
        } catch {
          case e: Exception => {
            log(error(Some(e)))
            false
          }
        }

      case _ => false
    }

  private def getSiteService[T](clazz: Class[T]): Option[T] =
    Option(getSite.getService(clazz).asInstanceOf[T])

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

  override def setFocus = treeViewer.setFocus
  
  override def dispose {
    trackEntryPref.dispose()
    proofEntryTracker foreach (_.dispose())
    adapterFactory.dispose
    super.dispose
  }

}
