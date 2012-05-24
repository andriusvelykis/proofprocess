/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle.util;

import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.Trace;

import org.ai4fm.proofprocess.isabelle.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage
 * @generated
 */
public class IsabelleProofProcessAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IsabelleProofProcessPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsabelleProofProcessAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = IsabelleProofProcessPackage.eINSTANCE;
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
	protected IsabelleProofProcessSwitch<Adapter> modelSwitch =
		new IsabelleProofProcessSwitch<Adapter>() {
			@Override
			public Adapter caseDisplayTerm(DisplayTerm object) {
				return createDisplayTermAdapter();
			}
			@Override
			public Adapter caseIsaTerm(IsaTerm object) {
				return createIsaTermAdapter();
			}
			@Override
			public Adapter caseNameTerm(NameTerm object) {
				return createNameTermAdapter();
			}
			@Override
			public Adapter caseInstTerm(InstTerm object) {
				return createInstTermAdapter();
			}
			@Override
			public Adapter caseInst(Inst object) {
				return createInstAdapter();
			}
			@Override
			public Adapter caseIsabelleTrace(IsabelleTrace object) {
				return createIsabelleTraceAdapter();
			}
			@Override
			public Adapter caseNamedTermTree(NamedTermTree object) {
				return createNamedTermTreeAdapter();
			}
			@Override
			public Adapter caseIsabelleCommand(IsabelleCommand object) {
				return createIsabelleCommandAdapter();
			}
			@Override
			public Adapter caseTerm(Term object) {
				return createTermAdapter();
			}
			@Override
			public Adapter caseTrace(Trace object) {
				return createTraceAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.isabelle.DisplayTerm <em>Display Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.isabelle.DisplayTerm
	 * @generated
	 */
	public Adapter createDisplayTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.isabelle.IsaTerm <em>Isa Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.isabelle.IsaTerm
	 * @generated
	 */
	public Adapter createIsaTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.isabelle.NameTerm <em>Name Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.isabelle.NameTerm
	 * @generated
	 */
	public Adapter createNameTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.isabelle.InstTerm <em>Inst Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.isabelle.InstTerm
	 * @generated
	 */
	public Adapter createInstTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.isabelle.Inst <em>Inst</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.isabelle.Inst
	 * @generated
	 */
	public Adapter createInstAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.isabelle.IsabelleTrace <em>Isabelle Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleTrace
	 * @generated
	 */
	public Adapter createIsabelleTraceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.isabelle.NamedTermTree <em>Named Term Tree</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.isabelle.NamedTermTree
	 * @generated
	 */
	public Adapter createNamedTermTreeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.ai4fm.proofprocess.isabelle.IsabelleCommand <em>Isabelle Command</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.ai4fm.proofprocess.isabelle.IsabelleCommand
	 * @generated
	 */
	public Adapter createIsabelleCommandAdapter() {
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

} //IsabelleProofProcessAdapterFactory
