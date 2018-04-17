package com.yada.spos.mag.service

import java.io.File
import java.nio.file.Paths

import com.yada.spos.db.dao.{AppFileHistoryDao, AppFileLatestDao}
import com.yada.spos.db.model.{AppFileHistory, AppFileLatest}
import com.yada.spos.db.query.AppFileHistoryQuery
import com.yada.spos.mag.service.ext._
import net.dongliu.apk.parser.ApkParser
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.core.io.ClassPathResource
import org.springframework.data.domain.Pageable
import scala.collection.convert.decorateAll._

/**
  * Created by pangChangSong on 2016/9/22.
  * 应用管理service测试
  */
@RunWith(classOf[JUnitRunner])
class AppFileHistoryServiceTest extends FlatSpec with Matchers with MockitoSugar {
  val appFileHistoryService = new AppFileHistoryService

  /**
    * 分页查询查询所有
    */
  "testFindAll" should "handle successful" in {
    appFileHistoryService.appFileHistoryDao = mock[AppFileHistoryDao]
    appFileHistoryService.findAll(mock[AppFileHistoryQuery], mock[Pageable])
    //验证调用一次查询所有方法
    verify(appFileHistoryService.appFileHistoryDao, times(1)).findAll(any(classOf[AppFileHistoryQuery]), any(classOf[Pageable]))
  }


  /**
    * 测试查询一个方法
    */
  "testFindOne" should "handle successful" in {
    appFileHistoryService.appFileHistoryDao = mock[AppFileHistoryDao]
    //调用方法
    appFileHistoryService.findOne("112")
    //验证调用一次查询一个方法
    verify(appFileHistoryService.appFileHistoryDao, times(1)).findOne(112L)
  }

  /**
    * 测试保存方法
    */
  "testSave" should "handle successful" in {
    appFileHistoryService.appFileHistoryDao = mock[AppFileHistoryDao]
    //调用方法
    appFileHistoryService.save(mock[AppFileHistory])
    //验证调用一次查询一个方法
    verify(appFileHistoryService.appFileHistoryDao, times(1)).saveAndFlush(any(classOf[AppFileHistory]))
  }

  /**
    * 测试删除方法方法
    */
  "testDelete1" should "handle successful" in {
    appFileHistoryService.appFileHistoryDao = mock[AppFileHistoryDao]
    appFileHistoryService.appFileLatestDao = mock[AppFileLatestDao]
    appFileHistoryService.sFtpService = mock[SFtpService]
    appFileHistoryService.systemHandler = mock[SystemHandler]
    appFileHistoryService.apkFileTupleHandler = mock[ApkFileTupleHandler]
    //查询下一级的所有机构
    when(appFileHistoryService.systemHandler.getNextLevOrgs("000", "2")).thenReturn(getOrgInfo)
    val sftp = mock[SFtp]
    //活动sftp
    when(appFileHistoryService.sFtpService.getSFtps).thenReturn(List(sftp))
    val appFileHistory = mock[AppFileHistory]
    when(appFileHistory.getVersionCode).thenReturn("1.23")
    when(appFileHistory.getAppPackageName).thenReturn("qq.com")
    //根据应用文件id查询应用文件历史信息
    when(appFileHistoryService.appFileHistoryDao.findOne(1L)).thenReturn(appFileHistory)
    val appFileLatest = mock[AppFileLatest]
    when(appFileLatest.getVersionCode).thenReturn("1.25")
    when(appFileLatest.getMinVersionCode).thenReturn("1.23")
    when(appFileLatest.getFileName).thenReturn("/temp/qq.com_1.23.apk")
    //根据包名、机构和机构类型查询最新版本
    when(appFileHistoryService.appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(appFileLatest)
    val appFileHistory1 = mock[AppFileHistory]
    when(appFileHistory1.getVersionCode).thenReturn("1.24")
    when(appFileHistory1.getAppPackageName).thenReturn("qq.com")
    val appFileHistory2 = mock[AppFileHistory]
    when(appFileHistory2.getVersionCode).thenReturn("1.24.1")
    when(appFileHistory2.getAppPackageName).thenReturn("qq.com")
    //根据包名、机构和机构类型查询历史信息
    when(appFileHistoryService.appFileHistoryDao.findByAppPackageNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(List(appFileHistory2, appFileHistory1).asJava)

    //调用方法
    appFileHistoryService.delete("1", "000", "2")
    //验证方法的调用次数
    //验证根据应用文件id查询应用文件历史信息方法调用一次
    verify(appFileHistoryService.appFileHistoryDao, times(1)).findOne(1L)
    //验证appFileHistory的获得包名方法调用一次，获得版本序号的方法调用1次
    verify(appFileHistory, times(1)).getAppPackageName
    verify(appFileHistory, times(1)).getVersionCode
    //验证根据包名、机构和机构类型查询最新版本调用4次
    verify(appFileHistoryService.appFileLatestDao, times(4)).findByAppPackageNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //验证调用一次查询下级所有机构的方法
    verify(appFileHistoryService.systemHandler, times(1)).getNextLevOrgs("000", "2")
    //调用三次查询出版本信息方法
    verify(appFileHistoryService.appFileHistoryDao, times(3)).findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //验证删除方法调用3次
    verify(appFileHistoryService.appFileHistoryDao, times(3)).delete(any(classOf[AppFileHistory]))
    //验证刷新方法调用3次
    verify(appFileHistoryService.appFileHistoryDao, times(3)).flush()
    //更新最低版本信息
    //验证调用3次根据包名、机构和机构类型查询历史信息
    verify(appFileHistoryService.appFileHistoryDao, times(3)).findByAppPackageNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //验证appFileHistory2获得版本序号方法调用3次
    verify(appFileHistory2, times(3)).getVersionCode
    //验证appFileHistory1获得版本序号方法调用3次
    verify(appFileHistory1, times(3)).getVersionCode
    //验证更新最新版本方法调用3次
    verify(appFileHistoryService.appFileLatestDao, times(3)).saveAndFlush(any(classOf[AppFileLatest]))
    //验证更新应用文件历史信息6次
    verify(appFileHistoryService.appFileHistoryDao, times(6)).saveAndFlush(any(classOf[AppFileHistory]))
    //调用三次删除文件方法
    verify(appFileHistoryService.apkFileTupleHandler, times(3)).deleteFile(any(classOf[AppFileHistory]), any(classOf[String]), any(classOf[String]))
  }

  /**
    * 测试上传应用文件到临时目录
    */
  "testUploadAppFileToTempDir" should "handle successful" in {
    appFileHistoryService.zipFileHandler = mock[ZipFileHandler]
    appFileHistoryService.apkFileTupleHandler = mock[ApkFileTupleHandler]
    appFileHistoryService.apkInfoHandler = mock[AppInfoHandler]
    //mock请求返回数据
    val res = new ClassPathResource("/appUpload/yada.apk", this.getClass)
    val apkFileTuple = ApkFileTuple("yada", res.getFile, Paths.get("/yada.properties").toFile, null)
    when(appFileHistoryService.apkFileTupleHandler.read(any(classOf[String]))).thenReturn(List(apkFileTuple))
    //mock传入参数
    val zipFile = mock[File]
    when(zipFile.getName).thenReturn("yada.zip")
    //调用方法
    appFileHistoryService.uploadAppFileToTempDir(zipFile, "000", "2")
    //验证调用的方法
    //验证解压zip文件调用一次
    verify(appFileHistoryService.zipFileHandler, times(1)).unzipToPath(any(classOf[File]), any(classOf[String]))
    //验证读取临时目录下文件成元祖的方法调用一次
    verify(appFileHistoryService.apkFileTupleHandler, times(1)).read(any(classOf[String]))
    //验证读取元祖信息到页面展示的方法调用一次
    verify(appFileHistoryService.apkInfoHandler, times(1)).read(any(classOf[ApkFileTuple])
      , any(classOf[String]), any(classOf[String]), any(classOf[String]), any(classOf[ApkParser]))
  }

  /**
    * 保存应用上传文件，并上传到服务器
    */
  "testSaveAppFiles" should "handle successful" in {
    //mock对象
    appFileHistoryService.apkFileTupleHandler = mock[ApkFileTupleHandler]
    appFileHistoryService.apkInfoHandler = mock[AppInfoHandler]
    val map = mock[Map[String, List[String]]]
    //获得zip包里的应用信息列表的版本map（key：包名，机构号，机构类型， value是可以对应的版本序号的列表）
    when(appFileHistoryService.apkInfoHandler.getVersionCodeMap(any(classOf[List[AppFileHistory]]))).
      thenReturn(map)
    //构造参数
    val appInfo = mock[AppInfo]
    val appFileHistory = mock[AppFileHistory]
    //获得应用文件历史信息
    when(appInfo.getAppFileHistory).thenReturn(appFileHistory)
    //路径是随意的
    when(appInfo.getTempDir).thenReturn("/temp")
    //调用方法
    appFileHistoryService.saveAppFiles(List(appInfo), "000", "2")
    //验证方法调用
    //验证调用一次获得临时目录的方法
    verify(appInfo, times(1)).getTempDir
    //验证调用一次获得应用历史信息方法（调用“获得zip包里的应用信息列表的版本map”方法是调用，因为集合只有一条信息，所以调用一次）
    verify(appInfo, times(1)).getAppFileHistory
    //验证调用一次“获得zip包里的应用信息列表的版本map”方法
    verify(appFileHistoryService.apkInfoHandler, times(1)).getVersionCodeMap(any(classOf[List[AppFileHistory]]))
    //验证调用一次“保存历史数据,加入默认分组”方法
    verify(appFileHistoryService.apkInfoHandler, times(1)).saveAppFileHistory(any(classOf[List[AppInfo]]), any(classOf[String]), any(classOf[String]), any(classOf[Map[String, List[String]]]), any(classOf[File]))
    //验证调用一次“更新最高版本”方法
    verify(appFileHistoryService.apkInfoHandler, times(1)).updateAppFileLatest(any(classOf[List[AppInfo]]), any(classOf[String]), any(classOf[String]))
    //验证调用一次“上传文件方法”
    verify(appFileHistoryService.apkFileTupleHandler, times(1)).uploadFile(any(classOf[List[AppInfo]]), any(classOf[File]))
    //验证调用一次删除临时目录以前的数据（非当天的数据）
    verify(appFileHistoryService.apkFileTupleHandler, times(1)).deleteTempFile(any(classOf[File]))
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
