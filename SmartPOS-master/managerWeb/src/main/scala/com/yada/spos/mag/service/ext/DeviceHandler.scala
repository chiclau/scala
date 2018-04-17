package com.yada.spos.mag.service.ext

import java.io.{InputStream, InputStreamReader}
import java.util

import au.com.bytecode.opencsv.{CSVParser, CSVReader}
import com.yada.spos.db.dao.{DeviceDao, ProductsDao}
import com.yada.spos.db.model.Device
import com.yada.spos.mag.exception.UploadException
import org.apache.poi.ss.usermodel._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.convert.decorateAll._

/**
  * Created by pangChangSong on 2016/9/20.
  * 设备处理
  */
@Component
class DeviceHandler {

  @Autowired
  var productsDao: ProductsDao = _

  @Autowired
  var deviceDao: DeviceDao = _

  /**
    * 读取xls信息
    *
    * @param inputStream 输入流
    * @param orgId       机构
    * @param orgType     机构类型
    * @return 设备集合
    */
  def readXls(inputStream: InputStream, orgId: String, orgType: String): List[Device] = {
    //验证上传厂商编号和sn是否重复集合
    val list = new util.ArrayList[String]
    val wb = WorkbookFactory.create(inputStream)
    val sheet = wb.getSheetAt(0)
    val rowEnd = sheet.getLastRowNum
    (1 to rowEnd).map(it => {
      val row = sheet.getRow(it)
      val cellEnd = row.getPhysicalNumberOfCells
      // 判断是否只有三列有值
      if (cellEnd != 3) {
        throw UploadException("文件格式错误，上传失败")
      }
      val sn = row.getCell(0).readCell(it + 1, 0)
      val firmCode = row.getCell(1).readCell(it + 1, 1)
      val prodCode = row.getCell(2).readCell(it + 1, 2)
      //查询产品
      val products = productsDao.findByFirmCodeAndProdCode(firmCode, prodCode)
      if (products == null) {
        throw UploadException(s"厂商[$firmCode]产品[$prodCode]不存在，上传失败!")
      }
      val tempDevice = deviceDao.findByDevSnAndFirmCode(sn, firmCode)
      if (tempDevice != null) {
        throw UploadException(s"厂商[$firmCode]设备[$sn]已存在，上传失败!")
      }
      val snLength = sn.length
      if (snLength > 32) {
        throw UploadException(s"厂商[$firmCode]sn[$sn]长度过长，上传失败!")
      }
      if (list.contains(firmCode + sn)) {
        throw UploadException(s"厂商[$firmCode]sn[$sn]重复，上传失败!")
      }
      list.add(firmCode + sn)
      val device = new Device
      device.setDevSn(sn)
      device.setFirmCode(firmCode)
      device.setFirmName(products.getFirmName)
      device.setProdCode(prodCode)
      device.setProdName(products.getProdName)
      device.setOrgId(orgId)
      device.setOrgType(orgType)
      device.setIsActive("0") //未激活
      device.setStatus("0") //为关联商户
      device
    }).toList
  }

  /**
    * 读取csv格式文件
    *
    * @param inputStream 输入流
    * @param orgId       机构
    * @param orgType     机构类型
    * @return 设备集合
    */
  def readCsv(inputStream: InputStream, orgId: String, orgType: String): List[Device] = {
    val list = new util.ArrayList[String]
    //从第二行开始读取
    val reader = new InputStreamReader(inputStream)
    val csvReader = new CSVReader(reader, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER,
      1, CSVParser.DEFAULT_STRICT_QUOTES)
    try {
      var count = 2 //跳过第一行
      csvReader.readAll().asScala.map(it => {
        if (it.length != 3) {
          throw UploadException("文件格式错误，上传失败")
        }
        val sn = it(0).readCsv(count, 0)
        val firmCode = it(1).readCsv(count, 1)
        val prodCode = it(2).readCsv(count, 2)
        //查询产品
        val products = productsDao.findByFirmCodeAndProdCode(firmCode, prodCode)
        if (products == null) {
          throw new RuntimeException(s"厂商[$firmCode]产品[$prodCode]不存在，上传失败!")
        }
        val tempDevice = deviceDao.findByDevSnAndFirmCode(sn, firmCode)
        if (tempDevice != null) {
          throw UploadException(s"厂商[$firmCode]设备[$sn]已存在，上传失败!")
        }
        val snLength = sn.length
        if (snLength > 32) {
          throw UploadException(s"厂商[$firmCode]sn[$sn]长度过长，上传失败!")
        }
        if (list.contains(firmCode + sn)) {
          throw UploadException(s"厂商[$firmCode]sn[$sn]重复，上传失败!")
        }
        count = count + 1
        list.add(firmCode + sn)
        val device = new Device
        device.setDevSn(sn)
        device.setFirmCode(firmCode)
        device.setFirmName(products.getFirmName)
        device.setProdCode(prodCode)
        device.setProdName(products.getProdName)
        device.setOrgId(orgId)
        device.setOrgType(orgType)
        device.setIsActive("0") //未激活
        device.setStatus("0") //为关联商户
        device
      }).toList
    } finally {
      if (reader != null) reader.close()
      if (csvReader != null) csvReader.close()
    }

  }

  implicit class ReadProperty(cell: Cell) {
    def readCell(rowNum: Int, cellNum: Int): String = {
      val cellValue = cell.getCellType match {
        //数字类型,先toLong将信息显示完整，再toString
        case 0 => cell.getNumericCellValue.toLong
        //字符串类型
        case 1 => cell.getRichStringCellValue.getString
        //日期类型
        case 2 =>
          if (DateUtil.isCellDateFormatted(cell)) {
            String.format("%1$tY-%1$tm-%1$td", cell.getDateCellValue)
          } else {
            cell.getNumericCellValue.toLong
          }
        case _ => " "
      }
      val cellStr = cellValue.toString
      if (cellStr.trim == "") throw UploadException(s"第[$rowNum]行，第[$cellNum]列为空，上传失败!")
      else cellStr
    }
  }

  implicit class ReadCsvProperty(csvStr: String) {
    def readCsv(rowNum: Int, cellNum: Int): String = {
      if (csvStr == null || csvStr.isEmpty) throw UploadException(s"第[$rowNum]行，第[$cellNum]列为空，上传失败!")
      else csvStr
    }
  }

}
