/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves;

import net.sourceforge.czt.base.ast.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Zml Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.zeves.ZmlTerm#getTerm <em>Term</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.ZmlTerm#getZmlVersion <em>Zml Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getZmlTerm()
 * @model
 * @generated
 */
public interface ZmlTerm extends DisplayTerm {
	/**
	 * Returns the value of the '<em><b>Term</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Term</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Term</em>' attribute.
	 * @see #setTerm(Term)
	 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getZmlTerm_Term()
	 * @model dataType="org.ai4fm.proofprocess.zeves.CztTerm" required="true"
	 * @generated
	 */
	Term getTerm();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.zeves.ZmlTerm#getTerm <em>Term</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Term</em>' attribute.
	 * @see #getTerm()
	 * @generated
	 */
	void setTerm(Term value);

	/**
	 * Returns the value of the '<em><b>Zml Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Zml Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Zml Version</em>' attribute.
	 * @see #setZmlVersion(String)
	 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getZmlTerm_ZmlVersion()
	 * @model required="true"
	 * @generated
	 */
	String getZmlVersion();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.zeves.ZmlTerm#getZmlVersion <em>Zml Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Zml Version</em>' attribute.
	 * @see #getZmlVersion()
	 * @generated
	 */
	void setZmlVersion(String value);

} // ZmlTerm
