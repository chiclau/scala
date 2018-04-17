package com.yada.spos.mag.controller


import com.yada.spos.db.model.Firm
import com.yada.spos.db.query.FirmQuery
import com.yada.spos.mag.service.{DeviceService, FirmService, OtaHistoryService, ProductsService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

/**
  * Created by duan on 2016/8/29.
  * 厂商管理
  */
@Controller
@RequestMapping(Array("firm"))
class FirmController {
  @Autowired
  var firmService: FirmService = _

  @Autowired
  var productsService: ProductsService = _

  @Autowired
  var deviceService: DeviceService = _

  @Autowired
  var otaHistoryService: OtaHistoryService = _

  /**
    * 菜单点击默认跳到空页面
    *
    * @param model model
    */
  @RequestMapping(value = Array("emptyList.do"))
  def emptyList(model: Model): String = {
    model.addAttribute("firmQuery", new FirmQuery)
    "firm/list"
  }

  /**
    * 根据条件查询厂商信息
    *
    * @param firmQuery    厂商查询类
    * @param firmPageable 分页
    * @param model        model
    */
  @RequestMapping(value = Array("list.do"))
  def list(firmQuery: FirmQuery, @PageableDefault firmPageable: Pageable, model: Model): String = {
    model.addAttribute("firmQuery", firmQuery)
    model.addAttribute("firmPageable", firmPageable)
    val page: Page[Firm] = firmService.findPage(firmQuery, firmPageable)
    model.addAttribute("page", page)
    "firm/list"
  }

  /**
    * 跳转到添加厂商页面
    *
    * @param model model
    */
  @RequestMapping(value = Array("insert.do"))
  def insert(model: Model): String = {
    "firm/insert"
  }

  /**
    * 保存厂商实体信息
    *
    * @param firm 厂商
    */
  @RequestMapping(value = Array("save.do"))
  def save(firm: Firm): String = {
    firmService.save(firm)
    "redirect:/firm/list.do"
  }

  /**
    * 编辑厂商信息
    *
    * @param firmCode 厂商编码
    * @param model    model
    */
  @RequestMapping(value = Array("edit.do"))
  def edit(firmCode: String, model: Model): String = {
    val firm = firmService.findOne(firmCode)
    model.addAttribute("firm", firm)
    "firm/edit"
  }

  /**
    * 更新厂商信息
    *
    * @param firm 厂商
    */
  @RequestMapping(value = Array("update.do"))
  def update(firm: Firm): String = {
    firmService.save(firm)
    productsService.updateFirmNameByFirmCode(firm.firmCode, firm.firmName)
    deviceService.updateFirmNameByFirmCode(firm.firmCode, firm.firmName)
    otaHistoryService.updateFirmNameByFirmCode(firm.firmCode, firm.firmName)
    "redirect:/firm/list.do"
  }

  /**
    * Ajax验证厂商编号是否已经存在
    *
    * @param firmCode 厂商编号
    *
    */
  @RequestMapping(value = Array("ajaxValidateFirmCode.do"))
  @ResponseBody
  def ajaxValidateFirmCode(firmCode: String): String = {
    var result: String = "0"
    val firm: Firm = firmService.findOne(firmCode)
    if (firm != null) {
      result = "1"
    }
    result
  }
}
