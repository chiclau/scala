package com.yada.spos.mag.controller

import java.text.SimpleDateFormat
import java.util.Date

import com.typesafe.config.ConfigFactory
import com.yada.spos.db.model.OnlineParam
import com.yada.spos.db.query.OnlineParamQuery
import com.yada.spos.mag.core.shiro.ShiroComponent
import com.yada.spos.mag.service.OnlineParamService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

/**
  * 设备参数维护（只允许分行操作)
  * Created by nickDu on 2016/9/8.
  */
@Controller
@RequestMapping(Array("onlineParam"))
class OnlineParamController {
  @Autowired
  var onlineParamService: OnlineParamService = _
  @Autowired
  var shiroComponent: ShiroComponent = _
  val orgType = ConfigFactory.load().getString("orgType")

  /**
    * 菜单点击默认跳到空页面
    *
    * @param model model
    */
  @RequestMapping(value = Array("emptyList.do"))
  def emptyList(model: Model): String = {
    model.addAttribute("onlineParamQuery", new OnlineParamQuery)
    "onlineParam/list"
  }

  @RequestMapping(value = Array("list.do"))
  def list(model: Model, query: OnlineParamQuery, @PageableDefault onlineParamPageable: Pageable): String = {
    model.addAttribute("onlineParamQuery", query)
    model.addAttribute("onlineParamPageable", onlineParamPageable)
    query.orgId = shiroComponent.findUser.orgID
    query.orgType = orgType
    val page = onlineParamService.findPage(query, onlineParamPageable)
    model.addAttribute("page", page)
    "onlineParam/list"
  }

  @RequestMapping(value = Array("edit.do"))
  def edit(model: Model, onlineParamId: String): String = {
    val onlineParam = onlineParamService.findById(onlineParamId)
    model.addAttribute("model", onlineParam)
    "onlineParam/edit"
  }

  @RequestMapping(value = Array("update.do"))
  def update(onlineParam: OnlineParam, reqParams: String): String = {
    val onlineParamNew = onlineParamService.findById(onlineParam.onlineParamId.toString)
    val now: Date = new Date()
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
    onlineParamNew.updTime = dateFormat.format(now)
    onlineParamNew.paramName = onlineParam.paramName
    onlineParamNew.paramValue = onlineParam.paramValue
    onlineParamNew.paramDesc = onlineParam.paramDesc
    onlineParamService.saveOrUpdate(onlineParamNew)
    "redirect:/onlineParam/list.do"
  }

  @RequestMapping(value = Array("create.do"))
  def insert(model: Model): String = {
    "onlineParam/create"
  }

  @RequestMapping(value = Array("save.do"))
  def save(onlineParam: OnlineParam): String = {
    val now: Date = new Date()
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
    onlineParam.updTime = dateFormat.format(now)
    onlineParam.creTime = dateFormat.format(now)
    onlineParam.orgId = shiroComponent.findUser.orgID
    onlineParam.orgType = orgType
    onlineParamService.saveOrUpdate(onlineParam)
    "redirect:/onlineParam/list.do"
  }

  /**
    * Ajax验证参数名称是否已经存在
    *
    * @param paramName 参数名称
    *
    */
  @RequestMapping(value = Array("ajaxValidateParamName.do"))
  @ResponseBody
  def ajaxValidateParamName(paramName: String): String = {
    var result: String = "0"
    val orgId = shiroComponent.findUser.orgID
    val onlineParam: OnlineParam = onlineParamService.findByOrgIdAndOrgTypeAndParamName(orgId, orgType, paramName)
    if (onlineParam != null) {
      result = "1"
    }
    result
  }

  /**
    * 修改时Ajax验证参数名称是否已经存在
    *
    * @param paramName 参数名称
    *
    */
  @RequestMapping(value = Array("ajaxValidateParamNameUpdate.do"))
  @ResponseBody
  def ajaxValidateParamNameUpdate(paramName: String, onlineParamId: String): String = {
    var result: String = "0"
    val orgId = shiroComponent.findUser.orgID
    val onlineParam: OnlineParam = onlineParamService.findByOrgIdAndOrgTypeAndParamName(orgId, orgType, paramName)
    if (onlineParam != null && onlineParam.onlineParamId.toString != onlineParamId) {
      result = "1"
    }
    result
  }
}
