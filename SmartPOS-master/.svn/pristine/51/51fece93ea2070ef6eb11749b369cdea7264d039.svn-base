package com.yada.spos.db.query

import java.util
import javax.persistence.criteria.{CriteriaBuilder, CriteriaQuery, Predicate, Root}
import com.yada.spos.db.model.TermWorkKey
import org.springframework.data.jpa.domain.Specification

import scala.beans.BeanProperty

/**
  * Created by duan on 2016/9/2.
  * 终端密钥查询类
  */
class BTermWorkKeyQuery extends Specification[TermWorkKey] {
  /**
    * 商户号
    */
  @BeanProperty
  var merChantId: String = _
  /**
    * 终端号
    */
  @BeanProperty
  var terminalId: String = _

  override def toPredicate(root: Root[TermWorkKey], cq: CriteriaQuery[_], cb: CriteriaBuilder): Predicate = {
    val list: util.List[Predicate] = new util.LinkedList[Predicate]()
    if (merChantId != null && !merChantId.trim().isEmpty) {
      list.add(cb.equal(root.get("merChantId").as(classOf[String]), merChantId.trim))
    }
    if (terminalId != null && !terminalId.trim().isEmpty) {
      list.add(cb.equal(root.get("terminalId").as(classOf[String]), terminalId.trim))
    }
    if (list.size() > 0) {
      cq.where(list.toArray(new Array[Predicate](list.size())): _*)
    }
    cq.getRestriction
  }
}
