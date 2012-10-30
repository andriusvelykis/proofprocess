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
 * A representation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.filehistory.FileHistoryProject#getFiles <em>Files</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.filehistory.FileHistoryPackage#getFileHistoryProject()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface FileHistoryProject extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Files</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.filehistory.FileEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Files</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Files</em>' containment reference list.
	 * @see org.ai4fm.filehistory.FileHistoryPackage#getFileHistoryProject_Files()
	 * @model containment="true"
	 * @generated
	 */
	EList<FileEntry> getFiles();

} // FileHistoryProject
