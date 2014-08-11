package org.ai4fm.proofprocess.isabelle.ui.actions

import isabelle.YXML
import org.ai4fm.proofprocess.Attempt
import org.ai4fm.proofprocess.Proof
import org.ai4fm.proofprocess.isabelle.core.prover.ProverData
import org.ai4fm.proofprocess.isabelle.core.prover.ProverDataConverter
import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.FileDialog
import org.eclipse.ui.handlers.HandlerUtil
import scala.collection.JavaConversions._


class ExportPProcessToIsabelleHandler extends AbstractHandler {

  @throws(classOf[ExecutionException])
  override def execute(event: ExecutionEvent): Object = {
    
    // get the structured selection if available
    val selection = Option(HandlerUtil.getCurrentSelection(event)) collect { case ss: IStructuredSelection => ss }
    
    val selected = selection map (_.iterator.toList )
    
    val attempts = selected map { _.collect{ case a: Attempt => a } }

    attempts foreach {
      _ foreach exportToFile(event)
    }

    // return value is reserved for future APIs
    null
  }
  
  private def exportToFile(event: ExecutionEvent)(attempt: Attempt) {
    
    // TODO get it from selection?
    val proof = attempt.eContainer().asInstanceOf[Proof]
    
    val proofTree = ProverDataConverter.attempt(proof, attempt)
    val encoded = ProverData.Encode.encodeTheory(List(proofTree).toMap)
    val yxml = YXML.string_of_tree(encoded)

    val fileDialog = new FileDialog(HandlerUtil.getActiveShell(event), SWT.SAVE)
    fileDialog.setText("Select File to Export ProofProcess Data as Isabelle YXML")
    fileDialog.setOverwrite(true)
    fileDialog.setFilterExtensions(Array("*.yxml", "*.*"))
    fileDialog.setFileName("attempt.yxml")
    val savePath = Option(fileDialog.open())

    savePath foreach { path =>
      printToFile(new java.io.File(path))(p => {
        p.println(yxml)
      })
    }
    
  }

  private def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }
  
}
