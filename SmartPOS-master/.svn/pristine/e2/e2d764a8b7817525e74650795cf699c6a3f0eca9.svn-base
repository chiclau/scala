package com.yada.spos.db.model

import javax.persistence._

import scala.beans.BeanProperty

/**
  * Created by pangChangSong on 2016/8/30.
  * 机构
  */
@Entity(name = "T_B_ORG")
class Org {

  /**
    * 机构id
    */
  @Id
  @Column(name = "ORG_ID", columnDefinition = "VARCHAR2(16)", nullable = false)
  @BeanProperty
  var orgId: String = _

  /**
    * 上级机构编码
    */
  @Column(name = "P_ORG_ID", columnDefinition = "VARCHAR2(16)")
  @BeanProperty
  var pOrgId: String = _

  /**
    * 机构级别
    */
  @Column(name = "ORG_LEV", columnDefinition = "NUMBER(2)")
  @BeanProperty
  var orgLev: Long = _

  /**
    * 银联联行号
    */
  @Column(name = "CUP_ID", columnDefinition = "VARCHAR2(11)")
  @BeanProperty
  var cupId: String = _

  /**
    * 区域代码, 老4位行号
    */
  @Column(name = "ZONE_CODE", columnDefinition = "CHAR(4)")
  @BeanProperty
  var zoneCode: String = _

  /**
    * 行号, 新eacq行号
    */
  @Column(name = "BANK_NO", columnDefinition = "CHAR(10)")
  @BeanProperty
  var bankNo: String = _

  /**
    * 机构名称
    */
  @Column(name = "NAME", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var name: String = _

  /**
    * 联系人
    */
  @Column(name = "CONTACT", columnDefinition = "VARCHAR2(8)")
  @BeanProperty
  var contact: String = _

  /**
    * 联系电话
    */
  @Column(name = "TEL", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var tel: String = _

  /**
    * 传真号码
    */
  @Column(name = "FAX", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var fax: String = _

  /**
    * 机构地址
    */
  @Column(name = "ADDR", columnDefinition = "VARCHAR2(64)")
  @BeanProperty
  var addr: String = _

  /**
    * 状态 (0 关闭、1 正常、2 暂停、3 未启用)
    */
  @Column(name = "STATUS", columnDefinition = "CHAR(1)")
  @BeanProperty
  var status: String = _

  /**
    * 新线系统标志, 新旧线标识 1上线 0 不上线
    */
  @Column(name = "ONLINE_FLAG", columnDefinition = "CHAR(1)")
  @BeanProperty
  var onlineFlag: String = _

  /**
    * 核心网点机构号
    */
  @Column(name = "ACQ_ORG_ID", columnDefinition = "CHAR(5)")
  @BeanProperty
  var acqOrgId: String = _

  /**
    * 核心省行机构号
    */
  @Column(name = "ACQ_BANK_NO", columnDefinition = "CHAR(5)")
  @BeanProperty
  var acqBankNo: String = _

  /**
    * EACQ_ORG_ID
    */
  @Column(name = "EACQ_ORG_ID", columnDefinition = "VARCHAR2(16)")
  @BeanProperty
  var eacqOrgId: String = _
}
