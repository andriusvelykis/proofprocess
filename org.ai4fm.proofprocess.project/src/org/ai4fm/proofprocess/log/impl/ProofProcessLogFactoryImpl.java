/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.log.impl;

import org.ai4fm.proofprocess.log.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProofProcessLogFactoryImpl extends EFactoryImpl implements ProofProcessLogFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProofProcessLogFactory init() {
		try {
			ProofProcessLogFactory theProofProcessLogFactory = (ProofProcessLogFactory)EPackage.Registry.INSTANCE.getEFactory("http://org/ai4fm/proofprocess/log/v1.0.0.13"); 
			if (theProofProcessLogFactory != null) {
				return theProofProcessLogFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ProofProcessLogFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofProcessLogFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ProofProcessLogPackage.PROOF_LOG: return (EObject)createProofLog();
			case ProofProcessLogPackage.ACTIVITY: return (EObject)createActivity();
			case ProofProcessLogPackage.PROOF_ACTIVITY: return (EObject)createProofActivity();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProofLog createProofLog() {
		ProofLogImpl proofLog = new ProofLogImpl();
		return proofLog;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Activity createActivity() {
		ActivityImpl activity = new ActivityImpl();
		return activity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProofActivity createProofActivity() {
		ProofActivityImpl proofActivity = new ProofActivityImpl();
		return proofActivity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProofProcessLogPackage getProofProcessLogPackage() {
		return (ProofProcessLogPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ProofProcessLogPackage getPackage() {
		return ProofProcessLogPackage.eINSTANCE;
	}

} //ProofProcessLogFactoryImpl
