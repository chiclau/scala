package com.yada.spos.pos.biz

import java.text.SimpleDateFormat
import java.util.Date

import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.common.SignValidate
import com.yada.spos.db.dao.{AppFileHistoryDao, DeviceDao, DownUpdateUpDao}
import com.yada.spos.db.model.DownUpdateUp
import com.yada.spos.pos.ibiz.{AppUpdateResultReq, AppUpdateResultResp, IAppUpdateResult}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import scala.collection.immutable.TreeMap

/**
  * 下载更新结果上送
  * Created by nickDu on 2016/9/6.
  */
@Service
@Transactional
class AppUpdateResultBiz extends IAppUpdateResult with LazyLogging {
  @Autowired
  var deviceDao: DeviceDao = _
  @Autowired
  var downUpdateUpDao: DownUpdateUpDao = _
  @Autowired
  var signValidate: SignValidate = _
  @Autowired
  var appFileHistoryDao: AppFileHistoryDao = _

  def handle(req: AppUpdateResultReq): AppUpdateResultResp = {
    logger.info(s"msgID is [${req.msgID}]")
    val device = deviceDao.findByDevSnAndFirmCode(req.sn, req.firmCode)
    if (device == null) {
      AppUpdateResultResp("2301", Some("device not found"))
    } else if (req.startTime == null || req.endTime == null || req.result == null || req.updateType == null || req.`type` == null ||
      req.startTime.isEmpty || req.endTime.isEmpty || req.result.isEmpty || req.updateType.isEmpty || req.`type`.isEmpty) {
      AppUpdateResultResp("1001", Some("miss request data"))
    } else if (device.zekLmk == null || device.zekLmk.isEmpty) {
      AppUpdateResultResp("2302", Some("device no sign in"))
    } else {
      val signData = TreeMap("desc" -> req.desc, "endTime" -> req.endTime, "firmCode" -> req.firmCode, "pkgName" -> req.pkgName, "result" -> req.result,
        "sn" -> req.sn, "startTime" -> req.startTime, "timestamp" -> req.date, "type" -> req.`type`, "updateType" -> req.updateType)
      if (signValidate.handle(device.zekLmk, signData, req.authorization)) {
        val downUpdateUp = new DownUpdateUp
        downUpdateUp.devSn = req.sn
        downUpdateUp.firmCode = req.firmCode
        downUpdateUp.prodCode = device.prodCode //t_device查
        downUpdateUp.moduleType = req.`type`

        downUpdateUp.pkgName = req.pkgName
        downUpdateUp.currentVersion = req.version
        downUpdateUp.startTime = req.startTime
        downUpdateUp.endTime = req.endTime
        val now: Date = new Date()
        val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
        downUpdateUp.updTime = dateFormat.format(now)
        downUpdateUp.resultType = req.updateType
        downUpdateUp.result = req.result
        downUpdateUp.mark = req.desc
        downUpdateUpDao.saveAndFlush(downUpdateUp)
        AppUpdateResultResp("00", None)
      } else {
        AppUpdateResultResp("4403", Some("sign error"))
      }
    }
  }
}
