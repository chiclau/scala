package com.yada.spos.front

import java.util.Properties
import javax.sql.DataSource

import com.typesafe.config.ConfigFactory
import com.yada.spos.common.rs.RESTServerBySpring
import com.yada.spos.common.{DatabaseEncrypt, HsmComponent}
import com.yada.spos.db.SpringJpaConfig
import com.yada.spos.front.core.ErrorResponseHandler
import org.apache.tomcat.jdbc.pool.DataSourceFactory
import org.springframework.context.annotation._
import org.springframework.jdbc.datasource.embedded.{EmbeddedDatabaseBuilder, EmbeddedDatabaseType}
import org.springframework.orm.jpa.vendor.{Database, HibernateJpaVendorAdapter}

@Configuration
@ComponentScan(value = Array("com.yada.spos"))
@Import(Array(classOf[SpringJpaConfig]))
class SpringConfig {

  @Profile(Array("test"))
  @Bean(name = Array("dataSource"))
  def testDataSource: DataSource = {
    new EmbeddedDatabaseBuilder()
      .setType(EmbeddedDatabaseType.H2)
      .build()
  }

  @Profile(Array("test", "default"))
  @Bean(name = Array("vendorAdapter"))
  def testVendorAdapter: HibernateJpaVendorAdapter = {
    val vendorAdapter = new HibernateJpaVendorAdapter
    vendorAdapter.setGenerateDdl(true)
    vendorAdapter.setShowSql(true)
    vendorAdapter
  }

  @Profile(Array("sit", "uat", "product"))
  @Bean(name = Array("dataSource"))
  def dataSource: DataSource = {
    val dsf = new DataSourceFactory
    val dsp = new Properties
    //加载配置
    val cf = ConfigFactory.load()
    //得到环境
    val springProfiles = cf.getString("springProfiles")
    //设置配置
    dsp.setProperty("driverClassName", cf.getString(s"db.$springProfiles.jdbc.driver"))
    dsp.setProperty("url", cf.getString(s"db.$springProfiles.jdbc.url"))
    val (username: String, password: String) = if (cf.getBoolean(s"db.$springProfiles.jdbc.encipher")) {
      val de = new DatabaseEncrypt
      (de.decrypt(cf.getString(s"db.$springProfiles.jdbc.username")), de.decrypt(cf.getString(s"db.$springProfiles.jdbc.password")))
    }
    dsp.setProperty("username", username)
    dsp.setProperty("password", password)
    dsp.setProperty("maxActive", cf.getString(s"db.$springProfiles.jdbc.maxActive"))
    dsp.setProperty("maxIdle", cf.getString(s"db.$springProfiles.jdbc.maxIdle"))
    dsp.setProperty("defaultAutoCommit", cf.getString(s"db.$springProfiles.jdbc.defaultAutoCommit"))
    dsp.setProperty("timeBetweenEvictionRunsMillis", cf.getString(s"db.$springProfiles.jdbc.timeBetweenEvictionRunsMillis"))
    dsp.setProperty("minEvictableIdleTimeMillis", cf.getString(s"db.$springProfiles.jdbc.minEvictableIdleTimeMillis"))
    dsp.setProperty("testOnBorrow", cf.getString(s"db.$springProfiles.jdbc.testOnBorrow"))
    dsp.setProperty("validationQuery", cf.getString(s"db.$springProfiles.jdbc.validationQuery"))
    dsf.createDataSource(dsp)
  }

  @Profile(Array("sit", "uat", "product"))
  @Bean(name = Array("vendorAdapter"))
  def vendorAdapter: HibernateJpaVendorAdapter = {
    val vendorAdapter = new HibernateJpaVendorAdapter
    vendorAdapter.setGenerateDdl(false)
    vendorAdapter.setShowSql(false)
    vendorAdapter.setDatabase(Database.ORACLE)
    vendorAdapter
  }

  @Bean(name = Array("hsmComponent"))
  def hsmComponent(): HsmComponent = {
    new HsmComponent
  }

  @Bean
  def restServerBySpring() = new RESTServerBySpring(List(classOf[RootRS], classOf[ErrorResponseHandler]))
}

object SpringContext extends AnnotationConfigApplicationContext(classOf[SpringConfig]) {

}
