package com.yada.spos.pos.ibiz

import javax.ws.rs.core.MediaType
import javax.ws.rs.{POST, Path, Produces}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.common.SignValidate
import com.yada.spos.db.dao.{AppFileHistoryDao, DeviceDao, DeviceInfoUpDao}
import com.yada.spos.db.model.DeviceInfoUp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Scope, ScopedProxyMode}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import scala.collection.immutable.TreeMap

/**
  * 终端信息上送
  * Created by nickDu on 2016/9/6.
  */
trait ITermInfo {
  def handle(req: TermInfoReq): TermInfoResp
}

/**
  *
  * @param msgID         消息id
  * @param firmCode      厂商代码
  * @param sn            设备SN号
  * @param date          交易日期
  * @param authorization 签名信息
  * @param infos         终端信息列表
  */
case class TermInfoReq(msgID: String,
                       firmCode: String,
                       sn: String,
                       date: String,
                       authorization: String,
                       infos: List[Info])

/**
  *
  * @param retCode 返回码
  * @param retMsg  返回信息
  */
case class TermInfoResp(retCode: String,
                        retMsg: Option[String])

/**
  * @param `type`     模块ID 01-固件 02-应用
  * @param pkgName    包名  当type=02时必填
  * @param version    当前版本  格式为*.*.**
  * @param updateTime 更新时间  格式为yyyyMMddHHmmss
  */
case class Info(pkgName: String,
                `type`: String,
                updateTime: String,
                version: String
               )
