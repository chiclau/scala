package com.yada.spos.mag.service.ext

import java.io.{File, FileInputStream, FileOutputStream}
import java.nio.file.Paths
import java.util.{Date, Properties}

import com.yada.spos.db.dao.{OtaFileLatestDao, OtaHistoryDao}
import com.yada.spos.db.model._
import com.yada.spos.mag.exception.UploadException
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.beans.BeanProperty
import scala.collection.convert.decorateAll._
import scala.io.Source

/**
  * Created by pangChangSong on 2016/9/5.
  * ota信息处理
  */
@Component
class OtaInfoHandler {

  @Autowired
  var otaHistoryDao: OtaHistoryDao = _
  @Autowired
  var otaFileLatestDao: OtaFileLatestDao = _
  @Autowired
  var systemHandler: SystemHandler = _

  /**
    * 处理ota上传信息，返回OtaInfo在页面显示
    *
    * @param tuple    ota文件处理元祖
    * @param products 页面选择的产品
    * @param firm     页面选择的厂商
    * @param orgType  机构类型
    * @param orgId    机构号
    * @param tempDir  临时目录
    * @return OtaInfo
    */
  def dealOtaZipInfo(tuple: OtaFileTuple, products: Products, firm: Firm, orgType: String, orgId: String, tempDir: String): OtaInfo = {
    val orgName = systemHandler.getOrg(orgId, orgType).orgName
    val (publishDate, forceUpdate, modeHd, modeHand) = {
      //解析properties文件
      val properties = new Properties()
      val in = new FileInputStream(tuple.propertyFile)
      try {
        properties.load(in)
      } finally {
        if (in != null) in.close()
      }
      (properties.read("OTA.publishDate"),
        properties.read("OTA.forceUpdate"),
        properties.read("OTA.modeHd"),
        properties.read("OTA.modeHand"))
    }
    //解析txt文件
    val remark = if (tuple.desFile == null) {
      ""
    } else {
      val source = Source.fromFile(tuple.desFile, "GBK")
      try {
        source.mkString
      } finally {
        if (source != null) source.close()
      }
    }

    //解析zip文件
    val zipFile = tuple.zipFile
    val fileName = zipFile.getName
    val prodCode = products.getProdCode
    //解析文件名
    val nameSplitAfterArr = fileName.substring(0, fileName.indexOf(".zip")).split("_")
    val nameProdCode: String = nameSplitAfterArr.apply(0)
    val versionStr: String = nameSplitAfterArr.apply(3)
    //判断页面传过来的prodCode和通过zip的名字得到的是否一至
    if (nameProdCode != prodCode) {
      //不一致不能上传
      throw UploadException(s"${fileName.substring(0, fileName.lastIndexOf("."))}.zip文件的产品编号[$nameProdCode]和页面选择的产品编号[$prodCode]不符, 不能上传！")
    } else if (versionStr == null || versionStr.isEmpty || !versionStr.startsWith("V")) {
      //验证zip命名是否规范（版本字符串不能为空且以V开头）
      throw UploadException(s"${zipFile.getName.substring(0, zipFile.getName.lastIndexOf("."))}.zip文件命名不符合规范, 不能上传！")
    } else {
      val otaHis = otaHistoryDao.findByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType(firm.getFirmCode, products.getProdCode, versionStr.substring(1), orgId, orgType)
      if (otaHis != null) throw UploadException(s"机构[$orgName]厂商[${firm.getFirmName}]产品[${products.getProdName}]版本[${versionStr.substring(1)}文件[$fileName]已经存在")
    }
    //设置ota历史信息
    val otaHistory = new OtaHistory
    otaHistory.setFirmCode(firm.getFirmCode)
    otaHistory.setFirmName(firm.getFirmName)
    otaHistory.setForceUpdate(forceUpdate)
    otaHistory.setModeHand(modeHand)
    otaHistory.setModeHd(modeHd)
    otaHistory.setOrgId(orgId)
    otaHistory.setOrgType(orgType)
    otaHistory.setOtaName("固件." + products.getProdCode)
    otaHistory.setOtaPackageName("OTA")
    otaHistory.setProdCode(products.getProdCode)
    otaHistory.setProdName(products.getProdName)
    otaHistory.setPublicDate(publishDate)
    otaHistory.setRemark(remark)
    otaHistory.setVersionName(versionStr.substring(1))
    val otaInfo = new OtaInfo
    otaInfo.setOtaHistory(otaHistory)
    otaInfo.setTempDir(tempDir)
    otaInfo
  }

  /**
    * 保存ota上传文件的数据
    *
    * @param otaHistory ota历史文件信息
    * @param orgId      机构号
    * @param orgType    机构类型
    */
  def saveOtaData(otaHistory: OtaHistory, orgId: String, orgType: String): Unit = {
    //当前时间
    val curTime = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", new Date)
    //保存之前先查询一下看看是否存在了
    val otaHis = otaHistoryDao.findByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType(otaHistory.getFirmCode, otaHistory.getProdCode, otaHistory.getVersionName, orgId, orgType)
    //已经有这条信息了，先删除这条信息
    if (otaHis != null) {
      otaHistoryDao.delete(otaHis)
      otaHistoryDao.flush()
    }
    //保存历史数据
    otaHistory.setUpTime(curTime)
    //查询ota文件最新版本
    val otaFileLatest = otaFileLatestDao.findByFirmCodeAndProdCodeAndOrgIdAndOrgType(otaHistory.getFirmCode, otaHistory.getProdCode, orgId, orgType)
    //获得最新版本
    //如果最新版本存在就取最新版本的最小版本号，否则还没有上传就去当前版本
    val minVersionName = if (otaFileLatest == null) otaHistory.getVersionName else otaFileLatest.getMinVersionName
    otaHistory.setMinVersionName(minVersionName)
    //保存ota历史
    otaHistoryDao.saveAndFlush(otaHistory)
  }

  /**
    * 更新otafile最新版本（更新ota最新版本）
    *
    * @param otaHistory ota历史信息
    * @param orgId      机构
    * @param orgType    机构类型
    */
  def updateOtaFileLatest(otaHistory: OtaHistory, orgId: String, orgType: String): Unit = {
    //查询所有的ota
    val otas = otaHistoryDao.findAll()
    //所有的ota历史信息按：机构类型、机构好、厂商编号、产品类型分组，得到版本序号的集合
    val versionNameMap = getVersionCodeMap(otas)
    //得到最大版本
    val firmCode = otaHistory.getFirmCode
    val prodCode = otaHistory.getProdCode
    val key = orgType + orgId + firmCode + prodCode
    val maxVersionCode = versionNameMap(key).max
    //得到最新版本对ota历史信息
    val maxOtaHistory = otaHistoryDao.findByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType(firmCode, prodCode, maxVersionCode, orgId, orgType)
    //查询最新表的数据
    val otaFileLatest = otaFileLatestDao.findByFirmCodeAndProdCodeAndOrgIdAndOrgType(firmCode, prodCode, orgId, orgType)
    if (otaFileLatest != null) {
      //最新表存在数据
      val otaFileNew = new OtaFileLatest
      //不是最新的先删除，然后插入最新的
      otaFileLatestDao.delete(otaFileLatest)
      otaFileLatestDao.flush()
      //设置最新应用文件表数据,不包括属性releaseId
      BeanUtils.copyProperties(maxOtaHistory, otaFileNew, "releaseId")
      //保存数据
      otaFileLatestDao.saveAndFlush(otaFileNew)
    } else {
      val otaFileNew = new OtaFileLatest
      //设置最新应用文件表数据,不包括属性releaseId
      BeanUtils.copyProperties(maxOtaHistory, otaFileNew, "releaseId")
      //保存数据
      otaFileLatestDao.saveAndFlush(otaFileNew)
    }
  }

  /**
    * 获得版本序号map
    *
    * @param otas AppFileHistory的列表
    * @return map（key：机构类型、机构好、厂商编号、产品类型， value是可以对应的版本序号的列表）
    */
  private def getVersionCodeMap(otas: java.util.List[OtaHistory]): Map[String, List[String]] = {
    otas.asScala.groupBy(ota => {
      //查询所有的历史信息
      val orgType = ota.getOrgType
      val orgId = ota.getOrgId
      val firmCode = ota.getFirmCode
      val prodCode = ota.getProdCode
      orgType + orgId + firmCode + prodCode
    }).map(f => (f._1, f._2.toList.map(v => v.getVersionName)))
  }

  /**
    * 生成MD5文件
    *
    * @param md5Str              md5字符串
    * @param serverFile          服务器文件全路径文件
    * @param zipFileTempPathFile 临时文件路径
    */
  def buildM5File(md5Str: String, serverFile: File, zipFileTempPathFile: File): Unit = {
    //创建md5存放地址的file
    val md5File: File = Paths.get(zipFileTempPathFile.getAbsolutePath, s"${serverFile.getName}.md5").toFile
    //判断是否存在，不存在创建空文件
    if (!md5File.exists) {
      md5File.createNewFile
    }
    val bos = new FileOutputStream(md5File)
    try {
      //将信息写到文件中
      bos.write(md5Str.getBytes("GBK"))
    } finally {
      if (bos != null) bos.close()
    }
  }

  implicit class ReadProperty(properties: Properties) {
    def read(name: String): String = {
      val value = properties.getProperty(name)
      if (value == null && name == "OTA.forceUpdate") throw UploadException(s"缺少属性$name")
      value
    }
  }

}

/**
  * ota上传到临时文件的返回信息(不能定义成case class类型，因为Controller里要作为方法参数)
  */
class OtaInfo {
  /**
    * ota历史信息
    */
  @BeanProperty
  var otaHistory: OtaHistory = _
  /**
    * ota历史信息
    */
  @BeanProperty
  var tempDir: String = _
}


