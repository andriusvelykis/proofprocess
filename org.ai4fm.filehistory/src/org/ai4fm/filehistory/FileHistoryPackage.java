/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.filehistory;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.ai4fm.filehistory.FileHistoryFactory
 * @model kind="package"
 * @generated
 */
public interface FileHistoryPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "filehistory";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org/ai4fm/filehistory";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "filehistory";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FileHistoryPackage eINSTANCE = org.ai4fm.filehistory.impl.FileHistoryPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.ai4fm.filehistory.impl.FileHistoryProjectImpl <em>Project</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.filehistory.impl.FileHistoryProjectImpl
	 * @see org.ai4fm.filehistory.impl.FileHistoryPackageImpl#getFileHistoryProject()
	 * @generated
	 */
	int FILE_HISTORY_PROJECT = 0;

	/**
	 * The feature id for the '<em><b>Files</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_HISTORY_PROJECT__FILES = 0;

	/**
	 * The number of structural features of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_HISTORY_PROJECT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.filehistory.impl.FileEntryImpl <em>File Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.filehistory.impl.FileEntryImpl
	 * @see org.ai4fm.filehistory.impl.FileHistoryPackageImpl#getFileEntry()
	 * @generated
	 */
	int FILE_ENTRY = 1;

	/**
	 * The feature id for the '<em><b>Versions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ENTRY__VERSIONS = 0;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ENTRY__PATH = 1;

	/**
	 * The number of structural features of the '<em>File Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.ai4fm.filehistory.impl.FileVersionImpl <em>File Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.filehistory.impl.FileVersionImpl
	 * @see org.ai4fm.filehistory.impl.FileHistoryPackageImpl#getFileVersion()
	 * @generated
	 */
	int FILE_VERSION = 2;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_VERSION__TIMESTAMP = 0;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_VERSION__PATH = 1;

	/**
	 * The feature id for the '<em><b>Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_VERSION__CHECKSUM = 2;

	/**
	 * The feature id for the '<em><b>Sync Point</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_VERSION__SYNC_POINT = 3;

	/**
	 * The feature id for the '<em><b>Sync Checksum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_VERSION__SYNC_CHECKSUM = 4;

	/**
	 * The number of structural features of the '<em>File Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_VERSION_FEATURE_COUNT = 5;


	/**
	 * Returns the meta object for class '{@link org.ai4fm.filehistory.FileHistoryProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project</em>'.
	 * @see org.ai4fm.filehistory.FileHistoryProject
	 * @generated
	 */
	EClass getFileHistoryProject();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.filehistory.FileHistoryProject#getFiles <em>Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Files</em>'.
	 * @see org.ai4fm.filehistory.FileHistoryProject#getFiles()
	 * @see #getFileHistoryProject()
	 * @generated
	 */
	EReference getFileHistoryProject_Files();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.filehistory.FileEntry <em>File Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File Entry</em>'.
	 * @see org.ai4fm.filehistory.FileEntry
	 * @generated
	 */
	EClass getFileEntry();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.filehistory.FileEntry#getVersions <em>Versions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Versions</em>'.
	 * @see org.ai4fm.filehistory.FileEntry#getVersions()
	 * @see #getFileEntry()
	 * @generated
	 */
	EReference getFileEntry_Versions();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.filehistory.FileEntry#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see org.ai4fm.filehistory.FileEntry#getPath()
	 * @see #getFileEntry()
	 * @generated
	 */
	EAttribute getFileEntry_Path();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.filehistory.FileVersion <em>File Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File Version</em>'.
	 * @see org.ai4fm.filehistory.FileVersion
	 * @generated
	 */
	EClass getFileVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.filehistory.FileVersion#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see org.ai4fm.filehistory.FileVersion#getTimestamp()
	 * @see #getFileVersion()
	 * @generated
	 */
	EAttribute getFileVersion_Timestamp();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.filehistory.FileVersion#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see org.ai4fm.filehistory.FileVersion#getPath()
	 * @see #getFileVersion()
	 * @generated
	 */
	EAttribute getFileVersion_Path();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.filehistory.FileVersion#getChecksum <em>Checksum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Checksum</em>'.
	 * @see org.ai4fm.filehistory.FileVersion#getChecksum()
	 * @see #getFileVersion()
	 * @generated
	 */
	EAttribute getFileVersion_Checksum();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.filehistory.FileVersion#getSyncPoint <em>Sync Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sync Point</em>'.
	 * @see org.ai4fm.filehistory.FileVersion#getSyncPoint()
	 * @see #getFileVersion()
	 * @generated
	 */
	EAttribute getFileVersion_SyncPoint();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.filehistory.FileVersion#getSyncChecksum <em>Sync Checksum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sync Checksum</em>'.
	 * @see org.ai4fm.filehistory.FileVersion#getSyncChecksum()
	 * @see #getFileVersion()
	 * @generated
	 */
	EAttribute getFileVersion_SyncChecksum();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FileHistoryFactory getFileHistoryFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.ai4fm.filehistory.impl.FileHistoryProjectImpl <em>Project</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.filehistory.impl.FileHistoryProjectImpl
		 * @see org.ai4fm.filehistory.impl.FileHistoryPackageImpl#getFileHistoryProject()
		 * @generated
		 */
		EClass FILE_HISTORY_PROJECT = eINSTANCE.getFileHistoryProject();

		/**
		 * The meta object literal for the '<em><b>Files</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILE_HISTORY_PROJECT__FILES = eINSTANCE.getFileHistoryProject_Files();

		/**
		 * The meta object literal for the '{@link org.ai4fm.filehistory.impl.FileEntryImpl <em>File Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.filehistory.impl.FileEntryImpl
		 * @see org.ai4fm.filehistory.impl.FileHistoryPackageImpl#getFileEntry()
		 * @generated
		 */
		EClass FILE_ENTRY = eINSTANCE.getFileEntry();

		/**
		 * The meta object literal for the '<em><b>Versions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILE_ENTRY__VERSIONS = eINSTANCE.getFileEntry_Versions();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_ENTRY__PATH = eINSTANCE.getFileEntry_Path();

		/**
		 * The meta object literal for the '{@link org.ai4fm.filehistory.impl.FileVersionImpl <em>File Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.filehistory.impl.FileVersionImpl
		 * @see org.ai4fm.filehistory.impl.FileHistoryPackageImpl#getFileVersion()
		 * @generated
		 */
		EClass FILE_VERSION = eINSTANCE.getFileVersion();

		/**
		 * The meta object literal for the '<em><b>Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_VERSION__TIMESTAMP = eINSTANCE.getFileVersion_Timestamp();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_VERSION__PATH = eINSTANCE.getFileVersion_Path();

		/**
		 * The meta object literal for the '<em><b>Checksum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_VERSION__CHECKSUM = eINSTANCE.getFileVersion_Checksum();

		/**
		 * The meta object literal for the '<em><b>Sync Point</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_VERSION__SYNC_POINT = eINSTANCE.getFileVersion_SyncPoint();

		/**
		 * The meta object literal for the '<em><b>Sync Checksum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_VERSION__SYNC_CHECKSUM = eINSTANCE.getFileVersion_SyncChecksum();

	}

} //FileHistoryPackage
