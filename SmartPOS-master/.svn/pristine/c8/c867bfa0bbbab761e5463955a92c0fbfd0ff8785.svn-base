package com.yada.spos.front.rs

import javax.inject.Singleton
import javax.ws.rs.core.MediaType
import javax.ws.rs.{POST, Path, PathParam, Produces}

import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.front.SpringContext
import com.yada.spos.front.ibiz.IAcq


/**
  * 收单相关服务
  */
@Singleton
@Path("/")
class Acq extends IAcq with LazyLogging {
  val acqBiz = SpringContext.getBean(classOf[IAcq])

  /**
    * 收单app
    *
    * @param firmCode 厂商代码
    * @param sn       设备SN号
    * @param data     发送数据
    * @return
    */
  @Path("/firms/{firmCode}/devices/{sn}")
  @POST
  @Produces(Array(MediaType.APPLICATION_OCTET_STREAM))
  override def handle(@PathParam("firmCode") firmCode: String,
                      @PathParam("sn") sn: String,
                      data: Array[Byte]): Array[Byte] = {
    acqBiz.handle(firmCode, sn, data)
  }
}