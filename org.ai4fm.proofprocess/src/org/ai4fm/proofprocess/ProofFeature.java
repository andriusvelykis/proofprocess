/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proof Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.ProofFeature#getName <em>Name</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.ProofFeature#getType <em>Type</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.ProofFeature#getParams <em>Params</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofFeature()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ProofFeature extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' reference.
	 * @see #setName(ProofFeatureDef)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofFeature_Name()
	 * @model required="true"
	 * @generated
	 */
	ProofFeatureDef getName();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.ProofFeature#getName <em>Name</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' reference.
	 * @see #getName()
	 * @generated
	 */
	void setName(ProofFeatureDef value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.ai4fm.proofprocess.ProofFeatureType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.ai4fm.proofprocess.ProofFeatureType
	 * @see #setType(ProofFeatureType)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofFeature_Type()
	 * @model
	 * @generated
	 */
	ProofFeatureType getType();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.ProofFeature#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.ai4fm.proofprocess.ProofFeatureType
	 * @see #getType()
	 * @generated
	 */
	void setType(ProofFeatureType value);

	/**
	 * Returns the value of the '<em><b>Params</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Params</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Params</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofFeature_Params()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Term> getParams();

} // ProofFeature
