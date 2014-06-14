package org.ai4fm.proofprocess.project.core

import org.eclipse.emf.cdo.transaction.CDOTransaction
import org.ai4fm.proofprocess.project.core.PProcessDataManager.PProcessData
import scala.collection.mutable;
import org.eclipse.core.resources.IProject

/**
 * Provides a mutable cache for transactions of PP data roots for each project.
 * 
 * @author Andrius Velykis
 */
class PProcessDataStore {

  private case class TrData(transaction: CDOTransaction, data: PProcessData)
  
  private val transactions = mutable.Map[IProject, TrData]()

  def apply(project: IProject): PProcessData = {
    val trData = transactions.getOrElseUpdate(project, openTransaction(project))
    trData.data
  }

  private def openTransaction(project: IProject): TrData = {

    val session = PProcessDataManager.session(project)
    val transaction = PProcessDataManager.openTransaction(session)
    val data = PProcessDataManager.loadData(transaction)
    
    TrData(transaction, data)
  }

  def dispose() {
    // close all transaction
    transactions.values map (_.transaction) foreach (_.close())
    transactions.clear()
  }
  
}
