package com.yada.spos.front.core.pos

import javax.ws.rs.client.{ClientBuilder, Entity}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.yada.spos.common.rs.JsonObjectMapperProvider
import com.yada.spos.front.core.ErrorResponseException
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.stereotype.Component

/**
  * POS-RS服务的客户端
  */
trait IRSClient {
  @throws(clazz = classOf[ErrorResponseException])
  def send[REQ, RESP](entity: RSClientEntity[REQ, RESP]): RESP
}

@Component
class RSClient extends IRSClient {
  val resourceConfig = new ResourceConfig()
  resourceConfig.register(classOf[JsonObjectMapperProvider])
  resourceConfig.register(classOf[JacksonFeature])

  /**
    *
    * @param entity 客户端实体
    * @tparam REQ  请求类型
    * @tparam RESP 响应类型
    * @return 响应
    */
  @throws(clazz = classOf[ErrorResponseException])
  override def send[REQ, RESP](entity: RSClientEntity[REQ, RESP]): RESP = {
    val client = ClientBuilder.newClient(resourceConfig)
    val target = client.target(entity.url)
    val response = target.request()
      .post(Entity.json(entity.reqData))
    if (response.getStatus == 200) {
      val respBody = response.readEntity(entity.respType)
      response.close()
      respBody
    } else {
      response.close()
      throw new ErrorResponseException("500", "服务器发生错误")
    }
  }
}

/**
  * 智能POS客户端实体
  *
  * @param url      请求的URL
  * @param reqData  请求数据
  * @param respType 请求的响应
  * @tparam REQ  请求类型
  * @tparam RESP 响应类型
  */
case class RSClientEntity[REQ, RESP](url: String, reqData: REQ, respType: Class[RESP])
