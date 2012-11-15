package org.ai4fm.proofprocess.core.parse

import org.ai4fm.proofprocess.core.prefs.PProcessCorePreferences._

import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent

/** A trait that encapsulates toggle of ProofProcess tracking preference.
  * 
  * @author Andrius Velykis
  */
trait TrackingToggle {

  // check core preferences whether proof process tracking is enabled
  private def trackingPref() = getBoolean(TRACK_PROOF_PROCESS, false)
  private var tracking = trackingPref()
  
  // listener for "tracking" preference change
  private lazy val prefsListener = prefKeyListener(TRACK_PROOF_PROCESS) {
    tracking = trackingPref()
    
    handleTrackingChanged(tracking)
  }

  def init() {
    preferences.addPreferenceChangeListener(prefsListener)
  }
  def dispose() {
    preferences.removePreferenceChangeListener(prefsListener)
  }
  
  def isTracking = tracking
  
  def handleTrackingChanged(tracking: Boolean) {}
  
  private def prefKeyListener(key: String)(f: => Unit) =
    new IPreferenceChangeListener {
      def preferenceChange(event: PreferenceChangeEvent) {
        if (event.getKey == key) {
          f
        }
      }
    }
  
}

object TrackingToggle {

  def apply(trackingChanged: Boolean => Unit): TrackingToggle =
    new TrackingToggle {
      override def handleTrackingChanged(tracking: Boolean) = trackingChanged(tracking)
    }
}
