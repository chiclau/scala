package com.yada.spos.db.dao

import java.util
import javax.persistence.EntityManager

import com.yada.spos.db.model.AppGroupApps
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{Page, PageImpl, Pageable}
import scala.collection.convert.decorateAll._

/**
  * Created by pangCHangSong on 2016/10/19.
  * 应用分组取消关联应用dao
  */
class AppsUnAssociateAppGroupDao {

  @Autowired
  var entityManager: EntityManager = _

  /**
    * 分页查询sql
    *
    * @param orgId         机构号
    * @param orgType       机构类型
    * @param curAppGroupId 应用分组id
    * @param appName       应用名称
    * @param curTime       当前上传时间
    * @param pageable      pageable
    * @return Page分页对象
    */
  def findAll(orgId: String, orgType: String, curAppGroupId: String, appName: String, curTime: String, pageable: Pageable): Page[AppGroupApps] = {
    val total = findCount(orgId, orgType, curAppGroupId, appName, curTime)
    if (total > 0) {
      //组装sql
      val pageNumber = pageable.getPageNumber
      val pageSize = pageable.getPageSize
      val startNum = pageNumber * pageSize + 1
      val endNum = (pageNumber + 1) * pageSize
      val sql = getFindAllSql(orgId, orgType, curAppGroupId, appName, curTime, startNum, endNum)
      //查询数据
      val findQuery = entityManager.createNativeQuery(sql)
      val list = findQuery.getResultList.asInstanceOf[util.ArrayList[Array[Object]]]
      val appGroupAppsList = list.asScala.map(it => {
        val appGroupApps = new AppGroupApps
        appGroupApps.setAppPackageName(it(0).asInstanceOf[String])
        appGroupApps.setAppName(it(1).asInstanceOf[String])
        appGroupApps
      }).toList
      //构建分页实体
      new PageImpl[AppGroupApps](appGroupAppsList.asJava, pageable, total)
    } else {
      //构建分页实体
      new PageImpl[AppGroupApps](new util.ArrayList[AppGroupApps](), pageable, 0)
    }
  }

  /**
    * 查询总数
    *
    * @param orgId         机构号
    * @param orgType       机构类型
    * @param curAppGroupId 应用分组
    * @param appName       应用名称
    * @param curTime       上传时间
    * @return 查询总数
    */
  def findCount(orgId: String, orgType: String, curAppGroupId: String, appName: String, curTime: String): java.lang.Long = {
    val sql = getCountSql(orgId, orgType, curAppGroupId, appName, curTime)
    val query = entityManager.createNativeQuery(sql)
    query.getSingleResult.asInstanceOf[java.math.BigDecimal].longValue()
  }

  /**
    * findAll查询sql
    *
    * @param orgId         机构号
    * @param orgType       机构类型
    * @param curAppGroupId 应用分组id
    * @param appName       应用名称
    * @param curTime       上传时间
    * @param startNum      查询的起始
    * @param endNum        查询的结束
    * @return findAll查询sql
    */
  def getFindAllSql(orgId: String, orgType: String, curAppGroupId: String, appName: String, curTime: String, startNum: Int, endNum: Int): String = {
    val findSql = "SELECT APP_PACKAGE_NAME as appPackageName, APP_NAME as appName " +
      "FROM (SELECT ROWNUM NUM, A.APP_PACKAGE_NAME, A.APP_NAME, A.CRE_TIME" +
      " FROM (SELECT TP.APP_PACKAGE_NAME, AL.APP_NAME, AL.CRE_TIME FROM TEMP TP " +
      "LEFT JOIN T_APP_FILE_LATEST AL " +
      "ON (TP.APP_PACKAGE_NAME = AL.APP_PACKAGE_NAME AND TP.ORG_ID = AL.ORG_ID AND TP.ORG_TYPE = AL.ORG_TYPE)) A) B "
    val sql = getWithSql(orgId, orgType, curAppGroupId) + findSql
    val where = getWhere(appName, curTime)
    if (where != "") {
      sql + where + s"AND NUM BETWEEN $startNum AND $endNum"
    } else {
      sql + s"WHERE NUM BETWEEN $startNum AND $endNum"
    }
  }

  /**
    * Count查询sql
    *
    * @param orgId         机构号
    * @param orgType       机构类型
    * @param curAppGroupId 应用分组id
    * @param appName       应用名称
    * @param creTime       上传时间
    * @return Count查询sql
    */
  def getCountSql(orgId: String, orgType: String, curAppGroupId: String, appName: String, creTime: String): String = {
    val countSql = "SELECT COUNT(1) FROM TEMP TP " +
      "LEFT JOIN T_APP_FILE_LATEST B " +
      "ON (TP.APP_PACKAGE_NAME = B.APP_PACKAGE_NAME AND TP.ORG_ID = B.ORG_ID AND TP.ORG_TYPE = B.ORG_TYPE )"
    val sql = getWithSql(orgId, orgType, curAppGroupId) + countSql
    sql + getWhere(appName, creTime)
  }

  /**
    * 得到with的sql语句
    *
    * @param orgId         机构号
    * @param orgType       机构类型
    * @param curAppGroupId 应用分组id
    * @return 查询sql
    */
  def getWithSql(orgId: String, orgType: String, curAppGroupId: String): String = {
    String.format("WITH TEMP AS (SELECT T.APP_PACKAGE_NAME, A.ORG_ID, A.ORG_TYPE  " +
      "FROM T_APP_GROUP_APPS T" +
      " JOIN T_APP_GROUP A ON T.APP_GROUP_ID = A.APP_GROUP_ID" +
      " WHERE T.APP_GROUP_ID = '%s') ", curAppGroupId)
  }

  /**
    * @param appName 应用名称
    * @param creTime 上传时间
    * @return 返回where条件
    */
  def getWhere(appName: String, creTime: String): String = {
    if (appName != null && !appName.trim.isEmpty) {
      if (creTime != null && !creTime.trim.isEmpty) {
        String.format(" WHERE B.APP_NAME = '%s' AND B.CRE_TIME LIKE '%s'", appName.trim, creTime.trim + "%")
      } else {
        String.format(" WHERE B.APP_NAME = '%s'", appName.trim)
      }
    } else {
      if (creTime != null && !creTime.trim.isEmpty) {
        String.format(" WHERE B.CRE_TIME LIKE '%s'", creTime.trim + "%")
      } else {
        ""
      }
    }
  }

}
