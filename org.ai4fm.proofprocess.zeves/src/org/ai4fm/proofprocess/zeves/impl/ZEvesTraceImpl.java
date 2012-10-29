/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage;
import org.ai4fm.proofprocess.zeves.ZEvesTrace;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ZEves Trace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ZEvesTraceImpl#getUsedLemmas <em>Used Lemmas</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ZEvesTraceImpl#getText <em>Text</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ZEvesTraceImpl#getCase <em>Case</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ZEvesTraceImpl extends CDOObjectImpl implements ZEvesTrace {
	/**
	 * The default value of the '{@link #getText() <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getText()
	 * @generated
	 * @ordered
	 */
	protected static final String TEXT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getCase() <em>Case</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCase()
	 * @generated
	 * @ordered
	 */
	protected static final String CASE_EDEFAULT = "\"\"";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ZEvesTraceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ZEvesProofProcessPackage.Literals.ZEVES_TRACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public EList<String> getUsedLemmas() {
		return (EList<String>)eDynamicGet(ZEvesProofProcessPackage.ZEVES_TRACE__USED_LEMMAS, ZEvesProofProcessPackage.Literals.ZEVES_TRACE__USED_LEMMAS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText() {
		return (String)eDynamicGet(ZEvesProofProcessPackage.ZEVES_TRACE__TEXT, ZEvesProofProcessPackage.Literals.ZEVES_TRACE__TEXT, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setText(String newText) {
		eDynamicSet(ZEvesProofProcessPackage.ZEVES_TRACE__TEXT, ZEvesProofProcessPackage.Literals.ZEVES_TRACE__TEXT, newText);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCase() {
		return (String)eDynamicGet(ZEvesProofProcessPackage.ZEVES_TRACE__CASE, ZEvesProofProcessPackage.Literals.ZEVES_TRACE__CASE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCase(String newCase) {
		eDynamicSet(ZEvesProofProcessPackage.ZEVES_TRACE__CASE, ZEvesProofProcessPackage.Literals.ZEVES_TRACE__CASE, newCase);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ZEvesProofProcessPackage.ZEVES_TRACE__USED_LEMMAS:
				return getUsedLemmas();
			case ZEvesProofProcessPackage.ZEVES_TRACE__TEXT:
				return getText();
			case ZEvesProofProcessPackage.ZEVES_TRACE__CASE:
				return getCase();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ZEvesProofProcessPackage.ZEVES_TRACE__USED_LEMMAS:
				getUsedLemmas().clear();
				getUsedLemmas().addAll((Collection<? extends String>)newValue);
				return;
			case ZEvesProofProcessPackage.ZEVES_TRACE__TEXT:
				setText((String)newValue);
				return;
			case ZEvesProofProcessPackage.ZEVES_TRACE__CASE:
				setCase((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ZEvesProofProcessPackage.ZEVES_TRACE__USED_LEMMAS:
				getUsedLemmas().clear();
				return;
			case ZEvesProofProcessPackage.ZEVES_TRACE__TEXT:
				setText(TEXT_EDEFAULT);
				return;
			case ZEvesProofProcessPackage.ZEVES_TRACE__CASE:
				setCase(CASE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ZEvesProofProcessPackage.ZEVES_TRACE__USED_LEMMAS:
				return !getUsedLemmas().isEmpty();
			case ZEvesProofProcessPackage.ZEVES_TRACE__TEXT:
				return TEXT_EDEFAULT == null ? getText() != null : !TEXT_EDEFAULT.equals(getText());
			case ZEvesProofProcessPackage.ZEVES_TRACE__CASE:
				return CASE_EDEFAULT == null ? getCase() != null : !CASE_EDEFAULT.equals(getCase());
		}
		return super.eIsSet(featureID);
	}

} //ZEvesTraceImpl
