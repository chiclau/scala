package com.yada.spos.db.dao

import com.yada.spos.db.model.{AppGroup, AppGroupApps}
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor, Modifying, Query}
import org.springframework.data.repository.query.Param

/**
  * Created by pangChangSong on 2016/9/3.
  * 应用分组和应用关联表
  */
trait AppGroupAppsDao extends JpaRepository[AppGroupApps, java.lang.Long] with JpaSpecificationExecutor[AppGroupApps] {
  /**
    * 根据应用分组查所有应用
    *
    * @param appGroup 分组
    * @return
    */
  def findByAppGroup(appGroup: AppGroup): java.util.List[AppGroupApps]

  /**
    * 根据包名和是否是默认分组查询应用分组应用关联
    *
    * @param appPackageName 包名
    * @param isDefaultGroup 是否是默认分组
    * @param orgId          机构号
    * @param orgType        机构类型
    * @return 应用分组应用关联
    */
  def findByAppPackageNameAndAppGroupIsDefaultGroupAndAppGroupOrgIdAndAppGroupOrgType(appPackageName: String, isDefaultGroup: String, orgId: String, orgType: String): AppGroupApps

  /**
    * 应用分组和包名查询应用分组应用关联
    *
    * @param appPackageName 包名
    * @param appGroup       应用分组
    * @return 应用分组应用关联
    */
  def findByAppPackageNameAndAppGroup(appPackageName: String, appGroup: AppGroup): AppGroupApps

  /**
    * 根据应用分组id更新应用分组名称
    *
    * @param appGroupId   应用分组id
    * @param appGroupName 应用分组名称
    * @return 更新数量
    */
  @Modifying(clearAutomatically = true)
  @Query(nativeQuery = true, value = "UPDATE T_APP_GROUP_APPS SET APP_GROUP_NAME = :appGroupName WHERE APP_GROUP_ID = :appGroupId")
  def updateAppGroupName(@Param("appGroupId") appGroupId: String, @Param("appGroupName") appGroupName: String): Int

}
