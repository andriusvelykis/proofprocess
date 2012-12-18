package org.ai4fm.proofprocess.isabelle.core.parse

import isabelle.Command.State
import isabelle.Isabelle_Markup
import isabelle.Markup
import isabelle.Pretty
import isabelle.Term.Term
import isabelle.Term_XML
import isabelle.XML
import org.ai4fm.proofprocess.{Term => PPTerm}
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory


object ResultParser {
  
  val factory = IsabelleProofProcessFactory.eINSTANCE
  
  /** Retrieves goal terms from the command results.
    * <p>
    * It gives priority to the actual Isabelle terms passed via tracing mechanism. These currently
    * require an extension to Isabelle to output them. Returns IsaTerms in that case.
    * </p>
    * <p>
    * If the tracing terms are not available, uses the XML of marked-up terms used for user
    * display of goals. The XML structures are wrapped into MarkupTerm structures.
    * </p>
    */
  def goalTerms(cmdState: State): Option[List[PPTerm]] = {
    
    val results = cmdState.results.values
    
    def findInResults[A](fn: (XML.Tree => Option[A])) =
      results.map(fn).flatten.headOption
    
    // find the first list of goal terms from the trace, if available 
    val traceTerms = findInResults(traceGoalTerms)
    // find the first list of goal terms (with markups) from the results, if available
    val resultMarkupTerms = findInResults(resultGoalMarkup)
    
    if (traceTerms.isDefined) {

      // get the rendered term strings from the results
      // note there can be less of them, so pad (fill) the list with empty renders to accommodate
      val rendered = resultMarkupTerms.getOrElse(Nil).map(_._1)
      val renderedFull = rendered.padTo(traceTerms.get.size, "")
      
      val renderTerms = renderedFull.zip(traceTerms.get)
      val ppTerms = renderTerms.map(Function.tupled(isaTerm))
      
      Some(ppTerms)
    } else if (resultMarkupTerms.isDefined) {
      
      val ppTerms = markupTerms(resultMarkupTerms.get)
      
      Some(ppTerms)
    } else {
      None
    }
  }


  /** Parses goals from trace element.
    * @return a list of terms if they were parsed (empty list means no outstanding subgoals), 
    *         or None if term information could not be parsed.
    */
  def traceGoalTerms(resultElem: XML.Tree): Option[List[Term]] = resultElem match {
    case XML.Elem(Markup(Isabelle_Markup.TRACING, _), trace) =>
      // find "subgoal_terms" elem and then decode each "subgoal_term" in it
      trace collectFirst
        { case XML.Elem(Markup("subgoal_terms", _), subgoals) => subgoals } map
        { _ collect { case XML.Elem(Markup("subgoal_term", _), term) => Term_XML.Decode.term(term) } }
    case _ => None
  }

  def nestedMarkupTerms(elem: XML.Tree): List[XML.Elem] =
    collectDepthFirst(elem, { case MarkupTerm(termXml) => termXml })


  object MarkupTerm {
    def unapply(elem: XML.Tree): Option[XML.Elem] = elem match {
      case termXml @ XML.Elem(Markup(Isabelle_Markup.TERM, _), _) => Some(termXml)
      case _ => None
    }
  }

  
  /** Parses goals from result element (writeln).
    * @return a list of pairs @{code (render, term)} for each subgoal,
    *         where the {@code render} is a human-readable render of the term, 
    *         {@code term} is the marked-up term XML element.
    */
  def resultGoalMarkup(resultElem: XML.Tree): Option[List[(String, XML.Elem)]] = resultElem match {
    case output @ XML.Elem(Markup(Isabelle_Markup.WRITELN, _), _) =>
      val subgoalOpts = collectDepthFirst(output,
        {
          case subgoal @ XML.Elem(Markup(Isabelle_Markup.SUBGOAL, _), _) => {
            val terms = nestedMarkupTerms(subgoal)
            terms.headOption
          }
        })
      
      // get the rendering of each term as well
      val subgoals = subgoalOpts.flatten.map({ term => (render(term), term) })
      if (!subgoals.isEmpty) {
        // found subgoals - return them
        Some(subgoals)
      } else {
        // no subgoals found, check if "no subgoals" is there
        if (isNoSubgoals(output)) {
          // no subgoals - return empty list
          Some(Nil)
        } else {
          // neither "no subgoals" is available, cannot find the goals then
          None
        }
      }
    case _ => None
  }
  
  def collectDepthFirst[A](elem: XML.Tree, pf: PartialFunction[XML.Tree, A]): List[A] = {
    if (pf.isDefinedAt(elem)) {
      List(pf(elem))
    } else elem match {
      // for an element, append collected results from each of its children
      case XML.Elem(_, body) => body.foldRight(List[A]()){ (e, el) => collectDepthFirst(e, pf) ::: el }
      case _ => Nil
    }
  }
  
  def isNoSubgoals(elem: XML.Tree): Boolean = elem match {
    case XML.Text(text) if (text.contains("No subgoals!")) => true
    case XML.Elem(_, body) => body exists isNoSubgoals
    case _ => false
  }
  
  def isError(elem: XML.Tree): Boolean = elem match {
    case XML.Elem(Markup(Isabelle_Markup.ERROR, _), _) => true
    case _ => false
  }
  
  def render(elem: XML.Tree): String = Pretty.str_of(List(elem))
  
  def isaTerm(display: String, term: Term): PPTerm = {
    val ppTerm = factory.createIsaTerm()
    ppTerm.setDisplay(display)
    ppTerm.setTerm(term)
    ppTerm
  }
  
  def markupTerm(display: String, term: XML.Tree): PPTerm = {
    val ppTerm = factory.createMarkupTerm()
    ppTerm.setDisplay(display)
    ppTerm.setTerm(term)
    ppTerm
  }
  
  def markupTerms(termDisplays: List[(String, XML.Tree)]): List[PPTerm] =
    termDisplays map Function.tupled(markupTerm)
  
}
