package org.ai4fm.proofprocess.isabelle.parse;

import scala.collection.immutable.List;
import isabelle.XML.Tree;
import isabelle.YXML;

/**
 * Encodes/decodes Isabelle XML structures to/from YXML format.
 * <p>
 * The format uses replacement markers which differ from default YXML chunk markers. This is
 * necessary in order to embed the YXML string in an XML file. The default chunk markers are control
 * characters and thus are not allowed in XML1.0 document. XML1.1 would support them, but it Java
 * XML parsers have bugs with XML1.1.
 * </p>
 * 
 * @author Andrius Velykis
 */
public class YXmlParser {

	/*
	 * Replacement chunk markers. Using non-unicode (special) characters to avoid clashes with text.
	 */
	private static final char X = '\uFDEE';
	private static final char Y = '\uFDEF';
	
	public static String convertToYXml(Tree term) { 
		return safeEncode(YXML.string_of_tree(term));
	}
	
	public static Tree parseYXml(String yxml) {
		return YXML.parse_failsafe(safeDecode(yxml));
	}
	
	public static String convertBodyToYXml(List<Tree> term) { 
		return safeEncode(YXML.string_of_body(term));
	}
	
	public static List<Tree> parseBodyYXml(String yxml) {
		return YXML.parse_body_failsafe(safeDecode(yxml));
	}
	
	private static String safeEncode(String yxml) {
		return yxml.replace(YXML.X(), X).replace(YXML.Y(), Y);
	}
	
	private static String safeDecode(String yxml) {
		return yxml.replace(X, YXML.X()).replace(Y, YXML.Y());
	}
	
}
