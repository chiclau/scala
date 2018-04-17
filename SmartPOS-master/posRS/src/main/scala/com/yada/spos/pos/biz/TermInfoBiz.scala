package com.yada.spos.pos.biz

import javax.ws.rs.core.MediaType
import javax.ws.rs.{POST, Path, Produces}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.common.SignValidate
import com.yada.spos.db.dao.{AppFileHistoryDao, DeviceDao, DeviceInfoUpDao}
import com.yada.spos.db.model.DeviceInfoUp
import com.yada.spos.pos.ibiz.{ITermInfo, TermInfoReq, TermInfoResp}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Scope, ScopedProxyMode}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import scala.collection.immutable.TreeMap

/**
  * 终端信息上送
  * Created by nickDu on 2016/9/6.
  */
@Service
@Transactional
class TermInfoBiz extends ITermInfo with LazyLogging {
  @Autowired
  var deviceDao: DeviceDao = _
  @Autowired
  var deviceInfoUpDao: DeviceInfoUpDao = _
  @Autowired
  var signValidate: SignValidate = _
  @Autowired
  var appFileHistoryDao: AppFileHistoryDao = _

  @Path("/termInfo")
  @Produces(Array(MediaType.APPLICATION_JSON))
  @POST
  def handle(req: TermInfoReq): TermInfoResp = {
    logger.info(s"msgID is [${req.msgID}]")
    val device = deviceDao.findByDevSnAndFirmCode(req.sn, req.firmCode)
    if (device == null) {
      TermInfoResp("2301", Some("device not found"))
    } else if (device.zekLmk == null || device.zekLmk.isEmpty) {
      TermInfoResp("2302", Some("device no sign in"))
    } else {
      val om = new ObjectMapper()
      om.registerModule(DefaultScalaModule)
      val infoString = om.writeValueAsString(req.infos)
      //签名验证
      val signData = TreeMap("firmCode" -> req.firmCode, "infos" -> infoString, "sn" -> req.sn, "timestamp" -> req.date)
      if (signValidate.handle(device.zekLmk, signData, req.authorization)) {
        //将设备原有上传的信息全部置为无效
        deviceInfoUpDao.updateInfoStatusByDevSnAndFirmCode("0", device.devSn, device.firmCode)
        req.infos.foreach(f => {
          var deviceInfoUp = deviceInfoUpDao.findByDevSnAndFirmCodeAndPkgNameAndModuleType(device.devSn, device.firmCode, f.pkgName, f.`type`)
          if (deviceInfoUp == null) {
            deviceInfoUp = new DeviceInfoUp
            deviceInfoUp.devSn = device.devSn
            deviceInfoUp.firmCode = device.firmCode
            deviceInfoUp.pkgName = f.pkgName
            deviceInfoUp.moduleType = f.`type`
          }
          deviceInfoUp.prodCode = device.prodCode
          deviceInfoUp.currentVersion = f.version
          deviceInfoUp.updTime = f.updateTime
          deviceInfoUp.infoStatus = "1"

          val app = appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(f.pkgName, f.version, device.orgId, device.orgType)
          if (app != null) {
            deviceInfoUp.appUpdTime = app.publicDate
            deviceInfoUp.creTime = app.creTime
          }
          deviceInfoUpDao.saveAndFlush(deviceInfoUp)
        })
        TermInfoResp("00", None)
      }
      else {
        TermInfoResp("4403", Some("sign error"))
      }
    }
  }
}