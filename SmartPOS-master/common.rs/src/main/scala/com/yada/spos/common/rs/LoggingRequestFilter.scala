package com.yada.spos.common.rs

import java.io.ByteArrayInputStream
import java.util.UUID
import javax.ws.rs.container.{ContainerRequestContext, ContainerRequestFilter, PreMatching}
import javax.ws.rs.core.MediaType

import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.codec.binary.Hex

import scala.collection.convert.decorateAll._

/**
  * 记录请求的过滤器
  */
@PreMatching
class LoggingRequestFilter extends ContainerRequestFilter with LazyLogging {
  private val MSG_ID = "msgID"
  private val START_MILLIONS = "START_MILLIONS"

  override def filter(requestContext: ContainerRequestContext): Unit = {
    val msgID = UUID.randomUUID()
    requestContext.getHeaders.add(MSG_ID, msgID.toString)
    requestContext.setProperty(MSG_ID, msgID)
    requestContext.setProperty(START_MILLIONS, System.currentTimeMillis().toString)
    val msg = mkReqMsg(requestContext)
    logger.info(msg)
  }

  protected def mkReqMsg(requestContext: ContainerRequestContext): String = {
    val msgID = requestContext.getProperty(MSG_ID)
    val len = requestContext.getEntityStream.available()
    val bts = new Array[Byte](len)
    requestContext.getEntityStream.read(bts)
    requestContext.setEntityStream(new ByteArrayInputStream(bts))
    val (bodyType, body) = requestContext.getMediaType match {
      case MediaType.APPLICATION_JSON_TYPE =>
        "JSON" -> new String(bts)
      case _ =>
        "HEX" -> Hex.encodeHexString(bts)
    }
    val headMsg = requestContext.getHeaders.asScala.map(it => {
      val (key, value) = it
      s"    $key = $value"
    }).mkString("\r\n")
    f"""
       |HTTP REQUEST UUID[$msgID]
       |METHOD: ${requestContext.getMethod}%-5s ${requestContext.getUriInfo.getRequestUri.toString}
       |HEAD
       |$headMsg
       |BODY($bodyType)
       |$body
      """.stripMargin
  }
}
