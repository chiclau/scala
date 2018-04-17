package com.yada.spos.common

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

/**
  * BCD编码解码测试
  */
@RunWith(classOf[JUnitRunner])
class BCDCodecTest extends FlatSpec with Matchers with BCDCodec {
  "bcd decode [0x01,0x23,0x45,0x67,0x89,0x10]" should "012345678910" in {
    val data = Array[Byte](0x01, 0x23, 0x45, 0x67, 0x89.toByte, 0x10)
    data.fromBCD shouldBe "012345678910"
  }

  "bcd encode 012345678910" should "[0x01,0x23,0x45,0x67,0x89,0x10]" in {
    "012345678910".toBCD shouldBe Array[Byte](0x01, 0x23, 0x45, 0x67, 0x89.toByte, 0x10)
  }

  an[IllegalArgumentException] should be thrownBy "01234567891".toBCD

}
