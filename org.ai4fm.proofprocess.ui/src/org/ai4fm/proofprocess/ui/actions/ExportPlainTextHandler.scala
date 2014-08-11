package org.ai4fm.proofprocess.ui.actions

import scala.collection.JavaConverters.asScalaIteratorConverter

import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.core.print.PrintPlainText
import org.ai4fm.proofprocess.core.util.PProcessUtil.getAdapter
import org.ai4fm.proofprocess.ui.TermSelectionSourceProvider
import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.FileDialog
import org.eclipse.ui.handlers.HandlerUtil


/**
 * @author Andrius Velykis
 */
class ExportPlainTextHandler extends AbstractHandler {

  @throws(classOf[ExecutionException])
  override def execute(event: ExecutionEvent): Object = {

    // get the structured selection if available
    val selection = Option(HandlerUtil.getCurrentSelection(event)) collect {
      case ss: IStructuredSelection => ss
    }
    
    val selected = selection map { _.iterator.asScala.toList }
    
    selected.toList.flatten foreach exportPlainText(event)

    // return value is reserved for future APIs
    null
  }
  
  private def exportPlainText(event: ExecutionEvent)(prfElem: Any) {

    val printer = new PrintPlainText(termPrinter)
    val printed = printer.print(prfElem)

    val fileDialog = new FileDialog(HandlerUtil.getActiveShell(event), SWT.SAVE)
    fileDialog.setText("Select File to Export ProofProcess Data as Plain Text")
    fileDialog.setOverwrite(true)
    fileDialog.setFilterExtensions(Array("*.txt", "*.*"))
    fileDialog.setFileName("attempt.txt")
    val savePath = Option(fileDialog.open())

    savePath foreach { path =>
      printToFile(new java.io.File(path))(p => {
        p.println(printed)
      })
    }
    
  }

  private def termPrinter(t: Term): Option[String] = {
    val termSourceProvider = getAdapter(t, classOf[TermSelectionSourceProvider], true)
    // TODO review null
    termSourceProvider map { _.getTermSource(null).rendered.getString }
  }

  private def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f, "UTF-8")
    try { op(p) } finally { p.close() }
  }
  
}
