package org.ai4fm.proofprocess.isabelle.parse

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EDataType.Internal.ConversionDelegate;

/**
 * A conversion delegate factory for custom-serialization Isabelle data types.
 *
 * @author Andrius Velykis
 */
class IsabelleConversionDelegateFactory extends ConversionDelegate.Factory {

  override def createConversionDelegate(eDataType: EDataType): ConversionDelegate =
    eDataType.getName match {

      case "IsabelleTerm" => IsabelleTermConversionDelegate

      case "IsabelleXML" => IsabelleXMLConversionDelegate

      case unknown => throw new IllegalArgumentException(
        "The datatype '" + unknown + "' is not a valid classifier");
    }

}
