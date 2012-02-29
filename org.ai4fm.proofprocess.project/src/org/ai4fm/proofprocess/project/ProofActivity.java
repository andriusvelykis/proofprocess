/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.project;

import org.ai4fm.proofprocess.ProofEntry;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proof Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.project.ProofActivity#getProofRef <em>Proof Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.project.ProjectProofProcessPackage#getProofActivity()
 * @model
 * @generated
 */
public interface ProofActivity extends Activity {
	/**
	 * Returns the value of the '<em><b>Proof Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proof Ref</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proof Ref</em>' reference.
	 * @see #setProofRef(ProofEntry)
	 * @see org.ai4fm.proofprocess.project.ProjectProofProcessPackage#getProofActivity_ProofRef()
	 * @model
	 * @generated
	 */
	ProofEntry getProofRef();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.project.ProofActivity#getProofRef <em>Proof Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Proof Ref</em>' reference.
	 * @see #getProofRef()
	 * @generated
	 */
	void setProofRef(ProofEntry value);

} // ProofActivity
