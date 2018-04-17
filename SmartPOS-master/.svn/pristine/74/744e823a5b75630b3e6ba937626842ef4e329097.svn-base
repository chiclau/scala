package com.yada.spos.mag.service

import com.yada.spos.db.dao.{AppGroupDao, AppGroupDevDao, DeviceDao, TermWorkKeyDao}
import com.yada.spos.db.model.{AppGroup, AppGroupDev, Device, DevicePK}
import com.yada.spos.db.query.{BTermWorkKeyQuery, DeviceQuery}
import com.yada.spos.mag.service.ext.SystemHandler
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.data.domain.Pageable

import scala.collection.convert.decorateAll._

/**
  * Created by pangChangSong on 2016/9/19.
  * 终端密钥service测试
  */
@RunWith(classOf[JUnitRunner])
class BTermWorkKeyServiceTest extends FlatSpec with Matchers with MockitoSugar {
  val bTermWorkKeyService = new BTermWorkKeyService

  "testFindAll" should "handle successful" in {
    bTermWorkKeyService.bTermWorkKeyDao = mock[TermWorkKeyDao]
    //调用方法
    bTermWorkKeyService.findAll(any(classOf[BTermWorkKeyQuery]), any(classOf[Pageable]))
    //验证调用一次分页查询所有方法
    verify(bTermWorkKeyService.bTermWorkKeyDao, times(1)).findAll(any(classOf[BTermWorkKeyQuery]), any(classOf[Pageable]))
  }

  /**
    * 测试验证是否可以绑定(机构下商户号不存在时，不能绑定)
    * 登录用户所在的机构下没有这个商户不能绑定
    *
    * 可以绑定的条件：0.商户号必须在当前登录用户的机构下
    * 1.商户号、终端号在终端密钥表中存在唯一一条
    * 2.商户号、终端号不能绑定其他设备
    * 3.需要绑定的设备必须存在，且未绑定
    */
  "testValidateIsMayBound0" should "handle successful" in {
    bTermWorkKeyService.systemHandler = mock[SystemHandler]
    //判断机构下商户号是否存在
    when(bTermWorkKeyService.systemHandler.validateOrgIsExistsMer("00011", "2", "111111111111111")).thenReturn(false)
    val bTermWorkKeyQuery = mock[BTermWorkKeyQuery]
    val device = mock[Device]
    when(device.getMerNo).thenReturn("111111111111111")
    when(device.getTermNo).thenReturn("11111111")
    //调用方法
    val mess = bTermWorkKeyService.validateIsMayBound(device, "00011", "2", bTermWorkKeyQuery, mock[DeviceQuery])
    //验证调用一次“判断机构下商户号是否存在”方法
    verify(bTermWorkKeyService.systemHandler, times(1)).validateOrgIsExistsMer("00011", "2", "111111111111111")
    //断言返回”0“-在t_b_term_work_key表中查询不存在唯一商户号和终端号记录
    mess shouldBe "4"
  }


  /**
    * 测试验证是否可以绑定(根据商户号、终端号查询终端密钥表,不存在数据或数据不唯一时)
    * 必须是存在，并且是有唯一一条时才可以绑定
    *
    * 可以绑定的条件：0.商户号必须在当前登录用户的机构下
    * 1.商户号、终端号在终端密钥表中存在唯一一条
    * 2.商户号、终端号不能绑定其他设备
    * 3.需要绑定的设备必须存在，且未绑定
    */
  "testValidateIsMayBound1" should "handle successful" in {
    bTermWorkKeyService.systemHandler = mock[SystemHandler]
    //判断机构下商户号是否存在,存在
    when(bTermWorkKeyService.systemHandler.validateOrgIsExistsMer("00011", "2", "111111111111111")).thenReturn(true)
    //根据商户号、终端号查询终端密钥表，判断是否存在，如果不存在，不能绑定，必须是存在，并且是有唯一一条时才可以绑定
    val bTermWorkKeyQuery = mock[BTermWorkKeyQuery]
    when(bTermWorkKeyService.systemHandler.validateBTermWorkKeyIsExistsUniqueMerAndTerm("2", "111111111111111", "11111111", bTermWorkKeyQuery)).thenReturn(false)
    val device = mock[Device]
    when(device.getMerNo).thenReturn("111111111111111")
    when(device.getTermNo).thenReturn("11111111")
    //调用方法
    val mess = bTermWorkKeyService.validateIsMayBound(device, "00011", "2", bTermWorkKeyQuery, mock[DeviceQuery])
    //验证调用一次“判断机构下商户号是否存在”方法
    verify(bTermWorkKeyService.systemHandler, times(1)).validateOrgIsExistsMer("00011", "2", "111111111111111")
    //验证调用一次“在t_b_term_work_key表中查询不存在唯一商户号和终端号记录”
    verify(bTermWorkKeyService.systemHandler, times(1)).validateBTermWorkKeyIsExistsUniqueMerAndTerm("2", "111111111111111", "11111111", bTermWorkKeyQuery)
    //断言返回”0“-在t_b_term_work_key表中查询不存在唯一商户号和终端号记录
    mess shouldBe "0"
  }

  /**
    * 测试验证是否可以绑定(当终端已经绑定时，不能绑定)
    */
  "testValidateIsMayBound2" should "handle successful" in {
    bTermWorkKeyService.systemHandler = mock[SystemHandler]
    bTermWorkKeyService.deviceDao = mock[DeviceDao]
    //判断机构下商户号是否存在,存在
    when(bTermWorkKeyService.systemHandler.validateOrgIsExistsMer("00011", "2", "111111111111111")).thenReturn(true)
    //根据商户号、终端号查询终端密钥表,存在数据
    val bTermWorkKeyQuery = mock[BTermWorkKeyQuery]
    when(bTermWorkKeyService.systemHandler.validateBTermWorkKeyIsExistsUniqueMerAndTerm("2", "111111111111111", "11111111", bTermWorkKeyQuery)).thenReturn(true)
    //根据商户号、终端号、机构号和机构类型查询设备表
    //当返回非空时证明该商户已经绑定终端，不能再绑定了
    when(bTermWorkKeyService.deviceDao.findAll(any(classOf[DeviceQuery]))).thenReturn(List(new Device).asJava)
    //模拟参数
    val device = mock[Device]
    when(device.getMerNo).thenReturn("111111111111111")
    when(device.getTermNo).thenReturn("11111111")
    val deviceQuery = mock[DeviceQuery]
    //调用方法
    val mess = bTermWorkKeyService.validateIsMayBound(device, "00011", "2", bTermWorkKeyQuery, deviceQuery)
    //验证调用一次“判断机构下商户号是否存在”方法
    verify(bTermWorkKeyService.systemHandler, times(1)).validateOrgIsExistsMer("00011", "2", "111111111111111")
    //验证调用一次“在t_b_term_work_key表中查询不存在唯一商户号和终端号记录”
    verify(bTermWorkKeyService.systemHandler, times(1)).validateBTermWorkKeyIsExistsUniqueMerAndTerm("2", "111111111111111", "11111111", bTermWorkKeyQuery)
    //验证device的获得商户号3次、终端号调用2次,一次是“根据商户号、终端号查询终端密钥表”，一次是“根据商户号、终端号、机构号和机构类型查询设备表”
    verify(device, times(3)).getMerNo
    verify(device, times(2)).getTermNo
    //验证deviceQuery的设置机构、机构类型、商户号和终端号各调用一次
    verify(deviceQuery).setOrgId("00011")
    verify(deviceQuery).setOrgType("2")
    verify(deviceQuery).setMerNo("111111111111111")
    verify(deviceQuery).setTermNo("11111111")
    //验证“根据商户号、终端号、机构号和机构类型查询设备表”方法调用一次
    verify(bTermWorkKeyService.deviceDao, times(1)).findAll(deviceQuery)
    //断言返回”3“-终端已经绑定
    mess shouldBe "3"
  }

  /**
    * 测试验证是否可以绑定(当设备不存在或已绑定时，不能绑定)
    * 根据设备sn和厂商编号查询不到设备或设备的“设备状态”是1-已关联终端时，不能绑定
    */
  "testValidateIsMayBound3" should "handle successful" in {
    bTermWorkKeyService.systemHandler = mock[SystemHandler]
    bTermWorkKeyService.deviceDao = mock[DeviceDao]
    //判断机构下商户号是否存在,存在
    when(bTermWorkKeyService.systemHandler.validateOrgIsExistsMer("00011", "2", "111111111111111")).thenReturn(true)
    //根据商户号、终端号查询终端密钥表,存在数据
    val bTermWorkKeyQuery = mock[BTermWorkKeyQuery]
    when(bTermWorkKeyService.systemHandler.validateBTermWorkKeyIsExistsUniqueMerAndTerm("2", "111111111111111", "11111111", bTermWorkKeyQuery)).thenReturn(true)
    //根据商户号、终端号、机构号和机构类型查询设备表
    //当返回空时证明该商户未绑定终端
    when(bTermWorkKeyService.deviceDao.findAll(any(classOf[DeviceQuery]))).thenReturn(null)
    //根据设备sn和厂商编号查询设备，返回设备状态是1-已关联终端
    val returnDevice = mock[Device]
    when(returnDevice.getStatus).thenReturn("1")
    when(bTermWorkKeyService.deviceDao.findOne(any(classOf[DevicePK]))).thenReturn(returnDevice)
    //模拟参数
    val device = mock[Device]
    when(device.getMerNo).thenReturn("111111111111111")
    when(device.getTermNo).thenReturn("11111111")
    val deviceQuery = mock[DeviceQuery]
    //调用方法
    val mess = bTermWorkKeyService.validateIsMayBound(device, "00011", "2", bTermWorkKeyQuery, deviceQuery)
    //验证调用一次“判断机构下商户号是否存在”方法
    verify(bTermWorkKeyService.systemHandler, times(1)).validateOrgIsExistsMer("00011", "2", "111111111111111")
    //验证调用一次“在t_b_term_work_key表中查询不存在唯一商户号和终端号记录”
    verify(bTermWorkKeyService.systemHandler, times(1)).validateBTermWorkKeyIsExistsUniqueMerAndTerm("2", "111111111111111", "11111111", bTermWorkKeyQuery)
    //验证device的获得商户号3次、终端号调用2次,一次是“根据商户号、终端号查询终端密钥表”，一次是“根据商户号、终端号、机构号和机构类型查询设备表”
    //调用一次设备sn和厂商编号,是在查询“根据设备sn和厂商编号查询设备”时调用
    verify(device, times(3)).getMerNo
    verify(device, times(2)).getTermNo
    verify(device, times(1)).getDevSn
    verify(device, times(1)).getFirmCode
    //验证deviceQuery的设置机构、机构类型、商户号和终端号各调用一次（设置的如下值是设置1次）
    verify(deviceQuery).setOrgId("00011")
    verify(deviceQuery).setOrgType("2")
    verify(deviceQuery).setMerNo("111111111111111")
    verify(deviceQuery).setTermNo("11111111")
    //验证“根据商户号、终端号、机构号和机构类型查询设备表”方法调用一次
    verify(bTermWorkKeyService.deviceDao, times(1)).findAll(deviceQuery)
    //验证根据厂商编号和设备sn查询设备方法调用一次
    verify(bTermWorkKeyService.deviceDao, times(1)).findOne(any(classOf[DevicePK]))
    //断言返回”2“-设备不存在或已绑定
    mess shouldBe "2"
  }

  /**
    * 测试验证是否可以绑定(当设备存在且未绑定时，此时可以绑定)
    *
    */
  "testValidateIsMayBound4" should "handle successful" in {
    bTermWorkKeyService.systemHandler = mock[SystemHandler]
    bTermWorkKeyService.deviceDao = mock[DeviceDao]
    //判断机构下商户号是否存在,存在
    when(bTermWorkKeyService.systemHandler.validateOrgIsExistsMer("00011", "2", "111111111111111")).thenReturn(true)
    //根据商户号、终端号查询终端密钥表,存在数据
    val bTermWorkKeyQuery = mock[BTermWorkKeyQuery]
    when(bTermWorkKeyService.systemHandler.validateBTermWorkKeyIsExistsUniqueMerAndTerm("2", "111111111111111", "11111111", bTermWorkKeyQuery)).thenReturn(true)
    //根据商户号、终端号、机构号和机构类型查询设备表
    //当返回空时证明该商户未绑定终端
    when(bTermWorkKeyService.deviceDao.findAll(any(classOf[DeviceQuery]))).thenReturn(null)
    //根据设备sn和厂商编号查询设备，返回设备状态是0-未关联终端
    val returnDevice = mock[Device]
    when(returnDevice.getStatus).thenReturn("0")
    when(bTermWorkKeyService.deviceDao.findOne(any(classOf[DevicePK]))).thenReturn(returnDevice)
    //模拟参数
    val device = mock[Device]
    when(device.getMerNo).thenReturn("111111111111111")
    when(device.getTermNo).thenReturn("11111111")
    val deviceQuery = mock[DeviceQuery]
    //调用方法
    val mess = bTermWorkKeyService.validateIsMayBound(device, "00011", "2", bTermWorkKeyQuery, deviceQuery)
    //验证bTermWorkKeyQuery的设置商户号、终端号各调用一次
    //验证调用一次“判断机构下商户号是否存在”方法
    verify(bTermWorkKeyService.systemHandler, times(1)).validateOrgIsExistsMer("00011", "2", "111111111111111")
    //验证调用一次“在t_b_term_work_key表中查询不存在唯一商户号和终端号记录”
    verify(bTermWorkKeyService.systemHandler, times(1)).validateBTermWorkKeyIsExistsUniqueMerAndTerm("2", "111111111111111", "11111111", bTermWorkKeyQuery)

    //验证device的获得商户号3次、终端号各调用2次,一次是“根据商户号、终端号查询终端密钥表”，一次是“根据商户号、终端号、机构号和机构类型查询设备表”
    //调用一次设备sn和厂商编号,是在查询“根据设备sn和厂商编号查询设备”时调用
    verify(device, times(3)).getMerNo
    verify(device, times(2)).getTermNo
    verify(device, times(1)).getDevSn
    verify(device, times(1)).getFirmCode
    //验证deviceQuery的设置机构、机构类型、商户号和终端号各调用一次（设置的如下值是设置1次）
    verify(deviceQuery).setOrgId("00011")
    verify(deviceQuery).setOrgType("2")
    verify(deviceQuery).setMerNo("111111111111111")
    verify(deviceQuery).setTermNo("11111111")
    //验证“根据商户号、终端号、机构号和机构类型查询设备表”方法调用一次
    verify(bTermWorkKeyService.deviceDao, times(1)).findAll(deviceQuery)
    //验证根据厂商编号和设备sn查询设备方法调用一次
    verify(bTermWorkKeyService.deviceDao, times(1)).findOne(any(classOf[DevicePK]))
    //断言返回”1“-可以绑定
    mess shouldBe "1"
  }

  /**
    * 测试商户终端和设备绑定
    */
  "testBound" should "handle successful" in {
    bTermWorkKeyService.deviceDao = mock[DeviceDao]
    val newDevice = mock[Device]
    when(bTermWorkKeyService.deviceDao.findOne(any(classOf[DevicePK]))).thenReturn(newDevice)
    val device = mock[Device]
    when(device.getDevSn).thenReturn("111")
    when(device.getFirmCode).thenReturn("AAAA")
    when(device.getMerNo).thenReturn("111111111111111")
    when(device.getTermNo).thenReturn("11111111")
    //调用方法
    bTermWorkKeyService.bound(device)
    //验证调用一次device的获得sn、获得厂商编号、获得商户号和终端号
    verify(device, times(1)).getDevSn
    verify(device, times(1)).getFirmCode
    verify(device, times(1)).getMerNo
    verify(device, times(1)).getTermNo
    //验证调用一次设置商户号、终端号和是否绑定各一次
    verify(newDevice, times(1)).setMerNo("111111111111111")
    verify(newDevice, times(1)).setTermNo("11111111")
    verify(newDevice, times(1)).setStatus("1")
    //验证保存设备方法调用一次
    verify(bTermWorkKeyService.deviceDao, times(1)).saveAndFlush(any(classOf[Device]))
  }


  /**
    * 测试保存设备方法
    */
  "testRemoveBound" should "handle successful" in {
    bTermWorkKeyService.deviceDao = mock[DeviceDao]
    bTermWorkKeyService.appGroupDevDao = mock[AppGroupDevDao]
    bTermWorkKeyService.appGroupDao = mock[AppGroupDao]
    val device = mock[Device]
    when(device.getFirmCode).thenReturn("123")
    when(device.getDevSn).thenReturn("123")
    when(device.getMerNo).thenReturn("123")
    when(device.getTermNo).thenReturn("123")
    when(device.getTmkDek).thenReturn("123")
    when(device.getTmkKcv).thenReturn("123")
    when(device.getTmkLmk).thenReturn("123")
    when(device.getZekDek).thenReturn("123")
    when(device.getZekKcv).thenReturn("123")
    when(device.getZekLmk).thenReturn("123")
    when(bTermWorkKeyService.deviceDao.findOne(any(classOf[DeviceQuery]))).thenReturn(device)
    when(bTermWorkKeyService.appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(mock[AppGroup])
    //调用方法
    bTermWorkKeyService.removeBound(mock[DeviceQuery], "000", "2")
    //验证调用，清除数据
    verify(device, times(1)).setMerNo("")
    verify(device, times(1)).setTermNo("")
    verify(device, times(1)).setTmkDek("")
    verify(device, times(1)).setTmkKcv("")
    verify(device, times(1)).setTmkLmk("")
    verify(device, times(1)).setZekDek("")
    verify(device, times(1)).setZekKcv("")
    verify(device, times(1)).setZekLmk("")
    //验证设置绑定状态是0-未绑定
    verify(device, times(1)).setStatus("0")
    //验证调用一次保存方法
    verify(bTermWorkKeyService.deviceDao, times(1)).saveAndFlush(any(classOf[Device]))
    //验证调用一次查询默认分组方法
    verify(bTermWorkKeyService.appGroupDao, times(1)).findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //验证调用一次删除应用分组关联设备
    verify(bTermWorkKeyService.appGroupDevDao, times(1)).deleteByFirmCodeAndDevSn(any(classOf[String]), any(classOf[String]))
    //验证调用一次保存方法
    verify(bTermWorkKeyService.appGroupDevDao, times(1)).saveAndFlush(any(classOf[AppGroupDev]))
  }
}
