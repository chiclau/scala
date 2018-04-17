package com.yada.spos.db.model

import javax.persistence._
import scala.beans.BeanProperty

/**
  * Created by duan on 2016/8/31.
  * 设备管理
  */
@Entity(name = "t_device")
@IdClass(classOf[DevicePK])
class Device {
  /**
    * 设备SN号(联合主键)
    */
  @Id
  @Column(name = "DEV_SN", columnDefinition = "VARCHAR2(32)", nullable = false)
  @BeanProperty
  var devSn: String = _
  /**
    * 厂商编号(联合主键)
    */
  @Id
  @Column(name = "FIRM_CODE", columnDefinition = "CHAR(4)", nullable = false)
  @BeanProperty
  var firmCode: String = _
  /**
    * 厂商名称
    */
  @Column(name = "FIRM_NAME", columnDefinition = "VARCHAR2(120)")
  @BeanProperty
  var firmName: String = _
  /**
    * 产品型号
    */
  @Column(name = "PROD_CODE", columnDefinition = "VARCHAR2(12)")
  @BeanProperty
  var prodCode: String = _

  /**
    * 产品型号名称
    */
  @Column(name = "PROD_NAME", columnDefinition = "VARCHAR2(60)")
  @BeanProperty
  var prodName: String = _

  /**
    * 是否激活 0-未激活  1-激活
    */
  @Column(name = "IS_ACTIVE", columnDefinition = "CHAR(1)")
  @BeanProperty
  var isActive: String = _

  /**
    * 加密机密钥保护的设备密钥
    */
  @Column(name = "DEK_LMK", columnDefinition = "VARCHAR2(48)")
  @BeanProperty
  var dekLmk: String = _
  /**
    * 区域密钥保护的设备密钥
    */
  @Column(name = "DEK_ZMK", columnDefinition = "VARCHAR2(48)")
  @BeanProperty
  var dekZmk: String = _
  /**
    * 设备密钥校验值
    */
  @Column(name = "DEK_KCV", columnDefinition = "VARCHAR2(16)")
  @BeanProperty
  var dekKcv: String = _

  /**
    * 加密机保护的通讯密钥
    */
  @Column(name = "ZEK_LMK", columnDefinition = "VARCHAR2(48)")
  @BeanProperty
  var zekLmk: String = _
  /**
    * 设备密钥保护的通讯密钥
    */
  @Column(name = "ZEK_DEK", columnDefinition = "VARCHAR2(48)")
  @BeanProperty
  var zekDek: String = _

  /**
    * 通讯密钥校验值
    */
  @Column(name = "ZEK_KCV", columnDefinition = "VARCHAR2(16)")
  @BeanProperty
  var zekKcv: String = _

  /**
    * 加密机保护的收单密钥
    */
  @Column(name = "TMK_LMK", columnDefinition = "VARCHAR2(48)")
  @BeanProperty
  var tmkLmk: String = _


  /**
    * 设备密钥保护的收单密钥
    */
  @Column(name = "TMK_DEK", columnDefinition = "VARCHAR2(48)")
  @BeanProperty
  var tmkDek: String = _

  /**
    * 收单密钥校验值
    */
  @Column(name = "TMK_KCV", columnDefinition = "VARCHAR2(16)")
  @BeanProperty
  var tmkKcv: String = _


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
    * 设备状态 0-未关联 1-已关联商户终端
    */
  @Column(name = "STATUS", columnDefinition = "CHAR(1)")
  @BeanProperty
  var status: String = _


  /**
    * 硬件识别码   SA1===SA1(3G)   SA2===SA2(4G)
    */
  @Column(name = "DEVICE_FLAG", columnDefinition = "VARCHAR2(12)")
  @BeanProperty
  var deviceFlag: String = _

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

@SerialVersionUID(2902136624416910546L)
class DevicePK extends Serializable {
  @BeanProperty
  var devSn: String = _
  @BeanProperty
  var firmCode: String = _

  def canEqual(other: Any): Boolean = other.isInstanceOf[DevicePK]

  override def equals(other: Any): Boolean = other match {
    case that: DevicePK =>
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

