package com.yada.spos.mag.service

import com.yada.spos.db.dao.{FirmDao, OnlineParamDao}
import com.yada.spos.db.model.OnlineParam
import com.yada.spos.db.query.OnlineParamQuery
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.data.domain.Pageable

/**
  * Created by pangChangSong on 2016/9/25.
  * 设备参数维护service测试
  */
@RunWith(classOf[JUnitRunner])
class OnlineParamServiceTest extends FlatSpec with Matchers with MockitoSugar {

  val onlineParamService = new OnlineParamService

  /**
    * 测试保存设备参数方法
    */
  "testSaveOrUpdate" should "handle successful" in {
    onlineParamService.onlineParamDao = mock[OnlineParamDao]
    //调用方法
    onlineParamService.saveOrUpdate(mock[OnlineParam])
    //验证调用一次保存方法
    verify(onlineParamService.onlineParamDao, times(1)).saveAndFlush(any(classOf[OnlineParam]))
  }

  /**
    * 分页查询所有
    */
  "testFindPage" should "handle successful" in {
    onlineParamService.onlineParamDao = mock[OnlineParamDao]
    //调用方法
    onlineParamService.findPage(mock[OnlineParamQuery], mock[Pageable])
    //验证调用一次分页查询全部方法
    verify(onlineParamService.onlineParamDao, times(1)).findAll(any(classOf[OnlineParamQuery]), any(classOf[Pageable]))
  }

  /**
    * 测试根据设备参数id查询一个设备参数
    */
  "testFindById" should "handle successful" in {
    onlineParamService.onlineParamDao = mock[OnlineParamDao]
    //调用方法
    onlineParamService.findById("1111")
    //验证调用一次查询一个方法
    verify(onlineParamService.onlineParamDao, times(1)).findOne("1111".toLong)
  }

  /**
    * 测试根据机构类型，机构id,参数名称查询
    */
  "testFindByOrgIdAndOrgTypeAndParamName" should "handle successful" in {
    onlineParamService.onlineParamDao = mock[OnlineParamDao]
    //调用方法
    onlineParamService.findByOrgIdAndOrgTypeAndParamName("000", "2", "121212")
    //验证调用一次根据机构类型，机构id,参数名称查询
    verify(onlineParamService.onlineParamDao, times(1)).findByOrgIdAndOrgTypeAndParamName("000", "2", "121212")
  }
}
