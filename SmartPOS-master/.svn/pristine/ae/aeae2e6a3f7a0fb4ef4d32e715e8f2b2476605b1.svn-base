package com.yada.spos.mag.service

import com.yada.spos.db.dao._
import com.yada.spos.db.model._
import com.yada.spos.db.query.{AppGroupQuery, VAppGroupDeviceQuery}
import com.yada.spos.mag.service.ext.{OrgInfo, SystemHandler}
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.data.domain.Pageable

import scala.collection.convert.decorateAll._

/**
  * Created by pangChangSong on 2016/9/17.
  * 应用分组管理测试
  */
@RunWith(classOf[JUnitRunner])
class AppGroupServiceTest extends FlatSpec with Matchers with MockitoSugar {

  val appGroupService = new AppGroupService

  /**
    * 分页查询测试
    */
  "testFindPage" should "handle successful" in {
    //mock对象
    appGroupService.appGroupDao = mock[AppGroupDao]
    //调用方法
    appGroupService.findPage(any(classOf[AppGroupQuery]), any(classOf[Pageable]))
    //验证查询一次findAll方法
    verify(appGroupService.appGroupDao, times(1)).findAll(any(classOf[AppGroupQuery]), any(classOf[Pageable]))
  }

  /**
    * 测试应用分组关联应用（总行）
    * 总行只有默认分组，总行关联应用要给分行的默认分组也关联应用
    */
  "testAssociatedApp1" should "handle successful" in {
    //mock对象
    appGroupService.appGroupDao = mock[AppGroupDao]
    appGroupService.appGroupAppsDao = mock[AppGroupAppsDao]
    appGroupService.systemHandler = mock[SystemHandler]
    val appGroup = new AppGroup
    appGroup.setAppGroupName("默认分组")
    //根据应用分组id查询应用分组
    when(appGroupService.appGroupDao.findOne(any(classOf[Long]))).thenReturn(appGroup)
    //总行查询下一级的所有机构
    when(appGroupService.systemHandler.getNextLevOrgs(any(classOf[String]), any(classOf[String]))).thenReturn(getOrgInfo)
    //查询默认分组
    when(appGroupService.appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(appGroup)

    //调用方法
    appGroupService.associatedApp("qq", "qq.com", "000", "2", "1")
    //验证根据应用分组id查询应用分组查询一次
    verify(appGroupService.appGroupDao, times(1)).findOne(any(classOf[Long]))
    //验证”总行查询下一级的所有机构“操作一次
    verify(appGroupService.systemHandler, times(1)).getNextLevOrgs(any(classOf[String]), any(classOf[String]))
    //由于总行下有两个分行，所以”查询默认分组“操作2次
    verify(appGroupService.appGroupDao, times(2)).findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //保存应用分组关联设备实体3次，总行和两个分行分别1次
    verify(appGroupService.appGroupAppsDao, times(3)).saveAndFlush(any(classOf[AppGroupApps]))
  }

  /**
    * 测试应用分组关联应用（分行）
    * 分行应用分组关联应用只关联自己，不会查询下级机构
    */
  "testAssociatedApp2" should "handle successful" in {
    //mock对象
    appGroupService.appGroupDao = mock[AppGroupDao]
    appGroupService.appGroupAppsDao = mock[AppGroupAppsDao]
    appGroupService.systemHandler = mock[SystemHandler]
    val appGroup = new AppGroup
    appGroup.setAppGroupName("默认分组")
    //根据应用分组id查询应用分组
    when(appGroupService.appGroupDao.findOne(any(classOf[Long]))).thenReturn(appGroup)
    //调用方法
    appGroupService.associatedApp("qq", "qq.com", "00011", "2", "1")
    //验证根据应用分组id查询应用分组查询一次
    verify(appGroupService.appGroupDao, times(1)).findOne(any(classOf[Long]))
    //保存应用分组关联设备实体1次，分行只插入自己的
    verify(appGroupService.appGroupAppsDao, times(1)).saveAndFlush(any(classOf[AppGroupApps]))
    //验证”总行查询下一级的所有机构“操作没有进行，分行值操作自己
    verify(appGroupService.systemHandler, never()).getNextLevOrgs(any(classOf[String]), any(classOf[String]))
    //验证”查询默认分组“操作没有进行，分行不能关联默认分组，因为分行看不见默认分组，但是可以关联其他的分组
    verify(appGroupService.appGroupDao, never()).findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String]), any(classOf[String]), any(classOf[String]))
  }

  /**
    * 测试应用分组应用取消关联应用（总行）
    * 总行应用分组取消关联应用，由于总行只有默认分组，总行应用分组取消关联应用，那么相应的分行默认分组也要取消关联这个应用
    */
  "testCancelAssociateApp1" should "handle successful" in {
    //mock对象
    appGroupService.appGroupDao = mock[AppGroupDao]
    appGroupService.appGroupAppsDao = mock[AppGroupAppsDao]
    appGroupService.systemHandler = mock[SystemHandler]
    //根据包名查询总行默认分组关联应用
    when(appGroupService.appGroupAppsDao.findByAppPackageNameAndAppGroupIsDefaultGroupAndAppGroupOrgIdAndAppGroupOrgType(any(classOf[String]),
      any(classOf[String]), any(classOf[String]), any(classOf[String]))).thenReturn(mock[AppGroupApps])
    //总行查询下一级的所有机构
    when(appGroupService.systemHandler.getNextLevOrgs(any(classOf[String]), any(classOf[String]))).thenReturn(getOrgInfo)
    //调用方法
    appGroupService.cancelAssociateApp("qq.com", "1", "000", "2")
    //验证”总行查询下一级的所有机构“方法调用一次,查询出两个机构
    verify(appGroupService.systemHandler, times(1)).getNextLevOrgs(any(classOf[String]), any(classOf[String]))
    //验证“根据包名查询总行默认分组关联应用”方法调用三次，总行一次，两个分行各一次
    verify(appGroupService.appGroupAppsDao, times(3)).findByAppPackageNameAndAppGroupIsDefaultGroupAndAppGroupOrgIdAndAppGroupOrgType(any(classOf[String]),
      any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //验证删除默认分组关联应用方法调用三次（总行一次，分行二次）
    verify(appGroupService.appGroupAppsDao, times(3)).delete(any(classOf[AppGroupApps]))
    //验证删除后刷新方法调用三次（总行一次，分行二次）
    verify(appGroupService.appGroupAppsDao, times(3)).flush()

    //从未调用方法,分行调用的方法
    // 根据应用分组id查询出应用分组
    verify(appGroupService.appGroupDao, never()).findOne(any(classOf[Long]))
    //根据应用分组和包名查询应用分组关联应用
    verify(appGroupService.appGroupAppsDao, never()).findByAppPackageNameAndAppGroup(any(classOf[String]), any(classOf[AppGroup]))
  }

  /**
    * 测试应用分组应用取消关联应用（分行）
    * 分行取消关联只取消自己分组下的应用
    */
  "testCancelAssociateApp2" should "handle successful" in {
    //mock对象
    appGroupService.appGroupDao = mock[AppGroupDao]
    appGroupService.appGroupAppsDao = mock[AppGroupAppsDao]
    appGroupService.systemHandler = mock[SystemHandler]
    //根据应用分组id查询应用分组
    when(appGroupService.appGroupDao.findOne(any(classOf[Long]))).thenReturn(mock[AppGroup])
    //根据应用分组和包名查询应用分组关联应用
    when(appGroupService.appGroupAppsDao.findByAppPackageNameAndAppGroup(any(classOf[String]), any(classOf[AppGroup]))).thenReturn(mock[AppGroupApps])
    //调用方法
    appGroupService.cancelAssociateApp("qq.com", "1", "00011", "2")

    //验证“根据应用分组id查询应用分组”方法调用一次
    verify(appGroupService.appGroupDao, times(1)).findOne(any(classOf[Long]))
    //验证“根据应用分组和包名查询应用分组关联应用”方法调用一次
    verify(appGroupService.appGroupAppsDao, times(1)).findByAppPackageNameAndAppGroup(any(classOf[String]), any(classOf[AppGroup]))
    //验证“根据删除应用分组关联应用”方法调用一次
    verify(appGroupService.appGroupAppsDao, times(1)).delete(any(classOf[AppGroupApps]))
    //验证“刷新”方法调用一次
    verify(appGroupService.appGroupAppsDao, times(1)).flush()

    //验证未调用方法，总行调用的
    //验证”总行查询下一级的所有机构“方法从未调用
    verify(appGroupService.systemHandler, never()).getNextLevOrgs(any(classOf[String]), any(classOf[String]))
    //验证“根据包名查询总行默认分组关联应用”方法从未调用
    verify(appGroupService.appGroupAppsDao, never()).findByAppPackageNameAndAppGroupIsDefaultGroupAndAppGroupOrgIdAndAppGroupOrgType(any(classOf[String]),
      any(classOf[String]), any(classOf[String]), any(classOf[String]))
  }

  /**
    * 测试保存（新增应用分组）
    */
  "testSave" should "handle successful" in {
    //mock对象
    appGroupService.appGroupDao = mock[AppGroupDao]
    val appGroup = mock[AppGroup]
    //调用方法
    appGroupService.save(appGroup, "000", "2")
    //验证设置机构、机构类型、和是否默认分组各调用一次
    verify(appGroup).setOrgId(any(classOf[String]))
    verify(appGroup).setOrgType(any(classOf[String]))
    verify(appGroup).setIsDefaultGroup(any(classOf[String]))
    //验证保存应用分组调用一次
    verify(appGroupService.appGroupDao).saveAndFlush(any(classOf[AppGroup]))
  }

  /**
    * 测试应用分组名称是否唯一(唯一，数据库里没有，可以新增)
    */
  "testIsExistAppGroupByAppGroupName1" should "handle successful" in {
    //mock对象
    appGroupService.appGroupDao = mock[AppGroupDao]
    //根据应用分组名称、机构和机构类型查询应用分组
    when(appGroupService.appGroupDao.findByAppGroupNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String])))
      .thenReturn(null)
    val result = appGroupService.isExistAppGroupByAppGroupName(any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //断言返回值是true
    result shouldBe true
    //验证”根据应用分组名称、机构和机构类型查询应用分组“被调用一次
    verify(appGroupService.appGroupDao).findByAppGroupNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]))
  }

  /**
    * 测试应用分组名称是否唯一(不唯一，数据库里有数据，不能新增)
    */
  "testIsExistAppGroupByAppGroupName2" should "handle successful" in {
    //mock对象
    appGroupService.appGroupDao = mock[AppGroupDao]
    val appGroup = new AppGroup
    appGroup.setAppGroupName("分组")
    appGroup.setOrgId("000")
    appGroup.setOrgType("2")
    //根据应用分组名称、机构和机构类型查询应用分组
    when(appGroupService.appGroupDao.findByAppGroupNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String])))
      .thenReturn(List(appGroup).asJava)
    val result = appGroupService.isExistAppGroupByAppGroupName(any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //断言返回值是false
    result shouldBe false
    //验证”根据应用分组名称、机构和机构类型查询应用分组“被调用一次
    verify(appGroupService.appGroupDao).findByAppGroupNameAndOrgIdAndOrgType(any(classOf[String]), any(classOf[String]), any(classOf[String]))
  }

  /**
    * 分页查询所有关联应用分组的应用
    *
    */
  "testFindAllAppsAssociateAppGroup" should "handle successful" in {
    //mock对象
    appGroupService.appsUnAssociateAppGroupDao = mock[AppsUnAssociateAppGroupDao]
    //调用方法
    appGroupService.findAllAppsAssociateAppGroup("qq", "20161019", mock[Pageable], "000011", "2", "225")
    //验证调用一次分页查询方法
    verify(appGroupService.appsUnAssociateAppGroupDao, times(1)).findAll(any(classOf[String]), any(classOf[String]), any(classOf[String]), any(classOf[String]), any(classOf[String])
      , any(classOf[Pageable]))
  }

  /**
    * 分页查询所有未关联应用分组的应用
    */
  "testFindAllAppsUnAssociateAppGroup" should "handle successful" in {
    //mock对象
    appGroupService.appsAssociateAppGroupDao = mock[AppsAssociateAppGroupDao]
    //调用方法
    val result = appGroupService.findAllAppsUnAssociateAppGroup("qq", "20161019", mock[Pageable], "000011", "2", "225")
    //验证调用一次分页查询方法
    verify(appGroupService.appsAssociateAppGroupDao, times(1)).findAll(any(classOf[String]), any(classOf[String]), any(classOf[String]), any(classOf[String]), any(classOf[String])
      , any(classOf[Pageable]))
  }

  /**
    * 分页查询所有关联默认应用分组的设备
    *
    */
  "testFindAllDevsAssociateDefAppGroup" should "handle successful" in {
    //mock对象
    appGroupService.vAppGroupDeviceDao = mock[VAppGroupDeviceDao]
    appGroupService.appGroupDao = mock[AppGroupDao]
    //根据机构号、机构类型和默认分组查询应用分组
    val appGroup = mock[AppGroup]
    when(appGroupService.appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup("000011", "2", "0")).thenReturn(appGroup)
    val vappGroupDeviceQuery = mock[VAppGroupDeviceQuery]
    //调用方法，如果没有查询到应用分组关联设备，则机构下的所有关联商户、终端的设备都可以关联
    appGroupService.findAllDevsAssociateDefAppGroup(vappGroupDeviceQuery, mock[Pageable], "000011", "2")
    //根据机构号、机构类型和默认分组查询应用分组
    verify(appGroupService.appGroupDao, times(1)).findByOrgIdAndOrgTypeAndIsDefaultGroup("000011", "2", "0")
    //验证vappGroupDeviceQuery设置应用分组id和状态
    verify(vappGroupDeviceQuery, times(1)).setStatus(any(classOf[String]))
    verify(vappGroupDeviceQuery, times(1)).setAppGroupId(any(classOf[String]))
    //验证分页查询一次
    verify(appGroupService.vAppGroupDeviceDao, times(1)).findAll(any(classOf[VAppGroupDeviceQuery]), any(classOf[Pageable]))
  }

  /**
    * 分页查询所有关联应用分组的设备
    */
  "testFindAllDevsAssociateAppGroup1" should "handle successful" in {
    //mock对象
    appGroupService.vAppGroupDeviceDao = mock[VAppGroupDeviceDao]
    //调用方法
    val vappGroupDeviceQuery = mock[VAppGroupDeviceQuery]
    appGroupService.findAllDevsAssociateAppGroup(vappGroupDeviceQuery, mock[Pageable], "1")
    //验证从为调用
    //appGroupDevQuery设置应用分组id
    verify(vappGroupDeviceQuery, times(1)).setAppGroupId(any(classOf[String]))
    //分页查询方法
    verify(appGroupService.vAppGroupDeviceDao, times(1)).findAll(any(classOf[VAppGroupDeviceQuery]), any(classOf[Pageable]))
  }

  /**
    * 应用分组关联设备
    */
  "associateDev" should "handle successful" in {
    //mock对象
    appGroupService.appGroupDevDao = mock[AppGroupDevDao]
    appGroupService.appGroupDao = mock[AppGroupDao]
    //根据应用分组id查询应用分组
    when(appGroupService.appGroupDao.findOne(any(classOf[java.lang.Long]))).thenReturn(mock[AppGroup])
    //根据厂商编号和sn删除应用关联设备
    when(appGroupService.appGroupDevDao.deleteByFirmCodeAndDevSn("AAAA", "aaaa")).thenReturn(1)
    //调用方法
    appGroupService.associateDev("AAAA", "aaaa", "1")
    //验证调用一次“删除默认分组关联设备（因为设备和应用分组是多对一，所以只根据厂商编号和设备sn删除就可以）”
    verify(appGroupService.appGroupDevDao, times(1)).deleteByFirmCodeAndDevSn("AAAA", "aaaa")
    //验证调用一次“根据应用分组id查询应用分组”
    verify(appGroupService.appGroupDao, times(1)).findOne(any(classOf[java.lang.Long]))
    //验证调用一次“保存应用分组关联设备”
    verify(appGroupService.appGroupDevDao, times(1)).saveAndFlush(any(classOf[AppGroupDev]))
  }

  /**
    * 取消应用分组关联设备
    * 删除根据设备sn和厂商编号查询应用分组关联设备
    */
  "unAssociateDev" should "handle successful" in {
    //mock对象
    appGroupService.appGroupDevDao = mock[AppGroupDevDao]
    appGroupService.appGroupDao = mock[AppGroupDao]
    //根据设备sn和厂商编号查询应用分组关联设备
    when(appGroupService.appGroupDevDao.findByDevSNAndFirmCode(any(classOf[String]), any(classOf[String]))).thenReturn(mock[AppGroupDev])
    //查询默认分组
    val defAppGroup = mock[AppGroup]
    when(appGroupService.appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String]), any(classOf[String]), any(classOf[String])))
      .thenReturn(defAppGroup)
    //调用方法
    appGroupService.unAssociateDev("aaaa", "1111111", "000011", "2")
    //验证调用一次“根据设备sn和厂商编号查询应用分组关联设备”
    verify(appGroupService.appGroupDevDao).findByDevSNAndFirmCode(any(classOf[String]), any(classOf[String]))
    //验证调用一次“删除应用分组关联设备”
    verify(appGroupService.appGroupDevDao).delete(any(classOf[AppGroupDev]))
    //验证调用一次"刷新方法"
    verify(appGroupService.appGroupDevDao).flush()
    //验证调用一次"查询默认分组方法"
    verify(appGroupService.appGroupDao).findByOrgIdAndOrgTypeAndIsDefaultGroup(any(classOf[String]), any(classOf[String]), any(classOf[String]))
    //验证调用一次”保存方法“
    verify(appGroupService.appGroupDevDao).saveAndFlush(any(classOf[AppGroupDev]))
  }

  "testUpdateAppGroup" should "handle successful" in {
    //mock对象
    appGroupService.appGroupAppsDao = mock[AppGroupAppsDao]
    appGroupService.appGroupDao = mock[AppGroupDao]
    //更新应用分组
    when(appGroupService.appGroupDao.updateAppGroup("1", "应用分组1", "112")).thenReturn(1)
    //调用方法
    appGroupService.updateAppGroup("1", "应用分组1", "112")
    //验证调用一次更新应用分组
    verify(appGroupService.appGroupDao).updateAppGroup("1", "应用分组1", "112")
    //验证调用一次更新应用分组关联应用的应用分组名称
    verify(appGroupService.appGroupAppsDao).updateAppGroupName("1", "应用分组1")
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
