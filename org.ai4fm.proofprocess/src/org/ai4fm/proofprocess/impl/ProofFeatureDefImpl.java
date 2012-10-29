/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import org.ai4fm.proofprocess.ProofFeatureDef;
import org.ai4fm.proofprocess.ProofProcessPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proof Feature Def</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofFeatureDefImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofFeatureDefImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofFeatureDefImpl extends CDOObjectImpl implements ProofFeatureDef {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "";

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = "\"\"";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProofFeatureDefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessPackage.Literals.PROOF_FEATURE_DEF;
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
	public String getName() {
		return (String)eDynamicGet(ProofProcessPackage.PROOF_FEATURE_DEF__NAME, ProofProcessPackage.Literals.PROOF_FEATURE_DEF__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(ProofProcessPackage.PROOF_FEATURE_DEF__NAME, ProofProcessPackage.Literals.PROOF_FEATURE_DEF__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eDynamicGet(ProofProcessPackage.PROOF_FEATURE_DEF__DESCRIPTION, ProofProcessPackage.Literals.PROOF_FEATURE_DEF__DESCRIPTION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eDynamicSet(ProofProcessPackage.PROOF_FEATURE_DEF__DESCRIPTION, ProofProcessPackage.Literals.PROOF_FEATURE_DEF__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProofProcessPackage.PROOF_FEATURE_DEF__NAME:
				return getName();
			case ProofProcessPackage.PROOF_FEATURE_DEF__DESCRIPTION:
				return getDescription();
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
			case ProofProcessPackage.PROOF_FEATURE_DEF__NAME:
				setName((String)newValue);
				return;
			case ProofProcessPackage.PROOF_FEATURE_DEF__DESCRIPTION:
				setDescription((String)newValue);
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
			case ProofProcessPackage.PROOF_FEATURE_DEF__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ProofProcessPackage.PROOF_FEATURE_DEF__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
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
			case ProofProcessPackage.PROOF_FEATURE_DEF__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case ProofProcessPackage.PROOF_FEATURE_DEF__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
		}
		return super.eIsSet(featureID);
	}

} //ProofFeatureDefImpl
