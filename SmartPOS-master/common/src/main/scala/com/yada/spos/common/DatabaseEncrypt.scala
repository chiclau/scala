package com.yada.spos.common

import org.apache.commons.codec.binary.Base64

/**
  * 数据库加解密
  */
class DatabaseEncrypt {

  /**
    * 加密
    *
    * @param before 加密前的数据
    * @return 加密后的数据
    */
  def encrypt(before: String): String = {
    Base64.encodeBase64String(before.getBytes("UTF-8"))
  }

  /**
    * 解密
    *
    * @param after 加密后的数据
    * @return 未加密的数据
    */
  def decrypt(after: String): String = {
    new String(Base64.decodeBase64(after), "UTF-8")
  }
}

