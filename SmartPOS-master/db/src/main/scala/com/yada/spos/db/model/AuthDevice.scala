package com.yada.spos.db.model

import javax.persistence._

import scala.beans.BeanProperty

/**
  * Created by duan on 2016/9/2.
  */
@Entity(name = "t_auth_device")
class AuthDevice {
  /**
    * 授权设备的序列号SN
    */
  @Id
  @Column(name = "AUTH_POS_SN", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var authPosSn: String = _
  /**
    * rsa公钥
    */
  @Column(name = "RSA_PUBLIC_KEY", columnDefinition = "VARCHAR2(3072)")
  @BeanProperty
  var rsaPublicKey: String = _
  /**
    * 授权设备状态
    */
  @Column(name = "STATUS", columnDefinition = "CHAR(1)")
  @BeanProperty
  var status: String = _
  /**
    * 机构ID
    */
  @Column(name = "ORG_ID", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var orgId: String = _

  /**
    * 机构类型  1-固话  2-总对总
    */
  @Column(name = "ORG_TYPE", columnDefinition = "CHAR(1)")
  @BeanProperty
  var orgType: String = _

}
