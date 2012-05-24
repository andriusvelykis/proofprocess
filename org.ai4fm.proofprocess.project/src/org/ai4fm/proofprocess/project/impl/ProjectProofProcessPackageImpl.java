/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.project.impl;

import org.ai4fm.proofprocess.ProofProcessPackage;

import org.ai4fm.proofprocess.project.Position;
import org.ai4fm.proofprocess.project.Project;
import org.ai4fm.proofprocess.project.ProjectProofProcessFactory;
import org.ai4fm.proofprocess.project.ProjectProofProcessPackage;
import org.ai4fm.proofprocess.project.TextLoc;

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
public class ProjectProofProcessPackageImpl extends EPackageImpl implements ProjectProofProcessPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass projectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass positionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textLocEClass = null;

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
	 * @see org.ai4fm.proofprocess.project.ProjectProofProcessPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ProjectProofProcessPackageImpl() {
		super(eNS_URI, ProjectProofProcessFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ProjectProofProcessPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ProjectProofProcessPackage init() {
		if (isInited) return (ProjectProofProcessPackage)EPackage.Registry.INSTANCE.getEPackage(ProjectProofProcessPackage.eNS_URI);

		// Obtain or create and register package
		ProjectProofProcessPackageImpl theProjectProofProcessPackage = (ProjectProofProcessPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ProjectProofProcessPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ProjectProofProcessPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ProofProcessPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theProjectProofProcessPackage.createPackageContents();

		// Initialize created meta-data
		theProjectProofProcessPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theProjectProofProcessPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ProjectProofProcessPackage.eNS_URI, theProjectProofProcessPackage);
		return theProjectProofProcessPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProject() {
		return projectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProject_Label() {
		return (EAttribute)projectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPosition() {
		return positionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPosition_Offset() {
		return (EAttribute)positionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPosition_Length() {
		return (EAttribute)positionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextLoc() {
		return textLocEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextLoc_FilePath() {
		return (EAttribute)textLocEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTextLoc_Position() {
		return (EReference)textLocEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectProofProcessFactory getProjectProofProcessFactory() {
		return (ProjectProofProcessFactory)getEFactoryInstance();
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
		projectEClass = createEClass(PROJECT);
		createEAttribute(projectEClass, PROJECT__LABEL);

		positionEClass = createEClass(POSITION);
		createEAttribute(positionEClass, POSITION__OFFSET);
		createEAttribute(positionEClass, POSITION__LENGTH);

		textLocEClass = createEClass(TEXT_LOC);
		createEAttribute(textLocEClass, TEXT_LOC__FILE_PATH);
		createEReference(textLocEClass, TEXT_LOC__POSITION);
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

		// Obtain other dependent packages
		ProofProcessPackage theProofProcessPackage = (ProofProcessPackage)EPackage.Registry.INSTANCE.getEPackage(ProofProcessPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		projectEClass.getESuperTypes().add(theProofProcessPackage.getProofStore());
		textLocEClass.getESuperTypes().add(theProofProcessPackage.getLoc());

		// Initialize classes and features; add operations and parameters
		initEClass(projectEClass, Project.class, "Project", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProject_Label(), ecorePackage.getEString(), "label", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(positionEClass, Position.class, "Position", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPosition_Offset(), ecorePackage.getEInt(), "offset", null, 1, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPosition_Length(), ecorePackage.getEInt(), "length", null, 1, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(textLocEClass, TextLoc.class, "TextLoc", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTextLoc_FilePath(), ecorePackage.getEString(), "filePath", null, 0, 1, TextLoc.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTextLoc_Position(), this.getPosition(), null, "position", null, 0, 1, TextLoc.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //ProjectProofProcessPackageImpl
