package com.yada.spos.db.query

import java.util
import javax.persistence.criteria.{CriteriaBuilder, CriteriaQuery, Predicate, Root}

import com.yada.spos.db.model.OnlineParam
import org.springframework.data.jpa.domain.Specification

import scala.beans.BeanProperty

/**
  * 设备参数维护查询实体
  * Created by nickDu on 2016/9/8.
  */
class OnlineParamQuery extends Specification[OnlineParam] {
  /**
    * 参数名称
    */
  @BeanProperty
  var paramName: String = _

  /**
    * 参数值
    */
  @BeanProperty
  var paramValue: String = _

  /**
    * 机构类型
    */
  @BeanProperty
  var orgType: String = _

  /**
    * 机构id
    */
  @BeanProperty
  var orgId: String = _

  override def toPredicate(root: Root[OnlineParam], cq: CriteriaQuery[_], cb: CriteriaBuilder): Predicate = {
    val list: util.List[Predicate] = new util.LinkedList[Predicate]()
    if (paramName != null && !paramName.trim().isEmpty) {
      list.add(cb.equal(root.get("paramName").as(classOf[String]), paramName.trim))
    }
    if (paramValue != null && !paramValue.trim().isEmpty) {
      list.add(cb.equal(root.get("paramValue").as(classOf[String]), paramValue.trim))
    }
    if (orgId != null && !orgType.trim().isEmpty) {
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
