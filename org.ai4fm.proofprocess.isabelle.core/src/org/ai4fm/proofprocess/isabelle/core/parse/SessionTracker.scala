package org.ai4fm.proofprocess.isabelle.core.parse

import isabelle.Command
import isabelle.Document
import isabelle.Session
import isabelle.eclipse.core.IsabelleCorePlugin
import isabelle.eclipse.core.util.LoggingActor
import isabelle.eclipse.core.util.SessionEvents
import java.util.concurrent.ConcurrentLinkedQueue
import org.ai4fm.proofprocess.isabelle.core.IsabellePProcessCorePlugin._
import org.ai4fm.proofprocess.isabelle.core.analysis.ProofAnalyzer
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.jobs.IJobChangeEvent
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.core.runtime.jobs.JobChangeAdapter
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent
import scala.actors.Actor._


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
  
  // check core preferences whether proof process tracking is enabled
  import org.ai4fm.proofprocess.core.prefs.PProcessCorePreferences._
  private def trackingPref() = getBoolean(TRACK_PROOF_PROCESS, false)
  private var tracking = trackingPref()
  
  // listener for "tracking" preference change
  private lazy val prefsListener = prefKeyListener(TRACK_PROOF_PROCESS) {
    tracking = trackingPref()
    
    if (!tracking) {
      // no longer tracking, so discard all pending events
      pendingEvents.clear
    }
  }

  def init() {
    initSessionEvents()
    preferences.addPreferenceChangeListener(prefsListener)
  }
  def dispose() {
    preferences.removePreferenceChangeListener(prefsListener)
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

  private def addPendingAnalysis(changed: Session.Commands_Changed) = if (tracking) {

    IsabelleCorePlugin.getIsabelle.session foreach{ session =>
      
      // Isabelle State is immutable, so just take the whole state for processing 
      // we can analyse what is needed in a low-priority analysis thread
      pendingEvents.add(CommandAnalysisEvent(changed.commands, session.current_state))

      // wake up the analysis job
      analysisJob.schedule()
    }
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

  private def prefKeyListener(key: String)(f: => Unit) =
    new IPreferenceChangeListener {
      def preferenceChange(event: PreferenceChangeEvent) {
        if (event.getKey == key) {
          f
        }
      }
    }

  @throws(classOf[CoreException])
  private def analyze(event: CommandAnalysisEvent, monitor: IProgressMonitor): IStatus = {

    // delegate to the proof analyzer
    ProofAnalyzer.analyze(event.docState, event.changedCommands, monitor)
  }

}

case class CommandAnalysisEvent(val changedCommands: Set[Command], val docState: Document.State)

