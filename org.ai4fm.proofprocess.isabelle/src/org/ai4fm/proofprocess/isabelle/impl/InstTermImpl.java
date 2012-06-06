/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.isabelle.Inst;
import org.ai4fm.proofprocess.isabelle.InstTerm;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage;
import org.ai4fm.proofprocess.isabelle.NameTerm;

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
 * An implementation of the model object '<em><b>Inst Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.impl.InstTermImpl#getTerm <em>Term</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.impl.InstTermImpl#getInsts <em>Insts</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InstTermImpl extends EObjectImpl implements InstTerm {
	/**
	 * The cached value of the '{@link #getTerm() <em>Term</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTerm()
	 * @generated
	 * @ordered
	 */
	protected Term term;

	/**
	 * The cached value of the '{@link #getInsts() <em>Insts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsts()
	 * @generated
	 * @ordered
	 */
	protected EList<Inst> insts;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstTermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IsabelleProofProcessPackage.Literals.INST_TERM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Term getTerm() {
		return term;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTerm(Term newTerm, NotificationChain msgs) {
		Term oldTerm = term;
		term = newTerm;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IsabelleProofProcessPackage.INST_TERM__TERM, oldTerm, newTerm);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTerm(Term newTerm) {
		if (newTerm != term) {
			NotificationChain msgs = null;
			if (term != null)
				msgs = ((InternalEObject)term).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IsabelleProofProcessPackage.INST_TERM__TERM, null, msgs);
			if (newTerm != null)
				msgs = ((InternalEObject)newTerm).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IsabelleProofProcessPackage.INST_TERM__TERM, null, msgs);
			msgs = basicSetTerm(newTerm, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IsabelleProofProcessPackage.INST_TERM__TERM, newTerm, newTerm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Inst> getInsts() {
		if (insts == null) {
			insts = new EObjectContainmentEList<Inst>(Inst.class, this, IsabelleProofProcessPackage.INST_TERM__INSTS);
		}
		return insts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IsabelleProofProcessPackage.INST_TERM__TERM:
				return basicSetTerm(null, msgs);
			case IsabelleProofProcessPackage.INST_TERM__INSTS:
				return ((InternalEList<?>)getInsts()).basicRemove(otherEnd, msgs);
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
			case IsabelleProofProcessPackage.INST_TERM__TERM:
				return getTerm();
			case IsabelleProofProcessPackage.INST_TERM__INSTS:
				return getInsts();
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
			case IsabelleProofProcessPackage.INST_TERM__TERM:
				setTerm((Term)newValue);
				return;
			case IsabelleProofProcessPackage.INST_TERM__INSTS:
				getInsts().clear();
				getInsts().addAll((Collection<? extends Inst>)newValue);
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
			case IsabelleProofProcessPackage.INST_TERM__TERM:
				setTerm((Term)null);
				return;
			case IsabelleProofProcessPackage.INST_TERM__INSTS:
				getInsts().clear();
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
			case IsabelleProofProcessPackage.INST_TERM__TERM:
				return term != null;
			case IsabelleProofProcessPackage.INST_TERM__INSTS:
				return insts != null && !insts.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //InstTermImpl
