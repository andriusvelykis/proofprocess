/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.Property#getName <em>Name</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.Property#getType <em>Type</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.Property#getParams <em>Params</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProperty()
 * @model
 * @generated
 */
public interface Property extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' reference.
	 * @see #setName(PropertyDef)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProperty_Name()
	 * @model required="true"
	 * @generated
	 */
	PropertyDef getName();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.Property#getName <em>Name</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' reference.
	 * @see #getName()
	 * @generated
	 */
	void setName(PropertyDef value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.ai4fm.proofprocess.PropertyType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.ai4fm.proofprocess.PropertyType
	 * @see #setType(PropertyType)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProperty_Type()
	 * @model
	 * @generated
	 */
	PropertyType getType();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.Property#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.ai4fm.proofprocess.PropertyType
	 * @see #getType()
	 * @generated
	 */
	void setType(PropertyType value);

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
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProperty_Params()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Term> getParams();

} // Property
