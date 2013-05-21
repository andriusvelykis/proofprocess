package org.ai4fm.proofprocess.ui.util

import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.plugin

import org.eclipse.core.runtime.{IStatus, Status}
import org.eclipse.jface.viewers.ILabelProvider
import org.eclipse.swt.widgets.Shell
import org.eclipse.ui.dialogs.ElementListSelectionDialog


/**
 * A list selection dialog that allows using the entered filter text as a new value.
 * 
 * The entered value is stored and can be accessed via #filterText 
 * 
 * @author Andrius Velykis
 */
class FilteredEntryDialog(parent: Shell, renderer: ILabelProvider)
    extends ElementListSelectionDialog(parent, renderer) {

  private var _filterText = ""

  def filterText: String = _filterText

  protected def selectedFilterText: String = fFilteredList.getFilter

  override def setFilter(filter: String) {
    this._filterText = filter
    super.setFilter(filter)
  }

  override def validateCurrentSelection(): Boolean = {
    val selected = getSelectedElements
    if (selected.isEmpty) {
      // no elements - a new one will be created
      updateStatus(new Status(IStatus.OK, plugin.pluginId, ""))
      true
    } else {
      super.validateCurrentSelection()
    }
  }

  override def computeResult() {
    this._filterText = selectedFilterText
    super.computeResult()
  }

}
