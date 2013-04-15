package org.ai4fm.proofprocess.isabelle.core.parse

import org.ai4fm.proofprocess.core.analysis.{Assumption, EqTerm, Judgement}
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory
import org.ai4fm.proofprocess.isabelle.core.data.{EqIsaTerm, EqMarkupTerm}

import isabelle.{Markup, Pretty}
import isabelle.{Term_XML, XML}
import isabelle.Command.State
import isabelle.Term.Term


/**
 * @author Andrius Velykis
 */
object ResultParser {
  
  val factory = IsabelleProofProcessFactory.eINSTANCE


  /**
   * Parses command results, such as assumptions, goals, proof type from the command state.
   */
  def parseCommandResults(commandState: State): Option[StepResults] = {

    val factTerms = ResultParser.parseFacts(commandState)

    val inAssms = collectMapped(factTerms, ResultParser.IN_ASSM_LABELS)
    val outAssms = collectMapped(factTerms, ResultParser.OUT_ASSM_LABELS)
    
    val isByCmd = "by" == commandState.command.name

    val stepTypeOpt = ResultParser.stepProofType(commandState)
    // last step 'by' has no output, so assume 'prove' step type
    val stepType = stepTypeOpt orElse ( if (isByCmd) Some(StepProofType.Prove) else None ) 
    
    // workaround for "by" not outputting goals in "markup" term case
    // also "by" when used in forward proof can go into "state" proof and output outstanding
    // goals - so we replace it with "finished", i.e. empty list of goals
    val goals = if (isByCmd) Some(Nil) else ResultParser.goalTerms(commandState)
    
    // only produce results if step type is defined
    stepType map ( StepResults(commandState, _, inAssms, outAssms, goals) )
  }
  
  private def collectMapped[A, B](mapped: Map[A, List[B]], collect: Set[A]): List[B] = {
    val results = collect.toList flatMap mapped.get
    results.flatten
  }


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
  def goalTerms(cmdState: State): Option[List[EqTerm]] = {
    
    val results = cmdState.resultValues.toStream
    
    // find the first list of goal terms from the trace, if available 
    val traceTerms = inTrace(results)(traceGoalTerms)
    // find the first list of goal terms (with markups) from the results, if available
    val resultMarkupTerms = inResults(results)( (_, body) => resultGoalMarkup(body) )

    (traceTerms, resultMarkupTerms) match {

      case (Some(traceTerms), Some(resultMarkupTerms)) => {
        // require markup terms when getting trace terms (for rendering)

        // get the rendered term strings from the results
        // note there can be less of them, so pad (fill) the list with empty renders to accommodate
        val rendered = resultMarkupTerms.map(_._1)
        val renderedFull = rendered.padTo(traceTerms.size, "")
        
        val renderTerms = renderedFull.zip(traceTerms)
        val ppTerms = renderTerms.map(Function.tupled(isaTerm))
        
        Some(ppTerms)
      }

      case (_, Some(resultMarkupTerms)) => {
        val ppTerms = markupTerms(resultMarkupTerms)
        
        Some(ppTerms)
      }

      case _ => None
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


  // "picking this" both in `in` and `out`?
  val IN_ASSM_LABELS = Set("picking this:", "using this:")
  val OUT_ASSM_LABELS = Set("picking this:", "this:")
  
  val ALL_ASSM_LABELS = IN_ASSM_LABELS ++ OUT_ASSM_LABELS


  /**
   * Parses the facts (e.g. assumptions) from the command proof state output.
   * 
   * If available, uses Isabelle term fact tracing (requires `proof.ML` patch).
   */
  def parseFacts(cmdState: State): Map[String, List[EqTerm]] = {

    val results = cmdState.resultValues.toStream

    // find the first list of fact terms from the trace, if available
    val facts = inTrace(results)(parseFactsInTrace)

    val markupFacts = inResults(results)( (_, body) => Some(labelledTermMarkup(ALL_ASSM_LABELS)(body)) )

    val labelFacts = (facts, markupFacts) match {
      case (Some(isaFactTerms), Some(labelMarkupFacts)) => {
        // if Isabelle terms are available for the facts, use them together with
        // the rendering and labels from markup.
        val allFacts = labelMarkupFacts zip isaFactTerms
        // unpack and create IsaTerms
        allFacts.map { case ((label, (display, _)), term) => (label, isaTerm(display, term)) }
      }

      case (_, Some(labelMarkupFacts)) => {
        // unpack and create MarkupTerms
        labelMarkupFacts.map { case (label, (display, mTerm)) => (label, markupTerm(display, mTerm)) }
      }

      case _ => Stream.empty
    }

    // group by label
    labelFacts.toList.groupBy (_._1).mapValues { _.map(_._2) }
  }


  /**
   * Parses fact terms from trace element.
   * @return a list of terms if they were parsed or None if term information could not be parsed.
   */
  def parseFactsInTrace(trace: XML.Body): Option[List[Term]] = {
    // find "fact_terms" elems and then decode each "fact_term" in them
    val factGroups = trace collect
      { case XML.Elem(Markup("fact_terms", _), factTerms) => factTerms } map
      { _ collect { case XML.Elem(Markup("fact_term", _), term) => Term_XML.Decode.term(term) } }

    if (factGroups.isEmpty) {
      None
    } else {
      Some(factGroups.flatten)
    }
  }


  /**
   * Parses labelled terms (e.g. for "picking this" from structured proof) from result
   * element (writeln).
   *
   * @return  a stream of `(label, (render, term))` for all matching labels, where the `render` is
   *          a human-readable render of the term, `term` is the marked-up term XML element.
   */
  def labelledTermMarkup(labelMatch: String => Boolean)
                        (body: XML.Body): Stream[(String, (String, XML.Elem))] = {

    val labelledTerms = collectDepthFirst(body,
      {
        case LabelledBlock(label, blockBody) if (labelMatch(label)) => {
          val terms = nestedMarkupTerms(blockBody)

          // group each term with its label
          terms map ((label, _))
        }
      }).flatten

    // get the rendering of each term
    val withRender = labelledTerms map { case (label, t) => (label, (render(t), t)) }

    withRender
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
      case XML.Elem(Markup(Markup.BLOCK, _), XML.Text(text) :: rest) => Some((text.trim, rest))
      case _ => None
    }
  }

  object ProofTypeBlock {
    def unapply(elem: XML.Tree): Option[StepProofType.StepProofType] = elem match {

      case LabelledBlock(text, _) =>
        if (text.startsWith("proof (prove)")) Some(StepProofType.Prove)
        else if (text.startsWith("proof (state)")) Some(StepProofType.State)
        else if (text.startsWith("proof (chain)")) Some(StepProofType.Chain)
        else None

      case _ => None
    }
  }

  object ResultState {
    def unapply(elem: XML.Tree): Option[(StepProofType.StepProofType, XML.Body)] =
      elem match {
        case XML.Elem(Markup(Markup.WRITELN_MESSAGE, _),
          XML.Elem(Markup(Markup.STATE, _), stateBody) :: _) => {
          val proofBlocks = collectDepthFirst(stateBody, {
            case ProofTypeBlock(typ) => typ
          })
          // assume a single proof block per writeln message for proof type indication
          // use the whole state for results lookup, since results are no longer nested
          // under proof type declaration (since Isabelle 2013)
          proofBlocks.headOption map (typ => (typ, stateBody))
        }
        case _ => None
      }
  }

  object Tracing {
    def unapply(elem: XML.Tree): Option[XML.Body] = elem match {
      case XML.Elem(Markup(Markup.TRACING_MESSAGE, _), tracingBody) => Some(tracingBody)
      case _ => None
    }
  }

  def inResults[A](body: Stream[XML.Tree])
                  (lookup: (StepProofType.StepProofType, XML.Body) => Option[A]): Option[A] = {
    
    val results = body flatMap {
      case ResultState(typ, stateBody) => lookup(typ, stateBody)
      case _ => None
    }
    // single state only, so take the first one
    results.headOption
  }
  
  def inTrace[A](body: TraversableOnce[XML.Tree])(lookup: XML.Body => Option[A]): Option[A] = {
    
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
  
  def isaTerm(display: String, term: Term): EqTerm = {
    val ppTerm = factory.createIsaTerm()
    ppTerm.setDisplay(display)
    ppTerm.setTerm(term)
    new EqIsaTerm(ppTerm)
  }
  
  def markupTerm(display: String, term: XML.Tree): EqTerm = {
    val ppTerm = factory.createMarkupTerm()
    ppTerm.setDisplay(display)
    ppTerm.setTerm(term)
    new EqMarkupTerm(ppTerm)
  }
  
  def markupTerms(termDisplays: List[(String, XML.Tree)]): List[EqTerm] =
    termDisplays map Function.tupled(markupTerm)

  object StepProofType extends Enumeration {
    type StepProofType = Value
    val Prove, State, Chain = Value
  }
  
  def stepProofType(cmdState: State): Option[StepProofType.StepProofType] = {

    val results = cmdState.resultValues.toStream
    
    val typeOpt = inResults(results)( (typ, body) => Some(typ) )
    typeOpt
  }
  
//  private def proofTypeMarkup(body: XML.Body): Option[StepProofType.StepProofType] = {
//
//    val foundTypes = collectDepthFirst(body, { case ProofTypeBlock(typ) => typ } )
//    foundTypes.headOption
//  }
  
  
  def splitAssmsGoal(term: EqTerm): Judgement[EqTerm] = term match {
    // TODO implement splitting of term into assumptions + goal
    case _ => Judgement(Nil, term)
  }
  
//  def splitAssmsGoalText(termStr: String): (List[String], String) = {
//    
//    // TODO shorthand? [[Assm; Assm2]]==>Goal -- or are these not in proof goals?
//    
//  }
  
  implicit class CommandValueState(state: State) {
    def resultValues: Iterator[XML.Tree] = state.results.entries map (_._2)
  }
  
  implicit class MyIteratorOps[T](i: Iterator[T]) {
    def nextOption: Option[T] = if (i.hasNext) Some(i.next) else None
  }


  case class StepResults(state: State,
                         stateType: StepProofType.StepProofType,
                         inAssms: List[EqTerm],
                         outAssms: List[EqTerm],
                         outGoals: Option[List[EqTerm]]) {
    
    lazy val inAssmProps = inAssms map (Assumption(_))
    lazy val outAssmProps = outAssms map (Assumption(_))
    
    private def addInAssms(goal: Judgement[EqTerm]): Judgement[EqTerm] = {
      val updAssms = (goal.assms ::: inAssms).distinct
      goal.copy( assms = updAssms )
    }

    // convert each goal to a Judgement (assms + goal)
    // also link explicit assumptions with out goals - add them to each out goal
    lazy val outGoalProps = outGoals map { goals =>
      goals map ResultParser.splitAssmsGoal map addInAssms
    }
    
  }

}
