package org.ai4fm.proofprocess.isabelle.ui.actions

import java.io.File

import scala.xml.PrettyPrinter
import scala.xml.XML

import org.ai4fm.proofprocess.Attempt
import org.ai4fm.proofprocess.Proof
import org.ai4fm.proofprocess.isabelle.core.prover.ProverData
import org.ai4fm.proofprocess.isabelle.core.prover.ProverData.ProofGoal
import org.ai4fm.proofprocess.isabelle.core.prover.ProverDataConverter
import org.ai4fm.proofprocess.ui.util.SWTUtil
import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.FileDialog
import org.eclipse.ui.handlers.HandlerUtil

import isabelle.YXML


class ExportPProcessToIsabelleHandler extends AbstractHandler {

  @throws(classOf[ExecutionException])
  override def execute(event: ExecutionEvent): Object = {

    val selected = SWTUtil.selectionElements(HandlerUtil.getCurrentSelection(event))

    val proofs = selected collect { case proof: Proof => proof }

    if (!proofs.isEmpty) {
      // export proofs
      val proofData = proofs map exportProof
      exportToFile(event, proofData)
      
    } else {
      // check and export attempts if one is selected
      // note: do not support multiple attempts yet (need to find common parents in this case..)
      val attemptStream = selected.toStream collect { case attempt: Attempt => attempt }
      attemptStream.headOption foreach { attempt =>
        exportToFile(event, List(exportAttempt(attempt)))
      }
    }

    // return value is reserved for future APIs
    null
  }

  private def exportProof(proof: Proof): (String, List[ProofGoal]) =
    ProverDataConverter.proof(proof)

  private def exportAttempt(attempt: Attempt): (String, List[ProofGoal]) = {
    
    // TODO get it from selection?
    val proof = attempt.eContainer().asInstanceOf[Proof]
    
    ProverDataConverter.attempt(proof, attempt)
  }
  
  private def exportToFile(event: ExecutionEvent, thy: List[(String, List[ProofGoal])]) {
    
    val encoded = ProverData.Encode.encodeTheory(thy.toMap)
    val yxml = YXML.string_of_tree(encoded)

    val fileDialog = new FileDialog(HandlerUtil.getActiveShell(event), SWT.SAVE)
    fileDialog.setText("Select File to Export ProofProcess Data as Tinker YXML")
    fileDialog.setOverwrite(true)
    fileDialog.setFilterExtensions(Array("*.yxml", "*.*"))
    fileDialog.setFileName("attempt.yxml")
    val savePath = Option(fileDialog.open()) map { path => new java.io.File(path) }

    savePath foreach { f =>
      printToFile(f)(p => {
        p.println(yxml)
      })
    }

    val saveXmlPath = savePath map { f => new File(f.getParentFile, f.getName + ".xml") }

    saveXmlPath foreach { f =>
      printToFile(f)(w => {
        val encodedXML = XML.loadString(encoded.toString)
        val xmlPretty = new PrettyPrinter(100, 2)
        val xmlOut = new StringBuilder
        xmlPretty.format(encodedXML, xmlOut)
        w.println(xmlOut)
      })
    }
    
  }

  private def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }
  
}
