package org.ai4fm.proofprocess.ui.features

import org.ai4fm.proofprocess.{ProofFeature, ProofStore}
import org.ai4fm.proofprocess.ProofProcessPackage.{Literals => PPLiterals}
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.{error, log, plugin}
import org.ai4fm.proofprocess.ui.util.SWTUtil.{fnToDoubleClickListener, fnToModifyListener, noArgFnToSelectionAdapter, selectionElement}

import org.eclipse.core.databinding.observable.list.IObservableList
import org.eclipse.emf.databinding.EMFObservables
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider
import org.eclipse.jface.dialogs.StatusDialog
import org.eclipse.jface.layout.{GridDataFactory, GridLayoutFactory}
import org.eclipse.jface.resource.{JFaceResources, LocalResourceManager}
import org.eclipse.jface.viewers.{DoubleClickEvent, TableViewer}
import org.eclipse.jface.window.Window
import org.eclipse.swt.SWT
import org.eclipse.swt.events.ModifyEvent
import org.eclipse.swt.widgets.{Composite, Control, Shell, Text}
import org.eclipse.ui.forms.events.{ExpansionAdapter, ExpansionEvent, HyperlinkAdapter, HyperlinkEvent}
import org.eclipse.ui.forms.widgets.{FormToolkit, ImageHyperlink, Section}
import org.eclipse.ui.forms.widgets.ExpandableComposite.{EXPANDED, NO_TITLE_FOCUS_BOX, TITLE_BAR, TWISTIE}


/**
 * A dialog to display proof feature information.
 *
 * @author Andrius Velykis
 */
class FeatureInfoDialog(parent: Shell,
                        proofStore: => Option[ProofStore],
                        feature: ProofFeature,
                        callback: Option[Int => Unit] = None)
    extends StatusDialog(parent) {

  private val adapterFactory =
    new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)

  private val labelProvider = new AdapterFactoryLabelProvider(adapterFactory)

  private var featureLink: ImageHyperlink = _
  private var featureDescField: Text = _

  private var miscCommentsField: Text = _
  private var miscCommentsChanged = false

  val paramsObservable = EMFObservables.observeList(feature, PPLiterals.PROOF_FEATURE__PARAMS)

  setTitle("Proof Feature")
  // non-modal dialog
  setShellStyle(SWT.DIALOG_TRIM | SWT.MAX | SWT.RESIZE | SWT.MODELESS | Window.getDefaultOrientation)
  setBlockOnOpen(false)

  override def getDialogBoundsSettings = plugin.dialogSettings("FeatureInfoDialog")


  /**
   * Create the dialog area.
   *
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(Composite)
   */
  override def createDialogArea(parent: Composite): Control = {
    
    val toolkit = new FormToolkit(parent.getDisplay)
    val form = toolkit.createScrolledForm(parent)
    form.setLayoutData(GridDataFactory.fillDefaults.grab(true, true).create)
    form.setText("Proof feature information")
    toolkit.decorateFormHeading(form.getForm)

    val resourceManager = new LocalResourceManager(JFaceResources.getResources, form)
    
    form.getBody.setLayout(GridLayoutFactory.swtDefaults.create)

    val infoMsg = "Select a proof feature to mark for this proof step. " +
      "Add feature parameters from the previous dialog."
    toolkit.createLabel(form.getBody, infoMsg, SWT.WRAP)

    val featureInfoControl = createFeatureInfo(toolkit, form.getBody)
    featureInfoControl.setLayoutData(fillHorizontal.create)

    toolkit.createLabel(form.getBody, 
        "Parameter terms (select them in Mark Features dialog): ", SWT.WRAP)
    val paramTermsControl = createTermList(toolkit, form.getBody, paramsObservable)
    paramTermsControl.setLayoutData(fillBoth.hint(100, 50).create)

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

    val miscComments = feature.getMisc()
    createSection("Additional comments", None, !miscComments.isEmpty) { parent =>
      val container = toolkit.createComposite(parent, SWT.WRAP)
      container.setLayout(GridLayoutFactory.swtDefaults.create)

      miscCommentsField = toolkit.createText(container, miscComments, 
          SWT.MULTI | SWT.WRAP | SWT.V_SCROLL)
      miscCommentsField.setLayoutData(fillBoth.hint(100, 30).create)

      // record if misc field has been changed, and set the value during save
      miscCommentsField.addModifyListener { _: ModifyEvent => miscCommentsChanged = true }

      toolkit.paintBordersFor(container)

      container
    }

    form
  }

  private def createFeatureInfo(toolkit: FormToolkit, parent: Composite): Control = {
    
    val container = toolkit.createComposite(parent, SWT.WRAP)
    container.setLayout(GridLayoutFactory.fillDefaults.numColumns(2).create)

    toolkit.createLabel(container, "Feature: ")
    featureLink = toolkit.createImageHyperlink(container, SWT.NONE)
    featureLink.setLayoutData(fillHorizontal.create)
    featureLink.addHyperlinkListener(new HyperlinkAdapter {
      override def linkActivated(e: HyperlinkEvent) = selectFeature()
    })

    // placeholder to take space
    toolkit.createComposite(container)

    featureDescField = toolkit.createText(container, "", SWT.MULTI | SWT.WRAP | SWT.V_SCROLL)
    featureDescField.setLayoutData(fillHorizontal.hint(100, 30).create)
    featureDescField.setEditable(false)

    updateFeatureLink()

    toolkit.paintBordersFor(container)
    container
  }

  private def updateFeatureLink() = Option(feature.getName) match {

    case None => {
      featureLink.setText("(not set)")
      featureDescField.setText("")
    }

    case Some(featureDef) => {
      featureLink.setImage(labelProvider.getImage(featureDef))
      featureLink.setText(labelProvider.getText(featureDef))
      featureDescField.setText(featureDef.getDescription)
    }
  }

  private def selectFeature() = proofStore match {
    case Some(proofStore) => {
      val featureDialog = new FeatureDefSelectionDialog(featureLink.getShell, proofStore)
      featureDialog.selectedFeature = Option(feature.getName)

      if (featureDialog.open() == Window.OK) {
        val featureName = featureDialog.selectedFeatureName

        // locate the intent (or create a new one with the given name)
        val featureDef = featureName map (PProcessUtil.getProofFeatureDef(proofStore, _))
        feature.setName(featureDef.orNull)

        updateFeatureLink()
      }
    }

    case None => log(error(msg = Some("Proof store is not available")))
  }


  private def createTermList(toolkit: FormToolkit,
                             parent: Composite,
                             terms: IObservableList): Control = {

    val container = toolkit.createComposite(parent, SWT.NONE)
    container.setLayout(GridLayoutFactory.fillDefaults.numColumns(2).create)

    val table = toolkit.createTable(container, SWT.V_SCROLL | SWT.H_SCROLL)
    table.setLayoutData(fillBoth.hint(100, 50).create)

    val viewer = new TableViewer(table)
    viewer.setContentProvider(new ObservableListContentProvider)
    viewer.setLabelProvider(labelProvider)

    viewer.addDoubleClickListener { e: DoubleClickEvent =>
      selectionElement(e.getSelection) match {
//        case Some(t: Term) => showTerm(t)
        case _ => // ignore
      }
    }

    viewer.setInput(terms)

    val buttons = toolkit.createComposite(container, SWT.NONE)
    buttons.setLayout(GridLayoutFactory.fillDefaults.create)
    buttons.setLayoutData(GridDataFactory.fillDefaults.hint(70, SWT.DEFAULT).create)

    val removeButton = toolkit.createButton(buttons, "Remove", SWT.PUSH)
    removeButton.setLayoutData(fillHorizontal.create)

    removeButton.addSelectionListener { () =>
      selectionElement(viewer.getSelection) match {
        case Some(elem) => terms.remove(elem)
        case _ =>
      }
    }

    container
  }


  private def fillBoth: GridDataFactory = GridDataFactory.fillDefaults.grab(true, true)

  private def fillHorizontal: GridDataFactory = GridDataFactory.fillDefaults.grab(true, false)

  override def close(): Boolean = {

    if (getReturnCode == Window.OK) {
      saveChanges()
    }

    val result = super.close()
    if (result) {
      dispose()
    }

    callback match {
      case Some(f) => f(getReturnCode)
      case None => // ignore
    }

    result
  }

  private def saveChanges() = {
    if (miscCommentsChanged) {
      feature.setMisc(miscCommentsField.getText)
    }
  }

  private def dispose() {
    paramsObservable.dispose()
    adapterFactory.dispose()
  }

}
