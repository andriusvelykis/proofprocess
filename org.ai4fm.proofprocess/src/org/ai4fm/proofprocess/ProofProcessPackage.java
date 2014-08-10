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
	String eNS_URI = "http://org/ai4fm/proofprocess/v1.0.0.14";

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
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.DisplayTermImpl <em>Display Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.DisplayTermImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getDisplayTerm()
	 * @generated
	 */
	int DISPLAY_TERM = 2;

	/**
	 * The feature id for the '<em><b>Display</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISPLAY_TERM__DISPLAY = TERM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Display Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISPLAY_TERM_FEATURE_COUNT = TERM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.StringTermImpl <em>String Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.StringTermImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getStringTerm()
	 * @generated
	 */
	int STRING_TERM = 3;

	/**
	 * The feature id for the '<em><b>Display</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TERM__DISPLAY = DISPLAY_TERM__DISPLAY;

	/**
	 * The number of structural features of the '<em>String Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TERM_FEATURE_COUNT = DISPLAY_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.Loc <em>Loc</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.Loc
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getLoc()
	 * @generated
	 */
	int LOC = 4;

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
	int TRACE = 5;

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
	int PROOF_STEP = 6;

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
	int PROOF_INFO = 7;

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
	 * The feature id for the '<em><b>In Features</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_INFO__IN_FEATURES = 2;

	/**
	 * The feature id for the '<em><b>Out Features</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_INFO__OUT_FEATURES = 3;

	/**
	 * The number of structural features of the '<em>Proof Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_INFO_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofFeatureDefImpl <em>Proof Feature Def</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofFeatureDefImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofFeatureDef()
	 * @generated
	 */
	int PROOF_FEATURE_DEF = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_FEATURE_DEF__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_FEATURE_DEF__DESCRIPTION = 1;

	/**
	 * The number of structural features of the '<em>Proof Feature Def</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_FEATURE_DEF_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofFeatureImpl <em>Proof Feature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofFeatureImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofFeature()
	 * @generated
	 */
	int PROOF_FEATURE = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_FEATURE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_FEATURE__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Params</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_FEATURE__PARAMS = 2;

	/**
	 * The feature id for the '<em><b>Misc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_FEATURE__MISC = 3;

	/**
	 * The number of structural features of the '<em>Proof Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_FEATURE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofElemImpl <em>Proof Elem</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofElemImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofElem()
	 * @generated
	 */
	int PROOF_ELEM = 10;

	/**
	 * The feature id for the '<em><b>Info</b></em>' containment reference.
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
	int PROOF_ENTRY = 11;

	/**
	 * The feature id for the '<em><b>Info</b></em>' containment reference.
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
	int PROOF_SEQ = 12;

	/**
	 * The feature id for the '<em><b>Info</b></em>' containment reference.
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
	int PROOF_PARALLEL = 13;

	/**
	 * The feature id for the '<em><b>Info</b></em>' containment reference.
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
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofIdImpl <em>Proof Id</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofIdImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofId()
	 * @generated
	 */
	int PROOF_ID = 14;

	/**
	 * The feature id for the '<em><b>Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_ID__INFO = PROOF_ELEM__INFO;

	/**
	 * The feature id for the '<em><b>Entry Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_ID__ENTRY_REF = PROOF_ELEM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Proof Id</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_ID_FEATURE_COUNT = PROOF_ELEM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.AttemptImpl <em>Attempt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.AttemptImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getAttempt()
	 * @generated
	 */
	int ATTEMPT = 15;

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
	int PROOF = 16;

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
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofStoreImpl <em>Proof Store</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofStoreImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofStore()
	 * @generated
	 */
	int PROOF_STORE = 17;

	/**
	 * The feature id for the '<em><b>Proofs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_STORE__PROOFS = 0;

	/**
	 * The feature id for the '<em><b>Intents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_STORE__INTENTS = 1;

	/**
	 * The feature id for the '<em><b>Features</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_STORE__FEATURES = 2;

	/**
	 * The number of structural features of the '<em>Proof Store</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_STORE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.ProofFeatureType <em>Proof Feature Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.ProofFeatureType
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofFeatureType()
	 * @generated
	 */
	int PROOF_FEATURE_TYPE = 18;

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
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.DisplayTerm <em>Display Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Display Term</em>'.
	 * @see org.ai4fm.proofprocess.DisplayTerm
	 * @generated
	 */
	EClass getDisplayTerm();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.DisplayTerm#getDisplay <em>Display</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display</em>'.
	 * @see org.ai4fm.proofprocess.DisplayTerm#getDisplay()
	 * @see #getDisplayTerm()
	 * @generated
	 */
	EAttribute getDisplayTerm_Display();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.StringTerm <em>String Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Term</em>'.
	 * @see org.ai4fm.proofprocess.StringTerm
	 * @generated
	 */
	EClass getStringTerm();

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
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.ProofInfo#getInFeatures <em>In Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>In Features</em>'.
	 * @see org.ai4fm.proofprocess.ProofInfo#getInFeatures()
	 * @see #getProofInfo()
	 * @generated
	 */
	EReference getProofInfo_InFeatures();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.ProofInfo#getOutFeatures <em>Out Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Out Features</em>'.
	 * @see org.ai4fm.proofprocess.ProofInfo#getOutFeatures()
	 * @see #getProofInfo()
	 * @generated
	 */
	EReference getProofInfo_OutFeatures();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.ProofFeatureDef <em>Proof Feature Def</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Feature Def</em>'.
	 * @see org.ai4fm.proofprocess.ProofFeatureDef
	 * @generated
	 */
	EClass getProofFeatureDef();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.ProofFeatureDef#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.ai4fm.proofprocess.ProofFeatureDef#getName()
	 * @see #getProofFeatureDef()
	 * @generated
	 */
	EAttribute getProofFeatureDef_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.ProofFeatureDef#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.ai4fm.proofprocess.ProofFeatureDef#getDescription()
	 * @see #getProofFeatureDef()
	 * @generated
	 */
	EAttribute getProofFeatureDef_Description();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.ProofFeature <em>Proof Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Feature</em>'.
	 * @see org.ai4fm.proofprocess.ProofFeature
	 * @generated
	 */
	EClass getProofFeature();

	/**
	 * Returns the meta object for the reference '{@link org.ai4fm.proofprocess.ProofFeature#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Name</em>'.
	 * @see org.ai4fm.proofprocess.ProofFeature#getName()
	 * @see #getProofFeature()
	 * @generated
	 */
	EReference getProofFeature_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.ProofFeature#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.ai4fm.proofprocess.ProofFeature#getType()
	 * @see #getProofFeature()
	 * @generated
	 */
	EAttribute getProofFeature_Type();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.ProofFeature#getParams <em>Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Params</em>'.
	 * @see org.ai4fm.proofprocess.ProofFeature#getParams()
	 * @see #getProofFeature()
	 * @generated
	 */
	EReference getProofFeature_Params();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.ProofFeature#getMisc <em>Misc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Misc</em>'.
	 * @see org.ai4fm.proofprocess.ProofFeature#getMisc()
	 * @see #getProofFeature()
	 * @generated
	 */
	EAttribute getProofFeature_Misc();

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
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.ProofElem#getInfo <em>Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Info</em>'.
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
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.ProofId <em>Proof Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Id</em>'.
	 * @see org.ai4fm.proofprocess.ProofId
	 * @generated
	 */
	EClass getProofId();

	/**
	 * Returns the meta object for the reference '{@link org.ai4fm.proofprocess.ProofId#getEntryRef <em>Entry Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entry Ref</em>'.
	 * @see org.ai4fm.proofprocess.ProofId#getEntryRef()
	 * @see #getProofId()
	 * @generated
	 */
	EReference getProofId_EntryRef();

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
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.ProofStore <em>Proof Store</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Store</em>'.
	 * @see org.ai4fm.proofprocess.ProofStore
	 * @generated
	 */
	EClass getProofStore();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.ProofStore#getProofs <em>Proofs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Proofs</em>'.
	 * @see org.ai4fm.proofprocess.ProofStore#getProofs()
	 * @see #getProofStore()
	 * @generated
	 */
	EReference getProofStore_Proofs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.ProofStore#getIntents <em>Intents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Intents</em>'.
	 * @see org.ai4fm.proofprocess.ProofStore#getIntents()
	 * @see #getProofStore()
	 * @generated
	 */
	EReference getProofStore_Intents();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.ProofStore#getFeatures <em>Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Features</em>'.
	 * @see org.ai4fm.proofprocess.ProofStore#getFeatures()
	 * @see #getProofStore()
	 * @generated
	 */
	EReference getProofStore_Features();

	/**
	 * Returns the meta object for enum '{@link org.ai4fm.proofprocess.ProofFeatureType <em>Proof Feature Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Proof Feature Type</em>'.
	 * @see org.ai4fm.proofprocess.ProofFeatureType
	 * @generated
	 */
	EEnum getProofFeatureType();

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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.DisplayTermImpl <em>Display Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.DisplayTermImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getDisplayTerm()
		 * @generated
		 */
		EClass DISPLAY_TERM = eINSTANCE.getDisplayTerm();

		/**
		 * The meta object literal for the '<em><b>Display</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DISPLAY_TERM__DISPLAY = eINSTANCE.getDisplayTerm_Display();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.StringTermImpl <em>String Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.StringTermImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getStringTerm()
		 * @generated
		 */
		EClass STRING_TERM = eINSTANCE.getStringTerm();

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
		 * The meta object literal for the '<em><b>In Features</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_INFO__IN_FEATURES = eINSTANCE.getProofInfo_InFeatures();

		/**
		 * The meta object literal for the '<em><b>Out Features</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_INFO__OUT_FEATURES = eINSTANCE.getProofInfo_OutFeatures();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.ProofFeatureDefImpl <em>Proof Feature Def</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.ProofFeatureDefImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofFeatureDef()
		 * @generated
		 */
		EClass PROOF_FEATURE_DEF = eINSTANCE.getProofFeatureDef();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROOF_FEATURE_DEF__NAME = eINSTANCE.getProofFeatureDef_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROOF_FEATURE_DEF__DESCRIPTION = eINSTANCE.getProofFeatureDef_Description();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.ProofFeatureImpl <em>Proof Feature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.ProofFeatureImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofFeature()
		 * @generated
		 */
		EClass PROOF_FEATURE = eINSTANCE.getProofFeature();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_FEATURE__NAME = eINSTANCE.getProofFeature_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROOF_FEATURE__TYPE = eINSTANCE.getProofFeature_Type();

		/**
		 * The meta object literal for the '<em><b>Params</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_FEATURE__PARAMS = eINSTANCE.getProofFeature_Params();

		/**
		 * The meta object literal for the '<em><b>Misc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROOF_FEATURE__MISC = eINSTANCE.getProofFeature_Misc();

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
		 * The meta object literal for the '<em><b>Info</b></em>' containment reference feature.
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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.ProofIdImpl <em>Proof Id</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.ProofIdImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofId()
		 * @generated
		 */
		EClass PROOF_ID = eINSTANCE.getProofId();

		/**
		 * The meta object literal for the '<em><b>Entry Ref</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_ID__ENTRY_REF = eINSTANCE.getProofId_EntryRef();

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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.ProofStoreImpl <em>Proof Store</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.ProofStoreImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofStore()
		 * @generated
		 */
		EClass PROOF_STORE = eINSTANCE.getProofStore();

		/**
		 * The meta object literal for the '<em><b>Proofs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_STORE__PROOFS = eINSTANCE.getProofStore_Proofs();

		/**
		 * The meta object literal for the '<em><b>Intents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_STORE__INTENTS = eINSTANCE.getProofStore_Intents();

		/**
		 * The meta object literal for the '<em><b>Features</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_STORE__FEATURES = eINSTANCE.getProofStore_Features();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.ProofFeatureType <em>Proof Feature Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.ProofFeatureType
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofFeatureType()
		 * @generated
		 */
		EEnum PROOF_FEATURE_TYPE = eINSTANCE.getProofFeatureType();

	}

} //ProofProcessPackage
