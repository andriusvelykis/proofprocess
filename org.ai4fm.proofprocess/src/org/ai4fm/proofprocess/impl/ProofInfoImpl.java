/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.ProofFeature;
import org.ai4fm.proofprocess.ProofInfo;
import org.ai4fm.proofprocess.ProofProcessPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proof Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofInfoImpl#getIntent <em>Intent</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofInfoImpl#getNarrative <em>Narrative</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofInfoImpl#getInFeatures <em>In Features</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofInfoImpl#getOutFeatures <em>Out Features</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofInfoImpl extends CDOObjectImpl implements ProofInfo {
	/**
	 * The default value of the '{@link #getNarrative() <em>Narrative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNarrative()
	 * @generated
	 * @ordered
	 */
	protected static final String NARRATIVE_EDEFAULT = "\"\"";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProofInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessPackage.Literals.PROOF_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Intent getIntent() {
		return (Intent)eDynamicGet(ProofProcessPackage.PROOF_INFO__INTENT, ProofProcessPackage.Literals.PROOF_INFO__INTENT, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Intent basicGetIntent() {
		return (Intent)eDynamicGet(ProofProcessPackage.PROOF_INFO__INTENT, ProofProcessPackage.Literals.PROOF_INFO__INTENT, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIntent(Intent newIntent) {
		eDynamicSet(ProofProcessPackage.PROOF_INFO__INTENT, ProofProcessPackage.Literals.PROOF_INFO__INTENT, newIntent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getNarrative() {
		return (String)eDynamicGet(ProofProcessPackage.PROOF_INFO__NARRATIVE, ProofProcessPackage.Literals.PROOF_INFO__NARRATIVE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNarrative(String newNarrative) {
		eDynamicSet(ProofProcessPackage.PROOF_INFO__NARRATIVE, ProofProcessPackage.Literals.PROOF_INFO__NARRATIVE, newNarrative);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public EList<ProofFeature> getInFeatures() {
		return (EList<ProofFeature>)eDynamicGet(ProofProcessPackage.PROOF_INFO__IN_FEATURES, ProofProcessPackage.Literals.PROOF_INFO__IN_FEATURES, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public EList<ProofFeature> getOutFeatures() {
		return (EList<ProofFeature>)eDynamicGet(ProofProcessPackage.PROOF_INFO__OUT_FEATURES, ProofProcessPackage.Literals.PROOF_INFO__OUT_FEATURES, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProofProcessPackage.PROOF_INFO__IN_FEATURES:
				return ((InternalEList<?>)getInFeatures()).basicRemove(otherEnd, msgs);
			case ProofProcessPackage.PROOF_INFO__OUT_FEATURES:
				return ((InternalEList<?>)getOutFeatures()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProofProcessPackage.PROOF_INFO__INTENT:
				if (resolve) return getIntent();
				return basicGetIntent();
			case ProofProcessPackage.PROOF_INFO__NARRATIVE:
				return getNarrative();
			case ProofProcessPackage.PROOF_INFO__IN_FEATURES:
				return getInFeatures();
			case ProofProcessPackage.PROOF_INFO__OUT_FEATURES:
				return getOutFeatures();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ProofProcessPackage.PROOF_INFO__INTENT:
				setIntent((Intent)newValue);
				return;
			case ProofProcessPackage.PROOF_INFO__NARRATIVE:
				setNarrative((String)newValue);
				return;
			case ProofProcessPackage.PROOF_INFO__IN_FEATURES:
				getInFeatures().clear();
				getInFeatures().addAll((Collection<? extends ProofFeature>)newValue);
				return;
			case ProofProcessPackage.PROOF_INFO__OUT_FEATURES:
				getOutFeatures().clear();
				getOutFeatures().addAll((Collection<? extends ProofFeature>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ProofProcessPackage.PROOF_INFO__INTENT:
				setIntent((Intent)null);
				return;
			case ProofProcessPackage.PROOF_INFO__NARRATIVE:
				setNarrative(NARRATIVE_EDEFAULT);
				return;
			case ProofProcessPackage.PROOF_INFO__IN_FEATURES:
				getInFeatures().clear();
				return;
			case ProofProcessPackage.PROOF_INFO__OUT_FEATURES:
				getOutFeatures().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ProofProcessPackage.PROOF_INFO__INTENT:
				return basicGetIntent() != null;
			case ProofProcessPackage.PROOF_INFO__NARRATIVE:
				return NARRATIVE_EDEFAULT == null ? getNarrative() != null : !NARRATIVE_EDEFAULT.equals(getNarrative());
			case ProofProcessPackage.PROOF_INFO__IN_FEATURES:
				return !getInFeatures().isEmpty();
			case ProofProcessPackage.PROOF_INFO__OUT_FEATURES:
				return !getOutFeatures().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProofInfoImpl
