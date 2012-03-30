package org.ai4fm.proofprocess.isabelle.core.parse;

import isabelle.Markup;
import isabelle.Pretty;
import isabelle.XML.Elem;
import isabelle.XML.Tree;
import isabelle.scala.ScalaCollections;

import java.util.ArrayList;
import java.util.List;

import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.isabelle.IsaTerm;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory;

import scala.collection.Iterator;

/**
 * @author Andrius Velykis
 */
public class TermParser {

	private static final IsabelleProofProcessFactory FACTORY = IsabelleProofProcessFactory.eINSTANCE;
	
	public static List<Term> parseGoals(Tree source) {
		
		List<Term> goals = new ArrayList<Term>();
		
		// get all XML elements for <subgoal>
		for (Elem subgoalElem : getElems(ScalaCollections.singletonList(source), Markup.SUBGOAL())) {
			// expect a single <term> XML element inside
			Elem termElem = getSingleElem(subgoalElem.body(), Markup.TERM());
			
			// wrap the <term> XML into an IsaTerm
			
			IsaTerm isaTerm = FACTORY.createIsaTerm();
			isaTerm.setTerm(termElem);
			
			// render the element for display
			isaTerm.setDisplay(renderDisplay(termElem));
			
			goals.add(isaTerm);
		}
		
		return goals;
	}
	
	private static List<Elem> getElems(scala.collection.immutable.List<Tree> source, String markup) {
		List<Elem> elems = new ArrayList<Elem>();
		collectElems(source, markup, elems);
		return elems;
	}
	
	private static void collectElems(scala.collection.immutable.List<Tree> source, String markup,
			List<Elem> elems) {
		for (Iterator<Tree> it = source.iterator(); it.hasNext();) {
			Tree obj = it.next();
			if (obj instanceof Elem) {
				Elem elem = (Elem) obj;

				if (markup.equals(elem.markup().name())) {
					// found elem with correct markup
					elems.add(elem);
				} else {
					// recurse deeper
					collectElems(elem.body(), markup, elems);
				}
			}
		}
	}
	
	private static Elem getSingleElem(scala.collection.immutable.List<Tree> body, String markup) {
		List<Elem> elems = getElems(body, markup);
		if (elems.size() == 1) {
			// if single element found, return it
			return elems.get(0);
		}
		
		// invalid cases
		if (elems.isEmpty()) {
			throw new IllegalArgumentException("No <" + markup + "> found: " + body);
		}
		
		throw new IllegalArgumentException("Multiple <" + markup + "> found: " + body);
	}
	
	private static String renderDisplay(Tree elem) {
		return Pretty.str_of(ScalaCollections.singletonList(elem));
	}
	
	public static boolean isError(Tree elem) {
		return (elem instanceof Elem) && Markup.ERROR().equals(((Elem) elem).markup().name());
	}
	
	public static boolean isNoSubgoals(Tree elem) {
		if (isError(elem)) {
			return false;
		}
		
		// TODO fix a quick workaround
		String text = renderDisplay(elem);
		return text.contains("No subgoals!");
	}
	
	public static int getGoalCount(Tree source) {
		return getElems(ScalaCollections.singletonList(source), Markup.SUBGOAL()).size();
	}
}
