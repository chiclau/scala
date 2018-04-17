package com.yada.spos.db.model

import javax.persistence._

import scala.beans.BeanProperty

/**
  * 总对总用户表
  * Created by nickDu on 2016/9/14.
  */
@Entity(name = "T_P_SHIRO_USER")
class HhapUser {

  /**
    * 用户ID
    */
  @Id
  @Column(name = "USER_ID", columnDefinition = "VARCHAR2(15)", nullable = false)
  @BeanProperty
  var userId: String = _

  /**
    * 分组ID
    */
  @ManyToOne(targetEntity = classOf[HhapUserGroup])
  @JoinColumn(name = "USER_GRP_ID", referencedColumnName = "USER_GRP_ID", columnDefinition = "NUMBER(15)")
  @BeanProperty
  var userGroupId: HhapUserGroup = _

  /**
    * 登录名
    */
  @Column(name = "LOGIN_NAME", columnDefinition = "VARCHAR2(32)", unique = true)
  @BeanProperty
  var loginName: String = _

  /**
    * 密码
    */
  @Column(name = "PWD", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var pwd: String = _
}
