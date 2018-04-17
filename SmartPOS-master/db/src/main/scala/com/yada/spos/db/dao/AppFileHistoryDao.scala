package com.yada.spos.db.dao

import com.yada.spos.db.model.AppFileHistory
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor}

/**
  * Created by pangChangSong on 2016/8/30.
  * 应用上传历史dao
  */
trait AppFileHistoryDao extends JpaRepository[AppFileHistory, java.lang.Long] with JpaSpecificationExecutor[AppFileHistory] {

  /**
    * 根据包名和版本序号查询 机构类型下的机构应用上传历史
    *
    * @param packageName 包名
    * @param versionCode 应用版本序号
    * @param orgId       机构
    * @param orgType     机构类型
    * @return 应用历史信息
    */
  def findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType(packageName: String, versionCode: String, orgId: String, orgType: String): AppFileHistory

  /**
    * 根据包名和版本序号查询 机构类型下应用上传历史
    *
    * @param packageName 包名
    * @param versionCode 应用版本序号
    * @param orgType     机构类型
    * @param orgId       机构id
    * @return 应用历史信息列表
    */
  def findByAppPackageNameAndVersionCodeAndOrgTypeAndOrgIdNot(packageName: String, versionCode: String, orgType: String, orgId: String): java.util.List[AppFileHistory]

  /**
    * 根据包名和版本序号查询 机构类型下的机构应用上传历史信息
    *
    * @param packageName 包名
    * @param orgId       机构
    * @param orgType     机构类型
    * @return 应用历史信息列表
    */
  def findByAppPackageNameAndOrgIdAndOrgType(packageName: String, orgId: String, orgType: String): java.util.List[AppFileHistory]

  /**
    * 根据包名查询 机构类型下的上传历史信息
    *
    * @param packageName 包名
    * @param orgType     机构类型
    * @param orgId       机构id
    * @return 应用历史信息列表
    */
  def findByAppPackageNameAndOrgTypeAndOrgIdNot(packageName: String, orgType: String, orgId: String): java.util.List[AppFileHistory]
}
