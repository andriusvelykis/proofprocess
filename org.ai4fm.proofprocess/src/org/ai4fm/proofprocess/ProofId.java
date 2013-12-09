/**
 */
package org.ai4fm.proofprocess;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proof Id</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.ProofId#getEntryRef <em>Entry Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofId()
 * @model
 * @generated
 */
public interface ProofId extends ProofElem {
	/**
	 * Returns the value of the '<em><b>Entry Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry Ref</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry Ref</em>' reference.
	 * @see #setEntryRef(ProofEntry)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofId_EntryRef()
	 * @model required="true"
	 * @generated
	 */
	ProofEntry getEntryRef();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.ProofId#getEntryRef <em>Entry Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry Ref</em>' reference.
	 * @see #getEntryRef()
	 * @generated
	 */
	void setEntryRef(ProofEntry value);

} // ProofId
