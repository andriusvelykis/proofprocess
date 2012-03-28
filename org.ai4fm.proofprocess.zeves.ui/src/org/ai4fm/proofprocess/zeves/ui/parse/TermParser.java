package org.ai4fm.proofprocess.zeves.ui.parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.czt.eclipse.editors.zeditor.ZEditorUtil;
import net.sourceforge.czt.eclipse.zeves.core.ZEvesResultConverter;
import net.sourceforge.czt.parser.util.DeleteAnnVisitor;
import net.sourceforge.czt.print.util.XmlString;
import net.sourceforge.czt.session.CommandException;
import net.sourceforge.czt.session.Key;
import net.sourceforge.czt.session.SectionInfo;
import net.sourceforge.czt.session.SectionInfoException;
import net.sourceforge.czt.session.SectionManager;
import net.sourceforge.czt.z.ast.Pred;
import net.sourceforge.czt.zeves.response.ZEvesOutput;

import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.zeves.UnparsedTerm;
import org.ai4fm.proofprocess.zeves.ZEvesProofProcessFactory;
import org.ai4fm.proofprocess.zeves.ui.ZEvesProofUIPlugin;

/**
 * @author Andrius Velykis
 */
public class TermParser {

	private static final String ZML_PRINT_KEY = ZEvesProofUIPlugin.PLUGIN_ID + ".term-zml";
	
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
				long start = System.currentTimeMillis();
				Pred goalPred = ZEvesResultConverter.parseZEvesPred((SectionManager) sectInfo, sectName, goalStr);
				System.out.println("Parsing term: " + (System.currentTimeMillis() - start));
				
				start = System.currentTimeMillis();
				String zmlPred = printZml((SectionManager) sectInfo, goalPred);
				System.out.println("Printing term to ZML: " + (System.currentTimeMillis() - start));
				
				// TODO convert the parsed predicate
				UnparsedTerm term = FACTORY.createUnparsedTerm();
				term.setDisplay(zmlPred);
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
	
	private static String printZml(SectionManager sectInfo, net.sourceforge.czt.base.ast.Term cztTerm)
			throws CommandException {
		
		// strip all annotations (e.g. LocAnn, etc) during serialization
		cztTerm.accept(new DeleteAnnVisitor());
		
		SectionManager cloned = sectInfo.clone();
		
		Key<net.sourceforge.czt.base.ast.Term> termKey = new Key<net.sourceforge.czt.base.ast.Term>(
				ZML_PRINT_KEY, net.sourceforge.czt.base.ast.Term.class);
		
		cloned.put(termKey, cztTerm);
		XmlString printed = cloned.get(new Key<XmlString>(ZML_PRINT_KEY, XmlString.class));
		
		return printed.toString();
	}
	
}
