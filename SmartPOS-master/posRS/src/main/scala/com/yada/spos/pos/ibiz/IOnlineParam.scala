package com.yada.spos.pos.ibiz


/**
  * 联机参数下载
  * Created by nickDu on 2016/9/7.
  */
trait IOnlineParam {

  def handle(req: OnlineParamReq): OnlineParamResp

}

/**
  *
  * @param msgID         消息Id
  * @param firmCode      厂商代码
  * @param sn            设备SN号
  * @param date          交易日期
  * @param authorization 签名信息
  */
case class OnlineParamReq(msgID: String, firmCode: String, sn: String, date: String, authorization: String)

/**
  *
  * @param retCode 返回码
  * @param retMsg  返回信息
  * @param body    信息主体
  */
case class OnlineParamResp(retCode: String, retMsg: Option[String], body: Option[Map[String, String]])

