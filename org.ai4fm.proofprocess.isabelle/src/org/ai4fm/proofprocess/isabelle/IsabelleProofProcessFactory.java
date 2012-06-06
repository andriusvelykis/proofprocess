/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage
 * @generated
 */
public interface IsabelleProofProcessFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IsabelleProofProcessFactory eINSTANCE = org.ai4fm.proofprocess.isabelle.impl.IsabelleProofProcessFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Markup Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Markup Term</em>'.
	 * @generated
	 */
	MarkupTerm createMarkupTerm();

	/**
	 * Returns a new object of class '<em>Isa Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Isa Term</em>'.
	 * @generated
	 */
	IsaTerm createIsaTerm();

	/**
	 * Returns a new object of class '<em>Name Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Name Term</em>'.
	 * @generated
	 */
	NameTerm createNameTerm();

	/**
	 * Returns a new object of class '<em>Named Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Named Term</em>'.
	 * @generated
	 */
	NamedTerm createNamedTerm();

	/**
	 * Returns a new object of class '<em>Inst Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Inst Term</em>'.
	 * @generated
	 */
	InstTerm createInstTerm();

	/**
	 * Returns a new object of class '<em>Inst</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Inst</em>'.
	 * @generated
	 */
	Inst createInst();

	/**
	 * Returns a new object of class '<em>Isabelle Trace</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Isabelle Trace</em>'.
	 * @generated
	 */
	IsabelleTrace createIsabelleTrace();

	/**
	 * Returns a new object of class '<em>Named Term Tree</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Named Term Tree</em>'.
	 * @generated
	 */
	NamedTermTree createNamedTermTree();

	/**
	 * Returns a new object of class '<em>Isabelle Command</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Isabelle Command</em>'.
	 * @generated
	 */
	IsabelleCommand createIsabelleCommand();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IsabelleProofProcessPackage getIsabelleProofProcessPackage();

} //IsabelleProofProcessFactory
