/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.filehistory.impl;

import org.ai4fm.filehistory.FileHistoryPackage;
import org.ai4fm.filehistory.FileVersion;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>File Version</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.filehistory.impl.FileVersionImpl#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link org.ai4fm.filehistory.impl.FileVersionImpl#getPath <em>Path</em>}</li>
 *   <li>{@link org.ai4fm.filehistory.impl.FileVersionImpl#getChecksum <em>Checksum</em>}</li>
 *   <li>{@link org.ai4fm.filehistory.impl.FileVersionImpl#getSyncPoint <em>Sync Point</em>}</li>
 *   <li>{@link org.ai4fm.filehistory.impl.FileVersionImpl#getSyncChecksum <em>Sync Checksum</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FileVersionImpl extends CDOObjectImpl implements FileVersion {
	/**
	 * The default value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected static final long TIMESTAMP_EDEFAULT = 0L;

	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getChecksum() <em>Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChecksum()
	 * @generated
	 * @ordered
	 */
	protected static final String CHECKSUM_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSyncPoint() <em>Sync Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSyncPoint()
	 * @generated
	 * @ordered
	 */
	protected static final int SYNC_POINT_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getSyncChecksum() <em>Sync Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSyncChecksum()
	 * @generated
	 * @ordered
	 */
	protected static final String SYNC_CHECKSUM_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FileVersionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FileHistoryPackage.Literals.FILE_VERSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getTimestamp() {
		return (Long)eDynamicGet(FileHistoryPackage.FILE_VERSION__TIMESTAMP, FileHistoryPackage.Literals.FILE_VERSION__TIMESTAMP, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTimestamp(long newTimestamp) {
		eDynamicSet(FileHistoryPackage.FILE_VERSION__TIMESTAMP, FileHistoryPackage.Literals.FILE_VERSION__TIMESTAMP, newTimestamp);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPath() {
		return (String)eDynamicGet(FileHistoryPackage.FILE_VERSION__PATH, FileHistoryPackage.Literals.FILE_VERSION__PATH, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPath(String newPath) {
		eDynamicSet(FileHistoryPackage.FILE_VERSION__PATH, FileHistoryPackage.Literals.FILE_VERSION__PATH, newPath);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getChecksum() {
		return (String)eDynamicGet(FileHistoryPackage.FILE_VERSION__CHECKSUM, FileHistoryPackage.Literals.FILE_VERSION__CHECKSUM, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setChecksum(String newChecksum) {
		eDynamicSet(FileHistoryPackage.FILE_VERSION__CHECKSUM, FileHistoryPackage.Literals.FILE_VERSION__CHECKSUM, newChecksum);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getSyncPoint() {
		return (Integer)eDynamicGet(FileHistoryPackage.FILE_VERSION__SYNC_POINT, FileHistoryPackage.Literals.FILE_VERSION__SYNC_POINT, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSyncPoint(int newSyncPoint) {
		eDynamicSet(FileHistoryPackage.FILE_VERSION__SYNC_POINT, FileHistoryPackage.Literals.FILE_VERSION__SYNC_POINT, newSyncPoint);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSyncChecksum() {
		return (String)eDynamicGet(FileHistoryPackage.FILE_VERSION__SYNC_CHECKSUM, FileHistoryPackage.Literals.FILE_VERSION__SYNC_CHECKSUM, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSyncChecksum(String newSyncChecksum) {
		eDynamicSet(FileHistoryPackage.FILE_VERSION__SYNC_CHECKSUM, FileHistoryPackage.Literals.FILE_VERSION__SYNC_CHECKSUM, newSyncChecksum);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FileHistoryPackage.FILE_VERSION__TIMESTAMP:
				return getTimestamp();
			case FileHistoryPackage.FILE_VERSION__PATH:
				return getPath();
			case FileHistoryPackage.FILE_VERSION__CHECKSUM:
				return getChecksum();
			case FileHistoryPackage.FILE_VERSION__SYNC_POINT:
				return getSyncPoint();
			case FileHistoryPackage.FILE_VERSION__SYNC_CHECKSUM:
				return getSyncChecksum();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FileHistoryPackage.FILE_VERSION__TIMESTAMP:
				setTimestamp((Long)newValue);
				return;
			case FileHistoryPackage.FILE_VERSION__PATH:
				setPath((String)newValue);
				return;
			case FileHistoryPackage.FILE_VERSION__CHECKSUM:
				setChecksum((String)newValue);
				return;
			case FileHistoryPackage.FILE_VERSION__SYNC_POINT:
				setSyncPoint((Integer)newValue);
				return;
			case FileHistoryPackage.FILE_VERSION__SYNC_CHECKSUM:
				setSyncChecksum((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case FileHistoryPackage.FILE_VERSION__TIMESTAMP:
				setTimestamp(TIMESTAMP_EDEFAULT);
				return;
			case FileHistoryPackage.FILE_VERSION__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case FileHistoryPackage.FILE_VERSION__CHECKSUM:
				setChecksum(CHECKSUM_EDEFAULT);
				return;
			case FileHistoryPackage.FILE_VERSION__SYNC_POINT:
				setSyncPoint(SYNC_POINT_EDEFAULT);
				return;
			case FileHistoryPackage.FILE_VERSION__SYNC_CHECKSUM:
				setSyncChecksum(SYNC_CHECKSUM_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case FileHistoryPackage.FILE_VERSION__TIMESTAMP:
				return getTimestamp() != TIMESTAMP_EDEFAULT;
			case FileHistoryPackage.FILE_VERSION__PATH:
				return PATH_EDEFAULT == null ? getPath() != null : !PATH_EDEFAULT.equals(getPath());
			case FileHistoryPackage.FILE_VERSION__CHECKSUM:
				return CHECKSUM_EDEFAULT == null ? getChecksum() != null : !CHECKSUM_EDEFAULT.equals(getChecksum());
			case FileHistoryPackage.FILE_VERSION__SYNC_POINT:
				return getSyncPoint() != SYNC_POINT_EDEFAULT;
			case FileHistoryPackage.FILE_VERSION__SYNC_CHECKSUM:
				return SYNC_CHECKSUM_EDEFAULT == null ? getSyncChecksum() != null : !SYNC_CHECKSUM_EDEFAULT.equals(getSyncChecksum());
		}
		return super.eIsSet(featureID);
	}

} //FileVersionImpl
