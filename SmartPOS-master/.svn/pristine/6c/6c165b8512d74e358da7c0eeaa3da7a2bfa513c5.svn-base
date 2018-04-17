package com.yada.spos.mag.filter

import javax.servlet.FilterChain
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

/**
  * Created by locky on 2016/9/20.
  */
@Component
class MenuFilter extends OncePerRequestFilter {

  override def doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain): Unit = {
    // 不是ajax请求
    if (request.getHeader("X-Requested-With") == null && request.getServletPath.endsWith(".do")) {
      val splits = request.getServletPath.split("/")
      if (splits.nonEmpty) {
        val curMenuID = splits.find(it => !it.isEmpty).getOrElse("")
        request.setAttribute("curMenuID", curMenuID)
        request.setAttribute("isSubMenu", Array("firm", "device", "products").contains(curMenuID))
      }
    }
    filterChain.doFilter(request, response)
  }
}
