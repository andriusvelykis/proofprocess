/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle.impl;

import isabelle.Term.Term;

import org.ai4fm.proofprocess.isabelle.IsaTerm;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Isa Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.impl.IsaTermImpl#getTerm <em>Term</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IsaTermImpl extends DisplayTermImpl implements IsaTerm {
	/**
	 * The default value of the '{@link #getTerm() <em>Term</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTerm()
	 * @generated
	 * @ordered
	 */
	protected static final Term TERM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTerm() <em>Term</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTerm()
	 * @generated
	 * @ordered
	 */
	protected Term term = TERM_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IsaTermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IsabelleProofProcessPackage.Literals.ISA_TERM;
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
	public void setTerm(Term newTerm) {
		Term oldTerm = term;
		term = newTerm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IsabelleProofProcessPackage.ISA_TERM__TERM, oldTerm, term));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IsabelleProofProcessPackage.ISA_TERM__TERM:
				return getTerm();
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
			case IsabelleProofProcessPackage.ISA_TERM__TERM:
				setTerm((Term)newValue);
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
			case IsabelleProofProcessPackage.ISA_TERM__TERM:
				setTerm(TERM_EDEFAULT);
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
			case IsabelleProofProcessPackage.ISA_TERM__TERM:
				return TERM_EDEFAULT == null ? term != null : !TERM_EDEFAULT.equals(term);
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
		result.append(" (term: ");
		result.append(term);
		result.append(')');
		return result.toString();
	}

} //IsaTermImpl
