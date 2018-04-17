package com.yada.spos.db.dao

import com.yada.spos.db.model.AuthDeviceFlow
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor}

/**
  * 授权设备请求流水
  * Created by nickDu on 2016/9/5.
  */
trait AuthDeviceFlowDao extends JpaRepository[AuthDeviceFlow, String] with JpaSpecificationExecutor[AuthDeviceFlow] {

}
