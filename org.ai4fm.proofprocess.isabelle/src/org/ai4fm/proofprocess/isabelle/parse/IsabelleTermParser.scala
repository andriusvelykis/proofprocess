package org.ai4fm.proofprocess.isabelle.parse

import isabelle.Term.Term
import isabelle.Term_XML

/** Encodes/decodes Isabelle Term into YXML.
 * <p>
 * The produced YXML is safe to embed into XML1.0 documents.
 * See {@link org.ai4fm.proofprocess.isabelle.parse.YXmlParser} for more info.
 * </p>
 * 
 * @author Andrius Velykis
 */
object IsabelleTermParser {

  def convertToYXml(term: Term): String = {
    val encoded = Term_XML.Encode.term(term)
    YXmlParser.convertBodyToYXml(encoded)
  }
  
  def parseYXml(yxml: String): Term = {
    val decoded = YXmlParser.parseBodyYXml(yxml)
    Term_XML.Decode.term(decoded)
  }
  
}
