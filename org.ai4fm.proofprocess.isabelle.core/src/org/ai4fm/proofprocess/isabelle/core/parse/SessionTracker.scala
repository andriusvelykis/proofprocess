package org.ai4fm.proofprocess.isabelle.core.parse

import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue

import scala.actors.Actor._

import org.ai4fm.proofprocess.core.parse.TrackingToggle
import org.ai4fm.proofprocess.isabelle.core.IsabellePProcessCorePlugin.error
import org.ai4fm.proofprocess.isabelle.core.analysis.ProofAnalyzer
import org.ai4fm.proofprocess.isabelle.core.patch.IsabellePatcher
import org.eclipse.core.runtime.{CoreException, IProgressMonitor, IStatus, Status}
import org.eclipse.core.runtime.jobs.{IJobChangeEvent, Job, JobChangeAdapter}

import isabelle.{Command, Document, Session}
import isabelle.eclipse.core.IsabelleCore
import isabelle.eclipse.core.util.{LoggingActor, SessionEvents}


/**
  * @author Andrius Velykis 
  */
class SessionTracker extends SessionEvents {

  // the actor to react to session events
  // make it lazy, otherwise there are problems with plug-in loading sequence
  override protected lazy val sessionActor = LoggingActor {
    loop {
      react {
        case changed: Session.Commands_Changed => addPendingAnalysis(changed)
        case _ => // do nothing
      }
    }
  }

  // subscribe to commands change session events
  override protected def sessionEvents(session: Session) = List(session.commands_changed)

  /** Reacts to preference changes whether ProofProcess tracking is enabled */
  private val tracking = TrackingToggle { isTracking =>
    if (!isTracking) {
      // no longer tracking, so discard all pending events
      pendingEvents.clear
    }
  }

  def init() {
    initSessionEvents()
    tracking.init()
  }
  def dispose() {
    tracking.dispose()
    disposeSessionEvents()
  }

  /** A concurrent queue is used for pending events, because the queue is
    * likely to be updated from multiple threads.
    */
  private val pendingEvents = new ConcurrentLinkedQueue[CommandAnalysisEvent]

  private val analysisJob = new AnalysisJob
  // add a listener that reschedules the job after completion if there are pending events
  analysisJob.addJobChangeListener(jobDoneListener {
    if (!pendingEvents.isEmpty) {
      // there are pending events, reschedule the job to consume them
      analysisJob.schedule
    }
  })

  private val patcher = new IsabellePatcher


  private def addPendingAnalysis(changed: Session.Commands_Changed) = if (tracking.isTracking) {

    IsabelleCore.isabelle.session foreach { session =>
      
      // Isabelle State is immutable, so just take the whole state for processing 
      // we can analyse what is needed in a low-priority analysis thread
      pendingEvents.add(CommandAnalysisEvent(changed.commands, session.current_state))

      // wake up the analysis job
      analysisJob.schedule()
    }
  }


  /**
   * Retrieves all pending events and merges them.
   * 
   * Uses the last available snapshot state and collects all commands into one
   */
  private def allPendingEvents(): Option[CommandAnalysisEvent] = {
    val allPending = pollAll(pendingEvents)

    if (allPending.isEmpty) {
      None
    } else {
      val allCmdLists = allPending map (_.changedCommands)
      val allCmds = allCmdLists.flatten.toSet

      val lastState = allPending.last.docState
      Some(CommandAnalysisEvent(allCmds, lastState))
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
          analyze(event, monitor)
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
  private def analyze(event: CommandAnalysisEvent, monitor: IProgressMonitor): IStatus =
    if (patcher.checkIsabellePatched()) {

      // delegate to the proof analyzer
      ProofAnalyzer.analyze(event.docState, event.changedCommands, monitor)

    } else {
      Status.CANCEL_STATUS
    }

}

case class CommandAnalysisEvent(val changedCommands: Set[Command], val docState: Document.State)

