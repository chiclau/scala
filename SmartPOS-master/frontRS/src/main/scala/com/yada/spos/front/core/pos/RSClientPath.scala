package com.yada.spos.front.core.pos

import com.typesafe.config.ConfigFactory
import com.yada.spos.front.core.pos.model._

/**
  * 智能POS客户端路径类
  */
class RSClientPath[REQ, RESP](url: String, reqType: Class[REQ], respType: Class[RESP]) {
  def createEntity(req: REQ): RSClientEntity[REQ, RESP] = {
    RSClientEntity(url, req, respType)
  }
}


object RSClientPath {

  val config = ConfigFactory.load()
  /**
    * 上传类：终端信息
    */
  val UP_TERM_INFO = new RSClientPath(s"${config.getString("posRS.url")}/up/termInfo", classOf[UpTermInfoReq], classOf[String])
  /**
    * 上传类: 下载更新结果上送
    */
  val UP_APP_UPDATE_RESULT = new RSClientPath(s"${config.getString("posRS.url")}/up/appUpdateResult", classOf[UpUpdateResultReq], classOf[String])
  /**
    * 下载类：应用信息查询
    */
  val DOWN_APP_INFO = new RSClientPath(s"${config.getString("posRS.url")}/down/appInfo", classOf[DownAppInfoReq], classOf[String])
  /**
    * 下载类：收单主密钥下载
    */
  val DOWN_TMK = new RSClientPath(s"${config.getString("posRS.url")}/down/tmk", classOf[DownTMKReq], classOf[String])
  /**
    * 下载类：联机参数下载
    */
  val DOWN_ONLINE_PARAM = new RSClientPath(s"${config.getString("posRS.url")}/down/onlineParam", classOf[DownOnlineParamReq], classOf[String])
  /**
    * 管理类：设备签到
    */
  val MAG_SIGN = new RSClientPath(s"${config.getString("posRS.url")}/mag/sign", classOf[MagSignReq], classOf[String])
  /**
    * 管理类：设备初始化
    */
  val MAG_INIT = new RSClientPath(s"${config.getString("posRS.url")}/mag/init", classOf[MagInitReq], classOf[String])
}