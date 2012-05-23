package org.ai4fm.proofprocess.ui

import org.eclipse.ui.plugin.AbstractUIPlugin


object PProcessUIPlugin {

  // The shared instance
  private var instance: PProcessUIPlugin = _
  def plugin = instance
    
}

class PProcessUIPlugin extends AbstractUIPlugin {

  PProcessUIPlugin.instance = this
  
  // The plug-in ID
  def pluginId = "org.ai4fm.proofprocess.ui" //$NON-NLS-1$
  
}
