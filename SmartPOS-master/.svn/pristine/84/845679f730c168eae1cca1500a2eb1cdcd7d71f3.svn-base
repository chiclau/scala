package com.yada.spos.mag.controller

import com.typesafe.config.ConfigFactory
import com.yada.spos.db.model.AppGroup
import com.yada.spos.db.query.{AppGroupQuery, VAppGroupDeviceQuery}
import com.yada.spos.mag.core.shiro.ShiroComponent
import com.yada.spos.mag.service.AppGroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

/**
  * Created by pangChangSong on 2016/9/7.
  * 应用分组管理Controller
  */
@Controller
@RequestMapping(Array("appGroup"))
class AppGroupController {

  @Autowired
  var appGroupService: AppGroupService = _
  @Autowired
  var shiroComponent: ShiroComponent = _

  val orgType = ConfigFactory.load().getString("orgType")

  /**
    * 菜单点击默认跳到空页面
    *
    * @return
    */
  @RequestMapping(value = Array("emptyList.do"))
  def emptyList(model: Model): String = {
    val orgId = shiroComponent.findUser.orgID
    model.addAttribute("orgId", orgId)
    model.addAttribute("orgType", orgType)
    model.addAttribute("appGroupQuery", new AppGroupQuery)
    "appGroup/list"
  }

  /**
    * 分页查询
    *
    * @param model            model
    * @param appGroupQuery    分组的查询类
    * @param appGroupPageable 分页
    */
  @RequestMapping(value = Array("list.do"))
  def list(model: Model, appGroupQuery: AppGroupQuery, @PageableDefault appGroupPageable: Pageable): String = {
    model.addAttribute("appGroupQuery", appGroupQuery)
    model.addAttribute("appGroupPageable", appGroupPageable)
    val orgId = shiroComponent.findUser.orgID
    appGroupQuery.orgId = orgId
    appGroupQuery.orgType = orgType
    //分页查询所有
    //判断是不是总行，非总行不能查询到默认分组
    val page = appGroupService.findPage(appGroupQuery, appGroupPageable)
    model.addAttribute("orgId", orgId)
    model.addAttribute("orgType", orgType)
    model.addAttribute("page", page)
    "appGroup/list"
  }

  /**
    * 新增应用分组
    *
    */
  @RequestMapping(value = Array("insert.do"))
  def insert(): String = {
    "appGroup/create"
  }

  @RequestMapping(value = Array("ajax_validateAppGroupName.do"))
  @ResponseBody
  def ajax_validateAppGroupName(appGroupName: String): String = {
    val orgId = shiroComponent.findUser.orgID
    appGroupService.isExistAppGroupByAppGroupName(appGroupName, orgId, orgType).toString
  }

  /**
    * 保存应用
    *
    * @param appGroup 分组
    */
  @RequestMapping(value = Array("save.do"))
  def save(appGroup: AppGroup): String = {
    val orgId = shiroComponent.findUser.orgID
    appGroupService.save(appGroup, orgId, orgType)
    "redirect:/appGroup/list.do"
  }

  /**
    * 关联应用
    *
    * @param model                 model
    * @param appName               应用名称
    * @param creTime               上传时间
    * @param associatedAppPageable 分页
    * @param appGroupId            用户分组id
    */
  @RequestMapping(value = Array("associatedAppList.do"))
  def associatedAppList(model: Model, appName: String, creTime: String, @PageableDefault associatedAppPageable: Pageable, appGroupId: String): String = {
    model.addAttribute("creTime", creTime)
    model.addAttribute("appName", appName)
    model.addAttribute("appGroupId", appGroupId)
    model.addAttribute("associatedAppPageable", associatedAppPageable)
    val orgId = shiroComponent.findUser.orgID
    //分页查询机构类型、机构下所有未关联这个应用分组的应用
    val page = appGroupService.findAllAppsUnAssociateAppGroup(appName, creTime, associatedAppPageable, orgId, orgType, appGroupId)
    model.addAttribute("page", page)
    page.getContent
    "appGroup/associatedAppList"
  }

  /**
    * 全部关联(应用分组关联应用)
    *
    * @param appNameAndAppPackageNames 要关联的应用直接以逗号分割，每个应用的应用名称、包名以~分割
    */
  @RequestMapping(value = Array("allAssociatedApp.do"))
  def allAssociatedApp(appNameAndAppPackageNames: String, appGroupId: String): String = {
    val orgId = shiroComponent.findUser.orgID
    val appNameAndAppPackageNameAndAppGroupIdArr = appNameAndAppPackageNames.split(",")
    appNameAndAppPackageNameAndAppGroupIdArr.foreach(f => {
      val elements = f.split("~")
      val appName = elements(0)
      val appPackageName = elements(1)
      appGroupService.associatedApp(appName, appPackageName, orgId, orgType, appGroupId)
    })
    "redirect:/appGroup/associatedAppList.do"
  }


  /**
    * 取消关联应用
    *
    * @param model                  model
    * @param appName                应用名称
    * @param creTime                上传时间
    * @param appGroupId             应用分组id
    * @param unAssociateAppPageable 分页
    */
  @RequestMapping(value = Array("unAssociateAppList.do"))
  def unAssociateAppList(model: Model, appName: String, creTime: String, appGroupId: String, @PageableDefault unAssociateAppPageable: Pageable): String = {
    model.addAttribute("creTime", creTime)
    model.addAttribute("appName", appName)
    model.addAttribute("unAssociateAppPageable", unAssociateAppPageable)
    model.addAttribute("appGroupId", appGroupId)
    val orgId = shiroComponent.findUser.orgID
    //分页查询机构类型、机构下所有关联这个应用分组的应用
    val page = appGroupService.findAllAppsAssociateAppGroup(appName, creTime, unAssociateAppPageable, orgId, orgType, appGroupId)
    model.addAttribute("page", page)
    "appGroup/unAssociatedAppList"
  }

  /**
    * 全部取消关联(应用分组取消关联应用)
    *
    * @param appPackageNames 要关联的应用直接以逗号分割，每个应用的应用包名
    */
  @RequestMapping(value = Array("allUnAssociatedApp.do"))
  def allUnAssociatedApp(appPackageNames: String, appGroupId: String): String = {
    val orgId = shiroComponent.findUser.orgID
    val appGroupArr = appPackageNames.split(",")
    appGroupArr.foreach(f => {
      appGroupService.cancelAssociateApp(f, appGroupId, orgId, orgType)
    })
    "redirect:/appGroup/unAssociateAppList.do"
  }

  /**
    * 应用分组关联设备查询
    *
    * @param model                             model
    * @param associatedVAppGroupDeviceQuery    应用分组关联设备查询类
    * @param appGroupId                        应用分组id
    * @param associatedVAppGroupDevicePageable 分页接口
    */
  @RequestMapping(value = Array("associatedDevList.do"))
  def associatedDevList(model: Model, associatedVAppGroupDeviceQuery: VAppGroupDeviceQuery, appGroupId: String, @PageableDefault associatedVAppGroupDevicePageable: Pageable): String = {
    model.addAttribute("associatedVAppGroupDeviceQuery", associatedVAppGroupDeviceQuery)
    model.addAttribute("associatedVAppGroupDevicePageable", associatedVAppGroupDevicePageable)
    model.addAttribute("appGroupId", appGroupId)
    val orgId = shiroComponent.findUser.orgID
    //分页查询应用分组关联设备视图
    val page = appGroupService.findAllDevsAssociateDefAppGroup(associatedVAppGroupDeviceQuery, associatedVAppGroupDevicePageable, orgId, orgType)
    model.addAttribute("page", page)
    "appGroup/associatedDevList"
  }

  /**
    * 关联所有设备
    *
    * @param appGroupId        应用分组id
    * @param firmCodeAndDevSns 设备之间用逗号分割，厂商编号和设备sn用~分割
    */
  @RequestMapping(value = Array("allAssociateDev.do"))
  def allAssociateDev(appGroupId: String, firmCodeAndDevSns: String): String = {
    val allFirmCodeAndDevSns = firmCodeAndDevSns.split(",")
    allFirmCodeAndDevSns.foreach(f => {
      val one = f.split("~")
      appGroupService.associateDev(one(0), one(1), appGroupId)
    })
    "redirect:/appGroup/associatedDevList.do"
  }

  /**
    * 应用分组取消关联设备查询
    *
    * @param model                               model
    * @param unAssociatedVAppGroupDeviceQuery    应用分组关联设备查询类
    * @param appGroupId                          应用分组id
    * @param unAssociatedVAppGroupDevicePageable 分页接口
    */
  @RequestMapping(value = Array("unAssociatedDevList.do"))
  def unAssociatedDevList(model: Model, unAssociatedVAppGroupDeviceQuery: VAppGroupDeviceQuery, appGroupId: String, @PageableDefault unAssociatedVAppGroupDevicePageable: Pageable): String = {
    model.addAttribute("unAssociatedVAppGroupDeviceQuery", unAssociatedVAppGroupDeviceQuery)
    model.addAttribute("unAssociatedVAppGroupDevicePageable", unAssociatedVAppGroupDevicePageable)
    model.addAttribute("appGroupId", appGroupId)
    //分页查询机构类型、机构下所有关联这个应用分组的设备
    val page = appGroupService.findAllDevsAssociateAppGroup(unAssociatedVAppGroupDeviceQuery, unAssociatedVAppGroupDevicePageable, appGroupId)
    model.addAttribute("page", page)
    "appGroup/unAssociatedDevList"
  }

  /**
    * 取消关联所有设备
    *
    * @param appGroupId        应用分组id
    * @param firmCodeAndDevSns 设备之间用逗号分割，厂商编号和设备sn用~分割
    */
  @RequestMapping(value = Array("AllUnAssociatedDev.do"))
  def AllUnAssociatedDev(appGroupId: String, firmCodeAndDevSns: String): String = {
    val orgId = shiroComponent.findUser.orgID
    val allFirmCodeAndDevSns = firmCodeAndDevSns.split(",")
    allFirmCodeAndDevSns.foreach(f => {
      val one = f.split("~")
      appGroupService.unAssociateDev(one(0), one(1), orgId, orgType)
    })
    "redirect:/appGroup/unAssociatedDevList.do"
  }

  /**
    * 修改应用分组
    *
    * @param appGroupId id
    * @param model      model
    * @return 编辑页面
    */
  @RequestMapping(value = Array("edit.do"))
  def edit(appGroupId: String, model: Model): String = {
    val appGroup = appGroupService.findOne(appGroupId)
    model.addAttribute("appGroup", appGroup)
    "appGroup/edit"
  }

  /**
    * 更新应用分组
    *
    * @param appGroupId   应用分组id
    * @param appGroupName 应用分组名称
    * @param appGroupDesc 应用分组描述
    * @return 编辑页面
    */
  @RequestMapping(value = Array("update.do"))
  def update(appGroupId: String, appGroupName: String, appGroupDesc: String): String = {
    appGroupService.updateAppGroup(appGroupId, appGroupName, appGroupDesc)
    "redirect:/appGroup/list.do"
  }
}
