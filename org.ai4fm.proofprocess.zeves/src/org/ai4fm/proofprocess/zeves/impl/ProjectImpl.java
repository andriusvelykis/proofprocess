/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.Proof;

import org.ai4fm.proofprocess.zeves.Activity;
import org.ai4fm.proofprocess.zeves.Project;
import org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage;

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
 * An implementation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ProjectImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ProjectImpl#getZEvesVersion <em>ZEves Version</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ProjectImpl#getProofs <em>Proofs</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ProjectImpl#getActivities <em>Activities</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ProjectImpl#getIntents <em>Intents</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProjectImpl extends EObjectImpl implements Project {
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
	 * The default value of the '{@link #getZEvesVersion() <em>ZEves Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZEvesVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String ZEVES_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getZEvesVersion() <em>ZEves Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZEvesVersion()
	 * @generated
	 * @ordered
	 */
	protected String zEvesVersion = ZEVES_VERSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProofs() <em>Proofs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProofs()
	 * @generated
	 * @ordered
	 */
	protected EList<Proof> proofs;

	/**
	 * The cached value of the '{@link #getActivities() <em>Activities</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivities()
	 * @generated
	 * @ordered
	 */
	protected EList<Activity> activities;

	/**
	 * The cached value of the '{@link #getIntents() <em>Intents</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntents()
	 * @generated
	 * @ordered
	 */
	protected EList<Intent> intents;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ZEvesProofProcessPackage.Literals.PROJECT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ZEvesProofProcessPackage.PROJECT__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getZEvesVersion() {
		return zEvesVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setZEvesVersion(String newZEvesVersion) {
		String oldZEvesVersion = zEvesVersion;
		zEvesVersion = newZEvesVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ZEvesProofProcessPackage.PROJECT__ZEVES_VERSION, oldZEvesVersion, zEvesVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Proof> getProofs() {
		if (proofs == null) {
			proofs = new EObjectContainmentEList<Proof>(Proof.class, this, ZEvesProofProcessPackage.PROJECT__PROOFS);
		}
		return proofs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Activity> getActivities() {
		if (activities == null) {
			activities = new EObjectContainmentEList<Activity>(Activity.class, this, ZEvesProofProcessPackage.PROJECT__ACTIVITIES);
		}
		return activities;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Intent> getIntents() {
		if (intents == null) {
			intents = new EObjectContainmentEList<Intent>(Intent.class, this, ZEvesProofProcessPackage.PROJECT__INTENTS);
		}
		return intents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ZEvesProofProcessPackage.PROJECT__PROOFS:
				return ((InternalEList<?>)getProofs()).basicRemove(otherEnd, msgs);
			case ZEvesProofProcessPackage.PROJECT__ACTIVITIES:
				return ((InternalEList<?>)getActivities()).basicRemove(otherEnd, msgs);
			case ZEvesProofProcessPackage.PROJECT__INTENTS:
				return ((InternalEList<?>)getIntents()).basicRemove(otherEnd, msgs);
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
			case ZEvesProofProcessPackage.PROJECT__LABEL:
				return getLabel();
			case ZEvesProofProcessPackage.PROJECT__ZEVES_VERSION:
				return getZEvesVersion();
			case ZEvesProofProcessPackage.PROJECT__PROOFS:
				return getProofs();
			case ZEvesProofProcessPackage.PROJECT__ACTIVITIES:
				return getActivities();
			case ZEvesProofProcessPackage.PROJECT__INTENTS:
				return getIntents();
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
			case ZEvesProofProcessPackage.PROJECT__LABEL:
				setLabel((String)newValue);
				return;
			case ZEvesProofProcessPackage.PROJECT__ZEVES_VERSION:
				setZEvesVersion((String)newValue);
				return;
			case ZEvesProofProcessPackage.PROJECT__PROOFS:
				getProofs().clear();
				getProofs().addAll((Collection<? extends Proof>)newValue);
				return;
			case ZEvesProofProcessPackage.PROJECT__ACTIVITIES:
				getActivities().clear();
				getActivities().addAll((Collection<? extends Activity>)newValue);
				return;
			case ZEvesProofProcessPackage.PROJECT__INTENTS:
				getIntents().clear();
				getIntents().addAll((Collection<? extends Intent>)newValue);
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
			case ZEvesProofProcessPackage.PROJECT__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case ZEvesProofProcessPackage.PROJECT__ZEVES_VERSION:
				setZEvesVersion(ZEVES_VERSION_EDEFAULT);
				return;
			case ZEvesProofProcessPackage.PROJECT__PROOFS:
				getProofs().clear();
				return;
			case ZEvesProofProcessPackage.PROJECT__ACTIVITIES:
				getActivities().clear();
				return;
			case ZEvesProofProcessPackage.PROJECT__INTENTS:
				getIntents().clear();
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
			case ZEvesProofProcessPackage.PROJECT__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case ZEvesProofProcessPackage.PROJECT__ZEVES_VERSION:
				return ZEVES_VERSION_EDEFAULT == null ? zEvesVersion != null : !ZEVES_VERSION_EDEFAULT.equals(zEvesVersion);
			case ZEvesProofProcessPackage.PROJECT__PROOFS:
				return proofs != null && !proofs.isEmpty();
			case ZEvesProofProcessPackage.PROJECT__ACTIVITIES:
				return activities != null && !activities.isEmpty();
			case ZEvesProofProcessPackage.PROJECT__INTENTS:
				return intents != null && !intents.isEmpty();
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
		result.append(", zEvesVersion: ");
		result.append(zEvesVersion);
		result.append(')');
		return result.toString();
	}

} //ProjectImpl
