package com.yada.spos.front.rs

import javax.inject.Singleton
import javax.ws.rs._
import javax.ws.rs.core.MediaType

import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.front.SpringContext
import com.yada.spos.front.ibiz._


/**
  * 智能POS相关服务
  */
@Singleton
@Path("/")
class Spos extends ISposService with IDeviceInit with LazyLogging {

  val deviceInitBiz = SpringContext.getBean(classOf[IDeviceInit])
  val sposServiceBiz = SpringContext.getBean(classOf[ISposService])

  /**
    * 终端信息上送
    *
    * @param msgID         消息id
    * @param firmCode      厂商代码
    * @param sn            设备SN号
    * @param date          交易日期
    * @param authorization 签名信息
    * @param req           终端信息列表
    * @return 返回码
    */
  @Path("/firms/{firmCode}/devices/{sn}/informations")
  @POST
  @Produces(Array(MediaType.APPLICATION_JSON))
  override def terminalInfoUpload(@HeaderParam("msgID") msgID: String,
                                  @PathParam("firmCode") firmCode: String,
                                  @PathParam("sn") sn: String,
                                  @HeaderParam("X-SPOS-Date") date: String,
                                  @HeaderParam("X-SPOS-Authorization") authorization: String,
                                  req: TermInfosReq): String = {

    sposServiceBiz.terminalInfoUpload(msgID, firmCode, sn, date, authorization, req)
  }

  /**
    * 下载更新结果上送
    *
    * @param msgID         消息id
    * @param firmCode      厂商代码
    * @param sn            设备SN号
    * @param date          交易日期
    * @param authorization 签名信息
    * @param req           参数
    * @return 返回码
    */
  @Path("/firms/{firmCode}/devices/{sn}/updateResults/updateResult")
  @POST
  @Produces(Array(MediaType.APPLICATION_JSON))
  override def appUpdateResult(@HeaderParam("msgID") msgID: String,
                               @PathParam("firmCode") firmCode: String,
                               @PathParam("sn") sn: String,
                               @HeaderParam("X-SPOS-Date") date: String,
                               @HeaderParam("X-SPOS-Authorization") authorization: String,
                               req: AppUpdateResultReq): String = {
    sposServiceBiz.appUpdateResult(msgID, firmCode, sn, date, authorization, req)
  }

  /**
    * 应用信息查询
    *
    * @param msgID         消息id
    * @param firmCode      厂商代码
    * @param sn            设备SN号
    * @param date          交易日期
    * @param authorization 签名信息
    * @return 返回查询结果
    */
  @Path("/firms/{firmCode}/devices/{sn}/applications")
  @GET
  @Produces(Array(MediaType.APPLICATION_JSON))
  override def queryAppInfo(@HeaderParam("msgID") msgID: String,
                            @PathParam("firmCode") firmCode: String,
                            @PathParam("sn") sn: String,
                            @HeaderParam("X-SPOS-Date") date: String,
                            @HeaderParam("X-SPOS-Authorization") authorization: String): String = {
    sposServiceBiz.queryAppInfo(msgID, firmCode, sn, date, authorization)
  }

  /**
    * 收单主密钥下载
    *
    * @param msgID         消息id
    * @param firmCode      厂商代码
    * @param sn            设备SN号
    * @param date          交易日期
    * @param authorization 签名信息
    * @return 返回下载结果
    */
  @Path("/firms/{firmCode}/devices/{sn}/tmks/tmk")
  @GET
  @Produces(Array(MediaType.APPLICATION_JSON))
  override def downloadTmk(@HeaderParam("msgID") msgID: String,
                           @PathParam("firmCode") firmCode: String,
                           @PathParam("sn") sn: String,
                           @HeaderParam("X-SPOS-Date") date: String,
                           @HeaderParam("X-SPOS-Authorization") authorization: String): String = {
    sposServiceBiz.downloadTmk(msgID, firmCode, sn, date, authorization)
  }

  /**
    * 联机参数下载
    *
    * @param msgID         消息id
    * @param firmCode      厂商代码
    * @param sn            设备SN号
    * @param date          交易日期
    * @param authorization 签名信息
    * @return 返回下载结果
    */
  @Path("/firms/{firmCode}/devices/{sn}/online/params")
  @GET
  @Produces(Array(MediaType.APPLICATION_JSON))
  override def onlineParams(@HeaderParam("msgID") msgID: String,
                            @PathParam("firmCode") firmCode: String,
                            @PathParam("sn") sn: String,
                            @HeaderParam("X-SPOS-Date") date: String,
                            @HeaderParam("X-SPOS-Authorization") authorization: String): String = {
    sposServiceBiz.onlineParams(msgID, firmCode, sn, date, authorization)
  }

  /**
    * 设备签到
    *
    * @param msgID    消息id
    * @param firmCode 厂商代码
    * @param sn       设备SN号
    * @param date     交易日期
    * @return 返回下载结果
    */
  @Path("/firms/{firmCode}/devices/{sn}/sign")
  @GET
  @Produces(Array(MediaType.APPLICATION_JSON))
  override def deviceSign(@HeaderParam("msgID") msgID: String,
                          @PathParam("firmCode") firmCode: String,
                          @PathParam("sn") sn: String,
                          @HeaderParam("X-SPOS-Date") date: String): String = {
    sposServiceBiz.deviceSign(msgID, firmCode, sn, date)
  }

  /**
    * 设备初始化
    *
    * @param msgID    消息id
    * @param firmCode 厂商代码
    * @param sn       设备SN号
    * @param req      请求参数
    * @return 返回信息
    */
  @Path("/firms/{firmCode}/devices/{sn}/initializations/initialization")
  @POST
  @Produces(Array(MediaType.APPLICATION_JSON))
  override def handle(@HeaderParam("msgID") msgID: String,
                      @PathParam("firmCode") firmCode: String,
                      @PathParam("sn") sn: String,
                      req: DeviceInitReq): String = {
    deviceInitBiz.handle(msgID, firmCode, sn, req)
  }
}

