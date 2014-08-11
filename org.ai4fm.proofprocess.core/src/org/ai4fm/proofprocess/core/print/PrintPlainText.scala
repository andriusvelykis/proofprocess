package org.ai4fm.proofprocess.core.print

import scala.collection.JavaConverters.asScalaBufferConverter

import org.ai4fm.proofprocess.Attempt
import org.ai4fm.proofprocess.DisplayTerm
import org.ai4fm.proofprocess.Proof
import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.ProofFeature
import org.ai4fm.proofprocess.ProofId
import org.ai4fm.proofprocess.ProofInfo
import org.ai4fm.proofprocess.ProofParallel
import org.ai4fm.proofprocess.ProofSeq
import org.ai4fm.proofprocess.Term


/**
 * Prints PP elements to plain text format (Markdown-like)
 * 
 * @author Andrius Velykis
 */
class PrintPlainText(termPrinter: Term => Option[String] = _ => None) {

  def print(elem: Any): String = elem match {

    case attempt: Attempt =>
      printProof(attempt) + "\n\n" +
      print(attempt.getProof)

    case info: ProofInfo => {
      List(
        Option(info.getIntent) map { _.getName } orElse Some(""),
        printNarrative(info.getNarrative),
        printLabelList("In Features:", info.getInFeatures.asScala),
        printLabelList("Out Features:", info.getOutFeatures.asScala),
        if (!info.getInFeatures.isEmpty || !info.getOutFeatures.isEmpty) Some("---") else None
      ).flatten.mkString("\n\n")
    }

    case seq: ProofSeq => {
      val seqStr = 
        "### SEQ: " + print(seq.getInfo) ::
        (seq.getEntries.asScala map print).toList
      bullet(seqStr.mkString("\n\n"))
    }

    case par: ProofParallel => {
      val parStr = 
        "### PAR: " + print(par.getInfo) ::
        (par.getEntries.asScala map print).toList
      bullet(parStr.mkString("\n\n"))
    }

    case entry: ProofEntry => {
      val entryStr = List(
        Some("#### ENTRY: " + print(entry.getInfo)),
        //print(entry.getProofStep),
        printLabelList("\u2192 In Goals:", entry.getProofStep.getInGoals.asScala),
        printLabelList("\u2190 Out Goals:", entry.getProofStep.getOutGoals.asScala, Some("Done"))
      )
      bullet(entryStr.flatten.mkString("\n\n"))
    }

    case link: ProofId => {
      bullet("#### LINK: " + print(link.getInfo))
    }

    // TODO delegate somehow printing of terms?
    case term: Term => printTerm(term)

    case feature: ProofFeature => {
      val featStr = List(
        Option(feature.getName) map { _.getName } getOrElse "<?feature>",
        "(",
        (feature.getParams.asScala map print).mkString(", "),
        ")"
      ).mkString

      List(
        Some(featStr),
        printNarrative(feature.getMisc)
      ).flatten.mkString("\n\n")
    }

    case prf: Proof => {
      val attemptsStr =
        if (prf.getAttempts.size == 1) "1 attempt"
        else prf.getAttempts.size + " attempts"

      val label = "# Proofs of " + prf.getLabel + " (" + attemptsStr + ")"

      val prfStr = label :: (prf.getAttempts.asScala.toList map print)
      prfStr.mkString("\n\n")
    }

    case _ => "Printing not supported: " + elem.getClass
  }

  private def println(elem: Any): String =
    if (elem != null) print(elem) + "\n" else ""

  private def printOpt(opt: Option[String]): String = opt getOrElse ""

  private def prependLines(prepend: String)(text: String): String =
    prependLines(prepend, prepend)(text)
  
  private def prependLines(firstLine: String, prepend: String)(text: String): String =
    firstLine + text.replace("\n", "\n" + prepend)
  
  private val INDENT = "  "

  private def indent(text: String) = prependLines(INDENT)(text)

  private def bullet(text: String) = prependLines("  - ", "    ")(text)

  private def printNarrative(text: String): Option[String] =
    Option(text) filterNot { _.isEmpty } map prependLines("> ")

  private def printProof(attempt: Attempt): String = Option(attempt.eContainer) match {
    case Some(prf: Proof) => {
      List(
        "## Proof attempt of " + prf.getLabel,
        numbered(prf.getGoals.asScala),
        "---"
      ).mkString("\n\n")
    }

    case _ => "Unknown attempt"
  }

  private def numbered(goals: Seq[_]): String = {
    val goalStrs = goals map print
    val numbered = goalStrs.zipWithIndex

    val printed = numbered map {
      case (str, i) => prependLines(" " + String.valueOf(i + 1) + ". ", "    ")(str)
    }
    printed.mkString("\n\n")
  }

  private def printLabelList(label: String,
                             elems: Seq[_],
                             empty: => Option[String] = None): Option[String] =
    if (elems.isEmpty) {
      empty map { label + "\n\n" + _ }
    } else {
      val str = label + "\n\n" +
                numbered(elems) 
      Some(str)
    }

  private def printTerm(term: Term): String = term match {
    case display: DisplayTerm => display.getDisplay
    case _ => termPrinter(term) getOrElse "Cannot print term: " + term
  }

  private def classes(cls: Class[_]): Stream[Class[_]] = {
    val superCls = Option(cls.getSuperclass) map classes
    cls #:: (superCls getOrElse Stream.empty)
  }
  
}
