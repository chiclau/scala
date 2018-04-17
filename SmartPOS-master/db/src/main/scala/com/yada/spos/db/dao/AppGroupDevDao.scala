package com.yada.spos.db.dao

import com.yada.spos.db.model.{AppGroup, AppGroupDev}
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor, Modifying, Query}
import org.springframework.data.repository.query.Param

/**
  * 应用分组关联设备
  * Created by nickDu on 2016/9/7.
  */
trait AppGroupDevDao extends JpaRepository[AppGroupDev, java.lang.Long] with JpaSpecificationExecutor[AppGroupDev] {
  /**
    * 根据sn和厂商编号查询应用分组关联设备实体
    *
    * @param devSN    设备SN号
    * @param firmCode 设备SN号
    * @return 关联的应用分组
    */
  def findByDevSNAndFirmCode(devSN: String, firmCode: String): AppGroupDev

  /**
    * 根据应用分组查询应用分组下的所有应用分组关联设备数据
    *
    * @param appGroup 应用分组
    * @return 应用分组关联设备列表
    */
  def findByAppGroup(appGroup: AppGroup): java.util.List[AppGroupDev]

  /**
    * 根据厂商编号和设备sn删除应用分组关联设备
    *
    * @param firmCode 厂商编号
    * @param devSn    设备sn
    */
  @Modifying(clearAutomatically = true)
  @Query(nativeQuery = true, value = "delete from T_APP_GROUP_DEV t where t.FIRM_CODE=:firmCode and t.DEV_SN=:devSn")
  def deleteByFirmCodeAndDevSn(@Param("firmCode") firmCode: String, @Param("devSn") devSn: String): Int
}
