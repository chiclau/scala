package com.yada.spos.front.ibiz

/**
  * 前置服务
  */
trait ISposService {
  /**
    * 终端信息上送
    *
    * @param msgID         消息id
    * @param firmCode      厂商代码
    * @param sn            设备SN号
    * @param date          交易日期
    * @param authorization 签名信息
    * @param req           终端信息列表
    * @return 返回码
    */
  def terminalInfoUpload(msgID: String,
                         firmCode: String,
                         sn: String,
                         date: String,
                         authorization: String,
                         req: TermInfosReq): String

  /**
    * 下载更新结果上送
    *
    * @param msgID         消息id
    * @param firmCode      厂商代码
    * @param sn            设备SN号
    * @param date          交易日期
    * @param authorization 签名信息
    * @param req           参数
    * @return 返回码
    */
  def appUpdateResult(msgID: String,
                      firmCode: String,
                      sn: String,
                      date: String,
                      authorization: String,
                      req: AppUpdateResultReq): String

  /**
    * 应用信息查询
    *
    * @param msgID         消息id
    * @param firmCode      厂商代码
    * @param sn            设备SN号
    * @param date          交易日期
    * @param authorization 签名信息
    * @return 返回查询结果
    */
  def queryAppInfo(msgID: String,
                   firmCode: String,
                   sn: String,
                   date: String,
                   authorization: String): String


  /**
    * 收单主密钥下载
    *
    * @param msgID         消息id
    * @param firmCode      厂商代码
    * @param sn            设备SN号
    * @param date          交易日期
    * @param authorization 签名信息
    * @return 返回下载结果
    */
  def downloadTmk(msgID: String,
                  firmCode: String,
                  sn: String,
                  date: String,
                  authorization: String): String

  /**
    * 联机参数下载
    *
    * @param msgID         消息id
    * @param firmCode      厂商代码
    * @param sn            设备SN号
    * @param date          交易日期
    * @param authorization 签名信息
    * @return 返回下载结果
    */
  def onlineParams(msgID: String,
                   firmCode: String,
                   sn: String,
                   date: String,
                   authorization: String): String

  /**
    * 设备签到
    *
    * @param msgID    消息id
    * @param firmCode 厂商代码
    * @param sn       设备SN号
    * @param date     交易日期
    * @return 返回下载结果
    */
  def deviceSign(msgID: String,
                 firmCode: String,
                 sn: String,
                 date: String): String

}

/**
  *
  * @param infos 终端信息列表
  */
case class TermInfosReq(infos: List[TermInfo])


/**
  * @param `type`     模块ID 01-固件 02-应用
  * @param pkgName    包名  当type=02时必填
  * @param version    当前版本  格式为*.*.**
  * @param updateTime 更新时间  格式为yyyyMMddHHmmss
  */
case class TermInfo(`type`: String,
                    pkgName: String,
                    version: String,
                    updateTime: String)

/**
  * 下载更新结果上送
  *
  * @param updateType 结果类型  01下载  02更新
  * @param `type`     模块ID 01-OTA 02-受控应用
  * @param pkgName    包名   包名，固件取下面对应的值：OTA
  * @param startTime  开始时间  格式为yyyyMMddHHmmss
  * @param endTime    结束时间  格式为yyyyMMddHHmmss
  * @param result     结果      0成功、1失败
  * @param desc       结果描述  失败时填写失败原因
  */
case class AppUpdateResultReq(updateType: String,
                              `type`: String,
                              pkgName: String,
                              startTime: String,
                              endTime: String,
                              result: String,
                              desc: String)