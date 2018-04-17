package com.yada.spos.db.model

import javax.persistence.{Column, Entity, Id, IdClass}

import scala.beans.BeanProperty

/**
  * 角色表
  * Created by nickDu on 2016/9/14.
  */
@Entity(name = "T_ROLE")
@IdClass(classOf[RolePK])
class Role {
  /**
    * 机构类型  1-固话  2-总对总
    */
  @Id
  @Column(name = "ORG_TYPE", columnDefinition = "CHAR(1)")
  @BeanProperty
  var orgType: String = _

  /**
    * 系统角色名称
    */
  @Id
  @Column(name = "SYS_ROLE_NAME", columnDefinition = "VARCHAR2(128)")
  @BeanProperty
  var sysRoleName: String = _

  /**
    * 名称
    */
  @Id
  @Column(name = "ROLE_NAME", columnDefinition = "VARCHAR2(128)")
  @BeanProperty
  var roleName: String = _
}

@SerialVersionUID(1L)
class RolePK extends Serializable {
  @BeanProperty
  var orgType: String = _
  @BeanProperty
  var sysRoleName: String = _
  @BeanProperty
  var roleName: String = _

  def canEqual(other: Any): Boolean = other.isInstanceOf[RolePK]

  override def equals(other: Any): Boolean = other match {
    case that: RolePK =>
      (that canEqual this) &&
        orgType == that.orgType &&
        sysRoleName == that.sysRoleName &&
        roleName == that.roleName
    case _ => false
  }

  override def hashCode(): Int = {
    //noinspection HashCodeUsesVar
    val state = Seq(orgType, sysRoleName, roleName)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}