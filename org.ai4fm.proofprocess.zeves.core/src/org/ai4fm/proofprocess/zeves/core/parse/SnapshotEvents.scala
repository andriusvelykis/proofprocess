package org.ai4fm.proofprocess.zeves.core.parse

import net.sourceforge.czt.zeves.snapshot.{ISnapshotChangedListener, SnapshotChangedEvent, ZEvesSnapshot}


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
