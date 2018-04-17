package com.yada.spos.pos.biz

import com.typesafe.scalalogging.LazyLogging
import com.yada.sdk.device.encryption.hsm.HSMException
import com.yada.spos.common.HsmComponent
import com.yada.spos.db.dao.DeviceDao
import com.yada.spos.pos.ibiz.{DeviceSignReq, DeviceSignResp, DeviceSignRespBody, IDeviceSign}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * 设备签到
  * Created by nickDu on 2016/9/5.
  */
@Service
@Transactional
class DeviceSignBiz extends IDeviceSign with LazyLogging {
  @Autowired
  var deviceDao: DeviceDao = _

  @Autowired
  var hsmComponent: HsmComponent = _

  def handle(req: DeviceSignReq): DeviceSignResp = {
    logger.info(s"msgID is [${req.msgID}]")
    val device = deviceDao.findByDevSnAndFirmCode(req.sn, req.firmCode)
    if (device == null) {
      DeviceSignResp("2301", Some("device not found"), None)
    } else if (device.dekLmk == null || device.dekLmk.isEmpty) {
      DeviceSignResp("2303", Some("device not auth"), None)
    } else if (device.status == "0") {
      //未绑定
      DeviceSignResp("2304", Some("device not binding"), None)
    } else {
      try {
        val zek = hsmComponent.readZek(device.dekLmk)
        if (device.isActive == "0") {
          device.isActive = "1"
        }
        device.zekLmk = zek.zekLmk
        device.zekDek = zek.zekDek
        device.zekKcv = zek.zekKcv
        deviceDao.saveAndFlush(device)
        val body = DeviceSignRespBody(device.zekDek, device.zekKcv, device.merNo, device.termNo)
        DeviceSignResp("00", None, Some(body))
      } catch {
        case e: HSMException => DeviceSignResp("2303", Some("hsm error"), None)
      }
    }
  }
}
