package com.yada.spos.mag.service

import java.io.InputStream

import com.yada.spos.db.dao.AuthDeviceDao
import com.yada.spos.db.model.AuthDevice
import com.yada.spos.db.query.AuthDeviceQuery
import com.yada.spos.mag.service.ext.AuthDeviceHandler
import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile

/**
  * Created by pangChangSong on 2016/9/23.
  * 母POSservice测试
  */
@RunWith(classOf[JUnitRunner])
class AuthDeviceServiceTest extends FlatSpec with Matchers with MockitoSugar {

  val authDeviceService = new AuthDeviceService

  /**
    * 分页查询查询所有
    */
  "testFindAll" should "handle successful" in {
    authDeviceService.authDeviceDao = mock[AuthDeviceDao]
    //调用方法
    authDeviceService.findAll(mock[AuthDeviceQuery], mock[Pageable])
    //验证调用一次查询所有方法
    verify(authDeviceService.authDeviceDao, times(1)).findAll(any(classOf[AuthDeviceQuery]), any(classOf[Pageable]))
  }

  /**
    * 测试查询一个方法
    */
  "testFindOne" should "handle successful" in {
    authDeviceService.authDeviceDao = mock[AuthDeviceDao]
    //调用方法
    authDeviceService.findOne("112")
    //验证调用一次查询一个方法
    verify(authDeviceService.authDeviceDao, times(1)).findOne("112")
  }

  /**
    * 测试保存或更新母pos方法，会首先获得证书的公钥，然后设置值保存
    */
  "testSaveAndUpdate" should "handle successful" in {
    authDeviceService.authDeviceDao = mock[AuthDeviceDao]
    authDeviceService.authDeviceHandler = mock[AuthDeviceHandler]
    //当调用获得证书的公钥的方法是返回12345566
    when(authDeviceService.authDeviceHandler.getPublicKey(mock[InputStream], "123456")).thenReturn("12345566")
    val file = mock[MultipartFile]
    when(file.getInputStream).thenReturn(mock[InputStream])
    //调用方法
    authDeviceService.saveAndUpdate("111", file, "000", "2", "123456")
    //验证调用一次file.getInputStream方法
    verify(file, times(1)).getInputStream
    //验证调用saveAndFlush保存方法
    verify(authDeviceService.authDeviceDao, times(1)).saveAndFlush(any(classOf[AuthDevice]))
  }

}
