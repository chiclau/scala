package com.yada.spos.mag.service.ext

import java.io.{File, FileInputStream}
import java.nio.file.Paths

import com.typesafe.config.ConfigFactory
import com.yada.spos.mag.exception.UploadException
import com.yada.spos.mag.service.SFtpService
import com.yada.spos.mag.util.SimpleDigest
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
  * Created by pangChangSong on 2016/9/5.
  * ota上传处理
  */
@Component
class OtaFileHandler {

  @Autowired
  var sFtpService: SFtpService = _
  @Autowired
  var zipFileHandler: ZipFileHandler = _
  @Autowired
  var otaInfoHandler: OtaInfoHandler = _

  //读取临时目录文件到元祖
  def read(dir: String): OtaFileTuple = {
    val files = Paths.get(dir).toFile.listFiles()
    if (files == null || files.isEmpty) throw UploadException("上传zip文件为空，不能上传！")
    val propertyFile = files.findByExtName("properties")
    //txt不是必须的
    val desFile = try {
      files.findByExtName("txt")
    } catch {
      case e: UploadException => null
    }
    val zip = files.findByExtName("zip")
    OtaFileTuple(zip, propertyFile, desFile)
  }

  /**
    * 上传文件的服务器
    *
    * @param otaInfo otaInfo
    * @param orgId   机构id
    * @param orgType 机构类型
    */
  def uploadFile(otaInfo: OtaInfo, orgId: String, orgType: String): Unit = {
    val tempDir = otaInfo.getTempDir
    //得到文件名前置
    val zipFileName = getPreFileName(tempDir)
    //获取目标版本
    val otaHistory = otaInfo.getOtaHistory
    val targetVersion = otaHistory.getVersionName
    //得到中间版本,和中间版本的文件名
    Paths.get(tempDir, zipFileName).toFile.listFiles().foreach(it => {
      val fileName = it.getName
      val (interimVersion, interimVersionFileName) = if (fileName.startsWith("V")) {
        (fileName.substring(1), fileName)
      } else {
        (fileName, fileName)
      }
      //得到新的临时目录
      val tempDirFile = Paths.get(tempDir, zipFileName, interimVersionFileName).toFile
      tempDirFile.listFiles().foreach(it => {
        //生成md5文件
        val in = new FileInputStream(it)
        try {
          val md5Str = new SimpleDigest().doDigest(in, "MD5")
          otaInfoHandler.buildM5File(md5Str, it, tempDirFile)
        } finally {
          if (in != null) in.close()
        }
      })
      //sftp上传文件
      sftpUploadFile(tempDirFile, orgId, orgType, otaHistory.getFirmCode, otaHistory.getProdCode, targetVersion, interimVersion)
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
      //D:\SPOS\UP_LOAD\TEMP\20161013\2\000011\OTA\12
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
    * 得到文件名前置
    *
    * @param tempDir 临时目录
    * @return 文件名前置
    */
  private def getPreFileName(tempDir: String): String = {
    val zipFileNames = Paths.get(tempDir).toFile.listFiles().groupBy(it => {
      if (it.getName.endsWith(".zip")) {
        //解压zip
        zipFileHandler.unzipToPath(it, tempDir)
      }
      //按后缀分组
      it.getName.endsWith(".zip")
    })
    val fileName = zipFileNames(true).apply(0).getName
    fileName.substring(0, fileName.lastIndexOf("."))
  }

  /**
    * 抽取的sftp上传的共有方法
    *
    * @param tempDirFile    临时目录
    * @param orgId          机构号
    * @param orgType        机构类型
    * @param targetVersion  目标版本
    * @param interimVersion 过往版本
    */
  private def sftpUploadFile(tempDirFile: File, orgId: String, orgType: String, firmCode: String, prodCode: String, targetVersion: String, interimVersion: String): Unit = {
    //读取配置文件
    val config = ConfigFactory.load()
    //上传到ftp目录
    val path = s"${config.getString("appFile.serverPathStartWith")}/$orgType/$orgId/$firmCode/$prodCode/OTA/$targetVersion/$interimVersion"
    val sftps = sFtpService.getSFtps
    //跳过的数据不为空，且存在该条数据，上传目录的部分文件到服务器
    val files = tempDirFile.listFiles().map(it => {
      //值上传zip和md5文件
      if (it.getName.endsWith(".zip") || it.getName.endsWith(".md5")) it
      else null
    })
    sftps.foreach(sftp => {
      //上传path目录下的所有文件
      sFtpService.upload(sftp, files, path, "01")
    })
  }

  implicit class FileExistByExtName(files: Array[File]) {

    def findByExtName(extName: String): File = {
      files.find(it => {
        val name = it.getName
        val p = name.lastIndexOf(".")
        name.substring(p + 1) == extName
      }) match {
        case Some(x) => x
        case None => throw UploadException(s"上传zip文件不存在后缀是[$extName]的文件")
      }
    }
  }

}

/**
  * ota上传文件解析的元祖
  *
  * @param zipFile      zip文件
  * @param propertyFile properties文件
  * @param desFile      txt文件
  */
case class OtaFileTuple(zipFile: File, propertyFile: File, desFile: File)

