package org.ai4fm.proofprocess.ui.features

import org.ai4fm.proofprocess.ProofElem
import org.ai4fm.proofprocess.ProofFeature
import org.ai4fm.proofprocess.ProofProcessPackage.{Literals => PPLiterals}
import org.ai4fm.proofprocess.ProofStore
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.error
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.log
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.plugin
import org.ai4fm.proofprocess.ui.util.SWTUtil._
import org.eclipse.core.databinding.DataBindingContext
import org.eclipse.core.databinding.observable.list.IObservableList
import org.eclipse.core.databinding.observable.value.IValueChangeListener
import org.eclipse.core.databinding.observable.value.ValueChangeEvent
import org.eclipse.emf.databinding.EMFObservables
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.jface.databinding.swt.WidgetProperties
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider
import org.eclipse.jface.dialogs.StatusDialog
import org.eclipse.jface.layout.GridDataFactory
import org.eclipse.jface.layout.GridLayoutFactory
import org.eclipse.jface.resource.JFaceResources
import org.eclipse.jface.resource.LocalResourceManager
import org.eclipse.jface.viewers.DoubleClickEvent
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.jface.window.Window
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Shell
import org.eclipse.ui.forms.events.ExpansionAdapter
import org.eclipse.ui.forms.events.ExpansionEvent
import org.eclipse.ui.forms.events.HyperlinkAdapter
import org.eclipse.ui.forms.events.HyperlinkEvent
import org.eclipse.ui.forms.widgets.ExpandableComposite.EXPANDED
import org.eclipse.ui.forms.widgets.ExpandableComposite.NO_TITLE_FOCUS_BOX
import org.eclipse.ui.forms.widgets.ExpandableComposite.TITLE_BAR
import org.eclipse.ui.forms.widgets.ExpandableComposite.TWISTIE
import org.eclipse.ui.forms.widgets.FormToolkit
import org.eclipse.ui.forms.widgets.Section


/**
 * A dialog to display proof feature information.
 *
 * @author Andrius Velykis
 */
class FeatureInfoDialog(parent: Shell,
                        proofStore: => Option[ProofStore],
                        elem: ProofElem,
                        feature: ProofFeature,
                        callback: Option[Int => Unit] = None)
    extends StatusDialog(parent) {

  private val adapterFactory =
    new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)

  private val labelProvider = new AdapterFactoryLabelProvider(adapterFactory)

  val databindingContext = new DataBindingContext

  val featureObservable = EMFObservables.observeValue(feature, PPLiterals.PROOF_FEATURE__NAME)
  val paramsObservable = EMFObservables.observeList(feature, PPLiterals.PROOF_FEATURE__PARAMS)
  val miscObservable = EMFObservables.observeValue(feature, PPLiterals.PROOF_FEATURE__MISC)

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

    createSection("Additional comments", None, !feature.getMisc.isEmpty) { parent =>
      val container = toolkit.createComposite(parent, SWT.WRAP)
      container.setLayout(GridLayoutFactory.swtDefaults.create)

      val miscCommentsField = toolkit.createText(container, "", SWT.MULTI | SWT.WRAP | SWT.V_SCROLL)
      miscCommentsField.setLayoutData(fillBoth.hint(100, 30).create)

      databindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(miscCommentsField),
                                   miscObservable)

      toolkit.paintBordersFor(container)

      container
    }

    form
  }

  private def createFeatureInfo(toolkit: FormToolkit, parent: Composite): Control = {
    
    val container = toolkit.createComposite(parent, SWT.WRAP)
    container.setLayout(GridLayoutFactory.fillDefaults.numColumns(2).create)

    toolkit.createLabel(container, "Feature: ")
    val featureLink = toolkit.createImageHyperlink(container, SWT.NONE)
    featureLink.setLayoutData(fillHorizontal.create)
    featureLink.addHyperlinkListener(new HyperlinkAdapter {
      override def linkActivated(e: HyperlinkEvent) = selectFeature()
    })

    // placeholder to take space
    toolkit.createComposite(container)

    val featureDescField = toolkit.createText(container, "", SWT.MULTI | SWT.WRAP | SWT.V_SCROLL)
    featureDescField.setLayoutData(fillHorizontal.hint(100, 30).create)
    featureDescField.setEditable(false)
    featureDescField.setEnabled(false)

    def updateFeatureLink() = Option(feature.getName) match {

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

    featureObservable.addValueChangeListener(new IValueChangeListener {
      override def handleValueChange(event: ValueChangeEvent) {
        updateFeatureLink()
      }
    })
    updateFeatureLink()

    toolkit.paintBordersFor(container)
    container
  }


  private def selectFeature() = proofStore match {
    case Some(proofStore) => {
      val featureDialog = new FeatureDefSelectionDialog(getShell, proofStore)
      featureDialog.selectedFeature = Option(feature.getName)

      if (featureDialog.open() == Window.OK) {
        val featureName = featureDialog.selectedFeatureName

        // locate the intent (or create a new one with the given name)
        val featureDef = featureName map (PProcessUtil.getProofFeatureDef(proofStore, _))
        feature.setName(featureDef.orNull)
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

    val stringButton = toolkit.createButton(buttons, "Custom...", SWT.PUSH)
    stringButton.setLayoutData(fillHorizontal.create)

    stringButton.addSelectionListener { () =>
      val dialog = new StringTermDialog(stringButton.getShell, elem, feature)
      dialog.open()
    }

    container
  }


  private def fillBoth: GridDataFactory = GridDataFactory.fillDefaults.grab(true, true)

  private def fillHorizontal: GridDataFactory = GridDataFactory.fillDefaults.grab(true, false)

  override def close(): Boolean = {

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

  private def dispose() {
    featureObservable.dispose()
    paramsObservable.dispose()
    miscObservable.dispose()
    databindingContext.dispose()
    adapterFactory.dispose()
  }

  override def getInitialSize(): Point =
    defaultInitialDialogSize(getDialogBoundsSettings,
      super.getInitialSize(), new Point(400, 400))

}
