package com.yada.spos.db.model

import javax.persistence.{Column, Entity, Id}

import scala.beans.BeanProperty

/**
  * Created by pangChangSong on 2016/9/26.
  * 总对总商户基础表
  */
@Entity(name = "T_B_MERCHANT_BASE")
class HhapMerchant {

  /**
    * 商户号
    */
  @Id
  @Column(name = "MER_ID", columnDefinition = "CHAR(15)", nullable = false)
  @BeanProperty
  var merId: String = _

  /**
    * 一级分行结构号
    */
  @Column(name = "ZONE_ORG_ID", columnDefinition = "VARCHAR2(16)")
  @BeanProperty
  var zoneOrgId: String = _

}
