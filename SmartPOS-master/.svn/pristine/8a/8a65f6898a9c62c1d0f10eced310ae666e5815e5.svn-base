package com.yada.spos.db.model

import javax.persistence.{Column, Entity, Id}

import scala.beans.BeanProperty

/**
  * Created by duan on 2016/8/29.
  * 厂商信息
  */
@Entity(name = "t_firm")
class Firm {
  /**
    * 厂商编号(主键)(不可重复)
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
}
