/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proof Feature Def</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.ProofFeatureDef#getName <em>Name</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.ProofFeatureDef#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofFeatureDef()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ProofFeatureDef extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofFeatureDef_Name()
	 * @model default="" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.ProofFeatureDef#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * The default value is <code>"\"\""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofFeatureDef_Description()
	 * @model default="\"\"" required="true"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.ProofFeatureDef#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

} // ProofFeatureDef
