/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle;

import org.ai4fm.proofprocess.Term;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Named Term Tree</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.NamedTermTree#getName <em>Name</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.NamedTermTree#getTerms <em>Terms</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.NamedTermTree#getBranches <em>Branches</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getNamedTermTree()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface NamedTermTree extends CDOObject {
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
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getNamedTermTree_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.isabelle.NamedTermTree#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Terms</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Terms</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Terms</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getNamedTermTree_Terms()
	 * @model containment="true"
	 * @generated
	 */
	EList<Term> getTerms();

	/**
	 * Returns the value of the '<em><b>Branches</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.isabelle.NamedTermTree}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Branches</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branches</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getNamedTermTree_Branches()
	 * @model containment="true"
	 * @generated
	 */
	EList<NamedTermTree> getBranches();

} // NamedTermTree
