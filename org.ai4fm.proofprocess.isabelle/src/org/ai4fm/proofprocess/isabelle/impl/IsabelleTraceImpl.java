/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage;
import org.ai4fm.proofprocess.isabelle.IsabelleTrace;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

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
public class IsabelleTraceImpl extends EObjectImpl implements IsabelleTrace {
	/**
	 * The default value of the '{@link #getCommand() <em>Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommand()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMAND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCommand() <em>Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommand()
	 * @generated
	 * @ordered
	 */
	protected String command = COMMAND_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSimpLemmas() <em>Simp Lemmas</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimpLemmas()
	 * @generated
	 * @ordered
	 */
	protected EList<String> simpLemmas;

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
	public String getCommand() {
		return command;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommand(String newCommand) {
		String oldCommand = command;
		command = newCommand;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IsabelleProofProcessPackage.ISABELLE_TRACE__COMMAND, oldCommand, command));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getSimpLemmas() {
		if (simpLemmas == null) {
			simpLemmas = new EDataTypeUniqueEList<String>(String.class, this, IsabelleProofProcessPackage.ISABELLE_TRACE__SIMP_LEMMAS);
		}
		return simpLemmas;
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
				setCommand((String)newValue);
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
				setCommand(COMMAND_EDEFAULT);
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
				return COMMAND_EDEFAULT == null ? command != null : !COMMAND_EDEFAULT.equals(command);
			case IsabelleProofProcessPackage.ISABELLE_TRACE__SIMP_LEMMAS:
				return simpLemmas != null && !simpLemmas.isEmpty();
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
		result.append(" (command: ");
		result.append(command);
		result.append(", simpLemmas: ");
		result.append(simpLemmas);
		result.append(')');
		return result.toString();
	}

} //IsabelleTraceImpl
