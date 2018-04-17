package com.yada.spos.mag.service

import com.yada.spos.db.dao.ProductsDao
import com.yada.spos.db.model.Products
import com.yada.spos.db.query.ProductsQuery
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.data.domain.Pageable

/**
  * Created by pangChangSong on 2016/9/25.
  * 产品管理service测试
  */
@RunWith(classOf[JUnitRunner])
class ProductsServiceTest extends FlatSpec with Matchers with MockitoSugar {

  val productsService = new ProductsService

  /**
    * 测试保存产品方法
    */
  "testSave" should "handle successful" in {
    productsService.productsDao = mock[ProductsDao]
    //调用方法
    productsService.save(mock[Products])
    //验证调用一次保存方法
    verify(productsService.productsDao, times(1)).saveAndFlush(any(classOf[Products]))
  }

  /**
    * 分页查询所有
    */
  "testFindAll" should "handle successful" in {
    productsService.productsDao = mock[ProductsDao]
    //调用方法
    productsService.findAll(mock[ProductsQuery], mock[Pageable])
    //验证调用一次分页查询全部方法
    verify(productsService.productsDao, times(1)).findAll(any(classOf[ProductsQuery]), any(classOf[Pageable]))
  }

  /**
    * 测试根据产品查询类查询一个产品（产品查询类里只有厂商编号和产品类型）
    */
  "testFindOne" should "handle successful" in {
    productsService.productsDao = mock[ProductsDao]
    //调用方法
    productsService.findOne(mock[ProductsQuery])
    //验证调用一次查询一个方法
    verify(productsService.productsDao, times(1)).findOne(any(classOf[ProductsQuery]))
  }

  /**
    * 测试根据厂商编号查询产品
    */
  "testFindByFirmCode" should "handle successful" in {
    productsService.productsDao = mock[ProductsDao]
    //调用方法
    productsService.findByFirmCode("AAAA")
    //验证调用一次根据厂商编号查询
    verify(productsService.productsDao, times(1)).findByFirmCode("AAAA")
  }
}
