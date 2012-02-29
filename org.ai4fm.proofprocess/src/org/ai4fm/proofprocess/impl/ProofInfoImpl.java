/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.ProofInfo;
import org.ai4fm.proofprocess.ProofProcessPackage;
import org.ai4fm.proofprocess.Property;

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
 * An implementation of the model object '<em><b>Proof Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofInfoImpl#getIntent <em>Intent</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofInfoImpl#getNarrative <em>Narrative</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofInfoImpl#getInProps <em>In Props</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofInfoImpl#getOutProps <em>Out Props</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofInfoImpl extends EObjectImpl implements ProofInfo {
	/**
	 * The cached value of the '{@link #getIntent() <em>Intent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntent()
	 * @generated
	 * @ordered
	 */
	protected Intent intent;

	/**
	 * The default value of the '{@link #getNarrative() <em>Narrative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNarrative()
	 * @generated
	 * @ordered
	 */
	protected static final String NARRATIVE_EDEFAULT = "\"\"";

	/**
	 * The cached value of the '{@link #getNarrative() <em>Narrative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNarrative()
	 * @generated
	 * @ordered
	 */
	protected String narrative = NARRATIVE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInProps() <em>In Props</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInProps()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> inProps;

	/**
	 * The cached value of the '{@link #getOutProps() <em>Out Props</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutProps()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> outProps;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProofInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessPackage.Literals.PROOF_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Intent getIntent() {
		if (intent != null && intent.eIsProxy()) {
			InternalEObject oldIntent = (InternalEObject)intent;
			intent = (Intent)eResolveProxy(oldIntent);
			if (intent != oldIntent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ProofProcessPackage.PROOF_INFO__INTENT, oldIntent, intent));
			}
		}
		return intent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Intent basicGetIntent() {
		return intent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntent(Intent newIntent) {
		Intent oldIntent = intent;
		intent = newIntent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProofProcessPackage.PROOF_INFO__INTENT, oldIntent, intent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNarrative() {
		return narrative;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNarrative(String newNarrative) {
		String oldNarrative = narrative;
		narrative = newNarrative;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProofProcessPackage.PROOF_INFO__NARRATIVE, oldNarrative, narrative));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Property> getInProps() {
		if (inProps == null) {
			inProps = new EObjectContainmentEList<Property>(Property.class, this, ProofProcessPackage.PROOF_INFO__IN_PROPS);
		}
		return inProps;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Property> getOutProps() {
		if (outProps == null) {
			outProps = new EObjectContainmentEList<Property>(Property.class, this, ProofProcessPackage.PROOF_INFO__OUT_PROPS);
		}
		return outProps;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProofProcessPackage.PROOF_INFO__IN_PROPS:
				return ((InternalEList<?>)getInProps()).basicRemove(otherEnd, msgs);
			case ProofProcessPackage.PROOF_INFO__OUT_PROPS:
				return ((InternalEList<?>)getOutProps()).basicRemove(otherEnd, msgs);
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
			case ProofProcessPackage.PROOF_INFO__INTENT:
				if (resolve) return getIntent();
				return basicGetIntent();
			case ProofProcessPackage.PROOF_INFO__NARRATIVE:
				return getNarrative();
			case ProofProcessPackage.PROOF_INFO__IN_PROPS:
				return getInProps();
			case ProofProcessPackage.PROOF_INFO__OUT_PROPS:
				return getOutProps();
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
			case ProofProcessPackage.PROOF_INFO__INTENT:
				setIntent((Intent)newValue);
				return;
			case ProofProcessPackage.PROOF_INFO__NARRATIVE:
				setNarrative((String)newValue);
				return;
			case ProofProcessPackage.PROOF_INFO__IN_PROPS:
				getInProps().clear();
				getInProps().addAll((Collection<? extends Property>)newValue);
				return;
			case ProofProcessPackage.PROOF_INFO__OUT_PROPS:
				getOutProps().clear();
				getOutProps().addAll((Collection<? extends Property>)newValue);
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
			case ProofProcessPackage.PROOF_INFO__INTENT:
				setIntent((Intent)null);
				return;
			case ProofProcessPackage.PROOF_INFO__NARRATIVE:
				setNarrative(NARRATIVE_EDEFAULT);
				return;
			case ProofProcessPackage.PROOF_INFO__IN_PROPS:
				getInProps().clear();
				return;
			case ProofProcessPackage.PROOF_INFO__OUT_PROPS:
				getOutProps().clear();
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
			case ProofProcessPackage.PROOF_INFO__INTENT:
				return intent != null;
			case ProofProcessPackage.PROOF_INFO__NARRATIVE:
				return NARRATIVE_EDEFAULT == null ? narrative != null : !NARRATIVE_EDEFAULT.equals(narrative);
			case ProofProcessPackage.PROOF_INFO__IN_PROPS:
				return inProps != null && !inProps.isEmpty();
			case ProofProcessPackage.PROOF_INFO__OUT_PROPS:
				return outProps != null && !outProps.isEmpty();
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
		result.append(" (narrative: ");
		result.append(narrative);
		result.append(')');
		return result.toString();
	}

} //ProofInfoImpl
