/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Isa Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.IsaTerm#getKind <em>Kind</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.IsaTerm#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getIsaTerm()
 * @model
 * @generated
 */
public interface IsaTerm extends DisplayTerm {
	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.ai4fm.proofprocess.isabelle.TermKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.ai4fm.proofprocess.isabelle.TermKind
	 * @see #setKind(TermKind)
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getIsaTerm_Kind()
	 * @model required="true"
	 * @generated
	 */
	TermKind getKind();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.isabelle.IsaTerm#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see org.ai4fm.proofprocess.isabelle.TermKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(TermKind value);

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
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getIsaTerm_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.isabelle.IsaTerm#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // IsaTerm
