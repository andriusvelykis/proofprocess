/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage
 * @generated
 */
public interface ZEvesProofProcessFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ZEvesProofProcessFactory eINSTANCE = org.ai4fm.proofprocess.zeves.impl.ZEvesProofProcessFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Project</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project</em>'.
	 * @generated
	 */
	Project createProject();

	/**
	 * Returns a new object of class '<em>Activity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Activity</em>'.
	 * @generated
	 */
	Activity createActivity();

	/**
	 * Returns a new object of class '<em>Proof Activity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proof Activity</em>'.
	 * @generated
	 */
	ProofActivity createProofActivity();

	/**
	 * Returns a new object of class '<em>ZEves Trace</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ZEves Trace</em>'.
	 * @generated
	 */
	ZEvesTrace createZEvesTrace();

	/**
	 * Returns a new object of class '<em>Position</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Position</em>'.
	 * @generated
	 */
	Position createPosition();

	/**
	 * Returns a new object of class '<em>Text Loc</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Text Loc</em>'.
	 * @generated
	 */
	TextLoc createTextLoc();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ZEvesProofProcessPackage getZEvesProofProcessPackage();

} //ZEvesProofProcessFactory
