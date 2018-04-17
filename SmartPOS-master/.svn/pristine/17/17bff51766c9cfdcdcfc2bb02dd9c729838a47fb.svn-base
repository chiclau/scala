package com.yada.spos.mag.service

import java.util.Date

import com.yada.spos.db.dao.{AppGroupDao, AppGroupDevDao, DeviceDao, ProductsDao}
import com.yada.spos.db.model._
import com.yada.spos.db.query.DeviceQuery
import com.yada.spos.mag.service.ext.DeviceHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Scope, ScopedProxyMode}
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

/**
  * Created by duan on 2016/8/31.
  * 设备管理
  */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
class DeviceService {

  @Autowired
  var deviceDao: DeviceDao = _
  @Autowired
  var productsDao: ProductsDao = _
  @Autowired
  var appGroupDevDao: AppGroupDevDao = _
  @Autowired
  var appGroupDao: AppGroupDao = _
  @Autowired
  var deviceHandler: DeviceHandler = _

  /**
    * 根据条件查询数据
    *
    * @param deviceQuery 查询条件
    * @param pageable    分页
    * @return
    */
  def findAll(deviceQuery: DeviceQuery, pageable: Pageable): Page[Device] = {
    deviceDao.findAll(deviceQuery, pageable)
  }

  /**
    * 验证是否能删除
    *
    * @param firmCode 厂商编号
    * @param devSn    设备sn
    * @return 1-设备已激活无法删除！/2-设备已绑定无法删除！
    *         0-可以删除
    */
  def validateIsCanDelete(firmCode: String, devSn: String): String = {
    val device = deviceDao.findByDevSnAndFirmCode(devSn, firmCode)
    if (device.getIsActive == "1") {
      "1"
    } else if (device.getStatus == "1") {
      "2"
    } else {
      "0"
    }
  }

  /**
    * 删除设备
    *
    * @param devicePK 设备主键
    */
  def delete(devicePK: DevicePK): Unit = {
    deviceDao.delete(devicePK)
    deviceDao.flush()
    appGroupDevDao.deleteByFirmCodeAndDevSn(devicePK.getFirmCode, devicePK.getDevSn)
  }

  /**
    *
    * @param file    文件
    * @param orgId   机构id
    * @param orgType 机构号
    * @return true-导入成功，导入失败抛出异常
    */
  def importSn(file: MultipartFile, orgId: String, orgType: String): Unit = {
    val fileName = file.getOriginalFilename
    val extName = fileName.substring(fileName.lastIndexOf("."))
    extName match {
      case ".xls" =>
        val devices = deviceHandler.readXls(file.getInputStream, orgId, orgType)
        //保存设备数据和插入数据到默认分组
        savaData(devices, orgId, orgType)
      case ".csv" =>
        //保存设备数据和插入数据到默认分组
        val devices = deviceHandler.readCsv(file.getInputStream, orgId, orgType)
        //保存设备数据和插入数据到默认分组
        savaData(devices, orgId, orgType)
    }
  }

  /**
    * 保存设备数据和插入数据到默认分组
    *
    * @param devices 设备列表
    * @param orgId   机构号
    * @param orgType 机构类型
    */
  private def savaData(devices: List[Device], orgId: String, orgType: String): Unit = {
    devices.foreach(it => {
      if (it != null) {
        //保存数据
        deviceDao.saveAndFlush(it)
        //插入数据到默认分组
        val appGroup = appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup(orgId, orgType, "0")
        val timeStamp = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", new Date)
        val appGroupDev = new AppGroupDev
        appGroupDev.setDevSN(it.getDevSn)
        appGroupDev.setFirmCode(it.getFirmCode)
        appGroupDev.setAvailable("1")
        appGroupDev.setCreTime(timeStamp)
        appGroupDev.setUpdTime(timeStamp)
        appGroupDev.setAppGroup(appGroup)
        appGroupDev.setAvailable("1")
        appGroupDevDao.saveAndFlush(appGroupDev)
      }
    })
  }

  /**
    * 根据厂商编号更新厂商名称
    *
    * @param firmCode 厂商编号
    * @param firmName 厂商名称
    * @return
    */
  def updateFirmNameByFirmCode(firmCode: String, firmName: String): Int = {
    deviceDao.updateFirmNameByFirmCode(firmCode, firmName)
  }

  /**
    * 根据产品型号更新产品型号名称
    *
    * @param prodName 产品型号名称
    * @param prodCode 产品型号
    * @param firmCode 厂商编号
    * @return
    */
  def updateProdNameByProdCodeAndFirmCode(prodName: String, prodCode: String, firmCode: String): Int = {
    deviceDao.updateProdNameByProdCodeAndFirmCode(prodName, prodCode, firmCode)
  }

}
