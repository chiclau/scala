package com.yada.spos.front.ibiz

/**
  * 设备初始化
  */
trait IDeviceInit {
  /**
    * 设备初始化
    *
    * @param msgID    消息id
    * @param firmCode 厂商代码
    * @param sn       设备SN号
    * @param req      请求参数
    * @return 返回信息
    */
  def handle(msgID: String,
             firmCode: String,
             sn: String,
             req: DeviceInitReq): String
}

/**
  * 设备初始化请求参数
  *
  * @param keyDeviceSn 母pos设备SN号
  * @param timestamp   时间戳
  * @param signValue   签名信息
  */
case class DeviceInitReq(keyDeviceSn: String,
                         timestamp: String,
                         signValue: String)