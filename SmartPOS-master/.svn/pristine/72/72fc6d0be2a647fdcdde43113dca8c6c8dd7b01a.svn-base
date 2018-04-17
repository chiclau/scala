package com.yada.spos.db.dao

import com.yada.spos.db.model.OtaHistory
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor, Modifying, Query}
import org.springframework.data.repository.query.Param

/**
  * Created by pangChangSong on 2016/9/3.
  * 固件上传历史dao
  *
  */
trait OtaHistoryDao extends JpaRepository[OtaHistory, String] with JpaSpecificationExecutor[OtaHistory] {

  /**
    * 根据厂商编号、产品型号、版本名称、机构号、机构类型查询otaHistory信息
    *
    * @param firmCode    厂商编号
    * @param prodCode    产品型号
    * @param versionName 版本名称
    * @param orgId       机构号
    * @param orgType     机构类型
    * @return otaHistory实体
    */
  def findByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType(firmCode: String, prodCode: String, versionName: String, orgId: String, orgType: String): OtaHistory

  /**
    * 根据厂商编号更新厂商名称
    *
    * @param firmCode 厂商编号
    * @param firmName 厂商名称
    * @return
    */
  @Modifying(clearAutomatically = true)
  @Query(nativeQuery = true, value = "UPDATE T_OTA_FILE_HISTORY SET FIRM_NAME = :firmName WHERE FIRM_CODE = :firmCode")
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
  @Query(nativeQuery = true, value = "UPDATE T_OTA_FILE_HISTORY SET PROD_NAME = :prodName WHERE FIRM_CODE = :firmCode AND PROD_CODE = :prodCode")
  def updateProdNameByProdCodeAndFirmCode(@Param("prodName") prodName: String, @Param("prodCode") prodCode: String, @Param("firmCode") firmCode: String): Int
}
