package org.ai4fm.proofprocess.ui.features

import org.ai4fm.proofprocess.{ProofFeatureDef, ProofStore}
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.{error, plugin}
import org.ai4fm.proofprocess.ui.util.FilteredEntryDialog

import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.swt.widgets.Shell


/**
 * A dialog to select available feature definition or enter a new one.
 * 
 * @author Andrius Velykis
 */
class FeatureDefSelectionDialog private (parent: Shell,
                                         proofStore: ProofStore,
                                         adapterFactory: ComposedAdapterFactory)
    extends FilteredEntryDialog(parent, new AdapterFactoryLabelProvider(adapterFactory)) {

  /**
   * A constructor for the dialog that instantiates the adapter factory.
   */
  def this(parent: Shell, proofStore: ProofStore) = this(parent, proofStore,
    new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE))


  setTitle("Select Feature")
  setMessage("Enter or select the proof feature definition:")
  setMultipleSelection(false)

  setElements(proofStore.getFeatures.toArray)

  override def getDialogBoundsSettings = plugin.dialogSettings("FeatureDefSelectionDialog")

  // adapt for convenience
  def selectedFeature: Option[ProofFeatureDef] = 
    Option(super.getResult) flatMap (_.headOption) map { case pf: ProofFeatureDef => pf }


  def selectedFeature_=(featureOpt: Option[ProofFeatureDef]) =
    featureOpt foreach { feature => setInitialSelections(Array(feature)) }


  override def validateCurrentSelection(): Boolean =
    super.validateCurrentSelection() && validateFeatureName()

  private def validateFeatureName(): Boolean =
    if (getSelectedElements.isEmpty && selectedFilterText.isEmpty) {
      updateStatus(error(msg = Some("Enter a proof feature name.")))
      false
    } else {
      true
    }


  def selectedFeatureName: Option[String] = selectedFeature match {
    case Some(feature) => Some(feature.getName)

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
