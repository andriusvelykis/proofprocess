/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.util;

import org.ai4fm.proofprocess.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.ai4fm.proofprocess.ProofProcessPackage
 * @generated
 */
public class ProofProcessSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ProofProcessPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofProcessSwitch() {
		if (modelPackage == null) {
			modelPackage = ProofProcessPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ProofProcessPackage.ATTEMPT: {
				Attempt attempt = (Attempt)theEObject;
				T result = caseAttempt(attempt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.INTENT: {
				Intent intent = (Intent)theEObject;
				T result = caseIntent(intent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.PROOF_OBJECT: {
				ProofObject proofObject = (ProofObject)theEObject;
				T result = caseProofObject(proofObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.PROOF_PROPERTY: {
				ProofProperty proofProperty = (ProofProperty)theEObject;
				T result = caseProofProperty(proofProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.PROOF_REFERENCE: {
				ProofReference proofReference = (ProofReference)theEObject;
				T result = caseProofReference(proofReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.ATTEMPT_SET: {
				AttemptSet attemptSet = (AttemptSet)theEObject;
				T result = caseAttemptSet(attemptSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.ATTEMPT_GROUP: {
				AttemptGroup attemptGroup = (AttemptGroup)theEObject;
				T result = caseAttemptGroup(attemptGroup);
				if (result == null) result = caseAttempt(attemptGroup);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.ATTEMPT_ENTRY: {
				AttemptEntry attemptEntry = (AttemptEntry)theEObject;
				T result = caseAttemptEntry(attemptEntry);
				if (result == null) result = caseAttempt(attemptEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attempt</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attempt</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttempt(Attempt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Intent</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Intent</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntent(Intent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proof Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proof Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProofObject(ProofObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proof Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proof Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProofProperty(ProofProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proof Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proof Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProofReference(ProofReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attempt Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attempt Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttemptSet(AttemptSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attempt Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attempt Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttemptGroup(AttemptGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attempt Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attempt Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttemptEntry(AttemptEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ProofProcessSwitch
