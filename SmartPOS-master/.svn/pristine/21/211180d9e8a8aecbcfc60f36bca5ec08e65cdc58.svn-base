package com.yada.spos.db.model

import javax.persistence._

import scala.beans.BeanProperty

/**
  * 更新下载结果上送
  * Created by nickDu on 2016/9/7.
  */
@Entity(name = "T_DOWN_UPDATE_UP")
class DownUpdateUp {
  /**
    * 主键id
    */
  @Id
  @GeneratedValue(generator = "DOWN_UPDATE_UP_SEQ")
  @SequenceGenerator(name = "DOWN_UPDATE_UP_SEQ", sequenceName = "SEQ_T_DOWN_UPDATE_UP", allocationSize = 1)
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
    * 01-OTA 02-受控应用
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
    * 开始更新/下载时间
    */
  @Column(name = "START_TIME", columnDefinition = "VARCHAR2(16)")
  @BeanProperty
  var startTime: String = _

  /**
    * 结束更新/下载时间
    */
  @Column(name = "END_TIME", columnDefinition = "VARCHAR2(16)")
  @BeanProperty
  var endTime: String = _

  /**
    * 更新时间
    */
  @Column(name = "UPD_TIME", columnDefinition = "VARCHAR2(14)")
  @BeanProperty
  var updTime: String = _

  /**
    * 01-下载  02-更新
    */
  @Column(name = "RESULT_TYPE", columnDefinition = "VARCHAR2(4) default 01")
  @BeanProperty
  var resultType: String = _

  /**
    * 0-成功  1-失败
    */
  @Column(name = "RESULT", columnDefinition = "CHAR(1)")
  @BeanProperty
  var result: String = _

  /**
    * 填写失败原因
    */
  @Column(name = "MARK", columnDefinition = "VARCHAR2(200)")
  @BeanProperty
  var mark: String = _
}
