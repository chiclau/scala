package com.yada.spos.pos.biz

import com.yada.spos.common.{HsmComponent, SignValidate}
import com.yada.spos.db.dao.{DeviceDao, OnlineParamDao}
import com.yada.spos.db.model.Device
import com.yada.spos.pos.ibiz.{OnlineParamReq, OnlineParamResp}
import org.apache.commons.codec.binary.Hex
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.immutable.TreeMap

/**
  * 联机参数下载测试
  * Created by nickDu on 2016/9/12.
  */
@RunWith(classOf[JUnitRunner])
class OnlineParamsTestBiz extends FlatSpec with Matchers {
  val deviceDao = Mockito.mock(classOf[DeviceDao])
  val hsmComponent = Mockito.mock(classOf[HsmComponent])
  val onlineParamDao = Mockito.mock(classOf[OnlineParamDao])
  val signValidate = Mockito.mock(classOf[SignValidate])

  val onlineParam = new OnlineParamBiz
  onlineParam.deviceDao = deviceDao
  onlineParam.onlineParamDao = onlineParamDao
  onlineParam.hsmComponent = hsmComponent
  onlineParam.signValidate = signValidate

  val req = OnlineParamReq("1", "1", "1", "20160709", "xcx")

  /**
    * 未找到设备
    */
  "testOnlineParamTest1" should "handle successful" in {
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(null)
    val resp = onlineParam.handle(req)
    val exceptResp = OnlineParamResp("2301", Some("device not found"), None)
    resp shouldBe exceptResp
  }

  /**
    * 设备没有zekLmk
    */
  "testOnlineParamTest2" should "handle successful" in {
    val device = new Device
    device.zekLmk = ""
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val resp = onlineParam.handle(req)
    val exceptResp = OnlineParamResp("2302", Some("device no sign in"), None)
    resp shouldBe exceptResp
  }

  /**
    * 签名错误
    */
  "testOnlineParamTest3" should "handle successful" in {
    val device = new Device
    device.zekLmk = "zekLmk"
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val signData = TreeMap("firmCode" -> req.firmCode, "sn" -> req.sn, "timestamp" -> req.date)
    Mockito.when(signValidate.handle(device.zekLmk, signData, req.authorization)).thenReturn(false)
    val resp = onlineParam.handle(req)
    val exceptResp = OnlineParamResp("4403", Some("sign error"), None)
    resp shouldBe exceptResp
  }

  /**
    * 正常返回（mtmsPWD 为空）
    */
  "testOnlineParamTest4" should "handle successful" in {
    val device = new Device
    device.zekLmk = "zekLmk"
    device.orgId = "0"
    device.orgType = "1"
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val signData = TreeMap("firmCode" -> req.firmCode, "sn" -> req.sn, "timestamp" -> req.date)
    Mockito.when(signValidate.handle(device.zekLmk, signData, req.authorization)).thenReturn(true)
    val list = new java.util.ArrayList[com.yada.spos.db.model.OnlineParam]
    val onlineParam1 = new com.yada.spos.db.model.OnlineParam
    onlineParam1.paramName = "test"
    onlineParam1.paramValue = "test"
    onlineParam1.paramDesc = "test"
    val onlineParam2 = new com.yada.spos.db.model.OnlineParam
    onlineParam2.paramName = "test2"
    onlineParam2.paramValue = "test2"
    onlineParam2.paramDesc = "test2"
    list.add(onlineParam1)
    list.add(onlineParam2)
    Mockito.when(onlineParamDao.findByOrgIdAndOrgType(device.orgId, device.orgType)).thenReturn(list)
    val map = Map[String, String]("test" -> "test", "test2" -> "test2")
    val resp = onlineParam.handle(req)
    val exceptResp = OnlineParamResp("00", None, Some(map))
    resp shouldBe exceptResp
  }

  /**
    * 正常返回（mtmsPWD 不为空）
    */
  "testOnlineParamTest5" should "handle successful" in {
    val device = new Device
    device.zekLmk = "zekLmk"
    device.orgId = "0"
    device.orgType = "1"
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val signData = TreeMap("firmCode" -> req.firmCode, "sn" -> req.sn, "timestamp" -> req.date)
    Mockito.when(signValidate.handle(device.zekLmk, signData, req.authorization)).thenReturn(true)
    //查询参数
    val list = new java.util.ArrayList[com.yada.spos.db.model.OnlineParam]
    val onlineParam1 = new com.yada.spos.db.model.OnlineParam
    onlineParam1.paramName = "test"
    onlineParam1.paramValue = "test"
    onlineParam1.paramDesc = "test"
    val onlineParam2 = new com.yada.spos.db.model.OnlineParam
    onlineParam2.paramName = "spospwd"
    onlineParam2.paramValue = "spospwd"
    onlineParam2.paramDesc = "test2"
    list.add(onlineParam1)
    list.add(onlineParam2)
    Mockito.when(onlineParamDao.findByOrgIdAndOrgType(device.orgId, device.orgType)).thenReturn(list)
    Mockito.when(hsmComponent.encodeDataByBytes(device.zekLmk, onlineParam2.paramValue.getBytes())).thenReturn("pwd".getBytes())
    val pwd = Hex.encodeHexString("pwd".getBytes)
    val map = Map[String, String]("test" -> "test", "spospwd" -> pwd)
    val resp = onlineParam.handle(req)
    val exceptResp = OnlineParamResp("00", None, Some(map))
    resp shouldBe exceptResp
  }
}
