/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import org.ai4fm.proofprocess.Attempt;
import org.ai4fm.proofprocess.AttemptEntry;
import org.ai4fm.proofprocess.AttemptGroup;
import org.ai4fm.proofprocess.AttemptSet;
import org.ai4fm.proofprocess.CompositionType;
import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.ProofObject;
import org.ai4fm.proofprocess.ProofProcessFactory;
import org.ai4fm.proofprocess.ProofProcessPackage;
import org.ai4fm.proofprocess.ProofProperty;
import org.ai4fm.proofprocess.ProofReference;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProofProcessPackageImpl extends EPackageImpl implements ProofProcessPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attemptEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass intentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass proofObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass proofPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass proofReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attemptSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attemptGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attemptEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum compositionTypeEEnum = null;

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
	 * @see org.ai4fm.proofprocess.ProofProcessPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ProofProcessPackageImpl() {
		super(eNS_URI, ProofProcessFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ProofProcessPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ProofProcessPackage init() {
		if (isInited) return (ProofProcessPackage)EPackage.Registry.INSTANCE.getEPackage(ProofProcessPackage.eNS_URI);

		// Obtain or create and register package
		ProofProcessPackageImpl theProofProcessPackage = (ProofProcessPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ProofProcessPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ProofProcessPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theProofProcessPackage.createPackageContents();

		// Initialize created meta-data
		theProofProcessPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theProofProcessPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ProofProcessPackage.eNS_URI, theProofProcessPackage);
		return theProofProcessPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttempt() {
		return attemptEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttempt_Description() {
		return (EAttribute)attemptEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttempt_Intent() {
		return (EReference)attemptEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntent() {
		return intentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntent_Name() {
		return (EAttribute)intentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProofObject() {
		return proofObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProofObject_Properties() {
		return (EReference)proofObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProofObject_Content() {
		return (EReference)proofObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProofProperty() {
		return proofPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProofProperty_Name() {
		return (EAttribute)proofPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProofProperty_Description() {
		return (EAttribute)proofPropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProofReference() {
		return proofReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttemptSet() {
		return attemptSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttemptSet_Attempts() {
		return (EReference)attemptSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttemptSet_Label() {
		return (EAttribute)attemptSetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttemptGroup() {
		return attemptGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttemptGroup_CompositionType() {
		return (EAttribute)attemptGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttemptGroup_Attempts() {
		return (EReference)attemptGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttemptGroup_Contained() {
		return (EReference)attemptGroupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttemptEntry() {
		return attemptEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttemptEntry_Content() {
		return (EReference)attemptEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttemptEntry_Inputs() {
		return (EReference)attemptEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttemptEntry_Outputs() {
		return (EReference)attemptEntryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getCompositionType() {
		return compositionTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofProcessFactory getProofProcessFactory() {
		return (ProofProcessFactory)getEFactoryInstance();
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
		attemptEClass = createEClass(ATTEMPT);
		createEAttribute(attemptEClass, ATTEMPT__DESCRIPTION);
		createEReference(attemptEClass, ATTEMPT__INTENT);

		intentEClass = createEClass(INTENT);
		createEAttribute(intentEClass, INTENT__NAME);

		proofObjectEClass = createEClass(PROOF_OBJECT);
		createEReference(proofObjectEClass, PROOF_OBJECT__PROPERTIES);
		createEReference(proofObjectEClass, PROOF_OBJECT__CONTENT);

		proofPropertyEClass = createEClass(PROOF_PROPERTY);
		createEAttribute(proofPropertyEClass, PROOF_PROPERTY__NAME);
		createEAttribute(proofPropertyEClass, PROOF_PROPERTY__DESCRIPTION);

		proofReferenceEClass = createEClass(PROOF_REFERENCE);

		attemptSetEClass = createEClass(ATTEMPT_SET);
		createEReference(attemptSetEClass, ATTEMPT_SET__ATTEMPTS);
		createEAttribute(attemptSetEClass, ATTEMPT_SET__LABEL);

		attemptGroupEClass = createEClass(ATTEMPT_GROUP);
		createEAttribute(attemptGroupEClass, ATTEMPT_GROUP__COMPOSITION_TYPE);
		createEReference(attemptGroupEClass, ATTEMPT_GROUP__ATTEMPTS);
		createEReference(attemptGroupEClass, ATTEMPT_GROUP__CONTAINED);

		attemptEntryEClass = createEClass(ATTEMPT_ENTRY);
		createEReference(attemptEntryEClass, ATTEMPT_ENTRY__CONTENT);
		createEReference(attemptEntryEClass, ATTEMPT_ENTRY__INPUTS);
		createEReference(attemptEntryEClass, ATTEMPT_ENTRY__OUTPUTS);

		// Create enums
		compositionTypeEEnum = createEEnum(COMPOSITION_TYPE);
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
		attemptGroupEClass.getESuperTypes().add(this.getAttempt());
		attemptEntryEClass.getESuperTypes().add(this.getAttempt());

		// Initialize classes and features; add operations and parameters
		initEClass(attemptEClass, Attempt.class, "Attempt", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttempt_Description(), ecorePackage.getEString(), "description", null, 0, 1, Attempt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttempt_Intent(), this.getIntent(), null, "intent", null, 1, 1, Attempt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(attemptEClass, this.getAttempt(), "getAttempts", 0, -1, IS_UNIQUE, IS_ORDERED);

		addEOperation(attemptEClass, this.getCompositionType(), "getCompositionType", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(intentEClass, Intent.class, "Intent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntent_Name(), ecorePackage.getEString(), "name", null, 1, 1, Intent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(proofObjectEClass, ProofObject.class, "ProofObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProofObject_Properties(), this.getProofProperty(), null, "properties", null, 0, -1, ProofObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProofObject_Content(), this.getProofReference(), null, "content", null, 1, 1, ProofObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(proofPropertyEClass, ProofProperty.class, "ProofProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProofProperty_Name(), ecorePackage.getEString(), "name", null, 1, 1, ProofProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProofProperty_Description(), ecorePackage.getEString(), "description", null, 0, 1, ProofProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(proofReferenceEClass, ProofReference.class, "ProofReference", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(proofReferenceEClass, ecorePackage.getEString(), "getText", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(attemptSetEClass, AttemptSet.class, "AttemptSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttemptSet_Attempts(), this.getAttemptGroup(), null, "attempts", null, 0, -1, AttemptSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttemptSet_Label(), ecorePackage.getEString(), "label", null, 0, 1, AttemptSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attemptGroupEClass, AttemptGroup.class, "AttemptGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttemptGroup_CompositionType(), this.getCompositionType(), "compositionType", "SEQUENTIAL", 1, 1, AttemptGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttemptGroup_Attempts(), this.getAttempt(), null, "attempts", null, 0, -1, AttemptGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttemptGroup_Contained(), this.getAttempt(), null, "contained", null, 0, -1, AttemptGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attemptEntryEClass, AttemptEntry.class, "AttemptEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttemptEntry_Content(), this.getProofReference(), null, "content", null, 1, 1, AttemptEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttemptEntry_Inputs(), this.getProofObject(), null, "inputs", null, 1, -1, AttemptEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttemptEntry_Outputs(), this.getProofObject(), null, "outputs", null, 0, -1, AttemptEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(compositionTypeEEnum, CompositionType.class, "CompositionType");
		addEEnumLiteral(compositionTypeEEnum, CompositionType.SEQUENTIAL);
		addEEnumLiteral(compositionTypeEEnum, CompositionType.PARALLEL);

		// Create resource
		createResource(eNS_URI);
	}

} //ProofProcessPackageImpl
