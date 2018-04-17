package com.yada.spos.mag.service.ext

import java.io.File

import com.yada.spos.db.model.OtaHistory
import com.yada.spos.mag.service.{SFtp, SFtpService}
import org.apache.commons.io.FileUtils
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.core.io.ClassPathResource

/**
  * Created by pangChangSong on 2016/9/25.
  * 测试ota文件处理器
  */
@RunWith(classOf[JUnitRunner])
class OtaFileHandlerTest extends FlatSpec with Matchers with MockitoSugar {

  val otaFileHandler = new OtaFileHandler

  /**
    * 测试读取临时目录下的文件到一个元祖
    */
  "testRead" should "handle successful" in {
    val res = new ClassPathResource("/test", this.getClass)
    val zipRes = new ClassPathResource("/test/Desktop.zip", this.getClass)
    val txtRes = new ClassPathResource("/test/new 1.txt", this.getClass)
    val properitesRes = new ClassPathResource("/test/yada.properties", this.getClass)
    //调用方法
    val result = otaFileHandler.read(res.getFile.getAbsolutePath)
    result.zipFile shouldBe zipRes.getFile
    result.propertyFile shouldBe properitesRes.getFile
    result.desFile shouldBe txtRes.getFile
  }

  /**
    * 测试上传文件的服务器
    */
  "testUploadFile" should "handle successful" in {
    //mock对象
    otaFileHandler.sFtpService = mock[SFtpService]
    otaFileHandler.otaInfoHandler = mock[OtaInfoHandler]
    otaFileHandler.zipFileHandler = new ZipFileHandler
    //获得sftp对象
    val sftp = mock[SFtp]
    when(otaFileHandler.sFtpService.getSFtps).thenReturn(List(sftp))
    val res = new ClassPathResource("/test", this.getClass)
    val otaInfo = mock[OtaInfo]
    when(otaInfo.getTempDir).thenReturn(res.getFile.getAbsolutePath)
    val otaHistory = mock[OtaHistory]
    when(otaInfo.getOtaHistory).thenReturn(otaHistory)
    when(otaHistory.getVersionName).thenReturn("1.2")
    //调用方法
    otaFileHandler.uploadFile(otaInfo, "000", "2")
    //删除临时文件
    val res1 = new ClassPathResource("/test/Desktop", this.getClass)
    FileUtils.deleteDirectory(res1.getFile)
    //验证调用方法
    //验证调用一次获得临时目录方法
    verify(otaInfo, times(1)).getTempDir
    //验证调用一次获得ota历史信息方法
    verify(otaInfo, times(1)).getOtaHistory
    //调用一次ota历史信息获得版本名称方法
    verify(otaHistory, times(1)).getVersionName
    //调用一次生成md5文件方法
    verify(otaFileHandler.otaInfoHandler, times(1)).buildM5File(any(classOf[String]), any(classOf[File]), any(classOf[File]))
    //调用一次活动sftp方法
    verify(otaFileHandler.sFtpService, times(1)).getSFtps
    //调用一次上传方法
    verify(otaFileHandler.sFtpService, times(1)).upload(any(classOf[SFtp]), any(classOf[Array[File]]), any(classOf[String]),  any(classOf[String]))
  }
}
