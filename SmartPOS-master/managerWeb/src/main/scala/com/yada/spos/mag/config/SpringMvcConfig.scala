package com.yada.spos.mag.config

import java.nio.charset.Charset
import java.util
import java.util.Properties

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.yada.spos.mag.exception.CommonExceptionResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.http.converter.{HttpMessageConverter, StringHttpMessageConverter}
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import org.springframework.web.servlet.{HandlerExceptionResolver, HandlerInterceptor}
import org.springframework.web.servlet.config.annotation._
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver
import org.springframework.web.servlet.view.JstlView

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = Array("com.yada.spos.mag.controller"))
class SpringMvcConfig extends WebMvcConfigurerAdapter {
  override def configureViewResolvers(registry: ViewResolverRegistry): Unit = {
    val view = new JstlView
    registry.enableContentNegotiation(Array(view): _*)
    registry.jsp("/WEB-INF/pages/", ".jsp")
  }

  override def configureMessageConverters(converters: util.List[HttpMessageConverter[_]]): Unit = {
    converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")))
    val jacksonMapper = new ObjectMapper()
    jacksonMapper.registerModule(DefaultScalaModule)
    converters.add(new MappingJackson2HttpMessageConverter(jacksonMapper))
  }

  override def addResourceHandlers(registry: ResourceHandlerRegistry): Unit = {
    registry.addResourceHandler(Array("/webjars/**"): _*).addResourceLocations(Array("/webjars/"): _*)
    registry.addResourceHandler(Array("/css/**"): _*).addResourceLocations(Array("/WEB-INF/css/"): _*)
    registry.addResourceHandler(Array("/images/**"): _*).addResourceLocations(Array("/WEB-INF/images/"): _*)
    registry.addResourceHandler(Array("/js/**"): _*).addResourceLocations(Array("/WEB-INF/js/"): _*)
  }

  override def configureHandlerExceptionResolvers(exceptionResolvers: util.List[HandlerExceptionResolver]): Unit = {
    exceptionResolvers.add(new SimpleMappingExceptionResolver)
  }

  override def addArgumentResolvers(argumentResolvers: util.List[HandlerMethodArgumentResolver]): Unit = {
    argumentResolvers.add(new PageableHandlerMethodArgumentResolver())
  }


  override def addViewControllers(registry: ViewControllerRegistry): Unit = {
    registry.addViewController("/login.do").setViewName("login")
    registry.addViewController("/welcome.do").setViewName("welcome")
    registry.addViewController("/").setViewName("login")
  }

  @Bean
  def multipartResolver(): CommonsMultipartResolver = {
    new CommonsMultipartResolver
  }

  @Bean
  def simpleMappingExceptionResolver(): SimpleMappingExceptionResolver = {
    val simpleMappingExceptionResolver = new CommonExceptionResolver
    val properties = new Properties
    properties.setProperty("java.lang.Throwable", "common/error")
    simpleMappingExceptionResolver.setExceptionMappings(properties)
    simpleMappingExceptionResolver
  }

  @Autowired
  var pageInterceptor: HandlerInterceptor = _

  override def addInterceptors(registry: InterceptorRegistry): Unit = {
    registry.addInterceptor(pageInterceptor).addPathPatterns(Array("/*/list.do**", "/*/*List.do**"): _*)
  }
}
