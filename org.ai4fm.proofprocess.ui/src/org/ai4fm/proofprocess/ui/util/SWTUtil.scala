package org.ai4fm.proofprocess.ui.util

import scala.language.implicitConversions

import org.eclipse.jface.util.{IPropertyChangeListener, PropertyChangeEvent}
import org.eclipse.jface.viewers._
import org.eclipse.swt.events._
import org.eclipse.swt.widgets.Control


/**
 * Adapted from scala.tools.eclipse.util.SWTUtils
 * 
 * @author Andrius Velykis
 */
object SWTUtil {
  
  implicit def fnToModifyListener(f: ModifyEvent => Unit): ModifyListener = new ModifyListener {
    def modifyText(e: ModifyEvent) = f(e)
  }

  implicit def fnToValListener(f: VerifyEvent => Unit) = new VerifyListener {
    def verifyText(e: VerifyEvent) = f(e)
  }

  implicit def fnToSelectionAdapter(p: SelectionEvent => Any): SelectionAdapter =
    new SelectionAdapter() {
      override def widgetSelected(e: SelectionEvent) { p(e) }
    }

  implicit def noArgFnToSelectionAdapter(p: () => Any): SelectionAdapter =
    new SelectionAdapter() {
      override def widgetSelected(e: SelectionEvent) { p() }
    }
  
  implicit def noArgFnToMouseUpListener(f: () => Any): MouseAdapter = new MouseAdapter {
    override def mouseUp(me: MouseEvent) = f()
  }

  implicit def fnToPropertyChangeListener(p: PropertyChangeEvent => Any): IPropertyChangeListener =
    new IPropertyChangeListener() {
      def propertyChange(e: PropertyChangeEvent) { p(e) }
    }

  implicit def noArgFnToSelectionChangedListener(p: () => Any): ISelectionChangedListener =
    new ISelectionChangedListener {
      def selectionChanged(event: SelectionChangedEvent) { p() }
    }

  implicit def fnToDoubleClickListener(p: DoubleClickEvent => Any): IDoubleClickListener =
    new IDoubleClickListener {
      def doubleClick(event: DoubleClickEvent) { p(event) }
    }

  implicit def noArgFnToDoubleClickListener(p: () => Any): IDoubleClickListener =
    new IDoubleClickListener {
      def doubleClick(event: DoubleClickEvent) { p() }
    }

  implicit def control2PimpedControl(control: Control): PimpedControl = new PimpedControl(control)

  class PimpedControl(control: Control) {

    def onKeyReleased(p: KeyEvent => Any) {
      control.addKeyListener(new KeyAdapter {
        override def keyReleased(e: KeyEvent) { p(e) }
      })
    }

    def onKeyReleased(p: => Any) {
      control.addKeyListener(new KeyAdapter {
        override def keyReleased(e: KeyEvent) { p }
      })
    }

    def onFocusLost(p: => Any) {
      control.addFocusListener(new FocusAdapter {
        override def focusLost(e: FocusEvent) { p }
      })
    }

  }

  def selectionElement(sel: ISelection): Option[Any] =
    Option(sel) match {
      case Some(ss: IStructuredSelection) => Option(ss.getFirstElement)
      case _ => None
    }

}
