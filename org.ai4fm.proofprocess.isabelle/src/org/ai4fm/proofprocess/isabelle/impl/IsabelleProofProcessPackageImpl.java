/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle.impl;

import org.ai4fm.proofprocess.ProofProcessPackage;

import org.ai4fm.proofprocess.isabelle.CompositeTerm;
import org.ai4fm.proofprocess.isabelle.DisplayTerm;
import org.ai4fm.proofprocess.isabelle.IsaTerm;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage;
import org.ai4fm.proofprocess.isabelle.IsabelleTrace;
import org.ai4fm.proofprocess.isabelle.TermKind;

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
public class IsabelleProofProcessPackageImpl extends EPackageImpl implements IsabelleProofProcessPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass displayTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass isaTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositeTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass isabelleTraceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum termKindEEnum = null;

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
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IsabelleProofProcessPackageImpl() {
		super(eNS_URI, IsabelleProofProcessFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link IsabelleProofProcessPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IsabelleProofProcessPackage init() {
		if (isInited) return (IsabelleProofProcessPackage)EPackage.Registry.INSTANCE.getEPackage(IsabelleProofProcessPackage.eNS_URI);

		// Obtain or create and register package
		IsabelleProofProcessPackageImpl theIsabelleProofProcessPackage = (IsabelleProofProcessPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof IsabelleProofProcessPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new IsabelleProofProcessPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ProofProcessPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theIsabelleProofProcessPackage.createPackageContents();

		// Initialize created meta-data
		theIsabelleProofProcessPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIsabelleProofProcessPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IsabelleProofProcessPackage.eNS_URI, theIsabelleProofProcessPackage);
		return theIsabelleProofProcessPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDisplayTerm() {
		return displayTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDisplayTerm_Display() {
		return (EAttribute)displayTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIsaTerm() {
		return isaTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIsaTerm_Kind() {
		return (EAttribute)isaTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIsaTerm_Name() {
		return (EAttribute)isaTermEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositeTerm() {
		return compositeTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositeTerm_Terms() {
		return (EReference)compositeTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIsabelleTrace() {
		return isabelleTraceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIsabelleTrace_Command() {
		return (EAttribute)isabelleTraceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIsabelleTrace_SimpLemmas() {
		return (EAttribute)isabelleTraceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTermKind() {
		return termKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsabelleProofProcessFactory getIsabelleProofProcessFactory() {
		return (IsabelleProofProcessFactory)getEFactoryInstance();
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
		displayTermEClass = createEClass(DISPLAY_TERM);
		createEAttribute(displayTermEClass, DISPLAY_TERM__DISPLAY);

		isaTermEClass = createEClass(ISA_TERM);
		createEAttribute(isaTermEClass, ISA_TERM__KIND);
		createEAttribute(isaTermEClass, ISA_TERM__NAME);

		compositeTermEClass = createEClass(COMPOSITE_TERM);
		createEReference(compositeTermEClass, COMPOSITE_TERM__TERMS);

		isabelleTraceEClass = createEClass(ISABELLE_TRACE);
		createEAttribute(isabelleTraceEClass, ISABELLE_TRACE__COMMAND);
		createEAttribute(isabelleTraceEClass, ISABELLE_TRACE__SIMP_LEMMAS);

		// Create enums
		termKindEEnum = createEEnum(TERM_KIND);
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
		displayTermEClass.getESuperTypes().add(theProofProcessPackage.getTerm());
		isaTermEClass.getESuperTypes().add(this.getDisplayTerm());
		compositeTermEClass.getESuperTypes().add(this.getDisplayTerm());
		isabelleTraceEClass.getESuperTypes().add(theProofProcessPackage.getTrace());

		// Initialize classes and features; add operations and parameters
		initEClass(displayTermEClass, DisplayTerm.class, "DisplayTerm", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDisplayTerm_Display(), ecorePackage.getEString(), "display", null, 0, 1, DisplayTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(isaTermEClass, IsaTerm.class, "IsaTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIsaTerm_Kind(), this.getTermKind(), "kind", null, 1, 1, IsaTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIsaTerm_Name(), ecorePackage.getEString(), "name", null, 0, 1, IsaTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compositeTermEClass, CompositeTerm.class, "CompositeTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompositeTerm_Terms(), theProofProcessPackage.getTerm(), null, "terms", null, 1, -1, CompositeTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(isabelleTraceEClass, IsabelleTrace.class, "IsabelleTrace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIsabelleTrace_Command(), ecorePackage.getEString(), "command", null, 1, 1, IsabelleTrace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIsabelleTrace_SimpLemmas(), ecorePackage.getEString(), "simpLemmas", null, 0, -1, IsabelleTrace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(termKindEEnum, TermKind.class, "TermKind");
		addEEnumLiteral(termKindEEnum, TermKind.CONST);
		addEEnumLiteral(termKindEEnum, TermKind.BOUND);
		addEEnumLiteral(termKindEEnum, TermKind.VAR);
		addEEnumLiteral(termKindEEnum, TermKind.FIXED);
		addEEnumLiteral(termKindEEnum, TermKind.FREE);
		addEEnumLiteral(termKindEEnum, TermKind.NUMERAL);

		// Create resource
		createResource(eNS_URI);
	}

} //IsabelleProofProcessPackageImpl
