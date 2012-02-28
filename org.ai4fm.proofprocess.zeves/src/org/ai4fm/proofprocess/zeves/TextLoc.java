/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves;

import org.ai4fm.proofprocess.Loc;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Text Loc</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.zeves.TextLoc#getFilePath <em>File Path</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.TextLoc#getPosition <em>Position</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getTextLoc()
 * @model
 * @generated
 */
public interface TextLoc extends Loc {
	/**
	 * Returns the value of the '<em><b>File Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Path</em>' attribute.
	 * @see #setFilePath(String)
	 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getTextLoc_FilePath()
	 * @model
	 * @generated
	 */
	String getFilePath();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.zeves.TextLoc#getFilePath <em>File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Path</em>' attribute.
	 * @see #getFilePath()
	 * @generated
	 */
	void setFilePath(String value);

	/**
	 * Returns the value of the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Position</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Position</em>' containment reference.
	 * @see #setPosition(Position)
	 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#getTextLoc_Position()
	 * @model containment="true"
	 * @generated
	 */
	Position getPosition();

	/**
	 * Sets the value of the '{@link org.ai4fm.proofprocess.zeves.TextLoc#getPosition <em>Position</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Position</em>' containment reference.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(Position value);

} // TextLoc
