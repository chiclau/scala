package com.yada.spos.mag.config

import javax.servlet.{Filter, ServletContext}

import com.typesafe.config.ConfigFactory
import com.yada.spos.mag.filter.SiteMeshFilter
import org.springframework.web.filter.{CharacterEncodingFilter, DelegatingFilterProxy}
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  override def getRootConfigClasses: Array[Class[_]] = Array(classOf[SpringRootConfig])

  override def getServletConfigClasses: Array[Class[_]] = Array(classOf[SpringMvcConfig])

  override def getServletMappings: Array[String] = Array("/")

  override def getServletFilters: Array[Filter] = {
    Array({
      val encodingFilter = new CharacterEncodingFilter
      encodingFilter.setEncoding("UTF-8")
      encodingFilter.setForceEncoding(true)
      encodingFilter
    }, {
      val proxy = new DelegatingFilterProxy
      proxy.setTargetBeanName("shiroFilter")
      proxy
    },
      new SiteMeshFilter
      , {
        val proxy = new DelegatingFilterProxy
        proxy.setTargetBeanName("menuFilter")
        proxy
      })
  }

  override def onStartup(servletContext: ServletContext): Unit = {
    super.onStartup(servletContext)
    servletContext.setInitParameter("spring.profiles.active", ConfigFactory.load.getString("springProfiles"))
  }
}
