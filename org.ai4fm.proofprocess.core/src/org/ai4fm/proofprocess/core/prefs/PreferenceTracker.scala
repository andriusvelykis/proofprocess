package org.ai4fm.proofprocess.core.prefs

import org.eclipse.core.runtime.preferences.IEclipsePreferences
import org.eclipse.core.runtime.preferences.IEclipsePreferences.{IPreferenceChangeListener, PreferenceChangeEvent}

/**
 * @author Andrius Velykis
 */
class PreferenceTracker(prefs: IEclipsePreferences, prefKey: String)(changeHandler: () => Unit) {

  val prefListener = new IPreferenceChangeListener {
    override def preferenceChange(event: PreferenceChangeEvent) {
      if (prefKey == event.getKey()) {
        changeHandler()
      }
    }
  }

  prefs.addPreferenceChangeListener(prefListener)

  def dispose() {
    prefs.removePreferenceChangeListener(prefListener)
  }

}
