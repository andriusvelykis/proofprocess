/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.filehistory;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.filehistory.FileEntry#getVersions <em>Versions</em>}</li>
 *   <li>{@link org.ai4fm.filehistory.FileEntry#getPath <em>Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.filehistory.FileHistoryPackage#getFileEntry()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface FileEntry extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Versions</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.filehistory.FileVersion}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Versions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Versions</em>' containment reference list.
	 * @see org.ai4fm.filehistory.FileHistoryPackage#getFileEntry_Versions()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<FileVersion> getVersions();

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see org.ai4fm.filehistory.FileHistoryPackage#getFileEntry_Path()
	 * @model
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link org.ai4fm.filehistory.FileEntry#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

} // FileEntry
