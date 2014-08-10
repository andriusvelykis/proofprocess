package org.ai4fm.proofprocess.ui.features

import org.ai4fm.proofprocess.ProofElem
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ProofFeature
import org.ai4fm.proofprocess.ProofProcessFactory
import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.core.store.ProofElemComposition
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.ui.TermSelectionSource
import org.ai4fm.proofprocess.ui.TermSelectionSourceProvider
import org.ai4fm.proofprocess.ui.internal.PProcessImages
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.plugin
import org.ai4fm.proofprocess.ui.util.AdaptingLabelProvider
import org.ai4fm.proofprocess.ui.util.AdaptingTableLabelProvider
import org.ai4fm.proofprocess.ui.util.SWTUtil.defaultInitialDialogSize
import org.ai4fm.proofprocess.ui.util.SWTUtil.fnToSelectionChangedListener
import org.ai4fm.proofprocess.ui.util.SWTUtil.selectionElement
import org.ai4fm.proofprocess.ui.util.ScalaArrayContentProvider
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.jface.dialogs.StatusDialog
import org.eclipse.jface.layout.GridDataFactory
import org.eclipse.jface.layout.GridLayoutFactory
import org.eclipse.jface.resource.JFaceResources
import org.eclipse.jface.resource.LocalResourceManager
import org.eclipse.jface.viewers.ILabelProvider
import org.eclipse.jface.viewers.ITableLabelProvider
import org.eclipse.jface.viewers.SelectionChangedEvent
import org.eclipse.jface.viewers.StyledString
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.jface.window.Window
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Text
import org.eclipse.ui.forms.events.ExpansionAdapter
import org.eclipse.ui.forms.events.ExpansionEvent
import org.eclipse.ui.forms.widgets.ExpandableComposite.EXPANDED
import org.eclipse.ui.forms.widgets.ExpandableComposite.NO_TITLE_FOCUS_BOX
import org.eclipse.ui.forms.widgets.ExpandableComposite.TITLE_BAR
import org.eclipse.ui.forms.widgets.ExpandableComposite.TWISTIE
import org.eclipse.ui.forms.widgets.FormToolkit
import org.eclipse.ui.forms.widgets.Section
import org.eclipse.swt.widgets.Button

/**
 * A dialog to create a StringTerm (custom term).
 *
 * @author Andrius Velykis
 */
class StringTermDialog(parent: Shell,
                       elem: ProofElem,
                       feature: ProofFeature) extends StatusDialog(parent) {

  private val adapterFactory =
    new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)

  private val labelProvider = new AdapterFactoryLabelProvider(adapterFactory)

  private lazy val ppFactory = ProofProcessFactory.eINSTANCE

  private lazy val inGoals = ProofElemComposition.composeInGoals(elem)
  private lazy val outGoals = ProofElemComposition.composeOutGoals(elem)

  private var stringTermDisplay: StyledText = _
  private var isOutTermCheck: Button = _
  
  private var inGoalDisplay: StyledText = _
  private var inGoalsTable: TableViewer = _
  private var outGoalDisplay: Option[StyledText] = None
  private var outGoalsTable: Option[TableViewer] = None
  
  setTitle("Create Custom Term")
  setShellStyle(getShellStyle() | SWT.MAX | SWT.RESIZE)

  override def getDialogBoundsSettings = plugin.dialogSettings("StringTermDialog")

  /**
   * Create the dialog area.
   *
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(Composite)
   */
  override def createDialogArea(parent: Composite): Control = {
    
    val toolkit = new FormToolkit(parent.getDisplay)
    val form = toolkit.createScrolledForm(parent)
    form.setLayoutData(GridDataFactory.fillDefaults.grab(true, true).create)
    form.setText("Enter custom string term")
    val toolbar = form.getToolBarManager
    toolkit.decorateFormHeading(form.getForm)

    val resourceManager = new LocalResourceManager(JFaceResources.getResources, form)
    
    form.getBody.setLayout(GridLayoutFactory.swtDefaults.create)

    val sectionListener = new ExpansionAdapter {
      override def expansionStateChanged(e: ExpansionEvent) = form.reflow(true)
    }

    def createSection(text: String,
                      desc: Option[String],
                      expanded: Boolean = false,
                      grabSpace: Boolean = true)(
                        contents: Composite => Control) {

      val sectFlags = TITLE_BAR | NO_TITLE_FOCUS_BOX | TWISTIE |
        (if (expanded) EXPANDED else 0) |
        (if (desc.isDefined) Section.DESCRIPTION else 0)
      
      val section = toolkit.createSection(form.getBody, sectFlags)
      section.setText(text)
      desc foreach (section.setDescription)

      val layoutData = GridDataFactory.fillDefaults.grab(true, false).create
      section.setLayoutData(layoutData)

      if (grabSpace) {
        layoutData.grabExcessVerticalSpace = expanded

        // toggle grabbing vertical space when expanded/collapsed
        section.addExpansionListener(new ExpansionAdapter {
          override def expansionStateChanged(e: ExpansionEvent) =
            // do not grab vertical space if collapsed
            layoutData.grabExcessVerticalSpace = e.getState
        })
        
        section.addExpansionListener(sectionListener)
      }
      
      val control = contents(section)
      control.setLayoutData(fillBoth.create)
      section.setClient(control)
    }

    createSection("String term", None, true, false) { parent =>
      val container = toolkit.createComposite(parent, SWT.WRAP)
      container.setLayout(GridLayoutFactory.swtDefaults.create)

      stringTermDisplay = createTermDisplay(toolkit, container, true)

      isOutTermCheck = toolkit.createButton(container, "Out term", SWT.CHECK)

      toolkit.paintBordersFor(container)

      container
    }

    createSection("Copy from before state", Some("The state before step.")) { parent =>
      val container = toolkit.createComposite(parent, SWT.WRAP)
      container.setLayout(GridLayoutFactory.swtDefaults.create)

      inGoalDisplay = createTermDisplay(toolkit, container)

      inGoalsTable = createTermList(toolkit, container, inGoals.size > 1, true)
      
      toolkit.paintBordersFor(container)

      container
    }

    createSection("Copy from after state", Some("Step results.")) { parent =>
      if (outGoals.isEmpty) {

        val container = toolkit.createComposite(parent, SWT.WRAP)
        container.setLayout(GridLayoutFactory.swtDefaults.numColumns(2).create)

        // no output goals - all proved
        toolkit.createLabel(container, "Results: ")
        val allProvedLabel = createLabelWithImage(
          toolkit, container, "All proved!",
          resourceManager.createImageWithDefault(PProcessImages.SUCCESS))

        container

      } else {

        val container = toolkit.createComposite(parent, SWT.WRAP)
        container.setLayout(GridLayoutFactory.swtDefaults.create)

        outGoalDisplay = Some(createTermDisplay(toolkit, container))

        val resultsLabel = toolkit.createLabel(container, "Results: ")
        resultsLabel.setLayoutData(GridDataFactory.swtDefaults.create)

        outGoalsTable = Some(createTermList(toolkit, container, outGoals.size > 1, false))

        toolkit.paintBordersFor(container)
        container
      }
    }

    showTerms()
    form
  }

  private def createTermDisplay(toolkit: FormToolkit,
                                parent: Composite,
                                editable: Boolean = false): StyledText = {

    val style = SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
    val fullStyle = if (!editable) style | SWT.READ_ONLY else style
    val termDisplayField = new StyledText(parent, fullStyle)
    termDisplayField.setLayoutData(fillBoth.hint(100, 40).create)

    toolkit.adapt(termDisplayField)

    termDisplayField
  }

  private def showTermDisplay(displayField: StyledText, term: Option[(Term, ProofEntry)]) = {
    val rendered = term flatMap Function.tupled(termSelectionSource) map (_.rendered)
    displayField.setStyledText(rendered)
  }

  private def showTerms() = {
    inGoalsTable.setInput(inGoals)
    outGoalsTable foreach (_.setInput(outGoals))

    updateTermDisplays()
  }

  private def updateTermDisplays() {
    updateTermDisplay(inGoalsTable, inGoalDisplay)
    (outGoalDisplay, outGoalsTable) match {
      case (Some(d), Some(t)) => updateTermDisplay(t, d)
      case _ => // not available
    }
  }

  private def updateTermDisplay(termList: TableViewer, displayField: StyledText) {
    val selected = selectionElement(termList.getSelection)
    val selectedOrFirstObj = selected orElse {
      if (termList.getTable.getItemCount > 0) Option(termList.getElementAt(0)) else None
    }

    val selectedOrFirst = selectedOrFirstObj flatMap {
      _ match {
        case (t: Term, e: ProofEntry) => Some(t, e)
        case _ => None
      }
    }

    showTermDisplay(displayField, selectedOrFirst)
  }


  /**
   * Implicit wrapper for StyledText to support StyledString
   */
  private implicit class FancyStyledText(textField: StyledText) {
    def setStyledText(text: Option[StyledString]) = text match {
      case Some(text) => {
        textField.setText(text.getString)
        textField.setStyleRanges(text.getStyleRanges)
      }

      case None => textField.setText("")
    }
  }

  private def termSelectionSource(t: Term,
                                  sourceEntry: ProofEntry): Option[TermSelectionSource] = {
    val termSourceProvider =
      PProcessUtil.getAdapter(t, classOf[TermSelectionSourceProvider], true)
    val context = sourceEntry.getProofStep
    val termSource = termSourceProvider map (_.getTermSource(context))
    termSource
  }

  private def createTermList(toolkit: FormToolkit,
                             parent: Composite,
                             multi: Boolean,
                             inGoals: Boolean): TableViewer = {

    val table = toolkit.createTable(parent, SWT.V_SCROLL | SWT.H_SCROLL)
    val hhint = 100
    val layoutData = if (multi) fillBoth.hint(hhint, 20)
                     else fillHorizontal.hint(hhint, SWT.DEFAULT)

    table.setLayoutData(layoutData.create)

    val viewer = new TableViewer(table)
    viewer.setContentProvider(ScalaArrayContentProvider)
    viewer.setLabelProvider(new FirstElemLabelProvider(labelProvider))

    viewer.addSelectionChangedListener { e: SelectionChangedEvent => updateTermDisplays() }

    viewer
  }

  // TODO reuse
  private def addFeatureParam(feature: ProofFeature, param: Term, isInTerm: Boolean) {
    // out feature if it is already out and out-param is added; or if it is the first out-param
    val isOutFeature = elem.getInfo.getOutFeatures.contains(feature)
    val isOutTerm = !isInTerm && (isOutFeature || feature.getParams.isEmpty)

    feature.getParams.add(cloneTerm(param))
    
    if (isOutTerm != isOutFeature) {
      // move to the correct feature list - just swap the lists
      val featureLists = (elem.getInfo.getInFeatures, elem.getInfo.getOutFeatures)
      val (add, remove) = if (isOutTerm) featureLists.swap else featureLists
      remove.remove(feature)
      add.add(feature)
    }
    
  }

  // TODO review and generalise!
  private def cloneTerm(term: Term): Term = EcoreUtil.copy(term)


  private def createTextField(toolkit: FormToolkit,
                              parent: Composite,
                              label: String,
                              initText: String): Text = {

    val l = toolkit.createLabel(parent, label)
    l.setLayoutData(GridDataFactory.swtDefaults.align(SWT.BEGINNING, SWT.BEGINNING).create)

    val t = toolkit.createText(parent, initText, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL)

    val lineHeight = t.getLineHeight + 2 // add a bit, otherwise it is too small
    val minHeight = 30 max (2 * lineHeight)

    val lines = initText.split("\\r?\\n").length
    val height = lines * lineHeight

    t.setLayoutData(fillHorizontal.hint(100, height max minHeight).create)

    t
  }

  private def fillBoth: GridDataFactory = GridDataFactory.fillDefaults.grab(true, true)

  private def fillHorizontal: GridDataFactory = GridDataFactory.fillDefaults.grab(true, false)

  private def createLabelWithImage(toolkit: FormToolkit,
                                   parent: Composite,
                                   text: String,
                                   image: Image): Control = {

    val composite = toolkit.createComposite(parent)
    composite.setLayout(GridLayoutFactory.swtDefaults.numColumns(2).margins(0, 0).create)

    val img = toolkit.createLabel(composite, "")
    img.setImage(image)

    toolkit.createLabel(composite, text)

    composite
  }


  override def close(): Boolean = {

    if (getReturnCode == Window.OK) {
      saveChanges()
    } else {
      discardChanges()
    }
    
    val result = super.close()
    if (result) {
      dispose()
    }

    result
  }

  private def saveChanges() = {
    val termValue = stringTermDisplay.getText.trim
    if (!termValue.isEmpty) {
      val stringTerm = ppFactory.createStringTerm
      stringTerm.setDisplay(termValue)

      val isInTerm = !isOutTermCheck.getSelection
      addFeatureParam(feature, stringTerm, isInTerm)
    }
  }

  private def discardChanges() {}

  private def dispose() {
    adapterFactory.dispose()
  }

  override def getInitialSize(): Point =
    defaultInitialDialogSize(getDialogBoundsSettings,
      super.getInitialSize(), new Point(600, 600))

  private val fst: PartialFunction[Any, Any] = { case (first, _) => first }

  private class FirstElemLabelProvider(base: ILabelProvider with ITableLabelProvider)
      extends AdaptingLabelProvider(base)(fst) with AdaptingTableLabelProvider

}
