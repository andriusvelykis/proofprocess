package org.ai4fm.proofprocess.ui.features

import org.ai4fm.proofprocess.{Intent, ProofStore}
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.{error, plugin}
import org.ai4fm.proofprocess.ui.util.FilteredEntryDialog

import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.swt.widgets.Shell


/**
 * A dialog to select available intent or enter a new one.
 *
 * @author Andrius Velykis
 */
class IntentSelectionDialog private (parent: Shell,
                                     proofStore: ProofStore,
                                     adapterFactory: ComposedAdapterFactory)
    extends FilteredEntryDialog(parent, new AdapterFactoryLabelProvider(adapterFactory)) {

  /**
   * A constructor for the dialog that instantiates the adapter factory.
   */
  def this(parent: Shell, proofStore: ProofStore) = this(parent, proofStore,
    new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE))

  setTitle("Select Intent")
  setMessage("Enter or select the intent:")
  setMultipleSelection(false)

  setElements(proofStore.getIntents.toArray)

  override def getDialogBoundsSettings = plugin.dialogSettings("IntentSelectionDialog")

  // adapt for convenience
  def selectedIntent: Option[Intent] =
    Option(super.getResult) flatMap (_.headOption) map { case i: Intent => i }

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
