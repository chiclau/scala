package com.yada.spos.db.model

import javax.persistence._

import scala.beans.BeanProperty

/**
  * Created by pangChangSong on 2016/10/18.
  * 应用分组关联设备、关联设备视图
  */
@Entity(name = "V_L_APP_GROUP_DEVICE")
class VAppGroupDevice {

  /**
    * 主键id
    */
  @Id
  @Column(name = "ID", columnDefinition = "VARCHAR2(32)", nullable = false)
  @BeanProperty
  var id: String = _

  /**
    * 设备sn
    */
  @Column(name = "DEV_SN", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var devSn: String = _

  /**
    * 厂商编号
    */
  @Column(name = "FIRM_CODE", columnDefinition = "VARCHAR2(4)")
  @BeanProperty
  var firmCode: String = _

  /**
    * 应用分组id
    */
  @Column(name = "APP_GROUP_ID", columnDefinition = "VARCHAR2(16)")
  @BeanProperty
  var appGroupId: String = _

  /**
    * 商户号
    */
  @Column(name = "MER_NO", columnDefinition = "VARCHAR2(15)")
  @BeanProperty
  var merNo: String = _

  /**
    * 终端号
    */
  @Column(name = "TERM_NO", columnDefinition = "VARCHAR2(8)")
  @BeanProperty
  var termNo: String = _

  /**
    * 产品型号
    */
  @Column(name = "PROD_CODE", columnDefinition = "VARCHAR2(12)")
  @BeanProperty
  var prodCode: String = _

  /**
    * 状态
    */
  @Column(name = "STATUS", columnDefinition = "CHAR(1)")
  @BeanProperty
  var status: String = _
}
