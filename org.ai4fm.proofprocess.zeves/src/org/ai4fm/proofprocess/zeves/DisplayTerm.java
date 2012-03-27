/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves;

import org.ai4fm.proofprocess.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Display Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.zeves.DisplayTerm#getDisplay <em>Display</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getDisplayTerm()
 * @model abstract="true"
 * @generated
 */
public interface DisplayTerm extends Term {
	/**
	 * Returns the value of the '<em><b>Display</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display</em>' attribute.
	 * @see #setDisplay(String)
	 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getDisplayTerm_Display()
	 * @model
	 * @generated
	 */
	String getDisplay();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.zeves.DisplayTerm#getDisplay <em>Display</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display</em>' attribute.
	 * @see #getDisplay()
	 * @generated
	 */
	void setDisplay(String value);

} // DisplayTerm
