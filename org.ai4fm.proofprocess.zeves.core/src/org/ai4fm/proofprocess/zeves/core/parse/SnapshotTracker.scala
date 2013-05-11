package org.ai4fm.proofprocess.zeves.core.parse

import java.util.concurrent.ConcurrentLinkedQueue

import scala.collection.JavaConversions._

import org.ai4fm.proofprocess.core.parse.TrackingToggle
import org.ai4fm.proofprocess.zeves.core.analysis.ProofAnalyzer
import org.ai4fm.proofprocess.zeves.core.internal.ZEvesPProcessCorePlugin.error
import org.eclipse.core.runtime.{CoreException, IProgressMonitor, IStatus, Status}
import org.eclipse.core.runtime.jobs.{IJobChangeEvent, Job, JobChangeAdapter}

import SnapshotUtil._
import net.sourceforge.czt.session.SectionInfo
import net.sourceforge.czt.zeves.snapshot.ISnapshotEntry
import net.sourceforge.czt.zeves.snapshot.SnapshotChangedEvent
import net.sourceforge.czt.zeves.snapshot.SnapshotChangedEvent.SnapshotChangeType._
import net.sourceforge.czt.zeves.snapshot.ZEvesSnapshot

/** @author Andrius Velykis
  */
class SnapshotTracker(snapshot: ZEvesSnapshot) {

  /** Reacts to snapshot events, e.g. when proofs are added/removed */
  private lazy val snapshotEvents = new SnapshotEvents(snapshot)(
    snapshotEvent => addPendingAnalysis(snapshotEvent))

  /** Reacts to preference changes whether ProofProcess tracking is enabled */
  private val tracking = TrackingToggle { isTracking =>
    if (!isTracking) {
      // no longer tracking, so discard all pending events
      pendingEvents.clear
    }
  }

  def init() {
    snapshotEvents.init
    tracking.init
  }
  
  def dispose() {
    tracking.dispose
    snapshotEvents.dispose
  }

  /** A concurrent queue is used for pending events, because the queue is
    * likely to be updated from multiple threads.
    */
  private val pendingEvents = new ConcurrentLinkedQueue[SnapshotAnalysisEvent]

  private val analysisJob = new AnalysisJob
  // add a listener that reschedules the job after completion if there are pending events
  analysisJob.addJobChangeListener(jobDoneListener {
    if (!pendingEvents.isEmpty) {
      // there are pending events, reschedule the job to consume them
      analysisJob.schedule
    }
  })

  private def addPendingAnalysis(changed: SnapshotChangedEvent) = if (tracking.isTracking) {

    changed.getType match {
      case ADD => {
        val sectInfo = snapshot.getSectionInfo
        // keep only non-error proof entries - the rest will be ignored
        val nonErrEntries = changed.getEntries.filter(e => !isError(e) && isProof(e))
        nonErrEntries foreach { entry =>
          // TODO collect entries from the same proof together?
          pendingEvents.add(SnapshotAnalysisEvent(changed, sectInfo, entry, proofEntries(snapshot)(entry)))
        }
      }
      case REMOVE => {
        // for remove, use the last proof left, if any
        // FIXME nulls
        pendingEvents.add(SnapshotAnalysisEvent(changed, null, null, lastProof(snapshot)))
      }
    }

    // wake up the analysis job
    analysisJob.schedule()
  }

  private class AnalysisJob extends Job("Analysing proof process") {

    // lowest priority
    setPriority(Job.DECORATE)

    override protected def run(monitor: IProgressMonitor): IStatus = {

      val nextEvent = Option(pendingEvents.poll())
      nextEvent match {
        // nothing to execute
        case None => Status.OK_STATUS
        case Some(event) => try {
          // TODO check if shutting down and cancel the job
          analyze(event)(monitor)
        } catch {
          case e: CoreException => error(Some(e), Some("Proof process analysis failed: " + e.getMessage))
        }
      }
    }
  }

  private def jobDoneListener(f: => Unit) =
    new JobChangeAdapter {
      override def done(event: IJobChangeEvent) = f
    }

  @throws(classOf[CoreException])
  private def analyze(event: SnapshotAnalysisEvent)(implicit monitor: IProgressMonitor): IStatus = {

    val start = System.currentTimeMillis

    val resultStatus = event.event.getType match {
      case ADD => {
        // delegate to the proof analyzer
        ProofAnalyzer.analyze(event.sectInfo, event.entryProof, event.entry)
      }
      case _ => {
        // do not support other types at the moment
        Status.OK_STATUS
      }
    }

    println("Analysing event " + event.event.getType + " -- " + (System.currentTimeMillis - start))

    resultStatus
  }

}

case class SnapshotAnalysisEvent(event: SnapshotChangedEvent, sectInfo: SectionInfo,
                                 entry: ISnapshotEntry, entryProof: List[ISnapshotEntry])
