package org.ai4fm.proofprocess.ui.features

import org.ai4fm.proofprocess.{ProofStep, Term}
import org.ai4fm.proofprocess.core.util.PProcessUtil.getAdapter
import org.ai4fm.proofprocess.ui.TermSelectionSourceProvider
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.plugin
import org.ai4fm.proofprocess.ui.util.SWTUtil._
import org.ai4fm.proofprocess.ui.util.ScalaArrayContentProvider

import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.jface.dialogs.{IDialogConstants, StatusDialog}
import org.eclipse.jface.layout.{GridDataFactory, GridLayoutFactory}
import org.eclipse.jface.resource.{JFaceResources, LocalResourceManager}
import org.eclipse.jface.viewers.{DoubleClickEvent, StructuredSelection, StyledString, TableViewer}
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.{Composite, Control, Shell, Text}
import org.eclipse.ui.forms.events.{ExpansionAdapter, ExpansionEvent}
import org.eclipse.ui.forms.widgets.{FormToolkit, Section}
import org.eclipse.ui.forms.widgets.ExpandableComposite.{EXPANDED, NO_TITLE_FOCUS_BOX, TITLE_BAR, TWISTIE}


/**
 * A dialog to select/extract a sub-term from the given term
 *
 * @author Andrius Velykis
 */
class SubTermSelectionDialog(parent: Shell,
                             term: Term,
                             context: ProofStep,
                             okButtonLabel: Option[String] = None) extends StatusDialog(parent) {

  private val adapterFactory =
    new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)

  private val labelProvider = new AdapterFactoryLabelProvider(adapterFactory)

  private var renderedTermField: StyledText = _

  private var subTermsTable: TableViewer = _

  private var schemaTermsTable: TableViewer = _

  setTitle("Select Sub-term")
  setShellStyle(getShellStyle() | SWT.MAX | SWT.RESIZE)

  private var _selectedTerm: Term = term
  def selectedTerm: Term = _selectedTerm

  override def getDialogBoundsSettings = plugin.dialogSettings("SubTermSelectionDialog")

  override protected def createButtonsForButtonBar(parent: Composite) {
    super.createButtonsForButtonBar(parent)
    okButtonLabel foreach { text =>
      val okBtn = getButton(IDialogConstants.OK_ID)
      okBtn.setText(text)
      setButtonLayoutData(okBtn)
    }
  }


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

    val infoMsg = 
      "Select the term, sub-term or a schema term to mark as proof feature parameter."

    toolkit.createLabel(form.getBody, infoMsg, SWT.NONE)

    toolkit.createLabel(form.getBody, "Term:", SWT.NONE)
    renderedTermField = new StyledText(form.getBody,
      SWT.BORDER | SWT.MULTI | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL)
    renderedTermField.setLayoutData(fillBoth.hint(100, 40).create)

    toolkit.adapt(renderedTermField)

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



      subTermsTable = createTermList(toolkit, container)
      subTermsTable.getTable.setLayoutData(fillBoth.hint(100, 20).create)

      toolkit.paintBordersFor(container)

      container
    }

    createSection("Term schemas", None, true) { parent =>
      val container = toolkit.createComposite(parent, SWT.WRAP)
      container.setLayout(GridLayoutFactory.swtDefaults.create)

      schemaTermsTable = createTermList(toolkit, container)
      schemaTermsTable.getTable.setLayoutData(fillBoth.hint(100, 20).create)

      toolkit.paintBordersFor(container)

      container
    }

    showTerm(term)
    form
  }


  private def createTermList(toolkit: FormToolkit,
                             parent: Composite): TableViewer = {

    val table = toolkit.createTable(parent, SWT.V_SCROLL | SWT.H_SCROLL)

    val viewer = new TableViewer(table)
    viewer.setContentProvider(ScalaArrayContentProvider)
    viewer.setLabelProvider(labelProvider)

    viewer.addDoubleClickListener { e: DoubleClickEvent =>
      selectionElement(e.getSelection) match {
        case Some(t: Term) => showTerm(t)
        case _ => // ignore
      }
    }

    viewer
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

  private def showTerm(t: Term) {

    _selectedTerm = t

    val termSourceProvider = getAdapter(t, classOf[TermSelectionSourceProvider], true)
    val termSource = termSourceProvider map (_.getTermSource(context))

    val (rendered, subTerms, termSchemas) = termSource match {
      case Some(source) => (source.rendered, source.subTerms, source.schemaTerms)

      case None => (new StyledString(""), Nil, Nil)
    }

    renderedTermField.setText(rendered.getString)
    renderedTermField.setStyleRanges(rendered.getStyleRanges)

    subTermsTable.setInput(subTerms)
    subTermsTable.setSelection(new StructuredSelection(t), true)

    schemaTermsTable.setInput(termSchemas)
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

  override def getInitialSize(): Point =
    defaultInitialDialogSize(getDialogBoundsSettings,
      super.getInitialSize(), new Point(700, 600))

}
