package com.yada.spos.db.dao

import com.yada.spos.db.model.Firm
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor}

/**
  * Created by duan on 2016/8/29.
  */
trait FirmDao extends JpaRepository[Firm, String] with JpaSpecificationExecutor[Firm] {

}
