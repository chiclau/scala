package com.yada.spos.mag.service

import com.yada.spos.db.dao.FirmDao
import com.yada.spos.db.model.Firm
import com.yada.spos.db.query.FirmQuery
import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.springframework.data.domain.Pageable

/**
  * Created by pangChangSong on 2016/9/24.
  * 厂商service测试
  */
@RunWith(classOf[JUnitRunner])
class FirmServiceTest extends FlatSpec with Matchers with MockitoSugar {

  val firmService = new FirmService

  /**
    * 测试保存厂商方法
    */
  "testSave" should "handle successful" in {
    firmService.firmDao = mock[FirmDao]
    //调用方法
    firmService.save(mock[Firm])
    //验证调用一次保存方法
    verify(firmService.firmDao, times(1)).saveAndFlush(any(classOf[Firm]))
  }

  /**
    * 分页查询所有
    */
  "testFindPage" should "handle successful" in {
    firmService.firmDao = mock[FirmDao]
    //调用方法
    firmService.findPage(mock[FirmQuery], mock[Pageable])
    //验证调用一次分页查询全部方法
    verify(firmService.firmDao, times(1)).findAll(any(classOf[FirmQuery]), any(classOf[Pageable]))
  }

  /**
    * 测试根据厂商编号查询一个
    */
  "testFindOne" should "handle successful" in {
    firmService.firmDao = mock[FirmDao]
    //调用方法
    firmService.findOne("AAAA")
    //验证调用一次查询一个方法
    verify(firmService.firmDao, times(1)).findOne("AAAA")
  }

  /**
    * 测试根据厂商编号查询一个
    */
  "testFindAll" should "handle successful" in {
    firmService.firmDao = mock[FirmDao]
    //调用方法
    firmService.findAll()
    //验证调用一次查询全部方法
    verify(firmService.firmDao, times(1)).findAll()
  }

}
