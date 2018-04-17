package com.yada.spos.common.rs

import java.net.URI

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.jetty.JettyHttpContainerFactory
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent


class RESTServerBySpring(rsList: List[Class[_]]) extends ApplicationListener[ContextRefreshedEvent] with LazyLogging {
  override def onApplicationEvent(e: ContextRefreshedEvent): Unit = {
    // 读取netty监听端口
    val port = ConfigFactory.load().getInt("port")
    val resourceConfig = new ResourceConfig()
    rsList.foreach(rs => resourceConfig.register(rs))
    // 增加json转换scala对象的处理
    resourceConfig.register(classOf[JsonObjectMapperProvider])
    resourceConfig.register(classOf[JacksonFeature])
    // 增加日志
    resourceConfig.register(classOf[LoggingResponseFilter])
    resourceConfig.register(classOf[LoggingRequestFilter])
    resourceConfig.register(classOf[LoggingThrowableHandler])
    JettyHttpContainerFactory.createServer(URI.create(s"http://0.0.0.0:$port"), resourceConfig)
    logger.info(s"server start...port[$port]")
  }
}