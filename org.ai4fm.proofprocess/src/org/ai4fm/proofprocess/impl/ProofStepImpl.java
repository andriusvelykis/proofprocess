/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.Loc;
import org.ai4fm.proofprocess.ProofProcessPackage;
import org.ai4fm.proofprocess.ProofStep;
import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.Trace;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proof Step</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofStepImpl#getInGoals <em>In Goals</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofStepImpl#getOutGoals <em>Out Goals</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofStepImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofStepImpl#getTrace <em>Trace</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofStepImpl extends CDOObjectImpl implements ProofStep {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProofStepImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessPackage.Literals.PROOF_STEP;
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
	public EList<Term> getInGoals() {
		return (EList<Term>)eDynamicGet(ProofProcessPackage.PROOF_STEP__IN_GOALS, ProofProcessPackage.Literals.PROOF_STEP__IN_GOALS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public EList<Term> getOutGoals() {
		return (EList<Term>)eDynamicGet(ProofProcessPackage.PROOF_STEP__OUT_GOALS, ProofProcessPackage.Literals.PROOF_STEP__OUT_GOALS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Loc getSource() {
		return (Loc)eDynamicGet(ProofProcessPackage.PROOF_STEP__SOURCE, ProofProcessPackage.Literals.PROOF_STEP__SOURCE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(Loc newSource, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newSource, ProofProcessPackage.PROOF_STEP__SOURCE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSource(Loc newSource) {
		eDynamicSet(ProofProcessPackage.PROOF_STEP__SOURCE, ProofProcessPackage.Literals.PROOF_STEP__SOURCE, newSource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Trace getTrace() {
		return (Trace)eDynamicGet(ProofProcessPackage.PROOF_STEP__TRACE, ProofProcessPackage.Literals.PROOF_STEP__TRACE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrace(Trace newTrace, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newTrace, ProofProcessPackage.PROOF_STEP__TRACE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTrace(Trace newTrace) {
		eDynamicSet(ProofProcessPackage.PROOF_STEP__TRACE, ProofProcessPackage.Literals.PROOF_STEP__TRACE, newTrace);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProofProcessPackage.PROOF_STEP__IN_GOALS:
				return ((InternalEList<?>)getInGoals()).basicRemove(otherEnd, msgs);
			case ProofProcessPackage.PROOF_STEP__OUT_GOALS:
				return ((InternalEList<?>)getOutGoals()).basicRemove(otherEnd, msgs);
			case ProofProcessPackage.PROOF_STEP__SOURCE:
				return basicSetSource(null, msgs);
			case ProofProcessPackage.PROOF_STEP__TRACE:
				return basicSetTrace(null, msgs);
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
			case ProofProcessPackage.PROOF_STEP__IN_GOALS:
				return getInGoals();
			case ProofProcessPackage.PROOF_STEP__OUT_GOALS:
				return getOutGoals();
			case ProofProcessPackage.PROOF_STEP__SOURCE:
				return getSource();
			case ProofProcessPackage.PROOF_STEP__TRACE:
				return getTrace();
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
			case ProofProcessPackage.PROOF_STEP__IN_GOALS:
				getInGoals().clear();
				getInGoals().addAll((Collection<? extends Term>)newValue);
				return;
			case ProofProcessPackage.PROOF_STEP__OUT_GOALS:
				getOutGoals().clear();
				getOutGoals().addAll((Collection<? extends Term>)newValue);
				return;
			case ProofProcessPackage.PROOF_STEP__SOURCE:
				setSource((Loc)newValue);
				return;
			case ProofProcessPackage.PROOF_STEP__TRACE:
				setTrace((Trace)newValue);
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
			case ProofProcessPackage.PROOF_STEP__IN_GOALS:
				getInGoals().clear();
				return;
			case ProofProcessPackage.PROOF_STEP__OUT_GOALS:
				getOutGoals().clear();
				return;
			case ProofProcessPackage.PROOF_STEP__SOURCE:
				setSource((Loc)null);
				return;
			case ProofProcessPackage.PROOF_STEP__TRACE:
				setTrace((Trace)null);
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
			case ProofProcessPackage.PROOF_STEP__IN_GOALS:
				return !getInGoals().isEmpty();
			case ProofProcessPackage.PROOF_STEP__OUT_GOALS:
				return !getOutGoals().isEmpty();
			case ProofProcessPackage.PROOF_STEP__SOURCE:
				return getSource() != null;
			case ProofProcessPackage.PROOF_STEP__TRACE:
				return getTrace() != null;
		}
		return super.eIsSet(featureID);
	}

} //ProofStepImpl
