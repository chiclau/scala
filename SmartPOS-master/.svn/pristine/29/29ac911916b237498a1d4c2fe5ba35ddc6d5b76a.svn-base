package com.yada.spos.front.biz

import com.yada.spos.front.core.pos
import com.yada.spos.front.core.pos.RSClient
import com.yada.spos.front.core.pos.model.MagInitReq
import com.yada.spos.front.ibiz.DeviceInitReq
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class DeviceInitBizTest extends FlatSpec with Matchers {

  "testDeviceInit" should "handle successful" in {
    val mockResp =
      """
        |{
        |    "retcode":"00",
        |    "body":{
        |        “dek_zmk” : “D280551EADEBB290CFB40DA4BB5DD584s”,
        |        “dek_kcv” : ”3131313131313131”
        |    }
        |}
      """.stripMargin
    val rSClient = Mockito.mock(classOf[RSClient])
    val deviceInit = new DeviceInitBiz
    deviceInit.posRSClient = rSClient
    Mockito.when(rSClient.send(pos.RSClientPath.MAG_INIT.createEntity(
      MagInitReq("1", "1", "1", "keyDeviceSnTest", "timestampTest", "signValueTest")
    ))).thenReturn(mockResp)
    val resp = deviceInit.handle("1", "1", "1", DeviceInitReq("keyDeviceSnTest", "timestampTest", "signValueTest"))
    resp shouldBe mockResp
  }

}
