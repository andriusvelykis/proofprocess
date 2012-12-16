package org.ai4fm.proofprocess.ui.actions

import org.ai4fm.proofprocess.core.prefs.PProcessCorePreferences._

/**
 * A toggle command state for `TRACK_PROOF_PROCESS` preference.
 *
 * Read/write preferences in Core plugin.
 *
 * @author Andrius Velykis
 */
class TrackPProcessState extends CustomToggleState(getBoolean(TRACK_PROOF_PROCESS, true),
                                                   preferences.putBoolean(TRACK_PROOF_PROCESS, _))

