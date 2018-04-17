package com.yada.spos.mag.config

import javax.sql.DataSource

import com.yada.spos.db.SpringJpaConfig
import com.yada.spos.db.dao.{AppsAssociateAppGroupDao, AppsUnAssociateAppGroupDao}
import org.springframework.context.annotation._
import org.springframework.jdbc.datasource.embedded.{EmbeddedDatabaseBuilder, EmbeddedDatabaseType}
import org.springframework.jndi.JndiObjectFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.stereotype.Controller

@Configuration
@ComponentScan(
  basePackages = Array("com.yada.spos"),
  excludeFilters = Array(new ComponentScan.Filter(value = Array(classOf[Controller]))))
@Import(Array(classOf[SpringJpaConfig], classOf[SpringShiroConfig]))
class SpringRootConfig {

  @Bean
  def appsAssociateAppGroupDao: AppsAssociateAppGroupDao = {
    new AppsAssociateAppGroupDao
  }

  @Bean
  def appsUnAssociateAppGroupDao: AppsUnAssociateAppGroupDao = new AppsUnAssociateAppGroupDao

  @Profile(Array("test"))
  @Bean(name = Array("dataSource"))
  def testDataSource: DataSource = {
    new EmbeddedDatabaseBuilder()
      .setType(EmbeddedDatabaseType.H2)
      .addScript("classpath:init.sql")
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
  def sitDataSource: DataSource = {
    val bean = new JndiObjectFactoryBean
    bean.setJndiName("jdbc/spos_sit")
    bean.afterPropertiesSet()
    bean.getObject.asInstanceOf[DataSource]
  }

  @Profile(Array("sit", "uat", "product"))
  @Bean(name = Array("vendorAdapter"))
  def sitVendorAdapter: HibernateJpaVendorAdapter = {
    val vendorAdapter = new HibernateJpaVendorAdapter
    vendorAdapter.setGenerateDdl(false)
    vendorAdapter.setShowSql(false)
    vendorAdapter
  }
}
