/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attempt Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.AttemptSet#getAttempts <em>Attempts</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.AttemptSet#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getAttemptSet()
 * @model
 * @generated
 */
public interface AttemptSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Attempts</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.AttemptGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attempts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attempts</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getAttemptSet_Attempts()
	 * @model containment="true"
	 * @generated
	 */
	EList<AttemptGroup> getAttempts();

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getAttemptSet_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.AttemptSet#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

} // AttemptSet
