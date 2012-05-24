package org.ai4fm.proofprocess.ui.views;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.PageBookView;

/**
 * A workaround for the fact that PageRec class is "static protected", which cannot be accessed by
 * Scala 2.9. It is solved in Scala 2.10, so remove when upping the dependencies.
 * 
 * See http://issues.scala-lang.org/browse/SI-1806 for details
 * 
 * @author Andrius Velykis
 */
public abstract class ScalaPageBookView extends PageBookView {

	@Override
	protected PageBookView.PageRec doCreatePage(IWorkbenchPart part) {
		return new PageBookView.PageRec(part, doCreatePage0(part));
	}
	
	protected abstract IPage doCreatePage0(IWorkbenchPart part);

	@Override
	protected void doDestroyPage(IWorkbenchPart part, PageBookView.PageRec pageRecord) {
		doDestroyPage(part, pageRecord.page);
	}
	
	protected abstract void doDestroyPage(IWorkbenchPart part, IPage page);

}
