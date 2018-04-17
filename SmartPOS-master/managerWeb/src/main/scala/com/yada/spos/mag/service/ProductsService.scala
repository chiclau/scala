package com.yada.spos.mag.service

import com.yada.spos.db.dao.ProductsDao
import com.yada.spos.db.model.Products
import com.yada.spos.db.query.ProductsQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Scope, ScopedProxyMode}
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * Created by duan on 2016/8/30.
  */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
class ProductsService {
  @Autowired
  var productsDao: ProductsDao = _

  /**
    * 根据条件查询产品型号
    *
    * @param productsQuery 产品型号查询类
    * @param pageable      分页
    * @return
    */
  def findAll(productsQuery: ProductsQuery, pageable: Pageable): Page[Products] = {
    productsDao.findAll(productsQuery, pageable)
  }

  /**
    * 查询单个产品型号信息
    *
    * @param productsQuery 产品型号查询类
    * @return
    */
  def findOne(productsQuery: ProductsQuery): Products = {
    productsDao.findOne(productsQuery)
  }

  /**
    * 保存产品型号信息
    *
    * @param products 产品型号实体类
    * @return
    */
  def save(products: Products): Products = {
    productsDao.saveAndFlush(products)
  }

  /**
    * 根据厂商编号查询产品
    *
    * @param firmCode 厂商编号
    * @return 产品列表
    */
  def findByFirmCode(firmCode: String): java.util.List[Products] = {
    productsDao.findByFirmCode(firmCode)
  }

  /**
    * 根据厂商编号更新厂商名称
    *
    * @param firmCode 厂商编号
    * @param firmName 厂商名称
    * @return
    */
  def updateFirmNameByFirmCode(firmCode: String, firmName: String): Int = {
    productsDao.updateFirmNameByFirmCode(firmCode, firmName)
  }
}
