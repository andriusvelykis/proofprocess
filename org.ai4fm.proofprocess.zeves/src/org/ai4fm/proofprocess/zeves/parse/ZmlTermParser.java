package org.ai4fm.proofprocess.zeves.parse;

import java.io.StringReader;
import java.io.StringWriter;

import org.xml.sax.InputSource;

import net.sourceforge.czt.base.ast.Term;
import net.sourceforge.czt.base.util.UnmarshalException;
import net.sourceforge.czt.parser.util.DeleteAnnVisitor;
import net.sourceforge.czt.zeves.jaxb.JaxbXmlReader;
import net.sourceforge.czt.zeves.jaxb.JaxbXmlWriter;

/**
 * @author Andrius Velykis
 */
public class ZmlTermParser {

	public static String convertToZml(Term cztTerm) {
		
		// strip all annotations (e.g. LocAnn, etc) during serialization
		cztTerm.accept(new DeleteAnnVisitor());
		
		// use the JAXB writer directly, instead of going via SectionManager
		// this allows us to avoid instantiating SectionManager
		// and we always use the "zeves" JAXB writer
		JaxbXmlWriter xmlWriter = new JaxbXmlWriter();
		
		StringWriter writer = new StringWriter();
		xmlWriter.write(cztTerm, writer);
		
		return writer.toString();
	}
	
	public static Term parseZml(String zml) {
		
		// use the JAXB reader directly to avoid instantiating a SectionManager
		JaxbXmlReader reader = new JaxbXmlReader();
		InputSource source = new InputSource(new StringReader(zml));
		try {
			return reader.read(source);
		} catch (UnmarshalException e) {
			// TODO handle the errors better?
			e.printStackTrace();
			return null;
		}
	}
	
}
