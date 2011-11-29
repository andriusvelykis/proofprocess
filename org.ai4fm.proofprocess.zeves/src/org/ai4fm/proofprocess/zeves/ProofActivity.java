/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves;

import org.ai4fm.proofprocess.Attempt;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proof Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.zeves.ProofActivity#getAttempt <em>Attempt</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getProofActivity()
 * @model
 * @generated
 */
public interface ProofActivity extends Activity {
	/**
	 * Returns the value of the '<em><b>Attempt</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attempt</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attempt</em>' reference.
	 * @see #setAttempt(Attempt)
	 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getProofActivity_Attempt()
	 * @model
	 * @generated
	 */
	Attempt getAttempt();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.zeves.ProofActivity#getAttempt <em>Attempt</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attempt</em>' reference.
	 * @see #getAttempt()
	 * @generated
	 */
	void setAttempt(Attempt value);

} // ProofActivity
