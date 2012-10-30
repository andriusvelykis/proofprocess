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

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

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
public class InstTermImpl extends CDOObjectImpl implements InstTerm {
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
	public Term getTerm() {
		return (Term)eDynamicGet(IsabelleProofProcessPackage.INST_TERM__TERM, IsabelleProofProcessPackage.Literals.INST_TERM__TERM, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTerm(Term newTerm, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newTerm, IsabelleProofProcessPackage.INST_TERM__TERM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTerm(Term newTerm) {
		eDynamicSet(IsabelleProofProcessPackage.INST_TERM__TERM, IsabelleProofProcessPackage.Literals.INST_TERM__TERM, newTerm);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public EList<Inst> getInsts() {
		return (EList<Inst>)eDynamicGet(IsabelleProofProcessPackage.INST_TERM__INSTS, IsabelleProofProcessPackage.Literals.INST_TERM__INSTS, true, true);
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
				return getTerm() != null;
			case IsabelleProofProcessPackage.INST_TERM__INSTS:
				return !getInsts().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //InstTermImpl
