package com.yada.spos.mag.controller

import java.io.File
import java.nio.file.Paths

import com.typesafe.config.ConfigFactory
import com.yada.spos.db.model.AppFileHistory
import com.yada.spos.db.query.AppFileHistoryQuery
import com.yada.spos.mag.core.shiro.ShiroComponent
import com.yada.spos.mag.exception.UploadException
import com.yada.spos.mag.service.ext.{AppInfo, SystemHandler}
import com.yada.spos.mag.service.{AppFileHistoryService, SFtpService}
import org.apache.commons.io.FileUtils
import org.apache.shiro.SecurityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{ModelAttribute, RequestMapping, ResponseBody, SessionAttributes}
import org.springframework.web.multipart.MultipartFile

import scala.collection.convert.decorateAll._


/**
  * Created by duan on 2016/9/2.
  * 应用管理(历史应用)
  */
@Controller
@RequestMapping(Array("appFileHistory"))
@SessionAttributes(value = Array("appFileResult"))
class AppFileHistoryController {
  @Autowired
  var appFileHistoryService: AppFileHistoryService = _
  @Autowired
  var sFtpService: SFtpService = _
  @Autowired
  var shiroComponent: ShiroComponent = _
  @Autowired
  var systemHandler: SystemHandler = _
  val orgType = ConfigFactory.load().getString("orgType")

  /**
    * 菜单点击默认跳到空页面
    *
    * @param model model
    */
  @RequestMapping(value = Array("emptyList.do"))
  def emptyList(model: Model): String = {
    model.addAttribute("appFileHistoryQuery", new AppFileHistoryQuery)
    "appFileHistory/list"
  }

  /**
    * 根据条件查询设备数据
    *
    * @param appFileHistoryQuery    历史应用查询类
    * @param appFileHistoryPageable 分页
    * @param model                  model
    */
  @RequestMapping(value = Array("list.do"))
  def list(appFileHistoryQuery: AppFileHistoryQuery, @PageableDefault(sort = Array("creTime"), direction = Direction.DESC) appFileHistoryPageable: Pageable
           , model: Model): String = {
    model.addAttribute("appFileHistoryQuery", appFileHistoryQuery)
    model.addAttribute("appFileHistoryPageable", appFileHistoryPageable)
    val orgId = shiroComponent.findUser.orgID
    appFileHistoryQuery.orgId = orgId
    val orgName = systemHandler.getOrg(orgId, orgType).orgName
    appFileHistoryQuery.orgType = orgType
    val page: Page[AppFileHistory] = appFileHistoryService.findAll(appFileHistoryQuery, appFileHistoryPageable)
    model.addAttribute("page", page)
    model.addAttribute("org", orgName)
    model.addAttribute("orgType", if (orgType == "1") "固话" else "总对总")
    SecurityUtils.getSubject.getPreviousPrincipals
    "appFileHistory/list"
  }

  /**
    * 删除应用包
    *
    * @param appFileId id
    */
  @RequestMapping(value = Array("ajaxDelete.do"))
  @ResponseBody
  def ajaxDelete(appFileId: String): String = {
    val orgId = shiroComponent.findUser.orgID
    appFileHistoryService.checkAppIsCanDelete(appFileId, orgId, orgType)
  }

  @RequestMapping(value = Array("delete.do"))
  def delete(appFileId: String): String = {
    val orgId = shiroComponent.findUser.orgID
    appFileHistoryService.delete(appFileId, orgId, orgType)
    "redirect:/appFileHistory/list.do"
  }

  /**
    * 到上传页面
    */
  @RequestMapping(value = Array("uploadAppFile.do"))
  def uploadAppFile(): String = {
    "appFileHistory/uploadAppFile"
  }

  /**
    * 将文件保存到临时目录
    *
    * @param model   model
    * @param zipFile zip文件
    */
  @RequestMapping(value = Array("uploadAppFileToTempDir.do"))
  def uploadAppFileToTempDir(model: Model, zipFile: MultipartFile): String = {
    //加载配置文件
    val config = ConfigFactory.load()
    val user = shiroComponent.findUser
    val orgId = user.orgID
    val userName = user.username
    val orgName = systemHandler.getOrg(orgId, orgType).orgName
    //将zip文件转到这个临时目录下
    val zipTempPath = config.getString("appFile.tempDir") + File.separator + userName + File.separator + "app"
    val zip = new File(zipTempPath)
    var result: List[AppInfo] = null
    try {
      //目录存在就删除然后重新创建
      if (zip.exists()) FileUtils.forceDelete(zip)
      zip.mkdirs()
      //创建一个File
      val zipFileTemp = new File(zipTempPath + File.separator + zipFile.getOriginalFilename)
      //将请求中的zip文件复制到新创建的文件
      zipFile.transferTo(zipFileTemp)
      //上传文件到临时目录
      result = appFileHistoryService.uploadAppFileToTempDir(zipFileTemp, orgId, orgType)
      model.addAttribute("appFileResult", result.asJava)
    } catch {
      case e: UploadException =>
        //如果临时目录存在就删除
        if (result != null && result.nonEmpty) {
          val tempFile = Paths.get(result.head.tempDir).toFile
          if (tempFile.exists()) FileUtils.forceDelete(tempFile)
        }
        val mess = if (e.errorMsg != null) s"，失败原因: ${e.errorMsg}" else ""
        throw new RuntimeException(s"机构[$orgName]用户[$userName]上传应用文件[${zipFile.getOriginalFilename}]失败" + mess, e)
      case e: Throwable =>
        //如果临时目录存在就删除
        if (result != null && result.nonEmpty) {
          val tempFile = Paths.get(result.head.tempDir).toFile
          if (tempFile.exists()) FileUtils.forceDelete(tempFile)
        }
        throw new RuntimeException("", e)
    } finally {
      //目录存在就删除然后重新创建
      if (zip.exists()) FileUtils.forceDelete(zip)
    }
    "appFileHistory/uploadAppFileConfirm"
  }

  /**
    * 保存应用文件信息
    *
    * @param appFileResult 应用信息
    */
  @RequestMapping(value = Array("saveAppFile.do"))
  def saveAppFile(@ModelAttribute("appFileResult") appFileResult: java.util.List[AppInfo]): String = {
    val user = shiroComponent.findUser
    val orgId = user.orgID
    val userName = user.username
    val orgName = systemHandler.getOrg(orgId, orgType).orgName
    try {
      appFileHistoryService.saveAppFiles(appFileResult.asScala.toList, orgId, orgType)
    } catch {
      case e: UploadException =>
        val mess = if (e.errorMsg != null) s"，失败原因: ${e.errorMsg}" else ""
        throw new RuntimeException(s"机构[$orgName]用户[$userName]保存应用文件失败" + mess, e)
      case e: Throwable =>
        throw new RuntimeException("", e)
    } finally {
      //如果临时目录存在就删除
      if (appFileResult != null && !appFileResult.isEmpty) {
        val tempFile = Paths.get(appFileResult.get(0).tempDir).toFile
        if (tempFile.exists()) FileUtils.forceDelete(tempFile)
      }
    }
    "redirect:/appFileHistory/list.do"
  }


  /**
    * 上传取消
    *
    * @param appFileResult 应用信息列表
    */
  @RequestMapping(value = Array("cancel.do"))
  def cancel(@ModelAttribute("appFileResult") appFileResult: java.util.List[AppInfo]): String = {
    if (appFileResult != null && !appFileResult.isEmpty) {
      //如果临时目录存在就删除
      val tempFile = Paths.get(appFileResult.get(0).tempDir).toFile
      if (tempFile.exists()) FileUtils.forceDelete(tempFile)
    }
    "redirect:/appFileHistory/list.do"
  }

  @RequestMapping(value = Array("show.do"))
  def show(model: Model, appFileId: String): String = {
    val appFile = appFileHistoryService.findOne(appFileId)
    model.addAttribute("model", appFile)
    "appFileHistory/show"
  }
}
