package com.yada.spos.pos.biz

import com.yada.spos.common.SignValidate
import com.yada.spos.db.dao.{AppFileHistoryDao, DeviceDao, DownUpdateUpDao}
import com.yada.spos.db.model.Device
import com.yada.spos.pos.ibiz.{AppUpdateResultReq, AppUpdateResultResp}
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.immutable.TreeMap

/**
  * 下载更新结果上送测试
  * Created by nickDu on 2016/9/12.
  */
@RunWith(classOf[JUnitRunner])
class AppUpdateResultBizTest extends FlatSpec with Matchers {
  val deviceDao = Mockito.mock(classOf[DeviceDao])
  val downUpdateUpDao = Mockito.mock(classOf[DownUpdateUpDao])
  val appFileHistoryDao = Mockito.mock(classOf[AppFileHistoryDao])
  val signValidate = Mockito.mock(classOf[SignValidate])

  val appUpdateResult = new AppUpdateResultBiz()
  appUpdateResult.deviceDao = deviceDao
  appUpdateResult.downUpdateUpDao = downUpdateUpDao
  appUpdateResult.appFileHistoryDao = appFileHistoryDao
  appUpdateResult.signValidate = signValidate

  val req = AppUpdateResultReq("1", "1", "1", "20160709", "xcx", "01", "02", "pkgName", "version", "startTime", "endTime", "result", "desc")

  /**
    * 未找到设备
    */
  "testAppUpdateResult1" should "handle successful" in {
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(null)
    val resp = appUpdateResult.handle(req)
    val exceptResp = AppUpdateResultResp("2301", Some("device not found"))
    resp shouldBe exceptResp
  }

  /**
    * 缺少参数
    */
  "testAppUpdateResult2" should "handle successful" in {
    val device = new Device
    device.zekLmk = "zekLmk"
    val req = AppUpdateResultReq("1", "1", "1", "20160709", "xcx", "01", "02", "pkgName", "version", "", "endTime", "result", "desc")
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val resp = appUpdateResult.handle(req)
    val exceptResp = AppUpdateResultResp("1001", Some("miss request data"))
    resp shouldBe exceptResp
  }

  /**
    * 签名错误
    */
  "testAppUpdateResult3" should "handle successful" in {
    val device = new Device
    device.zekLmk = "zekLmk"
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val signData = TreeMap("desc" -> req.desc, "endTime" -> req.endTime, "firmCode" -> req.firmCode, "pkgName" -> req.pkgName, "result" -> req.result,
      "sn" -> req.sn, "startTime" -> req.startTime, "timestamp" -> req.date, "type" -> req.`type`, "updateType" -> req.updateType)
    Mockito.when(signValidate.handle("zekLmk", signData, "xcx")).thenReturn(false)
    val resp = appUpdateResult.handle(req)
    val exceptResp = AppUpdateResultResp("4403", Some("sign error"))
    resp shouldBe exceptResp
  }

  /**
    * 正确返回
    */
  "testAppUpdateResult4" should "handle successful" in {
    val device = new Device
    device.zekLmk = "zekLmk"
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val signData = TreeMap("desc" -> req.desc, "endTime" -> req.endTime, "firmCode" -> req.firmCode, "pkgName" -> req.pkgName, "result" -> req.result,
      "sn" -> req.sn, "startTime" -> req.startTime, "timestamp" -> req.date, "type" -> req.`type`, "updateType" -> req.updateType)
    Mockito.when(signValidate.handle("zekLmk", signData, "xcx")).thenReturn(true)
    val resp = appUpdateResult.handle(req)
    val exceptResp = AppUpdateResultResp("00", None)
    resp shouldBe exceptResp
  }

}
