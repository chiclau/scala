package com.yada.spos.mag.service.ext

import java.io.{File, FileInputStream}
import java.nio.charset.MalformedInputException
import java.nio.file.Paths
import java.util
import java.util.{Date, Properties}

import com.typesafe.config.ConfigFactory
import com.yada.spos.db.dao._
import com.yada.spos.db.model.{AppFileHistory, AppFileLatest, AppGroupApps, DictApkPermission}
import com.yada.spos.mag.exception.UploadException
import com.yada.spos.mag.util.SimpleDigest
import net.dongliu.apk.parser.ApkParser
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.beans.BeanProperty
import scala.collection.convert.decorateAll._
import scala.io.Source

@Component
class AppInfoHandler {
  @Autowired
  var systemHandler: SystemHandler = _
  @Autowired
  var appFileHistoryDao: AppFileHistoryDao = _
  @Autowired
  var appFileLatestDao: AppFileLatestDao = _
  @Autowired
  var dictApkPermissionDao: DictApkPermissionDao = _
  @Autowired
  var apkIconHandler: ApkIconHandler = _
  @Autowired
  var appGroupDao: AppGroupDao = _
  @Autowired
  var appGroupAppsDao: AppGroupAppsDao = _
  @Autowired
  var apkFileTupleHandler: ApkFileTupleHandler = _
  @Autowired
  var apkSign: ApkSign = _


  /**
    * zip包信息的读取，组装页面的展示信息
    *
    * @param tuple     tuple
    * @param orgType   机构类型
    * @param orgId     机构号
    * @param tempDir   临时目录
    * @param apkParser apk解析器
    * @return AppInfo
    */
  def read(tuple: ApkFileTuple, orgType: String, orgId: String, tempDir: String, apkParser: ApkParser): AppInfo = {
    val apkMeta = apkParser.getApkMeta
    //获得包名，版本号和权限列表
    val packageName = apkMeta.getPackageName
    val versionCode = apkMeta.getVersionCode.toString
    val apkPermissions = apkMeta.getUsesPermissions
    var orgTypeString = ""
    if ("1".equals(orgType)) {
      orgTypeString = "固话"
    } else if ("2".equals(orgType)) {
      orgTypeString = "总对总"
    }
    val orgName = systemHandler.getOrg(orgId, orgType).orgName
    //验证是否能上传应用
    val result = checkAppIsUpload(packageName, versionCode, orgId, orgType)
    if ("2".equals(result))
      throw UploadException(s"机构类型[$orgTypeString]机构[$orgName]包名[$packageName]版本[$versionCode]应用已经存在，不可上传")
    else if ("3".equals(result))
      throw UploadException(s"机构类型[$orgTypeString]机构[$orgName]包名[$packageName]总行或其他分行已上传过，不可上传")
    else if ("4".equals(result))
      throw UploadException(s"机构类型[$orgTypeString]机构[$orgName]包名[$packageName]]版本[$versionCode]有分行已经上传过，不可上传")
    //加载properties文件到内存
    val properties = new Properties()
    val propertyFile = tuple.propertyFile
    val in = new FileInputStream(propertyFile)
    try {
      properties.load(in)
    } finally {
      if (in != null) in.close()
    }
    //读取txt信息
    val remark = if (tuple.desFile == null) {
      ""
    } else {
      var source: Source = null
      try {
        source = Source.fromFile(tuple.desFile, "GBK")
        val tempRemark = source.mkString
        if (tempRemark.length > 105) throw UploadException(s"[${tuple.apkName}.txt]说明的字段过长，请缩减")
        else tempRemark
      } catch {
        case e: MalformedInputException => throw UploadException(s"[${tuple.apkName}.txt]文件解析有误，请检查文件是否符合规范")
      } finally {
        if (source != null) source.close()
      }
    }
    //生成icon文件
    apkIconHandler.iconToFile(apkParser.getIconFile, tempDir, s"${packageName}___$versionCode")
    //设置应用历史信息
    val appFileHistory = new AppFileHistory
    appFileHistory.setAppName(apkMeta.getLabel)
    appFileHistory.setAppPackageName(packageName)
    appFileHistory.setVersionCode(versionCode)
    appFileHistory.setVersionName(apkMeta.getVersionName)
    appFileHistory.setOrgType(orgType)
    appFileHistory.setOrgId(orgId)
    appFileHistory.setFileLength(tuple.apkFile.length)
    appFileHistory.setPublicDate(properties.read(packageName, "publishDate", propertyFile))
    appFileHistory.setDeleteUpdate(properties.read(packageName, "deleteUpdate", propertyFile))
    appFileHistory.setForceUpdate(properties.read(packageName, "forceUpdate", propertyFile))
    appFileHistory.setModeHd(properties.read(packageName, "modeHd", propertyFile))
    appFileHistory.setModeHand(properties.read(packageName, "modeHand", propertyFile))
    appFileHistory.setRemark(remark)
    if (apkPermissions != null && !apkPermissions.isEmpty)
      appFileHistory.setAppPermission(apkPermissions.asScala.reduce((a, b) => a + "," + b))
    //服务器路径
    //读取配置文件
    val config = ConfigFactory.load()
    val serverPathName = s"${config.getString("appFile.serverPathStartWith")}/$orgType/$orgId/APP/$packageName/$versionCode.apk"
    appFileHistory.setFileName(serverPathName)
    //设置每个应用到应用上传历史表实体，放到集合里传到页面
    //根据包名查询最新版本
    val appFileLatest = appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(packageName, orgId, orgType)
    val (existPermissions, reducesPermissions, insertsPermissions) = if (appFileLatest != null && appFileLatest.getAppPermission != null) {
      //得到最新版本的权限
      val oldPermissions = appFileLatest.getAppPermission.split(",").toList
      val newPermissions = apkPermissions.asScala.toList
      //得到最新版本和当前版本的交集
      val intersect = oldPermissions intersect newPermissions
      (intersect.toDictApkPermissions, (oldPermissions diff intersect).toDictApkPermissions, (newPermissions diff intersect).toDictApkPermissions)
    } else {
      (List.empty[DictApkPermission], List.empty[DictApkPermission], apkPermissions.asScala.toList.toDictApkPermissions)
    }
    val appInfo = new AppInfo
    appInfo.setAppName(tuple.apkName)
    appInfo.setAppFileHistory(appFileHistory)
    appInfo.setTempDir(tempDir)
    appInfo.setExistPermissions(existPermissions.asJava)
    appInfo.setInsertPermissions(insertsPermissions.asJava)
    appInfo.setReducePermissions(reducesPermissions.asJava)
    appInfo
  }

  /**
    * 判断该应用总行是否上传，总行上传，分行就不能上传
    *
    * @param packageName 包名
    * @param orgId       机构号
    * @param orgType     机构类型
    * @return "1"-可上传/"2"-本行已上传该版本的应用，不可上传/"3"-总行或其他分行已上传过，不可上传/"4"-总行上传的版本有分行已上传过，不可上传
    */
  private def checkAppIsUpload(packageName: String, versionCode: String, orgId: String, orgType: String): String = {
    //判断app历史表是否存在当前记录，存在返回
    val appFileHis = appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(packageName, versionCode, orgId, orgType)
    if (appFileHis != null) "2"
    else if (orgType == AppInfoHandler.epospHeadOrgType && orgId != AppInfoHandler.epospHeadOrgId) {
      //固话分行
      val appFileHistorys = appFileHistoryDao.findByAppPackageNameAndOrgTypeAndOrgIdNot(packageName, orgType, orgId)
      if (appFileHistorys != null && appFileHistorys.asScala.nonEmpty) "3"
      else "1"
    } else if (orgType == AppInfoHandler.hhapHeadOrgType && orgId != AppInfoHandler.hhapHeadOrgId) {
      //总对总分行
      val appFileHistorys = appFileHistoryDao.findByAppPackageNameAndOrgTypeAndOrgIdNot(packageName, orgType, orgId)
      if (appFileHistorys != null && appFileHistorys.asScala.nonEmpty) "3"
      else "1"
    } else {
      //总行
      val appFileHistorys = appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgTypeAndOrgIdNot(packageName, versionCode, orgType, orgId)
      if (appFileHistorys != null && appFileHistorys.asScala.nonEmpty) "4"
      else "1"
    }

  }

  /**
    * 验证是否能删除应用
    *
    * @param appPackageName 包名
    * @param versionCode    版本号
    * @param orgId          机构
    * @param orgType        机构类型
    * @param appFileLatest  最新版本
    * @return isLatestVersion--是最新版本，不能删除，isHeadOrgApp--是总行应用，不能删除
    *         success--可以删除
    */
  def checkAppIsCanDelete(appPackageName: String, versionCode: String, orgId: String, orgType: String, appFileLatest: AppFileLatest): String = {
    if ((orgType == AppInfoHandler.epospHeadOrgType && orgId == AppInfoHandler.epospHeadOrgId) ||
      (orgType == AppInfoHandler.hhapHeadOrgType && orgId == AppInfoHandler.hhapHeadOrgId)) {
      //总行登录
      if (appFileLatest.getVersionCode == versionCode) {
        //最新应用，不能删除
        "isLatestVersion"
      } else {
        "success"
      }
    } else if (orgType == AppInfoHandler.hhapHeadOrgType && orgId != AppInfoHandler.hhapHeadOrgId) {
      //总对总分行
      //是否是总行应用
      val appFileLatestHead = appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(appPackageName, versionCode, AppInfoHandler.hhapHeadOrgId, orgType)
      if (appFileLatestHead != null) {
        //总行上传应用，不能删除
        "isHeadOrgApp"
      } else if (appFileLatest.getVersionCode == versionCode) {
        //最新应用，不能删除
        "isLatestVersion"
      } else {
        "success"
      }
    } else {
      //固话分行
      //是否是总行应用
      val appFileLatestHead = appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(appPackageName, versionCode, AppInfoHandler.epospHeadOrgId, orgType)
      if (appFileLatestHead != null) {
        //总行上传应用，不能删除
        "isHeadOrgApp"
      } else if (appFileLatest.getVersionCode == versionCode) {
        //最新应用，不能删除
        "isLatestVersion"
      } else {
        "success"
      }
    }
  }


  /**
    * 保存历史数据，并添加到默认分组
    *
    * @param appInfos       应用信息列表
    * @param orgId          机构号
    * @param orgType        机构类型
    * @param versionCodeMap 版本序号map（key是包名+机构号+机构类型，value是版本序号列表）
    * @param tempDirFile    临时目录文件
    */
  def saveAppFileHistory(appInfos: List[AppInfo], orgId: String, orgType: String, versionCodeMap: Map[String, List[String]], tempDirFile: File): Unit = {
    val tempDir = tempDirFile.getAbsolutePath
    //当前时间
    val curTime = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", new Date)
    val orgs = if ((orgType == AppInfoHandler.epospHeadOrgType && orgId == AppInfoHandler.epospHeadOrgId) ||
      (orgType == AppInfoHandler.hhapHeadOrgType && orgId == AppInfoHandler.hhapHeadOrgId)) {
      //将数据插入到所有分行和总行
      systemHandler.getNextLevOrgs(orgId, orgType)
    } else null
    appInfos.foreach(app => {
      val appFileHistory = app.getAppFileHistory
      //得到包名和版本号
      val packageName = appFileHistory.getAppPackageName
      val versionCode = appFileHistory.getVersionCode
      val apkName = app.getAppName
      //查询最新表的数据
      val appFileLatest = appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(packageName, orgId, orgType)
      //得到最小版本号
      //最新表不存在，取上传集合的最小值,因为最新表中没有数据，所以数据库中不存在这个应用
      val minVersionCode = if (appFileLatest != null) appFileLatest.getMinVersionCode
      else {
        //得到上传列表版本的最小值（按包名分组）
        val key = packageName + orgId + orgType
        versionCodeMap(key).min
      }
      //设置最小版本是最新表中的最小版本
      appFileHistory.setMinVersionCode(minVersionCode)
      //设置当前时间
      appFileHistory.setCreTime(curTime)
      //apk文件签名
      val path = Paths.get(tempDir, s"$apkName.apk")
      val newFile = apkSign.sign(path).toFile
      //apk文件md5加密
      appFileHistory.setFileMd5(new SimpleDigest().doDigest(new FileInputStream(newFile), "MD5"))
      //保存数据
      appFileHistoryDao.saveAndFlush(appFileHistory)
      //应用关联到默认的应用分组
      appAssociatedToDefaultGroup(appFileHistory)
      //md5文件加密，生成md5文件，放到放到临时目录下
      val fileName = new File(appFileHistory.getFileName)
      apkFileTupleHandler.buildM5File(appFileHistory.getFileMd5, fileName, tempDirFile)
      if ((orgType == AppInfoHandler.epospHeadOrgType && orgId == AppInfoHandler.epospHeadOrgId) ||
        (orgType == AppInfoHandler.hhapHeadOrgType && orgId == AppInfoHandler.hhapHeadOrgId)) {
        //总行不仅要插入本机构，还要插入分行
        orgs.asScala.foreach(f => {
          //复制appFileHistory到新的对象,忽略复制属性appFileId
          val newAppFileHistory = new AppFileHistory
          BeanUtils.copyProperties(appFileHistory, newAppFileHistory, "appFileId")
          //各个分行的文件的全路径和机构
          //读取配置文件
          val config = ConfigFactory.load()
          val curOrgId = f.orgId
          val fileNamePath = s"${config.getString("appFile.serverPathStartWith")}/$orgType/$curOrgId/APP/$packageName/$versionCode.apk"
          newAppFileHistory.setFileName(fileNamePath)
          newAppFileHistory.setOrgId(curOrgId)
          //判断是否存在数据，存在数据删除
          val branchAppFileHistory = appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(packageName, versionCode, f.orgId, orgType)
          if (branchAppFileHistory != null) {
            appFileHistoryDao.delete(branchAppFileHistory)
            appFileHistoryDao.flush()
          }
          //保存数据
          appFileHistoryDao.saveAndFlush(newAppFileHistory)
          //上传应用加入到默认的应用分组中(分行的)
          appAssociatedToDefaultGroup(newAppFileHistory)
          val fileName = new File(newAppFileHistory.getFileName)
          apkFileTupleHandler.buildM5File(newAppFileHistory.getFileMd5, fileName, tempDirFile)
        })
      }
    })
  }

  /**
    * 应用关联到默认的应用分组，首先判断是否关联默认分组，关联就不再关联
    *
    * @param appFileHistory 应用文件历史实体
    */
  def appAssociatedToDefaultGroup(appFileHistory: AppFileHistory): Unit = {
    val appPackageName = appFileHistory.getAppPackageName
    val orgId = appFileHistory.getOrgId
    val orgType = appFileHistory.getOrgType
    val appName = appFileHistory.getAppName
    //判断应用是否关联分组，关联就不添加，上传应用加入到默认的应用分组中
    val appGroupAppsOld = appGroupAppsDao.findByAppPackageNameAndAppGroupIsDefaultGroupAndAppGroupOrgIdAndAppGroupOrgType(appPackageName, "0", orgId, orgType)
    if (appGroupAppsOld == null) {
      //上传应用加入到默认的应用分组中 0-默认分组
      val appGroup = appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup(orgId, orgType, "0")
      val appGroupApps = new AppGroupApps
      appGroupApps.setAppGroup(appGroup)
      appGroupApps.setAppGroupName(appGroup.getAppGroupName)
      appGroupApps.setAppName(appName)
      appGroupApps.setAppPackageName(appPackageName)
      appGroupAppsDao.saveAndFlush(appGroupApps)
    }
  }

  /**
    * 更新应用文件的最新版本数据
    *
    * @param appInfos 应用信息列表
    * @param orgId    机构号
    * @param orgType  机构类型
    */
  def updateAppFileLatest(appInfos: List[AppInfo], orgId: String, orgType: String): Unit = {
    val orgs = if ((orgType == AppInfoHandler.epospHeadOrgType && orgId == AppInfoHandler.epospHeadOrgId) ||
      (orgType == AppInfoHandler.hhapHeadOrgType && orgId == AppInfoHandler.hhapHeadOrgId)) {
      //总行
      //查询下一级的所有机构
      systemHandler.getNextLevOrgs(orgId, orgType)
    } else new util.ArrayList[OrgInfo]
    //只用到机构号，其他的值随意
    val orgInfo = OrgInfo(orgId, orgType, 0, "")
    orgs.add(orgInfo)
    appInfos.groupBy(it => {
      it.getAppFileHistory.getAppPackageName
    }).foreach(it => {
      val appPackageName = it._1
      //得到数据库所有这个应用的“应用文件历史信息”
      val appFileHistorys = appFileHistoryDao.findByAppPackageNameAndOrgIdAndOrgType(appPackageName, orgId, orgType)
      val versionCodeMap = getVersionCodeMap(appFileHistorys.asScala.toList)
      //获得最高版本
      val key = appPackageName + orgId + orgType
      val maxVersionCode = versionCodeMap(key).max
      orgs.asScala.foreach(org => {
        //保存最新应用文件表数据
        appFileLatestUpdate(appPackageName, org.orgId, orgType, maxVersionCode)
      })
    })
  }


  /**
    * 更新应用文件最新版本
    *
    * @param appPackageName 包名
    * @param orgId          机构
    * @param orgType        机构类型
    * @param maxVersionCode 最新版本号
    */
  private def appFileLatestUpdate(appPackageName: String, orgId: String, orgType: String,
                                  maxVersionCode: String): Unit = {
    val appFileLatestNew = new AppFileLatest
    //查询历史标的最大版本号
    val maxAppFileHistory = appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(appPackageName, maxVersionCode, orgId, orgType)
    //根据包名得到最新版本
    val appFileLatest = appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(appPackageName, orgId, orgType)
    if (appFileLatest != null) {
      //只要存在就删除，因为maxAppFileHistory是最新版本，不然总行上传可能存在bug
      //总行上传时更新分行数据，有可能是总行上收分行的应用，如果这样应该版本统一，所以要删除
      //删除原来的最高版本
      appFileLatestDao.delete(appFileLatest)
      appFileLatestDao.flush()
    }
    //保存最新应用文件表数据
    BeanUtils.copyProperties(maxAppFileHistory, appFileLatestNew, "appFileId")
    appFileLatestDao.saveAndFlush(appFileLatestNew)
  }

  /**
    * 获得版本序号map
    *
    * @param apps AppFileHistory的列表
    * @return map（key：包名，机构号，机构类型， value是可以对应的版本序号的列表）
    */
  def getVersionCodeMap(apps: List[AppFileHistory]): Map[String, List[String]] = {
    apps.groupBy(app => {
      //查询所有的历史信息
      val packageName = app.getAppPackageName
      val orgId = app.getOrgId
      val orgType = app.getOrgType
      packageName + orgId + orgType
    }).map(f => (f._1, f._2.map(v => v.getVersionCode)))
  }


  implicit class ReadProperty(properties: Properties) {
    def read(packageName: String, name: String, propertyFile: File): String = {
      val fullName = s"$packageName.$name"
      val value = properties.getProperty(fullName)
      if (value == null && name != "publishDate") throw UploadException(s"文件[${propertyFile.getName}]缺少属性[$fullName]")
      value
    }
  }

  implicit class ToPermissions(list: List[String]) {
    def toDictApkPermissions: List[DictApkPermission] = {
      list
        .filter(it => dictApkPermissionDao.findByApkPermissionCode(it) != null)
        .map(it => dictApkPermissionDao.findByApkPermissionCode(it))
    }
  }

}

/**
  * 应用信息
  */
class AppInfo {
  /**
    * 应用名称
    */
  @BeanProperty
  var appName: String = _
  /**
    * 已有权限
    */
  @BeanProperty
  var existPermissions: java.util.List[DictApkPermission] = _
  /**
    * 新增权限
    */
  @BeanProperty
  var insertPermissions: java.util.List[DictApkPermission] = _
  /**
    * 减少权限
    */
  @BeanProperty
  var reducePermissions: java.util.List[DictApkPermission] = _
  /**
    * 应用历史信息
    */
  @BeanProperty
  var appFileHistory: AppFileHistory = _
  /**
    * 历史目录
    */
  @BeanProperty
  var tempDir: String = _
}

object AppInfoHandler extends AppInfoHandler {

  /**
    * 总对总总行机构号
    */
  val hhapHeadOrgId = "000"

  /**
    * 总对总机构类型
    */
  val hhapHeadOrgType = "2"

  /**
    * 固话总行机构号
    */
  val epospHeadOrgId = "00"

  /**
    * 固话机构类型
    */
  val epospHeadOrgType = "1"

}