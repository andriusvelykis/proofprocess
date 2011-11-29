/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attempt Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.AttemptGroup#getCompositionType <em>Composition Type</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.AttemptGroup#getAttempts <em>Attempts</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.AttemptGroup#getContained <em>Contained</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getAttemptGroup()
 * @model
 * @generated
 */
public interface AttemptGroup extends Attempt {
	/**
	 * Returns the value of the '<em><b>Composition Type</b></em>' attribute.
	 * The default value is <code>"SEQUENTIAL"</code>.
	 * The literals are from the enumeration {@link org.ai4fm.proofprocess.CompositionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composition Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composition Type</em>' attribute.
	 * @see org.ai4fm.proofprocess.CompositionType
	 * @see #setCompositionType(CompositionType)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getAttemptGroup_CompositionType()
	 * @model default="SEQUENTIAL" required="true"
	 * @generated
	 */
	CompositionType getCompositionType();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.AttemptGroup#getCompositionType <em>Composition Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composition Type</em>' attribute.
	 * @see org.ai4fm.proofprocess.CompositionType
	 * @see #getCompositionType()
	 * @generated
	 */
	void setCompositionType(CompositionType value);

	/**
	 * Returns the value of the '<em><b>Attempts</b></em>' reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.Attempt}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attempts</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attempts</em>' reference list.
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getAttemptGroup_Attempts()
	 * @model
	 * @generated
	 */
	EList<Attempt> getAttempts();

	/**
	 * Returns the value of the '<em><b>Contained</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.Attempt}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contained</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contained</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getAttemptGroup_Contained()
	 * @model containment="true"
	 * @generated
	 */
	EList<Attempt> getContained();

} // AttemptGroup
