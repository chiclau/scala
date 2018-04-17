package com.yada.spos.db.model

import javax.persistence.{Column, Entity, Id}

import scala.beans.BeanProperty

/**
  * 总对总用户表扩展表
  * Created by nickDu on 2016/9/14.
  */
@Entity(name = "T_P_SHIRO_USER_EXT")
class HhapUserExt {
  /**
    * 用户ID
    */
  @Id
  @Column(name = "USER_ID", columnDefinition = "VARCHAR2(15)", nullable = false)
  @BeanProperty
  var userId: String = _

  /**
    * 机构
    */
  @Column(name = "ORG_ID", columnDefinition = "VARCHAR2(16)")
  @BeanProperty
  var orgId: String = _

  /**
    * staffId
    */
  @Column(name = "STAFF_ID", columnDefinition = "INTEGER")
  @BeanProperty
  var staffId: String = _

  /**
    * 最后登录ip
    */
  @Column(name = "LAST_LOGIN_IP_ADDR", columnDefinition = "VARCHAR2(15)")
  @BeanProperty
  var lastLoginIpAddr: String = _

  /**
    * 最后登录时间
    */
  @Column(name = "LAST_LOGIN_DATE_TIME", columnDefinition = "CHAR(14)")
  @BeanProperty
  var lastLoginDateTime: String = _

  /**
    * 最后登出时间
    */
  @Column(name = "LAST_LOGOUT_DATE_TIME", columnDefinition = "CHAR(14)")
  @BeanProperty
  var lasrLogoutDateTime: String = _

  /**
    * 登录总次数
    */
  @Column(name = "LOGIN_CNT", columnDefinition = "INTEGER")
  @BeanProperty
  var loginCnt: String = _

  /**
    * 最后修改密码时间
    */
  @Column(name = "LAST_CHG_PWD_DATE_TIME", columnDefinition = "CHAR(14)")
  @BeanProperty
  var lastChgPwdDateTime: String = _

  /**
    * 创建时间
    */
  @Column(name = "CREATE_DATE_TIME", columnDefinition = "CHAR(14)")
  @BeanProperty
  var createDateTime: String = _

  /**
    * 创建用户
    */
  @Column(name = "CREATE_USER_ID", columnDefinition = "INTEGER")
  @BeanProperty
  var createUserId: String = _

  /**
    * 用户状态(0:停用,1启用)
    */
  @Column(name = "STATUS", columnDefinition = "CHAR(1)")
  @BeanProperty
  var status: String = _

  /**
    * 用户登录失败次数
    */
  @Column(name = "LOGIN_FAILED_CNT", columnDefinition = "INTEGER")
  @BeanProperty
  var loginFailedCnt: String = _

  /**
    * 最后修改密码时间
    */
  @Column(name = "LAST_LOGIN_FAILED_DATE_TIME", columnDefinition = "CHAR(14)")
  @BeanProperty
  var lastLoginFailedDateTime: String = _

}
