package com.yada.spos.pos.rs

import javax.inject.Singleton
import javax.ws.rs.core.MediaType
import javax.ws.rs.{POST, Path, Produces}

import com.yada.spos.pos.SpringContext
import com.yada.spos.pos.ibiz._

@Singleton
@Path("/")
class Up extends ITermInfo with IAppUpdateResult {
  val termInfoBiz = SpringContext.getBean(classOf[ITermInfo])

  @Path("/termInfo")
  @Produces(Array(MediaType.APPLICATION_JSON))
  @POST
  def handle(req: TermInfoReq): TermInfoResp = termInfoBiz.handle(req)

  val appUpdateResultBiz = SpringContext.getBean(classOf[IAppUpdateResult])

  @Path("/appUpdateResult")
  @Produces(Array(MediaType.APPLICATION_JSON))
  @POST
  def handle(req: AppUpdateResultReq): AppUpdateResultResp = appUpdateResultBiz.handle(req)
}
