package org.ai4fm.proofprocess.zeves.core.parse

import net.sourceforge.czt.zeves.util.PrintVisitor
import net.sourceforge.czt.base.ast.Term
import net.sourceforge.czt.z.ast.Pred

/**
 * An extension of Z/EVES PrintVisitor, which supports proof commands. For unsupported
 * terms in the proof commands, uses ".." to avoid complex expressions.
 *
 * @author Andrius Velykis
 */
class ProofCommandPrinter extends PrintVisitor(true) {

  override def visitTerm(term: Term) = "<..>";

  override def visitPred(term: Pred) = "<..>";

}

object ProofCommandPrinter {

  def print(term: Term): String = {
    val printed = term.accept(new ProofCommandPrinter())

    // make it all in one line and remove tabs, also compact all spaces into 1-space
    val tidy = printed.replace("\n", " ").replace("\t", " ").replaceAll(" +", " ").trim()
    tidy
  }
}
