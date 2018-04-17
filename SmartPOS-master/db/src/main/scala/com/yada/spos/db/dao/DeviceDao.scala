package com.yada.spos.db.dao

import com.yada.spos.db.model.{Device, DevicePK}
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor, Modifying, Query}
import org.springframework.data.repository.query.Param

/**
  * Created by duan on 2016/8/31.
  * 设备管理
  */
trait DeviceDao extends JpaRepository[Device, DevicePK] with JpaSpecificationExecutor[Device] {
  /**
    * 根据设备SN号和厂商编号查询设备
    *
    * @param devSn    设备SN号
    * @param firmCode 厂商编号
    * @return 设备
    */
  def findByDevSnAndFirmCode(devSn: String, firmCode: String): Device

  /**
    * 根据厂商编号更新厂商名称
    *
    * @param firmCode 厂商编号
    * @param firmName 厂商名称
    * @return
    */
  @Modifying(clearAutomatically = true)
  @Query(nativeQuery = true, value = "UPDATE T_DEVICE SET FIRM_NAME = :firmName WHERE FIRM_CODE = :firmCode")
  def updateFirmNameByFirmCode(@Param("firmCode") firmCode: String, @Param("firmName") firmName: String): Int

  /**
    * 根据产品型号更新产品型号名称
    *
    * @param prodCode 产品型号
    * @param firmCode 厂商编号
    * @param prodName 产品型号名称
    * @return
    */
  @Modifying(clearAutomatically = true)
  @Query(nativeQuery = true, value = "UPDATE T_DEVICE SET PROD_NAME = :prodName WHERE FIRM_CODE = :firmCode AND PROD_CODE = :prodCode")
  def updateProdNameByProdCodeAndFirmCode(@Param("prodName") prodName: String, @Param("prodCode") prodCode: String, @Param("firmCode") firmCode: String): Int
}
