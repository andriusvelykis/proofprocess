/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle;

import org.ai4fm.proofprocess.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Name Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.NameTerm#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getNameTerm()
 * @model
 * @generated
 */
public interface NameTerm extends Term {

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getNameTerm_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.isabelle.NameTerm#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);
} // NameTerm
