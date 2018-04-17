package com.yada.spos.mag.service

import java.io.InputStream

import com.yada.spos.db.dao.{AppGroupDao, AppGroupDevDao, DeviceDao}
import com.yada.spos.db.model.{AppGroup, AppGroupDev, Device, DevicePK}
import com.yada.spos.db.query.DeviceQuery
import com.yada.spos.mag.service.ext.DeviceHandler
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile

/**
  * Created by pangChangSong on 2016/9/20.
  * 设备管理service测试
  */
@RunWith(classOf[JUnitRunner])
class DeviceServiceTest extends FlatSpec with Matchers with MockitoSugar {

  val deviceService = new DeviceService

  /**
    * 测试分页查询所有
    */
  "testFindAll" should "handle successful" in {
    deviceService.deviceDao = mock[DeviceDao]
    //调用方法
    deviceService.findAll(any(classOf[DeviceQuery]), any(classOf[Pageable]))
    //验证调用一次delete方法
    verify(deviceService.deviceDao, times(1)).findAll(any(classOf[DeviceQuery]), any(classOf[Pageable]))
  }

  /**
    * 测试删除设备
    */
  "testDelete" should "handle successful" in {
    deviceService.deviceDao = mock[DeviceDao]
    deviceService.appGroupDevDao = mock[AppGroupDevDao]
    //调用方法
    deviceService.delete(mock[DevicePK])
    //验证调用一次delete方法
    verify(deviceService.deviceDao, times(1)).delete(any(classOf[DevicePK]))
    //验证调用一次刷新方法
    verify(deviceService.deviceDao, times(1)).flush()
    //验证调用一次根据厂商编号和设备sn删除应用分组关联设备
    verify(deviceService.appGroupDevDao, times(1)).deleteByFirmCodeAndDevSn(any(classOf[String]), any(classOf[String]))
  }

  /**
    * 测试验证是否能删除设备(1-设备已经激活，不能删除)
    * 设备未激活和未绑定终端才可以删除
    */
  "testValidateIsCanDelete1" should "handle successful" in {
    deviceService.deviceDao = mock[DeviceDao]
    val device = mock[Device]
    when(device.getIsActive).thenReturn("1")
    //根据设备sn和厂商编号查询设备
    when(deviceService.deviceDao.findByDevSnAndFirmCode("AAAA", "AAAA")).thenReturn(device)
    //调用方法
    val result = deviceService.validateIsCanDelete("AAAA", "AAAA")
    result shouldBe "1"
    //验证调用一次”根据设备sn和厂商编号查询设备“方法
    verify(deviceService.deviceDao, times(1)).findByDevSnAndFirmCode("AAAA", "AAAA")
    //验证调用一次device获得是否激活方法
    verify(device, times(1)).getIsActive
  }

  /**
    * 测试验证是否能删除设备(2-设备已绑定终端，不能删除)
    * 设备未激活和未绑定终端才可以删除
    */
  "testValidateIsCanDelete2" should "handle successful" in {
    deviceService.deviceDao = mock[DeviceDao]
    val device = mock[Device]
    when(device.getIsActive).thenReturn("0")
    when(device.getStatus).thenReturn("1")
    //根据设备sn和厂商编号查询设备
    when(deviceService.deviceDao.findByDevSnAndFirmCode("AAAA", "AAAA")).thenReturn(device)
    //调用方法
    val result = deviceService.validateIsCanDelete("AAAA", "AAAA")
    result shouldBe "2"
    //验证调用一次”根据设备sn和厂商编号查询设备“方法
    verify(deviceService.deviceDao, times(1)).findByDevSnAndFirmCode("AAAA", "AAAA")
    //验证调用一次device获得是否激活和是否绑定方法
    verify(device, times(1)).getIsActive
    verify(device, times(1)).getStatus
  }

  /**
    * 测试验证是否能删除设备（设备未激活和未绑定终端才可以删除）
    */
  "testValidateIsCanDelete3" should "handle successful" in {
    deviceService.deviceDao = mock[DeviceDao]
    val device = mock[Device]
    when(device.getIsActive).thenReturn("0")
    when(device.getStatus).thenReturn("0")
    //根据设备sn和厂商编号查询设备
    when(deviceService.deviceDao.findByDevSnAndFirmCode("AAAA", "AAAA")).thenReturn(device)
    //调用方法
    val result = deviceService.validateIsCanDelete("AAAA", "AAAA")
    result shouldBe "0"
    //验证调用一次”根据设备sn和厂商编号查询设备“方法
    verify(deviceService.deviceDao, times(1)).findByDevSnAndFirmCode("AAAA", "AAAA")
    //验证调用一次device获得是否激活和是否绑定方法
    verify(device, times(1)).getIsActive
    verify(device, times(1)).getStatus
  }

  /**
    * 测试导入xls格式的文件
    */
  "testImportSnForXls" should "handle successful" in {
    //mock对象
    deviceService.deviceHandler = mock[DeviceHandler]
    deviceService.deviceDao = mock[DeviceDao]
    deviceService.appGroupDao = mock[AppGroupDao]
    deviceService.appGroupDevDao = mock[AppGroupDevDao]
    //mock读取xls返回值
    val device1 = new Device
    val device = new Device
    device.setDevSn("123")
    device.setFirmCode("AAAA")
    device1.setDevSn("1234")
    device1.setFirmCode("AAAd")
    //读取xls格式的信息
    when(deviceService.deviceHandler.readXls(any(classOf[InputStream]), any(classOf[String])
      , any(classOf[String]))).thenReturn(List(device, device1))
    //查询默认分组，每个机构只有一个
    when(deviceService.appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String])
      , any(classOf[String]), any(classOf[String]))).thenReturn(mock[AppGroup])
    val file = mock[MultipartFile]
    when(file.getInputStream).thenReturn(mock[InputStream])
    when(file.getOriginalFilename).thenReturn("设备sn.xls")
    //调用方法
    deviceService.importSn(file, "000", "2")
    //验证"读取xls格式的信息"调用一次
    verify(deviceService.deviceHandler, times(1)).readXls(any(classOf[InputStream]), any(classOf[String])
      , any(classOf[String]))
    //验证”查询默认分组“调用两次，因为读取xls返回了两条数据
    verify(deviceService.appGroupDao, times(2)).findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String])
      , any(classOf[String]), any(classOf[String]))
    //验证”保存设备“调用两次，因为读取xls返回了两条数据
    verify(deviceService.deviceDao, times(2)).saveAndFlush(any(classOf[Device]))
    //验证插入默认分组两次，因为读取xls返回了两条数据
    verify(deviceService.appGroupDevDao, times(2)).saveAndFlush(any(classOf[AppGroupDev]))
  }

  /**
    * 测试导入Csv格式的文件
    */
  "testImportSnForCsv" should "handle successful" in {
    //mock对象
    deviceService.deviceHandler = mock[DeviceHandler]
    deviceService.deviceDao = mock[DeviceDao]
    deviceService.appGroupDao = mock[AppGroupDao]
    deviceService.appGroupDevDao = mock[AppGroupDevDao]
    //mock读取xls返回值
    val device1 = new Device
    val device = new Device
    device.setDevSn("123")
    device.setFirmCode("AAAA")
    device1.setDevSn("1234")
    device1.setFirmCode("AAAd")
    //读取csv格式的信息
    when(deviceService.deviceHandler.readCsv(any(classOf[InputStream]), any(classOf[String])
      , any(classOf[String]))).thenReturn(List(device, device1))
    //查询默认分组，每个机构只有一个
    when(deviceService.appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String])
      , any(classOf[String]), any(classOf[String]))).thenReturn(mock[AppGroup])
    val file = mock[MultipartFile]
    when(file.getInputStream).thenReturn(mock[InputStream])
    when(file.getOriginalFilename).thenReturn("设备sn.csv")
    //调用方法
    deviceService.importSn(file, "000", "2")
    //验证"读取csv格式的信息"调用一次
    verify(deviceService.deviceHandler, times(1)).readCsv(any(classOf[InputStream]), any(classOf[String])
      , any(classOf[String]))
    //验证”查询默认分组“调用两次，因为读取csv格式的信息返回了两条数据
    verify(deviceService.appGroupDao, times(2)).findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String])
      , any(classOf[String]), any(classOf[String]))
    //验证”保存设备“调用两次，因为读取csv格式的信息返回了两条数据
    verify(deviceService.deviceDao, times(2)).saveAndFlush(any(classOf[Device]))
    //验证插入默认分组两次，因为读取csv格式的信息返回了两条数据
    verify(deviceService.appGroupDevDao, times(2)).saveAndFlush(any(classOf[AppGroupDev]))
  }
}
