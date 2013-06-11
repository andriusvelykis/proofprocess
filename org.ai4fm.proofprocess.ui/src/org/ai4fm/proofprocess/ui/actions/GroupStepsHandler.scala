package org.ai4fm.proofprocess.ui.actions

import scala.collection.JavaConverters._

import org.ai4fm.proofprocess.{Attempt, ProofDecor, ProofElem, ProofParallel, ProofProcessFactory, ProofSeq}
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.ui.features.MarkFeaturesDialog
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.{error, log}

import org.eclipse.core.commands.{AbstractHandler, ExecutionEvent, ExecutionException}
import org.eclipse.emf.cdo.transaction.CDOTransaction
import org.eclipse.emf.cdo.util.CommitException
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.jface.window.Window
import org.eclipse.ui.handlers.HandlerUtil


/**
 * Groups selected proof elements together into a proof sequence
 * (or a proof decoration if a single element only).
 *
 * @author Andrius Velykis
 */
class GroupStepsHandler extends AbstractHandler {

  @throws(classOf[ExecutionException])
  override def execute(event: ExecutionEvent): AnyRef = {

    val selection = Option(HandlerUtil.getCurrentSelection(event))
    val selectedElems =
      selection match {
        case Some(ss: IStructuredSelection) => ss.toList.asScala
        case _ => Nil
      }

    val proofElems = selectedElems flatMap proofElem

    def error(message: String) = 
      MessageDialog.openError(HandlerUtil.getActiveShell(event), "Group Steps", message)

    if (!proofElems.isEmpty) {
      val topElems = dropChildren(proofElems)
      findSingleParent(topElems) match {
        case None =>
          error("Steps to be grouped must be in the same level.")

        case Some(parentParallel: ProofParallel) =>
          if (topElems.size > 1) {
            error("Cannot group parallel proofs.")
          } else {
            val head = topElems.head
            val insertIndex = parentParallel.getEntries.indexOf(head)
            groupElements(event, topElems, g => parentParallel.getEntries.add(insertIndex, g))
          }

        case Some(parentSeq: ProofSeq) => {
          val groupedIndex = parentSeq.getEntries.asScala indexOfSlice topElems
          if (groupedIndex < 0) {
            error("Steps to be grouped must be uniformly selected.")
          } else {
            groupElements(event, topElems, g => parentSeq.getEntries.add(groupedIndex, g))
          }
        }

        case Some(parentDecor: ProofDecor) =>
          groupElements(event, topElems, parentDecor.setEntry)

        case Some(parentAttempt: Attempt) =>
          groupElements(event, topElems, parentAttempt.setProof)

        case Some(unknown) => 
          error("Steps must belong to some proof. Currently they belong to: " + unknown)
      }
    }



    // return value is reserved for future APIs
    null
  }


  private def proofElem(e: Any): Option[ProofElem] = e match {
    case elem: ProofElem => Some(elem)
    case _ => None
  }

  private def handleProofEntrySelected(event: ExecutionEvent, e: ProofElem) {

    val shell = HandlerUtil.getActiveShell(event)
    
    val dialog = new MarkFeaturesDialog(shell, e)
    dialog.open()
  }

  private def dropChildren(elems: Seq[ProofElem]): Seq[ProofElem] =
    elems filterNot parentIn(elems)

  private def parentIn(elems: Seq[ProofElem])(e: ProofElem): Boolean =
    parents(e) exists (elems.contains)
  
  private def parents(elem: ProofElem): List[ProofElem] = elem.eContainer match {
    case pe: ProofElem => pe :: parents(pe)
    case _ => Nil
  }

  private def findSingleParent(elems: Iterable[ProofElem]): Option[EObject] = {
    val parents = elems.toList map (e => Option(e.eContainer))
    parents.distinct match {
      case Nil => None
      case single :: Nil => single
      // multiple parents found - bad
      case multiple => None
    }
  }

  private def factory = ProofProcessFactory.eINSTANCE

  private def groupElements(event: ExecutionEvent,
                            elems: Seq[ProofElem],
                            addGroup: ProofElem => Unit) {
    val transaction = PProcessUtil.cdoTransaction(elems.head)
    val savePoint = transaction map (_.setSavepoint())

    val group =
      if (elems.size == 1) {
        // decorate only
        val decor = factory.createProofDecor
        decor.setInfo(factory.createProofInfo)
        decor.setEntry(elems.head)
        decor
      } else {

        val seq = factory.createProofSeq
        seq.setInfo(factory.createProofInfo)
        seq.getEntries.addAll(elems.asJava)
        seq
      }

    // add to the correct place
    addGroup(group)

    val dialog = new MarkFeaturesDialog(HandlerUtil.getActiveShell(event), group)

    if (dialog.open() == Window.OK) {
      commit(transaction)
    } else {
      savePoint foreach (_.rollback())
    }
    
  }

  private def commit(transaction: Option[CDOTransaction]) =
    try {
      transaction foreach { _.commit() }
    } catch {
      case ex: CommitException => log(error(Some(ex)))
    }

}
