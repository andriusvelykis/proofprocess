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
public class ProofStepImpl extends EObjectImpl implements ProofStep {
	/**
	 * The cached value of the '{@link #getInGoals() <em>In Goals</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInGoals()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> inGoals;

	/**
	 * The cached value of the '{@link #getOutGoals() <em>Out Goals</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutGoals()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> outGoals;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected Loc source;

	/**
	 * The cached value of the '{@link #getTrace() <em>Trace</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrace()
	 * @generated
	 * @ordered
	 */
	protected Trace trace;

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
	public EList<Term> getInGoals() {
		if (inGoals == null) {
			inGoals = new EObjectContainmentEList<Term>(Term.class, this, ProofProcessPackage.PROOF_STEP__IN_GOALS);
		}
		return inGoals;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Term> getOutGoals() {
		if (outGoals == null) {
			outGoals = new EObjectContainmentEList<Term>(Term.class, this, ProofProcessPackage.PROOF_STEP__OUT_GOALS);
		}
		return outGoals;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Loc getSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(Loc newSource, NotificationChain msgs) {
		Loc oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProofProcessPackage.PROOF_STEP__SOURCE, oldSource, newSource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(Loc newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject)source).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProofProcessPackage.PROOF_STEP__SOURCE, null, msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProofProcessPackage.PROOF_STEP__SOURCE, null, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProofProcessPackage.PROOF_STEP__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Trace getTrace() {
		return trace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrace(Trace newTrace, NotificationChain msgs) {
		Trace oldTrace = trace;
		trace = newTrace;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProofProcessPackage.PROOF_STEP__TRACE, oldTrace, newTrace);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrace(Trace newTrace) {
		if (newTrace != trace) {
			NotificationChain msgs = null;
			if (trace != null)
				msgs = ((InternalEObject)trace).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProofProcessPackage.PROOF_STEP__TRACE, null, msgs);
			if (newTrace != null)
				msgs = ((InternalEObject)newTrace).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProofProcessPackage.PROOF_STEP__TRACE, null, msgs);
			msgs = basicSetTrace(newTrace, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProofProcessPackage.PROOF_STEP__TRACE, newTrace, newTrace));
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
				return inGoals != null && !inGoals.isEmpty();
			case ProofProcessPackage.PROOF_STEP__OUT_GOALS:
				return outGoals != null && !outGoals.isEmpty();
			case ProofProcessPackage.PROOF_STEP__SOURCE:
				return source != null;
			case ProofProcessPackage.PROOF_STEP__TRACE:
				return trace != null;
		}
		return super.eIsSet(featureID);
	}

} //ProofStepImpl
