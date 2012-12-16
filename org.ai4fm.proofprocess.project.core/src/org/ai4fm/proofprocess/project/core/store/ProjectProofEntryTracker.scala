package org.ai4fm.proofprocess.project.core.store

import scala.collection.JavaConverters._

import org.ai4fm.proofprocess.ProofEntry
import org.ai4fm.proofprocess.core.store.IProofEntryTracker
import org.ai4fm.proofprocess.log.{Activity, ProofActivity, ProofLog, ProofProcessLogPackage}
import org.ai4fm.proofprocess.project.core.ProofManager
import org.eclipse.core.databinding.observable.{ChangeEvent, IChangeListener, Observables, Realm}
import org.eclipse.core.databinding.observable.value.{IValueChangeListener, ValueChangeEvent, WritableValue}
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.CoreException
import org.eclipse.emf.databinding.EMFProperties
import org.eclipse.emf.ecore.{EObject, EStructuralFeature}


/**
 * Tracks activities added to the ProofLog.
 * 
 * Fires delayed notifications about the latest activity (its ProofEntry) added to the log, so that
 * interested parties can highlight which proof entry has been analysed.
 * 
 * @author Andrius Velykis
 */
class ProjectProofEntryTracker(project: IProject) extends IProofEntryTracker {
  
  @throws(classOf[CoreException])
  lazy val proofLog: ProofLog = ProofManager.proofLog(project)
  
  var observer: Option[DelayedListFeatureObserver[_, _]] = None
  
  @throws(classOf[CoreException])
  def initTrackLatestEntry(f: ProofEntry => Unit) {

    def lastActivityEntry(activities: Seq[Activity]): Option[ProofEntry] = {
      val backwards = activities.reverseIterator flatMap {
        case a: ProofActivity => Option(a.getProofRef)
        case _ => None
      }

      if (backwards.hasNext) {
        Some(backwards.next)
      } else {
        None
      }
    }

    val activityObserver = new DelayedListFeatureObserver(
      proofLog,
      ProofProcessLogPackage.eINSTANCE.getProofLog_Activities)(
      lastActivityEntry,
      f)

    observer = Some(activityObserver)

  }

  def dispose() {
    observer foreach (_.dispose())
  }

  class DelayedListFeatureObserver[A, B](source: EObject,
                                         feature: EStructuralFeature)
                                        (result: Seq[A] => Option[B],
                                         handler: B => Unit) {

    val listProp = EMFProperties.list(feature)
    val observableList = listProp.observe(source)

    val listListener = new IChangeListener {
      override def handleChange(event: ChangeEvent) {
        val res = result(observableList.asInstanceOf[java.util.List[A]].asScala)
        
        // if result can be calculated, set it to the value observable
        res foreach valueObservable.setValue
      }
    }

    val valueObservable = new WritableValue
    val valueDelayed = Observables.observeDelayedValue(1000, valueObservable)
    val valueListener = new IValueChangeListener {
      override def handleValueChange(event: ValueChangeEvent) =
        handler(valueObservable.getValue.asInstanceOf[B])
    }

    observableList.addChangeListener(listListener)
    valueDelayed.addValueChangeListener(valueListener)

    def dispose() {
      valueDelayed.removeValueChangeListener(valueListener)
      observableList.removeChangeListener(listListener)
    }
  }

}
