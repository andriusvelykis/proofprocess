/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle.impl;

import isabelle.XML.Tree;

import org.ai4fm.proofprocess.ProofProcessPackage;

import org.ai4fm.proofprocess.isabelle.DisplayTerm;
import org.ai4fm.proofprocess.isabelle.Inst;
import org.ai4fm.proofprocess.isabelle.InstTerm;
import org.ai4fm.proofprocess.isabelle.IsaTerm;
import org.ai4fm.proofprocess.isabelle.IsabelleCommand;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage;
import org.ai4fm.proofprocess.isabelle.IsabelleTrace;

import org.ai4fm.proofprocess.isabelle.NameTerm;
import org.ai4fm.proofprocess.isabelle.NamedTermTree;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
	private EClass nameTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instEClass = null;

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
	private EClass namedTermTreeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass isabelleCommandEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType yXmlTermEDataType = null;

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
	public EAttribute getIsaTerm_Term() {
		return (EAttribute)isaTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNameTerm() {
		return nameTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNameTerm_Name() {
		return (EAttribute)nameTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstTerm() {
		return instTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstTerm_Term() {
		return (EReference)instTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstTerm_Insts() {
		return (EReference)instTermEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInst() {
		return instEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInst_Name() {
		return (EAttribute)instEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInst_Term() {
		return (EReference)instEClass.getEStructuralFeatures().get(1);
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
	public EReference getIsabelleTrace_Command() {
		return (EReference)isabelleTraceEClass.getEStructuralFeatures().get(0);
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
	public EClass getNamedTermTree() {
		return namedTermTreeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedTermTree_Name() {
		return (EAttribute)namedTermTreeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNamedTermTree_Terms() {
		return (EReference)namedTermTreeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNamedTermTree_Branches() {
		return (EReference)namedTermTreeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIsabelleCommand() {
		return isabelleCommandEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIsabelleCommand_Source() {
		return (EAttribute)isabelleCommandEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getYXmlTerm() {
		return yXmlTermEDataType;
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
		createEAttribute(isaTermEClass, ISA_TERM__TERM);

		nameTermEClass = createEClass(NAME_TERM);
		createEAttribute(nameTermEClass, NAME_TERM__NAME);

		instTermEClass = createEClass(INST_TERM);
		createEReference(instTermEClass, INST_TERM__TERM);
		createEReference(instTermEClass, INST_TERM__INSTS);

		instEClass = createEClass(INST);
		createEAttribute(instEClass, INST__NAME);
		createEReference(instEClass, INST__TERM);

		isabelleTraceEClass = createEClass(ISABELLE_TRACE);
		createEReference(isabelleTraceEClass, ISABELLE_TRACE__COMMAND);
		createEAttribute(isabelleTraceEClass, ISABELLE_TRACE__SIMP_LEMMAS);

		namedTermTreeEClass = createEClass(NAMED_TERM_TREE);
		createEAttribute(namedTermTreeEClass, NAMED_TERM_TREE__NAME);
		createEReference(namedTermTreeEClass, NAMED_TERM_TREE__TERMS);
		createEReference(namedTermTreeEClass, NAMED_TERM_TREE__BRANCHES);

		isabelleCommandEClass = createEClass(ISABELLE_COMMAND);
		createEAttribute(isabelleCommandEClass, ISABELLE_COMMAND__SOURCE);

		// Create data types
		yXmlTermEDataType = createEDataType(YXML_TERM);
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
		nameTermEClass.getESuperTypes().add(theProofProcessPackage.getTerm());
		instTermEClass.getESuperTypes().add(theProofProcessPackage.getTerm());
		isabelleTraceEClass.getESuperTypes().add(theProofProcessPackage.getTrace());
		isabelleCommandEClass.getESuperTypes().add(this.getNamedTermTree());

		// Initialize classes and features; add operations and parameters
		initEClass(displayTermEClass, DisplayTerm.class, "DisplayTerm", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDisplayTerm_Display(), ecorePackage.getEString(), "display", null, 0, 1, DisplayTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(isaTermEClass, IsaTerm.class, "IsaTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIsaTerm_Term(), this.getYXmlTerm(), "term", null, 1, 1, IsaTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nameTermEClass, NameTerm.class, "NameTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNameTerm_Name(), ecorePackage.getEString(), "name", null, 1, 1, NameTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instTermEClass, InstTerm.class, "InstTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstTerm_Term(), this.getNameTerm(), null, "term", null, 0, 1, InstTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstTerm_Insts(), this.getInst(), null, "insts", null, 0, -1, InstTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instEClass, Inst.class, "Inst", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInst_Name(), ecorePackage.getEString(), "name", null, 0, 1, Inst.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInst_Term(), theProofProcessPackage.getTerm(), null, "term", null, 1, 1, Inst.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(isabelleTraceEClass, IsabelleTrace.class, "IsabelleTrace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIsabelleTrace_Command(), this.getIsabelleCommand(), null, "command", null, 1, 1, IsabelleTrace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIsabelleTrace_SimpLemmas(), ecorePackage.getEString(), "simpLemmas", null, 0, -1, IsabelleTrace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(namedTermTreeEClass, NamedTermTree.class, "NamedTermTree", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamedTermTree_Name(), ecorePackage.getEString(), "name", null, 1, 1, NamedTermTree.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNamedTermTree_Terms(), theProofProcessPackage.getTerm(), null, "terms", null, 0, -1, NamedTermTree.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNamedTermTree_Branches(), this.getNamedTermTree(), null, "branches", null, 0, -1, NamedTermTree.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(isabelleCommandEClass, IsabelleCommand.class, "IsabelleCommand", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIsabelleCommand_Source(), ecorePackage.getEString(), "source", null, 1, 1, IsabelleCommand.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(yXmlTermEDataType, Tree.class, "YXmlTerm", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //IsabelleProofProcessPackageImpl
