package org.ai4fm.proofprocess.isabelle.parse

import isabelle.Term.Term

import org.ai4fm.proofprocess.parse.StringCompression

import org.eclipse.emf.ecore.EDataType

import IsabelleXMLConversionDelegate.decompressYXml


/**
 * A conversion delegate to serialize Isabelle Term data type.
 * 
 * @author Andrius Velykis
 */
object IsabelleTermConversionDelegate extends EDataType.Internal.ConversionDelegate {

  override def convertToString(value: Any): String = value match {
    case isaTerm: Term => convertIsabelleTermToString(isaTerm)
  }

  override def createFromString(literal: String): AnyRef =
    createIsabelleTermFromString(literal)


  def convertIsabelleTermToString(instanceValue: Term): String = {
    val yxml = IsabelleTermParser.convertToYXml(instanceValue)
    // compress the YXML string, since it tends to occupy the majority of storage space
    // the compression can be up to 95% efficient on large Strings
    StringCompression.compress(yxml)
  }

  def createIsabelleTermFromString(initialValue: String): Term = {
    val yxml = decompressYXml(initialValue)
    IsabelleTermParser.parseYXml(yxml)
  }

}
