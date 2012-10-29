/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.Term;

import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage;
import org.ai4fm.proofprocess.isabelle.NamedTermTree;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Named Term Tree</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.impl.NamedTermTreeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.impl.NamedTermTreeImpl#getTerms <em>Terms</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.impl.NamedTermTreeImpl#getBranches <em>Branches</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NamedTermTreeImpl extends CDOObjectImpl implements NamedTermTree {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NamedTermTreeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IsabelleProofProcessPackage.Literals.NAMED_TERM_TREE;
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
	public String getName() {
		return (String)eDynamicGet(IsabelleProofProcessPackage.NAMED_TERM_TREE__NAME, IsabelleProofProcessPackage.Literals.NAMED_TERM_TREE__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(IsabelleProofProcessPackage.NAMED_TERM_TREE__NAME, IsabelleProofProcessPackage.Literals.NAMED_TERM_TREE__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Term> getTerms() {
		return (EList<Term>)eDynamicGet(IsabelleProofProcessPackage.NAMED_TERM_TREE__TERMS, IsabelleProofProcessPackage.Literals.NAMED_TERM_TREE__TERMS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<NamedTermTree> getBranches() {
		return (EList<NamedTermTree>)eDynamicGet(IsabelleProofProcessPackage.NAMED_TERM_TREE__BRANCHES, IsabelleProofProcessPackage.Literals.NAMED_TERM_TREE__BRANCHES, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IsabelleProofProcessPackage.NAMED_TERM_TREE__TERMS:
				return ((InternalEList<?>)getTerms()).basicRemove(otherEnd, msgs);
			case IsabelleProofProcessPackage.NAMED_TERM_TREE__BRANCHES:
				return ((InternalEList<?>)getBranches()).basicRemove(otherEnd, msgs);
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
			case IsabelleProofProcessPackage.NAMED_TERM_TREE__NAME:
				return getName();
			case IsabelleProofProcessPackage.NAMED_TERM_TREE__TERMS:
				return getTerms();
			case IsabelleProofProcessPackage.NAMED_TERM_TREE__BRANCHES:
				return getBranches();
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
			case IsabelleProofProcessPackage.NAMED_TERM_TREE__NAME:
				setName((String)newValue);
				return;
			case IsabelleProofProcessPackage.NAMED_TERM_TREE__TERMS:
				getTerms().clear();
				getTerms().addAll((Collection<? extends Term>)newValue);
				return;
			case IsabelleProofProcessPackage.NAMED_TERM_TREE__BRANCHES:
				getBranches().clear();
				getBranches().addAll((Collection<? extends NamedTermTree>)newValue);
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
			case IsabelleProofProcessPackage.NAMED_TERM_TREE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case IsabelleProofProcessPackage.NAMED_TERM_TREE__TERMS:
				getTerms().clear();
				return;
			case IsabelleProofProcessPackage.NAMED_TERM_TREE__BRANCHES:
				getBranches().clear();
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
			case IsabelleProofProcessPackage.NAMED_TERM_TREE__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case IsabelleProofProcessPackage.NAMED_TERM_TREE__TERMS:
				return !getTerms().isEmpty();
			case IsabelleProofProcessPackage.NAMED_TERM_TREE__BRANCHES:
				return !getBranches().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //NamedTermTreeImpl
