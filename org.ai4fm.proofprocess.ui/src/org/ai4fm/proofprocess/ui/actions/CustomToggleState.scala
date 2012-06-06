package org.ai4fm.proofprocess.ui.actions

import org.eclipse.core.commands.IStateListener
import org.eclipse.core.commands.State

/** A command [[org.eclipse.core.commands.State]] that initialises value from preferences
  * and updates the preferences when the command state changes. Used for ''toggle'' commands
  * and thus works with `Boolean` preference values.
  * 
  * @author Andrius Velykis
  */
class CustomToggleState(prefGet: => Boolean, prefSet: Boolean => Unit) extends State {
  
  // initialise with value from preferences
  setValue(prefGet)
  
  addListener(new IStateListener {
    override def handleStateChange(state: State, oldValue: Any) {
      
      // cast to Any, otherwise it is Object and cannot match with scala.Boolean, which is AnyVal
      val newVal: Any = getValue
      newVal match {
        case newState: Boolean => prefSet(newState)
        case _ => // ignore
      }
    }
  })

}

import org.ai4fm.proofprocess.core.prefs.PProcessCorePreferences._

/** A toggle command state for `TRACK_PROOF_PROCESS` preference.
  * 
  * @author Andrius Velykis
  */
class TrackProofProcessState
  // read/write preferences in Core plugin
  extends CustomToggleState(getBoolean(TRACK_PROOF_PROCESS, true), preferences.putBoolean(TRACK_PROOF_PROCESS, _))
