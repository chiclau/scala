package com.yada.spos.db.dao

import com.yada.spos.db.model.AppFileLatest
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor}

/**
  * Created by pangChangSong on 2016/8/30.
  * 应用最新版本dao
  */
trait AppFileLatestDao extends JpaRepository[AppFileLatest, java.lang.Long] with JpaSpecificationExecutor[AppFileLatest] {

  /**
    * 根据包名查询机构类型下的机构应用最新版本
    *
    * @param appPackageName 包名
    * @param orgId          机构Id
    * @param orgType        机构类型
    * @return 应用最新版本
    */
  def findByAppPackageNameAndOrgIdAndOrgType(appPackageName: String, orgId: String, orgType: String): AppFileLatest

  /**
    * 根据机构号和机构类型查询应用列表
    *
    * @param orgId   机构号
    * @param orgType 机构类型
    * @return 应用列表
    */
  def findByOrgIdAndOrgType(orgId: String, orgType: String): java.util.List[AppFileLatest]
}
