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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

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
public class ProofImpl extends EObjectImpl implements Proof {
	/**
	 * The cached value of the '{@link #getGoals() <em>Goals</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGoals()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> goals;

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
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAttempts() <em>Attempts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttempts()
	 * @generated
	 * @ordered
	 */
	protected EList<Attempt> attempts;

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
	public EList<Term> getGoals() {
		if (goals == null) {
			goals = new EObjectContainmentEList<Term>(Term.class, this, ProofProcessPackage.PROOF__GOALS);
		}
		return goals;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProofProcessPackage.PROOF__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Attempt> getAttempts() {
		if (attempts == null) {
			attempts = new EObjectContainmentEList<Attempt>(Attempt.class, this, ProofProcessPackage.PROOF__ATTEMPTS);
		}
		return attempts;
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
				return goals != null && !goals.isEmpty();
			case ProofProcessPackage.PROOF__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case ProofProcessPackage.PROOF__ATTEMPTS:
				return attempts != null && !attempts.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (label: ");
		result.append(label);
		result.append(')');
		return result.toString();
	}

} //ProofImpl
