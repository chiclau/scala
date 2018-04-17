package com.yada.spos.pos.biz

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.yada.spos.common.SignValidate
import com.yada.spos.db.dao.{AppFileHistoryDao, DeviceDao, DeviceInfoUpDao}
import com.yada.spos.db.model.{AppFileHistory, Device, DeviceInfoUp}
import com.yada.spos.pos.ibiz.{Info, TermInfoReq, TermInfoResp}
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.immutable.TreeMap

/**
  * 终端信息上送
  * Created by nickDu on 2016/9/12.
  */
@RunWith(classOf[JUnitRunner])
class TermInfoBizTest extends FlatSpec with Matchers {
  val deviceDao = Mockito.mock(classOf[DeviceDao])
  val deviceInfoUpDao = Mockito.mock(classOf[DeviceInfoUpDao])
  val appFileHistoryDao = Mockito.mock(classOf[AppFileHistoryDao])
  val signValidate = Mockito.mock(classOf[SignValidate])

  val termInfo = new TermInfoBiz
  termInfo.deviceDao = deviceDao
  termInfo.deviceInfoUpDao = deviceInfoUpDao
  termInfo.appFileHistoryDao = appFileHistoryDao
  termInfo.signValidate = signValidate

  val info1 = Info("01", "ota01", "1", "20160930")
  val info2 = Info("02", "app02", "1", "20160930")
  val list = List(info1, info2)
  val req = TermInfoReq("1", "1", "1", "20160709", "xcx", list)

  /**
    * 未找到设备
    */
  "testTermInfoTest1" should "handle successful" in {
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(null)
    val resp = termInfo.handle(req)
    val exceptResp = TermInfoResp("2301", Some("device not found"))
    resp shouldBe exceptResp
  }

  /**
    * 签名错误
    */
  "testTermInfoTest2" should "handle successful" in {
    val device = new Device
    device.zekLmk = "zekLmk"
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val signData = TreeMap("firmCode" -> req.firmCode, "infos" -> req.infos.toString, "sn" -> req.sn, "timestamp" -> req.date)
    Mockito.when(signValidate.handle(device.zekLmk, signData, req.authorization)).thenReturn(false)
    val resp = termInfo.handle(req)
    val exceptResp = TermInfoResp("4403", Some("sign error"))
    resp shouldBe exceptResp
  }

  /**
    * 正常返回
    */
  "testTermInfoTest3" should "handle successful" in {
    val device = new Device
    device.zekLmk = "zekLmk"
    device.devSn = "sn"
    device.firmCode = "1"
    device.prodCode = "1"
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val om = new ObjectMapper()
    om.registerModule(DefaultScalaModule)
    val infoString = om.writeValueAsString(req.infos)
    val signData = TreeMap("firmCode" -> req.firmCode, "infos" -> infoString, "sn" -> req.sn, "timestamp" -> req.date)
    Mockito.when(signValidate.handle(device.zekLmk, signData, req.authorization)).thenReturn(true)
    //查询是否存储过
    Mockito.when(deviceInfoUpDao.findByDevSnAndFirmCodeAndPkgNameAndModuleType(device.devSn, device.firmCode, info1.pkgName, info1.`type`)).thenReturn(null)
    val deviceInfoUp = new DeviceInfoUp
    deviceInfoUp.devSn = "1"
    deviceInfoUp.firmCode = "1"
    deviceInfoUp.pkgName = "app02"
    deviceInfoUp.moduleType = "02"
    Mockito.when(deviceInfoUpDao.findByDevSnAndFirmCodeAndPkgNameAndModuleType(device.devSn, device.firmCode, info2.pkgName, info2.`type`)).thenReturn(deviceInfoUp)
    //查询一些时间信息
    val appFileHistory = new AppFileHistory
    appFileHistory.publicDate = "20190709"
    appFileHistory.creTime = "20190709223300"
    Mockito.when(appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(info1.pkgName, info1.version, device.orgId, device.orgType)).thenReturn(appFileHistory)
    Mockito.when(appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(info2.pkgName, info2.version, device.orgId, device.orgType)).thenReturn(appFileHistory)
    val resp = termInfo.handle(req)
    val exceptResp = TermInfoResp("00", None)
    resp shouldBe exceptResp
  }
}
