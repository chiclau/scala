package com.yada.spos.front.ibiz

/**
  * 收单交易接口
  */
trait IAcq {
  /**
    * 处理收单交易
    *
    * @param firmCode 厂商代码
    * @param sn       设备SN
    * @param data     二进制数据
    * @return
    */
  def handle(firmCode: String, sn: String, data: Array[Byte]): Array[Byte]
}
