package com.yada.spos.mag.controller

import com.typesafe.config.ConfigFactory
import com.yada.spos.db.model.AuthDevice
import com.yada.spos.db.query.AuthDeviceQuery
import com.yada.spos.mag.core.shiro.ShiroComponent
import com.yada.spos.mag.service.AuthDeviceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}
import org.springframework.web.multipart.MultipartFile

/**
  * Created by duan on 2016/8/31.
  * 母POS管理
  */
@Controller
@RequestMapping(Array("authDevice"))
class AuthDeviceController {

  @Autowired
  var authDeviceService: AuthDeviceService = _
  @Autowired
  var shiroComponent: ShiroComponent = _
  val orgType = ConfigFactory.load().getString("orgType")

  /**
    * 菜单点击默认跳到空页面
    *
    * @param model model
    * @return
    */
  @RequestMapping(value = Array("emptyList.do"))
  def emptyList(model: Model): String = {
    model.addAttribute("authDeviceQuery", new AuthDeviceQuery)
    "authDevice/list"
  }

  /**
    * 根据条件查询设备数据
    *
    * @param authDeviceQuery    母POS查询类
    * @param authDevicePageable 分页
    * @param model              model
    */
  @RequestMapping(value = Array("list.do"))
  def list(authDeviceQuery: AuthDeviceQuery, @PageableDefault authDevicePageable: Pageable, model: Model): String = {
    model.addAttribute("authDeviceQuery", authDeviceQuery)
    model.addAttribute("authDevicePageable", authDevicePageable)
    authDeviceQuery.orgId = shiroComponent.findUser.orgID
    authDeviceQuery.orgType = orgType
    val page: Page[AuthDevice] = authDeviceService.findAll(authDeviceQuery, authDevicePageable)
    model.addAttribute("page", page)
    "authDevice/list"
  }

  /**
    * 跳转新增页面
    *
    * @param model model
    */
  @RequestMapping(value = Array("insert.do"))
  def insert(model: Model): String = {
    "authDevice/create"
  }

  @RequestMapping(value = Array("ajax_validateSn.do"))
  @ResponseBody
  def ajax_validateSn(authPosSn: String): String = {
    val authDevice = authDeviceService.findOne(authPosSn)
    if (authDevice == null) "true"
    else "false"
  }

  /**
    * 保存或更新授权的母pos
    *
    * @param authPosSn 母POSsn
    * @param p12File   上传的证书文件
    * @param pwd       证书密码
    */
  @RequestMapping(value = Array("saveAndUpdate.do"))
  def saveAndUpdate(authPosSn: String, p12File: MultipartFile, pwd: String): String = {
    val orgId = shiroComponent.findUser.orgID
    authDeviceService.saveAndUpdate(authPosSn, p12File, orgId, orgType, pwd)
    "redirect:/authDevice/list.do"
  }

  /**
    * 编辑母POS
    *
    * @param authPosSn 母POSsn
    * @param model     model
    */
  @RequestMapping(value = Array("edit.do"))
  def edit(authPosSn: String, model: Model): String = {
    val authDevice: AuthDevice = authDeviceService.findOne(authPosSn)
    model.addAttribute("model", authDevice)
    "authDevice/edit"
  }
}
