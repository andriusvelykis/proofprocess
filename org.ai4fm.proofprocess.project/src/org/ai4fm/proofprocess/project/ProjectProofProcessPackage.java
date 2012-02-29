/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.project;

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
 * @see org.ai4fm.proofprocess.project.ProjectProofProcessFactory
 * @model kind="package"
 * @generated
 */
public interface ProjectProofProcessPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "project";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org/ai4fm/proofprocess/project/v1.0.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "project";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProjectProofProcessPackage eINSTANCE = org.ai4fm.proofprocess.project.impl.ProjectProofProcessPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.project.impl.ProjectImpl <em>Project</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.project.impl.ProjectImpl
	 * @see org.ai4fm.proofprocess.project.impl.ProjectProofProcessPackageImpl#getProject()
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
	 * The feature id for the '<em><b>Proofs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__PROOFS = 1;

	/**
	 * The feature id for the '<em><b>Activities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__ACTIVITIES = 2;

	/**
	 * The feature id for the '<em><b>Intents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__INTENTS = 3;

	/**
	 * The number of structural features of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.project.impl.ActivityImpl <em>Activity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.project.impl.ActivityImpl
	 * @see org.ai4fm.proofprocess.project.impl.ProjectProofProcessPackageImpl#getActivity()
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
	 * The meta object id for the '{@link org.ai4fm.proofprocess.project.impl.ProofActivityImpl <em>Proof Activity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.project.impl.ProofActivityImpl
	 * @see org.ai4fm.proofprocess.project.impl.ProjectProofProcessPackageImpl#getProofActivity()
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
	 * The meta object id for the '{@link org.ai4fm.proofprocess.project.impl.PositionImpl <em>Position</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.project.impl.PositionImpl
	 * @see org.ai4fm.proofprocess.project.impl.ProjectProofProcessPackageImpl#getPosition()
	 * @generated
	 */
	int POSITION = 3;

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
	 * The meta object id for the '{@link org.ai4fm.proofprocess.project.impl.TextLocImpl <em>Text Loc</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.project.impl.TextLocImpl
	 * @see org.ai4fm.proofprocess.project.impl.ProjectProofProcessPackageImpl#getTextLoc()
	 * @generated
	 */
	int TEXT_LOC = 4;

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
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.project.Project <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project</em>'.
	 * @see org.ai4fm.proofprocess.project.Project
	 * @generated
	 */
	EClass getProject();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.project.Project#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.ai4fm.proofprocess.project.Project#getLabel()
	 * @see #getProject()
	 * @generated
	 */
	EAttribute getProject_Label();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.project.Project#getProofs <em>Proofs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Proofs</em>'.
	 * @see org.ai4fm.proofprocess.project.Project#getProofs()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Proofs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.project.Project#getActivities <em>Activities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Activities</em>'.
	 * @see org.ai4fm.proofprocess.project.Project#getActivities()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Activities();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.project.Project#getIntents <em>Intents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Intents</em>'.
	 * @see org.ai4fm.proofprocess.project.Project#getIntents()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Intents();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.project.Activity <em>Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Activity</em>'.
	 * @see org.ai4fm.proofprocess.project.Activity
	 * @generated
	 */
	EClass getActivity();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.project.Activity#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.ai4fm.proofprocess.project.Activity#getDescription()
	 * @see #getActivity()
	 * @generated
	 */
	EAttribute getActivity_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.project.Activity#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see org.ai4fm.proofprocess.project.Activity#getTimestamp()
	 * @see #getActivity()
	 * @generated
	 */
	EAttribute getActivity_Timestamp();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.project.ProofActivity <em>Proof Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Activity</em>'.
	 * @see org.ai4fm.proofprocess.project.ProofActivity
	 * @generated
	 */
	EClass getProofActivity();

	/**
	 * Returns the meta object for the reference '{@link org.ai4fm.proofprocess.project.ProofActivity#getProofRef <em>Proof Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Proof Ref</em>'.
	 * @see org.ai4fm.proofprocess.project.ProofActivity#getProofRef()
	 * @see #getProofActivity()
	 * @generated
	 */
	EReference getProofActivity_ProofRef();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.project.Position <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Position</em>'.
	 * @see org.ai4fm.proofprocess.project.Position
	 * @generated
	 */
	EClass getPosition();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.project.Position#getOffset <em>Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Offset</em>'.
	 * @see org.ai4fm.proofprocess.project.Position#getOffset()
	 * @see #getPosition()
	 * @generated
	 */
	EAttribute getPosition_Offset();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.project.Position#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.ai4fm.proofprocess.project.Position#getLength()
	 * @see #getPosition()
	 * @generated
	 */
	EAttribute getPosition_Length();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.project.TextLoc <em>Text Loc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Loc</em>'.
	 * @see org.ai4fm.proofprocess.project.TextLoc
	 * @generated
	 */
	EClass getTextLoc();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.project.TextLoc#getFilePath <em>File Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Path</em>'.
	 * @see org.ai4fm.proofprocess.project.TextLoc#getFilePath()
	 * @see #getTextLoc()
	 * @generated
	 */
	EAttribute getTextLoc_FilePath();

	/**
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.project.TextLoc#getPosition <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Position</em>'.
	 * @see org.ai4fm.proofprocess.project.TextLoc#getPosition()
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
	ProjectProofProcessFactory getProjectProofProcessFactory();

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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.project.impl.ProjectImpl <em>Project</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.project.impl.ProjectImpl
		 * @see org.ai4fm.proofprocess.project.impl.ProjectProofProcessPackageImpl#getProject()
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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.project.impl.ActivityImpl <em>Activity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.project.impl.ActivityImpl
		 * @see org.ai4fm.proofprocess.project.impl.ProjectProofProcessPackageImpl#getActivity()
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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.project.impl.ProofActivityImpl <em>Proof Activity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.project.impl.ProofActivityImpl
		 * @see org.ai4fm.proofprocess.project.impl.ProjectProofProcessPackageImpl#getProofActivity()
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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.project.impl.PositionImpl <em>Position</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.project.impl.PositionImpl
		 * @see org.ai4fm.proofprocess.project.impl.ProjectProofProcessPackageImpl#getPosition()
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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.project.impl.TextLocImpl <em>Text Loc</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.project.impl.TextLocImpl
		 * @see org.ai4fm.proofprocess.project.impl.ProjectProofProcessPackageImpl#getTextLoc()
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

} //ProjectProofProcessPackage
