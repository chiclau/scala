package com.yada.spos.db.model

import javax.persistence._

import scala.beans.BeanProperty

/**
  * Created by pangChangSong on 2016/9/6.
  * 心跳日志流水
  */
@Entity(name = "T_HEART_LOG_DETAIL")
class HeartLogDetail {

  /**
    * 主键(SEQ)
    */
  @Id
  @GeneratedValue(generator = "HEART_LOG_DETAIL_SEQ")
  @SequenceGenerator(name = "HEART_LOG_DETAIL_SEQ", sequenceName = "SEQ_T_HEART_LOG_DET", allocationSize = 1)
  @Column(name = "ID", columnDefinition = "NUMBER(16)", nullable = false)
  @BeanProperty
  var id: Long = _

  /**
    * 设备SN
    */
  @Column(name = "DEV_SN", columnDefinition = "VARCHAR2(32)", nullable = false)
  @BeanProperty
  var devSn: String = _

  /**
    * 厂商编号
    */
  @Column(name = "FIRM_CODE", columnDefinition = "CHAR(4)", nullable = false)
  @BeanProperty
  var firmCode: String = _

  /**
    * 产品型号
    */
  @Column(name = "PROD_CODE", columnDefinition = "VARCHAR2(12)", nullable = false)
  @BeanProperty
  var prodCode: String = _

  /**
    * 上传时间
    */
  @Column(name = "UP_TIME", columnDefinition = "VARCHAR2(14)")
  @BeanProperty
  var upTime: String = _

  /**
    * 时间戳
    */
  @Column(name = "DEV_TIMESTAMP", columnDefinition = "VARCHAR2(14)")
  @BeanProperty
  var devTimestamp: String = _

  /**
    * 基站信息
    */
  @Column(name = "BASE_STATION", columnDefinition = "VARCHAR2(50)")
  @BeanProperty
  var baseStation: String = _

  /**
    * GPS
    */
  @Column(name = "BASE_GPS", columnDefinition = "VARCHAR2(128)")
  @BeanProperty
  var baseGps: String = _
}
