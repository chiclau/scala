package com.yada.spos.front

import java.util.Date
import javax.ws.rs.client.ClientBuilder

import com.yada.spos.common.rs.JsonObjectMapperProvider
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.ResourceConfig

/**
  * 设备签到
  *
  * Created by nickDu on 2016/10/27.
  */
object DeviceSignClientTest extends App {
  val rc = new ResourceConfig()
  rc.register(classOf[JsonObjectMapperProvider])
  rc.register(classOf[JacksonFeature])
  val client = ClientBuilder.newClient()
  val target = client.target("http://127.0.0.1:60102/frontRS/v1/firms/JFMT/devices/SN999999999999999/sign")
  val response = target.request()
    .header("X-SPOS-Date", String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", new Date))
    .header("X-SPOS-Authorization", "dsalksdjflaksjdfsldfjlsdjfsldkfjsld")
    .get
  println(s"response status[${response.getStatus}]")
  println(response.readEntity(classOf[String]))
  response.close()
}
