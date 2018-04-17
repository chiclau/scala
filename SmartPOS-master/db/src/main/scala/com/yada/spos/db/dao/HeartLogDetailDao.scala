package com.yada.spos.db.dao

import com.yada.spos.db.model.HeartLogDetail
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor}

/**
  * Created by pangChangSong on 2016/9/6.
  * 心跳日志流水dao
  */
trait HeartLogDetailDao extends JpaRepository[HeartLogDetail, java.lang.Long] with JpaSpecificationExecutor[HeartLogDetail] {

}
