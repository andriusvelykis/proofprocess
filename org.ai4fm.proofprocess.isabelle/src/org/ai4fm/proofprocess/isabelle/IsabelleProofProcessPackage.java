/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle;

import org.ai4fm.proofprocess.ProofProcessPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory
 * @model kind="package"
 * @generated
 */
public interface IsabelleProofProcessPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "isabelle";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org/ai4fm/proofprocess/isabelle/v1.0.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "isabelle";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IsabelleProofProcessPackage eINSTANCE = org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.isabelle.impl.DisplayTermImpl <em>Display Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.isabelle.impl.DisplayTermImpl
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getDisplayTerm()
	 * @generated
	 */
	int DISPLAY_TERM = 0;

	/**
	 * The feature id for the '<em><b>Display</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISPLAY_TERM__DISPLAY = ProofProcessPackage.TERM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Display Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISPLAY_TERM_FEATURE_COUNT = ProofProcessPackage.TERM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.isabelle.impl.IsaTermImpl <em>Isa Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsaTermImpl
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getIsaTerm()
	 * @generated
	 */
	int ISA_TERM = 1;

	/**
	 * The feature id for the '<em><b>Display</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISA_TERM__DISPLAY = DISPLAY_TERM__DISPLAY;

	/**
	 * The feature id for the '<em><b>Term</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISA_TERM__TERM = DISPLAY_TERM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Isa Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISA_TERM_FEATURE_COUNT = DISPLAY_TERM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.isabelle.impl.IsabelleTraceImpl <em>Isabelle Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleTraceImpl
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getIsabelleTrace()
	 * @generated
	 */
	int ISABELLE_TRACE = 2;

	/**
	 * The feature id for the '<em><b>Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISABELLE_TRACE__COMMAND = ProofProcessPackage.TRACE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Simp Lemmas</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISABELLE_TRACE__SIMP_LEMMAS = ProofProcessPackage.TRACE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Isabelle Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISABELLE_TRACE_FEATURE_COUNT = ProofProcessPackage.TRACE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '<em>YXml Term</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see isabelle.XML.Tree
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getYXmlTerm()
	 * @generated
	 */
	int YXML_TERM = 3;


	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.isabelle.DisplayTerm <em>Display Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Display Term</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.DisplayTerm
	 * @generated
	 */
	EClass getDisplayTerm();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.isabelle.DisplayTerm#getDisplay <em>Display</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.DisplayTerm#getDisplay()
	 * @see #getDisplayTerm()
	 * @generated
	 */
	EAttribute getDisplayTerm_Display();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.isabelle.IsaTerm <em>Isa Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Isa Term</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.IsaTerm
	 * @generated
	 */
	EClass getIsaTerm();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.isabelle.IsaTerm#getTerm <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Term</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.IsaTerm#getTerm()
	 * @see #getIsaTerm()
	 * @generated
	 */
	EAttribute getIsaTerm_Term();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.isabelle.IsabelleTrace <em>Isabelle Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Isabelle Trace</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleTrace
	 * @generated
	 */
	EClass getIsabelleTrace();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.isabelle.IsabelleTrace#getCommand <em>Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Command</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleTrace#getCommand()
	 * @see #getIsabelleTrace()
	 * @generated
	 */
	EAttribute getIsabelleTrace_Command();

	/**
	 * Returns the meta object for the attribute list '{@link org.ai4fm.proofprocess.isabelle.IsabelleTrace#getSimpLemmas <em>Simp Lemmas</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Simp Lemmas</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleTrace#getSimpLemmas()
	 * @see #getIsabelleTrace()
	 * @generated
	 */
	EAttribute getIsabelleTrace_SimpLemmas();

	/**
	 * Returns the meta object for data type '{@link isabelle.XML.Tree <em>YXml Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>YXml Term</em>'.
	 * @see isabelle.XML.Tree
	 * @model instanceClass="isabelle.XML.Tree"
	 * @generated
	 */
	EDataType getYXmlTerm();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IsabelleProofProcessFactory getIsabelleProofProcessFactory();

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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.isabelle.impl.DisplayTermImpl <em>Display Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.isabelle.impl.DisplayTermImpl
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getDisplayTerm()
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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.isabelle.impl.IsaTermImpl <em>Isa Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsaTermImpl
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getIsaTerm()
		 * @generated
		 */
		EClass ISA_TERM = eINSTANCE.getIsaTerm();

		/**
		 * The meta object literal for the '<em><b>Term</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ISA_TERM__TERM = eINSTANCE.getIsaTerm_Term();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.isabelle.impl.IsabelleTraceImpl <em>Isabelle Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleTraceImpl
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getIsabelleTrace()
		 * @generated
		 */
		EClass ISABELLE_TRACE = eINSTANCE.getIsabelleTrace();

		/**
		 * The meta object literal for the '<em><b>Command</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ISABELLE_TRACE__COMMAND = eINSTANCE.getIsabelleTrace_Command();

		/**
		 * The meta object literal for the '<em><b>Simp Lemmas</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ISABELLE_TRACE__SIMP_LEMMAS = eINSTANCE.getIsabelleTrace_SimpLemmas();

		/**
		 * The meta object literal for the '<em>YXml Term</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see isabelle.XML.Tree
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getYXmlTerm()
		 * @generated
		 */
		EDataType YXML_TERM = eINSTANCE.getYXmlTerm();

	}

} //IsabelleProofProcessPackage
