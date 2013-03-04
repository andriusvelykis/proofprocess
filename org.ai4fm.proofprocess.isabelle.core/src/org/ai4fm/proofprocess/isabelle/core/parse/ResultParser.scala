package org.ai4fm.proofprocess.isabelle.core.parse

import org.ai4fm.proofprocess.{Term => PPTerm}
import org.ai4fm.proofprocess.core.analysis.Judgement
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory

import isabelle.{Markup, Pretty}
import isabelle.{Term_XML, XML}
import isabelle.Command.State
import isabelle.Term.Term


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
    
    val results = cmdState.results.values.toList
    
    def findInResults[A](fn: (XML.Tree => Option[A])) =
      results.map(fn).flatten.headOption
    
    // find the first list of goal terms from the trace, if available 
    val traceTerms = inTrace(results)(traceGoalTerms)
    // find the first list of goal terms (with markups) from the results, if available
    val resultMarkupTerms = inResults(results)( (_, body) => resultGoalMarkup(body) )
    
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

  /**
   * Parses goals from trace element.
   * @return a list of terms if they were parsed (empty list means no outstanding subgoals),
   *         or None if term information could not be parsed.
   */
  def traceGoalTerms(trace: XML.Body): Option[List[Term]] =
    // find "subgoal_terms" elem and then decode each "subgoal_term" in it
    trace collectFirst
      { case XML.Elem(Markup("subgoal_terms", _), subgoals) => subgoals } map
      { _ collect { case XML.Elem(Markup("subgoal_term", _), term) => Term_XML.Decode.term(term) } }


  def labelledTerms(cmdState: State,
                    labelMatch: String => Boolean): Map[String, List[PPTerm]] = {
    
    val results = cmdState.results.values.toList
    
    val lTerms = inResults(results)( (_, body) => labelledTermMarkup(labelMatch)(body) ) 
    
    val labelledPPTerms = lTerms map ( lMap => lMap mapValues markupTerms )
    
    labelledPPTerms getOrElse Map()
  }

  /**
   * Parses labelled terms (e.g. for "picking this" from structured proof) from result
   * element (writeln).
   *
   * @return  a labelled list of pairs `(render, term)` for each label type, where the `render` is a
   *          human-readable render of the term, `term` is the marked-up term XML element.
   */
  def labelledTermMarkup(labelMatch: String => Boolean)
                        (body: XML.Body): Option[Map[String, List[(String, XML.Elem)]]] = {

    val labelledTerms = collectDepthFirst(body,
      {
        case LabelledBlock(label, blockBody) if (labelMatch(label)) => {
          val terms = nestedMarkupTerms(blockBody)

          // group each term with its label
          terms map ((label, _))
        }
      })

    // group by label
    val groupedTerms = labelledTerms.flatten groupBy (_._1)

    // drop the labels from values, and get the rendering of each term
    val withRender = groupedTerms mapValues { lTerms =>
      lTerms.toList map { case (_, t) => (render(t), t) }
    }

    Some(withRender)
  }

  def nestedMarkupTerms(body: XML.Body): Stream[XML.Elem] =
    collectDepthFirst(body, { case MarkupTerm(termXml) => termXml })


  object MarkupTerm {
    def unapply(elem: XML.Tree): Option[XML.Elem] = elem match {
      case termXml @ XML.Elem(Markup(Markup.TERM, _), _) => Some(termXml)
      case _ => None
    }
  }


  object LabelledBlock {
    def unapply(elem: XML.Tree): Option[(String, XML.Body)] = elem match {
      // block with the first body element having text
      case XML.Elem(Markup(Markup.BLOCK, _), XML.Text(text) :: rest) => Some((text, rest))
      case _ => None
    }
  }

  object ProofTypeBlock {
    def unapply(elem: XML.Tree): Option[(StepProofType.StepProofType, XML.Body)] = elem match {

      case LabelledBlock(text, body) =>
        if (text.startsWith("proof (prove)")) Some((StepProofType.Prove, body))
        else if (text.startsWith("proof (state)")) Some((StepProofType.State, body))
        else if (text.startsWith("proof (chain)")) Some((StepProofType.Chain, body))
        else None

      case _ => None
    }
  }

  object ResultState {
    def unapply(elem: XML.Tree): Option[(StepProofType.StepProofType, XML.Body)] =
      elem match {
        case XML.Elem(Markup(Markup.WRITELN, _),
          XML.Elem(Markup(Markup.STATE, _), stateBody) :: _) => {
          val proofBlocks = collectDepthFirst(stateBody, {
            case ProofTypeBlock(typ, body) => (typ, body)
          })
          // assume a single proof block per writeln
          proofBlocks.headOption
        }
        case _ => None
      }
  }

  object Tracing {
    def unapply(elem: XML.Tree): Option[XML.Body] = elem match {
      case XML.Elem(Markup(Markup.TRACING, _), tracingBody) => Some(tracingBody)
      case _ => None
    }
  }

  def inResults[A](body: XML.Body)
                  (lookup: (StepProofType.StepProofType, XML.Body) => Option[A]): Option[A] = {
    
    val results = body.toStream flatMap {
      case ResultState(typ, stateBody) => lookup(typ, stateBody)
      case _ => None
    }
    // single state only, so take the first one
    results.headOption
  }
  
  def inTrace[A](body: XML.Body)(lookup: XML.Body => Option[A]): Option[A] = {
    
    val results = body.toStream flatMap {
      case Tracing(traceBody) => lookup(traceBody)
      case _ => None
    }
    // single tracing only, so take the first one
    results.headOption
  }

  /**
   * Parses goals from result element (writeln).
   * @return a list of pairs @{code (render, term)} for each subgoal,
   *         where the {@code render} is a human-readable render of the term,
   *         {@code term} is the marked-up term XML element.
   */
  def resultGoalMarkup(body: XML.Body): Option[List[(String, XML.Elem)]] = {

    val subgoalOpts = collectDepthFirst(body, {
      case XML.Elem(Markup(Markup.SUBGOAL, _), subgoalBody) => {
        val terms = nestedMarkupTerms(subgoalBody)
        terms.headOption
      }
    })

    // get the rendering of each term as well
    val subgoals = subgoalOpts.flatten.map({ term => (render(term), term) })
    if (!subgoals.isEmpty) {
      // found subgoals - return them
      Some(subgoals.toList)
    } else {
      // no subgoals found, check if "no subgoals" is there
      if (isNoSubgoals(body)) {
        // no subgoals - return empty list
        Some(Nil)
      } else {
        // neither "no subgoals" is available, cannot find the goals then
        None
      }
    }
  }

  def collectDepthFirst[A](elems: XML.Body, pf: PartialFunction[XML.Tree, A]): Stream[A] = {

    val matchElemList = pf andThen (e => Stream(e))

    def matchNested: PartialFunction[XML.Tree, Stream[A]] = {
      // for an element, append collected results from each of its children
      case XML.Elem(_, body) => collectDepthFirst(body, pf)
      case _ => Stream[A]()
    }

    val matchElemOrNested = matchElemList orElse matchNested

    // for each elem, either match or match nested
    (elems.toStream collect matchElemOrNested).flatten
  }

  def isNoSubgoals(body: XML.Body): Boolean =
    body exists { elem =>
      elem match {
        case XML.Text(text) if (text.contains("No subgoals!")) => true
        case XML.Elem(_, body) => isNoSubgoals(body)
        case _ => false
      }
    }
  
  def isError(elem: XML.Tree): Boolean = elem match {
    case XML.Elem(Markup(Markup.ERROR, _), _) => true
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

  object StepProofType extends Enumeration {
    type StepProofType = Value
    val Prove, State, Chain = Value
  }
  
  def stepProofType(cmdState: State): Option[StepProofType.StepProofType] = {

    val results = cmdState.results.values.toList
    
    val typeOpt = inResults(results)( (typ, body) => Some(typ) )
    typeOpt
  }
  
//  private def proofTypeMarkup(body: XML.Body): Option[StepProofType.StepProofType] = {
//
//    val foundTypes = collectDepthFirst(body, { case ProofTypeBlock(typ) => typ } )
//    foundTypes.headOption
//  }
  
  
  def splitAssmsGoal(term: PPTerm): Judgement[PPTerm] = term match {
    // TODO implement splitting of term into assumptions + goal
    case _ => Judgement(Nil, term)
  }
  
//  def splitAssmsGoalText(termStr: String): (List[String], String) = {
//    
//    // TODO shorthand? [[Assm; Assm2]]==>Goal -- or are these not in proof goals?
//    
//  }
}
