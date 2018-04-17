package com.yada.spos.front.biz

import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.front.core.pos
import com.yada.spos.front.core.pos.RSClientPath
import com.yada.spos.front.core.pos.model._
import com.yada.spos.front.ibiz.{AppUpdateResultReq, ISposService, TermInfosReq}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class SposServiceBiz extends ISposService with LazyLogging {
  @Autowired
  var posRSClient: pos.RSClient = _

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
  override def terminalInfoUpload(msgID: String,
                                  firmCode: String,
                                  sn: String,
                                  date: String,
                                  authorization: String,
                                  req: TermInfosReq): String = {

    val data = RSClientPath.UP_TERM_INFO.createEntity(UpTermInfoReq(
      msgID,
      firmCode,
      sn,
      date,
      authorization,
      req.infos))
    posRSClient.send(data)
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
  override def appUpdateResult(msgID: String,
                               firmCode: String,
                               sn: String,
                               date: String,
                               authorization: String,
                               req: AppUpdateResultReq): String = {
    val data = RSClientPath.UP_APP_UPDATE_RESULT.createEntity(UpUpdateResultReq(
      msgID,
      firmCode,
      sn,
      date,
      authorization,
      req.updateType,
      req.`type`,
      req.pkgName,
      req.startTime,
      req.endTime,
      req.result,
      req.desc))
    posRSClient.send(data)
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
  override def queryAppInfo(msgID: String,
                            firmCode: String,
                            sn: String,
                            date: String,
                            authorization: String): String = {
    val data = RSClientPath.DOWN_APP_INFO.createEntity(DownAppInfoReq(
      msgID,
      firmCode,
      sn,
      date,
      authorization
    ))
    posRSClient.send(data)
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
  override def downloadTmk(msgID: String,
                           firmCode: String,
                           sn: String,
                           date: String,
                           authorization: String): String = {
    val data = RSClientPath.DOWN_TMK.createEntity(DownTMKReq(
      msgID,
      firmCode,
      sn,
      date,
      authorization
    ))
    posRSClient.send(data)
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
  override def onlineParams(msgID: String,
                            firmCode: String,
                            sn: String,
                            date: String,
                            authorization: String): String = {
    val data = RSClientPath.DOWN_ONLINE_PARAM.createEntity(DownOnlineParamReq(
      msgID,
      firmCode,
      sn,
      date,
      authorization
    ))
    posRSClient.send(data)
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
  override def deviceSign(msgID: String,
                          firmCode: String,
                          sn: String,
                          date: String): String = {
    val data = RSClientPath.MAG_SIGN.createEntity(MagSignReq(
      msgID,
      firmCode,
      sn,
      date
    ))
    posRSClient.send(data)
  }
}

