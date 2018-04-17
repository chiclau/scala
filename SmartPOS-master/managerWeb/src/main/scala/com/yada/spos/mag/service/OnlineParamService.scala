package com.yada.spos.mag.service

import com.yada.spos.db.dao.OnlineParamDao
import com.yada.spos.db.model.OnlineParam
import com.yada.spos.db.query.OnlineParamQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Scope, ScopedProxyMode}
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * 设备参数维护
  * Created by nickDu on 2016/9/8.
  */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
class OnlineParamService {
  @Autowired
  var onlineParamDao: OnlineParamDao = _

  /**
    * 根据条件分页查询
    *
    * @param onlineParamQuery 设备参数查询类
    * @param pageable         分页
    * @return 查询结果
    */
  def findPage(onlineParamQuery: OnlineParamQuery, pageable: Pageable): Page[OnlineParam] = {
    onlineParamDao.findAll(onlineParamQuery, pageable)
  }

  /**
    * 根据id查询
    *
    * @param onlineParamId id
    * @return 查询结果
    */
  def findById(onlineParamId: String): OnlineParam = {
    onlineParamDao.findOne(onlineParamId.toLong)
  }

  /**
    * 更新保存实体
    */
  def saveOrUpdate(onlineParam: OnlineParam): OnlineParam = {
    onlineParamDao.saveAndFlush(onlineParam)
  }

  /**
    * 根据机构类型，机构id,参数名称查询
    *
    * @param orgId     机构id
    * @param orgType   机构类型
    * @param paramName 参数名称
    * @return 查询结果
    */
  def findByOrgIdAndOrgTypeAndParamName(orgId: String, orgType: String, paramName: String) = {
    onlineParamDao.findByOrgIdAndOrgTypeAndParamName(orgId, orgType, paramName)
  }
}
