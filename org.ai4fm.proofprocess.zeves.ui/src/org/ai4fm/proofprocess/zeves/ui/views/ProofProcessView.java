package org.ai4fm.proofprocess.zeves.ui.views;

import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.MessagePage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;


public class ProofProcessView extends PageBookView {

	@Override
	protected IPage createDefaultPage(PageBook book) {
		MessagePage messagePage = new MessagePage();
		initPage(messagePage);
		messagePage.setMessage("Not interested in this part");
		messagePage.createControl(book);
		return messagePage;
	}

	@Override
	protected PageRec doCreatePage(IWorkbenchPart part) {
		
		ProofProcessPage proofProcessPage = new ProofProcessPage(getResource(part).getProject());
		initPage(proofProcessPage);
		proofProcessPage.createControl(getPageBook());
		return new PageRec(part, proofProcessPage);
	}

	@Override
	protected void doDestroyPage(IWorkbenchPart part, PageRec pageRecord) {
		pageRecord.page.dispose();
	}

	@Override
	protected IWorkbenchPart getBootstrapPart() {
		IWorkbenchPage page = getSite().getPage();
		if (page != null) {
			// check whether the active part is important to us
			IWorkbenchPart activePart = page.getActivePart();
			return isImportant(activePart) ? activePart : null;
		}
		return null;
	}

	@Override
	protected boolean isImportant(IWorkbenchPart part) {
		return getResource(part) != null;
	}
	
	private IResource getResource(IWorkbenchPart part) {
		IResource resource = (IResource) part.getAdapter(IResource.class);
		if (resource == null && part instanceof IEditorPart) {
			// check editor input
			IEditorInput input = ((IEditorPart) part).getEditorInput();
			resource = (IResource) input.getAdapter(IResource.class);
		}
		
		return resource;
	}

}
