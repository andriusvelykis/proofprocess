/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.filehistory;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.filehistory.FileVersion#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link org.ai4fm.filehistory.FileVersion#getPath <em>Path</em>}</li>
 *   <li>{@link org.ai4fm.filehistory.FileVersion#getChecksum <em>Checksum</em>}</li>
 *   <li>{@link org.ai4fm.filehistory.FileVersion#getSyncPoint <em>Sync Point</em>}</li>
 *   <li>{@link org.ai4fm.filehistory.FileVersion#getSyncChecksum <em>Sync Checksum</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.filehistory.FileHistoryPackage#getFileVersion()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface FileVersion extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timestamp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timestamp</em>' attribute.
	 * @see #setTimestamp(long)
	 * @see org.ai4fm.filehistory.FileHistoryPackage#getFileVersion_Timestamp()
	 * @model required="true"
	 * @generated
	 */
	long getTimestamp();

	/**
	 * Sets the value of the '{@link org.ai4fm.filehistory.FileVersion#getTimestamp <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timestamp</em>' attribute.
	 * @see #getTimestamp()
	 * @generated
	 */
	void setTimestamp(long value);

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
	 * @see org.ai4fm.filehistory.FileHistoryPackage#getFileVersion_Path()
	 * @model required="true"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link org.ai4fm.filehistory.FileVersion#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Checksum</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Checksum</em>' attribute.
	 * @see #setChecksum(String)
	 * @see org.ai4fm.filehistory.FileHistoryPackage#getFileVersion_Checksum()
	 * @model required="true"
	 * @generated
	 */
	String getChecksum();

	/**
	 * Sets the value of the '{@link org.ai4fm.filehistory.FileVersion#getChecksum <em>Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Checksum</em>' attribute.
	 * @see #getChecksum()
	 * @generated
	 */
	void setChecksum(String value);

	/**
	 * Returns the value of the '<em><b>Sync Point</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sync Point</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sync Point</em>' attribute.
	 * @see #setSyncPoint(int)
	 * @see org.ai4fm.filehistory.FileHistoryPackage#getFileVersion_SyncPoint()
	 * @model required="true"
	 * @generated
	 */
	int getSyncPoint();

	/**
	 * Sets the value of the '{@link org.ai4fm.filehistory.FileVersion#getSyncPoint <em>Sync Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sync Point</em>' attribute.
	 * @see #getSyncPoint()
	 * @generated
	 */
	void setSyncPoint(int value);

	/**
	 * Returns the value of the '<em><b>Sync Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sync Checksum</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sync Checksum</em>' attribute.
	 * @see #setSyncChecksum(String)
	 * @see org.ai4fm.filehistory.FileHistoryPackage#getFileVersion_SyncChecksum()
	 * @model required="true"
	 * @generated
	 */
	String getSyncChecksum();

	/**
	 * Sets the value of the '{@link org.ai4fm.filehistory.FileVersion#getSyncChecksum <em>Sync Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sync Checksum</em>' attribute.
	 * @see #getSyncChecksum()
	 * @generated
	 */
	void setSyncChecksum(String value);

} // FileVersion
