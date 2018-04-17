package com.yada.spos.db.dao

import com.yada.spos.db.model.{Products, ProductsPK}
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor, Modifying, Query}
import org.springframework.data.repository.query.Param

/**
  * Created by duan on 2016/8/30.
  */
trait ProductsDao extends JpaRepository[Products, ProductsPK] with JpaSpecificationExecutor[Products] {

  /**
    * 根据厂商编号查询所有的产品
    *
    * @param firmCode 厂商编号
    * @return 产品列表
    */
  def findByFirmCode(firmCode: String): java.util.List[Products]

  /**
    * 根据厂商编号和产品型号查询产品
    *
    * @param firmCode 厂商编号
    * @param prodCode 产品型号
    * @return 产品
    */
  def findByFirmCodeAndProdCode(firmCode: String, prodCode: String): Products

  /**
    * 根据厂商编号更新厂商名称
    *
    * @param firmCode 厂商编号
    * @param firmName 厂商名称
    * @return
    */
  @Modifying(clearAutomatically = true)
  @Query(nativeQuery = true, value = "UPDATE T_PRODUCT SET FIRM_NAME = :firmName WHERE FIRM_CODE = :firmCode")
  def updateFirmNameByFirmCode(@Param("firmCode") firmCode: String, @Param("firmName") firmName: String): Int
}
