/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle.impl;

import org.ai4fm.proofprocess.Term;

import org.ai4fm.proofprocess.isabelle.Inst;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Inst</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.impl.InstImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.impl.InstImpl#getIndex <em>Index</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.impl.InstImpl#getTerm <em>Term</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InstImpl extends CDOObjectImpl implements Inst {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int INDEX_EDEFAULT = 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IsabelleProofProcessPackage.Literals.INST;
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
	public String getName() {
		return (String)eDynamicGet(IsabelleProofProcessPackage.INST__NAME, IsabelleProofProcessPackage.Literals.INST__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		eDynamicSet(IsabelleProofProcessPackage.INST__NAME, IsabelleProofProcessPackage.Literals.INST__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getIndex() {
		return (Integer)eDynamicGet(IsabelleProofProcessPackage.INST__INDEX, IsabelleProofProcessPackage.Literals.INST__INDEX, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIndex(int newIndex) {
		eDynamicSet(IsabelleProofProcessPackage.INST__INDEX, IsabelleProofProcessPackage.Literals.INST__INDEX, newIndex);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Term getTerm() {
		return (Term)eDynamicGet(IsabelleProofProcessPackage.INST__TERM, IsabelleProofProcessPackage.Literals.INST__TERM, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTerm(Term newTerm, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newTerm, IsabelleProofProcessPackage.INST__TERM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTerm(Term newTerm) {
		eDynamicSet(IsabelleProofProcessPackage.INST__TERM, IsabelleProofProcessPackage.Literals.INST__TERM, newTerm);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IsabelleProofProcessPackage.INST__TERM:
				return basicSetTerm(null, msgs);
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
			case IsabelleProofProcessPackage.INST__NAME:
				return getName();
			case IsabelleProofProcessPackage.INST__INDEX:
				return getIndex();
			case IsabelleProofProcessPackage.INST__TERM:
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
			case IsabelleProofProcessPackage.INST__NAME:
				setName((String)newValue);
				return;
			case IsabelleProofProcessPackage.INST__INDEX:
				setIndex((Integer)newValue);
				return;
			case IsabelleProofProcessPackage.INST__TERM:
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
			case IsabelleProofProcessPackage.INST__NAME:
				setName(NAME_EDEFAULT);
				return;
			case IsabelleProofProcessPackage.INST__INDEX:
				setIndex(INDEX_EDEFAULT);
				return;
			case IsabelleProofProcessPackage.INST__TERM:
				setTerm((Term)null);
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
			case IsabelleProofProcessPackage.INST__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case IsabelleProofProcessPackage.INST__INDEX:
				return getIndex() != INDEX_EDEFAULT;
			case IsabelleProofProcessPackage.INST__TERM:
				return getTerm() != null;
		}
		return super.eIsSet(featureID);
	}

} //InstImpl
