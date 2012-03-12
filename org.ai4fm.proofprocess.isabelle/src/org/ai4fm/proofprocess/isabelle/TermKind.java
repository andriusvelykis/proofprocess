/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.isabelle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Term Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage#getTermKind()
 * @model
 * @generated
 */
public enum TermKind implements Enumerator {
	/**
	 * The '<em><b>CONST</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONST_VALUE
	 * @generated
	 * @ordered
	 */
	CONST(0, "CONST", "CONST"),

	/**
	 * The '<em><b>BOUND</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BOUND_VALUE
	 * @generated
	 * @ordered
	 */
	BOUND(1, "BOUND", "BOUND"),

	/**
	 * The '<em><b>VAR</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VAR_VALUE
	 * @generated
	 * @ordered
	 */
	VAR(2, "VAR", "VAR"), /**
	 * The '<em><b>FIXED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FIXED_VALUE
	 * @generated
	 * @ordered
	 */
	FIXED(3, "FIXED", "FIXED"), /**
	 * The '<em><b>FREE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FREE_VALUE
	 * @generated
	 * @ordered
	 */
	FREE(4, "FREE", "FREE"), /**
	 * The '<em><b>NUMERAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NUMERAL_VALUE
	 * @generated
	 * @ordered
	 */
	NUMERAL(5, "NUMERAL", "NUMERAL"), /**
	 * The '<em><b>SKOLEM</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SKOLEM_VALUE
	 * @generated
	 * @ordered
	 */
	SKOLEM(6, "SKOLEM", "SKOLEM");

	/**
	 * The '<em><b>CONST</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CONST</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONST
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CONST_VALUE = 0;

	/**
	 * The '<em><b>BOUND</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BOUND</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BOUND
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BOUND_VALUE = 1;

	/**
	 * The '<em><b>VAR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>VAR</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VAR
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int VAR_VALUE = 2;

	/**
	 * The '<em><b>FIXED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FIXED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FIXED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FIXED_VALUE = 3;

	/**
	 * The '<em><b>FREE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FREE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FREE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FREE_VALUE = 4;

	/**
	 * The '<em><b>NUMERAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NUMERAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NUMERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NUMERAL_VALUE = 5;

	/**
	 * The '<em><b>SKOLEM</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SKOLEM</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SKOLEM
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SKOLEM_VALUE = 6;

	/**
	 * An array of all the '<em><b>Term Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TermKind[] VALUES_ARRAY =
		new TermKind[] {
			CONST,
			BOUND,
			VAR,
			FIXED,
			FREE,
			NUMERAL,
			SKOLEM,
		};

	/**
	 * A public read-only list of all the '<em><b>Term Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TermKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Term Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TermKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TermKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Term Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TermKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TermKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Term Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TermKind get(int value) {
		switch (value) {
			case CONST_VALUE: return CONST;
			case BOUND_VALUE: return BOUND;
			case VAR_VALUE: return VAR;
			case FIXED_VALUE: return FIXED;
			case FREE_VALUE: return FREE;
			case NUMERAL_VALUE: return NUMERAL;
			case SKOLEM_VALUE: return SKOLEM;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private TermKind(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //TermKind
