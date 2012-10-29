/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proof Store</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.ProofStore#getProofs <em>Proofs</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.ProofStore#getIntents <em>Intents</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofStore()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ProofStore extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Proofs</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.Proof}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proofs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proofs</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofStore_Proofs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Proof> getProofs();

	/**
	 * Returns the value of the '<em><b>Intents</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.Intent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Intents</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Intents</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofStore_Intents()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Intent> getIntents();

} // ProofStore
