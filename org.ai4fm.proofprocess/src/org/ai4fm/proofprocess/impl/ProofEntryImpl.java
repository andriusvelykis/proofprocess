/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import org.ai4fm.proofprocess.ProofEntry;
import org.ai4fm.proofprocess.ProofProcessPackage;
import org.ai4fm.proofprocess.ProofStep;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proof Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofEntryImpl#getProofStep <em>Proof Step</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofEntryImpl extends ProofElemImpl implements ProofEntry {
	/**
	 * The cached value of the '{@link #getProofStep() <em>Proof Step</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProofStep()
	 * @generated
	 * @ordered
	 */
	protected ProofStep proofStep;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProofEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessPackage.Literals.PROOF_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofStep getProofStep() {
		return proofStep;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProofStep(ProofStep newProofStep, NotificationChain msgs) {
		ProofStep oldProofStep = proofStep;
		proofStep = newProofStep;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProofProcessPackage.PROOF_ENTRY__PROOF_STEP, oldProofStep, newProofStep);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProofStep(ProofStep newProofStep) {
		if (newProofStep != proofStep) {
			NotificationChain msgs = null;
			if (proofStep != null)
				msgs = ((InternalEObject)proofStep).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProofProcessPackage.PROOF_ENTRY__PROOF_STEP, null, msgs);
			if (newProofStep != null)
				msgs = ((InternalEObject)newProofStep).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProofProcessPackage.PROOF_ENTRY__PROOF_STEP, null, msgs);
			msgs = basicSetProofStep(newProofStep, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProofProcessPackage.PROOF_ENTRY__PROOF_STEP, newProofStep, newProofStep));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProofProcessPackage.PROOF_ENTRY__PROOF_STEP:
				return basicSetProofStep(null, msgs);
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
			case ProofProcessPackage.PROOF_ENTRY__PROOF_STEP:
				return getProofStep();
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
			case ProofProcessPackage.PROOF_ENTRY__PROOF_STEP:
				setProofStep((ProofStep)newValue);
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
			case ProofProcessPackage.PROOF_ENTRY__PROOF_STEP:
				setProofStep((ProofStep)null);
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
			case ProofProcessPackage.PROOF_ENTRY__PROOF_STEP:
				return proofStep != null;
		}
		return super.eIsSet(featureID);
	}

} //ProofEntryImpl
