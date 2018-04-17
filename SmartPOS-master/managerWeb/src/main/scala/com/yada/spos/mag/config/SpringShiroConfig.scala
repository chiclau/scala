package com.yada.spos.mag.config

import org.apache.shiro.cache.MemoryConstrainedCacheManager
import org.apache.shiro.realm.Realm
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}

import scala.collection.convert.decorateAll._

/**
  * Created by locky on 2016/9/13.
  */
@Configuration
class SpringShiroConfig {
  @Bean
  def shiroFilter: ShiroFilterFactoryBean = {
    val bean = new ShiroFilterFactoryBean
    bean.setLoginUrl("/login.do")
    bean.setSuccessUrl("/welcome.do")
    bean.setFilterChainDefinitionMap(Map(
      "/login.do" -> "authc",
      "/logout.do" -> "logout",
      "/*.do" -> "authc",
      "/*/*.do" -> "authc"
    ).asJava)
    bean.setSecurityManager(securityManager)
    bean
  }

  @Autowired
  var loginRealm: Realm = _

  @Bean
  def securityManager: DefaultWebSecurityManager = {
    val mag = new DefaultWebSecurityManager()
    mag.setRealm(loginRealm)
    mag.setCacheManager(new MemoryConstrainedCacheManager)
    mag
  }
}
