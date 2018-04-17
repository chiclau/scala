package com.yada.spos.db.model

import javax.persistence._

import scala.beans.BeanProperty

/**
  * 终端应用信息上送
  * Created by nickDu on 2016/9/6.
  */
@Entity(name = "T_DEVICE_INFO_UP")
class DeviceInfoUp {
  /**
    * 主键id
    */
  @Id
  @GeneratedValue(generator = "DEVICE_INFO_UP_SEQ")
  @SequenceGenerator(name = "DEVICE_INFO_UP_SEQ", sequenceName = "SEQ_T_DEVICE_INFO_UP", allocationSize = 1)
  @Column(name = "ID", columnDefinition = "NUMBER(16)", nullable = false)
  @BeanProperty
  var Id: Long = _

  /**
    * 设备SN号
    */
  @Column(name = "DEV_SN", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var devSn: String = _

  /**
    * 厂商编号
    */
  @Column(name = "FIRM_CODE", columnDefinition = "CHAR(4)")
  @BeanProperty
  var firmCode: String = _

  /**
    * 产品型号
    */
  @Column(name = "PROD_CODE", columnDefinition = "VARCHAR2(12)")
  @BeanProperty
  var prodCode: String = _

  /**
    * 01-OTA 02-受控应用 03-K21 04-K21APP 05-RES 06-非受控应用
    */
  @Column(name = "MODULE_TYPE", columnDefinition = "VARCHAR2(4)")
  @BeanProperty
  var moduleType: String = _

  /**
    * 包名
    */
  @Column(name = "PKG_NAME", columnDefinition = "VARCHAR2(200)")
  @BeanProperty
  var pkgName: String = _

  /**
    * 当前版本
    */
  @Column(name = "CURRENT_VERSION", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var currentVersion: String = _

  /**
    * YYYYMMDDHHMMSS
    */
  @Column(name = "APP_UPD_TIME", columnDefinition = "VARCHAR2(16)")
  @BeanProperty
  var appUpdTime: String = _

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
    * 信息状态0无效 1有效
    */
  @Column(name = "INFO_STATUS", columnDefinition = "CHAR(1) default 1")
  @BeanProperty
  var infoStatus: String = _
}
