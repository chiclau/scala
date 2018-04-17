package com.yada.spos.db.dao

import com.yada.spos.db.model.PospOrgZmk
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor}

/**
  * 机构密钥dao
  * Created by nickDu on 2016/9/4.
  */
trait PospOrgZmkDao extends JpaRepository[PospOrgZmk, String] with JpaSpecificationExecutor[PospOrgZmk] {
  /**
    * 根据机构id查询
    *
    * @param orgId 机构id
    * @return
    */
  def findByOrgId(orgId: String): PospOrgZmk
}
