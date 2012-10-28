package org.ai4fm.proofprocess.cdo

import scala.collection.JavaConversions.mapAsJavaMap

import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Plugin
import org.eclipse.core.runtime.Status
import org.eclipse.emf.cdo.net4j.CDONet4jUtil
import org.eclipse.emf.cdo.server.CDOServerUtil
import org.eclipse.emf.cdo.server.IRepository
import org.eclipse.emf.cdo.server.db.CDODBUtil
import org.eclipse.emf.cdo.server.net4j.CDONet4jServerUtil
import org.eclipse.emf.cdo.session.CDOSession
import org.eclipse.net4j.acceptor.IAcceptor
import org.eclipse.net4j.connector.IConnector
import org.eclipse.net4j.db.DBUtil
import org.eclipse.net4j.db.h2.H2Adapter
import org.eclipse.net4j.jvm.JVMUtil
import org.eclipse.net4j.util.container.IPluginContainer
import org.eclipse.net4j.util.lifecycle.LifecycleUtil
import org.h2.jdbcx.JdbcDataSource
import org.osgi.framework.BundleContext


/**
  * @author Andrius Velykis 
  */
object PProcessCDOPlugin {

  // The shared instance
  private var instance: PProcessCDOPlugin = _
  def plugin = instance

  def log(status: IStatus) = plugin.getLog.log(status)

  /** Returns a new error `IStatus` for this plug-in.
    *
    * @param message    text to have as status message
    * @param exception  exception to wrap in the error `IStatus`
    * @return  the error `IStatus` wrapping the exception
    */
  def error(ex: Option[Throwable] = None, msg: Option[String] = None): IStatus = {
    
    // if message is not given, try to use exception's
    val message = msg orElse { ex.flatMap(ex => Option(ex.getMessage)) }
    
    new Status(IStatus.ERROR, plugin.pluginId, 0, message.orNull, ex.orNull);
  }
}

class PProcessCDOPlugin extends Plugin {

  PProcessCDOPlugin.instance = this
  
  // The plug-in ID
  def pluginId = "org.ai4fm.proofprocess.cdo" //$NON-NLS-1$
  
  // a flag to check if lazy values have been initialised
  private var lazyInit = false

  // CDO instance variables
  // adapted from Gastro CDO example and http://wiki.eclipse.org/Run_a_CDO_container_inside_eclipse_runtime
  lazy val (acceptor: IAcceptor, connector: IConnector,
      repository: IRepository, session: CDOSession) = {

    val dbName = "proofprocess"
    val serverPort = "9090"

    // lazy init is happening
    lazyInit = true
    
    val container = IPluginContainer.INSTANCE

    val dataSource = new JdbcDataSource
    dataSource.setURL("jdbc:h2:database/" + dbName)

    val mappingStrategy = CDODBUtil.createHorizontalMappingStrategy(true)
    val dbAdapter = new H2Adapter
    val dbConnectionProvider = DBUtil.createConnectionProvider(dataSource)
    val store = CDODBUtil.createStore(mappingStrategy, dbAdapter, dbConnectionProvider)

    val props = Map(
      IRepository.Props.OVERRIDE_UUID -> dbName,
      IRepository.Props.SUPPORTING_AUDITS -> "true",
      IRepository.Props.SUPPORTING_BRANCHES -> "false")

    val repository = CDOServerUtil.createRepository(dbName, store, props)
    CDOServerUtil.addRepository(container, repository)
    CDONet4jServerUtil.prepareContainer(container)

    val acceptor = JVMUtil.getAcceptor(container, "default")
    val connector = JVMUtil.getConnector(container, "default")
    
    val config = CDONet4jUtil.createNet4jSessionConfiguration()
    config.setConnector(connector)
    config.setRepositoryName(dbName)
    
//    config.setLazyPopulatingPackageRegistry()

    val session = config.openNet4jSession
    (acceptor, connector, repository, session)
  }
  
  lazy val transaction = session.openTransaction
  lazy val resource = transaction.getOrCreateResource("/proofprocess")
  
  @throws(classOf[Exception])
  override def stop(context: BundleContext) {
    
    if (lazyInit) {
      LifecycleUtil.deactivate(resource)
      LifecycleUtil.deactivate(transaction)
      LifecycleUtil.deactivate(session)
      LifecycleUtil.deactivate(acceptor)
      LifecycleUtil.deactivate(connector)
      LifecycleUtil.deactivate(repository)
    }
    
    super.stop(context)
  }
  
}
