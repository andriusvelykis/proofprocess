/**
 */
package org.ai4fm.proofprocess.isabelle;

import org.ai4fm.proofprocess.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assumption Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.AssumptionTerm#getTerm <em>Term</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getAssumptionTerm()
 * @model
 * @generated
 */
public interface AssumptionTerm extends Term {
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
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getAssumptionTerm_Term()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Term getTerm();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.isabelle.AssumptionTerm#getTerm <em>Term</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Term</em>' containment reference.
	 * @see #getTerm()
	 * @generated
	 */
	void setTerm(Term value);

} // AssumptionTerm
