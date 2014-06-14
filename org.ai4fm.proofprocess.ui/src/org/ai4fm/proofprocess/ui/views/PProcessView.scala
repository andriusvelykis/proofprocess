package org.ai4fm.proofprocess.ui.views

import org.ai4fm.proofprocess.core.store.IPProcessStoreProvider
import org.ai4fm.proofprocess.core.util.PProcessUtil

import org.eclipse.core.resources.IResource
import org.eclipse.ui.{IEditorPart, IWorkbenchPart}
import org.eclipse.ui.ide.ResourceUtil
import org.eclipse.ui.part.{IPage, MessagePage, PageBook, PageBookView}
import org.eclipse.ui.part.PageBookView.PageRec


class PProcessView extends PageBookView {

  override protected def createDefaultPage(book: PageBook): IPage = {
    val messagePage = new MessagePage
    initPage(messagePage)

    messagePage.setMessage("Proof process is not available.");
    messagePage.createControl(book);
    messagePage;
  }

  override protected def doCreatePage(part: IWorkbenchPart): PageRec = {
    // must resolve without None
    val ppStoreProvider = getPProcessStore(part).get

    // create a page with the proof store provider
    val proofProcessPage = new PProcessPage(this, ppStoreProvider, Some(ppStoreProvider))
    initPage(proofProcessPage)
    proofProcessPage.createControl(getPageBook())

    new PageRec(part, proofProcessPage)
  }

  override protected def doDestroyPage(part: IWorkbenchPart, pageRecord: PageRec) {
    pageRecord.page.dispose()
    pageRecord.dispose()
  }

  override protected def getBootstrapPart(): IWorkbenchPart = {
    val part = Option(getSite.getPage) flatMap { p => Option(p.getActivePart) } filter { isImportant }
    part.orNull
  }

  override protected def isImportant(part: IWorkbenchPart) = getPProcessStore(part).isDefined

  private def getPProcessStore(part: IWorkbenchPart): Option[IPProcessStoreProvider] =
    getResourceAdapter(part, classOf[IPProcessStoreProvider])
  
  private def getResourceAdapter[A](part: IWorkbenchPart, adapterClass: Class[A]): Option[A] =
    // force load adapters (likely in different plugins, which may be not loaded yet)
    getResource(part) flatMap { resource => PProcessUtil.getAdapter(resource, adapterClass, true) }

  
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
