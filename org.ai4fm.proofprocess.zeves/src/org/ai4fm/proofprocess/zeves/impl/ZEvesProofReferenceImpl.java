/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves.impl;

import java.util.Collection;

import org.ai4fm.proofprocess.zeves.Position;
import org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage;
import org.ai4fm.proofprocess.zeves.ZEvesProofReference;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ZEves Proof Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ZEvesProofReferenceImpl#getFilePath <em>File Path</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ZEvesProofReferenceImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ZEvesProofReferenceImpl#getMarkup <em>Markup</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ZEvesProofReferenceImpl#getGoal <em>Goal</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ZEvesProofReferenceImpl#getUsedLemmas <em>Used Lemmas</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ZEvesProofReferenceImpl#getText <em>Text</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.zeves.impl.ZEvesProofReferenceImpl#getCase <em>Case</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ZEvesProofReferenceImpl extends EObjectImpl implements ZEvesProofReference {
	/**
	 * The default value of the '{@link #getFilePath() <em>File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilePath()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFilePath() <em>File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilePath()
	 * @generated
	 * @ordered
	 */
	protected String filePath = FILE_PATH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected Position position;

	/**
	 * The default value of the '{@link #getMarkup() <em>Markup</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMarkup()
	 * @generated
	 * @ordered
	 */
	protected static final String MARKUP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMarkup() <em>Markup</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMarkup()
	 * @generated
	 * @ordered
	 */
	protected String markup = MARKUP_EDEFAULT;

	/**
	 * The default value of the '{@link #getGoal() <em>Goal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGoal()
	 * @generated
	 * @ordered
	 */
	protected static final String GOAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGoal() <em>Goal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGoal()
	 * @generated
	 * @ordered
	 */
	protected String goal = GOAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getUsedLemmas() <em>Used Lemmas</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsedLemmas()
	 * @generated
	 * @ordered
	 */
	protected EList<String> usedLemmas;

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
	 * The cached value of the '{@link #getText() <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getText()
	 * @generated
	 * @ordered
	 */
	protected String text = TEXT_EDEFAULT;

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
	 * The cached value of the '{@link #getCase() <em>Case</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCase()
	 * @generated
	 * @ordered
	 */
	protected String case_ = CASE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ZEvesProofReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ZEvesProofProcessPackage.Literals.ZEVES_PROOF_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFilePath(String newFilePath) {
		String oldFilePath = filePath;
		filePath = newFilePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__FILE_PATH, oldFilePath, filePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPosition(Position newPosition, NotificationChain msgs) {
		Position oldPosition = position;
		position = newPosition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__POSITION, oldPosition, newPosition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPosition(Position newPosition) {
		if (newPosition != position) {
			NotificationChain msgs = null;
			if (position != null)
				msgs = ((InternalEObject)position).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__POSITION, null, msgs);
			if (newPosition != null)
				msgs = ((InternalEObject)newPosition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__POSITION, null, msgs);
			msgs = basicSetPosition(newPosition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__POSITION, newPosition, newPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMarkup() {
		return markup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMarkup(String newMarkup) {
		String oldMarkup = markup;
		markup = newMarkup;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__MARKUP, oldMarkup, markup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGoal() {
		return goal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGoal(String newGoal) {
		String oldGoal = goal;
		goal = newGoal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__GOAL, oldGoal, goal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getUsedLemmas() {
		if (usedLemmas == null) {
			usedLemmas = new EDataTypeUniqueEList<String>(String.class, this, ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__USED_LEMMAS);
		}
		return usedLemmas;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText() {
		return text;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setText(String newText) {
		String oldText = text;
		text = newText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__TEXT, oldText, text));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCase() {
		return case_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCase(String newCase) {
		String oldCase = case_;
		case_ = newCase;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__CASE, oldCase, case_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__POSITION:
				return basicSetPosition(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__FILE_PATH:
				return getFilePath();
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__POSITION:
				return getPosition();
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__MARKUP:
				return getMarkup();
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__GOAL:
				return getGoal();
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__USED_LEMMAS:
				return getUsedLemmas();
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__TEXT:
				return getText();
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__CASE:
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
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__FILE_PATH:
				setFilePath((String)newValue);
				return;
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__POSITION:
				setPosition((Position)newValue);
				return;
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__MARKUP:
				setMarkup((String)newValue);
				return;
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__GOAL:
				setGoal((String)newValue);
				return;
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__USED_LEMMAS:
				getUsedLemmas().clear();
				getUsedLemmas().addAll((Collection<? extends String>)newValue);
				return;
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__TEXT:
				setText((String)newValue);
				return;
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__CASE:
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
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__FILE_PATH:
				setFilePath(FILE_PATH_EDEFAULT);
				return;
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__POSITION:
				setPosition((Position)null);
				return;
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__MARKUP:
				setMarkup(MARKUP_EDEFAULT);
				return;
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__GOAL:
				setGoal(GOAL_EDEFAULT);
				return;
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__USED_LEMMAS:
				getUsedLemmas().clear();
				return;
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__TEXT:
				setText(TEXT_EDEFAULT);
				return;
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__CASE:
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
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__FILE_PATH:
				return FILE_PATH_EDEFAULT == null ? filePath != null : !FILE_PATH_EDEFAULT.equals(filePath);
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__POSITION:
				return position != null;
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__MARKUP:
				return MARKUP_EDEFAULT == null ? markup != null : !MARKUP_EDEFAULT.equals(markup);
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__GOAL:
				return GOAL_EDEFAULT == null ? goal != null : !GOAL_EDEFAULT.equals(goal);
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__USED_LEMMAS:
				return usedLemmas != null && !usedLemmas.isEmpty();
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__TEXT:
				return TEXT_EDEFAULT == null ? text != null : !TEXT_EDEFAULT.equals(text);
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__CASE:
				return CASE_EDEFAULT == null ? case_ != null : !CASE_EDEFAULT.equals(case_);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (filePath: ");
		result.append(filePath);
		result.append(", markup: ");
		result.append(markup);
		result.append(", goal: ");
		result.append(goal);
		result.append(", usedLemmas: ");
		result.append(usedLemmas);
		result.append(", text: ");
		result.append(text);
		result.append(", case: ");
		result.append(case_);
		result.append(')');
		return result.toString();
	}

} //ZEvesProofReferenceImpl
