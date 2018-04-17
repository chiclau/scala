package com.yada.spos.pos.biz

import com.typesafe.scalalogging.LazyLogging
import com.yada.sdk.device.encryption.hsm.HSMException
import com.yada.spos.common.{HsmComponent, SignValidate}
import com.yada.spos.db.dao.{DeviceDao, OnlineParamDao}
import com.yada.spos.pos.ibiz.{IOnlineParam, OnlineParamReq, OnlineParamResp}
import org.apache.commons.codec.binary.Hex
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import scala.collection.convert.decorateAll._
import scala.collection.immutable.TreeMap


/**
  * 联机参数下载
  * Created by nickDu on 2016/9/7.
  */
@Service
@Transactional
class OnlineParamBiz extends IOnlineParam with LazyLogging {
  @Autowired
  var deviceDao: DeviceDao = _

  @Autowired
  var onlineParamDao: OnlineParamDao = _

  @Autowired
  var signValidate: SignValidate = _

  @Autowired
  var hsmComponent: HsmComponent = _

  def handle(req: OnlineParamReq): OnlineParamResp = {
    logger.info(s"msgID is [${req.msgID}]")
    val device = deviceDao.findByDevSnAndFirmCode(req.sn, req.firmCode)
    if (device == null) {
      OnlineParamResp("2301", Some("device not found"), None)
    } else if (device.zekLmk == null || device.zekLmk.isEmpty) {
      OnlineParamResp("2302", Some("device no sign in"), None)
    } else {
      //签名验证
      val signData = TreeMap("firmCode" -> req.firmCode, "sn" -> req.sn, "timestamp" -> req.date)
      if (signValidate.handle(device.zekLmk, signData, req.authorization)) {
        val onlineParams = onlineParamDao.findByOrgIdAndOrgType(device.orgId, device.orgType).asScala.toList
        val sposPwd = onlineParams.filter(v => v.paramName == "spospwd")
        var onlineParamsMap = onlineParams.map(v => (v.paramName, v.paramValue)).toMap
        //sposPwd 是否为空
        if (sposPwd.nonEmpty) {
          try {
            val pwdBytes = hsmComponent.encodeDataByBytes(device.zekLmk, sposPwd.head.paramValue.getBytes())
            val pwd = Hex.encodeHexString(pwdBytes)
            onlineParamsMap += ("spospwd" -> pwd)
          } catch {
            case e: HSMException => OnlineParamResp("2303", Some("hsm error"), None)
          }

        }
        OnlineParamResp("00", None, Some(onlineParamsMap))
      } else {
        OnlineParamResp("4403", Some("sign error"), None)
      }
    }
  }

}
