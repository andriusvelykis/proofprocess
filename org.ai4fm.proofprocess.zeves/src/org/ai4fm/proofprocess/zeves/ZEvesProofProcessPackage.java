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
	String eNS_URI = "http://org/ai4fm/proofprocess/zeves/v1.0.0";

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
	 * The meta object id for the '{@link org.ai4fm.proofprocess.zeves.impl.ZEvesTraceImpl <em>ZEves Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesTraceImpl
	 * @see org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessPackageImpl#getZEvesTrace()
	 * @generated
	 */
	int ZEVES_TRACE = 0;

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

	}

} //ZEvesProofProcessPackage
