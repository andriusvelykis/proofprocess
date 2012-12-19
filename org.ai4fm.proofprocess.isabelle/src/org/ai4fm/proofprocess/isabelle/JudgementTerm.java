/**
 */
package org.ai4fm.proofprocess.isabelle;

import org.ai4fm.proofprocess.Term;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Judgement Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.JudgementTerm#getAssms <em>Assms</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.JudgementTerm#getGoal <em>Goal</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getJudgementTerm()
 * @model
 * @generated
 */
public interface JudgementTerm extends Term {
	/**
	 * Returns the value of the '<em><b>Assms</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assms</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assms</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getJudgementTerm_Assms()
	 * @model containment="true"
	 * @generated
	 */
	EList<Term> getAssms();

	/**
	 * Returns the value of the '<em><b>Goal</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Goal</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Goal</em>' containment reference.
	 * @see #setGoal(Term)
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getJudgementTerm_Goal()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Term getGoal();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.isabelle.JudgementTerm#getGoal <em>Goal</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Goal</em>' containment reference.
	 * @see #getGoal()
	 * @generated
	 */
	void setGoal(Term value);

} // JudgementTerm
