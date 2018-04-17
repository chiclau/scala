package com.yada.spos.db.dao

import com.yada.spos.db.model.DeviceInfoUp
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor, Modifying, Query}
import org.springframework.data.repository.query.Param

/**
  * 终端应用信息上送dao
  * Created by nickDu on 2016/9/6.
  */
trait DeviceInfoUpDao extends JpaRepository[DeviceInfoUp, String] with JpaSpecificationExecutor[DeviceInfoUp] {
  /**
    * 根据设备sn和厂商编号更新终端应用信息的状态
    *
    * @param devSn    设备SN号
    * @param firmCode 厂商编号
    */
  @Modifying(clearAutomatically = true)
  @Query(nativeQuery = true, value = "UPDATE T_DEVICE_INFO_UP SET INFO_STATUS = :status WHERE DEV_SN = :devSn AND FIRM_CODE = :firmCode")
  def updateInfoStatusByDevSnAndFirmCode(@Param("status") status: String, @Param("devSn") devSn: String, @Param("firmCode") firmCode: String): Int

  /**
    * 根据sn,厂商代码和包名,包类型通过数据库获取是否存储过
    *
    * @param devSn      sn
    * @param firmCode   厂商代码
    * @param pkgName    包名
    * @param moduleType 包类型
    */
  def findByDevSnAndFirmCodeAndPkgNameAndModuleType(devSn: String, firmCode: String, pkgName: String, moduleType: String): DeviceInfoUp
}
