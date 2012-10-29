/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.log.impl;

import org.ai4fm.proofprocess.ProofEntry;

import org.ai4fm.proofprocess.log.ProofActivity;
import org.ai4fm.proofprocess.log.ProofProcessLogPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proof Activity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.log.impl.ProofActivityImpl#getProofRef <em>Proof Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofActivityImpl extends ActivityImpl implements ProofActivity {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProofActivityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessLogPackage.Literals.PROOF_ACTIVITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofEntry getProofRef() {
		return (ProofEntry)eDynamicGet(ProofProcessLogPackage.PROOF_ACTIVITY__PROOF_REF, ProofProcessLogPackage.Literals.PROOF_ACTIVITY__PROOF_REF, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofEntry basicGetProofRef() {
		return (ProofEntry)eDynamicGet(ProofProcessLogPackage.PROOF_ACTIVITY__PROOF_REF, ProofProcessLogPackage.Literals.PROOF_ACTIVITY__PROOF_REF, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProofRef(ProofEntry newProofRef) {
		eDynamicSet(ProofProcessLogPackage.PROOF_ACTIVITY__PROOF_REF, ProofProcessLogPackage.Literals.PROOF_ACTIVITY__PROOF_REF, newProofRef);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProofProcessLogPackage.PROOF_ACTIVITY__PROOF_REF:
				if (resolve) return getProofRef();
				return basicGetProofRef();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ProofProcessLogPackage.PROOF_ACTIVITY__PROOF_REF:
				setProofRef((ProofEntry)newValue);
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
			case ProofProcessLogPackage.PROOF_ACTIVITY__PROOF_REF:
				setProofRef((ProofEntry)null);
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
			case ProofProcessLogPackage.PROOF_ACTIVITY__PROOF_REF:
				return basicGetProofRef() != null;
		}
		return super.eIsSet(featureID);
	}

} //ProofActivityImpl
