package org.ai4fm.proofprocess.zeves.core.analysis

import org.ai4fm.proofprocess.Trace
import org.ai4fm.proofprocess.core.analysis.{FeatureEqualityHelper, ProofEntryMatcher}
import org.ai4fm.proofprocess.zeves.{ZEvesProofProcessPackage, ZEvesTrace}

/**
 * Proof entry matcher for Z/EVES ProofProcess data.
 * 
 * Provides customised matchers for Z/EVES trace, etc.
 * 
 * @author Andrius Velykis
 */
object ZEvesProofEntryMatcher extends ProofEntryMatcher {

  override def matchTrace(trace1: Trace, trace2: Trace): Boolean = (trace1, trace2) match {
    
    case (zt1: ZEvesTrace, zt2: ZEvesTrace) => matchZEvesTrace(zt1, zt2)
    
    case _ => super.matchTrace(trace1, trace2)
  }

  def matchZEvesTrace(trace1: ZEvesTrace, trace2: ZEvesTrace): Boolean = {

    val ppPackage = ZEvesProofProcessPackage.eINSTANCE

    // only match on a subset of ZEvesTrace features
    val eqHelper = FeatureEqualityHelper(
      ppPackage.getZEvesTrace_Text(),
      ppPackage.getZEvesTrace_UsedLemmas,
      ppPackage.getZEvesTrace_Case)

    eqHelper.equals(trace1, trace2)
  }
  
}
