/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.Attempt;
import org.ai4fm.proofprocess.Proof;
import org.ai4fm.proofprocess.ProofProcessPackage;
import org.ai4fm.proofprocess.Term;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proof</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofImpl#getGoals <em>Goals</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofImpl#getAttempts <em>Attempts</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofImpl extends CDOObjectImpl implements Proof {
	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProofImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessPackage.Literals.PROOF;
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
	@Override
	@SuppressWarnings("unchecked")
	public EList<Term> getGoals() {
		return (EList<Term>)eDynamicGet(ProofProcessPackage.PROOF__GOALS, ProofProcessPackage.Literals.PROOF__GOALS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLabel() {
		return (String)eDynamicGet(ProofProcessPackage.PROOF__LABEL, ProofProcessPackage.Literals.PROOF__LABEL, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabel(String newLabel) {
		eDynamicSet(ProofProcessPackage.PROOF__LABEL, ProofProcessPackage.Literals.PROOF__LABEL, newLabel);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public EList<Attempt> getAttempts() {
		return (EList<Attempt>)eDynamicGet(ProofProcessPackage.PROOF__ATTEMPTS, ProofProcessPackage.Literals.PROOF__ATTEMPTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProofProcessPackage.PROOF__GOALS:
				return ((InternalEList<?>)getGoals()).basicRemove(otherEnd, msgs);
			case ProofProcessPackage.PROOF__ATTEMPTS:
				return ((InternalEList<?>)getAttempts()).basicRemove(otherEnd, msgs);
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
			case ProofProcessPackage.PROOF__GOALS:
				return getGoals();
			case ProofProcessPackage.PROOF__LABEL:
				return getLabel();
			case ProofProcessPackage.PROOF__ATTEMPTS:
				return getAttempts();
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
			case ProofProcessPackage.PROOF__GOALS:
				getGoals().clear();
				getGoals().addAll((Collection<? extends Term>)newValue);
				return;
			case ProofProcessPackage.PROOF__LABEL:
				setLabel((String)newValue);
				return;
			case ProofProcessPackage.PROOF__ATTEMPTS:
				getAttempts().clear();
				getAttempts().addAll((Collection<? extends Attempt>)newValue);
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
			case ProofProcessPackage.PROOF__GOALS:
				getGoals().clear();
				return;
			case ProofProcessPackage.PROOF__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case ProofProcessPackage.PROOF__ATTEMPTS:
				getAttempts().clear();
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
			case ProofProcessPackage.PROOF__GOALS:
				return !getGoals().isEmpty();
			case ProofProcessPackage.PROOF__LABEL:
				return LABEL_EDEFAULT == null ? getLabel() != null : !LABEL_EDEFAULT.equals(getLabel());
			case ProofProcessPackage.PROOF__ATTEMPTS:
				return !getAttempts().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProofImpl
