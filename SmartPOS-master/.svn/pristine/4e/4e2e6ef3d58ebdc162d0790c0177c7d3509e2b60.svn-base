package com.yada.spos.db.model

import javax.persistence.{Column, Entity, Id}

import scala.beans.BeanProperty

/**
  * 总对总角色
  * Created by nickDu on 2016/9/14.
  */
@Entity(name = "T_P_SHIRO_ROLE")
class HhapRole {
  /**
    * 角色ID
    */
  @Id
  @Column(name = "ROLE_ID", columnDefinition = "NUMBER(15)", nullable = false)
  @BeanProperty
  var roleId: String = _

  /**
    * 名称
    */
  @Column(name = "ROLE_NAME", columnDefinition = "NVARCHAR2(64)")
  @BeanProperty
  var roleName: String = _

  /**
    * 描述
    */
  @Column(name = "DSC", columnDefinition = "NVARCHAR2(64)")
  @BeanProperty
  var dsc: String = _

}
