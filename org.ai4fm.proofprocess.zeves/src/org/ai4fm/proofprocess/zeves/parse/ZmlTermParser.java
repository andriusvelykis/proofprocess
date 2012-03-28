package org.ai4fm.proofprocess.zeves.parse;

import java.io.IOException;

import net.sourceforge.czt.base.ast.Term;
import net.sourceforge.czt.base.util.UnmarshalException;
import net.sourceforge.czt.parser.util.DeleteAnnVisitor;
import net.sourceforge.czt.parser.util.ParseException;
import net.sourceforge.czt.parser.zeves.ParseUtils;
import net.sourceforge.czt.print.util.XmlString;
import net.sourceforge.czt.session.CommandException;
import net.sourceforge.czt.session.Key;
import net.sourceforge.czt.session.Markup;
import net.sourceforge.czt.session.SectionManager;
import net.sourceforge.czt.session.Source;
import net.sourceforge.czt.session.StringSource;

/**
 * @author Andrius Velykis
 */
public class ZmlTermParser {

	private static final String ZML_PRINT_KEY = "term-zml";
	
	public static String convertToZml(Term cztTerm) {
		
		// strip all annotations (e.g. LocAnn, etc) during serialization
		cztTerm.accept(new DeleteAnnVisitor());
		
		// create new section manager
		SectionManager sectInfo = new SectionManager("zeves");
		
		sectInfo.put(new Key<Term>(ZML_PRINT_KEY, Term.class), cztTerm);
		try {
			
			XmlString printed = sectInfo.get(new Key<XmlString>(ZML_PRINT_KEY, XmlString.class));
			return printed.toString();
			
		} catch (CommandException e) {
			// TODO log?
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Term parseZml(String zml) {
		
		// create new section manager
		SectionManager sectInfo = new SectionManager("zeves");
		
		Source zmlSource = new StringSource(zml, ZML_PRINT_KEY);
		zmlSource.setMarkup(Markup.ZML);
		
		// TODO use SectionInfo (arbitrary ZML (non-Spec/ZSect) not supported at the moment)
		try {
			return ParseUtils.parse(zmlSource, sectInfo);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnmarshalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
