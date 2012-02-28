/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import org.ai4fm.proofprocess.ProofDecor;
import org.ai4fm.proofprocess.ProofElem;
import org.ai4fm.proofprocess.ProofProcessPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proof Decor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofDecorImpl#getEntry <em>Entry</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofDecorImpl extends ProofElemImpl implements ProofDecor {
	/**
	 * The cached value of the '{@link #getEntry() <em>Entry</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntry()
	 * @generated
	 * @ordered
	 */
	protected ProofElem entry;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProofDecorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessPackage.Literals.PROOF_DECOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofElem getEntry() {
		return entry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntry(ProofElem newEntry, NotificationChain msgs) {
		ProofElem oldEntry = entry;
		entry = newEntry;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProofProcessPackage.PROOF_DECOR__ENTRY, oldEntry, newEntry);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntry(ProofElem newEntry) {
		if (newEntry != entry) {
			NotificationChain msgs = null;
			if (entry != null)
				msgs = ((InternalEObject)entry).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProofProcessPackage.PROOF_DECOR__ENTRY, null, msgs);
			if (newEntry != null)
				msgs = ((InternalEObject)newEntry).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProofProcessPackage.PROOF_DECOR__ENTRY, null, msgs);
			msgs = basicSetEntry(newEntry, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProofProcessPackage.PROOF_DECOR__ENTRY, newEntry, newEntry));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProofProcessPackage.PROOF_DECOR__ENTRY:
				return basicSetEntry(null, msgs);
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
			case ProofProcessPackage.PROOF_DECOR__ENTRY:
				return getEntry();
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
			case ProofProcessPackage.PROOF_DECOR__ENTRY:
				setEntry((ProofElem)newValue);
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
			case ProofProcessPackage.PROOF_DECOR__ENTRY:
				setEntry((ProofElem)null);
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
			case ProofProcessPackage.PROOF_DECOR__ENTRY:
				return entry != null;
		}
		return super.eIsSet(featureID);
	}

} //ProofDecorImpl
