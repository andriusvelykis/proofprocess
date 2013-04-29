/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.ProofElem;
import org.ai4fm.proofprocess.ProofEntry;
import org.ai4fm.proofprocess.ProofParallel;
import org.ai4fm.proofprocess.ProofProcessPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proof Parallel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofParallelImpl#getEntries <em>Entries</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.impl.ProofParallelImpl#getLinks <em>Links</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProofParallelImpl extends ProofElemImpl implements ProofParallel {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProofParallelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProofProcessPackage.Literals.PROOF_PARALLEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public EList<ProofElem> getEntries() {
		return (EList<ProofElem>)eDynamicGet(ProofProcessPackage.PROOF_PARALLEL__ENTRIES, ProofProcessPackage.Literals.PROOF_PARALLEL__ENTRIES, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ProofEntry> getLinks() {
		return (EList<ProofEntry>)eDynamicGet(ProofProcessPackage.PROOF_PARALLEL__LINKS, ProofProcessPackage.Literals.PROOF_PARALLEL__LINKS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProofProcessPackage.PROOF_PARALLEL__ENTRIES:
				return ((InternalEList<?>)getEntries()).basicRemove(otherEnd, msgs);
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
			case ProofProcessPackage.PROOF_PARALLEL__ENTRIES:
				return getEntries();
			case ProofProcessPackage.PROOF_PARALLEL__LINKS:
				return getLinks();
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
			case ProofProcessPackage.PROOF_PARALLEL__ENTRIES:
				getEntries().clear();
				getEntries().addAll((Collection<? extends ProofElem>)newValue);
				return;
			case ProofProcessPackage.PROOF_PARALLEL__LINKS:
				getLinks().clear();
				getLinks().addAll((Collection<? extends ProofEntry>)newValue);
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
			case ProofProcessPackage.PROOF_PARALLEL__ENTRIES:
				getEntries().clear();
				return;
			case ProofProcessPackage.PROOF_PARALLEL__LINKS:
				getLinks().clear();
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
			case ProofProcessPackage.PROOF_PARALLEL__ENTRIES:
				return !getEntries().isEmpty();
			case ProofProcessPackage.PROOF_PARALLEL__LINKS:
				return !getLinks().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProofParallelImpl
