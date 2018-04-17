package com.yada.spos.mag.service.ext

import java.io.ByteArrayOutputStream
import java.net.URI
import java.nio.ByteBuffer
import java.nio.file.{Files, Path, Paths}
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.{KeyFactory, PrivateKey, Signature}
import java.util.zip.{ZipEntry, ZipInputStream, ZipOutputStream}

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.codec.binary.Base64
import org.springframework.stereotype.Component

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.Try

@Component
class ApkSign extends LazyLogging {
  protected val (rootPublicKeyPath, workPublicKeyPath, workPrivateKeyPath) = {
    val cf = ConfigFactory.load()
    Try {
      (new URI(cf.getString("cert.rootPublicKey")),
        new URI(cf.getString("cert.workPublicKey")),
        new URI(cf.getString("cert.workPrivateKey")))
    }.recover {
      case t: Throwable =>
        (this.getClass.getClassLoader.getResource("cert/boc.crt").toURI,
          this.getClass.getClassLoader.getResource("cert/lanxin.crt").toURI,
          this.getClass.getClassLoader.getResource("cert/lanxin.key").toURI)
    }.get
  }
  //  /**
  //    * 根证书公钥
  //    */
  //  private val rootPublicKeyCert: X509Certificate = {
  //    val certFactory = CertificateFactory.getInstance("X.509")
  //    certFactory.generateCertificate(Files.newInputStream(Paths.get(rootPublicKeyPath))).asInstanceOf[X509Certificate]
  //  }

  /**
    * 读文件转成字节数组
    *
    * @param path 文件路径
    * @return 对应的字节数组
    */
  private def readFileToArrayByte(path: Path): Array[Byte] = {
    val fis = Files.newInputStream(path)
    val bos = new ByteArrayOutputStream(fis.available())
    val bts = new Array[Byte](1024)
    try {
      def read(): Unit = {
        val inLen = fis.read(bts)
        if (inLen > -1) {
          bos.write(bts, 0, inLen)
          read()
        }
      }
      read()
      val rootPublicKeyCert = bos.toByteArray
      rootPublicKeyCert
    }
    finally {
      fis.close()
      bos.close()
    }
  }

  /**
    * 根证书公钥
    */
  private val rootPublicKeyCert: Array[Byte] = {
    val path = Paths.get(rootPublicKeyPath)
    readFileToArrayByte(path)
  }

  val algorithm = "SHA256withRSA"

  /**
    * 验证应用签名的公钥
    */
  private val workPublicKeyCert: Array[Byte] = {
    val path = Paths.get(workPublicKeyPath)
    readFileToArrayByte(path)
  }

  //  /**
  //    * 验证应用签名的公钥
  //    */
  //  private val workPublicKeyCert: X509Certificate = {
  //    val certFactory = CertificateFactory.getInstance("X.509")
  //    val workPublicKeyCert = certFactory.generateCertificate(Files.newInputStream(Paths.get(workPublicKeyPath))).asInstanceOf[X509Certificate]
  //    workPublicKeyCert.verify(rootPublicKeyCert.getPublicKey)
  //    workPublicKeyCert
  //  }

  /**
    * 用于给应用签名的私钥
    */
  private val workPrivateKey: PrivateKey = {
    val source = Source.fromURI(workPrivateKeyPath)
    val base64PK = source.getLines().filterNot(line => line.startsWith("-")).mkString
    val spec = new PKCS8EncodedKeySpec(Base64.decodeBase64(base64PK))
    val keyFactory = KeyFactory.getInstance("RSA")
    keyFactory.generatePrivate(spec).asInstanceOf[RSAPrivateKey]
  }


  /**
    * apk文件签名
    *
    * @param path apk路径
    * @return 返回签名后apk文件路径
    */
  def sign(path: Path): Path = {
    // 产生中间的APK
    val tmpPath = createNewApk(path, zos => {})
    // 产生带签名的APK
    val signInfo = packSignInfo(tmpPath)
    createNewApk(tmpPath, zos => {
      val zipEntry = new ZipEntry("META-INF/BOC_SIGN_INFO.headinfo")
      // 设置实体的最后修改时间
      zipEntry.setTime(0)
      zipEntry.setExtra(null)
      zos.putNextEntry(zipEntry)
      zos.write(signInfo)
      zos.closeEntry()
    })
  }

  protected def createNewApk(oldZipPath: Path, writeNewFile: ZipOutputStream => Unit): Path = {
    val newZipPath = Paths.get(oldZipPath.toAbsolutePath.toString + ".tmp")
    val zos = new ZipOutputStream(Files.newOutputStream(newZipPath))
    val zis = new ZipInputStream(Files.newInputStream(oldZipPath))
    val bts = new Array[Byte](1024)
    try {
      doCopy()
      def doCopy(): Unit = {
        val entry = zis.getNextEntry
        if (entry != null) {
          val zipEntry = new ZipEntry(entry.getName)
          // 设置实体的最后修改时间
          zipEntry.setTime(0)
          zipEntry.setExtra(null)
          zos.putNextEntry(zipEntry)
          doCopyData()
          zos.closeEntry()
          doCopy()
        }
      }
      def doCopyData(): Unit = {
        val len = zis.read(bts)
        if (len > -1) {
          zos.write(bts, 0, len)
          doCopyData()
        }
      }
      writeNewFile(zos)
      //      val zipEntry = new ZipEntry("META-INF/SIGNINFO")
      //      // 设置实体的最后修改时间
      //      zipEntry.setTime(0)
      //      zos.putNextEntry(zipEntry)
      //      zos.write(signInfo)
      //      zos.closeEntry()

    } finally {
      zos.close()
      zis.close()
    }
    Files.delete(oldZipPath)
    Files.move(newZipPath, oldZipPath)
    oldZipPath
  }

  protected def packSignInfo(path: Path): Array[Byte] = {
    val signValue = signFileResult(path)
    val bts = new ArrayBuffer[Byte]
    bts += 0x04
    signValue.length
    // -----签名信息域组装开始-----
    // 组装用户标识信息
    val userDataBytes = "BOC_SIGN_INFO".getBytes
    val userData = ByteBuffer.allocate(1 + 1 + userDataBytes.length)
    userData.put(0x01.toByte)
    userData.put(userDataBytes.length.toByte)
    userData.put(userDataBytes)
    userData.flip()
    // 组装算法标识
    val algorithmBytes = algorithm.getBytes
    val algorithmData = ByteBuffer.allocate(1 + 1 + algorithmBytes.length)
    algorithmData.put(0x01.toByte)
    algorithmData.put(algorithmBytes.length.toByte)
    algorithmData.put(algorithmBytes)
    algorithmData.flip()
    // 组装工作公钥证书:类型+长度+数据
    val rootPKData = ByteBuffer.allocate(1 + 4 + rootPublicKeyCert.length)
    rootPKData.put(0x04.toByte)
    rootPKData.put(reverseByteArray(rootPublicKeyCert.length))
    rootPKData.put(rootPublicKeyCert)
    rootPKData.flip()
    // 组装工作公钥证书:类型+长度+数据
    val workPKData = ByteBuffer.allocate(1 + 4 + workPublicKeyCert.length)
    workPKData.put(0x04.toByte)
    workPKData.put(reverseByteArray(workPublicKeyCert.length))
    workPKData.put(workPublicKeyCert)
    workPKData.flip()
    // 组装签名信息:类型+长度+数据
    val signData = ByteBuffer.allocate(1 + 4 + signValue.length)
    signData.put(0x04.toByte)
    signData.put(reverseByteArray(signValue.length))
    signData.put(signValue)
    signData.flip()
    // -----签名信息域组装完毕-----
    // ----签名文件头组装开始-----
    // 头版本信息组装
    val headVersionBytes = "BOC_F_SIGN_V1.0".getBytes()
    val headVersionData = ByteBuffer.allocate(1 + 1 + headVersionBytes.length)
    headVersionData.put(0x01.toByte)
    headVersionData.put(headVersionBytes.length.toByte)
    headVersionData.put(headVersionBytes)
    headVersionData.flip()
    // 头文件长度组装
    val headLengthData = ByteBuffer.allocate(1 + 1 + 4)
    headLengthData.put(0x03.toByte)
    headLengthData.put(0x04.toByte)
    headLengthData.put(reverseByteArray(6 + headVersionData.remaining() + 6 + 6))
    headLengthData.flip()
    // 签名信息域偏移位置（从文件头开始计算）(从用户标识信息开始)
    val signFieldOffsetData = ByteBuffer.allocate(1 + 1 + 4)
    signFieldOffsetData.put(0x03.toByte)
    signFieldOffsetData.put(0x04.toByte)
    signFieldOffsetData.put(reverseByteArray(headLengthData.remaining() + headVersionData.remaining() + 6 + 6))
    signFieldOffsetData.flip()
    // 签名信息域长度
    val signFiledLengthData = ByteBuffer.allocate(1 + 1 + 4)
    val signFiledLength = userData.remaining() + algorithmData.remaining() + rootPKData.remaining() + workPKData.remaining() + signData.remaining()
    signFiledLengthData.put(0x03.toByte)
    signFiledLengthData.put(0x04.toByte)
    signFiledLengthData.put(reverseByteArray(signFiledLength))
    signFiledLengthData.flip()
    // ----签名文件头组装完毕-----

    // 数据打包
    val data = ArrayBuffer.empty[Byte]
    data ++= headLengthData.array()
    data ++= headVersionData.array()
    data ++= signFieldOffsetData.array()
    data ++= signFiledLengthData.array()
    data ++= userData.array()
    data ++= algorithmData.array()
    data ++= rootPKData.array()
    data ++= workPKData.array()
    data ++= signData.array()
    data.toArray
  }


  /**
    * 对文件进行签名
    *
    * @param path 文件路径
    * @return 签名数据
    */
  protected def signFileResult(path: Path): Array[Byte] = {
    val signature = Signature.getInstance(algorithm)
    signature.initSign(workPrivateKey)
    readData(path, signature)
    signature.sign()
  }

  //  /**
  //    * 给出文件的路径，计算文件中的签名是否正确
  //    *
  //    * @param path 文件路径
  //    * @return true签名正确；false签名不正确
  //    */
  //  def verifySign(path: Path): Boolean = {
  //    val signature = Signature.getInstance(algorithm)
  //    signature.initVerify(workPublicKeyCert)
  //    readData(path, signature)
  //    val signatureData = Array.emptyByteArray
  //    signature.verify(signatureData)
  //  }

  /**
    * 读取数据
    *
    * @param path      文件路径
    * @param signature 签名/验签的工具
    */
  protected def readData(path: Path, signature: Signature): Unit = {
    val bts = new Array[Byte](1024)
    val inputStream = Files.newInputStream(path)
    try {
      def read(): Unit = {
        val len = inputStream.read(bts)
        if (len > -1) {
          signature.update(bts, 0, len)
          read()
        }
      }
      read()
    } finally {
      inputStream.close()
    }
  }

  /**
    * 反转字节数组
    *
    * @param int 要反转的字节数组的
    * @return 反转字节数组
    */
  def reverseByteArray(int: Int): Array[Byte] = {
    val result = new Array[Byte](4)
    result(0) = int.toByte
    result(1) = (int >> 8).toByte
    result(2) = (int >> 16).toByte
    result(3) = (int >> 24).toByte
    result
  }
}
