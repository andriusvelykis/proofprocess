/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.impl;

import org.ai4fm.proofprocess.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
public class ProofProcessFactoryImpl extends EFactoryImpl implements ProofProcessFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProofProcessFactory init() {
		try {
			ProofProcessFactory theProofProcessFactory = (ProofProcessFactory)EPackage.Registry.INSTANCE.getEFactory("http://org/ai4fm/proofprocess/v1.0.0.11"); 
			if (theProofProcessFactory != null) {
				return theProofProcessFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ProofProcessFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofProcessFactoryImpl() {
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
			case ProofProcessPackage.INTENT: return (EObject)createIntent();
			case ProofProcessPackage.PROOF_STEP: return (EObject)createProofStep();
			case ProofProcessPackage.PROOF_INFO: return (EObject)createProofInfo();
			case ProofProcessPackage.PROOF_FEATURE_DEF: return (EObject)createProofFeatureDef();
			case ProofProcessPackage.PROOF_FEATURE: return (EObject)createProofFeature();
			case ProofProcessPackage.PROOF_ENTRY: return (EObject)createProofEntry();
			case ProofProcessPackage.PROOF_SEQ: return (EObject)createProofSeq();
			case ProofProcessPackage.PROOF_PARALLEL: return (EObject)createProofParallel();
			case ProofProcessPackage.PROOF_DECOR: return (EObject)createProofDecor();
			case ProofProcessPackage.ATTEMPT: return (EObject)createAttempt();
			case ProofProcessPackage.PROOF: return (EObject)createProof();
			case ProofProcessPackage.PROOF_STORE: return (EObject)createProofStore();
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
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ProofProcessPackage.PROOF_FEATURE_TYPE:
				return createProofFeatureTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ProofProcessPackage.PROOF_FEATURE_TYPE:
				return convertProofFeatureTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Intent createIntent() {
		IntentImpl intent = new IntentImpl();
		return intent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProofStep createProofStep() {
		ProofStepImpl proofStep = new ProofStepImpl();
		return proofStep;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProofInfo createProofInfo() {
		ProofInfoImpl proofInfo = new ProofInfoImpl();
		return proofInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProofFeatureDef createProofFeatureDef() {
		ProofFeatureDefImpl proofFeatureDef = new ProofFeatureDefImpl();
		return proofFeatureDef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProofFeature createProofFeature() {
		ProofFeatureImpl proofFeature = new ProofFeatureImpl();
		return proofFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProofEntry createProofEntry() {
		ProofEntryImpl proofEntry = new ProofEntryImpl();
		return proofEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProofSeq createProofSeq() {
		ProofSeqImpl proofSeq = new ProofSeqImpl();
		return proofSeq;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProofParallel createProofParallel() {
		ProofParallelImpl proofParallel = new ProofParallelImpl();
		return proofParallel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProofDecor createProofDecor() {
		ProofDecorImpl proofDecor = new ProofDecorImpl();
		return proofDecor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Attempt createAttempt() {
		AttemptImpl attempt = new AttemptImpl();
		return attempt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Proof createProof() {
		ProofImpl proof = new ProofImpl();
		return proof;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProofStore createProofStore() {
		ProofStoreImpl proofStore = new ProofStoreImpl();
		return proofStore;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofFeatureType createProofFeatureTypeFromString(EDataType eDataType, String initialValue) {
		ProofFeatureType result = ProofFeatureType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertProofFeatureTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProofProcessPackage getProofProcessPackage() {
		return (ProofProcessPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ProofProcessPackage getPackage() {
		return ProofProcessPackage.eINSTANCE;
	}

} //ProofProcessFactoryImpl
