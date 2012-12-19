/**
 */
package org.ai4fm.proofprocess.isabelle.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.Term;

import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage;
import org.ai4fm.proofprocess.isabelle.JudgementTerm;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Judgement Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.impl.JudgementTermImpl#getAssms <em>Assms</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.impl.JudgementTermImpl#getGoal <em>Goal</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JudgementTermImpl extends CDOObjectImpl implements JudgementTerm {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JudgementTermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM;
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
	@SuppressWarnings("unchecked")
	public EList<Term> getAssms() {
		return (EList<Term>)eDynamicGet(IsabelleProofProcessPackage.JUDGEMENT_TERM__ASSMS, IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__ASSMS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Term getGoal() {
		return (Term)eDynamicGet(IsabelleProofProcessPackage.JUDGEMENT_TERM__GOAL, IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__GOAL, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGoal(Term newGoal, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newGoal, IsabelleProofProcessPackage.JUDGEMENT_TERM__GOAL, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGoal(Term newGoal) {
		eDynamicSet(IsabelleProofProcessPackage.JUDGEMENT_TERM__GOAL, IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__GOAL, newGoal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IsabelleProofProcessPackage.JUDGEMENT_TERM__ASSMS:
				return ((InternalEList<?>)getAssms()).basicRemove(otherEnd, msgs);
			case IsabelleProofProcessPackage.JUDGEMENT_TERM__GOAL:
				return basicSetGoal(null, msgs);
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
			case IsabelleProofProcessPackage.JUDGEMENT_TERM__ASSMS:
				return getAssms();
			case IsabelleProofProcessPackage.JUDGEMENT_TERM__GOAL:
				return getGoal();
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
			case IsabelleProofProcessPackage.JUDGEMENT_TERM__ASSMS:
				getAssms().clear();
				getAssms().addAll((Collection<? extends Term>)newValue);
				return;
			case IsabelleProofProcessPackage.JUDGEMENT_TERM__GOAL:
				setGoal((Term)newValue);
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
			case IsabelleProofProcessPackage.JUDGEMENT_TERM__ASSMS:
				getAssms().clear();
				return;
			case IsabelleProofProcessPackage.JUDGEMENT_TERM__GOAL:
				setGoal((Term)null);
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
			case IsabelleProofProcessPackage.JUDGEMENT_TERM__ASSMS:
				return !getAssms().isEmpty();
			case IsabelleProofProcessPackage.JUDGEMENT_TERM__GOAL:
				return getGoal() != null;
		}
		return super.eIsSet(featureID);
	}

} //JudgementTermImpl
