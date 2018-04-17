package com.yada.spos.db.model

import javax.persistence.{JoinColumn, ManyToOne, _}

import scala.beans.BeanProperty

/**
  * 应用分组关联设备
  * Created by nickDu on 2016/9/7.
  */
@Entity(name = "T_APP_GROUP_DEV")
class AppGroupDev {
  /**
    * 分组明细ID(SEQ)
    */
  @Id
  @GeneratedValue(generator = "APP_GROUP_DEV_SEQ")
  @SequenceGenerator(name = "APP_GROUP_DEV_SEQ", sequenceName = "SEQ_T_APP_GROUP_DEV", allocationSize = 1)
  @Column(name = "DEV_APP_GROUP_ID", columnDefinition = "NUMBER(16)", nullable = false)
  @BeanProperty
  var devAppGroupId: Long = _

  /**
    * 设备SN号
    */
  @Column(name = "DEV_SN", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var devSN: String = _

  /**
    * 厂商编号
    */
  @Column(name = "FIRM_CODE", columnDefinition = "CHAR(4)")
  @BeanProperty
  var firmCode: String = _

  /**
    * 应用分组ID
    */
  @ManyToOne(targetEntity = classOf[AppGroup])
  @JoinColumn(name = "APP_GROUP_ID", referencedColumnName = "APP_GROUP_ID", columnDefinition = "NUMBER(16)")
  @BeanProperty
  var appGroup: AppGroup = _

  /**
    * 创建时间
    */
  @Column(name = "CRE_TIME", columnDefinition = "VARCHAR2(14)")
  @BeanProperty
  var creTime: String = _

  /**
    * 更新时间
    */
  @Column(name = "UPD_TIME", columnDefinition = "VARCHAR2(14)")
  @BeanProperty
  var updTime: String = _

  /**
    * 有效性  1有效  0无效  有效性依据
    */
  @Column(name = "AVAILABLE", columnDefinition = "CHAR(1) default 1")
  @BeanProperty
  var available: String = _
}
