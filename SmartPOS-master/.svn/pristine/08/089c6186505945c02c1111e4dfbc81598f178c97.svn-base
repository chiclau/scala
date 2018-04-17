package com.yada.spos.mag.service.ext

import java.io.{File, FileInputStream, OutputStream}
import java.nio.charset.Charset
import java.nio.file.{Files, Paths}
import java.util.zip.ZipInputStream

import org.apache.commons.io.FileUtils
import org.springframework.stereotype.Component

/**
  * 压缩文件的处理者
  */
@Component
class ZipFileHandler {
  /**
    * 解压文件到指定目录下
    *
    * @param sourceFile 源文件
    * @param targetPath 目标路径
    */
  def unzipToPath(sourceFile: File, targetPath: String): Unit = {
    val rootDirName = sourceFile.getName.substring(0, sourceFile.getName.lastIndexOf("."))
    val rootDir = Paths.get(targetPath, rootDirName)
    if (rootDir.toFile.exists()) FileUtils.forceDelete(rootDir.toFile)
    Files.createDirectories(rootDir)
    val zis = new ZipInputStream(new FileInputStream(sourceFile), Charset.forName("GBK"))
    try {
      unzip(zis)
    } finally {
      if (zis != null) zis.close()
    }
    def unzip(zis: ZipInputStream): Unit = {
      val zipEntry = zis.getNextEntry
      if (zipEntry != null) {
        val path = Paths.get(targetPath, rootDirName, zipEntry.getName)
        if (zipEntry.isDirectory) {
          Files.createDirectories(path)
        } else {
          val os = Files.newOutputStream(path)
          try {
            read(zis, os)
            os.flush()
          } finally {
            os.close()
          }
        }
        zis.closeEntry()
        unzip(zis)
      }
    }

    def read(inputStream: ZipInputStream, outputStream: OutputStream): Unit = {
      val bts = new Array[Byte](4096)
      val len = inputStream.read(bts)
      if (len > -1) {
        outputStream.write(bts, 0, len)
        read(inputStream, outputStream)
      }
    }
  }
}