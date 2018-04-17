package com.yada.spos.heart.rs

import javax.inject.Singleton
import javax.ws.rs._
import javax.ws.rs.core.MediaType

import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.heart.SpringAplicationContext
import com.yada.spos.heart.ibiz.{HeartReq, HeartResp, IHeart}

@Singleton
@Path("/")
class Heart extends IHeart with LazyLogging {
  val biz = SpringAplicationContext.getBean(classOf[IHeart])

  @Path("/firms/{firmCode}/devices/{sn}/upload")
  @POST
  @Produces(Array(MediaType.APPLICATION_JSON))
  def handle(@HeaderParam("msgID") msgID: String,
             @PathParam("firmCode") firmCode: String,
             @PathParam("sn") sn: String,
             @HeaderParam("X-SPOS-Date") timestamp: String,
             @HeaderParam("X-SPOS-Authorization") sign: String,
             req: HeartReq): HeartResp = {
    biz.handle(msgID, firmCode, sn, timestamp, sign, req)
  }
}