package com.yada.spos.front.core.pos.model

import com.yada.spos.front.ibiz.TermInfo

/**
  * 终端信息上送
  *
  * @param msgID         消息id
  * @param firmCode      厂商代码
  * @param sn            设备SN号
  * @param date          交易日期
  * @param authorization 签名信息
  * @param infos         终端信息列表
  */
case class UpTermInfoReq(msgID: String,
                         firmCode: String,
                         sn: String,
                         date: String,
                         authorization: String,
                         infos: List[TermInfo])