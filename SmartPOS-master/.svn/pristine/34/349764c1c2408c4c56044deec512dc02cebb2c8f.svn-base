package com.yada.spos.db.dao

import com.yada.spos.db.model.AppGroup
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor, Modifying, Query}
import org.springframework.data.repository.query.Param

/**
  * Created by pangChangSong on 2016/9/3.
  * 应用分组dao
  */
trait AppGroupDao extends JpaRepository[AppGroup, java.lang.Long] with JpaSpecificationExecutor[AppGroup] {

  /**
    * 根据机构、机构类型和是否是默认分组查询应用分组
    *
    * @param orgId          机构
    * @param orgType        机构类型
    * @param isDefaultGroup 是否是默认分组
    * @return 应用分组
    */
  def findByOrgIdAndOrgTypeAndIsDefaultGroup(orgId: String, orgType: String, isDefaultGroup: String): AppGroup

  /**
    * 根据应用分组名称查询应用分组
    *
    * @param appGroupName 应用分组名称
    * @param orgId        机构号
    * @param orgType      机构类型
    * @return 应用分组列表
    */
  def findByAppGroupNameAndOrgIdAndOrgType(appGroupName: String, orgId: String, orgType: String): java.util.List[AppGroup]

  /**
    * 根据应用分组id更新应用分组
    *
    * @param appGroupId   应用分组id
    * @param appGroupName 应用分组名称
    * @param appGroupDesc 应用分组描述
    * @return 更新数量
    */
  @Modifying(clearAutomatically = true)
  @Query(nativeQuery = true, value = "UPDATE T_APP_GROUP SET APP_GROUP_NAME = :appGroupName, APP_GROUP_DESC = :appGroupDesc  WHERE APP_GROUP_ID = :appGroupId")
  def updateAppGroup(@Param("appGroupId") appGroupId: String, @Param("appGroupName") appGroupName: String, @Param("appGroupDesc") appGroupDesc: String): Int

}
