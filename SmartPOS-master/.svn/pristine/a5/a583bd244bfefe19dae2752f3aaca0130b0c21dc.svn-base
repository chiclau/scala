package com.yada.spos.front.core

import javax.ws.rs.core.Response
import javax.ws.rs.ext.{ExceptionMapper, Provider}

import com.typesafe.scalalogging.LazyLogging

@Provider
class ErrorResponseHandler extends ExceptionMapper[ErrorResponseException] with LazyLogging {
  override def toResponse(exception: ErrorResponseException): Response = {
    Response.status(exception.errorCode.toInt).entity(exception.errorMessage).build()
  }
}
