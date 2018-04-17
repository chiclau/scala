package com.yada.spos.front.biz

import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.front.core.pos
import com.yada.spos.front.core.pos.model.MagInitReq
import com.yada.spos.front.ibiz.{DeviceInitReq, IDeviceInit}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class DeviceInitBiz extends IDeviceInit with LazyLogging {
  @Autowired
  var posRSClient: pos.RSClient = _


  override def handle(msgID: String,
                      firmCode: String,
                      sn: String,
                      req: DeviceInitReq): String = {
    val data = pos.RSClientPath.MAG_INIT.createEntity(MagInitReq(
      msgID,
      firmCode,
      sn,
      req.keyDeviceSn,
      req.timestamp,
      req.signValue))
    posRSClient.send(data)
  }
}



