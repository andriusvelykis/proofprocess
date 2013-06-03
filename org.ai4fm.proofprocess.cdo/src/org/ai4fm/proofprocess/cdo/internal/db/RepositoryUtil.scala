package org.ai4fm.proofprocess.cdo.internal.db

import java.io.{File, FileInputStream, FileOutputStream, IOException}
import java.util.Collections

import scala.Option.option2Iterable
import scala.collection.JavaConverters._
import scala.collection.TraversableOnce.flattenTraversableOnce
import scala.collection.mutable

import org.ai4fm.proofprocess.cdo.internal.PProcessCDOPlugin.{error, info, log}

import org.eclipse.emf.cdo.eresource.CDOResourceNode
import org.eclipse.emf.cdo.server.{CDOServerExporter, CDOServerImporter, IRepository}
import org.eclipse.emf.cdo.session.CDOSession
import org.eclipse.emf.cdo.view.CDOView
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.{EClass, EClassifier, EObject, EPackage, EStructuralFeature}
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil


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
  def upgradeRepository(repo: IRepository, session: CDOSession) = {

    println("Needs upgrade: " + needsUpgrade(repo))

    log(info("Upgrading repository " + repo.getName))

    exportSnapshots(repo, session)

    val repoBackup = exportRepository(repo)
    log(info("Backed up the repository to " + repoBackup))

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

  private def exportSnapshots(repo: IRepository, session: CDOSession) = {
    val repoView = session.openView

    val resourcePaths = collectResourcePaths(repoView)
    println("Resources: " + resourcePaths)

    val resourceContents = resourcePaths flatMap resourceContent(repoView)
    val exportFiles = resourceContents.toMap mapValues upgradeExport

    exportFiles foreach {
      case (path, file) => log(info("Exported converted data for " + path + " to " + file))
    }

    println("Exported: " + exportFiles)
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
    copier.copy(eObj)
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


  /**
   * An EMF copy utility that maps data to corresponding EMF classes in the latest corresponding
   * packages.
   * 
   * @author Andrius Velykis
   */
  private class PackageUpgradeCopier extends EcoreUtil.Copier {

    private val classCache: mutable.Map[EClass, EClass] = mutable.Map()
    private val featureCache: mutable.Map[EStructuralFeature, EStructuralFeature] = mutable.Map()

    lazy val allPackages: Set[EPackage] = {
      val reg = EPackage.Registry.INSTANCE
      val packages = reg.keySet.asScala map (reg.getEPackage(_))
      packages.toSet
    }

    override protected def getTarget(eClass: EClass): EClass =
      classCache.getOrElseUpdate(eClass, findNewestClass(eClass))

    override protected def getTarget(eStructuralFeature: EStructuralFeature): EStructuralFeature =
      featureCache.getOrElseUpdate(eStructuralFeature, findNewestFeature(eStructuralFeature))


    private def findNewestClass(eClass: EClass): EClass = {
      val matchClasses = allPackages flatMap findNamedClass(eClass.getName)

      // take the last class - its package has the biggest namespace URI (latest version)
      val sorted = matchClasses.toSeq.sortBy(_.getEPackage.getNsURI)
      sorted.last
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

  }

}
