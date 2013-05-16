package org.ai4fm.proofprocess.zeves.ui.internal

import org.ai4fm.proofprocess.zeves.core.ZEvesPProcessCore

import org.eclipse.ui.IStartup


/**
 * Plug-in auto-start class.
 * 
 * @author Andrius Velykis
 */
class ZEvesPProcessUIStartup extends IStartup {

  override def earlyStartup() {
    // for auto-start, initialise Isabelle ProofProcess Core plug-in in turn.
    // This is needed because auto-start requires UI dependencies,
    // while pp.isabelle.core initialises the tracking support.
    // Note that using OSGI Declarative Services in pp.isabelle.core messes up Eclipse plug-in
    // startup sequence and default workspace is used instead of the selected one..

    ZEvesPProcessCore.init()
  }
  
}
