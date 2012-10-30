/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.project.impl;

import org.ai4fm.proofprocess.project.Position;
import org.ai4fm.proofprocess.project.ProjectProofProcessPackage;
import org.ai4fm.proofprocess.project.TextLoc;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Text Loc</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.ai4fm.proofprocess.project.impl.TextLocImpl#getFilePath <em>File Path</em>}</li>
 *   <li>{@link org.ai4fm.proofprocess.project.impl.TextLocImpl#getPosition <em>Position</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TextLocImpl extends CDOObjectImpl implements TextLoc {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TextLocImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProjectProofProcessPackage.Literals.TEXT_LOC;
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
	public String getFilePath() {
		return (String)eDynamicGet(ProjectProofProcessPackage.TEXT_LOC__FILE_PATH, ProjectProofProcessPackage.Literals.TEXT_LOC__FILE_PATH, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFilePath(String newFilePath) {
		eDynamicSet(ProjectProofProcessPackage.TEXT_LOC__FILE_PATH, ProjectProofProcessPackage.Literals.TEXT_LOC__FILE_PATH, newFilePath);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Position getPosition() {
		return (Position)eDynamicGet(ProjectProofProcessPackage.TEXT_LOC__POSITION, ProjectProofProcessPackage.Literals.TEXT_LOC__POSITION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPosition(Position newPosition, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newPosition, ProjectProofProcessPackage.TEXT_LOC__POSITION, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPosition(Position newPosition) {
		eDynamicSet(ProjectProofProcessPackage.TEXT_LOC__POSITION, ProjectProofProcessPackage.Literals.TEXT_LOC__POSITION, newPosition);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProjectProofProcessPackage.TEXT_LOC__POSITION:
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
			case ProjectProofProcessPackage.TEXT_LOC__FILE_PATH:
				return getFilePath();
			case ProjectProofProcessPackage.TEXT_LOC__POSITION:
				return getPosition();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ProjectProofProcessPackage.TEXT_LOC__FILE_PATH:
				setFilePath((String)newValue);
				return;
			case ProjectProofProcessPackage.TEXT_LOC__POSITION:
				setPosition((Position)newValue);
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
			case ProjectProofProcessPackage.TEXT_LOC__FILE_PATH:
				setFilePath(FILE_PATH_EDEFAULT);
				return;
			case ProjectProofProcessPackage.TEXT_LOC__POSITION:
				setPosition((Position)null);
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
			case ProjectProofProcessPackage.TEXT_LOC__FILE_PATH:
				return FILE_PATH_EDEFAULT == null ? getFilePath() != null : !FILE_PATH_EDEFAULT.equals(getFilePath());
			case ProjectProofProcessPackage.TEXT_LOC__POSITION:
				return getPosition() != null;
		}
		return super.eIsSet(featureID);
	}

} //TextLocImpl
