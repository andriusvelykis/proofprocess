package org.ai4fm.proofprocess.zeves.parse;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EDataType.Internal.ConversionDelegate;


/**
 * A conversion delegate factory for custom-serialization Z/EVES data types.
 *  
 * @author Andrius Velykis
 */
public class ZEvesConversionDelegateFactory implements ConversionDelegate.Factory {

	@Override
	public ConversionDelegate createConversionDelegate(EDataType eDataType) {
		if ("ZmlTerm".equals(eDataType.getName())) {
			return new ZmlTermConversionDelegate();
		} else {
			throw new IllegalArgumentException(
					"The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

}
