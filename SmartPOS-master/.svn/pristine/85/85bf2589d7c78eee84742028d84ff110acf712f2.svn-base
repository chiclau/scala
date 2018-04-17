package com.yada.spos.mag.service

import java.io.{File, FileInputStream}
import java.util.Properties

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch._
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.mag.exception.UploadException
import org.springframework.stereotype.Service

import scala.collection.convert.decorateAll._

/**
  * Created by pangChangSong on 2016/9/3.
  * 文件上传fpt管理类
  */
@Service
class SFtpService extends LazyLogging {
  val config = ConfigFactory.load()
  val springProfiles = config.getString("springProfiles")
  val sftpIps = config.getString(s"sftp.$springProfiles.ip").split(";")
  val sftpPort = config.getInt(s"sftp.$springProfiles.port")
  val sftpUsernames = config.getString(s"sftp.$springProfiles.username").split(";")
  val sftpPasswords = config.getString(s"sftp.$springProfiles.password").split(";")
  val sftpIsSkips = config.getBoolean(s"sftp.$springProfiles.isSkip")

  def getSFtps: List[SFtp] = {
    val sftps = new java.util.ArrayList[SFtp]
    val len = sftpIps.length - 1
    for (i <- 0.to(len)) {
      sftps.add(SFtp(sftpIps.apply(i), sftpPort, sftpUsernames.apply(i), sftpPasswords.apply(i), sftpIsSkips))
    }
    sftps.asScala.toList
  }

  /**
    * 上传文件目录
    *
    * @param sftp        ftp实体
    * @param uploadFiles 上传文件列表
    * @param desDir      远程文件目录可以是绝对目录，也可以是相对于sftp的directory的相对目录
    * @param upType      上传包类型  01-OTA 02-APK
    */
  def upload(sftp: SFtp, uploadFiles: Array[File], desDir: String, upType: String) {
    var channel: ChannelSftp = null
    var session: Session = null
    try {
      //获得链接
      val connect = open(sftp)
      channel = connect._1
      session = connect._2
      //创建目录
      if ("02".equals(upType)) {
        mkDirs(desDir, channel)
      } else {
        //得到OTA级的目录
        val parentPath = desDir.substring(0, desDir.lastIndexOf("/"))
        val topPath = parentPath.substring(0, parentPath.lastIndexOf("/"))
        mkDirs(topPath, channel)
        val oldTargetVersionFiles = channel.ls("/" + topPath)
        //判断是否存在目标版本的目录
        if (oldTargetVersionFiles != null && oldTargetVersionFiles.size() != 0) {
          val oldTargetVersion = oldTargetVersionFiles.firstElement().toString
          val oldTargetVersionFileName = oldTargetVersion.substring(oldTargetVersion.lastIndexOf(" ") + 1)
          val oldPath = topPath + "/" + oldTargetVersionFileName
          //判断是否需要修改目标版本的目录名
          if (!oldPath.equals(parentPath)){
            channel.cd("/")
            channel.rename(oldPath, parentPath)
          }
          //创建过往版本目录
          channel.cd("/")
          mkDirs(desDir, channel)
        } else {
          channel.cd("/")
          mkDirs(desDir, channel)
        }
      }
      logger.info(s"创建目录$desDir")
      uploadFiles.foreach(f => {
        if (f != null) {
          //上传到当前目录
          logger.info(s"上传文件[${f.getName}]")
          val in = new FileInputStream(f)
          try {
            //上传apk文件时，修改文件命名为版本号
            if ("02".equals(upType)) {
              val sourceName = f.getName
              val index = sourceName.lastIndexOf("___") + 3
              val upFileName = sourceName.substring(index)
              channel.put(in, s"${channel.pwd()}/$upFileName")
            }
            //OTA正常上传
            else {
              println(channel.pwd())
              channel.put(in, s"${channel.pwd()}/${f.getName}")
            }
          } finally {
            if (in != null) in.close()
          }
          logger.info(s"上传文件[${f.getName}]成功")
        }
      })
    } catch {
      case e: Throwable =>
        logger.warn("", e)
        throw UploadException(s"上传目录[${uploadFiles.apply(0).getParent}]下的文件到目录[$desDir]失败")
    } finally {
      //关闭链接
      if (channel != null && channel.isConnected) channel.disconnect()
      //关闭会话
      if (session != null && session.isConnected) channel.disconnect()
    }
  }

  /**
    * ftp删除文件列表
    *
    * @param  sftp        ftp实体
    * @param desDir       远程文件目录可以是绝对目录，也可以是相对于sftp的directory的相对目录
    * @param delFileNames 删除的文件名集合
    */
  def delete(sftp: SFtp, desDir: String, delFileNames: Array[String]): Unit = {
    var channel: ChannelSftp = null
    var session: Session = null
    try {
      //获得链接
      val connect = open(sftp)
      channel = connect._1
      session = connect._2
      //创建目录
      channel.cd(desDir)
      logger.info(s"进入目录$desDir")
      delFileNames.foreach(f => {
        try {
          logger.info(s"开始删除文件[$f]")
          channel.rm(f)
          logger.info(s"删除文件[$f]成功")
        } catch {
          case e: SftpException =>
            //不等于说明文件存在，报的是其他异常，抛出去
            if (e.id != ChannelSftp.SSH_FX_NO_SUCH_FILE) throw new RuntimeException(e)
            else logger.info(s"删除文件[$f]失败，文件不存在")
        }
      })
    } catch {
      case e: Throwable =>
        logger.warn("", e)
        throw UploadException(s"删除目录[$desDir]下的文件失败")
    } finally {
      //关闭链接
      if (channel != null && channel.isConnected) channel.disconnect()
      //关闭会话
      if (session != null && session.isConnected) channel.disconnect()
    }
  }

  /**
    * ftp从源目录获得文件到目标目录
    *
    * @param sftp        sftp
    * @param srcDir      源目录
    * @param desDir      目标目录
    * @param delFileName 目标文件
    */
  def get(sftp: SFtp, srcDir: String, desDir: String, delFileName: String): Unit = {
    var channel: ChannelSftp = null
    var session: Session = null
    try {
      //获得链接
      val connect = open(sftp)
      channel = connect._1
      session = connect._2
      //创建目录
      channel.cd(desDir)
      logger.info(s"进入目录[$desDir]")
      //上传到当前目录
      logger.info(s"获得文件[$delFileName]")
      channel.get(s"$srcDir/$delFileName", s"$desDir/$delFileName")
      logger.info(s"获得文件[$delFileName]成功")
    } catch {
      case e: Throwable =>
        logger.warn("", e)
        throw UploadException(s"获得目录[$desDir]下的文件[$delFileName]到目录[$srcDir]失败")
    } finally {
      //关闭链接
      if (channel != null && channel.isConnected) channel.disconnect()
      //关闭会话
      if (session != null && session.isConnected) channel.disconnect()
    }
  }

  /**
    * 判断目录是否存在
    *
    * @param dir     目录
    * @param channel channel
    * @return true/false
    */
  private def isDirExist(dir: String, channel: ChannelSftp): Boolean = {
    try {
      channel.cd(dir)
      true
    } catch {
      case e: Throwable =>
        false
    }
  }

  /**
    * 创建层级目录
    *
    * @param path    目录
    * @param channel channel
    */
  private def mkDirs(path: String, channel: ChannelSftp): Unit = {
    if (null == path) {
    } else {
      val paths = if (path.indexOf("\\") != -1) path.split("\\")
      else {
        if (path.indexOf("/") != -1) path.split("/")
        else Array(path)
      }
      paths.foreach(p => {
        //检查目录是否存在
        if (!isDirExist(p, channel)) {
          channel.mkdir(p) //创建目录
          channel.cd(p) // 进入创建的目录
        }
      })
    }
    logger.info(s">>>>>create directory:[$path]")
  }

  /**
    * 打开链接
    *
    * @param sftp sftp对象
    * @return 元祖：ChannelSftp, Session
    */
  private def open(sftp: SFtp): (ChannelSftp, Session) = {
    val jsch = new JSch()
    val sshConfig = new Properties()
    //创建回话
    val session = jsch.getSession(sftp.username, sftp.ip, sftp.port)
    logger.info("sftp Session Created")
    //设置密码
    session.setPassword(sftp.password)
    //首次登录会弹出是否记住主机，设置这个属性就不弹出来了
    sshConfig.put("StrictHostKeyChecking", "no")
    //设置配置
    session.setConfig(sshConfig)
    session.connect()
    logger.info("sftp Session Connected")
    //打开sftp channel
    val channel = session.openChannel("sftp").asInstanceOf[ChannelSftp]
    logger.info("Channel Opened")
    channel.connect()
    logger.info("Channel Connected")
    (channel, session)
  }
}

/**
  * sftp实体
  *
  * @param ip       ip地址
  * @param port     端口
  * @param username 用户名
  * @param password 密码
  * @param isSkip   是否跳过ftp
  */
case class SFtp(ip: String, port: Int, username: String, password: String, isSkip: Boolean)
