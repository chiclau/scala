package com.yada.spos.pos.biz

import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.common.{HsmComponent, SignValidate}
import com.yada.spos.db.dao.{DeviceDao, PospOrgZmkDao, TermWorkKeyDao}
import com.yada.spos.pos.ibiz.{ITmk, TmkReq, TmkResp, TmkRespBody}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import scala.collection.immutable.TreeMap

/**
  * 收单主密钥下载
  * Created by nickDu on 2016/9/7.
  */
@Service
@Transactional
class TmkBiz extends ITmk with LazyLogging {
  @Autowired
  var deviceDao: DeviceDao = _

  @Autowired
  var pospOrgZmkDao: PospOrgZmkDao = _

  @Autowired
  var termWorkKeyDao: TermWorkKeyDao = _

  @Autowired
  var signValidate: SignValidate = _

  @Autowired
  var hsmComponent: HsmComponent = _

  def handle(req: TmkReq): TmkResp = {
    logger.info(s"msgID is [${req.msgID}]")
    val device = deviceDao.findByDevSnAndFirmCode(req.sn, req.firmCode)
    if (device == null) {
      TmkResp("2301", Some("device not found"), None)
    } else if (device.status == "0") {
      //未绑定
      TmkResp("2301", Some("not binding"), None)
    } else if (device.zekLmk == null || device.zekLmk.isEmpty) {
      TmkResp("2302", Some("device no sign in"), None)
    } else {
      //签名验证
      val signData = TreeMap("firmCode" -> req.firmCode, "sn" -> req.sn, "timestamp" -> req.date)
      if (signValidate.handle(device.zekLmk, signData, req.authorization)) {
        val pospOrgZmk = pospOrgZmkDao.findByOrgId(device.orgId)
        if (pospOrgZmk == null) {
          TmkResp("2302", Some("pospOrgZmk not found"), None)
        }
        logger.info(s"zmkLmk is ${pospOrgZmk.zmkLmk}")
        val termWorkKey = termWorkKeyDao.findByMerChantIdAndTerminalId(device.merNo, device.termNo)
        if (termWorkKey == null) {
          TmkResp("2302", Some("termWorkKey not found"), None)
        }
        logger.info(s"tmkZmk is ${termWorkKey.tmkZmk}")
        //用TmkZmk和ZmkLmk解出TmkLmk
        val tmk = hsmComponent.readTmk(termWorkKey.tmkZmk, pospOrgZmk.zmkLmk)
        logger.info(s"tmkLmk is ${tmk.tmkLmk}")
        //用DekLmk和tmklmk组合产生tmkDek
        val tmkDek = hsmComponent.readTmkDek(device.dekLmk, tmk.tmkLmk)
        TmkResp("00", None, Some(TmkRespBody(tmkDek, tmk.tmkKcv)))
      } else {
        TmkResp("4601", Some("sign error"), None)
      }
    }
  }
}
