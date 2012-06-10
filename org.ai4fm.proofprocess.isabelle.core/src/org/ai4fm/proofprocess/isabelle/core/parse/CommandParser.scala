package org.ai4fm.proofprocess.isabelle.core.parse

import isabelle.Command
import isabelle.Command.State
import isabelle.Markup
import isabelle.Markup_Tree
import isabelle.Properties
import isabelle.Term.{Term => ITerm}
import isabelle.Term_XML
import isabelle.Text
import isabelle.Token
import isabelle.XML
import org.ai4fm.proofprocess.Term
import org.ai4fm.proofprocess.isabelle.Inst
import org.ai4fm.proofprocess.isabelle.IsabelleCommand
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory
import org.ai4fm.proofprocess.isabelle.NamedTermTree
import scala.annotation.tailrec
import scala.collection.JavaConversions._


/** A parser for Isabelle Commands. Parses a named-term-tree structure from the
  * command token/markup information. The structure allows having a command with
  * methods and named fact-lists in a generic way.  
  * 
  * @author Andrius Velykis
  */
object CommandParser {

  private type TokenInfo = (Token, Stream[Markup])
  private type TermInfo = (String, Stream[Markup])
  
  val factory = IsabelleProofProcessFactory.eINSTANCE
  
  def parse(cmdState: State): IsabelleCommand = {

    val command = factory.createIsabelleCommand()
    command.setSource(cmdState.command.source.trim)
    
    // use command tokens with markup infos to determine command arguments
    // filter out whitespace/comments though
    val tokenInfos = tokenInfoStream(cmdState, cmdState.command.span).filter({ case (token, _) => !token.is_ignored });

    // check if the first token is COMMAND, then create the command and parse the tokens
    tokenInfos.headOption match {
      case Some((Token(Token.Kind.COMMAND, source), markup)) => {
        
        command.setName(commandName(markup) getOrElse source)
        
        val terms = cmdTerms(cmdState)

        @tailrec
        def parseTokens(ids: List[TermInfo], facts: List[Term], insts: List[Inst],
          namedRoot: NamedTermTree, termRoot: NamedTermTree,
          tokens: Stream[TokenInfo]) {

          def consume(consumeIds: List[TermInfo] = ids) {
            // add all outstanding terms (from "identifiers" list)
            termRoot.getTerms().addAll(consumeIds.reverse.map(info => getTerm(terms, info)))
            // add all outstanding facts
            termRoot.getTerms().addAll(facts.reverse)
          }

          if (tokens.isEmpty) {
            // consume all
            consume()
          } else tokens.head match {
            case IdentifierToken(source, markups) =>
              // add it to the identifier list
              parseTokens((source, markups) :: ids, facts, insts, namedRoot, termRoot, tokens.tail)
            case (Token(Token.Kind.IDENT, source), markups) => markups.head match {
              // for identifiers, assume a single markup statement (TODO review?)

              // if it is a fact, add its name to the fact list
              // also consume any outstanding terms for the fact (terms go before facts, e.g. "x="Y" in exI)
              case Markup.Entity("fact", name) =>
                // consume insts to produce a InstTerm
                
                val namedFactOpt = lookaheadNamedFact(terms, name, tokens.tail)
                val (fact, nextTokens) = namedFactOpt getOrElse ((nameTerm(name), tokens.tail))
                
                val instFact = withInsts(fact, insts)
                parseTokens(ids, instFact :: facts, Nil, namedRoot, termRoot, nextTokens)

              // if it is a method, start a new method branch
              case Markup.Entity(Markup.METHOD, name) => {
                val method = addTermTree(command, name)

                consume()
                // new branch on the method
                parseTokens(Nil, Nil, Nil, method, method, tokens.tail)
              }
              case markup => {
                println("Unknown identifier markup: " + markup)
                // ignore and continue parsing
                parseTokens(ids, facts, insts, namedRoot, termRoot, tokens.tail)
              }
            }
            // extractor SemiToken not working here somehow
            // maybe a bug in pattern matcher (e.g. https://issues.scala-lang.org/browse/SI-1697)
            // TODO review with Scala 2.10
            case (Token(Token.Kind.KEYWORD, ":"), _) => {
              // a named fact list encountered, e.g. "intro: allI"
              // collect all subsequent facts into a named term tree

              // take the last identifier: it will be the name for this term tree
              val termsName = ids.head._1
              val tree = addTermTree(namedRoot, termsName)

              // consume previous
              consume(ids.tail)
              // new branch on the named tree
              parseTokens(Nil, Nil, Nil, namedRoot, tree, tokens.tail)
            }
            case (Token(Token.Kind.KEYWORD, "="), _) => {
              // a named inst encountered, e.g. "x="Y + 1""
              // lookahead and join with the last identifier
              val lookahead = tokens.tail
              val (nextToken, markups) = lookahead.head

              // create Inst out of last identifier and next token info
              // TODO also handle postinsts, e.g. [of _ "Y + 1"]
              val instVal = inst(terms, ids.head, (nextToken.source, markups))

              // continue parsing taking into account the consumed Inst
              parseTokens(ids.tail, facts, instVal :: insts, namedRoot, termRoot, lookahead.tail)
            }
            case _ => {
              // ignore and continue parsing
              parseTokens(ids, facts, insts, namedRoot, termRoot, tokens.tail)
            }
          }
        }
        
        // parse command contents
        parseTokens(Nil, Nil, Nil, command, command, tokenInfos.tail)
        command
      }
      case _ => command
    }
  }
  
  private def commandName(markups: Stream[Markup]): Option[String] = {
    val markupName = markups.collectFirst({ 
      case Markup(Markup.COMMAND, props) => props.collectFirst({ 
        case (Markup.NAME, value) => value }) })
    
    markupName getOrElse None
  }

  /**
   * Extractor object for instantiation trace markup
   * 
   * @author Andrius Velykis
   */
  object InstTrace {

    // inst index property
    val Index = new Properties.Int("index")

    def unapply(markup: Markup): Option[(String, Int)] =
      markup match {
        case Markup("inst", props @ Markup.Name(name)) => props match {
          case Index(index) => Some(name, index)
          case _ => None
        }
        case _ => None
      }
  }
  
  private def inst(terms: Map[String, ITerm], name: TermInfo, term: TermInfo): Inst = {
    val inst = factory.createInst()
    
    // try if there is a reported inst name on the term markup
    val instTrace = term._2.collectFirst({ case InstTrace(name, index) => (name, index) })
    
    val (instName, instIndex) = instTrace match {
      case Some(res) => res
      // trace not available - just use the name from parsing
      // TODO split it if it has index in the name?
      case None => (name._1, 0)
    }
    
    inst.setName(instName)
    inst.setIndex(instIndex)
    inst.setTerm(getTerm(terms, term))
    
    inst
  }
  
  private def getTerm(terms: Map[String, ITerm], info: TermInfo): Term = {
    
    val src = stripTerm(info._1)
    
    val srcTerm = terms.get(src)
    
    val term = terms.get(src) match {
      case Some(term) => {
        // term representation was traced
        val isaTerm = factory.createIsaTerm;
        isaTerm.setTerm(term)
        isaTerm
      }
      case None => {
        // no Isabelle term available, use markup term
        val isaTerm = factory.createMarkupTerm;
        // TODO parse markup?
        isaTerm
      }
    }
    
    term.setDisplay(src);
    term
  }
  
  private def stripTerm(term: String) = {
    def strip(term: String, quote: String) = term.stripPrefix(quote).stripSuffix(quote)
    
    strip(strip(term, "\""), "'").trim
  }
  
  private def nameTerm(name: String): Term = {
    val term = factory.createNameTerm()
    term.setName(name)
    term
  }
  
  private def withInsts(term: Term, insts: List[Inst]): Term =
    if (insts.isEmpty) {
      // not insts, just use the name term
      term
    } else {
      // wrap into InstTerm
      val instTerm = factory.createInstTerm()
      instTerm.setTerm(term)
      instTerm.getInsts().addAll(insts)
      instTerm
    }
  
  private def addTermTree(parent: NamedTermTree, name: String): NamedTermTree = {
    val tree = factory.createNamedTermTree()
    tree.setName(name)
    parent.getBranches().add(tree)
    
    tree
  }
  
  /** Creates a stream of tokens with their associated markup information */
  private def tokenInfoStream(cmdState: State, tokens: List[Token], offset: Int = 0): Stream[TokenInfo] = {
    if (tokens.isEmpty) {
      Stream.empty
    } else {
      val token = tokens.head
      val tokenRange = Text.Range(offset, offset + token.source.length)

      // select markups for the token range, which may carry additional properties
      val markupOptInfos = cmdState.markup.select(tokenRange)({
        case Text.Info(_, XML.Elem(markup, _)) => markup
      })
      
      // collect (filter+map) to keep only the markup information
      val markupInfos = markupOptInfos.collect{case Text.Info(_, Some(markup)) => markup}
      
      // construct the rest of the stream recursively
      Stream.cons(
          (token, markupInfos), 
          tokenInfoStream(cmdState, tokens.tail, tokenRange.stop))
    }
  }
  
  private def cmdTerms(cmdState: State): Map[String, ITerm] = {
    // get everything nested in TRACING->cmd_terms elements - it will give us the command term XML
    // structures
    val cmdXmlTerms = cmdState.results map { _._2 } collect {
      case XML.Elem(Markup(Markup.TRACING, _), XML.Elem(Markup("cmd_terms", _), cterms) :: Nil) => cterms
    } flatten;

    // split each XML term into source/term pair and parse the values
    val terms = cmdXmlTerms collect {
      case XML.Elem(Markup("cmd_term", _), XML.Elem(Markup("source", _), src) :: XML.Elem(Markup("term", _), term) :: Nil) => 
        // couple source with term if the source can be determined 
        (cmdTermSrc(src) map {src => (src, Term_XML.Decode.term(term))})
    }
    
    // filter terms with invalid source and return a map
    terms.flatten.toMap
  }

  private def cmdTermSrc(body: XML.Body): Option[String] = {
    // assume a single element
    val src = body.headOption flatMap {
      // the source can be given as a String or wrapped into a token
      case XML.Text(str) => Some(str)
      case XML.Elem(Markup("token", _), XML.Text(str) :: Nil) => Some(str)
      case _ => None
    }
    
    // sometimes DEL character appears and messes up the source - delete it
    src.map( _.filterNot( _ == '\u007F'))
  }
  
  private object SemiToken {
    def unapply(tokenInfo: TokenInfo): Option[_] = tokenInfo match {
      case (Token(Token.Kind.KEYWORD, ":"), _) => Some()
      case _ => None
    }
  }

  private object IdentifierToken {
    def unapply(tokenInfo: TokenInfo): Option[(String, Stream[Markup])] = tokenInfo match {
      case (Token(Token.Kind.STRING | Token.Kind.ALT_STRING, source), markups) =>
        // found a string, treat it as "term" identifier
        Some((source, markups))
      case (Token(Token.Kind.IDENT, source), markups) if markups.isEmpty =>
        // no markup for the identifier - add it to the identifier list
        Some((source, markups))
      case _ => None
    }
  }

  /** Checks if the following tokens represent a named fact, e.g. "foo: "x > 10"" */
  private def lookaheadNamedFact(terms: Map[String, ITerm], name: String,
    tokens: Stream[TokenInfo]): Option[(Term, Stream[TokenInfo])] = {

    // require semicolon as the next token
    val semicolonOpt = tokens.headOption collect { case SemiToken(_) => tokens.tail  }

    // require identified/term as a next token
    val nextTerm = semicolonOpt flatMap { nextTokens =>
      {
        val nextToken = nextTokens.headOption
        nextToken collect { case IdentifierToken(source, markups) => (getTerm(terms, (source, markups)), nextTokens.tail) }
      }
    }

    // create NamedTerm if found
    nextTerm map {
      case (term, nextTokens) =>
        {
          val namedFact = factory.createNamedTerm
          namedFact.setName(name)
          namedFact.setTerm(term)

          // return the named term and the fact that two tokens were consumed
          (namedFact, nextTokens)
        }
    }
  }
  
  def commandId(cmd: Command): Option[String] = {
    // find first non-ignored token and use it if applicable
    val firstId = cmd.span.find(token => !token.is_command && !token.is_ignored)
    
    // ensure it is a name and then use its source
    firstId filter {_.is_name} map {_.source}
  }
  
}
