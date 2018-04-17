package com.yada.spos.db.model

import javax.persistence._

import scala.beans.BeanProperty

/**
  * 设备参数
  * Created by nickDu on 2016/9/7.
  */
@Entity(name = "T_ONLINE_PARAM")
class OnlineParam {
  /**
    * 主键id
    */
  @Id
  @GeneratedValue(generator = "ONLINE_PARAM_SEQ")
  @SequenceGenerator(name = "ONLINE_PARAM_SEQ", sequenceName = "SEQ_T_ONLINE_PARAM", allocationSize = 1)
  @Column(name = "ONLINE_PARAM_ID", columnDefinition = "NUMBER(16)", nullable = false)
  @BeanProperty
  var onlineParamId: Long = _

  /**
    * 参数名称
    */
  @Column(name = "PARAM_NAME", columnDefinition = "VARCHAR2(64)")
  @BeanProperty
  var paramName: String = _

  /**
    * 参数描述
    */
  @Column(name = "PARAM_DESC", columnDefinition = "VARCHAR2(255)")
  @BeanProperty
  var paramDesc: String = _

  /**
    * 参数值
    */
  @Column(name = "PARAM_VALUE", columnDefinition = "VARCHAR2(255)")
  @BeanProperty
  var paramValue: String = _

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
