package com.yada.spos.db.query

import java.util
import javax.persistence.criteria.{CriteriaBuilder, CriteriaQuery, Predicate, Root}

import com.yada.spos.db.model.OtaHistory
import org.springframework.data.jpa.domain.Specification

import scala.beans.BeanProperty

/**
  * Created by duan on 2016/9/5.
  */
class OtaHistoryQuery extends Specification[OtaHistory] {
  /**
    * 产品型号
    */
  @BeanProperty
  var prodCode: String = _

  /**
    * 厂商编号
    */
  @BeanProperty
  var firmCode: String = _

  /**
    * 机构号
    */
  @BeanProperty
  var orgId: String = _

  /**
    * 机构类型
    */
  @BeanProperty
  var orgType: String = _


  override def toPredicate(root: Root[OtaHistory], cq: CriteriaQuery[_], cb: CriteriaBuilder): Predicate = {
    val list: util.List[Predicate] = new util.LinkedList[Predicate]()
    if (prodCode != null && !prodCode.trim().isEmpty) {
      list.add(cb.equal(root.get("prodCode").as(classOf[String]), prodCode.trim))
    }
    if (firmCode != null && !firmCode.trim().isEmpty) {
      list.add(cb.equal(root.get("firmCode").as(classOf[String]), firmCode.trim))
    }
    if (orgId != null && !orgId.trim().isEmpty) {
      list.add(cb.equal(root.get("orgId").as(classOf[String]), orgId))
    }
    if (orgType != null && !orgType.trim().isEmpty) {
      list.add(cb.equal(root.get("orgType").as(classOf[String]), orgType))
    }
    if (list.size() > 0) {
      cq.where(list.toArray(new Array[Predicate](list.size())): _*)
    }
    cq.getRestriction
  }
}
