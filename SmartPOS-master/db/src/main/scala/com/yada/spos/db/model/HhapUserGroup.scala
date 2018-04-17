package com.yada.spos.db.model

import javax.persistence.{JoinTable, _}

import scala.beans.BeanProperty

/**
  * 总对总用户分组
  * Created by nickDu on 2016/9/14.
  */
@Entity(name = "T_P_SHIRO_USER_GRP")
class HhapUserGroup {
  /**
    * 用户分组ID
    */
  @Id
  @Column(name = "USER_GRP_ID", columnDefinition = "NUMBER(15)", nullable = false)
  @BeanProperty
  var userGrpId: String = _

  /**
    * 名称
    */
  @Column(name = "NAME", columnDefinition = "NVARCHAR2(64)")
  @BeanProperty
  var name: String = _

  @ManyToMany(targetEntity = classOf[HhapRole], fetch = FetchType.EAGER)
  @JoinTable(name = "T_P_SHIRO_USER_GRP_ROLE_IDX", joinColumns = Array(new JoinColumn(name = "USER_GRP_ID")), inverseJoinColumns = Array(new JoinColumn(name = "ROLE_ID")))
  @BeanProperty
  var roles: java.util.List[HhapRole] = _
}
