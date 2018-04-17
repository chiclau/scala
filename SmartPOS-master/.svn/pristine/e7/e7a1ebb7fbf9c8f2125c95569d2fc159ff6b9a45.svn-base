package com.yada.spos.mag.service

import java.io.File
import java.nio.file.Paths
import java.util.Date

import com.typesafe.config.ConfigFactory
import com.yada.spos.db.dao._
import com.yada.spos.db.model.{OtaHistory, ProductsPK}
import com.yada.spos.db.query.OtaHistoryQuery
import com.yada.spos.mag.service.ext.{OtaInfo, _}
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Scope, ScopedProxyMode}
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import scala.collection.convert.decorateAll._

/**
  * Created by pangChangSong on 2016/9/4.
  * ota管理Controller
  */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
class OtaHistoryService {

  @Autowired
  var otaHistoryDao: OtaHistoryDao = _
  @Autowired
  var otaFileLatestDao: OtaFileLatestDao = _
  @Autowired
  var productsDao: ProductsDao = _
  @Autowired
  var firmDao: FirmDao = _
  @Autowired
  var zipFileHandler: ZipFileHandler = _
  @Autowired
  var otaInfoHandler: OtaInfoHandler = _
  @Autowired
  var otaFileHandler: OtaFileHandler = _
  @Autowired
  var systemHandler: SystemHandler = _

  /**
    * 根据条件查询
    *
    * @param firmQuery 厂商查询类
    * @param pageable  分页
    * @return
    */
  def findAll(firmQuery: OtaHistoryQuery, pageable: Pageable): Page[OtaHistory] = {
    otaHistoryDao.findAll(firmQuery, pageable)
  }

  /**
    * 根据厂商编号更新厂商名称
    *
    * @param firmCode 厂商编号
    * @param firmName 厂商名称
    * @return
    */
  def updateFirmNameByFirmCode(firmCode: String, firmName: String): Int = {
    otaHistoryDao.updateFirmNameByFirmCode(firmCode, firmName)
    otaFileLatestDao.updateFirmNameByFirmCode(firmCode, firmName)
  }

  /**
    * 根据产品型号更新产品型号名称
    *
    * @param prodName 产品型号名称
    * @param prodCode 产品型号
    * @param firmCode 厂商编号
    * @return
    */
  def updateProdNameByProdCodeAndFirmCode(prodName: String, prodCode: String, firmCode: String): Int = {
    otaHistoryDao.updateProdNameByProdCodeAndFirmCode(prodName, prodCode, firmCode)
    otaFileLatestDao.updateProdNameByProdCodeAndFirmCode(prodName, prodCode, firmCode)
  }

  /**
    * 上传ota到临时目录
    *
    * @param zipFile  上传的文件
    * @param orgId    机构id
    * @param orgType  机构类型
    * @param firmCode 厂商编号
    * @param prodCode 产品型号
    * @return otaInfo
    */
  def uploadOtaToTempDir(zipFile: File, orgId: String, orgType: String, firmCode: String, prodCode: String): OtaInfo = {
    // 临时目录
    val tempDir = Paths.get(ConfigFactory.load.getString("appFile.tempDir"), String.format("%1$tY%1$tm%1$td", new Date), orgType, orgId, "OTA").toString
    //解压zip
    zipFileHandler.unzipToPath(zipFile, tempDir)
    //查询产品型号和厂商
    val productsPk = new ProductsPK
    productsPk.firmCode = firmCode
    productsPk.prodCode = prodCode
    val products = productsDao.findOne(productsPk)
    val firm = firmDao.findOne(firmCode)
    //zip文件名
    val zipName = zipFile.getName.substring(0, zipFile.getName.lastIndexOf("."))
    //临时路径
    val tempDirPath = Paths.get(tempDir, zipName).toString
    //获得路径下的所有文件
    val otaFileTuple = otaFileHandler.read(tempDirPath)
    otaInfoHandler.dealOtaZipInfo(otaFileTuple, products, firm, orgType, orgId, tempDirPath)
  }

  /**
    * 保存ota上传信息
    *
    * @param otaInfo otaInfo实体
    * @param orgId   机构号
    * @param orgType 机构类型
    */
  def saveUploadOta(otaInfo: OtaInfo, orgId: String, orgType: String): Unit = {
    //查询所有下级分行
    val orgs = systemHandler.getNextLevOrgs(orgId, orgType)
    //机构(只用到机构号)
    val headOrg = OrgInfo(orgId, orgType, 0, "")
    orgs.add(headOrg)
    val otaHistory = otaInfo.getOtaHistory
    orgs.asScala.foreach(it => {
      val newOtaHistory = new OtaHistory
      BeanUtils.copyProperties(otaHistory, newOtaHistory, "releaseId")
      val curOrgId = it.orgId
      newOtaHistory.setOrgId(curOrgId)
      //保存历史数据
      otaInfoHandler.saveOtaData(newOtaHistory, curOrgId, orgType)
      //更新ota最新版本
      otaInfoHandler.updateOtaFileLatest(newOtaHistory, curOrgId, orgType)
      //上传文件
      val config = ConfigFactory.load()
      val springProfiles = config.getString("springProfiles")
      //判断是否ftp到服务器目录
      if (!config.getBoolean(s"sftp.$springProfiles.isSkip")) {
        otaFileHandler.uploadFile(otaInfo, curOrgId, orgType)
      }
    })
    //删除临时目录以前的数据（非当天的数据）
    val tempFile = Paths.get(otaInfo.getTempDir).toFile
    otaFileHandler.deleteTempFile(tempFile)
  }
}
