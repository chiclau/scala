package com.yada.spos.mag.service

import java.io._
import java.nio.file.Paths
import java.util.Date

import com.typesafe.config.ConfigFactory
import com.yada.spos.db.dao.{AppFileHistoryDao, _}
import com.yada.spos.db.model.{AppFileHistory, _}
import com.yada.spos.db.query.AppFileHistoryQuery
import com.yada.spos.mag.service.ext._
import net.dongliu.apk.parser.ApkParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Scope, ScopedProxyMode}
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import scala.collection.convert.decorateAll._
import scala.collection.mutable.ListBuffer

/**
  * Created by duan on 2016/9/2.
  * 设备管理
  */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
class AppFileHistoryService {

  @Autowired
  var appFileHistoryDao: AppFileHistoryDao = _
  @Autowired
  var appFileLatestDao: AppFileLatestDao = _
  @Autowired
  var dictApkPermissionDao: DictApkPermissionDao = _
  @Autowired
  var sFtpService: SFtpService = _
  @Autowired
  var zipFileHandler: ZipFileHandler = _
  @Autowired
  var apkFileTupleHandler: ApkFileTupleHandler = _
  @Autowired
  var apkInfoHandler: AppInfoHandler = _
  @Autowired
  var systemHandler: SystemHandler = _

  /**
    * 保存和更新实体
    *
    * @param appFileHistory 应用实体（历史应用）
    */
  def save(appFileHistory: AppFileHistory): Unit = {
    appFileHistoryDao.saveAndFlush(appFileHistory)
  }

  /**
    * 根据条件查询数据
    *
    * @param appFileHistoryQuery 应用查询类（历史应用）
    * @param pageable            分页
    * @return
    */
  def findAll(appFileHistoryQuery: AppFileHistoryQuery, pageable: Pageable): Page[AppFileHistory] = {
    //设置总行应用包名
    appFileHistoryDao.findAll(appFileHistoryQuery, pageable)
  }

  /**
    * 根据id查信息
    *
    * @param appFileId id
    * @return
    */
  def findOne(appFileId: String): AppFileHistory = {
    appFileHistoryDao.findOne(appFileId.toLong)
  }

  /**
    * 验证是否可以删除
    *
    * @param appFileId 应用文件id
    * @param orgId     机构号
    * @param orgType   机构类型
    * @return isLatestVersion--是最新版本，不能删除，isHeadOrgApp--是总行应用，不能删除
    *         success--可以删除
    */
  def checkAppIsCanDelete(appFileId: String, orgId: String, orgType: String): String = {
    //查询一个文件
    val appFile = appFileHistoryDao.findOne(appFileId.toLong)
    val versionCode = appFile.getVersionCode
    val appPackageName = appFile.getAppPackageName
    //查询最新版本
    val appFileLatest: AppFileLatest = appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(appPackageName, orgId, orgType)
    //判断是否可以删除
    apkInfoHandler.checkAppIsCanDelete(appPackageName, versionCode, orgId, orgType, appFileLatest)
  }

  /**
    * 删除应用文件信息
    *
    * @param appFileId 应用文件id
    * @param orgId     机构号
    * @param orgType   机构类型
    */
  def delete(appFileId: String, orgId: String, orgType: String): Unit = {
    //查询一个文件
    val appFile = appFileHistoryDao.findOne(appFileId.toLong)
    val versionCode = appFile.getVersionCode
    val appPackageName = appFile.getAppPackageName
    //查询最新版本
    val appFileLatest: AppFileLatest = appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(appPackageName, orgId, orgType)
    //判断是总行还是分行
    if ((orgType == AppInfoHandler.epospHeadOrgType && orgId == AppInfoHandler.epospHeadOrgId) ||
      (orgType == AppInfoHandler.hhapHeadOrgType && orgId == AppInfoHandler.hhapHeadOrgId)) {
      //查询总行下的所有分行
      val orgs = systemHandler.getNextLevOrgs(orgId, orgType)
      //只用到机构号
      val org = OrgInfo(orgId, orgType, 0, "")
      orgs.add(org)
      //删除数据
      val allBranchAppHisInfos = orgs.asScala.map(it => {
        //删除版本信息
        //先查询出版本信息
        val newHisApp = appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(appPackageName, versionCode, it.orgId, orgType)
        appFileHistoryDao.delete(newHisApp)
        appFileHistoryDao.flush()
        newHisApp
      })
      //更新最低版本
      if (appFileLatest.getMinVersionCode == versionCode) {
        orgs.asScala.foreach(it => {
          //查询最新版本
          val appFileLatestBranch = appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(appPackageName, it.orgId, orgType)
          //更新最低版本
          updateMinVersion(appPackageName, it.orgId, orgType, appFileLatestBranch)
        })
      }
      allBranchAppHisInfos.foreach(it => {
        //删除文件
        apkFileTupleHandler.deleteFile(it, appPackageName, versionCode)
      })
    } else {
      //分行
      //删除信息
      appFileHistoryDao.delete(appFileId.toLong)
      appFileHistoryDao.flush()
      //更新最低版本
      if (appFileLatest.getMinVersionCode == versionCode) {
        //更新最低版本
        updateMinVersion(appPackageName, orgId, orgType, appFileLatest)
      }
      //删除文件
      apkFileTupleHandler.deleteFile(appFile, appPackageName, versionCode)
    }

  }

  /**
    * 更新最低版本
    */
  private def updateMinVersion(packageName: String, orgId: String, orgType: String, appFileLatest: AppFileLatest): Unit = {
    val appFiles = appFileHistoryDao.findByAppPackageNameAndOrgIdAndOrgType(packageName, orgId, orgType)
    val versionList = ListBuffer.empty[String]
    appFiles.asScala.foreach(f => versionList += f.getVersionCode)
    val minCode = versionList.min
    appFileLatest.setMinVersionCode(minCode)
    //更新最新表
    appFileLatestDao.saveAndFlush(appFileLatest)
    //更新历史表
    appFiles.asScala.foreach(f => {
      f.setMinVersionCode(minCode)
      appFileHistoryDao.saveAndFlush(f)
    })
  }

  /**
    * 上传文件到临时目录
    *
    * @param zipFile zip文件
    * @param orgId   机构号
    * @param orgType 机构类型
    * @return list是应用历史信息和权限说明(新增,删除,已有权限),临时目录
    */
  def uploadAppFileToTempDir(zipFile: File, orgId: String, orgType: String): List[AppInfo] = {
    // 临时目录
    val tempDir = Paths.get(ConfigFactory.load.getString("appFile.tempDir"), String.format("%1$tY%1$tm%1$td", new Date), orgType, orgId, "APP").toString
    zipFileHandler.unzipToPath(zipFile, tempDir)
    //临时路径
    val tempDirPath = Paths.get(tempDir, zipFile.getName.substring(0, zipFile.getName.lastIndexOf("."))).toString
    val apkFileTupleList = apkFileTupleHandler.read(tempDirPath)
    apkFileTupleList.map(it => {
      val apkParser = new ApkParser(it.apkFile)
      try {
        apkInfoHandler.read(it, orgType, orgId, tempDirPath, apkParser)
      } finally {
        if (apkParser != null) {
          //关闭apk解析器
          apkParser.close()
        }
      }
    })
  }

  /**
    * 保存应用上传文件，并上传到服务器
    *
    * @param appInfos appInfos的列表
    * @param orgId    机构号
    * @param orgType  机构类型
    */
  def saveAppFiles(appInfos: List[AppInfo], orgId: String, orgType: String): Unit = {
    //临时目录的目录
    val tempDirFile = Paths.get(appInfos.head.getTempDir).toFile
    //得到当前上传包的、按包名分组的版本map，按包名分组，更新最低版本号
    val versionCodeMap = apkInfoHandler.getVersionCodeMap(appInfos.map(f => f.getAppFileHistory))
    //保存历史数据,加入默认分组
    apkInfoHandler.saveAppFileHistory(appInfos, orgId, orgType, versionCodeMap, tempDirFile)
    //更新最高版本
    apkInfoHandler.updateAppFileLatest(appInfos, orgId, orgType)
    //上传文件
    val config = ConfigFactory.load()
    val springProfiles = config.getString("springProfiles")
    //判断是否ftp到服务器目录
    if (!config.getBoolean(s"sftp.$springProfiles.isSkip")) {
      apkFileTupleHandler.uploadFile(appInfos, tempDirFile)
    }
    //删除临时目录以前的数据（非当天的数据）
    apkFileTupleHandler.deleteTempFile(tempDirFile)
  }

}


