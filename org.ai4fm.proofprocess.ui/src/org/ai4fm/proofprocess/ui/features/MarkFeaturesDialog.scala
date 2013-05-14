package org.ai4fm.proofprocess.ui.features

import org.ai4fm.proofprocess.{ProofElem, ProofEntry, Term}
import org.ai4fm.proofprocess.core.store.ProofElemComposition
import org.ai4fm.proofprocess.ui.{PProcessImages, PProcessUIPlugin}
import org.ai4fm.proofprocess.ui.util.ScalaArrayContentProvider

import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.jface.dialogs.StatusDialog
import org.eclipse.jface.layout.{GridDataFactory, GridLayoutFactory}
import org.eclipse.jface.resource.{JFaceResources, LocalResourceManager}
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.{Composite, Control, Shell}
import org.eclipse.ui.forms.events.{ExpansionAdapter, ExpansionEvent}
import org.eclipse.ui.forms.widgets.{FormToolkit, Section}
import org.eclipse.ui.forms.widgets.ExpandableComposite._


/**
 * A dialog to mark various proof process features.
 * 
 * @author Andrius Velykis
 */
class MarkFeaturesDialog(parent: Shell, elem: ProofElem) extends StatusDialog(parent) {

  private val adapterFactory =
    new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)

  private val labelProvider = new AdapterFactoryLabelProvider(adapterFactory)

  lazy val inGoals = ProofElemComposition.composeInGoals(elem)
  lazy val outGoals = ProofElemComposition.composeOutGoals(elem)
  

  setTitle("Mark Features")
  setShellStyle(getShellStyle() | SWT.MAX | SWT.RESIZE)

  override def getDialogBoundsSettings = PProcessUIPlugin.plugin.getDialogSettings

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

    val resourceManager = new LocalResourceManager(JFaceResources.getResources, form)
    
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
      val layoutData = fillBoth.create
      section.setLayoutData(layoutData)

      // toggle grabbing vertical space when expanded/collapsed
      section.addExpansionListener(new ExpansionAdapter {
        override def expansionStateChanged(e: ExpansionEvent) =
          // do not grab vertical space if collapsed
          layoutData.grabExcessVerticalSpace = e.getState
      })
      
      section.addExpansionListener(sectionListener)
      
      val control = contents(section)
      control.setLayoutData(fillBoth.create)
      section.setClient(control)
    }

    createSection("Before step", "The state before step.") { parent =>
      val container = toolkit.createComposite(parent, SWT.WRAP)
      container.setLayout(GridLayoutFactory.swtDefaults.create)

      val inGoalsTable = createTermList(toolkit, container, inGoals)
      inGoalsTable.setLayoutData(fillBoth.hint(100, 20).create)
      
      toolkit.paintBordersFor(container)

      container
    }

    createSection("Proof step", "Information about the proof step.") { parent => 
      createStepInfo(toolkit, parent)
    }

    createSection("After step", "Step results.") { parent =>
      val container = toolkit.createComposite(parent, SWT.WRAP)
      container.setLayout(GridLayoutFactory.swtDefaults.numColumns(2).create)

      if (outGoals.isEmpty) {
        // no output goals - all proved
        toolkit.createLabel(container, "Results: ")
        val allProvedLabel = createLabelWithImage(
          toolkit, container, "All proved!",
          resourceManager.createImageWithDefault(PProcessImages.SUCCESS))

      } else {
        val resultsLabel = toolkit.createLabel(container, "Results: ")
        resultsLabel.setLayoutData(GridDataFactory.swtDefaults.span(2, 1).create)
        
        val inGoalsTable = createTermList(toolkit, container, outGoals)
        inGoalsTable.setLayoutData(fillBoth.hint(100, 20).span(2, 1).create)
      }
      
      toolkit.paintBordersFor(container)

      container
    }

    form
  }


  private def createStepInfo(toolkit: FormToolkit, parent: Composite): Control = {
    
    val container = toolkit.createComposite(parent, SWT.WRAP)
    container.setLayout(GridLayoutFactory.swtDefaults.numColumns(2).create)

    val intent = elem.getInfo.getIntent
    toolkit.createLabel(container, "Intent: ")
    val intentLink = toolkit.createImageHyperlink(container, SWT.NONE)
    if (intent == null) {
      intentLink.setText("(not set)")
    } else {
      intentLink.setImage(labelProvider.getImage(intent))
      intentLink.setText(labelProvider.getText(intent))
    }

    val desc = elem.getInfo.getNarrative
    toolkit.createLabel(container, "Narrative: ")
    toolkit.createLabel(container, desc, SWT.WRAP)

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


  private def createTermList(toolkit: FormToolkit,
                             parent: Composite,
                             terms: Seq[Term]): Control = {

    val table = toolkit.createTable(parent, SWT.NONE)

    val viewer = new TableViewer(table)
    viewer.setContentProvider(ScalaArrayContentProvider)
    viewer.setLabelProvider(labelProvider)

    viewer.setInput(terms)
    table
  }

  private def fillBoth: GridDataFactory =
    GridDataFactory.fillDefaults.grab(true, true)

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
    val result = super.close()
    if (result) {
      dispose()
    }

    result
  }

  private def dispose() {
    adapterFactory.dispose
  }

}
