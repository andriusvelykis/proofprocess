package org.ai4fm.proofprocess.zeves.core.parse

import net.sourceforge.czt.eclipse.zeves.ui.core.ZEvesSnapshot
import net.sourceforge.czt.eclipse.zeves.ui.core.ISnapshotChangedListener
import net.sourceforge.czt.eclipse.zeves.ui.core.SnapshotChangedEvent

/** @author Andrius Velykis
  */
class SnapshotEvents(snapshot: ZEvesSnapshot)(onEvent: SnapshotChangedEvent => Unit) {

  private lazy val snapshotListener = new ISnapshotChangedListener() {
    override def snapshotChanged(event: SnapshotChangedEvent) = onEvent(event)
  }

  def init() {
    snapshot.addSnapshotChangedListener(snapshotListener)
  }

  def dispose() {
    snapshot.removeSnapshotChangedListener(snapshotListener)
  }

}
