package com.yada.spos.pos.biz

import com.yada.spos.common.{Dek, HsmComponent, SignValidate}
import com.yada.spos.db.dao.{AuthDeviceDao, AuthDeviceFlowDao, DeviceDao, PospOrgZmkDao}
import com.yada.spos.db.model.{AuthDevice, Device, PospOrgZmk}
import com.yada.spos.pos.ibiz.{DeviceInitReq, DeviceInitResp, DeviceInitRespBody}
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

/**
  * 设备初始化测试
  * Created by nickDu on 2016/9/12.
  */
@RunWith(classOf[JUnitRunner])
class DeviceInitBizTest extends FlatSpec with Matchers {
  val deviceDao = Mockito.mock(classOf[DeviceDao])
  val authDeviceDao = Mockito.mock(classOf[AuthDeviceDao])
  val pospOrgZmkDao = Mockito.mock(classOf[PospOrgZmkDao])
  val hsmComponent = Mockito.mock(classOf[HsmComponent])
  val authDeviceFlowDao = Mockito.mock(classOf[AuthDeviceFlowDao])
  val signValidate = Mockito.mock(classOf[SignValidate])

  val deviceInit = new DeviceInitBiz
  deviceInit.deviceDao = deviceDao
  deviceInit.authDeviceDao = authDeviceDao
  deviceInit.pospOrgZmkDao = pospOrgZmkDao
  deviceInit.hsmComponent = hsmComponent
  deviceInit.authDeviceFlowDao = authDeviceFlowDao
  deviceInit.signValidate = signValidate

  var req = DeviceInitReq("1", "1", "1", "20160709", "xcx", "001", "20160709", "12ef")

  /**
    * 未找到母pos设备
    */
  "testDeviceInitTest1" should "handle successful" in {
    Mockito.when(authDeviceDao.findByAuthPosSn(req.keyDeviceSn)).thenReturn(null)
    val resp = deviceInit.handle(req)
    val exceptResp = DeviceInitResp("2301", Some("auth device not found"), None)
    resp shouldBe exceptResp
  }

  /**
    * 未找到设备
    */
  "testDeviceInitTest2" should "handle successful" in {
    val authDevice = new AuthDevice
    Mockito.when(authDeviceDao.findByAuthPosSn(req.keyDeviceSn)).thenReturn(authDevice)
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(null)
    val resp = deviceInit.handle(req)
    val exceptResp = DeviceInitResp("2301", Some("device not found"), None)
    resp shouldBe exceptResp
  }

  /**
    * 验签失败
    */
  "testDeviceInitTest3" should "handle successful" in {
    val authDevice = new AuthDevice
    authDevice.rsaPublicKey = "ll"
    Mockito.when(authDeviceDao.findByAuthPosSn(req.keyDeviceSn)).thenReturn(authDevice)
    val device = new Device
    device.zekLmk = "zekLmk"
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    Mockito.when(signValidate.verify(any(classOf[String]), any(classOf[String]), any(classOf[Array[Byte]]), any(classOf[Array[Byte]]))).thenReturn(false)
    val resp = deviceInit.handle(req)
    val exceptResp = DeviceInitResp("4403", Some("sign error"), None)
    resp shouldBe exceptResp
  }

  /**
    * 正常返回
    */
  //TODO    补充测试
  "testDeviceInitTest4" should "handle successful" in {
    val authDevice = new AuthDevice
    authDevice.rsaPublicKey = "sss"
    Mockito.when(authDeviceDao.findByAuthPosSn(req.keyDeviceSn)).thenReturn(authDevice)
    val device = new Device
    device.zekLmk = "zekLmk"
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val pospOrgZmk = new PospOrgZmk
    pospOrgZmk.zmkLmk = "zmkLmk"
    Mockito.when(pospOrgZmkDao.findByOrgId(device.orgId)).thenReturn(pospOrgZmk)
    Mockito.when(hsmComponent.readDek(pospOrgZmk.zmkLmk)).thenReturn(Dek("dekLmk", "dekZmk", "dekKcv"))
    Mockito.when(signValidate.verify(any(classOf[String]), any(classOf[String]), any(classOf[Array[Byte]]), any(classOf[Array[Byte]]))).thenReturn(true)
    val resp = deviceInit.handle(req)
    val exceptResp = DeviceInitResp("00", None, Some(DeviceInitRespBody("dekZmk", "dekKcv")))

    resp shouldBe exceptResp
  }
}
