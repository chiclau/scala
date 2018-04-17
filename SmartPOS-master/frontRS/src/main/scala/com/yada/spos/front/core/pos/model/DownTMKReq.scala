package com.yada.spos.front.core.pos.model

/**
  * 收单主密钥下载
  *
  * @param msgID         消息id
  * @param firmCode      厂商代码
  * @param sn            设备SN号
  * @param date          交易日期
  * @param authorization 签名信息
  */
case class DownTMKReq(msgID: String,
                      firmCode: String,
                      sn: String,
                      date: String,
                      authorization: String)