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
import org.ai4fm.proofprocess.ProofProcessPackage;
import org.ai4fm.proofprocess.ProofStore;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proof Store</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofStoreImpl#getProofs <em>Proofs</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofStoreImpl#getIntents <em>Intents</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofStoreImpl extends EObjectImpl implements ProofStore {
	/**
	 * The cached value of the '{@link #getProofs() <em>Proofs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProofs()
	 * @generated
	 * @ordered
	 */
	protected EList<Proof> proofs;

	/**
	 * The cached value of the '{@link #getIntents() <em>Intents</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntents()
	 * @generated
	 * @ordered
	 */
	protected EList<Intent> intents;

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
	public EList<Proof> getProofs() {
		if (proofs == null) {
			proofs = new EObjectContainmentEList<Proof>(Proof.class, this, ProofProcessPackage.PROOF_STORE__PROOFS);
		}
		return proofs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Intent> getIntents() {
		if (intents == null) {
			intents = new EObjectContainmentEList<Intent>(Intent.class, this, ProofProcessPackage.PROOF_STORE__INTENTS);
		}
		return intents;
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
				return proofs != null && !proofs.isEmpty();
			case ProofProcessPackage.PROOF_STORE__INTENTS:
				return intents != null && !intents.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProofStoreImpl
