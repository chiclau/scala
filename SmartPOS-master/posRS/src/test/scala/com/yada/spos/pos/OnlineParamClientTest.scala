package com.yada.spos.pos

import javax.ws.rs.client.{ClientBuilder, Entity}

import com.yada.spos.common.rs.JsonObjectMapperProvider
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.ResourceConfig

import scala.collection.convert.decorateAll._

/**
  * 联机参数下载
  * Created by nickDu on 2016/10/27.
  */
object OnlineParamClientTest extends App {
  val rc = new ResourceConfig()
  rc.register(classOf[JsonObjectMapperProvider])
  rc.register(classOf[JacksonFeature])
  val client = ClientBuilder.newClient()
  val target = client.target("http://127.0.0.1:60101/posRS/down/onlineParam")
  val response = target.request()
    .post(Entity.json(Map("firmCode" -> "AAAA", "sn" -> "121212", "date" -> "20160928151356", "authorization" -> "asdfdsf").asJava))
  println(s"response status[${response.getStatus}]")
  println(response.readEntity(classOf[String]))
  response.close()
}
