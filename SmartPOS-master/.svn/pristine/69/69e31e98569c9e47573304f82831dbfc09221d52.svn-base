package com.yada.spos.pos.biz

import com.yada.spos.common.{HsmComponent, SignValidate}
import com.yada.spos.db.dao.{DeviceDao, PospOrgZmkDao, TermWorkKeyDao}
import com.yada.spos.db.model.{Device, PospOrgZmk, TermWorkKey}
import com.yada.spos.pos.ibiz.{TmkReq, TmkResp, TmkRespBody}
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.immutable.TreeMap

/**
  * 收单主密钥下载测试
  * Created by nickDu on 2016/9/12.
  */
@RunWith(classOf[JUnitRunner])
class ITmkTest extends FlatSpec with Matchers {
  val deviceDao = Mockito.mock(classOf[DeviceDao])
  val termWorkKeyDao = Mockito.mock(classOf[TermWorkKeyDao])
  val hsmComponent = Mockito.mock(classOf[HsmComponent])
  val pospOrgZmkDao = Mockito.mock(classOf[PospOrgZmkDao])
  val signValidate = Mockito.mock(classOf[SignValidate])

  val tmk = new TmkBiz
  tmk.deviceDao = deviceDao
  tmk.termWorkKeyDao = termWorkKeyDao
  tmk.hsmComponent = hsmComponent
  tmk.pospOrgZmkDao = pospOrgZmkDao
  tmk.signValidate = signValidate

  val req = TmkReq("1", "1", "1", "20160709", "xcx")

  /**
    * 未找到设备
    */
  "testTmkTest1" should "handle successful" in {
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(null)
    val resp = tmk.handle(req)
    val exceptResp = TmkResp("2301", Some("device not found"), None)
    resp shouldBe exceptResp
  }

  /**
    * 未绑定
    */
  "testTmkTest2" should "handle successful" in {
    val device = new Device
    device.dekLmk = "dekLmk"
    device.status = "0" //未绑定
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val resp = tmk.handle(req)
    val exceptResp = TmkResp("2301", Some("not binding"), None)
    resp shouldBe exceptResp
  }

  /**
    * 签名错误
    */
  "testTmkTest3" should "handle successful" in {
    val device = new Device
    device.zekLmk = "zekLmk"
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val signData = TreeMap("firmCode" -> req.firmCode, "sn" -> req.sn, "timestamp" -> req.date)
    Mockito.when(signValidate.handle(device.zekLmk, signData, req.authorization)).thenReturn(false)
    val resp = tmk.handle(req)
    val exceptResp = TmkResp("4601", Some("sign error"), None)
    resp shouldBe exceptResp
  }

  /**
    * 正常返回
    */
  "testTmkTest4" should "handle successful" in {
    val device = new Device
    device.zekLmk = "zekLmk"
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val signData = TreeMap("firmCode" -> req.firmCode, "sn" -> req.sn, "timestamp" -> req.date)
    Mockito.when(signValidate.handle(device.zekLmk, signData, req.authorization)).thenReturn(true)
    val pospOrgZmk = new PospOrgZmk
    pospOrgZmk.zmkLmk = "zmkLmk"
    Mockito.when(pospOrgZmkDao.findByOrgId(device.orgId)).thenReturn(pospOrgZmk)
    val termWorkKey = new TermWorkKey
    termWorkKey.tmkZmk = "tmkZmk"
    Mockito.when(termWorkKeyDao.findByMerChantIdAndTerminalId(device.merNo, device.termNo)).thenReturn(termWorkKey)
    Mockito.when(hsmComponent.readTmk(termWorkKey.tmkZmk, pospOrgZmk.zmkLmk)).thenReturn(com.yada.spos.common.Tmk("tmkLmk", "tmkKcv"))
    Mockito.when(hsmComponent.readTmkDek(device.dekLmk, "tmkLmk")).thenReturn("tmkDek")
    val resp = tmk.handle(req)
    val exceptResp = TmkResp("00", None, Some(TmkRespBody("tmkDek", "tmkKcv")))
    resp shouldBe exceptResp
  }
}
