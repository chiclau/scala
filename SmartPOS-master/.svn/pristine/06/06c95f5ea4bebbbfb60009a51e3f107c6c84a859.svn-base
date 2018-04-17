package com.yada.spos.db.model

import javax.persistence._

import scala.beans.BeanProperty

/**
  * Created by pangChangSong on 2016/8/30.
  * 应用最新版本
  */
@Entity(name = "T_APP_FILE_LATEST")
class AppFileLatest {

  /**
    * 主键id
    */
  @Id
  @GeneratedValue(generator = "APP_FILE_LATEST_SEQ")
  @SequenceGenerator(name = "APP_FILE_LATEST_SEQ", sequenceName = "SEQ_T_APP_FILE_LATEST", allocationSize = 1)
  @Column(name = "APP_FILE_LATEST_ID", columnDefinition = "NUMBER(16)", nullable = false)
  @BeanProperty
  var appFileId: Long = _

  /**
    * 应用版本序号
    */
  @Column(name = "VERSION_CODE", columnDefinition = "VARCHAR2(20)")
  @BeanProperty
  var versionCode: String = _

  /**
    * 应用版本号
    */
  @Column(name = "VERSION_NAME", columnDefinition = "VARCHAR2(12)")
  @BeanProperty
  var versionName: String = _

  /**
    * 应用最低版本序号
    */
  @Column(name = "MIN_VERSION_CODE", columnDefinition = "VARCHAR2(20)")
  @BeanProperty
  var minVersionCode: String = _

  /**
    * 应用包名
    */
  @Column(name = "APP_PACKAGE_NAME", columnDefinition = "VARCHAR2(255)")
  @BeanProperty
  var appPackageName: String = _

  /**
    * 应用名称
    */
  @Column(name = "APP_NAME", columnDefinition = "VARCHAR2(255)")
  @BeanProperty
  var appName: String = _

  /**
    * 是否HD横屏模式 0-不支持 1-支持
    */
  @Column(name = "MODE_HD", columnDefinition = "CHAR(1)")
  @BeanProperty
  var modeHd: String = _

  /**
    * 是否手持模式 0-不支持 1-支持
    */
  @Column(name = "MODE_HAND", columnDefinition = "CHAR(1)")
  @BeanProperty
  var modeHand: String = _

  /**
    * 全量更新包的包名
    */
  @Column(name = "FILE_NAME", columnDefinition = "VARCHAR2(255)")
  @BeanProperty
  var fileName: String = _

  /**
    * 全量包MD5
    */
  @Column(name = "FILE_MD5", columnDefinition = "VARCHAR2(32)")
  @BeanProperty
  var fileMd5: String = _

  /**
    * 全量文件字节数
    */
  @Column(name = "FILE_LENGTH", columnDefinition = "NUMBER(10)")
  @BeanProperty
  var fileLength: Long = _

  /**
    * 发布时间
    */
  @Column(name = "PUBLIC_DATE", columnDefinition = "VARCHAR2(10)")
  @BeanProperty
  var publicDate: String = _

  /**
    * 强制更新，0-不强制    1-强制
    */
  @Column(name = "FORCE_UPDATE", columnDefinition = "CHAR(1)")
  @BeanProperty
  var forceUpdate: String = _

  /**
    * 删除后更新，0-不删除     1-删除后更新
    */
  @Column(name = "DELETE_UPDATE", columnDefinition = "CHAR(1)")
  @BeanProperty
  var deleteUpdate: String = _

  /**
    * 更新说明
    */
  @Column(name = "REMARK", columnDefinition = "VARCHAR2(210)")
  @BeanProperty
  var remark: String = _

  /**
    * 创建时间
    */
  @Column(name = "CRE_TIME", columnDefinition = "VARCHAR2(14)")
  @BeanProperty
  var creTime: String = _

  /**
    * APK权限编码串，分号分割
    */
  @Column(name = "APP_PERMISSION", columnDefinition = "VARCHAR2(4000)")
  @BeanProperty
  var appPermission: String = _

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
