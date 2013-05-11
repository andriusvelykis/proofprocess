package org.ai4fm.proofprocess.zeves.core.parse

import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue

import scala.collection.JavaConversions._

import org.ai4fm.proofprocess.core.parse.TrackingToggle
import org.ai4fm.proofprocess.zeves.core.analysis.ProofAnalyzer
import org.ai4fm.proofprocess.zeves.core.internal.ZEvesPProcessCorePlugin.error
import org.eclipse.core.runtime.{CoreException, IProgressMonitor, IStatus, Status}
import org.eclipse.core.runtime.jobs.{IJobChangeEvent, Job, JobChangeAdapter}

import net.sourceforge.czt.session.SectionInfo
import net.sourceforge.czt.zeves.snapshot.{ISnapshotEntry, SnapshotChangedEvent}
import net.sourceforge.czt.zeves.snapshot.SnapshotChangedEvent.SnapshotChangeType._
import net.sourceforge.czt.zeves.snapshot.ZEvesSnapshot

import SnapshotUtil.{isError, isProof, proofEntries}


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
        // keep only non-error proof entries - the rest will be ignored
        val validEntries = changed.getEntries.filter(e => !isError(e) && isProof(e))

        if (!validEntries.isEmpty) {
          pendingEvents.add(SnapshotAnalysisEvent(
            snapshot.getSectionInfo,
            snapshot.getEntries.toList,
            validEntries.toList))
        }
      }
      case _ => {
        // ignore removals in analysis - not handling them at the moment anyway..
        // for remove, use the last proof left, if any
//        pendingEvents.add(SnapshotAnalysisEvent(changed, null, null, lastProof(snapshot)))
      }
    }

    // wake up the analysis job
    analysisJob.schedule()
  }

  /**
   * Collects proof spans for the changed entries.
   * 
   * The `changed` list must be reversed, so that the later changed elements are captured first.
   */
  private def collectProofSpans(snapshotEntries: List[ISnapshotEntry],
                                changed: List[ISnapshotEntry]): List[List[ISnapshotEntry]] =
    changed match {
      case Nil => Nil
      case c :: cl => {
        val proofSpan = proofEntries(snapshotEntries, c)
        // drop the ones in the proof span
        val restChanged = changed.filter(e => !proofSpan.contains(e))
        val restSpans = collectProofSpans(snapshotEntries, restChanged)

        proofSpan :: restSpans
      }
    }


  /**
   * Retrieves all pending events and merges them.
   * 
   * Uses the last available snapshot state and collects all changed entries
   */
  private def allPendingEvents(): Option[SnapshotAnalysisEvent] = {
    val allPending = pollAll(pendingEvents)

    if (allPending.isEmpty) {
      None
    } else {
      val allChangedLists = allPending map (_.changedEntries)
      // remove duplicates
      val allChanged = allChangedLists.flatten.distinct

      val lastPending = allPending.last
      Some(SnapshotAnalysisEvent(lastPending.sectInfo, lastPending.snapshotState, allChanged))
    }
  }

  private def pollAll[A](queue: Queue[A]): List[A] = Option(queue.poll()) match {
    case None => Nil
    case Some(elem) => elem :: pollAll(queue)
  }


  private class AnalysisJob extends Job("Analysing proof process") {

    // lowest priority
    setPriority(Job.DECORATE)

    override protected def run(monitor: IProgressMonitor): IStatus = {

      // merge pending events for performance improvements
//      val nextEvent = Option(pendingEvents.poll())
      val nextEvent = allPendingEvents()
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
    
    // collect proof spans (reverse entries as necessary)
    val proofSpans = collectProofSpans(event.snapshotState, event.changedEntries.reverse)

    // reverse proof spans to try getting the original order..
    val orderedSpans = proofSpans.reverse
    orderedSpans foreach { proofSpan =>
      // delegate to the proof analyzer
      ProofAnalyzer.analyze(event.sectInfo, proofSpan)
    }
    
    println("Analysing event " + (System.currentTimeMillis - start))

    Status.OK_STATUS
  }

}

case class SnapshotAnalysisEvent(sectInfo: SectionInfo,
                                 snapshotState: List[ISnapshotEntry],
                                 changedEntries: List[ISnapshotEntry])
