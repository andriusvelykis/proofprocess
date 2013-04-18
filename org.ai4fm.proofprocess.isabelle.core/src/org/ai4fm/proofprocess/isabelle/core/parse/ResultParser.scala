package org.ai4fm.proofprocess.isabelle.core.parse

import scala.annotation.tailrec

import org.ai4fm.proofprocess.core.analysis.{EqTerm, Judgement}
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory
import org.ai4fm.proofprocess.isabelle.core.IsabellePProcessCorePlugin.{error, log}
import org.ai4fm.proofprocess.isabelle.core.data.{EqIsaTerm, EqMarkupTerm}
import org.ai4fm.proofprocess.isabelle.core.logic.Logic

import isabelle.{Command, Markup, Pretty, Symbol}
import isabelle.{Term_XML, XML}
import isabelle.Term.Term


/**
 * @author Andrius Velykis
 */
object ResultParser {
  
  val factory = IsabelleProofProcessFactory.eINSTANCE


  /**
   * Parses command results, such as assumptions, goals, proof type from the command state.
   */
  def parseCommandResults(commandState: Command.State): Option[CommandResults] = {

    val factTerms = parseFacts(commandState)

    val inAssms = collectMapped(factTerms, IN_ASSM_LABELS)
    val outAssms = collectMapped(factTerms, OUT_ASSM_LABELS)
    
    val isByCmd = "by" == commandState.command.name

    val stepTypeOpt = stepProofType(commandState)
    // last step 'by' has no output, so assume 'prove' step type
    val stepType = stepTypeOpt orElse ( if (isByCmd) Some(StepProofType.Prove) else None ) 
    
    // workaround for "by" not outputting goals in "markup" term case
    // also "by" when used in forward proof can go into "state" proof and output outstanding
    // goals - so we replace it with "finished", i.e. empty list of goals
    val goals = if (isByCmd) Some(Nil) else goalTerms(commandState)
    
    // only produce results if step type is defined
    stepType map ( CommandResults(commandState, _, inAssms, outAssms, goals) )
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
  def goalTerms(cmdState: Command.State): Option[List[EqTerm]] = {
    
    val results = cmdState.resultValues.toStream
    
    // find the first list of goal terms from the trace, if available 
    val traceTerms = inTrace(results)(traceGoalTerms).headOption
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
  def parseFacts(cmdState: Command.State): Map[String, List[EqTerm]] = {

    val results = cmdState.resultValues.toStream

    // find the first list of fact terms from the trace, if available
    val factsStream = inTrace(results)(parseFactsInTrace).flatten
    val facts = if (factsStream.isEmpty) None else Some(factsStream.toList)

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
    trace collectFirst
      { case XML.Elem(Markup("fact_terms", _), factTerms) => factTerms } map
      { _ collect { case XML.Elem(Markup("fact_term", _), term) => Term_XML.Decode.term(term) } }
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
  
  def inTrace[A](body: TraversableOnce[XML.Tree])(lookup: XML.Body => Option[A]): Stream[A] = {
    
    body.toStream flatMap {
      case Tracing(traceBody) => lookup(traceBody)
      case _ => None
    }
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
  
  def stepProofType(cmdState: Command.State): Option[StepProofType.StepProofType] = {

    val results = cmdState.resultValues.toStream
    
    val typeOpt = inResults(results)( (typ, body) => Some(typ) )
    typeOpt
  }
  
//  private def proofTypeMarkup(body: XML.Body): Option[StepProofType.StepProofType] = {
//
//    val foundTypes = collectDepthFirst(body, { case ProofTypeBlock(typ) => typ } )
//    foundTypes.headOption
//  }


  /**
   * Splits the given term into assumptions (premises) and goal (conclusion).
   * The result is wrapped into a Judgement.
   * 
   * This provides a "normal form" to match goals, e.g. sometimes the assumptions are inline with
   * the goal, other times they are available as "this: ..." and listed separately.
   */
  def splitAssmsGoal(term: EqTerm): Judgement[EqTerm] = term match {

    case iTerm: EqIsaTerm => splitIsaTerm(iTerm)

    // TODO implement better splitting for markup term - currently only splitting the display..
    case mTerm: EqMarkupTerm => splitMarkupTerm(mTerm)

    // catch-all just in case
    case _ => {
      log(error(msg = Some("Splitting unknown term: " + term)))
      // for unknown term, assume it's all goal - don't split
      Judgement(Nil, term)
    }
  }


  /**
   * Splits Isabelle term into assumptions + goal.
   * 
   * Uses logic operations to split on top-level meta implications. Also tries to split the display
   * string accordingly.
   */
  private def splitIsaTerm(term: EqIsaTerm): Judgement[EqIsaTerm] = {

    val assms = Logic.strip_imp_prems(term.isabelleTerm)
    if (assms.isEmpty) {
      // no assumptions to split - just use the original term
      Judgement(Nil, term)
    } else {
      
      val goal = Logic.strip_imp_concl(term.isabelleTerm)
      val (assmStrs, goalStr) = splitDisplay(term.term.getDisplay)

      // just in case - pad the list to the assumptions length
      val fullAssmStrs = assmStrs.padTo(assms.length, "")
      val isaAssms = assms zip fullAssmStrs

      val isaGoal = (goal, goalStr)

      // create new terms for split assumptions and goal
      def createITerm(isaT: (Term, String)): EqIsaTerm = {
        val (term, display) = isaT
        val newTerm = factory.createIsaTerm
        newTerm.setTerm(term)
        newTerm.setDisplay(display)
        new EqIsaTerm(newTerm)
      }

      val assmTerms = isaAssms map createITerm
      val goalTerm = createITerm(isaGoal)

      Judgement(assmTerms, goalTerm)
    }
  }


  /**
   * Splits a markup term into assumptions + goal.
   * 
   * Currently only splits the display string. Markup XML splitting is not supported at the moment.
   */
  private def splitMarkupTerm(term: EqMarkupTerm): Judgement[EqMarkupTerm] = {
    val (assmStrs, goalStr) = splitDisplay(term.display)

    if (assmStrs.isEmpty) {
      // no assumptions to split - just use the original term
      Judgement(Nil, term)
    } else {
      // create new terms for split assumptions and goal
      def createMTerm(display: String): EqMarkupTerm = {
        val newTerm = factory.createMarkupTerm
        newTerm.setTerm(XML.Text("TODO split"))
        newTerm.setDisplay(display)
        new EqMarkupTerm(newTerm)
      }
      val assmTerms = assmStrs map createMTerm
      val goalTerm = createMTerm(goalStr)
      
      Judgement(assmTerms, goalTerm)
    }
  }


  // assume single-character for now
  lazy val META_IMPLICATION = Symbol.decode("\\<Longrightarrow>").charAt(0)

  /**
   * Splits the given term display string on the top-level meta implications into
   * assumptions + goal.
   */
  private def splitDisplay(termStr: String): (List[String], String) = {

    // split on META_IMPLICATION, but only when it is not nested in parentheses -
    // top level implication only
    val (_, splitIndices) = (termStr.zipWithIndex foldLeft (0, Nil: List[Int])) {
      case ((openParens, implInds), (ch, index)) =>
        ch match {
          case '(' => (openParens + 1, implInds)
          case ')' => (openParens - 1, implInds)

          // only split on implication when it is not nested in parentheses
          case META_IMPLICATION if openParens == 0 => (openParens, index :: implInds)

          // continue otherwise
          case _ => (openParens, implInds)
        }
    }

    if (splitIndices.isEmpty) {
      // nothing to split on - everything is the goal
      (Nil, termStr)
    } else {

      // split at indices (they are reversed, which is ok) and trim the inner spaces
      val splits = splitAt(termStr, splitIndices, Nil).map(_.trim)

      // the last is goal, the rest are assumptions
      // also trim the values and remove lingering parentheses if needed
      val goalStr = trimParens(splits.last)
      val assmStrs = splits.init map trimParens

      (assmStrs, goalStr)
    }
  }

  @tailrec
  private def splitAt(str: String,
                      reverseInds: List[Int],
                      acc: List[String]): List[String] = reverseInds match {
    // collect the last remaining string
    case Nil => str :: acc

    // split the strings and continue on the part at the front
    case index :: is => {
      val (remaining, take) = (str.substring(0, index), str.substring(index + 1))
      splitAt(remaining, is, take :: acc)
    }
  }

  private def trimParens(str: String): String = {
    val str1 = str.trim
    val trimFirst = if (str1.startsWith("(")) str1.substring(1) else str
    val trimLast = if (trimFirst.endsWith(")")) trimFirst.substring(0, trimFirst.length - 1)
                   else trimFirst

    trimLast.trim
  }

  
  implicit class CommandValueState(state: Command.State) {
    def resultValues: Iterator[XML.Tree] = state.results.entries map (_._2)
  }
  
  implicit class MyIteratorOps[T](i: Iterator[T]) {
    def nextOption: Option[T] = if (i.hasNext) Some(i.next) else None
  }

}
