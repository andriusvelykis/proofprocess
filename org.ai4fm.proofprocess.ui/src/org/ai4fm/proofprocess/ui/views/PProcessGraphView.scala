package org.ai4fm.proofprocess.ui.views

import scala.collection.JavaConversions._

import org.ai4fm.proofprocess.ui.PProcessUIPlugin
import org.ai4fm.proofprocess.ui.graph.{PProcessFigureProvider, PProcessGraphContentProvider}
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.gef4.zest.core.viewers.{EntityConnectionData, GraphViewer}
import org.eclipse.gef4.zest.core.widgets.ZestStyles
import org.eclipse.gef4.zest.layouts.LayoutAlgorithm
import org.eclipse.gef4.zest.layouts.algorithms.{
  CompositeLayoutAlgorithm,
  DirectedGraphLayoutAlgorithm,
  HorizontalShiftAlgorithm
}
import org.eclipse.jface.action.{Action, GroupMarker, IAction, MenuManager}
import org.eclipse.jface.commands.ActionHandler
import org.eclipse.jface.layout.{GridDataFactory, GridLayoutFactory}
import org.eclipse.jface.resource.{JFaceResources, LocalResourceManager}
import org.eclipse.jface.viewers.{ISelection, IStructuredSelection}
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.{
  IActionBars,
  IPartListener2,
  ISelectionListener,
  ISharedImages,
  IWorkbenchActionConstants,
  IWorkbenchCommandConstants,
  IWorkbenchPart,
  IWorkbenchPartReference,
  PlatformUI
}
import org.eclipse.ui.handlers.IHandlerService
import org.eclipse.ui.part.ViewPart


/** @author Andrius Velykis
  */
object PProcessGraphView {

  private val viewId = PProcessUIPlugin.plugin.pluginId + ".PProcessGraphView"
  private val propLinkSelection = viewId + ".linkSelection"

  // init default values once
  private def prefs = PProcessUIPlugin.prefStore
  prefs.setDefault(propLinkSelection, true)

}

/** A view that displays certain selected ProofProcess elements as a graph structure
  * 
  * @author Andrius Velykis
  */
class PProcessGraphView extends ViewPart {

  // import object contents to avoid full name referencing
  import PProcessGraphView._

  private val adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

  private var graphViewer: GraphViewer = _
  private lazy val graphContentProvider = new PProcessGraphContentProvider // with PProcessNestedContentProvider
  private lazy val graphLabelProvider = new AdapterFactoryLabelProvider(adapterFactory) with PProcessFigureProvider {

    override val resourceManager = new LocalResourceManager(JFaceResources.getResources(), graphViewer.getControl)

    override def getImage(entity: AnyRef): Image = entity match {
      case conn: EntityConnectionData => null
      case _ => super.getImage(entity)
    }

    override def getText(entity: AnyRef): String = entity match {
      case conn: EntityConnectionData => null
      case _ => super.getText(entity)
    }
  }

  // get the preferences value whether to follow
  private var followSelection = prefs.getBoolean(propLinkSelection)
  private var lastSelection: Option[Any] = None

  private val partListener = new PartInactiveHandler
  private val selectionListener = new ISelectionListener {
    override def selectionChanged(part: IWorkbenchPart, selection: ISelection) =
      handleSelectionChanged(part, selection)
  }

  override def createPartControl(parent: Composite) {

    val main = new Composite(parent, SWT.NONE)
    main.setLayout(GridLayoutFactory.fillDefaults.extendedMargins(0, 0, 2, 0).create)

    graphViewer = new GraphViewer(main, SWT.NONE | SWT.V_SCROLL | SWT.H_SCROLL)
    graphViewer.getControl().setLayoutData(GridDataFactory.fillDefaults.grab(true, true).create)
    graphViewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED)

    // add context menu
    val mgr = new MenuManager();
    // add a placeholder for contributed actions
    mgr.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS))
    val menu = mgr.createContextMenu(graphViewer.getGraphControl)
    graphViewer.getGraphControl.setMenu(menu)
    // register the menu with site to allow contributions
    getSite.registerContextMenu(mgr, graphViewer)

    registerToolbarActions(getViewSite.getActionBars)

    graphViewer.setContentProvider(graphContentProvider)
    graphViewer.setLabelProvider(graphLabelProvider)

    graphViewer.setLayoutAlgorithm(layout(), true)
    graphViewer.applyLayout()

    // register as global selection provider
    getSite().setSelectionProvider(graphViewer)

    getSite.getWorkbenchWindow.getPartService.addPartListener(partListener)
  }

  def layout(): LayoutAlgorithm = {
    // new SpaceTreeLayoutAlgorithm()
    new CompositeLayoutAlgorithm(Array(new DirectedGraphLayoutAlgorithm, new HorizontalShiftAlgorithm))
  }

  override def setFocus = graphViewer.getControl.setFocus

  private def registerToolbarActions(actionBars: IActionBars) {

    // toggle link is also a handler for "link with editor" workbench action
    val toggleLinkAction = new ToggleLinkAction();
    toggleLinkAction.setActionDefinitionId(IWorkbenchCommandConstants.NAVIGATE_TOGGLE_LINK_WITH_EDITOR);

    // add actions to toolbar
    Option(actionBars.getToolBarManager()) foreach { mgr =>
      {
        // also add group markers to allow command-based additions
        mgr.add(new GroupMarker("view"))
        mgr.add(toggleLinkAction)
      }
    }

    // register as the handler
    val handlerService = getSite().getService(classOf[IHandlerService]).asInstanceOf[IHandlerService];
    handlerService.activateHandler(
      IWorkbenchCommandConstants.NAVIGATE_TOGGLE_LINK_WITH_EDITOR,
      new ActionHandler(toggleLinkAction));

  }

  override def dispose {
    getSite.getWorkbenchWindow.getPartService.removePartListener(partListener)
    stopListeningForSelectionChanges
    adapterFactory.dispose
    super.dispose
  }

  /** Start to listen for selection changes. */
  private def startListeningForSelectionChanges() = getSite.getPage.addPostSelectionListener(selectionListener)

  /** Stop to listen for selection changes. */
  private def stopListeningForSelectionChanges() = getSite.getPage.removePostSelectionListener(selectionListener);

  private def updateOutput(element: Option[Any]) = {
    element foreach { e =>
      // TODO check if the same?
      graphViewer.setInput(e)

      val desc = Option(graphLabelProvider.getText(e)) getOrElse ""
      setContentDescription(desc)
      setTitleToolTip(desc)
    }
  }

  private def handleSelectionChanged(part: IWorkbenchPart, selection: ISelection) {

    if (part != this) {

      val selected = findSelectedPProcessElement(part, selection)

      lastSelection = if (!followSelection) {

        selected match {
          case s @ Some(elem) => s
          // only store actually found selections (e.g. do not reset previous 'last selection')
          case None => this.lastSelection
        }

      } else {
        updateOutput(selected)
        // do not track selection
        None
      }
    }
  }

  private def findSelectedPProcessElement(part: IWorkbenchPart, selection: ISelection): Option[Any] =
    selection match {
      case ss: IStructuredSelection => {
        // use content provider to determine if can compute graph for the selected element
        val it = ss.iterator.filter(graphContentProvider.computeGraph.isDefinedAt)
        // select the first matching
        if (it.hasNext) Some(it.next) else None
      }
      case _ => None
    }

  /** Action to toggle linking with selection. */
  private class ToggleLinkAction extends ToggleAction(
    "Link with Selection", "Link with Selection", propLinkSelection, followSelection) {

    private def images = PlatformUI.getWorkbench.getSharedImages
    setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED))
    setDisabledImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED_DISABLED))

    override def handleValueChanged(link: Boolean) {
      followSelection = link

      if (followSelection) {
        // update the output with the last selection
        updateOutput(lastSelection)
      }
    }
  }

  private abstract class ToggleAction(text: String, desc: String, prefKey: String, initVal: Boolean)
    extends Action(text, IAction.AS_CHECK_BOX) {

    //    PlatformUI.getWorkbench().getHelpSystem().setHelp(this, TOGGLE_LINK_ID)
    setToolTipText(desc)
    setDescription(desc)
    setChecked(initVal)

    override def run() {
      setChecked(isChecked())
      valueChanged(isChecked())
    }

    private def valueChanged(value: Boolean) {
      // set new value in the preferences
      prefs.setValue(prefKey, value)

      // delegate to the implementation
      handleValueChanged(value)
    }

    protected def handleValueChanged(value: Boolean): Unit

  }

  private class PartInactiveHandler extends IPartListener2 {

    private def thisPart(ref: IWorkbenchPartReference) = ref.getId == getSite.getId

    override def partVisible(ref: IWorkbenchPartReference) =
      if (thisPart(ref)) {

        Option(ref.getPage.getActivePart) foreach { part => handleSelectionChanged(part, ref.getPage.getSelection) }
        startListeningForSelectionChanges()
      }

    override def partHidden(ref: IWorkbenchPartReference) =
      if (thisPart(ref)) stopListeningForSelectionChanges()

    override def partInputChanged(ref: IWorkbenchPartReference) =
      if (!thisPart(ref)) {
        
        // retrieve part and selection - then handle the changes
        Option(ref.getPart(false)) flatMap {
          part => Option((part.getSite.getSelectionProvider)) map (provider => (part, provider.getSelection))
        } foreach Function.tupled(handleSelectionChanged)
      }

    override def partActivated(partRef: IWorkbenchPartReference) {}

    override def partBroughtToTop(partRef: IWorkbenchPartReference) {}

    override def partClosed(partRef: IWorkbenchPartReference) {}

    override def partDeactivated(partRef: IWorkbenchPartReference) {}

    override def partOpened(partRef: IWorkbenchPartReference) {}

  }

}
