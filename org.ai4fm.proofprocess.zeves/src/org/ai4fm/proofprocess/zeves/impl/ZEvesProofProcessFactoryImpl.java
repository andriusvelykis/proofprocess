/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves.impl;

import org.ai4fm.proofprocess.zeves.*;

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
public class ZEvesProofProcessFactoryImpl extends EFactoryImpl implements ZEvesProofProcessFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ZEvesProofProcessFactory init() {
		try {
			ZEvesProofProcessFactory theZEvesProofProcessFactory = (ZEvesProofProcessFactory)EPackage.Registry.INSTANCE.getEFactory("http://org/ai4fm/proofprocess/zeves/v1.0.0"); 
			if (theZEvesProofProcessFactory != null) {
				return theZEvesProofProcessFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ZEvesProofProcessFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ZEvesProofProcessFactoryImpl() {
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
			case ZEvesProofProcessPackage.UNPARSED_TERM: return createUnparsedTerm();
			case ZEvesProofProcessPackage.ZEVES_TRACE: return createZEvesTrace();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnparsedTerm createUnparsedTerm() {
		UnparsedTermImpl unparsedTerm = new UnparsedTermImpl();
		return unparsedTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ZEvesTrace createZEvesTrace() {
		ZEvesTraceImpl zEvesTrace = new ZEvesTraceImpl();
		return zEvesTrace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ZEvesProofProcessPackage getZEvesProofProcessPackage() {
		return (ZEvesProofProcessPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ZEvesProofProcessPackage getPackage() {
		return ZEvesProofProcessPackage.eINSTANCE;
	}

} //ZEvesProofProcessFactoryImpl
