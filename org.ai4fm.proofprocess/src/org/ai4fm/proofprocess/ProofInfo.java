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
 * A representation of the model object '<em><b>Proof Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.ProofInfo#getIntent <em>Intent</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.ProofInfo#getNarrative <em>Narrative</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.ProofInfo#getInProps <em>In Props</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.ProofInfo#getOutProps <em>Out Props</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofInfo()
 * @model
 * @generated
 */
public interface ProofInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Intent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Intent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Intent</em>' reference.
	 * @see #setIntent(Intent)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofInfo_Intent()
	 * @model
	 * @generated
	 */
	Intent getIntent();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.ProofInfo#getIntent <em>Intent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Intent</em>' reference.
	 * @see #getIntent()
	 * @generated
	 */
	void setIntent(Intent value);

	/**
	 * Returns the value of the '<em><b>Narrative</b></em>' attribute.
	 * The default value is <code>"\"\""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Narrative</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Narrative</em>' attribute.
	 * @see #setNarrative(String)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofInfo_Narrative()
	 * @model default="\"\""
	 * @generated
	 */
	String getNarrative();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.ProofInfo#getNarrative <em>Narrative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Narrative</em>' attribute.
	 * @see #getNarrative()
	 * @generated
	 */
	void setNarrative(String value);

	/**
	 * Returns the value of the '<em><b>In Props</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Props</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Props</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofInfo_InProps()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getInProps();

	/**
	 * Returns the value of the '<em><b>Out Props</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Out Props</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out Props</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofInfo_OutProps()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getOutProps();

} // ProofInfo
