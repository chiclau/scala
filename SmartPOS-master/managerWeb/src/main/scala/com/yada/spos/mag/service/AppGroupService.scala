package com.yada.spos.mag.service

import java.util.Date

import com.yada.spos.db.dao._
import com.yada.spos.db.model._
import com.yada.spos.db.query.{AppGroupQuery, VAppGroupDeviceQuery}
import com.yada.spos.mag.service.ext.SystemHandler
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Scope, ScopedProxyMode}
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import scala.collection.convert.decorateAll._

/**
  * Created by pangChangSong on 2016/9/7.
  * 应用分组service
  */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
class AppGroupService {

  @Autowired
  var appGroupDao: AppGroupDao = _
  @Autowired
  var appGroupAppsDao: AppGroupAppsDao = _
  @Autowired
  var systemHandler: SystemHandler = _
  @Autowired
  var appFileLatestDao: AppFileLatestDao = _
  @Autowired
  var appGroupDevDao: AppGroupDevDao = _
  @Autowired
  var vAppGroupDeviceDao: VAppGroupDeviceDao = _
  @Autowired
  var appsAssociateAppGroupDao: AppsAssociateAppGroupDao = _
  @Autowired
  var appsUnAssociateAppGroupDao: AppsUnAssociateAppGroupDao = _

  /**
    * 查询所有
    *
    * @param query    用户分组查询类
    * @param pageable 分页
    * @return 用户分组分页对象
    */
  def findPage(query: AppGroupQuery, pageable: Pageable): Page[AppGroup] = {
    appGroupDao.findAll(query, pageable)
  }

  /**
    * 分组关联应用
    *
    * @param appName        应用名称
    * @param appPackageName 应用包名
    * @param orgId          机构id
    * @param orgType        机构类型
    * @param appGroupId     分组id
    */
  def associatedApp(appName: String, appPackageName: String, orgId: String, orgType: String, appGroupId: String): Unit = {
    val appGroup = appGroupDao.findOne(appGroupId.toLong)
    //插入应用分组关联应用
    saveAppGroupApps(appName, appPackageName, appGroup)
    if ((orgType == "1" && orgId == "00") || (orgType == "2" && orgId == "000")) {
      //总行
      //查询总行下的所有分行
      val orgs = systemHandler.getNextLevOrgs(orgId, orgType)
      //插入到所有的分行默认分组里
      orgs.asScala.foreach(f => {
        //查询默认分组
        val defaultAppGroup = appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup(f.orgId, f.orgType, "0")
        //插入应用分组关联应用
        saveAppGroupApps(appName, appPackageName, defaultAppGroup)
      })
    }
  }

  /**
    * 保存应用分组关联应用
    *
    * @param appName        应用名称
    * @param appPackageName 包名
    * @param appGroup       应用分组
    * @return AppGroupApps对象
    */
  private def saveAppGroupApps(appName: String, appPackageName: String, appGroup: AppGroup): AppGroupApps = {
    val appGroupApps = new AppGroupApps
    appGroupApps.setAppName(appName)
    appGroupApps.setAppGroup(appGroup)
    appGroupApps.setAppGroupName(appGroup.appGroupName)
    appGroupApps.setAppPackageName(appPackageName)
    appGroupAppsDao.saveAndFlush(appGroupApps)
  }

  def cancelAssociateApp(appPackageName: String, appGroupId: String, orgId: String, orgType: String): Unit = {
    if ((orgType == "1" && orgId == "00") || (orgType == "2" && orgId == "000")) {
      //总行
      val defaultAppGroupApp = appGroupAppsDao.findByAppPackageNameAndAppGroupIsDefaultGroupAndAppGroupOrgIdAndAppGroupOrgType(appPackageName, "0", orgId, orgType)
      appGroupAppsDao.delete(defaultAppGroupApp)
      appGroupAppsDao.flush()
      //删除分行默认分组相应的应用关联
      //查询总行下的所有分行
      val orgs = systemHandler.getNextLevOrgs(orgId, orgType)
      //插入到所有的分行默认分组里
      orgs.asScala.foreach(f => {
        //查询默认分组关联应用关系
        val branchDefaultAppGroup = appGroupAppsDao.findByAppPackageNameAndAppGroupIsDefaultGroupAndAppGroupOrgIdAndAppGroupOrgType(appPackageName, "0", f.orgId, f.orgType)
        appGroupAppsDao.delete(branchDefaultAppGroup)
        appGroupAppsDao.flush()
      })
    } else {
      //分行
      val appGroup = appGroupDao.findOne(appGroupId.toLong)
      val appGroupApps = appGroupAppsDao.findByAppPackageNameAndAppGroup(appPackageName, appGroup)
      appGroupAppsDao.delete(appGroupApps)
      appGroupAppsDao.flush()
    }
  }

  /**
    * 保存应用分组
    *
    * @param appGroup 应用分组
    * @param orgId    机构
    * @param orgType  机构类型
    */
  def save(appGroup: AppGroup, orgId: String, orgType: String): Unit = {
    appGroup.setOrgId(orgId)
    appGroup.setOrgType(orgType)
    appGroup.setIsDefaultGroup("1")
    appGroupDao.saveAndFlush(appGroup)
  }

  /**
    * 根据应用分组名称判断是否存在应用分组
    *
    * @param appGroupName 应用分组包名
    * @return true-存在/false-不存在
    */
  def isExistAppGroupByAppGroupName(appGroupName: String, orgId: String, orgType: String): Boolean = {
    val appGroups = appGroupDao.findByAppGroupNameAndOrgIdAndOrgType(appGroupName, orgId, orgType)
    appGroups == null || appGroups.isEmpty
  }

  /**
    * 分页查询所有关联应用分组的应用(取消关联时查询)
    *
    * @param appName    应用名称
    * @param creTime    上传时间
    * @param orgId      机构
    * @param orgType    机构类型
    * @param appGroupId 应用分组
    * @param pageable   分页
    * @return page对象
    */
  def findAllAppsAssociateAppGroup(appName: String, creTime: String, pageable: Pageable, orgId: String, orgType: String, appGroupId: String): Page[AppGroupApps] = {
    //查询应用分组下的 所有关联的应用
    appsUnAssociateAppGroupDao.findAll(orgId, orgType, appGroupId, appName, creTime, pageable)
  }

  /**
    * 分页查询所有未关联应用分组的应用(关联应用时查询)
    *
    * @param appName    应用名称
    * @param creTime    上传时间
    * @param orgId      机构
    * @param orgType    机构类型
    * @param appGroupId 应用分组
    * @param pageable   分页
    * @return page对象
    */
  def findAllAppsUnAssociateAppGroup(appName: String, creTime: String, pageable: Pageable, orgId: String, orgType: String, appGroupId: String): Page[AppGroupApps] = {
    appsAssociateAppGroupDao.findAll(orgId, orgType, appGroupId, appName, creTime, pageable)
  }

  /**
    * 分页查询所有关联默认应用分组的设备（关联设备时）
    *
    * @param associatedAVAppGroupDeviceQuery 应用分组关联设备视图查询类
    * @param pageable                        分页接口
    * @param orgId                           机构id
    * @param orgType                         机构类型
    * @return page对象
    */
  def findAllDevsAssociateDefAppGroup(associatedAVAppGroupDeviceQuery: VAppGroupDeviceQuery, pageable: Pageable, orgId: String, orgType: String): Page[VAppGroupDevice] = {
    //关联设备关联默认分组中的设备
    val appGroup = appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup(orgId, orgType, "0")
    associatedAVAppGroupDeviceQuery.setAppGroupId(appGroup.getAppGroupId.toString)
    //设置终端sn绑定状态是已绑定
    associatedAVAppGroupDeviceQuery.setStatus("1")
    //分页查询
    vAppGroupDeviceDao.findAll(associatedAVAppGroupDeviceQuery, pageable)
  }

  /**
    * 分页查询所有关联应用分组的设备（取消关联）
    *
    * @param unAssociatedVAppGroupDeviceQuery 应用分组关联设备查询的query
    * @param pageable                         分页接口
    * @param appGroupId                       机构分组Id
    * @return page对象
    */
  def findAllDevsAssociateAppGroup(unAssociatedVAppGroupDeviceQuery: VAppGroupDeviceQuery, pageable: Pageable, appGroupId: String): Page[VAppGroupDevice] = {
    //查询应用分组下的所有应用分组关联设备
    unAssociatedVAppGroupDeviceQuery.setAppGroupId(appGroupId)
    vAppGroupDeviceDao.findAll(unAssociatedVAppGroupDeviceQuery, pageable)
  }

  /**
    * 应用分组关联设备
    *
    * @param firmCode   厂商编号
    * @param devSn      设备sn
    * @param appGroupId 应用分组id
    */
  def associateDev(firmCode: String, devSn: String, appGroupId: String): Unit = {
    //当前时间
    val curTime = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", new Date)
    //删除默认分组关联设备（因为设备和应用分组是多对一，所以只根据厂商编号和设备sn删除就可以）
    val result = appGroupDevDao.deleteByFirmCodeAndDevSn(firmCode, devSn)
    if (result > 0) {
      val appGroup = appGroupDao.findOne(appGroupId.toLong)
      val appGroupDev = new AppGroupDev
      appGroupDev.setDevSN(devSn)
      appGroupDev.setFirmCode(firmCode)
      appGroupDev.setAppGroup(appGroup)
      appGroupDev.setAvailable("1") //有效性默认是1
      appGroupDev.setCreTime(curTime)
      appGroupDev.setUpdTime(curTime)
      appGroupDevDao.saveAndFlush(appGroupDev)
    } else {
      throw new RuntimeException(s"厂商[$firmCode]sn[$devSn]关联应用时，删除设备和默认分组的关联关系失败")
    }

  }

  /**
    * 取消应用分组关联设备
    *
    * @param firmCode 厂商编号
    * @param devSn    设备sn
    * @param orgId    机构号
    * @param orgType  机构类型
    */
  def unAssociateDev(firmCode: String, devSn: String, orgId: String, orgType: String): Unit = {
    //根据厂商编号和设备sn查询应用分组关联应用
    val appGroupDev = appGroupDevDao.findByDevSNAndFirmCode(devSn, firmCode)
    appGroupDevDao.delete(appGroupDev)
    appGroupDevDao.flush()
    //重新将设备放到默认分组里
    val appGroupDevNew = new AppGroupDev
    BeanUtils.copyProperties(appGroupDev, appGroupDevNew, "devAppGroupId")
    //查询默认分组
    val defAppGroup = appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup(orgId, orgType, "0")
    appGroupDevNew.setAppGroup(defAppGroup)
    appGroupDevDao.saveAndFlush(appGroupDevNew)
  }

  /**
    * 根据id查询分组
    *
    * @param appGroupId id
    * @return
    */
  def findOne(appGroupId: String): AppGroup = {
    appGroupDao.findOne(appGroupId.toLong)
  }

  /**
    * 更新应用分组
    *
    * @param appGroupId   应用分组id
    * @param appGroupName 应用分组名称
    * @param appGroupDesc 应用分组描述
    */
  def updateAppGroup(appGroupId: String, appGroupName: String, appGroupDesc: String): Unit = {
    //更新应用分组的名称和应用分组描述
    val result = appGroupDao.updateAppGroup(appGroupId, appGroupName, appGroupDesc)
    if (result > 0) {
      //更新应用分组关联应用的应用分组名称
      appGroupAppsDao.updateAppGroupName(appGroupId, appGroupName)
    }
  }

}
