package com.yada.spos.common.rs

import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.{ExceptionMapper, Provider}

import com.typesafe.scalalogging.LazyLogging

/**
  * Created by pangChangSong on 2016/10/12.
  * 记录异常信息
  */
@Provider
class LoggingThrowableHandler extends ExceptionMapper[Throwable] with LazyLogging {
  override def toResponse(exception: Throwable): Response = {
    exception match {
      case e: WebApplicationException =>
        logger.warn(e.getMessage)
        e.getResponse
      case e: Throwable =>
        logger.error("", exception)
        Response.status(500).build()
    }


  }
}