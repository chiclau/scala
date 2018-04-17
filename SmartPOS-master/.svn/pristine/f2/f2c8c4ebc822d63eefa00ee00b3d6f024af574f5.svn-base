package com.yada.spos.db.dao

import com.yada.spos.db.model.Org
import org.springframework.data.jpa.repository.JpaRepository

/**
  * Created by pangChangSong on 2016/8/30.
  * 机构dao
  */
trait OrgDao extends JpaRepository[Org, String] {

  /**
    * 根据机构id查询下一级机构
    *
    * @param orgId 机构id
    * @return 下一级机构列表
    */
  def findByPOrgId(orgId: String): java.util.List[Org]
}
