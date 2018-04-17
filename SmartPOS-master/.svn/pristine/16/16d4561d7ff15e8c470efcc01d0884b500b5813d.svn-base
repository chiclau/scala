package com.yada.spos.common

import com.typesafe.config.ConfigFactory
import com.yada.sdk.device.encryption.hsm.EncryptionMachine

/**
  * 加密机通信
  * Created by locky on 2016/9/6.
  */
class HsmComponent {
  // 加载配置文件
  val config = ConfigFactory.load()
  val hsmIp = config.getString("hsm.ip")
  val hsmPort = config.getInt("hsm.port")
  val iEncryption = new EncryptionMachine("22.188.41.19", 8)

  /**
    * 获取zek
    *
    * @param dekLmk 加密机密钥保护的设备密钥
    * @return
    */
  def readZek(dekLmk: String): Zek = {
    val zek = iEncryption.getZekKeyArray(dekLmk)
    Zek(zek(0), zek(1), zek(2))
  }

  /**
    * 获取dek
    *
    * @param zmkLmk 加密机保护的通讯密钥
    * @return
    */
  def readDek(zmkLmk: String): Dek = {
    val dek = iEncryption.getDekKeyArray(zmkLmk)
    Dek(dek(0), dek(1), dek(2))
  }

  //  /**
  //    * 解密数据
  //    *
  //    * @param zekLmk    加密机保护的通讯密钥
  //    * @param signValue 签名数据
  //    * @return
  //    */
  //  def readData(zekLmk: String, signValue: String): String = {
  //    iEncryption.getDataByDecryption(signValue, zekLmk)
  //  }

  /**
    * 解密数据
    *
    * @param zekLmk    加密机保护的通讯密钥
    * @param signValue 签名数据
    * @return
    */
  def readDataByBytes(zekLmk: String, signValue: Array[Byte]): Array[Byte] = {
    iEncryption.getByteDataByDecryption(signValue, zekLmk)
  }

  //  /**
  //    * 加密数据
  //    *
  //    * @param zekLmk  加密机保护的通讯密钥
  //    * @param data 密码
  //    * @return
  //    */
  //  def encodeData(zekLmk: String, data: String): String = {
  //    iEncryption.getDataByEncryption(data, zekLmk)
  //  }

  /**
    * 加密数据
    *
    * @param zekLmk 加密机保护的通讯密钥
    * @param data   密码
    * @return
    */
  def encodeDataByBytes(zekLmk: String, data: Array[Byte]): Array[Byte] = {
    iEncryption.getByteDataByEncryption(data, zekLmk)
  }

  /**
    * 用TmkZmk和ZmkLmk解出TmkLmk
    *
    * @param tmkZmk TMK_ZMK
    * @param zmkLmk 区域密钥密文LMK下
    * @return
    */
  def readTmk(tmkZmk: String, zmkLmk: String): Tmk = {
    val tmk = iEncryption.getTmkKeyArray(tmkZmk, zmkLmk)
    Tmk(tmk(0), tmk(1))
  }

  /**
    * 用DekLmk和tmklmk组合产生tmkDek
    *
    * @param dekLmk dekLmk
    * @param tmkLmk tmkLmk
    * @return
    */
  def readTmkDek(dekLmk: String, tmkLmk: String): String = {
    iEncryption.getDekTmk(dekLmk, tmkLmk)
  }
}

/**
  *
  * @param zekDek 设备密钥保护的通讯密钥
  * @param zekLmk 加密机保护的通讯密钥
  * @param zekKcv 通讯密钥校验值
  */
case class Zek(zekDek: String, zekLmk: String, zekKcv: String)

/**
  *
  * @param dekLmk 加密机密钥保护的设备密钥
  * @param dekZmk 区域密钥保护的设备密钥
  * @param dekKcv 设备密钥校验值
  */
case class Dek(dekLmk: String, dekZmk: String, dekKcv: String)

/**
  *
  * @param tmkLmk tmkLmk
  * @param tmkKcv tmkKcv
  */
case class Tmk(tmkLmk: String, tmkKcv: String)
