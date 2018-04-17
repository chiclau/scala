package com.yada.spos.pos.ibiz

import com.typesafe.scalalogging.LazyLogging

/**
  * 收单主密钥下载
  * Created by nickDu on 2016/9/7.
  */
trait ITmk extends LazyLogging {
  def handle(req: TmkReq): TmkResp
}

/**
  *
  * @param msgID         消息Id
  * @param firmCode      厂商代码
  * @param sn            设备SN号
  * @param date          交易日期
  * @param authorization 签名信息
  */
case class TmkReq(msgID: String, firmCode: String, sn: String, date: String, authorization: String)

/**
  *
  * @param retCode 返回码
  * @param retMsg  返回信息
  * @param body    信息主体
  */
case class TmkResp(retCode: String, retMsg: Option[String], body: Option[TmkRespBody])

/**
  *
  * @param tmkDek 主密钥
  * @param tmkKcv 校验值
  */
case class TmkRespBody(tmkDek: String, tmkKcv: String)
