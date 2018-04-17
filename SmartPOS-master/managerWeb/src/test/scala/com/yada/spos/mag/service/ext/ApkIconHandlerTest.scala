package com.yada.spos.mag.service.ext

import java.io.File

import net.dongliu.apk.parser.ApkParser
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.core.io.ClassPathResource

/**
  * Created by pangChangSong on 2016/9/20.
  * 测试获取apk的icon文件
  */
@RunWith(classOf[JUnitRunner])
class ApkIconHandlerTest extends FlatSpec with Matchers {
  val apkIconHandler = new ApkIconHandler

  /**
    * 测试获取apk的icon，并放到指定目录
    */
  "testIconToFile" should "handle successful" in {
    val res = new ClassPathResource("/appUpload/yada.apk", this.getClass)
    val apkParser = new ApkParser(res.getFile)
    apkIconHandler.iconToFile(apkParser.getIconFile, res.getFile.getParent, "yada")
    val file = new File(s"${res.getFile.getParent}/yada_icon.png")
    //验证文件存在
    file.exists() shouldBe true
    //删除文件
    file.delete()
  }
}
