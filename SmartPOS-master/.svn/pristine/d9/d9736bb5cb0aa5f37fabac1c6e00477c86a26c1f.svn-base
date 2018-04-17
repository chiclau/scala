package com.yada.spos.common.rs

import javax.ws.rs.container.{ContainerRequestContext, ContainerResponseContext, ContainerResponseFilter}
import javax.ws.rs.core.MediaType

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.codec.binary.Hex

import scala.collection.convert.decorateAll._

/**
  * 记录业务处理消息的类
  */
class LoggingResponseFilter extends ContainerResponseFilter with LazyLogging {
  private val objectMapper = new ObjectMapper()
  objectMapper.registerModule(DefaultScalaModule)

  private val MSG_ID = "MSG_ID"
  private val START_MILLIONS = "START_MILLIONS"

  override def filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext): Unit = {
    val respMsg = mkRespMsg(requestContext, responseContext)
    val costMillions = requestContext.getProperty(START_MILLIONS) match {
      case start: String => (System.currentTimeMillis() - start.toLong).toString
      case _ => "UNKNOW"
    }
    logger.info(
      s"""
         |handle cost millions:$costMillions
         |$respMsg
      """.stripMargin)
  }


  protected def mkRespMsg(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext): String = {
    val msgID = requestContext.getProperty(MSG_ID)
    val entity = Option(responseContext.getEntity)
    val (bodyType, body) = entity.fold({
      "NULL" -> ""
    })(e => {
      responseContext.getMediaType match {
        case MediaType.APPLICATION_JSON_TYPE =>
          "JSON" -> objectMapper.writeValueAsString(e)
        case MediaType.APPLICATION_OCTET_STREAM_TYPE =>
          "HEX" -> Hex.encodeHexString(e.asInstanceOf[Array[Byte]])
        case _ =>
          "UNKNOW" -> e.toString
      }
    })
    val headMsg = responseContext.getHeaders.asScala.map(it => {
      val (key, value) = it
      s"    $key = $value"
    }).mkString("\r\n")

    f"""HTTP RESPONSE UUID[$msgID]
        |METHOD: ${requestContext.getMethod}%-5s ${requestContext.getUriInfo.getRequestUri.toString}
        |${responseContext.getStatus} ${responseContext.getStatusInfo}
        |HEAD
        |$headMsg
        |BODY($bodyType)
        |$body
          """.stripMargin
  }
}
