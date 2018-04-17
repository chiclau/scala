package com.yada.spos.front

import javax.ws.rs.client.{ClientBuilder, Entity}

import com.yada.spos.common.rs.JsonObjectMapperProvider
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.ResourceConfig


/**
  * 收单测试
  * Created by nickDu on 2016/10/27.
  */
object AcqClientTest extends App {
  val data = Array(-6, -1, 31, 79, -89, 92, 50, 1, 11, 38, 99, -65, 58, 119, -98, -34, 6, 61, -23, 3, 72, 45, 2, 18, -44, 115, -21, -96, 46, -10, 55, 71, 36, -72, -103, 62, 105, 50, 113, 79, -118, -107, -37, 13, 111, -7, -121, -54, 111, -33, -81, 116, 85, -114, 90, -14, -20, -27, -87, 76, -73, -41, -67, -88)
  //  val data = Hex.decodeHex("FAFF1F4FA75C32010B2663BF3A779EDE092D996843E03FFAD473EBA02EF6374724B8993E6932714F8A95DB0D6FF987CA6FDFAF74558E5AF2ECE5A94CB7D7BDA8".toCharArray)
  //  val data = "6000120000130608002020010000C00008990000000036001236353031303031313130343635303035333131303031310009303030303031303031FFFFFFFF05".getBytes()
  val rc = new ResourceConfig()
  rc.register(classOf[JsonObjectMapperProvider])
  rc.register(classOf[JacksonFeature])
  val client = ClientBuilder.newClient()
  val target = client.target("http://127.0.0.1:60102/frontRS/acq/firms/JFMT/devices/SN999999999999999")
  val response = target.request()
    .post(Entity.json(data))
  println(s"response status[${response.getStatus}]")
  println(response.readEntity(classOf[String]))
  response.close()
}
