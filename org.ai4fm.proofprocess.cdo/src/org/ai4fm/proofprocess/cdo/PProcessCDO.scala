package org.ai4fm.proofprocess.cdo

import java.net.URI

import org.eclipse.emf.cdo.session.CDOSession


/**
 * A facade to access ProofProcess CDO link.
 * 
 * @author Andrius Velykis
 */
object PProcessCDO {

  /**
   * Retrieves an open session for the given repository.
   * 
   * Initialises a new repository if one does not exist.
   */
  def session(databaseLoc: URI, repositoryName: String): CDOSession =
    PProcessCDOPlugin.plugin.session(databaseLoc, repositoryName)
  
}
