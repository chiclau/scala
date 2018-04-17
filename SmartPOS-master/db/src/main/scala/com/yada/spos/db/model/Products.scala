package com.yada.spos.db.model

import javax.persistence._

import scala.beans.BeanProperty

/**
  * Created by duan on 2016/8/30.
  * 产品型号信息
  */
@Entity(name = "t_product")
@IdClass(classOf[ProductsPK])
class Products {
  /**
    * 产品型号
    */
  @Id
  @Column(name = "PROD_CODE", columnDefinition = "VARCHAR2(12)", nullable = false)
  @BeanProperty
  var prodCode: String = _
  /**
    * 产品型号名称
    */
  @Column(name = "PROD_NAME", columnDefinition = "VARCHAR2(60)")
  @BeanProperty
  var prodName: String = _
  /**
    * 所属厂商编号
    */
  @Id
  @Column(name = "FIRM_CODE", columnDefinition = "CHAR(4)")
  @BeanProperty
  var firmCode: String = _
  /**
    * 所属厂家名称
    */
  @Column(name = "FIRM_NAME", columnDefinition = "VARCHAR2(120)")
  @BeanProperty
  var firmName: String = _
  /**
    * 设备模式 1 - HD模式  2 - HAND模式
    */
  @Column(name = "DEVICE_MODE", columnDefinition = "CHAR(1)")
  @BeanProperty
  var deviceMode: String = _
}

@SerialVersionUID(1L)
class ProductsPK extends Serializable {
  @BeanProperty
  var prodCode: String = _
  @BeanProperty
  var firmCode: String = _

  def canEqual(other: Any): Boolean = other.isInstanceOf[ProductsPK]

  override def equals(other: Any): Boolean = other match {
    case that: ProductsPK =>
      (that canEqual this) &&
        prodCode == that.prodCode &&
        firmCode == that.firmCode
    case _ => false
  }

  override def hashCode(): Int = {
    //noinspection HashCodeUsesVar
    val state = Seq(prodCode, firmCode)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
