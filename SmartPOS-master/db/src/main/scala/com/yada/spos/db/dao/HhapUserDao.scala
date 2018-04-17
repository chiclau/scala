package com.yada.spos.db.dao

import com.yada.spos.db.model.HhapUser
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor}

/**
  * 总对总用户信息
  * Created by nickDu on 2016/9/14.
  */
trait HhapUserDao extends JpaRepository[HhapUser, String] with JpaSpecificationExecutor[HhapUser] {
  /**
    *
    * @param loginName 用户名
    * @param pwd       密码
    * @return
    */
  def findByLoginNameAndPwd(loginName: String, pwd: String): HhapUser

  /**
    *
    * @param loginName 用户名
    * @return
    */
  def findByLoginName(loginName: String): HhapUser
}
