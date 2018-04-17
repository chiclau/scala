package com.yada.spos.mag.service.ext

import java.io.File
import java.nio.file.Path

import com.yada.spos.db.dao._
import com.yada.spos.db.model._
import net.dongliu.apk.parser.ApkParser
import net.dongliu.apk.parser.bean.Icon
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.core.io.ClassPathResource

import scala.collection.convert.decorateAll._

/**
  * Created by pangChangSong on 2016/10/16.
  * 测试应用信息处理
  */
@RunWith(classOf[JUnitRunner])
class AppInfoHandlerTest extends FlatSpec with Matchers with MockitoSugar {
  val apkInfoHandler = new AppInfoHandler
  /**
    * 测试zip包信息的读取，组装页面的展示信息
    */
  "testRead" should "handle successful" in {
    apkInfoHandler.appFileHistoryDao = mock[AppFileHistoryDao]
    apkInfoHandler.appFileLatestDao = mock[AppFileLatestDao]
    apkInfoHandler.apkIconHandler = mock[ApkIconHandler]
    apkInfoHandler.dictApkPermissionDao = mock[DictApkPermissionDao]
    apkInfoHandler.systemHandler = mock[SystemHandler]
    when(apkInfoHandler.systemHandler.getOrg("000", "2")).thenReturn(OrgInfo("2", "000", 1, "中行卡中心"))
    //查询历史信息，根据包名、版本序号、机构和机构类型
    when(apkInfoHandler.appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(
      any(classOf[String]), any(classOf[String]), any(classOf[String]), any(classOf[String])
    )).thenReturn(null)
    //根据包名、机构和机构类型查询最新版本
    val appFileLatest = mock[AppFileLatest]
    when(appFileLatest.getAppPermission).thenReturn("1,2,3")
    when(apkInfoHandler.appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(
      any(classOf[String]), any(classOf[String]), any(classOf[String])
    )).thenReturn(appFileLatest)
    val dictApkPermission1 = new DictApkPermission
    dictApkPermission1.setApkPermissionCode("1")
    when(apkInfoHandler.dictApkPermissionDao.findByApkPermissionCode("1")).thenReturn(dictApkPermission1)
    val dictApkPermission2 = new DictApkPermission
    dictApkPermission2.setApkPermissionCode("2")
    when(apkInfoHandler.dictApkPermissionDao.findByApkPermissionCode("2")).thenReturn(dictApkPermission2)
    val dictApkPermission3 = new DictApkPermission
    dictApkPermission3.setApkPermissionCode("3")
    when(apkInfoHandler.dictApkPermissionDao.findByApkPermissionCode("3")).thenReturn(dictApkPermission3)
    //调用方法
    val res = new ClassPathResource("/appUpload", this.getClass)
    val apkRes = new ClassPathResource("/appUpload/yada.apk", this.getClass)
    val txtRes = new ClassPathResource("/appUpload/yada.txt", this.getClass)
    val properitesRes = new ClassPathResource("/appUpload/yada.properties", this.getClass)
    val tuple = ApkFileTuple("yada", apkRes.getFile, properitesRes.getFile, txtRes.getFile)
    val apkParser = new ApkParser(apkRes.getFile)
    val result = apkInfoHandler.read(tuple, "2", "000", res.getFile.getAbsolutePath, apkParser)
    //验证调用一次查询历史信息，根据包名、版本序号、机构和机构类型
    verify(apkInfoHandler.appFileHistoryDao, times(1)).findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(
      any(classOf[String]), any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //验证调用一次生成icon文件
    verify(apkInfoHandler.apkIconHandler, times(1)).iconToFile(any(classOf[Icon]), any(classOf[String]), any(classOf[String]))
    //验证调用一次查询最新数据
    verify(apkInfoHandler.appFileLatestDao, times(1)).findByAppPackageNameAndOrgIdAndOrgType(
      any(classOf[String]), any(classOf[String]), any(classOf[String]))
    val tempDir = result.getTempDir
    val appFileHistory = result.getAppFileHistory
    val appName = result.getAppName
    val insertPermissions = result.getInsertPermissions
    val existsPermissons = result.getExistPermissions
    val reducePermissions = result.getReducePermissions
    tempDir shouldBe res.getFile.getAbsolutePath
    appName shouldBe "yada"
    insertPermissions shouldBe List().asJava
    existsPermissons shouldBe List().asJava
    val apkPermissionCodes = reducePermissions.asScala.map(it => {
      it.getApkPermissionCode
    }).toList
    apkPermissionCodes.contains("1") shouldBe true
    apkPermissionCodes.contains("2") shouldBe true
    apkPermissionCodes.contains("3") shouldBe true
    appFileHistory.getVersionCode shouldBe "1"
    appFileHistory.getVersionName shouldBe "1.0"
    appFileHistory.getAppPackageName shouldBe "com.example.fengm.androidtest"
    appFileHistory.getAppName shouldBe "AndroidTest"
    appFileHistory.getModeHand shouldBe "1"
    appFileHistory.getModeHd shouldBe "1"
    appFileHistory.getFileName shouldBe "test/2/000/APP/com.example.fengm.androidtest/1.apk"
    appFileHistory.getFileLength shouldBe 1503149
    appFileHistory.getPublicDate shouldBe "2016091819"
    appFileHistory.getForceUpdate shouldBe "1"
    appFileHistory.getDeleteUpdate shouldBe "1"
    appFileHistory.getRemark shouldBe "你好789"
    appFileHistory.getOrgId shouldBe "000"
    appFileHistory.getOrgType shouldBe "2"
  }

  /**
    * 验证是否能删除应用（分行删除应用，判断是不是总行上传的应用总行上传的应用不能删除，不是总行上传的应用判断是不是最新版本，是最新版本不能删除）
    * 1.是总行上传应用的情况
    */
  "testCheckAppIsCanDelete1" should "handle successful" in {
    apkInfoHandler.appFileHistoryDao = mock[AppFileHistoryDao]
    //根据包名、版本号、机构和机构类型查询应用文件历史(返回null，非总行上传的应用)
    when(apkInfoHandler.appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(any(classOf[String]),
      any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(mock[AppFileHistory])
    val appFileLatest = mock[AppFileLatest]
    val result = apkInfoHandler.checkAppIsCanDelete("qq.com", "1.2", "000011", "2", appFileLatest)
    result shouldBe "isHeadOrgApp"
    //验证调用一次 根据包名、版本号、机构和机构类型查询应用文件历史 方法
    verify(apkInfoHandler.appFileHistoryDao, times(1)).findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]), any(classOf[String]))
  }

  /**
    * 验证是否能删除应用（分行删除应用，判断是不是总行上传的应用总行上传的应用不能删除，不是总行上传的应用判断是不是最新版本，是最新版本不能删除）
    * 2.不是总行上传应用，是最新版本的情况
    */
  "testCheckAppIsCanDelete2" should "handle successful" in {
    apkInfoHandler.appFileHistoryDao = mock[AppFileHistoryDao]
    //根据包名、版本号、机构和机构类型查询应用文件历史(返回null，非总行上传的应用)
    when(apkInfoHandler.appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(any(classOf[String]),
      any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(null)
    val appFileLatest = mock[AppFileLatest]
    when(appFileLatest.getVersionCode).thenReturn("1.2")
    val result = apkInfoHandler.checkAppIsCanDelete("qq.com", "1.2", "000011", "2", appFileLatest)
    result shouldBe "isLatestVersion"
    //验证调用一次 根据包名、版本号、机构和机构类型查询应用文件历史 方法
    verify(apkInfoHandler.appFileHistoryDao, times(1)).findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]), any(classOf[String]))
    verify(appFileLatest, times(1)).getVersionCode
  }

  /**
    * 验证是否能删除应用（分行删除应用，判断是不是总行上传的应用总行上传的应用不能删除，不是总行上传的应用判断是不是最新版本，是最新版本不能删除）
    * 3.不是总行上传应用，不是最新版本的情况
    */
  "testCheckAppIsCanDelete3" should "handle successful" in {
    apkInfoHandler.appFileHistoryDao = mock[AppFileHistoryDao]
    //根据包名、版本号、机构和机构类型查询应用文件历史(返回null，非总行上传的应用)
    when(apkInfoHandler.appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(any(classOf[String]),
      any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(null)
    val appFileLatest = mock[AppFileLatest]
    when(appFileLatest.getVersionCode).thenReturn("1.3")
    val result = apkInfoHandler.checkAppIsCanDelete("qq.com", "1.2", "000011", "2", appFileLatest)
    result shouldBe "success"
    //验证调用一次 根据包名、版本号、机构和机构类型查询应用文件历史 方法
    verify(apkInfoHandler.appFileHistoryDao, times(1)).findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]), any(classOf[String]))
    verify(appFileLatest, times(1)).getVersionCode
  }

  /**
    * 验证是否能删除应用（总行删除应用，判断是不是最新版本，是最新版本不能删除）
    * 4.是最新版本的情况
    */
  "testCheckAppIsCanDelete4" should "handle successful" in {
    apkInfoHandler.appFileHistoryDao = mock[AppFileHistoryDao]
    val appFileLatest = mock[AppFileLatest]
    when(appFileLatest.getVersionCode).thenReturn("1.2")
    val result = apkInfoHandler.checkAppIsCanDelete("qq.com", "1.2", "000011", "2", appFileLatest)
    result shouldBe "isLatestVersion"
    verify(appFileLatest, times(1)).getVersionCode
  }

  /**
    * 验证是否能删除应用（总行删除应用，判断是不是最新版本，是最新版本不能删除）
    * 5.不是是最新版本的情况success
    */
  "testCheckAppIsCanDelete5" should "handle successful" in {
    apkInfoHandler.appFileHistoryDao = mock[AppFileHistoryDao]
    val appFileLatest = mock[AppFileLatest]
    when(appFileLatest.getVersionCode).thenReturn("1.3")
    val result = apkInfoHandler.checkAppIsCanDelete("qq.com", "1.2", "000011", "2", appFileLatest)
    result shouldBe "success"
    verify(appFileLatest, times(1)).getVersionCode
  }

  /**
    * 测试保存历史数据，并添加到默认分组（分行操作，上传两个apk（包名相同））
    */
  "testSaveAppFIleHistory" should "handle successful" in {
    apkInfoHandler.appFileHistoryDao = mock[AppFileHistoryDao]
    apkInfoHandler.appFileLatestDao = mock[AppFileLatestDao]
    apkInfoHandler.systemHandler = mock[SystemHandler]
    apkInfoHandler.appGroupAppsDao = mock[AppGroupAppsDao]
    apkInfoHandler.appGroupDao = mock[AppGroupDao]
    apkInfoHandler.apkFileTupleHandler = mock[ApkFileTupleHandler]
    apkInfoHandler.apkSign = mock[ApkSign]

    //apk签名
    val apkRes1 = new ClassPathResource("/appUpload/yada1_1.0.apk", this.getClass)
    val path = mock[Path]
    val file = apkRes1.getFile
    when(apkInfoHandler.apkSign.sign(any(classOf[Path]))).thenReturn(path)
    when(path.toFile).thenReturn(file)
    //    when(file.getAbsolutePath).thenReturn(apkRes1.getFile.getAbsolutePath + ".signed")

    //apk1
    val appInfo1 = mock[AppInfo]
    val appFileHistory1 = mock[AppFileHistory]
    when(appFileHistory1.getAppPackageName).thenReturn("qq.com")
    when(appFileHistory1.getVersionCode).thenReturn("1.9")
    when(appFileHistory1.getOrgId).thenReturn("000011")
    when(appFileHistory1.getOrgType).thenReturn("2")
    when(appFileHistory1.getAppName).thenReturn("qq")
    when(appFileHistory1.getFileName).thenReturn("qq")
    when(appInfo1.getAppFileHistory).thenReturn(appFileHistory1)
    //apk2
    val appInfo2 = mock[AppInfo]
    val appFileHistory2 = mock[AppFileHistory]
    when(appFileHistory2.getAppPackageName).thenReturn("qq.com")
    when(appFileHistory2.getVersionCode).thenReturn("1.8")
    when(appFileHistory2.getOrgId).thenReturn("000011")
    when(appFileHistory2.getOrgType).thenReturn("2")
    when(appFileHistory2.getAppName).thenReturn("qq")
    when(appFileHistory2.getFileName).thenReturn("qq")
    when(appInfo2.getAppFileHistory).thenReturn(appFileHistory2)
    //根据包名、机构号和机构类型查询应用文件最新版本
    val appFileLatest = mock[AppFileLatest]
    when(appFileLatest.getMinVersionCode).thenReturn("1.1")
    when(apkInfoHandler.appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(appFileLatest)
    //根据包名、是否默认分组、机构、机构类型查询应用是否关联应用分组
    when(apkInfoHandler.appGroupAppsDao.findByAppPackageNameAndAppGroupIsDefaultGroupAndAppGroupOrgIdAndAppGroupOrgType(any(classOf[String])
      , any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(null)
    //查询默认分组
    val defAppGroup = mock[AppGroup]
    when(defAppGroup.getAppGroupName).thenReturn("北京分行默认分组")
    when(apkInfoHandler.appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(defAppGroup)
    //调用方法
    apkInfoHandler.saveAppFileHistory(List(appInfo1, appInfo2), "000011", "2", Map(), mock[File])
    //验证方法调用
    //验证调用一次 获得appInfo1的应用历史信息 方法
    verify(appInfo1, times(1)).getAppFileHistory
    //验证调用一次 appFileHistory1的获得包名和版本号 方法
    verify(appFileHistory1, times(2)).getAppPackageName //一次加入默认分组，1次遍历
    verify(appFileHistory1, times(1)).getVersionCode
    //验证调用一次 设置最小版本号和上传时间 方法
    verify(appFileHistory1, times(1)).setMinVersionCode("1.1")
    verify(appFileHistory1, times(1)).setCreTime(any(classOf[String]))
    verify(appFileHistory1, times(1)).setFileMd5(any(classOf[String]))
    //调用一次获得机构号、机构类型和应用名称
    verify(appFileHistory1, times(1)).getOrgId
    verify(appFileHistory1, times(1)).getOrgType
    verify(appFileHistory1, times(1)).getAppName
    //验证调用一次 保存应用文件历史 方法
    verify(apkInfoHandler.appFileHistoryDao, times(1)).saveAndFlush(appFileHistory1)
    //验证调用一次 获得appInfo2的应用历史信息 方法
    verify(appInfo2, times(1)).getAppFileHistory
    //验证调用一次 appFileHistory2的获得包名和版本号 方法
    verify(appFileHistory2, times(2)).getAppPackageName //一次加入默认分组，1次遍历
    verify(appFileHistory2, times(1)).getVersionCode
    //验证调用一次 设置最小版本号和上传时间 方法
    verify(appFileHistory2, times(1)).setMinVersionCode("1.1")
    verify(appFileHistory2, times(1)).setCreTime(any(classOf[String]))
    verify(appFileHistory2, times(1)).setFileMd5(any(classOf[String]))
    //调用一次获得机构号、机构类型和应用名称
    verify(appFileHistory2, times(1)).getOrgId
    verify(appFileHistory2, times(1)).getOrgType
    verify(appFileHistory2, times(1)).getAppName
    //验证调用一次 保存应用文件历史 方法
    verify(apkInfoHandler.appFileHistoryDao, times(1)).saveAndFlush(appFileHistory2)
    //验证调用2次 查询最新版本 方法
    verify(apkInfoHandler.appFileLatestDao, times(2)).findByAppPackageNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //验证调用2次 最新版本的最小版本号 方法
    verify(appFileLatest, times(2)).getMinVersionCode
    //验证调用2次 根据包名、是否默认分组、机构、机构类型查询应用是否关联应用分组 方法
    verify(apkInfoHandler.appGroupAppsDao, times(2)).findByAppPackageNameAndAppGroupIsDefaultGroupAndAppGroupOrgIdAndAppGroupOrgType(any(classOf[String])
      , any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //验证调用2次 查询默认分组 方法
    verify(apkInfoHandler.appGroupDao, times(2)).findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //验证调用2次 保存应用分组关联应用 方法
    verify(apkInfoHandler.appGroupAppsDao, times(2)).saveAndFlush(any(classOf[AppGroupApps]))
    //验证调用两次成才md5文件
    verify(apkInfoHandler.apkFileTupleHandler, times(2)).buildM5File(any(classOf[String]), any(classOf[File]), any(classOf[File]))
  }

  /**
    * 测试保存历史数据，并添加到默认分组（总行操作，上传4个apk（1、2包名相同，3、4包名相同））
    */
  "testSaveAppFIleHistory1" should "handle successful" in {
    apkInfoHandler.appFileHistoryDao = mock[AppFileHistoryDao]
    apkInfoHandler.appFileLatestDao = mock[AppFileLatestDao]
    apkInfoHandler.systemHandler = mock[SystemHandler]
    apkInfoHandler.appGroupAppsDao = mock[AppGroupAppsDao]
    apkInfoHandler.appGroupDao = mock[AppGroupDao]
    apkInfoHandler.apkFileTupleHandler = mock[ApkFileTupleHandler]
    //apk1
    val appInfo1 = mock[AppInfo]
    val appFileHistory1 = mock[AppFileHistory]
    when(appFileHistory1.getAppPackageName).thenReturn("qq.com")
    when(appFileHistory1.getVersionCode).thenReturn("1.9")
    when(appFileHistory1.getOrgId).thenReturn("000")
    when(appFileHistory1.getOrgType).thenReturn("2")
    when(appFileHistory1.getAppName).thenReturn("qq")
    when(appFileHistory1.getFileName).thenReturn("qq")
    when(appInfo1.getAppFileHistory).thenReturn(appFileHistory1)
    //apk2
    val appInfo2 = mock[AppInfo]
    val appFileHistory2 = mock[AppFileHistory]
    when(appFileHistory2.getAppPackageName).thenReturn("qq.com")
    when(appFileHistory2.getVersionCode).thenReturn("1.8")
    when(appFileHistory2.getOrgId).thenReturn("000")
    when(appFileHistory2.getOrgType).thenReturn("2")
    when(appFileHistory2.getAppName).thenReturn("qq")
    when(appFileHistory2.getFileName).thenReturn("qq")
    when(appInfo2.getAppFileHistory).thenReturn(appFileHistory2)
    //apk3
    val appInfo3 = mock[AppInfo]
    val appFileHistory3 = mock[AppFileHistory]
    when(appFileHistory3.getAppPackageName).thenReturn("qq.com1")
    when(appFileHistory3.getVersionCode).thenReturn("1.7")
    when(appFileHistory3.getOrgId).thenReturn("000")
    when(appFileHistory3.getOrgType).thenReturn("2")
    when(appFileHistory3.getAppName).thenReturn("qq1")
    when(appFileHistory3.getFileName).thenReturn("qq")
    when(appInfo3.getAppFileHistory).thenReturn(appFileHistory3)
    //apk4
    val appInfo4 = mock[AppInfo]
    val appFileHistory4 = mock[AppFileHistory]
    when(appFileHistory4.getAppPackageName).thenReturn("qq.com1")
    when(appFileHistory4.getVersionCode).thenReturn("1.6")
    when(appFileHistory4.getOrgId).thenReturn("000")
    when(appFileHistory4.getOrgType).thenReturn("2")
    when(appFileHistory4.getAppName).thenReturn("qq1")
    when(appFileHistory4.getFileName).thenReturn("qq")
    when(appInfo4.getAppFileHistory).thenReturn(appFileHistory4)
    //根据包名、机构号和机构类型查询应用文件最新版本
    when(apkInfoHandler.appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(null)
    //查询下一级的所有机构
    when(apkInfoHandler.systemHandler.getNextLevOrgs(any(classOf[String]), any(classOf[String]))).thenReturn(getOrgInfo)
    //查询数据库中数据是否存在
    when(apkInfoHandler.appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(any(classOf[String])
      , any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(mock[AppFileHistory])
    //根据包名、是否默认分组、机构、机构类型查询应用是否关联应用分组
    when(apkInfoHandler.appGroupAppsDao.findByAppPackageNameAndAppGroupIsDefaultGroupAndAppGroupOrgIdAndAppGroupOrgType(any(classOf[String])
      , any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(null)
    //查询默认分组
    val defAppGroup = mock[AppGroup]
    when(defAppGroup.getAppGroupName).thenReturn("北京分行默认分组")
    when(apkInfoHandler.appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(defAppGroup)
    //调用方法
    val map = Map(
      "qq.com000112" -> List("1.8", "1.9"),
      "qq.com000222" -> List("1.8", "1.9"),
      "qq.com0002" -> List("1.8", "1.9"),
      "qq.com1000112" -> List("1.6", "1.7"),
      "qq.com1000222" -> List("1.6", "1.7"),
      "qq.com10002" -> List("1.6", "1.7")
    )
    //验证方法调用
    apkInfoHandler.saveAppFileHistory(List(appInfo1, appInfo2, appInfo3, appInfo4), "000", "2", map, mock[File])

    //验证调用一次 获得appInfo1的应用历史信息 方法
    verify(appInfo1, times(1)).getAppFileHistory
    //验证调用3次 appFileHistory1的获得包名和版本号 方法
    verify(appFileHistory1, times(4)).getAppPackageName //3次加入默认分组，1次遍历
    verify(appFileHistory1, times(3)).getVersionCode
    //验证调用一次 设置最小版本号和上传时间 方法
    verify(appFileHistory1, times(1)).setMinVersionCode("1.8")
    verify(appFileHistory1, times(1)).setCreTime(any(classOf[String]))
    //调用3次获得机构号、机构类型和应用名称
    verify(appFileHistory1, times(3)).getOrgId
    verify(appFileHistory1, times(3)).getOrgType
    verify(appFileHistory1, times(3)).getAppName
    //验证调用一次 保存应用文件历史 方法
    verify(apkInfoHandler.appFileHistoryDao, times(1)).saveAndFlush(appFileHistory1)

    //验证调用1次 获得appInfo2的应用历史信息 方法
    verify(appInfo2, times(1)).getAppFileHistory
    //验证调用3次 appFileHistory2的获得包名和版本号 方法
    verify(appFileHistory2, times(4)).getAppPackageName //3次加入默认分组，1次遍历
    verify(appFileHistory2, times(3)).getVersionCode
    //验证调用一次 设置最小版本号和上传时间 方法
    verify(appFileHistory2, times(1)).setMinVersionCode("1.8")
    verify(appFileHistory2, times(1)).setCreTime(any(classOf[String]))
    //调用3次获得机构号、机构类型和应用名称
    verify(appFileHistory2, times(3)).getOrgId
    verify(appFileHistory2, times(3)).getOrgType
    verify(appFileHistory2, times(3)).getAppName
    //验证调用一次 保存应用文件历史 方法
    verify(apkInfoHandler.appFileHistoryDao, times(1)).saveAndFlush(appFileHistory2)

    //验证调用一次 获得appInfo3的应用历史信息 方法
    verify(appInfo3, times(1)).getAppFileHistory
    //验证调用3次 appFileHistory3的获得包名和版本号 方法
    verify(appFileHistory3, times(4)).getAppPackageName //3次加入默认分组，1次遍历
    verify(appFileHistory3, times(3)).getVersionCode
    //验证调用一次 设置最小版本号和上传时间 方法
    verify(appFileHistory3, times(1)).setMinVersionCode("1.6")
    verify(appFileHistory3, times(1)).setCreTime(any(classOf[String]))
    //调用3次获得机构号、机构类型和应用名称
    verify(appFileHistory3, times(3)).getOrgId
    verify(appFileHistory3, times(3)).getOrgType
    verify(appFileHistory3, times(3)).getAppName
    //验证调用一次 保存应用文件历史 方法
    verify(apkInfoHandler.appFileHistoryDao, times(1)).saveAndFlush(appFileHistory3)

    //验证调用一次 获得appInfo4的应用历史信息 方法
    verify(appInfo4, times(1)).getAppFileHistory
    //验证调用一次 appFileHistory2的获得包名和版本号 方法
    verify(appFileHistory4, times(4)).getAppPackageName //3次加入默认分组，1次遍历
    verify(appFileHistory4, times(3)).getVersionCode
    //验证调用一次 设置最小版本号和上传时间 方法
    verify(appFileHistory4, times(1)).setMinVersionCode("1.6")
    verify(appFileHistory4, times(1)).setCreTime(any(classOf[String]))
    //调用3次获得机构号、机构类型和应用名称
    verify(appFileHistory4, times(3)).getOrgId
    verify(appFileHistory4, times(3)).getOrgType
    verify(appFileHistory4, times(3)).getAppName
    //验证调用一次 保存应用文件历史 方法
    verify(apkInfoHandler.appFileHistoryDao, times(1)).saveAndFlush(appFileHistory4)
    //验证调用4次 查询最新版本 方法
    verify(apkInfoHandler.appFileLatestDao, times(4)).findByAppPackageNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //验证调用12次 根据包名、是否默认分组、机构、机构类型查询应用是否关联应用分组 方法
    verify(apkInfoHandler.appGroupAppsDao, times(12)).findByAppPackageNameAndAppGroupIsDefaultGroupAndAppGroupOrgIdAndAppGroupOrgType(any(classOf[String])
      , any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //验证调用12次 查询默认分组 方法
    verify(apkInfoHandler.appGroupDao, times(12)).findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //验证调用12次 保存应用分组关联应用 方法
    verify(apkInfoHandler.appGroupAppsDao, times(12)).saveAndFlush(any(classOf[AppGroupApps]))
    //调用一次查询下级所有机构方法
    verify(apkInfoHandler.systemHandler, times(1)).getNextLevOrgs(any(classOf[String]), any(classOf[String]))
    //调用8次查询数据是否存在（4个应用，两个机构）
    verify(apkInfoHandler.appFileHistoryDao, times(8)).findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(any(classOf[String])
      , any(classOf[String]), any(classOf[String]), any(classOf[String]))
    verify(apkInfoHandler.appFileHistoryDao, times(8)).delete(any(classOf[AppFileHistory]))
    verify(apkInfoHandler.appFileHistoryDao, times(8)).flush()
    //验证调用12次成才md5文件
    verify(apkInfoHandler.apkFileTupleHandler, times(12)).buildM5File(any(classOf[String]), any(classOf[File]), any(classOf[File]))
  }

  /**
    * 更新应用文件的最新版本数据
    */
  "testUpdateAppFileLatest" should "handle successful" in {
    apkInfoHandler.appFileHistoryDao = mock[AppFileHistoryDao]
    apkInfoHandler.appFileLatestDao = mock[AppFileLatestDao]
    apkInfoHandler.systemHandler = mock[SystemHandler]

    //查询下一级的所有机构
    when(apkInfoHandler.systemHandler.getNextLevOrgs(any(classOf[String]), any(classOf[String]))).thenReturn(getOrgInfo)
    //apk1
    val appInfo1 = mock[AppInfo]
    val appFileHistory1 = mock[AppFileHistory]
    when(appFileHistory1.getAppPackageName).thenReturn("qq.com")
    when(appFileHistory1.getVersionCode).thenReturn("1.9")
    when(appFileHistory1.getOrgId).thenReturn("000")
    when(appFileHistory1.getOrgType).thenReturn("2")
    when(appFileHistory1.getFileName).thenReturn("qq")
    when(appInfo1.getAppFileHistory).thenReturn(appFileHistory1)
    //apk2
    val appInfo2 = mock[AppInfo]
    val appFileHistory2 = mock[AppFileHistory]
    when(appFileHistory2.getAppPackageName).thenReturn("qq.com")
    when(appFileHistory2.getVersionCode).thenReturn("1.8")
    when(appFileHistory2.getOrgId).thenReturn("000")
    when(appFileHistory2.getOrgType).thenReturn("2")
    when(appFileHistory2.getFileName).thenReturn("qq")
    when(appInfo2.getAppFileHistory).thenReturn(appFileHistory2)
    //apk3
    val appInfo3 = mock[AppInfo]
    val appFileHistory3 = mock[AppFileHistory]
    when(appFileHistory3.getAppPackageName).thenReturn("qq.com1")
    when(appFileHistory3.getVersionCode).thenReturn("1.7")
    when(appFileHistory3.getOrgId).thenReturn("000")
    when(appFileHistory3.getOrgType).thenReturn("2")
    when(appFileHistory3.getFileName).thenReturn("qq1")
    when(appInfo3.getAppFileHistory).thenReturn(appFileHistory3)
    //apk4
    val appInfo4 = mock[AppInfo]
    val appFileHistory4 = mock[AppFileHistory]
    when(appFileHistory4.getAppPackageName).thenReturn("qq.com1")
    when(appFileHistory4.getVersionCode).thenReturn("1.6")
    when(appFileHistory4.getOrgId).thenReturn("000")
    when(appFileHistory4.getOrgType).thenReturn("2")
    when(appFileHistory4.getFileName).thenReturn("qq1")
    when(appInfo4.getAppFileHistory).thenReturn(appFileHistory4)

    //根据包名、机构和机构类型查询应用文件历史信息
    when(apkInfoHandler.appFileHistoryDao.findByAppPackageNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String])
      , any(classOf[String]))).thenReturn(List(appFileHistory1, appFileHistory2, appFileHistory3, appFileHistory4).asJava)
    //查询数据库最大版本
    when(apkInfoHandler.appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType("qq.com", "1.9", "000", "2")).thenReturn(appFileHistory1)
    when(apkInfoHandler.appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType("qq.com", "1.9", "00011", "2")).thenReturn(appFileHistory1)
    when(apkInfoHandler.appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType("qq.com", "1.9", "00022", "2")).thenReturn(appFileHistory1)
    when(apkInfoHandler.appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType("qq.com1", "1.7", "000", "2")).thenReturn(appFileHistory3)
    when(apkInfoHandler.appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType("qq.com1", "1.7", "00011", "2")).thenReturn(appFileHistory3)
    when(apkInfoHandler.appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType("qq.com1", "1.7", "00022", "2")).thenReturn(appFileHistory3)
    //查询最新版本
    when(apkInfoHandler.appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(mock[AppFileLatest])
    //调用方法
    apkInfoHandler.updateAppFileLatest(List(appInfo1, appInfo2, appInfo3, appInfo4), "000", "2")
    //验证方法调用
    verify(apkInfoHandler.systemHandler, times(1)).getNextLevOrgs(any(classOf[String]), any(classOf[String]))
    //调用2次查询应用文件历史信息，两个包名
    verify(apkInfoHandler.appFileHistoryDao, times(2)).findByAppPackageNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //验证根据包名、最大版本、机构和机构类型查询历史的最大版本号
    verify(apkInfoHandler.appFileHistoryDao, times(6)).findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String])
      , any(classOf[String]), any(classOf[String]))
    //验证根据包名、机构和机构类型查询最新版本
    verify(apkInfoHandler.appFileLatestDao, times(6)).findByAppPackageNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //调用7次删除方法
    verify(apkInfoHandler.appFileLatestDao, times(6)).delete(any(classOf[AppFileLatest]))
    verify(apkInfoHandler.appFileLatestDao, times(6)).flush()
    //调用六次保存方法
    verify(apkInfoHandler.appFileLatestDao, times(6)).saveAndFlush(any(classOf[AppFileLatest]))
  }

  def getOrgInfo: java.util.List[OrgInfo] = {
    val list = new java.util.ArrayList[OrgInfo]
    val orgInfo = OrgInfo("00011", "2", 1, "北京分行")
    val orgInfo1 = OrgInfo("00022", "2", 1, "长春分行")
    list.add(orgInfo)
    list.add(orgInfo1)
    list
  }
}
