package org.ai4fm.proofprocess.ui

import org.ai4fm.proofprocess.Term

import org.eclipse.jface.viewers.StyledString


/**
 * An interface for term selection source.
 * 
 * Provides accessors for term/sub-term selection options:
 * sub-terms, schema terms (terms with placeholders) and term rendering. 
 * 
 * @author Andrius Velykis
 */
trait TermSelectionSource {

  def rendered: StyledString

  def subTerms: List[Term]

  def schemaTerms: List[Term]

  def diff(other: Term): (Term, Term)

}
