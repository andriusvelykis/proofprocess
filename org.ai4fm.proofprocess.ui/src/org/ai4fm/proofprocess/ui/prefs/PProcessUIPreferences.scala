package org.ai4fm.proofprocess.ui.prefs

import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin
import org.eclipse.core.runtime.Platform
import org.eclipse.core.runtime.preferences.{AbstractPreferenceInitializer, DefaultScope, IScopeContext, InstanceScope}

/**
  * @author Andrius Velykis 
  */
object PProcessUIPreferences {
  
  private def pluginId = PProcessUIPlugin.plugin.pluginId
  
  private[prefs] def prefNode(prefScope: IScopeContext) = prefScope.getNode(pluginId)
  
  // Instance scope by itself does not include default values.
  // Need to use IPreferencesService to access values - but can use the prefs for listeners
  lazy val prefs = prefNode(InstanceScope.INSTANCE)
  
  /** Retrieves Boolean value for ProofProcess UI preferences (supports Default values) */
  def getBoolean(key: String, default: Boolean) =
    Platform.getPreferencesService.getBoolean(pluginId, key, default, null)
  
  /** Preference indicating whether to track proof process live */
  val TRACK_LATEST_PROOF_ENTRY = pluginId + ".trackLatestProofEntry"
  
}

/**
  * @author Andrius Velykis 
  */
class PProcessUIPreferenceInitializer extends AbstractPreferenceInitializer {
  
  override def initializeDefaultPreferences() {
    
    import PProcessUIPreferences._
    
    val prefDefaults = prefNode(DefaultScope.INSTANCE)
    
    // track latest proof entry by default
    prefDefaults.putBoolean(TRACK_LATEST_PROOF_ENTRY, true)
  }
}
