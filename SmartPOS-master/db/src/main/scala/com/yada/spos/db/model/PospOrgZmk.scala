package com.yada.spos.db.model

import javax.persistence.{Column, Entity, Id}

import scala.beans.BeanProperty

/**
  * 机构密钥表
  * Created by nickDu on 2016/9/4.
  */
@Entity(name = "T_B_POSP_ORG_ZMK")
class PospOrgZmk {
  /**
    * 机构id
    */
  @Id
  @Column(name = "ORG_ID", columnDefinition = "VARCHAR2(16)", nullable = false)
  @BeanProperty
  var orgId: String = _

  /**
    * 区域密钥密文LMK下
    */
  @Column(name = "ZMK_LMK", columnDefinition = "VARCHAR2(128)")
  @BeanProperty
  var zmkLmk: String = _

  /**
    * 密钥校验值
    */
  @Column(name = "CHECK_VALUE", columnDefinition = "VARCHAR2(128)")
  @BeanProperty
  var checkValue: String = _
}
