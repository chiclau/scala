package com.yada.spos.db.query

import java.util
import javax.persistence.criteria.{CriteriaBuilder, CriteriaQuery, Predicate, Root}
import com.yada.spos.db.model.Firm
import org.springframework.data.jpa.domain.Specification
import scala.beans.BeanProperty


/**
  * Created by duan on 2016/8/30.
  * 厂商管理的查询实体
  */
class FirmQuery extends Specification[Firm] {
  /**
    * 厂商代码
    */
  @BeanProperty
  var firmCode: String = _
  /**
    * 厂商名称
    */
  @BeanProperty
  var firmName: String = _

  override def toPredicate(root: Root[Firm], cq: CriteriaQuery[_], cb: CriteriaBuilder): Predicate = {
    val list: util.List[Predicate] = new util.LinkedList[Predicate]()
    if (firmCode != null && !firmCode.trim().isEmpty) {
      list.add(cb.like(root.get("firmCode").as(classOf[String]), s"%${firmCode.trim}%"))
    }
    if (firmName != null && !firmName.trim().isEmpty) {
      list.add(cb.like(root.get("firmName").as(classOf[String]), s"%${firmName.trim}%"))
    }
    if (list.size() > 0) {
      cq.where(list.toArray(new Array[Predicate](list.size())): _*)
    }
    cq.getRestriction
  }
}
