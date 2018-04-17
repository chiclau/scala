package com.yada.spos.mag.controller


import com.yada.spos.db.model.Products
import com.yada.spos.db.query.ProductsQuery
import com.yada.spos.mag.service.{DeviceService, FirmService, OtaHistoryService, ProductsService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

/**
  * Created by duan on 2016/8/30.
  * 产品型号
  */
@Controller
@RequestMapping(Array("products"))
class ProductsController {
  @Autowired
  var productsService: ProductsService = _
  @Autowired
  var firmService: FirmService = _
  @Autowired
  var deviceService: DeviceService = _
  @Autowired
  var otaHistoryService: OtaHistoryService = _

  /**
    * 菜单点击默认跳到空页面
    *
    * @param model model
    */
  @RequestMapping(value = Array("emptyList.do"))
  def emptyList(model: Model): String = {
    model.addAttribute("productsQuery", new ProductsQuery)
    val firmList = firmService.findAll()
    model.addAttribute("firmList", firmList)
    "products/list"
  }

  /**
    * 跳转到保存产品型号页面
    *
    * @param model model
    * @return
    */
  @RequestMapping(value = Array("insert.do"))
  def insert(model: Model): String = {
    val firmList = firmService.findAll()
    model.addAttribute("firmList", firmList)
    "products/insert"
  }

  /**
    * 保存产品型号实体
    *
    * @param products 产品型号实体类
    * @return
    */
  @RequestMapping(value = Array("save.do"))
  def save(products: Products): String = {
    productsService.save(products)
    "redirect:/products/list.do"
  }

  /**
    * 编辑产品型信息
    *
    * @param productsQuery 产品型号查询类
    * @param model         model
    * @return
    */
  @RequestMapping(value = Array("edit.do"))
  def edit(productsQuery: ProductsQuery, model: Model): String = {
    model.addAttribute("products", productsService.findOne(productsQuery))
    "products/edit"
  }

  /**
    * 保存产品型号实体
    *
    * @param products 产品型号实体类
    * @return
    */
  @RequestMapping(value = Array("update.do"))
  def update(products: Products): String = {
    productsService.save(products)
    deviceService.updateProdNameByProdCodeAndFirmCode(products.prodName, products.prodCode, products.firmCode)
    otaHistoryService.updateProdNameByProdCodeAndFirmCode(products.prodName, products.prodCode, products.firmCode)
    "redirect:/products/list.do"
  }

  /**
    * 根据条件查询产品型号信息
    *
    * @param productsQuery    产品型号查询类
    * @param productsPageable 分页
    * @param model            model
    * @return
    */
  @RequestMapping(value = Array("list.do"))
  def list(productsQuery: ProductsQuery, @PageableDefault productsPageable: Pageable, model: Model): String = {
    model.addAttribute("productsQuery", productsQuery)
    model.addAttribute("productsPageable", productsPageable)
    val page: Page[Products] = productsService.findAll(productsQuery, productsPageable)
    val firmList = firmService.findAll()
    model.addAttribute("firmList", firmList)
    model.addAttribute("page", page)
    "products/list"
  }


  /**
    * Ajax验证厂商编号和产品编号是否已经存在
    *
    * @param firmCode    厂商编号
    * @param productCode 厂品编号
    */
  @RequestMapping(value = Array("ajaxValidateDuplicateId.do"))
  @ResponseBody
  def ajaxValidateDuplicateId(firmCode: String, productCode: String): String = {
    val productsQuery = new ProductsQuery
    productsQuery.setFirmCode(firmCode)
    productsQuery.setProdCode(productCode)
    val product: Products = productsService.findOne(productsQuery)
    if (product != null) "1"
    else "0"
  }
}
