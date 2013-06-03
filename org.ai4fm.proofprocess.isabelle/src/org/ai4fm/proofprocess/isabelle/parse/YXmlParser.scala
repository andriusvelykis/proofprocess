package org.ai4fm.proofprocess.isabelle.parse

import isabelle.{XML, YXML}


/**
 * Encodes/decodes Isabelle XML structures to/from YXML format.
 *
 * The format uses replacement markers which differ from default YXML chunk markers. This is
 * necessary in order to embed the YXML string in an XML file. The default chunk markers are control
 * characters and thus are not allowed in XML1.0 document. XML1.1 would support them, but it Java
 * XML parsers have bugs with XML1.1.
 *
 * @author Andrius Velykis
 */
object YXmlParser {

  /*
   * Replacement chunk markers. Using non-unicode (special) characters to avoid clashes with text.
   */
  private val X = '\uFDEE';
  private val Y = '\uFDEF';

  def convertToYXml(term: XML.Tree): String = safeEncode(YXML.string_of_tree(term))

  def parseYXml(yxml: String): XML.Tree = YXML.parse_failsafe(safeDecode(yxml))

  def convertBodyToYXml(term: XML.Body): String = safeEncode(YXML.string_of_body(term))

  def parseBodyYXml(yxml: String): XML.Body = YXML.parse_body_failsafe(safeDecode(yxml))

  private def safeEncode(yxml: String): String = yxml.replace(YXML.X, X).replace(YXML.Y, Y)

  private def safeDecode(yxml: String): String = yxml.replace(X, YXML.X).replace(Y, YXML.Y)

  def isYXml(encoded: String): Boolean = 
    !encoded.isEmpty && (encoded.charAt(0) == X || encoded.charAt(0) == Y)

}
