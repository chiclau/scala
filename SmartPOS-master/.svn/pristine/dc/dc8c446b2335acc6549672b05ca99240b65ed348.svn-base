package com.yada.spos.pos.ibiz

// TODO 注释
trait IDeviceInit {
  def handle(req: DeviceInitReq): DeviceInitResp
}

/**
  *
  * @param msgID         消息Id
  * @param firmCode      厂商代码
  * @param sn            设备SN号
  * @param date          交易日期
  * @param authorization 签名信息
  * @param keyDeviceSn   母pos设备SN号
  * @param timestamp     时间戳
  * @param signValue     签名信息
  */
case class DeviceInitReq(msgID: String, firmCode: String, sn: String, date: String, authorization: String, keyDeviceSn: String, timestamp: String, signValue: String)

/**
  *
  * @param retCode 返回码
  * @param retMsg  返回信息
  * @param body    信息主体
  */
case class DeviceInitResp(retCode: String, retMsg: Option[String], body: Option[DeviceInitRespBody])

/**
  *
  * @param dek_zmk 区域密钥保护的设备密钥
  * @param dek_kcv 设备密钥校验值
  */
case class DeviceInitRespBody(dek_zmk: String, dek_kcv: String)
