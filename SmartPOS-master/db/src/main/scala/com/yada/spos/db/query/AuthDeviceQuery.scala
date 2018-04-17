package com.yada.spos.db.query

import java.util
import javax.persistence.criteria.{CriteriaBuilder, CriteriaQuery, Predicate, Root}

import com.yada.spos.db.model.AuthDevice
import org.springframework.data.jpa.domain.Specification

import scala.beans.BeanProperty

/**
  * Created by duan on 2016/9/2.
  * 母POS管理的查询类
  */
class AuthDeviceQuery extends Specification[AuthDevice] {
  /**
    * 母POSsn
    */
  @BeanProperty
  var authPosSn: String = _
  /**
    * 机构关联ID
    */
  @BeanProperty
  var orgId: String = _

  /**
    * 机构类型  1-固话  2-总对总
    */
  @BeanProperty
  var orgType: String = _

  override def toPredicate(root: Root[AuthDevice], cq: CriteriaQuery[_], cb: CriteriaBuilder): Predicate = {
    val list: util.List[Predicate] = new util.LinkedList[Predicate]()
    if (authPosSn != null && !authPosSn.trim().isEmpty) {
      list.add(cb.equal(root.get("authPosSn").as(classOf[String]), authPosSn.trim))
    }
    if (orgId != null) {
      list.add(cb.equal(root.get("orgId").as(classOf[String]), orgId))
    }
    if (orgType != null && !orgType.trim().isEmpty) {
      list.add(cb.equal(root.get("orgType").as(classOf[String]), orgType.trim))
    }
    if (list.size() > 0) {
      cq.where(list.toArray(new Array[Predicate](list.size())): _*)
    }
    cq.getRestriction
  }
}
