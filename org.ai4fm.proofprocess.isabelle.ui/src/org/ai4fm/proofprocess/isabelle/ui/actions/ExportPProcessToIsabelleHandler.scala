package org.ai4fm.proofprocess.isabelle.ui.actions

import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.ui.handlers.HandlerUtil
import scala.collection.JavaConversions._

class ExportPProcessToIsabelleHandler extends AbstractHandler {

  @throws(classOf[ExecutionException])
  override def execute(event: ExecutionEvent): Object = {
    
    // get the structured selection if available
    val selection = Option(HandlerUtil.getCurrentSelection(event)) collect { case ss: IStructuredSelection => ss }
    
    val selected = selection map (_.iterator.toList )
    
    selected foreach { elems =>
      println(elems)
    }

    // return value is reserved for future APIs
    null
  }
  
}
