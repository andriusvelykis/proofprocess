/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import org.ai4fm.proofprocess.Attempt;
import org.ai4fm.proofprocess.ProofElem;
import org.ai4fm.proofprocess.ProofProcessPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attempt</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.AttemptImpl#getProof <em>Proof</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttemptImpl extends EObjectImpl implements Attempt {
	/**
	 * The cached value of the '{@link #getProof() <em>Proof</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProof()
	 * @generated
	 * @ordered
	 */
	protected ProofElem proof;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttemptImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessPackage.Literals.ATTEMPT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofElem getProof() {
		return proof;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProof(ProofElem newProof, NotificationChain msgs) {
		ProofElem oldProof = proof;
		proof = newProof;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProofProcessPackage.ATTEMPT__PROOF, oldProof, newProof);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProof(ProofElem newProof) {
		if (newProof != proof) {
			NotificationChain msgs = null;
			if (proof != null)
				msgs = ((InternalEObject)proof).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProofProcessPackage.ATTEMPT__PROOF, null, msgs);
			if (newProof != null)
				msgs = ((InternalEObject)newProof).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProofProcessPackage.ATTEMPT__PROOF, null, msgs);
			msgs = basicSetProof(newProof, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProofProcessPackage.ATTEMPT__PROOF, newProof, newProof));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProofProcessPackage.ATTEMPT__PROOF:
				return basicSetProof(null, msgs);
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
			case ProofProcessPackage.ATTEMPT__PROOF:
				return getProof();
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
			case ProofProcessPackage.ATTEMPT__PROOF:
				setProof((ProofElem)newValue);
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
			case ProofProcessPackage.ATTEMPT__PROOF:
				setProof((ProofElem)null);
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
			case ProofProcessPackage.ATTEMPT__PROOF:
				return proof != null;
		}
		return super.eIsSet(featureID);
	}

} //AttemptImpl
