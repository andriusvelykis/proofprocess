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
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.AttemptImpl <em>Attempt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.AttemptImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getAttempt()
	 * @generated
	 */
	int ATTEMPT = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Intent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT__INTENT = 1;

	/**
	 * The number of structural features of the '<em>Attempt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.IntentImpl <em>Intent</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.IntentImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getIntent()
	 * @generated
	 */
	int INTENT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Intent</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofObjectImpl <em>Proof Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofObjectImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofObject()
	 * @generated
	 */
	int PROOF_OBJECT = 2;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_OBJECT__PROPERTIES = 0;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_OBJECT__CONTENT = 1;

	/**
	 * The number of structural features of the '<em>Proof Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_OBJECT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.ProofPropertyImpl <em>Proof Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.ProofPropertyImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofProperty()
	 * @generated
	 */
	int PROOF_PROPERTY = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_PROPERTY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_PROPERTY__DESCRIPTION = 1;

	/**
	 * The number of structural features of the '<em>Proof Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_PROPERTY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.ProofReference <em>Proof Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.ProofReference
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofReference()
	 * @generated
	 */
	int PROOF_REFERENCE = 4;

	/**
	 * The number of structural features of the '<em>Proof Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROOF_REFERENCE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.AttemptSetImpl <em>Attempt Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.AttemptSetImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getAttemptSet()
	 * @generated
	 */
	int ATTEMPT_SET = 5;

	/**
	 * The feature id for the '<em><b>Attempts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_SET__ATTEMPTS = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_SET__LABEL = 1;

	/**
	 * The number of structural features of the '<em>Attempt Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_SET_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.AttemptGroupImpl <em>Attempt Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.AttemptGroupImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getAttemptGroup()
	 * @generated
	 */
	int ATTEMPT_GROUP = 6;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_GROUP__DESCRIPTION = ATTEMPT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Intent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_GROUP__INTENT = ATTEMPT__INTENT;

	/**
	 * The feature id for the '<em><b>Composition Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_GROUP__COMPOSITION_TYPE = ATTEMPT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attempts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_GROUP__ATTEMPTS = ATTEMPT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Contained</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_GROUP__CONTAINED = ATTEMPT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Attempt Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_GROUP_FEATURE_COUNT = ATTEMPT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.impl.AttemptEntryImpl <em>Attempt Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.impl.AttemptEntryImpl
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getAttemptEntry()
	 * @generated
	 */
	int ATTEMPT_ENTRY = 7;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_ENTRY__DESCRIPTION = ATTEMPT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Intent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_ENTRY__INTENT = ATTEMPT__INTENT;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_ENTRY__CONTENT = ATTEMPT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Inputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_ENTRY__INPUTS = ATTEMPT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_ENTRY__OUTPUTS = ATTEMPT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Attempt Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTEMPT_ENTRY_FEATURE_COUNT = ATTEMPT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.CompositionType <em>Composition Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.CompositionType
	 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getCompositionType()
	 * @generated
	 */
	int COMPOSITION_TYPE = 8;


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
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.Attempt#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.ai4fm.proofprocess.Attempt#getDescription()
	 * @see #getAttempt()
	 * @generated
	 */
	EAttribute getAttempt_Description();

	/**
	 * Returns the meta object for the reference '{@link org.ai4fm.proofprocess.Attempt#getIntent <em>Intent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Intent</em>'.
	 * @see org.ai4fm.proofprocess.Attempt#getIntent()
	 * @see #getAttempt()
	 * @generated
	 */
	EReference getAttempt_Intent();

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
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.ProofObject <em>Proof Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Object</em>'.
	 * @see org.ai4fm.proofprocess.ProofObject
	 * @generated
	 */
	EClass getProofObject();

	/**
	 * Returns the meta object for the reference list '{@link org.ai4fm.proofprocess.ProofObject#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Properties</em>'.
	 * @see org.ai4fm.proofprocess.ProofObject#getProperties()
	 * @see #getProofObject()
	 * @generated
	 */
	EReference getProofObject_Properties();

	/**
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.ProofObject#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Content</em>'.
	 * @see org.ai4fm.proofprocess.ProofObject#getContent()
	 * @see #getProofObject()
	 * @generated
	 */
	EReference getProofObject_Content();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.ProofProperty <em>Proof Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Property</em>'.
	 * @see org.ai4fm.proofprocess.ProofProperty
	 * @generated
	 */
	EClass getProofProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.ProofProperty#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.ai4fm.proofprocess.ProofProperty#getName()
	 * @see #getProofProperty()
	 * @generated
	 */
	EAttribute getProofProperty_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.ProofProperty#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.ai4fm.proofprocess.ProofProperty#getDescription()
	 * @see #getProofProperty()
	 * @generated
	 */
	EAttribute getProofProperty_Description();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.ProofReference <em>Proof Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Proof Reference</em>'.
	 * @see org.ai4fm.proofprocess.ProofReference
	 * @generated
	 */
	EClass getProofReference();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.AttemptSet <em>Attempt Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attempt Set</em>'.
	 * @see org.ai4fm.proofprocess.AttemptSet
	 * @generated
	 */
	EClass getAttemptSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.AttemptSet#getAttempts <em>Attempts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attempts</em>'.
	 * @see org.ai4fm.proofprocess.AttemptSet#getAttempts()
	 * @see #getAttemptSet()
	 * @generated
	 */
	EReference getAttemptSet_Attempts();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.AttemptSet#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.ai4fm.proofprocess.AttemptSet#getLabel()
	 * @see #getAttemptSet()
	 * @generated
	 */
	EAttribute getAttemptSet_Label();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.AttemptGroup <em>Attempt Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attempt Group</em>'.
	 * @see org.ai4fm.proofprocess.AttemptGroup
	 * @generated
	 */
	EClass getAttemptGroup();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.AttemptGroup#getCompositionType <em>Composition Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Composition Type</em>'.
	 * @see org.ai4fm.proofprocess.AttemptGroup#getCompositionType()
	 * @see #getAttemptGroup()
	 * @generated
	 */
	EAttribute getAttemptGroup_CompositionType();

	/**
	 * Returns the meta object for the reference list '{@link org.ai4fm.proofprocess.AttemptGroup#getAttempts <em>Attempts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Attempts</em>'.
	 * @see org.ai4fm.proofprocess.AttemptGroup#getAttempts()
	 * @see #getAttemptGroup()
	 * @generated
	 */
	EReference getAttemptGroup_Attempts();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.AttemptGroup#getContained <em>Contained</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contained</em>'.
	 * @see org.ai4fm.proofprocess.AttemptGroup#getContained()
	 * @see #getAttemptGroup()
	 * @generated
	 */
	EReference getAttemptGroup_Contained();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.AttemptEntry <em>Attempt Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attempt Entry</em>'.
	 * @see org.ai4fm.proofprocess.AttemptEntry
	 * @generated
	 */
	EClass getAttemptEntry();

	/**
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.AttemptEntry#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Content</em>'.
	 * @see org.ai4fm.proofprocess.AttemptEntry#getContent()
	 * @see #getAttemptEntry()
	 * @generated
	 */
	EReference getAttemptEntry_Content();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.AttemptEntry#getInputs <em>Inputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inputs</em>'.
	 * @see org.ai4fm.proofprocess.AttemptEntry#getInputs()
	 * @see #getAttemptEntry()
	 * @generated
	 */
	EReference getAttemptEntry_Inputs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.AttemptEntry#getOutputs <em>Outputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outputs</em>'.
	 * @see org.ai4fm.proofprocess.AttemptEntry#getOutputs()
	 * @see #getAttemptEntry()
	 * @generated
	 */
	EReference getAttemptEntry_Outputs();

	/**
	 * Returns the meta object for enum '{@link org.ai4fm.proofprocess.CompositionType <em>Composition Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Composition Type</em>'.
	 * @see org.ai4fm.proofprocess.CompositionType
	 * @generated
	 */
	EEnum getCompositionType();

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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.AttemptImpl <em>Attempt</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.AttemptImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getAttempt()
		 * @generated
		 */
		EClass ATTEMPT = eINSTANCE.getAttempt();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTEMPT__DESCRIPTION = eINSTANCE.getAttempt_Description();

		/**
		 * The meta object literal for the '<em><b>Intent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTEMPT__INTENT = eINSTANCE.getAttempt_Intent();

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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.ProofObjectImpl <em>Proof Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.ProofObjectImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofObject()
		 * @generated
		 */
		EClass PROOF_OBJECT = eINSTANCE.getProofObject();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_OBJECT__PROPERTIES = eINSTANCE.getProofObject_Properties();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROOF_OBJECT__CONTENT = eINSTANCE.getProofObject_Content();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.ProofPropertyImpl <em>Proof Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.ProofPropertyImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofProperty()
		 * @generated
		 */
		EClass PROOF_PROPERTY = eINSTANCE.getProofProperty();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROOF_PROPERTY__NAME = eINSTANCE.getProofProperty_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROOF_PROPERTY__DESCRIPTION = eINSTANCE.getProofProperty_Description();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.ProofReference <em>Proof Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.ProofReference
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getProofReference()
		 * @generated
		 */
		EClass PROOF_REFERENCE = eINSTANCE.getProofReference();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.AttemptSetImpl <em>Attempt Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.AttemptSetImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getAttemptSet()
		 * @generated
		 */
		EClass ATTEMPT_SET = eINSTANCE.getAttemptSet();

		/**
		 * The meta object literal for the '<em><b>Attempts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTEMPT_SET__ATTEMPTS = eINSTANCE.getAttemptSet_Attempts();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTEMPT_SET__LABEL = eINSTANCE.getAttemptSet_Label();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.AttemptGroupImpl <em>Attempt Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.AttemptGroupImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getAttemptGroup()
		 * @generated
		 */
		EClass ATTEMPT_GROUP = eINSTANCE.getAttemptGroup();

		/**
		 * The meta object literal for the '<em><b>Composition Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTEMPT_GROUP__COMPOSITION_TYPE = eINSTANCE.getAttemptGroup_CompositionType();

		/**
		 * The meta object literal for the '<em><b>Attempts</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTEMPT_GROUP__ATTEMPTS = eINSTANCE.getAttemptGroup_Attempts();

		/**
		 * The meta object literal for the '<em><b>Contained</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTEMPT_GROUP__CONTAINED = eINSTANCE.getAttemptGroup_Contained();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.impl.AttemptEntryImpl <em>Attempt Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.impl.AttemptEntryImpl
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getAttemptEntry()
		 * @generated
		 */
		EClass ATTEMPT_ENTRY = eINSTANCE.getAttemptEntry();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTEMPT_ENTRY__CONTENT = eINSTANCE.getAttemptEntry_Content();

		/**
		 * The meta object literal for the '<em><b>Inputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTEMPT_ENTRY__INPUTS = eINSTANCE.getAttemptEntry_Inputs();

		/**
		 * The meta object literal for the '<em><b>Outputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTEMPT_ENTRY__OUTPUTS = eINSTANCE.getAttemptEntry_Outputs();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.CompositionType <em>Composition Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.CompositionType
		 * @see org.ai4fm.proofprocess.impl.ProofProcessPackageImpl#getCompositionType()
		 * @generated
		 */
		EEnum COMPOSITION_TYPE = eINSTANCE.getCompositionType();

	}

} //ProofProcessPackage
