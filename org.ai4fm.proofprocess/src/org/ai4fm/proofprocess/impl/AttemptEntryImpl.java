/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.Attempt;
import org.ai4fm.proofprocess.AttemptEntry;
import org.ai4fm.proofprocess.CompositionType;
import org.ai4fm.proofprocess.ProofObject;
import org.ai4fm.proofprocess.ProofProcessPackage;
import org.ai4fm.proofprocess.ProofReference;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attempt Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.AttemptEntryImpl#getContent <em>Content</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.AttemptEntryImpl#getInputs <em>Inputs</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.AttemptEntryImpl#getOutputs <em>Outputs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttemptEntryImpl extends AttemptImpl implements AttemptEntry {
	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected ProofReference content;

	/**
	 * The cached value of the '{@link #getInputs() <em>Inputs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<ProofObject> inputs;

	/**
	 * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputs()
	 * @generated
	 * @ordered
	 */
	protected EList<ProofObject> outputs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttemptEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessPackage.Literals.ATTEMPT_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofReference getContent() {
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContent(ProofReference newContent, NotificationChain msgs) {
		ProofReference oldContent = content;
		content = newContent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProofProcessPackage.ATTEMPT_ENTRY__CONTENT, oldContent, newContent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContent(ProofReference newContent) {
		if (newContent != content) {
			NotificationChain msgs = null;
			if (content != null)
				msgs = ((InternalEObject)content).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProofProcessPackage.ATTEMPT_ENTRY__CONTENT, null, msgs);
			if (newContent != null)
				msgs = ((InternalEObject)newContent).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProofProcessPackage.ATTEMPT_ENTRY__CONTENT, null, msgs);
			msgs = basicSetContent(newContent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProofProcessPackage.ATTEMPT_ENTRY__CONTENT, newContent, newContent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProofObject> getInputs() {
		if (inputs == null) {
			inputs = new EObjectContainmentEList<ProofObject>(ProofObject.class, this, ProofProcessPackage.ATTEMPT_ENTRY__INPUTS);
		}
		return inputs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProofObject> getOutputs() {
		if (outputs == null) {
			outputs = new EObjectContainmentEList<ProofObject>(ProofObject.class, this, ProofProcessPackage.ATTEMPT_ENTRY__OUTPUTS);
		}
		return outputs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProofProcessPackage.ATTEMPT_ENTRY__CONTENT:
				return basicSetContent(null, msgs);
			case ProofProcessPackage.ATTEMPT_ENTRY__INPUTS:
				return ((InternalEList<?>)getInputs()).basicRemove(otherEnd, msgs);
			case ProofProcessPackage.ATTEMPT_ENTRY__OUTPUTS:
				return ((InternalEList<?>)getOutputs()).basicRemove(otherEnd, msgs);
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
			case ProofProcessPackage.ATTEMPT_ENTRY__CONTENT:
				return getContent();
			case ProofProcessPackage.ATTEMPT_ENTRY__INPUTS:
				return getInputs();
			case ProofProcessPackage.ATTEMPT_ENTRY__OUTPUTS:
				return getOutputs();
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
			case ProofProcessPackage.ATTEMPT_ENTRY__CONTENT:
				setContent((ProofReference)newValue);
				return;
			case ProofProcessPackage.ATTEMPT_ENTRY__INPUTS:
				getInputs().clear();
				getInputs().addAll((Collection<? extends ProofObject>)newValue);
				return;
			case ProofProcessPackage.ATTEMPT_ENTRY__OUTPUTS:
				getOutputs().clear();
				getOutputs().addAll((Collection<? extends ProofObject>)newValue);
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
			case ProofProcessPackage.ATTEMPT_ENTRY__CONTENT:
				setContent((ProofReference)null);
				return;
			case ProofProcessPackage.ATTEMPT_ENTRY__INPUTS:
				getInputs().clear();
				return;
			case ProofProcessPackage.ATTEMPT_ENTRY__OUTPUTS:
				getOutputs().clear();
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
			case ProofProcessPackage.ATTEMPT_ENTRY__CONTENT:
				return content != null;
			case ProofProcessPackage.ATTEMPT_ENTRY__INPUTS:
				return inputs != null && !inputs.isEmpty();
			case ProofProcessPackage.ATTEMPT_ENTRY__OUTPUTS:
				return outputs != null && !outputs.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	
	/* (non-Javadoc)
	 * @see org.ai4fm.proofprocess.impl.AttemptImpl#getCompositionType()
	 */
	@Override
	public CompositionType getCompositionType() {
		return CompositionType.SEQUENTIAL;
	}

	/* (non-Javadoc)
	 * @see org.ai4fm.proofprocess.impl.AttemptImpl#getAttempts()
	 */
	@Override
	public EList<Attempt> getAttempts() {
		return ECollections.emptyEList();
	}

} //AttemptEntryImpl
