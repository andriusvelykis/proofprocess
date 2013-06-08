package org.ai4fm.proofprocess.isabelle.parse

import org.ai4fm.proofprocess.parse.StringCompression

import org.eclipse.emf.ecore.EDataType

import isabelle.XML


/**
 * A conversion delegate to serialize Isabelle XML data types.
 * 
 * @author Andrius Velykis
 */
object IsabelleXMLConversionDelegate extends EDataType.Internal.ConversionDelegate {

  override def convertToString(value: Any): String = value match {
    case isaXml: XML.Tree => convertIsabelleXMLToString(isaXml)
  }

  override def createFromString(literal: String): AnyRef =
    createIsabelleXMLFromString(literal)

  def createIsabelleXMLFromString(initialValue: String): XML.Tree = {
    val yxml = decompressYXml(initialValue)
    YXmlParser.parseYXml(yxml)
  }

  /**
   * Decompresses YXML String. Does nothing if already uncompressed.
   */
  def decompressYXml(value: String): String =
    if (YXmlParser.isYXml(value)) {
      // already uncompressed
      value
    } else {
      // compressed
      StringCompression.decompress(value)
    }


  def convertIsabelleXMLToString(instanceValue: XML.Tree): String = {
    val yxml = YXmlParser.convertToYXml(instanceValue)
    // compress the YXML string, since it tends to occupy the majority of storage space
    // the compression can be up to 95% efficient on large Strings
    StringCompression.compress(yxml)
  }

}
