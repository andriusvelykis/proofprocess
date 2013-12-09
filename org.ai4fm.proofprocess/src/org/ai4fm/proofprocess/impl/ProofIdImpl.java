/**
 */
package org.ai4fm.proofprocess.impl;

import org.ai4fm.proofprocess.ProofEntry;
import org.ai4fm.proofprocess.ProofId;
import org.ai4fm.proofprocess.ProofProcessPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proof Id</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofIdImpl#getEntryRef <em>Entry Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofIdImpl extends ProofElemImpl implements ProofId {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProofIdImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessPackage.Literals.PROOF_ID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofEntry getEntryRef() {
		return (ProofEntry)eDynamicGet(ProofProcessPackage.PROOF_ID__ENTRY_REF, ProofProcessPackage.Literals.PROOF_ID__ENTRY_REF, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofEntry basicGetEntryRef() {
		return (ProofEntry)eDynamicGet(ProofProcessPackage.PROOF_ID__ENTRY_REF, ProofProcessPackage.Literals.PROOF_ID__ENTRY_REF, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntryRef(ProofEntry newEntryRef) {
		eDynamicSet(ProofProcessPackage.PROOF_ID__ENTRY_REF, ProofProcessPackage.Literals.PROOF_ID__ENTRY_REF, newEntryRef);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProofProcessPackage.PROOF_ID__ENTRY_REF:
				if (resolve) return getEntryRef();
				return basicGetEntryRef();
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
			case ProofProcessPackage.PROOF_ID__ENTRY_REF:
				setEntryRef((ProofEntry)newValue);
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
			case ProofProcessPackage.PROOF_ID__ENTRY_REF:
				setEntryRef((ProofEntry)null);
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
			case ProofProcessPackage.PROOF_ID__ENTRY_REF:
				return basicGetEntryRef() != null;
		}
		return super.eIsSet(featureID);
	}

} //ProofIdImpl
