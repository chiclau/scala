package com.yada.spos.front.biz

import com.yada.spos.front.core.pos
import com.yada.spos.front.core.pos.RSClient
import com.yada.spos.front.core.pos.model._
import com.yada.spos.front.ibiz.AppUpdateResultReq
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

/**
  * 前置服务测试
  * Created by nickDu on 2016/9/4.
  */
@RunWith(classOf[JUnitRunner])
class SposServiceBizTest extends FlatSpec with Matchers {
  val frontService = new SposServiceBiz
  val rsClient = Mockito.mock(classOf[RSClient])
  frontService.posRSClient = rsClient

  /**
    * 终端信息上送
    */
  //  "testTerminalInfoUpload" should "handle successful" in {
  //    val mockResp ="""
  //        |{
  //        |"retCode": "00"
  //        |}
  //      """.stripMargin
  //    Mockito.when(rsClient.send(pos.RSClientPath.UP_TERM_INFO.createEntity(
  //      UpTermInfoReq("1", "1", "1", "20160709", "authorizationTest", "infosTest")
  //    ))).thenReturn(mockResp)
  //    val resp = frontService.terminalInfoUpload("1", "1", "1", "20160709", "authorizationTest", "infosTest")
  //    resp shouldBe mockResp
  //  }

  /**
    * 下载更新结果上送
    */
  "testAppUpdateResult" should "handle successful" in {
    val mockResp =
      """
        |{
        |"retCode": "00"
        |}
      """.stripMargin
    Mockito.when(rsClient.send(pos.RSClientPath.UP_APP_UPDATE_RESULT.createEntity(
      UpUpdateResultReq("1", "1", "1", "20160709", "authorizationTest", "01", "02", "pkgName", "20160709", "20160709", "0", "")
    ))).thenReturn(mockResp)
    val resp = frontService.appUpdateResult("1", "1", "1", "20160709", "authorizationTest", AppUpdateResultReq("01", "02", "pkgName", "20160709", "20160709", "0", ""))
    resp shouldBe mockResp
  }

  /**
    * 应用信息查询
    */
  "testQueryAppInfo" should "handle successful" in {
    val mockResp =
      """{
        |    "retCode":"00",
        |    "body":{
        |        "appInfos":[
        |            {
        |    "type":"01",
        |    "appName":"固件",
        |    "pkgName":"OTA",
        |    "versionName":"1.2.01"
        |            },{
        |    "type":"02",
        |    "appName":"收单"
        |    "pkgName":"com.newland.qmf",
        |    "versionName":"1.1.01",
        |    "versionCode":2,
        |    "minVersionCode":1,
        |    "deleteUpdate":1,
        |    "paramMD5":""
        |            }
        |        ]
        |    }
        |}
      """.stripMargin
    Mockito.when(rsClient.send(pos.RSClientPath.DOWN_APP_INFO.createEntity(
      DownAppInfoReq("1", "1", "1", "20160709", "authorizationTest")
    ))).thenReturn(mockResp)
    val resp = frontService.queryAppInfo("1", "1", "1", "20160709", "authorizationTest")
    resp shouldBe mockResp
  }

  /**
    * 收单主密钥下载
    */
  "testDownloadTmk" should "handle successful" in {
    val mockResp =
      """
        |{
        |    "retcode":"00",
        |    “body”:{
        |        “tmk_dek” : “64028613BA80354AD876C350126C4248a41254b5”,
        |         “tmk_kcv” : “c1981cd7”
        |
        |    }
        |}
      """.stripMargin
    Mockito.when(rsClient.send(pos.RSClientPath.DOWN_TMK.createEntity(
      DownTMKReq("1", "1", "1", "20160709", "authorizationTest")
    ))).thenReturn(mockResp)
    val resp = frontService.downloadTmk("1", "1", "1", "20160709", "authorizationTest")
    resp shouldBe mockResp
  }

  /**
    * 联机参数下载
    */
  "testOnlineParams" should "handle successful" in {
    val mockResp =
      """
        |{
        |    "retcode":"00",
        |    “body”:{
        |        “heartInterval” : 10,
        |        “informationInterval” : 1,
        |        “downLoadUrl”:”https://sposfile/”,
        |        “spospwd”: “3131313131313131”
        |    }
        |}
      """.stripMargin
    Mockito.when(rsClient.send(pos.RSClientPath.DOWN_ONLINE_PARAM.createEntity(
      DownOnlineParamReq("1", "1", "1", "20160709", "authorizationTest")
    ))).thenReturn(mockResp)
    val resp = frontService.onlineParams("1", "1", "1", "20160709", "authorizationTest")
    resp shouldBe mockResp
  }

  /**
    * 设备签到
    */
  "testDeviceSign" should "handle successful" in {
    val mockResp =
      """
        |{
        |    "retcode":"00",
        |    "body":{
        |        “zek_dek” : “D280551EADEBB290CFB40DA4BB5DD584s”,
        |        “zek_kcv” : ”3131313131313131”
        |        “merchantId”:”104110045258623”
        |        “terminalId”:”11008623”
        |    }
        |}
      """.stripMargin

    Mockito.when(rsClient.send(pos.RSClientPath.MAG_SIGN.createEntity(
      MagSignReq("1", "1", "1", "20160709")
    ))).thenReturn(mockResp)
    val resp = frontService.deviceSign("1", "1", "1", "20160709")
    resp shouldBe mockResp
  }
}
