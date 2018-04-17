package com.yada.spos.db.model

import javax.persistence.{JoinColumn, ManyToOne, _}

import scala.beans.BeanProperty

/**
  * Created by pangChangSong on 2016/9/2.
  * 应用分组关联应用
  */
@Entity(name = "T_APP_GROUP_APPS")
class AppGroupApps {

  /**
    * 分组明细ID(SEQ)
    */
  @Id
  @GeneratedValue(generator = "APP_GROUP_APPS_SEQ")
  @SequenceGenerator(name = "APP_GROUP_APPS_SEQ", sequenceName = "SEQ_T_APP_GROUP_APPS", allocationSize = 1)
  @Column(name = "APP_GROUP_DETAIL_ID", columnDefinition = "NUMBER(16)", nullable = false)
  @BeanProperty
  var appGroupDetailId: Long = _

  /**
    * 应用分组
    */
  @ManyToOne(targetEntity = classOf[AppGroup])
  @JoinColumn(name = "APP_GROUP_ID", referencedColumnName = "APP_GROUP_ID", columnDefinition = "NUMBER(16)")
  @BeanProperty
  var appGroup: AppGroup = _

  /**
    * 应用分组名称
    */
  @Column(name = "APP_GROUP_NAME", columnDefinition = "VARCHAR2(60)")
  @BeanProperty
  var appGroupName: String = _

  /**
    * 应用包名
    */
  @Column(name = "APP_PACKAGE_NAME", columnDefinition = "VARCHAR2(255)")
  @BeanProperty
  var appPackageName: String = _

  /**
    * 应用名称
    */
  @Column(name = "APP_NAME", columnDefinition = "VARCHAR2(255)")
  @BeanProperty
  var appName: String = _
}
