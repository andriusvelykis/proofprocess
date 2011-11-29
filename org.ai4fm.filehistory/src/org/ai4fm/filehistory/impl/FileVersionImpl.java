/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.filehistory.impl;

import org.ai4fm.filehistory.FileHistoryPackage;
import org.ai4fm.filehistory.FileVersion;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

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
public class FileVersionImpl extends EObjectImpl implements FileVersion {
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
	 * The cached value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected long timestamp = TIMESTAMP_EDEFAULT;

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
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

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
	 * The cached value of the '{@link #getChecksum() <em>Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChecksum()
	 * @generated
	 * @ordered
	 */
	protected String checksum = CHECKSUM_EDEFAULT;

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
	 * The cached value of the '{@link #getSyncPoint() <em>Sync Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSyncPoint()
	 * @generated
	 * @ordered
	 */
	protected int syncPoint = SYNC_POINT_EDEFAULT;

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
	 * The cached value of the '{@link #getSyncChecksum() <em>Sync Checksum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSyncChecksum()
	 * @generated
	 * @ordered
	 */
	protected String syncChecksum = SYNC_CHECKSUM_EDEFAULT;

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
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimestamp(long newTimestamp) {
		long oldTimestamp = timestamp;
		timestamp = newTimestamp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FileHistoryPackage.FILE_VERSION__TIMESTAMP, oldTimestamp, timestamp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FileHistoryPackage.FILE_VERSION__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChecksum(String newChecksum) {
		String oldChecksum = checksum;
		checksum = newChecksum;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FileHistoryPackage.FILE_VERSION__CHECKSUM, oldChecksum, checksum));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSyncPoint() {
		return syncPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSyncPoint(int newSyncPoint) {
		int oldSyncPoint = syncPoint;
		syncPoint = newSyncPoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FileHistoryPackage.FILE_VERSION__SYNC_POINT, oldSyncPoint, syncPoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSyncChecksum() {
		return syncChecksum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSyncChecksum(String newSyncChecksum) {
		String oldSyncChecksum = syncChecksum;
		syncChecksum = newSyncChecksum;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FileHistoryPackage.FILE_VERSION__SYNC_CHECKSUM, oldSyncChecksum, syncChecksum));
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
				return timestamp != TIMESTAMP_EDEFAULT;
			case FileHistoryPackage.FILE_VERSION__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case FileHistoryPackage.FILE_VERSION__CHECKSUM:
				return CHECKSUM_EDEFAULT == null ? checksum != null : !CHECKSUM_EDEFAULT.equals(checksum);
			case FileHistoryPackage.FILE_VERSION__SYNC_POINT:
				return syncPoint != SYNC_POINT_EDEFAULT;
			case FileHistoryPackage.FILE_VERSION__SYNC_CHECKSUM:
				return SYNC_CHECKSUM_EDEFAULT == null ? syncChecksum != null : !SYNC_CHECKSUM_EDEFAULT.equals(syncChecksum);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (timestamp: ");
		result.append(timestamp);
		result.append(", path: ");
		result.append(path);
		result.append(", checksum: ");
		result.append(checksum);
		result.append(", syncPoint: ");
		result.append(syncPoint);
		result.append(", syncChecksum: ");
		result.append(syncChecksum);
		result.append(')');
		return result.toString();
	}

} //FileVersionImpl
