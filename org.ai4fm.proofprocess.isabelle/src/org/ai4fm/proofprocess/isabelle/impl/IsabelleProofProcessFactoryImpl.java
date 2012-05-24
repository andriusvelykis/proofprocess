/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle.impl;

import isabelle.XML.Tree;

import org.ai4fm.proofprocess.isabelle.*;
import org.ai4fm.proofprocess.isabelle.parse.YXmlTermParser;

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
public class IsabelleProofProcessFactoryImpl extends EFactoryImpl implements IsabelleProofProcessFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IsabelleProofProcessFactory init() {
		try {
			IsabelleProofProcessFactory theIsabelleProofProcessFactory = (IsabelleProofProcessFactory)EPackage.Registry.INSTANCE.getEFactory("http://org/ai4fm/proofprocess/isabelle/v1.0.0"); 
			if (theIsabelleProofProcessFactory != null) {
				return theIsabelleProofProcessFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IsabelleProofProcessFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsabelleProofProcessFactoryImpl() {
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
			case IsabelleProofProcessPackage.ISA_TERM: return createIsaTerm();
			case IsabelleProofProcessPackage.NAME_TERM: return createNameTerm();
			case IsabelleProofProcessPackage.INST_TERM: return createInstTerm();
			case IsabelleProofProcessPackage.INST: return createInst();
			case IsabelleProofProcessPackage.ISABELLE_TRACE: return createIsabelleTrace();
			case IsabelleProofProcessPackage.NAMED_TERM_TREE: return createNamedTermTree();
			case IsabelleProofProcessPackage.ISABELLE_COMMAND: return createIsabelleCommand();
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
			case IsabelleProofProcessPackage.YXML_TERM:
				return createYXmlTermFromString(eDataType, initialValue);
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
			case IsabelleProofProcessPackage.YXML_TERM:
				return convertYXmlTermToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsaTerm createIsaTerm() {
		IsaTermImpl isaTerm = new IsaTermImpl();
		return isaTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameTerm createNameTerm() {
		NameTermImpl nameTerm = new NameTermImpl();
		return nameTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstTerm createInstTerm() {
		InstTermImpl instTerm = new InstTermImpl();
		return instTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Inst createInst() {
		InstImpl inst = new InstImpl();
		return inst;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsabelleTrace createIsabelleTrace() {
		IsabelleTraceImpl isabelleTrace = new IsabelleTraceImpl();
		return isabelleTrace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamedTermTree createNamedTermTree() {
		NamedTermTreeImpl namedTermTree = new NamedTermTreeImpl();
		return namedTermTree;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsabelleCommand createIsabelleCommand() {
		IsabelleCommandImpl isabelleCommand = new IsabelleCommandImpl();
		return isabelleCommand;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Tree createYXmlTermFromString(EDataType eDataType, String initialValue) {
		return YXmlTermParser.parseYXml(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String convertYXmlTermToString(EDataType eDataType, Object instanceValue) {
		return YXmlTermParser.convertToYXml((Tree) instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsabelleProofProcessPackage getIsabelleProofProcessPackage() {
		return (IsabelleProofProcessPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IsabelleProofProcessPackage getPackage() {
		return IsabelleProofProcessPackage.eINSTANCE;
	}

} //IsabelleProofProcessFactoryImpl
