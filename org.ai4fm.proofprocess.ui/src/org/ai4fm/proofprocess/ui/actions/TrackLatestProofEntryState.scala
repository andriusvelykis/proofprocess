package org.ai4fm.proofprocess.ui.actions

import org.ai4fm.proofprocess.ui.prefs.PProcessUIPreferences._

/**
 * A toggle command state for `TRACK_LATEST_PROOF_ENTRY` preference.
 *
 * @author Andrius Velykis
 */
class TrackLatestProofEntryState extends CustomToggleState(getBoolean(TRACK_LATEST_PROOF_ENTRY, true),
                                                           prefs.putBoolean(TRACK_LATEST_PROOF_ENTRY, _))

