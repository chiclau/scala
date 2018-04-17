package com.yada.spos.db

import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.{JpaTransactionManager, LocalContainerEntityManagerFactoryBean}
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
  * spring data jpa 配置
  */
@Configuration
@EnableJpaRepositories(Array("com.yada.spos.db.dao"))
@EnableTransactionManagement(proxyTargetClass = true)
class SpringJpaConfig {
  @Autowired
  var dataSource: DataSource = _
  @Autowired
  var vendorAdapter: HibernateJpaVendorAdapter = _

  @Bean
  def entityManagerFactory(): EntityManagerFactory = {
    val factory = new LocalContainerEntityManagerFactoryBean()
    factory.setJpaVendorAdapter(vendorAdapter)
    factory.setPackagesToScan("com.yada.spos.db.model")
    factory.setDataSource(dataSource)
    factory.afterPropertiesSet()
    factory.getObject
  }

  @Bean(name = Array("transactionManager"))
  def transactionManager(): PlatformTransactionManager = {
    val txManager = new JpaTransactionManager()
    txManager.setEntityManagerFactory(entityManagerFactory())
    txManager
  }
}
