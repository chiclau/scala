package com.yada.spos.mag.service

import java.util.Date

import com.typesafe.scalalogging.LazyLogging
import com.yada.spos.db.dao.{AppGroupDao, AppGroupDevDao, DeviceDao, TermWorkKeyDao}
import com.yada.spos.db.model.{AppGroupDev, Device, DevicePK, TermWorkKey}
import com.yada.spos.db.query.{BTermWorkKeyQuery, DeviceQuery}
import com.yada.spos.mag.service.ext.SystemHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Scope, ScopedProxyMode}
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * Created by duan on 2016/9/2.
  * 终端密钥
  */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
class BTermWorkKeyService extends LazyLogging {

  @Autowired
  var bTermWorkKeyDao: TermWorkKeyDao = _
  @Autowired
  var deviceDao: DeviceDao = _
  @Autowired
  var systemHandler: SystemHandler = _
  @Autowired
  var appGroupDevDao: AppGroupDevDao = _
  @Autowired
  var appGroupDao: AppGroupDao = _

  /**
    * 根据条件查询数据
    *
    * @param bTermWorkKeyQuery 终端秘钥查询类
    * @param pageable          分页
    * @return 终端秘钥列表
    */
  def findAll(bTermWorkKeyQuery: BTermWorkKeyQuery, pageable: Pageable): Page[TermWorkKey] = {
    bTermWorkKeyDao.findAll(bTermWorkKeyQuery, pageable)
  }

  /**
    * 验证是否可以绑定
    *
    * @param device            设备
    * @param orgId             机构号
    * @param orgType           机构类型
    * @param bTermWorkKeyQuery 终端密钥查询类
    * @param deviceQuery       设备查询类
    * @return 验证结果(0-在t_b_term_work_key表中查询不存在唯一商户号和终端号记录，1-成功，
    *         2-设备不存在或已绑定， 3-终端已经绑定, 4机构下没有商户)
    */
  def validateIsMayBound(device: Device, orgId: String, orgType: String, bTermWorkKeyQuery: BTermWorkKeyQuery, deviceQuery: DeviceQuery): String = {
    //判断机构下的商户是否存在
    if (!systemHandler.validateOrgIsExistsMer(orgId, orgType, device.getMerNo)) "4"
    else {
      //在t_b_term_work_key表中查询不存在唯一商户号和终端号记录
      if (!systemHandler.validateBTermWorkKeyIsExistsUniqueMerAndTerm(orgType, device.getMerNo, device.getTermNo, bTermWorkKeyQuery)) "0"
      else {
        //判断商户终端是否已经绑定设备
        deviceQuery.setOrgId(orgId)
        deviceQuery.setOrgType(orgType)
        deviceQuery.setTermNo(device.getTermNo)
        deviceQuery.setMerNo(device.getMerNo)
        val devices = deviceDao.findAll(deviceQuery)
        //终端已经绑定
        if (devices != null && !devices.isEmpty) "3"
        else {
          //根据设备sn和商户号查询该设备信息
          val devicePk = new DevicePK
          devicePk.setDevSn(device.getDevSn)
          devicePk.setFirmCode(device.getFirmCode)
          val deviceTemp = deviceDao.findOne(devicePk)
          //设备不存在或已绑定
          if (deviceTemp == null || deviceTemp.getStatus == "1") "2"
          else "1"
        }
      }
    }
  }

  /**
    * 绑定
    *
    * @param device 设备
    */
  def bound(device: Device): Unit = {
    val devicePK = new DevicePK
    devicePK.setDevSn(device.getDevSn)
    devicePK.setFirmCode(device.getFirmCode)
    val newDevice = deviceDao.findOne(devicePK)
    newDevice.setMerNo(device.getMerNo)
    newDevice.setTermNo(device.getTermNo)
    newDevice.setStatus("1") //已绑定
    deviceDao.saveAndFlush(newDevice)
  }

  /**
    * 取消绑定
    *
    * @param deviceQuery 设备查询类
    * @param orgId       机构
    * @param orgType     机构类型
    */
  def removeBound(deviceQuery: DeviceQuery, orgId: String, orgType: String): Unit = {
    val deviceTemp: Device = deviceDao.findOne(deviceQuery)
    deviceTemp.setTermNo("")
    deviceTemp.setMerNo("")
    deviceTemp.setStatus("0")
    deviceTemp.setTmkDek("")
    deviceTemp.setTmkKcv("")
    deviceTemp.setTmkLmk("")
    deviceTemp.setZekDek("")
    deviceTemp.setZekKcv("")
    deviceTemp.setZekLmk("")
    val firmCode = deviceTemp.getFirmCode
    val devSn = deviceTemp.getDevSn
    logger.info(s"firmCode[$firmCode]sn[$devSn] remove Bound merchant and terminal, merNo[${
      deviceTemp.getMerNo
    }]termNo[${deviceTemp.getTermNo}]tmkDek[${deviceTemp.getTmkDek}]tmkKcv[${
      deviceTemp.getTmkKcv
    }]tmkLmk[${deviceTemp.getTmkLmk}]zekDek[${deviceTemp.getZekDek}]zekKcv[${
      deviceTemp.getZekKcv
    }]zekLmk[${deviceTemp.getZekLmk}]")
    deviceDao.saveAndFlush(deviceTemp)
    //先删除应用分组关联设备
    appGroupDevDao.deleteByFirmCodeAndDevSn(firmCode, devSn)
    //插入到默认分组
    val appGroup = appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup(orgId, orgType, "0")
    //当前时间
    val curTime = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", new Date)
    val appGroupDev = new AppGroupDev
    appGroupDev.setDevSN(devSn)
    appGroupDev.setFirmCode(firmCode)
    appGroupDev.setAppGroup(appGroup)
    appGroupDev.setAvailable("1") //有效性默认是1
    appGroupDev.setCreTime(curTime)
    appGroupDev.setUpdTime(curTime)
    appGroupDevDao.saveAndFlush(appGroupDev)
  }
}
