package com.yada.spos.db.query

import java.util
import javax.persistence.criteria.{CriteriaBuilder, CriteriaQuery, Predicate, Root}

import com.yada.spos.db.model.AppFileHistory
import org.springframework.data.jpa.domain.Specification

import scala.beans.BeanProperty

/**
  * Created by duan on 2016/8/31.
  * 设备管理的查询类
  */
class AppFileHistoryQuery extends Specification[AppFileHistory] {
  /**
    * 应用名称
    */
  @BeanProperty
  var appName: String = _

  @BeanProperty
  var orgId: String = _

  @BeanProperty
  var orgType: String = _

  override def toPredicate(root: Root[AppFileHistory], cq: CriteriaQuery[_], cb: CriteriaBuilder): Predicate = {
    val list: util.List[Predicate] = new util.LinkedList[Predicate]()
    if (appName != null && !appName.trim().isEmpty) {
      list.add(cb.equal(root.get("appName").as(classOf[String]), appName.trim))
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
