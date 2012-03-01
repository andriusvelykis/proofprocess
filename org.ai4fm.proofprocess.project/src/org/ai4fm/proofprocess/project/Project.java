/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.project;

import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.Proof;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.project.Project#getLabel <em>Label</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.project.Project#getProofs <em>Proofs</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.project.Project#getIntents <em>Intents</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.project.ProjectProofProcessPackage#getProject()
 * @model
 * @generated
 */
public interface Project extends EObject {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.ai4fm.proofprocess.project.ProjectProofProcessPackage#getProject_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.project.Project#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Proofs</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.Proof}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proofs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proofs</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.project.ProjectProofProcessPackage#getProject_Proofs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Proof> getProofs();

	/**
	 * Returns the value of the '<em><b>Intents</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.Intent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Intents</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Intents</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.project.ProjectProofProcessPackage#getProject_Intents()
	 * @model containment="true"
	 * @generated
	 */
	EList<Intent> getIntents();

} // Project
