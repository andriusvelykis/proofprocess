package org.ai4fm.proofprocess.cdo.internal.db

import java.io.{File, FileInputStream, FileOutputStream, IOException}
import java.util.Collections

import scala.collection.JavaConverters._
import scala.collection.TraversableOnce.flattenTraversableOnce
import scala.collection.mutable
import scala.util.{Failure, Success, Try}

import org.ai4fm.proofprocess.cdo.CopyParticipant
import org.ai4fm.proofprocess.cdo.internal.PProcessCDOPlugin.{error, info, log, plugin}

import org.eclipse.core.runtime.{CoreException, IProgressMonitor, NullProgressMonitor, Platform}
import org.eclipse.emf.cdo.eresource.CDOResourceNode
import org.eclipse.emf.cdo.server.{CDOServerExporter, CDOServerImporter, IRepository}
import org.eclipse.emf.cdo.session.CDOSession
import org.eclipse.emf.cdo.transaction.CDOTransaction
import org.eclipse.emf.cdo.util.CommitException
import org.eclipse.emf.cdo.view.CDOView
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.{EClass, EClassifier, EObject, EPackage, EReference, EStructuralFeature}
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.net4j.util.lifecycle.LifecycleUtil

import org.h2.tools.DeleteDbFiles


/**
 * Utilities for working with CDO repositories.
 * 
 * @author Andrius Velykis
 */
object RepositoryUtil {

  /**
   * Upgrades the repository to latest EMF packages.
   *
   * The upgrade is performed by converting the current data snapshot and exporting it to disk.
   * The repository is then cleaned and converted data is re-exported back to the repository.
   *
   * Note that this loses CDO database revisions and other DB data, however we are not using it
   * at the moment anyway.
   *
   * CDO currently does not support proper model evolution (data upgrade when the meta-model
   * changes). See https://bugs.eclipse.org/bugs/show_bug.cgi?id=256856 for details.
   */
  def upgradeRepository(repoInfo: PProcessRepository,
                        monitor: IProgressMonitor = new NullProgressMonitor) {

    val repo = repoInfo.repository

    println("Needs upgrade: " + needsUpgrade(repo))

    log(info("Upgrading repository " + repo.getName + ". Exporting data files..."))

    val exported = exportSnapshots(repo, repoInfo.session)

    log(info("Backing up repository " + repo.getName + "..."))
    val repoBackup = exportRepository(repo)
    log(info("Backed up the repository to " + repoBackup))

    log(info("Wiping repository " + repo.getName + "..."))
    wipeRepository(repoInfo)
    log(info("Wiped repository " + repo.getName + ". Importing converted data..."))

    // create a new repo access - the old one is invalid (gives exceptions)
    withRepoSession(repoInfo.databaseLoc, repoInfo.name) { session =>
      importData(session, exported)
    }

    log(info("Finished upgrading repository " + repo.getName + "."))
  }

  def needsUpgrade(repo: IRepository): Boolean = {
    val packageInfos = repo.getPackageRegistry.getPackageInfos

    val userPackageInfos = packageInfos.iterator filterNot (_.isSystemPackage)
    val userPackages = userPackageInfos map (info => Option(info.getEPackage(true)))
    val dynamic = userPackages.flatten exists isDynamicPackage

    // if any of the packages is dynamic, need to upgrade the repository to use non-dynamic ones
    // (dynamic packages are used when only Ecore file is available from history)
    dynamic
  }

  // package is dynamic if any of its classes is dynamic
  private def isDynamicPackage(pkg: EPackage): Boolean = 
    pkg.getEClassifiers.asScala exists isDynamicClass

  private def isDynamicClass(eClass: EClassifier): Boolean = eClass.getInstanceClass == null

  private def isDynamic(eObj: EObject): Boolean = isDynamic(eObj.eClass)

  private def exportSnapshots(repo: IRepository, session: CDOSession): Map[String, File] = {
    val repoView = session.openView

    val resourcePaths = collectResourcePaths(repoView)

    val resourceContents = resourcePaths flatMap resourceContent(repoView)
    // note that Map.mapValues is lazy!
    val exportFiles = resourceContents map { case (path, eObj) => (path, upgradeExport(eObj)) }

    exportFiles foreach {
      case (path, file) => log(info("Exported converted data for " + path + " to " + file))
    }

    exportFiles.toMap
  }

  private def collectResourcePaths(view: CDOView): Seq[String] = {
    val rootNodes = view.getRootResource.getContents.asScala map { case n: CDOResourceNode => n }
    rootNodes map (_.getPath)
  }

  private def resourceContent(view: CDOView)(path: String): Option[(String, EObject)] = {
    val resource = Option(view.getResource(path, true))
    // assume resource has a single root element
    val rootElem = resource flatMap (_.getContents.asScala.headOption)
    // join with path
    rootElem map ((path, _))
  }

  private def upgradeExport(eObj: EObject): File = {
    val upgraded = upgradeData(eObj)
    exportToTempXML(upgraded)
  }

  private def upgradeData(eObj: EObject): EObject = {
    // use a copier that matches the classes/features in the latest packages
    val copier = new PackageUpgradeCopier
    val result = copier.copy(eObj)
    copier.finish()
    result
  }

  private def exportToTempXML(eObj: EObject): File = {

    val tempExportFile = File.createTempFile("proofprocess-export", ".xml")
    val tempExportUri = URI.createFileURI(tempExportFile.getAbsolutePath)

    // Obtain a new resource set
    val resSet = new ResourceSetImpl

    // Create a resource
    val resource = resSet.createResource(tempExportUri)
    // note that eObj cannot be contained somewhere else (e.g. in another resource),
    // since the containment will be updated
    resource.getContents().add(eObj)

    // Now save the content.
    try {
      resource.save(Collections.EMPTY_MAP)
    } catch {
      case e: IOException => {
        log(error(Some(e)))
        throw e
      }
    }

    tempExportFile
  }

  def exportRepository(repo: IRepository): File = {
    val tempExportFile = File.createTempFile("proofprocess-cdo-repo-export", ".xml")
    val tempExportStream = new FileOutputStream(tempExportFile)

    try {
      val xmlExport = new CDOServerExporter.XML(repo)
      xmlExport.exportRepository(tempExportStream)
    } finally {
      tempExportStream.close()
    }

    tempExportFile
  }

  /**
   * Imports full repository contents into the given repository.
   * 
   * The repository must be inactive and empty (all data and meta-data will be replaced) 
   */
  def importRepository(repo: IRepository, exportFile: File) {

    val tempImportStream = new FileInputStream(exportFile)
    try {
      val xmlImport = new CDOServerImporter.XML(repo)
      xmlImport.importRepository(tempImportStream)
    } finally {
      tempImportStream.close()
    }
  }

  private def wipeRepository(repoInfo: PProcessRepository) {
    val repo = repoInfo.repository
    
    val sessionActive = LifecycleUtil.isActive(repoInfo.session)
    if (sessionActive) {
      repoInfo.deactivate()
    }
    
    val active = LifecycleUtil.isActive(repo)
    if (active) {
      LifecycleUtil.deactivate(repo)
    }

    // try deleting repository files to compact everything
    val deleted = deleteRepoFiles(repoInfo)

    if (!deleted) {
      // try at least dropping tables
      val dropOnActivate = repo.getStore.isDropAllDataOnActivate

      repo.getStore.setDropAllDataOnActivate(true)
      LifecycleUtil.activate(repo)

      LifecycleUtil.deactivate(repo)
      repo.getStore.setDropAllDataOnActivate(dropOnActivate)
    }

  }

  private def deleteRepoFiles(repoInfo: PProcessRepository): Boolean =
    Try(new File(repoInfo.databaseLoc)) match {

      case Failure(ex) => {
        log(error(Some(ex)))
        false
      }

      case Success(dbDir) => {
        DeleteDbFiles.execute(dbDir.getAbsolutePath, repoInfo.name, true)
        true
      }
    }


  def importData(session: CDOSession, resources: Map[String, File]) {
    val transaction = session.openTransaction()

    try {
      resources foreach Function.tupled(importResource(transaction))
    } finally {
      transaction.close()
    }
  }

  private def importResource(transaction: CDOTransaction)(path: String, file: File) {

    loadXMLFile(file) match {
      case None => // ignore
      case Some(rootObj) => {
        createResourceContents(transaction)(path, rootObj)
      }
    }
  }

  private def loadXMLFile(file: File): Option[EObject] = {
    val fileUri = URI.createFileURI(file.getAbsolutePath)

    // Obtain a new resource set
    val resSet = new ResourceSetImpl

    // Create a resource
    val emfResource = resSet.createResource(fileUri)

    if (file.exists) {
      try {
        // load EMF resource
        emfResource.load(null)
      } catch {
        case e: IOException => log(error(Some(e)))
      }

      emfResource.getContents.asScala.headOption
    } else {
      log(error(msg = Some("Cannot locate data file to import: " + file.getAbsolutePath)))
      None
    }
  }

  private def createResourceContents(transaction: CDOTransaction)(path: String, root: EObject) {
    val emfResource = transaction.getOrCreateResource(path)
    emfResource.getContents.add(root)
    // FIXME
    val monitor = null

    try {
      transaction.commit(monitor)
      log(info("Imported " + path))
    } catch {
      case e: CommitException => log(error(Some(e)))
    }
  }

  private def withRepoSession(dbLoc: java.net.URI, repoName: String)(f: CDOSession => Unit) {
    val repoInfo = new PProcessRepository(dbLoc, repoName)
    val session = repoInfo.session

    try {
      f(session)
    } finally {
      repoInfo.deactivate()
    }
  }


  /**
   * An EMF copy utility that maps data to corresponding EMF classes in the latest corresponding
   * packages.
   * 
   * @author Andrius Velykis
   * @see http://wiki.eclipse.org/EMF/Recipes#Recipe:_Copying_models_from_an_EPackage_implementation_to_another_one
   */
  private class PackageUpgradeCopier extends EcoreUtil.Copier {

    private def MODEL_EVOLUTION_ID = plugin.pluginId + ".modelEvolution"

    private lazy val copyParticipants: List[CopyParticipant] = {
      val extensionRegistry = Platform.getExtensionRegistry
      val extensions = extensionRegistry.getConfigurationElementsFor(MODEL_EVOLUTION_ID)

      try {
        val execExts = extensions.toList map (_.createExecutableExtension("class"))

        execExts flatMap {
          _ match {
            case Some(c: CopyParticipant) => Some(c)
            case _ => {
              log(error(msg = Some("Cannot instantiate copy participant extension!")))
              None
            }
          }
        }
      } catch {
        case ex: CoreException => {
          log(error(Some(ex)))
          Nil
        }
      }
    }

    private val classCache: mutable.Map[EClass, EClass] = mutable.Map()
    private val featureCache: mutable.Map[EStructuralFeature, EStructuralFeature] = mutable.Map()

    lazy val allPackages: Set[EPackage] = {
      val reg = EPackage.Registry.INSTANCE
      val packages = reg.keySet.asScala map (reg.getEPackage(_))
      packages.toSet
    }

    override protected def copyReference(eReference: EReference,
                                         eObject: EObject,
                                         copyEObject: EObject) {

      val matchingParticipants = copyParticipants.toStream filter {
        _.canCopyReference(this, eReference, eObject, copyEObject)
      }

      // take just the first one
      val activeParticipant = matchingParticipants.headOption
      activeParticipant match {
        case Some(p) => p.copyReference(this, eReference, eObject, copyEObject)
        case None => super.copyReference(eReference, eObject, copyEObject)
      }

    }

    override protected def getTarget(eClass: EClass): EClass =
      classCache.getOrElseUpdate(eClass, findNewestClass(eClass))

    override protected def getTarget(eStructuralFeature: EStructuralFeature): EStructuralFeature =
      featureCache.getOrElseUpdate(eStructuralFeature, findNewestFeature(eStructuralFeature))


    private def findNewestClass(eClass: EClass): EClass = {
      val matchClasses = allPackages.toStream flatMap findNamedClass(eClass.getName)
      // only take non-dynamic classes (generated classes - non-history)
      val generatedClasses = matchClasses filterNot isDynamicClass

      generatedClasses.headOption match {
        case Some(generatedClass) => generatedClass
        // cannot find a generated class (only dynamic ones are available) - use the original one
        case None => eClass
      }
    }

    private def findNamedClass(name: String)(pkg: EPackage): Option[EClass] =
      Option(pkg.getEClassifier(name)) match {
        case Some(eClass: EClass) => Some(eClass)
        case _ => None
      }

    private def findNewestFeature(eStructuralFeature: EStructuralFeature): EStructuralFeature = {
      val featureClass = eStructuralFeature.getEContainingClass
      val targetClass = getTarget(featureClass)

      Option(targetClass.getEStructuralFeature(eStructuralFeature.getName)) match {
        case Some(feature) => feature

        case None => {
          log(error(msg = Some("Cannot migrate feature " + eStructuralFeature.getName)))
          eStructuralFeature
        }
      }
    }

    def finish() =
      copyParticipants foreach { p => if (p.canFinish(this)) p.finish(this) }

  }

}
