package org.ai4fm.proofprocess.ui

import org.ai4fm.proofprocess.ProofStep

/**
 * Adaptor interface for accessing term selections.
 * 
 * Allows retrieving term/sub-term selections within the given proof step context.
 * 
 * @author Andrius Velykis
 */
trait TermSelectionSourceProvider {

  def getTermSource(context: ProofStep): TermSelectionSource

}
