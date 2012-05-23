/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle;

import org.ai4fm.proofprocess.Trace;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Isabelle Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.IsabelleTrace#getCommand <em>Command</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.IsabelleTrace#getSimpLemmas <em>Simp Lemmas</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getIsabelleTrace()
 * @model
 * @generated
 */
public interface IsabelleTrace extends Trace {
	/**
	 * Returns the value of the '<em><b>Command</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Command</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Command</em>' containment reference.
	 * @see #setCommand(IsabelleCommand)
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getIsabelleTrace_Command()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IsabelleCommand getCommand();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.isabelle.IsabelleTrace#getCommand <em>Command</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Command</em>' containment reference.
	 * @see #getCommand()
	 * @generated
	 */
	void setCommand(IsabelleCommand value);

	/**
	 * Returns the value of the '<em><b>Simp Lemmas</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Simp Lemmas</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Simp Lemmas</em>' attribute list.
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getIsabelleTrace_SimpLemmas()
	 * @model
	 * @generated
	 */
	EList<String> getSimpLemmas();

} // IsabelleTrace
