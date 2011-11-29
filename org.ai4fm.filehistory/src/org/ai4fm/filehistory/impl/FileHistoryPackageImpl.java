/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.filehistory.impl;

import org.ai4fm.filehistory.FileEntry;
import org.ai4fm.filehistory.FileHistoryFactory;
import org.ai4fm.filehistory.FileHistoryPackage;
import org.ai4fm.filehistory.FileHistoryProject;
import org.ai4fm.filehistory.FileVersion;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FileHistoryPackageImpl extends EPackageImpl implements FileHistoryPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fileHistoryProjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fileEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fileVersionEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.ai4fm.filehistory.FileHistoryPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private FileHistoryPackageImpl() {
		super(eNS_URI, FileHistoryFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link FileHistoryPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static FileHistoryPackage init() {
		if (isInited) return (FileHistoryPackage)EPackage.Registry.INSTANCE.getEPackage(FileHistoryPackage.eNS_URI);

		// Obtain or create and register package
		FileHistoryPackageImpl theFileHistoryPackage = (FileHistoryPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof FileHistoryPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new FileHistoryPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theFileHistoryPackage.createPackageContents();

		// Initialize created meta-data
		theFileHistoryPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theFileHistoryPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(FileHistoryPackage.eNS_URI, theFileHistoryPackage);
		return theFileHistoryPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFileHistoryProject() {
		return fileHistoryProjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFileHistoryProject_Files() {
		return (EReference)fileHistoryProjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFileEntry() {
		return fileEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFileEntry_Versions() {
		return (EReference)fileEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFileEntry_Path() {
		return (EAttribute)fileEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFileVersion() {
		return fileVersionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFileVersion_Timestamp() {
		return (EAttribute)fileVersionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFileVersion_Path() {
		return (EAttribute)fileVersionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFileVersion_Checksum() {
		return (EAttribute)fileVersionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFileVersion_SyncPoint() {
		return (EAttribute)fileVersionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFileVersion_SyncChecksum() {
		return (EAttribute)fileVersionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FileHistoryFactory getFileHistoryFactory() {
		return (FileHistoryFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		fileHistoryProjectEClass = createEClass(FILE_HISTORY_PROJECT);
		createEReference(fileHistoryProjectEClass, FILE_HISTORY_PROJECT__FILES);

		fileEntryEClass = createEClass(FILE_ENTRY);
		createEReference(fileEntryEClass, FILE_ENTRY__VERSIONS);
		createEAttribute(fileEntryEClass, FILE_ENTRY__PATH);

		fileVersionEClass = createEClass(FILE_VERSION);
		createEAttribute(fileVersionEClass, FILE_VERSION__TIMESTAMP);
		createEAttribute(fileVersionEClass, FILE_VERSION__PATH);
		createEAttribute(fileVersionEClass, FILE_VERSION__CHECKSUM);
		createEAttribute(fileVersionEClass, FILE_VERSION__SYNC_POINT);
		createEAttribute(fileVersionEClass, FILE_VERSION__SYNC_CHECKSUM);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(fileHistoryProjectEClass, FileHistoryProject.class, "FileHistoryProject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFileHistoryProject_Files(), this.getFileEntry(), null, "files", null, 0, -1, FileHistoryProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fileEntryEClass, FileEntry.class, "FileEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFileEntry_Versions(), this.getFileVersion(), null, "versions", null, 1, -1, FileEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileEntry_Path(), ecorePackage.getEString(), "path", null, 0, 1, FileEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fileVersionEClass, FileVersion.class, "FileVersion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFileVersion_Timestamp(), ecorePackage.getELong(), "timestamp", null, 1, 1, FileVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileVersion_Path(), ecorePackage.getEString(), "path", null, 1, 1, FileVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileVersion_Checksum(), ecorePackage.getEString(), "checksum", null, 1, 1, FileVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileVersion_SyncPoint(), ecorePackage.getEInt(), "syncPoint", null, 1, 1, FileVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFileVersion_SyncChecksum(), ecorePackage.getEString(), "syncChecksum", null, 1, 1, FileVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //FileHistoryPackageImpl
