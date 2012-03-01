/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.log;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proof Log</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.log.ProofLog#getActivities <em>Activities</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.ai4fm.proofprocess.log.ProofProcessLogPackage#getProofLog()
 * @model
 * @generated
 */
public interface ProofLog extends EObject {
	/**
	 * Returns the value of the '<em><b>Activities</b></em>' containment reference list.
	 * The list contents are of type {@link org.ai4fm.proofprocess.log.Activity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activities</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activities</em>' containment reference list.
	 * @see org.ai4fm.proofprocess.log.ProofProcessLogPackage#getProofLog_Activities()
	 * @model containment="true"
	 * @generated
	 */
	EList<Activity> getActivities();

} // ProofLog
