package org.ai4fm.proofhistory.isabelle.scala

import isabelle._
import org.ai4fm.{proofprocess => pp}
import pp.Term
import pp.isabelle.CompositeTerm
import pp.isabelle.DisplayTerm
import pp.isabelle.IsabelleProofProcessFactory
import pp.isabelle.IsaTerm
import scala.collection.mutable.Stack
import org.ai4fm.proofprocess.isabelle.TermKind

import scala.collection.JavaConversions._

object TermParser {

  private val FACTORY = IsabelleProofProcessFactory.eINSTANCE
  
  private val IGNORE_MARKUPS = List(Markup.BREAK)

  def parseGoals(source: XML.Tree): java.util.List[Term] =
    {
      getElems(List(source), Markup.SUBGOAL).foldRight(Nil: List[Term]) {
        (goal, goalTerms) =>
          {
            val term = getSingleElem(goal.body, Markup.TERM)
            val ppTerm = parseTerm(term)
            ppTerm :: goalTerms
          }
      }
    }
  
  private def getElems(source: XML.Body, markup: String): List[XML.Elem] = {
    
    // need uppercase variable name to match on variable
    // see http://stackoverflow.com/questions/1332574/common-programming-mistakes-for-scala-developers-to-avoid/2489355#2489355
    val MatchMarkup = markup
    
    source.foldRight(Nil: List[XML.Elem])((e, ts) => e match {
      case XML.Elem(Markup(MatchMarkup, _), body) => (e.asInstanceOf[XML.Elem] :: ts)
      case XML.Elem(_, body) => (getElems(body, markup) ::: ts)
      case _ => ts
    })
  }

  private def getSingleElem(source: XML.Body, markup: String): XML.Elem =
    getElems(source, markup) match {
      // single element
      case List(term) => term
      case Nil => throw new IllegalArgumentException("No <" + markup + "> found: " + source)
      case _ => throw new IllegalArgumentException("Multiple <" + markup + "> found: " + source)
    }
  

  def parseTerm(source: XML.Tree): Term =
    {
      val termStack = new Stack[DisplayTerm]

      var rootTerm: Option[Term] = None

      def parse(source: XML.Body): Unit = {
        source.foreach(e => e match {
          case XML.Text(s) => {
            termStack.top.setDisplay(s)
          }
          case XML.Elem(Markup(Markup.BLOCK, _), body) if (body.size == 1) => {
            // single element inside a block - continue on the inner element,
            // skipping the outer BLOCK
            parse(body)
          }
          // Some elements are in result XML for pretty-printing only, and should 
          // be ignored in parsing altogether, e.g. <break>
          case XML.Elem(Markup(markup, _), _) if ignoreElem(markup) => // ignore
          case XML.Elem(markup, body) => {
            val term = parseElem(markup)

            addToParent(term)

            // push the term onto stack, and continue in its body
            termStack.push(term)
            parse(body)
            termStack.pop()
            
            // sometimes the name is not specified, and the text contents are used as the name
            term match {
              case t: IsaTerm => {
                if (t.getName() == null) {
                  // copy the display to the name
                  t.setName(t.getDisplay());
                }
              }
              case _ =>
            }
          }
        })
      }
      
      def ignoreElem(markup: String) = {
        IGNORE_MARKUPS.contains(markup)
      }

      def addToParent(term: DisplayTerm) = {
        if (termStack.isEmpty) {
          // this is the root term, others will be added to it
          rootTerm = Some(term)
        } else {
          // the top should be composite - add to it
          termStack.top match {
            case comp: CompositeTerm => {
              // add the term to the parent composite
              comp.getTerms().add(term)
            }
            case _ => {
              // TODO nice message
              System.out.println("Invalid top of the stack: " + termStack.top + ";\nterm: " + term + ";\nsource: " + source);
//              throw new IllegalStateException("Top of the stack: " + termStack.top + ";\nterm: " + term + ";\nsource: " + source);
            }
          }
        }
      }

      def parseElem(elemMarkup: Markup): DisplayTerm = {
        elemMarkup match {
          case Markup(Markup.BLOCK, _) => {
            val comp = FACTORY.createCompositeTerm();
            // TODO render the display?
            comp
          }
          case Markup(Markup.ENTITY, props) => {
            val isaTerm = FACTORY.createIsaTerm();

            props.foreach { prop =>
              prop match {
                case Markup.Kind(kind) => {
                  val termKind: TermKind = kind match {
                    case Markup.CONSTANT => TermKind.CONST
                    case bad => throw new IllegalStateException("Unknown entity kind: " + bad)
                  }

                  isaTerm.setKind(termKind)
                }
                case Markup.Name(name) => isaTerm.setName(name)
                case _ =>
              }
            }
            
            isaTerm
          }
          case Markup(Markup.BOUND, _) => {
            val isaTerm = FACTORY.createIsaTerm();
            isaTerm.setKind(TermKind.BOUND)
            isaTerm
          }
          case Markup(Markup.VAR, _) => {
            val isaTerm = FACTORY.createIsaTerm();
            isaTerm.setKind(TermKind.VAR)
            isaTerm
          }
          case Markup(Markup.FIXED, _) => {
            val isaTerm = FACTORY.createIsaTerm();
            isaTerm.setKind(TermKind.FIXED)
            isaTerm
          }
          case Markup(Markup.FREE, _) => {
            val isaTerm = FACTORY.createIsaTerm();
            isaTerm.setKind(TermKind.FREE)
            isaTerm
          }
          case bad => throw new IllegalStateException("Unsupported markup: " + elemMarkup)
        }
      }

      // do the parsing - ensuring its TERM
      source match {
        case XML.Elem(Markup(Markup.TERM, _), body) => parse(body)
        case _ => throw new IllegalArgumentException("Expected <term>, found: " + source)
      }
      
      rootTerm match {
        case Some(term) => term
        case None => throw new IllegalStateException("Invalid term: " + source)
      }
    }
  
}
