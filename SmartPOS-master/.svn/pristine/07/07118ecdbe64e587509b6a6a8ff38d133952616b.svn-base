package com.yada.spos.mag

import java.io.FileInputStream
import java.security.{KeyStore, MessageDigest}

import org.apache.commons.codec.binary.Hex

import scala.collection.convert.decorateAll._

/**
  * 读取证书的工具
  */
object ReadCertUtil extends App {
  val path = "D:/lanxin.pfx"
  val pwd = "lanxin"
  val keyStore = KeyStore.getInstance("pkcs12")
  keyStore.load(new FileInputStream(path), pwd.toCharArray)
  val keyAlias = keyStore.aliases().asScala.toList.head
  val cert = keyStore.getCertificate(keyAlias)
  val msgDigest: MessageDigest = MessageDigest.getInstance("SHA1")
  msgDigest.update(cert.getEncoded)
  val certDigest = msgDigest.digest()
  val digest = Hex.encodeHexString(certDigest)
  println(digest)
}
