package com.yada.spos.db.model

import javax.persistence._

import scala.beans.BeanProperty

/**
  * 授权设备请求流水
  * Created by nickDu on 2016/9/5.
  */
@Entity(name = "t_auth_device_flow")
class AuthDeviceFlow {
  /**
    * 主键
    */
  @Id
  @GeneratedValue(generator = "AUTH_DEVICE_FLOW_SEQ")
  @SequenceGenerator(name = "AUTH_DEVICE_FLOW_SEQ", sequenceName = "SEQ_T_AUTH_DEVICE_FLOW", allocationSize = 1)
  @Column(name = "FLOW_ID", columnDefinition = "NUMBER(16)", nullable = false)
  @BeanProperty
  var flowId: Long = _

  /**
    * 母POS SN号
    */
  @Column(name = "AUTH_POS_SN", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var authPosSn: String = _

  /**
    * 生产厂商编号
    */
  @Column(name = "FIRM_CODE", columnDefinition = "CHAR(4)")
  @BeanProperty
  var firmCode: String = _

  /**
    * 被授权设备sn
    */
  @Column(name = "DEV_SN", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var devSn: String = _

  /**
    * 被授权设备sn  应答码 200成功  500失败 默认200
    */
  @Column(name = "RET_CODE", columnDefinition = "VARCHAR2(4) default 200")
  @BeanProperty
  var retCode: String = _

  /**
    * 错误描述
    */
  @Column(name = "ERR_MSG", columnDefinition = "VARCHAR2(255)")
  @BeanProperty
  var errMsg: String = _

  /**
    * 创建时间
    */
  @Column(name = "CRE_TIME", columnDefinition = "VARCHAR2(14)")
  @BeanProperty
  var creTime: String = _
}
