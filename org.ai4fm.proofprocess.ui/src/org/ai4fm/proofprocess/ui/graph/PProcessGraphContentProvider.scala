package org.ai4fm.proofprocess.ui.graph

import scala.collection.immutable.Map

import org.ai4fm.proofprocess.{Attempt, Proof, ProofElem, ProofStore}
import org.eclipse.gef4.zest.core.viewers.IGraphEntityContentProvider
import org.eclipse.jface.viewers.Viewer


/** ProofProcess data content provider for Zest GraphViewer.
  * 
  * Utilises [[org.ai4fm.proofprocess.ui.graph.PProcessGraph]] for graph computation
  * 
  * @author Andrius Velykis
  */
class PProcessGraphContentProvider extends IGraphEntityContentProvider {

  private var graph: Map[AnyRef, List[AnyRef]] = Map()

  override def inputChanged(viewer: Viewer, oldInput: AnyRef, newInput: AnyRef) = {

    val inputGraph = if (computeGraph.isDefinedAt(newInput)) {
      computeGraph(newInput)
    } else {
      Map() 
    }
    
    // combine with empty map to work around type restrictions
    graph = inputGraph ++ Map()
  }
  
  override def getElements(inputElement: AnyRef): Array[AnyRef] = graph.keys.toArray

  override def getConnectedTo(entity: AnyRef): Array[AnyRef] = graph.getOrElse(entity, Nil).toArray

  override def dispose() {}

  /** Computes the graph - can be used to determine whether the graph is computable for any object. */
  val computeGraph: PartialFunction[Any, Map[_ <: AnyRef, List[AnyRef]]] = {
    case store: ProofStore => PProcessGraph.storeGraph(store)
    case proof: Proof => PProcessGraph.proofGraph(proof)
    case attempt: Attempt => PProcessGraph.attemptGraph(attempt)
    case tree: ProofElem => PProcessGraph.proofTreeGraph(tree)
  }
  
}
