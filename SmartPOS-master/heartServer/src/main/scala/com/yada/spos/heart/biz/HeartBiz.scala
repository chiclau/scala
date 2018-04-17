package com.yada.spos.heart.biz

import java.util.Date

import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.common.SignValidate
import com.yada.spos.db.dao.{DeviceDao, HeartLogDao, HeartLogDetailDao}
import com.yada.spos.db.model.{DevicePK, HeartLog, HeartLogDetail, HeartLogPK}
import com.yada.spos.heart.ibiz.{HeartReq, HeartResp, IHeart}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import scala.collection.immutable.TreeMap


/**
  * Created by pangChangSong on 2016/10/12.
  * v1版本心跳
  */
@Service
@Transactional("transactionManager")
class HeartBiz extends IHeart with LazyLogging {

  @Autowired
  var heartLogDao: HeartLogDao = _
  @Autowired
  var deviceDao: DeviceDao = _
  @Autowired
  var heartLogDetailDao: HeartLogDetailDao = _
  @Autowired
  var signValidate: SignValidate = _

  def handle(msgID: String,
             firmCode: String,
             sn: String,
             timestamp: String,
             sign: String,
             req: HeartReq): HeartResp = {
    //根据厂商编号和sn查询设备
    val devicePK = new DevicePK
    devicePK.setDevSn(sn)
    devicePK.setFirmCode(firmCode)
    val device = deviceDao.findOne(devicePK)
    //设备不存在，返回01
    if (device == null) {
      logger.info(s"$firmCode not exists device[$sn]")
      HeartResp("2301", Some("device not found"))
    } else if (device.zekLmk == null || device.zekLmk.isEmpty) {
      HeartResp("2302", Some("device no sign in"))
    } else {
      //设备存在
      //验证签名（根据本地的组装的签名信息和zeklmk(设备中)传给加密机，返回的md5值和请求的md5值比较）
      //组装签名信息
      var signMap = TreeMap(
        "timestamp" -> timestamp,
        "state" -> req.state,
        "firmCode" -> firmCode,
        "sn" -> sn
      )
      if (req.station != null && req.station.nonEmpty) {
        signMap += ("station" -> req.station)
      }
      //签名不通过，返回01
      if (!signValidate.handle(device.zekLmk, signMap, sign)) {
        logger.info(s"$firmCode.$sn validate sign fail,signMsg[${signMap.toString()}]")
        HeartResp("4403", Some("sign error"))
      } else {
        //按着厂商编号和sn获取心跳信息
        val heartLogPK = new HeartLogPK
        heartLogPK.setDevSn(sn)
        heartLogPK.setFirmCode(firmCode)
        val heartLog = heartLogDao.findOne(heartLogPK)
        val upTime = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", new Date)
        //否插入数据，是更新原有
        if (heartLog != null) {
          logger.info(s"delete heart log[${heartLogPK.toString}]")
          //先删除原有数据,然后在插入
          heartLogDao.delete(heartLog)
          heartLogDao.flush()
        }
        //插入数据
        val heartLogNew = new HeartLog
        //设置心跳日志信息
        heartLogNew.setDevSn(sn)
        heartLogNew.setFirmCode(firmCode)
        heartLogNew.setDevTimestamp(timestamp)
        heartLogNew.setProdCode(device.getProdCode)
        heartLogNew.setBaseStation(req.station)
        heartLogNew.setUpTime(upTime)
        //GPS 信息未上送，暂时传空
        heartLogNew.setBaseGps("")
        heartLogDao.saveAndFlush(heartLogNew)
        //插入信息到心跳明细表
        val heartLogDetail = new HeartLogDetail
        heartLogDetail.setDevSn(sn)
        heartLogDetail.setFirmCode(firmCode)
        heartLogDetail.setProdCode(device.getProdCode)
        heartLogDetail.setDevTimestamp(timestamp)
        heartLogDetail.setBaseStation(req.station)
        //GPS 信息未上送，暂时传空
        heartLogDetail.setBaseGps("")
        heartLogDetail.setUpTime(upTime)
        heartLogDetailDao.saveAndFlush(heartLogDetail)
        logger.info(s"heart handler end...$msgID")
        HeartResp("00", None)
      }
    }
  }
}