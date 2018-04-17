package com.yada.spos.pos.ibiz

/**
  * Created by locky on 2016/10/27.
  */
trait IDeviceSign {
  def handle(req: DeviceSignReq): DeviceSignResp
}

/**
  *
  * @param msgID         消息Id
  * @param firmCode      厂商代码
  * @param sn            设备SN号
  * @param date          交易日期
  * @param authorization 签名信息
  */
case class DeviceSignReq(msgID: String, firmCode: String, sn: String, date: String, authorization: String)

/**
  *
  * @param retCode 返回码
  * @param retMsg  返回信息
  * @param body    信息主体
  */
case class DeviceSignResp(retCode: String, retMsg: Option[String], body: Option[DeviceSignRespBody])

/**
  *
  * @param zek_dek    设备密钥保护的通讯密钥
  * @param zek_kcv    通讯密钥校验值
  * @param merchantId 商户号
  * @param terminalId 终端号
  */
case class DeviceSignRespBody(zek_dek: String, zek_kcv: String, merchantId: String, terminalId: String)
