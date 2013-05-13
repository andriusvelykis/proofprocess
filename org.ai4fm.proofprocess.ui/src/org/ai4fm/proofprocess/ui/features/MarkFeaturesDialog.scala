package org.ai4fm.proofprocess.ui.features

import org.ai4fm.proofprocess.ProofEntry

import org.eclipse.jface.dialogs.StatusDialog
import org.eclipse.jface.layout.{GridDataFactory, GridLayoutFactory}
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.{Composite, Control, Shell}
import org.eclipse.ui.forms.events.{ExpansionAdapter, ExpansionEvent}
import org.eclipse.ui.forms.widgets.{FormToolkit, Section}
import org.eclipse.ui.forms.widgets.ExpandableComposite._


/**
 * A dialog to mark various proof process features.
 * 
 * @author Andrius Velykis
 */
class MarkFeaturesDialog(parent: Shell, entry: ProofEntry) extends StatusDialog(parent) {

  setTitle("Mark Features")
  setShellStyle(getShellStyle() | SWT.MAX | SWT.RESIZE)


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
    toolkit.decorateFormHeading(form.getForm)
    
    form.getBody.setLayout(GridLayoutFactory.swtDefaults.create)

    toolkit.createLabel(form.getBody, "Some more information", SWT.NONE)

    val sectionListener = new ExpansionAdapter {
      override def expansionStateChanged(e: ExpansionEvent) = form.reflow(true)
    }
    
    def createSection(text: String, desc: String)(contents: Composite => Control) {
      val section = toolkit.createSection(form.getBody,
          Section.DESCRIPTION | TITLE_BAR | NO_TITLE_FOCUS_BOX | TWISTIE | EXPANDED)
      section.setText(text)
      section.setDescription(desc)
      section.setLayoutData(GridDataFactory.fillDefaults.grab(true, false).create)
      section.addExpansionListener(sectionListener)
      
      val control = contents(section)
      control.setLayoutData(GridDataFactory.fillDefaults.grab(true, true).create)
      section.setClient(control)
    }

    createSection("Proof step", "Information about the proof step.") { parent =>
      val container = toolkit.createComposite(parent, SWT.WRAP)
      container.setLayout(GridLayoutFactory.swtDefaults.create)

      toolkit.createLabel(container, "Fooo", SWT.NONE)

      container
    }

    createSection("Before step", "The state before step.") { parent =>
      val container = toolkit.createComposite(parent, SWT.WRAP)
      container.setLayout(GridLayoutFactory.swtDefaults.create)

      toolkit.createLabel(container, "Fooo", SWT.NONE)

      container
    }

    createSection("After step", "Step results.") { parent =>
      val container = toolkit.createComposite(parent, SWT.WRAP)
      container.setLayout(GridLayoutFactory.swtDefaults.create)

      toolkit.createLabel(container, "Fooo", SWT.NONE)

      container
    }

    form
  }

}
