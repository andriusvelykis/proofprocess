package org.ai4fm.proofprocess.ui.features

import scala.collection.JavaConverters._

import org.ai4fm.proofprocess.{ProofElem, ProofEntry, ProofStep, ProofStore, Term}
import org.ai4fm.proofprocess.core.store.ProofElemComposition
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.ui.{TermSelectionSource, TermSelectionSourceProvider}
import org.ai4fm.proofprocess.ui.internal.PProcessImages
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.{error, log, plugin}
import org.ai4fm.proofprocess.ui.prefs.PProcessUIPreferences
import org.ai4fm.proofprocess.ui.util.{AdaptingLabelProvider, AdaptingTableLabelProvider}
import org.ai4fm.proofprocess.ui.util.SWTUtil.{fnToDoubleClickListener, fnToModifyListener, selectionElement}
import org.ai4fm.proofprocess.ui.util.ScalaArrayContentProvider

import org.eclipse.emf.cdo.util.CommitException
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.jface.action.Action
import org.eclipse.jface.dialogs.StatusDialog
import org.eclipse.jface.layout.{GridDataFactory, GridLayoutFactory}
import org.eclipse.jface.resource.{JFaceResources, LocalResourceManager}
import org.eclipse.jface.viewers.{DoubleClickEvent, ILabelProvider, ITableLabelProvider, StyledString, TableViewer}
import org.eclipse.jface.window.Window
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.events.ModifyEvent
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.{Composite, Control, Shell, Text}
import org.eclipse.ui.forms.events.{ExpansionAdapter, ExpansionEvent, HyperlinkAdapter, HyperlinkEvent}
import org.eclipse.ui.forms.widgets.{FormToolkit, ImageHyperlink, Section}
import org.eclipse.ui.forms.widgets.ExpandableComposite.{EXPANDED, NO_TITLE_FOCUS_BOX, TITLE_BAR, TWISTIE}


/**
 * A dialog to mark various proof process features.
 * 
 * @author Andrius Velykis
 */
class MarkFeaturesDialog(parent: Shell, elem: ProofElem) extends StatusDialog(parent) {

  private val adapterFactory =
    new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)

  private val labelProvider = new AdapterFactoryLabelProvider(adapterFactory)

  lazy val inGoals = ProofElemComposition.composeInGoals(elem)
  lazy val outGoals = ProofElemComposition.composeOutGoals(elem)

  // set a savepoint to rollback on "cancel"
  val transaction = PProcessUtil.cdoTransaction(elem)
  val editSavePoint = transaction map (_.setSavepoint())

  var intentLink: ImageHyperlink = _
  var narrativeField: Text = _
  var narrativeChanged = false

  var inGoalDisplay: StyledText = _
  var inGoalsTable: TableViewer = _
  var outGoalDisplay: Option[StyledText] = None
  var outGoalsTable: Option[TableViewer] = None

  lazy val (inGoalFiltered, outGoalFiltered) = createFilteredGoals()

  var filterAffectedGoal: Boolean = _
  

  setTitle("Mark Features")
  setShellStyle(getShellStyle() | SWT.MAX | SWT.RESIZE)

  override def getDialogBoundsSettings = plugin.dialogSettings("MarkFeaturesDialog")

  /**
   * Create the dialog area.
   *
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(Composite)
   */
  override def createDialogArea(parent: Composite): Control = {
    
    val toolkit = new FormToolkit(parent.getDisplay)
    val form = toolkit.createScrolledForm(parent)
    form.setLayoutData(GridDataFactory.fillDefaults.grab(true, true).create)
    form.setText("Mark proof process features")
    val toolbar = form.getToolBarManager
    toolkit.decorateFormHeading(form.getForm)

    toolbar.add(new FilterAffectedGoal)
    toolbar.update(true)

    val resourceManager = new LocalResourceManager(JFaceResources.getResources, form)
    
    form.getBody.setLayout(GridLayoutFactory.swtDefaults.create)

    toolkit.createLabel(form.getBody, "Some more information", SWT.NONE)

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

    createSection("Intent && features", None, true, false) { parent =>
      createWhyInfo(toolkit, parent)
    }

    createSection("Before step", Some("The state before step.")) { parent =>
      val container = toolkit.createComposite(parent, SWT.WRAP)
      container.setLayout(GridLayoutFactory.swtDefaults.create)

      inGoalDisplay = createTermDisplay(toolkit, container)

      inGoalsTable = createTermList(toolkit, container, inGoals.size > 1)
      
      toolkit.paintBordersFor(container)

      container
    }

    createSection("Proof step", Some("Information about the proof step.")) { parent => 
      createStepInfo(toolkit, parent)
    }

    createSection("After step", Some("Step results.")) { parent =>
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

        outGoalsTable = Some(createTermList(toolkit, container, outGoals.size > 1))

        toolkit.paintBordersFor(container)
        container
      }
    }

    showTerms()
    form
  }


  private def createWhyInfo(toolkit: FormToolkit, parent: Composite): Control = {
    
    val container = toolkit.createComposite(parent, SWT.WRAP)
    container.setLayout(GridLayoutFactory.swtDefaults.numColumns(2).create)

    toolkit.createLabel(container, "Intent: ")
    intentLink = toolkit.createImageHyperlink(container, SWT.NONE)
    intentLink.setLayoutData(fillHorizontal.create)
    intentLink.addHyperlinkListener(new HyperlinkAdapter {
      override def linkActivated(e: HyperlinkEvent) = selectIntent()
    })

    updateIntentLink()

    val narr = elem.getInfo.getNarrative
    narrativeField = createTextField(toolkit, container, "Narrative:", narr)
    // record if narrative field has been changed, and set the value during save
    narrativeField.addModifyListener { _: ModifyEvent => narrativeChanged = true }


    val featuresLabel = toolkit.createLabel(container, "Features:")
    featuresLabel.setLayoutData(GridDataFactory.swtDefaults.align(SWT.BEGINNING, SWT.BEGINNING).create)
    val featuresTable = toolkit.createTable(container, SWT.V_SCROLL)
    featuresTable.setLayoutData(fillBoth.hint(100, 50).create)

    val viewer = new TableViewer(featuresTable)
    viewer.setContentProvider(ScalaArrayContentProvider)
    viewer.setLabelProvider(labelProvider)

    val allFeatures = elem.getInfo.getInFeatures.asScala ++ elem.getInfo.getOutFeatures.asScala
    viewer.setInput(allFeatures)

    toolkit.paintBordersFor(container)
    container
  }


  private def createStepInfo(toolkit: FormToolkit, parent: Composite): Control = {

    val container = toolkit.createComposite(parent, SWT.WRAP)
    container.setLayout(GridLayoutFactory.swtDefaults.numColumns(2).create)
    
    elem match {
      case entry: ProofEntry => {

        val source = entry.getProofStep.getSource
        toolkit.createLabel(container, "Source: ")
        createLabelWithImage(toolkit, container,
          labelProvider.getText(source), labelProvider.getImage(source))
      }

      case _ => // ignore
    }

    container
  }

  private def updateIntentLink() = Option(elem.getInfo.getIntent) match {

    case None => intentLink.setText("(not set)")

    case Some(intent) => {
      intentLink.setImage(labelProvider.getImage(intent))
      intentLink.setText(labelProvider.getText(intent))
    }
  }

  private def selectIntent() = proofStore match {
    case Some(proofStore) => {
      val intentDialog = IntentSelectionDialog(intentLink.getShell, proofStore)
      // TODO use temporary value somewhere?
      intentDialog.selectedIntent = Option(elem.getInfo.getIntent)

      if (intentDialog.open() == Window.OK) {
        val intentName = intentDialog.selectedIntentName

        // locate the intent (or create a new one with the given name)
        val intent = intentName map (PProcessUtil.getIntent(proofStore, _))
        elem.getInfo.setIntent(intent.orNull)

        updateIntentLink()
      }
    }

    case None => log(error(msg = Some("Proof store is not available")))
  }

  private def proofStore: Option[ProofStore] = PProcessUtil.findProofStore(elem)

  private def createTermDisplay(toolkit: FormToolkit, parent: Composite): StyledText = {

    val termDisplayField = new StyledText(parent,
      SWT.BORDER | SWT.MULTI | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL)
    termDisplayField.setLayoutData(fillBoth.hint(100, 40).create)

    toolkit.adapt(termDisplayField)

    termDisplayField
  }

  private def showTermDisplay(displayField: StyledText, term: Option[(Term, ProofEntry)]) = {
    val rendered = term flatMap Function.tupled(termSelectionSource) map (_.rendered)
    displayField.setStyledText(rendered)
  }

  private def showTerms() = if (filterAffectedGoal) {
    showTermDisplay(inGoalDisplay, inGoalFiltered)
    inGoalsTable.setInput(replaceFirst(inGoalFiltered, inGoals))

    outGoalDisplay foreach (showTermDisplay(_, outGoalFiltered))
    outGoalsTable foreach (_.setInput(replaceFirst(outGoalFiltered, outGoals)))
  } else {
    showTermDisplay(inGoalDisplay, inGoals.headOption)
    inGoalsTable.setInput(inGoals)

    outGoalDisplay foreach (showTermDisplay(_, outGoals.headOption))
    outGoalsTable foreach (_.setInput(outGoals))
  }

  private def replaceFirst[A](elem: Option[A], list: Seq[A]): Seq[A] =
    if (list.isEmpty) elem.toList else elem.toList ++ list.tail


  private def createFilteredGoals(): (Option[(Term, ProofEntry)], Option[(Term, ProofEntry)]) =
    (inGoals.headOption, outGoals.headOption) match {

      case (Some((in, inEntry)), Some((out, outEntry))) =>
        termSelectionSource(in, inEntry) match {

          case Some(inSource) => {
            val (in1, out1) = inSource.diff(out)
            (Some(in1, inEntry), Some(out1, outEntry))
          }

          // unsupported
          case _ => (Some((in, inEntry)), Some((out, outEntry)))
        }

      case (i, o) => (i, o)
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
                             multi: Boolean): TableViewer = {

    val table = toolkit.createTable(parent, SWT.V_SCROLL | SWT.H_SCROLL)
    val hhint = 100
    val layoutData = if (multi) fillBoth.hint(hhint, 20)
                     else fillHorizontal.hint(hhint, SWT.DEFAULT)

    table.setLayoutData(layoutData.create)

    val viewer = new TableViewer(table)
    viewer.setContentProvider(ScalaArrayContentProvider)
    viewer.setLabelProvider(new FirstElemLabelProvider(labelProvider))

    viewer.addDoubleClickListener { e: DoubleClickEvent =>
      selectionElement(e.getSelection) match {
        case Some((t: Term, source: ProofEntry)) => selectSubTerm(t, source.getProofStep)
        case _ => // ignore
      }
    }

    viewer
  }


  private def selectSubTerm(term: Term, context: ProofStep): Term = {
    
    val subTermDialog = new SubTermSelectionDialog(intentLink.getShell, term, context)
    subTermDialog.open()

    // FIXME
    null
  }


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
    
    if (narrativeChanged) {
      elem.getInfo.setNarrative(narrativeField.getText)
    }
    
    try {
      transaction foreach { _.commit() }
    } catch {
      case ex: CommitException => log(error(Some(ex)))
    }
  }

  private def discardChanges() {
    editSavePoint foreach { _.rollback() }
  }

  private def dispose() {
    adapterFactory.dispose
  }


  private class FilterAffectedGoal extends Action("Filter Affected Goal", SWT.TOGGLE) {

    setToolTipText("Filter Affected Goal")
//    setDescription("?")
    setImageDescriptor(PProcessImages.FILTER)

    {
      val prefRawTree = PProcessUIPreferences.getBoolean(prefKey, false)
      setFilterAffectedGoal(prefRawTree, true)
    }

    def prefKey = PProcessUIPreferences.FILTER_AFFECTED_GOAL

    override def run() {
      setFilterAffectedGoal(!filterAffectedGoal)
      showTerms()
    }

    def setFilterAffectedGoal(doFilter: Boolean, init: Boolean = false) {
      filterAffectedGoal = doFilter
      setChecked(doFilter)

      PProcessUIPreferences.prefs.putBoolean(prefKey, doFilter)
    }
  }

  private val fst: PartialFunction[Any, Any] = { case (first, _) => first }

  private class FirstElemLabelProvider(base: ILabelProvider with ITableLabelProvider)
      extends AdaptingLabelProvider(base)(fst) with AdaptingTableLabelProvider

}
