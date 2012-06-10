package org.ai4fm.proofprocess.isabelle.ui

import org.eclipse.ui.plugin.AbstractUIPlugin


object IsabellePProcessUIPlugin {

  // The shared instance
  private var instance: IsabellePProcessUIPlugin = _
  def plugin = instance
    
}

class IsabellePProcessUIPlugin extends AbstractUIPlugin {

  IsabellePProcessUIPlugin.instance = this
  
  // The plug-in ID
  def pluginId = "org.ai4fm.proofprocess.isabelle.ui" //$NON-NLS-1$
  
}
