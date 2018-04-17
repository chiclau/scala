package com.yada.spos.db.model

import javax.persistence._

import scala.beans.BeanProperty

/**
  * Created by pangChangSong on 2016/9/3.
  * 固件发布最新
  */
@Entity(name = "T_OTA_FILE_LATEST")
class OtaFileLatest {
  /**
    * 主键id
    */
  @Id
  @GeneratedValue(generator = "OTA_FILE_LATEST_SEQ")
  @SequenceGenerator(name = "OTA_FILE_LATEST_SEQ", sequenceName = "SEQ_T_OTA_FILE_LATEST", allocationSize = 1)
  @Column(name = "RELEASE_ID", columnDefinition = "NUMBER(16)", nullable = false)
  @BeanProperty
  var releaseId: Long = _

  /**
    * 生产厂商编码
    */
  @Column(name = "FIRM_CODE", columnDefinition = "CHAR(4)")
  @BeanProperty
  var firmCode: String = _

  /**
    * 生产厂商名称
    */
  @Column(name = "FIRM_NAME", columnDefinition = "VARCHAR2(120)")
  @BeanProperty
  var firmName: String = _

  /**
    * 产品型号
    */
  @Column(name = "PROD_CODE", columnDefinition = "VARCHAR2(12)")
  @BeanProperty
  var prodCode: String = _

  /**
    * 产品名称
    */
  @Column(name = "PROD_NAME", columnDefinition = "VARCHAR2(60)")
  @BeanProperty
  var prodName: String = _

  /**
    * 版本名称
    */
  @Column(name = "VERSION_NAME", columnDefinition = "VARCHAR2(20)")
  @BeanProperty
  var versionName: String = _

  /**
    * 最小版本号
    */
  @Column(name = "MIN_VERSION_NAME", columnDefinition = "VARCHAR2(20)")
  @BeanProperty
  var minVersionName: String = _

  /**
    * 应用程序包名
    */
  @Column(name = "OTA_PACKAGE_NAME", columnDefinition = "VARCHAR2(255)")
  @BeanProperty
  var otaPackageName: String = _

  /**
    * 应用名称
    */
  @Column(name = "OTA_NAME", columnDefinition = "VARCHAR2(255)")
  @BeanProperty
  var otaName: String = _

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
    * 更新说明
    */
  @Column(name = "REMARK", columnDefinition = "VARCHAR2(256)")
  @BeanProperty
  var remark: String = _

  /**
    * 上传时间
    */
  @Column(name = "UP_TIME", columnDefinition = "VARCHAR2(14)")
  @BeanProperty
  var upTime: String = _

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
