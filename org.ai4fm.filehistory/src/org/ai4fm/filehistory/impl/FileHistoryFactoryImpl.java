/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.filehistory.impl;

import org.ai4fm.filehistory.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FileHistoryFactoryImpl extends EFactoryImpl implements FileHistoryFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FileHistoryFactory init() {
		try {
			FileHistoryFactory theFileHistoryFactory = (FileHistoryFactory)EPackage.Registry.INSTANCE.getEFactory("http://org/ai4fm/filehistory"); 
			if (theFileHistoryFactory != null) {
				return theFileHistoryFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new FileHistoryFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FileHistoryFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case FileHistoryPackage.FILE_HISTORY_PROJECT: return (EObject)createFileHistoryProject();
			case FileHistoryPackage.FILE_ENTRY: return (EObject)createFileEntry();
			case FileHistoryPackage.FILE_VERSION: return (EObject)createFileVersion();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FileHistoryProject createFileHistoryProject() {
		FileHistoryProjectImpl fileHistoryProject = new FileHistoryProjectImpl();
		return fileHistoryProject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FileEntry createFileEntry() {
		FileEntryImpl fileEntry = new FileEntryImpl();
		return fileEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FileVersion createFileVersion() {
		FileVersionImpl fileVersion = new FileVersionImpl();
		return fileVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FileHistoryPackage getFileHistoryPackage() {
		return (FileHistoryPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static FileHistoryPackage getPackage() {
		return FileHistoryPackage.eINSTANCE;
	}

} //FileHistoryFactoryImpl
