package org.ai4fm.proofprocess.ui.internal

import java.lang.management.ManagementFactory
import java.net.{MalformedURLException, URL}

import scala.collection.JavaConverters._

import org.eclipse.jface.dialogs.{IDialogConstants, MessageDialog}
import org.eclipse.jface.layout.GridDataFactory
import org.eclipse.swt.SWT
import org.eclipse.swt.events.{SelectionAdapter, SelectionEvent}
import org.eclipse.swt.widgets.{Composite, Control, Link, Shell}
import org.eclipse.ui.{PartInitException, PlatformUI}

import PProcessUIPlugin.{error, log}


/**
 * Performs and reports diagnostics needed to run ProofProcess framework.
 *
 * Notifies the user if appropriate Java PermGen size is not available.
 *
 * @author Andrius Velykis
 */
object PProcessDiagnostics {

  private def MEGABYTE: Long = 1024 * 1024

  private def PP_PERMGEN_SIZE = 128

  def checkPermGenSize() = getPermGenSize() match {
    case Some(size) => {
      val sizeMB = size / MEGABYTE

      if (sizeMB < PP_PERMGEN_SIZE) {
        reportSmallPermGen(sizeMB)
      }
    }

    case None => log(error(msg = Some("Unable to find PermGen size")))
  }


  private def getPermGenSize(): Option[Long] = {
    val memInfos = ManagementFactory.getMemoryPoolMXBeans.asScala

    val permGenNames = Set("Perm Gen", "CMS Perm Gen")
    val permGen = memInfos find (mem => permGenNames.contains(mem.getName))

    val max = permGen flatMap (mem => Option(mem.getUsage)) map (_.getMax)
    max filter (_ > 0)
  }


  private def reportSmallPermGen(currentMB: Long) {

    val workbench = PlatformUI.getWorkbench
    workbench.getDisplay.asyncExec(new Runnable {
      override def run() {
        val window = workbench.getActiveWorkbenchWindow

        val msg = 
          "The available Java PermGen space can be too small for running the ProofProcess framework.\n\n" +
          "Ensure your maximum PermGen size is at least 128M.\n" +
          "Current maximum PermGen size: " + currentMB + "M.\n\n" +
          "Set the maximum PermGen size in by adding the following argument to eclipse.ini:\n" +
          "-XX:MaxPermSize=128M\n\n" +
          "Read more here:"

        val link = "http://wiki.eclipse.org/FAQ_How_do_I_increase_the_permgen_size_available_to_Eclipse%3F"

        if (window != null) {
          val dialog = new ErrorDialogWithLink(
            window.getShell(), "Max PermGen Size Too Small", msg, link)

          dialog.open()
        } else {
          // at least report to the error log
          log(error(msg = Some(msg + " " + link)))
        }
      }
    })

  }


  private class ErrorDialogWithLink(parentShell: Shell,
                                    dialogTitle: String,
                                    dialogMessage: String,
                                    hyperlink: String)
      extends MessageDialog(parentShell, dialogTitle, null, dialogMessage,
                            MessageDialog.ERROR, Array(IDialogConstants.OK_LABEL), 0) {

    setShellStyle(getShellStyle | SWT.SHEET)

    override def createMessageArea(composite: Composite): Control = {

      val parent = super.createMessageArea(composite)

      val dummy = new Composite(composite, SWT.NONE)
      dummy.setLayoutData(GridDataFactory.fillDefaults.hint(0, 0).create)

      // place the link in the custom area
      val link = new Link(composite, SWT.WRAP)
      val linkText = "<a href=\"" + hyperlink + "\">" + hyperlink + "</a>"
      link.setText(linkText)

      link.addSelectionListener(new SelectionAdapter {
        override def widgetSelected(e: SelectionEvent) {
          openLinkInBrowser(e.text)
        }
      })

      parent
    }

    private def openLinkInBrowser(link: String) {
      try {
        // Open default external browser
        val browser = PlatformUI.getWorkbench.getBrowserSupport.getExternalBrowser
        browser.openURL(new URL(link))
      } catch {
        case ex: PartInitException => log(error(Some(ex)))
        case ex: MalformedURLException => log(error(Some(ex)))
      }
    }
  }

}
