package org.ai4fm.proofprocess.core.prefs

import org.ai4fm.proofprocess.core.PProcessCorePlugin
import org.eclipse.core.runtime.Platform
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer
import org.eclipse.core.runtime.preferences.DefaultScope
import org.eclipse.core.runtime.preferences.InstanceScope
import org.eclipse.core.runtime.preferences.IScopeContext

/**
  * @author Andrius Velykis 
  */
object PProcessCorePreferences {
  
  private def pluginId = PProcessCorePlugin.plugin.pluginId
  
  private[prefs] def prefNode(prefScope: IScopeContext) = prefScope.getNode(pluginId)
  
  // Instance scope by itself does not include default values.
  // Need to use IPreferencesService to access values - but can use the prefs for listeners
  lazy val preferences = prefNode(InstanceScope.INSTANCE)
  
  /** Retrieves Boolean value for ProofProcess Core preferences (supports Default values) */
  def getBoolean(key: String, default: Boolean) =
    Platform.getPreferencesService.getBoolean(pluginId, key, default, null)
  
  /** Preference indicating whether to track proof process live */
  val TRACK_PROOF_PROCESS = pluginId + ".trackProofProcess"
  
}

/**
  * @author Andrius Velykis 
  */
class PProcessCorePreferenceInitializer extends AbstractPreferenceInitializer {
  
  override def initializeDefaultPreferences() {
    
    import PProcessCorePreferences._
    
    val prefDefaults = prefNode(DefaultScope.INSTANCE)
    
    // track proof process by default
    prefDefaults.putBoolean(TRACK_PROOF_PROCESS, true)
  }
}
