package com.yada.spos.mag.service

import com.yada.spos.db.dao.AuthDeviceDao
import com.yada.spos.db.model.AuthDevice
import com.yada.spos.db.query.AuthDeviceQuery
import com.yada.spos.mag.service.ext.AuthDeviceHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Scope, ScopedProxyMode}
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

/**
  * Created by duan on 2016/9/2.
  * 设备管理
  */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
class AuthDeviceService {

  @Autowired
  var authDeviceDao: AuthDeviceDao = _
  @Autowired
  var authDeviceHandler: AuthDeviceHandler = _

  /**
    * 根据条件查询数据
    *
    * @param authDeviceQuery 母POS查询类
    * @param pageable        分页
    * @return 母POS列表
    */
  def findAll(authDeviceQuery: AuthDeviceQuery, pageable: Pageable): Page[AuthDevice] = {
    authDeviceDao.findAll(authDeviceQuery, pageable)
  }

  /**
    * 根据条件查询单个母POS信息
    *
    * @param authPosSn 母POSsn
    * @return 母POS
    */
  def findOne(authPosSn: String): AuthDevice = {
    authDeviceDao.findOne(authPosSn)
  }

  /**
    * 保存或更新方法
    *
    * @param authPosSn 母pos sn
    * @param p12File   证书文件
    * @param orgId     机构号
    * @param orgType   机构类型
    * @param pwd       证书密码
    */
  def saveAndUpdate(authPosSn: String, p12File: MultipartFile, orgId: String, orgType: String, pwd: String): Unit = {
    val inputStream = p12File.getInputStream
    //获得证书公钥
    val publicKey = authDeviceHandler.getPublicKey(inputStream, pwd)
    val authDevice = new AuthDevice
    authDevice.setAuthPosSn(authPosSn)
    authDevice.setRsaPublicKey(publicKey)
    authDevice.setOrgId(orgId)
    authDevice.setOrgType(orgType)
    authDevice.setStatus("1")
    authDeviceDao.saveAndFlush(authDevice)
  }

}
