package org.ai4fm.proofprocess.ui.views

import org.ai4fm.proofprocess.core.store.IProofStoreProvider
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.part.Page
import org.eclipse.swt.widgets.Control
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.swt.SWT
import org.eclipse.jface.layout.GridLayoutFactory
import org.eclipse.swt.widgets.Label
import org.eclipse.jface.viewers.TreeViewer
import org.eclipse.jface.layout.GridDataFactory
import org.eclipse.jface.action.MenuManager
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.IStatus
import org.ai4fm.proofprocess.ProofStore


class PProcessPage(private val proofStoreProvider: IProofStoreProvider) extends Page {

  private val adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
  
  private var main: Composite = _
  private var treeViewer: TreeViewer = _

  override def createControl(parent: Composite) {

    main = new Composite(parent, SWT.NONE);
    main.setLayout(GridLayoutFactory.fillDefaults.extendedMargins(0, 0, 2, 0).create);

    treeViewer = new TreeViewer(main, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
    treeViewer.getControl().setLayoutData(GridDataFactory.fillDefaults.grab(true, true).create);

//    val groupAttemptsAction = new GroupAttemptsAction();
//    // add context menu
//    val mgr = new MenuManager();
//    mgr.add(groupAttemptsAction);
//    val tree = treeViewer.getTree();
//    tree.setMenu(mgr.createContextMenu(tree));
    
    treeViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
    treeViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

    // load the proof process in a separate job, otherwise it delays the startup
    val loadJob = new Job("Loading proof process") {

      override protected def run(monitor: IProgressMonitor): IStatus = {

        // load the proof store if needed
        val proofStore = proofStoreProvider.store

        // set tree input back in the UI thread
        main.getDisplay asyncExec {() => treeViewer.setInput(proofStore)}

        Status.OK_STATUS
      }
    }
    loadJob.setPriority(Job.DECORATE);
    loadJob.schedule();
  }
  
  implicit private def runnable(f: () => Unit): Runnable =
    new Runnable() { def run() = f() }

  override def getControl = main

  override def setFocus = treeViewer.getControl.setFocus
  
  override def dispose {
    adapterFactory.dispose
	super.dispose
  }

}
