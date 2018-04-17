package com.yada.spos.pos

import javax.ws.rs.client.{ClientBuilder, Entity}

import scala.collection.convert.decorateAll._

/**
  * Created by locky on 2016/8/31.
  */
object TestApp extends App {
  val client = ClientBuilder.newClient()
  val target = client.target("http://127.0.0.1:60101/posRS/down/appInfo")
  val response = target.request()
    .post(Entity.json(Map(
      "firmCode" -> "AAAA", "sn" -> "121212", "date" -> "20160928151356", "authorization" -> "asdfdsf").asJava))
  println(s"response status[${response.getStatus}]")
  println(response.readEntity(classOf[String]))
  response.close()
}
