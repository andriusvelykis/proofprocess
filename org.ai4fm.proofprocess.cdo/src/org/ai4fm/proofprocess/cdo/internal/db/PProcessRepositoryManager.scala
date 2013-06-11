package org.ai4fm.proofprocess.cdo.internal.db

import java.net.URI

import scala.collection.mutable.{HashMap, SynchronizedMap}
import scala.concurrent.{Await, Future, future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

import org.ai4fm.proofprocess.cdo.internal.PProcessCDOPlugin.{error, log}

import org.eclipse.core.runtime.{IProgressMonitor, NullProgressMonitor}
import org.eclipse.emf.cdo.session.CDOSession


/**
 * A manager for ProofProcess repository connections.
 * 
 * Contains a cache of repositories and main access and initialisation methods.
 * 
 * @author Andrius Velykis
 */
class PProcessRepositoryManager {

  /** A synchronized lazy map for connected CDO repositories */
  private val repositories =
    new HashMap[RepositoryId, Future[PProcessRepository]]
      with SynchronizedMap[RepositoryId, Future[PProcessRepository]]
  
  /** 
   * Retrieves a Proof Process repository with a given name.
   * Initializes a new one if no such repository exists currently.
   */
  private def repository(repoId: RepositoryId, monitor: IProgressMonitor): PProcessRepository = {
    // get the repository future from the map, or create new one if not exists
    val repoFuture = repositories.getOrElseUpdate(repoId, future {
      initRepository(repoId, monitor)
    })

    // block until the future is resolved (repository is connected initially)
    getBlocking(repoFuture)

  }


  private def initRepository(repoId: RepositoryId,
                             monitor: IProgressMonitor): PProcessRepository = {
    val newRepo = initNewRepositoryConnection(repoId)

    if (RepositoryUtil.needsUpgrade(newRepo.repository)) {
      // upgrade current repository and then initialise a new connection
      RepositoryUtil.upgradeRepository(newRepo,
        subTask(monitor, "Upgrading ProofProcess repository..."))

      initNewRepositoryConnection(repoId)

    } else {
      newRepo
    }
  }

  private def initNewRepositoryConnection(repoId: RepositoryId): PProcessRepository =
    new PProcessRepository(repoId.databaseLoc, repoId.name)


  private def subTask(monitor: IProgressMonitor, name: String): IProgressMonitor = {
    monitor.subTask(name)
    monitor
  }

  private def getBlocking[A](future: Future[A]): A = {
    val result =
      future.value match {
        case Some(r) => r
        case None => {
          Await.result(future, Duration.Inf)
          future.value.get
        }
      }

    result match {
      case Success(r) => r
      case Failure(ex) => {
        log(error(Some(ex)))
        throw ex
      }
    }
  }

  /**
   * Retrieves an open session for the given repository.
   *
   * Initialises a new repository if one does not exist, migrates existing file-based data or
   * upgrades the repository if it uses old EMF packages.
   */
  def session(databaseLoc: URI,
              repositoryName: String,
              monitor: IProgressMonitor = new NullProgressMonitor): CDOSession =
    repository(RepositoryId(databaseLoc, repositoryName), monitor).session


  /**
   * Forces upgrade of the repository.
   *
   * Note that this action may also help compact the repository in the database.
   * 
   * The existing CDO sessions on the repository will not work after upgrade:
   * need to reinitialise new CDO sessions.
   */
  def upgradeRepository(databaseLoc: URI,
                        repositoryName: String,
                        monitor: IProgressMonitor = new NullProgressMonitor) = {
    val repoId = RepositoryId(databaseLoc, repositoryName)

    val ppRepo = repository(repoId, monitor)

    // upgrade the repository
    RepositoryUtil.upgradeRepository(ppRepo,
      subTask(monitor, "Upgrading ProofProcess repository..."))

    // remove the previous repository link
    repositories.remove(repoId)
  }


  private case class RepositoryId(val databaseLoc: URI, val name: String)

  def dispose() {
    // deactivate each repository
    repositories.values.foreach(f => getBlocking(f).deactivate)
  }

}
