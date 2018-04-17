package com.yada.spos.db.dao

import com.yada.spos.db.model.VAppGroupDevice
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor}

/**
  * Created by pangChangSong on 2016/10/18.
  * 应用分组关联设备、关联设备视图dao
  */
trait VAppGroupDeviceDao extends JpaRepository[VAppGroupDevice, String] with JpaSpecificationExecutor[VAppGroupDevice] {

}
