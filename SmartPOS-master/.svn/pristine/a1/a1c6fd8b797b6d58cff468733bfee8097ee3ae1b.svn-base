package com.yada.spos.db.query

import java.util
import javax.persistence.criteria.{CriteriaBuilder, CriteriaQuery, Predicate, Root}

import com.yada.spos.db.model.OtaFileLatest
import org.springframework.data.jpa.domain.Specification

import scala.beans.BeanProperty

/**
  * Created by duan on 2016/9/5.
  */
class OtaLatestQuery extends Specification[OtaFileLatest] {

  /**
    * 产品名称
    */
  @BeanProperty
  var prodName: String = _

  /**
    * 应用名称
    */
  @BeanProperty
  var otaName: String = _


  override def toPredicate(root: Root[OtaFileLatest], cq: CriteriaQuery[_], cb: CriteriaBuilder): Predicate = {
    val list: util.List[Predicate] = new util.LinkedList[Predicate]()
    if (prodName != null && !prodName.trim().isEmpty) {
      list.add(cb.equal(root.get("firmName").as(classOf[String]), prodName.trim))
    }
    if (otaName != null && !otaName.trim().isEmpty) {
      list.add(cb.equal(root.get("otaName").as(classOf[String]), otaName.trim))
    }
    if (list.size() > 0) {
      cq.where(list.toArray(new Array[Predicate](list.size())): _*)
    }
    cq.getRestriction
  }
}
