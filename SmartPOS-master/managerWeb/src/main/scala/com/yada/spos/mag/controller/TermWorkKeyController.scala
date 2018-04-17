package com.yada.spos.mag.controller

import com.typesafe.config.ConfigFactory
import com.yada.spos.db.model.Device
import com.yada.spos.db.query.{BTermWorkKeyQuery, DeviceQuery}
import com.yada.spos.mag.core.shiro.ShiroComponent
import com.yada.spos.mag.service.{BTermWorkKeyService, DeviceService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

/**
  * Created by duan on 2016/9/2.
  * 终端秘钥管理
  */
@Controller
@RequestMapping(Array("termWorkKey"))
class TermWorkKeyController {

  @Autowired
  var deviceService: DeviceService = _
  @Autowired
  var bTermWorkKeyService: BTermWorkKeyService = _
  @Autowired
  var shiroComponent: ShiroComponent = _
  val orgType = ConfigFactory.load().getString("orgType")

  /**
    * 菜单点击默认跳到空页面
    *
    * @return
    */
  @RequestMapping(value = Array("emptyList.do"))
  def emptyList(): String = {
    "termWorkKey/list"
  }

  /**
    * 终端sn绑定的list
    *
    * @param terminalListQuery         设备查询类
    * @param terminalListQueryPageable 分页
    * @param model                     model
    * @return
    */
  @RequestMapping(value = Array("list.do"))
  def list(terminalListQuery: DeviceQuery, @PageableDefault terminalListQueryPageable: Pageable, model: Model): String = {
    model.addAttribute("terminalListQuery", terminalListQuery)
    model.addAttribute("terminalListQueryPageable", terminalListQueryPageable)
    terminalListQuery.orgId = shiroComponent.findUser.orgID
    terminalListQuery.orgType = orgType
    val page: Page[Device] = deviceService.findAll(terminalListQuery, terminalListQueryPageable)
    model.addAttribute("page", page)
    "termWorkKey/list"
  }

  /**
    * 绑定
    *
    * @param firmCode 厂商编号
    * @param devSn    终端sn
    * @param model    model
    */
  @RequestMapping(value = Array("bound.do"))
  def bound(firmCode: String, devSn: String, model: Model): String = {
    model.addAttribute("firmCode", firmCode)
    model.addAttribute("devSn", devSn)
    "termWorkKey/bound"
  }

  /**
    * 终端绑定商户
    *
    * @param device 设备
    */
  @RequestMapping(value = Array("boundConfirm.do"))
  def boundConfirm(device: Device): String = {
    bTermWorkKeyService.bound(device)
    "redirect:/termWorkKey/list.do"
  }

  /**
    * 验证是否可以绑定
    *
    * @param device 实体
    * @return 验证结果
    */
  @RequestMapping(value = Array("ajaxValidateIsMayBound.do"))
  @ResponseBody
  def ajaxValidateIsMayBound(device: Device): String = {
    val orgId = shiroComponent.findUser.orgID
    bTermWorkKeyService.validateIsMayBound(device, orgId, orgType, new BTermWorkKeyQuery, new DeviceQuery)
  }

  /**
    * 解除绑定
    *
    * @param deviceQuery 设备查询类
    */
  @RequestMapping(value = Array("removeBound.do"))
  def removeBound(deviceQuery: DeviceQuery): String = {
    val orgId = shiroComponent.findUser.orgID
    bTermWorkKeyService.removeBound(deviceQuery, orgId, orgType)
    "redirect:/termWorkKey/list.do"
  }
}
