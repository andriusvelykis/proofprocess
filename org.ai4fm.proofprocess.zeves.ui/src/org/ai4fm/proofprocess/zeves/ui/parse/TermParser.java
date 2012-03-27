package org.ai4fm.proofprocess.zeves.ui.parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.czt.eclipse.editors.zeditor.ZEditorUtil;
import net.sourceforge.czt.eclipse.zeves.ZEvesPlugin;
import net.sourceforge.czt.eclipse.zeves.core.ZEvesResultConverter;
import net.sourceforge.czt.session.CommandException;
import net.sourceforge.czt.zeves.response.ZEvesOutput;

import org.ai4fm.proofprocess.Term;

/**
 * @author Andrius Velykis
 */
public class TermParser {

	public static List<Term> parseGoals(ZEvesOutput result) {
		
		List<Term> goals = new ArrayList<Term>();
		
		Object goalObj = result.getFirstResult();
		String goalStr = goalObj.toString().trim();
		
		if (ZEvesOutput.UNPRINTABLE_PREDICATE.equals(goalStr)) {
			// a special case - do not try parsing
			// TODO
		}
		
		try {
			
			ZEvesResultConverter.parseZEvesPred(sectInfo, sectName, goalStr);
			
			String converted = isPred ? 
					ZEvesResultConverter.convertPred(sectInfo, sectName, str, markup, textWidth, true) : 
					ZEvesResultConverter.convertParas(sectInfo, sectName, str, markup, textWidth, true);

			outputZ(output, converted);
			return output;
			
		} catch (IOException e) {
			ZEvesPlugin.getDefault().log(e);
			return withWarning("I/O problems parsing Z/Eves result: " + e.getMessage().trim(), str);
		} catch (CommandException e) {
			Throwable cause = e.getCause();
			if (cause == null) {
				cause = e;
			}
			
			String msg = "Cannot parse Z/Eves result: " + ZEditorUtil.clean(cause.getMessage()).trim();
			ZEvesPlugin.getDefault().log(msg, cause);
			return withWarning(msg, str);
		}
		
		// get all XML elements for <subgoal>
		for (Elem subgoalElem : getElems(ScalaCollections.singletonList(source), Markup.SUBGOAL())) {
			// expect a single <term> XML element inside
			Elem termElem = getSingleElem(subgoalElem.body(), Markup.TERM());
			Term ppTerm = parseTerm(termElem);
			goals.add(ppTerm);
		}
		
		return goals;
	}
	
}
