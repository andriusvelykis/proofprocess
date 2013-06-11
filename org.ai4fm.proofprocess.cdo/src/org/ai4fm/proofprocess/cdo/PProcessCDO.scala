package org.ai4fm.proofprocess.cdo

import java.net.URI

import org.ai4fm.proofprocess.cdo.internal.PProcessCDOPlugin

import org.eclipse.core.runtime.{IProgressMonitor, NullProgressMonitor}
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
   * Initialises a new repository if one does not exist, migrates existing file-based data or
   * upgrades the repository if it uses old EMF packages.
   */
  def session(databaseLoc: URI,
              repositoryName: String,
              monitor: IProgressMonitor = new NullProgressMonitor): CDOSession =
    PProcessCDOPlugin.plugin.session(databaseLoc, repositoryName)


  /**
   * Forces upgrade of the repository.
   * 
   * Note that this action may also help compact the repository in the database.
   * 
   * The existing CDO sessions on the repository will not work after upgrade:
   * need to reinitialise new CDO sessions.
   */
  def upgradeRepository(databaseLoc: URI,
                        repositoryName: String,
                        monitor: IProgressMonitor = new NullProgressMonitor) =
    PProcessCDOPlugin.plugin.upgradeRepository(databaseLoc, repositoryName)

}
