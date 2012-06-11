package org.ai4fm.proofprocess.isabelle.ui.actions

import org.ai4fm.proofprocess.Attempt
import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.ui.handlers.HandlerUtil
import scala.collection.JavaConversions._
import org.ai4fm.proofprocess.isabelle.core.prover.ProverDataConverter


class ExportPProcessToIsabelleHandler extends AbstractHandler {

  @throws(classOf[ExecutionException])
  override def execute(event: ExecutionEvent): Object = {
    
    // get the structured selection if available
    val selection = Option(HandlerUtil.getCurrentSelection(event)) collect { case ss: IStructuredSelection => ss }
    
    val selected = selection map (_.iterator.toList )
    
    val attempts = selected map { _.collect{ case a: Attempt => a } }

    attempts foreach {
      _.foreach { attempt =>
        println("Export attempt: " + ProverDataConverter.attempt(attempt))
      }
    }

    // return value is reserved for future APIs
    null
  }
  
}
