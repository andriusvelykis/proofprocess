package org.ai4fm.proofprocess.zeves.core.parse

import java.io.IOException

import org.ai4fm.proofprocess.Term

import org.ai4fm.proofprocess.zeves.ZEvesProofProcessFactory
import org.ai4fm.proofprocess.zeves.core.internal.ZEvesPProcessCorePlugin._

import net.sourceforge.czt.eclipse.zeves.core.ZEvesResultConverter
import net.sourceforge.czt.session.CommandException
import net.sourceforge.czt.session.Markup
import net.sourceforge.czt.session.SectionInfo
import net.sourceforge.czt.session.SectionManager
import net.sourceforge.czt.z.ast.Pred
import net.sourceforge.czt.z.ast.TruePred
import net.sourceforge.czt.zeves.response.ZEvesOutput

/** @author Andrius Velykis
  */
object TermParser {
  
  private val factory = ZEvesProofProcessFactory.eINSTANCE

  def parseGoals(sectInfo: SectionInfo, sectName: String, result: ZEvesOutput): List[Term] = {

    val goalStr = goalText(result)

    goalStr match {
      // a special case - do not try parsing
      case ZEvesOutput.UNPRINTABLE_PREDICATE => List(unparsedTerm(goalStr))

      case _ => {
        val predDisplay = parseTerm(sectInfo, sectName, goalStr)

        predDisplay match {
          // if the goal is TRUE, return no goals
          case Some((truePred: TruePred, _)) => List()

          // some predicate parsed successfully
          case Some((pred, goalPrinted)) => List(cztTerm(pred, goalPrinted))

          // cannot parse, return unparsed
          case None => List(unparsedTerm(goalStr))
        }

      }
    }

  }
  
  private def goalText(result: ZEvesOutput): String = {
    val goalObj = result.getFirstResult
    goalObj.toString.trim
  }
  
  private def unparsedTerm(display: String): Term = {
    val term = factory.createUnparsedTerm
    term.setDisplay(display)
    term
  }

  private def cztTerm(pred: Pred, display: String): Term = {
    // create a term encapsulating the CZT ast.Term.
    // It will be serialised to XML when saved
    val term = factory.createCztTerm
    term.setTerm(pred)
    term.setDisplay(display)
    term
  }

  private def parseTerm(sectInfo: SectionInfo, sectName: String, termStr: String): Option[(Pred, String)] = {

    try {

      // a bit of a hack, will need to review whether a sectInfo could be enough eventually
      sectInfo match {
        case sectMan: SectionManager => {
          // parse the goal from Z/EVES response
          val pred = ZEvesResultConverter.parseZEvesPred(sectMan, sectName, termStr)

          // pretty-print the goal predicate back, so that we get uniform CZT-printing, instead
          // of using Z/EVES conversion to CZT unicode
          val display = ZEvesResultConverter.printResult(sectMan, sectName, pred, Markup.UNICODE, 0, true)

          // return both the predicate and its pretty-print
          Some((pred, display))
        }

        case _ => None
      }

    } catch {
      case ioe: IOException => {
        log(error(Some(ioe)))
        None
      }
      case ce: CommandException => {
        val cause = Option(ce.getCause) getOrElse ce
//        val causeErr = ZEditorUtil.clean(cause.getMessage()).trim()
        val causeErr = cause.getMessage.trim
        log(error(Some(cause), Some("Cannot parse Z/EVES result: " + causeErr)))
        None
      }
    }

  }
  
}
