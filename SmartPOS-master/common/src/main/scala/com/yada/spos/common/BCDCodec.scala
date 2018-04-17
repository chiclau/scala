package com.yada.spos.common

import org.apache.commons.codec.binary.Hex

/**
  * BCD编码
  */
trait BCDCodec {

  implicit class FromBCD(data: Array[Byte]) {
    def fromBCD: String = {
      Hex.encodeHexString(data)
    }
  }

  implicit class ToBCD(data: String) {
    def toBCD: Array[Byte] = {
      if (data.length % 2 != 0) {
        throw new IllegalArgumentException("BCD encode data`s len can`t be odd")
      }
      Hex.decodeHex(data.toCharArray)
    }
  }

}