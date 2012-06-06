/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle;

import org.ai4fm.proofprocess.Term;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inst Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.InstTerm#getTerm <em>Term</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.InstTerm#getInsts <em>Insts</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getInstTerm()
 * @model
 * @generated
 */
public interface InstTerm extends Term {
	/**
	 * Returns the value of the '<em><b>Term</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Term</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Term</em>' containment reference.
	 * @see #setTerm(Term)
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getInstTerm_Term()
	 * @model containment="true"
	 * @generated
	 */
	Term getTerm();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.isabelle.InstTerm#getTerm <em>Term</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Term</em>' containment reference.
	 * @see #getTerm()
	 * @generated
	 */
	void setTerm(Term value);

	/**
	 * Returns the value of the '<em><b>Insts</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.isabelle.Inst}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Insts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Insts</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getInstTerm_Insts()
	 * @model containment="true"
	 * @generated
	 */
	EList<Inst> getInsts();

} // InstTerm
