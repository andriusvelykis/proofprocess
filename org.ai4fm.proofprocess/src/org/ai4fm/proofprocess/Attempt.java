/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attempt</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.Attempt#getProof <em>Proof</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getAttempt()
 * @model
 * @generated
 */
public interface Attempt extends EObject {
	/**
	 * Returns the value of the '<em><b>Proof</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proof</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proof</em>' containment reference.
	 * @see #setProof(ProofElem)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getAttempt_Proof()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ProofElem getProof();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.Attempt#getProof <em>Proof</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Proof</em>' containment reference.
	 * @see #getProof()
	 * @generated
	 */
	void setProof(ProofElem value);

} // Attempt
