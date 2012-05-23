package org.ai4fm.proofprocess.zeves.ui.views;

import org.ai4fm.proofprocess.core.store.IProofStoreProvider;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
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
		
		IResource res = getResource(part);

		// TODO message if unavailable
		IProofStoreProvider storeProvider = (IProofStoreProvider) getAdapter(res, IProofStoreProvider.class);
		
		ProofProcessPage proofProcessPage = new ProofProcessPage(storeProvider, res.getProject());
		initPage(proofProcessPage);
		proofProcessPage.createControl(getPageBook());
		return new PageRec(part, proofProcessPage);
	}
	
	/**
	 * A special adapter lookup that loads plugins for more adapters if it cannot resolve the type.
	 * 
	 * See https://bugs.eclipse.org/bugs/show_bug.cgi?id=82973 for the bug that adapter factories
	 * are not queried if the plug-in is not loaded.
	 * 
	 * @param element
	 * @param adapterType
	 * @return
	 */
	public static Object getAdapter(IAdaptable element, Class<?> adapterType) {
		Object o = element.getAdapter(adapterType);
		if (o != null) {
			return o;
		}
		
		// force load plugins and lookup again
		return Platform.getAdapterManager().loadAdapter(element, adapterType.getName());
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
