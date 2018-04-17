package com.yada.spos.mag.core.shiro

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import org.apache.shiro.authc._
import org.apache.shiro.authz.{AuthorizationInfo, SimpleAuthorizationInfo}
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Scope, ScopedProxyMode}
import org.springframework.stereotype.Component

import scala.collection.convert.decorateAll._

/**
  * 登陆用shiro的realm
  */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
class LoginRealm extends AuthorizingRealm with LazyLogging {
  @Autowired
  var userInfoFromHhap: UserInfoFromHhap = _

  val orgType = ConfigFactory.load.getString("orgType")

  override def doGetAuthorizationInfo(principalCollection: PrincipalCollection): AuthorizationInfo = {
    val username = this.getAvailablePrincipal(principalCollection).asInstanceOf[User].username
    orgType match {
      //      case "1" =>
      //        val roles = userInfoFromEposp.findRole(username, orgType)
      //        logger.info(s"user[$username] has roles $roles")
      //        new SimpleAuthorizationInfo(roles.toSet.asJava)
      case "2" =>
        val roles = userInfoFromHhap.findRole(username, orgType)
        logger.info(s"user[$username] has roles $roles")
        new SimpleAuthorizationInfo(roles.toSet.asJava)
      case _ => throw new RuntimeException("未找到对应机构类型的用户")
    }

  }

  override def doGetAuthenticationInfo(authenticationToken: AuthenticationToken): AuthenticationInfo = {
    val token = authenticationToken.asInstanceOf[UsernamePasswordToken]
    if (token.getUsername == null) throw new AccountException("用户名不可为空")
    if (token.getPassword == null) throw new AccountException("密码不可为空")
    orgType match {
      //      case "1" => val user = userInfoFromEposp.findUser(token.getUsername, token.getPassword) match {
      //        case None =>
      //          throw new AccountException("用户名密码不匹配，或用户已停用")
      //
      //        case Some(x) => x
      //      }
      //        val authInfo = new SimpleAuthenticationInfo(user, user.password, getName)
      //        logger.info(s"user[${user.username}] login success")
      //        authInfo
      case "2" =>
        val user = userInfoFromHhap.findUser(token.getUsername, token.getPassword) match {
          case None =>
            throw new AccountException("用户名密码不匹配，或用户已停用")
          case Some(x) => x
        }
        val authInfo = new SimpleAuthenticationInfo(user, user.password, getName)
        logger.info(s"user[${user.username}] login success")
        authInfo
      case _ =>
        throw new RuntimeException("未找到对应机构类型的用户")
    }
  }
}
