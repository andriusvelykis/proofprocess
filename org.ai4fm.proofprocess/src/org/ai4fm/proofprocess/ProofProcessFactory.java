/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.ai4fm.proofprocess.ProofProcessPackage
 * @generated
 */
public interface ProofProcessFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProofProcessFactory eINSTANCE = org.ai4fm.proofprocess.impl.ProofProcessFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Intent</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Intent</em>'.
	 * @generated
	 */
	Intent createIntent();

	/**
	 * Returns a new object of class '<em>Proof Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proof Object</em>'.
	 * @generated
	 */
	ProofObject createProofObject();

	/**
	 * Returns a new object of class '<em>Proof Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proof Property</em>'.
	 * @generated
	 */
	ProofProperty createProofProperty();

	/**
	 * Returns a new object of class '<em>Attempt Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attempt Set</em>'.
	 * @generated
	 */
	AttemptSet createAttemptSet();

	/**
	 * Returns a new object of class '<em>Attempt Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attempt Group</em>'.
	 * @generated
	 */
	AttemptGroup createAttemptGroup();

	/**
	 * Returns a new object of class '<em>Attempt Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attempt Entry</em>'.
	 * @generated
	 */
	AttemptEntry createAttemptEntry();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ProofProcessPackage getProofProcessPackage();

} //ProofProcessFactory
