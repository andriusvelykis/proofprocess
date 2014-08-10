/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.log;

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
 * @see org.ai4fm.proofprocess.log.ProofProcessLogFactory
 * @model kind="package"
 * @generated
 */
public interface ProofProcessLogPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "log";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org/ai4fm/proofprocess/log/v1.0.0.14";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "prooflog";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProofProcessLogPackage eINSTANCE = org.ai4fm.proofprocess.log.impl.ProofProcessLogPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.log.impl.ProofLogImpl <em>Proof Log</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.log.impl.ProofLogImpl
	 * @see org.ai4fm.proofprocess.log.impl.ProofProcessLogPackageImpl#getProofLog()
	 * @generated
	 */
	int PROOF_LOG = 0;

	/**
	 * The feature id for the '<em><b>Activities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_LOG__ACTIVITIES = 0;

	/**
	 * The number of structural features of the '<em>Proof Log</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_LOG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.log.impl.ActivityImpl <em>Activity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.log.impl.ActivityImpl
	 * @see org.ai4fm.proofprocess.log.impl.ProofProcessLogPackageImpl#getActivity()
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
	 * The meta object id for the '{@link org.ai4fm.proofprocess.log.impl.ProofActivityImpl <em>Proof Activity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.log.impl.ProofActivityImpl
	 * @see org.ai4fm.proofprocess.log.impl.ProofProcessLogPackageImpl#getProofActivity()
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
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.log.ProofLog <em>Proof Log</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Log</em>'.
	 * @see org.ai4fm.proofprocess.log.ProofLog
	 * @generated
	 */
	EClass getProofLog();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.log.ProofLog#getActivities <em>Activities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Activities</em>'.
	 * @see org.ai4fm.proofprocess.log.ProofLog#getActivities()
	 * @see #getProofLog()
	 * @generated
	 */
	EReference getProofLog_Activities();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.log.Activity <em>Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Activity</em>'.
	 * @see org.ai4fm.proofprocess.log.Activity
	 * @generated
	 */
	EClass getActivity();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.log.Activity#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.ai4fm.proofprocess.log.Activity#getDescription()
	 * @see #getActivity()
	 * @generated
	 */
	EAttribute getActivity_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.log.Activity#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see org.ai4fm.proofprocess.log.Activity#getTimestamp()
	 * @see #getActivity()
	 * @generated
	 */
	EAttribute getActivity_Timestamp();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.log.ProofActivity <em>Proof Activity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Activity</em>'.
	 * @see org.ai4fm.proofprocess.log.ProofActivity
	 * @generated
	 */
	EClass getProofActivity();

	/**
	 * Returns the meta object for the reference '{@link org.ai4fm.proofprocess.log.ProofActivity#getProofRef <em>Proof Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Proof Ref</em>'.
	 * @see org.ai4fm.proofprocess.log.ProofActivity#getProofRef()
	 * @see #getProofActivity()
	 * @generated
	 */
	EReference getProofActivity_ProofRef();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ProofProcessLogFactory getProofProcessLogFactory();

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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.log.impl.ProofLogImpl <em>Proof Log</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.log.impl.ProofLogImpl
		 * @see org.ai4fm.proofprocess.log.impl.ProofProcessLogPackageImpl#getProofLog()
		 * @generated
		 */
		EClass PROOF_LOG = eINSTANCE.getProofLog();

		/**
		 * The meta object literal for the '<em><b>Activities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_LOG__ACTIVITIES = eINSTANCE.getProofLog_Activities();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.log.impl.ActivityImpl <em>Activity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.log.impl.ActivityImpl
		 * @see org.ai4fm.proofprocess.log.impl.ProofProcessLogPackageImpl#getActivity()
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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.log.impl.ProofActivityImpl <em>Proof Activity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.log.impl.ProofActivityImpl
		 * @see org.ai4fm.proofprocess.log.impl.ProofProcessLogPackageImpl#getProofActivity()
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

	}

} //ProofProcessLogPackage
