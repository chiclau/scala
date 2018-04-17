package com.yada.spos.mag.util

import java.io.InputStream
import java.security.MessageDigest

import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.common.BCDCodec
import com.yada.spos.mag.exception.UploadException
import org.apache.commons.io.IOUtils

/**
  * Created by pangChangSong on 2016/9/2.
  * 基本摘要算法实现(Md5加密)
  */
class SimpleDigest extends BCDCodec with LazyLogging {

  /**
    * md5加密
    *
    * @param inputStream 输入流
    * @param algorithm   算法
    * @return
    */
  def doDigest(inputStream: InputStream, algorithm: String): String = {
    try {
      //创建byte数组inputStream.available()为流的字节长度
      val buffer = new Array[Byte](inputStream.available())
      //根据算法创建信息摘要
      val md = MessageDigest.getInstance(algorithm)
      //将输入流信息读取到byte数组
      inputStream.read(buffer)
      //更新摘要信息
      md.update(buffer)
      val resultBytes: Array[Byte] = md.digest
      //进行bcd编码
      resultBytes.fromBCD
    } catch {
      case e: Exception =>
        logger.warn("", e)
        throw UploadException("MD5加密失败！")
    } finally IOUtils.closeQuietly(inputStream)
  }

}
