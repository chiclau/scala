package com.yada.spos.db.dao

import com.yada.spos.db.model.{Role, RolePK}
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor}

/**
  * 角色Dao
  * Created by nickDu on 2016/9/14.
  */
trait RoleDao extends JpaRepository[Role, RolePK] with JpaSpecificationExecutor[Role] {
  /**
    * 根据机构类型和其他系统角色名称查询角色
    *
    * @param orgType     机构类型
    * @param sysRoleName 其他系统角色名称
    * @return 角色
    */
  def findByOrgTypeAndSysRoleName(orgType: String, sysRoleName: String): Role
}
