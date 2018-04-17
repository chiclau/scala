package com.yada.spos.mag.controller

import com.typesafe.config.ConfigFactory
import com.yada.spos.db.model.{Device, DevicePK}
import com.yada.spos.db.query.DeviceQuery
import com.yada.spos.mag.core.shiro.ShiroComponent
import com.yada.spos.mag.service.DeviceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}
import org.springframework.web.multipart.MultipartFile

/**
  * Created by duan on 2016/8/31.
  * 设备管理
  */
@Controller
@RequestMapping(Array("device"))
class DeviceController {

  @Autowired
  var deviceService: DeviceService = _
  @Autowired
  var shiroComponent: ShiroComponent = _
  val orgType = ConfigFactory.load().getString("orgType")

  /**
    * 跳转导入数据页面
    *
    * @return
    */
  @RequestMapping(value = Array("upload.do"))
  def upload: String = {
    "device/upload"
  }

  /**
    * 保存导入的设备数据
    *
    * @return
    */
  @RequestMapping(value = Array("importSn.do"))
  def importSN(snFile: MultipartFile): String = {
    val orgId = shiroComponent.findUser.orgID
    deviceService.importSn(snFile, orgId, orgType)
    "redirect:/device/list.do"
  }

  /**
    * 菜单点击默认跳到空页面
    *
    * @param model model
    * @return
    */
  @RequestMapping(value = Array("emptyList.do"))
  def emptyList(model: Model): String = {
    model.addAttribute("deviceQuery", new DeviceQuery)
    "device/list"
  }

  /**
    * 根据条件查询设备数据
    *
    * @param deviceQuery    设备查询类
    * @param devicePageable 分页
    * @param model          model
    * @return
    */
  @RequestMapping(value = Array("list.do"))
  def list(deviceQuery: DeviceQuery, @PageableDefault devicePageable: Pageable, model: Model): String = {
    model.addAttribute("deviceQuery", deviceQuery)
    model.addAttribute("devicePageable", devicePageable)
    deviceQuery.orgId = shiroComponent.findUser.orgID
    deviceQuery.orgType = orgType
    val page: Page[Device] = deviceService.findAll(deviceQuery, devicePageable)
    model.addAttribute("page", page)
    "device/list"
  }

  /**
    * 验证设备删除
    *
    * @param firmCode 厂商编号
    * @param devSn    设备sn
    */
  @RequestMapping(value = Array("ajaxValidateDelete.do"))
  @ResponseBody
  def ajaxValidateDelete(firmCode: String, devSn: String): String = {
    deviceService.validateIsCanDelete(firmCode, devSn)
  }

  /**
    * 删除设备Id
    *
    * @param devicePK 设备主键
    */
  @RequestMapping(value = Array("delete.do"))
  def delete(devicePK: DevicePK): String = {
    //删除设备
    deviceService.delete(devicePK)
    "redirect:/device/list.do"
  }

}
