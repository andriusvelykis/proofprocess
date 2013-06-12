package org.ai4fm.proofprocess.ui.graph

import org.ai4fm.proofprocess.{ProofParallel, ProofSeq}
import org.eclipse.gef4.zest.core.viewers.INestedContentProvider


/** ProofProcess data nesting provider for Zest GraphViewer.
  * 
  * @author Andrius Velykis
  */
trait PProcessNestedContentProvider extends INestedContentProvider {

  def hasChildren(element: AnyRef): Boolean = children.isDefinedAt(element)

  def getChildren(element: AnyRef): Array[AnyRef] = if (children.isDefinedAt(element)) children(element) else Array()

  // all proof elements except ProofEntry are nested by the entries
  private val children: PartialFunction[Any, Array[AnyRef]] = {
    case p: ProofParallel => p.getEntries.toArray
    case s: ProofSeq => s.getEntries.toArray
  }
  
}
