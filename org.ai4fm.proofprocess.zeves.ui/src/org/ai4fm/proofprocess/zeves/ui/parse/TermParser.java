package org.ai4fm.proofprocess.zeves.ui.parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.czt.eclipse.editors.zeditor.ZEditorUtil;
import net.sourceforge.czt.eclipse.zeves.core.ZEvesResultConverter;
import net.sourceforge.czt.session.CommandException;
import net.sourceforge.czt.session.SectionInfo;
import net.sourceforge.czt.zeves.response.ZEvesOutput;

import org.ai4fm.proofprocess.Term;
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
			
			List<Term> goals = new ArrayList<Term>();
			
			// TODO parse and convert
			if (false) {
				ZEvesResultConverter.parseZEvesPred(null, null, goalStr);
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
