package com.yada.spos.mag.service.ext

import java.nio.file.Paths

import org.apache.commons.io.FileUtils
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.mockito.MockitoSugar
import org.springframework.core.io.ClassPathResource

import scala.io.Source

/**
  * Created by pangChangSong on 2016/9/21.
  * zip文件处理器测试
  */
class ZipFileHandlerTest extends FlatSpec with Matchers with MockitoSugar {

  val zipFileHandler = new ZipFileHandler

  /**
    * 测试解压自拍包
    */
  "testUnzipToPath" should "handle successful" in {
    val res = new ClassPathResource("/otaUpload/Desktop.zip", this.getClass)
    val zipFile = res.getFile
    val desDir = zipFile.getParent
    zipFileHandler.unzipToPath(zipFile, desDir)
    //验证临时目录是desDir + 文件名
    val tempDirFile = Paths.get(s"$desDir/Desktop").toFile
    tempDirFile.exists() shouldBe true
    //验证目录下有三个文件
    tempDirFile.listFiles().length shouldBe 3
    //验证文件的内容
    tempDirFile.listFiles().foreach(it => {
      it.getName match {
        case "new 1.txt" =>
          val source = Source.fromFile(it)
          source.mkString shouldBe "你好123"
          source.close()
        case "new2.txt" =>
          val source = Source.fromFile(it)
          source.mkString shouldBe "你好456"
          source.close()
        case "new3.txt" =>
          val source = Source.fromFile(it)
          source.mkString shouldBe "你好789"
          source.close()
      }
    })
    //删除文件
    FileUtils.forceDelete(tempDirFile)
  }
}
