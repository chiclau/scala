package com.yada.spos.db.query

import java.util
import javax.persistence.criteria.{CriteriaBuilder, CriteriaQuery, Predicate, Root}

import com.yada.spos.db.model.AppGroup
import org.springframework.data.jpa.domain.Specification

import scala.beans.BeanProperty

/**
  * Created by pangChangSong on 2016/9/8.
  * 应用分组查询类
  */
class AppGroupQuery extends Specification[AppGroup] {

  @BeanProperty
  var appGroupName: String = _
  @BeanProperty
  var orgType: String = _
  @BeanProperty
  var orgId: String = _

  override def toPredicate(root: Root[AppGroup], query: CriteriaQuery[_], cb: CriteriaBuilder): Predicate = {
    val list: util.List[Predicate] = new util.LinkedList[Predicate]()
    if (appGroupName != null && !appGroupName.trim().isEmpty) {
      list.add(cb.equal(root.get("appGroupName").as(classOf[String]), appGroupName.trim))
    }
    if (orgId != null && !orgId.trim().isEmpty) {
      list.add(cb.equal(root.get("orgId").as(classOf[String]), orgId))
    }
    if (orgType != null && !orgType.trim().isEmpty) {
      list.add(cb.equal(root.get("orgType").as(classOf[String]), orgType.trim))
    }
    if (!((orgId == "00" && orgType == "1") || (orgId == "000" && orgType == "2"))) {
      //分行不能看到默认分组
      list.add(cb.notEqual(root.get("isDefaultGroup").as(classOf[String]), "0"))
    }
    if (list.size() > 0) {
      query.where(list.toArray(new Array[Predicate](list.size())): _*)
    }
    query.getRestriction
  }
}
