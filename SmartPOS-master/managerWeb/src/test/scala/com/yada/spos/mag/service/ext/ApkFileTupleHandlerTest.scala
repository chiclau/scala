package com.yada.spos.mag.service.ext

import java.io.File

import com.yada.spos.db.model.AppFileHistory
import com.yada.spos.mag.service.{SFtp, SFtpService}
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.core.io.ClassPathResource

/**
  * Created by pangChangSong on 2016/9/27.
  * APP文件处理测试
  */
@RunWith(classOf[JUnitRunner])
class ApkFileTupleHandlerTest extends FlatSpec with Matchers with MockitoSugar {
  val apkFileTupleHandler = new ApkFileTupleHandler

  /**
    * 测试读取临时目录文件成元祖列表
    */
  "testRead" should "handle successful" in {
    val res = new ClassPathResource("/appUpload", this.getClass)
    val apkRes1 = new ClassPathResource("/appUpload/yada.apk", this.getClass)
    val txtRes1 = new ClassPathResource("/appUpload/yada.txt", this.getClass)
    val properitesRes1 = new ClassPathResource("/appUpload/yada.properties", this.getClass)
    val apkRes2 = new ClassPathResource("/appUpload/yada1_1.0.apk", this.getClass)
    val properitesRes2 = new ClassPathResource("/appUpload/yada1_1.0.properties", this.getClass)
    //调用方法
    val result = apkFileTupleHandler.read(res.getFile.getAbsolutePath)
    result.size shouldBe 2
    val it1 = result.head
    val it2 = result(1)
    it1.apkName shouldBe "yada"
    it1.apkFile shouldBe apkRes1.getFile
    it1.propertyFile shouldBe properitesRes1.getFile
    it1.desFile shouldBe txtRes1.getFile
    it2.apkName shouldBe "yada1_1.0"
    it2.apkFile shouldBe apkRes2.getFile
    it2.propertyFile shouldBe properitesRes2.getFile
    it2.desFile shouldBe null
  }

  /**
    * 测试上传文件
    */
  "testUploadFile" should "handle successful" in {
    apkFileTupleHandler.systemHandler = mock[SystemHandler]
    apkFileTupleHandler.sFtpService = mock[SFtpService]
    val sftp = mock[SFtp]
    when(apkFileTupleHandler.sFtpService.getSFtps).thenReturn(List(sftp))
    val file1 = mock[File]
    when(file1.getName).thenReturn("yada1_1.0.properties")
    when(file1.getParent).thenReturn("/test11")
    val file2 = mock[File]
    when(file2.getName).thenReturn("yada1_1.0.apk")
    when(file2.getParent).thenReturn("/test11")
    val file3 = mock[File]
    when(file3.getName).thenReturn("yada1.txt")
    when(file3.getParent).thenReturn("/test11")
    val file4 = mock[File]
    when(file4.getName).thenReturn("yada2.apk")
    when(file4.getParent).thenReturn("/test11")
    val file5 = mock[File]
    when(file5.getName).thenReturn("yada2.properties")
    when(file5.getParent).thenReturn("/test11")
    val file = mock[File]
    when(file.listFiles()).thenReturn(Array(file1, file2, file3, file4, file5))
    when(file.getAbsolutePath).thenReturn("/test11")
    //查询下一级的所有机构
    when(apkFileTupleHandler.systemHandler.getNextLevOrgs(any(classOf[String]), any(classOf[String]))).thenReturn(getOrgInfo)
    //调用方法
    val appInfo = mock[AppInfo]
    val appFileHistory = mock[AppFileHistory]
    when(appFileHistory.getOrgId).thenReturn("000")
    when(appFileHistory.getOrgType).thenReturn("2")
    when(appFileHistory.getFileName).thenReturn("/test11/yada1_1.0.apk")
    when(appFileHistory.getAppPackageName).thenReturn("qq.com")
    when(appFileHistory.getVersionCode).thenReturn("1.0")
    when(appInfo.getAppFileHistory).thenReturn(appFileHistory)
    when(appInfo.getTempDir).thenReturn("/test11")
    when(appInfo.getAppName).thenReturn("qq")
    val appInfo1 = mock[AppInfo]
    val appFileHistory1 = mock[AppFileHistory]
    when(appFileHistory1.getOrgId).thenReturn("000")
    when(appFileHistory1.getOrgType).thenReturn("2")
    when(appFileHistory1.getFileName).thenReturn("/test11/yada2.apk")
    when(appFileHistory1.getAppPackageName).thenReturn("qq.com1")
    when(appFileHistory1.getVersionCode).thenReturn("1.1")
    when(appInfo1.getAppFileHistory).thenReturn(appFileHistory1)
    when(appInfo1.getTempDir).thenReturn("/test11")
    when(appInfo1.getAppName).thenReturn("qq1")
    apkFileTupleHandler.uploadFile(List(appInfo, appInfo1), file)
    //验证调用1次获得历史信息方法
    verify(appInfo, times(1)).getAppFileHistory
    //验证调用1次获得机构、机构类型、包名、版本名称和文件全路径名
    verify(appFileHistory, times(1)).getOrgId
    verify(appFileHistory, times(1)).getOrgType
    verify(appFileHistory, times(1)).getAppPackageName
    verify(appFileHistory, times(1)).getVersionCode
    verify(appFileHistory, times(1)).getFileName
    //验证调用1次获得历史信息方法
    verify(appInfo1, times(1)).getAppFileHistory
    //验证调用1次获得机构、机构类型、包名、版本名称和文件全路径名
    verify(appFileHistory1, times(1)).getOrgId
    verify(appFileHistory1, times(1)).getOrgType
    verify(appFileHistory1, times(1)).getAppPackageName
    verify(appFileHistory1, times(1)).getVersionCode
    verify(appFileHistory1, times(1)).getFileName
    //断言调用2次查询下一级的所有机构
    verify(apkFileTupleHandler.systemHandler, times(2)).getNextLevOrgs(any(classOf[String]), any(classOf[String]))
    //验证调用6次获得sftp方法
    verify(apkFileTupleHandler.sFtpService, times(6)).getSFtps
    //验证调用6次上传文件方法
    verify(apkFileTupleHandler.sFtpService, times(6)).upload(any(classOf[SFtp]), any(classOf[Array[File]]), any(classOf[String]),  any(classOf[String]))
  }

  /**
    * 重命名文件
    */
  "testRenameTo" should "handle successful" in {
    val apkRes = new ClassPathResource("/appUploadRaname", this.getClass)
    apkFileTupleHandler.renameTo(apkRes.getFile, "ranameYada", "qq.com_1.0")
    val apkRes1 = new ClassPathResource("/appUploadRaname", this.getClass)
    apkRes1.getFile.exists() shouldBe true
    apkFileTupleHandler.renameTo(apkRes1.getFile, "qq.com_1.0", "ranameYada")
  }

  /**
    * 根据包名查找上传文件
    */
  "testFindUploadFileByPackageName" should "handle successful" in {
    val res = new ClassPathResource("/appUpload", this.getClass)
    val list = apkFileTupleHandler.findUploadFileByPackageName(res.getFile, "yada1")
    val apkRes1 = new ClassPathResource("/appUpload/yada1_1.0.apk", this.getClass)
    val properitesRes1 = new ClassPathResource("/appUpload/yada1_1.0.properties", this.getClass)
    val apkRes2 = new ClassPathResource("/appUpload/yada.apk", this.getClass)
    val txtRes2 = new ClassPathResource("/appUpload/yada.txt", this.getClass)
    val properitesRes2 = new ClassPathResource("/appUpload/yada.properties", this.getClass)
    list.contains(apkRes1.getFile) shouldBe true
    list.contains(properitesRes1.getFile) shouldBe true
    list.contains(apkRes2.getFile) shouldBe false
    list.contains(txtRes2.getFile) shouldBe false
    list.contains(properitesRes2.getFile) shouldBe false
  }

  /**
    * 测试删除文件
    */
  "testDeleteFile" should "handle successful" in {
    apkFileTupleHandler.sFtpService = mock[SFtpService]
    //获得sftp
    val sftp = mock[SFtp]
    when(apkFileTupleHandler.sFtpService.getSFtps).thenReturn(List(sftp))
    val appFileHistory = mock[AppFileHistory]
    when(appFileHistory.getFileName).thenReturn("test/abc.apk")
    //调用方法
    apkFileTupleHandler.deleteFile(appFileHistory, "qq.com", "1.0")
    //验证调用一次appFileHistory的过的全路径名方法
    verify(appFileHistory, times(1)).getFileName
    //验证调用一次获得sftp方法
    verify(apkFileTupleHandler.sFtpService, times(1)).getSFtps
    //验证调用一次删除文件方法
    val delFiles = Array("1.0" + ".apk", "1.0" + ".txt", "1.0" + ".properties", "1.0" + ".apk.md5", "1.0" + "_icon.png")
    verify(apkFileTupleHandler.sFtpService, times(1)).delete(sftp, "test/", delFiles)
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
