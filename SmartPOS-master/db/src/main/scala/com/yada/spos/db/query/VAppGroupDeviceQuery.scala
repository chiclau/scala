package com.yada.spos.db.query

import java.util
import javax.persistence.criteria.{CriteriaBuilder, CriteriaQuery, Predicate, Root}

import com.yada.spos.db.model.VAppGroupDevice
import org.springframework.data.jpa.domain.Specification

import scala.beans.BeanProperty

/**
  * Created by pangChangSong on 2016/10/18.
  * 应用分组关联设备、关联设备视图查询类
  */
class VAppGroupDeviceQuery extends Specification[VAppGroupDevice] {

  /**
    * 应用分组id
    */
  @BeanProperty
  var appGroupId: String = _

  /**
    * 厂商编号
    */
  @BeanProperty
  var firmCode: String = _

  /**
    * 产品型号
    */
  @BeanProperty
  var prodCode: String = _

  /**
    * 商户号
    */
  @BeanProperty
  var merNo: String = _

  /**
    * 终端号
    */
  @BeanProperty
  var termNo: String = _

  /**
    * 设备终端绑定状态0-未绑定，1-绑定
    */
  @BeanProperty
  var status: String = _

  override def toPredicate(root: Root[VAppGroupDevice], cq: CriteriaQuery[_], cb: CriteriaBuilder): Predicate = {
    val list: util.List[Predicate] = new util.LinkedList[Predicate]()
    if (prodCode != null && !prodCode.trim().isEmpty) {
      list.add(cb.equal(root.get("prodCode").as(classOf[String]), prodCode.trim))
    }
    if (appGroupId != null && !appGroupId.isEmpty) {
      list.add(cb.equal(root.get("appGroupId").as(classOf[String]), appGroupId))
    }
    if (firmCode != null && !firmCode.trim().isEmpty) {
      list.add(cb.equal(root.get("firmCode").as(classOf[String]), firmCode.trim))
    }
    if (merNo != null && !merNo.trim().isEmpty) {
      list.add(cb.equal(root.get("merNo").as(classOf[String]), merNo.trim))
    }
    if (termNo != null && !termNo.trim().isEmpty) {
      list.add(cb.equal(root.get("termNo").as(classOf[String]), termNo.trim))
    }
    if (status != null && !status.trim().isEmpty) {
      list.add(cb.equal(root.get("status").as(classOf[String]), status.trim))
    }
    if (list.size() > 0) {
      cq.where(list.toArray(new Array[Predicate](list.size())): _*)
    }
    cq.getRestriction
  }
}
