package com.yada.spos.pos.ibiz

//TODO 注释
trait IAppUpdateResult {
  def handle(req: AppUpdateResultReq): AppUpdateResultResp
}


/**
  *
  * @param msgID         消息Id
  * @param firmCode      厂商代码
  * @param sn            设备SN号
  * @param date          交易日期
  * @param authorization 签名信息
  * @param updateType    结果类型  01下载  02更新
  * @param `type`        模块ID 01-OTA 02-受控应用
  * @param pkgName       包名   包名，固件取下面对应的值：OTA
  * @param version       当前版本
  * @param startTime     开始时间  格式为yyyyMMddHHmmss
  * @param endTime       结束时间  格式为yyyyMMddHHmmss
  * @param result        结果      0成功、1失败
  * @param desc          结果描述  失败时填写失败原因
  */
case class AppUpdateResultReq(msgID: String,
                              firmCode: String,
                              sn: String,
                              date: String,
                              authorization: String,
                              updateType: String,
                              `type`: String,
                              pkgName: String,
                              version: String,
                              startTime: String,
                              endTime: String,
                              result: String,
                              desc: String)

/**
  *
  * @param retCode 返回码
  * @param retMsg  返回信息
  */
case class AppUpdateResultResp(retCode: String, retMsg: Option[String])
