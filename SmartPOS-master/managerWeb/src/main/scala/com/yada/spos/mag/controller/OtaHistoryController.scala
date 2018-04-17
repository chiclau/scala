package com.yada.spos.mag.controller

import java.io.File
import java.nio.file.Paths

import com.typesafe.config.ConfigFactory
import com.yada.spos.db.model.{OtaHistory, Products}
import com.yada.spos.db.query.OtaHistoryQuery
import com.yada.spos.mag.core.shiro.ShiroComponent
import com.yada.spos.mag.exception.UploadException
import com.yada.spos.mag.service.ext.{OtaInfo, SystemHandler}
import com.yada.spos.mag.service.{FirmService, OtaHistoryService, ProductsService}
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{ModelAttribute, RequestMapping, ResponseBody, SessionAttributes}
import org.springframework.web.multipart.MultipartFile

/**
  * Created by duan on 2016/9/5.
  */
@Controller
@RequestMapping(Array("otaHistory"))
@SessionAttributes(value = Array("otaResult"))
class OtaHistoryController {
  @Autowired
  var otaHistoryService: OtaHistoryService = _
  @Autowired
  var firmService: FirmService = _
  @Autowired
  var productsService: ProductsService = _
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
    model.addAttribute("otaHistoryQuery", new OtaHistoryQuery)
    "otaHistory/list"
  }

  /**
    * 根据条件查询厂商信息
    *
    * @param otaHistoryQuery    查询类
    * @param otaHistoryPageable 分页
    * @param model              model
    * @return
    */
  @RequestMapping(value = Array("list.do"))
  def list(otaHistoryQuery: OtaHistoryQuery, otaHistoryPageable: Pageable, model: Model): String = {
    model.addAttribute("otaHistoryQuery", otaHistoryQuery)
    model.addAttribute("otaHistoryPageable", otaHistoryPageable)
    otaHistoryQuery.setOrgId(shiroComponent.findUser.orgID)
    otaHistoryQuery.setOrgType(orgType)
    val page: Page[OtaHistory] = otaHistoryService.findAll(otaHistoryQuery, otaHistoryPageable)
    model.addAttribute("page", page)
    "otaHistory/list"
  }

  /**
    * 到ota上传页面
    *
    * @param model model
    */
  @RequestMapping(value = Array("uploadOta.do"))
  def uploadOta(model: Model): String = {
    val firms = firmService.findAll()
    model.addAttribute("firms", firms)
    "otaHistory/uploadOta"
  }

  @RequestMapping(value = Array("getProducts.do"), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
  @ResponseBody
  def getProducts(firmCode: String): java.util.List[Products] = {
    productsService.findByFirmCode(firmCode)
  }

  /**
    * 上传ota下一步，到上传信息确认页面
    *
    * @param model     model
    * @param otaResult ota信息
    * @param zipFile   zip文件
    * @param firmCode  厂商编号
    * @param prodCode  产品型号
    */
  @RequestMapping(value = Array("uploadOtaToTempDir.do"))
  def uploadOtaToTempDir(model: Model, otaResult: OtaInfo, zipFile: MultipartFile, firmCode: String, prodCode: String): String = {
    //加载配置文件
    val config = ConfigFactory.load()
    val user = shiroComponent.findUser
    val orgId = user.orgID
    val userName = user.username
    val orgName = systemHandler.getOrg(orgId, orgType).orgName
    //将zip文件转到这个临时目录下
    val zipTempPath = config.getString("appFile.tempDir") + File.separator + userName + File.separator + "OTA"
    val zip = new File(zipTempPath)
    var result: OtaInfo = null
    try {
      //目录存在就删除然后重新创建
      if (zip.exists()) FileUtils.forceDelete(zip)
      zip.mkdirs()
      //创建一个File
      val zipFileTemp = new File(zipTempPath + File.separator + zipFile.getOriginalFilename)
      //将请求中的zip文件复制到新创建的文件
      zipFile.transferTo(zipFileTemp)
      //上传文件到临时目录
      result = otaHistoryService.uploadOtaToTempDir(zipFileTemp, orgId, orgType, firmCode, prodCode)
      model.addAttribute("otaResult", result)
      model.addAttribute("result", result.otaHistory)
    } catch {
      case e: UploadException =>
        //目录存在就删除临时目录
        if (result != null && result.tempDir != null) {
          val tempDirFile = Paths.get(otaResult.tempDir).toFile
          if (tempDirFile.exists()) FileUtils.forceDelete(tempDirFile)
        }
        val mess = if (e.errorMsg != null) s"，失败原因: ${e.errorMsg}" else ""
        throw new RuntimeException(s"机构[$orgName]用户[$userName]上传zip文件[${zipFile.getOriginalFilename}]失败" + mess, e)
      case e: Throwable =>
        //目录存在就删除临时目录
        if (result != null && result.tempDir != null) {
          val tempDirFile = Paths.get(otaResult.tempDir).toFile
          if (tempDirFile.exists()) FileUtils.forceDelete(tempDirFile)
        }
        throw new RuntimeException("", e)
    } finally {
      //目录存在就删除然后重新创建
      FileUtils.forceDelete(zip)
    }
    "otaHistory/uploadOtaConfirm"
  }

  /**
    * 保存ota
    *
    * @param otaResult 上传信息
    */
  @RequestMapping(value = Array("saveOta.do"))
  def saveOta(@ModelAttribute("otaResult") otaResult: OtaInfo): String = {
    val user = shiroComponent.findUser
    val orgId = user.orgID
    val userName = user.username
    val orgName = systemHandler.getOrg(orgId, orgType).orgName
    try {
      otaHistoryService.saveUploadOta(otaResult, orgId, orgType)
    } catch {
      case e: UploadException =>
        val mess = if (e.errorMsg != null) s"，失败原因: ${e.errorMsg}" else ""
        throw new RuntimeException(s"机构[$orgName]用户[$userName]上传zip文件保存ota失败" + mess, e)
      case e: Throwable =>
        throw new RuntimeException("", e)
    } finally {
      //如果临时目录存在就删除
      if (otaResult.tempDir != null) {
        val tempDirFile = Paths.get(otaResult.tempDir).toFile
        if (tempDirFile.exists()) FileUtils.forceDelete(tempDirFile)
      }
    }
    "redirect:/otaHistory/list.do"
  }

  /**
    * 上传取消
    *
    * @param otaResult 请求
    */
  @RequestMapping(value = Array("cancel.do"))
  def cancel(@ModelAttribute("otaResult") otaResult: OtaInfo): String = {
    //如果临时目录存在就删除
    if (otaResult.tempDir != null) FileUtils.forceDelete(Paths.get(otaResult.tempDir).toFile)
    "redirect:/otaHistory/list.do"
  }
}
