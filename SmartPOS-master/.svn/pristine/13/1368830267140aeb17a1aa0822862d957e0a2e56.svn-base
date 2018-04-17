package com.yada.spos.pos.rs

import javax.inject.Singleton
import javax.ws.rs.core.MediaType
import javax.ws.rs.{POST, Path, Produces}

import com.yada.spos.pos.SpringContext
import com.yada.spos.pos.ibiz._

@Singleton
@Path("/")
class Down extends IAppInfo with IOnlineParam with ITmk {
  val appInfoBiz = SpringContext.getBean(classOf[IAppInfo])

  @Path("/appInfo")
  @Produces(Array(MediaType.APPLICATION_JSON))
  @POST
  def handle(req: AppInfoReq): AppInfoResp = appInfoBiz.handle(req)

  val onlineParamBiz = SpringContext.getBean(classOf[IOnlineParam])

  @Path("/onlineParam")
  @Produces(Array(MediaType.APPLICATION_JSON))
  @POST
  def handle(req: OnlineParamReq): OnlineParamResp = onlineParamBiz.handle(req)

  val tmkBiz = SpringContext.getBean(classOf[ITmk])

  @Path("/tmk")
  @Produces(Array(MediaType.APPLICATION_JSON))
  @POST
  def handle(req: TmkReq): TmkResp = tmkBiz.handle(req)
}
