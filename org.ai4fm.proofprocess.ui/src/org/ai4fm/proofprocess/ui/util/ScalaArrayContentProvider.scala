package org.ai4fm.proofprocess.ui.util

import org.eclipse.jface.viewers.{IStructuredContentProvider, Viewer}


/**
 * An array/collection content provider that supports Scala collections.
 * 
 * @author Andrius Velykis
 */
class ScalaArrayContentProvider extends IStructuredContentProvider {

  /**
   * Returns the elements in the input, which must be either a Scala TraversableOnce,
   * an array or a Java Collection.
   */
  override def getElements(inputElement: AnyRef): Array[AnyRef] = inputElement match {
    case scalaCol: TraversableOnce[_] =>
      scalaCol.asInstanceOf[TraversableOnce[AnyRef]].toArray

    case arr: Array[_] =>
      arr.asInstanceOf[Array[AnyRef]]

    case javaCol: java.util.Collection[_] =>
      javaCol.asInstanceOf[java.util.Collection[AnyRef]].toArray

    case _ => Array()
  }


  /**
   * This implementation does nothing.
   */
  override def inputChanged(viewer: Viewer, oldInput: AnyRef, newInput: AnyRef) {
    // do nothing.
  }


  /**
   * This implementation does nothing.
   */
  override def dispose() {
    // do nothing.
  }

}

object ScalaArrayContentProvider extends ScalaArrayContentProvider
