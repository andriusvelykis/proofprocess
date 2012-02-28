/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves;

import org.ai4fm.proofprocess.ProofProcessPackage;

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
 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessFactory
 * @model kind="package"
 * @generated
 */
public interface ZEvesProofProcessPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "zeves";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org/ai4fm/proofprocess/zeves/v0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "zeves";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ZEvesProofProcessPackage eINSTANCE = org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.zeves.impl.ProjectImpl <em>Project</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.zeves.impl.ProjectImpl
	 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessPackageImpl#getProject()
	 * @generated
	 */
	int PROJECT = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__LABEL = 0;

	/**
	 * The feature id for the '<em><b>ZEves Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__ZEVES_VERSION = 1;

	/**
	 * The feature id for the '<em><b>Proofs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__PROOFS = 2;

	/**
	 * The feature id for the '<em><b>Activities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__ACTIVITIES = 3;

	/**
	 * The feature id for the '<em><b>Intents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__INTENTS = 4;

	/**
	 * The number of structural features of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.zeves.impl.ActivityImpl <em>Activity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.zeves.impl.ActivityImpl
	 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessPackageImpl#getActivity()
	 * @generated
	 */
	int ACTIVITY = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY__TIMESTAMP = 1;

	/**
	 * The number of structural features of the '<em>Activity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.zeves.impl.ProofActivityImpl <em>Proof Activity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.zeves.impl.ProofActivityImpl
	 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessPackageImpl#getProofActivity()
	 * @generated
	 */
	int PROOF_ACTIVITY = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_ACTIVITY__DESCRIPTION = ACTIVITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_ACTIVITY__TIMESTAMP = ACTIVITY__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Proof Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_ACTIVITY__PROOF_REF = ACTIVITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Proof Activity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_ACTIVITY_FEATURE_COUNT = ACTIVITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.zeves.impl.ZEvesTraceImpl <em>ZEves Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesTraceImpl
	 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessPackageImpl#getZEvesTrace()
	 * @generated
	 */
	int ZEVES_TRACE = 3;

	/**
	 * The feature id for the '<em><b>Markup</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZEVES_TRACE__MARKUP = ProofProcessPackage.TRACE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Goal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZEVES_TRACE__GOAL = ProofProcessPackage.TRACE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Used Lemmas</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZEVES_TRACE__USED_LEMMAS = ProofProcessPackage.TRACE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZEVES_TRACE__TEXT = ProofProcessPackage.TRACE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Case</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZEVES_TRACE__CASE = ProofProcessPackage.TRACE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>ZEves Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ZEVES_TRACE_FEATURE_COUNT = ProofProcessPackage.TRACE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.zeves.impl.PositionImpl <em>Position</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.zeves.impl.PositionImpl
	 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessPackageImpl#getPosition()
	 * @generated
	 */
	int POSITION = 4;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION__OFFSET = 0;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION__LENGTH = 1;

	/**
	 * The number of structural features of the '<em>Position</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.zeves.impl.TextLocImpl <em>Text Loc</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.zeves.impl.TextLocImpl
	 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessPackageImpl#getTextLoc()
	 * @generated
	 */
	int TEXT_LOC = 5;

	/**
	 * The feature id for the '<em><b>File Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LOC__FILE_PATH = ProofProcessPackage.LOC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LOC__POSITION = ProofProcessPackage.LOC_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Text Loc</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LOC_FEATURE_COUNT = ProofProcessPackage.LOC_FEATURE_COUNT + 2;


	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.zeves.Project <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project</em>'.
	 * @see org.ai4fm.proofprocess.zeves.Project
	 * @generated
	 */
	EClass getProject();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.zeves.Project#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.ai4fm.proofprocess.zeves.Project#getLabel()
	 * @see #getProject()
	 * @generated
	 */
	EAttribute getProject_Label();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.zeves.Project#getZEvesVersion <em>ZEves Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ZEves Version</em>'.
	 * @see org.ai4fm.proofprocess.zeves.Project#getZEvesVersion()
	 * @see #getProject()
	 * @generated
	 */
	EAttribute getProject_ZEvesVersion();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.zeves.Project#getProofs <em>Proofs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Proofs</em>'.
	 * @see org.ai4fm.proofprocess.zeves.Project#getProofs()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Proofs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.zeves.Project#getActivities <em>Activities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Activities</em>'.
	 * @see org.ai4fm.proofprocess.zeves.Project#getActivities()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Activities();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.zeves.Project#getIntents <em>Intents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Intents</em>'.
	 * @see org.ai4fm.proofprocess.zeves.Project#getIntents()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Intents();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.zeves.Activity <em>Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Activity</em>'.
	 * @see org.ai4fm.proofprocess.zeves.Activity
	 * @generated
	 */
	EClass getActivity();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.zeves.Activity#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.ai4fm.proofprocess.zeves.Activity#getDescription()
	 * @see #getActivity()
	 * @generated
	 */
	EAttribute getActivity_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.zeves.Activity#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see org.ai4fm.proofprocess.zeves.Activity#getTimestamp()
	 * @see #getActivity()
	 * @generated
	 */
	EAttribute getActivity_Timestamp();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.zeves.ProofActivity <em>Proof Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Activity</em>'.
	 * @see org.ai4fm.proofprocess.zeves.ProofActivity
	 * @generated
	 */
	EClass getProofActivity();

	/**
	 * Returns the meta object for the reference '{@link org.ai4fm.proofprocess.zeves.ProofActivity#getProofRef <em>Proof Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Proof Ref</em>'.
	 * @see org.ai4fm.proofprocess.zeves.ProofActivity#getProofRef()
	 * @see #getProofActivity()
	 * @generated
	 */
	EReference getProofActivity_ProofRef();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.zeves.ZEvesTrace <em>ZEves Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ZEves Trace</em>'.
	 * @see org.ai4fm.proofprocess.zeves.ZEvesTrace
	 * @generated
	 */
	EClass getZEvesTrace();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.zeves.ZEvesTrace#getMarkup <em>Markup</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Markup</em>'.
	 * @see org.ai4fm.proofprocess.zeves.ZEvesTrace#getMarkup()
	 * @see #getZEvesTrace()
	 * @generated
	 */
	EAttribute getZEvesTrace_Markup();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.zeves.ZEvesTrace#getGoal <em>Goal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Goal</em>'.
	 * @see org.ai4fm.proofprocess.zeves.ZEvesTrace#getGoal()
	 * @see #getZEvesTrace()
	 * @generated
	 */
	EAttribute getZEvesTrace_Goal();

	/**
	 * Returns the meta object for the attribute list '{@link org.ai4fm.proofprocess.zeves.ZEvesTrace#getUsedLemmas <em>Used Lemmas</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Used Lemmas</em>'.
	 * @see org.ai4fm.proofprocess.zeves.ZEvesTrace#getUsedLemmas()
	 * @see #getZEvesTrace()
	 * @generated
	 */
	EAttribute getZEvesTrace_UsedLemmas();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.zeves.ZEvesTrace#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.ai4fm.proofprocess.zeves.ZEvesTrace#getText()
	 * @see #getZEvesTrace()
	 * @generated
	 */
	EAttribute getZEvesTrace_Text();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.zeves.ZEvesTrace#getCase <em>Case</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Case</em>'.
	 * @see org.ai4fm.proofprocess.zeves.ZEvesTrace#getCase()
	 * @see #getZEvesTrace()
	 * @generated
	 */
	EAttribute getZEvesTrace_Case();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.zeves.Position <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Position</em>'.
	 * @see org.ai4fm.proofprocess.zeves.Position
	 * @generated
	 */
	EClass getPosition();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.zeves.Position#getOffset <em>Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Offset</em>'.
	 * @see org.ai4fm.proofprocess.zeves.Position#getOffset()
	 * @see #getPosition()
	 * @generated
	 */
	EAttribute getPosition_Offset();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.zeves.Position#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.ai4fm.proofprocess.zeves.Position#getLength()
	 * @see #getPosition()
	 * @generated
	 */
	EAttribute getPosition_Length();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.zeves.TextLoc <em>Text Loc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Loc</em>'.
	 * @see org.ai4fm.proofprocess.zeves.TextLoc
	 * @generated
	 */
	EClass getTextLoc();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.zeves.TextLoc#getFilePath <em>File Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Path</em>'.
	 * @see org.ai4fm.proofprocess.zeves.TextLoc#getFilePath()
	 * @see #getTextLoc()
	 * @generated
	 */
	EAttribute getTextLoc_FilePath();

	/**
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.zeves.TextLoc#getPosition <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Position</em>'.
	 * @see org.ai4fm.proofprocess.zeves.TextLoc#getPosition()
	 * @see #getTextLoc()
	 * @generated
	 */
	EReference getTextLoc_Position();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ZEvesProofProcessFactory getZEvesProofProcessFactory();

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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.zeves.impl.ProjectImpl <em>Project</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.zeves.impl.ProjectImpl
		 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessPackageImpl#getProject()
		 * @generated
		 */
		EClass PROJECT = eINSTANCE.getProject();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT__LABEL = eINSTANCE.getProject_Label();

		/**
		 * The meta object literal for the '<em><b>ZEves Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT__ZEVES_VERSION = eINSTANCE.getProject_ZEvesVersion();

		/**
		 * The meta object literal for the '<em><b>Proofs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__PROOFS = eINSTANCE.getProject_Proofs();

		/**
		 * The meta object literal for the '<em><b>Activities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__ACTIVITIES = eINSTANCE.getProject_Activities();

		/**
		 * The meta object literal for the '<em><b>Intents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__INTENTS = eINSTANCE.getProject_Intents();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.zeves.impl.ActivityImpl <em>Activity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.zeves.impl.ActivityImpl
		 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessPackageImpl#getActivity()
		 * @generated
		 */
		EClass ACTIVITY = eINSTANCE.getActivity();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTIVITY__DESCRIPTION = eINSTANCE.getActivity_Description();

		/**
		 * The meta object literal for the '<em><b>Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTIVITY__TIMESTAMP = eINSTANCE.getActivity_Timestamp();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.zeves.impl.ProofActivityImpl <em>Proof Activity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.zeves.impl.ProofActivityImpl
		 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessPackageImpl#getProofActivity()
		 * @generated
		 */
		EClass PROOF_ACTIVITY = eINSTANCE.getProofActivity();

		/**
		 * The meta object literal for the '<em><b>Proof Ref</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_ACTIVITY__PROOF_REF = eINSTANCE.getProofActivity_ProofRef();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.zeves.impl.ZEvesTraceImpl <em>ZEves Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesTraceImpl
		 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessPackageImpl#getZEvesTrace()
		 * @generated
		 */
		EClass ZEVES_TRACE = eINSTANCE.getZEvesTrace();

		/**
		 * The meta object literal for the '<em><b>Markup</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ZEVES_TRACE__MARKUP = eINSTANCE.getZEvesTrace_Markup();

		/**
		 * The meta object literal for the '<em><b>Goal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ZEVES_TRACE__GOAL = eINSTANCE.getZEvesTrace_Goal();

		/**
		 * The meta object literal for the '<em><b>Used Lemmas</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ZEVES_TRACE__USED_LEMMAS = eINSTANCE.getZEvesTrace_UsedLemmas();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ZEVES_TRACE__TEXT = eINSTANCE.getZEvesTrace_Text();

		/**
		 * The meta object literal for the '<em><b>Case</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ZEVES_TRACE__CASE = eINSTANCE.getZEvesTrace_Case();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.zeves.impl.PositionImpl <em>Position</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.zeves.impl.PositionImpl
		 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessPackageImpl#getPosition()
		 * @generated
		 */
		EClass POSITION = eINSTANCE.getPosition();

		/**
		 * The meta object literal for the '<em><b>Offset</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION__OFFSET = eINSTANCE.getPosition_Offset();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION__LENGTH = eINSTANCE.getPosition_Length();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.zeves.impl.TextLocImpl <em>Text Loc</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.zeves.impl.TextLocImpl
		 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessPackageImpl#getTextLoc()
		 * @generated
		 */
		EClass TEXT_LOC = eINSTANCE.getTextLoc();

		/**
		 * The meta object literal for the '<em><b>File Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_LOC__FILE_PATH = eINSTANCE.getTextLoc_FilePath();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEXT_LOC__POSITION = eINSTANCE.getTextLoc_Position();

	}

} //ZEvesProofProcessPackage
