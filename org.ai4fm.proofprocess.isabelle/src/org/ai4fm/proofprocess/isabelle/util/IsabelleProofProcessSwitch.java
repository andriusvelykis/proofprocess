/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle.util;

import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.Trace;

import org.ai4fm.proofprocess.isabelle.*;

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
 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage
 * @generated
 */
public class IsabelleProofProcessSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IsabelleProofProcessPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsabelleProofProcessSwitch() {
		if (modelPackage == null) {
			modelPackage = IsabelleProofProcessPackage.eINSTANCE;
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
			case IsabelleProofProcessPackage.DISPLAY_TERM: {
				DisplayTerm displayTerm = (DisplayTerm)theEObject;
				T result = caseDisplayTerm(displayTerm);
				if (result == null) result = caseTerm(displayTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IsabelleProofProcessPackage.MARKUP_TERM: {
				MarkupTerm markupTerm = (MarkupTerm)theEObject;
				T result = caseMarkupTerm(markupTerm);
				if (result == null) result = caseDisplayTerm(markupTerm);
				if (result == null) result = caseTerm(markupTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IsabelleProofProcessPackage.ISA_TERM: {
				IsaTerm isaTerm = (IsaTerm)theEObject;
				T result = caseIsaTerm(isaTerm);
				if (result == null) result = caseDisplayTerm(isaTerm);
				if (result == null) result = caseTerm(isaTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IsabelleProofProcessPackage.NAME_TERM: {
				NameTerm nameTerm = (NameTerm)theEObject;
				T result = caseNameTerm(nameTerm);
				if (result == null) result = caseTerm(nameTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IsabelleProofProcessPackage.NAMED_TERM: {
				NamedTerm namedTerm = (NamedTerm)theEObject;
				T result = caseNamedTerm(namedTerm);
				if (result == null) result = caseNameTerm(namedTerm);
				if (result == null) result = caseTerm(namedTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IsabelleProofProcessPackage.INST_TERM: {
				InstTerm instTerm = (InstTerm)theEObject;
				T result = caseInstTerm(instTerm);
				if (result == null) result = caseTerm(instTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IsabelleProofProcessPackage.INST: {
				Inst inst = (Inst)theEObject;
				T result = caseInst(inst);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IsabelleProofProcessPackage.ISABELLE_TRACE: {
				IsabelleTrace isabelleTrace = (IsabelleTrace)theEObject;
				T result = caseIsabelleTrace(isabelleTrace);
				if (result == null) result = caseTrace(isabelleTrace);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IsabelleProofProcessPackage.NAMED_TERM_TREE: {
				NamedTermTree namedTermTree = (NamedTermTree)theEObject;
				T result = caseNamedTermTree(namedTermTree);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IsabelleProofProcessPackage.ISABELLE_COMMAND: {
				IsabelleCommand isabelleCommand = (IsabelleCommand)theEObject;
				T result = caseIsabelleCommand(isabelleCommand);
				if (result == null) result = caseNamedTermTree(isabelleCommand);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Display Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Display Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDisplayTerm(DisplayTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Markup Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Markup Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMarkupTerm(MarkupTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Isa Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Isa Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIsaTerm(IsaTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Name Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Name Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNameTerm(NameTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedTerm(NamedTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inst Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inst Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInstTerm(InstTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inst</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inst</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInst(Inst object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Isabelle Trace</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Isabelle Trace</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIsabelleTrace(IsabelleTrace object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Term Tree</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Term Tree</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedTermTree(NamedTermTree object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Isabelle Command</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Isabelle Command</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIsabelleCommand(IsabelleCommand object) {
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

} //IsabelleProofProcessSwitch
