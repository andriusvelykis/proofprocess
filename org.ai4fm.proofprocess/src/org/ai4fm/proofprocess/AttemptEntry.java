/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attempt Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.AttemptEntry#getContent <em>Content</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.AttemptEntry#getInputs <em>Inputs</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.AttemptEntry#getOutputs <em>Outputs</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getAttemptEntry()
 * @model
 * @generated
 */
public interface AttemptEntry extends Attempt {
	/**
	 * Returns the value of the '<em><b>Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' containment reference.
	 * @see #setContent(ProofReference)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getAttemptEntry_Content()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ProofReference getContent();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.AttemptEntry#getContent <em>Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content</em>' containment reference.
	 * @see #getContent()
	 * @generated
	 */
	void setContent(ProofReference value);

	/**
	 * Returns the value of the '<em><b>Inputs</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.ProofObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inputs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inputs</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getAttemptEntry_Inputs()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<ProofObject> getInputs();

	/**
	 * Returns the value of the '<em><b>Outputs</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.ProofObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outputs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outputs</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getAttemptEntry_Outputs()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProofObject> getOutputs();

} // AttemptEntry
