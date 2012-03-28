package org.ai4fm.proofprocess.zeves.ui.parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.czt.eclipse.editors.zeditor.ZEditorUtil;
import net.sourceforge.czt.eclipse.zeves.core.ZEvesResultConverter;
import net.sourceforge.czt.session.CommandException;
import net.sourceforge.czt.session.Markup;
import net.sourceforge.czt.session.SectionInfo;
import net.sourceforge.czt.session.SectionManager;
import net.sourceforge.czt.z.ast.Pred;
import net.sourceforge.czt.zeves.response.ZEvesOutput;

import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.zeves.CztTerm;
import org.ai4fm.proofprocess.zeves.UnparsedTerm;
import org.ai4fm.proofprocess.zeves.ZEvesProofProcessFactory;
import org.ai4fm.proofprocess.zeves.ui.ZEvesProofUIPlugin;

/**
 * @author Andrius Velykis
 */
public class TermParser {

	private static final ZEvesProofProcessFactory FACTORY = ZEvesProofProcessFactory.eINSTANCE;
	
	public static List<Term> parseGoals(SectionInfo sectInfo, String sectName, ZEvesOutput result) {
		
		Object goalObj = result.getFirstResult();
		String goalStr = goalObj.toString().trim();
		
		if (ZEvesOutput.UNPRINTABLE_PREDICATE.equals(goalStr)) {
			// a special case - do not try parsing
			
			UnparsedTerm term = FACTORY.createUnparsedTerm();
			term.setDisplay(goalStr);
			return Collections.<Term>singletonList(term);
		}
		
		try {
			
			if (sectInfo instanceof SectionManager) {
				// a bit of a hack, will need to review whether a sectInfo could be enough eventually
				
				List<Term> goals = new ArrayList<Term>();
				
				// parse the goal from Z/Eves response
				Pred goalPred = ZEvesResultConverter.parseZEvesPred((SectionManager) sectInfo, sectName, goalStr);
				
				// pretty-print the goal back, so that we get uniform CZT-printing, instead
				// of using Z/Eves conversion to CZT unicode
				String goalPrinted = ZEvesResultConverter.printResult((SectionManager) sectInfo,
						sectName, goalPred, Markup.UNICODE, 0, true);

				// create a term encapsulating the CZT ast.Term.
				// It will be serialised to XML when saved
				CztTerm term = FACTORY.createCztTerm();
				term.setDisplay(goalPrinted);
				term.setTerm(goalPred);
				goals.add(term);
				
				return goals;
			}
			
		} catch (IOException e) {
			ZEvesProofUIPlugin.log(e);
		} catch (CommandException e) {
			Throwable cause = e.getCause();
			if (cause == null) {
				cause = e;
			}
			
			String msg = "Cannot parse Z/Eves result: " + ZEditorUtil.clean(cause.getMessage()).trim();
			ZEvesProofUIPlugin.log(msg, e);
		}
		
		// problems parsing - just output unparsed
		UnparsedTerm term = FACTORY.createUnparsedTerm();
		term.setDisplay(goalStr);
		return Collections.<Term>singletonList(term);
	}
	
}
