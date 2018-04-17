package com.yada.spos.mag.service.ext

import com.yada.spos.db.dao.{DeviceDao, ProductsDao}
import com.yada.spos.db.model.Products
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.convert.decorateAll._

/**
  * Created by pangChangSong on 2016/9/20.
  * 设备处理测试
  */
@RunWith(classOf[JUnitRunner])
class DeviceHandlerTest extends FlatSpec with Matchers with MockitoSugar {

  val deviceHandler = new DeviceHandler

  /**
    * 测试读取xls文件
    */
  "testReadXls" should "handle successful" in {
    deviceHandler.productsDao = mock[ProductsDao]
    deviceHandler.deviceDao = mock[DeviceDao]
    val products = new Products
    products.setFirmCode("AAAA")
    products.setFirmName("aaaa")
    products.setProdCode("N900")
    products.setProdName("N900")
    when(deviceHandler.productsDao.findByFirmCodeAndProdCode("AAAA", "N900"))
      .thenReturn(products)

    val products1 = new Products
    products1.setFirmCode("BBBB")
    products1.setFirmName("BBBB")
    products1.setProdCode("N1000")
    products1.setProdName("N1000")
    //根据厂商编号和产品类型查询产品
    when(deviceHandler.productsDao.findByFirmCodeAndProdCode("BBBB", "N1000"))
      .thenReturn(products1)
    //根据设备sn查询设备
    when(deviceHandler.deviceDao.findByDevSnAndFirmCode(any(classOf[String]), any(classOf[String]))).thenReturn(null)
    val inputStream = classOf[DeviceHandlerTest].getResourceAsStream("/deviceImport/设备sn.xls")
    //调用方法
    val devices = deviceHandler.readXls(inputStream, "000", "2")
    //验证调用”根据厂商编号和产品类型查询产品“方法两次
    verify(deviceHandler.productsDao, times(2)).findByFirmCodeAndProdCode(any(classOf[String]), any(classOf[String]))
    //断言返回两条数据
    devices.asJava.size() shouldBe 2
    //验证返回结果
    val reDevice1 = devices.head
    reDevice1.getDevSn shouldBe "123456789"
    reDevice1.getFirmCode shouldBe "AAAA"
    reDevice1.getFirmName shouldBe "aaaa"
    reDevice1.getProdCode shouldBe "N900"
    reDevice1.getProdName shouldBe "N900"
    reDevice1.getIsActive shouldBe "0"
    reDevice1.getStatus shouldBe "0"
    reDevice1.getOrgType shouldBe "2"
    reDevice1.getOrgId shouldBe "000"
    val reDevice2 = devices(1)
    reDevice2.getDevSn shouldBe "111111111"
    reDevice2.getFirmCode shouldBe "BBBB"
    reDevice2.getFirmName shouldBe "BBBB"
    reDevice2.getProdCode shouldBe "N1000"
    reDevice2.getProdName shouldBe "N1000"
    reDevice2.getIsActive shouldBe "0"
    reDevice2.getStatus shouldBe "0"
    reDevice2.getOrgType shouldBe "2"
    reDevice2.getOrgId shouldBe "000"
  }

  /**
    * 测试读取csv
    */
  "testReadCsv" should "handle successful" in {
    deviceHandler.productsDao = mock[ProductsDao]
    deviceHandler.deviceDao = mock[DeviceDao]
    val products = new Products
    products.setFirmCode("AAAA")
    products.setFirmName("aaaa")
    products.setProdCode("N900")
    products.setProdName("N900")
    when(deviceHandler.productsDao.findByFirmCodeAndProdCode("AAAA", "N900"))
      .thenReturn(products)

    val products1 = new Products
    products1.setFirmCode("BBBB")
    products1.setFirmName("BBBB")
    products1.setProdCode("N1000")
    products1.setProdName("N1000")
    //根据厂商编号和产品类型查询产品
    when(deviceHandler.productsDao.findByFirmCodeAndProdCode("BBBB", "N1000"))
      .thenReturn(products1)
    //根据设备sn查询设备
    when(deviceHandler.deviceDao.findByDevSnAndFirmCode(any(classOf[String]), any(classOf[String]))).thenReturn(null)
    val inputStream = classOf[DeviceHandlerTest].getResourceAsStream("/deviceImport/设备sn.csv")
    //调用方法
    val devices = deviceHandler.readCsv(inputStream, "000", "2")
    //验证调用”根据厂商编号和产品类型查询产品“方法两次
    verify(deviceHandler.productsDao, times(2)).findByFirmCodeAndProdCode(any(classOf[String]), any(classOf[String]))
    //断言返回两条数据
    devices.asJava.size() shouldBe 2
    //验证返回结果
    val reDevice1 = devices.head
    reDevice1.getDevSn shouldBe "123456789"
    reDevice1.getFirmCode shouldBe "AAAA"
    reDevice1.getFirmName shouldBe "aaaa"
    reDevice1.getProdCode shouldBe "N900"
    reDevice1.getProdName shouldBe "N900"
    reDevice1.getIsActive shouldBe "0"
    reDevice1.getStatus shouldBe "0"
    reDevice1.getOrgType shouldBe "2"
    reDevice1.getOrgId shouldBe "000"
    val reDevice2 = devices(1)
    reDevice2.getDevSn shouldBe "111111111"
    reDevice2.getFirmCode shouldBe "BBBB"
    reDevice2.getFirmName shouldBe "BBBB"
    reDevice2.getProdCode shouldBe "N1000"
    reDevice2.getProdName shouldBe "N1000"
    reDevice2.getIsActive shouldBe "0"
    reDevice2.getStatus shouldBe "0"
    reDevice2.getOrgType shouldBe "2"
    reDevice2.getOrgId shouldBe "000"
  }
}
