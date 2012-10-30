/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.isabelle.IsabelleCommand;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage;
import org.ai4fm.proofprocess.isabelle.IsabelleTrace;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Isabelle Trace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.impl.IsabelleTraceImpl#getCommand <em>Command</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.impl.IsabelleTraceImpl#getSimpLemmas <em>Simp Lemmas</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IsabelleTraceImpl extends CDOObjectImpl implements IsabelleTrace {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IsabelleTraceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IsabelleProofProcessPackage.Literals.ISABELLE_TRACE;
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
	public IsabelleCommand getCommand() {
		return (IsabelleCommand)eDynamicGet(IsabelleProofProcessPackage.ISABELLE_TRACE__COMMAND, IsabelleProofProcessPackage.Literals.ISABELLE_TRACE__COMMAND, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCommand(IsabelleCommand newCommand, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newCommand, IsabelleProofProcessPackage.ISABELLE_TRACE__COMMAND, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCommand(IsabelleCommand newCommand) {
		eDynamicSet(IsabelleProofProcessPackage.ISABELLE_TRACE__COMMAND, IsabelleProofProcessPackage.Literals.ISABELLE_TRACE__COMMAND, newCommand);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public EList<String> getSimpLemmas() {
		return (EList<String>)eDynamicGet(IsabelleProofProcessPackage.ISABELLE_TRACE__SIMP_LEMMAS, IsabelleProofProcessPackage.Literals.ISABELLE_TRACE__SIMP_LEMMAS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IsabelleProofProcessPackage.ISABELLE_TRACE__COMMAND:
				return basicSetCommand(null, msgs);
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
			case IsabelleProofProcessPackage.ISABELLE_TRACE__COMMAND:
				return getCommand();
			case IsabelleProofProcessPackage.ISABELLE_TRACE__SIMP_LEMMAS:
				return getSimpLemmas();
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
			case IsabelleProofProcessPackage.ISABELLE_TRACE__COMMAND:
				setCommand((IsabelleCommand)newValue);
				return;
			case IsabelleProofProcessPackage.ISABELLE_TRACE__SIMP_LEMMAS:
				getSimpLemmas().clear();
				getSimpLemmas().addAll((Collection<? extends String>)newValue);
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
			case IsabelleProofProcessPackage.ISABELLE_TRACE__COMMAND:
				setCommand((IsabelleCommand)null);
				return;
			case IsabelleProofProcessPackage.ISABELLE_TRACE__SIMP_LEMMAS:
				getSimpLemmas().clear();
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
			case IsabelleProofProcessPackage.ISABELLE_TRACE__COMMAND:
				return getCommand() != null;
			case IsabelleProofProcessPackage.ISABELLE_TRACE__SIMP_LEMMAS:
				return !getSimpLemmas().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IsabelleTraceImpl
