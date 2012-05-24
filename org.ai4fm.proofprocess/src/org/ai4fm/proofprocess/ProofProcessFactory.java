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
	 * Returns a new object of class '<em>Proof Step</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proof Step</em>'.
	 * @generated
	 */
	ProofStep createProofStep();

	/**
	 * Returns a new object of class '<em>Proof Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proof Info</em>'.
	 * @generated
	 */
	ProofInfo createProofInfo();

	/**
	 * Returns a new object of class '<em>Proof Feature Def</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proof Feature Def</em>'.
	 * @generated
	 */
	ProofFeatureDef createProofFeatureDef();

	/**
	 * Returns a new object of class '<em>Proof Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proof Feature</em>'.
	 * @generated
	 */
	ProofFeature createProofFeature();

	/**
	 * Returns a new object of class '<em>Proof Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proof Entry</em>'.
	 * @generated
	 */
	ProofEntry createProofEntry();

	/**
	 * Returns a new object of class '<em>Proof Seq</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proof Seq</em>'.
	 * @generated
	 */
	ProofSeq createProofSeq();

	/**
	 * Returns a new object of class '<em>Proof Parallel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proof Parallel</em>'.
	 * @generated
	 */
	ProofParallel createProofParallel();

	/**
	 * Returns a new object of class '<em>Proof Decor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proof Decor</em>'.
	 * @generated
	 */
	ProofDecor createProofDecor();

	/**
	 * Returns a new object of class '<em>Attempt</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attempt</em>'.
	 * @generated
	 */
	Attempt createAttempt();

	/**
	 * Returns a new object of class '<em>Proof</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proof</em>'.
	 * @generated
	 */
	Proof createProof();

	/**
	 * Returns a new object of class '<em>Proof Store</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proof Store</em>'.
	 * @generated
	 */
	ProofStore createProofStore();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ProofProcessPackage getProofProcessPackage();

} //ProofProcessFactory
