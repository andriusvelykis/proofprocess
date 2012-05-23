package org.ai4fm.proofprocess.isabelle.core.parse

import isabelle.Command.State
import isabelle.Markup
import isabelle.Markup_Tree
import isabelle.Text
import isabelle.Token
import isabelle.XML

import org.ai4fm.proofprocess.Term
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
  
  def parse(cmdState: State) = {

    val command = factory.createIsabelleCommand()
    command.setSource(cmdState.command.source.trim)
    
    // use command tokens with markup infos to determine command arguments
    // filter out whitespace/comments though
    val tokenInfos = tokenInfoStream(cmdState, cmdState.command.span).filter({ case (token, _) => !token.is_ignored });

    // check if the first token is COMMAND, then create the command and parse the tokens
    tokenInfos.headOption match {
      case Some((Token(Token.Kind.COMMAND, source), markup)) => {
        
        // TODO resolve name from markup?
        command.setName(source)
        
        // parse command contents
        parseTokens(Nil, Nil, command, command, command, tokenInfos.tail)
        command
      }
      case _ => command
    }
  }

  @tailrec
  private def parseTokens(ids: List[TermInfo], facts: List[String],
    methodRoot: NamedTermTree, namedRoot: NamedTermTree, termRoot: NamedTermTree,
    tokens: Stream[TokenInfo]) {

    def consume(consumeIds: List[TermInfo] = ids) {
      // add all outstanding terms (from "identifiers" list
      termRoot.getTerms().addAll(consumeIds.reverse.map(info => getTerm(info)))
      // add all outstanding facts
      termRoot.getTerms().addAll(facts.reverse.map(name => getFactTerm(name)))
    }

    def markIdentifier(term: TermInfo) =
      parseTokens(term :: ids, facts, methodRoot, namedRoot, termRoot, tokens.tail)

    def newBranch(methodRoot: NamedTermTree, namedRoot: NamedTermTree, termRoot: NamedTermTree) =
      parseTokens(Nil, Nil, methodRoot, namedRoot, termRoot, tokens.tail)

    def continueBranch =
      parseTokens(ids, facts, methodRoot, namedRoot, termRoot, tokens.tail)

    if (tokens.isEmpty) {
      // consume all
      consume()
    } else tokens.head match {
      case (Token(Token.Kind.STRING | Token.Kind.ALT_STRING, source), markups) =>
        // found a string, treat it as "term" identifier
        markIdentifier((source, markups))
      case (Token(Token.Kind.IDENT, source), markups) if markups.isEmpty =>
        // no markup for the identifier - add it to the identifier list
        markIdentifier((source, markups))
      case (Token(Token.Kind.IDENT, source), markups) => markups.head match {
        // for identifiers, assume a single markup statement (TODO review?)

        // if it is a fact, add its name to the fact list
        // also consume any outstanding terms for the fact (terms go before facts, e.g. "x="Y" in exI)
        case Markup.Entity("fact", name) =>
          // TODO consume identifiers to produce a "fact-with-args"
          parseTokens(ids, name :: facts, methodRoot, namedRoot, termRoot, tokens.tail)

        // if it is a method, start a new method branch
        case Markup.Entity(Markup.METHOD, name) => {
          val method = addTermTree(methodRoot, name)

          consume()
          newBranch(methodRoot, method, method)
        }
        case markup => {
          println("Unknown identifier markup: " + markup)
          // ignore and continue parsing
          continueBranch
        }
      }
      case (Token(Token.Kind.KEYWORD, ":"), _) => {
        // a named fact list encountered, e.g. "intro: allI"
        // collect all subsequent facts into a named term tree

        // take the last identifier: it will be the name for this term tree
        val termsName = ids.head._1
        val tree = addTermTree(namedRoot, termsName)

        // consume previous
        consume(ids.tail)
        newBranch(methodRoot, namedRoot, tree)
      }
      case (Token(Token.Kind.KEYWORD, "="), _) => {
        // a named argument encountered, e.g. "x="Y + 1""
        // lookahead and join with the last identifier
        println("= encountered!")
        //        parseToken(lastIdentifier, namedFacts(lastIdentifier), tokens.tail)
      }
      case _ => {
        // ignore and continue parsing
        continueBranch
      }
    }
  }
  
  private def getTerm(info: TermInfo): Term = {
    // FIXME parse the term
    val term = factory.createIsaTerm(); 
    term.setDisplay(info._1);
    term
  }
  
  private def getFactTerm(fact: String): Term = {
    val term = factory.createNameTerm(); 
    term.setName(fact);
    term
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
  
}
