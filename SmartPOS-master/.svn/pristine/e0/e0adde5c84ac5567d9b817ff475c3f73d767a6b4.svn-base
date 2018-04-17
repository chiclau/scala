package com.yada.spos.mag.service.ext

import com.yada.spos.db.dao.{OtaFileLatestDao, OtaHistoryDao}
import com.yada.spos.db.model.{Firm, OtaFileLatest, OtaHistory, Products}
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.core.io.ClassPathResource

import scala.collection.convert.decorateAll._
import scala.io.Source

/**
  * Created by pangChangSong on 2016/9/25.
  * 测试ota信息处理器
  */
@RunWith(classOf[JUnitRunner])
class OtaInfoHandlerTest extends FlatSpec with Matchers with MockitoSugar {

  val otaInfoHandler = new OtaInfoHandler

  "testDealOtaZipInfo" should "handle successful" in {
    //mock对象
    otaInfoHandler.otaHistoryDao = mock[OtaHistoryDao]
    otaInfoHandler.systemHandler = mock[SystemHandler]
    val orgInfo = mock[OrgInfo]
    when(otaInfoHandler.systemHandler.getOrg(any(classOf[String]), any(classOf[String]))).thenReturn(orgInfo)
    when(orgInfo.orgName).thenReturn("北京分行")
    //查询是否存在ota历史信息
    when(otaInfoHandler.otaHistoryDao.findByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType("AAAA", "11111", "1.1.07", "000", "2"))
      .thenReturn(null)
    //构造参数
    val zipRes = new ClassPathResource("/otaUpload/N900_SA1_OTA.ALL_V1.1.07.zip", this.getClass)
    val propertiesRes = new ClassPathResource("/otaUpload/ota.properties", this.getClass)
    val txtRes = new ClassPathResource("/otaUpload/ota.txt", this.getClass)
    val tuple = OtaFileTuple(zipRes.getFile, propertiesRes.getFile, txtRes.getFile)
    val products = new Products
    products.setProdCode("N900")
    products.setProdName("pppp")
    val firm = new Firm
    firm.setFirmCode("AAAA")
    firm.setFirmName("aaaa")
    //调用方法
    val otaInfo = otaInfoHandler.dealOtaZipInfo(tuple, products, firm, "2", "000", "/test")
    val otaHistory = otaInfo.getOtaHistory
    val TempDir = otaInfo.getTempDir
    otaHistory.getFirmCode shouldBe "AAAA"
    otaHistory.getFirmName shouldBe "aaaa"
    otaHistory.getProdName shouldBe "pppp"
    otaHistory.getProdCode shouldBe "N900"
    otaHistory.getModeHand shouldBe "1"
    otaHistory.getModeHd shouldBe "1"
    otaHistory.getPublicDate shouldBe "201610"
    otaHistory.getOrgId shouldBe "000"
    otaHistory.getOrgType shouldBe "2"
    otaHistory.getVersionName shouldBe "1.1.07"
    otaHistory.getOtaPackageName shouldBe "OTA"
    otaHistory.getOtaName shouldBe "固件.N900"
    otaHistory.getRemark shouldBe "你好789"
    TempDir shouldBe "/test"
  }

  /**
    * 保存ota数据，根据产品型号、厂商编号、版本名称、机构和机构类型查询ota历史信息为空，
    * 为空时，直接插入信息不用调用删除方法，
    * 根据产品型号、厂商编号、机构和机构类型查询ota文件最新版本为空情况
    * 为空时，最小版本取当前ota上传的目标版本
    */
  "testSaveOtaData1" should "handle successful" in {
    otaInfoHandler.otaHistoryDao = mock[OtaHistoryDao]
    otaInfoHandler.otaFileLatestDao = mock[OtaFileLatestDao]
    //根据产品型号、厂商编号、版本名称、机构和机构类型查询ota历史信息
    when(otaInfoHandler.otaHistoryDao.findByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType(
      "AAAA", "N900", "1.1.07", "000", "2")).thenReturn(null)
    //根据产品型号、厂商编号、机构和机构类型查询ota文件最新版本
    when(otaInfoHandler.otaFileLatestDao.findByFirmCodeAndProdCodeAndOrgIdAndOrgType(
      "AAAA", "N900", "000", "2")).thenReturn(null)
    //mock方法参数
    val otaHistory = mock[OtaHistory]
    when(otaHistory.getFirmCode).thenReturn("AAAA")
    when(otaHistory.getProdCode).thenReturn("N900")
    when(otaHistory.getVersionName).thenReturn("1.1.07")
    //调用方法
    otaInfoHandler.saveOtaData(otaHistory, "000", "2")
    //验证方法调用次数
    //调用两次活动厂商编号和产品类型方法(一次查询ota历史信息， 一次查询ota文件最新版本信息)
    // 调用两次次活动版本名称方法（一次查询ota历史信息，一次得到最低版本名称时）
    verify(otaHistory, times(2)).getFirmCode
    verify(otaHistory, times(2)).getProdCode
    verify(otaHistory, times(2)).getVersionName
    //调用一次查询ota历史信息方法
    verify(otaInfoHandler.otaHistoryDao, times(1)).findByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType(
      "AAAA", "N900", "1.1.07", "000", "2")
    //调用一次设置上传时间方法
    verify(otaHistory, times(1)).setUpTime(any(classOf[String]))
    //调用一次查询ota文件最新版本信息方法
    verify(otaInfoHandler.otaFileLatestDao, times(1)).findByFirmCodeAndProdCodeAndOrgIdAndOrgType(
      "AAAA", "N900", "000", "2")
    //调用一次设置最低版本名称
    verify(otaHistory, times(1)).setMinVersionName("1.1.07")
    //调用一次保存ota历史信息方法
    verify(otaInfoHandler.otaHistoryDao, times(1)).saveAndFlush(otaHistory)
  }

  /**
    * 保存ota数据，根据产品型号、厂商编号、版本名称、机构和机构类型查询ota历史信息不为空，
    * 不为空时，先删除历史信息，在刷新一下，再保存
    * 根据产品型号、厂商编号、机构和机构类型查询ota文件最新版本不为空情况
    * 不为空时，最小版本取最新版本的最小值
    */
  "testSaveOtaData2" should "handle successful" in {
    otaInfoHandler.otaHistoryDao = mock[OtaHistoryDao]
    otaInfoHandler.otaFileLatestDao = mock[OtaFileLatestDao]
    //根据产品型号、厂商编号、版本名称、机构和机构类型查询ota历史信息
    val otaHistory1 = mock[OtaHistory]
    when(otaInfoHandler.otaHistoryDao.findByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType(
      "AAAA", "N900", "1.1.07", "000", "2")).thenReturn(otaHistory1)
    //根据产品型号、厂商编号、机构和机构类型查询ota文件最新版本
    val otaFileLatest = mock[OtaFileLatest]
    when(otaFileLatest.getMinVersionName).thenReturn("1.1.05")
    when(otaInfoHandler.otaFileLatestDao.findByFirmCodeAndProdCodeAndOrgIdAndOrgType(
      "AAAA", "N900", "000", "2")).thenReturn(otaFileLatest)
    //mock方法参数
    val otaHistory = mock[OtaHistory]
    when(otaHistory.getFirmCode).thenReturn("AAAA")
    when(otaHistory.getProdCode).thenReturn("N900")
    when(otaHistory.getVersionName).thenReturn("1.1.07")
    //调用方法
    otaInfoHandler.saveOtaData(otaHistory, "000", "2")
    //验证方法调用次数
    //调用两次活动厂商编号和产品类型方法(一次查询ota历史信息， 一次查询ota文件最新版本信息)
    // 调用一次活动版本名称方法（一次查询ota历史信息）
    verify(otaHistory, times(2)).getFirmCode
    verify(otaHistory, times(2)).getProdCode
    verify(otaHistory, times(1)).getVersionName
    //调用一次查询ota历史信息方法
    verify(otaInfoHandler.otaHistoryDao, times(1)).findByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType(
      "AAAA", "N900", "1.1.07", "000", "2")
    //调用一次删除ota历史信息方法
    verify(otaInfoHandler.otaHistoryDao, times(1)).delete(otaHistory1)
    //调用一次刷新方法
    verify(otaInfoHandler.otaHistoryDao, times(1)).flush()
    //调用一次设置上传时间方法
    verify(otaHistory, times(1)).setUpTime(any(classOf[String]))
    //调用一次查询ota文件最新版本信息方法
    verify(otaInfoHandler.otaFileLatestDao, times(1)).findByFirmCodeAndProdCodeAndOrgIdAndOrgType(
      "AAAA", "N900", "000", "2")
    //调用一次获得最低版本号方法
    verify(otaFileLatest, times(1)).getMinVersionName
    //调用一次设置最低版本名称
    verify(otaHistory, times(1)).setMinVersionName("1.1.05")
    //调用一次保存ota历史信息方法
    verify(otaInfoHandler.otaHistoryDao, times(1)).saveAndFlush(otaHistory)
  }

  /**
    * 更新ota最新版本，最新版本为空时，直接插入最大版本
    */
  "testUpdateOtaFileLatest1" should "handle successful" in {
    otaInfoHandler.otaHistoryDao = mock[OtaHistoryDao]
    otaInfoHandler.otaFileLatestDao = mock[OtaFileLatestDao]
    val otaHis1 = mock[OtaHistory]
    val otaHis2 = mock[OtaHistory]
    val otaHis3 = mock[OtaHistory]
    when(otaHis1.getFirmCode).thenReturn("A1")
    when(otaHis2.getFirmCode).thenReturn("A1")
    when(otaHis3.getFirmCode).thenReturn("A2")
    when(otaHis1.getProdCode).thenReturn("N1")
    when(otaHis2.getProdCode).thenReturn("N1")
    when(otaHis3.getProdCode).thenReturn("N2")
    when(otaHis1.getVersionName).thenReturn("1.1")
    when(otaHis2.getVersionName).thenReturn("1.2")
    when(otaHis3.getVersionName).thenReturn("1.3")
    when(otaHis1.getOrgId).thenReturn("000")
    when(otaHis2.getOrgId).thenReturn("000")
    when(otaHis3.getOrgId).thenReturn("000")
    when(otaHis1.getOrgType).thenReturn("2")
    when(otaHis2.getOrgType).thenReturn("2")
    when(otaHis3.getOrgType).thenReturn("2")
    when(otaInfoHandler.otaHistoryDao.findAll()).thenReturn(List(otaHis1, otaHis2, otaHis3).asJava)

    //根据产品型号、厂商编号、最大版本名称、机构和机构类型查询ota历史信息
    val otaHistory = mock[OtaHistory]
    otaHistory.setFirmCode("A1")
    otaHistory.setProdCode("N1")
    when(otaInfoHandler.otaHistoryDao.findByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType(
      "A1", "N1", "1.2", "000", "2")).thenReturn(otaHistory)
    //根据产品型号、厂商编号、机构和机构类型查询ota历史信息
    when(otaInfoHandler.otaFileLatestDao.findByFirmCodeAndProdCodeAndOrgIdAndOrgType(
      "A1", "N1", "000", "2")).thenReturn(null)
    //调用方法
    val otaHistory1 = mock[OtaHistory]
    when(otaHistory1.getFirmCode).thenReturn("A1")
    when(otaHistory1.getProdCode).thenReturn("N1")
    otaInfoHandler.updateOtaFileLatest(otaHistory1, "000", "2")
    //验证方法调用
    //调用一次查询所有历史信息方法
    verify(otaInfoHandler.otaHistoryDao, times(1)).findAll()
    //得到版本集合时调用的方法
    verify(otaHis1, times(1)).getOrgId
    verify(otaHis1, times(1)).getFirmCode
    verify(otaHis1, times(1)).getOrgType
    verify(otaHis1, times(1)).getProdCode
    verify(otaHis1, times(1)).getVersionName

    verify(otaHis2, times(1)).getOrgId
    verify(otaHis2, times(1)).getFirmCode
    verify(otaHis2, times(1)).getOrgType
    verify(otaHis2, times(1)).getProdCode
    verify(otaHis2, times(1)).getVersionName

    verify(otaHis3, times(1)).getOrgId
    verify(otaHis3, times(1)).getFirmCode
    verify(otaHis3, times(1)).getOrgType
    verify(otaHis3, times(1)).getProdCode
    verify(otaHis3, times(1)).getVersionName
    //调用一次活动厂商编号和产品型号
    verify(otaHistory1, times(1)).getFirmCode
    verify(otaHistory1, times(1)).getProdCode
    //调用一次根据产品型号、厂商编号、最大版本名称、机构和机构类型查询ota历史信息
    verify(otaInfoHandler.otaHistoryDao, times(1)).findByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType(
      "A1", "N1", "1.2", "000", "2")
    //调用一次根据产品型号、厂商编号、机构和机构类型查询ota历史信息
    verify(otaInfoHandler.otaFileLatestDao, times(1)).findByFirmCodeAndProdCodeAndOrgIdAndOrgType(
      "A1", "N1", "000", "2")
    //调用一次保存最新版本方法
    verify(otaInfoHandler.otaFileLatestDao, times(1)).saveAndFlush(any(classOf[OtaFileLatest]))
  }

  /**
    * 更新ota最新版本，最新版本不为空时，先删除最新版本，然后在插入最大版本
    */
  "testUpdateOtaFileLatest2" should "handle successful" in {
    otaInfoHandler.otaHistoryDao = mock[OtaHistoryDao]
    otaInfoHandler.otaFileLatestDao = mock[OtaFileLatestDao]
    val otaHis1 = mock[OtaHistory]
    val otaHis2 = mock[OtaHistory]
    val otaHis3 = mock[OtaHistory]
    when(otaHis1.getFirmCode).thenReturn("A1")
    when(otaHis2.getFirmCode).thenReturn("A1")
    when(otaHis3.getFirmCode).thenReturn("A2")
    when(otaHis1.getProdCode).thenReturn("N1")
    when(otaHis2.getProdCode).thenReturn("N1")
    when(otaHis3.getProdCode).thenReturn("N2")
    when(otaHis1.getVersionName).thenReturn("1.1")
    when(otaHis2.getVersionName).thenReturn("1.2")
    when(otaHis3.getVersionName).thenReturn("1.3")
    when(otaHis1.getOrgId).thenReturn("000")
    when(otaHis2.getOrgId).thenReturn("000")
    when(otaHis3.getOrgId).thenReturn("000")
    when(otaHis1.getOrgType).thenReturn("2")
    when(otaHis2.getOrgType).thenReturn("2")
    when(otaHis3.getOrgType).thenReturn("2")
    when(otaInfoHandler.otaHistoryDao.findAll()).thenReturn(List(otaHis1, otaHis2, otaHis3).asJava)

    //根据产品型号、厂商编号、最大版本名称、机构和机构类型查询ota历史信息
    val otaHistory = mock[OtaHistory]
    otaHistory.setFirmCode("A1")
    otaHistory.setProdCode("N1")
    when(otaInfoHandler.otaHistoryDao.findByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType(
      "A1", "N1", "1.2", "000", "2")).thenReturn(otaHistory)
    //根据产品型号、厂商编号、机构和机构类型查询ota历史信息
    val otaFileLatest = mock[OtaFileLatest]
    when(otaInfoHandler.otaFileLatestDao.findByFirmCodeAndProdCodeAndOrgIdAndOrgType(
      "A1", "N1", "000", "2")).thenReturn(otaFileLatest)
    //调用方法
    val otaHistory1 = mock[OtaHistory]
    when(otaHistory1.getFirmCode).thenReturn("A1")
    when(otaHistory1.getProdCode).thenReturn("N1")
    otaInfoHandler.updateOtaFileLatest(otaHistory1, "000", "2")
    //验证方法调用
    //调用一次查询所有历史信息方法
    verify(otaInfoHandler.otaHistoryDao, times(1)).findAll()
    //得到版本集合时调用的方法
    verify(otaHis1, times(1)).getOrgId
    verify(otaHis1, times(1)).getFirmCode
    verify(otaHis1, times(1)).getOrgType
    verify(otaHis1, times(1)).getProdCode
    verify(otaHis1, times(1)).getVersionName

    verify(otaHis2, times(1)).getOrgId
    verify(otaHis2, times(1)).getFirmCode
    verify(otaHis2, times(1)).getOrgType
    verify(otaHis2, times(1)).getProdCode
    verify(otaHis2, times(1)).getVersionName

    verify(otaHis3, times(1)).getOrgId
    verify(otaHis3, times(1)).getFirmCode
    verify(otaHis3, times(1)).getOrgType
    verify(otaHis3, times(1)).getProdCode
    verify(otaHis3, times(1)).getVersionName
    //调用一次活动厂商编号和产品型号
    verify(otaHistory1, times(1)).getFirmCode
    verify(otaHistory1, times(1)).getProdCode
    //调用一次根据产品型号、厂商编号、最大版本名称、机构和机构类型查询ota历史信息
    verify(otaInfoHandler.otaHistoryDao, times(1)).findByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType(
      "A1", "N1", "1.2", "000", "2")
    //调用一次根据产品型号、厂商编号、机构和机构类型查询ota历史信息
    verify(otaInfoHandler.otaFileLatestDao, times(1)).findByFirmCodeAndProdCodeAndOrgIdAndOrgType(
      "A1", "N1", "000", "2")
    //调用一次上传最新版本的方法
    verify(otaInfoHandler.otaFileLatestDao, times(1)).delete(otaFileLatest)
    //调用一次刷新方法
    verify(otaInfoHandler.otaFileLatestDao, times(1)).flush()
    //调用一次保存最新版本方法
    verify(otaInfoHandler.otaFileLatestDao, times(1)).saveAndFlush(any(classOf[OtaFileLatest]))
  }

  /**
    * 测试生成md5文件
    */
  "testBuildM5File" should "handle successful" in {
    val zipRes = new ClassPathResource("/otaUpload/Desktop.zip", this.getClass)
    val dirRes = new ClassPathResource("/temp", this.getClass)
    otaInfoHandler.buildM5File("asdafkdflsdf", zipRes.getFile, dirRes.getFile)
    val md5File = new ClassPathResource("/temp/Desktop.zip.md5", this.getClass).getFile
    md5File.exists() shouldBe true
    Source.fromFile(md5File, "GBK").mkString shouldBe "asdafkdflsdf"
    md5File.delete()
  }

}
