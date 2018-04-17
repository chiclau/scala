package com.yada.spos.front.biz

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.common.HsmComponent
import com.yada.spos.db.dao.DeviceDao
import com.yada.spos.front.acq.AcqClient
import com.yada.spos.front.ibiz.IAcq
import org.apache.commons.codec.binary.Hex
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional("transactionManager")
class AcqBiz extends IAcq with LazyLogging {

  @Autowired
  var deviceDao: DeviceDao = _

  @Autowired
  var hsmComponent: HsmComponent = _

  @Autowired
  var acqClient: AcqClient = _

  val config = ConfigFactory.load()
  val acqIp = config.getString("acq.ip")
  val acqPort = config.getInt("acq.port")

  /**
    * 收单app
    *
    * @param firmCode 厂商代码
    * @param sn       设备SN号
    * @param data     发送数据
    * @return
    */
  override def handle(firmCode: String,
                      sn: String,
                      data: Array[Byte]): Array[Byte] = {
    val device = deviceDao.findByDevSnAndFirmCode(sn, firmCode)
    if (device == null) {
      throw new RuntimeException("device not found")
    } else if (device.status == "0") {
      //未关联商户
      throw new RuntimeException("device not binding")
    } else if (device.zekLmk == null || device.zekLmk.isEmpty) {
      throw new RuntimeException("device not have zekLmk")
    } else {
      //      val oldData =  new String(data, "UTF-8")
      val sendData = hsmComponent.readDataByBytes(device.zekLmk, data)
      logger.info(s"向POSP系统发送数据[${new String(Hex.encodeHex(sendData))}]")
      val response = acqClient.send(acqIp, acqPort, sendData)
      logger.info(s"POSP系统返回数据[${Hex.encodeHexString(response)}]")
      val returnData = hsmComponent.encodeDataByBytes(device.zekLmk, response)
      logger.info(s"收单接口返回数据[${Hex.encodeHexString(returnData)}]")
      returnData
    }
  }
}