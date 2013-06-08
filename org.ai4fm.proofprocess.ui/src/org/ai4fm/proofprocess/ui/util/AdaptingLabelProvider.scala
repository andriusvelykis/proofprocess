package org.ai4fm.proofprocess.ui.util

import org.eclipse.jface.viewers.{ILabelProvider, ILabelProviderListener, ITableLabelProvider}
import org.eclipse.swt.graphics.Image

/**
 * A label provider that adapts the actual element to match the given base label provider.
 *
 * @author Andrius Velykis
 */
class AdaptingLabelProvider[B <: ILabelProvider](val base: B)(adapter: PartialFunction[Any, Any])
    extends ILabelProvider {

  override def addListener(listener: ILabelProviderListener) = base.addListener(listener)

  override def removeListener(listener: ILabelProviderListener) = base.removeListener(listener)

  override def dispose() = base.dispose()

  override def isLabelProperty(element: AnyRef, property: String): Boolean =
    base.isLabelProperty(adapt(element), property)

  override def getImage(element: AnyRef): Image = base.getImage(adapt(element))

  override def getText(element: AnyRef): String = base.getText(adapt(element))


  def adapt(elem: Any): Any = if (adapter.isDefinedAt(elem)) adapter(elem) else elem
}


/**
 * A table label provider mixin to work with AdaptingLabelProvider.
 * 
 * @author Andrius Velykis
 */
trait AdaptingTableLabelProvider extends ITableLabelProvider {

  self: AdaptingLabelProvider[_ <: ITableLabelProvider] =>

  override def getColumnImage(element: AnyRef, columnIndex: Int): Image =
    base.getColumnImage(adapt(element), columnIndex)

  override def getColumnText(element: AnyRef, columnIndex: Int): String =
    base.getColumnText(adapt(element), columnIndex)
}
