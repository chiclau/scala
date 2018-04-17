package com.yada.spos.db.query

import javax.persistence.criteria.{CriteriaBuilder, CriteriaQuery, Predicate, Root}

import com.yada.spos.db.model.Device
import org.springframework.data.jpa.domain.Specification

import scala.beans.BeanProperty

/**
  * Created by duan on 2016/8/31.
  * 设备管理的查询类
  */
class DeviceQuery extends Specification[Device] {
  /**
    * 设备sn
    */
  @BeanProperty
  var devSn: String = _
  /**
    * 厂商编号
    */
  @BeanProperty
  var firmCode: String = _

  /**
    * 产品编号
    */
  @BeanProperty
  var prodCode: String = _
  /**
    * 设备状态 0-未关联 1-已关联商户终端
    */
  @BeanProperty
  var status: String = _
  /**
    * 是否激活 0-未激活  1-激活
    */
  @BeanProperty
  var isActive: String = _
  /**
    * 机构ID
    */
  @BeanProperty
  var orgId: String = _
  /**
    * 机构类型  1-固话  2-总对总
    */
  @BeanProperty
  var orgType: String = _
  /**
    * 终端号
    */
  @BeanProperty
  var termNo: String = _
  /**
    * 商户号
    */
  @BeanProperty
  var merNo: String = _

  override def toPredicate(root: Root[Device], cq: CriteriaQuery[_], cb: CriteriaBuilder): Predicate = {
    val list: java.util.List[Predicate] = new java.util.LinkedList[Predicate]()
    if (devSn != null && !devSn.trim().isEmpty) {
      list.add(cb.equal(root.get("devSn").as(classOf[String]), devSn.trim))
    }
    if (firmCode != null && !firmCode.trim().isEmpty) {
      list.add(cb.equal(root.get("firmCode").as(classOf[String]), firmCode.trim))
    }
    if (prodCode != null && !prodCode.trim().isEmpty) {
      list.add(cb.equal(root.get("prodCode").as(classOf[String]), prodCode.trim))
    }
    if (status != null && !status.trim().isEmpty) {
      list.add(cb.equal(root.get("status").as(classOf[String]), status.trim))
    }
    if (isActive != null && !isActive.trim().isEmpty) {
      list.add(cb.equal(root.get("isActive").as(classOf[String]), isActive.trim))
    }
    if (orgId != null && !orgId.trim().isEmpty) {
      list.add(cb.equal(root.get("orgId").as(classOf[String]), orgId))
    }
    if (orgType != null && !orgType.trim().isEmpty) {
      list.add(cb.equal(root.get("orgType").as(classOf[String]), orgType.trim))
    }
    if (termNo != null && !termNo.trim().isEmpty) {
      list.add(cb.equal(root.get("termNo").as(classOf[String]), termNo.trim))
    }
    if (merNo != null && !merNo.trim().isEmpty) {
      list.add(cb.equal(root.get("merNo").as(classOf[String]), merNo.trim))
    }
    if (list.size() > 0) {
      cq.where(list.toArray(new Array[Predicate](list.size())): _*)
    }
    cq.getRestriction
  }
}
