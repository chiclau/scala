package com.yada.spos.front.core.pos.model

/**
  * 设备初始化
  *
  * @param msgID       消息id
  * @param firmCode    厂商代码
  * @param sn          设备SN号
  * @param keyDeviceSn 母pos设备SN号
  * @param timestamp   时间戳
  * @param signValue   签名信息
  */
case class MagInitReq(msgID: String,
                      firmCode: String,
                      sn: String,
                      keyDeviceSn: String,
                      timestamp: String,
                      signValue: String)