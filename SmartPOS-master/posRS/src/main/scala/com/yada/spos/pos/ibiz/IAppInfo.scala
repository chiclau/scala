package com.yada.spos.pos.ibiz

// TODO 注释
trait IAppInfo {
  def handle(req: AppInfoReq): AppInfoResp
}

/**
  *
  * @param msgID         消息Id
  * @param firmCode      厂商代码
  * @param sn            设备SN号
  * @param date          交易日期
  * @param authorization 签名信息
  */
case class AppInfoReq(msgID: String, firmCode: String, sn: String, date: String, authorization: String)

/**
  *
  * @param retCode 返回码
  * @param retMsg  返回信息
  * @param body    信息主体
  */
case class AppInfoResp(retCode: String, retMsg: Option[String], body: Option[AppInfoRespBody])

/**
  *
  * @param appInfos 应用信息列表
  */
case class AppInfoRespBody(appInfos: List[AppInfomation])

/**
  *
  * @param `type`         模块ID  01-OTA 02-受控应用
  * @param pkgName        包名 当type=02时必填
  * @param appName        受控应用名称
  * @param versionName    平台应用版本 格式为*.*.*
  * @param versionCode    更新包当前版本 格式为*
  * @param minVersionCode 更新包最低版本要求  格式为*
  * @param deleteUpdate   是否删除后更新  当type=02时有效，0-否，  1-是
  * @param forceUpdate    是否强制更新  0普通更新，1强制更新，2静默更新
  * @param paramMD5       参数md5值
  */
case class AppInfomation(`type`: String, pkgName: String, appName: String, versionName: String, versionCode: String,
                         minVersionCode: String, deleteUpdate: Option[String], forceUpdate: Option[String], paramMD5: Option[String])