package org.ai4fm.proofprocess.ui.features

import org.ai4fm.proofprocess.{Intent, ProofProcessPackage}
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.plugin
import org.ai4fm.proofprocess.ui.util.SWTUtil.defaultInitialDialogSize

import org.eclipse.core.databinding.DataBindingContext
import org.eclipse.core.databinding.observable.value.IObservableValue
import org.eclipse.emf.cdo.transaction.CDOTransaction
import org.eclipse.emf.databinding.EMFObservables
import org.eclipse.jface.databinding.swt.WidgetProperties
import org.eclipse.jface.dialogs.StatusDialog
import org.eclipse.jface.layout.{GridDataFactory, GridLayoutFactory}
import org.eclipse.jface.resource.{JFaceResources, LocalResourceManager}
import org.eclipse.jface.window.Window
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.{Composite, Control, Shell}
import org.eclipse.ui.forms.widgets.FormToolkit


/**
 * A shared dialog that allows displaying and editing name and description fields
 * of some data structure.
 * 
 * @author Andrius Velykis
 */
class NameDescDialog(parentShell: Shell,
                     name: IObservableValue,
                     desc: IObservableValue,
                     transaction: Option[CDOTransaction],
                     title: String,
                     label: String,
                     dialogId: String)
    extends StatusDialog(parentShell) {


  val savePoint = transaction map (_.setSavepoint())

  val databindingContext = new DataBindingContext

  setTitle(label)
  setShellStyle(getShellStyle() | SWT.MAX | SWT.RESIZE)

  override def getDialogBoundsSettings = plugin.dialogSettings(dialogId)

  override def createDialogArea(parent: Composite): Control = {
    
    val toolkit = new FormToolkit(parent.getDisplay)
    val form = toolkit.createScrolledForm(parent)
    form.setLayoutData(GridDataFactory.fillDefaults.grab(true, true).create)
    form.setText(label)
    toolkit.decorateFormHeading(form.getForm)

    val resourceManager = new LocalResourceManager(JFaceResources.getResources, form)
    
    form.getBody.setLayout(GridLayoutFactory.swtDefaults.numColumns(2).create)

    toolkit.createLabel(form.getBody, "Name: ")
    val nameField = toolkit.createText(form.getBody, "", SWT.SINGLE)
    nameField.setLayoutData(fillHorizontal.create)

    databindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(nameField), name)

    val descLabel = toolkit.createLabel(form.getBody, "Description: ")
    descLabel.setLayoutData(GridDataFactory.swtDefaults.align(SWT.BEGINNING, SWT.BEGINNING).create)
    val descField = toolkit.createText(form.getBody, "", SWT.SINGLE)
    descField.setLayoutData(fillBoth.hint(100, 50).create)

    databindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(descField), desc)

    toolkit.paintBordersFor(form.getBody)

    form
  }

  override def getInitialSize(): Point =
    defaultInitialDialogSize(getDialogBoundsSettings,
      super.getInitialSize(), new Point(500, 350))


  private def fillBoth: GridDataFactory = GridDataFactory.fillDefaults.grab(true, true)

  private def fillHorizontal: GridDataFactory = GridDataFactory.fillDefaults.grab(true, false)

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

  private def saveChanges() = transaction foreach (_.commit())

  private def discardChanges() = savePoint foreach (_.rollback())

  def dispose() {
    databindingContext.dispose()
    name.dispose()
    desc.dispose()
  }

}

class IntentInfoDialog(parentShell: Shell, intent: Intent)
    extends NameDescDialog(parentShell,
      EMFObservables.observeValue(intent, ProofProcessPackage.Literals.INTENT__NAME),
      EMFObservables.observeValue(intent, ProofProcessPackage.Literals.INTENT__DESCRIPTION),
      PProcessUtil.cdoTransaction(intent),
      "Proof Intent", "Proof intent",
      "IntentInfoDialog")

