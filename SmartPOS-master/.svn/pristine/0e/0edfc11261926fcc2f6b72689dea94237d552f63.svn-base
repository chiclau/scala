package com.yada.spos.pos.biz

import java.text.SimpleDateFormat
import java.util.Date
import javax.ws.rs.core.MediaType
import javax.ws.rs.{POST, Path, Produces}

import com.typesafe.scalalogging.LazyLogging
import com.yada.sdk.device.encryption.hsm.HSMException
import com.yada.spos.common.{BCDCodec, HsmComponent, SignValidate}
import com.yada.spos.db.dao.{AuthDeviceDao, AuthDeviceFlowDao, DeviceDao, PospOrgZmkDao}
import com.yada.spos.db.model.AuthDeviceFlow
import com.yada.spos.pos.ibiz.{DeviceInitReq, DeviceInitResp, DeviceInitRespBody, IDeviceInit}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * 设备初始化
  */
@Service
@Transactional
class DeviceInitBiz extends IDeviceInit with LazyLogging with BCDCodec {
  @Autowired
  var authDeviceDao: AuthDeviceDao = _
  @Autowired
  var deviceDao: DeviceDao = _
  @Autowired
  var pospOrgZmkDao: PospOrgZmkDao = _
  @Autowired
  var authDeviceFlowDao: AuthDeviceFlowDao = _
  @Autowired
  var hsmComponent: HsmComponent = _
  @Autowired
  var signValidate: SignValidate = _

  @Path("/init")
  @Produces(Array(MediaType.APPLICATION_JSON))
  @POST
  def handle(req: DeviceInitReq): DeviceInitResp = {
    logger.info(s"msgID is [${req.msgID}]")
    val authDevice = authDeviceDao.findByAuthPosSn(req.keyDeviceSn)
    val device = deviceDao.findByDevSnAndFirmCode(req.sn, req.firmCode)
    if (authDevice == null) {
      DeviceInitResp("2301", Some("auth device not found"), None)
    } else if (device == null) {
      DeviceInitResp("2301", Some("device not found"), None)
    } else {
      val signData: StringBuilder = new StringBuilder
      signData.append("firmCode=").append(req.firmCode).append("&").append("keyDeviceSn=").append(req.keyDeviceSn).append("&").append("sn=").append(req.sn).append("&").append("timestamp=").append(req.timestamp)
      logger.info("local sign Data=[{}]", signData)
      val rsaPublicKey = authDevice.getRsaPublicKey
      val singFlag = signValidate.verify(rsaPublicKey, "SHA1withRSA", signData.toString().getBytes(), req.signValue.toBCD)
      if (singFlag) {
        val zmkLmk = pospOrgZmkDao.findByOrgId(device.orgId).zmkLmk
        try {
          val dek = hsmComponent.readDek(zmkLmk)
          //更新数据库密钥
          device.dekLmk = dek.dekLmk
          device.dekZmk = dek.dekZmk
          device.dekKcv = dek.dekKcv
          deviceDao.saveAndFlush(device)
        } catch {
          case e: HSMException => DeviceInitResp("2303", Some("hsm error"), None)
        }
        //记录流水
        val authDeviceFlow = new AuthDeviceFlow
        authDeviceFlow.authPosSn = req.keyDeviceSn
        authDeviceFlow.firmCode = req.firmCode
        authDeviceFlow.devSn = req.sn
        authDeviceFlow.retCode = "200"
        authDeviceFlow.errMsg = ""
        val now: Date = new Date()
        val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
        authDeviceFlow.creTime = dateFormat.format(now)
        authDeviceFlowDao.saveAndFlush(authDeviceFlow)
        val body = DeviceInitRespBody(device.dekZmk, device.dekKcv)
        DeviceInitResp("00", None, Some(body))
      } else {
        DeviceInitResp("4403", Some("sign error"), None)
      }
    }
  }
}
