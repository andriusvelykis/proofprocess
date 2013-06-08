package org.ai4fm.proofprocess.zeves.ui.term

import net.sourceforge.czt.base.{ast => z}

import org.ai4fm.proofprocess.zeves.{CztTerm, ZEvesProofProcessFactory}

/**
 * Extractor/factory object for CztTerm <-> z.Term
 * 
 * @author Andrius Velykis
 */
object CztPPTerm {

  private def factory = ZEvesProofProcessFactory.eINSTANCE

  def apply(term: z.Term, display: String): CztTerm = {
    val ppTerm = factory.createCztTerm
    ppTerm.setTerm(term)
    ppTerm.setDisplay(display)
    ppTerm
  }

  def unapply(cztTerm: CztTerm): Option[z.Term] = Option(cztTerm.getTerm)

}
