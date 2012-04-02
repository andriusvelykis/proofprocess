/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.util;

import org.ai4fm.proofprocess.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.ai4fm.proofprocess.ProofProcessPackage
 * @generated
 */
public class ProofProcessAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ProofProcessPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofProcessAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ProofProcessPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProofProcessSwitch<Adapter> modelSwitch =
		new ProofProcessSwitch<Adapter>() {
			@Override
			public Adapter caseIntent(Intent object) {
				return createIntentAdapter();
			}
			@Override
			public Adapter caseTerm(Term object) {
				return createTermAdapter();
			}
			@Override
			public Adapter caseLoc(Loc object) {
				return createLocAdapter();
			}
			@Override
			public Adapter caseTrace(Trace object) {
				return createTraceAdapter();
			}
			@Override
			public Adapter caseProofStep(ProofStep object) {
				return createProofStepAdapter();
			}
			@Override
			public Adapter caseProofInfo(ProofInfo object) {
				return createProofInfoAdapter();
			}
			@Override
			public Adapter caseProofFeatureDef(ProofFeatureDef object) {
				return createProofFeatureDefAdapter();
			}
			@Override
			public Adapter caseProofFeature(ProofFeature object) {
				return createProofFeatureAdapter();
			}
			@Override
			public Adapter caseProofElem(ProofElem object) {
				return createProofElemAdapter();
			}
			@Override
			public Adapter caseProofEntry(ProofEntry object) {
				return createProofEntryAdapter();
			}
			@Override
			public Adapter caseProofSeq(ProofSeq object) {
				return createProofSeqAdapter();
			}
			@Override
			public Adapter caseProofParallel(ProofParallel object) {
				return createProofParallelAdapter();
			}
			@Override
			public Adapter caseProofDecor(ProofDecor object) {
				return createProofDecorAdapter();
			}
			@Override
			public Adapter caseAttempt(Attempt object) {
				return createAttemptAdapter();
			}
			@Override
			public Adapter caseProof(Proof object) {
				return createProofAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.Intent <em>Intent</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.Intent
	 * @generated
	 */
	public Adapter createIntentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.Term <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.Term
	 * @generated
	 */
	public Adapter createTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.Loc <em>Loc</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.Loc
	 * @generated
	 */
	public Adapter createLocAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.Trace <em>Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.Trace
	 * @generated
	 */
	public Adapter createTraceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.ProofStep <em>Proof Step</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.ProofStep
	 * @generated
	 */
	public Adapter createProofStepAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.ProofInfo <em>Proof Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.ProofInfo
	 * @generated
	 */
	public Adapter createProofInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.ProofFeatureDef <em>Proof Feature Def</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.ProofFeatureDef
	 * @generated
	 */
	public Adapter createProofFeatureDefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.ProofFeature <em>Proof Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.ProofFeature
	 * @generated
	 */
	public Adapter createProofFeatureAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.ProofElem <em>Proof Elem</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.ProofElem
	 * @generated
	 */
	public Adapter createProofElemAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.ProofEntry <em>Proof Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.ProofEntry
	 * @generated
	 */
	public Adapter createProofEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.ProofSeq <em>Proof Seq</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.ProofSeq
	 * @generated
	 */
	public Adapter createProofSeqAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.ProofParallel <em>Proof Parallel</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.ProofParallel
	 * @generated
	 */
	public Adapter createProofParallelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.ProofDecor <em>Proof Decor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.ProofDecor
	 * @generated
	 */
	public Adapter createProofDecorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.Attempt <em>Attempt</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.Attempt
	 * @generated
	 */
	public Adapter createAttemptAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.Proof <em>Proof</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.Proof
	 * @generated
	 */
	public Adapter createProofAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ProofProcessAdapterFactory
