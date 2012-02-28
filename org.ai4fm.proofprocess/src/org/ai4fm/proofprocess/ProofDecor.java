/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proof Decor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.ProofDecor#getEntry <em>Entry</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofDecor()
 * @model
 * @generated
 */
public interface ProofDecor extends ProofElem {
	/**
	 * Returns the value of the '<em><b>Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry</em>' containment reference.
	 * @see #setEntry(ProofElem)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofDecor_Entry()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ProofElem getEntry();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.ProofDecor#getEntry <em>Entry</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry</em>' containment reference.
	 * @see #getEntry()
	 * @generated
	 */
	void setEntry(ProofElem value);

} // ProofDecor
