package org.ai4fm.proofprocess.zeves.parse;

import net.sourceforge.czt.base.ast.Term;

import org.ai4fm.proofprocess.parse.StringCompression;
import org.eclipse.emf.ecore.EDataType;


/**
 * A conversion delegate to serialize ZML data types.
 *  
 * @author Andrius Velykis
 */
public class ZmlTermConversionDelegate implements EDataType.Internal.ConversionDelegate {

	@Override
	public String convertToString(Object value) {
		return convertZmlTermToString(value);
	}

	@Override
	public Object createFromString(String literal) {
		return createZmlTermFromString(literal);
	}


	public static String convertZmlTermToString(Object instanceValue) {
		String zml = ZmlTermParser.convertToZml((Term) instanceValue);
		// compress the ZML string, since it tends to occupy the majority of storage space
		// the compression can be up to 95% efficient on large Strings
		return StringCompression.compress(zml);
	}

	public static Term createZmlTermFromString(String initialValue) {
		
		// check if uncompressed ZML value (backwards compatibility) or a compressed one
		String zml;
		if (initialValue.startsWith("<?xml")) {
			zml = initialValue;
		} else {
			// compressed
			zml = StringCompression.decompress(initialValue);
		}
		
		return ZmlTermParser.parseZml(zml);
	}
}
