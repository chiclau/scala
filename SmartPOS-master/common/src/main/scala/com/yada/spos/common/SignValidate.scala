package com.yada.spos.common

import java.security.{KeyFactory, MessageDigest, PublicKey, Signature}
import java.security.spec.X509EncodedKeySpec
import javax.xml.bind.DatatypeConverter

import com.typesafe.scalalogging.LazyLogging
import com.yada.sdk.device.encryption.hsm.HSMException
import org.apache.commons.codec.binary.Hex

import scala.collection.immutable.TreeMap

/**
  * 签名验证
  * Created by nickDu on 2016/9/5.
  */
class SignValidate(hsmComponent: HsmComponent) extends BCDCodec with LazyLogging {

  /**
    * 验证签名
    *
    * @param zekLmk      加密机保护的通讯密钥
    * @param signDataMap 签名数据源
    * @param signValue   签名信息(密文)
    * @return 成功/失败
    */
  def handle(zekLmk: String, signDataMap: TreeMap[String, String], signValue: String): Boolean = {
    var signData = append("", signDataMap)
    signData = signData.substring(0, signData.length - 1)
    logger.info(s"本地MD5加密的原始数据是$signData")
    val md5 = MessageDigest.getInstance("MD5")
    val b = signData.getBytes("UTF-8")
    md5.update(b, 0, b.length)
    val privateSign = new java.math.BigInteger(1, md5.digest()).toString(16)
    logger.info(s"本地生成签名数据是$privateSign")
    val signByte = Hex.decodeHex(signValue.toCharArray)
    try {
      val hsmResult = hsmComponent.readDataByBytes(zekLmk, signByte)
      val comeSign = Hex.encodeHexString(hsmResult)
      logger.info(s"加密机返回的签名数据是$comeSign")
      comeSign.equals(privateSign)
    } catch {
      case e: HSMException => false
    }
  }

  /**
    * 将map转成指定格式String
    *
    * @param preString 初始字符串
    * @param map       参数
    * @return 转换的字符串
    */
  def append(preString: String, map: Map[String, String]): String = {
    if (map.isEmpty) preString
    else {
      val (k, v) = map.head
      append(s"$preString$k=$v&", map.tail)
    }
  }

  /**
    * 母pos授权验签
    *
    * @param rsaPublicKey 证书公钥
    * @param algorithm    算法
    * @param data         数据
    * @param signData     签名数据
    * @return
    */
  def verify(rsaPublicKey: String, algorithm: String, data: Array[Byte], signData: Array[Byte]): Boolean = {
    try {
      val publicKeyData: String = rsaPublicKey
      val keySpec: X509EncodedKeySpec = new X509EncodedKeySpec(DatatypeConverter.parseHexBinary(publicKeyData))
      // 实例化密钥工厂
      val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
      // 生成公钥
      val pubKey: PublicKey = keyFactory.generatePublic(keySpec)
      val signature: Signature = Signature.getInstance(algorithm)
      signature.initVerify(pubKey)
      signature.update(data)
      signature.verify(signData)
    } catch {
      case e: Exception =>
        logger.warn("", e)
        false
    }
  }
}


