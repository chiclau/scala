package com.yada.spos.db.model

import javax.persistence.{Column, Entity, Id, IdClass}

import scala.beans.BeanProperty

/**
  * Created by duan on 2016/8/2.
  * 终端密钥表
  */
@Entity(name = "T_B_TERM_WORK_KEY")
@IdClass(classOf[TermWorkKeyPK])
class TermWorkKey {
  /**
    * 商户号
    */
  @Id
  @Column(name = "MERCHANT_ID", columnDefinition = "VARCHAR2(15)", nullable = false)
  @BeanProperty
  var merChantId: String = _
  /**
    * 终端号
    */
  @Id
  @Column(name = "TERMINAL_ID", columnDefinition = "VARCHAR2(8)")
  @BeanProperty
  var terminalId: String = _

  /**
    * 主密钥
    */
  @Column(name = "ZMK_LMK", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var zmkLmk: String = _
  /**
    * TMK_LMK
    */
  @Column(name = "TMK_LMK", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var tmkLmk: String = _
  /**
    * 设备密钥校验值
    */
  @Column(name = "TMK_ZMK", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var tmkZmk: String = _

  /**
    * TPK_LMK
    */
  @Column(name = "TPK_LMK", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var tpkLmk: String = _
  /**
    * TPK_TMK
    */
  @Column(name = "TPK_TMK", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var tpkTmk: String = _

  /**
    * 通讯密钥校验值
    */
  @Column(name = "TAK_LMK", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var takLmk: String = _

  /**
    * TAK_TMK
    */
  @Column(name = "TAK_TMK", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var takTmk: String = _


  /**
    * ZEK_LMK
    */
  @Column(name = "ZEK_LMK", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var zekLmk: String = _

  /**
    * ZEK_TMK
    */
  @Column(name = "ZEK_TMK", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var zekTmk: String = _

  /**
    * 签到时间
    */
  @Column(name = "LOGIN_TIME", columnDefinition = "VARCHAR2(14)")
  @BeanProperty
  var loginTime: String = _

  /**
    * 签到时间
    */
  @Column(name = "SETTLE_FLAG", columnDefinition = "CHAR(1)")
  @BeanProperty
  var selectFlag: String = _

  /**
    * 签到时间
    */
  @Column(name = "BATCH_NO", columnDefinition = "NUMBER(6)")
  @BeanProperty
  var batchNo: Int = _
  /**
    * 签到时间
    */
  @Column(name = "LOGIN_FLAG", columnDefinition = "CHAR(1)")
  @BeanProperty
  var loginFlag: String = _
  /**
    * 签到时间
    */
  @Column(name = "UPDATE_DATE", columnDefinition = "VARCHAR2(8)")
  @BeanProperty
  var updateDate: String = _


  /**
    * 签到时间
    */
  @Column(name = "UPDATE_TIME", columnDefinition = "VARCHAR2(6)")
  @BeanProperty
  var updateTime: String = _

  /**
    * 签到时间
    */
  @Column(name = "UPDATE_MOD", columnDefinition = "VARCHAR2(4)")
  @BeanProperty
  var updateMod: String = _

}

@SerialVersionUID(1L)
class TermWorkKeyPK extends Serializable {
  @BeanProperty
  var merChantId: String = _
  @BeanProperty
  var terminalId: String = _

  def canEqual(other: Any): Boolean = other.isInstanceOf[TermWorkKeyPK]

  override def equals(other: Any): Boolean = other match {
    case that: TermWorkKeyPK =>
      (that canEqual this) &&
        merChantId == that.merChantId &&
        terminalId == that.terminalId
    case _ => false
  }

  override def hashCode(): Int = {
    //noinspection HashCodeUsesVar
    val state = Seq(merChantId, terminalId)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
