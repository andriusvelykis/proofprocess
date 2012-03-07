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
 * A representation of the model object '<em><b>Composite Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.CompositeTerm#getTerms <em>Terms</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getCompositeTerm()
 * @model
 * @generated
 */
public interface CompositeTerm extends DisplayTerm {
	/**
	 * Returns the value of the '<em><b>Terms</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Terms</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Terms</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getCompositeTerm_Terms()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Term> getTerms();

} // CompositeTerm
