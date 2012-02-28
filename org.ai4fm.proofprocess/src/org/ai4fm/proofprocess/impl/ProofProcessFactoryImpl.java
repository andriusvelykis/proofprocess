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
			ProofProcessFactory theProofProcessFactory = (ProofProcessFactory)EPackage.Registry.INSTANCE.getEFactory("http://org/ai4fm/proofprocess/v0"); 
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
			case ProofProcessPackage.INTENT: return createIntent();
			case ProofProcessPackage.PROOF_STEP: return createProofStep();
			case ProofProcessPackage.PROOF_INFO: return createProofInfo();
			case ProofProcessPackage.PROPERTY_DEF: return createPropertyDef();
			case ProofProcessPackage.PROPERTY: return createProperty();
			case ProofProcessPackage.PROOF_ENTRY: return createProofEntry();
			case ProofProcessPackage.PROOF_SEQ: return createProofSeq();
			case ProofProcessPackage.PROOF_PARALLEL: return createProofParallel();
			case ProofProcessPackage.PROOF_DECOR: return createProofDecor();
			case ProofProcessPackage.ATTEMPT: return createAttempt();
			case ProofProcessPackage.PROOF: return createProof();
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
			case ProofProcessPackage.PROPERTY_TYPE:
				return createPropertyTypeFromString(eDataType, initialValue);
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
			case ProofProcessPackage.PROPERTY_TYPE:
				return convertPropertyTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Intent createIntent() {
		IntentImpl intent = new IntentImpl();
		return intent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofStep createProofStep() {
		ProofStepImpl proofStep = new ProofStepImpl();
		return proofStep;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofInfo createProofInfo() {
		ProofInfoImpl proofInfo = new ProofInfoImpl();
		return proofInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyDef createPropertyDef() {
		PropertyDefImpl propertyDef = new PropertyDefImpl();
		return propertyDef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property createProperty() {
		PropertyImpl property = new PropertyImpl();
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofEntry createProofEntry() {
		ProofEntryImpl proofEntry = new ProofEntryImpl();
		return proofEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofSeq createProofSeq() {
		ProofSeqImpl proofSeq = new ProofSeqImpl();
		return proofSeq;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofParallel createProofParallel() {
		ProofParallelImpl proofParallel = new ProofParallelImpl();
		return proofParallel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofDecor createProofDecor() {
		ProofDecorImpl proofDecor = new ProofDecorImpl();
		return proofDecor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attempt createAttempt() {
		AttemptImpl attempt = new AttemptImpl();
		return attempt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Proof createProof() {
		ProofImpl proof = new ProofImpl();
		return proof;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType createPropertyTypeFromString(EDataType eDataType, String initialValue) {
		PropertyType result = PropertyType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPropertyTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
