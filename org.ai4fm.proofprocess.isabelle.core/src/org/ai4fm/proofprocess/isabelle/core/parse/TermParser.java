package org.ai4fm.proofprocess.isabelle.core.parse;

import isabelle.Markup;
import isabelle.Pretty;
import isabelle.XML.Elem;
import isabelle.XML.Text;
import isabelle.XML.Tree;
import isabelle.scala.ScalaCollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.isabelle.CompositeTerm;
import org.ai4fm.proofprocess.isabelle.DisplayTerm;
import org.ai4fm.proofprocess.isabelle.IsaTerm;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory;
import org.ai4fm.proofprocess.isabelle.TermKind;

import scala.Tuple2;
import scala.collection.Iterator;

/**
 * @author Andrius Velykis
 */
public class TermParser {

	private static IsabelleProofProcessFactory FACTORY = IsabelleProofProcessFactory.eINSTANCE;
	
	private static Set<String> IGNORE_MARKUPS = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
			Markup.BREAK())));
	
	public static List<Term> parseGoals(Tree source) {
		
		List<Term> goals = new ArrayList<Term>();
		
		// get all XML elements for <subgoal>
		for (Elem subgoalElem : getElems(ScalaCollections.singletonList(source), Markup.SUBGOAL())) {
			// expect a single <term> XML element inside
			Elem termElem = getSingleElem(subgoalElem.body(), Markup.TERM());
			Term ppTerm = parseTerm(termElem);
			goals.add(ppTerm);
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
	
	private static Term parseTerm(Elem termElem) {
		// do the parsing - ensuring its TERM
		if (!Markup.TERM().equals(termElem.markup().name())) {
			throw new IllegalArgumentException("Expected <term>, found: " + termElem);
		}
		
		CompositeTermParser parser = new CompositeTermParser();
		parser.parse(termElem.body());
		
		Term term = parser.rootTerm;
		if (term == null) {
			throw new IllegalStateException("Invalid term: " + termElem);
		}
		
		return term;
	}
	
	private static class CompositeTermParser {
		
		private final Stack<DisplayTerm> termStack = new Stack<DisplayTerm>();
		private Term rootTerm;
		
		public void parse(scala.collection.immutable.List<Tree> source) {
			
			for (Iterator<Tree> it = source.iterator(); it.hasNext();) {
				Tree e = it.next();
				
				if (e instanceof Text) {
					
					String content = ((Text) e).content();
					
					DisplayTerm top = termStack.peek();
					if (top instanceof IsaTerm) {
						IsaTerm isaTerm = (IsaTerm) top;
						if (isaTerm.getName() == null) {
							isaTerm.setName(content);
						} else {
							// usually happens for CONST entities, they have a fully-qualified name
							// and a "display" text inside
//							System.out.println("Term name already set. Text: " + content + ";\nTerm: " + isaTerm);
						}
					} else {
						// usually happens for CompositeTerms, when parentheses are used, 
						// e.g. composite would have ["(", IsaTerm, ")"]. 
//						System.out.println("Text element inside a non-term. Text: " + content + ";\nTerm: " + top);
					}
					
				} else if (e instanceof Elem) {
					Elem elem = (Elem) e;
					Markup markup = elem.markup();
					
					if (Markup.BLOCK().equals(markup.name()) && getElemCount(elem.body()) <= 1) {
			            // single element inside a block - continue on the inner element,
			            // skipping the outer BLOCK. 
						// Note that we ignore the inner Texts in this check.
						parse(elem.body());
					} else if (ignoreElem(markup.name())){
						// Some elements are in result XML for pretty-printing
						// only, and should be ignored in parsing altogether, e.g. <break>.
						// Do nothing for this elem - continue onto the next one
						continue;
					} else {
						
						DisplayTerm term = parseElem(markup);
						
						// render the element for display
						term.setDisplay(renderDisplay(elem));
						
						addToParent(term, source);
						
			            // push the term onto stack, and continue in its body
			            termStack.push(term);
			            parse(elem.body());
						termStack.pop();
					}
					
				} else {
					throw new IllegalStateException("Unsupported XML element: " + e);
				}
			}
		}
		
		private int getElemCount(scala.collection.immutable.List<Tree> body) {
			int count = 0;
			
			for (Iterator<Tree> it = body.iterator(); it.hasNext();) {
				if (it.next() instanceof Elem) {
					count++;
				}
			}
			
			return count;
		}
		
		private boolean ignoreElem(String markup) {
			return IGNORE_MARKUPS.contains(markup);
		}
		
		private void addToParent(DisplayTerm term, scala.collection.immutable.List<Tree> source) {
			if (termStack.isEmpty()) {
				// this is the root term, others will be added to it
				rootTerm = term;
			} else {
				// the top should be composite - add to it
				DisplayTerm top = termStack.peek();
				if (top instanceof CompositeTerm) {
					// add the term to the parent composite
					((CompositeTerm) top).getTerms().add(term);
				} else {
					// TODO this happens for <fixed><free>
	                System.out.println("Invalid top of the stack: " + top + ";\nterm: " + term + ";\nsource: " + source);
//	                throw new IllegalStateException("Top of the stack: " + top + ";\nterm: " + term + ";\nsource: " + source);
				}
			}
		}
		
		private DisplayTerm parseElem(Markup elemMarkup) {
			
			if (Markup.BLOCK().equals(elemMarkup.name())) {
				// TODO render the display?
				return FACTORY.createCompositeTerm();
			}
			
			if (Markup.ENTITY().equals(elemMarkup.name())) {
				IsaTerm isaTerm = FACTORY.createIsaTerm();
				
				Map<String, String> props = propsMap(elemMarkup.properties());
				isaTerm.setName(props.get(Markup.NAME()));
				
				String kindStr = props.get(Markup.KIND());
				if (Markup.CONSTANT().equals(kindStr)) {
					isaTerm.setKind(TermKind.CONST);
				} else {
					throw new IllegalStateException("Unknown entity kind: " + kindStr);
				}
				
				return isaTerm;
			}
			
			TermKind termKind = getTermKind(elemMarkup.name());
			if (termKind != null) {
				IsaTerm isaTerm = FACTORY.createIsaTerm();
				isaTerm.setKind(termKind);
				return isaTerm;
			}
			
			throw new IllegalStateException("Unsupported markup: " + elemMarkup);
		}
		
		private TermKind getTermKind(String markup) {
			if (Markup.BOUND().equals(markup)) {
				return TermKind.BOUND;
			}
			
			if (Markup.VAR().equals(markup)) {
				return TermKind.VAR;
			}
			
			if (Markup.FIXED().equals(markup)) {
				return TermKind.FIXED;
			}
			
			if (Markup.FREE().equals(markup)) {
				return TermKind.FREE;
			}
			
			if ("numeral".equals(markup)) {
				return TermKind.NUMERAL;
			}
			
			if (Markup.SKOLEM().equals(markup)) {
				return TermKind.SKOLEM;
			}
			
			return null;
		}
		
		private Map<String, String> propsMap(
				scala.collection.immutable.List<Tuple2<String, String>> props) {

			Map<String, String> propsMap = new HashMap<String, String>();

			for (Iterator<Tuple2<String, String>> it = props.iterator(); it.hasNext();) {
				Tuple2<String, String> prop = it.next();
				propsMap.put(prop._1(), prop._2());
			}

			return propsMap;
		}
		
		private String renderDisplay(Elem elem) {
			return Pretty.str_of(ScalaCollections.<Tree>singletonList(elem));
		}
	}
}
