package org.ai4fm.proofprocess.ui.features

import scala.collection.JavaConverters._

import org.ai4fm.proofprocess.{ProofElem, ProofEntry, ProofFeature, ProofProcessFactory}
import org.ai4fm.proofprocess.{ProofStep, ProofStore, Term}
import org.ai4fm.proofprocess.ProofProcessPackage.{Literals => PPLiterals}
import org.ai4fm.proofprocess.core.store.ProofElemComposition
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.ui.{TermSelectionSource, TermSelectionSourceProvider}
import org.ai4fm.proofprocess.ui.internal.PProcessImages
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.{error, log, plugin}
import org.ai4fm.proofprocess.ui.prefs.PProcessUIPreferences
import org.ai4fm.proofprocess.ui.util.{AdaptingLabelProvider, AdaptingTableLabelProvider}
import org.ai4fm.proofprocess.ui.util.SWTUtil.{fnToDoubleClickListener, fnToOpenListener, noArgFnToSelectionAdapter, selectionElement}
import org.ai4fm.proofprocess.ui.util.ScalaArrayContentProvider

import org.eclipse.core.databinding.DataBindingContext
import org.eclipse.core.databinding.observable.list.MultiList
import org.eclipse.emf.cdo.transaction.CDOSavepoint
import org.eclipse.emf.cdo.util.CommitException
import org.eclipse.emf.databinding.EMFObservables.{observeList, observeValue}
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.jface.action.Action
import org.eclipse.jface.databinding.swt.WidgetProperties
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider
import org.eclipse.jface.dialogs.StatusDialog
import org.eclipse.jface.layout.{GridDataFactory, GridLayoutFactory}
import org.eclipse.jface.resource.{FontDescriptor, JFaceResources, LocalResourceManager, ResourceManager}
import org.eclipse.jface.viewers.{DoubleClickEvent, ILabelProvider, ITableLabelProvider, OpenEvent, StyledString, TableViewer}
import org.eclipse.jface.window.Window
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.graphics.{Font, Image}
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

  private lazy val ppFactory = ProofProcessFactory.eINSTANCE

  private lazy val inGoals = ProofElemComposition.composeInGoals(elem)
  private lazy val outGoals = ProofElemComposition.composeOutGoals(elem)

  // set a savepoint to rollback on "cancel"
  private val transaction = PProcessUtil.cdoTransaction(elem)
  private val editSavePoint = transaction map (_.setSavepoint())

  private var intentLink: ImageHyperlink = _

  private var featureInfoDialog: Option[FeatureInfoDialog] = None
  private var editFeature: Option[ProofFeature] = None

  private var inGoalDisplay: StyledText = _
  private var inGoalsTable: TableViewer = _
  private var outGoalDisplay: Option[StyledText] = None
  private var outGoalsTable: Option[TableViewer] = None

  /* Databinding */
  val databindingContext = new DataBindingContext

  val narrativeObservable = observeValue(elem.getInfo, PPLiterals.PROOF_INFO__NARRATIVE)

  val inFeaturesObs = observeList(elem.getInfo, PPLiterals.PROOF_INFO__IN_FEATURES)
  val outFeaturesObs = observeList(elem.getInfo, PPLiterals.PROOF_INFO__OUT_FEATURES)
  val allFeaturesObs = new MultiList(Array(inFeaturesObs, outFeaturesObs))

  private lazy val (inGoalFiltered, outGoalFiltered) = createFilteredGoals()

  private var filterAffectedGoal: Boolean = _
  

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

    val infoMsg = "Mark the important features of this proof step. " +
    		"Proof features should indicate the important parts of in/out goals or related terms."
    toolkit.createLabel(form.getBody, infoMsg, SWT.NONE)

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
      createWhyInfo(toolkit, parent, resourceManager)
    }

    createSection("Before step", Some("The state before step.")) { parent =>
      val container = toolkit.createComposite(parent, SWT.WRAP)
      container.setLayout(GridLayoutFactory.swtDefaults.create)

      inGoalDisplay = createTermDisplay(toolkit, container)

      inGoalsTable = createTermList(toolkit, container, inGoals.size > 1, true)
      
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

        outGoalsTable = Some(createTermList(toolkit, container, outGoals.size > 1, false))

        toolkit.paintBordersFor(container)
        container
      }
    }

    showTerms()
    form
  }

  private def createWhyInfo(toolkit: FormToolkit,
                            parent: Composite,
                            resourceManager: ResourceManager): Control = {
    
    val container = toolkit.createComposite(parent, SWT.WRAP)
    container.setLayout(GridLayoutFactory.swtDefaults.numColumns(2).create)

    toolkit.createLabel(container, "Intent: ")
    intentLink = toolkit.createImageHyperlink(container, SWT.NONE)
    intentLink.setLayoutData(fillHorizontal.create)
    intentLink.addHyperlinkListener(new HyperlinkAdapter {
      override def linkActivated(e: HyperlinkEvent) = selectIntent()
    })

    updateIntentLink()

    val narrativeField = createTextField(toolkit, container, "Narrative:", "")
    databindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(narrativeField),
                                 narrativeObservable)


    val featuresLabel = toolkit.createLabel(container, "Features:")
    featuresLabel.setLayoutData(GridDataFactory.swtDefaults.align(SWT.BEGINNING, SWT.BEGINNING).create)

    val featuresTableControl = createFeaturesTable(toolkit, container, resourceManager)
    featuresTableControl.setLayoutData(fillBoth.create)
    
    toolkit.paintBordersFor(container)
    container
  }

  private def createFeaturesTable(toolkit: FormToolkit,
                                  parent: Composite,
                                  resourceManager: ResourceManager) = {

    val container = toolkit.createComposite(parent, SWT.NONE)
    container.setLayout(GridLayoutFactory.fillDefaults.numColumns(2).create)

    val table = toolkit.createTable(container, SWT.V_SCROLL)
    table.setLayoutData(fillBoth.hint(100, 50).create)

    val featuresTable = new TableViewer(table)
    featuresTable.setContentProvider(new ObservableListContentProvider)

    def bold(font: Font): Font = {
      val fontDescriptor = FontDescriptor.createFrom(font)
      val boldFont = fontDescriptor.withStyle(SWT.BOLD)
      resourceManager.createFont(boldFont)
    }

    lazy val boldFont = bold(featuresTable.getTable.getFont)

    val featureLabelProvider =
      new AdapterFactoryLabelProvider.FontProvider(adapterFactory, featuresTable) {

        override def getFont(obj: AnyRef, col: Int): Font =
          if (Some(obj) == editFeature) {
            // bold currently edited feature
            Option(super.getFont(obj, col)) map (bold) getOrElse boldFont
          } else {
            super.getFont(obj, col)
          }
          
      }

    featureLabelProvider.setFireLabelUpdateNotifications(true)
    featuresTable.setLabelProvider(featureLabelProvider)

    featuresTable.setInput(allFeaturesObs)

    // edit on open (double-click)
    featuresTable.addOpenListener { e: OpenEvent =>
      selectionElement(e.getSelection) match {
        case Some(feature: ProofFeature) => editFeature(feature)
        case _ =>
      }
    }
    

    val buttons = toolkit.createComposite(container, SWT.NONE)
    buttons.setLayout(GridLayoutFactory.fillDefaults.create)
    buttons.setLayoutData(GridDataFactory.fillDefaults.hint(70, SWT.DEFAULT).create)

    val addButton = toolkit.createButton(buttons, "Add...", SWT.PUSH)
    addButton.setLayoutData(fillHorizontal.create)

    addButton.addSelectionListener { () => addNewFeature() }

    val editButton = toolkit.createButton(buttons, "Edit...", SWT.PUSH)
    editButton.setLayoutData(fillHorizontal.create)

    editButton.addSelectionListener { () =>
      selectionElement(featuresTable.getSelection) match {
        case Some(feature: ProofFeature) => editFeature(feature)
        case _ =>
      }
    }

    val removeButton = toolkit.createButton(buttons, "Remove", SWT.PUSH)
    removeButton.setLayoutData(fillHorizontal.create)

    removeButton.addSelectionListener { () =>
      selectionElement(featuresTable.getSelection) match {
        case Some(feature: ProofFeature) => removeFeature(feature)
        case _ =>
      }
    }

    container
  }


  private def addNewFeature(inFeature: Boolean = true, initialTerms: List[Term] = Nil) {

    val addSavePoint = transaction map (_.setSavepoint())

    val newFeature = ppFactory.createProofFeature
    newFeature.getParams.addAll(initialTerms.asJava)

    // add immediately
    val featuresList = if (inFeature) elem.getInfo.getInFeatures else elem.getInfo.getOutFeatures
    featuresList.add(newFeature)

    openFeatureInfo(newFeature, addSavePoint)
  }

  private def editFeature(feature: ProofFeature) {
    val editSavePoint = transaction map (_.setSavepoint())
    openFeatureInfo(feature, editSavePoint)
  }

  private def removeFeature(feature: ProofFeature) {
    val proofInfo = elem.getInfo
    proofInfo.getInFeatures.remove(feature)
    proofInfo.getOutFeatures.remove(feature)
  }

  private def openFeatureInfo(feature: ProofFeature,
                              savePoint: Option[CDOSavepoint]) {
    featureInfoDialog match {
      case Some(openDialog) => openDialog.close()
      case None => // ignore
    }

    editFeature = Some(feature)

    val newDialog = new FeatureInfoDialog(intentLink.getShell, proofStore, feature,
      Some(stopEditFeature(savePoint)))
    featureInfoDialog = Some(newDialog)
    newDialog.open()
  }

  private def stopEditFeature(savePoint: Option[CDOSavepoint])(returnCode: Int) {
    featureInfoDialog = None
    editFeature = None

    if (returnCode != Window.OK) {
      savePoint foreach (_.rollback())
    }
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
      val intentDialog = new IntentSelectionDialog(intentLink.getShell, proofStore)
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

    viewer.addDoubleClickListener { e: DoubleClickEvent =>
      selectionElement(e.getSelection) match {
        case Some((t: Term, source: ProofEntry)) => selectSubTerm(t, source.getProofStep, inGoals)
        case _ => // ignore
      }
    }

    viewer
  }


  private def selectSubTerm(term: Term, context: ProofStep, inGoal: Boolean) {

    val editingFeature = editFeature.isDefined
    val okLabel = if (editingFeature) "Mark Feature Term" else "Create New Feature With Term"
    
    val subTermDialog =
      new SubTermSelectionDialog(intentLink.getShell, term, context, Some(okLabel))

    if (subTermDialog.open() == Window.OK) {
      val selectedTerm = subTermDialog.selectedTerm
      editFeature match {
        case Some(feature) => addFeatureParam(feature, selectedTerm, inGoal)
        case None => addNewFeature(inGoal, List(selectedTerm))
      }
    }
  }

  private def addFeatureParam(feature: ProofFeature, param: Term, isInTerm: Boolean) {
    // out feature if it is already out and out-param is added; or if it is the first out-param
    val isOutFeature = elem.getInfo.getOutFeatures.contains(feature)
    val isOutTerm = !isInTerm && (isOutFeature || feature.getParams.isEmpty)

    feature.getParams.add(param)
    
    if (isOutTerm != isOutFeature) {
      // move to the correct feature list - just swap the lists
      val featureLists = (elem.getInfo.getInFeatures, elem.getInfo.getOutFeatures)
      val (add, remove) = if (isOutTerm) featureLists.swap else featureLists
      remove.remove(feature)
      add.add(feature)
    }
    
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
    narrativeObservable.dispose()
    
    allFeaturesObs.dispose()
    inFeaturesObs.dispose()
    outFeaturesObs.dispose()

    databindingContext.dispose()

    adapterFactory.dispose()
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
