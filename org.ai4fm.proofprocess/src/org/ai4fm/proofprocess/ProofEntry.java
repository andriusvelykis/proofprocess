/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proof Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.ProofEntry#getProofStep <em>Proof Step</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofEntry()
 * @model
 * @generated
 */
public interface ProofEntry extends ProofElem {
	/**
	 * Returns the value of the '<em><b>Proof Step</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proof Step</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proof Step</em>' containment reference.
	 * @see #setProofStep(ProofStep)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofEntry_ProofStep()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ProofStep getProofStep();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.ProofEntry#getProofStep <em>Proof Step</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Proof Step</em>' containment reference.
	 * @see #getProofStep()
	 * @generated
	 */
	void setProofStep(ProofStep value);

} // ProofEntry
