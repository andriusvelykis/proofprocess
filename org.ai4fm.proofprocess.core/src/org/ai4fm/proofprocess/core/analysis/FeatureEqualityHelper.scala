package org.ai4fm.proofprocess.core.analysis

import org.eclipse.emf.ecore.{EObject, EStructuralFeature}
import org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper

/**
 * A helper for EMF equality matching. Allows using only a subset of EObject features
 * for equality. 
 * 
 * @author Andrius Velykis
 */
class FeatureEqualityHelper(eqFeatures: Set[EStructuralFeature]) extends EqualityHelper {

  override def haveEqualFeature(eObj1: EObject, eObj2: EObject, feature: EStructuralFeature): Boolean =
    if (eqFeatures.contains(feature)) {
      super.haveEqualFeature(eObj1, eObj2, feature)
    } else {
      // this feature is not included in equality matching, so take it as equal without checking
      true
    }

}

object FeatureEqualityHelper {
  
  def apply(eqFeatures: EStructuralFeature*): FeatureEqualityHelper =
    new FeatureEqualityHelper(eqFeatures.toSet)
  
}
