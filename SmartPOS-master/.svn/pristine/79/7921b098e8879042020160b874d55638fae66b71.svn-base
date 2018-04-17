package com.yada.spos.db.dao

import com.yada.spos.db.model.DictApkPermission
import org.springframework.data.jpa.repository.JpaRepository

/**
  * Created by pangChangSong on 2016/8/30.
  * apk涉及到的权限字典dao
  */
trait DictApkPermissionDao extends JpaRepository[DictApkPermission, String] {

  /**
    * 根据权限代码查询权限
    *
    * @param apkPermissionCode apk权限代码
    * @return 权限实体
    */
  def findByApkPermissionCode(apkPermissionCode: String): DictApkPermission
}
