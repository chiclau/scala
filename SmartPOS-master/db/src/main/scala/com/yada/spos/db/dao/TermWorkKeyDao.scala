package com.yada.spos.db.dao

import com.yada.spos.db.model.{TermWorkKey, TermWorkKeyPK}
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor}

/**
  * Created by duan on 2016/9/2.
  * 终端密钥
  */
trait TermWorkKeyDao extends JpaRepository[TermWorkKey, TermWorkKeyPK] with JpaSpecificationExecutor[TermWorkKey] {

  /**
    * 根据商户号和终端号查询终端密钥
    *
    * @param merChantId 商户号
    * @param terminalId 终端号
    * @return 终端密钥
    */
  def findByMerChantIdAndTerminalId(merChantId: String, terminalId: String): TermWorkKey
}
