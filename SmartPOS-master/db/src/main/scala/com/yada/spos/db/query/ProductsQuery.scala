package com.yada.spos.db.query

import java.util
import javax.persistence.criteria.{CriteriaBuilder, CriteriaQuery, Predicate, Root}
import com.yada.spos.db.model.Products
import org.springframework.data.jpa.domain.Specification
import scala.beans.BeanProperty

/**
  * Created by duan on 2016/8/30.
  * 产品型号管理查询实体
  */
class ProductsQuery extends Specification[Products] {
  /**
    * 产品型号
    */
  @BeanProperty
  var prodCode: String = _
  /**
    * 产品型号名称
    */
  @BeanProperty
  var prodName: String = _
  /**
    * 所属厂商编号
    */
  @BeanProperty
  var firmCode: String = _
  /**
    * 所属厂商名称
    */
  @BeanProperty
  var firmName: String = _
  /**
    * 设备模式:  1 - HD模式   2 - HAND模式
    */
  @BeanProperty
  var deviceMode: String = _

  override def toPredicate(root: Root[Products], cq: CriteriaQuery[_], cb: CriteriaBuilder): Predicate = {
    val list: util.List[Predicate] = new util.LinkedList[Predicate]()
    if (prodCode != null && !prodCode.trim().isEmpty) {
      list.add(cb.equal(root.get("prodCode").as(classOf[String]), prodCode.trim))
    }
    if (prodName != null && !prodName.trim().isEmpty) {
      list.add(cb.equal(root.get("prodName").as(classOf[String]), prodName.trim))
    }
    if (firmCode != null && !firmCode.trim().isEmpty) {
      list.add(cb.equal(root.get("firmCode").as(classOf[String]), firmCode.trim))
    }
    if (firmName != null && !firmName.trim().isEmpty) {
      list.add(cb.equal(root.get("firmName").as(classOf[String]), firmName.trim))
    }
    if (deviceMode != null && !deviceMode.trim().isEmpty) {
      list.add(cb.equal(root.get("deviceMode").as(classOf[Int]), deviceMode.trim))
    }
    if (list.size() > 0) {
      cq.where(list.toArray(new Array[Predicate](list.size())): _*)
    }
    cq.getRestriction
  }

}
