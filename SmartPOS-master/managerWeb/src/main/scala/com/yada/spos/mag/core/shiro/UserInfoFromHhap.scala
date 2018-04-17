package com.yada.spos.mag.core.shiro

import com.yada.spos.common.BCDCodec
import com.yada.spos.db.dao.{HhapUserDao, HhapUserExtDao, HhapUserGroupDao, RoleDao}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.convert.decorateAll._
import scala.collection.mutable.ListBuffer

/**
  * 总对总用户信息
  * Created by nickDu on 2016/9/14.
  */
@Component
class UserInfoFromHhap extends IUser with BCDCodec {
  @Autowired
  var hhapUserDao: HhapUserDao = _
  @Autowired
  var hhapUserExtDao: HhapUserExtDao = _
  @Autowired
  var hhapUserGroupDao: HhapUserGroupDao = _
  @Autowired
  var roleDao: RoleDao = _

  override def findUser(username: String, password: Array[Char]): Option[User] = {
    val pwd = String.valueOf(password)
    //密码进行MD5加密
    val md5 = java.security.MessageDigest.getInstance("MD5")
    val b = pwd.getBytes("UTF-8")
    md5.update(b, 0, b.length)
    val md5Pwd = new java.math.BigInteger(1, md5.digest()).toString(16).toUpperCase()
    val hhapUser = hhapUserDao.findByLoginNameAndPwd(username, md5Pwd)
    if (hhapUser == null) {
      None
    } else {
      val hhapUserExt = hhapUserExtDao.findOne(hhapUser.userId)
      if (hhapUserExt == null)
        None
      else if (hhapUserExt.status == "0") //用户已停用
        None
      else
        Some(User(username, pwd, hhapUserExt.orgId))
    }
  }

  override def findRole(username: String, orgType: String): List[String] = {
    val hhapUser = hhapUserDao.findByLoginName(username)
    val hhapRoles = hhapUser.userGroupId.getRoles
    val roleList = ListBuffer.empty[String]
    hhapRoles.asScala.foreach(f => {
      val role = roleDao.findByOrgTypeAndSysRoleName(orgType, f.roleName)
      if (role != null)
        roleList += role.roleName
    })
    roleList.toList
  }
}
