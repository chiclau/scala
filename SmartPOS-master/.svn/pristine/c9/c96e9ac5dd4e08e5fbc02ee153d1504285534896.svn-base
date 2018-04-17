package com.yada.spos.pos.biz

import com.yada.spos.common.SignValidate
import com.yada.spos.db.dao._
import com.yada.spos.db.model._
import com.yada.spos.pos.ibiz.{AppInfoReq, AppInfoResp, AppInfoRespBody, AppInfomation}
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.immutable.TreeMap
import scala.collection.mutable.ListBuffer

/**
  * 应用信息查询测试
  * Created by nickDu on 2016/9/12.
  */
@RunWith(classOf[JUnitRunner])
class AppInfoBizTest extends FlatSpec with Matchers {
  val deviceDao = Mockito.mock(classOf[DeviceDao])
  val productsDao = Mockito.mock(classOf[ProductsDao])
  val appGroupDevDao = Mockito.mock(classOf[AppGroupDevDao])
  val appGroupAppsDao = Mockito.mock(classOf[AppGroupAppsDao])
  val appFileLatestDao = Mockito.mock(classOf[AppFileLatestDao])
  val otaFileLatestDao = Mockito.mock(classOf[OtaFileLatestDao])
  val signValidate = Mockito.mock(classOf[SignValidate])

  val appInfo = new AppInfoBiz()
  appInfo.deviceDao = deviceDao
  appInfo.productsDao = productsDao
  appInfo.appGroupDevDao = appGroupDevDao
  appInfo.appGroupAppsDao = appGroupAppsDao
  appInfo.appFileLatestDao = appFileLatestDao
  appInfo.otaFileLatestDao = otaFileLatestDao
  appInfo.signValidate = signValidate

  val req = AppInfoReq("1", "1", "1", "20160709", "xcx")

  /**
    * 未找到设备
    */
  "testAppInfo1" should "handle successful" in {
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(null)
    val resp = appInfo.handle(req)
    val exceptResp = AppInfoResp("2301", Some("device not found"), None)
    resp shouldBe exceptResp
  }

  /**
    * 签名错误
    */
  "testAppInfo2" should "handle successful" in {
    val device = new Device
    device.zekLmk = "zekLmk"
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val signData = TreeMap("firmCode" -> req.firmCode, "sn" -> req.sn, "timestamp" -> req.date)
    Mockito.when(signValidate.handle("zekLmk", signData, "xcx")).thenReturn(false)
    val resp = appInfo.handle(req)
    val exceptResp = AppInfoResp("4403", Some("sign error"), None)
    resp shouldBe exceptResp
  }

  /**
    * 未找到产品
    */
  "testAppInfo3" should "handle successful" in {
    val device = new Device
    device.firmCode = "1"
    device.prodCode = "1"
    device.zekLmk = "zekLmk"
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    val signData = TreeMap("firmCode" -> req.firmCode, "sn" -> req.sn, "timestamp" -> req.date)
    Mockito.when(signValidate.handle("zekLmk", signData, "xcx")).thenReturn(true)
    Mockito.when(productsDao.findByFirmCodeAndProdCode(device.firmCode, device.prodCode)).thenReturn(null)
    val resp = appInfo.handle(req)
    val exceptResp = AppInfoResp("2303", Some("product not found"), None)
    resp shouldBe exceptResp
  }

  /**
    * 正常返回(ota支持手持模式）
    */
  "testAppInfo4" should "handle successful" in {
    val device = new Device
    device.devSn = "1"
    device.firmCode = "1"
    device.prodCode = "1"
    device.zekLmk = "zekLmk"
    //找到设备
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    //签名通过
    val signData = TreeMap("firmCode" -> req.firmCode, "sn" -> req.sn, "timestamp" -> req.date)
    Mockito.when(signValidate.handle("zekLmk", signData, "xcx")).thenReturn(true)
    //找到产品
    val products = new Products
    products.deviceMode = "2"
    Mockito.when(productsDao.findByFirmCodeAndProdCode(device.firmCode, device.prodCode)).thenReturn(products)
    //查分组
    val appGroup = new AppGroup
    appGroup.orgId = "0"
    appGroup.orgType = "1"
    val appGroupDev = new AppGroupDev
    appGroupDev.appGroup = appGroup
    Mockito.when(appGroupDevDao.findByDevSNAndFirmCode(device.devSn, device.firmCode)).thenReturn(appGroupDev)

    //查分组下的应用
    val list = new java.util.ArrayList[AppGroupApps]
    val appGroupApps0 = new AppGroupApps
    appGroupApps0.appGroupDetailId = 1
    appGroupApps0.appGroupName = "test"
    appGroupApps0.appPackageName = "appPack0"
    appGroupApps0.appName = "app0"
    appGroupApps0.appGroup = appGroup
    list.add(appGroupApps0)
    val appGroupApps1 = new AppGroupApps
    appGroupApps1.appGroupDetailId = 1
    appGroupApps1.appGroupName = "test"
    appGroupApps1.appPackageName = "appPack1"
    appGroupApps1.appName = "app1"
    appGroupApps1.appGroup = appGroup
    list.add(appGroupApps1)
    Mockito.when(appGroupAppsDao.findByAppGroup(appGroup)).thenReturn(list)

    val app0Last = new AppFileLatest
    app0Last.appPackageName = "app0"
    app0Last.appName = "app0"
    app0Last.versionName = "1.1.1"
    app0Last.versionCode = "2"
    app0Last.minVersionCode = "0"
    app0Last.deleteUpdate = "0"
    app0Last.remark = "remark"
    app0Last.forceUpdate = "0"
    app0Last.fileMd5 = "md5"
    app0Last.modeHand = "0" //不支持手持模式
    Mockito.when(appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(appGroupApps0.appPackageName,
      appGroupApps0.appGroup.orgId, appGroupApps0.appGroup.orgType)).thenReturn(app0Last)
    val appLast1 = new AppFileLatest
    appLast1.appPackageName = "app1"
    appLast1.appName = "app1"
    appLast1.versionName = "1.1.1"
    appLast1.versionCode = "2"
    appLast1.minVersionCode = "0"
    appLast1.deleteUpdate = "0"
    appLast1.remark = "remark"
    appLast1.forceUpdate = "0"
    appLast1.fileMd5 = "md5"
    appLast1.modeHand = "1" //支持手持模式
    Mockito.when(appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(appGroupApps1.appPackageName,
      appGroupApps1.appGroup.orgId, appGroupApps1.appGroup.orgType)).thenReturn(appLast1)

    //查分组下的ota
    val otaFileLatest = new OtaFileLatest
    otaFileLatest.otaPackageName = "otaPack0"
    otaFileLatest.otaName = "ota0"
    otaFileLatest.versionName = "1.1.1"
    otaFileLatest.minVersionName = "0"
    otaFileLatest.modeHand = "1" //支持手持模式
    Mockito.when(otaFileLatestDao.findByFirmCodeAndProdCodeAndOrgIdAndOrgType(device.firmCode, device.prodCode, appGroup.orgId, appGroup.orgType))
      .thenReturn(otaFileLatest)
    val appInfos = ListBuffer.empty[AppInfomation]
    appInfos += AppInfomation("02", appLast1.appPackageName, appLast1.appName, appLast1.versionName, appLast1.versionCode,
      appLast1.minVersionCode, Some(appLast1.deleteUpdate), Some(appLast1.forceUpdate), Some(appLast1.fileMd5))
    appInfos += AppInfomation("01", otaFileLatest.otaPackageName, otaFileLatest.otaName, otaFileLatest.versionName, "", otaFileLatest.minVersionName, None, None, None)
    val resp = appInfo.handle(req)
    val exceptResp = AppInfoResp("00", None, Some(AppInfoRespBody(appInfos.toList)))
    resp shouldBe exceptResp
  }

  /**
    * 正常返回(ota不支持手持模式）
    */
  "testAppInfo5" should "handle successful" in {
    val device = new Device
    device.devSn = "1"
    device.firmCode = "1"
    device.prodCode = "1"
    device.zekLmk = "zekLmk"
    //找到设备
    Mockito.when(deviceDao.findByDevSnAndFirmCode(req.firmCode, req.sn)).thenReturn(device)
    //签名通过
    val signData = TreeMap("firmCode" -> req.firmCode, "sn" -> req.sn, "timestamp" -> req.date)
    Mockito.when(signValidate.handle("zekLmk", signData, "xcx")).thenReturn(true)
    //找到产品
    val products = new Products
    products.deviceMode = "2"
    Mockito.when(productsDao.findByFirmCodeAndProdCode(device.firmCode, device.prodCode)).thenReturn(products)
    //查分组
    val appGroup = new AppGroup
    appGroup.orgId = "0"
    appGroup.orgType = "1"
    val appGroupDev = new AppGroupDev
    appGroupDev.appGroup = appGroup
    Mockito.when(appGroupDevDao.findByDevSNAndFirmCode(device.devSn, device.firmCode)).thenReturn(appGroupDev)

    //查分组下的应用
    val list = new java.util.ArrayList[AppGroupApps]
    val appGroupApps0 = new AppGroupApps
    appGroupApps0.appGroupDetailId = 1
    appGroupApps0.appGroupName = "test"
    appGroupApps0.appPackageName = "appPack0"
    appGroupApps0.appName = "app0"
    appGroupApps0.appGroup = appGroup
    list.add(appGroupApps0)
    val appGroupApps1 = new AppGroupApps
    appGroupApps1.appGroupDetailId = 1
    appGroupApps1.appGroupName = "test"
    appGroupApps1.appPackageName = "appPack1"
    appGroupApps1.appName = "app1"
    appGroupApps1.appGroup = appGroup
    list.add(appGroupApps1)
    Mockito.when(appGroupAppsDao.findByAppGroup(appGroup)).thenReturn(list)

    val app0Last = new AppFileLatest
    app0Last.appPackageName = "app0"
    app0Last.appName = "app0"
    app0Last.versionName = "1.1.1"
    app0Last.versionCode = "2"
    app0Last.minVersionCode = "0"
    app0Last.deleteUpdate = "0"
    app0Last.remark = "remark"
    app0Last.forceUpdate = "0"
    app0Last.fileMd5 = "md5"
    app0Last.modeHand = "0" //不支持手持模式
    Mockito.when(appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(appGroupApps0.appPackageName,
      appGroupApps0.appGroup.orgId, appGroupApps0.appGroup.orgType)).thenReturn(app0Last)
    val appLast1 = new AppFileLatest
    appLast1.appPackageName = "app1"
    appLast1.appName = "app1"
    appLast1.versionName = "1.1.1"
    appLast1.versionCode = "2"
    appLast1.minVersionCode = "0"
    appLast1.deleteUpdate = "0"
    appLast1.remark = "remark"
    appLast1.forceUpdate = "0"
    appLast1.fileMd5 = "md5"
    appLast1.modeHand = "1" //支持手持模式
    Mockito.when(appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(appGroupApps1.appPackageName,
      appGroupApps1.appGroup.orgId, appGroupApps1.appGroup.orgType)).thenReturn(appLast1)

    //查分组下的ota
    val otaFileLatest = new OtaFileLatest
    otaFileLatest.otaPackageName = "otaPack0"
    otaFileLatest.otaName = "ota0"
    otaFileLatest.versionName = "1.1.1"
    otaFileLatest.minVersionName = "0"
    otaFileLatest.modeHand = "0" //不支持手持模式
    Mockito.when(otaFileLatestDao.findByFirmCodeAndProdCodeAndOrgIdAndOrgType(device.firmCode, device.prodCode, appGroup.orgId, appGroup.orgType))
      .thenReturn(otaFileLatest)
    val appInfos = ListBuffer.empty[AppInfomation]
    appInfos += AppInfomation("02", appLast1.appPackageName, appLast1.appName, appLast1.versionName, appLast1.versionCode,
      appLast1.minVersionCode, Some(appLast1.deleteUpdate), Some(appLast1.forceUpdate), Some(appLast1.fileMd5))
    val resp = appInfo.handle(req)
    val exceptResp = AppInfoResp("00", None, Some(AppInfoRespBody(appInfos.toList)))
    resp shouldBe exceptResp
  }
}
