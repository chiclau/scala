package com.yada.spos.mag.service.ext

import java.io.{File, FileOutputStream}
import java.nio.file.Paths
import java.util

import com.typesafe.config.ConfigFactory
import com.yada.spos.db.model.AppFileHistory
import com.yada.spos.mag.exception.UploadException
import com.yada.spos.mag.service.SFtpService
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.convert.decorateAll._

/**
  * APP文件处理
  */
@Component
class ApkFileTupleHandler {

  @Autowired
  var sFtpService: SFtpService = _
  @Autowired
  var systemHandler: SystemHandler = _

  def read(dir: String): List[ApkFileTuple] = {
    val tempFileList = Paths.get(dir).toFile.listFiles()
    if (tempFileList == null || tempFileList.isEmpty) throw UploadException("上传zip文件为空，不能上传！")
    tempFileList.groupBy(it => {
      val name = it.getName
      val p = name.lastIndexOf(".")
      name.substring(0, p)
    })
      .map(it => {
        val (preName, files) = it
        val apk = files.findByName(s"$preName.apk")
        val property = files.findByName(s"$preName.properties")
        val des = try {
          //txt不是必须的
          files.findByName(s"$preName.txt")
        } catch {
          case e: UploadException => null
        }
        ApkFileTuple(preName, apk, property, des)
      }).toList
  }

  /**
    * 上传文件
    *
    * @param appInfos    应用信息列表
    * @param tempDirFile 临时目录文件
    */
  def uploadFile(appInfos: List[AppInfo], tempDirFile: File): Unit = {
    appInfos.foreach(a => {
      //ftp文件的服务器
      //上传到临时目录和保存数据库都会判断是否存在该条记录，存在会放到skipApkOrg集合，因此skipApkOrg集合的数据不保存到数据库和上传到服务器
      //得到当前机构和当前机构类型和文件名
      val appFileHistory = a.getAppFileHistory
      val curOrgId = appFileHistory.getOrgId
      val curOrgType = appFileHistory.getOrgType
      val fileName = appFileHistory.getFileName
      val appPackageName = appFileHistory.getAppPackageName
      val versionCode = appFileHistory.getVersionCode
      //重命名文件
      renameTo(tempDirFile, a.getAppName, s"${appPackageName}___$versionCode")
      //根据包名查找上传文件
      val fileArr = findUploadFileByPackageName(tempDirFile, s"${appPackageName}___$versionCode")
      //sftp上传文件
      sftpUploadFile(fileArr.asScala.toList, curOrgId, curOrgType, fileName, appPackageName, versionCode)
      //判断是不是总行机构，是的话，要继续上传分行的
      if ((curOrgType == AppInfoHandler.epospHeadOrgType && curOrgId == AppInfoHandler.epospHeadOrgId) ||
        (curOrgType == AppInfoHandler.hhapHeadOrgType && curOrgId == AppInfoHandler.hhapHeadOrgId)) {
        //总行不仅要上传本身的还有上传分行的
        //查询出下一级的所有分行
        val orgs = systemHandler.getNextLevOrgs(curOrgId, curOrgType)
        orgs.asScala.foreach(f => {
          val curOrgId1 = f.orgId
          val curOrgType1 = f.orgType
          //sftp上传文件
          sftpUploadFile(fileArr.asScala.toList, curOrgId1, curOrgType1, fileName, appPackageName, versionCode)
        })
      }
    })
  }

  /**
    * 删除临时目录的当天以前的数据
    *
    * @param tempFile 临时目录文件
    */
  def deleteTempFile(tempFile: File): Unit = {
    if (tempFile != null && tempFile.exists()) {
      //删除临时目录下zip目录
      //D:\SPOS\UP_LOAD\TEMP\20161013\2\000011\APP\12
      val delFile = tempFile.getParentFile.getParentFile.getParentFile.getParentFile
      val delName = delFile.getName
      delFile.getParentFile.listFiles().foreach(it => {
        val fileName = it.getName
        if (delName.compareTo(fileName) > 0) {
          //删除非当天的所有文件
          FileUtils.forceDelete(it)
        }
      })
    }
  }

  /**
    * 删除文件
    *
    * @param appFileHistory 应用文件历史信息
    * @param appPackageName 包名
    * @param versionCode    版本序号
    */
  def deleteFile(appFileHistory: AppFileHistory, appPackageName: String, versionCode: String): Unit = {
    val newFileName = appFileHistory.getFileName
    val desDir = newFileName.substring(0, newFileName.lastIndexOf("/") + 1)
//    val packNameVersion = appPackageName + "_" + versionCode
    //删除文件列表
    val delFiles = Array(versionCode + ".apk", versionCode + ".txt", versionCode + ".properties", versionCode + ".apk.md5", versionCode + "_icon.png")
    //上传删除文件
    val config = ConfigFactory.load()
    val springProfiles = config.getString("springProfiles")
    val sFtps = sFtpService.getSFtps
    if (!config.getBoolean(s"sftp.$springProfiles.isSkip")) {
      sFtps.foreach(f => {
        sFtpService.delete(f, desDir, delFiles)
      })
    }
  }

  /**
    * 根据包名查找上传文件
    *
    * @param tempDirFile    临时目录文件
    * @param appPackageName 包名
    * @return 上传文件集合
    */
  def findUploadFileByPackageName(tempDirFile: File, appPackageName: String): java.util.List[File] = {
    val list = new util.ArrayList[File]
    tempDirFile.listFiles().foreach(f => {
      val fileName = f.getName
      if (fileName.startsWith(appPackageName)) {
        list.add(f)
      }
    })
    list
  }

  /**
    * 抽取的sftp上传的共有方法
    *
    * @param uploadFileArr  上传文件列表
    * @param fileName       apk全路径名
    * @param appPackageName 应用包名
    * @param versionCode    版本号
    */
  private def sftpUploadFile(uploadFileArr: List[File], orgId: String, orgType: String, fileName: String, appPackageName: String, versionCode: String): Unit = {
    //读取配置文件
    val config = ConfigFactory.load()
    //上传到ftp目录
    val path = s"${config.getString("appFile.serverPathStartWith")}/$orgType/$orgId/APP/$appPackageName/"
    val sftps = sFtpService.getSFtps
    //响应的应用放到响应的目录
    sftps.foreach(sftp => {
      //上传path目录下的所有文件
      sFtpService.upload(sftp, uploadFileArr.toArray, path, "02")
    })
  }

  /**
    * 重命名文件
    *
    * @param tempDirFile 临时目录文件
    * @param apkName     文件名
    * @param newFileName 包名_版本号
    */
  def renameTo(tempDirFile: File, apkName: String, newFileName: String): Unit = {
    tempDirFile.listFiles().foreach(f => {
      val fileName = f.getName
      val index = fileName.lastIndexOf(".")
      if (fileName.substring(0, index) == apkName) {
        val extName = fileName.substring(index)
        //重命名文件
        val newFile = Paths.get(f.getParent, newFileName + extName).toFile
        f.renameTo(newFile)
        //删除源文件
        f.delete()
      }
    })
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
    val md5File: File = new File(zipFileTempPathFile.getAbsolutePath + File.separator + serverFile.getName + ".md5")
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

  implicit class FindFileByName(files: Array[File]) {
    def findByName(name: String): File = {
      files.find(it => it.getName == name) match {
        case Some(x) => x
        case None => throw UploadException(s"上传的zip文件中缺少文件$name")
      }
    }
  }


}

case class ApkFileTuple(apkName: String, apkFile: File, propertyFile: File, desFile: File)
