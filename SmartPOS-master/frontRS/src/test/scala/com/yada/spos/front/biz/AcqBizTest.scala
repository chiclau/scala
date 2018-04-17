package com.yada.spos.front.biz

import com.yada.spos.common.HsmComponent
import com.yada.spos.db.dao.DeviceDao
import com.yada.spos.db.model.Device
import com.yada.spos.front.acq.AcqClient
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

/**
  * 收单app测试
  * Created by nickDu on 2016/9/19.
  */
@RunWith(classOf[JUnitRunner])
class AcqBizTest extends FlatSpec with Matchers {
  val deviceDao = Mockito.mock(classOf[DeviceDao])
  val hsmComponent = Mockito.mock(classOf[HsmComponent])
  val acqClient = Mockito.mock(classOf[AcqClient])

  val acq = new AcqBiz
  acq.deviceDao = deviceDao
  acq.hsmComponent = hsmComponent
  acq.acqClient = acqClient

  //  "acqTest1" should "handle successful" in {
  //    val device = new Device
  //    device.status = "1"
  //    device.zekLmk = "zekLmk"
  //    Mockito.when(deviceDao.findByDevSnAndFirmCode("1", "1")).thenReturn(device)
  //    Mockito.when(hsmComponent.readDataByBytes("zekLmk", "test".getBytes)).thenReturn("testResp".getBytes)
  //    Mockito.when(acqClient.send("22.7.24.158", 1000, "testResp".getBytes)).thenReturn("respData".getBytes)
  //    Mockito.when(hsmComponent.encodeDataByBytes("zekLmk", "respData".getBytes)).thenReturn("result".getBytes)
  //    val resp = acq.handle("1", "1", "test".getBytes())
  //    resp shouldBe "result".getBytes
  //  }
}
