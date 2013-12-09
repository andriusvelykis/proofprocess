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
			case ProofProcessPackage.INTENT: {
				Intent intent = (Intent)theEObject;
				T result = caseIntent(intent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.TERM: {
				Term term = (Term)theEObject;
				T result = caseTerm(term);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.LOC: {
				Loc loc = (Loc)theEObject;
				T result = caseLoc(loc);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.TRACE: {
				Trace trace = (Trace)theEObject;
				T result = caseTrace(trace);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.PROOF_STEP: {
				ProofStep proofStep = (ProofStep)theEObject;
				T result = caseProofStep(proofStep);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.PROOF_INFO: {
				ProofInfo proofInfo = (ProofInfo)theEObject;
				T result = caseProofInfo(proofInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.PROOF_FEATURE_DEF: {
				ProofFeatureDef proofFeatureDef = (ProofFeatureDef)theEObject;
				T result = caseProofFeatureDef(proofFeatureDef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.PROOF_FEATURE: {
				ProofFeature proofFeature = (ProofFeature)theEObject;
				T result = caseProofFeature(proofFeature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.PROOF_ELEM: {
				ProofElem proofElem = (ProofElem)theEObject;
				T result = caseProofElem(proofElem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.PROOF_ENTRY: {
				ProofEntry proofEntry = (ProofEntry)theEObject;
				T result = caseProofEntry(proofEntry);
				if (result == null) result = caseProofElem(proofEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.PROOF_SEQ: {
				ProofSeq proofSeq = (ProofSeq)theEObject;
				T result = caseProofSeq(proofSeq);
				if (result == null) result = caseProofElem(proofSeq);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.PROOF_PARALLEL: {
				ProofParallel proofParallel = (ProofParallel)theEObject;
				T result = caseProofParallel(proofParallel);
				if (result == null) result = caseProofElem(proofParallel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.PROOF_ID: {
				ProofId proofId = (ProofId)theEObject;
				T result = caseProofId(proofId);
				if (result == null) result = caseProofElem(proofId);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.ATTEMPT: {
				Attempt attempt = (Attempt)theEObject;
				T result = caseAttempt(attempt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.PROOF: {
				Proof proof = (Proof)theEObject;
				T result = caseProof(proof);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ProofProcessPackage.PROOF_STORE: {
				ProofStore proofStore = (ProofStore)theEObject;
				T result = caseProofStore(proofStore);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
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
	 * Returns the result of interpreting the object as an instance of '<em>Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTerm(Term object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Loc</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Loc</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLoc(Loc object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Trace</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Trace</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrace(Trace object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proof Step</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proof Step</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProofStep(ProofStep object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proof Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proof Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProofInfo(ProofInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proof Feature Def</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proof Feature Def</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProofFeatureDef(ProofFeatureDef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proof Feature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proof Feature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProofFeature(ProofFeature object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proof Elem</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proof Elem</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProofElem(ProofElem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proof Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proof Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProofEntry(ProofEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proof Seq</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proof Seq</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProofSeq(ProofSeq object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proof Parallel</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proof Parallel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProofParallel(ProofParallel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proof Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proof Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProofId(ProofId object) {
		return null;
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
	 * Returns the result of interpreting the object as an instance of '<em>Proof</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proof</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProof(Proof object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Proof Store</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Proof Store</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProofStore(ProofStore object) {
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
