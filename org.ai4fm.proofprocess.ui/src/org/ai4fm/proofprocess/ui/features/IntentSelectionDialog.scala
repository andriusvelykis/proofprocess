package org.ai4fm.proofprocess.ui.features

import org.ai4fm.proofprocess.{Intent, ProofStore}
import org.ai4fm.proofprocess.ui.PProcessUIPlugin.error
import org.ai4fm.proofprocess.ui.util.FilteredEntryDialog

import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.swt.widgets.Shell


/**
 * A dialog to select available intent or enter a new one.
 * 
 * @author Andrius Velykis
 */
class IntentSelectionDialog(parent: Shell,
                            adapterFactory: ComposedAdapterFactory,
                            proofStore: ProofStore)
    extends FilteredEntryDialog(parent, new AdapterFactoryLabelProvider(adapterFactory)) {

  setTitle("Select Intent")
  setMessage("Enter or select the intent:")
  setMultipleSelection(false)

  setElements(proofStore.getIntents.toArray)

  // adapt for convenience
  def selectedIntent: Option[Intent] = Option(super.getResult) match {
    case Some(results) if !results.isEmpty => Some(results.head.asInstanceOf[Intent])
    case _ => None
  }

  def selectedIntent_=(intentOpt: Option[Intent]) =
    intentOpt foreach { intent => setInitialSelections(Array(intent)) }


  override def validateCurrentSelection(): Boolean =
    super.validateCurrentSelection() && validateIntentName()

  private def validateIntentName(): Boolean =
    if (getSelectedElements.isEmpty && selectedFilterText.isEmpty) {
      updateStatus(error(msg = Some("Enter an intent name.")))
      false
    } else {
      true
    }


  def selectedIntentName: Option[String] = selectedIntent match {
    case Some(intent) => Some(intent.getName)

    case None => {
      val newName = filterText
      if (!newName.isEmpty) Some(newName) else None
    }
  }

  override def close(): Boolean = {
    val result = super.close()
    if (result) {
      adapterFactory.dispose()
    }

    result
  }

}

/**
 * Factory method to allow instantiating adapter factory for the dialog.
 */
object IntentSelectionDialog {
  def apply(parent: Shell, proofStore: ProofStore): IntentSelectionDialog = {
    val adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)

    new IntentSelectionDialog(parent, adapterFactory, proofStore)
  }
}
