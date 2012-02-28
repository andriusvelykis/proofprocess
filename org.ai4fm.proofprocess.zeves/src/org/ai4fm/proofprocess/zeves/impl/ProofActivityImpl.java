/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves.impl;

import org.ai4fm.proofprocess.ProofEntry;

import org.ai4fm.proofprocess.zeves.ProofActivity;
import org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage;

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
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ProofActivityImpl#getProofRef <em>Proof Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofActivityImpl extends ActivityImpl implements ProofActivity {
	/**
	 * The cached value of the '{@link #getProofRef() <em>Proof Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProofRef()
	 * @generated
	 * @ordered
	 */
	protected ProofEntry proofRef;

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
		return ZEvesProofProcessPackage.Literals.PROOF_ACTIVITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofEntry getProofRef() {
		if (proofRef != null && proofRef.eIsProxy()) {
			InternalEObject oldProofRef = (InternalEObject)proofRef;
			proofRef = (ProofEntry)eResolveProxy(oldProofRef);
			if (proofRef != oldProofRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ZEvesProofProcessPackage.PROOF_ACTIVITY__PROOF_REF, oldProofRef, proofRef));
			}
		}
		return proofRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofEntry basicGetProofRef() {
		return proofRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProofRef(ProofEntry newProofRef) {
		ProofEntry oldProofRef = proofRef;
		proofRef = newProofRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ZEvesProofProcessPackage.PROOF_ACTIVITY__PROOF_REF, oldProofRef, proofRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ZEvesProofProcessPackage.PROOF_ACTIVITY__PROOF_REF:
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
			case ZEvesProofProcessPackage.PROOF_ACTIVITY__PROOF_REF:
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
			case ZEvesProofProcessPackage.PROOF_ACTIVITY__PROOF_REF:
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
			case ZEvesProofProcessPackage.PROOF_ACTIVITY__PROOF_REF:
				return proofRef != null;
		}
		return super.eIsSet(featureID);
	}

} //ProofActivityImpl
