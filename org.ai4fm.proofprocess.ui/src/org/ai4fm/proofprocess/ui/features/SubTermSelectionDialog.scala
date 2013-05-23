package org.ai4fm.proofprocess.ui.features

import org.ai4fm.proofprocess.{ProofStep, Term}
import org.ai4fm.proofprocess.core.util.PProcessUtil.getAdapter
import org.ai4fm.proofprocess.ui.TermSelectionSourceProvider
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.plugin
import org.ai4fm.proofprocess.ui.util.ScalaArrayContentProvider

import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.jface.dialogs.StatusDialog
import org.eclipse.jface.layout.{GridDataFactory, GridLayoutFactory}
import org.eclipse.jface.resource.{JFaceResources, LocalResourceManager}
import org.eclipse.jface.viewers.{StructuredSelection, TableViewer}
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.{Composite, Control, Shell, Text}
import org.eclipse.ui.forms.events.{ExpansionAdapter, ExpansionEvent}
import org.eclipse.ui.forms.widgets.{FormToolkit, Section}
import org.eclipse.ui.forms.widgets.ExpandableComposite.{EXPANDED, NO_TITLE_FOCUS_BOX, TITLE_BAR, TWISTIE}


/**
 * A dialog to select/extract a sub-term from the given term
 * 
 * @author Andrius Velykis
 */
class SubTermSelectionDialog(parent: Shell, term: Term, context: ProofStep) extends StatusDialog(parent) {

  private val adapterFactory =
    new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)

  private val labelProvider = new AdapterFactoryLabelProvider(adapterFactory)

  setTitle("Select Sub-term")
  setShellStyle(getShellStyle() | SWT.MAX | SWT.RESIZE)

  override def getDialogBoundsSettings = plugin.dialogSettings("SubTermSelectionDialog")

  /**
   * Create the dialog area.
   *
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(Composite)
   */
  override def createDialogArea(parent: Composite): Control = {
    
    val toolkit = new FormToolkit(parent.getDisplay)
    val form = toolkit.createScrolledForm(parent)
    form.setLayoutData(GridDataFactory.fillDefaults.grab(true, true).create)
    form.setText("Select part of the term")
    toolkit.decorateFormHeading(form.getForm)

    val resourceManager = new LocalResourceManager(JFaceResources.getResources, form)
    
    form.getBody.setLayout(GridLayoutFactory.swtDefaults.create)

    toolkit.createLabel(form.getBody, "Some more information", SWT.NONE)

    toolkit.createLabel(form.getBody, "Term:", SWT.NONE)
    val renderedTermField = new StyledText(form.getBody,
      SWT.BORDER | SWT.MULTI | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL)
    renderedTermField.setLayoutData(fillBoth.create)

    toolkit.adapt(renderedTermField)

    val termSourceProvider = getAdapter(term, classOf[TermSelectionSourceProvider], true)
    val termSource = termSourceProvider map (_.getTermSource(context))

    termSource match {
      case Some(source) => {
        val rendered = source.rendered
        renderedTermField.setText(rendered.getString)
        renderedTermField.setStyleRanges(rendered.getStyleRanges)
      }

      case None => renderedTermField.setText("Fooo") // ignore
    }

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

    createSection("Sub-terms", None, true) { parent =>
      val container = toolkit.createComposite(parent, SWT.WRAP)
      container.setLayout(GridLayoutFactory.swtDefaults.create)

      val subTerms = termSource map (_.subTerms) getOrElse Nil

      val allSubTerms = term :: subTerms

      val subTermsTable = createTermList(toolkit, container, allSubTerms)
      subTermsTable.setLayoutData(fillBoth.hint(100, 20).create)

      toolkit.paintBordersFor(container)

      container
    }

    createSection("Term schemas", None, true) { parent =>
      val container = toolkit.createComposite(parent, SWT.WRAP)
      container.setLayout(GridLayoutFactory.swtDefaults.create)

      val termSchemas = termSource map (_.schemaTerms) getOrElse Nil

      val subTermsTable = createTermList(toolkit, container, termSchemas)
      subTermsTable.setLayoutData(fillBoth.hint(100, 20).create)

      toolkit.paintBordersFor(container)

      container
    }

//    createSection("Proof step", Some("Information about the proof step.")) { parent => 
//      createStepInfo(toolkit, parent)
//    }
//
//    createSection("After step", Some("Step results.")) { parent =>
//      val container = toolkit.createComposite(parent, SWT.WRAP)
//      container.setLayout(GridLayoutFactory.swtDefaults.numColumns(2).create)
//
//      if (outGoals.isEmpty) {
//        // no output goals - all proved
//        toolkit.createLabel(container, "Results: ")
//        val allProvedLabel = createLabelWithImage(
//          toolkit, container, "All proved!",
//          resourceManager.createImageWithDefault(PProcessImages.SUCCESS))
//
//      } else {
//        val resultsLabel = toolkit.createLabel(container, "Results: ")
//        resultsLabel.setLayoutData(GridDataFactory.swtDefaults.span(2, 1).create)
//        
//        val inGoalsTable = createTermList(toolkit, container, outGoals)
//        inGoalsTable.setLayoutData(fillBoth.hint(100, 20).span(2, 1).create)
//      }
//      
//      toolkit.paintBordersFor(container)
//
//      container
//    }

    form
  }


  private def createTermList(toolkit: FormToolkit,
                             parent: Composite,
                             terms: Seq[Term]): Control = {

    val table = toolkit.createTable(parent, SWT.V_SCROLL)

    val viewer = new TableViewer(table)
    viewer.setContentProvider(ScalaArrayContentProvider)
    viewer.setLabelProvider(labelProvider)

    viewer.setInput(terms)

    terms match {
      case first :: _ => viewer.setSelection(new StructuredSelection(first), true)
      case _ => // nothing
    }

    table
  }

  private def createTextField(toolkit: FormToolkit,
                              parent: Composite,
                              label: String,
                              initText: String): Text = {

    val l = toolkit.createLabel(parent, label)
    l.setLayoutData(GridDataFactory.swtDefaults.align(SWT.BEGINNING, SWT.BEGINNING).create)

    val t = toolkit.createText(parent, initText, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL)

    val lineHeight = t.getLineHeight + 2 // add a bit, otherwise it is too small
    val minHeight = 30 max (2 * lineHeight)

    val lines = initText.split("\\r?\\n").length
    val height = lines * lineHeight

    t.setLayoutData(fillHorizontal.hint(100, height max minHeight).create)

    t
  }

  private def fillBoth: GridDataFactory = GridDataFactory.fillDefaults.grab(true, true)

  private def fillHorizontal: GridDataFactory = GridDataFactory.fillDefaults.grab(true, false)

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
