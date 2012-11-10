/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle.impl;

import isabelle.Term.Term;
import isabelle.XML.Tree;

import org.ai4fm.proofprocess.isabelle.*;
import org.ai4fm.proofprocess.isabelle.parse.IsabelleTermParser;
import org.ai4fm.proofprocess.isabelle.parse.YXmlParser;
import org.ai4fm.proofprocess.parse.StringCompression;

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
			case IsabelleProofProcessPackage.MARKUP_TERM: return (EObject)createMarkupTerm();
			case IsabelleProofProcessPackage.ISA_TERM: return (EObject)createIsaTerm();
			case IsabelleProofProcessPackage.NAME_TERM: return (EObject)createNameTerm();
			case IsabelleProofProcessPackage.NAMED_TERM: return (EObject)createNamedTerm();
			case IsabelleProofProcessPackage.INST_TERM: return (EObject)createInstTerm();
			case IsabelleProofProcessPackage.INST: return (EObject)createInst();
			case IsabelleProofProcessPackage.ISABELLE_TRACE: return (EObject)createIsabelleTrace();
			case IsabelleProofProcessPackage.NAMED_TERM_TREE: return (EObject)createNamedTermTree();
			case IsabelleProofProcessPackage.ISABELLE_COMMAND: return (EObject)createIsabelleCommand();
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
			case IsabelleProofProcessPackage.ISABELLE_XML:
				return createIsabelleXMLFromString(eDataType, initialValue);
			case IsabelleProofProcessPackage.ISABELLE_TERM:
				return createIsabelleTermFromString(eDataType, initialValue);
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
			case IsabelleProofProcessPackage.ISABELLE_XML:
				return convertIsabelleXMLToString(eDataType, instanceValue);
			case IsabelleProofProcessPackage.ISABELLE_TERM:
				return convertIsabelleTermToString(eDataType, instanceValue);
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
	public MarkupTerm createMarkupTerm() {
		MarkupTermImpl markupTerm = new MarkupTermImpl();
		return markupTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IsaTerm createIsaTerm() {
		IsaTermImpl isaTerm = new IsaTermImpl();
		return isaTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NameTerm createNameTerm() {
		NameTermImpl nameTerm = new NameTermImpl();
		return nameTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NamedTerm createNamedTerm() {
		NamedTermImpl namedTerm = new NamedTermImpl();
		return namedTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InstTerm createInstTerm() {
		InstTermImpl instTerm = new InstTermImpl();
		return instTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Inst createInst() {
		InstImpl inst = new InstImpl();
		return inst;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IsabelleTrace createIsabelleTrace() {
		IsabelleTraceImpl isabelleTrace = new IsabelleTraceImpl();
		return isabelleTrace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NamedTermTree createNamedTermTree() {
		NamedTermTreeImpl namedTermTree = new NamedTermTreeImpl();
		return namedTermTree;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IsabelleCommand createIsabelleCommand() {
		IsabelleCommandImpl isabelleCommand = new IsabelleCommandImpl();
		return isabelleCommand;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Tree createIsabelleXMLFromString(EDataType eDataType, String initialValue) {
		String yxml = decompressYXml(initialValue);
		return YXmlParser.parseYXml(yxml);
	}
	
	/**
	 * Decompresses YXML String. Does nothing if already uncompressed.
	 *  
	 * @param initialValue
	 * @return
	 * @generated NOT
	 */
	private String decompressYXml(String initialValue) {
		if (YXmlParser.isYXml(initialValue)) {
			// already uncompressed
			return initialValue;
		} else {
			// compressed
			return StringCompression.decompress(initialValue);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String convertIsabelleXMLToString(EDataType eDataType, Object instanceValue) {
		String yxml = YXmlParser.convertToYXml((Tree) instanceValue);
		// compress the YXML string, since it tends to occupy the majority of storage space
		// the compression can be up to 95% efficient on large Strings
		return StringCompression.compress(yxml);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Term createIsabelleTermFromString(EDataType eDataType, String initialValue) {
		String yxml = decompressYXml(initialValue);
		return IsabelleTermParser.parseYXml(yxml);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String convertIsabelleTermToString(EDataType eDataType, Object instanceValue) {
		String yxml = IsabelleTermParser.convertToYXml((Term) instanceValue);
		// compress the YXML string, since it tends to occupy the majority of storage space
		// the compression can be up to 95% efficient on large Strings
		return StringCompression.compress(yxml);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
