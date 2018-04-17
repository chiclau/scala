package com.yada.spos.mag.core.shiro

/**
  * Created by locky on 2016/9/14.
  */
trait IUser {

  def findUser(username: String, password: Array[Char]): Option[User]

  def findRole(username: String, orgType: String): List[String]
}
