/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves.impl;

import org.ai4fm.proofprocess.Attempt;

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
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ProofActivityImpl#getAttempt <em>Attempt</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofActivityImpl extends ActivityImpl implements ProofActivity {
	/**
	 * The cached value of the '{@link #getAttempt() <em>Attempt</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttempt()
	 * @generated
	 * @ordered
	 */
	protected Attempt attempt;

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
	public Attempt getAttempt() {
		if (attempt != null && attempt.eIsProxy()) {
			InternalEObject oldAttempt = (InternalEObject)attempt;
			attempt = (Attempt)eResolveProxy(oldAttempt);
			if (attempt != oldAttempt) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ZEvesProofProcessPackage.PROOF_ACTIVITY__ATTEMPT, oldAttempt, attempt));
			}
		}
		return attempt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attempt basicGetAttempt() {
		return attempt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttempt(Attempt newAttempt) {
		Attempt oldAttempt = attempt;
		attempt = newAttempt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ZEvesProofProcessPackage.PROOF_ACTIVITY__ATTEMPT, oldAttempt, attempt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ZEvesProofProcessPackage.PROOF_ACTIVITY__ATTEMPT:
				if (resolve) return getAttempt();
				return basicGetAttempt();
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
			case ZEvesProofProcessPackage.PROOF_ACTIVITY__ATTEMPT:
				setAttempt((Attempt)newValue);
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
			case ZEvesProofProcessPackage.PROOF_ACTIVITY__ATTEMPT:
				setAttempt((Attempt)null);
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
			case ZEvesProofProcessPackage.PROOF_ACTIVITY__ATTEMPT:
				return attempt != null;
		}
		return super.eIsSet(featureID);
	}

} //ProofActivityImpl
