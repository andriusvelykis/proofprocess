package org.ai4fm.proofprocess.isabelle.core

/**
 * A handler to get user input on patching actions. 
 * 
 * @author Andrius Velykis
 */
trait IPatchActionHandler {

  def performPatch(files: List[String]): Boolean

  def reportPatchCompleted()
  
  def reportPatchProblem(msg: String)

}
