package com.yada.spos.mag.core.shiro

import org.apache.shiro.SecurityUtils
import org.springframework.stereotype.Service

@Service
class ShiroComponent {

  def findUser: User = {
    SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
  }
}
