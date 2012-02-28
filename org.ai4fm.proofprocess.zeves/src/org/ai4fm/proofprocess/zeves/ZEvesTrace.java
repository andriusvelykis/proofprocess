/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves;

import org.ai4fm.proofprocess.Trace;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ZEves Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.zeves.ZEvesTrace#getMarkup <em>Markup</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.ZEvesTrace#getGoal <em>Goal</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.ZEvesTrace#getUsedLemmas <em>Used Lemmas</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.ZEvesTrace#getText <em>Text</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.ZEvesTrace#getCase <em>Case</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getZEvesTrace()
 * @model
 * @generated
 */
public interface ZEvesTrace extends Trace {
	/**
	 * Returns the value of the '<em><b>Markup</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Markup</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Markup</em>' attribute.
	 * @see #setMarkup(String)
	 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getZEvesTrace_Markup()
	 * @model
	 * @generated
	 */
	String getMarkup();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.zeves.ZEvesTrace#getMarkup <em>Markup</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Markup</em>' attribute.
	 * @see #getMarkup()
	 * @generated
	 */
	void setMarkup(String value);

	/**
	 * Returns the value of the '<em><b>Goal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Goal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Goal</em>' attribute.
	 * @see #setGoal(String)
	 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getZEvesTrace_Goal()
	 * @model
	 * @generated
	 */
	String getGoal();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.zeves.ZEvesTrace#getGoal <em>Goal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Goal</em>' attribute.
	 * @see #getGoal()
	 * @generated
	 */
	void setGoal(String value);

	/**
	 * Returns the value of the '<em><b>Used Lemmas</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Used Lemmas</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Used Lemmas</em>' attribute list.
	 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getZEvesTrace_UsedLemmas()
	 * @model
	 * @generated
	 */
	EList<String> getUsedLemmas();

	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getZEvesTrace_Text()
	 * @model required="true"
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.zeves.ZEvesTrace#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

	/**
	 * Returns the value of the '<em><b>Case</b></em>' attribute.
	 * The default value is <code>"\"\""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Case</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Case</em>' attribute.
	 * @see #setCase(String)
	 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getZEvesTrace_Case()
	 * @model default="\"\"" required="true"
	 * @generated
	 */
	String getCase();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.zeves.ZEvesTrace#getCase <em>Case</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Case</em>' attribute.
	 * @see #getCase()
	 * @generated
	 */
	void setCase(String value);

} // ZEvesTrace
