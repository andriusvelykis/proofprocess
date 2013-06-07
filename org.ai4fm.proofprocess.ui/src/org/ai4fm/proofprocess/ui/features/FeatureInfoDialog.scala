package org.ai4fm.proofprocess.ui.features

import org.ai4fm.proofprocess.{ProofFeature, ProofProcessPackage, ProofStore}
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.{error, log, plugin}
import org.ai4fm.proofprocess.ui.util.SWTUtil.{fnToDoubleClickListener, selectionElement}

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
import org.eclipse.swt.widgets.{Composite, Control, Shell, Text}
import org.eclipse.ui.forms.events.{HyperlinkAdapter, HyperlinkEvent}
import org.eclipse.ui.forms.widgets.{FormToolkit, ImageHyperlink}


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

  private var paramTermsTable: TableViewer = _

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

    toolkit.createLabel(form.getBody, "Some more information", SWT.NONE)

    val featureInfoControl = createFeatureInfo(toolkit, form.getBody)
    featureInfoControl.setLayoutData(fillHorizontal.create)

    val paramsObservable =
      EMFObservables.observeList(feature, ProofProcessPackage.Literals.PROOF_FEATURE__PARAMS)

    toolkit.createLabel(form.getBody, "Parameter terms: ")
    paramTermsTable = createTermList(toolkit, form.getBody, paramsObservable)
    paramTermsTable.getTable.setLayoutData(fillBoth.hint(100, 20).create)

    form
  }

  private def createFeatureInfo(toolkit: FormToolkit, parent: Composite): Control = {
    
    val container = toolkit.createComposite(parent, SWT.WRAP)
    container.setLayout(GridLayoutFactory.swtDefaults.numColumns(2).create)

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
                             terms: IObservableList): TableViewer = {

    val table = toolkit.createTable(parent, SWT.V_SCROLL | SWT.H_SCROLL)

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

    viewer
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
    adapterFactory.dispose
  }

}
