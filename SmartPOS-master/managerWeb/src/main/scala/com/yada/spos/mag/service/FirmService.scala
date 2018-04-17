package com.yada.spos.mag.service

import com.yada.spos.db.dao.FirmDao
import com.yada.spos.db.model.Firm
import com.yada.spos.db.query.FirmQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Scope, ScopedProxyMode}
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
  * Created by duan on 2016/8/29.
  */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
class FirmService {
  @Autowired
  var firmDao: FirmDao = _

  /**
    * 保存和更新实体
    *
    * @param firm 厂商实体
    * @return
    */
  def save(firm: Firm): Firm = {
    firmDao.saveAndFlush(firm)
  }

  /**
    * 根据条件查询厂商列表
    *
    * @param firmQuery 厂商查询类
    * @param pageable  分页
    * @return
    */
  def findPage(firmQuery: FirmQuery, pageable: Pageable): Page[Firm] = {
    firmDao.findAll(firmQuery, pageable)
  }

  /**
    * 根据厂商编号查询厂商信息
    *
    * @param firmCode 厂商代码
    * @return
    */
  def findOne(firmCode: String): Firm = {
    firmDao.findOne(firmCode)
  }

  /**
    * 查询所有的厂商
    *
    * @return 返回厂商列表
    */
  def findAll(): java.util.List[Firm] = {
    firmDao.findAll()
  }
}
