package com.yada.spos.mag.service.ext

import com.yada.spos.db.dao.{HhapMerchantDao, OrgDao, TermWorkKeyDao}
import com.yada.spos.db.query.BTermWorkKeyQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.convert.decorateAll._

/**
  * Created by pangChangSong on 2016/9/5.
  * 处理机构信息，根据机构类型查询不同的机构
  */
@Component
class SystemHandler {

  @Autowired
  var hhapOrgDao: OrgDao = _
  @Autowired
  var hhapMerchantDao: HhapMerchantDao = _
  @Autowired
  var hhapBTermWorkKeyDao: TermWorkKeyDao = _

  /**
    * 验证终端号和商户号在终端密钥表中是否存在唯一数据
    *
    * @param orgType           机构类型
    * @param merId             商户号
    * @param termId            终端号
    * @param bTermWorkKeyQuery 终端密钥查询类
    * @return true-是/false-否
    */
  def validateBTermWorkKeyIsExistsUniqueMerAndTerm(orgType: String, merId: String, termId: String, bTermWorkKeyQuery: BTermWorkKeyQuery): Boolean = {
    orgType match {
      case "1" =>
        //固话
        false
      case "2" =>
        bTermWorkKeyQuery.setMerChantId(merId)
        bTermWorkKeyQuery.setTerminalId(termId)
        val bTermWorkKeys = hhapBTermWorkKeyDao.findAll(bTermWorkKeyQuery)
        bTermWorkKeys != null && bTermWorkKeys.size() == 1
    }
  }

  /**
    * 验证机构下是否存在商户
    *
    * @param orgId   机构号
    * @param orgType 机构类型
    * @param merId   商户号
    * @return true-是/false-否
    */
  def validateOrgIsExistsMer(orgId: String, orgType: String, merId: String): Boolean = {
    orgType match {
      case "1" =>
        //固话
        false
      case "2" =>
        val hhapMerchant = hhapMerchantDao.findOne(merId)
        hhapMerchant != null && hhapMerchant.getZoneOrgId == orgId
    }
  }

  /**
    * 获得机构号
    *
    * @param orgId   机构号
    * @param orgType 机构类型
    * @return 机构
    */
  def getOrg(orgId: String, orgType: String): OrgInfo = {
    orgType match {
      case "1" =>
        //固话
        null
      case "2" =>
        //总对总
        val hhapOrg = hhapOrgDao.findOne(orgId)
        OrgInfo(hhapOrg.orgId, orgType, hhapOrg.orgLev, hhapOrg.name)
    }
  }

  /**
    * 获得下一级的所有机构
    *
    * @param orgId   机构号
    * @param orgType 机构类型
    * @return 下一级的所有机构
    */
  def getNextLevOrgs(orgId: String, orgType: String): java.util.List[OrgInfo] = {
    val orgs = new java.util.ArrayList[OrgInfo]()
    orgType match {
      case "1" =>
      //固话
      case "2" =>
        //总对总
        val nextLevOrgs = hhapOrgDao.findByPOrgId(orgId)
        nextLevOrgs.asScala.foreach(hhapOrg => {
          orgs.add(OrgInfo(hhapOrg.orgId, orgType, hhapOrg.orgLev, hhapOrg.name))
        })
    }
    orgs
  }
}

/**
  * 机构
  *
  * @param orgId   机构号
  * @param orgType 机构类型
  * @param orgLev  机构级别
  * @param orgName 机构名称
  */
case class OrgInfo(orgId: String, orgType: String, orgLev: Long, orgName: String)

