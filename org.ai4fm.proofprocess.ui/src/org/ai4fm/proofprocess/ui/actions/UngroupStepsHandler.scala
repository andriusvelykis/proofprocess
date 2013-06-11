package org.ai4fm.proofprocess.ui.actions

import java.util.Collections.singletonList

import scala.collection.JavaConverters._

import org.ai4fm.proofprocess.{Attempt, ProofDecor, ProofElem, ProofInfo, ProofParallel, ProofProcessFactory, ProofSeq}
import org.ai4fm.proofprocess.core.util.PProcessUtil
import org.ai4fm.proofprocess.ui.internal.PProcessUIPlugin.{error, log}
import org.ai4fm.proofprocess.ui.util.SWTUtil._

import org.eclipse.core.commands.{AbstractHandler, ExecutionEvent, ExecutionException}
import org.eclipse.emf.cdo.transaction.CDOTransaction
import org.eclipse.emf.cdo.util.CommitException
import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.ui.handlers.HandlerUtil


/**
 * Removes the selected proof steps group and inserts children in its place.
 *
 * @author Andrius Velykis
 */
class UngroupStepsHandler extends AbstractHandler {

  private val factory = ProofProcessFactory.eINSTANCE

  @throws(classOf[ExecutionException])
  override def execute(event: ExecutionEvent): AnyRef = {

    // get the selected element if available
    val selection = selectionElement(HandlerUtil.getCurrentSelection(event))

    selection match {
      case Some(e: ProofSeq) => ungroup(event, e, e.getEntries.asScala)
      case Some(e: ProofDecor) => ungroup(event, e, List(e.getEntry))
      case _ => // ignore
    }

    // return value is reserved for future APIs
    null
  }

  private def ungroup(event: ExecutionEvent, elem: ProofElem, children: Seq[ProofElem]) = {

    val dialogTitle = "Ungroup steps"

    def error(message: String): Boolean = {
      MessageDialog.openError(HandlerUtil.getActiveShell(event), dialogTitle, message)
      false
    }

    val transaction = PProcessUtil.cdoTransaction(elem)
    val savePoint = transaction map (_.setSavepoint())

    val ungroupSuccess =
      Option(elem.eContainer) match {

        case Some(seq: ProofSeq) => ungroupToSeq(seq, elem, children)

        case Some(decor: ProofDecor) => ungroupToDecor(decor, elem, children)

        case Some(parallel: ProofParallel) =>
          if (children.size > 1) error("Cannot remove the top group in a parallel branch.")
          else replaceInList(parallel.getEntries, elem, children.asJava)

        case Some(attempt: Attempt) =>
          if (children.size != 1) error("Cannot remove the top group in the attempt.")
          else { attempt.setProof(children.head); true }

        case bad => error("Invalid parent of the group: " + bad)

      }

    def userConfirm =
      MessageDialog.openConfirm(HandlerUtil.getActiveShell(event), dialogTitle,
        "Do you want to ungroup the selected steps?")

    if (ungroupSuccess && userConfirm) {
      commit(transaction)
    } else {
      savePoint foreach (_.rollback())
    }

  }

  private def ungroupToSeq(parentSeq: ProofSeq,
                           elem: ProofElem,
                           children: Seq[ProofElem]): Boolean =
    // TODO try to preserve its intent/features?
    replaceInList(parentSeq.getEntries, elem, children.asJava)


  private def ungroupToDecor(parentDecor: ProofDecor,
                             elem: ProofElem,
                             children: Seq[ProofElem]): Boolean =
    if (children.size == 1) {
      parentDecor.setEntry(children.head)
      true

    } else {

      // convert decor to Seq and ungroup as to a Seq
      val newSeq = decorToSeq(parentDecor)
      if (replaceElement(parentDecor, newSeq)) {
        // now that we have Seq as parent, ungroup
        ungroupToSeq(newSeq, elem, children)
      } else {
        false
      }
    }


  private def decorToSeq(decor: ProofDecor): ProofSeq = {
    val seq = factory.createProofSeq
    seq.setInfo(factory.createProofInfo)

    moveProofInfo(decor.getInfo, seq.getInfo)

    Option(decor.getEntry) match {
      case Some(entry) => seq.getEntries.add(entry)
      case None => // ignore
    }

    seq
  }


  private def replaceElement(target: ProofElem,
                             replaceWith: ProofElem): Boolean = Option(target.eContainer) match {

    case Some(seq: ProofSeq) =>
      replaceInList(seq.getEntries, target, singletonList(replaceWith))

    case Some(parallel: ProofParallel) =>
      replaceInList(parallel.getEntries, target, singletonList(replaceWith))

    case Some(decor: ProofDecor) => {
      decor.setEntry(replaceWith)
      true
    }

    case Some(attempt: Attempt) => {
      attempt.setProof(replaceWith)
      true
    }

    case _ => false
  }

  private def replaceInList[A](seq: java.util.List[A],
                               target: A,
                               replaceWith: java.util.List[A]): Boolean = {
    val pos = seq.indexOf(target)
    if (pos < 0) {
      false
    } else {
      seq.remove(pos)
      seq.addAll(pos, replaceWith)
      true
    }
  }

  private def moveProofInfo(from: ProofInfo, to: ProofInfo) {
    to.setNarrative(from.getNarrative)
    to.setIntent(from.getIntent)

    to.getInFeatures.addAll(from.getInFeatures)
    to.getOutFeatures.addAll(from.getOutFeatures)
  }

  private def commit(transaction: Option[CDOTransaction]) =
    try {
      transaction foreach { _.commit() }
    } catch {
      case ex: CommitException => log(error(Some(ex)))
    }

}
