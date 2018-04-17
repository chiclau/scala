package com.yada.spos.pos.biz

import com.yada.spos.common.{HsmComponent, Zek}
import com.yada.spos.db.dao.DeviceDao
import com.yada.spos.db.model.Device
import com.yada.spos.pos.ibiz.{DeviceSignReq, DeviceSignResp, DeviceSignRespBody}
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

/**
  * 设备签到测试
  * Created by nickDu on 2016/9/12.
  */
@RunWith(classOf[JUnitRunner])
class DeviceSignBizTest extends FlatSpec with Matchers {
  val deviceDao = Mockito.mock(classOf[DeviceDao])
  val hsmComponent = Mockito.mock(classOf[HsmComponent])

  val deviceSign = new DeviceSignBiz
  deviceSign.deviceDao = deviceDao
  deviceSign.hsmComponent = hsmComponent

  val req = DeviceSignReq("1", "1", "1", "20160709", "xcx")

  /**
    * 未找到设备
    */
  "testDeviceSignTest1" should "handle successful" in {
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(null)
    val resp = deviceSign.handle(req)
    val exceptResp = DeviceSignResp("2301", Some("device not found"), None)
    resp shouldBe exceptResp
  }

  /**
    * 缺少dekLmk
    */
  "testDeviceSignTest2" should "handle successful" in {
    val device = new Device
    device.dekLmk = ""
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val resp = deviceSign.handle(req)
    val exceptResp = DeviceSignResp("2303", Some("device not auth"), None)
    resp shouldBe exceptResp
  }

  /**
    * 未绑定
    */
  "testDeviceSignTest3" should "handle successful" in {
    val device = new Device
    device.dekLmk = "dekLmk"
    device.status = "0" //未绑定
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val resp = deviceSign.handle(req)
    val exceptResp = DeviceSignResp("2304", Some("device not binding"), None)
    resp shouldBe exceptResp
  }

  /**
    * 正常返回
    */
  "testDeviceSignTest4" should "handle successful" in {
    val device = new Device
    device.dekLmk = "dekLmk"
    device.status = "1" //绑定
    device.merNo = "merNo"
    device.termNo = "termNo"
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    Mockito.when(hsmComponent.readZek(device.dekLmk)).thenReturn(Zek("zekDek", "zekLmk", "zekKcv"))
    val resp = deviceSign.handle(req)
    val exceptResp = DeviceSignResp("00", None, Some(DeviceSignRespBody("zekDek", "zekKcv", device.merNo, device.termNo)))
    resp shouldBe exceptResp
  }
}
