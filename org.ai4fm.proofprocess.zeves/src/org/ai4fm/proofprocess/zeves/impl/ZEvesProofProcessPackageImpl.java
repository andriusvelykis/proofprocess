/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves.impl;

import org.ai4fm.proofprocess.ProofProcessPackage;

import org.ai4fm.proofprocess.zeves.Activity;
import org.ai4fm.proofprocess.zeves.Position;
import org.ai4fm.proofprocess.zeves.Project;
import org.ai4fm.proofprocess.zeves.ProofActivity;
import org.ai4fm.proofprocess.zeves.TextLoc;
import org.ai4fm.proofprocess.zeves.ZEvesProofProcessFactory;
import org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage;
import org.ai4fm.proofprocess.zeves.ZEvesTrace;

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
public class ZEvesProofProcessPackageImpl extends EPackageImpl implements ZEvesProofProcessPackage {
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
	private EClass activityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass proofActivityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass zEvesTraceEClass = null;

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
	 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ZEvesProofProcessPackageImpl() {
		super(eNS_URI, ZEvesProofProcessFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ZEvesProofProcessPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ZEvesProofProcessPackage init() {
		if (isInited) return (ZEvesProofProcessPackage)EPackage.Registry.INSTANCE.getEPackage(ZEvesProofProcessPackage.eNS_URI);

		// Obtain or create and register package
		ZEvesProofProcessPackageImpl theZEvesProofProcessPackage = (ZEvesProofProcessPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ZEvesProofProcessPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ZEvesProofProcessPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ProofProcessPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theZEvesProofProcessPackage.createPackageContents();

		// Initialize created meta-data
		theZEvesProofProcessPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theZEvesProofProcessPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ZEvesProofProcessPackage.eNS_URI, theZEvesProofProcessPackage);
		return theZEvesProofProcessPackage;
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
	public EAttribute getProject_ZEvesVersion() {
		return (EAttribute)projectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_Proofs() {
		return (EReference)projectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_Activities() {
		return (EReference)projectEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_Intents() {
		return (EReference)projectEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActivity() {
		return activityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActivity_Description() {
		return (EAttribute)activityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActivity_Timestamp() {
		return (EAttribute)activityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProofActivity() {
		return proofActivityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProofActivity_ProofRef() {
		return (EReference)proofActivityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getZEvesTrace() {
		return zEvesTraceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getZEvesTrace_Markup() {
		return (EAttribute)zEvesTraceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getZEvesTrace_Goal() {
		return (EAttribute)zEvesTraceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getZEvesTrace_UsedLemmas() {
		return (EAttribute)zEvesTraceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getZEvesTrace_Text() {
		return (EAttribute)zEvesTraceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getZEvesTrace_Case() {
		return (EAttribute)zEvesTraceEClass.getEStructuralFeatures().get(4);
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
	public ZEvesProofProcessFactory getZEvesProofProcessFactory() {
		return (ZEvesProofProcessFactory)getEFactoryInstance();
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
		createEAttribute(projectEClass, PROJECT__ZEVES_VERSION);
		createEReference(projectEClass, PROJECT__PROOFS);
		createEReference(projectEClass, PROJECT__ACTIVITIES);
		createEReference(projectEClass, PROJECT__INTENTS);

		activityEClass = createEClass(ACTIVITY);
		createEAttribute(activityEClass, ACTIVITY__DESCRIPTION);
		createEAttribute(activityEClass, ACTIVITY__TIMESTAMP);

		proofActivityEClass = createEClass(PROOF_ACTIVITY);
		createEReference(proofActivityEClass, PROOF_ACTIVITY__PROOF_REF);

		zEvesTraceEClass = createEClass(ZEVES_TRACE);
		createEAttribute(zEvesTraceEClass, ZEVES_TRACE__MARKUP);
		createEAttribute(zEvesTraceEClass, ZEVES_TRACE__GOAL);
		createEAttribute(zEvesTraceEClass, ZEVES_TRACE__USED_LEMMAS);
		createEAttribute(zEvesTraceEClass, ZEVES_TRACE__TEXT);
		createEAttribute(zEvesTraceEClass, ZEVES_TRACE__CASE);

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
		proofActivityEClass.getESuperTypes().add(this.getActivity());
		zEvesTraceEClass.getESuperTypes().add(theProofProcessPackage.getTrace());
		textLocEClass.getESuperTypes().add(theProofProcessPackage.getLoc());

		// Initialize classes and features; add operations and parameters
		initEClass(projectEClass, Project.class, "Project", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProject_Label(), ecorePackage.getEString(), "label", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProject_ZEvesVersion(), ecorePackage.getEString(), "zEvesVersion", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Proofs(), theProofProcessPackage.getProof(), null, "proofs", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Activities(), this.getActivity(), null, "activities", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Intents(), theProofProcessPackage.getIntent(), null, "intents", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(activityEClass, Activity.class, "Activity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getActivity_Description(), ecorePackage.getEString(), "description", null, 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getActivity_Timestamp(), ecorePackage.getEDate(), "timestamp", null, 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(proofActivityEClass, ProofActivity.class, "ProofActivity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProofActivity_ProofRef(), theProofProcessPackage.getProofEntry(), null, "proofRef", null, 0, 1, ProofActivity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(zEvesTraceEClass, ZEvesTrace.class, "ZEvesTrace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getZEvesTrace_Markup(), ecorePackage.getEString(), "markup", null, 0, 1, ZEvesTrace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getZEvesTrace_Goal(), ecorePackage.getEString(), "goal", null, 0, 1, ZEvesTrace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getZEvesTrace_UsedLemmas(), ecorePackage.getEString(), "usedLemmas", null, 0, -1, ZEvesTrace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getZEvesTrace_Text(), ecorePackage.getEString(), "text", null, 1, 1, ZEvesTrace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getZEvesTrace_Case(), ecorePackage.getEString(), "case", "\"\"", 1, 1, ZEvesTrace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(positionEClass, Position.class, "Position", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPosition_Offset(), ecorePackage.getEInt(), "offset", null, 1, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPosition_Length(), ecorePackage.getEInt(), "length", null, 1, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(textLocEClass, TextLoc.class, "TextLoc", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTextLoc_FilePath(), ecorePackage.getEString(), "filePath", null, 0, 1, TextLoc.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTextLoc_Position(), this.getPosition(), null, "position", null, 0, 1, TextLoc.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //ZEvesProofProcessPackageImpl
