package com.yada.spos.front

import java.util.Date
import javax.ws.rs.client.{ClientBuilder, Entity}

import com.yada.spos.common.rs.JsonObjectMapperProvider
import com.yada.spos.front.ibiz.TermInfo
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.ResourceConfig

import scala.collection.convert.decorateAll._

/**
  * 终端信息上送
  *
  * Created by nickDu on 2016/10/27.
  */
object TerminalInfoUploadClientTest extends App {
  val rc = new ResourceConfig()
  rc.register(classOf[JsonObjectMapperProvider])
  rc.register(classOf[JacksonFeature])
  val client = ClientBuilder.newClient()
  val target = client.target("http://127.0.0.1:60102/frontRS/v1/firms/JFMT/devices/SN999999999999999/informations")
  val infos = Array(TermInfo("1", "1", "1", "1"), TermInfo("2", "2", "2", "2"))
  val response = target.request()
    .header("X-SPOS-Date", String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", new Date))
    .header("X-SPOS-Authorization", "dsalksdjflaksjdfsldfjlsdjfsldkfjsld")
    .post(Entity.json(Map("infos" -> infos).asJava))
  println(s"response status[${response.getStatus}]")
  println(response.readEntity(classOf[String]))
  response.close()
}
