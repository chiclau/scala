package com.yada.spos.front.core.pos.model

/**
  * 应用信息查询
  *
  * @param msgID         消息id
  * @param firmCode      厂商代码
  * @param sn            设备SN号
  * @param date          交易日期
  * @param authorization 签名信息
  */
case class DownAppInfoReq(msgID: String,
                          firmCode: String,
                          sn: String,
                          date: String,
                          authorization: String)
