/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see org.ai4fm.proofprocess.ProofProcessFactory
 * @model kind="package"
 * @generated
 */
public interface ProofProcessPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "proofprocess";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org/ai4fm/proofprocess/v0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "proof";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProofProcessPackage eINSTANCE = org.ai4fm.proofprocess.impl.ProofProcessPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.IntentImpl <em>Intent</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.IntentImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getIntent()
	 * @generated
	 */
	int INTENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENT__DESCRIPTION = 1;

	/**
	 * The number of structural features of the '<em>Intent</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.Term <em>Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.Term
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getTerm()
	 * @generated
	 */
	int TERM = 1;

	/**
	 * The number of structural features of the '<em>Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.Loc <em>Loc</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.Loc
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getLoc()
	 * @generated
	 */
	int LOC = 2;

	/**
	 * The number of structural features of the '<em>Loc</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOC_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.Trace <em>Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.Trace
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getTrace()
	 * @generated
	 */
	int TRACE = 3;

	/**
	 * The number of structural features of the '<em>Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofStepImpl <em>Proof Step</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofStepImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofStep()
	 * @generated
	 */
	int PROOF_STEP = 4;

	/**
	 * The feature id for the '<em><b>In Goals</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_STEP__IN_GOALS = 0;

	/**
	 * The feature id for the '<em><b>Out Goals</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_STEP__OUT_GOALS = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_STEP__SOURCE = 2;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_STEP__TRACE = 3;

	/**
	 * The number of structural features of the '<em>Proof Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_STEP_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofInfoImpl <em>Proof Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofInfoImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofInfo()
	 * @generated
	 */
	int PROOF_INFO = 5;

	/**
	 * The feature id for the '<em><b>Intent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_INFO__INTENT = 0;

	/**
	 * The feature id for the '<em><b>Narrative</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_INFO__NARRATIVE = 1;

	/**
	 * The feature id for the '<em><b>In Props</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_INFO__IN_PROPS = 2;

	/**
	 * The feature id for the '<em><b>Out Props</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_INFO__OUT_PROPS = 3;

	/**
	 * The number of structural features of the '<em>Proof Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_INFO_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.PropertyDefImpl <em>Property Def</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.PropertyDefImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getPropertyDef()
	 * @generated
	 */
	int PROPERTY_DEF = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEF__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEF__DESCRIPTION = 1;

	/**
	 * The number of structural features of the '<em>Property Def</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_DEF_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.PropertyImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Params</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__PARAMS = 2;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofElemImpl <em>Proof Elem</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofElemImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofElem()
	 * @generated
	 */
	int PROOF_ELEM = 8;

	/**
	 * The feature id for the '<em><b>Info</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_ELEM__INFO = 0;

	/**
	 * The number of structural features of the '<em>Proof Elem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_ELEM_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofEntryImpl <em>Proof Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofEntryImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofEntry()
	 * @generated
	 */
	int PROOF_ENTRY = 9;

	/**
	 * The feature id for the '<em><b>Info</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_ENTRY__INFO = PROOF_ELEM__INFO;

	/**
	 * The feature id for the '<em><b>Proof Step</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_ENTRY__PROOF_STEP = PROOF_ELEM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Proof Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_ENTRY_FEATURE_COUNT = PROOF_ELEM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofSeqImpl <em>Proof Seq</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofSeqImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofSeq()
	 * @generated
	 */
	int PROOF_SEQ = 10;

	/**
	 * The feature id for the '<em><b>Info</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_SEQ__INFO = PROOF_ELEM__INFO;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_SEQ__ENTRIES = PROOF_ELEM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Proof Seq</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_SEQ_FEATURE_COUNT = PROOF_ELEM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofParallelImpl <em>Proof Parallel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofParallelImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofParallel()
	 * @generated
	 */
	int PROOF_PARALLEL = 11;

	/**
	 * The feature id for the '<em><b>Info</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_PARALLEL__INFO = PROOF_ELEM__INFO;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_PARALLEL__ENTRIES = PROOF_ELEM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Proof Parallel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_PARALLEL_FEATURE_COUNT = PROOF_ELEM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofDecorImpl <em>Proof Decor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofDecorImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofDecor()
	 * @generated
	 */
	int PROOF_DECOR = 12;

	/**
	 * The feature id for the '<em><b>Info</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_DECOR__INFO = PROOF_ELEM__INFO;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_DECOR__ENTRY = PROOF_ELEM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Proof Decor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_DECOR_FEATURE_COUNT = PROOF_ELEM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.AttemptImpl <em>Attempt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.AttemptImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getAttempt()
	 * @generated
	 */
	int ATTEMPT = 13;

	/**
	 * The feature id for the '<em><b>Proof</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT__PROOF = 0;

	/**
	 * The number of structural features of the '<em>Attempt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofImpl <em>Proof</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProof()
	 * @generated
	 */
	int PROOF = 14;

	/**
	 * The feature id for the '<em><b>Goals</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF__GOALS = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF__LABEL = 1;

	/**
	 * The feature id for the '<em><b>Attempts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF__ATTEMPTS = 2;

	/**
	 * The number of structural features of the '<em>Proof</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.PropertyType <em>Property Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.PropertyType
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getPropertyType()
	 * @generated
	 */
	int PROPERTY_TYPE = 15;


	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.Intent <em>Intent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Intent</em>'.
	 * @see org.ai4fm.proofprocess.Intent
	 * @generated
	 */
	EClass getIntent();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.Intent#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.ai4fm.proofprocess.Intent#getName()
	 * @see #getIntent()
	 * @generated
	 */
	EAttribute getIntent_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.Intent#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.ai4fm.proofprocess.Intent#getDescription()
	 * @see #getIntent()
	 * @generated
	 */
	EAttribute getIntent_Description();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.Term <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Term</em>'.
	 * @see org.ai4fm.proofprocess.Term
	 * @generated
	 */
	EClass getTerm();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.Loc <em>Loc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Loc</em>'.
	 * @see org.ai4fm.proofprocess.Loc
	 * @generated
	 */
	EClass getLoc();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.Trace <em>Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trace</em>'.
	 * @see org.ai4fm.proofprocess.Trace
	 * @generated
	 */
	EClass getTrace();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.ProofStep <em>Proof Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Step</em>'.
	 * @see org.ai4fm.proofprocess.ProofStep
	 * @generated
	 */
	EClass getProofStep();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.ProofStep#getInGoals <em>In Goals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>In Goals</em>'.
	 * @see org.ai4fm.proofprocess.ProofStep#getInGoals()
	 * @see #getProofStep()
	 * @generated
	 */
	EReference getProofStep_InGoals();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.ProofStep#getOutGoals <em>Out Goals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Out Goals</em>'.
	 * @see org.ai4fm.proofprocess.ProofStep#getOutGoals()
	 * @see #getProofStep()
	 * @generated
	 */
	EReference getProofStep_OutGoals();

	/**
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.ProofStep#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source</em>'.
	 * @see org.ai4fm.proofprocess.ProofStep#getSource()
	 * @see #getProofStep()
	 * @generated
	 */
	EReference getProofStep_Source();

	/**
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.ProofStep#getTrace <em>Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Trace</em>'.
	 * @see org.ai4fm.proofprocess.ProofStep#getTrace()
	 * @see #getProofStep()
	 * @generated
	 */
	EReference getProofStep_Trace();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.ProofInfo <em>Proof Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Info</em>'.
	 * @see org.ai4fm.proofprocess.ProofInfo
	 * @generated
	 */
	EClass getProofInfo();

	/**
	 * Returns the meta object for the reference '{@link org.ai4fm.proofprocess.ProofInfo#getIntent <em>Intent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Intent</em>'.
	 * @see org.ai4fm.proofprocess.ProofInfo#getIntent()
	 * @see #getProofInfo()
	 * @generated
	 */
	EReference getProofInfo_Intent();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.ProofInfo#getNarrative <em>Narrative</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Narrative</em>'.
	 * @see org.ai4fm.proofprocess.ProofInfo#getNarrative()
	 * @see #getProofInfo()
	 * @generated
	 */
	EAttribute getProofInfo_Narrative();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.ProofInfo#getInProps <em>In Props</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>In Props</em>'.
	 * @see org.ai4fm.proofprocess.ProofInfo#getInProps()
	 * @see #getProofInfo()
	 * @generated
	 */
	EReference getProofInfo_InProps();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.ProofInfo#getOutProps <em>Out Props</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Out Props</em>'.
	 * @see org.ai4fm.proofprocess.ProofInfo#getOutProps()
	 * @see #getProofInfo()
	 * @generated
	 */
	EReference getProofInfo_OutProps();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.PropertyDef <em>Property Def</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Def</em>'.
	 * @see org.ai4fm.proofprocess.PropertyDef
	 * @generated
	 */
	EClass getPropertyDef();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.PropertyDef#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.ai4fm.proofprocess.PropertyDef#getName()
	 * @see #getPropertyDef()
	 * @generated
	 */
	EAttribute getPropertyDef_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.PropertyDef#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.ai4fm.proofprocess.PropertyDef#getDescription()
	 * @see #getPropertyDef()
	 * @generated
	 */
	EAttribute getPropertyDef_Description();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see org.ai4fm.proofprocess.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the reference '{@link org.ai4fm.proofprocess.Property#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Name</em>'.
	 * @see org.ai4fm.proofprocess.Property#getName()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.Property#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.ai4fm.proofprocess.Property#getType()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Type();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.Property#getParams <em>Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Params</em>'.
	 * @see org.ai4fm.proofprocess.Property#getParams()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_Params();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.ProofElem <em>Proof Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Elem</em>'.
	 * @see org.ai4fm.proofprocess.ProofElem
	 * @generated
	 */
	EClass getProofElem();

	/**
	 * Returns the meta object for the reference '{@link org.ai4fm.proofprocess.ProofElem#getInfo <em>Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Info</em>'.
	 * @see org.ai4fm.proofprocess.ProofElem#getInfo()
	 * @see #getProofElem()
	 * @generated
	 */
	EReference getProofElem_Info();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.ProofEntry <em>Proof Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Entry</em>'.
	 * @see org.ai4fm.proofprocess.ProofEntry
	 * @generated
	 */
	EClass getProofEntry();

	/**
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.ProofEntry#getProofStep <em>Proof Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Proof Step</em>'.
	 * @see org.ai4fm.proofprocess.ProofEntry#getProofStep()
	 * @see #getProofEntry()
	 * @generated
	 */
	EReference getProofEntry_ProofStep();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.ProofSeq <em>Proof Seq</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Seq</em>'.
	 * @see org.ai4fm.proofprocess.ProofSeq
	 * @generated
	 */
	EClass getProofSeq();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.ProofSeq#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see org.ai4fm.proofprocess.ProofSeq#getEntries()
	 * @see #getProofSeq()
	 * @generated
	 */
	EReference getProofSeq_Entries();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.ProofParallel <em>Proof Parallel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Parallel</em>'.
	 * @see org.ai4fm.proofprocess.ProofParallel
	 * @generated
	 */
	EClass getProofParallel();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.ProofParallel#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see org.ai4fm.proofprocess.ProofParallel#getEntries()
	 * @see #getProofParallel()
	 * @generated
	 */
	EReference getProofParallel_Entries();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.ProofDecor <em>Proof Decor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Decor</em>'.
	 * @see org.ai4fm.proofprocess.ProofDecor
	 * @generated
	 */
	EClass getProofDecor();

	/**
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.ProofDecor#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entry</em>'.
	 * @see org.ai4fm.proofprocess.ProofDecor#getEntry()
	 * @see #getProofDecor()
	 * @generated
	 */
	EReference getProofDecor_Entry();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.Attempt <em>Attempt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attempt</em>'.
	 * @see org.ai4fm.proofprocess.Attempt
	 * @generated
	 */
	EClass getAttempt();

	/**
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.Attempt#getProof <em>Proof</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Proof</em>'.
	 * @see org.ai4fm.proofprocess.Attempt#getProof()
	 * @see #getAttempt()
	 * @generated
	 */
	EReference getAttempt_Proof();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.Proof <em>Proof</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof</em>'.
	 * @see org.ai4fm.proofprocess.Proof
	 * @generated
	 */
	EClass getProof();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.Proof#getGoals <em>Goals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Goals</em>'.
	 * @see org.ai4fm.proofprocess.Proof#getGoals()
	 * @see #getProof()
	 * @generated
	 */
	EReference getProof_Goals();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.Proof#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.ai4fm.proofprocess.Proof#getLabel()
	 * @see #getProof()
	 * @generated
	 */
	EAttribute getProof_Label();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.Proof#getAttempts <em>Attempts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attempts</em>'.
	 * @see org.ai4fm.proofprocess.Proof#getAttempts()
	 * @see #getProof()
	 * @generated
	 */
	EReference getProof_Attempts();

	/**
	 * Returns the meta object for enum '{@link org.ai4fm.proofprocess.PropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Property Type</em>'.
	 * @see org.ai4fm.proofprocess.PropertyType
	 * @generated
	 */
	EEnum getPropertyType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ProofProcessFactory getProofProcessFactory();

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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.IntentImpl <em>Intent</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.IntentImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getIntent()
		 * @generated
		 */
		EClass INTENT = eINSTANCE.getIntent();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTENT__NAME = eINSTANCE.getIntent_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTENT__DESCRIPTION = eINSTANCE.getIntent_Description();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.Term <em>Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.Term
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getTerm()
		 * @generated
		 */
		EClass TERM = eINSTANCE.getTerm();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.Loc <em>Loc</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.Loc
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getLoc()
		 * @generated
		 */
		EClass LOC = eINSTANCE.getLoc();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.Trace <em>Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.Trace
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getTrace()
		 * @generated
		 */
		EClass TRACE = eINSTANCE.getTrace();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.ProofStepImpl <em>Proof Step</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.ProofStepImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofStep()
		 * @generated
		 */
		EClass PROOF_STEP = eINSTANCE.getProofStep();

		/**
		 * The meta object literal for the '<em><b>In Goals</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_STEP__IN_GOALS = eINSTANCE.getProofStep_InGoals();

		/**
		 * The meta object literal for the '<em><b>Out Goals</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_STEP__OUT_GOALS = eINSTANCE.getProofStep_OutGoals();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_STEP__SOURCE = eINSTANCE.getProofStep_Source();

		/**
		 * The meta object literal for the '<em><b>Trace</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_STEP__TRACE = eINSTANCE.getProofStep_Trace();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.ProofInfoImpl <em>Proof Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.ProofInfoImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofInfo()
		 * @generated
		 */
		EClass PROOF_INFO = eINSTANCE.getProofInfo();

		/**
		 * The meta object literal for the '<em><b>Intent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_INFO__INTENT = eINSTANCE.getProofInfo_Intent();

		/**
		 * The meta object literal for the '<em><b>Narrative</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROOF_INFO__NARRATIVE = eINSTANCE.getProofInfo_Narrative();

		/**
		 * The meta object literal for the '<em><b>In Props</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_INFO__IN_PROPS = eINSTANCE.getProofInfo_InProps();

		/**
		 * The meta object literal for the '<em><b>Out Props</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_INFO__OUT_PROPS = eINSTANCE.getProofInfo_OutProps();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.PropertyDefImpl <em>Property Def</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.PropertyDefImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getPropertyDef()
		 * @generated
		 */
		EClass PROPERTY_DEF = eINSTANCE.getPropertyDef();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEF__NAME = eINSTANCE.getPropertyDef_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_DEF__DESCRIPTION = eINSTANCE.getPropertyDef_Description();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.PropertyImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__NAME = eINSTANCE.getProperty_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__TYPE = eINSTANCE.getProperty_Type();

		/**
		 * The meta object literal for the '<em><b>Params</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__PARAMS = eINSTANCE.getProperty_Params();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.ProofElemImpl <em>Proof Elem</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.ProofElemImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofElem()
		 * @generated
		 */
		EClass PROOF_ELEM = eINSTANCE.getProofElem();

		/**
		 * The meta object literal for the '<em><b>Info</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_ELEM__INFO = eINSTANCE.getProofElem_Info();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.ProofEntryImpl <em>Proof Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.ProofEntryImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofEntry()
		 * @generated
		 */
		EClass PROOF_ENTRY = eINSTANCE.getProofEntry();

		/**
		 * The meta object literal for the '<em><b>Proof Step</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_ENTRY__PROOF_STEP = eINSTANCE.getProofEntry_ProofStep();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.ProofSeqImpl <em>Proof Seq</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.ProofSeqImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofSeq()
		 * @generated
		 */
		EClass PROOF_SEQ = eINSTANCE.getProofSeq();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_SEQ__ENTRIES = eINSTANCE.getProofSeq_Entries();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.ProofParallelImpl <em>Proof Parallel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.ProofParallelImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofParallel()
		 * @generated
		 */
		EClass PROOF_PARALLEL = eINSTANCE.getProofParallel();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_PARALLEL__ENTRIES = eINSTANCE.getProofParallel_Entries();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.ProofDecorImpl <em>Proof Decor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.ProofDecorImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofDecor()
		 * @generated
		 */
		EClass PROOF_DECOR = eINSTANCE.getProofDecor();

		/**
		 * The meta object literal for the '<em><b>Entry</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_DECOR__ENTRY = eINSTANCE.getProofDecor_Entry();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.AttemptImpl <em>Attempt</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.AttemptImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getAttempt()
		 * @generated
		 */
		EClass ATTEMPT = eINSTANCE.getAttempt();

		/**
		 * The meta object literal for the '<em><b>Proof</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTEMPT__PROOF = eINSTANCE.getAttempt_Proof();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.ProofImpl <em>Proof</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.ProofImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProof()
		 * @generated
		 */
		EClass PROOF = eINSTANCE.getProof();

		/**
		 * The meta object literal for the '<em><b>Goals</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF__GOALS = eINSTANCE.getProof_Goals();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROOF__LABEL = eINSTANCE.getProof_Label();

		/**
		 * The meta object literal for the '<em><b>Attempts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF__ATTEMPTS = eINSTANCE.getProof_Attempts();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.PropertyType <em>Property Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.PropertyType
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getPropertyType()
		 * @generated
		 */
		EEnum PROPERTY_TYPE = eINSTANCE.getPropertyType();

	}

} //ProofProcessPackage
