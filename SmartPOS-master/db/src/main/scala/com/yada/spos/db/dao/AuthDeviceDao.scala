package com.yada.spos.db.dao

import com.yada.spos.db.model.AuthDevice
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor}

/**
  * Created by duan on 2016/9/2.
  * 母POS管理
  */
trait AuthDeviceDao extends JpaRepository[AuthDevice, String] with JpaSpecificationExecutor[AuthDevice] {
  /**
    * 根据授权设备的序列号SN查询母pos
    *
    * @param authPosSn 授权设备的序列号SN
    * @return
    */
  def findByAuthPosSn(authPosSn: String): AuthDevice
}

