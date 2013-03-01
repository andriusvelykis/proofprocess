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
	 * The meta object id for the '{@link org.ai4fm.proofprocess.isabelle.impl.MarkupTermImpl <em>Markup Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.isabelle.impl.MarkupTermImpl
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getMarkupTerm()
	 * @generated
	 */
	int MARKUP_TERM = 1;

	/**
	 * The feature id for the '<em><b>Display</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MARKUP_TERM__DISPLAY = DISPLAY_TERM__DISPLAY;

	/**
	 * The feature id for the '<em><b>Term</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MARKUP_TERM__TERM = DISPLAY_TERM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Markup Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MARKUP_TERM_FEATURE_COUNT = DISPLAY_TERM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.isabelle.impl.IsaTermImpl <em>Isa Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsaTermImpl
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getIsaTerm()
	 * @generated
	 */
	int ISA_TERM = 2;

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
	 * The meta object id for the '{@link org.ai4fm.proofprocess.isabelle.impl.NameTermImpl <em>Name Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.isabelle.impl.NameTermImpl
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getNameTerm()
	 * @generated
	 */
	int NAME_TERM = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_TERM__NAME = ProofProcessPackage.TERM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Name Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_TERM_FEATURE_COUNT = ProofProcessPackage.TERM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.isabelle.impl.NamedTermImpl <em>Named Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.isabelle.impl.NamedTermImpl
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getNamedTerm()
	 * @generated
	 */
	int NAMED_TERM = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_TERM__NAME = NAME_TERM__NAME;

	/**
	 * The feature id for the '<em><b>Term</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_TERM__TERM = NAME_TERM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Named Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_TERM_FEATURE_COUNT = NAME_TERM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.isabelle.impl.InstTermImpl <em>Inst Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.isabelle.impl.InstTermImpl
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getInstTerm()
	 * @generated
	 */
	int INST_TERM = 5;

	/**
	 * The feature id for the '<em><b>Term</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_TERM__TERM = ProofProcessPackage.TERM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Insts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_TERM__INSTS = ProofProcessPackage.TERM_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Inst Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_TERM_FEATURE_COUNT = ProofProcessPackage.TERM_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.isabelle.impl.InstImpl <em>Inst</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.isabelle.impl.InstImpl
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getInst()
	 * @generated
	 */
	int INST = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST__NAME = 0;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST__INDEX = 1;

	/**
	 * The feature id for the '<em><b>Term</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST__TERM = 2;

	/**
	 * The number of structural features of the '<em>Inst</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.isabelle.impl.IsabelleTraceImpl <em>Isabelle Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleTraceImpl
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getIsabelleTrace()
	 * @generated
	 */
	int ISABELLE_TRACE = 7;

	/**
	 * The feature id for the '<em><b>Command</b></em>' containment reference.
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
	 * The meta object id for the '{@link org.ai4fm.proofprocess.isabelle.impl.NamedTermTreeImpl <em>Named Term Tree</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.isabelle.impl.NamedTermTreeImpl
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getNamedTermTree()
	 * @generated
	 */
	int NAMED_TERM_TREE = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_TERM_TREE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Terms</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_TERM_TREE__TERMS = 1;

	/**
	 * The feature id for the '<em><b>Branches</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_TERM_TREE__BRANCHES = 2;

	/**
	 * The number of structural features of the '<em>Named Term Tree</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_TERM_TREE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.isabelle.impl.IsabelleCommandImpl <em>Isabelle Command</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleCommandImpl
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getIsabelleCommand()
	 * @generated
	 */
	int ISABELLE_COMMAND = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISABELLE_COMMAND__NAME = NAMED_TERM_TREE__NAME;

	/**
	 * The feature id for the '<em><b>Terms</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISABELLE_COMMAND__TERMS = NAMED_TERM_TREE__TERMS;

	/**
	 * The feature id for the '<em><b>Branches</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISABELLE_COMMAND__BRANCHES = NAMED_TERM_TREE__BRANCHES;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISABELLE_COMMAND__SOURCE = NAMED_TERM_TREE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Isabelle Command</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISABELLE_COMMAND_FEATURE_COUNT = NAMED_TERM_TREE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.isabelle.impl.AssumptionTermImpl <em>Assumption Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.isabelle.impl.AssumptionTermImpl
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getAssumptionTerm()
	 * @generated
	 */
	int ASSUMPTION_TERM = 10;

	/**
	 * The feature id for the '<em><b>Term</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSUMPTION_TERM__TERM = ProofProcessPackage.TERM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Assumption Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSUMPTION_TERM_FEATURE_COUNT = ProofProcessPackage.TERM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.ai4fm.proofprocess.isabelle.impl.JudgementTermImpl <em>Judgement Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.ai4fm.proofprocess.isabelle.impl.JudgementTermImpl
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getJudgementTerm()
	 * @generated
	 */
	int JUDGEMENT_TERM = 11;

	/**
	 * The feature id for the '<em><b>Assms</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JUDGEMENT_TERM__ASSMS = ProofProcessPackage.TERM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Goal</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JUDGEMENT_TERM__GOAL = ProofProcessPackage.TERM_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Judgement Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JUDGEMENT_TERM_FEATURE_COUNT = ProofProcessPackage.TERM_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '<em>Isabelle XML</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see isabelle.XML.Tree
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getIsabelleXML()
	 * @generated
	 */
	int ISABELLE_XML = 12;

	/**
	 * The meta object id for the '<em>Isabelle Term</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see isabelle.Term.Term
	 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getIsabelleTerm()
	 * @generated
	 */
	int ISABELLE_TERM = 13;

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
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.isabelle.MarkupTerm <em>Markup Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Markup Term</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.MarkupTerm
	 * @generated
	 */
	EClass getMarkupTerm();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.isabelle.MarkupTerm#getTerm <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Term</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.MarkupTerm#getTerm()
	 * @see #getMarkupTerm()
	 * @generated
	 */
	EAttribute getMarkupTerm_Term();

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
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.isabelle.NameTerm <em>Name Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name Term</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.NameTerm
	 * @generated
	 */
	EClass getNameTerm();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.isabelle.NameTerm#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.NameTerm#getName()
	 * @see #getNameTerm()
	 * @generated
	 */
	EAttribute getNameTerm_Name();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.isabelle.NamedTerm <em>Named Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Term</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.NamedTerm
	 * @generated
	 */
	EClass getNamedTerm();

	/**
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.isabelle.NamedTerm#getTerm <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Term</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.NamedTerm#getTerm()
	 * @see #getNamedTerm()
	 * @generated
	 */
	EReference getNamedTerm_Term();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.isabelle.InstTerm <em>Inst Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Term</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.InstTerm
	 * @generated
	 */
	EClass getInstTerm();

	/**
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.isabelle.InstTerm#getTerm <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Term</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.InstTerm#getTerm()
	 * @see #getInstTerm()
	 * @generated
	 */
	EReference getInstTerm_Term();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.isabelle.InstTerm#getInsts <em>Insts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Insts</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.InstTerm#getInsts()
	 * @see #getInstTerm()
	 * @generated
	 */
	EReference getInstTerm_Insts();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.isabelle.Inst <em>Inst</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.Inst
	 * @generated
	 */
	EClass getInst();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.isabelle.Inst#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.Inst#getName()
	 * @see #getInst()
	 * @generated
	 */
	EAttribute getInst_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.isabelle.Inst#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.Inst#getIndex()
	 * @see #getInst()
	 * @generated
	 */
	EAttribute getInst_Index();

	/**
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.isabelle.Inst#getTerm <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Term</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.Inst#getTerm()
	 * @see #getInst()
	 * @generated
	 */
	EReference getInst_Term();

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
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.isabelle.IsabelleTrace#getCommand <em>Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Command</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleTrace#getCommand()
	 * @see #getIsabelleTrace()
	 * @generated
	 */
	EReference getIsabelleTrace_Command();

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
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.isabelle.NamedTermTree <em>Named Term Tree</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Term Tree</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.NamedTermTree
	 * @generated
	 */
	EClass getNamedTermTree();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.isabelle.NamedTermTree#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.NamedTermTree#getName()
	 * @see #getNamedTermTree()
	 * @generated
	 */
	EAttribute getNamedTermTree_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.isabelle.NamedTermTree#getTerms <em>Terms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Terms</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.NamedTermTree#getTerms()
	 * @see #getNamedTermTree()
	 * @generated
	 */
	EReference getNamedTermTree_Terms();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.isabelle.NamedTermTree#getBranches <em>Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Branches</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.NamedTermTree#getBranches()
	 * @see #getNamedTermTree()
	 * @generated
	 */
	EReference getNamedTermTree_Branches();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.isabelle.IsabelleCommand <em>Isabelle Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Isabelle Command</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleCommand
	 * @generated
	 */
	EClass getIsabelleCommand();

	/**
	 * Returns the meta object for the attribute '{@link org.ai4fm.proofprocess.isabelle.IsabelleCommand#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleCommand#getSource()
	 * @see #getIsabelleCommand()
	 * @generated
	 */
	EAttribute getIsabelleCommand_Source();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.isabelle.AssumptionTerm <em>Assumption Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Assumption Term</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.AssumptionTerm
	 * @generated
	 */
	EClass getAssumptionTerm();

	/**
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.isabelle.AssumptionTerm#getTerm <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Term</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.AssumptionTerm#getTerm()
	 * @see #getAssumptionTerm()
	 * @generated
	 */
	EReference getAssumptionTerm_Term();

	/**
	 * Returns the meta object for class '{@link org.ai4fm.proofprocess.isabelle.JudgementTerm <em>Judgement Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Judgement Term</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.JudgementTerm
	 * @generated
	 */
	EClass getJudgementTerm();

	/**
	 * Returns the meta object for the containment reference list '{@link org.ai4fm.proofprocess.isabelle.JudgementTerm#getAssms <em>Assms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assms</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.JudgementTerm#getAssms()
	 * @see #getJudgementTerm()
	 * @generated
	 */
	EReference getJudgementTerm_Assms();

	/**
	 * Returns the meta object for the containment reference '{@link org.ai4fm.proofprocess.isabelle.JudgementTerm#getGoal <em>Goal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Goal</em>'.
	 * @see org.ai4fm.proofprocess.isabelle.JudgementTerm#getGoal()
	 * @see #getJudgementTerm()
	 * @generated
	 */
	EReference getJudgementTerm_Goal();

	/**
	 * Returns the meta object for data type '{@link isabelle.XML.Tree <em>Isabelle XML</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Isabelle XML</em>'.
	 * @see isabelle.XML.Tree
	 * @model instanceClass="isabelle.XML.Tree"
	 * @generated
	 */
	EDataType getIsabelleXML();

	/**
	 * Returns the meta object for data type '{@link isabelle.Term.Term <em>Isabelle Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Isabelle Term</em>'.
	 * @see isabelle.Term.Term
	 * @model instanceClass="isabelle.Term.Term"
	 * @generated
	 */
	EDataType getIsabelleTerm();

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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.isabelle.impl.MarkupTermImpl <em>Markup Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.isabelle.impl.MarkupTermImpl
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getMarkupTerm()
		 * @generated
		 */
		EClass MARKUP_TERM = eINSTANCE.getMarkupTerm();

		/**
		 * The meta object literal for the '<em><b>Term</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MARKUP_TERM__TERM = eINSTANCE.getMarkupTerm_Term();

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
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.isabelle.impl.NameTermImpl <em>Name Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.isabelle.impl.NameTermImpl
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getNameTerm()
		 * @generated
		 */
		EClass NAME_TERM = eINSTANCE.getNameTerm();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_TERM__NAME = eINSTANCE.getNameTerm_Name();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.isabelle.impl.NamedTermImpl <em>Named Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.isabelle.impl.NamedTermImpl
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getNamedTerm()
		 * @generated
		 */
		EClass NAMED_TERM = eINSTANCE.getNamedTerm();

		/**
		 * The meta object literal for the '<em><b>Term</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAMED_TERM__TERM = eINSTANCE.getNamedTerm_Term();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.isabelle.impl.InstTermImpl <em>Inst Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.isabelle.impl.InstTermImpl
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getInstTerm()
		 * @generated
		 */
		EClass INST_TERM = eINSTANCE.getInstTerm();

		/**
		 * The meta object literal for the '<em><b>Term</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_TERM__TERM = eINSTANCE.getInstTerm_Term();

		/**
		 * The meta object literal for the '<em><b>Insts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_TERM__INSTS = eINSTANCE.getInstTerm_Insts();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.isabelle.impl.InstImpl <em>Inst</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.isabelle.impl.InstImpl
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getInst()
		 * @generated
		 */
		EClass INST = eINSTANCE.getInst();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INST__NAME = eINSTANCE.getInst_Name();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INST__INDEX = eINSTANCE.getInst_Index();

		/**
		 * The meta object literal for the '<em><b>Term</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST__TERM = eINSTANCE.getInst_Term();

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
		 * The meta object literal for the '<em><b>Command</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ISABELLE_TRACE__COMMAND = eINSTANCE.getIsabelleTrace_Command();

		/**
		 * The meta object literal for the '<em><b>Simp Lemmas</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ISABELLE_TRACE__SIMP_LEMMAS = eINSTANCE.getIsabelleTrace_SimpLemmas();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.isabelle.impl.NamedTermTreeImpl <em>Named Term Tree</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.isabelle.impl.NamedTermTreeImpl
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getNamedTermTree()
		 * @generated
		 */
		EClass NAMED_TERM_TREE = eINSTANCE.getNamedTermTree();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_TERM_TREE__NAME = eINSTANCE.getNamedTermTree_Name();

		/**
		 * The meta object literal for the '<em><b>Terms</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAMED_TERM_TREE__TERMS = eINSTANCE.getNamedTermTree_Terms();

		/**
		 * The meta object literal for the '<em><b>Branches</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAMED_TERM_TREE__BRANCHES = eINSTANCE.getNamedTermTree_Branches();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.isabelle.impl.IsabelleCommandImpl <em>Isabelle Command</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleCommandImpl
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getIsabelleCommand()
		 * @generated
		 */
		EClass ISABELLE_COMMAND = eINSTANCE.getIsabelleCommand();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ISABELLE_COMMAND__SOURCE = eINSTANCE.getIsabelleCommand_Source();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.isabelle.impl.AssumptionTermImpl <em>Assumption Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.isabelle.impl.AssumptionTermImpl
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getAssumptionTerm()
		 * @generated
		 */
		EClass ASSUMPTION_TERM = eINSTANCE.getAssumptionTerm();

		/**
		 * The meta object literal for the '<em><b>Term</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSUMPTION_TERM__TERM = eINSTANCE.getAssumptionTerm_Term();

		/**
		 * The meta object literal for the '{@link org.ai4fm.proofprocess.isabelle.impl.JudgementTermImpl <em>Judgement Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.ai4fm.proofprocess.isabelle.impl.JudgementTermImpl
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getJudgementTerm()
		 * @generated
		 */
		EClass JUDGEMENT_TERM = eINSTANCE.getJudgementTerm();

		/**
		 * The meta object literal for the '<em><b>Assms</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JUDGEMENT_TERM__ASSMS = eINSTANCE.getJudgementTerm_Assms();

		/**
		 * The meta object literal for the '<em><b>Goal</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JUDGEMENT_TERM__GOAL = eINSTANCE.getJudgementTerm_Goal();

		/**
		 * The meta object literal for the '<em>Isabelle XML</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see isabelle.XML.Tree
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getIsabelleXML()
		 * @generated
		 */
		EDataType ISABELLE_XML = eINSTANCE.getIsabelleXML();

		/**
		 * The meta object literal for the '<em>Isabelle Term</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see isabelle.Term.Term
		 * @see org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessPackageImpl#getIsabelleTerm()
		 * @generated
		 */
		EDataType ISABELLE_TERM = eINSTANCE.getIsabelleTerm();

	}

} //IsabelleProofProcessPackage
