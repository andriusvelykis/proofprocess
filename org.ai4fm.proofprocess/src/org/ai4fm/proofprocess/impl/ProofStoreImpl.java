/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.Proof;
import org.ai4fm.proofprocess.ProofFeatureDef;
import org.ai4fm.proofprocess.ProofProcessPackage;
import org.ai4fm.proofprocess.ProofStore;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proof Store</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofStoreImpl#getProofs <em>Proofs</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofStoreImpl#getIntents <em>Intents</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofStoreImpl#getFeatures <em>Features</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofStoreImpl extends CDOObjectImpl implements ProofStore {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProofStoreImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessPackage.Literals.PROOF_STORE;
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
	@SuppressWarnings("unchecked")
	public EList<Proof> getProofs() {
		return (EList<Proof>)eDynamicGet(ProofProcessPackage.PROOF_STORE__PROOFS, ProofProcessPackage.Literals.PROOF_STORE__PROOFS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public EList<Intent> getIntents() {
		return (EList<Intent>)eDynamicGet(ProofProcessPackage.PROOF_STORE__INTENTS, ProofProcessPackage.Literals.PROOF_STORE__INTENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ProofFeatureDef> getFeatures() {
		return (EList<ProofFeatureDef>)eDynamicGet(ProofProcessPackage.PROOF_STORE__FEATURES, ProofProcessPackage.Literals.PROOF_STORE__FEATURES, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProofProcessPackage.PROOF_STORE__PROOFS:
				return ((InternalEList<?>)getProofs()).basicRemove(otherEnd, msgs);
			case ProofProcessPackage.PROOF_STORE__INTENTS:
				return ((InternalEList<?>)getIntents()).basicRemove(otherEnd, msgs);
			case ProofProcessPackage.PROOF_STORE__FEATURES:
				return ((InternalEList<?>)getFeatures()).basicRemove(otherEnd, msgs);
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
			case ProofProcessPackage.PROOF_STORE__PROOFS:
				return getProofs();
			case ProofProcessPackage.PROOF_STORE__INTENTS:
				return getIntents();
			case ProofProcessPackage.PROOF_STORE__FEATURES:
				return getFeatures();
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
			case ProofProcessPackage.PROOF_STORE__PROOFS:
				getProofs().clear();
				getProofs().addAll((Collection<? extends Proof>)newValue);
				return;
			case ProofProcessPackage.PROOF_STORE__INTENTS:
				getIntents().clear();
				getIntents().addAll((Collection<? extends Intent>)newValue);
				return;
			case ProofProcessPackage.PROOF_STORE__FEATURES:
				getFeatures().clear();
				getFeatures().addAll((Collection<? extends ProofFeatureDef>)newValue);
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
			case ProofProcessPackage.PROOF_STORE__PROOFS:
				getProofs().clear();
				return;
			case ProofProcessPackage.PROOF_STORE__INTENTS:
				getIntents().clear();
				return;
			case ProofProcessPackage.PROOF_STORE__FEATURES:
				getFeatures().clear();
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
			case ProofProcessPackage.PROOF_STORE__PROOFS:
				return !getProofs().isEmpty();
			case ProofProcessPackage.PROOF_STORE__INTENTS:
				return !getIntents().isEmpty();
			case ProofProcessPackage.PROOF_STORE__FEATURES:
				return !getFeatures().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProofStoreImpl
