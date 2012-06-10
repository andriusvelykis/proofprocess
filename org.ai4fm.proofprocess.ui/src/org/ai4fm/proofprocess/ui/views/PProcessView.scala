package org.ai4fm.proofprocess.ui.views

import org.ai4fm.proofprocess.core.store.IProofStoreProvider

import org.eclipse.core.resources.IResource
import org.eclipse.ui.IEditorPart
import org.eclipse.ui.IWorkbenchPart
import org.eclipse.ui.ide.ResourceUtil
import org.eclipse.ui.part.IPage
import org.eclipse.ui.part.MessagePage
import org.eclipse.ui.part.PageBook


class PProcessView extends ScalaPageBookView {

  override protected def createDefaultPage(book: PageBook): IPage = {
    val messagePage = new MessagePage
    initPage(messagePage)

    messagePage.setMessage("Proof process is not available.");
    messagePage.createControl(book);
    messagePage;
  }

  override protected def doCreatePage0(part: IWorkbenchPart): IPage = {
    // must resolve without None
    val proofStoreProvider = getProofStore(part).get

    // create a page with the proof store provider
    val proofProcessPage = new PProcessPage(this, proofStoreProvider)
    initPage(proofProcessPage)
    proofProcessPage.createControl(getPageBook())

    proofProcessPage
  }

  protected def doDestroyPage(part: IWorkbenchPart, page: IPage) =
    page.dispose()

  override protected def getBootstrapPart(): IWorkbenchPart = {
    val part = Option(getSite.getPage) flatMap { p => Option(p.getActivePart) } filter { isImportant }
    part.orNull
  }

  override protected def isImportant(part: IWorkbenchPart) = getProofStore(part).isDefined

  private def getProofStore(part: IWorkbenchPart): Option[IProofStoreProvider] =
    // get the resource and try to resolve the proof store via adapter
    getResource(part) flatMap { resource =>
      // force load adapters (likely in different plugins, which may be not loaded yet)
      Option(ResourceUtil.getAdapter(resource, classOf[IProofStoreProvider], true).asInstanceOf[IProofStoreProvider])
    }
  
  private def getResource(part: IWorkbenchPart): Option[IResource] =
    part match {
      case editor: IEditorPart => {
        // cast to Option otherwise wrong ResourceUtil.getFile() is chosen
        val input = editor.getEditorInput().asInstanceOf[Object]

        // try resolving as file (a number of options there)
        // otherwise, try at least resource
        Option(ResourceUtil.getFile(input)) orElse Option(ResourceUtil.getResource(input))
      }
      case _ => None
    }

}
