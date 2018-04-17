package com.yada.spos.heart

import java.util.Date
import javax.ws.rs.client.{ClientBuilder, Entity}

import com.yada.spos.common.rs.JsonObjectMapperProvider
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.ResourceConfig

import scala.collection.convert.decorateAll._

object HeartClientTest extends App {
  val rc = new ResourceConfig()
  rc.register(classOf[JsonObjectMapperProvider])
  rc.register(classOf[JacksonFeature])
  val client = ClientBuilder.newClient()
  val target = client.target("http://127.0.0.1:60103/heartServer/v1/firms/JFMT/devices/SN999999999999999/upload")
  //  val target = client.target("http://22.188.177.27:80/heartServer/v1/firms/JFMT/devices/SN999999999999999/upload")
  val response = target.request()
    .header("X-SPOS-Date", String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", new Date))
    .header("X-SPOS-Authorization", "dsalksdjflaksjdfsldfjlsdjfsldkfjsld")
    .post(Entity.json(Map("state" -> "123456", "station" -> "987456123123122").asJava))
  println(s"response status[${response.getStatus}]")
  println(response.readEntity(classOf[String]))
  response.close()
}
