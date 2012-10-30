package org.ai4fm.proofprocess.cdo.internal.db

import java.net.URI

import scala.collection.JavaConversions.mapAsJavaMap

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

/** A class to encapsulate both CDO repository server and client.
  * 
  * A single H2 database is used for server. Client and server are communicating using local
  * JVM messages.
  * 
  * @param databaseLoc  the physical location of the database that stores repository
  * @param name  name of the repository within the database
  * 
  * @author Andrius Velykis
  */
class PProcessRepository(val databaseLoc: URI, val name: String) {

  // CDO instance variables
  // adapted from Gastro CDO example and http://wiki.eclipse.org/Run_a_CDO_container_inside_eclipse_runtime
  val (acceptor: IAcceptor,
      connector: IConnector,
      repository: IRepository,
      session: CDOSession) = {

    val dbName = name
    val serverPort = "9090"

    val container = IPluginContainer.INSTANCE

    val dataSource = new JdbcDataSource
    dataSource.setURL("jdbc:h2:" + databaseLoc.toString() + "/" + dbName)

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

//  lazy val transaction = session.openTransaction
//  lazy val resource = transaction.getOrCreateResource("/proofprocess")

  /** Deactivates all resources opened for the server and client. */
  def deactivate() {

    // FIXME deactivate transactions when they are created?
//    LifecycleUtil.deactivate(resource)
//    LifecycleUtil.deactivate(transaction)
    LifecycleUtil.deactivate(session)
    LifecycleUtil.deactivate(acceptor)
    LifecycleUtil.deactivate(connector)
    LifecycleUtil.deactivate(repository)
  }

}