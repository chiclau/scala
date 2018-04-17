package com.yada.spos.db.model

import javax.persistence._

import scala.beans.BeanProperty

/**
  * Created by pangChangSong on 2016/8/30.
  * apk涉及到的权限字典
  */
@Entity(name = "T_DICT_APK_PERMISSION")
class DictApkPermission {

  /**
    * 主键id
    */
  @Id
  @GeneratedValue(generator = "DICT_APK_PERMISSION_SEQ")
  @SequenceGenerator(name = "DICT_APK_PERMISSION_SEQ", sequenceName = "SEQ_T_DICT_APK_PERMISSION", allocationSize = 1)
  @Column(name = "APK_PERMISSION_ID", columnDefinition = "NUMBER(16)", nullable = false)
  @BeanProperty
  var apkPermissionId: Long = _

  @Column(name = "APK_PERMISSION_CODE", columnDefinition = "VARCHAR2(100)")
  @BeanProperty
  var apkPermissionCode: String = _

  @Column(name = "APK_PERMISSION_NAME", columnDefinition = "VARCHAR2(50)")
  @BeanProperty
  var apkPermissionName: String = _

  @Column(name = "APK_PERMISSION_ICON", columnDefinition = "VARCHAR2(127)")
  @BeanProperty
  var apkPermissionIcon: String = _

  /**
    * 创建时间
    */
  @Column(name = "CRE_TIME", columnDefinition = "VARCHAR2(14)")
  @BeanProperty
  var creTime: String = _

  /**
    * 更新时间
    */
  @Column(name = "UPD_TIME", columnDefinition = "VARCHAR2(14)")
  @BeanProperty
  var updTime: String = _

}
