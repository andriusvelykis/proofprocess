package org.ai4fm.proofhistory.isabelle.scala

import isabelle._

import org.ai4fm.{proofprocess => pp}
import pp.isabelle.IsabelleProofProcessFactory
import pp.isabelle.IsaTerm

object TermParser {

  private val FACTORY = IsabelleProofProcessFactory.eINSTANCE
  
  def parse(source: XML.Tree): pp.Term =
  {
    val term = FACTORY.createIsaTerm();
    term.setDisplay("Test")
    
    term
  }
  
}
