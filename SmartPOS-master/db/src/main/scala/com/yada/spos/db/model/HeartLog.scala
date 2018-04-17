package com.yada.spos.db.model

import javax.persistence.{Column, Entity, Id, IdClass}

import scala.beans.BeanProperty

/**
  * Created by pangChangSong on 2016/9/6.
  * 心跳日志
  */
@Entity(name = "T_HEART_LOG")
@IdClass(classOf[HeartLogPK])
class HeartLog {

  /**
    * 设备SN
    */
  @Id
  @Column(name = "DEV_SN", columnDefinition = "VARCHAR2(32)", nullable = false)
  @BeanProperty
  var devSn: String = _

  /**
    * 厂商代码
    */
  @Id
  @Column(name = "FIRM_CODE", columnDefinition = "CHAR(4)", nullable = false)
  @BeanProperty
  var firmCode: String = _

  /**
    * 产品型号
    */
  @Column(name = "PROD_CODE", columnDefinition = "VARCHAR2(12)")
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

@SerialVersionUID(1L)
class HeartLogPK extends Serializable {

  @BeanProperty
  var devSn: String = _

  @BeanProperty
  var firmCode: String = _


  def canEqual(other: Any): Boolean = other.isInstanceOf[HeartLogPK]

  override def equals(other: Any): Boolean = other match {
    case that: HeartLogPK =>
      (that canEqual this) &&
        devSn == that.devSn &&
        firmCode == that.firmCode
    case _ => false
  }

  override def hashCode(): Int = {
    //noinspection HashCodeUsesVar
    val state = Seq(devSn, firmCode)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

