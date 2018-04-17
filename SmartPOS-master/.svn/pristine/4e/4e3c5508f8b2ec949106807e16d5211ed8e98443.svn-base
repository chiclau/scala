package com.yada.spos.pos.biz

import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.common.SignValidate
import com.yada.spos.db.dao._
import com.yada.spos.pos.ibiz._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import scala.collection.convert.decorateAll._
import scala.collection.immutable.TreeMap
import scala.collection.mutable.ListBuffer


/**
  * 应用信息查询
  * Created by nickDu on 2016/9/7.
  */
@Service
@Transactional
class AppInfoBiz extends IAppInfo with LazyLogging {
  @Autowired
  var deviceDao: DeviceDao = _

  @Autowired
  var productsDao: ProductsDao = _

  @Autowired
  var appGroupDevDao: AppGroupDevDao = _

  @Autowired
  var appGroupAppsDao: AppGroupAppsDao = _

  @Autowired
  var appFileLatestDao: AppFileLatestDao = _

  @Autowired
  var otaFileLatestDao: OtaFileLatestDao = _

  @Autowired
  var signValidate: SignValidate = _

  def handle(req: AppInfoReq): AppInfoResp = {
    val device = deviceDao.findByDevSnAndFirmCode(req.sn, req.firmCode)
    logger.info(s"msgID is [${req.msgID}]")
    if (device == null) {
      AppInfoResp("2301", Some("device not found"), None)
    } else if (device.zekLmk == null || device.zekLmk.isEmpty) {
      AppInfoResp("2302", Some("device no sign in"), None)
    } else {
      val signData = TreeMap("firmCode" -> req.firmCode, "sn" -> req.sn, "timestamp" -> req.date)
      if (signValidate.handle(device.zekLmk, signData, req.authorization)) {
        val product = productsDao.findByFirmCodeAndProdCode(device.firmCode, device.prodCode)
        if (product != null) {
          val appInfos = ListBuffer.empty[AppInfomation]
          //根据sn查分组
          val appGroupDev = appGroupDevDao.findByDevSNAndFirmCode(device.devSn, device.firmCode)
          if (appGroupDev != null) {
            val appGroup = appGroupDev.appGroup
            //查询对应分组下的应用
            val appGroupApps = appGroupAppsDao.findByAppGroup(appGroup).asScala.toList
            if (appGroupApps != null && appGroupApps.nonEmpty) {
              appGroupApps.foreach(f => {
                //循环查询最新版本信息
                val appFileLatest = appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType(f.appPackageName, f.appGroup.orgId, f.appGroup.orgType)
                //支持手持模式
                if ((product.deviceMode == "2" && appFileLatest.modeHand == "1") || (product.deviceMode == "1" && appFileLatest.modeHd == "1")) {
                  appInfos += AppInfomation("02", appFileLatest.appPackageName, appFileLatest.appName, appFileLatest.versionName, appFileLatest.versionCode,
                    appFileLatest.minVersionCode, Some(appFileLatest.deleteUpdate), Some(appFileLatest.forceUpdate), Some(appFileLatest.fileMd5))
                }
              })
            }
            val otaFileLatest = otaFileLatestDao.findByFirmCodeAndProdCodeAndOrgIdAndOrgType(device.firmCode, device.prodCode, appGroup.orgId, appGroup.orgType)
            if (otaFileLatest != null) {
              if ((product.deviceMode == "2" && otaFileLatest.modeHand == "1") || (product.deviceMode == "1" && otaFileLatest.modeHd == "1")) {
                appInfos += AppInfomation("01", otaFileLatest.otaPackageName, otaFileLatest.otaName, otaFileLatest.versionName, "", otaFileLatest.minVersionName, None, None, None)
              }
            }
            AppInfoResp("00", None, Some(AppInfoRespBody(appInfos.toList)))
          }
          else {
            AppInfoResp("2303", Some("device do not have group"), None)
          }
        } else {
          AppInfoResp("2303", Some("product not found"), None)
        }
      } else {
        AppInfoResp("4403", Some("sign error"), None)
      }
    }
  }
}
