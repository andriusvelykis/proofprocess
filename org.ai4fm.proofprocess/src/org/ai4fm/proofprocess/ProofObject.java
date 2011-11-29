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
 * A representation of the model object '<em><b>Proof Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.ProofObject#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.ProofObject#getContent <em>Content</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofObject()
 * @model
 * @generated
 */
public interface ProofObject extends EObject {
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.ProofProperty}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' reference list.
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofObject_Properties()
	 * @model
	 * @generated
	 */
	EList<ProofProperty> getProperties();

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
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofObject_Content()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ProofReference getContent();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.ProofObject#getContent <em>Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content</em>' containment reference.
	 * @see #getContent()
	 * @generated
	 */
	void setContent(ProofReference value);

} // ProofObject
