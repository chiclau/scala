package com.yada.spos.pos.rs

import javax.inject.Singleton
import javax.ws.rs.core.MediaType
import javax.ws.rs.{POST, Path, Produces}

import com.yada.spos.pos.SpringContext
import com.yada.spos.pos.ibiz._

@Singleton
@Path("/")
class Mag extends IDeviceSign with IDeviceInit {
  val deviceSignBiz = SpringContext.getBean(classOf[IDeviceSign])

  @Path("/sign")
  @Produces(Array(MediaType.APPLICATION_JSON))
  @POST
  def handle(req: DeviceSignReq): DeviceSignResp = deviceSignBiz.handle(req)

  val deviceInitBiz = SpringContext.getBean(classOf[IDeviceInit])

  @Path("/init")
  @Produces(Array(MediaType.APPLICATION_JSON))
  @POST
  def handle(req: DeviceInitReq): DeviceInitResp = deviceInitBiz.handle(req)

}
