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
 * A representation of the model object '<em><b>Proof Elem</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.ProofElem#getInfo <em>Info</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofElem()
 * @model abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface ProofElem extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Info</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Info</em>' containment reference.
	 * @see #setInfo(ProofInfo)
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#getProofElem_Info()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ProofInfo getInfo();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.ProofElem#getInfo <em>Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Info</em>' containment reference.
	 * @see #getInfo()
	 * @generated
	 */
	void setInfo(ProofInfo value);

} // ProofElem
