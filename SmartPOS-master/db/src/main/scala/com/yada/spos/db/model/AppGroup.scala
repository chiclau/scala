package com.yada.spos.db.model

import javax.persistence._

import scala.beans.BeanProperty

/**
  * Created by pangChangSong on 2016/9/2.
  * 应用分组
  */
@Entity(name = "T_APP_GROUP")
class AppGroup {

  /**
    * 主键id
    */
  @Id
  @GeneratedValue(generator = "APP_GROUP_SEQ")
  @SequenceGenerator(name = "APP_GROUP_SEQ", sequenceName = "SEQ_T_APP_GROUP", allocationSize = 1)
  @Column(name = "APP_GROUP_ID", columnDefinition = "NUMBER(16)", nullable = false)
  @BeanProperty
  var appGroupId: Long = _

  /**
    * 组名称
    */
  @Column(name = "APP_GROUP_NAME", columnDefinition = "VARCHAR2(60)")
  @BeanProperty
  var appGroupName: String = _

  /**
    * 组描述
    */
  @Column(name = "APP_GROUP_DESC", columnDefinition = "VARCHAR2(255)")
  @BeanProperty
  var appGroupDesc: String = _

  /**
    * 是否是默认分组（0-默认分组，1-非默认分组）
    */
  @Column(name = "IS_DEFAULT_GROUP", columnDefinition = "VARCHAR2(1)")
  @BeanProperty
  var isDefaultGroup: String = _

  /**
    * 机构ID
    */
  @Column(name = "ORG_ID", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var orgId: String = _

  /**
    * 机构类型  1-固话  2-总对总
    */
  @Column(name = "ORG_TYPE", columnDefinition = "CHAR(1)")
  @BeanProperty
  var orgType: String = _
}
