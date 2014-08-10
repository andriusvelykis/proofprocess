/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle;

import isabelle.XML.Tree;
import org.ai4fm.proofprocess.DisplayTerm;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Markup Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.isabelle.MarkupTerm#getTerm <em>Term</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getMarkupTerm()
 * @model
 * @generated
 */
public interface MarkupTerm extends DisplayTerm {
	/**
	 * Returns the value of the '<em><b>Term</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Term</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Term</em>' attribute.
	 * @see #setTerm(Tree)
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getMarkupTerm_Term()
	 * @model dataType="org.ai4fm.proofprocess.isabelle.IsabelleXML" required="true"
	 *        annotation="http://www.eclipse.org/CDO/DBStore columnType='CLOB'"
	 * @generated
	 */
	Tree getTerm();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.isabelle.MarkupTerm#getTerm <em>Term</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Term</em>' attribute.
	 * @see #getTerm()
	 * @generated
	 */
	void setTerm(Tree value);

} // MarkupTerm
