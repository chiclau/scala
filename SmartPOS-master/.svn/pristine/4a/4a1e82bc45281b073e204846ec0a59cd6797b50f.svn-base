//package com.yada.spos.heart.rs
//
//import com.yada.spos.common.SignValidate
//import com.yada.spos.db.dao.{DeviceDao, HeartLogDao, HeartLogDetailDao}
//import com.yada.spos.db.model.HeartLogPK
//import com.yada.spos.db.model.{Device, DevicePK, HeartLog, HeartLogDetail}
//import com.yada.spos.heart.biz.HeartBiz
//import org.junit.runner.RunWith
//import org.mockito.Matchers.any
//import org.scalatest.junit.JUnitRunner
//import org.scalatest.mockito.MockitoSugar
//import org.scalatest.{FlatSpec, Matchers}
//import org.mockito.Mockito._
//
//import scala.collection.immutable.TreeMap
//
///**
//  * Created by pangChangSong on 2016/9/7.
//  * 心跳业务处理测试
//  */
//@RunWith(classOf[JUnitRunner])
//class HeartBizTest extends FlatSpec with Matchers with MockitoSugar {
//
//  val heart = new Heart
//
//
//  /**
//    * 测试业务处理，并且成功（数据库中有心跳信息数据时，先删除在插入）
//    */
//  "testHandleAndSuccess" should "handle successful" in {
//    heart.deviceDao = mock[DeviceDao]
//    heart.heartLogDao = mock[HeartLogDao]
//    heart.heartLogDetailDao = mock[HeartLogDetailDao]
//    heart.signValidate = mock[SignValidate]
//    when(heart.signValidate.handle(any(classOf[String]), any(classOf[TreeMap[String, String]]), any(classOf[String]))).thenReturn(true)
//    val device = new Device
//    device.prodCode = "123455"
//    device.zekLmk = "11111111111111111111111111111"
//    when(heart.deviceDao.findOne(any(classOf[DevicePK]))).thenReturn(device)
//    val heartLog = new HeartLog
//    heartLog.devSn = "123"
//    when(heart.heartLogDao.findOne(any(classOf[HeartLogPK]))).thenReturn(heartLog)
//    //验证返回成功
//    val req = HeartReq("yada", "assa")
//    val resp = heart.handle("12", "AAAA", "123", "20160830140922", "123456789012345678901234", req)
//    resp.retCode shouldBe "00"
//    resp.retMsg shouldBe None
//    //验证saveAndFlush方法调用一次
//    verify(heart.deviceDao, times(1)).findOne(any(classOf[DevicePK]))
//    verify(heart.signValidate, times(1)).handle(any(classOf[String]), any(classOf[TreeMap[String, String]]), any(classOf[String]))
//    verify(heart.heartLogDao, times(1)).findOne(any(classOf[HeartLogPK]))
//    verify(heart.heartLogDao, times(1)).saveAndFlush(any(classOf[HeartLog]))
//    verify(heart.heartLogDao, times(1)).delete(any(classOf[HeartLog]))
//    verify(heart.heartLogDao, times(1)).flush()
//    verify(heart.heartLogDetailDao, times(1)).saveAndFlush(any(classOf[HeartLogDetail]))
//  }
//
//  /**
//    * 测试业务处理，并且成功（数据库中无数据）
//    * 数据库中无数据直接插入
//    */
//  "testHandleAndSuccess1" should "handle successful" in {
//    heart.deviceDao = mock[DeviceDao]
//    heart.heartLogDao = mock[HeartLogDao]
//    heart.heartLogDetailDao = mock[HeartLogDetailDao]
//    heart.signValidate = mock[SignValidate]
//    when(heart.signValidate.handle(any(classOf[String]), any(classOf[TreeMap[String, String]]), any(classOf[String]))).thenReturn(true)
//    val device = new Device
//    device.prodCode = "123455"
//    device.zekLmk = "11111111111111111111111111111"
//    when(heart.deviceDao.findOne(any(classOf[DevicePK]))).thenReturn(device)
//    val heartLog = new HeartLog
//    heartLog.devSn = "123"
//    when(heart.heartLogDao.findOne(any(classOf[HeartLogPK]))).thenReturn(heartLog)
//    //验证返回成功
//    val req = HeartReq("yada", "assa")
//    val resp = heart.handle("12", "AAAA", "123", "20160830140922", "123456789012345678901234", req)
//    resp.retCode shouldBe "00"
//    resp.retMsg shouldBe None
//    //验证方法调用一次
//    verify(heart.deviceDao).findOne(any(classOf[DevicePK]))
//    verify(heart.signValidate).handle(any(classOf[String]), any(classOf[TreeMap[String, String]]), any(classOf[String]))
//    verify(heart.heartLogDao).findOne(any(classOf[HeartLogPK]))
//    verify(heart.heartLogDao).saveAndFlush(any(classOf[HeartLog]))
//    verify(heart.heartLogDetailDao).saveAndFlush(any(classOf[HeartLogDetail]))
//  }
//
//  /**
//    * 测试业务处理，并且未查询到设备,直接返回01
//    */
//  "testHandleAndNotFindDevice" should "handle successful" in {
//    heart.deviceDao = mock[DeviceDao]
//    when(heart.deviceDao.findOne(any(classOf[DevicePK]))).thenReturn(null)
//    val req = HeartReq("yada", "assa")
//    val resp = heart.handle("12", "AAAA", "123", "20160830140922", "123456789012345678901234", req)
//    resp.retCode shouldBe "01"
//    resp.retMsg shouldBe Some("device not exists")
//    //验证方法调用一次
//    verify(heart.deviceDao).findOne(any(classOf[DevicePK]))
//  }
//
//  /**
//    * 测试业务处理，并且签名失败
//    */
//  "testHandleAndNotSignFail" should "handle successful" in {
//    heart.deviceDao = mock[DeviceDao]
//    heart.signValidate = mock[SignValidate]
//    when(heart.deviceDao.findOne(any(classOf[DevicePK]))).thenReturn(mock[Device])
//    when(heart.signValidate.handle(any(classOf[String]), any(classOf[TreeMap[String, String]]), any(classOf[String]))).thenReturn(false)
//    val device = new Device
//    device.prodCode = "123455"
//    device.zekLmk = "11111111111111111111111111111"
//    when(heart.deviceDao.findOne(any(classOf[DevicePK]))).thenReturn(device)
//    val req = HeartReq("yada", "assa")
//    val resp = heart.handle("12", "AAAA", "123", "20160830140922", "123456789012345678901234", req)
//    resp.retCode shouldBe "01"
//    resp.retMsg shouldBe Some("sign error")
//    //验证方法调用一次
//    verify(heart.deviceDao).findOne(any(classOf[DevicePK]))
//    verify(heart.signValidate).handle(any(classOf[String]), any(classOf[TreeMap[String, String]]), any(classOf[String]))
//  }
//}
