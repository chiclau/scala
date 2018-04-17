package com.yada.spos.mag.service.ext

import java.io.InputStream
import java.security.KeyStore

import org.apache.commons.codec.binary.Hex
import org.springframework.stereotype.Component

import scala.collection.convert.decorateAll._

/**
  * Created by pangChangSong on 2016/9/11.
  * 母pos管理处理
  */
@Component
class AuthDeviceHandler {

  /**
    * 获得证书公钥
    *
    * @param inputStream 输入流
    * @return 公钥字符串
    * @param pwd 证书密码
    */
  def getPublicKey(inputStream: InputStream, pwd: String): String = {
    //获取公钥
    try {
      val keyStore = KeyStore.getInstance("pkcs12")
      keyStore.load(inputStream, pwd.toCharArray)
      val keyAlias = keyStore.aliases().asScala.toList.head
      val cert = keyStore.getCertificate(keyAlias)
      val publicKey = cert.getPublicKey
      Hex.encodeHexString(publicKey.getEncoded)
    } catch {
      case e: Throwable =>
        throw new RuntimeException("上传公钥证书有误，请重新上传！", e)
    }
  }
}
