package org.ai4fm.proofprocess.ui.internal

import org.eclipse.ui.IStartup

/**
 * An early-start entry point for ProofProcess UI plugin.
 * 
 * Notifies the user if appropriate Java PermGen space is not available.
 * 
 * @author Andrius Velykis
 */
class PProcessUIStartup extends IStartup {

  def earlyStartup() {
    PProcessDiagnostics.checkPermGenSize()
  }

}
