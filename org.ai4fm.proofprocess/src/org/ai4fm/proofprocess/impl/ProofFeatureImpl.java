/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.ProofFeature;
import org.ai4fm.proofprocess.ProofFeatureDef;
import org.ai4fm.proofprocess.ProofFeatureType;
import org.ai4fm.proofprocess.ProofProcessPackage;
import org.ai4fm.proofprocess.Term;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proof Feature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofFeatureImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofFeatureImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofFeatureImpl#getParams <em>Params</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofFeatureImpl extends CDOObjectImpl implements ProofFeature {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final ProofFeatureType TYPE_EDEFAULT = ProofFeatureType.USER;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProofFeatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessPackage.Literals.PROOF_FEATURE;
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
	public ProofFeatureDef getName() {
		return (ProofFeatureDef)eDynamicGet(ProofProcessPackage.PROOF_FEATURE__NAME, ProofProcessPackage.Literals.PROOF_FEATURE__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofFeatureDef basicGetName() {
		return (ProofFeatureDef)eDynamicGet(ProofProcessPackage.PROOF_FEATURE__NAME, ProofProcessPackage.Literals.PROOF_FEATURE__NAME, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(ProofFeatureDef newName) {
		eDynamicSet(ProofProcessPackage.PROOF_FEATURE__NAME, ProofProcessPackage.Literals.PROOF_FEATURE__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofFeatureType getType() {
		return (ProofFeatureType)eDynamicGet(ProofProcessPackage.PROOF_FEATURE__TYPE, ProofProcessPackage.Literals.PROOF_FEATURE__TYPE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ProofFeatureType newType) {
		eDynamicSet(ProofProcessPackage.PROOF_FEATURE__TYPE, ProofProcessPackage.Literals.PROOF_FEATURE__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Term> getParams() {
		return (EList<Term>)eDynamicGet(ProofProcessPackage.PROOF_FEATURE__PARAMS, ProofProcessPackage.Literals.PROOF_FEATURE__PARAMS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProofProcessPackage.PROOF_FEATURE__PARAMS:
				return ((InternalEList<?>)getParams()).basicRemove(otherEnd, msgs);
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
			case ProofProcessPackage.PROOF_FEATURE__NAME:
				if (resolve) return getName();
				return basicGetName();
			case ProofProcessPackage.PROOF_FEATURE__TYPE:
				return getType();
			case ProofProcessPackage.PROOF_FEATURE__PARAMS:
				return getParams();
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
			case ProofProcessPackage.PROOF_FEATURE__NAME:
				setName((ProofFeatureDef)newValue);
				return;
			case ProofProcessPackage.PROOF_FEATURE__TYPE:
				setType((ProofFeatureType)newValue);
				return;
			case ProofProcessPackage.PROOF_FEATURE__PARAMS:
				getParams().clear();
				getParams().addAll((Collection<? extends Term>)newValue);
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
			case ProofProcessPackage.PROOF_FEATURE__NAME:
				setName((ProofFeatureDef)null);
				return;
			case ProofProcessPackage.PROOF_FEATURE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case ProofProcessPackage.PROOF_FEATURE__PARAMS:
				getParams().clear();
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
			case ProofProcessPackage.PROOF_FEATURE__NAME:
				return basicGetName() != null;
			case ProofProcessPackage.PROOF_FEATURE__TYPE:
				return getType() != TYPE_EDEFAULT;
			case ProofProcessPackage.PROOF_FEATURE__PARAMS:
				return !getParams().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProofFeatureImpl
