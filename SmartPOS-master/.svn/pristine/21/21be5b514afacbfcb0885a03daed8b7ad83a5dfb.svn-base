package com.yada.spos.mag.service

import java.io.File
import java.nio.file.Paths
import java.util.Date

import com.typesafe.config.ConfigFactory
import com.yada.spos.db.dao.{FirmDao, OtaHistoryDao, ProductsDao}
import com.yada.spos.db.model.{Firm, OtaHistory, Products, ProductsPK}
import com.yada.spos.db.query.OtaHistoryQuery
import com.yada.spos.mag.service.ext._
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.data.domain.Pageable

/**
  * Created by pangChangSong on 2016/9/25.
  * ota管理service测试
  */
@RunWith(classOf[JUnitRunner])
class OtaHistoryServiceTest extends FlatSpec with Matchers with MockitoSugar {

  val otaHistoryService = new OtaHistoryService

  /**
    * 分页查询所有
    */
  "testFindPage" should "handle successful" in {
    otaHistoryService.otaHistoryDao = mock[OtaHistoryDao]
    //调用方法
    otaHistoryService.findAll(mock[OtaHistoryQuery], mock[Pageable])
    //验证调用一次分页查询全部方法
    verify(otaHistoryService.otaHistoryDao, times(1)).findAll(any(classOf[OtaHistoryQuery]), any(classOf[Pageable]))
  }

  /**
    * 测试ota上传到临时目录
    * 解析临时目录下的文件，组装页面显示信息，otaHistory实体和临时目录
    */
  "testUploadOtaToTempDir" should "handler successful" in {
    //mock对象
    otaHistoryService.zipFileHandler = mock[ZipFileHandler]
    otaHistoryService.productsDao = mock[ProductsDao]
    otaHistoryService.firmDao = mock[FirmDao]
    otaHistoryService.otaFileHandler = mock[OtaFileHandler]
    otaHistoryService.otaInfoHandler = mock[OtaInfoHandler]

    //根据厂商编号和产品类型查询产品
    val products = mock[Products]
    when(otaHistoryService.productsDao.findOne(any(classOf[ProductsPK]))).thenReturn(products)
    //根据厂商编号查询厂商
    val firm = mock[Firm]
    when(otaHistoryService.firmDao.findOne("AAAA")).thenReturn(firm)
    //读取临时目录下的子文件
    val otaInfo = OtaFileTuple(mock[File], mock[File], mock[File])
    val tempFile = Paths.get(ConfigFactory.load.getString("appFile.tempDir"), String.format("%1$tY%1$tm%1$td", new Date), "2", "000", "OTA", "yada").toString
    when(otaHistoryService.otaFileHandler.read(tempFile)).thenReturn(otaInfo)

    //mock传入参数
    val file = mock[File]
    when(file.getName).thenReturn("yada.zip")
    //调用方法
    otaHistoryService.uploadOtaToTempDir(file, "000", "2", "AAAA", "111111")
    //验证方法调用
    //调用一次解压文件到临时目录
    verify(otaHistoryService.zipFileHandler, times(1)).unzipToPath(file, Paths.get(ConfigFactory.load.getString("appFile.tempDir"), String.format("%1$tY%1$tm%1$td", new Date), "2", "000", "OTA").toString)
    //调用一次查询产品方法，查询出来的产品会作为参数传给处理临时目录下的所有文件，组装放回信息
    verify(otaHistoryService.productsDao, times(1)).findOne(any(classOf[ProductsPK]))
    //调用一次查询厂商方法，查询出来的产品会作为参数传给处理临时目录下的所有文件，组装放回信息
    verify(otaHistoryService.firmDao, times(1)).findOne("AAAA")
    //调用一次读取临时目录下的子文件，查询出来的产品会作为参数传给处理临时目录下的所有文件，组装放回信息
    verify(otaHistoryService.otaFileHandler, times(1)).read(tempFile)
    //验证调用一次处理临时目录下的所有文件，组装放回信息
    verify(otaHistoryService.otaInfoHandler, times(1)).dealOtaZipInfo(otaInfo, products, firm, "2", "000", tempFile)
  }

  "testSaveUploadOta" should "handler successful" in {
    //mock对象
    otaHistoryService.otaFileHandler = mock[OtaFileHandler]
    otaHistoryService.otaInfoHandler = mock[OtaInfoHandler]
    otaHistoryService.systemHandler = mock[SystemHandler]
    //mock参数
    val otaInfo = mock[OtaInfo]
    val otaHistory = mock[OtaHistory]
    when(otaInfo.getOtaHistory).thenReturn(otaHistory)
    //这个是随便写的，因为是mock，没有必要写真实路径
    when(otaInfo.getTempDir).thenReturn("test")
    when(otaHistoryService.systemHandler.getNextLevOrgs(any(classOf[String]), any(classOf[String]))).thenReturn(getOrgInfo)
    //调用方法
    otaHistoryService.saveUploadOta(otaInfo, "000", "2")
    //验证调用方法
    //调用3次保存历史数据的方法
    verify(otaHistoryService.otaInfoHandler, times(3)).saveOtaData(any(classOf[OtaHistory]), any(classOf[String]), any(classOf[String]))
    //调用3次更新ota版本的方法
    verify(otaHistoryService.otaInfoHandler, times(3)).updateOtaFileLatest(any(classOf[OtaHistory]), any(classOf[String]), any(classOf[String]))
    //验证文件上传方法被调用3次
    verify(otaHistoryService.otaFileHandler, times(3)).uploadFile(any(classOf[OtaInfo]), any(classOf[String]), any(classOf[String]))
    //验证调用一次删除临时目录方法
    verify(otaHistoryService.otaFileHandler, times(1)).deleteTempFile(any(classOf[File]))
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
