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
 * A representation of the model object '<em><b>Proof Step</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.ProofStep#getInGoals <em>In Goals</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.ProofStep#getOutGoals <em>Out Goals</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.ProofStep#getSource <em>Source</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.ProofStep#getTrace <em>Trace</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofStep()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ProofStep extends CDOObject {
	/**
	 * Returns the value of the '<em><b>In Goals</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Goals</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Goals</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofStep_InGoals()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Term> getInGoals();

	/**
	 * Returns the value of the '<em><b>Out Goals</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Out Goals</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out Goals</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofStep_OutGoals()
	 * @model containment="true"
	 * @generated
	 */
	EList<Term> getOutGoals();

	/**
	 * Returns the value of the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' containment reference.
	 * @see #setSource(Loc)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofStep_Source()
	 * @model containment="true"
	 * @generated
	 */
	Loc getSource();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.ProofStep#getSource <em>Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' containment reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Loc value);

	/**
	 * Returns the value of the '<em><b>Trace</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trace</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trace</em>' containment reference.
	 * @see #setTrace(Trace)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofStep_Trace()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Trace getTrace();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.ProofStep#getTrace <em>Trace</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trace</em>' containment reference.
	 * @see #getTrace()
	 * @generated
	 */
	void setTrace(Trace value);

} // ProofStep
