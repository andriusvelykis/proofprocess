/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.Attempt;
import org.ai4fm.proofprocess.AttemptGroup;
import org.ai4fm.proofprocess.CompositionType;
import org.ai4fm.proofprocess.ProofProcessPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attempt Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.AttemptGroupImpl#getCompositionType <em>Composition Type</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.AttemptGroupImpl#getAttempts <em>Attempts</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.AttemptGroupImpl#getContained <em>Contained</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttemptGroupImpl extends AttemptImpl implements AttemptGroup {
	/**
	 * The default value of the '{@link #getCompositionType() <em>Composition Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompositionType()
	 * @generated
	 * @ordered
	 */
	protected static final CompositionType COMPOSITION_TYPE_EDEFAULT = CompositionType.SEQUENTIAL;

	/**
	 * The cached value of the '{@link #getCompositionType() <em>Composition Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompositionType()
	 * @generated
	 * @ordered
	 */
	protected CompositionType compositionType = COMPOSITION_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAttempts() <em>Attempts</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttempts()
	 * @generated
	 * @ordered
	 */
	protected EList<Attempt> attempts;

	/**
	 * The cached value of the '{@link #getContained() <em>Contained</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContained()
	 * @generated
	 * @ordered
	 */
	protected EList<Attempt> contained;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttemptGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessPackage.Literals.ATTEMPT_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositionType getCompositionType() {
		return compositionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompositionType(CompositionType newCompositionType) {
		CompositionType oldCompositionType = compositionType;
		compositionType = newCompositionType == null ? COMPOSITION_TYPE_EDEFAULT : newCompositionType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProofProcessPackage.ATTEMPT_GROUP__COMPOSITION_TYPE, oldCompositionType, compositionType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Attempt> getAttempts() {
		if (attempts == null) {
			attempts = new EObjectResolvingEList<Attempt>(Attempt.class, this, ProofProcessPackage.ATTEMPT_GROUP__ATTEMPTS);
		}
		return attempts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Attempt> getContained() {
		if (contained == null) {
			contained = new EObjectContainmentEList<Attempt>(Attempt.class, this, ProofProcessPackage.ATTEMPT_GROUP__CONTAINED);
		}
		return contained;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProofProcessPackage.ATTEMPT_GROUP__CONTAINED:
				return ((InternalEList<?>)getContained()).basicRemove(otherEnd, msgs);
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
			case ProofProcessPackage.ATTEMPT_GROUP__COMPOSITION_TYPE:
				return getCompositionType();
			case ProofProcessPackage.ATTEMPT_GROUP__ATTEMPTS:
				return getAttempts();
			case ProofProcessPackage.ATTEMPT_GROUP__CONTAINED:
				return getContained();
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
			case ProofProcessPackage.ATTEMPT_GROUP__COMPOSITION_TYPE:
				setCompositionType((CompositionType)newValue);
				return;
			case ProofProcessPackage.ATTEMPT_GROUP__ATTEMPTS:
				getAttempts().clear();
				getAttempts().addAll((Collection<? extends Attempt>)newValue);
				return;
			case ProofProcessPackage.ATTEMPT_GROUP__CONTAINED:
				getContained().clear();
				getContained().addAll((Collection<? extends Attempt>)newValue);
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
			case ProofProcessPackage.ATTEMPT_GROUP__COMPOSITION_TYPE:
				setCompositionType(COMPOSITION_TYPE_EDEFAULT);
				return;
			case ProofProcessPackage.ATTEMPT_GROUP__ATTEMPTS:
				getAttempts().clear();
				return;
			case ProofProcessPackage.ATTEMPT_GROUP__CONTAINED:
				getContained().clear();
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
			case ProofProcessPackage.ATTEMPT_GROUP__COMPOSITION_TYPE:
				return compositionType != COMPOSITION_TYPE_EDEFAULT;
			case ProofProcessPackage.ATTEMPT_GROUP__ATTEMPTS:
				return attempts != null && !attempts.isEmpty();
			case ProofProcessPackage.ATTEMPT_GROUP__CONTAINED:
				return contained != null && !contained.isEmpty();
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
		result.append(" (compositionType: ");
		result.append(compositionType);
		result.append(')');
		return result.toString();
	}

} //AttemptGroupImpl
