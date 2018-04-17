package com.yada.spos.front.acq

import java.net.InetSocketAddress
import java.nio.ByteBuffer

import com.typesafe.scalalogging.LazyLogging
import com.yada.sdk.net.{FixLenPackageSplitterFactory, TcpClient}
import org.springframework.stereotype.Component


/**
  * 收单客户端
  * Created by nickDu on 2016/9/19.
  */
trait IAcqClient {
  def send(targetIp: String, targetPort: Int, data: Array[Byte]): Array[Byte]
}

@Component
class AcqClient extends IAcqClient with LazyLogging {
  /**
    * 收单客户端发送方法
    *
    * @param targetIp   ip地址
    * @param targetPort 端口
    * @param data       发送数据
    * @return 返回数据
    */
  override def send(targetIp: String, targetPort: Int, data: Array[Byte]): Array[Byte] = {
    val netSocketAddress = new InetSocketAddress(targetIp, targetPort)
    val reqBuffer = ByteBuffer.wrap(data)
    val client = new TcpClient(netSocketAddress, new FixLenPackageSplitterFactory(2, false), 15000)
    try {
      client.open()
      val respBuffer = client.send(reqBuffer)
      respBuffer.array
    } finally {
      if (client.isOpen) {
        client.close()
      }
    }
  }
}
