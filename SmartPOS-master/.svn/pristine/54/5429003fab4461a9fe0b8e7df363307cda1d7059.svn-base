package com.yada.spos.db.dao

import com.yada.spos.db.model.OnlineParam
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor}

/**
  * 联机参数下载
  * Created by nickDu on 2016/9/7.
  */
trait OnlineParamDao extends JpaRepository[OnlineParam, java.lang.Long] with JpaSpecificationExecutor[OnlineParam] {
  /**
    * 根据机构id和机构类型获取联机参数列表
    *
    * @param orgId   机构ID
    * @param orgType 机构类型
    * @return 联机参数下载列表
    */
  def findByOrgIdAndOrgType(orgId: String, orgType: String): java.util.List[OnlineParam]

  /**
    * 根据机构id和机构类型参数名称获取联机参数列表
    *
    * @param orgId     机构ID
    * @param orgType   机构类型
    * @param paramName 参数名称
    * @return 联机参数下载实体
    */
  def findByOrgIdAndOrgTypeAndParamName(orgId: String, orgType: String, paramName: String): OnlineParam
}
